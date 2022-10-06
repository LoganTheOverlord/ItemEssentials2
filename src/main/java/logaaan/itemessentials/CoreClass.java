package logaaan.itemessentials;

import logaaan.itemessentials.listeners.InventoryListeners;
import logaaan.itemessentials.listeners.ItemEvents;
import me.gleeming.command.CommandHandler;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class CoreClass extends JavaPlugin implements Listener {

    Logger logger = Logger.getLogger("Warnings");
    FileHandler logHandler;

    int warnings = 0;
    public static CoreClass MAINCLASS;
    public static Config oldies;

    public static Map<Material, String> clickSounds = new HashMap<>();
    public static Map<Material, String> pickupSounds = new HashMap<>();

    public static Map<String, String> oldCache = new HashMap<>();
    public static boolean enableSwitching = true;
    public static boolean useLoreFormatting = true;

    public static boolean useAttributes = true;
    public static boolean useEnchantments = true;
    public static boolean usePickupNotifications = false;
    public static boolean usePickupSounds = true;
    public static boolean useHotbarSounds = true;
    public static String enchantmentColor = "gl:green,yellow;";
    public static String loreEnchantment = "&e&l| &f&lEnchantments";
    public static String loreAttributes = "&e&l| &f&lAttributes";
    public static String loreAttackDamage = "&e&l| &f&lAttack Damage: &e&l";
    public static String loreArmor = "&e&l| &f&lArmor: &e&l";
    public static String loreNutritionValue = "&e&l| &f&lNutrition: &e&l";
    public static String loreCategory = "&e&l| &9";


    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new InventoryListeners(), this);
        getServer().getPluginManager().registerEvents(new ItemEvents(), this);
        CommandHandler.registerCommands(Commands.class, this);
        InventoryListeners.main = this;
        ItemEvents.main = this;

        MAINCLASS = this;
        enableSwitching = true;

        oldies  = new Config("translations_old.yml");

        Utils.initColors();
        Utils.initEnchants();

        try {
            EnsureDefaultConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            logHandler = new FileHandler(getDataFolder() + "/warning.log");
            logger.addHandler(logHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            logHandler.setFormatter(formatter);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            LoadConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        loadAllOldKeysIntoCache();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getLogger().info("Disabling plugin as per request.");
    }


    ///                 CONFIGURATION SECTION                       ///


    /// Checking default config values for the application. Adds necessary ones if some are missing.
    public void EnsureDefaultConfig() throws IOException {
        saveResource("config.yml", false);
        saveResource("translations_old.yml", false);
        saveResource("warning.log", false);
        getConfig().options().copyDefaults(true);
        saveConfig();
    }


    /// Loads all config values into appropriate fields, arrays and maps.
    public void LoadConfig() throws IOException {
        warnings = 0;
        reloadConfig();
        CoreClass.useLoreFormatting = Boolean.parseBoolean(loadConfigNode("use-lore-formatting", false));
        CoreClass.enableSwitching = Boolean.parseBoolean(loadConfigNode("enable-gamemode-switching", false));
        CoreClass.enchantmentColor = loadConfigNode("lore-enchantment-format", false);
        CoreClass.useAttributes = Boolean.parseBoolean(loadConfigNode("use-attributes", false));
        CoreClass.useEnchantments = Boolean.parseBoolean(loadConfigNode("use-enchantments", false));
        CoreClass.usePickupNotifications = Boolean.parseBoolean(loadConfigNode("use-pickup-notifications", false));
        CoreClass.usePickupSounds = Boolean.parseBoolean(loadConfigNode("use-pickup-sounds", false));
        CoreClass.useHotbarSounds = Boolean.parseBoolean(loadConfigNode("use-hotbar-sounds", false));

        //Translations
        CoreClass.loreAttackDamage = loadConfigNode("lore-lines.attack-damage", true);
        CoreClass.loreArmor = loadConfigNode("lore-lines.armor-value", true);
        CoreClass.loreNutritionValue = loadConfigNode("lore-lines.nutrition-value", true);
        CoreClass.loreAttributes = loadConfigNode("lore-lines.attributes", true);
        CoreClass.loreEnchantment = loadConfigNode("lore-lines.enchantments", true);
        CoreClass.loreCategory = loadConfigNode("lore-lines.category", true);

        //Load translations of enchantments!
        Utils.initEnchants();
        for (Enchantment e : Utils.getEnchantments()){
            if (loadConfigNode("enchantment-names."+e.getName(), true) != null){
                Utils.setEnchantmentNames(e, loadConfigNode("enchantment-names."+e.getName(), true));
            }
        }

        //Load custom click sounds.
        for (String s : Objects.requireNonNull(getConfig().getConfigurationSection("custom-click-sounds")).getKeys(false)){
            try {
                String val = getConfig().getString("custom-click-sounds." + s);
                clickSounds.put(Material.valueOf(s), val);
            } catch (Exception e){
                logWarning("Material (Item) '"+s+"' was not found to define custom click sound!");
            }
        }

        //Load custom pickup sounds.
        for (String s : Objects.requireNonNull(getConfig().getConfigurationSection("pickup-sounds")).getKeys(false)){
            try {
                String val = getConfig().getString("pickup-sounds." + s);
                pickupSounds.put(Material.valueOf(s), val);
            } catch (Exception e){
                logWarning("Material (Item) '"+s+"' was not found to define custom pickup sound!");
            }
        }

        //Initialize enchantment descriptions.
        Utils.initDesctiptions();

        getLogger().info("Loaded the configuration with "+warnings+" warnings. Check plugins/ItemEssentials/warning.log to see the warnings!");
    }



    /// Used to get configuration sections while also checking for changes in them.
    public String loadConfigNode(String node, boolean addToOld) throws IOException {
        //let's get both old and new values
        String _old = oldies.getString("old-keys."+node);
        String _curr = getConfig().getString(node);
        boolean hasKey = Helpers.arrayContains(getOldValuesForKey("old-keys." + node), _curr);
        if (_old != null && _curr != null && !_curr.equals(_old) && !hasKey){
            if (addToOld) {
                oldies.setString("old-keys." + node, _old + "<;|//>" + _curr);
                saveConfig();
            }
        }

        if (_curr != null) return _curr;
        else {
            logWarning(Utils.color("Config value for YML node '" + node + "' is missing! Please, add it, or regenerate your config!"));
            return null;
        }
    }

    //Checks whether an enchantment is considered old or not. Not used
    public static boolean isConsideredOldEnchantment(Enchantment e, String line){
        String[] keys = MAINCLASS.getOldValuesForKey("enchantment-names."+e.getName());
        if (keys.length > 0){
            for (String key : keys){
                if (line.contains(key)) return true;
            }
        }
        return false;
    }

    //Checks whether key is old or not, searching for key history.
    public static boolean isConsideredOldKey(String key, String line){
        String[] keys = MAINCLASS.getOldValuesForKey(key);
        if (keys.length > 0){
            for (String ki : keys){
                if (line.contains(ki)) return true;
            }
        }
        return false;
    }

    //Gets old values for keys.
    public String[] getOldValuesForKey(String node){
        String val = oldCache.get("old-keys."+node);
        if (val == null) return new String[] { "" };
        if (val.contains("<;|//>")) {
            return val.split("<;|//>", 1024);
        } else {
            return new String[] { val };
        }
    }

    //Loads all old keys history into reusable cache. Diminishes previous overheads.
    public void loadAllOldKeysIntoCache(){
        for (String k : Objects.requireNonNull(oldies.YMLConfig.getConfigurationSection("old-keys")).getKeys(false)){
            boolean hasChildren = oldies.YMLConfig.getConfigurationSection("old-keys." + k) != null;

            if (hasChildren){
                MapIntoOldCache("old-keys."+k);
            } else {
                oldCache.put("old-keys."+k, oldies.getString("old-keys."+k));
            }
        }
    }

    //Used to map keys into cache per-tree
    public void MapIntoOldCache(String node){
        for (String k : Objects.requireNonNull(oldies.YMLConfig.getConfigurationSection(node)).getKeys(false)){
            boolean hasChildren = oldies.YMLConfig.getConfigurationSection(node + k) != null;

            if (hasChildren){
                MapIntoOldCache(node+"."+k);
            } else {
                oldCache.put(node+"."+k, oldies.getString(node+"."+k));
            }
        }
    }

    public void logWarning(String warning){
        logger.info(warning);
        warnings++;
    }
}
