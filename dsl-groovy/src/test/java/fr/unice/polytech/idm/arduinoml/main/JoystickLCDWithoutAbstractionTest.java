package fr.unice.polytech.idm.arduinoml.main;

import java.io.File;

import org.junit.Test;

import fr.unice.polytech.idm.arduinoml.dsl.DSL;

public class JoystickLCDWithoutAbstractionTest {

	@Test
	public void test() {
		DSL dsl = new DSL();
		dsl.eval(new File("./scripts/WithooutAbstraction.groovy"));
	}

}
