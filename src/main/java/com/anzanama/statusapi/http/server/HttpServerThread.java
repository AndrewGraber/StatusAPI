package com.anzanama.statusapi.http.server;

import com.anzanama.statusapi.StatusAPI;

public class HttpServerThread extends Thread {
    private int port;

    public void setPort(int port) {
        this.port = port;
    }

    public void run() {
        try {
            StatusAPI.logger.info("Starting HTTP Server on port: " + this.port);
            new HttpServer(this.port).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
