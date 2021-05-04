package domein;

public class VerkoopPerTrack {

	private Track track;
	private String ISRC;
	private String EAN;
	private String trackname;
	private double brutoOpbrengst = 0.0;
	private double mastering = 0.0;
	private double promo = 0.0;
	private double opbrengst = 0.0;
	private int albumDownloads = 0;
	private int downloads = 0;
	private int streams = 0;
	private int jaarVanaf = 0;
	private int kwartaalVanaf = 0;
	private int jaarTot = 0;
	private int kwartaalTot = 0;

	public VerkoopPerTrack(Track track) {
		this.track = track;
		this.setISRC(track.getISRC());
		this.EAN = track.getEAN();
		this.trackname = track.getTrackname();
	}

	public void addAlbumDownload(int qty) {
		albumDownloads = albumDownloads + qty;
	}

	public void addDownload(int qty) {
		downloads = downloads + qty;
	}

	public void addStream(int qty) {
		streams = streams + qty;
	}

	public Track getTrack() {
		return track;
	}

	public String getEAN() {
		return EAN;
	}

	public String getTrackname() {
		return trackname;
	}

	public double getBrutoOpbrengst() {
		return brutoOpbrengst;
	}

	public double getPromo() {
		return promo;
	}

	public void setPromo(double promo) {
		this.promo = promo;
	}

	public double getMastering() {
		return mastering;
	}

	public void setMastering(double mastering) {
		this.mastering = mastering;
	}

	public String getISRC() {
		return ISRC;
	}

	public void setISRC(String iSRC) {
		ISRC = iSRC;
	}

	public double getOpbrengst() {
		return opbrengst;
	}

	public int getAlbumDownloads() {
		return albumDownloads;
	}

	public int getDownloads() {
		return downloads;
	}

	public int getStreams() {
		return streams;
	}

	public void voegLosseVerkoopToe(Track track, Verkoop verkoop) {

		double bedrag = 0.0;
		bedrag = verkoop.getRoyalty();
		opbrengst = opbrengst + bedrag;
	}

	public void voegAlbumVerkoopToe(Track track, Verkoop verkoop, int tracks) {

		double bedrag = 0.0;

		bedrag = verkoop.getRoyalty() / tracks;

		opbrengst = opbrengst + bedrag;
	}

	public void minusMasteringCost() {
		opbrengst = Math.max(opbrengst - mastering, 0.0);
	}

	public void minusPromoCost() {
		opbrengst = Math.max(opbrengst - promo, 0.0);
	}

	public void setBrutoOpbrengst() {
		brutoOpbrengst = opbrengst;
	}

	public int getJaarVanaf() {
		return jaarVanaf;
	}

	public int getKwartaalVanaf() {
		return kwartaalVanaf;
	}

	public void setJaarVanaf(Verkoop verkoop) {
		if (this.jaarVanaf == 0) {
			this.jaarVanaf = verkoop.getYear();
			this.kwartaalVanaf = verkoop.getQuarter();
		} else if ((verkoop.getYear() + "" + verkoop.getQuarter())
				.compareTo(this.jaarVanaf + "" + this.kwartaalVanaf) < 0) {
			this.jaarVanaf = verkoop.getYear();
			this.kwartaalVanaf = verkoop.getQuarter();
		}
	}

	public int getJaarTot() {
		return jaarTot;
	}

	public int getKwartaalTot() {
		return kwartaalTot;
	}

	public void setJaarTot(Verkoop verkoop) {
		if (this.jaarTot == 0) {
			this.jaarTot = verkoop.getYear();
			this.kwartaalTot = verkoop.getQuarter();
		} else if ((verkoop.getYear() + "" + verkoop.getQuarter())
				.compareTo(this.jaarTot + "" + this.kwartaalTot) > 0) {
			this.jaarTot = verkoop.getYear();
			this.kwartaalTot = verkoop.getQuarter();
		}
	}

}
