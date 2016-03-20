package controllers;

import play.*;
import play.cache.Cache;
import play.mvc.*;
import play.libs.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.codec.digest.DigestUtils;
import models.*;
import utils.DefaultErrorType;

public class SecuredAdminRequest extends Security.Authenticator {

  @Override
  public String getUsername(Http.Context ctx) {
    String result = null;
    Http.Request req = ctx.request();
    // get major authen headers
    String clientId = req.getHeader("x-aotter-clientid");
    String ts = req.getHeader("x-aotter-timestamp");
    String checksum = req.getHeader("x-aotter-checksum");
    // find authen
    // TBD: use cache
    /*ClientInfo ci = ClientInfo.findClientInfo(clientId);
    if (
      ci != null &&
      ci.platform == Platform.ADMIN &&
      isChecksumValid(ci, ts, checksum)
    ) {
      result = clientId;
    }*/
    return result;
  }

  @Override
  public Result onUnauthorized(Http.Context ctx) {
    ObjectNode result = Json.newObject();
    result.put(
      "error",
      Json.toJson(
        new AError(
          "ClientInfo",
          DefaultErrorType.UNAUTHORIZED
        )
      )
    );
    return ok(result);
  }

  /*private static boolean isChecksumValid(ClientInfo ci, String ts, String checksum){
    boolean result = false;
    try{
      // check ts
      Long tsL = Long.parseLong(ts);
      long currTime = System.currentTimeMillis();
      // time must be within 5 min
      if (currTime - tsL < 5 * 60000L ) {
        // check checksum string
        String correctChecksum = DigestUtils.sha256Hex(ci.clientSecret+"|"+ts);
        if (correctChecksum.equals(checksum)) {
          result = true;
        }
      }
    }
    catch (Exception e){}
    return result;
  }*/

}
