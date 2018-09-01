package com.kaikai.java.concurent.thread;

/**
 * 多线程交替打印a,b
 * 
 * @author 23192
 *
 */
public class WaitTest {

	String s = "kaikai";

	/**
	 * 返回值不能放到synchronized之前。
	 * 
	 * @throws InterruptedException
	 */
	public void printA() {
		synchronized (s) {
			while (true) {
				s.notify();
				System.out.println("a");
				try {
					s.wait();
				} catch (InterruptedException e) {
					// 为什么会抛出这个异常呢
					e.printStackTrace();
				}
			}

		}
	}

	public void printB()  {
		synchronized (s) {
			while (true) {
				s.notify();
				System.out.println("b");
				 try {
					s.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	public static void main(String[] args) {
		WaitTest waitTest = new WaitTest();
		Thread threadA = new Thread(() -> {
			waitTest.printA();

		}, "a");
		threadA.start();
		Thread threadB = new Thread(() -> {
			waitTest.printB();

		}, "b");
		threadB.start();
	}

}
