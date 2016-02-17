package fr.unice.polytech.idm.arduinoml.kernel.structural.sensor;

import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;

public class DigitalSensor extends Sensor {
	private int pin;

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}
	
	@Override
	public String toString() {
		return "digitalRead(" + String.valueOf(pin) + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		
		if(obj instanceof DigitalSensor) {
			DigitalSensor sensor = (DigitalSensor) obj;
			return this.getName().equals(sensor.getName()) && this.getPin() == sensor.getPin();
		}
					
		return false;
	}

	@Override
	public void accept(Visitor<?> visitor) {
		visitor.visit(this);
	}

}
