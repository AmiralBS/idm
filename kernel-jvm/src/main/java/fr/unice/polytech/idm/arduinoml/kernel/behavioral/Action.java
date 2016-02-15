package fr.unice.polytech.idm.arduinoml.kernel.behavioral;

import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.value.EValue;

public class Action implements Visitable {

	private EValue value;
	private Actionable actionable;

	@Override
	public void accept(Visitor<?> visitor) {
		visitor.visit(this);
	}

	public EValue getValue() {
		return value;
	}

	public void setValue(EValue value) {
		this.value = value;
	}

	public Actionable getActionable() {
		return actionable;
	}

	public void setActionable(Actionable actionable) {
		this.actionable = actionable;
	}

}
