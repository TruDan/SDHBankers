package com.sirdrakeheart.banker;

import net.citizensnpcs.commands.CommandHandler;
import net.citizensnpcs.npctypes.CitizensNPC;
import net.citizensnpcs.npctypes.CitizensNPCType;
import net.citizensnpcs.properties.Properties;


public class BankerType extends CitizensNPCType {
	
	@Override
	public CommandHandler getCommands() {
		return BankerCommands.INSTANCE;
	}

	@Override
	public CitizensNPC getInstance() {
		return new BankerNPC();
	}

	@Override
	public String getName() {
		return "banker";
	}

	@Override
	public Properties getProperties() {
		return BankerProperties.INSTANCE;
	}
}
