package traps.hunter;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import murderRun.Main;

// Triggered by Hunted
public class PoisonSmog {
	public PoisonSmog(Main plugin, Player player, Location trapLoc) {
		if (plugin.isHunted(player)) {
			plugin.getHunter().sendMessage(player.getName() + " activated the Poison Smog trap!");
			plugin.removeTrap(trapLoc);
			plugin.getHuntedList().forEach(user -> {
				user.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
			});
		}
	}
}
