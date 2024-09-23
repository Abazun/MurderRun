package traps.hunted;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import murderRun.Main;

// Use by Hunted
public class HideName {
	private int scheduler;

	public HideName(Main plugin, Player player) {
		if (plugin.isHunted(player)) {
		  	plugin.getTeamHunted().setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.FOR_OTHER_TEAMS);
			  plugin.getHuntedList().forEach(user -> {
				user.sendMessage("Your name is hidden!");
			});
      
      scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
  			int count = -1;
  			double progress = 1.0;
  			double time = 1.0 / (25); // seconds
  			
  			public void run() {
					switch(count) {
					case -1:
						break;
					case 0:
				  	plugin.getTeamHunted().setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.ALWAYS);

						plugin.getHuntedList().forEach(player -> {
							player.sendMessage("Your name is no longer hidden.");
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
