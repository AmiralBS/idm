package fr.unice.polytech.idm.arduinoml.kernel.behavioral;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.idm.arduinoml.kernel.NamedElement;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;
import fr.unice.polytech.idm.arduinoml.kernel.structural.value.EDirection;

public class Konami implements NamedElement, Visitable {
	
	private String name;
	private List<EDirection> code = new ArrayList<>();
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

	public void setCode(List<EDirection> konamiCode) {
		this.code = konamiCode;
	}
	
	public List<EDirection> getCode() {
		return this.code;
	}
}
