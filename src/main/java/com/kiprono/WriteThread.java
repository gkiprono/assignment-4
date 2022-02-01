package com.kiprono;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class WriteThread implements Runnable{
    protected BlockingQueue<String> blockingQueue = null;

    public WriteThread(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        PrintWriter writer = null;

        try{
            writer = new PrintWriter(new File("outputfile.txt"));
            String buffer = blockingQueue.take();
            while (! buffer.equals("EOF")){
                // do calculations here but first
                // check if string is not empty
                if (! buffer.isEmpty()){
                    writer.println(this.calculate(buffer));
                } else{ // write blank line
                    writer.println(buffer);
                }

                buffer = blockingQueue.take();

            }
        }catch ( IOException | InterruptedException e){
            e.printStackTrace();
        } finally {
            // close resources
            writer.close();
        }
    }

    public String calculate(String input){
        String result = null;

        // split input by space
        String[] splitStr = input.split(" ");

        boolean firstTime = true;
        int prev=0, next=0,sum=0;

        for (int i=0; i<splitStr.length-1;i++){

            if (isNumeric(splitStr[i])) {
                prev = Integer.parseInt(splitStr[i]);
                if (firstTime){
                    sum = prev;
                    firstTime = false;
                }
            } else {
                next = Integer.parseInt(splitStr[i+1]);
                sum += helper(next, splitStr[i]);
            }


        }

        return input + " = " + sum;
    }

    // uses regex to check if string is a number
    private  boolean isNumeric(String str){
        return str != null && str.matches("[0-9.]+");
    }

    private  int helper(int next,String symbol){
        int sum = 0;
        switch (symbol){
            case "+":
                // return next element to be added
                sum = next;
                break;
            case "-":
                // negate and return next element
                sum = -next ;
                break;
        }

        return sum;
    }
}
