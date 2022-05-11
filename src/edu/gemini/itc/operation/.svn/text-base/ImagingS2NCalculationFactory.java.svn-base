package edu.gemini.itc.operation;

import edu.gemini.itc.parameters.ObservingConditionParameters;
import edu.gemini.itc.parameters.ObservationDetailsParameters;
import edu.gemini.itc.parameters.SourceDefinitionParameters;
import edu.gemini.itc.parameters.TeleParameters;

import edu.gemini.itc.shared.Instrument;


public class ImagingS2NCalculationFactory extends CalculationFactory {

    public Calculatable getCalculationInstance( SourceDefinitionParameters sourceDefinitionParameters,
						ObservationDetailsParameters observationDetailsParameters,
						ObservingConditionParameters observingConditionParameters,
						TeleParameters teleParameters,
						Instrument instrument)
    {
        //Method A if S2N

        if (observationDetailsParameters.getCalculationMethod().equals(ObservationDetailsParameters.S2N)) {

            return new ImagingS2NMethodACalculation( observationDetailsParameters.getNumExposures(),
						     observationDetailsParameters.getSourceFraction(),
						     observationDetailsParameters.getExposureTime(),
						     instrument.getReadNoise(),
						     instrument.getPixelSize() );

        }    // Case B if Integration Time
	else if (observationDetailsParameters.getCalculationMethod().equals(ObservationDetailsParameters.INTTIME)) {

            if (sourceDefinitionParameters.getSourceGeometry().equals(SourceDefinitionParameters.EXTENDED_SOURCE)) {

                if (sourceDefinitionParameters.getExtendedSourceType().equals(SourceDefinitionParameters.UNIFORM)) {
                    return new ImagingUSBS2NMethodBCalculation( observationDetailsParameters.getNumExposures(),
								observationDetailsParameters.getSourceFraction(),
								observationDetailsParameters.getExposureTime(),
								instrument.getReadNoise(),
								observationDetailsParameters.getSNRatio(),
								instrument.getPixelSize() );
                }
            }
	    else {
                return new ImagingPointS2NMethodBCalculation( observationDetailsParameters.getNumExposures(),
							      observationDetailsParameters.getSourceFraction(),
							      observationDetailsParameters.getExposureTime(),
							      instrument.getReadNoise(),
							      observationDetailsParameters.getSNRatio(),
							      instrument.getPixelSize() );
            }
            // Case C should be method C
        } else {

            if (sourceDefinitionParameters.getSourceGeometry().equals(SourceDefinitionParameters.EXTENDED_SOURCE)) {

                if (sourceDefinitionParameters.getExtendedSourceType().equals(SourceDefinitionParameters.UNIFORM)) {

                    return new ImagingUSBS2NMethodCCalculation( observationDetailsParameters.getNumExposures(),
								observationDetailsParameters.getSourceFraction(),
								observationDetailsParameters.getExposureTime(),
								instrument.getReadNoise(),
								observationDetailsParameters.getSNRatio(),
								instrument.getPixelSize() );
                }
            }
	    else {
		return new ImagingPointS2NMethodCCalculation( observationDetailsParameters.getNumExposures(),
							      observationDetailsParameters.getSourceFraction(),
							      observationDetailsParameters.getExposureTime(),
							      instrument.getReadNoise(),
							      observationDetailsParameters.getSNRatio(),
							      instrument.getPixelSize() );
	    }
	}

	return new ImagingPointS2NMethodCCalculation( observationDetailsParameters.getNumExposures(),
						      observationDetailsParameters.getSourceFraction(),
						      observationDetailsParameters.getExposureTime(),
						      instrument.getReadNoise(),
						      observationDetailsParameters.getSNRatio(),
						      instrument.getPixelSize() );
    }
}
