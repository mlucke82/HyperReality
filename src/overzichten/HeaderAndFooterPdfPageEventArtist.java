package overzichten;

import java.util.Date;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import domein.VerkoopPerArtiest;

/**
 *
 * How To Set Header and Footer in pdf Using Itext Example - Using iText library
 * - core java tutorial
 *
 */

class HeaderAndFooterPdfPageEventArtist extends PdfPageEventHelper {

	private String IMGHRR = "resource/Hyper-Reality-Records-logo.png";
	private String IMGARR = "resource/Altered-Reality-Records-logo.png";
	private Image imageHRR;
	private Image imageARR;
	private VerkoopPerArtiest verkoop;

	private Font smallBold = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private Font superSmall = new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

	public HeaderAndFooterPdfPageEventArtist(VerkoopPerArtiest verkoop) {
		this.verkoop = verkoop;
	}

	public void onStartPage(PdfWriter pdfWriter, Document document) {
	}

	public void onEndPage(PdfWriter pdfWriter, Document document) {
		createHeader(pdfWriter, document);
		createFooter(pdfWriter, document);
	}

	public void createHeader(PdfWriter pdfWriter, Document document) {

		if (verkoop.getContainsHRR() && verkoop.getContainsARR()) {

			try {
				imageHRR = Image.getInstance(IMGHRR);
				imageARR = Image.getInstance(IMGARR);
				imageHRR.setAbsolutePosition(40, 685);
				imageHRR.scaleAbsolute(240, 89);
				imageARR.setAbsolutePosition(310, 685);
				imageARR.scaleAbsolute(240, 89);
				pdfWriter.getDirectContent().addImage(imageHRR);
				pdfWriter.getDirectContent().addImage(imageARR);
			}

			catch (Exception e) {
			}
		}

		if (verkoop.getContainsHRR() && !verkoop.getContainsARR()) {

			try {
				imageHRR = Image.getInstance(IMGHRR);
				imageHRR.setAbsolutePosition(56, 675);
				imageHRR.scaleAbsolute(480, 178);
				pdfWriter.getDirectContent().addImage(imageHRR);
			}

			catch (Exception e) {
			}
		}

		if (!verkoop.getContainsHRR() && verkoop.getContainsARR()) {

			try {
				imageARR = Image.getInstance(IMGARR);
				imageARR.setAbsolutePosition(56, 675);
				imageARR.scaleAbsolute(480, 178);
				pdfWriter.getDirectContent().addImage(imageARR);
			}

			catch (Exception e) {
			}
		}
	}

	public void createFooter(PdfWriter pdfWriter, Document document) {

		// Paragraph pageNumber = new Paragraph(String.format("Page %d",
		// pdfWriter.getPageNumber()), smallBold);
		Paragraph subScript = new Paragraph(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n"
						+ "                                         To request payment, please send an invoice to info@hyperrealityrecords.nl for payment to your Bank Account or PayPal. \n"
						+ "                     All amounts referred to in this Invoice are in Pounds Sterling (GBP), and all amounts owing under this Invoice shall be paid in Pounds Sterling (GBP). \n"
						+ "                                        All amounts denominated in other currencies (if any) shall be converted with the Exchange Rate on the date of calculation. \n\n"
						+ "                         Please keep in mind that a small PayPal or Bank fee can be applied to cross-border payments or when a payment is done in a foreign currency. \n\n"
						+ "                                                 If you have any queries or concerns please direct these to us and we will get back to you as soon as possible. \n"
						+ "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------",
				superSmall);
		
		// Paragraph datum = new Paragraph("" + new Date(), smallBold);

		float[] columnWidths = { 1 };
		PdfPTable tableLinks = new PdfPTable(columnWidths);
		tableLinks.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		tableLinks.setTotalWidth(600);

		tableLinks.addCell(subScript);
		tableLinks.writeSelectedRows(0, -1, 10, 100, pdfWriter.getDirectContent());
		

		// PdfPTable tableRechts = new PdfPTable(columnWidths);
		// tableRechts.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		// tableRechts.setTotalWidth(200);

		// tableRechts.addCell(datum);
		// tableRechts.writeSelectedRows(0, -1, 420, 30, pdfWriter.getDirectContent());

	}

}
