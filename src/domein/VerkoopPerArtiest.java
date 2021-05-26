package domein;

import java.util.ArrayList;

import database.Betalingbeheer;
import database.DatabaseException;
import overzichten.OverzichtArtiest;

public class VerkoopPerArtiest {

	private String artiestCode;
	private String artiestNaam;
	private double opbrengst = 0.0;
	private double reedsBetaald = 0.0;
	private double nogTeBetalen = 0.0;
	private int jaarVanaf = 0;
	private int kwartaalVanaf = 0;
	private int jaarTot = 0;
	private int kwartaalTot = 0;
	private ArrayList<VerkoopPerRelease> alleVerkopenPerRelease = new ArrayList<>();
	private ArrayList<VerkoopPerArtiestPerRelease> alleReleasesPerArtiest = new ArrayList<>();
	private Boolean containsHRR = false;
	private Boolean containsARR = false;

	public VerkoopPerArtiest(Artiest artiest, ArrayList<VerkoopPerRelease> alleVerkopenPerRelease) {

		this.alleVerkopenPerRelease = alleVerkopenPerRelease;
		this.artiestCode = artiest.getArtiestCode();
		this.artiestNaam = artiest.getNaam();
		getAlleArtiestOverzichten();
		bepaalOpbrengst();
		bepaalReedsBetaald();
		bepaalNogTeBetalen();
		new OverzichtArtiest(this);

	}

	private void getAlleArtiestOverzichten() {
		for (int i = 0; i < alleVerkopenPerRelease.size(); i++) {
			VerkoopPerRelease verkoop = alleVerkopenPerRelease.get(i);

			for (int j = 0; j < verkoop.verkopenPerArtiestPerRelease.size(); j++) {
				VerkoopPerArtiestPerRelease verkoopPerArtiestPerRelease = verkoop.verkopenPerArtiestPerRelease.get(j);
	
				if (verkoopPerArtiestPerRelease.getArtiestCode().equals(artiestCode)) {
					alleReleasesPerArtiest.add(verkoopPerArtiestPerRelease);
					setDatumVanaf(alleVerkopenPerRelease.get(i).getJaarVanaf(),
							alleVerkopenPerRelease.get(i).getKwartaalVanaf());
					setDatumTot(alleVerkopenPerRelease.get(i).getJaarTot(),
							alleVerkopenPerRelease.get(i).getKwartaalTot());
					setBooleans(verkoop);
				}
			}
		}
	}

	private void bepaalOpbrengst() {
		for (int i = 0; i < alleReleasesPerArtiest.size(); i++) {
			opbrengst = opbrengst + alleReleasesPerArtiest.get(i).getOpbrengst();
		}
	}

	private void bepaalReedsBetaald() {
		try {
			reedsBetaald = Betalingbeheer.getTotaalBetaaldPerArtiest(artiestCode);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void bepaalNogTeBetalen() {
		nogTeBetalen = opbrengst - reedsBetaald;
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

	public String getArtiestCode() {
		return artiestCode;
	}

	public void addOpbrengst(double opbrengst) {
		this.opbrengst += opbrengst;
	}

	public void addBetaling(double betaling) {
		this.reedsBetaald += betaling;
	}

	public String getArtiestNaam() {
		return artiestNaam;
	}

	public double getOpbrengst() {
		return opbrengst;
	}

	public double getReedsBetaald() {
		return reedsBetaald;
	}

	public double getNogTeBetalen() {
		return nogTeBetalen;
	}

	public void setNogTeBetalen(double nogTeBetalen) {
		this.nogTeBetalen = nogTeBetalen;
	}

	public int getJaarVanaf() {
		return jaarVanaf;
	}

	public void setJaarVanaf(int jaarVanaf) {
		this.jaarVanaf = jaarVanaf;
	}

	public int getKwartaalVanaf() {
		return kwartaalVanaf;
	}

	public void setKwartaalVanaf(int kwartaalVanaf) {
		this.kwartaalVanaf = kwartaalVanaf;
	}

	public int getJaarTot() {
		return jaarTot;
	}

	public void setJaarTot(int jaarTot) {
		this.jaarTot = jaarTot;
	}

	public int getKwartaalTot() {
		return kwartaalTot;
	}

	public void setKwartaalTot(int kwartaalTot) {
		this.kwartaalTot = kwartaalTot;
	}

	public void setArtiestCode(String artiestCode) {
		this.artiestCode = artiestCode;
	}

	public void setArtiestNaam(String artiestNaam) {
		this.artiestNaam = artiestNaam;
	}

	public void setOpbrengst(double opbrengst) {
		this.opbrengst = opbrengst;
	}

	public void setReedsBetaald(double reedsBetaald) {
		this.reedsBetaald = reedsBetaald;
	}

	public ArrayList<VerkoopPerArtiestPerRelease> getAlleReleasesPerArtiest() {
		return alleReleasesPerArtiest;
	}

	public void setBooleans(VerkoopPerRelease verkoop) {
		String hrr = "H";
		String arr = "A";
		if ((verkoop.getReleaseName().charAt(0) + "").equals(hrr)) {
			containsHRR = true;
		}
		if ((verkoop.getReleaseName().charAt(0) + "").equals(arr)) {
			containsARR = true;
		}

	}

	public Boolean getContainsHRR() {
		return containsHRR;
	}

	public Boolean getContainsARR() {
		return containsARR;
	}
}
