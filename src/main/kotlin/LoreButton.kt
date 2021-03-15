import events.ButtonPress
import org.bukkit.plugin.java.JavaPlugin

class LoreButton : JavaPlugin(){

    override fun onEnable() {
        saveDefaultConfig()
        val blockType: String = config.getString("blockType") ?: "IRON_BLOCK"
        server.pluginManager.registerEvents(ButtonPress(blockType), this)
    }
}