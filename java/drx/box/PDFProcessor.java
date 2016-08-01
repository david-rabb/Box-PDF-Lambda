package drx.box;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.commons.codec.digest.DigestUtils;
/**
 *
 * @author Dave
 */
public class PDFProcessor {
  private final PDDocument pdf;
  private String sha1;

  public PDFProcessor(byte[] fileBytes) {
    try {
      sha1 = DigestUtils.sha1Hex(fileBytes);
      pdf = PDDocument.load(fileBytes);

    } catch (Exception e) {
      throw new RuntimeException(e.getClass().getName()+": "+e.getMessage());
    }
  }

  public String getSha1() {
    return sha1;
  }

  public void close() throws IOException {
    pdf.close();
  }

  /* get all file metadata properties as Map */
  public Map getMetadata() {
    Map metadata = new HashMap();
    PDDocumentInformation info = pdf.getDocumentInformation();
    Set<String> keys = info.getMetadataKeys();
    for (String key : keys) {
      metadata.put(key, info.getCustomMetadataValue(key));
    }
    return metadata;
  }

  /* extract all text from page of PDF */
  public String getText(int page) {
    try {
      PDFTextStripper stripper = new PDFTextStripper();
      stripper.setStartPage(page);
      stripper.setEndPage(page);
      return stripper.getText(pdf);

    } catch(Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
  }

  /* export page as image */
  public byte[] getImage(int page) {
    try {
      PDFRenderer renderer = new PDFRenderer(pdf);
      BufferedImage image = renderer.renderImage(page-1);
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      ImageIO.write(image, "PNG", out);
      return out.toByteArray();

    } catch(Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
  }

}
