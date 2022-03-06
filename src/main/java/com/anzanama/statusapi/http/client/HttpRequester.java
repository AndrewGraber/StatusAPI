package com.anzanama.statusapi.http.client;

import io.netty.handler.codec.http.*;

import static io.netty.buffer.Unpooled.copiedBuffer;

public class HttpRequester {
    public static FullHttpRequest createRequest(String uri, String payload) {
        return createRequest(uri, payload.getBytes());
    }

    public static FullHttpRequest createRequest(String uri, byte[] payload) {
        return createRequest(uri, payload, HttpVersion.HTTP_1_1);
    }

    public static FullHttpRequest createRequest(String uri, byte[] payload, final HttpVersion version) {
        if (0 < payload.length)
        {
            FullHttpRequest request = new DefaultFullHttpRequest(version, HttpMethod.POST, uri, copiedBuffer(payload));
            request.headers().set(HttpHeaderNames.CONTENT_LENGTH, payload.length);
            return request;
        }
        else
        {
            return new DefaultFullHttpRequest(version, HttpMethod.POST, uri);
        }
    }
}
