package domein;

import java.util.ArrayList;

public class VerkoopPerArtiestPerRelease {

	private String artiestNaam;
	private String artiestCode;
	private String releaseName;
	private String ean;
	private double Opbrengst = 0.0;
	private ArrayList<VerkoopPerArtiestPerTrack> verkopenPerArtiestPerTrack = new ArrayList<>();

	public VerkoopPerArtiestPerRelease(Artiest artiest) {
		this.artiestNaam = artiest.getNaam();
		this.artiestCode = artiest.getArtiestCode();
	}

	public String getArtiestNaam() {
		return artiestNaam;
	}

	public String getArtiestCode() {
		return artiestCode;
	}

	public double getOpbrengst() {
		return Opbrengst;
	}

	public void voegVerkoopToeHRR(Track track, VerkoopPerTrack verkoopTotaalPerTrack) {

		if (track.getArtiest2Key() == null) {
			track.setArtiest2Perc(0);
		}
		if (track.getRemixer1Key() == null) {
			track.setRemixer1Perc(0);
		}
		if (track.getRemixer2Key() == null) {
			track.setRemixer2Perc(0);
		}

		double bedrag = 0.0;
		int percentageArtiesten = track.getArtiest1Perc() + track.getArtiest2Perc() + track.getRemixer1Perc()
				+ track.getRemixer2Perc();
		int percentageHRR = 100 - percentageArtiesten;
		bedrag = verkoopTotaalPerTrack.getOpbrengst() * percentageHRR / 100;

		Opbrengst = Opbrengst + bedrag;

		verkopenPerArtiestPerTrack
				.add(new VerkoopPerArtiestPerTrack(releaseName, track.getTrackname(), "101", percentageHRR, Opbrengst));

	}

	public void voegVerkoopToeArtiest(Track track, VerkoopPerTrack verkoopTotaalPerTrack) {

		double bedrag = 0.0;

		int percentage = 0;

		if (track.getArtiest2Key() == null) {
			track.setArtiest2Key("");
		}
		if (track.getRemixer1Key() == null) {
			track.setRemixer1Key("");
		}
		if (track.getRemixer2Key() == null) {
			track.setRemixer2Key("");
		}

		if (track.getArtiest1Key().equals(artiestCode)) {
			percentage = track.getArtiest1Perc();
			bedrag = verkoopTotaalPerTrack.getOpbrengst() * percentage / 100;
		}

		if (track.getArtiest2Key().equals(artiestCode)) {
			percentage = track.getArtiest2Perc();
			bedrag = verkoopTotaalPerTrack.getOpbrengst() * percentage / 100;
		}

		if (track.getRemixer1Key().equals(artiestCode)) {
			percentage = track.getRemixer1Perc();
			bedrag = verkoopTotaalPerTrack.getOpbrengst() * percentage / 100;
		}

		if (track.getRemixer2Key().equals(artiestCode)) {
			percentage = track.getRemixer2Perc();
			bedrag = verkoopTotaalPerTrack.getOpbrengst() * percentage / 100;
		}

		Opbrengst = Opbrengst + bedrag;

		verkopenPerArtiestPerTrack.add(
				new VerkoopPerArtiestPerTrack(releaseName, track.getTrackname(), artiestCode, percentage, Opbrengst));

	}

	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public String getReleaseName() {
		return releaseName;
	}

	public String getEan() {
		return ean;
	}

	public ArrayList<VerkoopPerArtiestPerTrack> getVerkopenPerArtiestPerTrack() {
		return verkopenPerArtiestPerTrack;
	}

}
