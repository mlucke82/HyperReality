
package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domein.Artiest;

/**
 * Klasse die klanten en hun gegevens ophaalt uit de database
 * 
 * @author Merijn
 */
public class Artiestbeheer {

	// de verschillende prep statements worden klaargezet
	private static PreparedStatement prepGeefArtiestData = null;
	private static PreparedStatement prepGeefArtiestCodes = null;

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
			prepGeefArtiestData = Connectiebeheer.getCon()
					.prepareStatement("SELECT * FROM artiesten WHERE ArtiestCode = ?");
			prepGeefArtiestCodes = Connectiebeheer.getCon().prepareStatement("SELECT ArtiestCode FROM artiesten");
		} catch (SQLException e) {
			throw new DatabaseException(foutmeldingOphalen);
		}
	}

	/**
	 * 
	 * @param key key van de artiest
	 * @return artiest de artiest met de ingevoerde artiestCode
	 * @throws DatabaseException
	 */

	public static Artiest getArtiest(String artiestCode) throws DatabaseException {
		Artiest artiest = null;
		try {
			prepGeefArtiestData.setString(1, artiestCode);
			ResultSet res = prepGeefArtiestData.executeQuery();
			while (res.next()) {
				artiest = new Artiest();
				artiest.setArtiestCode(artiestCode);
				artiest.setKey(res.getInt("Key"));
				artiest.setNaam(res.getString("Naam"));
			}
		} catch (SQLException e) {
			throw new DatabaseException(foutmeldingOphalen);
		}
		return artiest;
	}

	/**
	 * Geeft een array met alle artiestCodes
	 * 
	 * @throws DatabaseException
	 */
	public static ArrayList<String> getArtiestCodes() throws DatabaseException {
		ArrayList<String> codes = new ArrayList<String>();
		try {
			ResultSet res = prepGeefArtiestCodes.executeQuery();
			while (res.next()) {
				String string = res.getString("ArtiestCode");
				if (!string.equals("0")) {
					codes.add(res.getString("ArtiestCode"));
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException(foutmeldingOphalen);
		}
		return codes;
	}

}