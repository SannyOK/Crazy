package de.st_ddt.crazyarena.exceptions;

import org.bukkit.command.CommandSender;

import de.st_ddt.crazyarena.arenas.Arena;
import de.st_ddt.crazyplugin.exceptions.CrazyException;

public class CrazyArenaException extends CrazyException
{

	private static final long serialVersionUID = -5638998288487141423L;
	protected final Arena<?> arena;

	public CrazyArenaException(final Arena<?> arena)
	{
		super();
		this.arena = arena;
	}

	public final Arena<?> getArena()
	{
		return arena;
	}

	@Override
	public String getLangPath()
	{
		return "CRAZYARENA.ARENA" + arena.getName() + ".EXCEPTION";
	}

	@Override
	public void print(final CommandSender sender, final String header)
	{
		sender.sendMessage(header + locale.getLocaleMessage(sender, "HEAD", arena.getName()));
		if (printStackTrace)
			printStackTrace();
	}
}
