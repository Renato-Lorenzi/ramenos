package br.com.chat4e.views

import br.com.chat4e.ChatCallback
import org.eclipse.swt.widgets.Display
import rml.ramenos.messager.Buddy
import rml.ramenos.messager.Messenger

import java.text.SimpleDateFormat

@Singleton
class SWTChatViewController implements ChatCallback {

    static ChatCallback chatViewerImpl
    static SimpleDateFormat longDate = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss")
    static SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm")
    public static boolean logmode = true
    private Messenger messenger = new Messenger(this)
    protected ChatView chatView

    public void setChatView(ChatView chatView) {
        this.chatView = chatView
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

    private String getFormattedTextForConversation(Buddy b, String message, boolean send) {
        def user = send ? "me" : b.user
        "[${timeOnly.format(new Date())} ${user}]\$\r\n${message}"
    }


    @Override
    public void receivedMessage(final Buddy b, final String s) {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                if (s == null)
                    return
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
                    messenger.sendMessage(b, s)

                    message = getFormattedTextForConversation(b, s, true)
                } catch (Exception e) {
                    message = getFormattedTextForConversation(b, s, true) + " -- cannot be sent due to network issue"
                }
                chatView.displayChatInConversation(b, message)
            }
        })

    }


    public Messenger getMessenger() {
        return messenger
    }

    public void dispose() {
        messenger.stop()
    }
}
