package domein;

import java.util.ArrayList;

import database.*;

public class Runner {

	public static void main(String[] args) throws DatabaseException {

		Connectiebeheer.init();

		// te zoeken release
		// String eanCode = "5054281527027";

		// geef 1 release overzicht 
		// getReleaseOverzicht(eanCode);
		
		// geeft alle release overzichten 
		// getAlleReleaseOverzichten();
		
		// geeft alle artiest én release overzichten
		getAlleOverzichten(2020, 3);

	}

	private static void getAlleOverzichten(int year, int quarter) {
		ArrayList<VerkoopPerRelease> alleVerkopenPerRelease = getAlleReleaseOverzichten(year, quarter);
		ArrayList<String> alleArtiestCodes;
		ArrayList<Artiest> alleArtiesten = new ArrayList<>();

		try {
			alleArtiestCodes = Artiestbeheer.getArtiestCodes();
			for (int i = 0; i < alleArtiestCodes.size(); i++) {
				alleArtiesten.add(Artiestbeheer.getArtiest(alleArtiestCodes.get(i)));
			}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < alleArtiesten.size(); i++) {
			new VerkoopPerArtiest(alleArtiesten.get(i), alleVerkopenPerRelease);
		}

	}

	private static ArrayList<VerkoopPerRelease> getAlleReleaseOverzichten(int year, int quarter) {
		ArrayList<VerkoopPerRelease> verkopenPerRelease = new ArrayList<>();
		try {
			ArrayList<String> eanCodes = Trackbeheer.getEan();
			for (int i = 0; i < eanCodes.size(); i++) {
				verkopenPerRelease.add(getReleaseOverzicht(eanCodes.get(i), year, quarter));
			}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return verkopenPerRelease;
	}

	private static VerkoopPerRelease getReleaseOverzicht(String ean, int year, int quarter) {
		VerkoopPerRelease verkoopPerRelease = new VerkoopPerRelease(ean, year, quarter);
		return verkoopPerRelease;
	}

}
