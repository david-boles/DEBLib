package com.deb.lib.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JPanelImage extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;

  // Some code to initialize the background image.
  // Here, we use the constructor to load the image. This
  // can vary depending on the use case of the panel.
	public JPanelImage(File file) throws IOException {
		backgroundImage = ImageIO.read(file);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int sDim = GuiFs.getSmallestDimention(this);
		int xOff = (this.getWidth()-sDim)/2;
		int yOff = (this.getHeight()-sDim)/2;
		
		g.drawImage(backgroundImage, xOff, yOff, sDim+xOff, sDim+yOff, 0, 0, 1200, 1200, this);
	}
}