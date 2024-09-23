package traps.hunter;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import murderRun.Main;

// Use by Hunter
public class GlowHunter {
	public GlowHunter(Main plugin, Player player) {
		if (plugin.isHunter(player)) {
			player.sendMessage("Glow activated!");
			plugin.getHuntedList().forEach(user -> {
				user.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 99));
			});
		}
	}
}
