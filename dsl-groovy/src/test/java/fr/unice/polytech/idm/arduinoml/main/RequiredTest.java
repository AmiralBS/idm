package fr.unice.polytech.idm.arduinoml.main;

import java.io.File;

import org.junit.Test;

import fr.unice.polytech.idm.arduinoml.dsl.DSL;

public class RequiredTest {

	@Test
	public void test() {
		DSL dsl = new DSL();
		dsl.eval(new File("./scripts/DualcheckAlarm.groovy"));
		dsl.eval(new File("./scripts/MultiStateAlarm.groovy"));
		dsl.eval(new File("./scripts/StatebasedAlarm.groovy"));
		dsl.eval(new File("./scripts/VerySimpleAlarm.groovy"));
	}

}
