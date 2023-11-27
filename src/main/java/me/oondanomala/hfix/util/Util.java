package me.oondanomala.hfix.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final Pattern HOUSE_OWNER_REGEX = Pattern.compile("^You are in .+, by (.+)", Pattern.MULTILINE);

    public static String removeRank(String name) {
        // FIXME: Make it work for names with multiple tags (eg [WINNER] [MVP++] Oondanomala)
        // Maybe split by space and get the last one?
        try {
            return name.split("] ")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return name;
        }
    }

    public static String getHousingOwnerName() {
        ChatComponentText footer = ReflectionHelper.getPrivateValue(GuiPlayerTabOverlay.class, Minecraft.getMinecraft().ingameGUI.getTabList(), "field_175255_h", "footer");
        Matcher ownerMatcher = HOUSE_OWNER_REGEX.matcher(footer.getUnformattedText());
        if (ownerMatcher.find()) {
            return removeRank(ownerMatcher.group(1));
        }
        return null;
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
