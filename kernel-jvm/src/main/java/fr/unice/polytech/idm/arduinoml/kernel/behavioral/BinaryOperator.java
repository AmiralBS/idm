package fr.unice.polytech.idm.arduinoml.kernel.behavioral;

public enum BinaryOperator {
	EQ("=="), NE("!="), LT("<"), GT(">"), LE("<="), GE(">=");

	private String value;

	private BinaryOperator(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
