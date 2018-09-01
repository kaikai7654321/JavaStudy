package com.kaikai.java.concurent.thread;


import java.util.Date;

public class MessageProducer implements Runnable{

	private int MAX_NUM ;

	public MessageProducer( int maxNum) {
		this.MAX_NUM = maxNum;
	}

	public void putMessage() {
			
			while (true) {
				synchronized (Messages.linkedList) {
				Messages.linkedList.notifyAll();
				if (Messages.linkedList.size() >= MAX_NUM) {
					try {
						Messages.linkedList.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Date date = new Date();

				Messages.linkedList.push(date.toString());
				System.out.println(Thread.currentThread().getName() + " put " + date.toString() + " into messages!"+ " ! messages reamin: " + Messages.linkedList.size());

			}

		}

	}

	@Override
	public void run() {
		putMessage();
	}
}
