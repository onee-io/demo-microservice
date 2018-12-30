package me.onee.course;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by VOREVER
 * Date: 2018/12/30 21:44
 */
@SpringBootApplication
public class CourseServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CourseServiceApplication.class).web(WebApplicationType.NONE).run(args);
    }

}
