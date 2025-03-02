package com.mefrreex.chatsplitter.service;

import cn.nukkit.Player;
import com.mefrreex.chatsplitter.chat.ChatChannelType;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Map;

public interface ChatService {

    @UnmodifiableView Map<Player, ChatChannelType> getDefaultChannelType();

    ChatChannelType getDefaultChannelType(Player player);

    void setDefaultChannelType(Player player, ChatChannelType chatChannel);

    int getLocalChatRadius();

    void setLocalChatRadius(int localChatRadius);

    String getGlobalChatSymbol();

    void setGlobalChatSymbol(String globalChatSymbol);

    String getGlobalChatPrefix();

    void setGlobalChatPrefix(String globalChatPrefix);

    String getLocalChatPrefix();

    void setLocalChatPrefix(String localChatPrefix);

    String getSpyChatPrefix();

    void setSpyChatPrefix(String spyChatPrefix);

    boolean isEnableNoRecipientsMessage();

    void setEnableNoRecipientsMessage(boolean noRecipientsMessage);

    boolean isEnablePermissions();

    void setEnablePermissions(boolean enablePermissions);
}
