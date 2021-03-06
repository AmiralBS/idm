package fr.unice.polytech.idm.arduinoml.kernel.structural.actuator;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.idm.arduinoml.kernel.generator.Visitor;

public class LCD extends Actuator {

	private List<Integer> config = new ArrayList<>();
	private int cols;
	private int rows;
	private int refresh;

	public List<Integer> getConfig() {
		return config;
	}

	public void setConfig(List<Integer> config) {
		this.config = config;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Override
	public void accept(Visitor<?> visitor) {
		visitor.visit(this);
	}

	public int getRefresh() {
		return refresh;
	}

	public void setRefresh(int refresh) {
		this.refresh = refresh;
	}
	
}
