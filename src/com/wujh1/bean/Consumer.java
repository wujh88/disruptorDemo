package com.wujh1.bean;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    
    private BlockingQueue<PCData> queue;//ª∫≥Â∂”¡–
    
    private static final int SLEEPTIME=1000;
    
    
    
    public Consumer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }



    @Override
    public void run() {
        System.out.println("start constomer id:"+Thread.currentThread().getId());
        Random r = new Random();
        try {
            while(true){
                PCData data = queue.take();
                int re = data.getIntData()*data.getIntData();
                System.out.println(MessageFormat.format("{0}*{1}={2}", data.getIntData(),data.getIntData(),re));
                
                Thread.sleep(r.nextInt(SLEEPTIME));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        
    }

}
