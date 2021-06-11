package overzichten;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
//import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import domein.VerkoopPerRelease;
import domein.VerkoopPerTrack;

public class OverzichtRelease {
	private String FILE = "H:/HyperReality/PDFfromJava/Releases/";
	private Document document;
	private VerkoopPerRelease verkoop;
	private ArrayList<VerkoopPerTrack> verkopenPerTrack;
	private String pound = "\u00a3";
	private Font font = new Font(FontFamily.HELVETICA, 12, Font.NORMAL);
	private Font fontBold = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
	private Font fontHalf = new Font(FontFamily.HELVETICA, 2, Font.NORMAL);
	private String label = "";
	// private Font textFont;

	public OverzichtRelease(VerkoopPerRelease verkoop, int year, int quarter) {

		this.verkoop = verkoop;
		this.verkopenPerTrack = verkoop.verkopenPerTrack;
		document = new Document();
		document.setMargins(20, 20, 160, 100);
		isArr();
		String datum = year + "_Q" + quarter;

		try {
			FileOutputStream fos = new FileOutputStream(
					FILE + label + "/" + verkoop.getReleaseName() + "_" + datum + ".pdf");
			PdfWriter pdfWriter = PdfWriter.getInstance(document, fos);

			HeaderAndFooterPdfPageEventRelease headerAndFooter = new HeaderAndFooterPdfPageEventRelease(verkoop);
			pdfWriter.setPageEvent(headerAndFooter);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		document.open();
		addMetaData(document);
		try {
			createTablePerTrack();
			createTableTotaal();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		document.close();

	}

	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private void addMetaData(Document document) {
		document.addTitle(verkoop.getReleaseName() + "");
		document.addSubject("Using iText");
		document.addKeywords("Java, PDF, iText");
		document.addAuthor("HyperRealitySoft");
		document.addCreator("HyperRealitySoft");
	}

	private void createTableTotaal() throws DocumentException {

		float[] columnWidthsTitel = { 1, 1, 1 };
		PdfPTable tableTitel = new PdfPTable(columnWidthsTitel);
		tableTitel.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		Paragraph titel = new Paragraph("  Release totals:", font);
		tableTitel.addCell(" ");
		tableTitel.addCell(titel);
		tableTitel.addCell(" ");
		tableTitel.setTableEvent(new BorderEvent());

		float[] columnWidths = { 1, 1, 1, 1 };
		PdfPTable table = new PdfPTable(columnWidths);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		table.addCell(new Paragraph("Total Gross:", fontBold));
		table.addCell(new Paragraph("Total Mastering:", fontBold));
		table.addCell(new Paragraph("Total Promo:", fontBold));
		table.addCell(new Paragraph("Total Net Profit:", fontBold));

		table.addCell(new Paragraph(pound + " " + String.format("%.2f", verkoop.getBrutoOpbrengst()), font));
		table.addCell(new Paragraph(pound + " " + String.format("%.2f", verkoop.getMastering()), font));
		table.addCell(new Paragraph(pound + " " + String.format("%.2f", verkoop.getPromo()), font));
		table.addCell(new Paragraph(pound + " " + String.format("%.2f", verkoop.getOpbrengst()), font));
		table.setTableEvent(new BorderEvent());

		document.add(tableTitel);
		document.add(table);
	}

	private void createTablePerTrack() throws DocumentException {

		for (int i = 0; i < verkopenPerTrack.size(); i++) {

			PdfPTable table = new PdfPTable(1);

			// table name and isrc

			float[] columnWidths = { 1, 5 };
			PdfPTable tableName = new PdfPTable(columnWidths);
			tableName.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			tableName.addCell(new Paragraph("Trackname:", fontBold));
			tableName.addCell(new Paragraph(verkopenPerTrack.get(i).getTrackname(), font));
			tableName.addCell(new Paragraph("ISRC:", fontBold));
			tableName.addCell(new Paragraph(verkopenPerTrack.get(i).getISRC(), font));

			// table sales numbers

			float[] columnWidthsSales = { 1, 1, 1 };
			PdfPTable tableSales = new PdfPTable(columnWidthsSales);
			tableSales.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			tableSales.addCell(new Paragraph("AlbumDownloads:", fontBold));
			tableSales.addCell(new Paragraph("Downloads:", fontBold));
			tableSales.addCell(new Paragraph("Streams:", fontBold));

			tableSales.addCell(new Paragraph(verkopenPerTrack.get(i).getAlbumDownloads() + "", font));
			tableSales.addCell(new Paragraph(verkopenPerTrack.get(i).getDownloads() + "", font));
			tableSales.addCell(new Paragraph(verkopenPerTrack.get(i).getStreams() + "", font));

			// table bedragen

			float[] columnWidthsBedragen = { 1, 1, 1, 1 };
			PdfPTable tableBedragen = new PdfPTable(columnWidthsBedragen);
			tableBedragen.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			tableBedragen.addCell(new Paragraph("Gross Profit:", fontBold));
			tableBedragen.addCell(new Paragraph("Mastering Cost:", fontBold));
			tableBedragen.addCell(new Paragraph("Promo Cost:", fontBold));
			//PdfPCell cell = new PdfPCell(new Paragraph("Net Profit:", fontBold));
			//cell.setBackgroundColor(BaseColor.YELLOW);
			tableBedragen.addCell(new Paragraph("Net Profit:", fontBold));

			tableBedragen.addCell(new Paragraph(
					pound + " " + String.format("%.2f", verkopenPerTrack.get(i).getBrutoOpbrengst()), font));
			tableBedragen.addCell(
					new Paragraph(pound + " " + String.format("%.2f", verkopenPerTrack.get(i).getMastering()), font));
			tableBedragen.addCell(
					new Paragraph(pound + " " + String.format("%.2f", verkopenPerTrack.get(i).getPromo()), font));
			
			//PdfPCell cell = new PdfPCell(new Paragraph(pound + " " + String.format("%.2f", verkopenPerTrack.get(i).getOpbrengst()), font));
			//cell.setBackgroundColor(BaseColor.YELLOW);
			
			tableBedragen.addCell(
					new Paragraph(pound + " " + String.format("%.2f", verkopenPerTrack.get(i).getOpbrengst()), font));
			
			//tableBedragen.addCell(cell);

			// stop alles in de tabel
			Paragraph emptyHalf = new Paragraph(" ", fontHalf);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.addCell(tableName);
			table.addCell(emptyHalf);
			table.addCell(tableSales);
			table.addCell(emptyHalf);
			table.addCell(tableBedragen);
			table.setTableEvent(new BorderEvent());
			table.setKeepTogether(true);

			// stop de tabel in het document
			document.add(table);
			document.add(new Paragraph(" "));
		}

	}

	private void isArr() {
		String string = verkoop.getReleaseName().charAt(0) + "";
		if (string.equals("A")) {
			this.label = "ARR";
		} else {
			this.label = "HRR";
		}
	}

}
