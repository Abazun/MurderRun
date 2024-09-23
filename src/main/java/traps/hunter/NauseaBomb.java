package traps.hunter;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import murderRun.Main;

// Triggered by Hunted
public class NauseaBomb {
	public NauseaBomb(Main plugin, Player player, Location trapLoc) {
		if (plugin.isHunted(player)) {
			plugin.removeTrap(trapLoc);
			plugin.getHunter().sendMessage("Your Nausea Bomb was activated!");
			plugin.getHuntedList().forEach(user -> {
				user.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 100));
			});
		}
	}
}
