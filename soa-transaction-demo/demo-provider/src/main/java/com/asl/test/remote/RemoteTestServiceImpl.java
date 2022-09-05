package com.asl.test.remote;

import com.asl.test.service.RemoteTestService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class RemoteTestServiceImpl implements RemoteTestService {

    @Override
    public String test1(String s, Integer age) {
        System.out.println("正常请求到啦: "+s + " age: "+ age);
        return "test1";
    }

    @Override
    public String revertTest1(String s, Integer age) {
        System.out.println("revertTest1异常回滚请求到啦: "+s + " age: "+ age);
        return "revertTest1";
    }

    @Override
    public String test2(String s, Integer integer) {
        System.out.println("我请求到test2啦哈哈哈s: "+s + " age: "+ integer);
        return "test2";
    }

    @Override
    public String revertTest2(String name) {
        System.out.println("revertTest2我请求到test2啦哈哈哈s: "+" age: "+ name);
        return "revertTest2"+name;
    }
}
