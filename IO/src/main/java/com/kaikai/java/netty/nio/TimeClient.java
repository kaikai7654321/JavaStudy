package com.kaikai.java.netty.nio;

public class TimeClient {

	public static void main(String[] args) {
		int port = 8080;
		if (args != null && args.length > 0) {
			// 这里不是强制抛出异常，但是应该判断
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException exception) {
				// 使用默认值，什么都不需要写。只是为了捕捉异常
			}
		}
		// 添加处理逻辑
		new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001").start();
	}

}
