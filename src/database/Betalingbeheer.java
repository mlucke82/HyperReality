package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domein.Betaling;

/**
 * Klasse die betalingen naar artietsten ophaald uit de database
 * 
 * @author Merijn
 */

public class Betalingbeheer {

	// de verschillende prep statements worden klaargezet
	private static PreparedStatement prepGeefBetalingenPerArtiest = null;
	private static PreparedStatement prepGeefBetalingen = null;

	// een aantal foutmeldingen wordt als string opgeslagen
	private static String foutmeldingOphalen = "er is iets mis gegaan bij het ophalen van de gegevens";

	/**
	 * Initieert de vulling van de prep statements. De insert heeft een afwijkende
	 * melding.
	 * 
	 * @throws DatabaseException
	 */
	static void init() throws DatabaseException {

		try {
			prepGeefBetalingenPerArtiest = Connectiebeheer.getCon()
					.prepareStatement("SELECT * FROM betalingen WHERE ArtiestCode = ?");
			prepGeefBetalingen = Connectiebeheer.getCon().prepareStatement("SELECT * FROM betalingen");
		} catch (SQLException e) {
			throw new DatabaseException(foutmeldingOphalen);
		}
	}

	/**
	 * 
	 * @param key key van de artiest
	 * @return betalingen betalingen aan de artiest met de ingevoerde artiestCode
	 * @throws DatabaseException
	 */

	public static ArrayList<Betaling> getBetalingenPerArtiest(String artiestCode) throws DatabaseException {
		ArrayList<Betaling> betalingen = new ArrayList<Betaling>();
		try {
			prepGeefBetalingenPerArtiest.setString(1, artiestCode);
			ResultSet res = prepGeefBetalingenPerArtiest.executeQuery();
			while (res.next()) {
				Betaling betaling = new Betaling();
				betaling.setArtiestCode(artiestCode);
				betaling.setKey(res.getInt("Key"));
				betaling.setBetaaldBedrag(res.getDouble("BetaaldBedrag"));
				betalingen.add(betaling);
			}
		} catch (SQLException e) {
			throw new DatabaseException(foutmeldingOphalen);
		}
		return betalingen;
	}

	/**
	 * Geeft een array met alle betalingen
	 * 
	 * @throws DatabaseException
	 */

	public static ArrayList<Betaling> getBetalingen() throws DatabaseException {
		ArrayList<Betaling> betalingen = new ArrayList<Betaling>();
		try {
			ResultSet res = prepGeefBetalingen.executeQuery();
		
			while (res.next()) {
				Betaling betaling = new Betaling();
				betaling.setArtiestCode(res.getString("ArtiestCode"));
				betaling.setKey(res.getInt("Key"));
				betaling.setBetaaldBedrag(res.getDouble("BetaaldBedrag"));
				betalingen.add(betaling);
			}
		} catch (SQLException e) {
			throw new DatabaseException(foutmeldingOphalen);
		}
		return betalingen;
	}

	/**
	 * Geeft een double met totaal betaald bedrag per artiest
	 * 
	 * @throws DatabaseException
	 */

	public static double getTotaalBetaaldPerArtiest(String artiestCode) throws DatabaseException {
		Double totaalBedrag = 0.0;

		try {
			prepGeefBetalingenPerArtiest.setString(1, artiestCode);
			ResultSet res = prepGeefBetalingenPerArtiest.executeQuery();
			while (res.next()) {
				totaalBedrag += res.getDouble("BetaaldBedrag");
			}
		} catch (SQLException e) {
			throw new DatabaseException(foutmeldingOphalen);
		}
		return totaalBedrag;
	}

}
