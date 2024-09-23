package Util;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Armory {
	public static String hackTrapName = ChatColor.GOLD + "Sword b Gone";
	public static String levitationTrapName = ChatColor.GOLD + "Levitation Trap";
	public static String nauseaTrapName = ChatColor.GOLD + "Nausea Trap";
	public static String prisonTrapName = ChatColor.GOLD + "Prison Trap";
	public static String nauseaBombTrapName = ChatColor.GOLD + "Nausea Bomb Trap";
	public static String poisonTrapName = ChatColor.GOLD + "Poison Trap";
	public static String seekerTrapName = ChatColor.GOLD + "Seeker Trap";
	public static String stopName = ChatColor.GOLD + "Stop";
	public static String huntedGlowName = ChatColor.GOLD + "Hunted Glow";
	public static String hunterGlowName = ChatColor.GOLD + "Hunter Glow";
	public static String extraLifeName = ChatColor.GOLD + "Extra Life";
	public static String rabbitModeName = ChatColor.GOLD + "Rabbit Mode";
	public static String nameHideName = ChatColor.GOLD + "Name Hide";
	public static String creeperRouletteName = ChatColor.GOLD + "Creeper Roulette";
	public static String ghostName = ChatColor.GOLD + "Ghost";
	public static String heartlessAngelName = ChatColor.GOLD + "Heartless Angel";
	public static String jetPackName = ChatColor.GOLD + "Jetpack";
	public static String jumpBoostName = ChatColor.GOLD + "Jump Boost";
	public static String laserVisionName = ChatColor.GOLD + "Laser Vision";
	public static String trapWreckerName = ChatColor.GOLD + "Trap wrecker";
	public static String horcruxBusterName = ChatColor.GOLD + "Horcrux Buster";
	
	public static String huntedChestName = ChatColor.GOLD + "Chest of The Hunted";
	public static String huntedHelmetName = ChatColor.GOLD + "Helmet of The Hunted";
	public static String huntedBootsName = ChatColor.GOLD + "Boots of The Hunted";
	public static String huntedLeggingsName = ChatColor.GOLD + "Leggings of The Hunted";
	public static String huntedEnchantedBootsName = ChatColor.GOLD + "Enchanted Boots of The Hunted";
	
	public static String hunterSwordName = ChatColor.GOLD + "Vorpal Sword";
	
	public static int hackTrapPrice = 3;
	public static int levitationTrapPrice = 3; 
	public static int nauseaTrapPrice = 2;
	public static int prisonTrapPrice = 3;
	public static int nauseaBombPrice = 2; 
	public static int poisonTrapPrice = 2;
	public static int seekerTrapPrice = 5;
	public static int stopPrice = 4; 
	public static int huntedGlowPrice = 2; 
	public static int hunterGlowPrice = 2;
	public static int extraLifePrice = 23; 
	public static int rabbitModePrice = 4;
	public static int nameHidePrice = 2;
	public static int creeperPrice = 7;
	public static int ghostPrice = 6;
	public static int healthPrice = 8;
	public static int jetPrice = 6;
	public static int jumpPrice = 5;
	public static int laserPrice = 8;
	public static int wreckerPrice = 4;
	public static int busterPrice = 1;
	public static int chestPrice = 1;
	public static int helmetPrice = 1;
	public static int bootPrice = 1;
	public static int leggingPrice = 1;
	public static int enchantedPrice = 3;

	public static ItemStack getHunterSword() {
		ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
		List<String> swordLore = Arrays.asList(ChatColor.DARK_AQUA + "Your mighty weapon");
		sword.setItemMeta(getSwordMeta(sword.getItemMeta(), hunterSwordName, swordLore));
		return sword;
	}
	
	public static ItemStack getHuntedChestplate() {
		ItemStack armor = new ItemStack(Material.DIAMOND_CHESTPLATE);
		List<String> armorLore = Arrays.asList(ChatColor.DARK_AQUA + "Makes you take less damage...", getPrice(chestPrice));
		armor.setItemMeta(getArmorMeta(armor.getItemMeta(), huntedChestName, armorLore));
		return armor;
	}
	
	public static ItemStack getHuntedHelmet() {
		ItemStack armor = new ItemStack(Material.DIAMOND_HELMET);
		List<String> armorLore = Arrays.asList(ChatColor.DARK_AQUA + "Makes you take less damage...", getPrice(helmetPrice));
		armor.setItemMeta(getArmorMeta(armor.getItemMeta(), huntedHelmetName, armorLore));
		return armor;
	}
	
	public static ItemStack getHuntedBoots() {
		ItemStack armor = new ItemStack(Material.DIAMOND_BOOTS);
		List<String> armorLore = Arrays.asList(ChatColor.DARK_AQUA + "Makes you take less damage...", getPrice(bootPrice));
		armor.setItemMeta(getArmorMeta(armor.getItemMeta(), huntedBootsName, armorLore));
		return armor;
	}
	
	public static ItemStack getHuntedLeggings() {
		ItemStack armor = new ItemStack(Material.DIAMOND_LEGGINGS);
		List<String> armorLore = Arrays.asList(ChatColor.DARK_AQUA + "Makes you take less damage...", getPrice(leggingPrice));
		armor.setItemMeta(getArmorMeta(armor.getItemMeta(), huntedLeggingsName, armorLore));
		return armor;
	}
	
	public static ItemStack getEnchantedBoots() {
		ItemStack armor = new ItemStack(Material.DIAMOND_BOOTS);
		List<String> armorLore = Arrays.asList(ChatColor.DARK_AQUA + "Makes you take less damage...", 
				ChatColor.DARK_AQUA + "But enchanted....", getPrice(enchantedPrice));
		armor.setItemMeta(getEnchantedArmorMeta(armor.getItemMeta(), huntedEnchantedBootsName, armorLore));
		return armor;
	}
	
	public static ItemStack getLevitationTrap() {
		ItemStack levitationTrap = new ItemStack(Material.JUNGLE_PRESSURE_PLATE);
		List<String> leviLore = Arrays.asList(ChatColor.DARK_AQUA + "Makes the Hunter levitate", 
				ChatColor.DARK_AQUA + "for 5 seconds.", getPrice(levitationTrapPrice));
		levitationTrap.setItemMeta(getItemMeta(levitationTrap.getItemMeta(), levitationTrapName, leviLore));
		return levitationTrap;
	}

	public static ItemStack getFreezeTrap() {
		ItemStack freezeTrap = new ItemStack(Material.PACKED_ICE);
		List<String> freezeLore = Arrays.asList(ChatColor.DARK_AQUA + "Stops the Hunter", ChatColor.DARK_AQUA + "for 5 seconds.", getPrice(stopPrice));
		freezeTrap.setItemMeta(getItemMeta(freezeTrap.getItemMeta(), stopName, freezeLore));
		return freezeTrap;
	}
	
	public static ItemStack getHuntedGlow() {
		ItemStack glowHunted = new ItemStack(Material.GLOW_BERRIES);
		List<String> glowLore = Arrays.asList(ChatColor.DARK_AQUA + "Makes the Hunter glow", 
				ChatColor.DARK_AQUA + "for 5 seconds.", getPrice(huntedGlowPrice));
		glowHunted.setItemMeta(getItemMeta(glowHunted.getItemMeta(), hunterGlowName, glowLore));
		return glowHunted;
	}
	
	public static ItemStack getHackTrap() {
		ItemStack hackTrap = new ItemStack(Material.WOODEN_SWORD);
		List<String> hackLore = Arrays.asList(ChatColor.DARK_AQUA + "Removes the Hunter's weapon", 
				ChatColor.DARK_AQUA + "for 5 seconds.", getPrice(hackTrapPrice));
		hackTrap.setItemMeta(getSwordMeta(hackTrap.getItemMeta(), hackTrapName, hackLore));
		return hackTrap;
	}
	
	public static ItemStack getHorcrux() {
		ItemStack horcrux = new ItemStack(Material.RESPAWN_ANCHOR);
		List<String> totemLore = Arrays.asList(ChatColor.DARK_AQUA + "Sets your current position", ChatColor.DARK_AQUA + "as a respawn location", 
				ChatColor.DARK_AQUA + "and gives you", ChatColor.DARK_AQUA + "an extra life.", getPrice(extraLifePrice));
		horcrux.setItemMeta(getItemMeta(horcrux.getItemMeta(), extraLifeName, totemLore));
		return horcrux;
	}
	
	public static ItemStack getNauseaTrap() {
		ItemStack nauseaTrap = new ItemStack(Material.ROTTEN_FLESH);
		List<String> nauseaLore = Arrays.asList(ChatColor.DARK_AQUA + "Gives the Hunter nausea", 
				ChatColor.DARK_AQUA + "for 10 seconds.", getPrice(nauseaTrapPrice));
		nauseaTrap.setItemMeta(getItemMeta(nauseaTrap.getItemMeta(), nauseaTrapName, nauseaLore));
		return nauseaTrap;
	}
	
	public static ItemStack getPrisonTrap() {
		ItemStack prisonTrap = new ItemStack(Material.IRON_BARS);
		List<String> prisonLore = Arrays.asList(ChatColor.DARK_AQUA + "Sends the Hunter to prison", 
				ChatColor.DARK_AQUA + "for 30 seconds.", getPrice(prisonTrapPrice));
		prisonTrap.setItemMeta(getItemMeta(prisonTrap.getItemMeta(), prisonTrapName, prisonLore));
		return prisonTrap;
	}
	
	public static ItemStack getRabbitBuff() {
		ItemStack rabbitBuff = new ItemStack(Material.RABBIT_FOOT);
		List<String> rabbitLore = Arrays.asList(ChatColor.DARK_AQUA + "Makes you fast and invisible", 
				ChatColor.DARK_AQUA + "for 6 seconds.", getPrice(rabbitModePrice));
		rabbitBuff.setItemMeta(getItemMeta(rabbitBuff.getItemMeta(), rabbitModeName, rabbitLore));
		return rabbitBuff;
	}
	
	public static ItemStack getNoName() {
		ItemStack tagless = new ItemStack(Material.NAME_TAG);
		List<String> tagLore = Arrays.asList(ChatColor.DARK_AQUA + "Hides everyone's names", 
				ChatColor.DARK_AQUA + "on 'Team Hunted'", ChatColor.DARK_AQUA + "for 25 seconds.", getPrice(nameHidePrice));
		tagless.setItemMeta(getItemMeta(tagless.getItemMeta(), nameHideName, tagLore));
		return tagless;
	}
	
	public static ItemStack getPoisonSmog() {
		ItemStack poisonSmog = new ItemStack(Material.SPLASH_POTION);
		List<String> poisonLore = Arrays.asList(ChatColor.DARK_AQUA + "Gives your victims poison", 
				ChatColor.DARK_AQUA + "for 10 seconds.", getPrice(poisonTrapPrice));
		poisonSmog.setItemMeta(getItemMeta(poisonSmog.getItemMeta(), poisonTrapName, poisonLore));
		return poisonSmog;
	}
	
	public static ItemStack getAntiHorcrux() {
		ItemStack antiHorcrux = new ItemStack(Material.BARRIER);
		List<String> horcruxLore = Arrays.asList(ChatColor.DARK_AQUA + "Removes a Horcrux from the field.", 
				ChatColor.DARK_AQUA + "Must be standing at the Horcrux.", getPrice(busterPrice));
		antiHorcrux.setItemMeta(getItemMeta(antiHorcrux.getItemMeta(), horcruxBusterName, horcruxLore));
		return antiHorcrux;
	}
	
	public static ItemStack getCreeperRoulette() {
		ItemStack creeperRoulette = new ItemStack(Material.CREEPER_HEAD);
		List<String> creeperLore = Arrays.asList(ChatColor.DARK_AQUA + "Spawns a Creeper on", 
				ChatColor.DARK_AQUA + "one of your victims", ChatColor.DARK_AQUA + "chosen by random.", getPrice(creeperPrice));
		creeperRoulette.setItemMeta(getItemMeta(creeperRoulette.getItemMeta(), creeperRouletteName, creeperLore));
		return creeperRoulette;
	}
	
	public static ItemStack getGhost() {
		ItemStack ghost = new ItemStack(Material.GHAST_TEAR);
		List<String> ghostLore = Arrays.asList(ChatColor.DARK_AQUA + "Turns you into a Ghost", 
				ChatColor.DARK_AQUA + "for 10 seconds.", getPrice(ghostPrice));
		ghost.setItemMeta(getItemMeta(ghost.getItemMeta(), ghostName, ghostLore));
		return ghost;
	}
	
	public static ItemStack getHealthCut() {
		ItemStack healthCut = new ItemStack(Material.ZOMBIE_HEAD);
		List<String> cutLore = Arrays.asList(ChatColor.DARK_AQUA + "Sets your victims' health to", 
				ChatColor.DARK_AQUA + "1 heart for 30 seconds.", getPrice(healthPrice));
		healthCut.setItemMeta(getItemMeta(healthCut.getItemMeta(), heartlessAngelName, cutLore));
		return healthCut;
	}
	
	public static ItemStack getJetPack() {
		ItemStack jetPack = new ItemStack(Material.ELYTRA);
		List<String> jetpackLore = Arrays.asList(ChatColor.DARK_AQUA + "Turns your crouch into a", 
				ChatColor.DARK_AQUA + "Jetpack for 2 minutes.", getPrice(jetPrice));
		jetPack.setItemMeta(getItemMeta(jetPack.getItemMeta(), jetPackName, jetpackLore));
		return jetPack;
	}
	
	public static ItemStack getJumpBoost() {
		ItemStack jumpBoost = new ItemStack(Material.RABBIT_FOOT);
		List<String> jumpLore = Arrays.asList(ChatColor.DARK_AQUA + "Gives you a Jump Boost", 
				ChatColor.DARK_AQUA + "for 10 seconds.", getPrice(jumpPrice));
		jumpBoost.setItemMeta(getItemMeta(jumpBoost.getItemMeta(), jumpBoostName, jumpLore));
		return jumpBoost;
	}
	
	public static ItemStack getLaserVision() {
		ItemStack laserVision = new ItemStack(Material.SPYGLASS);
		List<String> laserLore = Arrays.asList(ChatColor.DARK_AQUA + "Tells you the nearest horcrux location.", getPrice(laserPrice));
		laserVision.setItemMeta(getItemMeta(laserVision.getItemMeta(), laserVisionName, laserLore));
		return laserVision;
	}
	
	public static ItemStack getNauseaBomb() {
		ItemStack nauseaBomb = new ItemStack(Material.ROTTEN_FLESH);
		List<String> nauseaLore = Arrays.asList(ChatColor.DARK_AQUA + "Gives your victims Nausea", 
				ChatColor.DARK_AQUA + "for 10 seocnds.", getPrice(nauseaBombPrice));
		nauseaBomb.setItemMeta(getItemMeta(nauseaBomb.getItemMeta(), nauseaBombTrapName, nauseaLore));
		return nauseaBomb;
	}
	
	public static ItemStack getSeeker() {
		ItemStack seeker = new ItemStack(Material.SPECTRAL_ARROW);
		List<String> seekerLore = Arrays.asList(ChatColor.DARK_AQUA + "Blinds (5s) and makes", 
				ChatColor.DARK_AQUA + "your victim glow(3s).", getPrice(seekerTrapPrice));
		seeker.setItemMeta(getItemMeta(seeker.getItemMeta(), seekerTrapName, seekerLore));
		return seeker;
	}
	
	public static ItemStack getTrapPurge() {
		ItemStack trapPurge = new ItemStack(Material.TNT);
		List<String> trapLore = Arrays.asList(ChatColor.DARK_AQUA + "Destroys all traps within", 
				ChatColor.DARK_AQUA + "a 5x5 block area", ChatColor.DARK_AQUA + "from your location.", getPrice(wreckerPrice));
		trapPurge.setItemMeta(getItemMeta(trapPurge.getItemMeta(), trapWreckerName, trapLore));
		return trapPurge;
	}
	
	public static ItemStack getHunterGlow() {
		ItemStack glowHunter = new ItemStack(Material.GLOW_BERRIES);
		List<String> glowLore = Arrays.asList(ChatColor.DARK_AQUA + "Makes your victims glow", 
				ChatColor.DARK_AQUA + "for 10 seconds.", getPrice(hunterGlowPrice));
		glowHunter.setItemMeta(getItemMeta(glowHunter.getItemMeta(), huntedGlowName, glowLore));
		return glowHunter;
	}
	
	private static ItemMeta getItemMeta(ItemMeta meta, String name, List<String> lore) {
		meta.setDisplayName(ChatColor.GOLD + name);
		meta.setLore(lore);
		meta.addEnchant(Enchantment.CHANNELING, 71, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		return meta;
	}
	
	private static ItemMeta getSwordMeta(ItemMeta meta, String name, List<String> lore) {
		meta.setDisplayName(ChatColor.GOLD + name);
		meta.setLore(lore);
		meta.addEnchant(Enchantment.MENDING, 71, true);
		meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		return meta;
	}
	
	private static ItemMeta getArmorMeta(ItemMeta meta, String name, List<String> lore) {
		meta.setDisplayName(ChatColor.GOLD + name);
		meta.setLore(lore);
		meta.addEnchant(Enchantment.MENDING, 71, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		return meta;
	}
	
	private static ItemMeta getEnchantedArmorMeta(ItemMeta meta, String name, List<String> lore) {
		meta.setDisplayName(ChatColor.GOLD + name);
		meta.setLore(lore);
		meta.addEnchant(Enchantment.MENDING, 71, true);
		meta.addEnchant(Enchantment.PROTECTION_FALL, 5, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		return meta;
	}
	
	private static String getPrice(int price) {
		return ChatColor.GOLD + "Costs " + price + (price == 1 ? " credit." : " credits.");
	}
}
