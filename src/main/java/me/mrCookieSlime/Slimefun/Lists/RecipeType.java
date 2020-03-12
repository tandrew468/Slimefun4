package me.mrCookieSlime.Slimefun.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.cscorelib2.item.CustomItem;
import io.github.thebusybiscuit.cscorelib2.recipes.MinecraftRecipe;
import io.github.thebusybiscuit.slimefun4.implementation.items.altar.AltarRecipe;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunMachine;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public class RecipeType implements Keyed {

    public static final RecipeType MULTIBLOCK = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "multiblock"), new CustomItem(Material.BRICKS, "&bMultiBlock", "", "&a&oBuild it in the World"));
    public static final RecipeType ARMOR_FORGE = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "armor_forge"), (SlimefunItemStack) SlimefunItems.ARMOR_FORGE, "", "&a&oCraft it in an Armor Forge");
    public static final RecipeType GRIND_STONE = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "grind_stone"), (SlimefunItemStack) SlimefunItems.GRIND_STONE, "", "&a&oGrind it using the Grind Stone");
    public static final RecipeType SMELTERY = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "smeltery"), (SlimefunItemStack) SlimefunItems.SMELTERY, "", "&a&oSmelt it using a Smeltery");
    public static final RecipeType ORE_CRUSHER = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "ore_crusher"), (SlimefunItemStack) SlimefunItems.ORE_CRUSHER, "", "&a&oCrush it using the Ore Crusher");
    public static final RecipeType GOLD_PAN = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "gold_pan"), (SlimefunItemStack) SlimefunItems.GOLD_PAN, "", "&a&oUse a Gold Pan on Gravel to obtain this Item");
    public static final RecipeType COMPRESSOR = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "compressor"), (SlimefunItemStack) SlimefunItems.COMPRESSOR, "", "&a&oCompress it using the Compressor");
    public static final RecipeType PRESSURE_CHAMBER = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "pressure_chamber"), (SlimefunItemStack) SlimefunItems.PRESSURE_CHAMBER, "", "&a&oCompress it using the Pressure Chamber");
    public static final RecipeType MAGIC_WORKBENCH = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "magic_workbench"), (SlimefunItemStack) SlimefunItems.MAGIC_WORKBENCH, "", "&a&oCraft it in a Magic Workbench");
    public static final RecipeType ORE_WASHER = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "ore_washer"), (SlimefunItemStack) SlimefunItems.ORE_WASHER, "", "&a&oWash it in an Ore Washer");
    public static final RecipeType ENHANCED_CRAFTING_TABLE = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "enhanced_crafting_table"), (SlimefunItemStack) SlimefunItems.ENHANCED_CRAFTING_TABLE, "", "&a&oA regular Crafting Table cannot", "&a&ohold this massive Amount of Power...");
    public static final RecipeType JUICER = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "juicer"), (SlimefunItemStack) SlimefunItems.JUICER, "", "&a&oUsed for Juice Creation");

    public static final RecipeType ANCIENT_ALTAR = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "ancient_altar"), (SlimefunItemStack) SlimefunItems.ANCIENT_ALTAR, (recipe, output) -> {
        AltarRecipe altarRecipe = new AltarRecipe(Arrays.asList(recipe), output);
        SlimefunPlugin.getAncientAltarListener().getRecipes().add(altarRecipe);
    });

    public static final RecipeType MOB_DROP = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "mob_drop"), new CustomItem(Material.IRON_SWORD, "&bMob Drop"), RecipeType::registerMobDrop, "", "&rKill the specified Mob to obtain this Item");

    public static final RecipeType HEATED_PRESSURE_CHAMBER = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "heated_pressure_chamber"), (SlimefunItemStack) SlimefunItems.HEATED_PRESSURE_CHAMBER);
    public static final RecipeType FOOD_FABRICATOR = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "food_fabricator"), (SlimefunItemStack) SlimefunItems.FOOD_FABRICATOR);
    public static final RecipeType FOOD_COMPOSTER = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "food_composter"), (SlimefunItemStack) SlimefunItems.FOOD_COMPOSTER);
    public static final RecipeType FREEZER = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "freezer"), (SlimefunItemStack) SlimefunItems.FREEZER);

    public static final RecipeType GEO_MINER = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "geo_miner"), (SlimefunItemStack) SlimefunItems.GEO_MINER);
    public static final RecipeType NUCLEAR_REACTOR = new RecipeType(new NamespacedKey(SlimefunPlugin.instance, "nuclear_reactor"), (SlimefunItemStack) SlimefunItems.NUCLEAR_REACTOR);

    public static final RecipeType NULL = new RecipeType();

    private final ItemStack item;
    private final NamespacedKey key;
    private final String machine;
    private BiConsumer<ItemStack[], ItemStack> consumer;

    private RecipeType() {
        this.item = null;
        this.machine = "";
        this.key = new NamespacedKey(SlimefunPlugin.instance, "null");
    }

    public RecipeType(ItemStack item, String machine) {
        this.item = item;
        this.machine = machine;

        if (machine.length() > 0) {
            this.key = new NamespacedKey(SlimefunPlugin.instance, machine.toLowerCase());
        }
        else {
            this.key = new NamespacedKey(SlimefunPlugin.instance, "unknown");
        }
    }

    public RecipeType(NamespacedKey key, SlimefunItemStack slimefunItem, String... lore) {
        this(key, slimefunItem, null, lore);
    }

    public RecipeType(NamespacedKey key, ItemStack item, BiConsumer<ItemStack[], ItemStack> callback, String... lore) {
        this.item = new CustomItem(item, null, lore);
        this.key = key;
        this.consumer = callback;

        if (item instanceof SlimefunItemStack) {
            this.machine = ((SlimefunItemStack) item).getItemID();
        }
        else {
            this.machine = "";
        }
    }

    /**
     * @deprecated Use the constructor with {@link NamespacedKey} instead
     * @param item
     *            The {@link ItemStack} to use for this {@link RecipeType}
     */
    @Deprecated
    public RecipeType(ItemStack item) {
        this(item, "");
    }

    public RecipeType(NamespacedKey key, ItemStack item) {
        this.key = key;
        this.item = item;
        this.machine = "";
    }

    public RecipeType(MinecraftRecipe<?> recipe) {
        this.item = new ItemStack(recipe.getMachine());
        this.machine = "";
        this.key = NamespacedKey.minecraft(recipe.getRecipeClass().getSimpleName().toLowerCase(Locale.ROOT).replace("recipe", ""));
    }

    public void register(ItemStack[] recipe, ItemStack result) {
        if (consumer != null) {
            consumer.accept(recipe, result);
        }
        else {
            SlimefunItem slimefunItem = SlimefunItem.getByID(this.machine);

            if (slimefunItem instanceof SlimefunMachine) {
                ((SlimefunMachine) slimefunItem).addRecipe(recipe, result);
            }
        }
    }

    public ItemStack toItem() {
        return this.item;
    }

    public ItemStack getItem(Player p) {
        return SlimefunPlugin.getLocal().getRecipeTypeItem(p, this);
    }

    public SlimefunItem getMachine() {
        return SlimefunItem.getByID(machine);
    }

    @Override
    public NamespacedKey getKey() {
        return key;
    }

    private static void registerMobDrop(ItemStack[] recipe, ItemStack output) {
        String mob = null;

        try {
            mob = ChatColor.stripColor(recipe[4].getItemMeta().getDisplayName()).toUpperCase().replace(' ', '_');
            EntityType entity = EntityType.valueOf(mob);
            Set<ItemStack> dropping = SlimefunPlugin.getRegistry().getMobDrops().getOrDefault(entity, new HashSet<>());
            dropping.add(output);
            SlimefunPlugin.getRegistry().getMobDrops().put(entity, dropping);
        }
        catch (Exception x) {
            Slimefun.getLogger().log(Level.WARNING, "An Exception occured when setting a Drop for the Mob Type: \"" + mob + "\"", x);
        }
    }

    public static List<ItemStack> getRecipeInputs(SlimefunMachine machine) {
        if (machine == null) return new ArrayList<>();
        List<ItemStack[]> recipes = machine.getRecipes();
        List<ItemStack> convertible = new ArrayList<>();

        for (int i = 0; i < recipes.size(); i++) {
            if (i % 2 == 0) convertible.add(recipes.get(i)[0]);
        }

        return convertible;
    }

    public static List<ItemStack[]> getRecipeInputList(SlimefunMachine machine) {
        if (machine == null) return new ArrayList<>();
        List<ItemStack[]> recipes = machine.getRecipes();
        List<ItemStack[]> convertible = new ArrayList<>();

        for (int i = 0; i < recipes.size(); i++) {
            if (i % 2 == 0) convertible.add(recipes.get(i));
        }

        return convertible;
    }

    public static ItemStack getRecipeOutput(SlimefunMachine machine, ItemStack input) {
        List<ItemStack[]> recipes = machine.getRecipes();
        return recipes.get(((getRecipeInputs(machine).indexOf(input) * 2) + 1))[0].clone();
    }

    public static ItemStack getRecipeOutputList(SlimefunMachine machine, ItemStack[] input) {
        List<ItemStack[]> recipes = machine.getRecipes();
        return recipes.get(((getRecipeInputList(machine).indexOf(input) * 2) + 1))[0];
    }
}
