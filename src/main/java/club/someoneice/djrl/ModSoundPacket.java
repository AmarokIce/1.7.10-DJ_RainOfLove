package club.someoneice.djrl;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public final class ModSoundPacket
    implements IMessage, IMessageHandler<ModSoundPacket, ModSoundPacket> {
  private static final ISound SOUND =
      PositionedSoundRecord.func_147673_a(new ResourceLocation(ModMain.MODID, "dj_rol"));

  boolean flag;

  public ModSoundPacket() {}

  public ModSoundPacket(final boolean flag) {
    this.flag = flag;
  }

  @Override
  public void fromBytes(ByteBuf byteBuf) {
    flag = byteBuf.readBoolean();
  }

  @Override
  public void toBytes(ByteBuf byteBuf) {
    byteBuf.writeBoolean(flag);
  }

  @Override
  public ModSoundPacket onMessage(ModSoundPacket modSoundPacket, MessageContext messageContext) {
    if (!modSoundPacket.flag) {
      Minecraft.getMinecraft().getSoundHandler().stopSound(SOUND);
      return null;
    }
    
    if (Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(SOUND)) {
      return null;
    }
    
    Minecraft.getMinecraft().getSoundHandler().playSound(SOUND);
    return null;
  }
}
