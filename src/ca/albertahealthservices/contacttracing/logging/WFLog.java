package ca.albertahealthservices.contacttracing.logging;

import com.worklight.common.Logger;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\f\n\002\030\002\n\002\020\000\n\002\b\003\030\000 \0032\0020\001:\001\003B\005¢\006\002\020\002¨\006\004"}, d2 = {"Lca/albertahealthservices/contacttracing/logging/WFLog;", "", "()V", "Companion", "app_release"}, k = 1, mv = {1, 1, 16})
public final class WFLog {
  public static final Companion Companion = new Companion(null);
  
  private static final String TAG = "ABTraceTogether";
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\"\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\020\002\n\002\b\005\n\002\020\003\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\016\020\005\032\0020\0062\006\020\007\032\0020\004J\016\020\b\032\0020\0062\006\020\007\032\0020\004J\016\020\t\032\0020\0062\006\020\007\032\0020\004J\030\020\n\032\0020\0062\006\020\007\032\0020\0042\b\020\013\032\004\030\0010\fJ\030\020\r\032\0020\0062\006\020\007\032\0020\0042\b\020\013\032\004\030\0010\fJ\016\020\016\032\0020\0062\006\020\007\032\0020\004R\016\020\003\032\0020\004XD¢\006\002\n\000¨\006\017"}, d2 = {"Lca/albertahealthservices/contacttracing/logging/WFLog$Companion;", "", "()V", "TAG", "", "log", "", "message", "logDebug", "logError", "logErrorWithException", "e", "", "logFatalWithException", "logInfo", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class Companion {
    private Companion() {}
    
    public final void log(String param1String) {
      Intrinsics.checkParameterIsNotNull(param1String, "message");
      Logger.getInstance(WFLog.TAG).log(param1String);
      Logger.send();
    }
    
    public final void logDebug(String param1String) {
      Intrinsics.checkParameterIsNotNull(param1String, "message");
      Logger.getInstance(WFLog.TAG).debug(param1String);
      Logger.send();
    }
    
    public final void logError(String param1String) {
      Intrinsics.checkParameterIsNotNull(param1String, "message");
      Logger.getInstance(WFLog.TAG).error(param1String);
      Logger.send();
    }
    
    public final void logErrorWithException(String param1String, Throwable param1Throwable) {
      Intrinsics.checkParameterIsNotNull(param1String, "message");
      Logger.getInstance(WFLog.TAG).error(param1String, new JSONObject(), param1Throwable);
      Logger.send();
    }
    
    public final void logFatalWithException(String param1String, Throwable param1Throwable) {
      Intrinsics.checkParameterIsNotNull(param1String, "message");
      Logger.getInstance(WFLog.TAG).fatal(param1String, new JSONObject(), param1Throwable);
      Logger.send();
    }
    
    public final void logInfo(String param1String) {
      Intrinsics.checkParameterIsNotNull(param1String, "message");
      Logger.getInstance(WFLog.TAG).error(param1String);
      Logger.send();
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/logging/WFLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */