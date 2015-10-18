package models;
import play.i18n.Messages;

public class AError {

  public int code;

  public String key;

  public String message;

  public AError(String key, utils.ErrorType errType){
    this.code = errType.getCode();
    this.message = Messages.get(errType.getMessageKey(), key);
    this.key = key;
  }

  public AError(String key, utils.ErrorType errType, Object... args){
    this.code = errType.getCode();
    this.message = Messages.get(errType.getMessageKey(), args);
    this.key = key;
  }

}