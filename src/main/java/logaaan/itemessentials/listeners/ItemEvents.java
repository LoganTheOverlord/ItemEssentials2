package logaaan.itemessentials.listeners;

import eu.endercentral.crazy_advancements.advancement.AdvancementDisplay;
import eu.endercentral.crazy_advancements.advancement.ToastNotification;
import logaaan.itemessentials.Config;
import logaaan.itemessentials.CoreClass;
import logaaan.itemessentials.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.EventListener;
import java.util.List;

public class ItemEvents implements EventListener, Listener {

    public static CoreClass main;

    @EventHandler
    public void onGmSwap(PlayerGameModeChangeEvent e) throws IOException {
        if (!CoreClass.enableSwitching) return;

        String gm = e.getNewGameMode().name();
        String lastGm = e.getPlayer().getGameMode().name();
        String name = e.getPlayer().getName();
        Player p = e.getPlayer();
        Config.invs.setString("inventories."+name+lastGm, Utils.getInventoryString(p));
        if (Config.invs.has("inventories."+name+gm)){
            e.getPlayer().getInventory().setContents(Utils.getInventoryFromNode("inventories."+name+gm));
        }
        Config.invs.save();
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent ev){
        ItemStack item = ev.getEntity().getItemStack();
        if (item == null || item != null && item.getItemMeta() == null) return;
        Utils.addDefaultsToItem(item, "");

        ev.getEntity().setCustomName(item.getItemMeta() != null ? item.getItemMeta().getDisplayName() : item.getType().name());
        ev.getEntity().setCustomNameVisible(true);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent ev){
        for (ItemStack item : ev.getDrops()){
            if (item == null || item != null && item.getItemMeta() == null) continue;
            Utils.addDefaultsToItem(item, "");

            ev.getEntity().setCustomName(item.getItemMeta() != null ? item.getItemMeta().getDisplayName() : item.getType().name());
            ev.getEntity().setCustomNameVisible(true);
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent ev){
        if (ev.getItem().getItemStack().hasItemMeta() && ev.getItem().getItemStack().getItemMeta().hasLore()){
            List<String> lor = ev.getItem().getItemStack().getItemMeta().getLore();
            String ownerOfItem = null;
            for (String v : lor){
                if (v.contains("Belongs to:")){
                    if (v.contains("Belongs to:")) {
                        ownerOfItem = v.split(": ", 64)[1];
                        ownerOfItem = ChatColor.stripColor(ownerOfItem);
                        break;
                    }
                }
            }
            if (ownerOfItem != null && ev.getEntity() instanceof Player && !((Player) ev.getEntity()).getDisplayName().equals(ownerOfItem)){
                ev.setCancelled(true);
            }
        }

        if (!(ev.getEntity() instanceof Player)) return;
        if (ev.isCancelled()) return;
        Player p = (Player) ev.getEntity();
        Utils.addDefaultsToItem(ev.getItem().getItemStack(), "");

        if (CoreClass.usePickupSounds) {
            if (CoreClass.pickupSounds.containsKey(ev.getItem().getItemStack().getType())) {
                try {
                    String val = CoreClass.pickupSounds.get(ev.getItem().getItemStack().getType());
                    String[] options = val.split(";");
                    String sound = options[0];
                    float vol = Float.parseFloat(options[1]);
                    float pitch = Float.parseFloat(options[2]);
                    p.playSound(p.getLocation(), sound, vol, pitch);
                } catch (Exception e) {
                    main.getLogger().info("There was an error with Material '" + ev.getItem().getItemStack().getType().name() + "' to play custom sound. Check your config!");
                }
            }
        }

        if (CoreClass.usePickupNotifications && !ev.isCancelled()) {
            ToastNotification tn = new ToastNotification(ev.getItem().getItemStack().getType(), Utils.color(ev.getItem().getName() + "\n" + "&7(+&f&l" + ev.getItem().getItemStack().getAmount() + "x&7)"), AdvancementDisplay.AdvancementFrame.TASK);
            tn.send(p);
        }
    }

}
