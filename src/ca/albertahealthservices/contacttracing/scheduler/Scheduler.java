package ca.albertahealthservices.contacttracing.scheduler;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import ca.albertahealthservices.contacttracing.Preference;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\0004\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\020\002\n\000\n\002\020\b\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\t\n\002\b\003\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\036\020\005\032\0020\0062\006\020\007\032\0020\b2\006\020\t\032\0020\n2\006\020\013\032\0020\fJ&\020\r\032\0020\0062\006\020\007\032\0020\b2\006\020\t\032\0020\n2\006\020\013\032\0020\f2\006\020\016\032\0020\017J&\020\020\032\0020\0062\006\020\007\032\0020\b2\006\020\t\032\0020\n2\006\020\013\032\0020\f2\006\020\021\032\0020\017R\016\020\003\032\0020\004XT¢\006\002\n\000¨\006\022"}, d2 = {"Lca/albertahealthservices/contacttracing/scheduler/Scheduler;", "", "()V", "TAG", "", "cancelServiceIntent", "", "requestCode", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "scheduleRepeatingServiceIntent", "intervalMillis", "", "scheduleServiceIntent", "timeFromNowInMillis", "app_release"}, k = 1, mv = {1, 1, 16})
public final class Scheduler {
  public static final Scheduler INSTANCE = new Scheduler();
  
  public static final String TAG = "Scheduler";
  
  public final void cancelServiceIntent(int paramInt, Context paramContext, Intent paramIntent) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramIntent, "intent");
    PendingIntent.getService(paramContext, paramInt, paramIntent, 134217728).cancel();
  }
  
  public final void scheduleRepeatingServiceIntent(int paramInt, Context paramContext, Intent paramIntent, long paramLong) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramIntent, "intent");
    Object object = paramContext.getSystemService("alarm");
    if (object != null) {
      object = object;
      PendingIntent pendingIntent = PendingIntent.getService(paramContext, paramInt, paramIntent, 134217728);
      CentralLog.Companion companion = CentralLog.Companion;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Purging alarm set to ");
      stringBuilder.append(Preference.INSTANCE.getLastPurgeTime(paramContext) + paramLong);
      companion.d("Scheduler", stringBuilder.toString());
      object.setRepeating(1, Preference.INSTANCE.getLastPurgeTime(paramContext) + paramLong, paramLong, pendingIntent);
      return;
    } 
    throw new TypeCastException("null cannot be cast to non-null type android.app.AlarmManager");
  }
  
  public final void scheduleServiceIntent(int paramInt, Context paramContext, Intent paramIntent, long paramLong) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramIntent, "intent");
    Object object = paramContext.getSystemService("alarm");
    if (object != null) {
      object = object;
      PendingIntent pendingIntent = PendingIntent.getService(paramContext, paramInt, paramIntent, 134217728);
      if (Build.VERSION.SDK_INT >= 23) {
        object.setExactAndAllowWhileIdle(2, SystemClock.elapsedRealtime() + paramLong, pendingIntent);
      } else {
        object.set(2, SystemClock.elapsedRealtime() + paramLong, pendingIntent);
      } 
      return;
    } 
    throw new TypeCastException("null cannot be cast to non-null type android.app.AlarmManager");
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/scheduler/Scheduler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */