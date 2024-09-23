package traps.hunter;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import murderRun.Main;

// Use by Hunter
public class JumpBoost {
	public JumpBoost(Main plugin, Player player) {
		if (plugin.isHunter(player)) {
			player.sendMessage("Jump Boost activated!");
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 3));
		}
	}
}
