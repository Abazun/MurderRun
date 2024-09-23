package traps.hunted;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import murderRun.Main;

public class Horcrux {
	
	// Use by Hunted
	public Horcrux(Main plugin, Player player) {
		if (plugin.isHunter(player)) {
			plugin.addLife(player);
			Location hLocation = toLocation(player);
			ArmorStand horcrux = player.getWorld().spawn(hLocation, ArmorStand.class);
			horcrux.setVisible(false);
			plugin.addHorcrux(horcrux, hLocation, player.getUniqueId());
			// TODO - add effect
		}
	}
	
	private Location toLocation(Player player) {
		return new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
	}
}
