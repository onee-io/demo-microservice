package me.onee.course.mapper;

import me.onee.course.dto.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by VOREVER
 * Date: 2018/12/30 16:32
 */
@Mapper
public interface CourseMapper {

    @Select("select id, title, description, teacher_id as teacherId from course")
    List<Course> getCourseList();

}
