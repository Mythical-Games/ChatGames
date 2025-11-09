package com.mythicalgames.chatgames;

import lombok.extern.slf4j.Slf4j;
import org.allaymc.api.plugin.Plugin;
import org.allaymc.api.server.Server;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;

import com.mythicalgames.chatgames.listeners.PlayerListener;
import com.mythicalgames.chatgames.manager.GameManager;

@Slf4j
public class ChatGames extends Plugin {
    private static ChatGames instance;
    public Config config;
    private GameManager gameManager;

    @Override
    public void onLoad() {
        instance = this;
        log.info("Loading ChatGames configuration...");
        config = ConfigManager.create(Config.class, config -> {
            config.withConfigurer(new YamlSnakeYamlConfigurer());
            config.withBindFile(pluginContainer.dataFolder().resolve("config.yml"));
            config.withRemoveOrphans(true);
            config.saveDefaults();
            config.load(true);
        });
    }

    @Override
    public void onEnable() {
        this.gameManager = new GameManager(this);
        this.gameManager.startScheduler();
        Server.getInstance().getEventBus().registerListener(new PlayerListener());
        log.info("ChatGames enabled!");
    }

    public static ChatGames getInstance() {
        return instance;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
