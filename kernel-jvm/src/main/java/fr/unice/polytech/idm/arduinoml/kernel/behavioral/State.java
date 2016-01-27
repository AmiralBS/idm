package fr.unice.polytech.idm.arduinoml.kernel.behavioral;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.idm.arduinoml.kernel.NamedElement;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;

public class State implements NamedElement, Visitable {

	private String name;
	private int ident;
	private List<Action> actions = new ArrayList<Action>();
	private List<Transition> transitions = new ArrayList<Transition>();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public List<Transition> getTransitions() {
		return transitions;
	}

	public void setTransitions(List<Transition> transitions) {
		this.transitions = transitions;
	}

	@Override
	public void accept(Visitor<?> visitor) {
		visitor.visit(this);
	}

	public int getIdent() {
		return ident;
	}

	public void setIdent(int ident) {
		this.ident = ident;
	}
}
