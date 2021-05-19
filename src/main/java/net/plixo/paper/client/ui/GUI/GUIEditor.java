package net.plixo.paper.client.ui.GUI;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.Lodestone;
import net.plixo.paper.client.tabs.*;
import net.plixo.paper.client.ui.TabbedUI;
import net.plixo.paper.client.ui.UITab;
import net.plixo.paper.client.ui.other.Toolbar;
import net.plixo.paper.client.ui.other.UIMouseMenu;
import net.plixo.paper.client.util.*;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class GUIEditor extends Screen {

    public static GUIEditor instance;
    UIMouseMenu menu;

    public GUIEditor() {
        super(new StringTextComponent("UI"));
        this.instance = this;
    }


    //static Minecraft mc = Minecraft.getInstance();

    public ArrayList<TabbedUI> tabs = new ArrayList<>();
    Toolbar toolbar;

    @Override
    public boolean isPauseScreen() {
        return false;
    }


    @Override
    public void render(MatrixStack p_230430_1_, int mouseX, int mouseY, float partialTicks) {

        Gui.setMatrix(p_230430_1_);

        Gui.drawRect(0, 0, width, height, ColorLib.getBackground(0.2f));
        toolbar.drawScreen(mouseX, mouseY);

        for (TabbedUI tab : tabs) {
            GL11.glPushMatrix();
            GL11.glTranslated(tab.x, tab.y, 0);
            tab.drawScreen(mouseX, mouseY);
            GL11.glPopMatrix();
        }
        if (menu != null) {
            menu.drawScreen(mouseX, mouseY);
        }
        MouseUtil.resetWheel();
        super.render(p_230430_1_, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void init() {

        tabs.clear();

        float side = width * 0.2f;
        float heightSide = height * 0.33f;

        toolbar = new Toolbar(0);
        toolbar.setDimensions(0, 0, width, 20);

        TabbedUI background = new TabbedUI(super.width - side, super.height - (heightSide + 10), "Project");
        background.y = 30;
        background.x = side;

        background.addTab(new TabViewport(0));
        background.addTab(new TabEditor(1));


        TabbedUI explorer = new TabbedUI(side, (this.height / 2f) - 12, "Test0");
        explorer.y = 30;

        // Tab b0 = new TabEvents(0);
        UITab b2 = new TabExplorer(0);
        // explorer.addTab(b0);
        explorer.addTab(b2);

        TabbedUI console = new TabbedUI(this.width - side, heightSide - 30, "Test0");
        console.y = this.height - (heightSide - 30);
        console.x = side;

        UITab b3 = new TabFiles(0);
        UITab b7 = new TabConsole(1);
        console.addTab(b3);
        console.addTab(b7);

        TabbedUI modules = new TabbedUI(side, this.height / 2.f - 30, "Test0");
        modules.y = this.height / 2.f + 30;

        UITab b4 = new TabInspector(0);
        modules.addTab(b4);

        tabs.add(background);
        tabs.add(explorer);
        tabs.add(console);
        tabs.add(modules);


        super.init();
    }

    @Override
    public boolean mouseScrolled(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
        MouseUtil.addDWheel((float) p_231043_5_);
        return false;
    }

    @Override
    public boolean charTyped(char typedChar, int keyCode) {

        try {
            for (TabbedUI tab : tabs) {
                tab.keyTyped(typedChar, keyCode);
            }
        } catch (Exception e) {
            Util.print(e);
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int action) {

        try {
            if (key == 280 && KeyboardUtil.isKeyDown(key)) {
                Util.print("stopped");

            }
            for (TabbedUI tab : tabs) {
                tab.keyPressed(key, scanCode, action);
            }
        } catch (Exception e) {
            Util.print(e);
            e.printStackTrace();
        }
        return super.keyPressed(key, scanCode, action);
    }

    float mouseX, mouseY;

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        try {


            this.mouseX = (float) mouseX;
            this.mouseY = (float) mouseY;
            if (menu != null) {
                menu.mouseClicked((float) mouseX, (float) mouseY, mouseButton);
                hideMenu();
                return false;
            }

            toolbar.mouseClicked((float) mouseX, (float) mouseY, mouseButton);
            if (mouseButton == 0) {
                for (TabbedUI tab : tabs) {
                    float newMx = (float) (mouseX - tab.x);
                    float newMy = (float) (mouseY - tab.y);

                    UITab CTab = tab.getHoveredHead(newMx, newMy);

                    if (CTab != null) {
                        tab.selectedIndex = CTab.head.id;
                    }
                }
            }

            for (TabbedUI tab : tabs) {
                float newMx = (float) (mouseX - tab.x);
                float newMy = (float) (mouseY - tab.y);

                tab.mouseClicked(newMx, newMy, mouseButton);

            }
        } catch (Exception e) {
            Util.print(e);
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        try {
            for (TabbedUI tab : tabs) {
                float newMx = (float) (mouseX - tab.x);
                float newMy = (float) (mouseY - tab.y);
                tab.mouseReleased(newMx, newMy, state);
            }
        } catch (Exception e) {
            Util.print(e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onClose() {
        try {


        Lodestone.save();
        for (TabbedUI tab : tabs) {
            tab.close();
        }
        } catch (Exception e) {
            Util.print(e);
            e.printStackTrace();
        }
        super.onClose();
    }

    @Override
    public void tick() {
        try {


        for (TabbedUI tab : tabs) {
            tab.onTick();
        }
        } catch (Exception e) {
            Util.print(e);
            e.printStackTrace();
        }
        super.tick();
    }

    public void hideMenu() {
        menu = null;
    }

    public void showMenu() {
        menu.build(mouseX, mouseY);
    }

    public void beginMenu() {
        menu = new UIMouseMenu();
    }

    public void addMenuOption(String name, Runnable runnable) {
        menu.addOption(name, runnable);
    }
}
