package controllers;

import play.*;
import play.cache.Cache;
import play.mvc.*;
import play.libs.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import utils.Util;
import utils.DefaultErrorType;

/**
 * logintoken is NOT required
 */
public class SecuredAppRequest extends Security.Authenticator {

  @Override
  public String getUsername(Http.Context ctx) {
    String result = null;
    Http.Request req = ctx.request();
    // get major authen headers
    String clientId = req.getHeader("x-aotter-clientid");
    String clientSecret = req.getHeader("x-aotter-clientsecret");
    // find authen
    ClientInfo ci = Util.getClientInfo(clientId, clientSecret);
    if (ci != null) {
      // set more info
      ctx.args.put("platform", ci.platform);
      ctx.args.put("clientSecret", ci.clientSecret);
      result = clientId;
    }
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

}
