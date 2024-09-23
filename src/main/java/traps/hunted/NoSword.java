package traps.hunted;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import Util.Armory;
import murderRun.Main;

public class NoSword {
	private int scheduler;

// Triggered by Hunter
	public NoSword(Main plugin, Player player, Location trapLoc) {
		if (plugin.isHunter(player)) {
			plugin.removeTrap(trapLoc);
			plugin.getHunter().getInventory().remove(Armory.getHunterSword());
			plugin.getHunter().sendMessage("Yoink!");
			plugin.getHuntedList().forEach(p -> {
				p.sendMessage("The hunter has lost their sword!");
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
						plugin.getHuntedList().forEach(p -> {
							p.sendMessage("The hunter has reclaimed their sword!");
						});
						plugin.getHunter().sendMessage("Here's your sword back :)");
						plugin.getHunter().getInventory().addItem(Armory.getHunterSword());
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
