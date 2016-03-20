package models;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.utils.IndexType;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.query.Query;
import org.apache.commons.codec.binary.Base64;
import java.util.*;
import java.security.SecureRandom;
import play.cache.Cache;
import play.libs.Crypto;
import play.data.format.Formats;
import play.data.validation.Constraints;

/**
 * Authentication for mobile app clients
 */
@Entity
@Indexes({
  @Index(
    fields = {
      @Field("clientId"),
      @Field("clientSecret")
    }
  )
})
public class ClientInfo extends MorphiaModel {

  @Indexed(unique=true)
  @Constraints.Required
  public String clientId;

  @Indexed(unique=true)
  @Constraints.Required
  public String clientSecret;

  public long platform;


  // for morphia to work properly
  public ClientInfo(){}

  public ClientInfo(long platform){
    this.clientId = randomString(20);
    this.clientSecret = randomString(72);
    this.platform = platform;
  }

  private static String randomString(int length) {
    SecureRandom random = new SecureRandom();
    byte bytes[] = new byte[length*2];
    random.nextBytes(bytes);
    String result = Base64.encodeBase64String(bytes);
    return result.substring(0, length);
  }

  public static ClientInfo findClientInfo(String clientId, String clientSecret){
    Query<ClientInfo> q = MorphiaModel.q(ClientInfo.class);
    return q.filter("clientId", clientId).filter("clientSecret", clientSecret).get();
  }

  public static ClientInfo findClientInfo(String clientId){
    Query<ClientInfo> q = MorphiaModel.q(ClientInfo.class);
    return q.filter("clientId", clientId).get();
  }

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

