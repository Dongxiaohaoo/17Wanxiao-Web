package top.dongxiaohao.dao;

import org.apache.ibatis.cursor.Cursor;
import top.dongxiaohao.entity.UserInfo;

import java.awt.*;
import java.util.ArrayList;

public interface UserDao {
    //根据电话查用户
    UserInfo queryUserByPhone(String phone);

    //记录新用户
    void addUser(UserInfo info);


    void updateUser(UserInfo info);

    Cursor<UserInfo> findAllUser();

    ArrayList<UserInfo> finallUsernormal();
}
