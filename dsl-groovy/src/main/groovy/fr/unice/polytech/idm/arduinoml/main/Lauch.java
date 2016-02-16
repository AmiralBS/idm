package fr.unice.polytech.idm.arduinoml.main;

import java.io.File;

import fr.unice.polytech.idm.arduinoml.dsl.DSL;

/**
 * This main takes one argument: tht path to the Groovy script file to execute.
 * This Groovy script file must follow GroovuinoML DSL's rules.
 * 
 * "We've Got A Groovy Thing Goin'"!
 * 
 * @author Thomas Moreau
 */
public class Lauch {
	public static void main(String[] args) {
		DSL dsl = new DSL();
		if(args.length > 0) {
			dsl.eval(new File(args[0]));
		} else {
			System.out.println("/!\\ Missing arg: Please specify the path to a Groovy script file to execute");
		}
	}
}
