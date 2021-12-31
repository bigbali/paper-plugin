package paperplugins.testplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;
import paperplugins.testplugin.utils.PlayerRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static paperplugins.testplugin.utils.PlayerRegistry.godMap;


public class Events implements Listener {

    private static final float JUMP_VELOCITY = 0.42F;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.broadcastMessage("Do you want to be a god?");
    }

    @EventHandler
    public void onPlayerJump(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Vector velocity = player.getVelocity();

        if (godMap.get(player) && velocity.getY() > 0
                && player.getLocation().getBlock().getType() != Material.LADDER
                && velocity.getY() == JUMP_VELOCITY) {
            // Calculate vector data
            float x = ((float)(event.getTo().getX() - event.getFrom().getX()) * 1.5f);
            float y = 1.4f;
            float z = ((float)(event.getTo().getZ() - event.getFrom().getZ()) * 1.5f);

            //player.sendMessage("X:" + Double.toString(x) + " " + "Y:"+ Double.toString(y)+ " " + "Z:" + Double.toString(z));
            player.setVelocity(new Vector(x,y,z));
        }

    }
}
