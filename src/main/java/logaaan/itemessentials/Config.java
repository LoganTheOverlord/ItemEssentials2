package logaaan.itemessentials;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    /**
     * Config file for plugin settings
     */
    public static Config plugin = new Config("config.yml");
    public static Config invs = new Config("inventories.yml");

    /**
     * Config file containing plugin messages
     */

    /**
     * File used for config
     */
    public File configFile;

    /**
     * File used as YAML
     */
    public YamlConfiguration YMLConfig;

    /**
     * Filename
     */
    private final String filename;

    /**
     * Create new config
     *
     * @param filename Filename to use
     */
    public Config(String filename) {
        this.filename = filename;
        configFile = new File(CoreClass.MAINCLASS.getDataFolder(), filename);
        this.YMLConfig = YamlConfiguration.loadConfiguration(configFile);
        if (configFile.length() < 5){
            YMLConfig = new YamlConfiguration();
        }
    }

    public void setString(String node, String value) throws IOException {
        YMLConfig.set(node, value);
        YMLConfig.save(configFile);
    }

    public String getString(String node){
        return YMLConfig.getString(node);
    }

    public boolean has(String node){
        return YMLConfig.get(node) != null;
    }

    public void save() throws IOException {
        YMLConfig.save(new File(CoreClass.MAINCLASS.getDataFolder() + "/" + this.filename));
    }

}

