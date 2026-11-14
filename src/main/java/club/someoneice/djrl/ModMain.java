package club.someoneice.djrl;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = ModMain.MODID)
public final class ModMain {
  public static final String MODID = "dj_raininglove";

  public static final SimpleNetworkWrapper NETWORK =
      NetworkRegistry.INSTANCE.newSimpleChannel(ModMain.MODID);

  @Mod.EventHandler
  public void preInit(final FMLPreInitializationEvent event) {
//    if (!event.getSide().isClient()) {
//      throw new IllegalStateException("Don't install this mod in Dedicated Server!");
//    }

    final ModEvent modEvent = new ModEvent();
    FMLCommonHandler.instance().bus().register(modEvent);
    MinecraftForge.EVENT_BUS.register(modEvent);

    NETWORK.registerMessage(ModSoundPacket.class, ModSoundPacket.class, 0, Side.CLIENT);
  }
}
