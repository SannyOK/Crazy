package de.st_ddt.crazyarena;

import de.st_ddt.crazyarena.arenas.race.RaceArena;

public class CrazyArenaRace extends CrazyArenaPlugin
{

	@Override
	protected void registerArenaTypes()
	{
		registerArenaType("Race", RaceArena.class);
	}

	@Override
	public String[] getArenaTypes()
	{
		return new String[] { "Race" };
	}
}
