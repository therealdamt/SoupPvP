# SoupPvP

### SoupPvP Core
Hello there everyone, my name is damt and this core has been created just to add something spicy to my portfolio. All concepts and all code that is stated should work right now. 
However, know that the code is still **not** tested!

### API
If you'd like a clearer view of the API you may visti ``src/test/java/xyz/damt/APIExample.java``

```java
public class APIExample implements Listener {

    //Getting The API
    private final SoupAPI soupAPI = JavaPlugin.getPlugin(Soup.class).getSoupAPI();

    //Events
    @EventHandler
    public void onCoinsGainEvent(CoinGainEvent e) {
        e.setCoinsAmount(e.getCoinsAmount() * 2);
        e.getPlayerProfile().getPlayer().sendMessage(CC.translate("&aYou were lucky so we increased your coins input by 2 times!"));
    }

    @EventHandler
    public void onDeathGainEvent(DeathGainEvent e) {
        e.getKilledProfile().getPlayer().sendMessage(CC.translate("&cRip you died I guess?"));
    }

    @EventHandler
    public void onKillGainEvent(KillGainEvent e) {
        e.getKillerProfile().getPlayer().sendMessage(CC.translate("&aYay! You killed someone called: " + e.getTargetProfile().getOfflinePlayer().getName()));
    }

    @EventHandler
    public void onSoupUseEvent(SoupUseEvent e) {
        Profile profile = e.getProfile();

        if (profile.getSoupsUsed() >= 20) {
            e.setCancelled(true);
            e.getProfile().getPlayer().sendMessage(CC.translate("&cYou are not allowed ot use this soup due to you exceeding the maximum soup uses limit!"));
        }
    }

    public void apiExampleUsage(Player player) {
        soupAPI.getDeaths(player.getUniqueId());
        soupAPI.getKills(player.getUniqueId());
        soupAPI.getSoupsUsed(player.getUniqueId());
        soupAPI.getCoins(player.getUniqueId());

        soupAPI.getProfile(player.getUniqueId()).setSoupsUsed(100000);
        soupAPI.getProfile(player.getUniqueId()).setDeaths(1000);
        soupAPI.getProfile(player.getUniqueId()).setCoins(1000);
    }

}
```

### Optimization
I've tried to optimize the plugin to my hardest, saving happens per 5 minutes to help the server not lag. Everything saves onto the mongo and nothing really to the config.yml.

### Configuration
You can check out the configuration here:

### Data Storage
You can choose between 2 of the following data storages:
* YML Storage
* Mongo Storage

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
  mongo: false
  use-placeholder-api: true
  health-increase: 4.00 #2 health = 1 heart
  kill-reward: 100
  death-loss: 100

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
    - "&aSoups Used&f: &7{used}"
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
    - "&aSoups Used&f: &7{used}"
    - " "
    - "&7&odamt.xyz"
    - "&7&m----------------------------"
```

### Credits

* [Telegram](https://t.me/therealdamt)
* [Website](https://damt.xyz)
* Discord - damt#0886

You are allowed to fork, and re-sell the product. Howeverm you need to change major detials, and selling this product without adding or forking major detials is not allowed. If you use this product you need to make sure to give ``damt`` credit and make sure to state that you forked it.
