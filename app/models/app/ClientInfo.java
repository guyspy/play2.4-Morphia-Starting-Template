package models.app;

import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;
import models.MongoModel;

public class ClientInfo extends MongoModel<ClientInfo> {

  public String clientId;

  public String clientSecret;

  public Long platform;

  public String name;

  public ClientInfo(){}

  public ClientInfo(Long platform, String name){
    this.clientId = randomString(20);
    this.clientSecret = randomString(72);
    this.platform = platform;
    this.name = name;
  }

  private static String randomString(int length) {
    SecureRandom random = new SecureRandom();
    byte bytes[] = new byte[length*2];
    random.nextBytes(bytes);
    String result = Base64.encodeBase64String(bytes);
    return result.substring(0, length);
  }

}
