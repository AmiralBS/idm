package fr.unice.polytech.idm.arduinoml.kernel.structural;

import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;

/**
 * Created by mael on 2/10/16.
 */
public class Button extends Sensor{

    private DigitalSensor button;

    public DigitalSensor getButton() {
        return button;
    }

    public void setButton(DigitalSensor button){
        this.button = button;
    }

    @Override
    public void accept(Visitor<?> visitor) {
        visitor.visit(this);
    }
}
