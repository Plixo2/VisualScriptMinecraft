package net.plixo.lodestone.client.util.io;

import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class KeyboardUtil {
    public static boolean isKeyDown(int key) {
        int state = GLFW.glfwGetKey(Minecraft.getInstance().getMainWindow().getHandle(), key);
        return state == GLFW.GLFW_PRESS;
    }

}
