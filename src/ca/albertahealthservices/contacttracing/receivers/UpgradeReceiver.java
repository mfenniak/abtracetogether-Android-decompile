package ca.albertahealthservices.contacttracing.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import ca.albertahealthservices.contacttracing.Utils;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\036\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\030\0002\0020\001B\005¢\006\002\020\002J\034\020\003\032\0020\0042\b\020\005\032\004\030\0010\0062\b\020\007\032\004\030\0010\bH\026¨\006\t"}, d2 = {"Lca/albertahealthservices/contacttracing/receivers/UpgradeReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 1, 16})
public final class UpgradeReceiver extends BroadcastReceiver {
  public void onReceive(Context paramContext, Intent paramIntent) {
    CentralLog.Companion companion;
    if (paramIntent == null) {
      try {
        Intrinsics.throwNpe();
        if ((Intrinsics.areEqual("android.intent.action.MY_PACKAGE_REPLACED", paramIntent.getAction()) ^ true) != 0)
          return; 
        if (paramContext != null) {
          CentralLog.Companion.i("UpgradeReceiver", "Starting service from upgrade receiver");
          Utils.INSTANCE.startBluetoothMonitoringService(paramContext);
        } 
      } catch (Exception exception) {
        companion = CentralLog.Companion;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to handle upgrade: ");
        stringBuilder.append(exception.getLocalizedMessage());
        companion.e("UpgradeReceiver", stringBuilder.toString());
      } 
      return;
    } 
    if ((Intrinsics.areEqual("android.intent.action.MY_PACKAGE_REPLACED", exception.getAction()) ^ true) != 0)
      return; 
    if (companion != null) {
      CentralLog.Companion.i("UpgradeReceiver", "Starting service from upgrade receiver");
      Utils.INSTANCE.startBluetoothMonitoringService((Context)companion);
    } 
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/receivers/UpgradeReceiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */