# OrykMinesNew Plugin

## Introduction

The **OrykMinesNew** plugin is designed to enhance Minecraft Prison servers by providing robust and customizable management of mine regions. These regions are specific areas where players can mine designated blocks, receive custom drops, and experience automated block regeneration.

## Features

1. **Dynamic Mine Regions**: Create customizable mine regions where specific blocks can be mined.
2. **Customizable Drops**: Configure custom drops for blocks mined within the regions.
3. **Automated Block Regeneration**: Implement a regeneration cycle for mined blocks.
4. **Player Notifications**: Notify players when they enter or leave a mine region.
5. **Integration with WorldGuard**: Define and protect mine regions using WorldGuard.
6. **Graphical User Interface (GUI)**: Manage mine regions using an intuitive GUI.
7. **Permissions System**: Control access to plugin features with permissions.
8. **Logging and Debugging**: Log significant events and provide detailed error messages.

## Installation

1. Download the OrykMinesNew plugin JAR file.
2. Place the JAR file in your server's `plugins` directory.
3. Restart your server to load the plugin.

## Configuration

The plugin configuration is located in `config.yml`. Example configuration:

```yaml
mines:
  mine1:
    enter_message: "Welcome to Mine 1!"
    leave_message: "You have left Mine 1."
    blocks:
      COAL_ORE:
        chance: 0.3
        drops:
          COAL:
            chance: 1.0
          IRON_INGOT:
            chance: 0.1
        sound_effect: false
      IRON_ORE:
        chance: 0.2
        drops:
          IRON_INGOT:
            chance: 1.0
        sound_effect: false
      DIAMOND_ORE:
        chance: 0.1
        drops:
          DIAMOND:
            chance: 1.0
        sound_effect: true
      STONE:
        chance: 0.4
        drops:
          COBBLESTONE:
            chance: 1.0
        sound_effect: false
    regeneration:
      min_time: 30
      max_time: 60
