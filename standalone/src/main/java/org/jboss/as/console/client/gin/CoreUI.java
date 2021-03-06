package org.jboss.as.console.client.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.annotations.DefaultGatekeeper;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;
import org.jboss.as.console.client.Footer;
import org.jboss.as.console.client.Header;
import org.jboss.as.console.client.MainLayoutPresenter;
import org.jboss.as.console.client.auth.CurrentUser;
import org.jboss.as.console.client.auth.SignInPagePresenter;
import org.jboss.as.console.client.server.ServerMgmtApplicationPresenter;
import org.jboss.as.console.client.server.deployments.DeploymentStore;
import org.jboss.as.console.client.server.deployments.DeploymentToolPresenter;
import org.jboss.as.console.client.server.interfaces.InterfaceToolPresenter;
import org.jboss.as.console.client.server.path.PathToolPresenter;
import org.jboss.as.console.client.server.properties.PropertyToolPresenter;
import org.jboss.as.console.client.server.sockets.SocketToolPresenter;
import org.jboss.as.console.client.server.subsys.SubsystemToolPresenter;
import org.jboss.as.console.client.system.SystemApplicationPresenter;
import org.jboss.as.console.client.util.message.MessageBar;
import org.jboss.as.console.client.util.message.MessageCenter;
import org.jboss.as.console.client.util.message.MessageCenterView;

/**
 * @author Heiko Braun
 * @date 1/31/11
 */
@GinModules(CoreUIModule.class)
public interface CoreUI extends Ginjector {


    PlaceManager getPlaceManager();
    EventBus getEventBus();
    ProxyFailureHandler getProxyFailureHandler();

    @DefaultGatekeeper
    Gatekeeper getLoggedInGatekeeper();
    CurrentUser getCurrentUser();

    // ----------------------------------------------------------------------

    Header getHeader();
    Footer getFooter();

    MessageBar getMessageBar();
    MessageCenter getMessageCenter();
    MessageCenterView getMessageCenterView();

    // ----------------------------------------------------------------------

    Provider<SignInPagePresenter> getSignInPagePresenter();
    AsyncProvider<MainLayoutPresenter> getMainLayoutPresenter();

    // ----------------------------------------------------------------------
    AsyncProvider<SystemApplicationPresenter> getSystemAppPresenter();

    // ----------------------------------------------------------------------
    AsyncProvider<ServerMgmtApplicationPresenter> getServerManagementAppPresenter();

    AsyncProvider<DeploymentToolPresenter> getDeploymentToolPresenter();
    DeploymentStore getDeploymentStore();

    AsyncProvider<InterfaceToolPresenter> getInterfaceToolPresenter();
    AsyncProvider<PathToolPresenter> getPathToolPresenter();
    AsyncProvider<PropertyToolPresenter> getPropertyToolPresenter();
    AsyncProvider<SocketToolPresenter> getSocketToolPresenter();
    AsyncProvider<SubsystemToolPresenter> getSubsystemToolPresenter();


}
