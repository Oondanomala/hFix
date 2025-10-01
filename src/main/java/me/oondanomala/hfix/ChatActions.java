package me.oondanomala.hfix;

import me.oondanomala.hfix.util.Util;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatActions {
    private static final Pattern CANT_BUILD_MESSAGE = Pattern.compile("^You can't build in this house!$|^You can't build while the house is in social mode!$");
    private static final Pattern JOIN_LEAVE_MESSAGES = Pattern.compile("^(?:\\[\\w+\\+*] )?\\w+ (?:entered|left) the world\\.$");
    private static final Pattern PARKOUR_COMPLETION_MESSAGE = Pattern.compile("^§r§\\w+\\u00A7r\\u00A7a completed the parkour in \\u00A7r\\u00A7e\\u00A7l\\d+:\\d\\d\\.\\d\\d\\d!\\u00A7r$");
    private static final Pattern RECEIVED_COOKIE_MESSAGE = Pattern.compile("^You received \\d\\d? cookies from (?:\\[\\w+\\+*] )?(\\w+)!$");
    private final Random random = new Random();
    private int lastAutoGGIndex = -1;

    @SubscribeEvent
    public void chatReceived(ClientChatReceivedEvent event) {
        if (event.type == 2) return;
        String chatMessage = event.message.getUnformattedText();
        String formattedChatMessage = event.message.getFormattedText();

        // Hide can't build message
        if (HFix.config.hideCantBuildMessage && CANT_BUILD_MESSAGE.matcher(chatMessage).matches()) {
            event.setCanceled(true);
        }
        // Hide join and leave messages
        else if (HFix.config.hideJoinAndLeaveMessage && JOIN_LEAVE_MESSAGES.matcher(chatMessage).matches()) {
            event.setCanceled(true);
        }
        // Parkour autoGG
        else if (HFix.config.parkourAutoGG && PARKOUR_COMPLETION_MESSAGE.matcher(formattedChatMessage).matches()) {
            if (HFix.config.autoGGMessages.length == 0) {
                Util.sendMessageToChat("/ac gg");
            } else if (HFix.config.autoGGMessages.length == 1) {
                Util.sendMessageToChat("/ac " + HFix.config.autoGGMessages[0]);
            } else {
                int index;
                do {
                    index = random.nextInt(HFix.config.autoGGMessages.length);
                } while (index == lastAutoGGIndex);
                lastAutoGGIndex = index;
                Util.sendMessageToChat("/ac " + HFix.config.autoGGMessages[index]);
            }
        }
        // Cookie autoTY
        else if (HFix.config.cookieAutoTy) {
            Matcher receivedCookieMatcher = RECEIVED_COOKIE_MESSAGE.matcher(chatMessage);
            if (receivedCookieMatcher.find()) {
                Util.sendMessageToChat("/ac ty " + receivedCookieMatcher.group(1));
            }
        }
    }
}
