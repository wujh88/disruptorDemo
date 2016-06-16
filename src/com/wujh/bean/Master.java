package com.wujh.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {
    //�������
    protected Queue<Object> workQueue = new ConcurrentLinkedQueue<Object>();
    //worker���̶���
    protected Map<String, Thread> threadMap = new HashMap<String, Thread>();
    //�����
    protected Map<String, Object> resultMap = new HashMap<String,Object>();
    
    //�Ƿ����е������񶼽���
    
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
    
    //�ύ����
    
    public void submit(Object obj){
        workQueue.add(obj);
        //System.out.println(obj.toString());
    }

    
    
    //��������������
    public Map<String, Object> getResultMap() {
        return resultMap;
    }
    
    //��ʼ��������worker���̣������д���
    
    public void execute(){
        for(Map.Entry<String, Thread> entry:threadMap.entrySet()){
            entry.getValue().start();
        }
    }
    
}