package fr.unice.polytech.idm.arduinoml.kernel.generator;

import java.util.StringJoiner;

import fr.unice.polytech.idm.arduinoml.kernel.LCDApp;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Screen;

/**
 * Quick and dirty visitor to support the generation of Wiring code
 */
public class LCDToWiring extends ToWiring {

	public LCDToWiring() {
		super();
	}

	@Override
	public void visit(LCDApp app) {
		wln("// Wiring code generated from an ArduinoML model");
		wln(String.format("// Application name: %s\n", app.getName()));

		StringJoiner pinsScreen = new StringJoiner("lcd(", ",", ");");
		for (int pin : app.getScreen().getPins()) {
			pinsScreen.add(String.valueOf(pin));
		}

		wln("#include <LiquidCrystal.h>");
		w("LiquidCrystal " + pinsScreen.toString());

		wln("void setup(){");
		app.getScreen().accept(this);
		wln("}\n");

		wln("void write(String input){");
		wln("    lcd.clear(); // clear LCD screen");
		wln("    lcd.setCursor(0,0);");
		wln("    lcd.print(input); // print text and move cursor to the next line  ");
		wln("    delay(" + app.getScreen().getRefresh() + ");");
		wln("}");

		wln("void loop() {");
		// TODO
		wln("}");
	}

	@Override
	public void visit(Screen screen) {
		wln("    lcd.begin(" + screen.getWidth() + "," + screen.getHeigth() + "); // tells Arduino the LCD dimension");
		wln("    lcd.setCursor(0,0);");
		wln("    lcd.print(\"Hello electropus!\"); // print text and move cursor to the next line  ");
		wln("    lcd.setCursor(0,1);");
		wln("    lcd.print(\"Please wait...\");");
		wln("    delay(" + screen.getRefresh() + ");");
		wln("    lcd.clear(); // clear LCD screen");
		wln("    lcd.setCursor(0,0);");
	}

}
