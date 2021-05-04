package overzichten;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
//import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import domein.VerkoopPerArtiest;
import domein.VerkoopPerArtiestPerRelease;

public class OverzichtArtiest {
	private String FILE = "H:/HyperReality/PDFfromJava/Artists/";
	private Document document;
	private VerkoopPerArtiest verkoop;
	private ArrayList<VerkoopPerArtiestPerRelease> verkopenPerArtiestPerRelease;
	private String pound = "\u00a3";
	private Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
	private Font catFont = new Font(FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private Font smallBold = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	// private Font textFont;

	public OverzichtArtiest(VerkoopPerArtiest verkoop) {

		// this code should run once at initialization/application startup
		// FontFactory.register("resource/Elements.ttf");
		// textFont = FontFactory.getFont("Elements", BaseFont.IDENTITY_H,
		// BaseFont.EMBEDDED, 12); //10 is the size

		document = new Document();
		document.setMargins(20, 20, 160, 20);

		try {
			FileOutputStream fos = new FileOutputStream(FILE + verkoop.getArtiestNaam() + ".pdf");
			PdfWriter pdfWriter = PdfWriter.getInstance(document, fos);

			HeaderAndFooterPdfPageEventArtist headerAndFooter = new HeaderAndFooterPdfPageEventArtist();
			pdfWriter.setPageEvent(headerAndFooter);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.verkoop = verkoop;
		this.verkopenPerArtiestPerRelease = verkoop.getAlleReleasesPerArtiest();
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

		float[] columnWidths = { 2, 2, 1 };
		PdfPTable table = new PdfPTable(columnWidths);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.setTableEvent(new BorderEvent());

		table.addCell("Catalog:");
		table.addCell("EAN:");
		table.addCell("Profit:");

		for (int i = 0; i < verkopenPerArtiestPerRelease.size(); i++) {
			Paragraph catalog = new Paragraph(verkopenPerArtiestPerRelease.get(i).getReleaseName(), font);
			Paragraph ean = new Paragraph(verkopenPerArtiestPerRelease.get(i).getEan(), font);
			Paragraph profit = new Paragraph(
					pound + " " + String.format("%.2f", verkopenPerArtiestPerRelease.get(i).getOpbrengst()), font);
			table.addCell(catalog);
			table.addCell(ean);
			table.addCell(profit);
		}

		document.add(table);
		document.add(new Paragraph(" "));
	}

	private void createTableTotaal() throws DocumentException {

		float[] columnWidths = { 2, 2, 1 };
		PdfPTable table = new PdfPTable(columnWidths);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.setTableEvent(new BorderEvent());

		table.addCell("Total Profit:");
		table.addCell(" ");
		Paragraph opbrengst = new Paragraph(pound + " " + String.format("%.2f", verkoop.getOpbrengst()), font);
		table.addCell(opbrengst);
		table.addCell("Already Paid:");
		table.addCell(" ");
		Paragraph betaald = new Paragraph(pound + " " + String.format("%.2f", verkoop.getReedsBetaald()), font);
		table.addCell(betaald);
		table.addCell("Remaining:");
		table.addCell(" ");
		Paragraph nogTeBetalen = new Paragraph(
				pound + " " + String.format("%.2f", verkoop.getOpbrengst() - verkoop.getReedsBetaald()), font);
		table.addCell(nogTeBetalen);

		document.add(table);
	}

}
