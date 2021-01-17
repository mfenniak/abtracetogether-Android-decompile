package ca.albertahealthservices.contacttracing.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import ca.albertahealthservices.contacttracing.Utils;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\036\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\030\0002\0020\001B\005¢\006\002\020\002J\030\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\bH\026¨\006\t"}, d2 = {"Lca/albertahealthservices/contacttracing/boot/StartOnBootReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 1, 16})
public final class StartOnBootReceiver extends BroadcastReceiver {
  public void onReceive(Context paramContext, Intent paramIntent) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramIntent, "intent");
    if (Intrinsics.areEqual("android.intent.action.BOOT_COMPLETED", paramIntent.getAction())) {
      CentralLog.Companion.d("StartOnBootReceiver", "boot completed received");
      try {
        CentralLog.Companion.d("StartOnBootReceiver", "Attempting to start service");
        Utils.INSTANCE.scheduleStartMonitoringService(paramContext, 500L);
      } finally {
        Exception exception = null;
        CentralLog.Companion companion = CentralLog.Companion;
        String str = exception.getLocalizedMessage();
        Intrinsics.checkExpressionValueIsNotNull(str, "e.localizedMessage");
        companion.e("StartOnBootReceiver", str);
      } 
    } 
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/boot/StartOnBootReceiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */