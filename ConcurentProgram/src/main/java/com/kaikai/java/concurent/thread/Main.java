package com.kaikai.java.concurent.thread;
/**
 * 1. 多个消费者和生产者
 * 2. 估计是线程比较少，计算机笔记四核，所以一般都是一个线程把所有循环走完才会执行下一个线程。
 *    也有几个线程交叉的。
 * 3. 一致想要每次put和get都是几个线程机会均等。其实只要while和synchronized交换位置即可。
 *    如果不按照下面的顺序，那么都是执行完while才会切换线程。
 * 4. yield没啥用。试着使用yield创作均等机会，但是没有用。
 * 
 * @author kaikai
 * @since  2018.9.2
 *
 */
public class Main {

	public static void main(String[] args) {
		int maxNum = 10;
		Thread consumer = new Thread(new MessageConsumer(),"consumer");
		Thread producer1 = new Thread(new MessageProducer(maxNum), "producer1");
		Thread producer2 = new Thread(new MessageProducer(maxNum), "producer2");
		
		consumer.start();
		producer1.start();
		producer2.start();

	}

}
