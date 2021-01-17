package ca.albertahealthservices.contacttracing.logging;

import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\f\n\002\030\002\n\002\020\000\n\002\b\003\030\000 \0032\0020\001:\001\003B\005¢\006\002\020\002¨\006\004"}, d2 = {"Lca/albertahealthservices/contacttracing/logging/CentralLog;", "", "()V", "Companion", "app_release"}, k = 1, mv = {1, 1, 16})
public final class CentralLog {
  public static final Companion Companion = new Companion(null);
  
  private static PowerManager pm;
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\0002\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\002\n\000\n\002\020\016\n\002\b\002\n\002\020\003\n\002\b\005\n\002\020\013\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\026\020\t\032\0020\n2\006\020\013\032\0020\f2\006\020\r\032\0020\fJ \020\t\032\0020\n2\006\020\013\032\0020\f2\006\020\r\032\0020\f2\b\020\016\032\004\030\0010\017J\026\020\016\032\0020\n2\006\020\013\032\0020\f2\006\020\r\032\0020\fJ\b\020\020\032\0020\fH\002J\026\020\021\032\0020\n2\006\020\013\032\0020\f2\006\020\r\032\0020\fJ\016\020\022\032\0020\n2\006\020\023\032\0020\004J\b\020\024\032\0020\025H\002J\026\020\026\032\0020\n2\006\020\013\032\0020\f2\006\020\r\032\0020\fR\034\020\003\032\004\030\0010\004X\016¢\006\016\n\000\032\004\b\005\020\006\"\004\b\007\020\b¨\006\027"}, d2 = {"Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;", "", "()V", "pm", "Landroid/os/PowerManager;", "getPm", "()Landroid/os/PowerManager;", "setPm", "(Landroid/os/PowerManager;)V", "d", "", "tag", "", "message", "e", "", "getIdleStatus", "i", "setPowerManager", "powerManager", "shouldLog", "", "w", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class Companion {
    private Companion() {}
    
    private final String getIdleStatus() {
      if (Build.VERSION.SDK_INT >= 23) {
        String str;
        PowerManager powerManager = getPm();
        if (powerManager != null && true == powerManager.isDeviceIdleMode()) {
          str = " IDLE ";
        } else {
          str = " NOT-IDLE ";
        } 
        return str;
      } 
      return " NO-DOZE-FEATURE ";
    }
    
    private final boolean shouldLog() {
      return false;
    }
    
    public final void d(String param1String1, String param1String2) {
      Intrinsics.checkParameterIsNotNull(param1String1, "tag");
      Intrinsics.checkParameterIsNotNull(param1String2, "message");
      Companion companion = this;
      if (!companion.shouldLog())
        return; 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(companion.getIdleStatus());
      stringBuilder1.append(param1String2);
      Log.d(param1String1, stringBuilder1.toString());
      SDLog sDLog = SDLog.INSTANCE;
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(companion.getIdleStatus());
      stringBuilder2.append(param1String2);
      sDLog.d(new String[] { param1String1, stringBuilder2.toString() });
    }
    
    public final void d(String param1String1, String param1String2, Throwable param1Throwable) {
      Intrinsics.checkParameterIsNotNull(param1String1, "tag");
      Intrinsics.checkParameterIsNotNull(param1String2, "message");
      Companion companion = this;
      if (!companion.shouldLog())
        return; 
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(companion.getIdleStatus());
      stringBuilder2.append(param1String2);
      Log.d(param1String1, stringBuilder2.toString(), param1Throwable);
      SDLog sDLog = SDLog.INSTANCE;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(companion.getIdleStatus());
      stringBuilder1.append(param1String2);
      sDLog.d(new String[] { param1String1, stringBuilder1.toString() });
    }
    
    public final void e(String param1String1, String param1String2) {
      Intrinsics.checkParameterIsNotNull(param1String1, "tag");
      Intrinsics.checkParameterIsNotNull(param1String2, "message");
      Companion companion = this;
      if (!companion.shouldLog())
        return; 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(companion.getIdleStatus());
      stringBuilder.append(param1String2);
      Log.e(param1String1, stringBuilder.toString());
      SDLog sDLog = SDLog.INSTANCE;
      stringBuilder = new StringBuilder();
      stringBuilder.append(companion.getIdleStatus());
      stringBuilder.append(param1String2);
      sDLog.e(new String[] { param1String1, stringBuilder.toString() });
    }
    
    public final PowerManager getPm() {
      return CentralLog.pm;
    }
    
    public final void i(String param1String1, String param1String2) {
      Intrinsics.checkParameterIsNotNull(param1String1, "tag");
      Intrinsics.checkParameterIsNotNull(param1String2, "message");
      Companion companion = this;
      if (!companion.shouldLog())
        return; 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(companion.getIdleStatus());
      stringBuilder.append(param1String2);
      Log.i(param1String1, stringBuilder.toString());
      SDLog sDLog = SDLog.INSTANCE;
      stringBuilder = new StringBuilder();
      stringBuilder.append(companion.getIdleStatus());
      stringBuilder.append(param1String2);
      sDLog.i(new String[] { param1String1, stringBuilder.toString() });
    }
    
    public final void setPm(PowerManager param1PowerManager) {
      CentralLog.pm = param1PowerManager;
    }
    
    public final void setPowerManager(PowerManager param1PowerManager) {
      Intrinsics.checkParameterIsNotNull(param1PowerManager, "powerManager");
      setPm(param1PowerManager);
    }
    
    public final void w(String param1String1, String param1String2) {
      Intrinsics.checkParameterIsNotNull(param1String1, "tag");
      Intrinsics.checkParameterIsNotNull(param1String2, "message");
      Companion companion = this;
      if (!companion.shouldLog())
        return; 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(companion.getIdleStatus());
      stringBuilder1.append(param1String2);
      Log.w(param1String1, stringBuilder1.toString());
      SDLog sDLog = SDLog.INSTANCE;
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(companion.getIdleStatus());
      stringBuilder2.append(param1String2);
      sDLog.w(new String[] { param1String1, stringBuilder2.toString() });
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/logging/CentralLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */