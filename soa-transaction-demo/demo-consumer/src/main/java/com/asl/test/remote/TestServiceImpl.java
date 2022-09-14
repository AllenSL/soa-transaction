package com.asl.test.remote;

import com.asl.soatransaction.annotation.SOATransaction;
import com.asl.test.remote.service.TestService;
import com.asl.test.service.RemoteTestService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import request.Test1Request;

import java.util.UUID;

@Service
public class TestServiceImpl implements TestService {

   @Reference(version = "1.0.0")
   RemoteTestService remoteTestService;

    @Override
    @SOATransaction
    public String clientTest() {
        String ssssss = remoteTestService.m1("方法1", 1);
        remoteTestService.m3(new Test1Request("m3",30));
        remoteTestService.m2("方法2",2);
        remoteTestService.m4(UUID.randomUUID().toString(),new Test1Request("m3",30));
        int a = 2/0;
        return "";
    }


}
