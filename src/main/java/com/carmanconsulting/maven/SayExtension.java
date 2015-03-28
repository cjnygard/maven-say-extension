package com.carmanconsulting.maven;

import org.apache.commons.lang3.SystemUtils;
import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;

import java.io.IOException;

@Component(role = AbstractMavenLifecycleParticipant.class)
public class SayExtension extends AbstractMavenLifecycleParticipant {
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Override
    public void afterSessionEnd(MavenSession session) throws MavenExecutionException {
        final MavenExecutionResult result = session.getResult();
        final MavenProject project = session.getTopLevelProject();
        if (SystemUtils.IS_OS_MAC_OSX) {
            if (!result.getExceptions().isEmpty()) {
                try {
                    Runtime.getRuntime().exec(String.format("say '%s build failed'", project.getName()));
                } catch (IOException e) {
                    throw new MavenExecutionException("Unable to say stuff!", e);
                }
            } else {
                try {
                    Runtime.getRuntime().exec(String.format("say '%s build succeeded!'", project.getName()));
                } catch (IOException e) {
                    throw new MavenExecutionException("Unable to say stuff!", e);
                }
            }
        }
    }
}
