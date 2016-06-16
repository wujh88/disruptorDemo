package com.wujh.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Worker implements Runnable {
    //�������
    protected Queue<Object> workQueue;
    //����������
    protected Map<String,Object> resultMap = new HashMap<String, Object>();
    
    
    public void setWorkQueue(Queue<Object> workQueue) {
        this.workQueue = workQueue;
    }
    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public Object handle(Object input){
        return input;
    }
    @Override
    public void run() {
        while(true){
            Object input = workQueue.poll();
            
            if(null==input) break;
            //����������
            Object re = handle(input);
            resultMap.put(Integer.toString(input.hashCode()),re);
            //System.out.println(re.toString());
        }
    }

}
