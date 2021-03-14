package me.drex.votifier.config;

import me.drex.votifier.Votifier;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YAMLConfig {

    public static List<String> commands = new ArrayList<String>() {{
        this.add("tellraw @a [{\"text\":\"%PLAYER% voted on %SERVICE%\",\"color\":\"green\"}]");
        this.add("scoreboard players add %PLAYER% voted 1");
    }};
    public static boolean enabled = true;
    public static int port = 8192;
    private static Yaml yaml = new Yaml();
    private static HashMap<String, Object> data = new HashMap<>();
    private static File CONFIG_FILE = Votifier.getPath().resolve("config.yaml").toFile();

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try {
                HashMap<String, Object> config = (HashMap<String, Object>) yaml.load(new FileInputStream(CONFIG_FILE));
                enabled = (boolean) config.getOrDefault("enabled", enabled);
                port = (int) config.getOrDefault("port", port);
                commands = (List<String>) config.getOrDefault("commands", commands);
            } catch (Exception ex) {
                Votifier.getLogger().error("Couldn't load config", ex);
            }
        } else {
            createFile();
        }

    }

    public static void createFile() {
        try {
            CONFIG_FILE.createNewFile();
            StringWriter writer = new StringWriter();
            data.put("enabled", enabled);
            data.put("port", 8192);
            data.put("commands", commands);
            yaml.dump(data, writer);
            FileOutputStream fileOutputStream = new FileOutputStream(CONFIG_FILE);
            fileOutputStream.write(writer.toString().getBytes());
        } catch (IOException ex) {
            Votifier.getLogger().error("Couldn't create config file", ex);
        }
    }

}