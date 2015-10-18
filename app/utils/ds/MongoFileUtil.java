package utils.ds;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import play.Play;


/**
 * GridFS
 */
public final class MongoFileUtil {

  private static MongoClient mongoClient;

  private static DB db;

  private static void init() throws UnknownHostException {
    if (mongoClient == null) {
      String _mongoURI = MongoDB.MONGODB_URI+MongoDB.MONGODB_NAME;
      MongoClientURI mongoURI = new MongoClientURI(_mongoURI);
      mongoClient = new MongoClient(mongoURI);
      db = mongoClient.getDB(MongoDB.MONGODB_NAME);
    }
  }

  public static void saveFile(String filename, String contentType, File file) throws IOException {
    init();
    GridFS gfs = new GridFS(db);
    GridFSInputFile gfsFile = gfs.createFile(file);
    gfsFile.setFilename(filename);
    gfsFile.setContentType(contentType);
    gfsFile.save();
  }

  public static InputStream getFile(String filename) {
    InputStream result = null;
    try{
      init();
      GridFS gfs = new GridFS(db);
      GridFSDBFile r = gfs.findOne(filename);
      result = r.getInputStream();
    } catch (Exception e){}
    return result;
  }

  public static void remove(String filename) {
    try{
      init();
      GridFS gfs = new GridFS(db);
      gfs.remove(filename);
    } catch (Exception e){}
  }



}
