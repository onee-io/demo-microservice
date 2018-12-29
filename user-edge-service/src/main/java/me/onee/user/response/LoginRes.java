package me.onee.user.response;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by VOREVER
 * Date: 2018/12/29 20:51
 */
@Data
public class LoginRes implements Serializable {

    String token;

}
