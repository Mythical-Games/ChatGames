package com.mythicalgames.chatgames.games;

import com.mythicalgames.chatgames.ChatGames;
import com.mythicalgames.chatgames.manager.GameManager;
import com.mythicalgames.economy.MythicalEconomy;
import org.allaymc.api.entity.interfaces.EntityPlayer;

import java.util.List;
import java.util.Random;

public class FastTypeGame implements BaseGame {

    private final ChatGames plugin = ChatGames.getInstance();
    private String phrase;

    @Override
    public void start() {
        List<String> phrases = plugin.config.fastType.phrases;
        if (phrases.isEmpty()) return;

        Random r = new Random();
        phrase = phrases.get(r.nextInt(phrases.size()));

        GameManager.broadcastMessage("§e[ChatGame] §7Type this phrase exactly: §b" + phrase);
    }

    @Override
    public boolean checkAnswer(EntityPlayer player, String message) {
        if (message.trim().equalsIgnoreCase(phrase)) {
            giveReward(player);
            GameManager.broadcastMessage("§a" + player.getOriginName() + " §7typed the phrase correctly!");
            return true;
        }
        return false;
    }

    private void giveReward(EntityPlayer player) {
        double min = plugin.config.reward.min;
        double max = plugin.config.reward.max;
        double reward = min + (Math.random() * (max - min));
        reward = Math.round(reward * 100.0) / 100.0;

        MythicalEconomy.getAPI().addBalance(player.getLoginData().getUuid(), reward);
        player.sendMessage("§aYou won §6$" + reward + "§a!");
    }

    @Override
    public String getName() {
        return "FastType";
    }

    @Override
    public int getDurationSeconds() {
        return 60;
    }
}

