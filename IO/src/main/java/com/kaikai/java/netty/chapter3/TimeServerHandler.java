package com.kaikai.java.netty.chapter3;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {
	private final static String QUERY_ORDER = "QUERY TIME ORDER";

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		// super.exceptionCaught(ctx, cause);
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("The time server receive order : " + body);
		String currentTime = QUERY_ORDER.equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString()
				: "BAD ORDER";
		// getBytes是Object的方法？no.是String的getBytes()函数
		// unpooled是byteBuf的函数
		// 为什么不调用write函数，而是在channelRead函数里面直接write
		// 使用ChannelHandlerContext
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.write(resp);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// super.channelReadComplete(ctx);
		ctx.flush();
	}
}
