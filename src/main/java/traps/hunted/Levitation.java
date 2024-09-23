package traps.hunted;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import murderRun.Main;

// Triggered by Hunter
public class Levitation {
	public Levitation(Main plugin, Player player, Location trapLoc) {
		if (plugin.isHunter(player)) {
			plugin.removeTrap(trapLoc);
			plugin.getHuntedList().forEach(p -> {
				p.sendMessage("The hunter activated a levitation trap!");
			});
			Collection<PotionEffect> effects = new ArrayList<PotionEffect>();
			
			effects.add(new PotionEffect(PotionEffectType.LEVITATION, 20, 100));
			effects.add(new PotionEffect(PotionEffectType.SLOW_FALLING, 420, 1));
			
			plugin.getHunter().addPotionEffects(effects);
			plugin.getHunter().sendMessage("Thank you for choosing Nova Airlines. We hope you have a safe flight!");
		}
	}
}
