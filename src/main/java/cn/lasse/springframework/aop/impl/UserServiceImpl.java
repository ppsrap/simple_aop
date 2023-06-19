package cn.lasse.springframework.aop.impl;

import cn.lasse.springframework.aop.IUserService;

import java.util.Random;

public class UserServiceImpl implements IUserService {
    @Override
    public String queryUserInfo() {
        try {
            System.out.println("我是函数：queryUserInfo()");
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "穗爷，007，广州";
    }

    @Override
    public String register(String userName) {
        try {
            System.out.println("我是函数：register()");
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "注册用户：" + userName + " success！";
    }
}