# Whitelister

Advanced whitelist plugin for Nukkit and PowerNukkitX

## 🤔 Introduction

The plugin is designed to make the whitelist easier to work with and provides a number of features over the standard whitelist:
- Configuring the whitelist in forms
- Configuring the whitelist via commands
- Configuring all messages in a specific file
- Setting that allows to kick all players when whitelist is enabled
- SQLite3 and MySQL support
- English, Russian language support

## 📷 Screenshots
![preview_main.png](.github/preview_main.png)
![preview_players.png](.github/preview_players.png)
![preview_settings.png](.github/preview_settings.png)

## 💻 Commands
| Name           | Description         |
|----------------|---------------------|
| /whitelister   | Plugin main command |

## 🔒 Permissions
| Name                            | Description                                                       |
|---------------------------------|-------------------------------------------------------------------|
| whitelister.command.whitelister | Allows the player to use the /whitelister command                 |
| whitelister.bypass              | Allows a player to enter a whitelisted server without being on it |

## 📋 Events
| Name                        | Cancellable | Description                                                    |
|-----------------------------|-------------|----------------------------------------------------------------|
| WhitelistKickPlayerEvent    | true        | Called when a player is kicked from the server by a whitelist  |

## 🔌 Installation
Place the plugin of the appropriate version in the `plugins` folder.