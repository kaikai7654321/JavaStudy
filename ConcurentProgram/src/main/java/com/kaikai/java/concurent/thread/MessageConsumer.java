package com.kaikai.java.concurent.thread;

public class MessageConsumer implements Runnable{

	public void getMessage() {
		
			while (true) {
				synchronized (Messages.linkedList) {
				Messages.linkedList.notifyAll();
				if (Messages.linkedList.size() <= 0) {
					try {
						
						Messages.linkedList.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				String popString =  Messages.linkedList.pop();
				System.out.println(Thread.currentThread().getName() + " get " + popString + " ! messages reamin: " + Messages.linkedList.size());
			}

		}

	}
	@Override
	public void run() {
		getMessage();
	}

}
