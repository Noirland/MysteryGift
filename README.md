# MysteryGift #

MysteryGift allow you to set up a weighted, randomised gifting system. Players can buy a randomised gift, for example through a online store, and get it given in game automatically.

Types of gifts supported:

* **Item** - Gives a player one or more stacks of items, the amount and materialdata can be variable
* **Permission** - Gives player a permission
* **Command** - Runs a command as console, replacing `{player}` and `{amount}` with the actual values
* **Multi** - Gives a player multiple of the of the above gifts

## Config File ##

Each gift is stored in its own section.

```
commands: # All command gifts
  "Fly Potion": # Name of gift
    command: "flypotion {player} {amount}" # Command to execute
    weight: 10 # Weight of the gift (default is 1), the larger the value the more likely it is
    rand: # Randomised amount between 1 and 64, inclusive
      min: 1
      max: 64
items:
  "Stone":
    material: STONE # Material type for item (from Bukkit)
    round: true # Rounds up the random value to the nearest stack size
    rand:
     min: 64
     max: 640
  "Red Wool":
    material: WOOL
    data: RED
    rand:
      min: 64
      max: 640
  "Anvil":
    material: ANVIL
    amount: 1
  "Enderchest":
    material: ENDERCHEST
    amount: 2
  "Carved Stonebrick":
    material: SMOOTH_BRICK
    data: SMOOTH_BRICK # Additional data for item (Bukkit MaterialData)
    amount 640
  "Creeper Head":
    material: SKULL_ITEM
    damage: 4 # Item damage (alternative to data)
    amount: 1
  "{data} Wool": # Show the data in the name
    material: WOOL
    data: # Randomly chooses one of the data to use
    - WHITE
    - ORANGE
    - MAGENTA
    - LIGHT_BLUE
    - YELLOW
    - LIME
    - PINK
    - GRAY
    - SILVER
    - CYAN
    - PURPLE
    - BLUE
    - BROWN
    - GREEN
    - RED
    - BLACK
    rand:
      min: 832
      max: 1728

perms:
    "Bat Disguise":
        perm: "disguisecraft.mob.bat" # Permission to give
        weight: 0.1
multi:
  "Diamond Armour Set": # Name of multi gift
    helm: # name of subgift
      type: item # the type of the gift (item, command, perm)
      material: DIAMOND_HELMET # same options as above for each type
      amount: 1
```

## Commands ##

`/mysterygift [player]`

Give a player a random gift

`/mgreload`

Reloads the plugin, usually to update gift types

## Permissions ##

`mysterygift.give` - Permission to give players gifts

`mysterygift.reload` - Permission to use reload command