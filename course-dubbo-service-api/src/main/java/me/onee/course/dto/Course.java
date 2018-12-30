package me.onee.course.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by VOREVER
 * Date: 2018/12/30 21:28
 */
@Data
public class Course implements Serializable {

    private int id;
    private String title;
    private String description;
    private int teacherId;
}
