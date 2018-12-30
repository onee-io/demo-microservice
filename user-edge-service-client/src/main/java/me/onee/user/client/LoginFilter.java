package me.onee.user.client;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import me.onee.thrift.user.dto.UserDTO;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by VOREVER
 * Date: 2018/12/29 23:47
 */
public abstract class LoginFilter implements Filter {

    // 创建缓存，默认3分钟过期
    private static TimedCache<String, UserDTO> timedCache = CacheUtil.newTimedCache(DateUnit.MINUTE.getMillis() * 3);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getParameter("token");
        if (StrUtil.isEmpty(token)) {
            // 请求参数中无 token，则尝试从 cookie 中获取
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        token = cookie.getValue();
                    }
                }
            }
        }
        // 获取用户登录信息
        UserDTO dto = null;
        if (StrUtil.isNotEmpty(token)) {
            dto = timedCache.get(token); // 读缓存
            if (dto == null) {
                dto = requestUserInfo(token);
                if (dto != null) {
                    timedCache.put(token, dto); // 写缓存
                }
            }
        }
        // 用户未登录 跳转至登录页面
        if (dto == null) {
            response.sendRedirect("http://127.0.0.1:8082/user/login");
            return;
        }

        // 用户已登录 各服务可自己实现此方法
        login(request, response, dto);
        filterChain.doFilter(request, response);
    }

    protected abstract void login(HttpServletRequest request, HttpServletResponse response, UserDTO dto);

    @Override
    public void destroy() {

    }

    private UserDTO requestUserInfo(String token) {
        String url = "http://127.0.0.1:8082/user/auth";
        String result = HttpUtil.createPost(url).header("token", token).execute().body();
        if (result != null) {
            System.out.println(result);
            return JSON.parseObject(result, UserDTO.class);
        }
        return null;
    }


}
