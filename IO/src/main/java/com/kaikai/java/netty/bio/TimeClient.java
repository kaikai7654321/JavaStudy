package com.kaikai.java.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;

		try {
			socket = new Socket("127.0.0.1", port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// true的意义？
			out = new PrintWriter(socket.getOutputStream(), true);

			out.println("QUERY TIME ORDER");
			System.out.println("send order 2 server succeed.");
			String resp = in.readLine();
			System.out.println("Now is : " + resp);
		} catch (IOException e) {
			// 不需要处理
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException eio) {
					eio.printStackTrace();
				}
			}

			if (null != out) {
				// out 并没有强制抛出异常。
				out.close();
				out = null;
			}

			if (null != socket) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				socket = null;
			}
		}

	}

}
