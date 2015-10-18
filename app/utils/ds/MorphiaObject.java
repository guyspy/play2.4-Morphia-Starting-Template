package utils.ds;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class MorphiaObject {

  public static MongoClient mongo;

  public static Morphia morphia;

  public static Datastore datastore;

}
