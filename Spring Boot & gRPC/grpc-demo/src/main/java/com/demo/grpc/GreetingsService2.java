package com.demo.grpc;

import helloworld.*;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class GreetingsService2 extends GreetingService2Grpc.GreetingService2ImplBase{

    /**
     * Server side method to greet user on request.
     * @param req
     * @param responseObserver
     */
    @Override
    public void sayHello(RequestMessage req, StreamObserver<ResponseMessage2> responseObserver) {
        ResponseMessage2 reply = ResponseMessage2.newBuilder().setMessage("Hello " + req.getName() + ", from server").setVal("IVAN").build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }



}

