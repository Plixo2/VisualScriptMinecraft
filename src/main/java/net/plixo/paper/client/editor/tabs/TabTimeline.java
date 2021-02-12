package net.plixo.paper.client.editor.tabs;

import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.UI.elements.UICanvas;
import net.plixo.paper.client.UI.elements.UIPointNumber;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.editor.ui.other.OptionMenu;
import net.plixo.paper.client.engine.components.timeline.Timeline;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.Util;

import java.io.File;

public class TabTimeline extends UITab {


    public Timeline currentTimeline = null;

    public TabTimeline(int id) {
        super(id, "Timeline");
        TheEditor.timeline = this;
    }

    @Override
    public void init() {
        canvas = new UICanvas(0) {
            @Override
            public void drawScreen(float mouseX, float mouseY) {
                super.drawScreen(mouseX, mouseY);
                if (currentTimeline != null) {
                    Gui.drawString(currentTimeline.name, 5, height - 5, -1);
                }

            }
        };
        canvas.setDimensions(0, 0, parent.width, parent.height);
        canvas.setRoundness(0);
        canvas.setColor(ColorLib.getBackground(0.1f));




        UICanvas time = new UICanvas(0) {

            @Override
            public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
                hideMenu();


                float percentX = (mouseX - x) / width;
                float percentY = (mouseY - y) / height;
                if (hovered(mouseX, mouseY) && currentTimeline != null) {
                    OptionMenu.TxtRun runnable = new OptionMenu.TxtRun("new Point") {
                        @Override
                        protected void run() {
                            if (currentTimeline != null) {
                                currentTimeline.add(new Timeline.Timepoint(percentX, percentY));
                            }
                        }
                    };
                    if (mouseButton == 1) {
                        showMenu(0, mouseX, mouseY, runnable);
                    }

                }
                super.mouseClicked(mouseX, mouseY, mouseButton);
            }

            @Override
            public void drawScreen(float mouseX, float mouseY) {
                super.drawScreen(mouseX, mouseY);
                float spacing = width / 10;
                for (int i = 0; i <= 10; i++) {
                    float lineX = i * spacing;
                    Gui.drawLine(x + lineX, y, x + lineX, y + height, ColorLib.utilLines(), 1);
                }
                float percentX = (mouseX - x) / width;
                float percentY = (mouseY - y) / height;
                if (currentTimeline != null) {
                    int size = currentTimeline.timepoints.size();
                    for (int i = 0; i < size; i++) {
                        Timeline.Timepoint point = currentTimeline.timepoints.get(i);

                        float aspect = width/height;
                        float dx = (point.progess - percentX)*aspect;
                        float dy = point.yLevel - percentY;
                        double distance = Math.sqrt(dx * dx + dy * dy);

                        float pX = x + (width * point.progess);
                        float pY = y + (height * point.yLevel);
                        Gui.drawCircle(pX, pY, 2, distance < 0.04 ? ColorLib.orange() : ColorLib.red());
                        if (i < size - 1) {
                            Timeline.Timepoint next = currentTimeline.timepoints.get(i + 1);
                            Gui.drawLine(pX, pY, x + (width * next.progess), y + (height * next.yLevel), -1, 2);
                        }
                    }
                }
                Gui.drawGradientRect(x, y, x + width, y + 5, 0x80000000, 0);
            }
        };
        time.setDimensions(100, 10, parent.width - 120, parent.height - 20);
        time.setColor(ColorLib.getBackground(0.5f));
        time.setRoundness(1);


        canvas.add(time);
    }

    public void initTimeline(File file) {
        if (currentTimeline != null) {
            currentTimeline.saveToFile();
        }
        currentTimeline = Timeline.loadFromFile(file);
    }

    @Override
    public void close() {
        if (currentTimeline != null) {
            currentTimeline.saveToFile();
        }
    }

}
