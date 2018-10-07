package com.kaikai.java.netty.aio;

/**
 * AIO, Netty权威指南。 这里的read和write都是建立在，client发送信息十分短的基础上。如果真实的，应该读取整句话。
 * 
 * @author kaikai
 *
 */
public class TimeServer {

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

		AysncTimeServerHandler timeServer = new AysncTimeServerHandler(port);
		new Thread(timeServer, "AIO-AsyncTimeServerHandler-001").start();

	}

}
