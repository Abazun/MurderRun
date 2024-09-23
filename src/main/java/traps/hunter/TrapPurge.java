package traps.hunter;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import murderRun.Main;

// Use by Hunter
public class TrapPurge {
	private int radius = 5;
	private int successes = 0;

	public TrapPurge(Main plugin, Player player) {
		if (plugin.isHunter(player)) {
			player.sendMessage("Activating!");
			if (plugin.trapsExist()) {
				Location loc = toLocation(player);
				plugin.getTraps().keySet().forEach(trap -> {
					if (loc.distanceSquared(trap) <= radius * radius) {
						player.getWorld().strikeLightning(trap);
						plugin.removeTrap(trap);
						successes++;
					}
					if (successes == 1) {
						player.sendMessage(plugin.getMRPrefix() + successes + " trap was removed!");
					} else if (successes > 1) {
						player.sendMessage(plugin.getMRPrefix() + successes + " traps were removed!");
					} else {
						player.sendMessage("Wasted....");
					}
				});
			} else {
				player.sendMessage(plugin.getMRPrefix() + "Wasted...");
			}
		}
	}
	
	private Location toLocation(Player player) {
		return new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
	}
}
