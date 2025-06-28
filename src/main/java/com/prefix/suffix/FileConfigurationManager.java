package com.prefix.suffix;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class FileConfigurationManager {

    /**
     * players.yml 파일과 config.yml을 다시 로드합니다.
     */
    public void reload() {
        plugin.reloadConfig(); // config.yml 다시 로드
        load(); // players.yml 다시 로드
    }
    private final JavaPlugin plugin;
    private final File configFile;
    private FileConfiguration config;

    public FileConfigurationManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "players.yml");
    }

    /**
     * players.yml 파일이 없으면 생성하고, 설정을 로드합니다.
     */
    public void load() {
        if (!configFile.exists()) {
            try {
                configFile.getParentFile().mkdirs(); // 플러그인 폴더 생성
                configFile.createNewFile(); // players.yml 파일 생성
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * 설정을 players.yml 파일에 저장합니다.
     */
    public void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 지정된 경로에 값을 설정합니다.
     */
    public void set(String path, Object value) {
        config.set(path, value);
    }

    /**
     * 경로에 있는 값을 문자열로 가져옵니다. 없으면 기본값 반환.
     */
    public String get(String path, String def) {
        return config.getString(path, def);
    }

    /**
     * 경로가 설정 파일에 존재하는지 확인합니다.
     */
    public boolean contains(String path) {
        return config.contains(path);
    }

    /**
     * 설정 파일 객체를 그대로 반환합니다.
     */
    public FileConfiguration getRawConfig() {
        return config;
    }
}
