package me.onee.course.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import me.onee.course.CourseService;
import me.onee.course.dto.CourseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by VOREVER
 * Date: 2018/12/30 22:38
 */
@Controller
@RequestMapping("/course")
public class CourseController {

    @Reference
    private CourseService courseService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<CourseDTO> getCourseList() {
        return courseService.getCourseList();
    }
}
