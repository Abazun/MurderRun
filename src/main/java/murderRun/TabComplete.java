package murderRun;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TabComplete implements TabCompleter{
	
	@Override
	public List<String> onTabComplete(CommandSender user, Command command, String arg, String[] param) {
		if(command.getName().toLowerCase().contains("murderrun") && user instanceof Player) {
			if (param.length == 1) {
				List<String> options = new ArrayList<String>();
				options.add("begin");
				options.add("buy");
				options.add("join");
				options.add("leave");
				options.add("setspawn");
				options.add("setup");
				options.add("stop");
				
				return options;
			}			
			
			if (param.length == 2) {
				if (param[0].toLowerCase().contains("setspawn")) {
					List<String> options = new ArrayList<String>();

					options.add("hunted");
					options.add("hunter");
					options.add("jail");
					return options;
				}
			}
			return null;
		}
		return null;
	}
}
