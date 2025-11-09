package com.mythicalgames.chatgames.games;

import com.mythicalgames.chatgames.ChatGames;
import com.mythicalgames.chatgames.manager.GameManager;
import com.mythicalgames.economy.MythicalEconomy;
import org.allaymc.api.entity.interfaces.EntityPlayer;

import java.util.List;
import java.util.Random;

public class MissingLetterGame implements BaseGame {

    private final ChatGames plugin = ChatGames.getInstance();
    private String word;
    private String maskedWord;

    @Override
    public void start() {
        List<String> words = plugin.config.missingLetter.words;
        if (words.isEmpty()) return;

        Random r = new Random();
        word = words.get(r.nextInt(words.size())).toLowerCase();

        int missingIndex = r.nextInt(word.length());
        char missingChar = word.charAt(missingIndex);

        StringBuilder masked = new StringBuilder(word);
        masked.setCharAt(missingIndex, '_');
        maskedWord = masked.toString();

        GameManager.broadcastMessage("§e[ChatGame] §7Fill in the missing letter: §b" + maskedWord);
    }

    @Override
    public boolean checkAnswer(EntityPlayer player, String message) {
        if (message.length() == 1 && word.contains(message.toLowerCase())) {
            giveReward(player);
            GameManager.broadcastMessage("§a" + player.getOriginName() + " §7guessed the missing letter! §e(" + word + ")");
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
        return "MissingLetter";
    }

    @Override
    public int getDurationSeconds() {
        return 60;
    }
}
