README.TXT

SUMMARY

The CanariCamETC is charge of making the Exposure Time Calculator for CanariCam 
instrument.

REQUERIMENTS

Is neccesary the following requirements to deploy the project: Maven3, 
Apache Tomcat 7.0.54 minimun, java openjdk 7.

STRUCTURE

The project is split into the following files and directories:

    	* Pom.xml: it is an XML file that contains information about the project 
		and configuration details used by Maven to build the project.
	
	* WebContent: it is a directory that contains the implementation of
		the web interface. (JSP files, html files, etc).

	* itcdata: it is a directory that stores all configuration files like:
		configuration file of CanariCam instrument, Telescope Emissivity,
		Sky Emission, etc.

	* src: Source directory that contains all source code, e.g., java code 
		implementation. 

	* target: This directory will contain itc.war file. The itc.war is file 
		create when you perform a project release. The command is:
		mvn clean compile war:war 

