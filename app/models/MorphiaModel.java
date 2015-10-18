package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.dao.BasicDAO;
import utils.ds.MorphiaObject;

public class MorphiaModel {

  @Id
  protected ObjectId id;

  protected Long _created;

  protected Long _modified;

  @PrePersist
  protected void ts(){
    Long ts = System.currentTimeMillis();
    if (this._created == null) {
      this._created = ts;
    }
    this._modified = ts;
  }

  public void save(){
    MorphiaObject.datastore.save(this);
  }

  public void delete(){
    MorphiaObject.datastore.delete(this);
  }

  public String getId(){
    return this.id.toString();
  }

  public Long getCreated(){
    return this._created;
  }

  public Long getModified(){
    return this._modified;
  }

  public static Object findById(Class classOfT, String id){
    Object result = null;
    try{
      result = MorphiaObject.datastore.get(classOfT, new ObjectId(id));
    } catch (Exception e){}
    return result;
  }

  public static Object findAndDelete(Query q){
    Object result = null;
    try{
      result = MorphiaObject.datastore.findAndDelete(q);
    } catch (Exception e){}
    return result;
  }

  public static Query q(Class classOfT){
    return MorphiaObject.datastore.createQuery(classOfT);
  }

  public static BasicDAO dao(Class classOfT){
    return new BasicDAO(classOfT, MorphiaObject.datastore);
  }

  public static long count(Class classOfT){
    return dao(classOfT).count();
  }

  public static long count(Class classOfT, Query q){
    return dao(classOfT).count(q);
  }

}

