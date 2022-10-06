package logaaan.itemessentials.listeners;

import eu.endercentral.crazy_advancements.advancement.AdvancementDisplay;
import eu.endercentral.crazy_advancements.advancement.ToastNotification;
import logaaan.itemessentials.CoreClass;
import logaaan.itemessentials.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.EventListener;
import java.util.Objects;

public class InventoryListeners implements EventListener, Listener {

    public static CoreClass main;

    @EventHandler
    public void InvEv6(InventoryClickEvent ev) {
        for (ItemStack i : ev.getInventory().getContents()) {
            if (i == null || i != null && i.getItemMeta() == null) continue;
            Utils.addDefaultsToItem(i, ev.getView().getTitle());

        }
        Player p = (Player) ev.getWhoClicked();
        if (ev.getCurrentItem() != null && CoreClass.clickSounds.containsKey((ev.getCurrentItem()).getType())) {
            try {
                String val = CoreClass.clickSounds.get(ev.getCurrentItem().getType());
                String[] options = val.split(";");
                String sound = options[0];
                float vol = Float.parseFloat(options[1]);
                float pitch = Float.parseFloat(options[2]);
                p.playSound(p.getLocation(), sound, vol, pitch);
            } catch (Exception e) {
                main.getLogger().info("There was an error with Material '" + ev.getCurrentItem().getType().name() + "' to play custom sound. Check your config!");
            }
        }

    }

    @EventHandler
    public void InvEv7(PlayerItemHeldEvent ev){

        if (!CoreClass.useHotbarSounds) return;
        Player p = ev.getPlayer();
        main.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> {
            if (CoreClass.clickSounds.containsKey(p.getItemInHand().getType())) {
                try {
                    String val = CoreClass.clickSounds.get(p.getItemInHand().getType());
                    String[] options = val.split(";");
                    String sound = options[0];
                    float vol = Float.parseFloat(options[1]);
                    float pitch = Float.parseFloat(options[2]);
                    p.playSound(p.getLocation(), sound, vol, pitch);
                } catch (Exception e) {
                    main.getLogger().info("There was an error with Material '" + p.getItemInHand().getType().name() + "' to play custom sound. Check your config!");
                }
            }
        }, 1);
    }

    @EventHandler
    public void InvEv1(InventoryCloseEvent ev){
        for (ItemStack i : ev.getPlayer().getInventory().getContents()){
            if (i == null || i != null && i.getItemMeta() == null) continue;
            Utils.addDefaultsToItem(i, ev.getView().getTitle());
        }
    }

    @EventHandler
    public void InvEv2(InventoryOpenEvent ev){
        for (ItemStack i : ev.getPlayer().getInventory().getContents()){
            if (i == null || i != null && i.getItemMeta() == null) continue;
            Utils.addDefaultsToItem(i, ev.getView().getTitle());
        }

        for (ItemStack i : ev.getInventory().getContents()){
            if (i == null || i != null && i.getItemMeta() == null) continue;
            Utils.addDefaultsToItem(i, ev.getView().getTitle());
        }
    }

    @EventHandler
    public void InvEv4(InventoryDragEvent ev){
        for (ItemStack i : ev.getView().getBottomInventory().getContents()){
            if (i == null || i != null && i.getItemMeta() == null) continue;
            Utils.addDefaultsToItem(i, ev.getView().getTitle());
        }

        for (ItemStack i : ev.getView().getTopInventory().getContents()){
            if (i == null || i != null && i.getItemMeta() == null) continue;
            Utils.addDefaultsToItem(i, ev.getView().getTitle());
        }
    }
}
