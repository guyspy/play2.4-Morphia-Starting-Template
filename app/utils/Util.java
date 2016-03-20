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
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import com.google.common.io.Files;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class Util {

  public static String UUID(){
    return java.util.UUID.randomUUID().toString();
  }

  public static String readFileAsString(String path) throws IOException {
    File file = Play.application().getFile(path);
    return Files.toString(file, StandardCharsets.UTF_8);
  }

  public static List<Object> _l(Object... args){
    List<Object> result = new ArrayList<Object>();
    for (Object o : args ) {
      result.add(o);
    }
    return result;
  }

  public static Object[] _a(Object... args){
    return args;
  }

  public static Map _m(Object... args){
    Map result = new HashMap();
    try{
      for ( int i = 0 ; i < args.length ; i = i+2 ) {
        result.put(args[i], args[i+1]);
      }
    } catch (Exception e){}
    return result;
  }

  public static Document _d(Object... args){
    Document result = new Document();
    try{
      for ( int i = 0 ; i < args.length ; i = i+2 ) {
        result.put(args[i].toString(), args[i+1]);
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

  // from play framework 1.2.7
  public static String decryptAES(String value, String privateKey) throws Exception {
    byte[] raw = privateKey.getBytes();
    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, skeySpec);
    byte[] byteVal = Hex.decodeHex(value.toCharArray());
    return new String(cipher.doFinal(byteVal));
  }

  public static String encryptAES(String value, String privateKey) throws Exception {
    byte[] raw = privateKey.getBytes();
    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
    byte[] byteVal = cipher.doFinal(value.getBytes());
    return String.valueOf(Hex.encodeHex(byteVal));
  }



}