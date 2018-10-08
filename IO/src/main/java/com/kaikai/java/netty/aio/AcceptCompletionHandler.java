package com.kaikai.java.netty.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * kaikai
 * 
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AysncTimeServerHandler> {

	@Override
	public void completed(AsynchronousSocketChannel result, AysncTimeServerHandler attachment) {
		attachment.serverChannel.accept(attachment, this);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		result.read(buffer, buffer, new ReadCompletionHandler(result));
	}

	@Override
	public void failed(Throwable exc, AysncTimeServerHandler attachment) {
		exc.printStackTrace();
		// 这个意思没有搞明白
		attachment.latch.countDown();
	}

}