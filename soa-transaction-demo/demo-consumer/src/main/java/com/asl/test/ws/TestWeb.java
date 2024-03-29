package com.asl.test.ws;

import com.asl.test.remote.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestWeb {

    @Autowired
    TestService testService;

    @GetMapping("/test")
    public Object test(){
        return testService.clientTest();
    }
}
