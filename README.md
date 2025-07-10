# Halfbloods

A demigod minecraft mod for Holy SMP

## Requirements

**Version:**  
Minecraft 1.21.7
Fabric 0.16.14

**Dependencies**  
Fabric API 0.128.2+1.21.7

## Modding status
- Uriel: Not implemented
- Artimes: **IMPLEMENTED YAY**
- Nyx: Not implemented

## Usage

Use `/setDeity <target> <deity>` to set a player's deity, deity names are all caps, ex: `ARTEMIS`.

## Project Structure

```access transformers
.
|-- client
|   |-- java
|   |   `-- lexicalerror
|   |       `-- halfbloods
|   |           |-- HalfbloodsClient.java       // Keybinds here (shouldn't need to touch)
|   |           |-- datagen
|   |           |-- entities
|   |           |   |-- ModEntitiesClient.java  // Register renderers here
|   |           |   `-- renderers               // Add renderers here
|   |           `-- mixin
|   `-- resources
`-- main
    |-- generated
    |-- java        
    |   `-- lexicalerror
    |       `-- halfbloods
    |           |-- Halfbloods.java             // Main entry
    |           |-- ModComponents.java          // Register Components here
    |           |-- abilities                   
    |           |   |-- Ability.java            
    |           |   |-- actives                 // Add actives here
    |           |   `-- passives                // Add passives here
    |           |-- commands                    // Add commands here
    |           |-- components                  // Add components here
    |           |-- config                      // Add gamerules here
    |           |-- deities                     // Add deity's to enum here
    |           |-- entities                    // Add & register entities here
    |           |-- items                       // Add & register entities here
    |           `-- mixin
    `-- resources
        |-- assets
        |-- data                                // Add tags here
        |-- fabric.mod.json
        `-- halfbloods.mixins.json
```

### Cardinal components
The player component includes a deity and a hashmap that maps cooldowns to names of active abilities
1. `getCooldown(name)`: returns remaining time of cooldown of an ability, stops at 0.
2. `setCooldown(name, duration)`: set cooldown of an ability.  

Other stuff shouldn't be important, to handle cooldowns from the ability class:
1. Get the player instance.
2. Get the player's component with: `PlayerComponent component = ModComponents.PLAYER_COMPONENT.get(player);`

### To add a deity and abilities:

**Passive**  
Create a java class `<passive>.java` that implements `passive`:
1. `getName()`, define your passive name here.
2. `apply()`, is called every tick.

**Active**  
Create a java class `<active>.java` that implements `active`:
1. `getName()`, define your passive name here.
2. `apply()`, does nothing.
3. `canActivate()` gets called and checked everytime the ability hotkey is pressed.
4. `activate()` gets called if ability hotkey is pressed and `canActivate()` is true.

**Deity**  
Add a diety to the `deities` enum, define the name, passives and actives.   


### TL;DR

To add a diety, add passives and actives classes, then define the diety in the enum. Right now there can only be two actives since there are only two hotkeys, but we can add more.