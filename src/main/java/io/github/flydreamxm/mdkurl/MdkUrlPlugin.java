package io.github.flydreamxm.mdkurl;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MdkUrlPlugin implements Plugin<Project>
{
	@Override
	public void apply(Project project)
	{
		project.getExtensions().create("MdkUrl", MdkUrlExtension.class, project);
	}
}
