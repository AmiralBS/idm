package fr.unice.polytech.idm.arduinoml.kernel.behavioral;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.idm.arduinoml.kernel.NamedElement;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.IKonamiCode;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.Joystick;
import fr.unice.polytech.idm.arduinoml.kernel.structural.sensor.KonamiSensor;

public class Konami implements NamedElement, Visitable {
	
	private String name;
	
	// Bricks used to execute the code
	private Joystick joystick;
	private List<KonamiSensor> sensors = new ArrayList<>();
	
	/** Konami code to execute */
	private List<IKonamiCode> code = new ArrayList<>();
	
	/** Number max of attempts to realize the Konami code */
	private int maxAttempts;

	@Override
	public void accept(Visitor<?> visitor) {
		visitor.visit(this);
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	public void setMaxAttempts(int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}
	
	public int getMaxAttempts() {
		return this.maxAttempts;
	}

	public void setCode(List<IKonamiCode> konamiCode) {
		this.code = konamiCode;
	}
	
	public List<IKonamiCode> getCode() {
		return this.code;
	}
	
	public Joystick getJoystick() {
		return joystick;
	}

	public void setJoystick(Joystick joystick) {
		this.joystick = joystick;
	}

	public List<KonamiSensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<KonamiSensor> sensors) {
		this.sensors = sensors;
	}
}
