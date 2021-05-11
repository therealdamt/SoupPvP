package xyz.damt.config.other;

import org.bukkit.configuration.file.FileConfiguration;

public class DatabaseHandler {

    public final boolean MONGO_HAS_AUTH;
    public final String MONGO_HOST;
    public final int MONGO_PORT;

    public final String MONGO_AUTH_DATABASE;
    public final String MONGO_USERNAME;
    public final String MONGO_PASSWORD;

    public DatabaseHandler(FileConfiguration soup) {
        this.MONGO_HAS_AUTH = soup.getBoolean("mongo.auth.enabled");
        this.MONGO_HOST = soup.getString("mongo.host");
        this.MONGO_PORT = soup.getInt("mongo.port");
        this.MONGO_AUTH_DATABASE = soup.getString("mongo.auth.database");

        this.MONGO_USERNAME = soup.getString("mongo.auth.username");
        this.MONGO_PASSWORD = soup.getString("mongo.auth.password");
    }


}
