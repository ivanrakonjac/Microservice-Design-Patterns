package com.demo.grpc;

import helloworld.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;

public class GreetingClient {

    public static void main(String[] args) throws Exception {
//        final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
//                .usePlaintext()
//                .build();
//
//        RequestMessage request = RequestMessage.newBuilder().setName("Himanshu").build();
//        ResponseMessage response;
//        ResponseMessage2 response2;
//        try {
//            response = GreetingServiceGrpc.newBlockingStub(channel).sayHello(request);
//            response2 = GreetingService2Grpc.newBlockingStub(channel).sayHello(request);
//            System.out.println(response.getMessage());
//            System.out.println(response2.getMessage() + " " + response2.getVal());
//        } catch (StatusRuntimeException e) {
//            return;
//        } finally {
//            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
//        }
    }

}
