package com.anzanama.statusapi;

import com.anzanama.statusapi.data.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mod.EventBusSubscriber(Side.SERVER)
public class EventLinker {
    public static int counter = 0;

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        StatusAPI.data.addPlayer();
        StatusAPI.data.addPlayerToList(new PlayerData(event.player.getDisplayNameString(), LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        StatusAPI.com.forEach((comm) -> comm.playerJoined(event));
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        StatusAPI.data.subtractPlayer();
        StatusAPI.data.removePlayerFromList(event.player.getDisplayNameString());
        StatusAPI.com.forEach((comm) -> comm.playerLeft(event));
    }
}
