package br.com.chat4e.views

import br.com.chat4e.ChatCallback
import br.com.chat4e.ComunicationException
import br.com.chat4e.views.core.ChatViewShow
import org.eclipse.swt.widgets.Display
import rml.ramenos.messager.Buddy
import rml.ramenos.messager.Messenger

import java.text.SimpleDateFormat

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
class SWTChatViewController implements ChatCallback {

    static ChatCallback chatViewerImpl
    static SimpleDateFormat longDate = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss")
    static SimpleDateFormat timeOnly = new SimpleDateFormat("hh:mm a")
    public static boolean logmode = true
    private Messenger messager
    protected ChatView chatView
    protected AccountsView accountsview
    private ChatViewShow chatViewController

    public void setChatView(ChatView chatView) {
        this.chatView = chatView
    }

    public void setAccountsView(AccountsView accountsview) {
        this.accountsview = accountsview
    }

    public AccountsView getAccountsview() {
        return accountsview
    }

    public ChatView getChatView() {
        return chatView
    }

    public String reverseWords(String s) {
        String[] splits = s.split(" ")
        String rWord = ""
        for (int i = splits.length - 1; i >= 0; i--) {
            rWord += splits[i]
            if (i != 0)
                rWord += " "
        }
        return rWord

    }

    private String getFormattedTextForConversation(Buddy b, String message,
                                                   boolean send) {
        "${timeOnly.format(new Date())} - [${b.user}]:\n ${message}"
    }

    private SWTChatViewController(ChatViewShow chatViewController) {
        this.chatViewController = chatViewController
        messager = new Messenger(this)
    }

    @Override
    public void receivedMessage(final Buddy b, final String s) {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                if (s == null)
                    return
                chatViewController.showView()
                chatView.ensureWindowOpened(b)
                String message = getFormattedTextForConversation(b, s, false)
                // chatView.ensureWindowOpened(b)
                chatView.displayChatInConversation(b, message)
                // PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                // .getActivePage().hideView((IViewPart) chatView)
            }
        })

    }

    @Override
    void sendFailed(String s) {

    }

    public void sendChat(final Buddy b, final String s) {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                String message
                String myname = ""
                if (myname.indexOf('/') != -1)
                    myname = myname.substring(0, myname.indexOf('/'))
                chatView.ensureWindowOpened(b)
                if (s == null)
                    return
                try {
                    message = s
                    messager.sendMessage(b, s)

                    message = getFormattedTextForConversation(b, s, true)
                } catch (ComunicationException e) {
                    message = getFormattedTextForConversation(b, s, true)
                    +" -- cannot be sent due to network issue"
                }
                chatView.displayChatInConversation(b, message)
            }
        })

    }


    static SWTChatViewController instance

    public static SWTChatViewController getInstance(
            ChatViewShow chatViewController) {
        if (!instance) {
            instance = new SWTChatViewController(chatViewController)
        }
        return instance;
    }

    public Messenger getMessager() {
        return messager
    }
}