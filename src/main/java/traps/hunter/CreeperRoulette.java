package traps.hunter;

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import murderRun.Main;

// Use by Hunter
public class CreeperRoulette {
	public CreeperRoulette(Main plugin, Player player) {
		if (plugin.isHunter(player)) {
			plugin.getHunter().sendMessage("Creeper Roulette!");
			
			if (!plugin.getHuntedList().isEmpty()) {
		    int rnd = new Random().nextInt(plugin.getHuntedList().size());
		    Location loc = plugin.getHuntedList().get(rnd).getLocation();
		    loc.getWorld().spawnEntity(loc, EntityType.CREEPER);
			} else {
				player.sendMessage("Could not find a Hunted Player");
			}
		}
	}
}
