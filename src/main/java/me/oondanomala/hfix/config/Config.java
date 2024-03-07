package me.oondanomala.hfix.config;

import me.oondanomala.hfix.HFix;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class Config {
    public Configuration config;
    public boolean parkourAutoGG;
    public boolean hideCantBuildMessage;
    public boolean cookieAutoTy;
    public boolean noCookie;
    public String[] noCookieWhitelist;

    public String[] ggList;

    public Config(File configFile) {
        config = new Configuration(configFile);
        loadConfig();
    }

    private void loadConfig() {
        //config.setCategoryPropertyOrder("chat", Arrays.asList("Parkour AutoGG", "Hide Can't Build Message", "Cookie AutoTY"));

        // Chat Actions
        config.setCategoryLanguageKey("chat", "config.hFix.category.chat");
        parkourAutoGG = config.getBoolean("Parkour AutoGG", "chat", true, "Automatically says gg whenever someone finishes a parkour. Be careful with this, it can be spam triggered.");
        hideCantBuildMessage = config.getBoolean("Hide Can't Build Message", "chat", false, "Hides the \"You can't build in this house!\" message.");
        cookieAutoTy = config.getBoolean("Cookie AutoTY", "chat", false, "Automatically thanks guests for giving cookies.");
        // NoCookie
        config.setCategoryLanguageKey("nocookie", "config.hFix.category.nocookie");
        noCookie = config.getBoolean("NoCookie", "nocookie", false, "Prevents you from giving cookies to any owner not in the list.");
        noCookieWhitelist = config.getStringList("NoCookie Whitelist", "nocookie", new String[]{}, "List of owners to allow giving cookies to. Names must be lowercase!");

        ggList = config.getStringList("Auto GG list", "chat", new String[]{}, "List of gg messages to be displayed upon pk completion. (Minimum 4 messages recommended)");

        config.save();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(HFix.MODID)) {
            loadConfig();
        }
    }
}
