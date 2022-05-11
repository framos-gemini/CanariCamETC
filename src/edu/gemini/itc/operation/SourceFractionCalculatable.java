package edu.gemini.itc.operation;

import edu.gemini.itc.shared.FormatStringWriter;

public interface SourceFractionCalculatable extends Calculatable{
	public void setImageQuality( double im_qual);
        public void setApType(String ap_type);
        public void setApDiam(double ap_diam);
        public void setSFPrint(boolean SFprint);

	public double getSourceFraction();
	public double getNPix();
	public double getApDiam();
	public double getApPix();
	public double getSwAp();
	public double getApRatio();
	public double getApFrac();

}
