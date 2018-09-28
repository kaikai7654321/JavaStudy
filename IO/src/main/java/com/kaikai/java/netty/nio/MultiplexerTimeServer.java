package com.kaikai.java.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {
	private final static String QUERY_ORDER = "QUERY TIME ORDER";
	private Selector selector;
	private ServerSocketChannel servChannel;
	// 根据前面thread的那篇博客，使用volatile修饰的变量是暂停一个线程最好的方法。
	private volatile boolean stop;

	MultiplexerTimeServer(int port) {
		try {
			selector = Selector.open();
			servChannel = ServerSocketChannel.open();
			servChannel.configureBlocking(false);
			// The wildcard is a special local IP address. It usually means "any" and can
			// only be used for bind operations.
			servChannel.socket().bind(new InetSocketAddress(port), 1024);
			servChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("TimeServer is start on the port : " + port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void stop() {
		this.stop = true;
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				selector.select(1000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				SelectionKey key = null;
				while (iterator.hasNext()) {
					key = iterator.next();
					iterator.remove();
					// 需要加try，如果没有try，那么抛出异常时不会关闭流的。
					try {
						handleInput(key);
					} catch (Exception e) {

					}

					if (null != key) {
						key.cancel();
						if (null != key.channel()) {
							key.channel().close();
						}
					}
				}
			} catch (IOException e) {

			}
		}
		if (null != selector) {
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleInput(SelectionKey key) throws IOException {
		// A key is valid upon creation and remains so until it is cancelled,its channel
		// is closed, or its selector is closed.
		if (key.isValid()) {
			// 拿到的key有两种可能，acceptable和readable;准确说不止这两种可能，但是这里我们只关心这两种。
			if (key.isAcceptable()) {
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false);
				sc.register(selector, SelectionKey.OP_READ);

			}

			if (key.isReadable()) {
				SocketChannel sc = (SocketChannel) key.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if (readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);

					String body = new String(bytes, "UTF-8");
					System.out.println("The time server receive order : " + body);
					String currentTime = QUERY_ORDER.equalsIgnoreCase(body)
							? new Date(System.currentTimeMillis()).toString()
							: "BAD ORDER";
					doWrite(sc, currentTime);

				} else if (readBytes < 0) {
					key.cancel();
					sc.close();
				} else {
					// 为什么读取0byte要区别对待
				}

			}
		}
	}

	private void doWrite(SocketChannel sc, String response) throws IOException {
		if (null != response && response.trim().length() > 0) {
			byte[] bytes = response.getBytes();
			// 这样就可以避免使用数组定义的固定长度？
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			sc.write(writeBuffer);

		}
	}

}
