package com.asl.test.service;


import com.asl.soatransaction.annotation.SOACommit;
import com.asl.soatransaction.annotation.SOARollBack;
import com.asl.soatransaction.annotation.SOAService;
import request.Test1Request;

@SOAService
public interface RemoteTestService {
     @SOACommit(
             rollBackMethod = "revertm1",
             value = {0,1}
     )
     String m1(String name,Integer age);

     @SOARollBack
     String revertm1(String name,Integer age);


     @SOACommit(
             rollBackMethod = "revertm2",
             value = {0}
     )
     String m2(String name,Integer age);

     @SOARollBack
     String revertm2(String name);

     @SOACommit(
             rollBackMethod = "revertm3",
             value = {0}
     )
     String m3(Test1Request test1Request);

     @SOARollBack
     String revertm3(Test1Request test1Request);


     @SOACommit(
             rollBackMethod = "revertm4",
             value = {0}
     )
     String m4(String uuId,Test1Request test1Request);

     @SOARollBack
     String revertm4(String uuId,Test1Request test1Request);
}
