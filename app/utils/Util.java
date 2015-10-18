package utils;

import play.Play;
import play.cache.Cache;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.nio.charset.StandardCharsets;
import com.google.common.io.Files;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import models.ClientInfo;


public class Util {

  public static String readFileAsString(String path) throws IOException {
    File file = Play.application().getFile(path);
    return Files.toString(file, StandardCharsets.UTF_8);
  }

  public static Map guavaMap(Object... args){
    Map result = new HashMap();
    try{
      for ( int i = 0 ; i < args.length ; i = i+2 ) {
        result.put(args[i], args[i+1]);
      }
    } catch (Exception e){}
    return result;
  }

  public static String formatQueryString(Object... args){
    String result = "";
    try{
      List<NameValuePair> nvps = new ArrayList<NameValuePair>();
      for ( int i = 0 ; i < args.length ; i = i+2 ) {
        nvps.add(
          new BasicNameValuePair(
            args[i].toString(),
            args[i+1].toString()
          )
        );
      }
      result = URLEncodedUtils.format(nvps, "UTF-8");
    } catch (Exception e){}
    return result;
  }

  public static String formatQueryString(Map<String, String[]> params){
    String result = "";
    try{
      List<NameValuePair> nvps = new ArrayList<NameValuePair>();
      for (String k : params.keySet() ) {
        String[] values = params.get(k);
        for (String v : values ) {
          nvps.add(
            new BasicNameValuePair(
              k,
              v
            )
          );
        }
      }
      result = URLEncodedUtils.format(nvps, "UTF-8");
    } catch (Exception e){}
    return result;
  }

  public static String getFromParams(String key, Map<String, String[]> params){
    String result = "";
    try{
      String[] values = params.get(key);
      result = values[0];
    } catch (Exception e){}
    return result;
  }

  /**
   * Helper for authentication
   */
  public static ClientInfo getClientInfo(String clientId, String clientSecret){
    ClientInfo ci = (ClientInfo)Cache.get(clientId+clientSecret);
    if (ci == null) {
      ci = ClientInfo.findClientInfo(clientId, clientSecret);
    }
    if (ci != null) {
      Cache.set(clientId+clientSecret, ci);
    }
    return ci;
  }



}
