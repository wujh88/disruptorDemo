package com.wujh.disruptor;

import com.lmax.disruptor.EventHandler;

public class TradeTransactionVasConsumer implements EventHandler<TradeTransaction> {

	@Override
	public void onEvent(TradeTransaction event, long sequence,
			boolean endOfBatch) throws Exception {
		//do something....
	}
	
}
