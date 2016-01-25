package fr.unice.polytech.idm.arduinoml.kernel.structural;

import fr.unice.polytech.idm.arduinoml.kernel.NamedElement;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;

public class Joystick implements NamedElement, Visitable {
	
	private String name;
	
	private int pinX;
	private int pinY;
	private int pinButton;

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

	public int getPinX() {
		return pinX;
	}

	public void setPinX(int pinX) {
		this.pinX = pinX;
	}

	public int getPinY() {
		return pinY;
	}

	public void setPinY(int pinY) {
		this.pinY = pinY;
	}

	public int getPinButton() {
		return pinButton;
	}

	public void setPinButton(int pinButton) {
		this.pinButton = pinButton;
	}

}
