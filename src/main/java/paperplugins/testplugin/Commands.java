package paperplugins.testplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import paperplugins.testplugin.utils.PlayerRegistry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static paperplugins.testplugin.utils.CustomLogger.log;
import static paperplugins.testplugin.utils.CustomLogger.plog;


public class Commands {
    public static void EnableGodMode(Player player, CommandSender sender, Plugin plugin){
        File file = new File("plugins/makegod/gods.yml");

        if (!file.exists()){
            PlayerRegistry.setup();
        }

        PlayerRegistry.add(player, "god");

        player.sendMessage(ChatColor.AQUA + "You have been charged with the power of the gods!");
        player.getWorld().strikeLightningEffect(player.getLocation());

        player.setAllowFlight(true);
        player.setWalkSpeed(0.6f);
        player.setFlySpeed(0.4f);

        new BukkitRunnable() {
            int timer = 0;
            public void run() {
                if (timer++ > 1 ) {
                    // Check if GODMODE is still true
                    if (PlayerRegistry.isGod(player)){
                        DisableGodMode(player, sender, plugin, true);

                        PlayerRegistry.remove(player);
                    }
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 600L); // 30 secs
    }

    public static void DisableGodMode(Player player, CommandSender sender, Plugin plugin, boolean expired){

        if (expired) {
            player.sendMessage(ChatColor.AQUA + "You can feel the power of the gods leaving your body...");
        }
        else {
           player.sendMessage(ChatColor.RED + "You have refused the power of the gods!");
        }

        player.getWorld().strikeLightningEffect(player.getLocation());

        player.setAllowFlight(false);
        player.setWalkSpeed(0.2f);
        player.setFlySpeed(0.2f);

        PlayerRegistry.remove(player);
    }
}
