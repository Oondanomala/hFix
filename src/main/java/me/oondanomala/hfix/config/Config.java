package me.oondanomala.hfix.config;

import me.oondanomala.hfix.HFix;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Config {
    public Configuration config;
    public boolean parkourAutoGG;
    public String[] autoGGMessages;
    public boolean hideCantBuildMessage;
    public boolean cookieAutoTy;
    public boolean noCookie;
    public String[] noCookieWhitelist;

    public Config(File configFile) {
        config = new Configuration(configFile);
        loadConfig();
    }

    private void loadConfig() {
        // Chat Actions
        config.setCategoryLanguageKey("chat", "config.hFix.category.chat");
        config.setCategoryPropertyOrder("chat", new ArrayList<>(Arrays.asList("Parkour AutoGG", "AutoGG Messages", "Hide Can't Build Message", "Cookie AutoTY")));
        parkourAutoGG = config.getBoolean("Parkour AutoGG", "chat", false, "Automatically says gg whenever someone finishes a parkour. Be careful with this, it can be spam triggered.");
        autoGGMessages = config.getStringList("AutoGG Messages", "chat", new String[]{"gg", "gg!", "GG", "nice"}, "List of gg messages to be sent upon parkour completion. (Minimum 4 messages recommended)");
        hideCantBuildMessage = config.getBoolean("Hide Can't Build Message", "chat", false, "Hides the \"You can't build in this house!\" message.");
        cookieAutoTy = config.getBoolean("Cookie AutoTY", "chat", false, "Automatically thanks guests for giving cookies.");
        // NoCookie
        config.setCategoryLanguageKey("nocookie", "config.hFix.category.nocookie");
        noCookie = config.getBoolean("NoCookie", "nocookie", false, "Prevents you from giving cookies to any owner not in the list.");
        noCookieWhitelist = config.getStringList("NoCookie Whitelist", "nocookie", new String[]{}, "List of owners to allow giving cookies to. Names must be lowercase!");

        config.save();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(HFix.MODID)) {
            loadConfig();
        }
    }
}
