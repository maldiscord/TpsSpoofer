package lol.maltest.tpsspoofer;

import lol.maltest.tpsspoofer.Util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class TpsSpoofer extends JavaPlugin implements Listener {

    public static TpsSpoofer plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("it has now enabled alr thanks");
        FileConfiguration config = this.getConfig();
        config.options().copyDefaults(true);
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // I dont normally do this, its to override the ordinal tps command
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommandOverride(PlayerCommandPreprocessEvent event){
        if (event.getMessage().equalsIgnoreCase("/tps")){
            Player player = event.getPlayer();
            if (player.hasPermission("mal.tps")) {
                player.sendMessage(ChatUtil.clr("&6TPS from last 1m, 5m, 15m: " + this.getConfig().getString("tps.1m") + ", " + this.getConfig().getString("tps.5m") + ", " + this.getConfig().getString("tps.15m")));
            } else {
                player.sendMessage(ChatUtil.clr(this.getConfig().getString("messages.noperm")));
            }
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("/tps reload")) {
            Player player = event.getPlayer();
            if(player.hasPermission("mal.tps.reload")) {
                this.reloadConfig();
                player.sendMessage(ChatUtil.clr("&cThe config for &etps spoofer &chas been reloaded successfully!"));
                event.setCancelled(true);
            }
        }
        }
    }
