package me.oondanomala.hfix.location;

import me.oondanomala.hfix.util.Util;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HousingChecker {
    @SubscribeEvent
    public void onWorldJoin(WorldEvent.Load event) {
        Util.registerEvents(new WorldHousingChecker());
    }
}
