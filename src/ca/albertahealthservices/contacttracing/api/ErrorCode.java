package ca.albertahealthservices.contacttracing.api;

import android.content.Context;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\034\n\002\030\002\n\002\b\002\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\032\020\037\032\0020\0042\006\020 \032\0020!2\n\b\002\020\"\032\004\030\0010\004R\024\020\003\032\0020\004XD¢\006\b\n\000\032\004\b\005\020\006R\024\020\007\032\0020\004XD¢\006\b\n\000\032\004\b\b\020\006R\024\020\t\032\0020\004XD¢\006\b\n\000\032\004\b\n\020\006R\024\020\013\032\0020\004XD¢\006\b\n\000\032\004\b\f\020\006R\024\020\r\032\0020\004XD¢\006\b\n\000\032\004\b\016\020\006R\024\020\017\032\0020\004XD¢\006\b\n\000\032\004\b\020\020\006R\024\020\021\032\0020\004XD¢\006\b\n\000\032\004\b\022\020\006R\024\020\023\032\0020\004XD¢\006\b\n\000\032\004\b\024\020\006R\024\020\025\032\0020\004XD¢\006\b\n\000\032\004\b\026\020\006R\024\020\027\032\0020\004XD¢\006\b\n\000\032\004\b\030\020\006R\024\020\031\032\0020\004XD¢\006\b\n\000\032\004\b\032\020\006R\024\020\033\032\0020\004XD¢\006\b\n\000\032\004\b\034\020\006R\024\020\035\032\0020\004XD¢\006\b\n\000\032\004\b\036\020\006¨\006#"}, d2 = {"Lca/albertahealthservices/contacttracing/api/ErrorCode;", "", "()V", "ADAPTER_DOES_NOT_EXIST", "", "getADAPTER_DOES_NOT_EXIST", "()Ljava/lang/String;", "APPLICATION_DOES_NOT_EXIST", "getAPPLICATION_DOES_NOT_EXIST", "APPLICATION_NOT_REGISTERED", "getAPPLICATION_NOT_REGISTERED", "AUTHORIZATION_FAILURE", "getAUTHORIZATION_FAILURE", "CHALLENGE_HANDLING_CANCELED", "getCHALLENGE_HANDLING_CANCELED", "ILLEGAL_ARGUMENT_EXCEPTION", "getILLEGAL_ARGUMENT_EXCEPTION", "LOGIN_ALREADY_IN_PROCESS", "getLOGIN_ALREADY_IN_PROCESS", "LOGOUT_ALREADY_IN_PROCESS", "getLOGOUT_ALREADY_IN_PROCESS", "MINIMUM_SERVER", "getMINIMUM_SERVER", "MISSING_CHALLENGE_HANDLER", "getMISSING_CHALLENGE_HANDLER", "REQUEST_TIMEOUT", "getREQUEST_TIMEOUT", "SERVER_ERROR", "getSERVER_ERROR", "UNEXPECTED_ERROR", "getUNEXPECTED_ERROR", "getStringForErrorCode", "context", "Landroid/content/Context;", "errorCode", "app_release"}, k = 1, mv = {1, 1, 16})
public final class ErrorCode {
  private static final String ADAPTER_DOES_NOT_EXIST = "ADAPTER_DOES_NOT_EXIST";
  
  private static final String APPLICATION_DOES_NOT_EXIST = "APPLICATION_DOES_NOT_EXIST";
  
  private static final String APPLICATION_NOT_REGISTERED = "APPLICATION_NOT_REGISTERED";
  
  private static final String AUTHORIZATION_FAILURE = "AUTHORIZATION_FAILURE";
  
  private static final String CHALLENGE_HANDLING_CANCELED = "CHALLENGE_HANDLING_CANCELED";
  
  private static final String ILLEGAL_ARGUMENT_EXCEPTION = "ILLEGAL_ARGUMENT_EXCEPTION";
  
  public static final ErrorCode INSTANCE = new ErrorCode();
  
  private static final String LOGIN_ALREADY_IN_PROCESS = "LOGIN_ALREADY_IN_PROCESS";
  
  private static final String LOGOUT_ALREADY_IN_PROCESS = "LOGOUT_ALREADY_IN_PROCESS";
  
  private static final String MINIMUM_SERVER = "MINIMUM_SERVER";
  
  private static final String MISSING_CHALLENGE_HANDLER = "MISSING_CHALLENGE_HANDLER";
  
  private static final String REQUEST_TIMEOUT = "REQUEST_TIMEOUT";
  
  private static final String SERVER_ERROR = "SERVER_ERROR";
  
  private static final String UNEXPECTED_ERROR = "UNEXPECTED_ERROR";
  
  static {
    APPLICATION_NOT_REGISTERED = "APPLICATION_NOT_REGISTERED";
    AUTHORIZATION_FAILURE = "AUTHORIZATION_FAILURE";
    CHALLENGE_HANDLING_CANCELED = "CHALLENGE_HANDLING_CANCELED";
    ILLEGAL_ARGUMENT_EXCEPTION = "ILLEGAL_ARGUMENT_EXCEPTION";
    LOGIN_ALREADY_IN_PROCESS = "LOGIN_ALREADY_IN_PROCESS";
    LOGOUT_ALREADY_IN_PROCESS = "LOGOUT_ALREADY_IN_PROCESS";
    MINIMUM_SERVER = "MINIMUM_SERVER";
    MISSING_CHALLENGE_HANDLER = "MISSING_CHALLENGE_HANDLER";
    REQUEST_TIMEOUT = "REQUEST_TIMEOUT";
    SERVER_ERROR = "SERVER_ERROR";
    UNEXPECTED_ERROR = "UNEXPECTED_ERROR";
    ADAPTER_DOES_NOT_EXIST = "ADAPTER_DOES_NOT_EXIST";
    APPLICATION_DOES_NOT_EXIST = "APPLICATION_DOES_NOT_EXIST";
  }
  
  public final String getADAPTER_DOES_NOT_EXIST() {
    return ADAPTER_DOES_NOT_EXIST;
  }
  
  public final String getAPPLICATION_DOES_NOT_EXIST() {
    return APPLICATION_DOES_NOT_EXIST;
  }
  
  public final String getAPPLICATION_NOT_REGISTERED() {
    return APPLICATION_NOT_REGISTERED;
  }
  
  public final String getAUTHORIZATION_FAILURE() {
    return AUTHORIZATION_FAILURE;
  }
  
  public final String getCHALLENGE_HANDLING_CANCELED() {
    return CHALLENGE_HANDLING_CANCELED;
  }
  
  public final String getILLEGAL_ARGUMENT_EXCEPTION() {
    return ILLEGAL_ARGUMENT_EXCEPTION;
  }
  
  public final String getLOGIN_ALREADY_IN_PROCESS() {
    return LOGIN_ALREADY_IN_PROCESS;
  }
  
  public final String getLOGOUT_ALREADY_IN_PROCESS() {
    return LOGOUT_ALREADY_IN_PROCESS;
  }
  
  public final String getMINIMUM_SERVER() {
    return MINIMUM_SERVER;
  }
  
  public final String getMISSING_CHALLENGE_HANDLER() {
    return MISSING_CHALLENGE_HANDLER;
  }
  
  public final String getREQUEST_TIMEOUT() {
    return REQUEST_TIMEOUT;
  }
  
  public final String getSERVER_ERROR() {
    return SERVER_ERROR;
  }
  
  public final String getStringForErrorCode(Context paramContext, String paramString) {
    String str;
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    if (Intrinsics.areEqual(paramString, AUTHORIZATION_FAILURE)) {
      str = paramContext.getString(2131820585);
      Intrinsics.checkExpressionValueIsNotNull(str, "context.getString(R.string.auth_error)");
    } else if (Intrinsics.areEqual(paramString, REQUEST_TIMEOUT)) {
      str = str.getString(2131820764);
      Intrinsics.checkExpressionValueIsNotNull(str, "context.getString(R.string.timeout_error)");
    } else if (Intrinsics.areEqual(paramString, SERVER_ERROR)) {
      str = str.getString(2131820775);
      Intrinsics.checkExpressionValueIsNotNull(str, "context.getString(R.string.unknown_error)");
    } else if (Intrinsics.areEqual(paramString, UNEXPECTED_ERROR) || Intrinsics.areEqual(paramString, CHALLENGE_HANDLING_CANCELED) || Intrinsics.areEqual(paramString, LOGIN_ALREADY_IN_PROCESS) || Intrinsics.areEqual(paramString, LOGOUT_ALREADY_IN_PROCESS)) {
      str = str.getString(2131820775);
      Intrinsics.checkExpressionValueIsNotNull(str, "context.getString(R.string.unknown_error)");
    } else {
      str = str.getString(2131820774);
      Intrinsics.checkExpressionValueIsNotNull(str, "context.getString(R.string.unexpected_error)");
    } 
    return str;
  }
  
  public final String getUNEXPECTED_ERROR() {
    return UNEXPECTED_ERROR;
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/api/ErrorCode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */