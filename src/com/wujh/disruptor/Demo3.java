package com.wujh.disruptor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

public class Demo3 {
	public static void main(String[] args) throws InterruptedException {
		long beginTime=System.currentTimeMillis();
		
		int bufferSize=1024;
		ExecutorService executor=Executors.newFixedThreadPool(4);
		//������캯�����������������˽�����2��demo֮��Ϳ��¾������ˣ���������~
		Disruptor<TradeTransaction> disruptor=new Disruptor<TradeTransaction>(new EventFactory<TradeTransaction>() {
			@Override
			public TradeTransaction newInstance() {
				return new TradeTransaction();
			}
		}, bufferSize, executor, ProducerType.SINGLE, new BusySpinWaitStrategy());
		
		//ʹ��disruptor������������C1,C2
		EventHandlerGroup<TradeTransaction> handlerGroup=disruptor.handleEventsWith(new TradeTransactionVasConsumer(),new TradeTransactionInDBHandler());
		
		TradeTransactionJMSNotifyHandler jmsConsumer=new TradeTransactionJMSNotifyHandler();
		//������C1,C2����֮��ִ��JMS��Ϣ���Ͳ��� Ҳ���������ߵ�C3
		handlerGroup.then(jmsConsumer);
		
		
		disruptor.start();//����
		CountDownLatch latch=new CountDownLatch(1);
		//������׼��
		executor.submit(new TradeTransactionPublisher(latch, disruptor));
		latch.await();//�ȴ�����������.
		disruptor.shutdown();
		executor.shutdown();
		
		System.out.println("�ܺ�ʱ:"+(System.currentTimeMillis()-beginTime));
//		long tt= System.currentTimeMillis();
//		for (int i = 0; i < 1000; i++) {
//			int j=i;
//		}
//		System.out.println("�ܺ�ʱ:"+(System.currentTimeMillis()-tt));
		
	}
}
