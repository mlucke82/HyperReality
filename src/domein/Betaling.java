package domein;

/**
 * Representeert artiest.
 * 
 * @author Merijn Lucke
 */
public class Betaling {

	private int key;
	private String artiestCode;
	private double betaaldBedrag;
	private String datum;

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getArtiestCode() {
		return artiestCode;
	}

	public void setArtiestCode(String artiestCode) {
		this.artiestCode = artiestCode;
	}

	public double getBetaaldBedrag() {
		return betaaldBedrag;
	}

	public void setBetaaldBedrag(double betaaldBedrag) {
		this.betaaldBedrag = betaaldBedrag;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

}