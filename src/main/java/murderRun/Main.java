package murderRun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	private boolean gameInProgress = false;
	private boolean gameSetupComplete = false;
	private boolean gameHasBegun = false;
	private Player theHunter = null;
	
	// Must be set for the game to start
	private Location jailLocation = null;
	private Location hunterSpawn = null;
	private Location huntedSpawn = null;
	
	protected HashMap<UUID, Integer> lifeMap;
	private HashMap<Location, String> traps;
	protected HashMap<UUID, Location> horcruxes;
	protected HashMap<UUID, Integer> money;
	protected HashMap<UUID, ArmorStand> horcruxList;

	private Team hunter;
	private Team hunted;
	private List<Player> huntedList;
	private List<Player> deathList;
	private Integer gameTimer = null;
	private final String mrPrefix = ChatColor.WHITE + "[Murder Run]: " + ChatColor.DARK_PURPLE;
	KeyedBossBar gameBar = null;
	public static JavaPlugin murderRun = null;
	private Scoreboard scoreboard = null;
	
	@Override
	public void onEnable() {
		murderRun = this;
		
		// TODO add enabled-world-checking logic
		this.getCommand("murderrun").setExecutor(new CommandRouter(this));
		this.getCommand("murderrun").setTabCompleter(new TabComplete());
		Bukkit.getPluginManager().registerEvents(new EventListener(this), murderRun);
	}
	
	@Override
	public void onDisable() {
		murderRun = null;
		unregisterTeam("hunted");
		unregisterTeam("hunter");
	}
	
	public Team getTeamHunter() {
		return hunter;
	}
	
	public void setTeamHunter(Team team) {
		hunter = team;
	}
	
	public Team getTeamHunted() {
		return hunted;
	}
	
	public void setTeamHunted(Team team) {
		hunted = team;
	}
	
	protected boolean isGameInProgress() {
		return gameInProgress;
	}
	
	public void setLife(Player player) {
		lifeMap.putIfAbsent(player.getUniqueId(), 1);
		if (lifeMap.get(player.getUniqueId()) != null) {
			player.sendMessage("Life count is " + lifeMap.get(player.getUniqueId()));
		}
	}	
	
	public void addLife(Player player) {
		Integer life = getLives(player);
		if (lifeMap.containsKey(player.getUniqueId())) {
			lifeMap.replace(player.getUniqueId(), life + 1);
		}
	}
	
	protected Integer getLives(Player player) {
		return lifeMap.get(player.getUniqueId());
	}
	
	protected void removeLife(Player player) {
		Integer life = getLives(player);
		if (lifeMap.containsKey(player.getUniqueId())) {
			lifeMap.replace(player.getUniqueId(), life - 1);
		}
	}
	
	public Player getHunter() {
		return theHunter;
	}
	
	public void initCruxList() {
		horcruxes = new HashMap<UUID, Location>();
		horcruxList = new HashMap<UUID, ArmorStand>();
	}
	
	public Location getJailLocation() {
		return jailLocation;
	}
	
	public boolean isHunter(Player player) {
		return player.getUniqueId() == getHunter().getUniqueId();
	}
	
	public void initTrapList() {
		traps = new HashMap<Location, String>();
	}
	
	public void initDeathList() {
		deathList = new ArrayList<Player>();
	}
	
	public void initHuntedList() {
		huntedList = new ArrayList<Player>();
	}
	
	protected void setTrap(String trapName, Player player, Location loc) {
		if (isGameInProgress()) {
			if (!traps.containsKey(loc)) {
				traps.put(loc, trapName);
				if (traps.containsKey(loc)) {
					player.sendMessage(trapName + " placed!");
				} else {
					player.sendMessage("Something went wrong.");
				}
			} else {
				player.sendMessage("Something went wrong.");
			}
		} else {
			player.sendMessage("Something went wrong.");
		}
	}
	
	public HashMap<Location, String> getTraps(){
		return traps;
	}
	
	public boolean isHunted(Player player) {
		return huntedList != null ? huntedList.contains(player) : false;
	}
	
	public void removeTrap(Location loc) {
		if (traps.get(loc) != null) {
			traps.remove(loc);
		}
	}
	
	public boolean isSetupComplete() {
		return gameSetupComplete;
	}
	
	public void initMoney() {
		money = new HashMap<UUID, Integer>();
	}
	
	public void giveMoney(Player player) {
		if (money != null) {
			money.putIfAbsent(player.getUniqueId(), isHunter(player) ? 30 : 25);
		}
	}
	
	public Integer getMoneyFor(Player player) {
		return money.get(player.getUniqueId());
	}
	
	public void subtractMoneyFor(Player player, Integer amount) {
		if (money.containsKey(player.getUniqueId())) {
			money.replace(player.getUniqueId(), getMoneyFor(player) - amount);
		}
	}
	
	public void setHunter(Player player) {
		this.theHunter = player;
	}
	
	public void setLifeMap() {
		this.lifeMap = new HashMap<UUID, Integer>();
	}
	
	public void removeDeath(Player player) {
		if (deathList != null) {
			if (deathList.contains(player)) {
				deathList.remove(player);
			}
		}
	}
	
	public void setSpawn(Location loc, String team) {
		if (!gameSetupComplete) {
			if (team == "hunter") {
				this.hunterSpawn = loc;
			} else if (team == "hunted") {
				this.huntedSpawn = loc;
			}
		}
	}
	
	public void setJail(Location loc) {
		this.jailLocation = loc;
	}
	
	private void checkGameSetupProgress() {		
		gameSetupComplete = (hunterSpawn != null && huntedSpawn != null && jailLocation != null 
				&& getTeamHunted().getSize() > 0);
	}
	
	public Location getHorcruxSpawn(Player player) {
		return horcruxes.get(player.getUniqueId());
	}
	
	public Location getHuntedSpawn() {
		return huntedSpawn;
	}
	
	public Location getHunterSpawn() {
		return hunterSpawn;
	}
	
	public void beginGame(Player player) {
		if (isHunter(player)) {
			checkGameSetupProgress();
			if (this.gameSetupComplete) {
				player.sendMessage(mrPrefix + "Starting game.");
				createGameTimer();
			} else {
				if (hunterSpawn == null) {
					player.sendMessage(mrPrefix + "You have not yet set your spawn location.");
				}
				if (huntedSpawn == null) {
					player.sendMessage(mrPrefix + "You have not yet set the spawn location for your victims.");
				}
				if (jailLocation == null) {
					player.sendMessage(mrPrefix + "You have not yet set the jail location.");
				}
				if (getTeamHunted().getSize() == 0) {
					player.sendMessage(mrPrefix + "No one has joined team 'Hunted' yet.");
				}
			}
		}
	}
	
	public void createGameTimer() {
		gameBar = Bukkit.createBossBar(new NamespacedKey(this, "murderrun"), (ChatColor.GOLD + "Game Time-Limit"), BarColor.WHITE, BarStyle.SEGMENTED_10);
		gameBar.addPlayer(theHunter);
		huntedList.forEach(player -> {
			gameBar.addPlayer(player);
		});
		
		gameTimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			int count = -1;
			double progress = 1.0;
			double time = 1.0 / (900); // seconds

			public void run() {
				if (progress > 0.0) {
					gameBar.setProgress(progress);
				}
				
				switch(count) {
				case -1:
					break;
				case 0:					
					huntedList.forEach(h -> {
						h.sendMessage("The Hunter ran out of time! You Win!");
					});		
					theHunter.sendMessage("You have run out of time. You lose.");
					stopGame();
					break;
				}
				
				if (progress >= 0.0) {
					progress -= time;
				}
				if (progress <= 0.0) {
					count ++;
				}
			}
		}, 0, 20);
		
		gameBar.setVisible(true);
		this.gameHasBegun = true;
	}
	
	public boolean gameHasBegun() {
		return gameHasBegun;
	}
	
	private void unregisterTeam(String teamName) {
		Team team = Bukkit.getServer().getScoreboardManager().getNewScoreboard().getTeam(teamName);
		if (team != null) {
			team.unregister();
		}
	}
	
	public void setGameProgress(Boolean progress) {
		gameInProgress = progress;
	}
	
	public void addHunted(Player player) {
		if (huntedList != null) {
			huntedList.add(player);
		}
	}
	
	public void addDeath(Player player) {
		if (deathList != null) {
			deathList.add(player);
		}
	}
	
	public List<Player> getHuntedList() {
		return huntedList;
	}
	
	public List<Player> getDeathList() {
		return deathList;
	}
	
	public void removeHuntedPlayer(Player player) {
		if (huntedList.contains(player)) {
			huntedList.remove(player);
		}
	}
	
	public boolean trapsExist() {
		return traps != null ? traps.size() > 0 : false;
	}
	
	public boolean cruxesExist() {
		return horcruxes != null ? horcruxes.size() > 0 : false;
	}
	
	public String getMRPrefix() {
		return mrPrefix;
	}
	
	public boolean isGameSetupComplete() {
		return gameSetupComplete;
	}
	
	public boolean isPlaying(Player player) {
		return isHunter(player) || isHunted(player);
	}
	
	public void clearPlayerLists() {
		huntedList.forEach(player -> {
			player.removeScoreboardTag("hunted");
		});
	}
	
	public void removeHorcrux(Location loc, UUID uuid) {
		if (horcruxes.containsValue(loc)) {
			horcruxes.remove(uuid);
		}
		if (horcruxList.containsKey(uuid)) {
			horcruxList.get(uuid).remove();
			horcruxList.remove(uuid);
		}
	}
	
	public void destroyHorcrux(Location loc) {
		horcruxes.keySet().forEach(key -> {
			if (horcruxes.get(key) == loc) {
				horcruxList.get(key).remove();
				horcruxList.remove(key);
				horcruxes.remove(key);
			}
		});		
	}
	
	public void addHorcrux(ArmorStand crux, Location loc, UUID uuid) {
		horcruxList.put(uuid, crux);
		horcruxes.put(uuid, loc);
	}
	
	public HashMap<UUID, Location> getCruxes() {
		return horcruxes;
	}
	
	public void stopTimer() {
		if (gameTimer != null) {
			Bukkit.getScheduler().cancelTask(gameTimer);
		}
	}
	
	public Scoreboard getScoreboard() {
		return scoreboard;
	}
	
	public void setScoreboard(Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
	}
	
	public void stopGame() {
		if (huntedList != null) {
			huntedList.forEach(h -> {
				h.getInventory().clear();
				if (h.getScoreboardTags().contains("ghosted")) {
					h.removeScoreboardTag("ghosted");
					h.removePotionEffect(PotionEffectType.INVISIBILITY);
				}
				if (h.getScoreboardTags().contains("gameover")) {
					h.removeScoreboardTag("gameover");
				}
			});		
		}
	
		theHunter.getInventory().clear();
		
		clearPlayerLists();
		theHunter.removeScoreboardTag("hunter");
		if (lifeMap != null) {
			lifeMap.clear();
		}
		if (traps != null) {
			traps.clear();
		}
		if (horcruxes != null) {
			horcruxes.clear();
		}
		
		if (!horcruxList.isEmpty()) {
			horcruxList.keySet().forEach(key -> {
				horcruxList.get(key).remove();
				horcruxList.remove(key);
			});		
		}
		
		if (gameBar != null) {
			gameBar.removeAll();
			gameBar = null;
		}
		this.gameInProgress = false;
		money = null;
		deathList = null;
		theHunter = null;
		huntedList = null;
		hunterSpawn = null;
		huntedSpawn = null;
		jailLocation = null;
		this.gameSetupComplete = false;
		this.gameHasBegun = false;
		unregisterTeam("hunted");
		unregisterTeam("hunter");
		
		Bukkit.getOnlinePlayers().forEach(p -> {
			p.setScoreboard(scoreboard);
		});
		
		stopTimer();
	}
}
