package traps.hunted;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import murderRun.Main;

// Use by Hunted
public class Stop {
	public Stop(Main plugin, Player player) {
		if (plugin.isHunted(player)) {
			plugin.getHuntedList().forEach(p -> {
				p.sendMessage("The hunter was stopped momentarily!");
			});
			Collection<PotionEffect> effects = new ArrayList<PotionEffect>();
			
			effects.add(new PotionEffect(PotionEffectType.SLOW, 100, 255));
			effects.add(new PotionEffect(PotionEffectType.BLINDNESS, 100, 100));

			plugin.getHunter().addPotionEffects(effects);
			plugin.getHunter().sendMessage("Stop to think and forget to start again.");
		}
	}
}
