package paperplugins.testplugin.utils;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class CustomLogger {
    // Log with color prefix
    public static void log(String text, ChatColor color){
        Bukkit.getLogger().info(color + text);
    }

    // Plain log, with default color
    public static void log(String text){
        Bukkit.getLogger().info(text);
    }

    // Log with plugin name prefix
    public static void plog(String text){
        Bukkit.getLogger().info(ChatColor.AQUA + "[MAKEGOD] " + ChatColor.RESET + text);
    }
}