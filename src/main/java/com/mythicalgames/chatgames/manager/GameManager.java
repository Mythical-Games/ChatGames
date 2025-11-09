package com.mythicalgames.chatgames.manager;

import com.mythicalgames.chatgames.ChatGames;
import com.mythicalgames.chatgames.games.*;
import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.scheduler.Task;
import org.allaymc.api.server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {
    private final List<BaseGame> games = new ArrayList<>();
    private final ChatGames plugin;
    private final Random random = new Random();
    private BaseGame activeGame;

    public GameManager(ChatGames plugin) {
        this.plugin = plugin;

        games.add(new QuickMathGame());
        games.add(new GuessNumberGame());
        games.add(new MinecraftTriviaGame());
        games.add(new SequenceGuessGame());
        games.add(new GuessWordGame());
        games.add(new WordUnscrambleGame());
        games.add(new FastTypeGame());
        games.add(new MissingLetterGame());
    }

    public void startScheduler() {
        int intervalTicks = plugin.config.intervalSeconds * 20;

        Server.getInstance().getScheduler().scheduleRepeating(ChatGames.getInstance(), new Task() {
            @Override
            public boolean onRun() {
                if (activeGame == null && !Server.getInstance().getPlayerManager().getPlayers().isEmpty()) {
                    startRandomGame();
                }
                return true;
            }
        }, intervalTicks, true);
    }

    private void startRandomGame() {
        if (games.isEmpty()) return;

        activeGame = games.get(random.nextInt(games.size()));
        activeGame.start();

        int durationTicks = activeGame.getDurationSeconds() * 20;

        Server.getInstance().getScheduler().scheduleDelayed(ChatGames.getInstance(), new Task() {
            @Override
            public boolean onRun() {
                if (activeGame != null) {
                    broadcastMessage("§cThe game §e" + activeGame.getName() + " §chas ended! No one answered in time.");
                    activeGame = null;
                }
                return true;
            }
        }, durationTicks, true);
    }

    public BaseGame getActiveGame() {
        return activeGame;
    }

    public void clearActiveGame() {
        activeGame = null;
    }

    public static void broadcastMessage(String message) {
        for (EntityPlayer onlinePlayer : Server.getInstance().getPlayerManager().getPlayers().values()) {
            onlinePlayer.sendMessage(message);
        }
    }
}
