package me.onee.user;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by VOREVER
 * Date: 2018/12/29 13:15
 */
@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        // 非 web 应用
        new SpringApplicationBuilder(UserServiceApplication.class).web(WebApplicationType.NONE).run(args);
    }
}
