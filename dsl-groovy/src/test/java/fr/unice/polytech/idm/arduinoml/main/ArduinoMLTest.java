package fr.unice.polytech.idm.arduinoml.main;

import java.io.File;

import org.junit.Test;

import fr.unice.polytech.idm.arduinoml.dsl.ArduinoMLDSL;

public class ArduinoMLTest {

	@Test
	public void test() {
		ArduinoMLDSL dsl = new ArduinoMLDSL();
		dsl.eval(new File("./scripts/DualcheckAlarm.groovy"));
		dsl.eval(new File("./scripts/MultiStateAlarm.groovy"));
		dsl.eval(new File("./scripts/StatebasedAlarm.groovy"));
		dsl.eval(new File("./scripts/VerySimpleAlarm.groovy"));
	}

}
