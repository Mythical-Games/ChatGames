package com.mythicalgames.chatgames.games;

import com.mythicalgames.chatgames.ChatGames;
import com.mythicalgames.chatgames.Config.WordHint;
import com.mythicalgames.chatgames.manager.GameManager;
import com.mythicalgames.economy.MythicalEconomy;
import org.allaymc.api.entity.interfaces.EntityPlayer;

import java.util.List;
import java.util.Random;

public class GuessWordGame implements BaseGame {

    private final ChatGames plugin = ChatGames.getInstance();
    private String hint;
    private String correctWord;

    @Override
    public void start() {
        List<WordHint> words = plugin.config.guessWord.words;
        if (words.isEmpty()) return;

        Random r = new Random();
        WordHint selected = words.get(r.nextInt(words.size()));

        hint = selected.hint;
        correctWord = selected.word.toLowerCase();

        GameManager.broadcastMessage("§e[ChatGame] §7Guess the word: §b" + hint);
    }

    @Override
    public boolean checkAnswer(EntityPlayer player, String message) {
        if (message.trim().equalsIgnoreCase(correctWord)) {
            giveReward(player);
            GameManager.broadcastMessage("§a" + player.getOriginName() + " §7guessed the word correctly! §e(" + correctWord + ")");
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
        return "GuessWord";
    }

    @Override
    public int getDurationSeconds() {
        return 60;
    }
}
