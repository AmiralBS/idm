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
	public void testDualCheckAlarm() {
		dsl.eval(new File("../scripts/DualcheckAlarm.groovy"));
		System.out.println("\n\n----------------------------------------------------------------\n\n\n");
	}

	@Test
	public void testDMultiStateAlarm() {
		dsl.eval(new File("../scripts/MultiStateAlarm.groovy"));
		System.out.println("\n\n----------------------------------------------------------------\n\n\n");
	}

	@Test
	public void testStatebasedAlarm() {
		dsl.eval(new File("../scripts/StatebasedAlarm.groovy"));
		System.out.println("\n\n----------------------------------------------------------------\n\n\n");
	}
	
	@Test
	public void testVerySimpleAlarm() {
		dsl.eval(new File("../scripts/VerySimpleAlarm.groovy"));
		System.out.println("\n\n----------------------------------------------------------------\n\n\n");
	}

	@Test
	public void testBindJoyToScreen() {
		dsl.eval(new File("../scripts/BindJoyToScreen.groovy"));
		System.out.println("\n\n----------------------------------------------------------------\n\n\n");
	}
	
	@Test
	public void testKonamiCode() {
		dsl.eval(new File("../scripts/KonamiCode.groovy"));
		System.out.println("\n\n----------------------------------------------------------------\n\n\n");
	}
	
	@Test
	public void testKonamiCodeWithoutLCD() {
		dsl.eval(new File("../scripts/KonamiCodeWithoutLCD.groovy"));
		System.out.println("\n\n----------------------------------------------------------------\n\n\n");
	}
	
	@Test
	public void testKonamiCodeWithSpec() {
		dsl.eval(new File("../scripts/KonamiCodeWithSpec.groovy"));
		System.out.println("\n\n----------------------------------------------------------------\n\n\n");
	}
	
	@Test
	public void testWithooutAbstraction() {
		dsl.eval(new File("../scripts/WithooutAbstraction.groovy"));
		System.out.println("\n\n----------------------------------------------------------------\n\n\n");
	}
	
	@Test
	public void testBindThermoToLCD() {
		dsl.eval(new File("../scripts/BindThermoToLCD.groovy"));
		System.out.println("\n\n----------------------------------------------------------------\n\n\n");
	}
	
	@Test
	public void testAnalogicalLed() {
		dsl.eval(new File("../scripts/AnalogicalLed.groovy"));
		System.out.println("\n\n----------------------------------------------------------------\n\n\n");
	}
	
	@Test
	public void testThermoAlarm() {
		dsl.eval(new File("../scripts/ThermoAlarm.groovy"));
		System.out.println("\n\n----------------------------------------------------------------\n\n\n");
	}
	
}
