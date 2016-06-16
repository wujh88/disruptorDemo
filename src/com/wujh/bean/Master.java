package com.wujh.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {
    //任务队列
    protected Queue<Object> workQueue = new ConcurrentLinkedQueue<Object>();
    //worker进程队列
    protected Map<String, Thread> threadMap = new HashMap<String, Thread>();
    //结果集
    protected Map<String, Object> resultMap = new HashMap<String,Object>();
    
    //是否所有的子任务都结束
    
    public boolean isComplete(){
        for(Map.Entry<String, Thread> entry:threadMap.entrySet()){
            if(entry.getValue().getState()!=Thread.State.TERMINATED){
                return false;
            }
        }
        return true;
    }

    public Master(Worker worker,int countWorker) {
        worker.setResultMap(resultMap);
        worker.setWorkQueue(workQueue);
        for (int i = 0; i < countWorker; i++) {
            threadMap.put(Integer.toString(i), new Thread(worker,Integer.toString(i)));
        }
    }
    
    //提交任务
    
    public void submit(Object obj){
        workQueue.add(obj);
        //System.out.println(obj.toString());
    }

    
    
    //返回子任务结果集
    public Map<String, Object> getResultMap() {
        return resultMap;
    }
    
    //开始运行所有worker进程，并进行处理
    
    public void execute(){
        for(Map.Entry<String, Thread> entry:threadMap.entrySet()){
            entry.getValue().start();
        }
    }
    
}