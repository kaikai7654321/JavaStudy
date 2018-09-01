package com.kaikai.java.concurent.thread;

/**
 * 测试yield的作用。 从下面可以看出，没有效果。也就和api里面的那句话很符合： A hint to the scheduler that the
 * current thread is willing to yield its current use of a processor.
 * 可能有效果，也可能没有。会不会在线程设置了优先级之后yield的效果会比较明显？这个没有测试。感觉 也没有必要。 毕竟，不建议使用。
 * 
 * @author kaikai
 * @since  2018.9.1
 *
 */
public class YieldTest {

	public static void main(String[] args) {
		String lock = "";
		Thread thread1 = new Thread(() -> {
			synchronized (lock) {
				for (int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName() + ": " + i);
					System.out.println("before thread1 yield!" + " !");
					Thread.yield();
					System.out.println("after thread1 yield!" + " !");
				}

			}

		}, "thread1");
		Thread thread2 = new Thread(() -> {
			synchronized (lock) {
				for (int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName() + ": " + i);
					System.out.println("before thread2 yield!" + " !");
					Thread.yield();
					System.out.println("after thread2 yield!" + " !");
				}
			}

		}, "thread2");

		Thread thread3 = new Thread(() -> {
			synchronized (lock) {
				for (int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName() + ": " + i);
					System.out.println("before thread3 yield!" + " !");
					Thread.yield();
					System.out.println("after thread3 yield!" + " !");
				}
			}

		}, "thread3");

		thread1.start();
		thread2.start();
		thread3.start();
	}

}
