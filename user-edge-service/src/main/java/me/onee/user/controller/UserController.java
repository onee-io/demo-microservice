package me.onee.user.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import me.onee.thrift.user.UserInfo;
import me.onee.thrift.user.dto.UserDTO;
import me.onee.user.common.RedisClient;
import me.onee.user.common.Result;
import me.onee.user.common.ResultCode;
import me.onee.user.response.LoginRes;
import me.onee.user.thrift.ServiceProvider;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by VOREVER
 * Date: 2018/12/29 16:37
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ServiceProvider serviceProvider;

    @Autowired
    private RedisClient redisClient;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result<LoginRes> login(@RequestParam String username,
                                  @RequestParam String password) {
        // 1、验证用户名和密码
        UserInfo userInfo = null;
        try {
            userInfo = serviceProvider.getUserService().getUserByName(username);
        } catch (TException e) {
            e.printStackTrace();
            return new Result<>(ResultCode.R401);
        }
        if (userInfo == null) {
            return new Result<>(ResultCode.R401);
        }
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        if (!userInfo.getPassword().equalsIgnoreCase(md5.digestHex(password))) {
            return new Result<>(ResultCode.R401);
        }

        // 2、生成 Token
        String token = RandomUtil.randomString(32);

        // 3、缓存用户
        redisClient.set(token, toDTO(userInfo), 3600);

        LoginRes res = new LoginRes();
        res.setToken(token);
        return new Result<>(ResultCode.R200, res);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam(required = false) String mobile,
                           @RequestParam(required = false) String email,
                           @RequestParam String verifyCode) {
        if (StrUtil.isEmpty(mobile) && StrUtil.isEmpty(email)) {
            return new Result(ResultCode.R402);
        }

        // 校验验证码
        String redisCode = null;
        if (StrUtil.isNotEmpty(mobile)) {
            redisCode = redisClient.get(mobile).toString();
        } else {
            redisCode = redisClient.get(email).toString();
        }
        if (!verifyCode.equals(redisCode)) {
            return new Result(ResultCode.R404);
        }

        // 注册用户
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword((new Digester(DigestAlgorithm.MD5)).digestHex(password));
        userInfo.setMobile(mobile);
        userInfo.setEmail(email);
        try {
            serviceProvider.getUserService().registerUser(userInfo);
            return new Result(ResultCode.R200);
        } catch (TException e) {
            e.printStackTrace();
            return new Result(ResultCode.R500);
        }
    }

    @RequestMapping(value = "/sendVerifyCode", method = RequestMethod.POST)
    @ResponseBody
    public Result sendVerifyCode(@RequestParam(required = false) String mobile,
                                 @RequestParam(required = false) String email) {
        if (StrUtil.isEmpty(mobile) && StrUtil.isEmpty(email)) {
            return new Result(ResultCode.R402);
        }
        // 生成验证码
        String message = "Verify code is: ";
        String code = RandomUtil.randomNumbers(6);
        try {
            boolean result = false;
            if (StrUtil.isNotEmpty(mobile)) {
                // 发送短信验证码
                result = serviceProvider.getMessageService().sendMobileMessage(mobile, message + code);
                redisClient.set(mobile, code, 300);
            } else {
                // 发送邮件验证码
                result = serviceProvider.getMessageService().sendEmailMessage(email, message + code);
                redisClient.set(email, code, 300);
            }
            if (result) {
                return new Result(ResultCode.R200);
            } else {
                return new Result(ResultCode.R403);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(ResultCode.R500);
        }
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    @ResponseBody
    public UserInfo auth(@RequestHeader String token) {
        return (UserInfo) redisClient.get(token);
    }

    private UserDTO toDTO(UserInfo userInfo) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(userInfo, dto);
        return dto;
    }

}
