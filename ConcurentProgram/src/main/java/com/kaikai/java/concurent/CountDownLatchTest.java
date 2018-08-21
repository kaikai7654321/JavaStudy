package com.kaikai.java.concurent;

import java.util.concurrent.CountDownLatch;
/**
 * 1. 测试CountDownLatch.n个线程同时开始，同时结束。关键在于两个latch的await和countDown
 * 2. 采用的方法是测试各个线程开始的时间
 * 3. 线程开始后一定要又结束的countDown
 * 4. 使用了lamda表达式。
 * 5. 结果就相差几毫秒，应该可以接受。
 * @author 23192
 *
 */
public class CountDownLatchTest {

	public static void main(String[] args) {
		CountDownLatchTest test = new CountDownLatchTest();
		try {
			test.timeTasks(10, () -> System.out.println(System.currentTimeMillis()));
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	public long timeTasks(int nThreads, final Runnable task) throws InterruptedException
	{
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThreads);
		
		for (int i = 0; i < nThreads; i++ )
		{
			// ��ʾ������, �̳�Thread
			Thread t = new Thread() {
				public void run() {
					try {
						startGate.await();
						try {
							task.run();
						}finally {
							endGate.countDown();
						}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally {
						
					}
				}
			};
			t.start();
		}
		long start = System.nanoTime();
		startGate.countDown();
		endGate.await();
		long end = System.nanoTime();
		return end - start;
		
	}

}
