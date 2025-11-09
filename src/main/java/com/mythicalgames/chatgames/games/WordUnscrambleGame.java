package com.mythicalgames.chatgames.games;

import com.mythicalgames.chatgames.ChatGames;
import com.mythicalgames.chatgames.manager.GameManager;
import com.mythicalgames.economy.MythicalEconomy;
import org.allaymc.api.entity.interfaces.EntityPlayer;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WordUnscrambleGame implements BaseGame {

    private final ChatGames plugin = ChatGames.getInstance();
    private String scrambledWord;
    private String originalWord;

    @Override
    public void start() {
        List<String> words = plugin.config.unscramble.words;
        if (words.isEmpty()) return;

        Random r = new Random();
        originalWord = words.get(r.nextInt(words.size())).toLowerCase();
        scrambledWord = scrambleWord(originalWord);

        GameManager.broadcastMessage("§e[ChatGame] §7Unscramble this word: §b" + scrambledWord);
    }

    @Override
    public boolean checkAnswer(EntityPlayer player, String message) {
        if (message.trim().equalsIgnoreCase(originalWord)) {
            giveReward(player);
            GameManager.broadcastMessage("§a" + player.getOriginName() + " §7unscrambled the word correctly! §e(" + originalWord + ")");
            return true;
        }
        return false;
    }

    private String scrambleWord(String word) {
        List<Character> letters = word.chars()
                .mapToObj(c -> (char) c)
                .toList();

        Collections.shuffle(letters);
        StringBuilder scrambled = new StringBuilder();
        for (char c : letters) {
            scrambled.append(c);
        }
        return scrambled.toString();
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
        return "WordUnscramble";
    }

    @Override
    public int getDurationSeconds() {
        return 60;
    }
}
