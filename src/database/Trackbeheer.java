package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domein.Track;

public class Trackbeheer {

// de verschillende prep statements worden klaargezet
	private static PreparedStatement prepGeefTrackData = null;
	private static PreparedStatement prepGeefAlleEan = null;

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
			prepGeefTrackData = Connectiebeheer.getCon().prepareStatement("SELECT * FROM tracks WHERE EAN = ?");
			prepGeefAlleEan = Connectiebeheer.getCon().prepareStatement("SELECT EAN FROM tracks");
		} catch (SQLException e) {
			throw new DatabaseException(foutmeldingOphalen);
		}
	}

	/**
	 * Geeft een array met alle tracks behorende bij een release
	 * 
	 * @throws DatabaseException
	 */
	public static ArrayList<Track> getTracks(String EAN) throws DatabaseException {
		ArrayList<Track> tracks = new ArrayList<Track>();
		Track track = null;
		try {
			prepGeefTrackData.setString(1, EAN);
			ResultSet res = prepGeefTrackData.executeQuery();
			while (res.next()) {
				track = new Track();
				track.setKey(res.getInt("Key"));
				track.setISRC(res.getString("ISRC"));
				track.setEAN(res.getString("EAN"));
				track.setTrackname(res.getString("Trackname"));
				track.setArtiest1Key(res.getString("Artiest1Key"));
				track.setArtiest2Key(res.getString("Artiest2Key"));
				track.setRemixer1Key(res.getString("Remixer1Key"));
				track.setRemixer2Key(res.getString("Remixer2Key"));
				track.setArtiest1Perc(res.getInt("Artiest1Perc"));
				track.setArtiest2Perc(res.getInt("Artiest2Perc"));
				track.setRemixer1Perc(res.getInt("Remixer1Perc"));
				track.setRemixer2Perc(res.getInt("Remixer2Perc"));
				track.setMastering(res.getDouble("Mastering"));
				track.setPromo(res.getDouble("Promo"));
				track.setReleasename(res.getString("ReleaseName"));
				tracks.add(track);
			}
		} catch (SQLException e) {
			throw new DatabaseException(foutmeldingOphalen);
		}
		return tracks;
	}

	/**
	 * Geeft een array met alle tracks
	 * 
	 * @throws DatabaseException
	 */
	public static ArrayList<String> getEan() throws DatabaseException {
		ArrayList<String> eans = new ArrayList<String>();
		try {
			ResultSet res = prepGeefAlleEan.executeQuery();
			while (res.next()) {
				String string = res.getString("EAN");
				if(!eans.contains(string))
				eans.add(string);
			}
		} catch (SQLException e) {
			throw new DatabaseException(foutmeldingOphalen);
		}
		return eans;
	}
}
