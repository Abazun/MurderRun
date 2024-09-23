package messageSystem;

import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import Util.Armory;
import murderRun.Main;
import murderRun.StartGame;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class JoinHunterSystem extends FixedSetPrompt {
	private final Main plugin;
	private final Player player;
	
	public JoinHunterSystem(Main plugin, Player player) {
		this.player = player;
		this.plugin = plugin;
	}
	
	@Override
	public String getPromptText(ConversationContext c) {
		TextComponent yes = new TextComponent("yes");
		yes.setColor(ChatColor.GREEN);
		yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "yes"));
		
		TextComponent no = new TextComponent("no");
		no.setColor(ChatColor.RED);
		no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "no"));

		return ": " + ChatColor.DARK_PURPLE + "Your inventory will be cleared to start the game. Do you wish to proceed? " + 
		"<" + yes.getText() + ChatColor.DARK_PURPLE + "> | <" + no.getText() + ChatColor.DARK_PURPLE + ">";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext c, String s) {
		Conversable cv = c.getForWhom();
		if (s.length() > 0) {
			if (s.toLowerCase().contains("yes")) {
				player.getInventory().clear();
				player.getInventory().addItem(Armory.getHunterSword());
				player.sendMessage(plugin.getMRPrefix() + "You are The Hunter. Once setup is complete, run '/murderRun begin' to begin the game.");
				new StartGame(this.plugin, player);
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
