package controllers;

import play.*;
import play.cache.Cache;
import play.libs.Json;
import play.libs.F.Promise;
import play.mvc.*;
import play.data.Form;
import org.apache.commons.lang3.StringUtils;
import models.*;
import utils.*;
import java.lang.Object;
import java.util.*;

@Security.Authenticated(SecuredAdminRequest.class)
public class AdminAPI extends JsonController {

}