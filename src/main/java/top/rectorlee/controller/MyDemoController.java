package top.rectorlee.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.rectorlee.annotation.Secret;
import top.rectorlee.entity.User;
import top.rectorlee.entity.UserReq;
import top.rectorlee.service.MyDemoService;
import top.rectorlee.utils.Result;

/**
 * @author Lee
 * @description
 * @date 2023-04-24  18:01:08
 */
@Slf4j
@RestController
public class MyDemoController {
    @Autowired
    private MyDemoService service;

    /**
     * 获取用户信息时进行数据脱敏
     */
    @RequestMapping("/{id}")
    @Secret(reqPropsName = {}, respPropsName = {"password"})
    public Result<User> getUser(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

    /**
     * 新增用户时对密码进行加密存入数据库中
     */
    @RequestMapping("/save")
    @Secret(reqPropsName = {"password"}, respPropsName = {})
    public Result saveUser(@RequestBody @Validated UserReq userReq) {
        return service.saveUser(userReq);
    }

    /**
     * 登录校验时比较用户名和密码是否一致
     */
    @RequestMapping("/login")
    @Secret(reqPropsName = {"password"}, respPropsName = {})
    public Result login(@RequestBody @Validated UserReq userReq) {
        return service.login(userReq);
    }
}
