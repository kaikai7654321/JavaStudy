package com.kaikai.java.concurent.againLock;


public class Main {
	NoteAgainLock notAgainLock = new NoteAgainLock();
	AgainLock againLock = new AgainLock();
	public static void main(String[] args) {
		Main main = new Main();
		System.out.println("start");
		main.add2();
		main.minus2();
		System.out.println("end");
		
	}

	public void add() {
		
		try {
			notAgainLock.lock();
			System.out.println("這是一個加法,調用減法");
			minus();
			System.out.println("這是一個加法,調用減法之後");
			notAgainLock.unlock();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void minus() {
		try {
			notAgainLock.lock();
			System.out.println("這是一個減法");
			notAgainLock.unlock();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void add2() {
		
		try {
			againLock.lock();
			System.out.println("這是一個加法,調用減法");
			minus();
			System.out.println("這是一個加法,調用減法之後");
			againLock.unlock();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void minus2() {
		try {
			againLock.lock();
			System.out.println("這是一個減法");
			againLock.unlock();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
