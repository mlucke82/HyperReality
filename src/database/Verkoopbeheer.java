package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domein.Verkoop;

/**
 * Klasse die voorstelling- en bijbehorende bezettingsgegevens ophaald uit de
 * database, en wegschrijft naar de database
 * 
 * @author Merijn
 */
public class Verkoopbeheer {

	// de verschillende prep statements worden klaargezet
	private static PreparedStatement prepGeefVerkoopData = null;

	// een aantal foutmeldingen wordt als string opgeslagen
	private static String foutmeldingOphalen = "er is iets mis gegaan bij het ophalen van de gegevens";

	/**
	 * Initieert de vulling van de prep statements. De insert wordt apart behandeld
	 * om een aparte foutmelding te kunnen geven
	 * 
	 * @throws DatabaseException
	 */
	public static void init() throws DatabaseException {
		try {
			prepGeefVerkoopData = Connectiebeheer.getCon().prepareStatement("SELECT * FROM verkoopdata WHERE EAN = ?");
		} catch (SQLException e) {
			throw new DatabaseException(foutmeldingOphalen);
		}
	}

	/**
	 * Geeft een array met alle verkopen van een release
	 * 
	 * @throws DatabaseException
	 */
	public static ArrayList<Verkoop> getVerkopen(String EAN, int year, int quarter) throws DatabaseException {
		//System.out.println(year+" check");
		//System.out.println(quarter+" check");
		ArrayList<Verkoop> verkopen = new ArrayList<Verkoop>();
		Verkoop verkoop = null;
		//int check = year * 4 + quarter;
		try {
			prepGeefVerkoopData.setString(1, EAN);
		//	prepGeefVerkoopData.setInt(2, check);
		//	prepGeefVerkoopData.setInt(3, year);
		//	prepGeefVerkoopData.setInt(4, quarter);
			ResultSet res = prepGeefVerkoopData.executeQuery();
			while (res.next()) {
				verkoop = new Verkoop();
				verkoop.setEAN(EAN);
				verkoop.setISRC(res.getString("ISRC"));
				verkoop.setKey(res.getInt("Key"));
				verkoop.setLabelName(res.getString("LabelName"));
				verkoop.setCatalog(res.getString("Catalog"));
				verkoop.setReleaseArtist(res.getString("ReleaseArtist"));
				verkoop.setReleaseName(res.getString("ReleaseName"));
				verkoop.setTrackArtist(res.getString("TrackArtist"));
				verkoop.setTrackTitle(res.getString("TrackTitle"));
				verkoop.setMixName(res.getString("MixName"));
				verkoop.setFormat(res.getString("Format"));
				verkoop.setSaleType(res.getString("SaleType"));
				verkoop.setQty(res.getInt("Qty"));
				verkoop.setValue(res.getDouble("Value"));
				verkoop.setDeal(res.getDouble("Deal"));
				verkoop.setRoyalty(res.getDouble("Royalty"));
				verkoop.setStoreName(res.getString("StoreName"));
				verkoop.setYourTrackRef(res.getString("YourTrackRef"));
				verkoop.setYear(res.getInt("Year"));
				verkoop.setQuarter(res.getInt("Quarter"));
				verkopen.add(verkoop);
			}
		} catch (SQLException e) {
			throw new DatabaseException(foutmeldingOphalen);
		}
		return verkopen;
	}
}
