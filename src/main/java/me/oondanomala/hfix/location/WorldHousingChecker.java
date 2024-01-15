package me.oondanomala.hfix.location;

import me.oondanomala.hfix.HFix;
import me.oondanomala.hfix.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class WorldHousingChecker {
    private int counter = 20;
    private int tries = 5;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (counter != 0) {
            --counter;
        } else {
            if (Minecraft.getMinecraft().theWorld == null) {
                counter = 20;
                --tries;
                return;
            }
            ScoreObjective scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
            if (scoreboard == null) {
                counter = 20;
                --tries;
            } else {
                HFix.instance.setOnHousing(StringUtils.stripControlCodes(scoreboard.getDisplayName()).equals("HOUSING"));
                Util.unregisterEvents(this);
            }
        }
        if (tries == 0) {
            HFix.instance.setOnHousing(false);
            Util.unregisterEvents(this);
        }
    }
}
