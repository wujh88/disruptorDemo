package com.wujh1.bean;

public final class PCData {
    private final int intData;

    public PCData(int intData) {
        this.intData = intData;
    }
    
    public PCData(String strData){
        this.intData = Integer.valueOf(strData);
    }
    
    public synchronized int getIntData() {
        return intData;
    }

    @Override
    public String toString() {
        return "data:" + intData;
    }
    
}
