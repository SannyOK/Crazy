package de.st_ddt.crazyutil.paramitrisable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.st_ddt.crazyplugin.exceptions.CrazyCommandNoSuchException;
import de.st_ddt.crazyplugin.exceptions.CrazyCommandParameterException;
import de.st_ddt.crazyplugin.exceptions.CrazyException;
import de.st_ddt.crazyutil.ChatConverter;

public class LocationParamitrisable extends TypedParamitrisable<Location>
{

	protected final static Pattern PATTERN_SPACE = Pattern.compile(" ");
	protected final CommandSender sender;

	public LocationParamitrisable(final CommandSender sender)
	{
		super(sender instanceof Player ? ((Player) sender).getLocation() : new Location(null, 0, 0, 0));
		this.sender = sender;
	}

	public LocationParamitrisable(final Location defaultValue, final CommandSender sender)
	{
		super(defaultValue == null ? new Location(null, 0, 0, 0) : defaultValue);
		this.sender = sender;
	}

	@Override
	public void setParameter(final String parameter) throws CrazyException
	{
		value = ChatConverter.stringToLocation(sender, PATTERN_SPACE.split(parameter));
	}

	public void addFullParams(final Map<String, TabbedParamitrisable> params, final String... prefixes)
	{
		for (final String prefix : prefixes)
			params.put(prefix, this);
		addAdvancedParams(params, prefixes);
	}

	public void addAdvancedParams(final Map<String, TabbedParamitrisable> params, final String... prefixes)
	{
		final TabbedParamitrisable xParam = new TabbedParamitrisable()
		{

			@Override
			public void setParameter(final String parameter) throws CrazyException
			{
				try
				{
					value.setX(Double.parseDouble(parameter));
				}
				catch (final NumberFormatException e)
				{
					throw new CrazyCommandParameterException(0, "Number (Double)");
				}
			}

			@Override
			public List<String> tab(final String parameter)
			{
				return new LinkedList<String>();
			}
		};
		final TabbedParamitrisable yParam = new TabbedParamitrisable()
		{

			@Override
			public void setParameter(final String parameter) throws CrazyException
			{
				try
				{
					value.setY(Double.parseDouble(parameter));
				}
				catch (final NumberFormatException e)
				{
					throw new CrazyCommandParameterException(0, "Number (Double)");
				}
			}

			@Override
			public List<String> tab(final String parameter)
			{
				return new LinkedList<String>();
			}
		};
		final TabbedParamitrisable zParam = new TabbedParamitrisable()
		{

			@Override
			public void setParameter(final String parameter) throws CrazyException
			{
				try
				{
					value.setZ(Double.parseDouble(parameter));
				}
				catch (final NumberFormatException e)
				{
					throw new CrazyCommandParameterException(0, "Number (Double)");
				}
			}

			@Override
			public List<String> tab(final String parameter)
			{
				return new LinkedList<String>();
			}
		};
		final TabbedParamitrisable worldParam = new TabbedParamitrisable()
		{

			@Override
			public void setParameter(final String parameter) throws CrazyException
			{
				value.setWorld(Bukkit.getWorld(parameter));
				if (value == null)
					throw new CrazyCommandNoSuchException("World", parameter, getWorldNames());
			}

			@Override
			public List<String> tab(final String parameter)
			{
				final List<String> res = new LinkedList<String>();
				for (final World world : Bukkit.getWorlds())
					if (world.getName().startsWith(parameter))
						res.add(world.getName());
				return res;
			}
		};
		for (final String prefix : prefixes)
		{
			params.put(prefix + "x", xParam);
			params.put(prefix + "y", yParam);
			params.put(prefix + "z", zParam);
			params.put(prefix + "world", worldParam);
		}
	}

	private String[] getWorldNames()
	{
		final List<World> worlds = Bukkit.getWorlds();
		final int length = worlds.size();
		final String[] res = new String[length];
		for (int i = 0; i < length; i++)
			res[i] = worlds.get(i).getName();
		return res;
	}
}
