# PunishmentCore
A simple to use Spigot punishment plugin that is **NOT** a Hypixel ripoff /s.

### Please note that this project is WIP.

### Features:
- SQLite for storing the punishments
- Punishment history
- Blacklist commands for muted players

### Commands:
- /kick <name> : Kicks a player with the specified reason.
- /ban <name> <reason> : Permanently bans a player with the specified reason.
- /tempban <name> <length> <reason> : Temporarily bans a player with the specified reason for the specified length
- /mute <name> <length> <reason> : Mutes a player with the specified reason for the specified length
- /warn <name> <reason> : Warns a player with the specified reason.
- /unban <name> : Unbans a player.
- /unmute <name> : Unmutes a player.
- /phistory <name> : Gets a player's punishment history.
- /getpunishment <id> : Gets a punishment by ID.

### Permissions:
- punishmentcore.warn : /warn
- punishmentcore.kick : /kick
- punishmentcore.mute : /mute
- punishmentcore.unmute : /unmute
- punishmentcore.tempban : /tempban
- punishmentcore.ban : /ban
- punishmentcore.unban : /unban
- punishmentcore.getpunishments : /phistory & /getpunishments

The SQLite file (`punishments.db`) is located in the server's root directory.

### Screenshots:
![Warning](https://i.debuggings.dev/9YDibaWV.png)
![Kick](https://i.debuggings.dev/AoTJj4QM.png)
![Mute](https://i.debuggings.dev/Q1xVEWEg.png)
![Temporary Ban](https://i.debuggings.dev/59AEbNew.png)
![Permanent Ban](https://i.debuggings.dev/mfZtfFYP.png)
![Punishment History](https://i.debuggings.dev/elglilwc.png)
![Punishment Information](https://i.debuggings.dev/yQ5xGlCq.png)

### Default Configuration:
```yaml
mute-domain: www.hypixel.net/mutes
ban-domain: https://hypixel.net/appeal
mute-commands: [
  /msg
  /message
  /tell
  /r
  /reply
  /whisper
  /w
  /me
]
```
