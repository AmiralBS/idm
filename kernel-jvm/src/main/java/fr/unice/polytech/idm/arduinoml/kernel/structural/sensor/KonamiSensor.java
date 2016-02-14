package fr.unice.polytech.idm.arduinoml.kernel.structural.sensor;

public class KonamiSensor extends DigitalSensor implements IKonamiCode {
	
	public KonamiSensor(DigitalSensor dsensor){
		super();
		this.setName(dsensor.getName());
		this.setPin(dsensor.getPin());
	}
	
}
