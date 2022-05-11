package edu.gemini.itc.operation;

import edu.gemini.itc.parameters.ObservingConditionParameters;
import edu.gemini.itc.parameters.ObservationDetailsParameters;
import edu.gemini.itc.parameters.SourceDefinitionParameters;
import edu.gemini.itc.parameters.TeleParameters;

import edu.gemini.itc.shared.Instrument;

public abstract class CalculationFactory {
    abstract Calculatable getCalculationInstance(
            SourceDefinitionParameters sourceDefinitionParameters,
            ObservationDetailsParameters observationDetailsParameters,
            ObservingConditionParameters observingConditionParameters,
            TeleParameters teleParameters,
            Instrument instrument);
    
    public boolean optionUF = false;
}

