package com.kaikai.java.concurent.againLock;

/**
 * 模拟可重入锁和不可冲入锁
 * @author 23192
 *
 */
public class NoteAgainLock {

	private boolean isLocked;

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	public synchronized void lock() throws InterruptedException {
		while(isLocked) {
			wait();
		}
		isLocked = true;
	}
	
	public synchronized void unlock() {
		isLocked = false;
		notify();
	}

}
