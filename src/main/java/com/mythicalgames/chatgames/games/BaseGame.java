package com.mythicalgames.chatgames.games;

import org.allaymc.api.entity.interfaces.EntityPlayer;

public interface BaseGame {
    void start();
    boolean checkAnswer(EntityPlayer player, String message);
    String getName();
    default int getDurationSeconds() {
        return 60;
    }
}

