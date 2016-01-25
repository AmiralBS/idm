package fr.unice.polytech.idm.arduinoml.kernel.generator;

import java.util.StringJoiner;

import fr.unice.polytech.idm.arduinoml.kernel.LCDApp;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Joystick;
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

		if (app.getJoystick() != null) {
			wln("int joyX = A" + app.getJoystick().getPinX() + "; // slider variable connecetd to analog pin A4");
			wln("int joyY = A" + app.getJoystick().getPinY() + "; // slider variable connecetd to analog pin A3");
			wln("int button = A" + app.getJoystick().getPinButton() + ";");

			wln("int value1 = 0; // variable to read the value from the analog pin 0");
			wln("int value2 = 0; // variable to read the value from the analog pin 1");
			wln("int buttonState;");
			wln("int joyXState;");
			wln("int joyYState;");
		}

		StringJoiner pinsScreen = new StringJoiner(",", "lcd(", ");");
		for (int pin : app.getScreen().getPins()) {
			pinsScreen.add(String.valueOf(pin));
		}
		wln("#include <LiquidCrystal.h>");
		wln("LiquidCrystal " + pinsScreen.toString() + "\n");

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
		wln("    buttonState = digitalRead(button);");
		wln("    joyXState = analogRead(joyX);");
		wln("    joyYState = analogRead(joyY);");
		app.getJoystick().accept(this);
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
	
	@Override
	public void visit(Joystick joystick) {
		wln("    if(buttonState == 0){    ");
		wln("        write(\"button\");");
		wln("    } else if(joyXState < 200) {");
		wln("        write(\"left\");");
		wln("    } else if(joyXState > 700){");
		wln("        write(\"right\");");
		wln("    } else if(joyYState < 200){"); 
		wln("        write(\"up\");");
		wln("    } else if(joyYState > 700){");
		wln("        write(\"down\");");
		wln("    } else {");
	    wln("        write(\"waiting input\");");
	    wln("    }");
	    wln("    delay(2000);");
	}
}
