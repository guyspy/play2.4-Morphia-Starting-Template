package controllers;

import play.*;
import play.cache.Cached;
import play.libs.Json;
import play.libs.F.Promise;
import play.mvc.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import utils.*;

@Security.Authenticated(SecuredAppRequest.class)
public class MobileAPI extends JsonController {

}

