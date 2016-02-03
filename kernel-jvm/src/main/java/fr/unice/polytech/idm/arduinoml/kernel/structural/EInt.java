package fr.unice.polytech.idm.arduinoml.kernel.structural;

public class EInt implements EValue {
	private int value;
	
	public EInt(int value) {
		this.setValue(value);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
