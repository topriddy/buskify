package com.buskify.components;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

/**
 *
 * @author Seamfix
 */
public final class AlertPanel extends Panel {

    public enum AlertType {

        WARNING(""), ERROR("alert-error"), SUCCESS("alert-success"), INFO("alert-info");
        private final String css;

        AlertType(String css) {
            this.css = css;
        }
    };
    private String alertMessage;
    AlertType alertType;
    WebMarkupContainer wrapper;

    public AlertPanel(String id, String alertMessage, AlertType alertType, final boolean closeable) {
        super(id);

        wrapper = new WebMarkupContainer("wrapper");
        setOutputMarkupId(true);
        setOutputMarkupPlaceholderTag(true);
        add(wrapper);
        this.alertMessage = alertMessage;
        Label alertLabel = new Label("alertMessage", new PropertyModel<String>(this, "alertMessage"));
        alertLabel.setEscapeModelStrings(false);
        alertLabel.setOutputMarkupId(true);
        alertLabel.setOutputMarkupPlaceholderTag(true);

        AjaxFallbackLink closeLink = new AjaxFallbackLink("closeLink") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                if (target != null) {
                    AlertPanel.this.setVisible(closeable);
                    target.add(AlertPanel.this);
                }
            }
        };
        closeLink.setVisible(closeable);
        setAlertType(alertType);
        wrapper.add(alertLabel);
        wrapper.add(closeLink);
    }

    public AlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(AlertType alertType) {
        this.alertType = alertType;
        
        wrapper.add(AttributeModifier.replace("class", "alert " + alertType.css));
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }
}
