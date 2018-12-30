package me.onee.course.dto;

import lombok.Data;
import me.onee.thrift.user.dto.UserDTO;

import java.io.Serializable;

/**
 * Created by VOREVER
 * Date: 2018/12/30 16:23
 */
@Data
public class CourseDTO implements Serializable {

    private int id;
    private String title;
    private String description;
    private UserDTO teacher;

}
