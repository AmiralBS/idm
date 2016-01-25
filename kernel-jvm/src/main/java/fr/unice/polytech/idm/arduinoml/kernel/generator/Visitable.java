package fr.unice.polytech.idm.arduinoml.kernel.generator;

public interface Visitable {

	public void accept(Visitor<?> visitor);

}
