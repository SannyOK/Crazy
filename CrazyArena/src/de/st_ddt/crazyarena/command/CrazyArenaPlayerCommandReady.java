package de.st_ddt.crazyarena.command;

import org.bukkit.entity.Player;

import de.st_ddt.crazyarena.CrazyArena;
import de.st_ddt.crazyarena.arenas.Arena;
import de.st_ddt.crazyarena.participants.ParticipantType;
import de.st_ddt.crazyplugin.exceptions.CrazyCommandCircumstanceException;
import de.st_ddt.crazyplugin.exceptions.CrazyCommandUsageException;
import de.st_ddt.crazyplugin.exceptions.CrazyException;

public class CrazyArenaPlayerCommandReady extends CrazyArenaPlayerCommandExecutor
{

	public CrazyArenaPlayerCommandReady(final CrazyArena plugin)
	{
		super(plugin);
	}

	@Override
	public void command(final Player player, final String[] args) throws CrazyException
	{
		if (args.length != 0)
			throw new CrazyCommandUsageException("");
		final Arena<?> arena = plugin.getArena(player);
		if (arena == null || !arena.isParticipant(player, ParticipantType.SELECTING))
			throw new CrazyCommandCircumstanceException("when waiting inside an arena!");
		arena.ready(player);
	}
}
