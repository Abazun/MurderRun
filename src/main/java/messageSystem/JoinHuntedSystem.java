package messageSystem;

import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import murderRun.Main;
import murderRun.RegisterPlayer;
import net.md_5.bungee.api.ChatColor;

public class JoinHuntedSystem extends FixedSetPrompt {
	private final Main plugin;
	private final Player player;
	
	public JoinHuntedSystem(Main plugin, Player player) {
		this.player = player;
		this.plugin = plugin;
	}
	
	@Override
	public String getPromptText(ConversationContext c) {
		return ": " + ChatColor.DARK_PURPLE + "Your inventory will be cleared to join. Do you confirm? <yes> | <no>";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext c, String s) {
		Conversable cv = c.getForWhom();
		if (s.length() > 0) {
			if (s.toLowerCase().contains("yes")) {
				cv.sendRawMessage(plugin.getMRPrefix() + "You have joined Team Hunted.");
				player.getInventory().clear();
				plugin.giveMoney(player);
				new RegisterPlayer(this.plugin, plugin.getTeamHunted(), player);
			} else {
				cv.sendRawMessage(plugin.getMRPrefix() + "Action canceled.");
			}
		}	
		return END_OF_CONVERSATION;
	}
	
	@Override
	protected boolean isInputValid(ConversationContext c, String s) {
		return true;
	}
}
