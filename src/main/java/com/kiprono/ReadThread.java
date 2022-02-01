package com.kiprono;

import java.io.*;
import java.util.concurrent.BlockingQueue;

public class ReadThread implements Runnable{
    // use a blockingQueue -> thread will wait for queues availability
    protected BlockingQueue<String> blockingQueue;

    public ReadThread(BlockingQueue<String> blockingDeque) {
        this.blockingQueue = blockingDeque;
    }

    @Override
    public void run() {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("input.txt"));
            String buffer;

            // loop until eof

            while ((buffer = reader.readLine()) != null){
                blockingQueue.put(buffer);
            }
            blockingQueue.put("EOF");

        }catch ( IOException | InterruptedException e){
            e.printStackTrace();
        } finally {
            // close the resources
            try {
                assert reader != null;
                reader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
