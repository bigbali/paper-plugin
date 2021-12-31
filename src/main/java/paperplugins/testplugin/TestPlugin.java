package paperplugins.testplugin;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import static paperplugins.testplugin.utils.CustomLogger.log;
import static paperplugins.testplugin.utils.CustomLogger.plog;


import paperplugins.testplugin.Commands;
import paperplugins.testplugin.utils.PlayerRegistry;

public final class TestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        plog("Makegod mounting...");
        Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
        PlayerRegistry.setup();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        plog("Makegod unmounting...");
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        try {
            if (cmd.getName().equals("makegod") && sender instanceof Player){
                Player player = (Player) sender;
                if (!PlayerRegistry.isGod(player)){
                    Commands.EnableGodMode(player, sender, this);
                }
                else {
                    Commands.DisableGodMode(player, sender, this, false);
                }
            }
        } catch (Exception e) {
            sender.sendMessage(e.getMessage());
            return false;
        }

        return true;
    }

}
