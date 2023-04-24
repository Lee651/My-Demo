package top.rectorlee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.rectorlee.entity.User;
import top.rectorlee.entity.UserReq;
import top.rectorlee.mapper.UserMapper;
import top.rectorlee.service.MyDemoService;
import top.rectorlee.utils.Result;

import java.util.Objects;

/**
 * @author Lee
 * @description
 * @date 2023-04-24  18:13:58
 */
@Service
public class MyDemoServiceImpl implements MyDemoService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<User> findById(Integer id) {
        User user = userMapper.findById(id);

        return new Result(HttpStatus.OK.value(), "查询成功", user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveUser(UserReq userReq) {
        userMapper.saveUser(userReq);

        return new Result(HttpStatus.OK.value(), "保存成功");
    }

    @Override
    public Result login(UserReq userReq) {
        User user = userMapper.findByUserName(userReq.getName());

        if (Objects.isNull(user)) {
            return new Result(HttpStatus.NOT_FOUND.value(), "用户不存在");
        }

        if (!userReq.getPassword().equals(user.getPassword())) {
            return new Result(HttpStatus.FORBIDDEN.value(), "密码不对");
        }

        return new Result(HttpStatus.OK.value(), "登录成功");
    }
}
