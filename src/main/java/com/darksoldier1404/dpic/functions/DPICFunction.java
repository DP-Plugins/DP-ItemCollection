package com.darksoldier1404.dpic.functions;

import com.darksoldier1404.dpic.obj.Category;
import com.darksoldier1404.dpic.obj.CategoryType;
import com.darksoldier1404.dpic.obj.CollectionUser;
import com.darksoldier1404.dpic.obj.Reward;
import com.darksoldier1404.dppc.api.inventory.DInventory;
import com.darksoldier1404.dppc.api.placeholder.PlaceholderBuilder;
import com.darksoldier1404.dppc.utils.ConfigUtils;
import com.darksoldier1404.dppc.utils.NBT;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.darksoldier1404.dpic.ItemCollection.plugin;

@SuppressWarnings("static-access")
public class DPICFunction {

    public static void initPlaceholder() {
        new PlaceholderBuilder.Builder(plugin)
                .identifier("dpic")
                .version("1.0.0")
                .onRequest((p, str) -> {
                    if (str.equals("total_collected")) {
                        CollectionUser user = plugin.udata.get(p.getUniqueId());
                        return String.valueOf(user.getCollectedItemCount());
                    }
                    if (str.equals("total_collections")) {
                        int i = 0;
                        for (Category category : plugin.categories.values()) {
                            for (ItemStack[] items : category.getInventory().getPageItems().values()) {
                                for (ItemStack item : items) {
                                    if (item != null && item.getType() != Material.AIR) {
                                        i++;
                                    }
                                }
                            }
                        }
                        return String.valueOf(i);
                    }
                    return null;
                }).build();
    }

    public static void createCollection(CommandSender sender, String name) {
        if (isExistCollection(name)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_collection_already_exists"));
            return;
        }
        Category category = new Category();
        category.setName(name);
        category.setType(CategoryType.TOTAL_REWARD_ONLY);
        DInventory inv = new DInventory(plugin.getLang().getWithArgs("inv_title_collection", name), 54, true, true, plugin);
        category.setInventory(inv);
        plugin.categories.put(name, category);
        plugin.categories.saveAll();
        sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_collection_created"));
    }

    public static void createReward(CommandSender sender, String name) {
        if (isExistReward(name)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_reward_already_exists"));
            return;
        }
        plugin.rewards.put(name, new Reward(name, new DInventory(plugin.getLang().getWithArgs("inv_title_reward", name), 27, plugin)));
        plugin.rewards.saveAll();
        sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_reward_created"));
    }

    public static boolean isExistCollection(String name) {
        return plugin.categories.containsKey(name);
    }

    public static boolean isExistReward(String name) {
        return plugin.rewards.containsKey(name);
    }

    public static void setMaxPage(CommandSender sender, String collectionName, String sMaxPage) {
        if (!isExistCollection(collectionName)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_collection_not_found"));
            return;
        }
        try {
            int maxPage = Integer.parseInt(sMaxPage);
            if (maxPage < 1) {
                sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_max_page_at_least_one"));
                return;
            }
            plugin.categories.get(collectionName).setMaxPage(maxPage);
            plugin.categories.saveAll();
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_max_page_set"));
        } catch (NumberFormatException e) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_error_not_a_number"));
        }
    }

    public static void editCollectionItems(CommandSender sender, String name) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_error_player_only"));
            return;
        }
        Player p = (Player) sender;
        if (!isExistCollection(name)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_collection_not_found"));
            return;
        }
        Category category = plugin.categories.get(name);
        DInventory inv = category.getInventory().clone();
        inv.setObj(name);
        inv.setChannel(101); // Item edit channel
        inv.setCurrentPage(0);
        inv.update();
        inv.openInventory(p);
    }

    public static void saveCollectionItems(Player p, DInventory inv) {
        if (inv.getObj() == null) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_error_no_object"));
            return;
        }
        String name = (String) inv.getObj();
        if (!isExistCollection(name)) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_collection_not_found"));
            return;
        }
        Category category = plugin.categories.get(name);
        category.setInventory(inv);
        plugin.categories.put(name, category);
        plugin.categories.saveAll();
        p.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_item_saved"));
    }

    public static void editRewardItems(CommandSender sender, String name) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_error_player_only"));
            return;
        }
        Player p = (Player) sender;
        if (!isExistReward(name)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_reward_not_found"));
            return;
        }
        Reward reward = plugin.rewards.get(name);
        DInventory inv = reward.getInventory().clone();
        inv.setObj(name);
        inv.setChannel(201); // Reward item edit channel
        inv.setCurrentPage(0);
        inv.update();
        inv.openInventory(p);
    }

    public static void saveRewardItems(Player p, DInventory inv) {
        if (inv.getObj() == null) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_error_no_object"));
            return;
        }
        String name = (String) inv.getObj();
        if (!isExistReward(name)) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_reward_not_found"));
            return;
        }
        Reward reward = plugin.rewards.get(name);
        reward.setInventory(inv);
        plugin.rewards.put(name, reward);
        plugin.rewards.saveAll();
        p.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_item_saved"));
    }

    public static void setRewardToCollection(CommandSender sender, String collectionName, String rewardName, String sStep) {
        if (!isExistCollection(collectionName)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_collection_not_found"));
            return;
        }
        if (!isExistReward(rewardName)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_reward_not_found"));
            return;
        }
        try {
            int step = Integer.parseInt(sStep);
            if (step < 1) {
                sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_step_at_least_one"));
                return;
            }
            Category category = plugin.categories.get(collectionName);
            Reward reward = plugin.rewards.get(rewardName);
            category.getRewards().put(step, reward);
            plugin.categories.put(collectionName, category);
            plugin.categories.saveAll();
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_reward_set"));
        } catch (NumberFormatException e) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_error_not_a_number"));
        }
    }

    public static void removeRewardFromCollection(CommandSender sender, String collectionName, String sStep) {
        if (!isExistCollection(collectionName)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_collection_not_found"));
            return;
        }
        try {
            int step = Integer.parseInt(sStep);
            if (step < 1) {
                sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_step_at_least_one"));
                return;
            }
            Category category = plugin.categories.get(collectionName);
            if (!category.getRewards().containsKey(step)) {
                sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_reward_not_set_for_step"));
                return;
            }
            category.getRewards().remove(step);
            plugin.categories.put(collectionName, category);
            plugin.categories.saveAll();
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_reward_removed"));
        } catch (NumberFormatException e) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_error_not_a_number"));
        }
    }

    public static void openCheckItemSettingGUI(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_error_player_only"));
            return;
        }
        Player p = (Player) sender;
        DInventory inv = new DInventory(plugin.getLang().get("inv_title_checkitem"), 27, plugin);
        ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = pane.getItemMeta();
        meta.setDisplayName(" ");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        pane.setItemMeta(meta);
        pane = NBT.setStringTag(pane, "dppc_clickcancel", "true");
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, pane);
        }
        ItemStack checkItem = plugin.config.getItemStack("Settings.checkItem");
        inv.setItem(13, checkItem);
        inv.applyChanges();
        inv.setChannel(301); // Check item setting channel
        inv.openInventory(p);
    }

    public static void saveCheckItem(Player p, DInventory inv) {
        ItemStack item = inv.getItem(13);
        if (item == null || item.getType() == Material.GRAY_STAINED_GLASS_PANE) {
            plugin.config.set("Settings.checkItem", null);
            plugin.saveConfig();
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_check_item_removed"));
            return;
        }
        plugin.config.set("Settings.checkItem", item);
        plugin.saveDataContainer();
        plugin.checkItem = item;
        p.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_check_item_set"));
    }

    public static void openCollection(CommandSender sender, String name) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_error_player_only"));
            return;
        }
        Player p = (Player) sender;
        if (!isExistCollection(name)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_collection_not_found"));
            return;
        }
        Category category = plugin.categories.get(name);
        category.openInventoryForUser(p);
    }

    public static void setTotalReward(CommandSender sender, String rewardName, String sStep) {
        if (!isExistReward(rewardName)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_reward_not_found"));
            return;
        }
        try {
            int step = Integer.parseInt(sStep);
            if (step < 1) {
                sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_step_at_least_one"));
                return;
            }
            plugin.totalRewardCategory.getRewards().put(step, plugin.rewards.get(rewardName));
            ConfigUtils.saveCustomData(plugin, plugin.totalRewardCategory.serialize(), "totalRewardCategory", "totalRewardCategory");
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_total_reward_set"));
        } catch (NumberFormatException e) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_error_not_a_number"));
        }
    }

    public static void removeTotalReward(CommandSender sender, String sStep) {
        if (!isExistCollection("total_reward")) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_total_reward_category_not_found"));
            return;
        }
        try {
            int step = Integer.parseInt(sStep);
            if (step < 1) {
                sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_step_at_least_one"));
                return;
            }
            if (!plugin.totalRewardCategory.getRewards().containsKey(step)) {
                sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_reward_not_set_for_step"));
                return;
            }
            plugin.totalRewardCategory.getRewards().remove(step);
            plugin.saveDataContainer();
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_total_reward_removed"));
        } catch (NumberFormatException e) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_error_not_a_number"));
        }
    }

    public static void openRewardClaimInventory(CommandSender sender, String collectionName) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_error_player_only"));
            return;
        }
        Player p = (Player) sender;
        if (!isExistCollection(collectionName)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_collection_not_found"));
            return;
        }
        CollectionUser user = plugin.udata.get(p.getUniqueId());
        if (user != null) {
            user.openRewardClaimInventory(p, collectionName, false);
            return;
        }
    }

    public static void openTotalRewardClaimInventory(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_error_player_only"));
            return;
        }
        Player p = (Player) sender;
        CollectionUser user = plugin.udata.get(p.getUniqueId());
        if (user != null) {
            user.openRewardClaimInventory(p, plugin.totalRewardCategory.getName(), true);
            return;
        }
    }

    public static List<String> getCollectionNames() {
        return new ArrayList<>(plugin.categories.keySet());
    }

    public static List<String> getRewardNames() {
        return new ArrayList<>(plugin.rewards.keySet());
    }

    public static ArrayList<String> getCollectionRewardSteps(String collectionName) {
        if (!isExistCollection(collectionName)) {
            return null;
        }
        Category category = plugin.categories.get(collectionName);
        return category.getRewards().keySet().stream()
                .map(String::valueOf)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static void addCommandReward(CommandSender sender, String rewardName, String command) {
        if (!isExistReward(rewardName)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_reward_not_found"));
            return;
        }
        Reward reward = plugin.rewards.get(rewardName);
        reward.getCommandRewards().add(command);
        plugin.rewards.put(rewardName, reward);
        plugin.rewards.saveAll();
        sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_reward_command_added"));
    }

    public static void removeCommandReward(CommandSender sender, String rewardName, String index) {
        if (!isExistReward(rewardName)) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_reward_not_found"));
            return;
        }
        Reward reward = plugin.rewards.get(rewardName);
        try {
            int i = Integer.parseInt(index);
            if (i < 1 || i > reward.getCommandRewards().size()) {
                sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_error_invalid_index"));
                return;
            }
            reward.getCommandRewards().remove(i - 1);
            plugin.rewards.put(rewardName, reward);
            plugin.rewards.saveAll();
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_reward_command_removed"));
        } catch (NumberFormatException e) {
            sender.sendMessage(plugin.getPrefix() + plugin.getLang().get("func_error_not_a_number"));
        }
    }
}
