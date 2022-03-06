package com.anzanama.statusapi.http.server;

import com.anzanama.statusapi.StatusAPI;
import com.anzanama.statusapi.data.PlayerData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class RequestHandler {
    public static String routeAndBuildResponse(HttpRequest request, HttpHeaders headers) {
        JsonObject response;
        switch(request.uri()) {
            case "/players":
                response = players();
                break;
            case "/player-list":
                response = playerList();
                break;
            case "/uptime":
                response = uptime();
                break;
            case "/tps":
                response = tps();
                break;
            case "/modlist":
                response = modlist();
                break;
            default:
                response = endpointNotFound();
                break;
        }

        Gson gson = new Gson();
        return gson.toJson(response);
    }

    public static JsonObject players() {
        int numPlayers = StatusAPI.data.getNumPlayers();
        JsonObject data = new JsonObject();
        data.addProperty("players", numPlayers);
        return data;
    }

    public static JsonObject playerList() {
        Object[] playerData = StatusAPI.data.getPlayerList().toArray();
        Gson gson = new Gson();
        JsonObject data = new JsonObject();
        JsonArray playerArr = new JsonArray();
        for(Object player : playerData) {
            JsonObject playerjson = (JsonObject) gson.toJsonTree(player);
            playerArr.add(playerjson);
        }
        data.add("players", playerArr);
        return data;
    }

    public static JsonObject uptime() {
        LocalDateTime startTime = StatusAPI.data.getStartTime();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(startTime, now);
        JsonObject data = new JsonObject();
        data.addProperty("uptime", DurationFormatUtils.formatDurationHMS(duration.toMillis()));
        return data;
    }

    public static JsonObject endpointNotFound() {
        JsonObject data = new JsonObject();
        data.addProperty("error", "Unknown endpoint specified");
        return data;
    }

    /* This function taken from net.minecraftforge.server.command.CommandTps */
    private static String getDimensionPrefix(int dimId) {
        DimensionType providerType = DimensionManager.getProviderType(dimId);
        if (providerType == null)
        {
            return String.format("DIM_%2d", dimId);
        }
        else
        {
            return String.format("%s", providerType.getName());
        }
    }

    /* This function taken from net.minecraftforge.server.command.CommandTps */
    private static long mean(long[] values) {
        long sum = 0L;
        for (long v : values)
        {
            sum += v;
        }
        return sum / values.length;
    }

    private static final DecimalFormat TIME_FORMATTER = new DecimalFormat("########0.000");

    /* Parts of this function taken from net.minecraftforge.server.command.CommandTps */
    public static JsonObject tps() {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        JsonObject data = new JsonObject();
        for (Integer dimId : DimensionManager.getIDs())
        {
            JsonObject worldData = new JsonObject();
            double worldTickTime = mean(server.worldTickTimes.get(dimId)) * 1.0E-6D;
            double worldTPS = Math.min(1000.0/worldTickTime, 20);
            worldData.addProperty("meanTickTime", TIME_FORMATTER.format(worldTickTime));
            worldData.addProperty("meanTps", TIME_FORMATTER.format(worldTPS));
            data.add(getDimensionPrefix(dimId), worldData);
        }
        double meanTickTime = mean(server.tickTimeArray) * 1.0E-6D;
        double meanTPS = Math.min(1000.0/meanTickTime, 20);

        data.addProperty("overallTickTime", TIME_FORMATTER.format(meanTickTime));
        data.addProperty("overallTps", TIME_FORMATTER.format(meanTPS));
        return data;
    }

    public static JsonObject modlist() {
        Map<String, ModContainer> mods = Loader.instance().getIndexedModList();
        JsonObject data = new JsonObject();
        JsonArray modArr = new JsonArray();
        for(Map.Entry<String, ModContainer> entry : mods.entrySet()) {
            JsonObject modData = new JsonObject();
            ModContainer mod = entry.getValue();
            modData.addProperty("modid", entry.getKey());
            modData.addProperty("modname", mod.getName());
            modData.addProperty("version", mod.getVersion());
            modArr.add(modData);
        }
        data.add("mods", modArr);
        return data;
    }
}
