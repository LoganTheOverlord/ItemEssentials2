# ItemEssentials2

> ItemEssentials is a fully-fledged (in-dev) plugin, that lets you control many things over items, be it inventories, or item themselves. Allows for good customization of items, and their behaviour.

> For current features, look up into Wiki. Information is always released upon Release, eg. non-alpha version.

> If you have any issues, submit an issue here, on github, or PM me "LogaaanCzech" on spigotmc.org. 

- See source code in master branch.

# Features

- You can control lore format, which includes custom attributes and enchantments display, colors, RGB, and many other things.
- Enchanting, disenchanting, loring, renaming, pickbinding (and soulbinding) is possible in one for-so-specified command.
- You can translate all parts of lore, including Enchantment names, attribute names, and so forth.
- You can also disable any part of lore you wish to stop displaying, apart from those, which are in-lored (pickbind, for example, in future, this will be written into NBT of item)
- Logs warning messages related to configuration into /warning.log file, which you can view later on.
- Has a history for translations, allowing for transition between versions of them, which means, that every piece of lore will be detected indenpendent of which one of previously used ones is contained in the lore of an item. This history also transits into item packs, and translates them into your format.
- Editing inventory click sounds, as well as pickup sounds, and even scrolling sounds!
- Displays all enchantments (up to level 1000 for now) in Roman number format with precision.

# Future updates

> Most of the focus goes into maintaining functionality, fixing bugs and adding new bleeding-edge features for better experience both for players, and admins.

# Credit section
- User: diademiemi, code: Tools for providing RGB support format, some of which are rewritten.
>Github: https://github.com/diademiemi/Embellish
- User: GleemingKnight, code: Command API & execution. Slight modifications and fixes were made to prevent errors, only thing left is fixing the auto-complete issue.
>Github: https://github.com/GleemingKnight/spigot-command-api
