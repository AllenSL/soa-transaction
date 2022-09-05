package com.asl.test.remote;

import com.asl.soatransaction.annotation.SOATransaction;
import com.asl.test.remote.service.TestService;
import com.asl.test.service.RemoteTestService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

   @Reference(version = "1.0.0")
   RemoteTestService remoteTestService;

    @Override
    @SOATransaction
    public String clientTest() {
        String ssssss = remoteTestService.test1("方法1", 1);
        remoteTestService.test2("方法2",2);
        int a = 2/0;
        return ssssss;
    }


}
