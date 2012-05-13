package com.buskify.components;

import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 *
 * @author Seamfix
 */
public class MyFeedbackPanel extends FeedbackPanel {

    public MyFeedbackPanel(String id) {
        super(id);
    }
    public MyFeedbackPanel(String id, IFeedbackMessageFilter filter) {
        super(id, filter);
    }
    @Override
    protected Component newMessageDisplayComponent(String id, FeedbackMessage message) {
        AlertPanel alertPanel = new AlertPanel("message", message.getMessage().toString(), AlertPanel.AlertType.ERROR, false);
        alertPanel.setVisible(true);
        switch (message.getLevel()) {
            case FeedbackMessage.INFO:
                alertPanel.setAlertType(AlertPanel.AlertType.INFO);
                break;
            case FeedbackMessage.WARNING:
                alertPanel.setAlertType(AlertPanel.AlertType.WARNING);
                break;
            case FeedbackMessage.SUCCESS:
                alertPanel.setAlertType(AlertPanel.AlertType.SUCCESS);
                break;
        }
        return alertPanel;
    }
}
