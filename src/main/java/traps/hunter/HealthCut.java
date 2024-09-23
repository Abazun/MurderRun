package traps.hunter;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import murderRun.Main;

// Use by Hunter
public class HealthCut {
	protected HashMap<UUID, Double> healthMap;
	private int scheduler;

	public HealthCut(final Main plugin, Player player) {
		if (plugin.isHunter(player)) {
			healthMap = new HashMap<UUID, Double>();
			plugin.getHuntedList().forEach(user -> {
				healthMap.put(user.getUniqueId(), user.getHealth());
				user.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2);
				user.setHealth(2);
				user.getWorld().playEffect(user.getLocation(), Effect.END_GATEWAY_SPAWN, 0);
			});
     
      scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
  			int count = -1;
  			double progress = 1.0;
  			double time = 1.0 / (30); // seconds
  			
  			public void run() {
					switch(count) {
					case -1:
						break;
					case 0:
						plugin.getHuntedList().forEach(player -> {
							player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
							player.setHealth(healthMap.get(player.getUniqueId()));
						});

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
		}
	}
	
	private void clearScheduler() {
		Bukkit.getScheduler().cancelTask(scheduler);
	}
}
