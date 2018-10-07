package com.kaikai.java.netty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AysncTimeServerHandler implements Runnable {

	private int port;
	CountDownLatch latch;
	AsynchronousServerSocketChannel serverChannel;

	public AysncTimeServerHandler(int port) {
		this.port = port;
		try {
			serverChannel = AsynchronousServerSocketChannel.open();
			serverChannel.bind(new InetSocketAddress(port));
			System.out.println("The time server is start in port : " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		latch = new CountDownLatch(1);
		doAccept();
		try {
			latch.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void doAccept() {
		// 这里有个问题，就是accept的第一个参数attachment有什么用？api里面没有解释，api里面给出的例子此参数为空。
		// 个人根据字面意思推测，应该类似留给用户的一个可以传想传的参数的变量。在handler的第二个
		// 泛型参数。
		serverChannel.accept(this, new AcceptCompletionHandler());
	}

}
