# SlimefunAdvancements

A slimefun addon that lets you do things like complete and configure advancements

[download here](https://blob.build/project/SlimefunAdvancements)

## Configuration

The configuration files can be found in your `plugins/SFAdvancements/` folder.

### groups.yml

Each item in the yml represents an advancement group, where the key is the key of the group.<br>
The key is used to refer to the group in `advancements.yml`.<br>
Each group has a `display`, which is an item. It should be an item representation.<br>
You can optionally specify a `frame_type` for the frame type surrounding the display item in the vanilla GUI. (By default, groups will have a Goal frame type)<br>
The valid types for a frame are `GOAL`, `TASK`, and `CHALLENGER`<br>
The item is used to display the group in the GUI.<br>
You can optionally specify a `background` string for the group, which is used in the vanilla GUI. (By default, groups will have a bedrock texture)<br>
It should be the name of a block texture file. These files can be found on https://mcasset.cloud/ in `assets/minecraft/textures/block/` for the specified version.

### Item Representation

You can represent an item in a few ways.<br>
1. just a string, the id of the item (either [vanilla material](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html) or [slimefun item id](https://sf-items.walshy.dev/))
2. an object with a string `type` (the id of the item), optional string `name`, and optional string list `lore`.
3. a serialized representation of the item

You can generate representations of an item by holding the item in your hand in-game and typing `/sfa dumpitem`.<br>
The results will be displayed in console.

Examples of #1 in `groups.yml`
```yaml
my_cool_group:
  display: NETHER_STAR
  background: glass
  frame_type: CHALLENGER

my_other_group:
  display: ELECTRIC_MOTOR
  background: redstone_block
```

Examples of #2 in `groups.yml`
```yaml
basic:
  display:
    type: SLIME_BALL
    name: "&fBasic"
    lore:
      - "&7&oThe core spirit of Slimefun."

electric:
  display:
    type: REDSTONE
    name: "&eElectric"
    lore:
      - "&7&oThe center of civilization."
```

Example of #3 in `groups.yml`
```yaml
hi:
  display:
    ==: org.bukkit.inventory.ItemStack
    v: 2865
    type: IRON_INGOT
    meta:
      ==: ItemMeta
      meta-type: UNSPECIFIC
      display-name: '{"extra":[{"bold":false,"italic":false,"underlined":false,"strikethrough":false,"obfuscated":false,"color":"aqua","text":"Zinc
        Ingot"}],"text":""}'
      PublicBukkitValues:
        slimefun:slimefun_item: ZINC_INGOT
```

### advancements.yml

This is where all your advancements will go.<br>
Each item represents an advancement, where the key is the key of the advancement.<br>
(It is stored as a NamespacedKey `sfadvancements:<key>`)<br>
An advancement contains a group, parent(optional), display, name, criteria, optionally hidden, and optional rewards.<br>

The group is the id defined in `groups.yml`.

The parent is the id of a different Advancement for this to be under. (For Advancement Trees)

The display is an item, represented as described in Item Representation. This is the item that is displayed in both the 
SFA GUI and the vanilla GUI. You can specify a frame type that will appear in the vanilla gui. (GOAL, TASK, CHALLENGER). 
You can specify a placeholder line `%criteria%` in the lore, which will be replaced with the criteria of the advancement. 

The name is what will appear in chat when someone completes the advancement.

(the display and name support color codes with `&`)

The criteria are a section, where each item is a criterion and the key is the criterion key. (details below)

`hidden` is either `true` or `false`. If true, the advancement is hidden by default both in the chest gui and in the vanilla menu. 

The rewards are a section of different reward types (e.g. `command`s, details below)

#### criterion

The key of the criterion should be unique per advancement (but may share keys in different advancements).<br>
The string itself doesn't matter, but if no name is specified, it will use the key.<br>
Each criterion has a name, type, and optional other parameters.

The name is what appears in the gui for progress. (Supports color codes with `&`)

The type is the type of criterion. By default, these are the default criterion types:
- `consume`
  - for eating items
  - note that this only works for vanilla consumption, NOT exotic garden fruits for example, use `interact` for that
  - has an item parameter `item`, item to consume
  - has an integer parameter `amount`, the number of items to consume
- `interact`
  - for right-clicking items
  - has an item parameter `item`, the item to be right-clicked
  - has an integer parameter `amount`, the number of times to interact
- `inventory`
  - for having an item in an inventory
  - has an item parameter `item`, the item to have in the inventory
  - has an integer parameter `amount`, the number of items you need to complete the criterion
- `multiblock`
  - for interacting with a slimefun multiblock
  - has a string parameter `multiblock`, the slimefun item id of the multiblock
- `place`
  - for placing blocks
  - has an item parameter `item`, the item to place down
  - has an integer parameter `amount`, the number of items to place
    - note that there is no protection against players repeatedly breaking and replacing the same block, so for most items setting the `amount` to 1 would be appropriate
- `break`
  - like `place` but for breaking blocks, same parameters
- `research`
  - for completing a research
  - has a string parameter `research`, the namespaced key of the research
    - namespaced keys have the format "plugin:key", so for slimefun researches, it is "slimefun:research", e.g. "slimefun:ender_talismans"
- `mobkill`
  - for killing a type of mob
  - has an integer parameter `amount`, the number of mobs to kill
  - has a string parameter `entity`, the mob to kill
    - entity types are generally lowercase, separated by underscores (ex. `stray`, `cave_spider`, `glow_squid`, etc.)
- `search`
  - for searching for a string in the slimefun guide
  - string parameter `search`, the exact string to search for in the guide
- `multiblockcraft`
  - for crafting an item in a multiblock
  - has an integer parameter `amount`, the number of times to craft (not how many items are to be crafted)
  - (optional) has an item parameter `item`, the item to craft
    - if not present, any item crafted with the multiblock will complete the criterion
  - (optional) has a string parameter `multiblock`, the slimefun id of the multiblock to craft
    - if not present, any multiblock can be used to craft the item to complete the criterion
    - you may specify neither an item nor multiblock, in which case any multiblock craft done by the player will increment the criterion

#### rewards

The rewards have a different section for different types.<br>
(for now, the only type is `commands`)<br>
The name of the section determines the type of reward.

Reward types:
- command
  - is a string list, with each line being a command to run
  - you can refer to the name of the player that completed the advancement via `%p%`

## Permissions

`sfa.command.<command name>`: allows the user to use the command

## Custom Criteria (developers)

see [api.md](https://github.com/qwertyuioplkjhgfd/SlimefunAdvancements/blob/main/api.md)

## TODO:
- ~~criteria system~~
  - ~~inventory criteria~~
  - ~~craft criteria (soon, see [Slimefun/Slimefun4#3439](https://github.com/Slimefun/Slimefun4/pull/3439))~~
  - ~~interact criteria~~
      - ~~place criteria~~
  - ~~research criteria~~
- ~~configurability~~
- ~~rewards~~
- add advancements
- ~~permissions~~
- ~~load default advancements (from other plugins)~~
- better readme, .github, ~~builds page~~
- ~~tree~~
- advancements api (crazy)
- cheat menu
- docs
