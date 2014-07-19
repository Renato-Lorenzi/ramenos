package br.com.chat4e.views.core

import org.eclipse.swt.widgets.Composite

public interface ChatViewPart {
    public void createPartControl(Composite parent)

    public void setFocus()

    public void refresh()

    public void dispose()
}
