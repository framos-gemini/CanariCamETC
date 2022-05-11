package edu.gemini.itc.canaricam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.gemini.itc.shared.ObservationResult;

public class CanaricamObsResult extends ObservationResult {
	
	private String obsMode;
	private double slitThrought;
	private String sourceGeometry;
	private HashMap<String, ChartsAndLinks> mapCharts = new HashMap();
	private String view;
	private List<String> listWarnings = new ArrayList<String>();
	
	
	
	public void setObsMode(String obsMode) {
		this.obsMode = obsMode;
	}
	
	public String getObsMode() {
		return obsMode;
	}

	public void setSlitThrought(double slitThroughput) {
		this.slitThrought = slitThroughput;
		
	}
	
	public double getSlitThrought() {
		return slitThrought;
		
	}

	public void setSourceGeometry(String sourceGeometry) {
		this.sourceGeometry = sourceGeometry;
		
	}
	
	public String getSourceGeometry() {
		return sourceGeometry;
		
	}
	
	public void addSouceCharts (String sourceChart, String link) {
		if (!mapCharts.containsKey(sourceChart)) {
			mapCharts.put(sourceChart, new ChartsAndLinks(link));
		}
	}
	

	public void setView(String view) {
		this.view = view;
	}
	
	
	public String getView() {
		return view;
	}

	public void addLinkSpectrum (String sourceChart, String linkSpectrum) {
		if (!mapCharts.containsKey(sourceChart)) {
			mapCharts.put(sourceChart, new ChartsAndLinks (linkSpectrum));
		} else {
			mapCharts.get(sourceChart).addLinkSpectrum(linkSpectrum);
		}
	}
	
	public HashMap<String, ChartsAndLinks> getMapCharts () {
		return mapCharts;
	}


	public void addWarnings(String warning) {
		listWarnings.add(warning);
	}
	
	public List<String> getWarnings() {
		return listWarnings;
	}
	
}
