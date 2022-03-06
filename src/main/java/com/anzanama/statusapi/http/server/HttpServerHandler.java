package com.anzanama.statusapi.http.server;

import com.anzanama.statusapi.StatusAPI;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.ReferenceCountUtil;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {
    private HttpResponder responder;

    public HttpServerHandler(HttpResponder responder) {
        this.responder = responder;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest) {
            ctx.writeAndFlush(responder.processRequest((FullHttpRequest) msg));
        } else {
            StatusAPI.logger.info("Message was not FullHttpRequest");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.writeAndFlush(HttpResponder.createResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR, cause.getMessage()));
        cause.printStackTrace();
        ctx.close();
    }
}
