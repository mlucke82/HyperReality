package domein;

/**
 * Representeert artiest.
 * 
 * @author Merijn Lucke
 */
public class Artiest {
	private int key;
	private String artiestCode;
	private String naam;
	private String adres;
	private String rekeningnummer;
	private String telefoonnummer;

	/**
	 * Geeft klantnummer.
	 * 
	 * @return klantnummer
	 */
	public int getKey() {
		return key;
	}

	public String getArtiestCode() {
		return artiestCode;
	}

	public void setArtiestCode(String artiestCode) {
		this.artiestCode = artiestCode;
	}

	/**
	 * Geeft klantnaam.
	 * 
	 * @return naam
	 */
	public String getNaam() {
		return naam;
	}

	/**
	 * Geeft stringrepresentatie van klant.
	 * 
	 * @return stringrepresentatie.
	 */
	public String toString() {
		return key + ", " + naam;
	}

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public String getRekeningnummer() {
		return rekeningnummer;
	}

	public void setRekeningnummer(String rekeningnummer) {
		this.rekeningnummer = rekeningnummer;
	}

	public String getTelefoonnummer() {
		return telefoonnummer;
	}

	public void setTelefoonnummer(String telefoonnummer) {
		this.telefoonnummer = telefoonnummer;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

}
