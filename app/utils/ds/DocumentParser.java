package utils.ds;

import play.Play;
import play.libs.Json;
import java.lang.reflect.Field;
import java.util.*;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.bson.json.JsonMode;

public class DocumentParser<T> {

  private Class clazz;

  private Document doc;

  public DocumentParser(Document doc){
    this.doc = doc;
  }

  public T toClass(Class clazz){
    this.clazz = clazz;

    Map m = new HashMap();
    Field[] fields = this.clazz.getFields();
    for (Field f : fields) {
      try{
        String fName = f.getName();
        m.put(fName, doc.get(fName, f.getType()));
      } catch (Exception e){}
    }
    //String json = Json.stringify(Json.toJson(m));
    //String json = doc.toJson(new JsonWriterSettings(JsonMode.SHELL));

    //play.Logger.info("json "+json);
    return (T)Json.fromJson(Json.toJson(m), clazz);
  }

}
