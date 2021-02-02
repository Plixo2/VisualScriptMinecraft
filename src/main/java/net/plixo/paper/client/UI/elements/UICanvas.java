package net.plixo.paper.client.UI.elements;

import java.util.ArrayList;

import net.plixo.paper.client.UI.IAbstractAction;
import net.plixo.paper.client.UI.UIElement;
import net.plixo.paper.client.util.Gui;
import org.lwjgl.opengl.GL11;

public class UICanvas extends UIElement {


	IAbstractAction action;
	ArrayList<UIElement> elements = new ArrayList<UIElement>();

	UIElement lastElement;

	int ticks = 0;

	public UICanvas(int id) {
		super(id);
	}

	public void add(UIElement element) {
		lastElement = element;
		elements.add(element);
	}

	public void clear() {
		lastElement = null;
		elements.clear();
	}

	@Override
	public void draw(float mouseX, float mouseY) {
		Gui.drawRoundetRect(x, y, x + width, y + height, roundness, this.color);

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, 0);
		for (UIElement element : elements) {
			element.draw(mouseX - x, mouseY - y);
		}
		GL11.glPopMatrix();

		super.draw(mouseX, mouseY);
	}


	public UIElement getLast() {
		return lastElement;
	}


	public ArrayList<UIElement> getList() {
		return elements;
	}

	public boolean hasValues() {
		return elements.size() > 0;
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) {
		for (UIElement element : elements) {
			element.keyTyped(typedChar, keyCode);
			if (action != null) {
				action.run(this.getId(), element.getId(), element);
			}
		}
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void keyPressed(int key, int scanCode, int action) {
		for (UIElement element : elements) {
			element.keyPressed(key , scanCode , action);
			if (this.action != null) {
				this.action.run(this.getId(), element.getId(), element);
			}
		}
		super.keyPressed(key, scanCode, action);
	}

	@Override
	public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

		for (UIElement element : elements) {
				element.mouseClicked(mouseX - x, mouseY - y, mouseButton);
			if (action != null) {
				action.run(this.getId(), element.getId(), element);
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	public void setButtonAction(IAbstractAction buttonAction) {
		this.action = buttonAction;
	}



	@Override
	public void update() {
		ticks += 1;
		boolean update = ticks > 10;
		for (UIElement element : elements) {
			element.update();
		
		
			if (update && action != null) {
				ticks = 0;
				action.run(this.getId(), element.getId(), element);
			}
		}
		
		
		super.update();
	}

}
