package club.someoneice.djrl;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public final class ModEvent {
  static final ISound SOUND =
      PositionedSoundRecord.func_147673_a(new ResourceLocation(ModMain.MODID, "dj_rol"));
  
  @SubscribeEvent
  public void playerHurtEvent(final LivingHurtEvent event) {
    final EntityLivingBase living = event.entityLiving;
    if (!(living instanceof EntityPlayerMP)) {
      return;
    }

    if (living.getHealth() - event.ammount > ModConfig.MIN_HEALTH) {
      return;
    }

    ModMain.NETWORK.sendTo(new ModSoundPacket(true), (EntityPlayerMP) living);
  }

  @SubscribeEvent
  public void playerHealEvent(final LivingHealEvent event) {
    final EntityLivingBase living = event.entityLiving;
    if (!(living instanceof EntityPlayerMP)) {
      return;
    }

    if (living.getHealth() + event.amount <= ModConfig.MIN_HEALTH) {
      return;
    }
    
    ModMain.NETWORK.sendTo(new ModSoundPacket(false), (EntityPlayerMP) living);
  }

  @SubscribeEvent
  public void playerRespawnEvent(final PlayerEvent.PlayerRespawnEvent event) {
    ModMain.NETWORK.sendTo(new ModSoundPacket(false), (EntityPlayerMP) event.player);
  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onPlayerChangedWorld(final TickEvent.RenderTickEvent event) {
    if (event.phase == TickEvent.Phase.END) {
      return;
    }

    if (Objects.nonNull(BossStatus.bossName)) {
      Minecraft.getMinecraft().getSoundHandler().playSound(SOUND);
      return;
    }
    
    if (Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(SOUND)) {
      Minecraft.getMinecraft().getSoundHandler().stopSound(SOUND);
    }
  }
}
