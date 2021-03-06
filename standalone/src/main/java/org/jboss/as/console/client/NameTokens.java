package org.jboss.as.console.client;

/**
 * @author Heiko Braun
 * @date 2/4/11
 */
public class NameTokens {

    public static final String mainLayout = "main";

    public static String getMainLayout() {
        return mainLayout;
    }

    public static final String signInPage = "login";
    public static String getSignInPage() {
        return signInPage;
    }

    public static final String errorPage = "err";
    public static String getErrorPage() {
        return errorPage;
    }

    public static final String serverConfig = "server";
    public static String getServerConfig() {
        return serverConfig;
    }

    public static final String deploymentTool = "deployments";
    public static String getDeploymentTool() {
        return deploymentTool;
    }

    public static final String systemApp = "system";
    public static String getSystemApp() {
        return systemApp;
    }

    public final static String InterfaceToolPresenter = "interfaces";
    public static String getInterfaceToolPresenter() {
        return InterfaceToolPresenter;
    }

    public final static String PathToolPresenter = "path";
    public static String getPathToolPresenter() {
        return PathToolPresenter;
    }

    public final static String PropertyToolPresenter = "properties";
    public static String getPropertyToolPresenter() {
        return PropertyToolPresenter;
    }

    public final static String SocketToolPresenter = "sockets";
    public static String getSocketToolPresenter() {
        return SocketToolPresenter;
    }

    public final static String SubsystemToolPresenter = "subsys";
    public static String getSubsystemToolPresenter() {
        return SubsystemToolPresenter;
    }
}
