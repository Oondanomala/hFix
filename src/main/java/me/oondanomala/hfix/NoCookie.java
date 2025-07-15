package me.oondanomala.hfix;

import me.oondanomala.hfix.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoCookie {
    private boolean isHoldingCookie(EntityPlayer player) {
        ItemStack heldItem = player.getCurrentEquippedItem();
        if (heldItem == null || heldItem.getTagCompound() == null) return false;
        return heldItem.getTagCompound().getCompoundTag("ExtraAttributes").getByte("COOKIE_ITEM") == 1;
    }

    @SubscribeEvent
    public void cookieUsed(PlayerInteractEvent event) {
        if (HFix.config.noCookie && event.action != PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
            if (isHoldingCookie(event.entityPlayer)) {
                String ownerName = Util.getHousingOwnerName();
                if (ownerName == null) return;
                if (!HFix.config.noCookieWhitelist.contains(ownerName.toLowerCase())) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
