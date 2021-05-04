package overzichten;

import java.io.IOException;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import domein.VerkoopPerRelease;

/**
 *
 * How To Set Header and Footer in pdf Using Itext Example - Using iText library
 * - core java tutorial
 *
 */

class HeaderAndFooterPdfPageEventRelease extends PdfPageEventHelper {

	private VerkoopPerRelease verkoop;
	private String IMGHRR = "resource/Hyper-Reality-Records-logo.png";
	private String IMGARR = "resource/Altered-Reality-Records-logo.png";
	private Boolean isARR = false;
	private Font catFont = new Font(FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private Font smallBold = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private Image image;

	public HeaderAndFooterPdfPageEventRelease(VerkoopPerRelease verkoop) {
		this.verkoop = verkoop;

	}

	public void onStartPage(PdfWriter pdfWriter, Document document) {

		setImage(document);

	}

	public void onEndPage(PdfWriter pdfWriter, Document document) {

		createHeader(pdfWriter, document);
		createFooter(pdfWriter, document);
	}

	public void setImage(Document document) {

		String string = verkoop.getReleaseName().charAt(0) + "";

		if (string.equals("A")) {
			this.isARR = true;
		}

		try {
			Image imageHRR = Image.getInstance(IMGHRR);
			Image imageARR = Image.getInstance(IMGARR);
			imageHRR.setAbsolutePosition(240, 685);
			imageHRR.scaleAbsolute(300, 111);
			imageARR.setAbsolutePosition(240, 685);
			imageARR.scaleAbsolute(300, 111);
			if (isARR) {
				image = imageARR;
			} else {
				image = imageHRR;
			}
		} catch (IOException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createHeader(PdfWriter pdfWriter, Document document) {

		Paragraph releaseName = new Paragraph(verkoop.getReleaseName(), catFont);
		Paragraph ean = new Paragraph("EAN: " + verkoop.getEan(), smallBold);
		Paragraph vanaf = new Paragraph("Released: " + verkoop.getJaarVanaf() + ", Q" + verkoop.getKwartaalVanaf(),
				smallBold);
		Paragraph tot = new Paragraph("Sales up to: " + verkoop.getJaarTot() + ", Q" + verkoop.getKwartaalTot(),
				smallBold);

		float[] columnWidths = { 1 };
		PdfPTable table = new PdfPTable(columnWidths);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.setTotalWidth(200);

		table.addCell(releaseName);
		table.addCell(ean);
		table.addCell(vanaf);
		table.addCell(tot);

		table.writeSelectedRows(0, -1, 60, 780, pdfWriter.getDirectContent());

		try {
			pdfWriter.getDirectContent().addImage(image);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createFooter(PdfWriter pdfWriter, Document document) {

		Paragraph pageNumber = new Paragraph(String.format("Page %d", pdfWriter.getPageNumber()), smallBold);
		Paragraph datum = new Paragraph("" + new Date(), smallBold);
		
		float[] columnWidths = { 1 };
		PdfPTable tableLinks = new PdfPTable(columnWidths);
		tableLinks.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		tableLinks.setTotalWidth(200);

		tableLinks.addCell(pageNumber);
		tableLinks.writeSelectedRows(0, -1, 10, 30, pdfWriter.getDirectContent());
			
		PdfPTable tableRechts = new PdfPTable(columnWidths);
		tableRechts.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		tableRechts.setTotalWidth(200);

		tableRechts.addCell(datum);
		tableRechts.writeSelectedRows(0, -1, 420, 30, pdfWriter.getDirectContent());

	}
}
