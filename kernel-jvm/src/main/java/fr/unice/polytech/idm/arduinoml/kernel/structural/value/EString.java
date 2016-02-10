package fr.unice.polytech.idm.arduinoml.kernel.structural.value;

public class EString implements EValue {
	private String value;
	
	public EString(String value) {
		this.setValue(value);	
	}

	@Override
	public String toString() {
		return this.value;
	}
	
	public String getValue(String value) {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
