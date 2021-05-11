# SoupPvP

### SoupPvP Core
Hello there everyone, my name is damt and this core has been created just to add something spicy to my portfolio. All concepts and all code that is stated should work right now. 
However, know that the code is still **not** tested!

### API
There is no API for this plugin yet. However, in the next push there will be events, commands and a general API.

### Optimization
I've tried to optimize the plugin to my hardest, saving happens per 5 minutes to help the server not lag. Everything saves onto the mongo and nothing really to the config.yml.

### Configuration
You can check out the configuration here:

```yml
mongo:
  host: "localhost"
  port: 27017
  auth:
    enabled: false
    database: "admin"
    username: ""
    password: ""

settings:
  announce-auto-save: true
  kill-reward: 100
  death-loss: 100

location:
  spawn:
    world: "world"
    x: 0.00
    y: 80.00
    z: 0.00
    yaw: 0.00
    pitch: 0.00

messages:
  death-message: "&a{player} &7has been slaughtered by &a{killer} &7using &a{reason}"

scoreboard:
  title: "&a&lSoupPvP &7(Map 1)"
  normal:
    - "&7&m----------------------------"
    - "&aPlayer Name&f:&7 {player}"
    - " "
    - "&aKills&f: &7{kills}"
    - "&aDeaths&f: &7{deaths}"
    - "&aCoins&f: &7{coins}"
    - " "
    - "&7&odamt.xyz"
    - "&7&m----------------------------"
  in-combat:
    - "&7&m----------------------------"
    - "&aPlayer Name&f:&7 {player}"
    - " "
    - "&aCombat&f: &7{time}s"
    - "&aKills&f: &7{kills}"
    - "&aCoins&f: &7{coins}"
    - " "
    - "&7&odamt.xyz"
    - "&7&m----------------------------"
```

### Credits

* [Telegram](https://t.me/therealdamt)
* [Website](https://damt.xyz)
* Discord - damt#0886

You are allowed to fork, and re-sell the product. Howeverm you need to change major detials, and selling this product without adding or forking major detials is not allowed. If you use this product you need to make sure to give ``damt`` credit and make sure to state that you forked it.
