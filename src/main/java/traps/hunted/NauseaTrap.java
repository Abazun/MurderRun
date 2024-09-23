package traps.hunted;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import murderRun.Main;

// Triggered by Hunter
public class NauseaTrap {
	public NauseaTrap(Main plugin, Player player, Location trapLoc) {
		if (plugin.isHunter(player)) {
			plugin.getHuntedList().forEach(p -> {
				p.sendMessage("The hunter activated a nausea trap!");
			});
			plugin.removeTrap(trapLoc);
			player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 100));
			player.sendMessage("You should probably go see a doctor...");
		}
	}
}
