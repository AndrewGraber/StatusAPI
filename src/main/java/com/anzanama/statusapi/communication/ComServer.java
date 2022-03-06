package com.anzanama.statusapi.communication;

import com.anzanama.statusapi.StatusAPI;
import com.anzanama.statusapi.config.ConfigVals;
import com.anzanama.statusapi.http.server.HttpServerThread;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class ComServer implements ICommunication {
    HttpServerThread thread;

    public ComServer() {
        StatusAPI.logger.info("Communication Method: http_server");
        this.thread = new HttpServerThread();
        this.thread.setPort(ConfigVals.server_port);
        this.thread.start();
    }

    public void playerJoined(PlayerEvent.PlayerLoggedInEvent event) {
        //Do nothing
    }

    public void playerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        //Do nothing
    }
}
