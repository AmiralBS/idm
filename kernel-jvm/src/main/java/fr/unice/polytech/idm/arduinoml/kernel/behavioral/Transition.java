package fr.unice.polytech.idm.arduinoml.kernel.behavioral;

import java.util.List;

import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;

public class Transition implements Visitable {

	private State next;
	private List<Condition> conditions;

	public State getNext() {
		return next;
	}

	public List<Condition> getConditions() {
		return conditions;
	}
	
	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public void setNext(State next) {
		this.next = next;
	}

	@Override
	public void accept(Visitor<?> visitor) {
		visitor.visit(this);
	}

}
