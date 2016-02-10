package fr.unice.polytech.idm.arduinoml.kernel.behavioral;

import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Sensor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.value.EValue;

public class Condition implements Visitable {

	private Sensor sensor;
	private EValue value;
	private BinaryOperator binaryOperator;
	private Operator operator;

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
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
