package traps.hunted;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import murderRun.Main;

// Use by Hunted
public class RabbitBuff {
	public RabbitBuff(Main plugin, Player player) {
		if (plugin.isHunted(player)) {
			player.sendMessage("Rabbit Buff activated!");
			Collection<PotionEffect> effects = new ArrayList<PotionEffect>();
			
			effects.add(new PotionEffect(PotionEffectType.SPEED, 120, 7));
			effects.add(new PotionEffect(PotionEffectType.INVISIBILITY, 120, 1));
			
			player.addPotionEffects(effects);
		}
	}
}
