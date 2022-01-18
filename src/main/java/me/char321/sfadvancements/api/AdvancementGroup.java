package me.char321.sfadvancements.api;

import me.char321.sfadvancements.SFAdvancements;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AdvancementGroup {
    private final ItemStack display;
    private List<Advancement> advancements = new ArrayList<>();

    public AdvancementGroup(ItemStack display) {
        this.display = display;
    }

    public void register() {
        SFAdvancements.getRegistry().getAdvancementGroups().add(this);
    }

    public ItemStack getDisplayItem() {
        return display;
    }

    public List<Advancement> getAdvancements() {
        return advancements;
    }
}
