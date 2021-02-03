package net.plixo.paper.client.forge;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class Keybinds {
    public static KeyBinding openUI;

    public static void register() {

        openUI = new KeyBinding("open.engine", GLFW.GLFW_KEY_RIGHT_SHIFT, "key.categories.misc");
        ClientRegistry.registerKeyBinding(openUI);
    }
}
