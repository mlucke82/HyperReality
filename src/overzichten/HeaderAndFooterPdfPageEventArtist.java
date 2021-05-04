package overzichten;

import java.io.IOException;
import java.net.MalformedURLException;
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

	private Font smallBold = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	public HeaderAndFooterPdfPageEventArtist() {
	}

	public void onStartPage(PdfWriter pdfWriter, Document document) {
	}

	public void onEndPage(PdfWriter pdfWriter, Document document) {
		createHeader(pdfWriter, document);
		createFooter(pdfWriter, document);
	}

	public void createHeader(PdfWriter pdfWriter, Document document) {

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
		 catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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
