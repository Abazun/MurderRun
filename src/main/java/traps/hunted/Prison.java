package traps.hunted;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import murderRun.Main;

public class Prison {
	
	// Triggered by Hunter
	private Location releaseLocation;
	private int scheduler;

	public Prison(Main plugin, Player player, Location trapLoc) {
		if (plugin.isHunter(player)) {	
			player.sendMessage("You've activated a trap.");
			plugin.removeTrap(trapLoc);
			this.releaseLocation = player.getLocation();
			player.teleport(plugin.getJailLocation());
			plugin.getHuntedList().forEach(p -> {
				p.sendMessage("The hunter was sent to jail!");
			});

			scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
  			int count = -1;
  			double progress = 1.0;
  			double time = 1.0 / (10); // seconds
  			
  			public void run() {
					switch(count) {
					case -1:
						break;
					case 0:
						plugin.getHunter().teleport(releaseLocation);
						plugin.getHuntedList().forEach(p -> {
							p.sendMessage("The hunter is out of jail!");
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
