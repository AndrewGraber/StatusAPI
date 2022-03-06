package com.anzanama.statusapi.http.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HttpClient {
    private String uri;
    private int port;
    private String payload;

    public HttpClient(String uri, int port, String payload) {
        this.uri = uri;
        this.port = port;
        this.payload = payload;
    }

    public void run() throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            //b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new HttpClientCodec());
                    ch.pipeline().addLast(new HttpObjectAggregator(1048576));
                    ch.pipeline().addLast(new HttpClientHandler());
                    ch.pipeline().addLast(new HttpRequestEncoder());
                }
            });

            HttpRequest request = HttpRequester.createRequest(this.uri, this.payload);
            request.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
            System.out.println("Sending message!");
            Channel f = b.connect(uri, port).channel();
            f.writeAndFlush(request);
            f.closeFuture().sync();
            System.out.println("Message was sent!");
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
