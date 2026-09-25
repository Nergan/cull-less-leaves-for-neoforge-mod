package com.culllessleaves.mod

import com.culllessleaves.mod.config.ClientConfig
import com.culllessleaves.mod.event.ModSetup
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(CullLessLeavesMod.MOD_ID)
class CullLessLeavesMod(modEventBus: IEventBus, modContainer: ModContainer) {
    companion object {
        const val MOD_ID = "culllessleaves"

        @JvmField
        val LOGGER: Logger = LogManager.getLogger(MOD_ID)
    }

    init {
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC)
        ModSetup.init(modEventBus, modContainer)
    }
}
