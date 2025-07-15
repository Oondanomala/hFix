package me.oondanomala.hfix.config;

import me.oondanomala.hfix.HFix;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class Config {
    public final Configuration config;

    public boolean parkourAutoGG;
    public String[] autoGGMessages;
    public boolean hideCantBuildMessage;
    public boolean hideJoinAndLeaveMessage;
    public boolean cookieAutoTy;
    public boolean noCookie;
    public Set<String> noCookieWhitelist;
    public boolean playerDetector;
    public Set<String> playerDetectorNames;
    public boolean playerDetectorSound;

    public Config(File configFile) {
        config = new Configuration(configFile);
        loadConfig();
    }

    private void loadConfig() {
        // Chat Actions
        config.setCategoryLanguageKey("chat", "config.hFix.category.chat");
        config.setCategoryPropertyOrder("chat", new ArrayList<>(Arrays.asList("Parkour AutoGG", "AutoGG Messages", "Hide Can't Build Message", "Hide Join and Leave Messages", "Cookie AutoTY")));
        parkourAutoGG = config.getBoolean(
                "Parkour AutoGG",
                "chat",
                false,
                "Automatically says gg whenever someone finishes a parkour."
        );
        autoGGMessages = config.getStringList(
                "AutoGG Messages",
                "chat",
                new String[]{"gg", "gg!", "GG", "nice"},
                "List of gg messages to be sent upon parkour completion. (Minimum 4 messages recommended)"
        );
        hideCantBuildMessage = config.getBoolean(
                "Hide Can't Build Message",
                "chat",
                false,
                "Hides the \"You can't build in this house!\" message."
        );
        hideJoinAndLeaveMessage = config.getBoolean(
                "Hide Join and Leave Messages",
                "chat",
                false,
                "Hides the house join and leave messages."
        );
        cookieAutoTy = config.getBoolean(
                "Cookie AutoTY",
                "chat",
                false,
                "Automatically thanks guests for giving cookies."
        );

        // NoCookie
        config.setCategoryLanguageKey("nocookie", "config.hFix.category.nocookie");
        noCookie = config.getBoolean(
                "NoCookie",
                "nocookie",
                false,
                "Prevents you from giving cookies to any owner not in the list."
        );
        noCookieWhitelist = Arrays.stream(config.getStringList(
                "NoCookie Whitelist",
                "nocookie",
                new String[]{},
                "List of owners to allow giving cookies to. Case insensitive."
        )).map(s -> s.toLowerCase(Locale.ENGLISH)).collect(Collectors.toSet());

        // PlayerDetector
        config.setCategoryLanguageKey("playerdetector", "config.hFix.category.playerdetector");
        config.setCategoryPropertyOrder("playerdetector", new ArrayList<>(Arrays.asList("Player Detector", "Play Sound", "Players to Detect")));
        playerDetector = config.getBoolean(
                "Player Detector",
                "playerdetector",
                false,
                "Warns you when a player in the list joins the server you are in."
        );
        playerDetectorSound = config.getBoolean(
                "Play Sound",
                "playerdetector",
                true,
                "Plays a sound when a player is detected."
        );
        playerDetectorNames = Arrays.stream(config.getStringList(
                "Players to Detect",
                "playerdetector",
                new String[]{},
                "List of players to detect. Case insensitive."
        )).map(s -> s.toLowerCase(Locale.ENGLISH)).collect(Collectors.toSet());

        config.save();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(HFix.MODID)) {
            loadConfig();
        }
    }
}
