package br.com.chat4e.views


import org.eclipse.jface.resource.JFaceResources
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabItem
import org.eclipse.swt.events.FocusEvent
import org.eclipse.swt.events.FocusListener
import org.eclipse.swt.events.KeyAdapter
import org.eclipse.swt.events.KeyEvent
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.Font
import org.eclipse.swt.graphics.RGB
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Text
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
class ChatWindow extends Composite {
    Text conversationText
    Text chatText
    private Buddy b
    int unread = 0
    CTabItem item
    SWTChatViewController controller
    private static final Font monospace = JFaceResources
            .getFont(JFaceResources.TEXT_FONT)
    private static final GridData conversationTextGridData = new GridData(
            GridData.FILL, GridData.FILL, true, true)
    private static final GridData chatTextGridData = new GridData(
            GridData.FILL, GridData.FILL, true, false)

    public ChatWindow(final SWTChatViewController controller, Buddy buddy,
                      Composite parent, CTabItem item, int style) {
        super(parent, style)
        this.controller = controller
        this.item = item
        this.b = buddy
        this.setLayout(new GridLayout(1, false))
        conversationText = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL)
        conversationText.setFont(monospace)
        conversationText.setLayoutData(conversationTextGridData)
        conversationText.getVerticalBar().setSelection(
                conversationText.getVerticalBar().getMaximum())
        conversationText.setBackground(new Color(conversationText.getDisplay(),
                new RGB(255, 255, 255)))
        conversationText.setEditable(false)
        chatText = new Text(this, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL)
        chatText.setText("")
        chatTextGridData.heightHint = 5 * chatText.lineHeight;
        chatText.setLayoutData(chatTextGridData)
        chatText.setForeground(new Color(chatText.getDisplay(), new RGB(160,
                160, 160)))
        chatText.setText("Click on this text to type and press \"Enter\" to send")
        chatText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (((e.stateMask & SWT.SHIFT) == 0) && (e.character == '\n' || e.character == '\r')) {
                    String text = chatText.getText()
                    controller.sendChat(b, text)
                    chatText.setText("")
                }
            }
        })

        chatText.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent arg0) {
                chatText.setForeground(new Color(chatText.getDisplay(),
                        new RGB(0, 0, 0)))
                if (chatText
                        .getText()
                        .equals("Click on this text to type and press \"Enter\" to send")) {
                    chatText.setText("")
                }

            }

            public void focusLost(FocusEvent arg0) {
                if (chatText.getText().equals("")) {
                    chatText.setForeground(new Color(chatText.getDisplay(),
                            new RGB(160, 160, 160)))
                    chatText.setText("Click on this text to type and press \"Enter\" to send")
                }
            }

        })
    }

    public Buddy getBuddy() {
        return b
    }

    @Override
    public boolean setFocus() {
        unread = 0
        if (!item.isDisposed()) {
            item.setText(b.user)
        }
        return chatText.setFocus()
    }

    @Override
    public boolean forceFocus() {
        unread = 0
        if (!item.isDisposed())
            item.setText(b.user)
        return chatText.forceFocus()
    }
}
