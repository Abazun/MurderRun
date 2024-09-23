package messageSystem;

import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import murderRun.Main;

public class BeginGame extends FixedSetPrompt {
	private final Main plugin;
	private final Player player;
	
	public BeginGame(Main plugin, Player player) {
		this.player = player;
		this.plugin = plugin;
	}
	
	@Override
	public String getPromptText(ConversationContext c) {
		return plugin.getMRPrefix() + "Your inventory will be cleared to start the game. Do you wish to proceed? <yes> | <no>";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext c, String s) {
		Conversable cv = c.getForWhom();
		if (s.length() > 0) {
			if (s.toLowerCase().contains("yes")) {
				beginGame(player);
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
	
	private void beginGame(Player player) {
		if (plugin.isHunter(player)) {
			if (plugin.isGameSetupComplete()) {
				player.sendMessage(plugin.getMRPrefix() + "Starting game.");
				plugin.createGameTimer();
				plugin.getHuntedList().forEach(h -> {
					h.teleport(plugin.getHuntedSpawn());
				});
				
				plugin.getHunter().teleport(plugin.getHunterSpawn());
				
			} else {
				if (plugin.getHunterSpawn() == null) {
					player.sendMessage(plugin.getMRPrefix() + "You have not yet set your spawn location.");
				}
				if (plugin.getHuntedSpawn() == null) {
					player.sendMessage(plugin.getMRPrefix() + "You have not yet set the spawn location for your victims.");
				}
				if (plugin.getJailLocation() == null) {
					player.sendMessage(plugin.getMRPrefix() + "You have not yet set the jail location.");
				}
				if (plugin.getTeamHunted().getSize() == 0) {
					player.sendMessage(plugin.getMRPrefix() + "No one has joined team 'Hunted' yet.");
				}
			}
		}
	}
}
