package space.davidboles.lib.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class GuiFs {
	
	public static int getSmallestDimention(JPanel f) {
		if(f.getWidth() < f.getHeight()) return f.getWidth();
			else return f.getHeight();
	}
	
	public static int getLargestDimention(JPanel f) {
		if(f.getWidth() > f.getHeight()) return f.getWidth();
			else return f.getHeight();
	}
	
	public static void printColorKeys() {
		List<String> colorKeys = new ArrayList<String>();
	    Set<Entry<Object, Object>> entries = UIManager.getLookAndFeelDefaults().entrySet();
	    for (@SuppressWarnings("rawtypes") Entry entry : entries)
	    {
	      if (entry.getValue() instanceof Color)
	      {
	        colorKeys.add((String) entry.getKey());
	      }
	    }
	 
	    // sort the color keys
	    Collections.sort(colorKeys);
	     
	    // print the color keys
	    for (String colorKey : colorKeys)
	    {
	      System.out.println(colorKey);
	    }
	}
	
}
