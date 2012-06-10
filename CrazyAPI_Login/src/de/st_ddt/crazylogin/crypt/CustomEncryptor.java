package de.st_ddt.crazylogin.crypt;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.bukkit.configuration.ConfigurationSection;

import de.st_ddt.crazylogin.LoginPlugin;

public abstract class CustomEncryptor implements Encryptor
{

	public CustomEncryptor(LoginPlugin plugin, ConfigurationSection config)
	{
		super();
	}

	@Override
	public abstract String encrypt(String name, String salt, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;

	@Override
	public abstract boolean match(String name, String password, String encrypted);

	@Override
	public String getAlgorithm()
	{
		return "Custom";
	}
}
