package br.com.chat4e.views

import br.com.chat4e.views.core.ChatViewPart
import org.eclipse.jface.action.Action
import org.eclipse.jface.fieldassist.AutoCompleteField
import org.eclipse.jface.fieldassist.TextContentAdapter
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.custom.CTabItem
import org.eclipse.swt.events.DisposeEvent
import org.eclipse.swt.events.DisposeListener
import org.eclipse.swt.events.KeyEvent
import org.eclipse.swt.events.KeyListener
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Text
import rml.ramenos.messager.Buddy
import rml.ramenos.messager.Messenger

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
    Messenger messenger
    Map<String, Buddy> buddies
    HashMap<Buddy, CTabItem> chatWindows
    DisposeListener disposeListener
    Action logModeToggle = null
    Action about = null

    /**
     * The constructor.
     *
     */
    public ChatView() {
        chatViewer = SWTChatViewController.instance
        chatViewer.setChatView(this)
        messenger = chatViewer.messenger
        buddies = messenger.buddies
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
        def searchBox = new Text(parent, SWT.SEARCH | SWT.ICON_CANCEL
                | SWT.ICON_SEARCH)
        searchBox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL))

        def adapter = new TextContentAdapter() {
            public void setControlContents(Control control, String text, int cursorPosition) {
                super.setControlContents(control, text, cursorPosition)
//                ensureWindowOpened(buddies[searchBox.text])
            }
        }


        new AutoCompleteField(searchBox, adapter, buddies.keySet() as String[]);
        tabFolder = new CTabFolder(parent, SWT.BORDER)
        tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true))

        //Isso tem que ficar depois do AutoCompleteField :-(
        searchBox.addKeyListener(new KeyListener() {
            @Override
            void keyPressed(KeyEvent e) {
                if (e.character.toString() in ['\r', '\n']) {
                    def b = buddies[searchBox.text] ? buddies[searchBox.text] : new Buddy(machine: searchBox.text, user: searchBox.text)
                    ensureWindowOpened(b)
                }
            }

            @Override
            void keyReleased(KeyEvent e) {

            }
        })
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

    @Override
    void dispose() {
        chatViewer.dispose()
    }
}