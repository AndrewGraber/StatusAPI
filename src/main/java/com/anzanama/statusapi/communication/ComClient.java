package com.anzanama.statusapi.communication;

import com.anzanama.statusapi.StatusAPI;
import com.anzanama.statusapi.config.ConfigVals;
import com.anzanama.statusapi.http.client.HttpClientThread;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ComClient implements ICommunication {
    private String uri;
    private int port;

    public ComClient() {
        StatusAPI.logger.info("Activating Communication Method: http_client");
        this.uri = ConfigVals.client_address;
        this.port = ConfigVals.client_port;
    }

    public void playerJoined(PlayerEvent.PlayerLoggedInEvent event) {
        HttpClientThread thread = new HttpClientThread();
        thread.setRecipient(uri, port);
        JsonObject data = new JsonObject();
        data.addProperty("event", "playerJoined");
        JsonObject playerData = new JsonObject();
        playerData.addProperty("username", event.player.getDisplayNameString());
        playerData.addProperty("timeJoined", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        data.add("player", playerData);
        Gson gson = new Gson();
        thread.setPayload(gson.toJson(data));
        thread.start();
        System.out.println("Thread ran... Continuing!");
    }

    public void playerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        HttpClientThread thread = new HttpClientThread();
        thread.setRecipient(uri, port);
        JsonObject data = new JsonObject();
        data.addProperty("event", "playerLeft");
        data.addProperty("username", event.player.getDisplayNameString());
        Gson gson = new Gson();
        thread.setPayload(gson.toJson(data));
        thread.start();
    }
}
