<%@page import="edu.gemini.itc.canaricam.CanariCamParameters"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="edu.gemini.itc.shared.FormatStringWriter"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="edu.gemini.itc.canaricam.CanaricamObsResult,
				 edu.gemini.itc.parameters.ObservationDetailsParameters,
				 edu.gemini.itc.parameters.ObservingConditionParameters,
				 edu.gemini.itc.parameters.SourceDefinitionParameters,
				 edu.gemini.itc.canaricam.CanariCam,
				 java.text.DecimalFormat,java.util.*" %>

<!DOCTYPE>
<html>
<head>
	<link rel="shortcut icon" href="/itc/itc/images/gtcicon.png">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="/itc/itc/jQuery/css/GTCweb.css" rel="stylesheet">  
	<script src="/itc/itc/jQuery/jquery-2.1.0.js" type="text/javascript"></script>
	<title>Observation Result</title>
	<script  type="text/javascript" >
 		$( document ).ready(function() {
			var tr_last = $(".calc-result" ).parent();
			var tbody = $(tr_last).parent();
			$(tr_last).remove();
			$(tbody).append(tr_last);
		});
	</script>
</head>
<body id="result">
<%
	CanaricamObsResult obsResult = (CanaricamObsResult) request.getAttribute("obsResult");
	SourceDefinitionParameters sdParameter = (SourceDefinitionParameters) request.getAttribute("sdParameters");
	CanariCamParameters ccParameters = (CanariCamParameters) request.getAttribute("ccParameters");
	ObservingConditionParameters obsConditionParameters = (ObservingConditionParameters) request.getAttribute("obsConditionParameters");
	ObservationDetailsParameters obsDetailsParameters = (ObservationDetailsParameters) request.getAttribute("obsDetailParameters");
	CanariCam instrument = (CanariCam) request.getAttribute("instrument");
	
	FormatStringWriter device = new FormatStringWriter();
	device.setPrecision(2);  // Two decimal places
	device.clear();
	DecimalFormat decFormatExp = new DecimalFormat("0.00E00");
	DecimalFormat decFormat = new DecimalFormat("0.#");
	DecimalFormat decFormat2 = new DecimalFormat("0.##");
	//response.getWriter().write((String) request.getAttribute("textPlane"));  
%> 
	<h1 id="title"> CanariCam ETC Results Page<i id="version">(v5.0)</i> </h1>

	<h2>Observation Results</h2>

	<table>
		<% if (obsResult.getObsMode().equals("imaging")) { %>
		<tr>
			<td>Software Aperture Diameter</td>
			<td><%  out.print(device.toString(obsResult.getAperture())); %> arcsec</td>
		</tr>
		
		<tr>
			<td>Fraction of source flux in aperture</td>
			<td><%  out.print(device.toString(obsResult.getSourceFranction())); %></td>
		</tr>
		
		<tr>
			<td>Enclosed pixels</td>
			<td><%  out.print(device.toString(obsResult.getnPix())); %></td>
		</tr>
		
		<tr>
			<td>Derived Image Size (FWHM) for a point source</td>
			<td><%  out.print(device.toString(obsResult.getImqual())); %> arcsec</td>
		</tr>  
		
		<tr>
			<td>Sky subtraction aperture </td>
			<td><%  out.print(device.toString(obsResult.getSkyAperDiameter())); %> times the sofrware aperture</td>
		</tr>
		<%
		}
		%>
		<tr>
			<td>The peak pixel signal + backgroud</td>
			<td><%  out.print(decFormatExp.format(obsResult.getPeak_pixel_count())); %></td>
		</tr>
		
		<tr>
			<td>Source noise </td>
			<td><% device.setPrecision(1);
					out.print(device.toString(obsResult.getVar_source())); 
				%>
			</td>
		</tr>
		
		<tr>
			<td>Background Noise</td>
			<td><%  out.print(device.toString(obsResult.getVar_background())); %></td>
		</tr> 
		
		<tr>
			<td>Dark Current Noise </td>
			<td><%  out.print(device.toString(obsResult.getVar_dark())); %></td>
		</tr>
		
		<tr>
			<td>Readout noise </td>
			<td><%  out.print(device.toString(obsResult.getVar_readout())); %></td>
		</tr> 
		
		<tr>
			<td>Total noise per exposure</td>
			<td><%  out.print(device.toString(obsResult.getNoise())); %></td>
		</tr>
		
		<tr>
			<td>Total signal per exposure</td>
			<td><%  out.print(device.toString(obsResult.getSignal())); %></td>
		</tr>
				
		<% if (obsResult.getMethod() == 'A') { %>
			<tr>
				<td>Intermediate S/N for one exposure </td>
				<td><% device.setPrecision(3);
					out.print(device.toString(obsResult.getIntermediateS2N())); 
				 	%>
				 </td>
			</tr>
			<tr>
				<td> <% out.print("On-source integration time");%> </td>
				<td>
					<%  
						out.print(Math.round(obsResult.getSrcExpTime()) + " secs, ( =" + Math.round(obsResult.getSrcExpTime() / 60)); 
						out.print(" mins, =" + decFormat.format(obsResult.getSrcExpTime() / 3600) + " hours)");
					%>
				</td>
			</tr>
			<tr>
				<td <% if (obsResult.getMethod() == 'B' || obsResult.getObsMode().equals("imaging")) out.print("class='calc-result'"); %>>Resulting S/N for the whole observation</td>
				<td <% if (obsResult.getMethod() == 'B' || obsResult.getObsMode().equals("imaging")) out.print("class='calc-result'"); %>>
					<% 	device.setPrecision(1);
						out.print(device.toString(obsResult.getFinalS2n()) + " (including sky subtraction)"); 
					%>
				</td>
			</tr>
						
		<% } %>
		
		
		<% if (obsResult.getMethod() == 'B' || obsResult.getMethod() == 'C') { %>
			<tr>
				<td>Derived number of exposures</td>
				<td><% out.print(obsResult.getNumberExposures()); %></td>
			</tr>
			<tr>
				<td>The effective S/N for the whole observation </td>
				<td><% out.print(device.toString(obsResult.getEffectiveS2n())); %></td>
			</tr>	
			<tr>
				<td class="calc-result"> <%  out.print("Required on-source integration time"); %> </td>
				<td class="calc-result"> <%	out.print(Math.round(obsResult.getSrcExpTime()) + " secs, ( =" + Math.round(obsResult.getSrcExpTime() / 60));
						out.print(" mins, =" + decFormat.format(obsResult.getSrcExpTime() / 3600) + " hours)");
					%>
				</td>
			</tr>	
		<% } %>
		
		<% if (obsResult.getObsMode().equals("polarimetry")) { %>
				<tr>
					<td <% if (obsResult.getMethod() == 'A') out.print("class='calc-result'"); %>>Polarization Error</td>
					<td <% if (obsResult.getMethod() == 'A') out.print("class='calc-result'"); %>><% out.print(decFormat2.format(obsResult.getPolarizError()) + " %"); %></td>
				</tr>
		<%
		}
		%>
				
	</table>
	
	<table class="obs-message">
		<tbody>
			<tr><td><% out.print(obsResult.getBackgroundLimitResult());	%> </td></tr>
			<%  List<String> listWarnings = obsResult.getWarnings();
				for (int i = 0; i < listWarnings.size(); ++i) {
					out.println("<tr> <td> "+listWarnings.get(i)+"</td></tr>");
				}
			%>
		</tbody>
	</table>
	
	<h2>Input Parameters</h2>
	<!--  
	ccParameters
	obsConditionParameters
	teleParameters
	 -->
	<h4>Source spatial profile, brightness, and spectral distribution</h4>
	<table class="obs-message">
		<tr>
			<td>The Source is a</td>
			<td><%
					if (sdParameter.getSourceSpec().equals(sdParameter.ELINE)) 
							out.println(
									"single emission line, at a wavelength of "+ sdParameter.getELineWavelength()
									+" microns, and with a width of " + sdParameter.getELineWidth()
									+ " km/s.\n  It's total flux is "+ sdParameter.getELineFlux()
									+" " + sdParameter.getELineFluxUnits() + " on a flat continuum of flux density " 
									+ sdParameter.getELineContinuumFlux()+ " " + sdParameter.getELineContinuumFluxUnits()+"."
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
			<td>Pixel Size in Spatial Direction</td>
			<td><% out.println(instrument.getPixelSize()); %> arcsec</td>
		</tr>
		</tbody>
	
	</table>
	
	 <h4>Observing Conditions</h4>
       
	<table class="obs-message">
		<tbody>
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
		</tbody>
	</table>
	<h4>Calculation and analysis methods</h4>
    <div class="obs-message"> <% out.println(obsDetailsParameters.printParameterSummary().replace("Calculation and analysis methods:", "")); %>  </div>

        	
</body>

<!-- 	
		<tr>	
			<td>
				<% 	if (obsResult.getMethod() == 'B' || obsResult.getMethod() == 'C') {
						out.print("Required total integration time");
					}else	if (obsResult.getMethod() == 'A')
						out.print("Requested total integration time");
				%>
				
			</td>
			
			<td>
				<% 
					device.setPrecision(0);
				  	out.print(
								device.toString(obsResult.getTotalIntegrationTime()) 
								+ " secs, ( =" + device.toString(obsResult.getTotalIntegrationTime() / 60) + 
								"mins, = " + device.toString(obsResult.getTotalIntegrationTime() / 3600) + " hours)"
					);
				%>
			</td>
		</tr>
		-->

</html>