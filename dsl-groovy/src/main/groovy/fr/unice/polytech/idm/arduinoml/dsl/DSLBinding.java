package fr.unice.polytech.idm.arduinoml.dsl;

import java.util.Map;

import fr.unice.polytech.idm.arduinoml.kernel.behavioral.Operator;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State;
import groovy.lang.Binding;
import groovy.lang.Script;

public class DSLBinding extends Binding implements BindName {
	// can be useful to return the script in case of syntax trick
	private Script script;
	
	private Model model;
	
	public DSLBinding() {
		super();
	}
	
	@SuppressWarnings("rawtypes")
	public DSLBinding(Map variables) {
		super(variables);
	}
	
	public DSLBinding(Script script) {
		super();
		this.script = script;
	}
	
	public void setScript(Script script) {
		this.script = script;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public Object getVariable(String name) {
		if ("and".equals(name)) {
			setVariable(CURRENT_OPERATOR, Operator.AND);
			return script;
		}
		if ("or".equals(name)) {
			setVariable(CURRENT_OPERATOR, Operator.OR);
			return script;
		}
		if ("success".equals(name)) {
			setVariable(CURRENT_STATE, (State) getVariable("finish"));
			return script;
		}
		if ("failure".equals(name)) {
			setVariable(CURRENT_STATE, (State) getVariable("fail"));
			return script;
		}
		if ("gameover".equals(name)) {
			setVariable(CURRENT_STATE, (State) getVariable("over"));
			return script;
		}
		return super.getVariable(name);
	}
	
	public void setVariable(String name, Object value) {
		super.setVariable(name, value);
	}
	
	public Model getModel() {
		return this.model;
	}
}
