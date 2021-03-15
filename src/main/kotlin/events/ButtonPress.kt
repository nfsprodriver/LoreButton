package events

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.EntityType
import org.bukkit.entity.Minecart
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.material.Button

class ButtonPress(private val blockType: String) : Listener {

    @EventHandler
    fun buttonPress(event: PlayerInteractEvent) {
        val clickedBlock: Block? = event.clickedBlock
        if (event.action == Action.RIGHT_CLICK_BLOCK && clickedBlock != null && clickedBlock.type.name.endsWith("BUTTON")) {
            val button: Button = Button(clickedBlock.type, clickedBlock.data)
            val block: Block = clickedBlock.getRelative(button.attachedFace)
            if (block.type.name == blockType) {
                BlockFace.values().forEach { blockFace ->
                    if (block.getRelative(blockFace).type.name.endsWith("RAIL")) {
                        val rail: Block = block.getRelative(blockFace)
                        val railLoc: Location = rail.location
                        val loreLoc = Location(railLoc.world, railLoc.x, railLoc.y + 1.0, railLoc.z)
                        if (railLoc.x == block.location.x || railLoc.z == block.location.z) {
                            val nearbyLores: Int = loreLoc.getNearbyEntitiesByType(Minecart::class.java, 5.0).count()
                            if (nearbyLores < 1) {
                                loreLoc.world.spawnEntity(loreLoc, EntityType.MINECART)
                                event.player.sendTitle("Good ride!", "", 20, 60, 20)
                            } else {
                                event.player.sendTitle("Too many carts!", "Please use or remove nearby cart.", 20, 100, 20)
                            }
                        }
                    }
                }
            }
        }
    }
}