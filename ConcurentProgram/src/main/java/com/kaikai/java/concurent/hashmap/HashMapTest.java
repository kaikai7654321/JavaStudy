package com.kaikai.java.concurent.hashmap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用java8的话，不会成环，但是会发生数据丢失的情况。
 * 
 * @author kaikai
 *
 */
public class HashMapTest extends Thread {
	/**
	 * 类的静态变量是各个实例共享的，因此并发的执行此线程一直在操作这两个变量 选择AtomicInteger避免可能的int++并发问题
	 */
	private static AtomicInteger ai = new AtomicInteger(0);
	// 初始化一个table长度为1的哈希表
	private static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(1);
	// 如果使用ConcurrentHashMap，不会出现类似的问题
	// private static ConcurrentHashMap<Integer, Integer> map = new
	// ConcurrentHashMap<Integer, Integer>(1);

	public void run() {
		for(int i = 0; i < 10000; i++)
		{
			map.put(i, i);
		}
//		while (ai.get() < 100000) { // 不断自增
//			map.put(ai.get(), ai.get());
//			ai.incrementAndGet();
//		}

		System.out.println(Thread.currentThread().getName() + "线程即将结束");
	}

	public static void main(String[] args) {
		HashMapTest mapTest = new HashMapTest();
		
		mapTest.concurentTest();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("数组大小：" + map.size());
	}

	/**
	 * 测试并发条件下，hash问题
	 */
	public void concurentTest() {
		HashMapTest t0 = new HashMapTest();
		HashMapTest t1 = new HashMapTest();
		HashMapTest t2 = new HashMapTest();
		HashMapTest t3 = new HashMapTest();
		HashMapTest t4 = new HashMapTest();
		HashMapTest t5 = new HashMapTest();
		HashMapTest t6 = new HashMapTest();
		HashMapTest t7 = new HashMapTest();
		HashMapTest t8 = new HashMapTest();
		HashMapTest t9 = new HashMapTest();

		t0.start();
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		t8.start();
		t9.start();

//		System.out.println("数组大小：" + map.size());
	}

	/**
	 * 测试fail-fast的
	 */
	public void failfastTest() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "one");
		map.put(2, "two");
		map.put(3, "three");

		Iterator iter = map.entrySet().iterator();
		map.remove(1);
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
	}
}