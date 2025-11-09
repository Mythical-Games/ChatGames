package com.mythicalgames.chatgames.games;

import com.mythicalgames.chatgames.ChatGames;
import com.mythicalgames.chatgames.manager.GameManager;
import com.mythicalgames.economy.MythicalEconomy;
import org.allaymc.api.entity.interfaces.EntityPlayer;

import java.util.List;
import java.util.Random;

public class SequenceGuessGame implements BaseGame {

    private final ChatGames plugin = ChatGames.getInstance();
    private String pattern;
    private String correctAnswer;

    @Override
    public void start() {
        List<String> patterns = plugin.config.sequence.patterns;
        if (patterns.isEmpty()) return;

        Random r = new Random();
        pattern = patterns.get(r.nextInt(patterns.size()));

        correctAnswer = extractAnswer(pattern);

        GameManager.broadcastMessage("§e[ChatGame] §7Sequence: §b" + pattern);
    }

    @Override
    public boolean checkAnswer(EntityPlayer player, String message) {
        if (message.trim().equalsIgnoreCase(correctAnswer)) {
            giveReward(player);
            GameManager.broadcastMessage("§a" + player.getOriginName() + " §7solved the sequence correctly! §e(" + correctAnswer + ")");
            return true;
        }
        return false;
    }

    private String extractAnswer(String pattern) {
        // Example patterns: "2, 4, 8, 16, ?"
        // This is a simple arithmetic progression detection
        String[] parts = pattern.split(",");
        int n = parts.length;

        int[] numbers = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            numbers[i] = Integer.parseInt(parts[i].trim());
        }

        int diff = numbers[1] - numbers[0];
        boolean isArithmetic = true;
        for (int i = 2; i < numbers.length; i++) {
            if (numbers[i] - numbers[i - 1] != diff) {
                isArithmetic = false;
                break;
            }
        }

        if (isArithmetic) {
            return String.valueOf(numbers[numbers.length - 1] + diff);
        } else {
            int ratio = numbers[1] / numbers[0];
            boolean isGeometric = true;
            for (int i = 2; i < numbers.length; i++) {
                if (numbers[i] / numbers[i - 1] != ratio) {
                    isGeometric = false;
                    break;
                }
            }
            if (isGeometric) {
                return String.valueOf(numbers[numbers.length - 1] * ratio);
            }
        }

        return "?";
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
        return "SequenceGuess";
    }

    @Override
    public int getDurationSeconds() {
        return 60;
    }
}
