package overzichten;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import domein.VerkoopPerArtiest;
import domein.VerkoopPerArtiestPerRelease;
import domein.VerkoopPerArtiestPerTrack;

public class OverzichtArtiest {
	private String FILE = "H:/HyperReality/PDFfromJava/Artists/";
	private Document document;
	private VerkoopPerArtiest verkoop;
	private ArrayList<VerkoopPerArtiestPerRelease> verkopenPerArtiestPerRelease;
	private ArrayList<VerkoopPerArtiestPerTrack> verkopenPerArtiestPerTrack;
	private String pound = "\u00a3";
	//private Font font = new Font(FontFamily.HELVETICA, 10, Font.NORMAL);
	private Font catFont = new Font(FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private Font smallBold = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	//werkt nog niet echt
	private Font customFont = FontFactory.getFont("resource/Elements.ttf",
		    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10, Font.NORMAL, BaseColor.BLACK);
		

	// private Font textFont;

	public OverzichtArtiest(VerkoopPerArtiest verkoop) {
		
		//BaseFont baseFont = font.getBaseFont();

		this.verkoop = verkoop;
		this.verkopenPerArtiestPerRelease = verkoop.getAlleReleasesPerArtiest();

		document = new Document();
		document.setMargins(0, 0, 160, 100);

		try {
			FileOutputStream fos = new FileOutputStream(FILE + verkoop.getArtiestNaam() + ".pdf");
			PdfWriter pdfWriter = PdfWriter.getInstance(document, fos);

			HeaderAndFooterPdfPageEventArtist headerAndFooter = new HeaderAndFooterPdfPageEventArtist(verkoop);
			pdfWriter.setPageEvent(headerAndFooter);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		document.open();
		addMetaData(document);
		try {
			createTableName();
			createTableReleases();
			createTableTotaal();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.close();
	}

	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private void addMetaData(Document document) {
		document.addTitle(verkoop.getArtiestNaam() + "");
		document.addSubject("Using iText");
		document.addKeywords("Java, PDF, iText");
		document.addAuthor("HyperRealitySoft");
		document.addCreator("HyperRealitySoft");
	}

	private void createTableName() throws DocumentException {

		Paragraph artistName = new Paragraph("Artist: " + verkoop.getArtiestNaam(), catFont);
		Paragraph vanaf = new Paragraph("First release: " + verkoop.getJaarVanaf() + ", Q" + verkoop.getKwartaalVanaf(),
				smallBold);
		Paragraph tot = new Paragraph("Sales up to: " + verkoop.getJaarTot() + ", Q" + verkoop.getKwartaalTot(),
				smallBold);

		float[] columnWidths = { 1 };
		PdfPTable table = new PdfPTable(columnWidths);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		table.addCell(artistName);
		table.addCell(vanaf);
		table.addCell(tot);

		document.add(table);
		document.add(new Paragraph(" "));
	}

	private void createTableReleases() throws DocumentException {

		float[] columnWidths = { 1, 4, 1, 1 };
		PdfPTable table = new PdfPTable(columnWidths);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.setTableEvent(new BorderEvent());

		table.addCell(new Paragraph("Catalog:", smallBold));
		table.addCell(new Paragraph("Trackname:", smallBold));
		table.addCell(new Paragraph("Your Pct.:", smallBold));
		table.addCell(new Paragraph("Your Profit:", smallBold));

		for (int i = 0; i < verkopenPerArtiestPerRelease.size(); i++) {
			verkopenPerArtiestPerTrack = verkopenPerArtiestPerRelease.get(i).getVerkopenPerArtiestPerTrack();
			for (int j = 0; j < verkopenPerArtiestPerTrack.size(); j++) {

				if (verkopenPerArtiestPerTrack.get(j).getPercentage() > 0) {

					table.addCell(new Paragraph(verkopenPerArtiestPerRelease.get(i).getReleaseName(), customFont));
					table.addCell(new Paragraph(verkopenPerArtiestPerTrack.get(j).getTrackTitle(), customFont));
					table.addCell(new Paragraph(verkopenPerArtiestPerTrack.get(j).getPercentage() + " %", customFont));
					table.addCell(new Paragraph(
							pound + " " + String.format("%.2f", verkopenPerArtiestPerTrack.get(j).getOpbrengst()),
							customFont));
				}
			}
		}

		document.add(table);
		document.add(new Paragraph(" "));
	}

	private void createTableTotaal() throws DocumentException {

		float[] columnWidths = { 2, 2, 1 };
		PdfPTable table = new PdfPTable(columnWidths);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.setTableEvent(new BorderEvent());

		table.addCell(new Paragraph("Total Profit:", smallBold));
		table.addCell(" ");
		Paragraph opbrengst = new Paragraph(pound + " " + String.format("%.2f", verkoop.getOpbrengst()), smallBold);
		table.addCell(opbrengst);
		table.addCell(new Paragraph("Already Paid:", smallBold));
		table.addCell(" ");
		Paragraph betaald = new Paragraph(pound + " " + String.format("%.2f", verkoop.getReedsBetaald()), smallBold);
		table.addCell(betaald);
		table.addCell(new Paragraph("Remaining:", smallBold));
		table.addCell(" ");
		Paragraph nogTeBetalen = new Paragraph(
				pound + " " + String.format("%.2f", verkoop.getOpbrengst() - verkoop.getReedsBetaald()), smallBold);
		table.addCell(nogTeBetalen);

		document.add(table);
	}

}
