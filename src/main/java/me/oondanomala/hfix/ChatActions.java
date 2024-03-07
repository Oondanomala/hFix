package me.oondanomala.hfix;

import me.oondanomala.hfix.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatActions {
    private static final Pattern CANT_BUILD_MESSAGE = Pattern.compile("^You can't build in this house!$|^You can't build while the house is in social mode!$");
    private static final Pattern PARKOUR_COMPLETION_MESSAGE = Pattern.compile(".+\\u00A7r\\u00A7a completed the parkour in \\u00A7r\\u00A7e\\u00A7l\\d+:\\d\\d\\.\\d\\d\\d!\\u00A7r$");
    private static final Pattern RECEIVED_COOKIE_MESSAGE = Pattern.compile("^You received \\d\\d? cookies from (.+)!$");

    private static int LAST_INDEX = -1;

    @SubscribeEvent
    public void chatReceived(ClientChatReceivedEvent event) {
        if (event.type == 2) return;

        // Hide can't build message
        if (HFix.config.hideCantBuildMessage && CANT_BUILD_MESSAGE.matcher(event.message.getUnformattedText()).matches()) {
            event.setCanceled(true);
        }
        // Parkour autoGG
        // TODO: Handle duplicate messages
        else if (HFix.config.parkourAutoGG && PARKOUR_COMPLETION_MESSAGE.matcher(event.message.getFormattedText()).matches()) {

            List<String> ggList = Arrays.asList("gg", "nice", "gj", "sweet", "rad", "sick");

            if (HFix.config.ggList.length != 0) {
                ggList = (Arrays.asList(HFix.config.ggList));
            }

            Random rn = new Random();
            int index = (rn.nextInt(ggList.size() + 1) - 1);

            if (index == LAST_INDEX) {
                ++index;
                if (index >= ggList.size()) {
                    --index;
                }
            }
            LAST_INDEX = index;

            Minecraft.getMinecraft().thePlayer.sendChatMessage("/ac " + ggList.get(index));
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