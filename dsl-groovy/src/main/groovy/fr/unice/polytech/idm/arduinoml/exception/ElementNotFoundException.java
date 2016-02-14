package fr.unice.polytech.idm.arduinoml.exception;

public class ElementNotFoundException extends Exception {

	private static final long serialVersionUID = -1206840642753364655L;
	
	public ElementNotFoundException() {
		super();
	}
	
	public ElementNotFoundException(String elementName) {
		super(elementName + " not found !");
	}

}
