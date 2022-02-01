package com.kiprono;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class Driver{
    public static void main(String[] args) {
        BlockingQueue<String> blockingDeque = new ArrayBlockingQueue<>(1024);

        ReadThread reader = new ReadThread(blockingDeque);
        WriteThread writer = new WriteThread(blockingDeque);

        new Thread(reader).start();
        new Thread(writer).start();


        System.out.println(writer.calculate("10 + 10 + 40 + 3"));
    }



}
