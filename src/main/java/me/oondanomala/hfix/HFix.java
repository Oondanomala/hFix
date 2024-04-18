package me.oondanomala.hfix;

import me.oondanomala.hfix.config.Config;
import me.oondanomala.hfix.location.HousingChecker;
import me.oondanomala.hfix.location.ServerChecker;
import me.oondanomala.hfix.location.WorldHousingChecker;
import me.oondanomala.hfix.util.Util;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = HFix.MODID, name = HFix.NAME, version = HFix.VERSION, acceptedMinecraftVersions = "1.8", clientSideOnly = true, guiFactory = "me.oondanomala.hfix.config.GuiFactory", updateJSON = "https://raw.githubusercontent.com/Oondanomala/hFix/master/versions.json")
public class HFix {
    public static final String MODID = "hfix";
    public static final String NAME = "hFix";
    public static final String VERSION = "1.0.1";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    @Mod.Instance(MODID)
    public static HFix instance;
    public static Config config;
    private final Object[] hypixelEventList = {new HousingChecker(), new WorldHousingChecker()};
    private final Object[] housingEventList = {new ChatActions(), new NoCookie()};
    public boolean isOnHypixel = false;
    public boolean isOnHousing = false;

    public void setOnHypixel(boolean onHypixel) {
        if (onHypixel) {
            Util.registerEvents(hypixelEventList);
        } else {
            Util.unregisterEvents(hypixelEventList);
        }
        isOnHypixel = onHypixel;
    }

    public void setOnHousing(boolean onHousing) {
        if (onHousing) {
            Util.registerEvents(housingEventList);
        } else {
            Util.unregisterEvents(housingEventList);
        }
        isOnHousing = onHousing;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Config(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Util.registerEvents(config, new ServerChecker());
    }
}
