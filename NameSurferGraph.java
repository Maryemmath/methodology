
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

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {

	ArrayList<NameSurferEntry> array = new ArrayList<>();

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		array = new ArrayList<>();
		update();
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		array = new ArrayList<>();
		update();
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note that
	 * this method does not actually draw the graph, but simply stores the entry;
	 * the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		array.add(entry);
		update();
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of entries.
	 * Your application must call update after calling either clear or addEntry;
	 * update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		drawGraphGrid();
		for (int i = 0; i < array.size(); i++) {
			drawGraph(array.get(i), i % 4);
		}
	}

	/*
	 * This method draws the graphics for each name
	 */
	private void drawGraph(NameSurferEntry ent, int c) {
		String name = ent.getName();
		Color col = evaluateColor(c - 1);
		for (int i = 0; i < NDECADES - 1; i++) {
			double Xi = i * (getWidth() / NDECADES);
			double Xf = (i + 1) * (getWidth() / NDECADES);
			double Yi = GRAPH_MARGIN_SIZE + evaluateYi(ent.getRank(i));
			double Yf = GRAPH_MARGIN_SIZE + evaluateYi(ent.getRank(i + 1));
			GLine line = new GLine(Xi, Yi, Xf, Yf);
			add(line);
			GLabel rating = new GLabel(name + ent.getRank(i));
			if (ent.getRank(i) == 0) {
				rating = new GLabel(name + " *");
			}
			rating.setColor(col);
			add(rating, Xi + RATING_OFFSET, Yi + RATING_OFFSET);
			if (i == NDECADES - 2) {
				rating = new GLabel(name + ent.getRank(i + 1));
				if (ent.getRank(i + 1) == 0) {
					rating = new GLabel(name + " *");
				}
				rating.setColor(col);
			}
			add(rating, Xi + RATING_OFFSET, Yi + RATING_OFFSET);
			if (i == NDECADES - 2) {
				add(rating, Xf + RATING_OFFSET, Yf + RATING_OFFSET);
			}
			line.setColor(col);
		}

	}

	/*
	 * This method choses the color of the graph
	 */
	private Color evaluateColor(int i) {
		if (i + 1 % 4 == 0) {
			return Color.BLACK;
		} else if (i + 1 % 4 == 1) {
			return Color.RED;
		} else if (i + 1 % 4 == 2) {
			return Color.BLUE;
		} else {
			return Color.YELLOW;
		}
	}

	/*
	 * This method counts the value of Y
	 */
	private int evaluateYi(int rank) {
		int range = getHeight() - 2 * GRAPH_MARGIN_SIZE;
		if (rank == 0) {
			return range;
		} else {
			double proportion = (double) rank / MAX_RANK;
			return (int) (range * proportion);
		}
	}

	/*
	 * This method draws lines grid
	 */
	private void drawGraphGrid() {
		for (int i = 0; i < NDECADES; i++) {
			double x = i * (getWidth() / NDECADES);
			GLine line = new GLine(x, 0, x, getHeight());
			add(line);
		}
		GLine marginalLine1 = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		add(marginalLine1);
		GLine marginalLine2 = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(),
				getHeight() - GRAPH_MARGIN_SIZE);
		add(marginalLine2);
		int counter = START_DECADE;
		for (int i = 0; i < NDECADES; i++) {
			String decade = "" + counter;
			GLabel decadeLabel = new GLabel(decade);
			double x = i * (getWidth() / NDECADES);
			add(decadeLabel, x + 3, getHeight() - GRAPH_MARGIN_SIZE + decadeLabel.getHeight() + 3);
			counter += 10;
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
}
