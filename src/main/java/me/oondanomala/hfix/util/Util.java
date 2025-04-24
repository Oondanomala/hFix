package me.oondanomala.hfix.util;

import me.oondanomala.hfix.HFix;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Util {
    private static final Pattern HOUSE_OWNER_REGEX = Pattern.compile("^You are in .+, by (?:\\[\\w+\\+*] )?(\\w+)", Pattern.MULTILINE);

    public static String getHousingOwnerName() {
        Matcher ownerMatcher = HOUSE_OWNER_REGEX.matcher(Minecraft.getMinecraft().ingameGUI.getTabList().footer.getUnformattedText());
        if (ownerMatcher.find()) {
            return ownerMatcher.group(1);
        }
        return null;
    }

    public static void showChatMessage(String message) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "[" + EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + HFix.NAME + EnumChatFormatting.GRAY + "] " + message));
    }

    public static void sendMessageToChat(String message) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
    }

    public static void registerEvents(Object... events) {
        for (Object event : events) {
            MinecraftForge.EVENT_BUS.register(event);
        }
    }

    public static void unregisterEvents(Object... events) {
        for (Object event : events) {
            MinecraftForge.EVENT_BUS.unregister(event);
        }
    }
}
