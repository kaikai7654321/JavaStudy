package com.kaikai.java.netty.fakenio;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimeServerHandlerExecutorPool {
	private ExecutorService executor;

	// 这里做了修改。书中使用的不是Executors,而是new了ThreadPoolExecutor。但是我觉得没有必要。使用Executors更简单。
	TimeServerHandlerExecutorPool(int maxPoolSize) {
		executor = Executors.newFixedThreadPool(maxPoolSize);
	}

	public void execute(Runnable task) {
		executor.execute(task);
	}
}
