package xyz.damt.util;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.Collection;
import java.util.List;

public class ConfigFile {

    protected File file;
    protected YamlConfiguration config;
    private final String configName;

    @SneakyThrows
    public ConfigFile(File folder, String configName) {
        this.configName = configName;
        file = new File(folder, configName);

        init();
    }


    public void init() throws IOException {
        if (!file.exists()) {
            file.createNewFile();
            loadDefaults();
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void loadDefaults() throws IOException {
        InputStream is = getClass().getResourceAsStream("/" + configName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(readFile(is));
        writer.close();
    }


    public String readFile(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String content = "";
        String line;
        while ((line = reader.readLine()) != null) {
            content += line + "\n";
        }

        reader.close();
        return content.trim();
    }


    public void save() throws IOException {
        if (!file.exists())
            file.createNewFile();

        config.save(file);
    }

    public String getString(String path) {
        return this.config.getString(path);
    }

    public int getInt(String path) {
        return this.config.getInt(path);
    }

    public double getDouble(String path) {
        return this.config.getDouble(path);
    }

    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    public Collection<String> getSection(String section) {
        return this.config.getConfigurationSection(section).getKeys(false);
    }

    public List<String> getStringList(String path) {
        return this.config.getStringList(path);
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void reloadConfig() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "There was an error reloading the file " + configName);
        }
    }


}
