package org.jboss.as.console.client;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;
import org.jboss.as.console.client.util.message.Message;
import org.jboss.as.console.client.util.message.MessageCenter;

/**
 * @author Heiko Braun
 * @date 2/4/11
 */
public class DefaultPlaceManager extends PlaceManagerImpl {

    private MessageCenter messageCenter;

    @Inject
    public DefaultPlaceManager(
            EventBus eventBus,
            TokenFormatter tokenFormatter, MessageCenter messageCenter ) {
        super(eventBus, tokenFormatter);
        this.messageCenter = messageCenter;
    }

    @Override
    public void revealErrorPlace(String invalidHistoryToken) {
        messageCenter.notify(
                new Message("Could not reveal: "+invalidHistoryToken,
                        Message.Severity.Fatal)
        );
    }

    public void revealDefaultPlace() {
        revealPlace( new PlaceRequest(NameTokens.mainLayout) );
    }

    @Override
    public void revealUnauthorizedPlace(String unauthorizedHistoryToken) {
        revealPlace( new PlaceRequest(NameTokens.signInPage) );
    }
}
