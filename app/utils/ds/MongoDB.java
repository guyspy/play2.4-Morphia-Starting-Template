package utils.ds;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Morphia;
import play.Logger;
import play.Play;
import models.*;

public final class MongoDB {

  public static final String MONGODB_URI = Play.application().configuration().getString("mongodb.uri");

  public static final String MONGODB_NAME = Play.application().configuration().getString("mongodb.name");

  /**
   * Connects to MongoDB based on the configuration settings.
   * <p/>
   * If the database is not reachable, an error message is written and the
   * application exits.
   */
  public static boolean connect() {
    String _mongoURI = MONGODB_URI+MONGODB_NAME;
    MongoClientURI mongoURI = new MongoClientURI(_mongoURI);
    MorphiaObject.mongo = null;
    try {
      MorphiaObject.mongo = new MongoClient(mongoURI);
    }
    catch (Exception e) {
      Logger.info("Unknown Host");
    }
    if (MorphiaObject.mongo != null) {
      MorphiaObject.morphia = new Morphia();
      MorphiaObject.datastore = MorphiaObject.morphia.createDatastore(MorphiaObject.mongo, mongoURI.getDatabase());
      //Map classes
      MorphiaObject.morphia.map(ClientInfo.class);
      //EnsureIndexes
      MorphiaObject.datastore.ensureIndexes();
      MorphiaObject.datastore.ensureCaps();
    }
    Logger.debug("** Morphia datastore: " + MorphiaObject.datastore.getDB());
    return true;
  }


}

