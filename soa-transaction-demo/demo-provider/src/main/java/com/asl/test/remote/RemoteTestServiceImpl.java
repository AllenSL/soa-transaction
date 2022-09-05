package com.asl.test.remote;

import com.alibaba.dubbo.config.annotation.Service;
import com.asl.test.service.RemoteTestService;

@Service
public class RemoteTestServiceImpl implements RemoteTestService {

    @Override
    public String test1(String s, Integer age) {
        System.out.println("我请求到啦哈哈哈s: "+s + " age: "+ age);
        return "test1";
    }

    @Override
    public String revertTest1(String s, Integer age) {
        System.out.println("sdfds我异常回滚啦哈哈s: "+s + " age: "+ age);
        return "    public String revertTest1(String s, Integer age) {\n";
    }

    @Override
    public String test2(String s, Integer integer) {
        System.out.println("我请求到test2啦哈哈哈s: "+s + " age: "+ integer);
        return "test2";
    }

    @Override
    public String test3() {
        return null;
    }
}
