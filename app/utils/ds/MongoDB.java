package utils.ds;

import play.Play;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoDatabase;
import com.mongodb.rx.client.MongoCollection;
import org.bson.Document;

public final class MongoDB {

  public static MongoClient mongoClient;

  public static MongoDatabase DB;

  public static final String MONGODB_URI = Play.application().configuration().getString("mongodb.uri");

  public static final String MONGODB_NAME = Play.application().configuration().getString("mongodb.name");

  /**
   * Connects to MongoDB based on the configuration settings.
   */
  public static boolean connect() {
    mongoClient = MongoClients.create(MONGODB_URI);
    DB = mongoClient.getDatabase(MONGODB_NAME);
    return true;
  }

  public static void test(){
    MongoCollection<Document> collection = DB.getCollection("test");
    collection.insertOne(new Document("name", "GG")).toBlocking().single();
  }

  public static MongoCollection<Document> col(String name){
    return DB.getCollection(name);
  }




}

