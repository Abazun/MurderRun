package traps.hunter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import murderRun.Main;

public class JetPack {
	private int scheduler;

	// Use by Hunter
	public JetPack(Main plugin, Player player) {
		if (plugin.isHunter(player)) {
			player.sendMessage("Jetpack activated!");
			player.addScoreboardTag("JetPack");
			
      scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
  			int count = -1;
  			double progress = 1.0;
  			double time = 1.0 / (120); // seconds
  			
  			public void run() {
					switch(count) {
					case -1:
						break;
					case 0:
						plugin.getHunter().removeScoreboardTag("JetPack");
						if (plugin.getHunter().hasPotionEffect(PotionEffectType.LEVITATION)) {
							plugin.getHunter().removePotionEffect(PotionEffectType.LEVITATION);
						}
						player.sendMessage("Jetpack deactivated.");
    				clearScheduler();
						break;
					}
					
					if (progress >= 0.0) {
						progress -= time;
					}
					if (progress <= 0.0) {
						count ++;
					}
  			}
  		}, 0, 20);
		} else {
			player.sendMessage("Something went wrong.");
		}
	}
	
	private void clearScheduler() {
		Bukkit.getScheduler().cancelTask(scheduler);
	}
}
