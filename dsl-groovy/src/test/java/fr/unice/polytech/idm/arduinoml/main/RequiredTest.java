package fr.unice.polytech.idm.arduinoml.main;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.idm.arduinoml.dsl.DSL;

public class RequiredTest {
	private DSL dsl;

	@Before
	public void setUp() throws Exception {
		dsl = new DSL();
	}

	@After
	public void tearDown() throws Exception {
		dsl = null;
	}

	@Test
	public void testWithooutAbstraction() {
		dsl.eval(new File("./scripts/WithooutAbstraction.groovy"));
	}

	@Test
	public void testDualCheckAlarm() {
		dsl.eval(new File("./scripts/DualcheckAlarm.groovy"));
	}

	@Test
	public void testDMultiStateAlarm() {
		dsl.eval(new File("./scripts/MultiStateAlarm.groovy"));
	}

	@Test
	public void testStatebasedAlarm() {
		dsl.eval(new File("./scripts/StatebasedAlarm.groovy"));
	}

	@Test
	public void testVerySimpleAlarm() {
		dsl.eval(new File("./scripts/VerySimpleAlarm.groovy"));
	}

}
