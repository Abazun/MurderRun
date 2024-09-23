package murderRun;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Util.Armory;
import net.md_5.bungee.api.ChatColor;
import traps.hunted.GlowHunted;
import traps.hunted.HideName;
import traps.hunted.Horcrux;
import traps.hunted.Levitation;
import traps.hunted.NauseaTrap;
import traps.hunted.NoSword;
import traps.hunted.Prison;
import traps.hunted.RabbitBuff;
import traps.hunted.Stop;
import traps.hunter.AntiHorcrux;
import traps.hunter.CreeperRoulette;
import traps.hunter.Ghost;
import traps.hunter.GlowHunter;
import traps.hunter.HealthCut;
import traps.hunter.JetPack;
import traps.hunter.JumpBoost;
import traps.hunter.NauseaBomb;
import traps.hunter.PoisonSmog;
import traps.hunter.Seeker;
import traps.hunter.TrapPurge;

public class EventListener implements Listener {
	private final Main plugin;
	private List<String> validTraps = Arrays.asList(Armory.hackTrapName, Armory.levitationTrapName, Armory.nauseaTrapName, 
			Armory.prisonTrapName, Armory.nauseaBombTrapName, Armory.poisonTrapName, Armory.seekerTrapName);
	
	private List<String> abilities = Arrays.asList(Armory.stopName, Armory.huntedGlowName, Armory.hunterGlowName, Armory.extraLifeName, 
			Armory.rabbitModeName, ChatColor.GOLD + "regen_buff", Armory.nameHideName, Armory.creeperRouletteName, Armory.ghostName, Armory.heartlessAngelName, 
			Armory.jetPackName, Armory.jumpBoostName, Armory.laserVisionName, Armory.trapWreckerName, Armory.horcruxBusterName);
	
	private List<String> armor = Arrays.asList(Armory.huntedChestName, Armory.huntedHelmetName, Armory.huntedBootsName,
			Armory.huntedLeggingsName, Armory.huntedEnchantedBootsName);
	
	private List<Material> restrictedBlocks = Arrays.asList(Material.CHEST, Material.CHEST_MINECART, Material.ENDER_CHEST, 
			Material.DROPPER, Material.HOPPER, Material.DISPENSER, Material.BLAST_FURNACE, Material.FURNACE, Material.ANVIL, Material.CRAFTING_TABLE,
			Material.FURNACE_MINECART, Material.HOPPER_MINECART, Material.TRAPPED_CHEST, Material.SHULKER_BOX, Material.ENCHANTING_TABLE, Material.CHIPPED_ANVIL,
			Material.DAMAGED_ANVIL, Material.ANVIL, Material.BLACK_SHULKER_BOX, Material.BLUE_SHULKER_BOX, Material.BROWN_SHULKER_BOX, Material.CYAN_SHULKER_BOX,
			Material.GRAY_SHULKER_BOX, Material.GREEN_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX, Material.LIGHT_GRAY_SHULKER_BOX, Material.LIME_SHULKER_BOX,
			Material.MAGENTA_SHULKER_BOX, Material.ORANGE_SHULKER_BOX, Material.PINK_SHULKER_BOX, Material.PURPLE_SHULKER_BOX, Material.RED_SHULKER_BOX, 
			Material.WHITE_SHULKER_BOX, Material.YELLOW_SHULKER_BOX, Material.BARREL, Material.LOOM, Material.SMOKER, Material.CARTOGRAPHY_TABLE, Material.FLETCHING_TABLE,
			Material.GRINDSTONE, Material.SMITHING_TABLE, Material.STONECUTTER, Material.ARMOR_STAND, Material.BREWING_STAND, Material.BUNDLE, Material.BEACON);
	
	public EventListener(Main plugin) {
		this.plugin = plugin;
	}
		
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (plugin.gameHasBegun()) {
			if (plugin.isHunted(e.getEntity()) && !e.getEntity().getScoreboardTags().contains("gameover")) {
				this.plugin.removeLife(e.getEntity());				
				if (plugin.getLives(e.getEntity()) > 0) {	
					e.getEntity().sendMessage("Horcrux Activated!");
				} else {
					e.getEntity().addScoreboardTag("gameover");
					plugin.addDeath(e.getEntity());
				}
				checkGameEnd();
			}
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		if (plugin.isGameInProgress()) {
			if (plugin.isHunted(e.getPlayer())) {
				Player player = e.getPlayer();
				player.getInventory().clear();
				if (player.getScoreboardTags().contains("gameover")) {
					player.removeScoreboardTag("gameover");
				}
				
				if (player.getScoreboardTags().contains("ghosted")) {
					player.removeScoreboardTag("ghosted");
					player.removePotionEffect(PotionEffectType.INVISIBILITY);
				}
				plugin.removeHuntedPlayer(player);
				plugin.removeDeath(player);
				checkGameEnd();
			} else if (plugin.isHunter(e.getPlayer())) {
				plugin.getHuntedList().forEach(h -> {
					h.sendMessage("The Hunter has fled! You Win!");
				});			
				plugin.stopGame();
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (plugin.isGameInProgress() && plugin.getScoreboard() != null) {
			e.getPlayer().setScoreboard(plugin.getScoreboard());
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		if (plugin.isGameInProgress() && !plugin.gameHasBegun()) {
			 if (plugin.getDeathList().contains(e.getPlayer()) && (!e.getPlayer().getScoreboardTags().contains("gameover") && !e.getPlayer().getScoreboardTags().contains("ghosted"))) {
				 if (plugin.getLives(e.getPlayer()) == 0) {
					 e.getPlayer().addScoreboardTag("gameover");
				 }
			 }
			
			if (plugin.isPlaying(e.getPlayer())) {
				if (plugin.isHunted(e.getPlayer()) && plugin.getHuntedSpawn() != null) {
				 	e.setRespawnLocation(plugin.getHuntedSpawn());
				} else if (plugin.getHunterSpawn() != null) {
					e.setRespawnLocation(plugin.getHunterSpawn());
				}	
			}
		} else if (plugin.gameHasBegun() && plugin.isPlaying(e.getPlayer())) {
			// Locations can't be null at this point
			if (e.getPlayer().getScoreboardTags().contains("gameover") && plugin.getHuntedSpawn() != null) {
				e.setRespawnLocation(plugin.getHuntedSpawn());
				e.getPlayer().addScoreboardTag("ghosted");
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2000000, 99));
				
		//		e.setRespawnLocation(plugin.getJailLocation());
			} else if (plugin.isHunter(e.getPlayer()) && plugin.getHunterSpawn() != null) {
				e.setRespawnLocation(plugin.getHunterSpawn());
			} else if (plugin.isHunted(e.getPlayer()) && plugin.getLives(e.getPlayer()) > 0) {
				e.getPlayer().sendMessage("Horcrux Activated!");
				e.setRespawnLocation(plugin.getHorcruxSpawn(e.getPlayer()));
				plugin.removeHorcrux(plugin.getHorcruxSpawn(e.getPlayer()), e.getPlayer().getUniqueId());
			} else if (plugin.getHuntedSpawn() != null) {
				e.setRespawnLocation(plugin.getHuntedSpawn());
			}
		}
	}	
	
	// Could add hunter/hunted validation to trap/ability but.....Anarchy....
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (e.getPlayer().getScoreboardTags().contains("ghosted")) {
			e.setCancelled(true);
		} else if (plugin.isGameInProgress()) {
			if(isGameItem(e.getItemDrop()) && playerIsPlaying(e.getPlayer())) {
				Player player = e.getPlayer();
				String trapName = e.getItemDrop().getItemStack().getItemMeta().getDisplayName();
				Location trapLoc = toLocation(player);
				if (isAbility(e)) {
					e.getPlayer().sendMessage("Ability triggered");
					triggerAbility(trapName, player);
					e.getItemDrop().remove();
				}else if (isTriggerTrap(e)) {
					plugin.setTrap(trapName, player, trapLoc);
					e.getItemDrop().remove();
				}
			}
		}
	}
	
	@EventHandler
	public void onTakeDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			if (plugin.isHunted((Player) e.getDamager())) {
				e.setCancelled(true);
			} else if (e.getDamager().getScoreboardTags().contains("ghosted")) {
				e.setCancelled(true);
				e.getDamager().sendMessage("You are a ghost and cannot damage anything.");
			}
		}
	}
	
//	@EventHandler
//	public void onInventoryChange(InventoryMoveItemEvent e) {
//		if (plugin.isGameInProgress()) {
//			if (isGameItem(e.getItem())) {
//				e.setCancelled(true);
//			}
//		}
//	}
	
	@EventHandler
	public void onItemConsume(PlayerItemConsumeEvent e) {
		if (plugin.isGameInProgress()) {
			if (isGameItem(e.getItem())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (plugin.isGameInProgress()) {
			if (plugin.isPlaying(e.getPlayer())) {
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK && restrictedBlocks.contains(e.getClickedBlock().getType())) {
					e.setCancelled(true);
				}
			}
		}
	}
	
//	@EventHandler
//	public void onItemEnchantPrepare(PrepareItemEnchantEvent e) {
//		if (plugin.isGameInProgress()) {
//			if (isGameItem(e.getItem())) {
//				e.setCancelled(true);
//			}
//		}
//	}
	
  @EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (isTriggerTrap(e.getItemInHand()) || isAbility(e.getItemInHand())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (this.plugin.gameHasBegun()) {
			if (playerIsPlaying(e.getPlayer())) {
				HashMap<Location, String> traps = plugin.getTraps();
				if (traps != null) {
					Location playerLoc = toLocation(e.getPlayer());
					if (traps.containsKey(playerLoc)) {
						triggerTrap(playerLoc, e.getPlayer());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onSneakToggle(PlayerToggleSneakEvent e) {
		if (this.plugin.gameHasBegun()) {
			if (plugin.isHunter(e.getPlayer()) && e.getPlayer().getScoreboardTags().contains("JetPack")) {
				if (!e.getPlayer().isSneaking()) {
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 2400, 4));
				} else {
					e.getPlayer().removePotionEffect(PotionEffectType.LEVITATION);
				}
			}
		}
	}
	
	// TODO - cancel enchanting, and crafting during game
	
	@EventHandler
	public void onMenuClick(InventoryClickEvent e) {
		if (plugin.isGameInProgress()) {
			if (e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK + "Buy Menu")) {
				if (e.getCurrentItem() != null) {
					Player player = (Player) e.getWhoClicked();
					
					switch(e.getCurrentItem().getType()) {
						case JUNGLE_PRESSURE_PLATE:
							giveItem(player, Armory.getLevitationTrap(), Armory.levitationTrapPrice);
							break;
						case PACKED_ICE:
							giveItem(player, Armory.getFreezeTrap(), Armory.stopPrice);
							break;
						case GLOW_BERRIES:
							if (plugin.isHunted(player)) {
								giveItem(player, Armory.getHuntedGlow(), Armory.huntedGlowPrice);
							} else {
								giveItem(player, Armory.getHunterGlow(), Armory.hunterGlowPrice);
							}
							break;
						case WOODEN_SWORD: 
							giveItem(player, Armory.getHackTrap(), Armory.hackTrapPrice);
							break;
						case RESPAWN_ANCHOR: 
							giveItem(player, Armory.getHorcrux(), Armory.extraLifePrice);
							break;
						case ROTTEN_FLESH:
							if (plugin.isHunted(player)) {
								giveItem(player, Armory.getNauseaTrap(), Armory.nauseaTrapPrice);
							} else {
								giveItem(player, Armory.getNauseaBomb(), Armory.nauseaBombPrice);
							}
							break;
						case IRON_BARS:
							giveItem(player, Armory.getPrisonTrap(), Armory.prisonTrapPrice);
							break;
						case RABBIT_FOOT:
							if (plugin.isHunted(player)) {
								giveItem(player, Armory.getRabbitBuff(), Armory.rabbitModePrice);
							} else {
								giveItem(player, Armory.getJumpBoost(), Armory.jumpPrice);
							}
							break;
						case NAME_TAG: 
							giveItem(player, Armory.getNoName(), Armory.nameHidePrice);
							break;
						case SPLASH_POTION:
							giveItem(player, Armory.getPoisonSmog(), Armory.poisonTrapPrice);
							break;
						case BARRIER:
							giveItem(player, Armory.getAntiHorcrux(), Armory.busterPrice);
							break;
						case CREEPER_HEAD:
							giveItem(player, Armory.getCreeperRoulette(), Armory.creeperPrice);
							break;
						case GHAST_TEAR:
							giveItem(player, Armory.getGhost(), Armory.ghostPrice);
							break;
						case ZOMBIE_HEAD:
							giveItem(player, Armory.getHealthCut(), Armory.healthPrice);
							break;
						case ELYTRA:
							giveItem(player, Armory.getJetPack(), Armory.jetPrice);
							break;
						case SPYGLASS:
							giveItem(player, Armory.getLaserVision(), Armory.laserPrice);
							break;
						case SPECTRAL_ARROW:
							giveItem(player, Armory.getSeeker(), Armory.seekerTrapPrice);
							break;
						case TNT:
							giveItem(player, Armory.getTrapPurge(), Armory.wreckerPrice);
							break;
						case DIAMOND_CHESTPLATE:	
							giveItem(player, Armory.getHuntedChestplate(), Armory.chestPrice);
							break;
						case DIAMOND_HELMET:
							giveItem(player, Armory.getHuntedHelmet(), Armory.helmetPrice);
							break;
						case DIAMOND_BOOTS:
							if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Enchanted")) {
								giveItem(player, Armory.getEnchantedBoots(), Armory.enchantedPrice);
							} else {
								giveItem(player, Armory.getHuntedBoots(), Armory.bootPrice);
							}
							break;
						case DIAMOND_LEGGINGS:
							giveItem(player, Armory.getHuntedLeggings(), Armory.leggingPrice);
							break;
					default:
						break;
					}
				}				
				e.setCancelled(true);
			}
		}
	}
	
	private void giveItem(Player player, ItemStack item, Integer amount) {
		if (hasEnoughMoney(player, amount)) {
			player.getInventory().addItem(item);
			plugin.subtractMoneyFor(player, amount);
			player.sendMessage(plugin.getMRPrefix() + "You have " + plugin.getMoneyFor(player) + " credit(s) left.");
		} else {
			player.sendMessage(plugin.getMRPrefix() + "Insufficient funds.");
		}
	}
	
	private boolean hasEnoughMoney(Player player, Integer amount) {
		Integer bank = plugin.getMoneyFor(player);
		return bank >= amount && (bank - amount) >= 0;
	}
	
	private void triggerTrap(Location loc, Player player) {
		String trap = plugin.getTraps().get(loc);
		if (trap != null) {
			if (trap.contains(Armory.hackTrapName) && plugin.isHunter(player)) {
				new NoSword(plugin, player, loc);
			} else if (trap.contains(Armory.levitationTrapName) && plugin.isHunter(player)) {
				new Levitation(plugin, player, loc);
			} else if (trap.contains(Armory.nauseaTrapName) && plugin.isHunter(player)) {
				new NauseaTrap(plugin, player, loc);
			} else if (trap.contains(Armory.prisonTrapName) && plugin.isHunter(player)) {
				new Prison(plugin, player, loc);
			} else if (trap.contains(Armory.nauseaBombTrapName) && plugin.isHunted(player)) {
				new NauseaBomb(plugin, player, loc);
			} else if (trap.contains(Armory.poisonTrapName) && plugin.isHunted(player)) {
				new PoisonSmog(plugin, player, loc);
			} else if (trap.contains(Armory.seekerTrapName) && plugin.isHunted(player)) {
				new Seeker(plugin, player, loc);
			}	
		}
	}	
	
	private boolean isGameItem(Item item) {
		return isTriggerTrap(item.getItemStack()) || isAbility(item.getItemStack()) || isArmor(item.getItemStack());
	}
	
	private boolean isGameItem(ItemStack item) {
		return isTriggerTrap(item) || isAbility(item) || isArmor(item);
	}
	
	private boolean playerIsPlaying(Player player) {
		return plugin.isHunted(player) || plugin.isHunter(player);
	}
	
	private void triggerAbility(String trap, Player player) {
		if (trap.contains(Armory.horcruxBusterName)) {
			 new AntiHorcrux(plugin, player);
		} else if (trap.contains(Armory.stopName)) {
			new Stop(plugin, player);
		} else if (trap.contains(Armory.hunterGlowName)) {
			new GlowHunted(plugin, player);
		} else if (trap.contains(Armory.huntedGlowName)) {
			new GlowHunter(plugin, player);
		} else if (trap.contains(Armory.extraLifeName)) {
			new Horcrux(plugin, player);
		} else if (trap.contains(Armory.rabbitModeName)) {
			new RabbitBuff(plugin, player);
		} else if (trap == "regen_buff") {
		} else if (trap.contains(Armory.nameHideName)) {
			new HideName(plugin, player);
		} else if (trap.contains(Armory.creeperRouletteName)) {
			new CreeperRoulette(plugin, player);
		} else if (trap.contains(Armory.ghostName)) {
			new Ghost(plugin, player);
		} else if (trap.contains(Armory.heartlessAngelName)) {
			new HealthCut(plugin, player);
		} else if (trap.contains(Armory.jetPackName)) {
			new JetPack(plugin, player);
		} else if (trap.contains(Armory.jumpBoostName)) {
			new JumpBoost(plugin, player);
		} else if (trap.contains(Armory.laserVisionName)) {
		} else if (trap.contains(Armory.trapWreckerName)) {
			new TrapPurge(plugin, player);
		}
	}
	
	private boolean isTriggerTrap(PlayerDropItemEvent trap) {
		return validTraps.contains(trap.getItemDrop().getItemStack().getItemMeta().getDisplayName()) && hasCorrectMeta(trap.getItemDrop().getItemStack());
	}
	
	private boolean isTriggerTrap(ItemStack stack) {
		return validTraps.contains(stack.getItemMeta().getDisplayName()) && hasCorrectMeta(stack);
	}
	
	private boolean isAbility(PlayerDropItemEvent trap) {
		return abilities.contains(trap.getItemDrop().getItemStack().getItemMeta().getDisplayName()) && hasCorrectMeta(trap.getItemDrop().getItemStack());
	}
	
	private boolean isAbility(ItemStack stack) {
		return abilities.contains(stack.getItemMeta().getDisplayName()) && hasCorrectMeta(stack);
	}
	
	private boolean hasCorrectMeta(ItemStack item) {
		return (item.containsEnchantment(Enchantment.CHANNELING) && item.getEnchantmentLevel(Enchantment.CHANNELING) == 71) ||
				(item.containsEnchantment(Enchantment.MENDING) && item.getEnchantmentLevel(Enchantment.MENDING) == 71);
	}
	
	private boolean isArmor(ItemStack stack) {
		return armor.contains(stack.getItemMeta().getDisplayName()) && hasCorrectMeta(stack);
	}
	
	private Location toLocation(Player player) {
		return new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
	}
	
	private void checkGameEnd() {
		if (plugin.gameHasBegun()) {
			if (plugin.getDeathList().size() == plugin.getHuntedList().size()) {
				// End game
				plugin.getHunter().sendMessage("You Win!");
				if (plugin.getHuntedList().size() > 0) {
					plugin.getHuntedList().forEach(player -> {
						player.sendMessage("The Hunter has won!");
						player.removeScoreboardTag("gameover");
					});
				}
				plugin.stopGame();
			}
		}
	}
}
