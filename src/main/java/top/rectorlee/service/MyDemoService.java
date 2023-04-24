package top.rectorlee.service;

import top.rectorlee.entity.User;
import top.rectorlee.entity.UserReq;
import top.rectorlee.utils.Result;

public interface MyDemoService {
    Result<User> findById(Integer id);

    Result saveUser(UserReq userReq);

    Result login(UserReq userReq);
}
