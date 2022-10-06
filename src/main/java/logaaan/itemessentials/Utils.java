package logaaan.itemessentials;

import logaaan.itemessentials.config.BukkitSerialization;
import logaaan.itemessentials.tool.Colour;
import logaaan.itemessentials.tool.Formatter;
import logaaan.itemessentials.tool.Tool;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

public class Utils {

    public static ItemStack[] getInventoryFromNode(String node) throws IOException {
        String inv = Config.invs.getString(node);
        return BukkitSerialization.itemStackArrayFromBase64(inv);
    }

    public static String getInventoryString(Player p){
       return BukkitSerialization.itemStackArrayToBase64(p.getInventory().getContents());
    }

    public static String getGradientFromString(String grad) {

        StringBuilder returned = new StringBuilder();

        if (grad.contains("gl:")) {
            String[] split = grad.split("gl:", grad.length());
            if (split.length > 0) {
                for (String s : split) {
                    if (s.contains(";")) {
                        String[] tww_s = s.split(";", 99);
                        if (tww_s.length > 0) {
                            String[] ttw2 = tww_s[0].split(",", 99);
                            String c1 = ttw2[0];
                            String c2 = ttw2[1];

                            Colour.populateColours();
                            String r = Formatter.format(Tool.setGradientColour(c1, c2, Formatter.format(tww_s[1])));
                            r = "&l"+r;
                            r = ChatColor.translateAlternateColorCodes('&',r);
                            returned.append(r);
                        }
                    }
                }
            }
        }


        if (returned.length() < 1) {
            returned = new StringBuilder(grad);
        }

        return returned.toString();
    }

    public static String getEnchant(Enchantment name){
        return EnchantmentNames.containsKey(name) ? EnchantmentNames.get(name) : name.getName();
    }

    public static HashMap<String, String> Colours = new HashMap<>();
    public static HashMap<Enchantment, String> EnchantmentNames = new HashMap<>();
    public static HashMap<Enchantment, String> EnchantmentDescriptions = new HashMap<>();

    public static void initColors(){
        Colours.put("0", "black");
        Colours.put("1", "darkblue");
        Colours.put("2", "darkgreen");
        Colours.put("3", "cyan");
        Colours.put("4", "darkred");
        Colours.put("5", "purple");
        Colours.put("6", "gold");
        Colours.put("7", "lightgrey");
        Colours.put("8", "darkgrey");
        Colours.put("9", "blue");
        Colours.put("a", "lightgreen");
        Colours.put("c", "lightred");
        Colours.put("d", "magenta");
        Colours.put("e", "yellow");
    }

    public static void initEnchants(){
        EnchantmentNames.put(Enchantment.DAMAGE_ALL, "Sharpness");
        EnchantmentNames.put(Enchantment.PROTECTION_ENVIRONMENTAL, "Proection");
        EnchantmentNames.put(Enchantment.DURABILITY, "Unbreaking");
        EnchantmentNames.put(Enchantment.DAMAGE_UNDEAD, "Smite");
        EnchantmentNames.put(Enchantment.DAMAGE_ARTHROPODS, "Bane of Arthropods");
        EnchantmentNames.put(Enchantment.ARROW_FIRE, "Flame");
        EnchantmentNames.put(Enchantment.ARROW_DAMAGE, "Power");
        EnchantmentNames.put(Enchantment.ARROW_KNOCKBACK, "Punch");
        EnchantmentNames.put(Enchantment.LOOT_BONUS_BLOCKS, "Fortune");
        EnchantmentNames.put(Enchantment.LOOT_BONUS_MOBS, "Looting");
        EnchantmentNames.put(Enchantment.VANISHING_CURSE, "Curse of Vanishing");
        EnchantmentNames.put(Enchantment.ARROW_INFINITE, "Infinity");
        EnchantmentNames.put(Enchantment.PROTECTION_EXPLOSIONS, "Blast Protection");
        EnchantmentNames.put(Enchantment.PROTECTION_FALL, "Feather Falling");
        EnchantmentNames.put(Enchantment.PROTECTION_FIRE, "Fire Proection");
        EnchantmentNames.put(Enchantment.PROTECTION_PROJECTILE, "Projectile Protection");
        EnchantmentNames.put(Enchantment.BINDING_CURSE, "Curse of Binding");
        EnchantmentNames.put(Enchantment.THORNS, "Thorns");
        EnchantmentNames.put(Enchantment.SOUL_SPEED, "Soul Walker");
        EnchantmentNames.put(Enchantment.SWEEPING_EDGE, "Sweeping Edge");
        EnchantmentNames.put(Enchantment.RIPTIDE, "Riptide");
        EnchantmentNames.put(Enchantment.LOYALTY, "Loyalty");
        EnchantmentNames.put(Enchantment.CHANNELING, "Channeling");
        EnchantmentNames.put(Enchantment.SILK_TOUCH, "Silk Touch");
        EnchantmentNames.put(Enchantment.DEPTH_STRIDER, "Depth Strider");
        EnchantmentNames.put(Enchantment.MULTISHOT, "Multishot");
        EnchantmentNames.put(Enchantment.QUICK_CHARGE, "Quick Charge");
        EnchantmentNames.put(Enchantment.KNOCKBACK, "Knockback");
        EnchantmentNames.put(Enchantment.DIG_SPEED, "Efficiency");
        EnchantmentNames.put(Enchantment.FIRE_ASPECT, "Fire Aspect");
        EnchantmentNames.put(Enchantment.FROST_WALKER, "Frost Walker");
        EnchantmentNames.put(Enchantment.IMPALING, "Impaling");
        EnchantmentNames.put(Enchantment.LUCK, "Luck of the Sea");
        EnchantmentNames.put(Enchantment.LURE, "Lure");
        EnchantmentNames.put(Enchantment.MENDING, "Mending");
        EnchantmentNames.put(Enchantment.OXYGEN, "Aqua Affinity");
        EnchantmentNames.put(Enchantment.PIERCING, "Piercing");
        EnchantmentNames.put(Enchantment.SWIFT_SNEAK, "Swift Sneak");
        EnchantmentNames.put(Enchantment.WATER_WORKER, "Water Worker");
    }

    public static String getRoman(int num) {

        String toSp = String.valueOf(num);
        StringBuilder val = new StringBuilder();
        for (int i = 0; i < toSp.length(); i++) {

            String x = toSp.substring(i <= toSp.length() ? i : i - 1);
            String roman = toRoman(Integer.parseInt(x));
            val.append(roman);
        }

        return val.toString();
    }

    public static String toRoman(int num){
        String n = "I";
        n = num == 1 ? "I" : n;
        n = num == 2 ? "II" : n;
        n = num == 3 ? "III" : n;
        n = num == 4 ? "IV" : n;
        n = num == 5 ? "V" : n;
        n = num == 6 ? "VI" : n;
        n = num == 7 ? "VII" : n;
        n = num == 8 ? "VIII" : n;
        n = num == 9 ? "IX" : n;
        n = num == 10 ? "X" : n;

        if (num > 10 && num <= 100) {
            int d = num / 10;
            n = d == 1 ? "X" : n;
            n = d == 2 ? "XX" : n;
            n = d == 3 ? "XXX" : n;
            n = d == 4 ? "XL" : n;
            n = d == 5 ? "L" : n;
            n = d == 6 ? "LX" : n;
            n = d == 7 ? "LXX" : n;
            n = d == 8 ? "LXXX" : n;
            n = d == 9 ? "XC" : n;
            n = d == 10 ? "C" : n;
        }
        if (num > 100 && num <= 1000) {
            int d = num / 100;
            n = d == 1 ? "C" : n;
            n = d == 2 ? "CC" : n;
            n = d == 3 ? "CCC" : n;
            n = d == 4 ? "CD" : n;
            n = d == 5 ? "D" : n;
            n = d == 6 ? "DC" : n;
            n = d == 7 ? "DCC" : n;
            n = d == 8 ? "DCCC" : n;
            n = d == 9 ? "CM" : n;
            n = d == 10 ? "M" : n;
        }
        return n;
    }

    public static double getDefaultDamage(Material m){
        double i = 1.0d;

        i = m.equals(Material.DIAMOND_SWORD) ? 7 : i;
        i = m.equals(Material.NETHERITE_SWORD) ? 8 : i;
        i = m.equals(Material.WOODEN_SWORD) ? 4 : i;
        i = m.equals(Material.GOLDEN_SWORD) ? 4 : i;
        i = m.equals(Material.STONE_SWORD) ? 5 : i;
        i = m.equals(Material.IRON_SWORD) ? 6 : i;

        i = m.equals(Material.DIAMOND_AXE) ? 9 : i;
        i = m.equals(Material.NETHERITE_AXE) ? 10 : i;
        i = m.equals(Material.WOODEN_AXE) ? 7 : i;
        i = m.equals(Material.GOLDEN_AXE) ? 7 : i;
        i = m.equals(Material.STONE_AXE) ? 9 : i;
        i = m.equals(Material.IRON_AXE) ? 9 : i;

        i = m.equals(Material.DIAMOND_PICKAXE) ? 3 : i;
        i = m.equals(Material.IRON_PICKAXE) ? 2.5d : i;
        i = m.equals(Material.STONE_PICKAXE) ? 2d : i;
        i = m.equals(Material.WOODEN_PICKAXE) ? 1.5d : i;
        i = m.equals(Material.GOLDEN_PICKAXE) ? 2.0d : i;
        i = m.equals(Material.NETHERITE_PICKAXE) ? 3.5d : i;

        i = m.equals(Material.DIAMOND_SHOVEL) ? 3 : i;
        i = m.equals(Material.IRON_SHOVEL) ? 2.5d : i;
        i = m.equals(Material.STONE_SHOVEL) ? 2d : i;
        i = m.equals(Material.WOODEN_SHOVEL) ? 1.5d : i;
        i = m.equals(Material.GOLDEN_SHOVEL) ? 2.0d : i;
        i = m.equals(Material.NETHERITE_SHOVEL) ? 3.5d : i;

        return i;
    }

    public static double getDefaultArmor(Material m){
        double i = 1.0d;

        i = m.equals(Material.DIAMOND_HELMET) ? 3 : i;
        i = m.equals(Material.IRON_HELMET) ? 2 : i;
        i = m.equals(Material.CHAINMAIL_HELMET) ? 2 : i;
        i = m.equals(Material.GOLDEN_HELMET) ? 2 : i;
        i = m.equals(Material.LEATHER_HELMET) ? 1 : i;
        i = m.equals(Material.NETHERITE_HELMET) ? 3 : i;

        i = m.equals(Material.DIAMOND_CHESTPLATE) ? 8 : i;
        i = m.equals(Material.NETHERITE_CHESTPLATE) ? 8 : i;
        i = m.equals(Material.CHAINMAIL_CHESTPLATE) ? 5 : i;
        i = m.equals(Material.GOLDEN_CHESTPLATE) ? 5 : i;
        i = m.equals(Material.LEATHER_CHESTPLATE) ? 3 : i;
        i = m.equals(Material.IRON_CHESTPLATE) ? 6 : i;

        i = m.equals(Material.DIAMOND_LEGGINGS) ? 6 : i;
        i = m.equals(Material.NETHERITE_LEGGINGS) ? 6 : i;
        i = m.equals(Material.CHAINMAIL_LEGGINGS) ? 4 : i;
        i = m.equals(Material.GOLDEN_LEGGINGS) ? 3 : i;
        i = m.equals(Material.LEATHER_LEGGINGS) ? 1 : i;
        i = m.equals(Material.IRON_LEGGINGS) ? 4 : i;

        i = m.equals(Material.DIAMOND_BOOTS) ? 3 : i;
        i = m.equals(Material.NETHERITE_BOOTS) ? 3 : i;
        i = m.equals(Material.CHAINMAIL_BOOTS) ? 1 : i;
        i = m.equals(Material.GOLDEN_BOOTS) ? 1 : i;
        i = m.equals(Material.LEATHER_BOOTS) ? 1 : i;
        i = m.equals(Material.IRON_BOOTS) ? 2 : i;

        return i;
    }

    public static String getAdditionalDamage(int level){
        if (level <= 0) return "";
        return " &7(&f&l+"+(0.5d * Math.max(0, level - 1) + 1.0)+"&7)";
    }

    public static void addDefaultsToItem(@NotNull ItemStack i, String title){
        if (!CoreClass.useLoreFormatting) return;

        ItemMeta m = i.getItemMeta();
        if (m == null) return;

        List<String> lor = m != null && m.getLore() != null ? m.getLore() : new ArrayList<>();
        Map<Enchantment, Integer> list = m.getEnchants();

        int sharpnessLevel = 0;
        if (list.containsKey(Enchantment.DAMAGE_ALL)){
            sharpnessLevel = list.get(Enchantment.DAMAGE_ALL);
        }

        if (!Objects.equals(ChatColor.stripColor(title), title) && !title.equalsIgnoreCase("")){
            int firstLine = 0;
            if (m.getLore() != null)
                firstLine = lor.indexOf(color(CoreClass.loreAttributes));
            if (firstLine <= 0){
                int c = 0;
                for (String l : lor){
                    if (CoreClass.isConsideredOldKey("lore-lines.attributes", l)){
                        firstLine = c;
                        break;
                    }
                    c++;
                }
            }
            while (lor.size() > firstLine && firstLine > 0) {
                lor.remove(firstLine);
            }
            if (firstLine < 0) firstLine = 0;
            if (firstLine > 0 && Objects.equals(lor.get(firstLine - 1), "")){
                lor.remove(firstLine - 1);
            }
            m.setLore(lor);
            i.setItemMeta(m);
            return;
        }

        String ownerOfItem = null;
        for (String v : lor){
            if (v.contains("Belongs to:")){
                if (v.contains("Belongs to:")) {
                    ownerOfItem = v.split(": ", 64)[1];
                    break;
                }
            }
        }

        int firstLine = 0;

        if (CoreClass.useAttributes) {
            if (m.getLore() != null)
                firstLine = lor.indexOf(color(CoreClass.loreAttributes));
            if (firstLine <= 0){
                int c = 0;
                for (String l : lor){
                    if (CoreClass.isConsideredOldKey("lore-lines.attributes", l)){
                        firstLine = c;
                        break;
                    }
                    c++;
                }
            }
            if (firstLine > 0) {
                while (lor.size() > firstLine) {
                    lor.remove(firstLine);
                }
                if (lor.size() > firstLine - 1 && !Objects.equals(lor.get(firstLine - 1), ""))
                    lor.add("");
                lor.add(color(CoreClass.loreAttributes));

                //ADD ATTRIBS & UTIL STRINGS (category, etc)
                double attack = Utils.getDefaultDamage(i.getType());
                if (attack > 1)
                    lor.add(color(CoreClass.loreAttackDamage + attack + getAdditionalDamage(sharpnessLevel)));
                if (getDefaultArmor(i.getType()) > 1)
                    lor.add(color(CoreClass.loreArmor + getDefaultArmor(i.getType())));
                if (foodValue(i) > 0d) {
                    lor.add(color(CoreClass.loreNutritionValue + foodValue(i)));
                }
                lor.add(color(CoreClass.loreCategory + getItemCategory(i)));
            } else {
                if (!lor.contains(color(CoreClass.loreAttributes))) {
                    lor.add("");
                    lor.add(color(CoreClass.loreAttributes));
                    //ADD ATTRIBS & UTIL STRINGS (category, etc)
                    double attack = Utils.getDefaultDamage(i.getType());
                    if (attack > 1)
                        lor.add(color(CoreClass.loreAttackDamage + attack + getAdditionalDamage(sharpnessLevel)));
                    if (getDefaultArmor(i.getType()) > 1)
                        lor.add(color(CoreClass.loreArmor + getDefaultArmor(i.getType())));
                    if (foodValue(i) > 0d) {
                        lor.add(color(CoreClass.loreNutritionValue + foodValue(i)));
                    }
                    lor.add(color(CoreClass.loreCategory + getItemCategory(i)));
                }
            }
            firstLine += 3;

        }


        if (CoreClass.useEnchantments && m!=null) {
            if (list.size() > 0) {
                firstLine = Math.max(lor.indexOf(color(CoreClass.loreEnchantment)), firstLine);
                if (firstLine <= 0){
                    int c = 0;
                    for (String l : lor){
                        if (CoreClass.isConsideredOldKey("lore-lines.enchantments", l)){
                            firstLine = c;
                            break;
                        }
                        c++;
                    }
                }
                // that works... hmmmm.... CoreClass.MAINCLASS.getLogger().info("IT HAS ENCHANTMENTS!");
                if (firstLine > 0) {
                    while (lor.size() > firstLine) {
                        lor.remove(firstLine);
                    }
                    lor.add("");
                    lor.add(color(CoreClass.loreEnchantment));
                    firstLine += 2;
                    //LOOP ENCHANTS, ADD TO LORE
                    for (Enchantment e : list.keySet()) {
                        String _r = color("&a&l☈ " + formatGradient(CoreClass.enchantmentColor + Utils.getEnchant(e) + " " + Utils.getRoman(list.get(e))));
                        lor.add(_r);
                        if (EnchantmentDescriptions.containsKey(e)) {
                            String _d = color(EnchantmentDescriptions.get(e));
                            lor.add(_d);
                            firstLine++;
                        }
                        firstLine++;
                    }

                } else {
                    if (!lor.contains(color(CoreClass.loreEnchantment))) {
                        lor.add("");
                        lor.add(color(CoreClass.loreEnchantment));
                        firstLine += 2;
                        //LOOP ENCHANTS, ADD TO LORE
                        for (Enchantment e : list.keySet()) {
                            String _r = color("&a&l☈ " + formatGradient(CoreClass.enchantmentColor + Utils.getEnchant(e) + " " + Utils.getRoman(list.get(e))));
                            lor.add(_r);
                            if (EnchantmentDescriptions.containsKey(e)) {
                                String _d = color(EnchantmentDescriptions.get(e));
                                lor.add(_d);
                                firstLine++;
                            }
                            firstLine++;
                        }
                    }
                }
            }
        }

        if (ownerOfItem != null){
            lor.add("");
            lor.add(color("&c&l| &fBelongs to: &e&l&n"+ownerOfItem));
        }

        //ADD NEEDED FLAGS
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        //SET LORE & META
        m.setLore(lor);
        i.setItemMeta(m);

        //WE'RE DONE!
    }

    public static double foodValue(ItemStack i){
        double d = 0;
        d = i.getType().equals(Material.CARROT) ? 3d : d;
        d = i.getType().equals(Material.BREAD) ? 5d : d;
        d = i.getType().equals(Material.COOKED_BEEF) ? 8d : d;
        d = i.getType().equals(Material.COOKED_PORKCHOP) ? 8d : d;
        d = i.getType().equals(Material.COOKED_MUTTON) ? 6d : d;
        d = i.getType().equals(Material.COOKED_RABBIT) ? 6d : d;
        d = i.getType().equals(Material.COOKED_CHICKEN) ? 6d : d;
        d = i.getType().equals(Material.COOKED_COD) ? 6d : d;
        d = i.getType().equals(Material.COOKED_SALMON) ? 6d : d;
        d = i.getType().equals(Material.SWEET_BERRIES) ? 2d : d;
        d = i.getType().equals(Material.DRIED_KELP) ? 1d : d;
        d = i.getType().equals(Material.APPLE) ? 3d : d;
        d = i.getType().equals(Material.GOLDEN_APPLE) ? 6d : d;
        d = i.getType().equals(Material.ENCHANTED_GOLDEN_APPLE) ? 6d : d;
        d = i.getType().equals(Material.GOLDEN_CARROT) ? 6d : d;
        d = i.getType().equals(Material.RABBIT_STEW) ? 8d : d;
        d = i.getType().equals(Material.MUSHROOM_STEW) ? 7d : d;
        d = i.getType().equals(Material.ROTTEN_FLESH) ? 3d : d;
        d = i.getType().equals(Material.PUMPKIN_PIE) ? 8d : d;
        d = i.getType().equals(Material.CAKE) ? 8d : d;
        d = i.getType().equals(Material.POTATO) ? 1d : d;
        d = i.getType().equals(Material.BAKED_POTATO) ? 5d : d;
        d = i.getType().equals(Material.POISONOUS_POTATO) ? 3d : d;
        d = i.getType().equals(Material.SALMON) ? 3d : d;
        d = i.getType().equals(Material.COD) ? 3d : d;
        d = i.getType().equals(Material.PUFFERFISH) ? 3d : d;
        d = i.getType().equals(Material.TROPICAL_FISH) ? 2d : d;
        d = i.getType().equals(Material.COOKIE) ? 1d : d;
        d = i.getType().equals(Material.MELON_SLICE) ? 2d : d;
        d = i.getType().equals(Material.GLOW_BERRIES) ? 2d : d;

        return d;
    }

    public static String color(String from){
        return ChatColor.translateAlternateColorCodes('&', from);
    }

    public static String formatGradient(@NotNull String message){
        return Utils.getGradientFromString(message);
    }

    public static String getItemCategory(ItemStack i){
        return Objects.requireNonNull(i.getType().getCreativeCategory()).name();
    }

    public static List<Enchantment> getEnchantments(){
        return new ArrayList<>(EnchantmentNames.keySet());
    }

    public static Enchantment getEnchantment(String name){
        Enchantment e = Enchantment.getByName(name);
        if (e == null){
            for (String v : EnchantmentNames.values()){
                String c = v.replaceAll(" ", "");
                if (c.equalsIgnoreCase(name)){
                    for (Enchantment x : EnchantmentNames.keySet()){
                        if (!EnchantmentNames.get(x).equals(v)) continue; else return x;
                    }
                }
            }
        } else {
            return e;
        }

        return null;
    }

    public static void setEnchantmentNames(Enchantment key, String value){
        EnchantmentNames.replace(key, value);
    }

    public static void setEnchantmentDescriptions(Enchantment key, String value){
        EnchantmentDescriptions.put(key, value);
    }

    public static void initDesctiptions() throws IOException {
        EnchantmentDescriptions.clear();
        for (Enchantment e : Utils.getEnchantments()){
            if (CoreClass.MAINCLASS.loadConfigNode("enchantment-descriptions."+e.getName(), true) != null){
                Utils.setEnchantmentDescriptions(e, CoreClass.MAINCLASS.loadConfigNode("enchantment-descriptions."+e.getName(), true));
            }
        }
    }
}
