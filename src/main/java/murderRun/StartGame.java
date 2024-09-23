package murderRun;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import net.md_5.bungee.api.ChatColor;

public class StartGame {
	public StartGame(Main plugin, Player player) {
		if (!plugin.isGameInProgress() && plugin.getHunter() == null) {
			plugin.setHunter(player);
			plugin.initTrapList();
			plugin.initDeathList();
			plugin.initHuntedList();
			plugin.initMoney();
			plugin.setLifeMap();
			plugin.initCruxList();
			
			Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
			Team hunted = scoreboard.registerNewTeam("hunted");
			Team hunter = scoreboard.registerNewTeam("hunter");
			
			hunter.setPrefix(ChatColor.RED + "[Hunter] ");
			hunted.setPrefix(ChatColor.BLUE + "[Hunted] ");
			//hunted.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.NEVER);
			hunted.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
			hunter.addEntry(player.getName());
			
			plugin.giveMoney(player);
			
			Bukkit.getOnlinePlayers().forEach(p -> {
				p.setScoreboard(scoreboard);
			});
			
			plugin.setScoreboard(scoreboard);
			
			plugin.setTeamHunted(hunted);
			plugin.setTeamHunter(hunter);

			plugin.setLife(player);
			plugin.setGameProgress(true);
		}
	}	
}
