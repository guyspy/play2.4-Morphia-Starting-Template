import play.*;
import play.mvc.*;
import java.lang.reflect.Method;

import models.*;

public class Global extends GlobalSettings {

  @Override
  public void onStart(Application app) {
    Logger.info("Application has started");
    utils.ds.MongoDB.connect();
    // create default clientInfos
    if (MorphiaModel.count(ClientInfo.class) == 0) {
      new ClientInfo(Platform.ADMIN).save();
      new ClientInfo(Platform.IOS).save();
      new ClientInfo(Platform.ANDROID).save();
      new ClientInfo(Platform.WP).save();
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
