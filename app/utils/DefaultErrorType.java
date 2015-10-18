package utils;

public enum DefaultErrorType implements ErrorType {

  UNAUTHORIZED(401, "validation.unauthorized"),
  FORBIDDEN(403, "validation.forbidden"),
  NOTFOUND(404, "validation.notFound"),
  ERROR(500, "validation.error");

  private int code;

  private String messageKey;

  private DefaultErrorType(int code, String messageKey){
    this.code = code;
    this.messageKey = messageKey;
  }

  public int getCode(){
    return this.code;
  }

  public String getMessageKey(){
    return this.messageKey;
  }

}
