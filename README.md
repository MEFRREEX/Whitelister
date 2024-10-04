# Whitelister
Advanced whitelist plugin for Nukkit and PowerNukkitX

[![License: GNU GPLv3](https://img.shields.io/badge/License-GNU%20GPLv3-yellow)](LICENSE)
[![Version](https://img.shields.io/badge/Version-1.4.0-brightgreen)](https://github.com/MEFRREEX/Whitelister/releases/tag/1.4.0)

## 📖 Overview
**Whitelister** is an advanced whitelist plugin for Nukkit and PowerNukkitX, providing enhanced features beyond the standard whitelist. With Whitelister, you can easily manage access to your server, configure settings through forms, commands, or directly in configuration files, and more. The plugin also supports multiple languages and various database integrations.

### ✨ Features
- **User-friendly Configuration**: Manage the whitelist through in-game forms or commands.
- **Customizable Messages**: Tailor all plugin messages in a single configuration file.
- **Kick on Enable**: Automatically disconnect all players when the whitelist is enabled.
- **Database Support**: Choose between JSON, SQLite3, or MySQL for storing whitelist data.
- **Multilingual**: Supports English, Russian, and Ukrainian.

## 📷 Screenshots
<p align="center">
  <img src=".github/preview_main.png" alt="Main Menu Preview" width="400"/>
  <img src=".github/preview_players.png" alt="Players Menu Preview" width="400"/>
  <img src=".github/preview_settings.png" alt="Settings Menu Preview" width="400"/>
</p>

## 💻 Commands
| Command       | Description                       |
|---------------|-----------------------------------|
| `/whitelister` | Opens the main Whitelister menu. |

## 🔒 Permissions
| Permission                      | Description                                                      |
|---------------------------------|------------------------------------------------------------------|
| `whitelister.command.whitelister` | Allows the player to use the `/whitelister` command.             |
| `whitelister.bypass`            | Allows a player to join a whitelisted server without being listed.|

## 📋 Events
| Event Name                | Cancellable | Description                                                     |
|---------------------------|-------------|-----------------------------------------------------------------|
| `WhitelistKickPlayerEvent` | Yes         | Triggered when a player is kicked due to whitelist activation.   |

## 🚀 Installation
To get started with Whitelister, follow these steps:

1. **Download Whitelister**  
   Head to the [releases page](https://github.com/MEFRREEX/Whitelister/releases) and download the latest `.jar` file.
2. **Place the Plugin in Your Server**  
   Move the downloaded `.jar` file to your server's `plugins` folder.
3. **Install Dependencies**  
   - Download [JOOQConnector](https://github.com/MEFRREEX/JOOQConnector) and place its `.jar` file in the `plugins` folder.
   - Download [FormConstructor](https://github.com/MEFRREEX/FormConstructor) and place its `.jar` file in the `plugins` folder.
4. **Start the Server**  
   Launch your server to complete the setup and generate configuration files.

---

[Switch to Russian](README_ru.md)