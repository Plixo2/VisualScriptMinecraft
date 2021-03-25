package net.plixo.paper.client.forge.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.plixo.paper.Lodestone;
import net.plixo.paper.client.ui.GUI.GUIEditor;
import net.plixo.paper.client.avs.components.variable.Variable;
import net.plixo.paper.client.avs.components.variable.VariableType;
import net.plixo.paper.client.forge.KeyBinds;
import org.lwjgl.glfw.GLFW;

public class KeyInput {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {

        Variable keyVar = new Variable(VariableType.INT, "Key");

        keyVar.booleanValue = event.getAction() == GLFW.GLFW_PRESS;
        keyVar.intValue = event.getKey();
        //    KeyBinding
        //    TranslationTextComponent translationTextComponent = new TranslationTextComponent(keybinding.getKeyDescription());

        Lodestone.update("OnKey", keyVar);
        //   event.
        if (KeyBinds.openUI.isPressed()) {
            //   Minecraft.getInstance().player.abilities.isFlying = true;
            Minecraft.getInstance().displayGuiScreen(new GUIEditor());
        }
        // System.out.println("Hell");
        //Minecraft.getInstance().player.abilities.
    }
}
