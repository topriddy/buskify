package com.buskify.components;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.string.AppendingStringBuffer;

public abstract class ConfirmationLink<T> extends Link<T> {

    private static final long serialVersionUID = 1L;
    private String confirmation;

    public ConfirmationLink(String id, String confirmMessage) {
        super(id);
        confirmation = confirmMessage;
    }

    @Override
    protected String getOnClickScript(final CharSequence url) {
        AppendingStringBuffer buffer = new AppendingStringBuffer();
        if (confirmation != null) {
            buffer.append("if(!confirm('");
            buffer.append(confirmation);
            buffer.append("')) return false; return true;");
        }
        return buffer.toString();
    }
}