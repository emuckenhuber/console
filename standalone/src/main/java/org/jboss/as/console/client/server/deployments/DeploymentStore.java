package org.jboss.as.console.client.server.deployments;

/**
 * Responsible for loading deployment data
 * and turning it a usable representation.
 *
 * @author Heiko Braun
 * @date 1/31/11
 */
public interface DeploymentStore {
    DeploymentRecord[] loadDeployments();
}
