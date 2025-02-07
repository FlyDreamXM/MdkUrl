package io.github.flydreamxm.mdkurl;

public final class UrlPair
{
	public final String name;
	public final String url;

	public UrlPair(String name, String url)
	{
		this.name = name;
		this.url = url;
	}

	public String name()
	{
		return this.name;
	}

	public String url()
	{
		return this.url;
	}
}
