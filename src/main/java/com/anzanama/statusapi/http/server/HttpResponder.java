package com.anzanama.statusapi.http.server;

import com.anzanama.statusapi.StatusAPI;
import com.google.gson.Gson;
import io.netty.handler.codec.http.*;
import scala.util.parsing.json.JSONObject;

import java.util.Optional;

import static io.netty.buffer.Unpooled.copiedBuffer;

public class HttpResponder {
    public FullHttpResponse processRequest(FullHttpRequest request) {
        FullHttpResponse response = createResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR, "Missing case error");

        if(isSupportedVersion(request)) {
            HttpHeaders headers = request.headers();
            Optional<String> host = Optional.ofNullable(headers.get("Host"));

            if(isAllowedHost(host)) {
                if(haveMatchingResource(request)) {
                    if(isSupportedMethod(request)) {
                        response = generateResponse(request, headers);
                    } else {
                        response = createResponse(HttpResponseStatus.METHOD_NOT_ALLOWED);
                    }
                } else {
                    response = createResponse(HttpResponseStatus.NOT_FOUND);
                }
            } else {
                response = createResponse(HttpResponseStatus.FORBIDDEN);
            }
        } else {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0,
                    HttpResponseStatus.HTTP_VERSION_NOT_SUPPORTED,
                    copiedBuffer("HTTP 1.1 Required".getBytes()));
        }

        if(HttpUtil.isKeepAlive(request)) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

        return response;
    }

    private boolean haveMatchingResource(HttpRequest request) {
        return true; //TODO
    }

    private boolean isSupportedMethod(HttpRequest request) {
        return true; //TODO
    }

    private FullHttpResponse generateResponse(HttpRequest request, HttpHeaders headers) {
        StatusAPI.logger.info("Got request from '" + request.uri() + "'");
        String data = RequestHandler.routeAndBuildResponse(request, headers);
        return createSuccessResponse(data); //TODO
    }

    private boolean isSupportedVersion(HttpRequest request) {
        return HttpVersion.HTTP_1_1 == request.protocolVersion();
    }

    private boolean isAllowedHost(final Optional<String> host) {
        return true;
    }

    public static FullHttpResponse createSuccessResponse() {
        return createSuccessResponse(HttpResponseStatus.OK.reasonPhrase());
    }

    public static FullHttpResponse createSuccessResponse(final String payload) {
        return createResponse(HttpResponseStatus.OK, payload);
    }

    public static FullHttpResponse createResponse(final HttpResponseStatus status) {
        return createResponse(status, status.reasonPhrase());
    }

    public static FullHttpResponse createResponse(final HttpResponseStatus status, String payload) {
        return createResponse(status, payload.getBytes());
    }

    public static FullHttpResponse createResponse(final HttpResponseStatus status, byte[] payload) {
        return createResponse(status, payload, HttpVersion.HTTP_1_1);
    }

    public static FullHttpResponse createResponse(final HttpResponseStatus status, byte[] payload, final HttpVersion version)
    {
        if (0 < payload.length)
        {
            FullHttpResponse response = new DefaultFullHttpResponse(version, status, copiedBuffer(payload));
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, payload.length);
            return response;
        }
        else
        {
            return new DefaultFullHttpResponse(version, status);
        }
    }
}
