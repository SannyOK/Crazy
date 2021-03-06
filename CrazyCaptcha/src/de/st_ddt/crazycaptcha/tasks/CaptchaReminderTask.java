package de.st_ddt.crazycaptcha.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.st_ddt.crazycaptcha.CrazyCaptcha;
import de.st_ddt.crazyutil.locales.Localized;

public class CaptchaReminderTask implements Runnable
{

	private final Player player;
	private final CrazyCaptcha plugin;
	private int taskID = -1;

	public CaptchaReminderTask(Player player, CrazyCaptcha plugin)
	{
		super();
		this.player = player;
		this.plugin = plugin;
	}

	@Override
	@Localized("CRAZYCAPTCHA.VERIFICATION.REQUEST $Captcha$")
	public void run()
	{
		if (!player.isOnline())
		{
			cancelTask();
			return;
		}
		if (plugin.isVerified(player))
		{
			cancelTask();
			return;
		}
		String captcha = plugin.getCaptchas().get(player.getName().toLowerCase());
		if (captcha == null)
		{
			cancelTask();
			return;
		}
		plugin.sendLocaleMessage("VERIFICATION.REQUEST", player, captcha);
	}

	public void startTask(long delay)
	{
		delay *= 20;
		if (taskID == -1)
			taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, delay, delay);
	}

	public void cancelTask()
	{
		if (taskID > -1)
		{
			Bukkit.getScheduler().cancelTask(taskID);
			taskID = -1;
		}
	}
}
