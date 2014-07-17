package br.com.chat4e.views;

import br.com.chat4e.views.core.ChatViewPart;

public class ChatViewerFactory {

    public static ChatViewPart createChatViewPart() {
        return new ChatView();
    }
}
