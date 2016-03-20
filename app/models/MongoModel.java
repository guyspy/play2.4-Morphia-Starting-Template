package models;

import play.libs.Json;
import java.lang.reflect.Field;
import org.bson.types.ObjectId;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.Success;
import org.bson.Document;
import rx.Observable;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.lte;
import static utils.Util._l;
import static utils.Util._a;
import static utils.Util._m;
import static utils.Util._d;
import utils.ds.MongoDB;
import utils.ds.DocumentParser;
import utils.Util;

public class MongoModel<T> {

  public String _id;

  public Long _created;

  public Long _modified;

  public MongoModel(){
    _id = Util.UUID();
  }

  public Document toDocument(){
    Document doc = new Document();
    Field[] fields = this.getClass().getFields();
    for (Field f : fields) {
      String fName = f.getName();
      try{
        doc.put(fName, f.get(this));
      }
      catch (Exception e){
        play.Logger.warn("[MongoModel] toDocument() failed with exception ", e);
      }
    }
    return doc;
  }

  public MongoCollection<Document> col(){
    return col(this.getClass());
  }

  public static MongoCollection<Document> col(Class clazz){
    String name = clazz.getSimpleName();
    return MongoDB.col(name);
  }

  public void create(){
    Document doc = toDocument();
    col().insertOne(doc).toBlocking().single();
  }

  public void prePersist(){
    Long ts = System.currentTimeMillis();
    if (this._created == null) {
      this._created = ts;
    }
    this._modified = ts;
  }

  public T save(){
    prePersist();
    Document doc = col().findOneAndUpdate(
      eq("_id", _id),
      _d("$set", toDocument()),
      new FindOneAndUpdateOptions()
        .upsert(true)
        .returnDocument(ReturnDocument.AFTER)
    ).toBlocking().single();
    return new DocumentParser<T>(doc).toClass(this.getClass());
  }

  public static Long count(Class clazz){
    return col(clazz).count().toBlocking().single();
  }

  /*public Observable<Document> save(){
    return col().findOneAndUpdate(
      eq("_id", _id),
      _d("$set", toDocument()),
      new FindOneAndUpdateOptions()
        .upsert(true)
        .returnDocument(ReturnDocument.AFTER)
    );
  }*/

}

