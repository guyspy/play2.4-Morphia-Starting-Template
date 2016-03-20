import play.*;
import play.mvc.*;
import java.lang.reflect.Method;

import models.*;
import models.app.ClientInfo;

public class Global extends GlobalSettings {

  @Override
  public void onStart(Application app) {
    Logger.info("Application has started");
    utils.ds.MongoDB.connect();
    // create default clientInfos
    if (MongoModel.count(ClientInfo.class) == 0) {
      ClientInfo admin = new ClientInfo(Platform.ADMIN, "admin").save();
      ClientInfo ios = new ClientInfo(Platform.IOS, "ios").save();
      ClientInfo android = new ClientInfo(Platform.ANDROID, "android").save();
      ClientInfo wp = new ClientInfo(Platform.WP, "wp").save();
    }

    // load dummy post data if not prod
    if (!Play.isProd()) {
      try{
        // load your dummy data here with Fixture.loadModel()
      } catch (Exception e){
        Logger.warn("[Global.onStart()] load data failed ", e);
      }
    }
  }

  @Override
  public void onStop(Application app) {
    Logger.info("Application shutdown...");
  }

  /*@Override
  public Action onRequest(Http.Request request, Method actionMethod) {
    Logger.info(request.toString());
    return super.onRequest(request, actionMethod);
  }*/

}
