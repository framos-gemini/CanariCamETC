// Copyright 1999 Association for Universities for Research in Astronomy, Inc.,
// Observatory Control System, Gemini Telescopes Project.
// See the file COPYRIGHT for complete details.
//
// $Id: CloudTransmissionVisitor.java,v 1.2 2003/11/21 14:31:02 shane Exp $
//
package edu.gemini.itc.operation;

import edu.gemini.itc.shared.SampledSpectrumVisitor;
import edu.gemini.itc.shared.SampledSpectrum;
import edu.gemini.itc.shared.ArraySpectrum;
import edu.gemini.itc.shared.DefaultArraySpectrum;
import edu.gemini.itc.shared.TransmissionElement;
import edu.gemini.itc.shared.ITCConstants;

/**
 * The CloudTransmissionVisitor is designed to adjust the SED for
 * clouds in the atmosphere.
 */
public final class CloudTransmissionVisitor extends TransmissionElement {
    private static final String FILENAME = "cloud_trans";

    /**
     * Constructs transmission visitor for clouds.
     */
    public CloudTransmissionVisitor(int skyTransparencyCloud)
            throws Exception {
        setTransmissionSpectrum(ITCConstants.TRANSMISSION_LIB + "/" + FILENAME +
                                skyTransparencyCloud + ITCConstants.DATA_SUFFIX);
    }

    public String toString() {
        return ("CloudTransmission");
    }
}
