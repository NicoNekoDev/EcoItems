package com.willfp.ecoitems.items

import com.willfp.eco.core.Prerequisite
import com.willfp.eco.core.config.interfaces.Config
import com.willfp.eco.core.items.Items
import com.willfp.eco.core.recipe.Recipes
import com.willfp.libreforge.loader.LibreforgePlugin
import com.willfp.libreforge.loader.configs.ConfigCategory
import com.willfp.libreforge.loader.configs.LegacyLocation
import org.bukkit.Bukkit
import org.bukkit.entity.Entity

object EcoItemsRecipes : ConfigCategory("recipe", "recipes") {
    override val legacyLocation = LegacyLocation(
        "items.yml",
        "recipes"
    )

    override fun clear(plugin: LibreforgePlugin) {
        // Do nothing.
    }

    override fun acceptConfig(plugin: LibreforgePlugin, id: String, config: Config) {
        val result = Items.lookup(config.getString("result"))
        val item = result.item.clone()

        if (config.has("recipe-give-amount")) {
            item.amount = config.getInt("recipe-give-amount") // Legacy
        }

        if (Prerequisite.HAS_FOLIA.isMet) {
            plugin.scheduler.runTask(Bukkit.getOnlinePlayers().map { it as Entity }) {
                Recipes.createAndRegisterRecipe(
                    plugin, id, item, config.getStrings("recipe"), config.getStringOrNull("permission")
                )
            }
        } else {
            Recipes.createAndRegisterRecipe(
                plugin, id, item, config.getStrings("recipe"), config.getStringOrNull("permission")
            )
        }

    }
}
