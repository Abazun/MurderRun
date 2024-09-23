package traps.hunter;

import org.bukkit.NamespacedKey;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import murderRun.Main;

// Use by Hunter
public class Ghost {	
	private int scheduler;
	
	public Ghost(Main plugin, Player player) {
		if (plugin.isHunter(player)) {
			KeyedBossBar bar = Bukkit.createBossBar(new NamespacedKey(plugin, "ghostmode"), (ChatColor.BLACK + "Ghost Time-Limit"), BarColor.WHITE, BarStyle.SOLID);
			bar.addPlayer(player);
			bar.setVisible(true);
						
			player.sendMessage("Ghost activated!");
			player.setGameMode(GameMode.SPECTATOR);
      
      scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
  			int count = -1;
  			double progress = 1.0;
  			double time = 1.0 / (10); // seconds
  			
  			public void run() {
					if (progress > 0.0) {
						bar.setProgress(progress);
					}
					
					switch(count) {
					case -1:
						break;
					case 0:
						player.sendMessage("Ghost deactivated!");
						player.setGameMode(GameMode.SURVIVAL);
    				bar.removeAll();
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
