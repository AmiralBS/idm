package fr.unice.polytech.idm.arduinoml.kernel.structural;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.unice.polytech.idm.arduinoml.kernel.NamedElement;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;

public class Screen implements NamedElement, Visitable {
	private String name;
	private int width;
	private int heigth;
	private List<Integer> pins;
	private Cursor cursor;
	private int refresh;
	private Map<String, String> messages;
	
	public Screen() {
		pins = new ArrayList<>();
		cursor = new Cursor();
		refresh = 1000;
		messages = new HashMap<>();
	}

	@Override
	public void accept(Visitor<?> visitor) {
		visitor.visit(this);
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public List<Integer> getPins() {
		return pins;
	}

	public void setPins(List<Integer> pins) {
		this.pins = pins;
	}

	public Cursor getCursor() {
		return cursor;
	}

	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}

	public int getRefresh() {
		return refresh;
	}

	public void setRefresh(int refresh) {
		this.refresh = refresh;
	}

	public Map<String, String> getMessages() {
		return messages;
	}

	public void setMessages(Map<String, String> messages) {
		this.messages = messages;
	}

}
