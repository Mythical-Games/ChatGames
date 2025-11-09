package com.mythicalgames.chatgames.games;

import com.mythicalgames.chatgames.ChatGames;
import com.mythicalgames.chatgames.Config.TriviaQuestion;
import com.mythicalgames.chatgames.manager.GameManager;
import com.mythicalgames.economy.MythicalEconomy;
import org.allaymc.api.entity.interfaces.EntityPlayer;

import java.util.List;
import java.util.Random;

public class MinecraftTriviaGame implements BaseGame {

    private final ChatGames plugin = ChatGames.getInstance();
    private String question;
    private String correctAnswer;

    @Override
    public void start() {
        List<TriviaQuestion> questions = plugin.config.trivia.questions;

        if (questions.isEmpty()) return;

        Random r = new Random();
        TriviaQuestion q = questions.get(r.nextInt(questions.size()));

        question = q.question;
        correctAnswer = q.answer.toLowerCase();

        GameManager.broadcastMessage("§e[ChatGame] §7Trivia: §b" + question);
    }

    @Override
    public boolean checkAnswer(EntityPlayer player, String message) {
        if (message.trim().equalsIgnoreCase(correctAnswer)) {
            giveReward(player);
            GameManager.broadcastMessage("§a" + player.getOriginName() + " §7answered the trivia correctly! §e(" + correctAnswer + ")");
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
        return "MinecraftTrivia";
    }

    @Override
    public int getDurationSeconds() {
        return 60;
    }
}
