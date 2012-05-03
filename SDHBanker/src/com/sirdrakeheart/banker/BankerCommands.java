package com.sirdrakeheart.banker;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.sirdrakeheart.plugin.SDHPlayer;
import com.sirdrakeheart.plugin.SDHPlayers;

import net.citizensnpcs.commands.CommandHandler;
import net.citizensnpcs.resources.npclib.HumanNPC;
import net.citizensnpcs.resources.sk89q.Command;
import net.citizensnpcs.resources.sk89q.CommandContext;
import net.citizensnpcs.resources.sk89q.CommandPermissions;
import net.citizensnpcs.resources.sk89q.CommandRequirements;

@CommandRequirements(
		requireSelected = true,
		requireOwnership = true,
		requireEconomy = true,
		requiredType = "banker")
public class BankerCommands extends CommandHandler {
	public static final BankerCommands INSTANCE = new BankerCommands();
	
	@Override
	public void addPermissions() {
		// TODO Auto-generated method stub
		
	}
	
	@CommandRequirements(
			requireEconomy = false,
			requireSelected = true,
			requiredType = "banker")
	@Command(
			aliases = "banker",
			usage = "banker setblock",
			desc = "set the ChestBank block of banker",
			modifiers = "setblock",
			min = 1,
			max = 1)
	@CommandPermissions("banker.setup")
	public static void settype(CommandContext args, Player player, HumanNPC npc) {
		SDHPlayer sdhplayer = SDHPlayers.getPlayer(player.getName());
		
		sdhplayer.setChestBankBlockSelectionMode(true);
		player.sendMessage(ChatColor.GRAY+"Please click the ChestBank block you wish to link this banker to.");
	}

}
