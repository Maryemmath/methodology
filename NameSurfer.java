
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {
	private JLabel nameLabel;
	private JTextField nameField;
	private JButton graphButton;
	private JButton clearButton;
	private NameSurferDataBase surferBase;
	private NameSurferGraph graph;

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {
		graph = new NameSurferGraph();
		add(graph);
		surferBase = new NameSurferDataBase(NAMES_DATA_FILE);
		nameLabel = new JLabel("Name: ");
		add(nameLabel, SOUTH);
		nameField = new JTextField(20);
		add(nameField, SOUTH);
		graphButton = new JButton("Graph");
		add(graphButton, SOUTH);
		clearButton = new JButton("Clear");
		add(clearButton, SOUTH);
		addActionListeners();
		nameField.addActionListener(this);
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nameField || e.getSource() == graphButton) {
			if (surferBase.findEntry(nameField.getText()) != null) {
				graph.addEntry(surferBase.findEntry(nameField.getText()));
			}
			nameField.setText("");
		}
		if (e.getSource() == clearButton) {
			graph.clear();
		}

	}
}
