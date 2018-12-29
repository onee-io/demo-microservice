package me.onee.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by VOREVER
 * Date: 2018/12/29 20:50
 */
@Data
public class UserDTO implements Serializable {

    private int id;
    private String username;
    private String password;
    private String realName;
    private String mobile;
    private String email;

}
