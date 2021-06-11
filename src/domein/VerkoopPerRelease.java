package domein;

import java.util.ArrayList;

import database.Artiestbeheer;
import database.DatabaseException;
import database.Trackbeheer;
import database.Verkoopbeheer;
import overzichten.OverzichtRelease;

public class VerkoopPerRelease {

	public String releaseName;
	public String ean;
	public double brutoOpbrengst = 0.0;
	public double mastering = 0.0;
	public double promo = 0.0;
	public double opbrengst = 0.0;
	public int jaarVanaf = 0;
	public int kwartaalVanaf = 0;
	public int jaarTot = 0;
	public int kwartaalTot = 0;
	public ArrayList<Verkoop> verkopenPerEan;
	public ArrayList<Track> tracksPerEan;
	public ArrayList<Artiest> artiestenPerEan;
	public ArrayList<VerkoopPerTrack> verkopenPerTrack;
	public ArrayList<VerkoopPerArtiestPerRelease> verkopenPerArtiestPerRelease;

	public VerkoopPerRelease(String ean, int year, int quarter) {

		this.ean = ean;
		getVerkopenPerEAN(year, quarter);
		releaseName = verkopenPerEan.get(0).getCatalog();
		getTracksPerEAN();
		getArtiesten();
		bepaalVerkopenPerTrack();
		bepaalVerkopenPerArtiestPerRelease();
		bepaalTotalen();
		new OverzichtRelease(this);

	}

	// geeft alle verkoop regels voor de release
	private void getVerkopenPerEAN(int year, int quarter) {
		try {
			this.verkopenPerEan = Verkoopbeheer.getVerkopen(ean, year, quarter);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// geeft alle tracks van de release
	private void getTracksPerEAN() {
		try {
			this.tracksPerEan = Trackbeheer.getTracks(ean);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// geeft alle artiesten van de release
	private void getArtiesten() {

		ArrayList<String> lijst = new ArrayList<>();
		ArrayList<Artiest> artiesten = new ArrayList<>();

		// de artiest keys worden opgehaald
		for (int i = 0; i < tracksPerEan.size(); i++) {
			Track track = tracksPerEan.get(i);
			if (track.getArtiest1Key() != null && !lijst.contains(track.getArtiest1Key())) {
				lijst.add(track.getArtiest1Key());
			}
			if (track.getArtiest2Key() != null && !lijst.contains(track.getArtiest2Key())) {
				lijst.add(track.getArtiest2Key());
			}
			if (track.getRemixer1Key() != null && !lijst.contains(track.getRemixer1Key())) {
				lijst.add(track.getRemixer1Key());
			}
			if (track.getRemixer2Key() != null && !lijst.contains(track.getRemixer2Key())) {
				lijst.add(track.getRemixer2Key());
			}
		}

		// tbv toevoegen HRR als artiest
		lijst.add("101");

		// de artiesten worden opgehaald op basis van de keys
		for (int i = 0; i < lijst.size(); i++) {
			try {
				artiesten.add(Artiestbeheer.getArtiest(lijst.get(i)));
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.artiestenPerEan = artiesten;
	}

	// bepaald de verkoopcijfers per track over alle verkopen heen
	public void bepaalVerkopenPerTrack() {
		// de arraylist met verkopenPerTrack wordt geinitialiseerd
		verkopenPerTrack = new ArrayList<>();

		// voor iedere aanwezige track wordt een verkoopPerTrack aangemaakt
		for (int i = 0; i < tracksPerEan.size(); i++) {
			VerkoopPerTrack verkoopPerTrack = new VerkoopPerTrack(tracksPerEan.get(i));

			// voor iedere aanwezige track wordt een track aangemaakt
			// to do: kan naar hierboven
			for (int j = 0; j < tracksPerEan.size(); j++) {
				Track track = tracksPerEan.get(j);

				// per verkoopPerTrack wordt de lijst met verkopen afgelopen en eventueel
				// toegevoegd
				if (verkoopPerTrack.getISRC().equals(track.getISRC())) {
					for (int k = 0; k < verkopenPerEan.size(); k++) {
						Verkoop verkoop = verkopenPerEan.get(k);
						verkoopPerTrack.setJaarTot(verkoop);
						verkoopPerTrack.setJaarVanaf(verkoop);
						if (verkoop.getISRC().equals("null")) {
							verkoopPerTrack.voegAlbumVerkoopToe(track, verkoop, tracksPerEan.size());
							verkoopPerTrack.addAlbumDownload(verkoop.getQty());
						} else if (verkoop.getISRC().equals(track.getISRC())) {
							verkoopPerTrack.voegLosseVerkoopToe(track, verkoop);
							if (verkoop.getFormat().equals("Stream")) {
								verkoopPerTrack.addStream(verkoop.getQty());
							} else {
								verkoopPerTrack.addDownload(verkoop.getQty());
							}
						}
					}

					verkoopPerTrack.setMastering(track.getMastering());
					verkoopPerTrack.setPromo(track.getPromo());
					verkoopPerTrack.setBrutoOpbrengst();
					verkoopPerTrack.minusMasteringCost();
					verkoopPerTrack.minusPromoCost();
				}
			}
			// de verkoopPerTrack wordt aan de arraylist toegevoegd
			verkopenPerTrack.add(verkoopPerTrack);
		}
	}

	public void bepaalVerkopenPerArtiestPerRelease() {
		// de arraylist met verkopenPerRelease wordt geinitialiseerd
		verkopenPerArtiestPerRelease = new ArrayList<>();

		// voor iedere artiest wordt er een verkoopPerArtiestPerRelease aangemaakt
		for (int i = 0; i < artiestenPerEan.size(); i++) {
			VerkoopPerArtiestPerRelease verkoopPerArtiestPerRelease = new VerkoopPerArtiestPerRelease(artiestenPerEan.get(i));
			
			// er worden verkoopPerTrack en Tracks aangemaakt en deze worden gebruikt om de
			// methoden aan te roepen
			for (int j = 0; j < verkopenPerTrack.size(); j++) {

				VerkoopPerTrack verkoopPerTrack = verkopenPerTrack.get(j);
				Track track = verkoopPerTrack.getTrack();
				
					verkoopPerArtiestPerRelease.setEan(ean);
					verkoopPerArtiestPerRelease.setReleaseName(releaseName);
				
				if (verkoopPerArtiestPerRelease.getArtiestCode().equals("101")) {
					verkoopPerArtiestPerRelease.voegVerkoopToeHRR(track, verkoopPerTrack);
				} else {
					verkoopPerArtiestPerRelease.voegVerkoopToeArtiest(track, verkoopPerTrack);
				}
			}
			// de verkoopPerArtiestPerRelease wordt toegevoegd aan de arraylist
			verkopenPerArtiestPerRelease.add(verkoopPerArtiestPerRelease);
		}
	}

	public void bepaalTotalen() {
		for (int i = 0; i < verkopenPerTrack.size(); i++) {

			VerkoopPerTrack verkoopPerTrack = verkopenPerTrack.get(i);

			brutoOpbrengst = brutoOpbrengst + verkoopPerTrack.getBrutoOpbrengst();
			mastering = mastering + verkoopPerTrack.getMastering();
			promo = promo + verkoopPerTrack.getPromo();
			opbrengst = opbrengst + verkoopPerTrack.getOpbrengst();
			setDatumVanaf(verkoopPerTrack.getJaarVanaf(), verkoopPerTrack.getKwartaalVanaf());
			setDatumTot(verkoopPerTrack.getJaarTot(), verkoopPerTrack.getKwartaalTot());
		}
	}

	public void setDatumVanaf(int year, int quarter) {
		if (this.jaarVanaf == 0) {
			this.jaarVanaf = year;
			this.kwartaalVanaf = quarter;
		} else if ((year + "" + quarter).compareTo(this.jaarVanaf + "" + this.kwartaalVanaf) < 0) {
			this.jaarVanaf = year;
			this.kwartaalVanaf = quarter;
		}
	}

	public void setDatumTot(int year, int quarter) {
		if (this.jaarTot == 0) {
			this.jaarTot = year;
			this.kwartaalTot = quarter;
		} else if ((year + "" + quarter).compareTo(this.jaarTot + "" + this.kwartaalTot) > 0) {
			this.jaarTot = year;
			this.kwartaalTot = quarter;
		}
	}

	public String getReleaseName() {
		return releaseName;
	}

	public String getEan() {
		return ean;
	}

	public double getBrutoOpbrengst() {
		return brutoOpbrengst;
	}

	public double getMastering() {
		return mastering;
	}

	public double getPromo() {
		return promo;
	}

	public double getOpbrengst() {
		return opbrengst;
	}

	public int getJaarVanaf() {
		return jaarVanaf;
	}

	public int getKwartaalVanaf() {
		return kwartaalVanaf;
	}

	public int getJaarTot() {
		return jaarTot;
	}

	public int getKwartaalTot() {
		return kwartaalTot;
	}

	public ArrayList<Verkoop> getVerkopenPerEan() {
		return verkopenPerEan;
	}

	public ArrayList<Track> getTracksPerEan() {
		return tracksPerEan;
	}

	public ArrayList<VerkoopPerTrack> getVerkopenPerTrack() {
		return verkopenPerTrack;
	}

	public ArrayList<VerkoopPerArtiestPerRelease> getVerkopenPerArtiestPerRelease() {
		return verkopenPerArtiestPerRelease;
	}

}
