package io.github.flydreamxm.mdkurl;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.ArtifactRepository;
import org.gradle.api.internal.artifacts.repositories.DefaultMavenArtifactRepository;

import java.net.URI;
import java.util.Map;

public class MdkUrlExtension
{
	private final RepositoryHandler repositories;

	public final UrlPair MIRRORS_BMCLAPI = new UrlPair("BMCLAPI", "https://bmclapi2.bangbang93.com/maven/");
	public final UrlPair MIRRORS_Lss233 = new UrlPair("Lss233", "https://lss233.littleservice.cn/repositories/minecraft/");
	public final UrlPair MOJANG_MAVEN = new UrlPair("Mojang Minecraft Libraries", "https://libraries.minecraft.net/");
	public final UrlPair NEOFORGE_MAVEN = new UrlPair("NeoForged Releases", "https://maven.neoforged.net/releases/");
	public final UrlPair FORGE_MAVEN = new UrlPair("Forge Maven", "https://maven.minecraftforge.net/");

	public MdkUrlExtension(Project project)
	{
		repositories = project.getRepositories();
	}

	public RepositoryHandler getRepositories()
	{
		return repositories;
	}

	public void repositories(Action<? super RepositoryHandler> configure)
	{
		configure.execute(repositories);
	}

	public void url(UrlPair pair)
	{
		url(pair.name(), pair.url());
	}

	public void url(String name, String url)
	{
		repositories.maven(repo -> {
			repo.setName(name);
			repo.setUrl(url);
		});
	}

	public void urls(String... urls)
	{
		for (String url : urls)
		{
			repositories.maven(repo -> {
				repo.setUrl(url);
			});
		}
	}

	public void urls(Map<String, String> map)
	{
		for (Map.Entry<String, String> entry : map.entrySet())
		{
			url(entry.getKey(), entry.getValue());
		}
	}

	public void urls(UrlPair[] pairs)
	{
		for (UrlPair pair : pairs)
		{
			url(pair);
		}
	}

	public void reset(UrlPair pair)
	{
		ArtifactRepository target = null;
		if (repositories.getNames().contains(pair.name()))
		{
			target = repositories.getByName(pair.name());
		}
		else
		{
			int index = -1;
			for (int i = 0; i < repositories.size(); ++i)
			{
				ArtifactRepository entry = repositories.get(i);
				if (repositories.get(i) instanceof DefaultMavenArtifactRepository)
				{
					DefaultMavenArtifactRepository repo = (DefaultMavenArtifactRepository)entry;
					if (repo.getUrl().equals(URI.create(pair.url())))
					{
						index = i;
						break;
					}
				}
			}
			if (index != -1)
			{
				target = repositories.get(index);
			}
		}
		if (target != null)
		{
			repositories.remove(target);
			repositories.addLast(target);
		}
	}

	public void commonSet()
	{
		url(MIRRORS_Lss233);
		url(MIRRORS_BMCLAPI);
	}

	public void commonReset()
	{
		reset(MOJANG_MAVEN);
		reset(NEOFORGE_MAVEN);
		reset(FORGE_MAVEN);
	}
}
