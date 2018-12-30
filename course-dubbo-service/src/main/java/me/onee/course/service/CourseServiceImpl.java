package me.onee.course.service;

import com.alibaba.dubbo.config.annotation.Service;
import me.onee.course.CourseService;
import me.onee.course.dto.Course;
import me.onee.course.dto.CourseDTO;
import me.onee.course.mapper.CourseMapper;
import me.onee.course.thrift.ServiceProvider;
import me.onee.thrift.user.UserInfo;
import me.onee.thrift.user.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VOREVER
 * Date: 2018/12/30 16:30
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ServiceProvider serviceProvider;

    @Override
    public List<CourseDTO> getCourseList() {
        List<Course> courses = courseMapper.getCourseList();
        List<CourseDTO> dtos = new ArrayList<>();
        if (courses != null && courses.size() != 0) {
            for (Course course : courses) {
                CourseDTO dto = new CourseDTO();
                BeanUtils.copyProperties(course, dto);
                try {
                    dto.setTeacher(toDTO(serviceProvider.getUserService().getUserById(course.getId())));
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                dtos.add(dto);
            }
        }
        return dtos;
    }

    private UserDTO toDTO(UserInfo userInfo) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(userInfo, dto);
        return dto;
    }
}
