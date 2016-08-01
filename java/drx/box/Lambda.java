package drx.box;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.Context;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * AWS Lambda Handler to process a PDF on Box and return data to callback url.
 * @author Dave
 */
public class Lambda implements RequestHandler<Lambda.Request, Lambda.Response> {

  @Override
  public Lambda.Response handleRequest(Request request, Context context) {
    Response response = new Response();
    PDFProcessor pdf = null;
    try {
      // validate inputs
      if (request.boxAuthToken==null) {
        throw new RuntimeException("missing boxAuthToken");
      }
      if (request.boxFileId==null) {
        throw new RuntimeException("missing boxFileId");
      }

      // connect to Box and get PDF
      long t0 = System.currentTimeMillis();
      byte[] fileBytes = BoxDownload.getFile(request.boxAuthToken, request.boxFileId);
      System.out.println("File size: "+(fileBytes.length/1000)+" KB");

      // extract metadata, text & image
      pdf = new PDFProcessor(fileBytes);
      long t1 = System.currentTimeMillis();
      System.out.println("Download time: "+(t1-t0)+" ms");
      response.sha1 = pdf.getSha1();
      response.metadata = pdf.getMetadata();
      response.frontText = pdf.getText(1);
      response.png = java.util.Base64.getEncoder().encodeToString(pdf.getImage(1));

      // return document
      response.status = "OK";
      long t2 = System.currentTimeMillis();
      System.out.println("Processing time: "+(t2-t1)+" ms");

    } catch(Exception e) {
      response.status = "ERROR";
      response.message = e.getMessage();
    } finally {
      try { if (pdf!=null) pdf.close();} catch(Exception e) {}
    }
    return response;
  }

  static class Request {
    String boxFileId;
    private String boxAuthToken;

    public String getBoxFileId() {
      return boxFileId;
    }
    public void setBoxFileId(String boxFileId) {
      this.boxFileId = boxFileId;
    }

    public String getBoxAuthToken() {
      return boxAuthToken;
    }
    public void setBoxAuthToken(String boxAuthToken) {
      this.boxAuthToken = boxAuthToken;
    }
  }

  static class Response {
    String status;
    String message;
    Map metadata;
    String png;
    String sha1;
    String frontText;

    public String getStatus() {
      return status;
    }
    public void setStatus(String status) {
      this.status = status;
    }

    public String getMessage() {
      return message;
    }
    public void setMessage(String message) {
      this.message = message;
    }

    public Map getMetadata() {
      return metadata;
    }
    public void setMetadata(Map metadata) {
      this.metadata = metadata;
    }

    public String getPng() {
      return png;
    }
    public void setPng(String png) {
      this.png = png;
    }

    public String getSha1() {
      return sha1;
    }
    public void setSha1(String sha1) {
      this.sha1 = sha1;
    }

    public String getFrontText() {
      return frontText;
    }
    public void setFrontText(String frontText) {
      this.frontText = frontText;
    }
  }
}
