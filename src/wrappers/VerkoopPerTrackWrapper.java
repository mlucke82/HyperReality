package wrappers;

import domein.VerkoopPerTrack;

public class VerkoopPerTrackWrapper {
	
	VerkoopPerTrack verkoopPerTrack;
	
	String trackname;
	String isrc;
	String ean;
	String brutoOpbrengst;
	String mastering;
	String promo;
	String nettoOpbrengst;
	
	public VerkoopPerTrackWrapper(VerkoopPerTrack verkoopPerTrack) {
		this.verkoopPerTrack = verkoopPerTrack;
		this.trackname = verkoopPerTrack.getTrackname();
		this.isrc = verkoopPerTrack.getISRC();
		this.ean = verkoopPerTrack.getEAN();
		this.brutoOpbrengst = (verkoopPerTrack.getBrutoOpbrengst() + "");
		this.mastering = (verkoopPerTrack.getMastering() + "");
		this.promo = (verkoopPerTrack.getPromo() + "");
		this.nettoOpbrengst = (verkoopPerTrack.getOpbrengst() + "");
		
	}
	
	public String getTrackname() {
		return trackname;
	}

	public String getIsrc() {
		return isrc;
	}

	public String getEan() {
		return ean;
	}

	public String getBrutoOpbrengst() {
		return brutoOpbrengst;
	}

	public String getMastering() {
		return mastering;
	}

	public String getPromo() {
		return promo;
	}

	public String getNettoOpbrengst() {
		return nettoOpbrengst;
	}
	
}
