package me.onee.user.mapper;

import me.onee.thrift.user.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by VOREVER
 * Date: 2018/12/29 12:40
 */
@Mapper
public interface UserMapper {

    @Select("select id, username, password, real_name as realName, mobile, email from user where id = #{id}")
    UserInfo getUserById(@Param("id") int id);

    @Select("select id, username, password, real_name as realName, mobile, email from user where username = #{username}")
    UserInfo getUserByName(@Param("username") String username);

    @Insert("insert into user (username, password, real_name, mobile, email) " +
            "values (#{u.username}, #{u.password}, #{u.realName}, #{u.mobile}, #{u.email})")
    void registerUser(@Param("u") UserInfo userInfo);

}
