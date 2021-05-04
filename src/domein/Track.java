package domein;

/**
 * Representeert artiest.
 * 
 * @author Merijn Lucke
 */
public class Track {
	private int key;
	private String ISRC;
	private String EAN;
	private String Trackname;
	private String Artiest1Key;
	private String Artiest2Key;
	private String Remixer1Key;
	private String Remixer2Key;
	private String releasename;
	private int Artiest1Perc;
	private int Artiest2Perc;
	private int Remixer1Perc;
	private int Remixer2Perc;
	private double Mastering;
	private double Promo;

	/**
	 * Creeert klant.
	 * 
	 * @param klantnummer klantnummer
	 * @param naam        naam
	 * @param telefoon    telefoonnummer
	 */
	public Track() {
	}

	/**
	 * Geeft klantnummer.
	 * 
	 * @return klantnummer
	 */
	public int getKey() {
		return key;
	}

	/**
	 * Geeft stringrepresentatie van klant.
	 * 
	 * @return stringrepresentatie.
	 */
	public String toString() {
		return Remixer1Key + ", ";
	}

	public String getISRC() {
		return ISRC;
	}

	public void setISRC(String iSRC) {
		ISRC = iSRC;
	}

	public String getEAN() {
		return EAN;
	}

	public void setEAN(String eAN) {
		EAN = eAN;
	}

	public String getTrackname() {
		return Trackname;
	}

	public void setTrackname(String trackname) {
		Trackname = trackname;
	}

	public String getArtiest1Key() {
		return Artiest1Key;
	}

	public void setArtiest1Key(String artiest1Key) {
		Artiest1Key = artiest1Key;
	}

	public String getArtiest2Key() {
		return Artiest2Key;
	}

	public void setArtiest2Key(String artiest2Key) {
		Artiest2Key = artiest2Key;
	}

	public String getRemixer1Key() {
		return Remixer1Key;
	}

	public void setRemixer1Key(String remixer1Key) {
		Remixer1Key = remixer1Key;
	}

	public String getRemixer2Key() {
		return Remixer2Key;
	}

	public void setRemixer2Key(String remixer2Key) {
		Remixer2Key = remixer2Key;
	}

	public int getArtiest1Perc() {
		return Artiest1Perc;
	}

	public void setArtiest1Perc(int artiest1Perc) {
		Artiest1Perc = artiest1Perc;
	}

	public int getArtiest2Perc() {
		return Artiest2Perc;
	}

	public void setArtiest2Perc(int artiest2Perc) {
		Artiest2Perc = artiest2Perc;
	}

	public int getRemixer1Perc() {
		return Remixer1Perc;
	}

	public void setRemixer1Perc(int remixer1Perc) {
		Remixer1Perc = remixer1Perc;
	}

	public int getRemixer2Perc() {
		return Remixer2Perc;
	}

	public void setRemixer2Perc(int remixer2Perc) {
		Remixer2Perc = remixer2Perc;
	}

	public double getMastering() {
		return Mastering;
	}

	public void setMastering(double d) {
		Mastering = d;
	}

	public double getPromo() {
		return Promo;
	}

	public void setPromo(double d) {
		Promo = d;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getReleasename() {
		return releasename;
	}

	public void setReleasename(String releasename) {
		this.releasename = releasename;
	}

}
