package domein;

public class Verkoop {

	private int Key;
	private String LabelName;
	private String Catalog;
	private String ReleaseArtist;
	private String ReleaseName;
	private String TrackArtist;
	private String TrackTitle;
	private String MixName;
	private String Format;
	private String SaleType;
	private int Qty;
	private double Value;
	private double Deal;
	private double Royalty;
	private String ISRC;
	private String EAN;
	private String StoreName;
	private String YourTrackRef;
	private int Year;
	private int Quarter;

	/**
	 * Creeert klant.
	 * 
	 * @param klantnummer klantnummer
	 * @param naam        naam
	 * @param telefoon    telefoonnummer
	 */
	public Verkoop() {
	}

	public String toString() {
		return (Catalog + ", " + SaleType + ", " + Year + ", " + Quarter);
	}

	public int getKey() {
		return Key;
	}

	public void setKey(int key) {
		Key = key;
	}

	public String getLabelName() {
		return LabelName;
	}

	public void setLabelName(String labelName) {
		LabelName = labelName;
	}

	public String getCatalog() {
		return Catalog;
	}

	public void setCatalog(String catalog) {
		Catalog = catalog;
	}

	public String getReleaseArtist() {
		return ReleaseArtist;
	}

	public void setReleaseArtist(String releaseArtist) {
		ReleaseArtist = releaseArtist;
	}

	public String getReleaseName() {
		return ReleaseName;
	}

	public void setReleaseName(String releaseName) {
		ReleaseName = releaseName;
	}

	public String getTrackArtist() {
		return TrackArtist;
	}

	public void setTrackArtist(String trackArtist) {
		TrackArtist = trackArtist;
	}

	public String getTrackTitle() {
		return TrackTitle;
	}

	public void setTrackTitle(String trackTitle) {
		TrackTitle = trackTitle;
	}

	public String getMixName() {
		return MixName;
	}

	public void setMixName(String mixName) {
		MixName = mixName;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public String getSaleType() {
		return SaleType;
	}

	public void setSaleType(String saleType) {
		SaleType = saleType;
	}

	public int getQty() {
		return Qty;
	}

	public void setQty(int qty) {
		Qty = qty;
	}

	public double getValue() {
		return Value;
	}

	public void setValue(double value) {
		Value = value;
	}

	public double getDeal() {
		return Deal;
	}

	public void setDeal(double deal) {
		Deal = deal;
	}

	public double getRoyalty() {
		return Royalty;
	}

	public void setRoyalty(double royalty) {
		Royalty = royalty;
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

	public String getStoreName() {
		return StoreName;
	}

	public void setStoreName(String storeName) {
		StoreName = storeName;
	}

	public String getYourTrackRef() {
		return YourTrackRef;
	}

	public void setYourTrackRef(String yourTrackRef) {
		YourTrackRef = yourTrackRef;
	}

	public int getYear() {
		return Year;
	}

	public void setYear(int year) {
		Year = year;
	}

	public int getQuarter() {
		return Quarter;
	}

	public void setQuarter(int quarter) {
		Quarter = quarter;
	}

}