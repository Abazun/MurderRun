package traps.hunter;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import murderRun.Main;

// Triggered by Hunted
public class Seeker {
	public Seeker(Main plugin, Player player, Location trapLoc) {
		if (plugin.isHunted(player)) {
			plugin.removeTrap(trapLoc);
			plugin.getHunter().sendMessage("Your Seeker trap was activated!");
			plugin.getHuntedList().forEach(user -> {
				Collection<PotionEffect> effects = new ArrayList<PotionEffect>();
				
				effects.add(new PotionEffect(PotionEffectType.BLINDNESS, 100, 255));
				effects.add(new PotionEffect(PotionEffectType.GLOWING, 60, 99));
				
				player.addPotionEffects(effects);
			});
		}
	}
}
