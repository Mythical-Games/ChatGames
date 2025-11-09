package com.mythicalgames.chatgames.listeners;

import com.mythicalgames.chatgames.ChatGames;
import com.mythicalgames.chatgames.games.BaseGame;
import com.mythicalgames.chatgames.manager.GameManager;

import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.eventbus.EventHandler;
import org.allaymc.api.eventbus.event.player.PlayerChatEvent;

public class PlayerListener {

    @EventHandler(priority = 3)
    public void onPlayerChat(PlayerChatEvent ev) {
        EntityPlayer player = ev.getPlayer();
        BaseGame activeGame = ChatGames.getInstance().getGameManager().getActiveGame();

        if (activeGame == null) return;
    
        if (activeGame.checkAnswer(player, ev.getMessage())) {
            GameManager.broadcastMessage("§a" + player.getOriginName() + " §7answered correctly for §e" + activeGame.getName() + "§7!");

            ChatGames.getInstance().getGameManager().clearActiveGame();
        }
    }
}
