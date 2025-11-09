package com.mythicalgames.chatgames.games;

import com.mythicalgames.chatgames.ChatGames;
import com.mythicalgames.chatgames.manager.GameManager;
import com.mythicalgames.economy.MythicalEconomy;

import org.allaymc.api.entity.interfaces.EntityPlayer;  

import java.util.Random;

public class QuickMathGame implements BaseGame {

    private final ChatGames plugin = ChatGames.getInstance();
    private String question;
    private int answer;

    @Override
    public void start() {
        Random r = new Random();
        int a = r.nextInt(plugin.config.quickMath.maxNumber);
        int b = r.nextInt(plugin.config.quickMath.maxNumber);
        String op = plugin.config.quickMath.operators.get(r.nextInt(plugin.config.quickMath.operators.size()));

        switch (op) {
            case "+" -> answer = a + b;
            case "-" -> answer = a - b;
            case "*" -> answer = a * b;
            case "/" -> answer = (b != 0) ? a / b : 0;
        }

        question = a + " " + op + " " + b + " = ?";
        GameManager.broadcastMessage("§e[ChatGame] §7Solve this: §b" + question);
    }

    @Override
    public boolean checkAnswer(EntityPlayer player, String message) {
        try {
            int guess = Integer.parseInt(message);
            if (guess == answer) {
                giveReward(player);
                GameManager.broadcastMessage("§a" + player.getOriginName() + " §7answered correctly! §e(" + answer + ")");
                return true;
            }
        } catch (NumberFormatException ignored) {}
        return false;
    }

    private void giveReward(EntityPlayer player) {
        double min = plugin.config.reward.min;
        double max = plugin.config.reward.max;
        double reward = min + (Math.random() * (max - min));

        MythicalEconomy.getAPI().addBalance(player.getLoginData().getUuid(), reward);

        player.sendMessage("§aYou won §6$" + String.format("%.2f", reward) + "§a!");
    }

    @Override
    public String getName() {
        return "QuickMath";
    }

    @Override
    public int getDurationSeconds() {
        return 60;
    }
}
