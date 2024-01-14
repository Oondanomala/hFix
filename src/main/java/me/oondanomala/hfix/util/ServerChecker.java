package me.oondanomala.hfix.util;

import me.oondanomala.hfix.HFix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.regex.Pattern;

public class ServerChecker {
    private static final Pattern HYPIXEL_IP_REGEX = Pattern.compile("^(?:[^./]+\\.)?hypixel\\.net", Pattern.CASE_INSENSITIVE);

    @SubscribeEvent
    public void playerJoinedServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (event.isLocal) {
            HFix.instance.setOnHypixel(false);
            return;
        }
        ServerData currentServerData = Minecraft.getMinecraft().getCurrentServerData();
        if (currentServerData == null) {
            HFix.instance.setOnHypixel(false);
            return;
        }
        if (HYPIXEL_IP_REGEX.matcher(currentServerData.serverIP).matches()) {
            HFix.instance.setOnHypixel(true);
        } else {
            Util.registerEvents(new WorldServerChecker());
        }
    }

    @SubscribeEvent
    public void playerLeftServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        HFix.instance.setOnHypixel(false);
        HFix.instance.setOnHousing(false);
    }
}
