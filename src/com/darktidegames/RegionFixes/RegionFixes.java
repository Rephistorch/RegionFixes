package com.darktidegames.RegionFixes;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RegionFixes extends JavaPlugin implements Listener
{
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onRespawn(PlayerRespawnEvent evt)
	{
		if (evt.isBedSpawn())
		{
			Location loc = evt.getPlayer().getBedSpawnLocation();
			Block b = loc.getBlock();
			Location newLoc = null;
			
			evt.getPlayer().sendMessage(evt.getPlayer().getBedSpawnLocation().toString());
			//Are any of the 8 blocks that the player normally spawns in occupied?
			//Player WILL spawn upwards if any of these blocks are obstructed
			if (!b.getRelative(0, 1, 0).isEmpty()
					|| !b.getRelative(0, 2, 0).isEmpty()
					|| !b.getRelative(0, 1, -1).isEmpty()
					|| !b.getRelative(0, 2, -1).isEmpty()
					|| !b.getRelative(-1, 1, -1).isEmpty()
					|| !b.getRelative(-1, 2, -1).isEmpty()
					|| !b.getRelative(-1, 1, 0).isEmpty()
					|| !b.getRelative(-1, 2, 0).isEmpty()
					)
			{
				//Are the 2 blocks straight above the bed air?
				if (b.getRelative(0, 1, 0).isEmpty()
						&& b.getRelative(0, 2, 0).isEmpty()
						)
				{
					newLoc = b.getRelative(0, 1, 0).getLocation();
				} else 
					//How does middle north look?
					if (b.getRelative(0, 1, -1).isEmpty()
					// Are either of the blocks above or below air?
						&& (b.getRelative(0, 0, -1).isEmpty()
							|| b.getRelative(0, 2, -1).isEmpty()
						))
				{
					newLoc = b.getRelative(0, b.getRelative(0, 0, -1).isEmpty() ? 0 : 1, -1).getLocation();
				} else  
				//How does middle west look?
					if (b.getRelative(-1, 1, 0).isEmpty()
						// Are either of the blocks above or below air?
						&& (b.getRelative(-1, 0, 0).isEmpty()
								|| b.getRelative(-1, 2, 0).isEmpty()
							))
				{
					newLoc = b.getRelative(-1, b.getRelative(-1, 0, 0).isEmpty() ? 0 : 1, 0).getLocation();
				} else  
					//How does middle south look?
					if (b.getRelative(0, 1, 1).isEmpty()
					// Are either of the blocks above or below air?
							&& (b.getRelative(0, 0, 1).isEmpty()
							|| b.getRelative(0, 2, 1).isEmpty()
						))
				{
					newLoc = b.getRelative(0, b.getRelative(0, 0, 1).isEmpty() ? 0 : 1, 1).getLocation();
				} else  
					//How does middle east look?
					if (b.getRelative(1, 1, 0).isEmpty()
					// Are either of the blocks above or below air?
							&& (b.getRelative(1, 0, 0).isEmpty()
							|| b.getRelative(1, 2, 0).isEmpty()
						))
				{
					newLoc = b.getRelative(1, b.getRelative(1, 0, 0).isEmpty() ? 0 : 1, 0).getLocation();
				} else {
					//No suitable spawn location found, player WILL glitch up
					evt.getPlayer().sendMessage("Your bed is obstructed!");
					newLoc = loc.getWorld().getSpawnLocation();
				}				
			}
			else
			{
				//evt.getPlayer().sendMessage("unobstructed spawn!");			
			}
			
			if (newLoc != null)
			{
				//evt.getPlayer().sendMessage("spawn assist engaged!");
				//evt.getPlayer().sendMessage(newLoc.toString());
				evt.setRespawnLocation(newLoc.add(.5, 0, .5));
			}
		}		
	}
}