package org.apache.maven.plugins.semver.goals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.semver.SemverMavenPlugin;
import org.apache.maven.plugins.semver.exceptions.SemverException;
import org.apache.maven.plugins.semver.providers.RepositoryProvider;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.RefSpec;

/**
 *
 * <p>used to be a goal that was used before a build on a BUILD-server (HUDSON).</p>
 * <p>Can be phased out when the BUILD-server jobs are obsolete.</p>
 *
 * @author sido
 * @deprecated
 */
@Deprecated
@Mojo(name = "cleanup-git-tags")
public class SemverMavenPluginGoalCleanupGitTags extends SemverMavenPlugin {

  /**
   *
   * @throws MojoExecutionException
   * @throws MojoFailureException
   */
  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {

    initializeProviders();

    String version = project.getVersion();
    String scmConnection = project.getScm().getConnection();
    File scmRoot = project.getBasedir();

    LOG.info("Semver-goal                       : CLEANUP-GIT-TAGS");
    LOG.info("Run-mode                          : " + getConfiguration().getRunMode());
    LOG.info("Version from POM                  : " + version);
    LOG.info("SCM-connection                    : " + scmConnection);
    LOG.info("SCM-root                          : " + scmRoot);
    LOG.info(FUNCTION_LINE_BREAK);
    
    try {
      cleanupGitRemoteTags(scmConnection, scmRoot);
    } catch (IOException e) {
      LOG.error("Error when determining config", e);
    } catch (GitAPIException e) {
      LOG.error("Error when determining GIT-repo", e);
    }
  }

  /**
   *
   * <p>Cleanup lost GIT-tags before making a release on BUILD-server (for example HUDSON)</p>
   *
   * @param scmConnection
   * @param scmRoot
   * @throws IOException
   * @throws GitAPIException
   */
  private void cleanupGitRemoteTags(String scmConnection, File scmRoot) throws IOException, GitAPIException {
    LOG.info("Determine local and remote GIT-tags for GIT-repo");
    LOG.info(MOJO_LINE_BREAK);
    getRepositoryProvider().pull();
    List<Ref> refs = getRepositoryProvider().getTags();
    if(refs.isEmpty()) {
      boolean found = false;
      for (Ref ref : refs) {
        if(ref.getName().contains(preparedReleaseTag)) {
          found = true;
          LOG.info("Delete local GIT-tag                 : " + ref.getName().substring(10));
          getRepositoryProvider().deleteTag(ref.getName());
          LOG.info("Delete remote GIT-tag                : " + ref.getName().substring(10));
          getRepositoryProvider().pushTag(ref.getName());
        } 
      }
      if (!found) {
        LOG.info("No local or remote prepared GIT-tags found");
      }
    } else {
      LOG.info("No local or remote prepared GIT-tags found");
    }
    
    getRepositoryProvider().closeRepository();
    LOG.info(MOJO_LINE_BREAK);
    
  }

}
