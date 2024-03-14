package me.oondanomala.hfix;

import me.oondanomala.hfix.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatActions {
    private static final Pattern CANT_BUILD_MESSAGE = Pattern.compile("^You can't build in this house!$|^You can't build while the house is in social mode!$");
    private static final Pattern PARKOUR_COMPLETION_MESSAGE = Pattern.compile(".+\\u00A7r\\u00A7a completed the parkour in \\u00A7r\\u00A7e\\u00A7l\\d+:\\d\\d\\.\\d\\d\\d!\\u00A7r$");
    private static final Pattern RECEIVED_COOKIE_MESSAGE = Pattern.compile("^You received \\d\\d? cookies from (.+)!$");
    private final Random random = new Random();
    private int lastAutoGGIndex = -1;

    @SubscribeEvent
    public void chatReceived(ClientChatReceivedEvent event) {
        if (event.type == 2) return;

        // Hide can't build message
        if (HFix.config.hideCantBuildMessage && CANT_BUILD_MESSAGE.matcher(event.message.getUnformattedText()).matches()) {
            event.setCanceled(true);
        }
        // Parkour autoGG
        else if (HFix.config.parkourAutoGG && PARKOUR_COMPLETION_MESSAGE.matcher(event.message.getFormattedText()).matches()) {
            if (HFix.config.autoGGMessages.length == 0) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/ac gg");
            } else if (HFix.config.autoGGMessages.length == 1) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/ac " + HFix.config.autoGGMessages[0]);
            } else {
                int index;
                do {
                    index = random.nextInt(HFix.config.autoGGMessages.length);
                } while (index == lastAutoGGIndex);
                lastAutoGGIndex = index;
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/ac " + HFix.config.autoGGMessages[index]);
            }
        }
        // Cookie autoTY
        else if (HFix.config.cookieAutoTy) {
            Matcher receivedCookieMatcher = RECEIVED_COOKIE_MESSAGE.matcher(event.message.getUnformattedText());
            if (receivedCookieMatcher.find()) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/ac ty " + Util.removeRank(receivedCookieMatcher.group(1)));
            }
        }
    }
}
