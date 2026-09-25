package com.culllessleaves.mod.client

import com.culllessleaves.mod.config.ClientConfig
import net.minecraft.client.Minecraft
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.ModContainer
import net.neoforged.fml.event.config.ModConfigEvent
import net.neoforged.neoforge.client.gui.ConfigurationScreen
import net.neoforged.neoforge.client.gui.IConfigScreenFactory

object ClientModEvents {
    fun init(modBus: IEventBus, modContainer: ModContainer) {
        modBus.register(this)
        modContainer.registerExtensionPoint(
            IConfigScreenFactory::class.java,
            IConfigScreenFactory { container, currentScreen -> ConfigurationScreen(container, currentScreen) },
        )
    }

    @SubscribeEvent
    fun onConfigReload(event: ModConfigEvent.Reloading) {
        if (event.config.spec != ClientConfig.SPEC) return
        val minecraft = Minecraft.getInstance()
        if (minecraft.level != null) {
            minecraft.levelRenderer.allChanged()
        }
    }
}
