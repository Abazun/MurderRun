package murderRun;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import messageSystem.JoinHuntedSystem;
import messageSystem.JoinHunterSystem;
import messageSystem.LeaveHuntedSystem;
import messageSystem.StopGame;

import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationFactory;

public class CommandRouter implements CommandExecutor {
	private final Main plugin;
	protected ConversationFactory factory = null;
	
	public CommandRouter(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender user, Command command, String arg, String[] param) {
		if (user instanceof Player && param[0] != null) {
			Player sender = (Player) user;
			if (sender.hasPermission("murderRun.access")) {
				factory = new ConversationFactory(this.plugin);
				
				switch (param[0]) {
					case "stop":
						if (plugin.isGameInProgress()) {
							if (plugin.isHunter(sender) || sender.isOp()) {
								factory.withFirstPrompt(new StopGame(this.plugin, sender)).withTimeout(30).
								withPrefix(c -> "[Murder Run]").withModality(true).thatExcludesNonPlayersWithMessage("Cannot compute.").
								withLocalEcho(false).buildConversation((Conversable) sender).begin();	
							}
						} else {
							sender.sendMessage(plugin.getMRPrefix() + "No game is in progress.");
						}
						break;
					case "buy":
						if (this.plugin.isGameInProgress() && (this.plugin.isHunted(sender) || this.plugin.isHunter(sender))) {
							new BuySystem(this.plugin, sender);
						} else {
							sender.sendMessage(plugin.getMRPrefix() + "Not allowed.");
						}
						break;
					case "join":
						if (this.plugin.isGameInProgress() && !plugin.gameHasBegun()) {
							factory.withFirstPrompt(new JoinHuntedSystem(this.plugin, sender)).withTimeout(30).
							withPrefix(c -> "[Murder Run]").withModality(true).thatExcludesNonPlayersWithMessage("Cannot compute.").
							withLocalEcho(false).buildConversation((Conversable) sender).begin();	
						}
						break;
					case "leave":
						if (this.plugin.isHunted(sender)) {
							factory.withFirstPrompt(new LeaveHuntedSystem(this.plugin, sender)).withTimeout(30).
							withPrefix(c -> "[Murder Run]").withModality(true).thatExcludesNonPlayersWithMessage("Cannot compute.").
							withLocalEcho(false).buildConversation((Conversable) sender).begin();	
						} else {
							sender.sendMessage(plugin.getMRPrefix() + "Not allowed.");
						}
						break;
					case "setup":
						factory.withFirstPrompt(new JoinHunterSystem(this.plugin, sender)).withTimeout(30).
						withPrefix(c -> "[Murder Run]").withModality(true).thatExcludesNonPlayersWithMessage("Cannot compute.").
						withLocalEcho(false).buildConversation((Conversable) sender).begin();	
						break;
					case "setspawn":
						if (param[1] != null) {
							if (param[1].contains("hunter")) {
								this.plugin.setSpawn(sender.getLocation(), "hunter");
							} else if (param[1].contains("hunted")) {
								this.plugin.setSpawn(sender.getLocation(), "hunted");
							} else if (param[1].contains("jail")) {
								this.plugin.setJail(sender.getLocation());
							}	else {
								sender.sendMessage(plugin.getMRPrefix() + "Could not set spawn for team '" +  param[1] + "'.");
							}
						} else {
							sender.sendMessage(plugin.getMRPrefix() + "Incomplete command.");
						}
						break;
					case "begin":
						if (plugin.isHunter(sender)) {
							plugin.beginGame(sender);
						}
						break;
					default:
						sender.sendMessage(plugin.getMRPrefix() + "Not Allowed.");
						break;
				}
			}
		}
		return false;
	}
}
