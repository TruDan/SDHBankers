package com.sirdrakeheart.banker;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;

import net.minecraft.server.InventoryLargeChest;
import net.minecraft.server.TileEntityChest;

import org.bukkit.craftbukkit.inventory.CraftInventoryDoubleChest;

import me.ellbristow.ChestBank.ChestBank;

import net.citizensnpcs.Citizens;
import net.citizensnpcs.npctypes.CitizensNPC;
import net.citizensnpcs.npctypes.CitizensNPCType;
import net.citizensnpcs.properties.Storage;
import net.citizensnpcs.resources.npclib.HumanNPC;

import com.sirdrakeheart.plugin.SDHPlayer;
import com.sirdrakeheart.plugin.SDHPlayers;

import com.sk89q.worldedit.Vector;

public class BankerNPC extends CitizensNPC {
	
	public Integer storageBlock_x,storageBlock_y,storageBlock_z = 0;

	@Override
	public CitizensNPCType getType() {
		return new BankerType();
	}

	@Override
	public void load(Storage profiles, int UID) {
		Integer x = profiles.getInt(UID + ".banker.storageBlock.x");
		Integer y = profiles.getInt(UID + ".banker.storageBlock.y");
		Integer z = profiles.getInt(UID + ".banker.storageBlock.z");
		this.storageBlock_x = x;
		this.storageBlock_y = y;
		this.storageBlock_z = z;
	}

	@Override
	public void save(Storage profiles, int UID) {
		profiles.setInt(UID + ".banker.storageBlock.x", this.storageBlock_x);
		profiles.setInt(UID + ".banker.storageBlock.y", this.storageBlock_y);
		profiles.setInt(UID + ".banker.storageBlock.z", this.storageBlock_z);
	}
	
	@Override
    public void onRightClick(Player player, HumanNPC npc) {  
		SDHPlayer sdhplayer = SDHPlayers.getPlayer(player.getName());
		if(sdhplayer.chestBankBlockSelectionMode == true) {
			Vector block = sdhplayer.getChestBankBlock();
			this.storageBlock_x = block.getBlockX();
			this.storageBlock_y = block.getBlockY();
			this.storageBlock_z = block.getBlockZ();
			sdhplayer.setChestBankBlockSelectionMode(false);
			player.sendMessage(ChatColor.GREEN+"Banker ChestBank set.");
		}
		else {
			player.sendMessage("Test");
			ChestBank plugin = (ChestBank) Citizens.plugin.getServer().getPluginManager().getPlugin("ChestBank");
	        boolean allowed = true;
	        Block block = player.getWorld().getBlockAt(this.storageBlock_x, this.storageBlock_y, this.storageBlock_z);
	        String network = plugin.getNetwork(block);
	        if (plugin.useNetworkPerms == true && (!player.hasPermission("chestbank.use.networks." + network.toLowerCase()) && !player.hasPermission("chestbank.use.networks.*")))
	        {
	            player.sendMessage(ChatColor.RED + "You are not allowed to use ChestBanks on the " + ChatColor.WHITE + network + ChatColor.RED + " network!");
	            allowed = false;
	        } else if (!plugin.useNetworkPerms && !player.hasPermission("chestbank.use.networks")) {
	            player.sendMessage(ChatColor.RED + "You are not allowed to use ChestBanks on named networks!");
	            allowed = false;
	        } else if (plugin.gotVault && plugin.gotEconomy && plugin.useFee != 0 && !player.hasPermission("chestbank.free.use.networks")) {
	            if (plugin.vault.economy.getBalance(player.getName()) < plugin.useFee) {
	                player.sendMessage(ChatColor.RED + "You cannot afford the transaction fee of " + ChatColor.WHITE + plugin.vault.economy.format(plugin.useFee) + ChatColor.RED + "!");
	                allowed = false;
	            }
	        }
	        if (allowed) {
	            DoubleChestInventory inv = plugin.chestAccounts.get(network + ">>" + player.getName());
	            if (inv != null && inv.getContents().length != 0) {
	                plugin.openInvs.put(player.getName(), network);
	                player.openInventory(inv);
	            } else {
	                inv = new CraftInventoryDoubleChest(new InventoryLargeChest(player.getName(), new TileEntityChest(), new TileEntityChest()));
	                plugin.chestAccounts.put(network + ">>" + player.getName(), inv);
	                plugin.setAccounts(plugin.chestAccounts);
	                plugin.openInvs.put(player.getName(), network);
	                player.openInventory(inv);
	            }
	        }
		}
	}

}
