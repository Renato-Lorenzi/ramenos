package br.com.chat4e.views


import br.com.chat4e.views.core.ChatViewPart
import br.com.chat4e.views.core.ChatViewShow
import org.eclipse.jface.action.Action
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.custom.CTabItem
import org.eclipse.swt.events.DisposeEvent
import org.eclipse.swt.events.DisposeListener
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.widgets.Composite
import rml.ramenos.messager.Buddy

/**
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @author Rajgopal Vaithiyanathan
 *
 */
class ChatView implements ChatViewPart {

    CTabFolder tabFolder

    SWTChatViewController chatViewer
    HashMap<Buddy, CTabItem> chatWindows
    DisposeListener disposeListener
    Action logModeToggle = null
    Action about = null

    /**
     * The constructor.
     *
     * @param chatViewController
     */
    public ChatView(ChatViewShow chatViewController) {
        chatViewer = SWTChatViewController.getInstance(chatViewController)
        chatViewer.setChatView(this)
        chatWindows = new HashMap<Buddy, CTabItem>()

        disposeListener = new DisposeListener() {

            public void widgetDisposed(DisposeEvent e) {
                Buddy b = ((ChatWindow) ((CTabItem) e.getSource()).getControl())
                        .getBuddy()
                if (b != null)
                    ChatView.this.chatWindows.remove(b)

            }

        }
    }

    public void createPartControl(Composite parent) {
        tabFolder = new CTabFolder(parent, SWT.BORDER)
        tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true))

    }

    /**
     * Passing the focus request to the form.
     */
    public void setFocus() {
        CTabItem item = tabFolder.getSelection()
        if (item == null)
            return
        item.getControl().setFocus()
    }

    public void ensureWindowOpened(Buddy b) {
        if (!chatWindows.containsKey(b)) {
            addChatWindow(b)
        }
    }

    private void addChatWindow(Buddy b) {
        CTabItem item = new CTabItem(tabFolder, SWT.CLOSE)
        item.setText(b.user)
        item.setControl(new ChatWindow(chatViewer, b, tabFolder, item, SWT.FILL))
        item.addDisposeListener(disposeListener)
        chatWindows.put(b, item)
        tabFolder.setSelection(item)
        tabFolder.setFocus()
    }

    public void closeChatWindow(Buddy b) {
        CTabItem item = chatWindows.get(b)
        if (item == null)
            return
        item.dispose()
        chatWindows.remove(b)
    }

    public void displayChatInConversation(Buddy b, String s) {
        ChatWindow w = (ChatWindow) chatWindows.get(b).getControl()
        // appened messes with the font :-/
        w.conversationText.setText(w.conversationText.getText() + s
                + w.conversationText.getLineDelimiter())
        w.conversationText.setSelection(w.conversationText.getText().length())
        if (tabFolder.getSelection() != chatWindows.get(b)) {
            w.unread++
            chatWindows.get(b).setText("[" + w.unread + "] " + "[${b.user}@${b.machine}]")
        }
        // }
    }

    public void refresh() {

    }
}