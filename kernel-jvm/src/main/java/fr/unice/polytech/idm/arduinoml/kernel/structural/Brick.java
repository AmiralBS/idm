package fr.unice.polytech.idm.arduinoml.kernel.structural;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.idm.arduinoml.kernel.NamedElement;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitable;

public abstract class Brick implements NamedElement, Visitable {

	private String name;
	private List<Integer> pins = new ArrayList<>();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getPins() {
		return pins;
	}

	public void setPins(List<Integer> pins) {
		this.pins = pins;
	}

}