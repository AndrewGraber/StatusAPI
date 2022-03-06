package com.anzanama.statusapi.data;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class StatusData {
    private AtomicInteger players;
    private AtomicReference<LocalDateTime> startTime;
    private CopyOnWriteArrayList<PlayerData> playerList;
    private long[] tickList;
    private int tickListIndex;

    public StatusData() {
        players = new AtomicInteger(0);
        playerList = new CopyOnWriteArrayList<>();
        tickList = new long[60];
        tickListIndex = 0;
        startTime = new AtomicReference<>(LocalDateTime.now());
    }

    public void addPlayer() {
        while(true) {
            int existingPlayers = players.get();
            int newValue = existingPlayers + 1;
            if(players.compareAndSet(existingPlayers, newValue)) {
                return;
            }
        }
    }

    public void subtractPlayer() {
        while(true) {
            int existingPlayers = players.get();
            int newValue = existingPlayers - 1;
            if(players.compareAndSet(existingPlayers, newValue)) {
                return;
            }
        }
    }

    public int getNumPlayers() {
        return players.get();
    }

    public LocalDateTime getStartTime() {
        return startTime.get();
    }

    public CopyOnWriteArrayList<PlayerData> getPlayerList() {
        return playerList;
    }

    public void addPlayerToList(PlayerData player) {
        playerList.add(player);
    }

    public void removePlayerFromList(String username) {
        Iterator<PlayerData> it = playerList.iterator();
        int indexOfPlayer = -1;
        int counter = 0;
        while(it.hasNext()) {
            PlayerData data = it.next();
            if(data.username == username) {
                indexOfPlayer = counter;
                break;
            }
            counter++;
        }
        if(indexOfPlayer >= 0) {
            playerList.remove(indexOfPlayer);
        }
    }

    public void tick() {
        tickList[tickListIndex++] = System.currentTimeMillis();
        if(tickListIndex >= tickList.length) tickListIndex = 0;
    }

    public long[] getTickList() {
        return tickList;
    }

    public int getTickListIndex() {
        return tickListIndex;
    }
}
