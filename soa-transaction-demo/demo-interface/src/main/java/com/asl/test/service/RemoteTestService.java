package com.asl.test.service;


import com.asl.soatransaction.annotation.SOACommit;
import com.asl.soatransaction.annotation.SOARollBack;
import com.asl.soatransaction.annotation.SOAService;

@SOAService
public interface RemoteTestService {
     @SOACommit(
             rollBackMethod = "revertTest1",
             value = {0,1}
     )
     String test1(String name,Integer age);

     @SOARollBack
     String revertTest1(String name,Integer age);

     String test2(String name,Integer age);

     String test3();
}
