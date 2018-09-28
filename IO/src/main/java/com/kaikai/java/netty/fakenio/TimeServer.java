package com.kaikai.java.netty.fakenio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author kaikai.2018.9.26
 *
 */
public class TimeServer {

	public static void main(String[] args) throws IOException {
		int port = 8080;
		if (args != null && args.length > 0) {
			// 这里不是强制抛出异常，但是应该判断
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException exception) {
				// 使用默认值，什么都不需要写。只是为了捕捉异常
			}
		}
		ServerSocket socketServer = null;
		// 这里的try还有什么用处？ 引出finally。
		try {
			socketServer = new ServerSocket(port);
			Socket socket = null;
			TimeServerHandlerExecutorPool executor = new TimeServerHandlerExecutorPool(50);
			while (true) {
				socket = socketServer.accept();
				// 每个socket做出反应
				executor.execute(new TimeServerHandler(socket));
			}

		} finally {
			if (socketServer != null) {
				System.out.println("Time server is to close!");
				socketServer.close();
				socketServer = null;
			}
		}

	}

}
