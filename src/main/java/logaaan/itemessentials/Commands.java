package logaaan.itemessentials;

import eu.endercentral.crazy_advancements.advancement.AdvancementDisplay;
import eu.endercentral.crazy_advancements.advancement.ToastNotification;
import logaaan.itemessentials.config.BukkitSerialization;
import me.gleeming.command.Command;
import me.gleeming.command.paramter.Param;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Commands {
    // All you have to do now is teleport the player
    // The messages like player not found, usage, player only, etc..
    // are handled automatically without the need to worry
    @Command(names = {"saveItems"}, permission = "ie.saveitems", playerOnly = true)
    public void saveItemsCommand(Player player, @Param(name = "inventoryName") String invName) throws IOException {



        String[] serialized = BukkitSerialization.playerInventoryToBase64(player.getInventory());
        Config.invs.setString("inventories." + invName, serialized[0]);
        Config.invs.save();

        player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1f, 1.2f);
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1f, 0.5f);
        player.sendMessage(Utils.color("&e&l| &fInventory has been &f&lsaved!&7 (&e&l&n"+invName+"&7)"));
        ToastNotification tn = new ToastNotification(Material.CHEST_MINECART, ChatColor.translateAlternateColorCodes('&', "&f&l" + invName + "\n" + "&7Inventory was &f&l&nsaved&7!"), AdvancementDisplay.AdvancementFrame.TASK);
        tn.send(player);

    }

    @Command(names = {"loadItems"}, permission = "ie.loaditems", playerOnly = true)
    public void loadItemsCommand(Player player, @Param(name = "inventoryName") String invName) throws IOException {

        String inv = Config.invs.getString("inventories."+invName);
        if (inv != null) {
            ItemStack[] itemStacks = BukkitSerialization.itemStackArrayFromBase64(inv);

            player.getInventory().setContents(itemStacks);

            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, 1f, 1.2f);
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1f, 0.5f);
            player.sendMessage(Utils.color("&e&l| &fInventory has been &f&lloaded!&7 (&e&l&n" + invName + "&7)"));
            ToastNotification tn = new ToastNotification(Material.CHEST_MINECART, ChatColor.translateAlternateColorCodes('&', "&f&l" + invName + "\n" + "&7Inventory was &f&l&nloaded&7!"), AdvancementDisplay.AdvancementFrame.TASK);
            tn.send(player);
        } else {
            player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1f, 1.2f);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 0.5f);
            player.sendMessage(Utils.color("&e&l| &fInventory &7'&e&l"+invName+"&7'&f was &l&nnot&f &l&nfound&f!"));
        }
    }

    @Command(names = {"rename", "renameitem", "itemname"}, permission = "ie.rename", playerOnly = true)
    public void renameItemCommand(Player player, @Param(name = "newitemName", concated = true, required = true) String newItemName) throws IOException {

        String colored = Utils.getGradientFromString(newItemName);
        colored = ChatColor.translateAlternateColorCodes('&', colored);

        ItemStack item = player.getItemInHand();
        if (item == null){
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 0.8f);
            player.sendMessage(Utils.color("&c&l| &fYou do not hold any item in hand!"));
            return;
        }
        ItemMeta m = item.getItemMeta();
        assert m != null;
        m.setDisplayName(colored);
        item.setItemMeta(m);
        Utils.addDefaultsToItem(item, "");
        player.playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1f, 2f);
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1f, 2f);

        ToastNotification tn = new ToastNotification(item.getType(), m.getDisplayName()+"\n"+ChatColor.translateAlternateColorCodes('&', "&7Item was &f&lrenamed&7!"), AdvancementDisplay.AdvancementFrame.TASK);
        tn.send(player);

    }

    @Command(names = {"lore", "itemlore", "setlore"}, permission = "ie.lore", playerOnly = true)
    public void renameItemCommand(Player player, @Param(name = "loreLineNumber") int line, @Param(name = "newLoreLine", concated = true, required = true) String newLoreLine) throws IOException {

        //Make sure it does adhere to it's own concept
        if (line <= 1) {
            line = 0;
        } else {
            line--;
        }

        //Format the lore string
        String colored = Utils.getGradientFromString(newLoreLine);
        colored = ChatColor.translateAlternateColorCodes('&', colored);

        //Now, let's check the item itself
        ItemStack item = player.getItemInHand();
        if (item == null){
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 0.8f);
            player.sendMessage(Utils.color("&c&l| &fYou do not hold any item in hand!"));
            return;
        }
        ItemMeta m = item.getItemMeta();
        assert m != null;
        List<String> lines = m.getLore() != null ? m.getLore() : new ArrayList<String>();
        if (lines.size() > line) {
            lines.set(line, colored);
        } else {
            if (lines.size() > Math.max(0, line - 1)) {
                lines.set(Math.max(0, line - 1), colored);
            } else {
                lines.add(colored);
            }
        }

        //Aaaand... Make sure it does follow the design for item!
        m.setLore(lines);
        item.setItemMeta(m);
        Utils.addDefaultsToItem(item, "");
        player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1f, 2f);
        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_BREAK, 1f, 0.4f);

        ToastNotification tn = new ToastNotification(item.getType(), m.getDisplayName().length() > 0 ? m.getDisplayName() : item.getType().name() + "\n" + ChatColor.translateAlternateColorCodes('&', "&7Item lore was &f&lchanged&7!"), AdvancementDisplay.AdvancementFrame.TASK);
        tn.send(player);

    }

    @Command(names = {"ie"}, permission = "ie.admin", playerOnly = true)
    public void AdminCommands(Player player, @Param(name = "param1", required = false) String arg1) throws IOException {

        if ( arg1 == null || arg1.length() <= 0){
            player.sendMessage(Utils.color("&e&l| &f&lᴀᴅᴍɪɴ ᴍᴇɴᴜ ꜰᴏʀ ɪᴛᴇᴍᴇꜱꜱᴇɴᴛɪᴀʟꜱ"));
            player.sendMessage(Utils.color("&e&l| &f&lᴀᴠᴀɪʟᴀʙʟᴇ ᴄᴏᴍᴍᴀɴᴅꜱ:"));
            player.sendMessage(Utils.color("&a&l•» &f&l/ɪᴇ ʀᴇʟᴏᴀᴅ&7 - &8ʀᴇʟᴏᴀᴅꜱ ᴛʜᴇ ᴘʟᴜɢɪɴ ᴄᴏɴꜰɪɢᴜʀᴀᴛɪᴏɴ"));
            player.sendMessage(Utils.color("&a&l•» &f&l/ʀᴇɴᴀᴍᴇ&7 - &8ʀᴇɴᴀᴍᴇꜱ ᴛʜᴇ ɪᴛᴇᴍ ɪɴ ʜᴀɴᴅ"));
            player.sendMessage(Utils.color("&a&l•» &f&l/ʟᴏʀᴇ&7 - &8ꜱᴇᴛꜱ ᴀ ʟᴏʀᴇ ʟɪɴᴇ ꜰᴏʀ ɪᴛᴇᴍ"));
            player.sendMessage(Utils.color("&a&l•» &f&l/ꜱᴀᴠᴇɪᴛᴇᴍꜱ&7,&f&l /ʟᴏᴀᴅɪᴛᴇᴍꜱ&7 - &8ɪɴᴠᴇɴᴛᴏʀʏ ꜱᴀᴠɪɴɢ/ʟᴏᴀᴅɪɴɢ"));
            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1f, 2f);
            player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_BREAK, 1f, 0.4f);
        }

        if (arg1 != null && arg1.equalsIgnoreCase("reload")){
            CoreClass.MAINCLASS.LoadConfig();
            player.sendMessage(Utils.color("&e&l| &fConfiguration was &f&lreloaded&f!"));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1f, 1.4f);
            player.playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1f, 0.4f);
        }

    }

    @Command(names = {"disenchant"}, permission = "ie.disenchant", playerOnly = true)
    public void disenchant(Player player, @Param(name = "allOrSpecific", concated = false, required = false) String args) throws IOException {

        ItemStack item = player.getItemInHand();
        if (item == null){
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 0.8f);
            player.sendMessage(Utils.color("&c&l| &fYou do not hold any item in hand!"));
            return;
        }
        ItemMeta m = item.getItemMeta();
        assert m != null;

        if (args == null || args != null && args.equalsIgnoreCase("all")) {
            for (Enchantment ench : m.getEnchants().keySet()) {
                m.removeEnchant(ench);
            }
        } else {

            Enchantment enchantment = Utils.getEnchantment(args);
            if (enchantment != null){
                m.removeEnchant(enchantment);
            } else {
                player.sendMessage(Utils.color("&c&l| &fThe enchantment &7'&e&l"+args+"&7' &fwas not found!"));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 0.8f);
                return;
            }
        }
        item.setItemMeta(m);
        Utils.addDefaultsToItem(item, "");
        player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1f, 2f);
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1f, 0.7f);

        ToastNotification tn = new ToastNotification(item.getType(), m.getDisplayName()+"\n"+ChatColor.translateAlternateColorCodes('&', "&7Enchants were &f&lremoved&7!"), AdvancementDisplay.AdvancementFrame.TASK);
        tn.send(player);


    }

    @Command(names = {"enchant","enchantitem","ench"}, permission = "ie.enchant", playerOnly = true)
    public void enchant(Player player, @Param(name = "enchantmentName", concated = false, required = true) String args, @Param(name = "enchantmentLevel", concated = false, required = false) int level) throws IOException {

        ItemStack item = player.getItemInHand();
        if (item == null){
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 0.8f);
            player.sendMessage(Utils.color("&c&l| &fYou do not hold any item in hand!"));
            return;
        }
        ItemMeta m = item.getItemMeta();
        assert m != null;
        Enchantment x = Utils.getEnchantment(args);
        if (x == null){
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 0.8f);
            player.sendMessage(Utils.color("&c&l| &fThat enchantment was not recognized!"));
            return;
        }
        m.addEnchant(x, level, true);
        item.setItemMeta(m);
        Utils.addDefaultsToItem(item, "");
        player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1f, 2f);
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1f, 0.7f);

        ToastNotification tn = new ToastNotification(item.getType(), m.getDisplayName()+"\n"+ChatColor.translateAlternateColorCodes('&', "&7Enchantment &f&ladded&7!"), AdvancementDisplay.AdvancementFrame.TASK);
        tn.send(player);

    }

    @Command(names = {"pickbound", "pickbind"}, permission = "ie.pickbind", playerOnly = true)
    public void pickbound(Player player, @Param(name = "allOrSpecific", concated = false, required = true) String args) throws IOException {


        //Now, let's check the item itself
        ItemStack item = player.getItemInHand();
        //User does not have any item
        if (item == null){
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 0.8f);
            player.sendMessage(Utils.color("&c&l| &fYou do not hold any item in hand!"));
            return;
        }

        //Continue
        ItemMeta m = item.getItemMeta();
        assert m != null;
        List<String> lines = m.getLore() != null ? m.getLore() : new ArrayList<String>();
        lines.add("");
        lines.add(Utils.color("&c&l| &fBelongs to: &e&l&n"+args));

        //Aaaand... Make sure it does follow the design for item!
        m.setLore(lines);
        item.setItemMeta(m);
        Utils.addDefaultsToItem(item, "");

        player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1f, 2f);
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1f, 0.7f);

        ToastNotification tn = new ToastNotification(item.getType(), ChatColor.translateAlternateColorCodes('&', "&7Item was bound \nto player &f&l"+args+"&7!"), AdvancementDisplay.AdvancementFrame.TASK);
        tn.send(player);

    }

}
