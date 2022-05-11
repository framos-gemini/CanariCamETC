package edu.gemini.itc.operation;

import edu.gemini.itc.shared.FormatStringWriter;
import edu.gemini.itc.shared.ObservationResult;

public interface Calculatable {
    public void calculate() throws Exception;

    public String getTextResult(FormatStringWriter device, ObservationResult obsResult);
}
