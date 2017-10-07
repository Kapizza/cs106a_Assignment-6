/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class NameSurferExtension extends Program implements NameSurferConstants {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {
		data = new NameSurferDataBase("names-data.txt");
		graph = new NameSurferGraphExtension();
		add(graph);
		addInteractors();
		addActionListeners();
		background.addActionListener(this);
		field.addActionListener(this);
	}

	private void addInteractors() {
		label = new JLabel("Name");
		add(label, SOUTH);
		field = new JTextField(20);
		add(field, SOUTH);
		graphButton = new JButton("Graph");
		add(graphButton, SOUTH);
		clearButton = new JButton("Clear");
		add(clearButton, SOUTH);
		deleteField = new JTextField(10);
		add(deleteField, SOUTH);
		deleteButton = new JButton("Delete");
		add(deleteButton, SOUTH);
		background = new JCheckBox("BackGround");
		add(background, SOUTH);
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so
	 * you will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == field || e.getActionCommand().equals("Graph")) {
			String text = field.getText();
			if (text.length() > 0) {
				text = formatName(text);
			}
			if (data.findEntry(text) != null) {
				graph.addEntry(data.findEntry(text));
				graph.update();
				field.setText("");
				println(data.findEntry(text));
			}
		}
		// /
		if (e.getActionCommand().equals("Clear")) {
			println("Clear");
			graph.clear();
		}
		// /
		if (background.isSelected()) {
			graph.setBackground(Color.GRAY);
			graph.update();
		} else {
			graph.setBackground(Color.WHITE);
			graph.update();
		}
		// /
		if (e.getActionCommand().equals("Delete")) {
			String text = deleteField.getText();
			if (text.length() > 0) {
				text = formatName(text);
				graph.deleteName(text);
				println("delete");
			}
		}

	}

	private String formatName(String str) {
		str = str.toLowerCase();
		char c = str.charAt(0);
		char k = (char) (c + 'A' - 'a');
		str = k + str.substring(1);
		return str;
	}

	/* Private instance variables */
	private NameSurferGraphExtension graph;
	private NameSurferDataBase data;
	private JLabel label;
	private JTextField field;
	private JButton graphButton;
	private JButton clearButton;
	private JCheckBox background;
	private JTextField deleteField;
	private JButton deleteButton;
}
