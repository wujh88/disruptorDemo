package com.wujh.business;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.wujh.bean.Master;
import com.wujh.bean.PlusWorker;

public class Client {
    public static void main(String[] args) {
    	Date date1= new Date();
    	System.out.println("date1--"+date1);
        Master m = new Master(new PlusWorker(), 5);//��������̴߳���
        for (int i = 0; i < 100; i++) {
            m.submit(i);
        }
        m.execute();
        int re = 0;
        Map<String, Object> resultMap = m.getResultMap();
        while(resultMap.size()>0||!m.isComplete()){
            Set<String> keys = resultMap.keySet();
            String key =  null;
            for(String k:keys){
                key=k;
                break;
            }
            Integer i = null;
            if(key != null){
                i = (Integer) resultMap.get(key);
            }
            if(i!=null){
                re+=i;//���м�������
            }
            
            if(key!=null){
                resultMap.remove(key);//��������ɵĽ���Ƴ�
            }
        }
        Date date2= new Date();
        System.out.println("date2--"+date2);
        System.out.println(re);
    }
}