package br.com.chat4e.views;

import br.com.chat4e.views.core.ChatViewPart;
import br.com.chat4e.views.core.ChatViewShow;

public class ChatViewerFactory {

    public static ChatViewPart createAccountViewPart(ChatViewShow chatViewShow) {
        return new AccountsView(chatViewShow);
    }

    public static ChatViewPart createChatViewPart(ChatViewShow chatViewShow) {
        return new ChatView(chatViewShow);
    }
}
