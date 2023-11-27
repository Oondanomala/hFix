package me.oondanomala.hfix.config;

import me.oondanomala.hfix.HFix;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class HFixConfig extends GuiConfig {
    public HFixConfig(GuiScreen parent) {
        super(parent,
                getCategories(),
                HFix.MODID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(HFix.config.config.toString()),
                "hFix Config");
    }

    private static List<IConfigElement> getCategories() {
        List<IConfigElement> list = new ArrayList<>();
        list.add(new ConfigElement(HFix.config.config.getCategory("chat")));
        list.add(new ConfigElement(HFix.config.config.getCategory("nocookie")));
        return list;
    }
}
