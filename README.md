# SplitChat
A plugin for local and global chat for your Nukkit server!

![image](https://user-images.githubusercontent.com/83061703/208570855-1c065f53-b62a-46f9-843a-0c2d134a96c1.png)

# Placeholders:

```{splitchat_prefix}``` - Use this placeholder in your chat format plugin to display the chat type in a player's message.

```{splitchat_distance}``` - Display the distance to the player who sent the message.

# Config:
```yml
# Distance at which the local message will be visible
radius: 100

# The symbol for sending a message to the global chat.
# Example: "!your message"
symbol: "!"

# Prefix for local and global chat 
# Example: "L | MEFRREEXX: your message" 
prefix:
  local: "§l §aL §8|§r"
  global: "§l §cG §8|§r"
  spy: "§l §6SPY §8|§r"
  
# If enabled, only players with permission can write in local and global chat.
# Permissions: splitchat.chat.local, splitchat.chat.global
enable-permissions: false
```

# Commands:

```/localchat``` - Spy on local chat


# Permissions: 

```splitchat.chat.local``` - Permission to send messages to the local chat

```splitchat.chat.global``` - Permission to send messages to the global chat

```splitchat.command.spy``` - Permission to use the /localspy command
