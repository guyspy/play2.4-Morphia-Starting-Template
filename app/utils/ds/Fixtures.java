package utils.ds;

import play.libs.Json;
import java.io.IOException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import com.fasterxml.jackson.databind.JsonNode;

public class Fixtures {

  public static void loadModel(String jsonPath, Class classOfT) throws IOException {
    String jsonStr = utils.Util.readFileAsString(jsonPath);
    JsonNode json = Json.parse(jsonStr);
    if (json.isArray()) {
      for (JsonNode n : json ) {
        Object o = Json.fromJson(n, classOfT);
        MorphiaObject.datastore.save(o);
      }
    }
    else {
      Object o = Json.fromJson(json, classOfT);
      MorphiaObject.datastore.save(o);
    }
  }

  public static void dropModel(Class classOfT){
    Datastore ds = MorphiaObject.datastore;
    Query q = ds.createQuery(classOfT);
    ds.delete(q);
  }

}
