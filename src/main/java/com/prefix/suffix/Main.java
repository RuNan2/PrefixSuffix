package com.prefix.suffix;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * 플러그인의 메인 클래스입니다.
 * config.yml 및 players.yml을 초기화하고, 명령어 및 이벤트를 등록합니다.
 */
public class Main extends JavaPlugin {
    public FileConfigurationManager configManager;

    @Override
    public void onEnable() {
        // config.yml 생성 및 불러오기
        saveDefaultConfig();

        // players.yml 로딩
        this.configManager = new FileConfigurationManager(this);
        this.configManager.load();

        // 명령어 등록
        getCommand("setprefix").setExecutor(new PrefixCommand(this));
        getCommand("setsuffix").setExecutor(new SuffixCommand(this));
        getCommand("setnickname").setExecutor(new NicknameCommand(this));
        getCommand("prefixsuffix").setExecutor(new ReloadCommand(this));

        // 이벤트 리스너 등록
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
    }

    @Override
    public void onDisable() {
        // 종료 시 players.yml 저장
        configManager.save();
    }
}
