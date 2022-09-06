package com.asl.test.remote;

import com.asl.test.service.RemoteTestService;
import org.apache.dubbo.config.annotation.Service;
import request.Test1Request;

@Service
public class RemoteTestServiceImpl implements RemoteTestService {

    @Override
    public String m1(String name, Integer age) {
        System.out.println("m1正常调用成功 name: "+name + " age: "+ age);
        return "m1";
    }

    @Override
    public String revertm1(String name, Integer age) {
        System.out.println("revertm1异常回滚成功: "+name + " age: "+ age);
        return "revertm1";
    }

    @Override
    public String m2(String name, Integer age) {
        System.out.println("m1正常调用成功 name: "+name + " age: "+ age);
        return "m2";
    }

    @Override
    public String revertm2(String name) {
        System.out.println("revertm2异常回滚成功 name: "+ name);
        return "revertm2";
    }

    @Override
    public String m3(Test1Request test1Request) {
        System.out.println("m3正常调用成功 request:: "+test1Request.toString());
        return "m3";
    }

    @Override
    public String revertm3(Test1Request test1Request) {
        System.out.println("revertm3异常回滚成功 request:: "+test1Request.toString());
        return "revertm3";
    }
}
