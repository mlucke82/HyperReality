package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Beheert de connectie met de database. Bevat methoden voor openen en sluiten
 * van connectie met database, en voor opvragen van de connectie.
 * 
 * @author Merijn
 */
public class Connectiebeheer {

	private static Connection con = null;
	private static String foutmeldingConnectie = "er is iets mis met de connectie";

	/**
	 * Opent de database connectie en initialiseerd klantbeheer en
	 * voorstellingsbeheer
	 * 
	 * @throws DatabaseException
	 */
	public static void init() throws DatabaseException {
		openDB();
		Verkoopbeheer.init();
		Artiestbeheer.init();
		Trackbeheer.init();
		Betalingbeheer.init();
	}

	/**
	 * Sluit de connectie met de database
	 * 
	 * @throws TheaterException
	 */
	public static void closeDB() throws DatabaseException {
		try {
			con.close();
		} catch (SQLException e) {
			throw new DatabaseException(foutmeldingConnectie);
		} catch (NullPointerException e1) {
		}
	}

	/**
	 * Geeft de connectie
	 * 
	 * @return con de connectie
	 */
	static Connection getCon() {
		return con;
	}

	/**
	 * Maakt een connectie met de database
	 * 
	 * @throws DatabaseException
	 */
	private static void openDB() throws DatabaseException {

		try {
			Class.forName(DBConst.DRIVERNAAM);
			con = DriverManager.getConnection(DBConst.URL, DBConst.GEBRUIKERSNAAM, DBConst.WACHTWOORD);
		} catch (SQLException | ClassNotFoundException e) {
			throw new DatabaseException(foutmeldingConnectie);
		}
	}

}
