package com.mythicalgames.chatgames.games;

import com.mythicalgames.chatgames.ChatGames;
import com.mythicalgames.chatgames.manager.GameManager;
import com.mythicalgames.economy.MythicalEconomy;
import org.allaymc.api.entity.interfaces.EntityPlayer;

import java.util.Random;

public class GuessNumberGame implements BaseGame {

    private final ChatGames plugin = ChatGames.getInstance();
    private int numberToGuess;
    private int minNumber;
    private int maxNumber;

    @Override
    public void start() {
        Random r = new Random();
        minNumber = plugin.config.guessNumber.min;
        maxNumber = plugin.config.guessNumber.max;

        numberToGuess = r.nextInt(maxNumber - minNumber + 1) + minNumber;

        GameManager.broadcastMessage("§e[ChatGame] §7Guess a number between §b" + minNumber + " §7and §b" + maxNumber + "§7!");
    }

    @Override
    public boolean checkAnswer(EntityPlayer player, String message) {
        try {
            int guess = Integer.parseInt(message);
            if (guess == numberToGuess) {
                giveReward(player);
                GameManager.broadcastMessage("§a" + player.getOriginName() + " §7guessed the correct number! §e(" + numberToGuess + ")");
                return true;
            } else if (guess < numberToGuess) {
                player.sendMessage("§7Too low! Try a higher number.");
            } else {
                player.sendMessage("§7Too high! Try a lower number.");
            }
        } catch (NumberFormatException ignored) {
            // Not a number, ignore
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
        return "GuessNumber";
    }

    @Override
    public int getDurationSeconds() {
        return 60;
    }
}

