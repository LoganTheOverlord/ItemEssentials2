package logaaan.itemessentials.listeners;

import logaaan.itemessentials.CoreClass;
import logaaan.itemessentials.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.EventListener;

public class InventoryListeners implements EventListener, Listener {

    public static CoreClass main;

    @EventHandler
    public void InvEv6(InventoryClickEvent ev) {
        if (!ev.getView().getTitle().equals(ev.getView().getType().getDefaultTitle())) return;
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
        } else {
            if (ev.getCurrentItem().getType().isBlock()) {
                p.playSound(p.getLocation(), ev.getCurrentItem().getType().createBlockData().getSoundGroup().getPlaceSound(), 1.0f, 1.2f);
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
            } else {
                if (p.getItemInHand().getType().isBlock()){
                    p.playSound(p.getLocation(), p.getItemInHand().getType().createBlockData().getSoundGroup().getPlaceSound(), 1.0f, 1.2f);
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
