package top.rectorlee.mapper;

import top.rectorlee.entity.User;
import top.rectorlee.entity.UserReq;

public interface UserMapper {
    User findById(Integer id);

    void saveUser(UserReq userReq);

    User findByUserName(String userName);
}
