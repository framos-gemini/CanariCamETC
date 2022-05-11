package edu.gemini.itc.canaricam;

import java.util.ArrayList;

public class ChartsAndLinks {
	
	private String chartLinks;
	ArrayList<String> listLinkSpectrum = new ArrayList<String>();
	
	public ChartsAndLinks (String link) {
		chartLinks = link;
	}
	
	public String getChartLinks() {
		return chartLinks;
	}
	public void setChartLinks(String chartLinks) {
		this.chartLinks = chartLinks;
	}
	
	public ArrayList<String> getListLinkSpectrum() {
		return listLinkSpectrum;
	}
	
	public void addLinkSpectrum(String link) {
		listLinkSpectrum.add(link);
	}
}
