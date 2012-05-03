package com.sirdrakeheart.banker;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

import net.citizensnpcs.properties.Node;
import net.citizensnpcs.properties.Properties;
import net.citizensnpcs.properties.PropertyManager;
import net.citizensnpcs.resources.npclib.HumanNPC;

public class BankerProperties extends PropertyManager implements Properties {
	private static final String isBanker = ".banker.toggle";
	public static final BankerProperties INSTANCE = new BankerProperties();
	
	private BankerProperties() {

	}

	@Override
	public List<Node> getNodes() {
		return null;
	}

	@Override
	public Collection<String> getNodesForCopy() {
		return nodesForCopy;
	}
	
	private static final List<String> nodesForCopy = Lists.newArrayList(
			"banker.toggle");
	
	@Override
	public void saveState(HumanNPC npc) {
		if (exists(npc)) {
			setEnabled(npc, npc.isType("banker"));
			BankerNPC banker = npc.getType("banker");
			banker.save(profiles, npc.getUID());
		}
	}

	@Override
	public void loadState(HumanNPC npc) {
		if (isEnabled(npc)) {
			if (!npc.isType("banker"))
				npc.registerType("banker");
			BankerNPC banker = npc.getType("banker");
			banker.load(profiles, npc.getUID());
		}
		saveState(npc);
	}

	@Override
	public void setEnabled(HumanNPC npc, boolean value) {
		profiles.setBoolean(npc.getUID() + isBanker, value);
	}
	
	@Override
	public boolean isEnabled(HumanNPC npc) {
		return profiles.getBoolean(npc.getUID() + isBanker);
	}
}
