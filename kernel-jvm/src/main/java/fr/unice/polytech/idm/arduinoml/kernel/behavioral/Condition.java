package fr.unice.polytech.idm.arduinoml.kernel.behavioral;

import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.value.EValue;

public class Condition implements Visitable {

	private Conditionable conditionable;
	private EValue value;
	private BinaryOperator binaryOperator;
	private Operator operator;

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public EValue getValue() {
		return value;
	}

	public void setValue(EValue value) {
		this.value = value;
	}

	public BinaryOperator getBinaryOperator() {
		return binaryOperator;
	}

	public void setBinaryOperator(BinaryOperator binaryOperator) {
		this.binaryOperator = binaryOperator;
	}

	public Conditionable getConditionable() {
		return conditionable;
	}

	public void setConditionable(Conditionable conditionable) {
		this.conditionable = conditionable;
	}
	
	@Override
	public void accept(Visitor<?> visitor) {
		visitor.visit(this);
	}
}
