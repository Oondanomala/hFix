package me.oondanomala.hfix.util;

import me.oondanomala.hfix.HFix;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class WorldServerChecker {
    private int counter = 20;
    private int tries = 5;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (counter != 0) {
            --counter;
        } else {
            if (Minecraft.getMinecraft().thePlayer == null) {
                counter = 20;
                --tries;
            } else {
                HFix.instance.setOnHypixel(Minecraft.getMinecraft().thePlayer.getClientBrand().toLowerCase().contains("hypixel"));
                Util.unregisterEvents(this);
            }
        }
        if (tries == 0) {
            HFix.instance.setOnHypixel(false);
            Util.unregisterEvents(this);
        }
    }
}
