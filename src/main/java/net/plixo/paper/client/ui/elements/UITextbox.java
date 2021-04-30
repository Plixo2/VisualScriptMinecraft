package net.plixo.paper.client.ui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;


/**
 *  for editing and displaying text in the UI
 *  using Minecraft {@code TextFieldWidget}
 **/
public class UITextbox extends UIElement {


    public TextFieldWidget field;

    public UITextbox(int id) {
        super(id);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, ColorLib.getBackground(0.3f));

        int color = ColorLib.interpolateColorAlpha(0x00000000, 0x23000000, hoverProgress / 100f);
        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, color);
        Gui.drawLinedRoundedRect(x, y, x + width, y + height, roundness, 0x6F000000, 1);

        field.render(Gui.matrixStack, (int) mouseX, (int) mouseY, 0);

        super.drawScreen(mouseX, mouseY);
    }


    //get the field context
    public String getText() {
        return field.getText();
    }

    //inputs
    @Override
    public void keyTyped(char typedChar, int keyCode) {
        field.charTyped(typedChar, keyCode);
        textFieldChanged();
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void keyPressed(int key, int scanCode, int action) {
        field.keyPressed(key, scanCode, action);
        textFieldChanged();
        super.keyPressed(key, scanCode, action);
    }

    //triggered after text field has changed (for abstract use)
    public void textFieldChanged() {
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        field.mouseClicked((int) mouseX, (int) mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    //set text field dimensions
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        field = new TextFieldWidget(Minecraft.getInstance().fontRenderer, (int) x + 4, (int) (y + height / 2) - 4, (int) width - 8, (int) height / 2, new StringTextComponent(""));
        //disable background
        field.setEnableBackgroundDrawing(false);
        field.setTextColor(textColor);
        field.setMaxStringLength(100000);
        super.setDimensions(x, y, width, height);
    }

    public void setText(String txt) {
        field.setText(txt);
    }
}
