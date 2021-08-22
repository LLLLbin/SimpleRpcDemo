package com.lbin.httpclient.controller;

import com.lbin.httpclient.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestController {
    @RequestMapping(value = "/test" ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String test(){
        return "{\"msg\":\"处理返回\"}";
    }

    @RequestMapping(value = "/params" ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String params(String name,String password){
        return "登录成功,用户名为"+name+"密码为"+password;
    }

    @RequestMapping(value = "/bodyParams" ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @CrossOrigin
    //@RequestBody将请求体中的参数转换成对象
    public String bodyParams(@RequestBody List<User> users){
        System.out.println(users);
        return users.toString();
    }
}
