package me.oondanomala.hfix.location;

import me.oondanomala.hfix.HFix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.Locale;
import java.util.regex.Pattern;

public class LocationChecker {
    private static final Pattern HYPIXEL_IP_REGEX = Pattern.compile("^(?:[^.]+\\.)*hypixel\\.net$", Pattern.CASE_INSENSITIVE);

    private boolean needHypixelCheck;
    private boolean needHousingCheck;

    @SubscribeEvent
    public void onServerJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (event.isLocal) {
            HFix.instance.setOnHypixel(false);
            return;
        }

        ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
        if (serverData != null) {
            if (HYPIXEL_IP_REGEX.matcher(serverData.serverIP).matches()) {
                HFix.instance.setOnHypixel(true);
            } else {
                needHypixelCheck = true;
            }
        } else {
            HFix.instance.setOnHypixel(false);
        }
    }

    @SubscribeEvent
    public void onServerLeave(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        HFix.instance.setOnHypixel(false);
        HFix.instance.setOnHousing(false);
        needHypixelCheck = false;
        needHousingCheck = false;
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        needHousingCheck = true;
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        HFix.instance.setOnHousing(false);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        if (needHypixelCheck) {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            if (player != null && player.getClientBrand() != null) {
                HFix.instance.setOnHypixel(player.getClientBrand().toLowerCase(Locale.ENGLISH).contains("hypixel"));
                needHypixelCheck = false;
            }
        }
        if (needHousingCheck) {
            World world = Minecraft.getMinecraft().theWorld;
            if (world != null) {
                ScoreObjective scoreboard = world.getScoreboard().getObjectiveInDisplaySlot(1);
                if (scoreboard != null) {
                    HFix.instance.setOnHousing(StringUtils.stripControlCodes(scoreboard.getDisplayName()).equals("HOUSING"));
                    needHousingCheck = false;
                }
            }
        }
    }
}
