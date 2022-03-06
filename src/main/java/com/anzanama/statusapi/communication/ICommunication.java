package com.anzanama.statusapi.communication;

import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public interface ICommunication {
    void playerJoined(PlayerEvent.PlayerLoggedInEvent event);
    void playerLeft(PlayerEvent.PlayerLoggedOutEvent event);
}
