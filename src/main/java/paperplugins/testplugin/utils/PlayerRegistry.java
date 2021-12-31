package paperplugins.testplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import paperplugins.testplugin.TestPlugin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static paperplugins.testplugin.utils.CustomLogger.plog;

public class PlayerRegistry {
    public static List<String> playerList = new ArrayList<String>();
    public static YamlConfiguration registry = new YamlConfiguration();
    public static HashMap<Player, Boolean> godMap = new HashMap<Player, Boolean>();
    public static String registryPath = "plugins/makegod/gods.yml";
    public static File file;

    public static boolean setup() {
        file = new File(registryPath);

        if (!file.exists()){
            plog("Player registry doesn't exist, creating it now...");

            try {
                YamlConfiguration yaml = new YamlConfiguration();
                yaml.save(registryPath);
                return true;
            }
            catch (IOException e) {
                plog("Failed to create player registry.");
                return false;
            }
        }
        try {
            registry.load(file);
        }
        catch (Exception e){
            plog("Failed to load player registry.");
            return false;
        }

        List<?> playerEntries = registry.getList("players");
        if (playerEntries != null){
            playerEntries.forEach(playerEntry -> {
                playerList.add((String) playerEntry);
            });
            return true;
        }

        return false;
    }

    public static boolean add(Player player, String mode){
        String playerName = player.getName();

        godMap.putIfAbsent(player, true);

        // Get date and define a format for it
        Date date = new Date();
        SimpleDateFormat dateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        // If player is not in playerList, add it to list and registry
        if (!registry.contains(playerName) && !playerList.contains(playerName)){
            playerList.add(playerName);

            registry.set(String.format("players.%s.time", playerName), dateTime.format(date));
            registry.set(String.format("players.%s.mode", playerName), mode);

            try {
                registry.save(file);
            }
            catch (IOException e){
                plog("Could not add player " + playerName + " to registry.");
                return false;
            }
        }

        return true;
    }

    // Reload registry (in case it has been modified since setup (for example, manually)),
    // remove player from player list and set player entry to null in registry in order to delete it
    public static boolean remove(Player player){
        String playerName = player.getName();

        try {
            registry.load(file);
        }
        catch (Exception e){
            plog("Failed to reload player registry.");
            return false;
        }

        // If player is in either registry or playerList, delete from list then delete from registry
        if (registry.contains(playerName) || playerList.contains(playerName)){
            playerList.remove(playerName);

            godMap.put(player, false);

            registry.set(String.format("players.%s", playerName), null);

            try {
                registry.save(file);
            }
            catch (IOException e){
                plog("Could not remove player " + playerName + " from registry.");
                return false;
            }
        }

        return true;
    }

    public static boolean isGod(Player player){
        return playerList.contains(player.getName());
    }
}
