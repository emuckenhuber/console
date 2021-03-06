/*
 * RHQ Management Platform
 * Copyright (C) 2005-2010 Red Hat, Inc.
 * All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package org.jboss.as.console.client.util.message;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.smartgwt.client.types.*;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.IMenuButton;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import java.util.List;

/**
 * @author Greg Hinkle
 */
public class MessageCenterView implements MessageCenter.MessageListener {

    public static final String LOCATOR_ID = "MessageCenter";

    private MessageCenter messageCenter;
    private HLayout layout;

    @Inject
    public MessageCenterView(MessageCenter messageCenter) {
        this.messageCenter = messageCenter;
    }

    public Widget asWidget()
    {
        layout = new HLayout();

        layout.setHeight100();
        layout.setAlign(Alignment.RIGHT);
        layout.setAlign(VerticalAlignment.CENTER);
        layout.setOverflow(Overflow.HIDDEN);

        final Menu recentEventsMenu = new Menu();
        recentEventsMenu.setTitle("Messages");

        IMenuButton recentEventsButton = new IMenuButton("Messages", recentEventsMenu);
        recentEventsButton.setTop(5);
        recentEventsButton.setShowMenuBelow(false);
        recentEventsButton.setAutoFit(true);
        recentEventsButton.setValign(VerticalAlignment.CENTER);

        recentEventsButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                List<Message> messages = messageCenter.getMessages();
                if (messages.isEmpty()) {
                    recentEventsMenu.setItems(new MenuItem("No recent messages"));
                } else {
                    MenuItem[] items = new MenuItem[messages.size()];
                    for (int i = 0, messagesSize = messages.size(); i < messagesSize; i++) {
                        final Message message = messages.get(i);
                        MenuItem messageItem = new MenuItem(message.conciseMessage, getSeverityIcon(message.severity));

                        items[i] = messageItem;

                        messageItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
                            public void onClick(MenuItemClickEvent event) {
                                showDetails(message);
                            }
                        });
                    }
                    recentEventsMenu.setItems(items);
                }
            }
        });

        VLayout vl = new VLayout();
        vl.setAutoWidth();
        vl.setAlign(Alignment.LEFT);
        vl.setAlign(VerticalAlignment.CENTER);
        vl.addMember(recentEventsButton);

        layout.addMember(new LayoutSpacer());
        layout.addMember(vl);

        // register listener
        messageCenter.addMessageListener(this);

        return layout;
    }

    private void showDetails(Message message) {
        DynamicForm form = new DynamicForm();
        form.setTitle("Details");
        form.setWrapItemTitles(false);

        StaticTextItem title = new StaticTextItem("title", "Title");
        title.setValue(message.conciseMessage);

        StaticTextItem severity = new StaticTextItem("severity", "Severity");
        FormItemIcon severityIcon = new FormItemIcon();
        severityIcon.setSrc(getSeverityIcon(message.severity));
        severity.setIcons(severityIcon);
        severity.setValue(message.severity.name());

        StaticTextItem date = new StaticTextItem("time", "Time");
        date.setValue(message.fired);

        StaticTextItem detail = new StaticTextItem("detail", "Detail");
        detail.setTitleOrientation(TitleOrientation.TOP);
        detail.setValue(message.detailedMessage);
        detail.setColSpan(2);

        ButtonItem okButton = new ButtonItem("ok", "OK");
        okButton.setColSpan(2);
        okButton.setAlign(Alignment.CENTER);

        form.setItems(title, severity, date, detail, okButton);

        final Window window = new Window();
        window.setTitle("Message");
        window.setTitle(message.conciseMessage);
        window.setWidth(600);
        window.setHeight(400);
        window.setIsModal(true);
        window.setShowModalMask(true);
        window.setCanDragResize(true);
        window.centerInPage();
        window.addItem(form);
        window.show();
        okButton.focusInItem();
        okButton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent clickEvent) {
                window.destroy();
            }
        });
    }

    public void onMessage(final Message message) {
        if (!message.isTransient()) {
            logMessage(message);

            final Label label = new Label(message.conciseMessage);
            label.setMargin(5);
            label.setAutoFit(true);
            label.setHeight(25);
            label.setWrap(false);

            String iconSrc = getSeverityIcon(message.severity);

            label.setIcon(iconSrc);

            label.setTooltip(message.detailedMessage);

            label.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent clickEvent) {
                    showDetails(message);
                }
            });

            layout.addMember(label, 1);
            layout.redraw();

            Timer hideTimer = new Timer() {
                @Override
                public void run() {
                    label.animateHide(AnimationEffect.FADE, new AnimationCallback() {
                        public void execute(boolean b) {
                            label.destroy();
                        }
                    });
                }
            };
            hideTimer.schedule(10000);
        }
    }

    private void logMessage(Message message) {
        // TODO: Format the message better.
        String logMessage = message.toString();
        switch (message.getSeverity()) {
            case Info:
                Log.info(logMessage);
                break;
            case Warning:
                Log.warn(logMessage);
                break;
            case Error:
                Log.error(logMessage);
                break;
            case Fatal:
                Log.fatal(logMessage);
                break;
        }
    }

    private String getSeverityIcon(Message.Severity severity) {
        String iconSrc = null;
        switch (severity) {
            case Info:
                iconSrc = "info/icn_info_blue.png";
                break;
            case Warning:
                iconSrc = "info/icn_info_orange.png";
                break;
            case Error:
            case Fatal:
                iconSrc = "info/icn_info_red.png";
                break;
        }
        return iconSrc;
    }

}
