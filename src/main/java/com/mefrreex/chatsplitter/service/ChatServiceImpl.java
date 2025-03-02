package com.mefrreex.chatsplitter.service;

import cn.nukkit.Player;
import com.mefrreex.chatsplitter.chat.ChatChannelType;

import java.util.HashMap;
import java.util.Map;

public class ChatServiceImpl implements ChatService {

    private final Map<Player, ChatChannelType> defaultChannelType = new HashMap<>();

    private int localChatRadius;
    private String globalChatSymbol;
    private String localChatSymbol;
    private String globalChatPrefix;
    private String localChatPrefix;
    private String spyChatPrefix;
    private boolean enableNoRecipientsMessage;
    private boolean enablePermissions;

    @Override
    public Map<Player, ChatChannelType> getDefaultChannelType() {
        return defaultChannelType;
    }

    @Override
    public ChatChannelType getDefaultChannelType(Player player) {
        return defaultChannelType.getOrDefault(player, ChatChannelType.LOCAL);
    }

    @Override
    public void setDefaultChannelType(Player player, ChatChannelType chatChannel) {
        defaultChannelType.put(player, chatChannel);
    }

    @Override
    public int getLocalChatRadius() {
        return localChatRadius;
    }

    @Override
    public void setLocalChatRadius(int localChatRadius) {
        this.localChatRadius = localChatRadius;
    }

    @Override
    public String getGlobalChatSymbol() {
        return globalChatSymbol;
    }

    @Override
    public void setGlobalChatSymbol(String globalChatSymbol) {
        this.globalChatSymbol = globalChatSymbol;
    }

    @Override
    public String getGlobalChatPrefix() {
        return globalChatPrefix;
    }

    @Override
    public void setGlobalChatPrefix(String globalChatPrefix) {
        this.globalChatPrefix = globalChatPrefix;
    }

    @Override
    public String getLocalChatPrefix() {
        return localChatPrefix;
    }

    @Override
    public void setLocalChatPrefix(String localChatPrefix) {
        this.localChatPrefix = localChatPrefix;
    }

    @Override
    public String getSpyChatPrefix() {
        return spyChatPrefix;
    }

    @Override
    public void setSpyChatPrefix(String spyChatPrefix) {
        this.spyChatPrefix = spyChatPrefix;
    }

    @Override
    public boolean isEnableNoRecipientsMessage() {
        return enableNoRecipientsMessage;
    }

    @Override
    public void setEnableNoRecipientsMessage(boolean enableNoRecipientsMessage) {
        this.enableNoRecipientsMessage = enableNoRecipientsMessage;
    }

    @Override
    public boolean isEnablePermissions() {
        return enablePermissions;
    }

    @Override
    public void setEnablePermissions(boolean enablePermissions) {
        this.enablePermissions = enablePermissions;
    }
}
