package traps.hunter;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import murderRun.Main;

//Use by Hunter
public class AntiHorcrux {
	public AntiHorcrux(Main plugin, Player player) {
		if (plugin.cruxesExist()) {
			Location loc = toLocation(player);
			if (plugin.getCruxes().containsValue(loc)) {
				plugin.destroyHorcrux(loc);
				player.sendMessage(plugin.getMRPrefix() + "Horcrux Destroyed!");
			} else {
				player.sendMessage(plugin.getMRPrefix() + "Wasted...");
			}
		} else {
			player.sendMessage(plugin.getMRPrefix() + "Wasted...");
		}
	}
	
	private Location toLocation(Player player) {
		return new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
	}
}
