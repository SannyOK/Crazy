package de.st_ddt.crazysquads.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.st_ddt.crazyplugin.exceptions.CrazyCommandCircumstanceException;
import de.st_ddt.crazyplugin.exceptions.CrazyCommandNoSuchException;
import de.st_ddt.crazyplugin.exceptions.CrazyCommandUsageException;
import de.st_ddt.crazyplugin.exceptions.CrazyException;
import de.st_ddt.crazysquads.CrazySquads;
import de.st_ddt.crazysquads.data.Squad;
import de.st_ddt.crazysquads.events.CrazySquadsSquadInviteEvent;
import de.st_ddt.crazyutil.locales.Localized;
import de.st_ddt.crazyutil.paramitrisable.PlayerParamitrisable;

public class CrazySquadsSquadPlayerCommandSquadInvite extends CrazySquadsSquadPlayerCommandExecutor
{

	public CrazySquadsSquadPlayerCommandSquadInvite(final CrazySquads plugin)
	{
		super(plugin);
	}

	@Override
	@Localized({ "CRAZYSQUADS.COMMAND.SQUAD.INVITE.CANCELLED $Invited$ $Reason$", "CRAZYSQUADS.COMMAND.SQUAD.INVITED.ALREADY $Invited$", "CRAZYSQUADS.SQUAD.INVITED $Owner$", "CRAZYSQUADS.COMMAND.SQUAD.INVITED $Invited$" })
	public void command(final Player player, final Squad squad, final String[] args) throws CrazyException
	{
		if (args.length != 1)
			throw new CrazyCommandUsageException("<Player>");
		final String name = args[0];
		final Player invited = Bukkit.getPlayerExact(name);
		if (invited == null)
			throw new CrazyCommandNoSuchException("Player", name);
		if (squad.getMembers().contains(invited))
			throw new CrazyCommandCircumstanceException("when invited is not already member of the squad!");
		final CrazySquadsSquadInviteEvent event = new CrazySquadsSquadInviteEvent(plugin, squad, invited);
		event.callEvent();
		if (event.isCancelled())
			plugin.sendLocaleMessage("COMMAND.SQUAD.INVITE.CANCELLED", player, squad.getName(), event.getReason());
		else if (plugin.getInvites().put(invited, squad) == squad)
			plugin.sendLocaleMessage("COMMAND.SQUAD.INVITED.ALREADY", player, invited.getName());
		else
		{
			plugin.sendLocaleMessage("SQUAD.INVITED", invited, player.getName());
			plugin.sendLocaleMessage("COMMAND.SQUAD.INVITED", player, invited.getName());
		}
	}

	@Override
	public List<String> tab(final Player player, final Squad squad, final String[] args)
	{
		if (args.length != 1)
			return null;
		return PlayerParamitrisable.tabHelp(args[0]);
	}

	@Override
	public boolean hasAccessPermission(final Player player, final Squad squad)
	{
		return squad.getOwner() == player;
	}
}
