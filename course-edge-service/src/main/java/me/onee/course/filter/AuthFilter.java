package me.onee.course.filter;

import me.onee.thrift.user.dto.UserDTO;
import me.onee.user.client.LoginFilter;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by VOREVER
 * Date: 2018/12/30 22:55
 */
@Configuration
@ServletComponentScan
@WebFilter
public class AuthFilter extends LoginFilter {
    @Override
    protected void login(HttpServletRequest request, HttpServletResponse response, UserDTO dto) {
        request.setAttribute("user", dto);
    }
}
