package murderRun;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import Util.Armory;
import net.md_5.bungee.api.ChatColor;

public class BuySystem {
	private final Main plugin;
	
	public BuySystem(Main plugin, Player player) {
		this.plugin = plugin;
		
		if (this.plugin.isHunted(player) || this.plugin.isHunter(player)) {
			player.sendMessage(plugin.getMRPrefix() + "You have " + plugin.getMoneyFor(player) + " credits.");
			Inventory gui = Bukkit.createInventory(player, 18, ChatColor.BLACK + "Buy Menu");
			
			gui.setContents(getItems(player));
			
			if (gui.getContents() != null) {
				player.openInventory(gui);
			}
		}
	}
	
	private ItemStack[] getItems(Player player) {
		if (this.plugin.isHunted(player)) {
			ItemStack levitationTrap = Armory.getLevitationTrap();
			ItemStack freezeTrap = Armory.getFreezeTrap();
			ItemStack hackTrap = Armory.getHackTrap();
			ItemStack horcrux = Armory.getHorcrux();
			ItemStack nauseaTrap = Armory.getNauseaTrap();
			ItemStack prisonTrap = Armory.getPrisonTrap();
			ItemStack rabbitBuff = Armory.getRabbitBuff();
			ItemStack tagless = Armory.getNoName();
			ItemStack helmet = Armory.getHuntedHelmet();
			ItemStack chest = Armory.getHuntedChestplate();
			ItemStack legs = Armory.getHuntedLeggings();
			ItemStack boots = Armory.getHuntedBoots();
			ItemStack eBoots = Armory.getEnchantedBoots();
			ItemStack glowHunted = Armory.getHuntedGlow();

			ItemStack[] items = {levitationTrap, freezeTrap, horcrux, glowHunted, hackTrap, nauseaTrap, prisonTrap, rabbitBuff, tagless, helmet, chest, legs, boots, eBoots};
			return items;
		} else if (this.plugin.isHunter(player)) {
			ItemStack poisonSmog = Armory.getPoisonSmog();
		//	ItemStack antiHorcrux = Armory.getAntiHorcrux();
			ItemStack creeperRoulette = Armory.getCreeperRoulette();
			ItemStack ghost = Armory.getGhost();
			ItemStack healthCut = Armory.getHealthCut();
			ItemStack jetPack = Armory.getJetPack();
			ItemStack jumpBoost = Armory.getJumpBoost();
		//	ItemStack laserVision = Armory.getLaserVision();
			ItemStack nauseaBomb = Armory.getNauseaBomb();
			ItemStack seeker = Armory.getSeeker();
			ItemStack trapPurge = Armory.getTrapPurge();
			ItemStack glowHunter = Armory.getHunterGlow();
			
			ItemStack[] items = {poisonSmog, creeperRoulette, ghost, healthCut, jetPack, jumpBoost, nauseaBomb, seeker, trapPurge, glowHunter};
			return items;
		} else {
			return null;
		}
	}
}