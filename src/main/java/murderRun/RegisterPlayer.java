package murderRun;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class RegisterPlayer {
	public RegisterPlayer(Main plugin, Team team, Player player) {
		team.addEntry(player.getName());
		if (team == plugin.getTeamHunted()) {
			plugin.setLife(player);
			plugin.addHunted(player);
		}
	}
}
