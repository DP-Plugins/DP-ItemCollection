<center><img src="https://i.postimg.cc/MKPVVR1s/dplogo-512.png" alt="logo"></center>
<center><img src="https://i.postimg.cc/RZ9dqPFx/introduce.png" alt="introduce"></center>
<iframe width="560" height="315" src="https://www.youtube-nocookie.com/embed/qUEmSENhzcw" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>

This plugin helps players on a Minecraft server collect various items and receive special rewards upon achieving goals. Run fun and easy item collection events without complicated setup!

---

<center><img src="https://i.postimg.cc/RZ9dqP08/description.png" alt="description"></center>

- Create various item collection categories and rewards
- Automatically save and view each player's collection progress
- Easily edit items and rewards through a GUI (menu)
- Real-time collection status display via PlaceholderAPI integration
- Manage all features easily with admin commands

---

<center><img src="https://i.postimg.cc/rwcjzhpH/depend-plugin.png" alt="depend-plugin"></center>

- All DP-Plugins require the `DPP-Core` plugin.
- The plugin will not work if `DPP-Core` is not installed.
- You can download `DPP-Core` here: <a href="https://github.com/DP-Plugins/DPP-Core/releases" target="_blank">Click me!</a>
- This plugin integrates with `PlaceholderAPI`.
- If PlaceholderAPI is not installed, the placeholder features provided by this plugin will not work.

---

<center><img src="https://i.postimg.cc/dV01RxJB/installation.png" alt="installation"></center>

1️⃣ Place the `DPP-Core` plugin and this plugin file (`DP-ItemCollection-*.jar`) into your server's `plugins` folder.

2️⃣ Restart the server, and the plugin will be automatically enabled.

3️⃣ If needed, you can open and modify `config.yml` and `plugin.yml` to customize settings.

---

<center><img src="https://i.postimg.cc/jSKcC85K/settings.png" alt="settings"></center>

- `config.yml`: Manages basic plugin settings and message options.

---

<center><img src="https://i.postimg.cc/SxqdjZKw/command.png" alt="command"></center>

❗ Some commands require admin permission (`dpic.admin`).

**Command List and Examples**

| Command                                                        | Permission   | Description                                   | Example                                                                    |
|----------------------------------------------------------------|--------------|-----------------------------------------------|----------------------------------------------------------------------------|
| `/dpic create <collection/reward> <name>`                      | dpic.admin   | Create a new item collection category or reward | `/dpic create collection TreasureHunt`<br>`/dpic create reward FinalReward` |
| `/dpic maxpage <collection> <maxPage>`                         | dpic.admin   | Set the maximum number of pages for a category | `/dpic maxpage TreasureHunt 5`                                             |
| `/dpic items <collection/reward> <name>`                       | dpic.admin   | Edit category/reward items via GUI             | `/dpic items collection TreasureHunt`<br>`/dpic items reward FinalReward`   |
| `/dpic addcmdreward <rewardName> <command>`                    | dpic.admin   | Add a command to a reward                      | `/dpic addcmdreward FinalReward say Congratulations!`                     |
| `/dpic removecmdreward <rewardName> <index>`                   | dpic.admin   | Remove a registered command from a reward      | `/dpic removecmdreward FinalReward 1`                                      |
| `/dpic setreward <collectionName> <rewardName> <step>`         | dpic.admin   | Link a reward to a collection category        | `/dpic setreward TreasureHunt FinalReward 3`                              |
| `/dpic removereward <collectionName> <step>`                   | dpic.admin   | Unlink a reward from a category                | `/dpic removereward TreasureHunt 3`                                        |
| `/dpic settotalreward <rewardName> <step>`                    | dpic.admin   | Link a reward for full collection completion   | `/dpic settotalreward FinalReward 10`                                      |
| `/dpic removetotalreward <step>`                               | dpic.admin   | Unlink full collection reward                  | `/dpic removetotalreward 10`                                               |
| `/dpic checkitem`                                              | dpic.admin   | Open GUI to set collection check item          | `/dpic checkitem`                                                          |
| `/dpic open <collectionName>`                                  | None         | Open collection progress GUI                   | `/dpic open TreasureHunt`                                                  |
| `/dpic openrewardclaim (collectionName)`                       | None         | Open reward claim GUI                          | `/dpic openrewardclaim`<br>`/dpic openrewardclaim TreasureHunt`           |

**❗Notes when using commands**

- Category/reward names support both Korean and English, but **spaces are not allowed**.
- If an incorrect command is entered, an error message will appear. Example: "Invalid type. (collection/reward)"
- Items and rewards can be intuitively edited through the GUI. Changes are saved automatically.
- Admin commands require either `OP` status or the `dpic.admin` permission.

---

<center><img src="https://i.postimg.cc/Z5ZH0fqL/api-integration.png" alt="api-integration"></center>

Display a player's collection progress in real-time on your server!

- `%dpic_total_collected%`: Number of items the player has collected
- `%dpic_total_collections%`: Total number of collectible items

---

<center><a href="https://discord.gg/JnMCqkn2FX"><img src="https://i.postimg.cc/4xZPn8dC/discord.png" alt="discord"></a></center>

- https://discord.gg/JnMCqkn2FX
- If you have any questions or issues, please contact your server administrator or refer to the plugin distribution page.
- Feel free to leave suggestions for new features or improvements anytime!

---
