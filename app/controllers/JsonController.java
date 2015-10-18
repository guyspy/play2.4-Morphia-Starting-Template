package controllers;

import play.*;
import play.mvc.*;
import play.libs.Json;
import play.data.Form;
import play.data.validation.ValidationError;
import java.util.List;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonController extends Controller {

  protected Object getBodyJsonAs(Class classOfT){
    Object result = null;
    JsonNode json = request().body().asJson();
    if (json != null) {
      result = Json.fromJson(json, classOfT);
    }
    return result;
  }

  protected Result success(Object obj){
    ObjectNode result = Json.newObject();
    result.put("success", Json.toJson(obj));
    return ok(result);
  }

  protected Result errors(models.AError... errors){
    ObjectNode result = Json.newObject();
    result.put("errors", Json.toJson(errors));
    return ok(result);
  }

  protected Result errors(String errorMessage){
    ObjectNode result = Json.newObject();
    result.put("errors", errorMessage);
    return ok(result);
  }

  protected Result errors(Form form){
    ObjectNode result = Json.newObject();
    result.put("errors", form.errorsAsJson());
    return ok(result);
  }
}
