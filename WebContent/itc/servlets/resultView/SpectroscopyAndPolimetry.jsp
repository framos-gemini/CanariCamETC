<%@page import="edu.gemini.itc.shared.FormatStringWriter"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="edu.gemini.itc.canaricam.CanaricamObsResult,
				 edu.gemini.itc.canaricam.ChartsAndLinks,
				 edu.gemini.itc.parameters.ObservationDetailsParameters,
				 edu.gemini.itc.parameters.ObservingConditionParameters,
				 edu.gemini.itc.parameters.SourceDefinitionParameters,
				 edu.gemini.itc.canaricam.CanariCam,
				 java.util.*, 
				 java.util.HashMap" %>

<!DOCTYPE>
<html>
<head>
	<link rel="shortcut icon" href="/itc/itc/images/gtcicon.png">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="/itc/itc/jQuery/css/GTCweb.css" rel="stylesheet">  
	<script type="text/javascript" src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=AM_HTMLorMML-full"></script>
	<title>Observation Result</title>
</head>
<body id="result">
	<%
		CanaricamObsResult obsResult = (CanaricamObsResult) request.getAttribute("obsResult");
		SourceDefinitionParameters sdParameter = (SourceDefinitionParameters) request.getAttribute("sdParameters");
		ObservingConditionParameters obsConditionParameters = (ObservingConditionParameters) request.getAttribute("obsConditionParameters");
		ObservationDetailsParameters obsDetailsParameters = (ObservationDetailsParameters) request.getAttribute("obsDetailParameters");
		CanariCam instrument = (CanariCam) request.getAttribute("instrument");
		
		FormatStringWriter device = new FormatStringWriter();
		device.setPrecision(2);  // Two decimal places
		device.clear();
		//response.getWriter().write((String) request.getAttribute("textPlane"));  
	%> 
	
	<h1 id="title"> CanariCam ETC Results Page<i id="version">(v5.0)</i></h1>
	
	<h2>Observation Results</h2>
	<table>
		<tr>
			<td>Software aperture extent along slit</td>
			<td><% out.print(device.toString(obsResult.getAperture()));%></td>
		</tr>
		<tr>
			<td>Fraction of source flux in aperture</td>
			<td><% out.print(device.toString(obsResult.getSlitThrought()));  %> </td>
		</tr>
		<tr>
			<td>Derived image size(FWHM) for a point source</td>
			<td><% out.print(device.toString(obsResult.getImqual())); %>
		</tr>
		<tr>
			<td>Sky subtraction aperture</td>
			<td><% out.print(device.toString(obsResult.getSkyAperDiameter()) + " times the software aperture."); %></td>	
		</tr>
		<tr>
			<td>Requested on-source integration time</td>
			<td><% out.print(device.toString(obsResult.getTotalIntegrationTime())+ " secs"); %> </td>
		</tr>
	</table>	
	
	
	<%

		System.out.println("------------------------ ");
		HashMap<String, ChartsAndLinks> mapCharts = obsResult.getMapCharts();
		Iterator it = (Iterator) mapCharts.keySet().iterator();
		while (it.hasNext()) {
	%>
			<div class="chart">
			<%
				String key = (String) it.next();
				ChartsAndLinks chart = mapCharts.get(key);
				out.println(chart.getChartLinks());
				ArrayList<String> linkSpectrums = chart.getListLinkSpectrum();
				for (String link : linkSpectrums) {
					out.println(link);
				}
			%>
			</div>
		<%
		}
		%>
	
	<h2>Input parameters</h2>
		
	<h4>Source spatial profile, brightness, and spectral distribution</h4>
	<table class="obs-message">
		<tr>
			<td>The Source is a</td>
			<td><%
					if (sdParameter.getSourceSpec().equals(sdParameter.ELINE)) 
							out.println(
									"single emission line, at a wavelength of "+ sdParameter.getELineWavelength()
									+" microns, and with a width of " + sdParameter.getELineWidth()
									+ " km/s.\n  Its total flux is "+ sdParameter.getELineFlux()
									+" " + sdParameter.getELineFluxUnitsCustom() + " on a flat continuum of flux density " 
									+ sdParameter.getELineContinuumFlux()+ " " + sdParameter.getELineContFluxUnits()+"."
							);
					
					else if (sdParameter.getSourceSpec().equals(sdParameter.BBODY)) 
							out.println(" " + sdParameter.getBBTemp() + "K Blackbody, at " +  sdParameter.getSourceNormalization() 
									+ " " + sdParameter.getPrettyUnits() +" in the "+ sdParameter.getNormBand() + " band."
							);
			
					else if (sdParameter.getSourceSpec().equals(sdParameter.LIBRARY_STAR)) 
						out.println(sdParameter.getSourceNormalization() +" " + sdParameter.getPrettyUnits() 
									+ " " + sdParameter.getSpecType() +	" star at " + sdParameter.getNormBand()+ ".");
			
					else if (sdParameter.getSourceSpec().equals(sdParameter.LIBRARY_NON_STAR))
						out.println(sdParameter.getSourceNormalization() +" " + sdParameter.getPrettyUnits() 
									+ " " + sdParameter.getSpecType() +	" at " + sdParameter.getNormBand()+ ".");
					else if (sdParameter.isSedUserDefined()) 
						out.println(" a user defined spectrum with the name: " +  sdParameter.getSpectrumResource());
					
					else if (sdParameter.getSourceSpec().equals(sdParameter.PLAW)) 
						out.println(" Power Law Spectrum, with an index of " + sdParameter.getPowerLawIndex());
					
				%>
			</td>
		</tr>
	</table>
	
	<h4>Instrument Configuration</h4>
	
	<table class="obs-message">
		<tbody>
			<% 	List<String> listComponents = instrument.getComponentsToString(); 
				for (String component : listComponents) {
					String aux[] = component.split(":");
					String htmlElement = (aux.length > 1) ? "<tr><td>"+aux[0]+"</td><td>"+aux[1]+"</td></tr>" : "<tr><td>"+aux[0]+"</td></tr>";
					out.println(htmlElement);
				}
			%>
		<tr>
			<td>Focal Plane Mask</td>
			<td><% out.println(instrument.getFocalPlaneCustom()); %></td>
		</tr>
		<tr>
			<td>Pixel Size in Spatial Direction</td>
			<td><% out.println(instrument.getPixelSize()+"\""); %></td>
		</tr>
		<tr>
			<td>Pixel Size in Spectral Direction</td>
			<td><% out.println(instrument.getGratingDispersion_nmppix()+"nm\n"); %></td>
		</tr>
		</tbody>
	
	</table>
	
	 <h4>Observing Conditions</h4>
       
	<table class="obs-message">
		<tr>
		  <td>Image Quality</td>
		  <td><% device.setPrecision(0);
		  		out.println(device.toString(obsConditionParameters.getImageQualityPercentile() * 100) + "%"); %> </td>
		</tr>
		<tr>
		  <td>Sky Transparency (cloud cover) </td>
		  <td><% out.println(device.toString(obsConditionParameters.getSkyTransparencyCloudPercentile() * 100) + "%"); %> </td>
		</tr>
		<tr>
			<td>Sky transparency (water vapour)</td>
			<td><% out.println(device.toString(obsConditionParameters.getSkyTransparencyWaterPercentile() * 100) + "%"); %></td>
		</tr>
		
        <tr>
        	<td>Sky background</td>
        	<td><% out.println(device.toString(obsConditionParameters.getSkyBackgroundPercentile() * 100) + "%"); %></td>
        </tr>
        <tr>
        	<td>Frequency of occurrence of these conditions</td>
        	<td><% 	out.println(device.toString(obsConditionParameters.getImageQualityPercentile() *
        					   obsConditionParameters.getSkyTransparencyCloudPercentile() *
        					   obsConditionParameters.getSkyTransparencyWaterPercentile() *
        					   obsConditionParameters.getSkyBackgroundPercentile() * 100) + "%");
        		%> </td>
        
		</tr>
	</table>
	<h4>Calculation and analysis methods</h4>
    <div class="obs-message"> <% out.println(obsDetailsParameters.printParameterSummary().replace("Calculation and analysis methods:", "")); %>  </div>
	

</html>