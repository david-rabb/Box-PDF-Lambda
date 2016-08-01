package drx.box;

import org.apache.http.client.fluent.Request;

/**
 * Use Box API to download a file. Pass auth token and fileId then follow redirects to download
 * file into byte array.
 * @author Dave
 */
public class BoxDownload {
  public static final String BOX_API = "https://api.box.com/2.0/files/%s/content";

  public static byte[] getFile(String authBearer, String fileId) {
    try {
      String url = String.format(BOX_API, fileId);
      //System.out.println(url);
      byte[] b = Request.Get(url)
              .addHeader("Authorization", "Bearer "+authBearer)
              .execute().returnContent().asBytes();
      return b;
      //System.out.println(response);
    } catch(Exception e) {
      e.printStackTrace();
      throw new RuntimeException("BoxDownload: "+e.getMessage());
    }
  }
}
