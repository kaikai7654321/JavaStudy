package com.kaikai.java.concurent.againLock;

public class AgainLock {
	private boolean isLocked;
	private int lockCount = 0;
	private Thread createBy = null;
	public void lock() throws InterruptedException {
		Thread thread = Thread.currentThread();
		while(isLocked && (null == createBy || thread != createBy )) {
			wait();
			
		}
		
		isLocked = false;
		lockCount++;
		createBy = thread;
	}
	
	public void unlock() {
		Thread thread = Thread.currentThread();
		if(null != createBy && thread == createBy) {
			lockCount --;
			if(lockCount == 0) {
				createBy = null;
				isLocked = false;
				notify();
			}
			
		}
		
	}
}
