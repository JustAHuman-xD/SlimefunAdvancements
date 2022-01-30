package me.char321.sfadvancements.api.criteria;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * what? an actually practical criterion? no way
 * this criterion is completed whenever someone places a specified item a specified number of times
 */
public class PlaceCriterion extends Criterion {
    private final ItemStack item;

    /**
     * creates a placecriterion
     *
     * @param id the id of the criterion (should be unique per advancement)
     * @param amount the amount of times someone needs to place the item
     * @param item the material of the item that players must place to perform the criterion (vanilla only)
     */
    public PlaceCriterion(String id, int amount, Material item) {
        this(id, amount, new ItemStack(item));
    }

    /**
     * creates a placecriterion
     *
     * @param id the id of the criterion (should be unique per advancement)
     * @param amount the amount of times someone needs to place the item
     * @param item the item that players must place to perform the criterion
     */
    public PlaceCriterion(String id, int amount, ItemStack item) {
        super(id, amount);
        this.item = item;
    }

    /**
     * returns the item that players must place to complete this criterion
     *
     * @return the item that players must place to complete this criterion
     */
    public ItemStack getItem() {
        return item;
    }
}
