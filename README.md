# ChatSplitter
A plugin for local and global chat for your Nukkit server!

![image](https://user-images.githubusercontent.com/83061703/208570855-1c065f53-b62a-46f9-843a-0c2d134a96c1.png)

## ⚙️ Configuration
```yml
# Available languages: eng (English), rus (Русский)
language: "eng"

# Distance at which the local message will be visible
local-chat-radius: 100

# The symbol for sending a message to the global chat.
# Example: "!your message"
global-chat-symbol: "!"

# Prefix for local and global chat 
# Example: "L | MEFRREEX: your message"
chat-prefix:
  local: "&l &aL &8|&r"
  global: "&l &cG &8|&r"
  spy: "&l &6SPY &8|&r"

# Message that there are no recipients
enable-no-recipients-message: true

# If enabled, only players with permission can write in local and global chat.
# Permissions: chatsplitter.chat.local, chatsplitter.chat.global
enable-permissions: false

# Plugin command
commands:
  # Local spy command
  spy:
    name: "localspy"
    description: "Local chat spy"
    aliases: ["lspy"]
```

## 🧩 Placeholders
### Warning: you must use these placeholders in your chat plugin, to display the chat type

```{chatsplitter_prefix}``` - Displays the type of chat (local, global or spy).

```{chatsplitter_distance}``` - Display the distance to the player who sent the message (only works with spy).

## 💻 Commands
| Command     | Description                 |
|-------------|-----------------------------|
| `/localspy` | Toggles the local chat spy. |

## 🔒 Permissions
| Permission                      | Description                                                     |
|---------------------------------|-----------------------------------------------------------------|
| `chatsplitter.chat.local`       | Allows the player to use local chat (if enabled in config.yml). |
| `chatsplitter.chat.global`      | Allows the player to use global chat (if enabled in config.yml. |
| `chatsplitter.command.spy`      | Allows the player to use the `/localspy` command.               |