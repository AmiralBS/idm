package fr.unice.polytech.idm.arduinoml.kernel.behavioral;

import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Command;
import fr.unice.polytech.idm.arduinoml.kernel.structural.EValue;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Sensor;

public class Condition implements Visitable {

	private Command command;
	private EValue value;
	private BinaryOperator binaryOperator;
	private Operator operator;

	public Command getCommand() {
		return command;
	}

	public void setSensor(Command command) {
		this.command = command;
	}

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

	@Override
	public void accept(Visitor<?> visitor) {
		visitor.visit(this);
	}
}
