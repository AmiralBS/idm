package fr.unice.polytech.idm.arduinoml.kernel.structural.value;

public enum ESignal implements EValue {
	HIGH("HIGH"), LOW("LOW");
	
	private String value;
	
	ESignal(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value.toString();
	}
}
