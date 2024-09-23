package traps.hunted;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import murderRun.Main;

// Use by Hunted
public class GlowHunted {
	public GlowHunted(Main plugin, Player player) {
		if (plugin.isHunted(player)) {
			plugin.getHuntedList().forEach(p -> {
				p.sendMessage("The hunter is glowing!");
			});
			
			plugin.getHunter().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 99));

			player.sendMessage("Glow activated!");
		} else {
			player.sendMessage("Something went wrong " + plugin.isHunted(player));
		}
	}
}
