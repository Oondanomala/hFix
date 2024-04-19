package me.oondanomala.hfix;

import me.oondanomala.hfix.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PlayerDetector {
    private Set<String> tabList = new HashSet<>();

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (HFix.config.playerDetector && event.phase == TickEvent.Phase.END) {
            if (Minecraft.getMinecraft().thePlayer == null) return;

            Set<String> set = new HashSet<>();
            for (NetworkPlayerInfo info : Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap()) {
                String name = info.getGameProfile().getName();

                if (!tabList.contains(name) && Arrays.asList(HFix.config.playerDetectorNames).contains(name.toLowerCase())) {
                    // FIXME: Sometimes the tab gets loaded in multiple ticks, and some players that were in the server get treated like they just joined
                    if (tabList.isEmpty()) {
                        Util.showChatMessage(EnumChatFormatting.RED + "WARNING: Player " + EnumChatFormatting.AQUA + name + EnumChatFormatting.RED + " is in the server.");
                    } else {
                        Util.showChatMessage(EnumChatFormatting.RED + "WARNING: Player " + EnumChatFormatting.AQUA + name + EnumChatFormatting.RED + " joined the server.");
                    }
                    if (HFix.config.playerDetectorSound) {
                        Minecraft.getMinecraft().thePlayer.playSound("random.orb", 1, 0.5f);
                    }
                }
                set.add(name);
            }

            tabList = set;
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        tabList.clear();
    }
}
