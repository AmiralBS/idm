package fr.unice.polytech.idm.arduinoml.kernel.structural;

import fr.unice.polytech.idm.arduinoml.kernel.NamedElement;
import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitable;

/**
 * Created by mael on 2/10/16.
 */
public abstract class Command implements NamedElement, Visitable{

    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
