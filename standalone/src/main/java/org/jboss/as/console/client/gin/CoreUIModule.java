package org.jboss.as.console.client.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.DefaultProxyFailureHandler;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.proxy.*;
import org.jboss.as.console.client.*;
import org.jboss.as.console.client.auth.*;
import org.jboss.as.console.client.server.ServerMgmtApplicationPresenter;
import org.jboss.as.console.client.server.ServerMgmtViewImpl;
import org.jboss.as.console.client.server.deployments.DeploymentStore;
import org.jboss.as.console.client.server.deployments.DeploymentToolPresenter;
import org.jboss.as.console.client.server.deployments.DeploymentToolViewImpl;
import org.jboss.as.console.client.server.deployments.MockDeploymentStoreImpl;
import org.jboss.as.console.client.server.interfaces.InterfaceToolPresenter;
import org.jboss.as.console.client.server.interfaces.InterfaceToolViewImpl;
import org.jboss.as.console.client.server.path.PathToolPresenter;
import org.jboss.as.console.client.server.path.PathToolViewImpl;
import org.jboss.as.console.client.server.properties.PropertyToolPresenter;
import org.jboss.as.console.client.server.properties.PropertyToolViewImpl;
import org.jboss.as.console.client.server.sockets.SocketToolPresenter;
import org.jboss.as.console.client.server.sockets.SocketToolViewImpl;
import org.jboss.as.console.client.server.subsys.SubsystemToolPresenter;
import org.jboss.as.console.client.server.subsys.SubsystemToolViewImpl;
import org.jboss.as.console.client.system.SystemApplicationPresenter;
import org.jboss.as.console.client.system.SystemApplicationViewImpl;
import org.jboss.as.console.client.util.message.MessageBar;
import org.jboss.as.console.client.util.message.MessageCenter;
import org.jboss.as.console.client.util.message.MessageCenterView;

/**
 * @author Heiko Braun
 * @date 1/31/11
 */
public class CoreUIModule extends AbstractPresenterModule {

    protected void configure() {

        // main layout
        bind(Header.class).in(Singleton.class);
        bind(Footer.class).in(Singleton.class);

        // supporting components
        bind(MessageBar.class).in(Singleton.class);
        bind(MessageCenter.class).in(Singleton.class);
        bind(MessageCenterView.class).in(Singleton.class);

        // ----------------------------------------------------------------------

        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        bind(PlaceManager.class).to(DefaultPlaceManager.class).in(Singleton.class);
        bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
        bind(RootPresenter.class).asEagerSingleton();
        bind(ProxyFailureHandler.class).to(DefaultProxyFailureHandler.class).in(Singleton.class);
        bind(Gatekeeper.class).to(LoggedInGatekeeper.class);
        bind(CurrentUser.class).in(Singleton.class);

        // sign in
        bindPresenter(SignInPagePresenter.class, SignInPagePresenter.MyView.class,
                SignInPageView.class, SignInPagePresenter.MyProxy.class);

        // main layout
        bindPresenter(MainLayoutPresenter.class,
                MainLayoutPresenter.MainLayoutView.class,
                MainLayoutViewImpl.class,
                MainLayoutPresenter.MainLayoutProxy.class);

        // ----------------------------------------------------------------------
        // system application
        bindPresenter(SystemApplicationPresenter.class,
                SystemApplicationPresenter.SystemAppView.class,
                SystemApplicationViewImpl.class,
                SystemApplicationPresenter.SystemAppProxy.class);

        // ----------------------------------------------------------------------
        // server management application
        bindPresenter(ServerMgmtApplicationPresenter.class,
                ServerMgmtApplicationPresenter.ServerManagementView.class,
                ServerMgmtViewImpl.class,
                ServerMgmtApplicationPresenter.ServerManagementProxy.class);

        // server deployments
        bindPresenter(DeploymentToolPresenter.class,
                DeploymentToolPresenter.DeploymentToolView.class,
                DeploymentToolViewImpl.class,
                DeploymentToolPresenter.DeploymentToolProxy.class);
        bind(DeploymentStore.class).to(MockDeploymentStoreImpl.class).in(Singleton.class);

        // server/interfaces
        bindPresenter(InterfaceToolPresenter.class,
                InterfaceToolPresenter.MyView.class,
                InterfaceToolViewImpl.class,
                InterfaceToolPresenter.MyProxy.class);
        
        // server/path
        bindPresenter(PathToolPresenter.class,
                PathToolPresenter.MyView.class,
                PathToolViewImpl.class,
                PathToolPresenter.MyProxy.class);
        
        // server/properties
        bindPresenter(PropertyToolPresenter.class,
                PropertyToolPresenter.MyView.class,
                PropertyToolViewImpl.class,
                PropertyToolPresenter.MyProxy.class);
        
        // server/sockets
        bindPresenter(SocketToolPresenter.class,
                SocketToolPresenter.MyView.class,
                SocketToolViewImpl.class,
                SocketToolPresenter.MyProxy.class);
        
        // server/subsystems
        bindPresenter(SubsystemToolPresenter.class,
                SubsystemToolPresenter.MyView.class,
                SubsystemToolViewImpl.class,
                SubsystemToolPresenter.MyProxy.class);
                
        

    }

}
