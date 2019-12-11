package com.platon.agent.utils;

import org.web3j.protocol.core.Response;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class ReceiptResult extends Response<List<Receipt>> {
    private Map<String,Receipt> map = new ConcurrentHashMap<>();

    /**
     * 并行解码Logs
     */
    public void resolve(Long blockNumber, ExecutorService threadPool) throws InterruptedException {
        if(getResult().isEmpty()) return;
        CountDownLatch latch = new CountDownLatch(getResult().size());
        getResult().forEach(r->{
            map.put(HexTool.prefix(r.getTransactionHash()),r);
            threadPool.submit(()->{
                try {
                    r.setBlockNumber(blockNumber);
                    r.decodeLogs();
                }finally {
                    latch.countDown();
                }
            });
        });
        latch.await();
    }
}