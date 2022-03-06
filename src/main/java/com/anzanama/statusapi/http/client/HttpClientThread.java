package com.anzanama.statusapi.http.client;

import com.anzanama.statusapi.StatusAPI;

public class HttpClientThread extends Thread {
    private String uri;
    private int port;
    private String payload;

    public void setRecipient(String uri, int port) {
        this.uri = uri;
        this.port = port;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void run() {
        try {
            StatusAPI.logger.info("Starting HTTP Client with host '" + this.uri + "' on port: " + this.port);
            new HttpClient(this.uri, this.port, this.payload).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
