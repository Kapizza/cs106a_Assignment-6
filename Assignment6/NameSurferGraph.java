/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants,
		ComponentListener {

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		entries = new ArrayList<NameSurferEntry>();

	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		removeAll();
		entries.clear();
		drawBackground();
		;
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note
	 * that this method does not actually draw the graph, but simply stores the
	 * entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		if (!entries.contains(entry))
			entries.add(entry);
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of
	 * entries. Your application must call update after calling either clear or
	 * addEntry; update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		drawBackground();
		graph();
	}

	/*
	 * This method draw all graphs on entered names;
	 */
	private void graph() {
		for (int i = 0; i < entries.size(); i++) {
			drawGraph(entries.get(i), i);

		}
	}

	/*
	 * This method draws single graph;
	 */
	private void drawGraph(NameSurferEntry entry, int colorNumber) {
		double height = (getHeight() - 2 * GRAPH_MARGIN_SIZE);
		for (int i = 0; i < NDECADES - 1; i++) {
			double x0 = i * getWidth() / NDECADES;
			double y0 = height * entry.getRank(i) / MAX_RANK
					+ GRAPH_MARGIN_SIZE;
			double x1 = (i + 1) * getWidth() / NDECADES;
			double y1 = height * entry.getRank(i + 1) / MAX_RANK
					+ GRAPH_MARGIN_SIZE;
			if (entry.getRank(i) == 0)
				y0 = height + GRAPH_MARGIN_SIZE;
			if (entry.getRank(i + 1) == 0)
				y1 = height + GRAPH_MARGIN_SIZE;
			GLine graph = new GLine(x0, y0, x1, y1);
			graph.setColor(graphColor(colorNumber));
			add(graph);
		}
		addNames(entry, colorNumber);
	}

	/*
	 * This method adds names on graph;
	 */
	private void addNames(NameSurferEntry entry, int colorNumber) {
		double height = (getHeight() - 2 * GRAPH_MARGIN_SIZE);
		String name = entry.getName();
		for (int i = 0; i < NDECADES; i++) {
			double x = i * getWidth() / NDECADES;
			double y = height * entry.getRank(i) / MAX_RANK + GRAPH_MARGIN_SIZE;
			;
			if (entry.getRank(i) == 0) {
				GLabel label = new GLabel(name + "*");
				label.setColor(graphColor(colorNumber));
				add(label, x, getHeight() - GRAPH_MARGIN_SIZE);
			} else {
				GLabel label = new GLabel(name
						+ Integer.toString(entry.getRank(i)));
				label.setColor(graphColor(colorNumber));
				add(label, x, y);
			}
		}
	}

	/*
	 * This method draws vertical and horizontal lines ands years at the bottom;
	 */
	private void drawBackground() {
		drawVerticalLines();
		drawHorizontalLines();
		drawYears();
	}

	/*
	 * This method draws horizontal lines;
	 */
	private void drawHorizontalLines() {
		GLine top = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(),
				GRAPH_MARGIN_SIZE);
		GLine bot = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(),
				getHeight() - GRAPH_MARGIN_SIZE);
		add(top);
		add(bot);
	}

	/*
	 * This method give colors to graph;
	 */
	private Color graphColor(int i) {
		switch (i % 4) {
		case 1:
			return Color.RED;
		case 2:
			return Color.BLUE;
		case 3:
			return Color.YELLOW;
		}
		return null;

	}

	/*
	 * This method draws vertical lines;
	 */
	private void drawVerticalLines() {
		for (int i = 1; i < NDECADES; i++) {
			GLine line = new GLine(i * getWidth() / NDECADES, 0, i * getWidth()
					/ NDECADES, getHeight());
			add(line);
		}

	}

	/*
	 * This method draws years;
	 */
	private void drawYears() {
		for (int i = 0; i < NDECADES; i++) {
			int year = START_DECADE + i * 10;
			GLabel label = new GLabel(Integer.toString(year));
			add(label, i * getWidth() / NDECADES + 5, getHeight());
		}
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}

	private ArrayList<NameSurferEntry> entries;

}