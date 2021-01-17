package ca.albertahealthservices.contacttracing.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import ca.albertahealthservices.contacttracing.MainActivity;
import ca.albertahealthservices.contacttracing.onboarding.OnboardingActivity;
import ca.albertahealthservices.contacttracing.services.BluetoothMonitoringService;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\f\n\002\030\002\n\002\020\000\n\002\b\003\030\000 \0032\0020\001:\001\003B\005¢\006\002\020\002¨\006\004"}, d2 = {"Lca/albertahealthservices/contacttracing/notifications/NotificationTemplates;", "", "()V", "Companion", "app_release"}, k = 1, mv = {1, 1, 16})
public final class NotificationTemplates {
  public static final Companion Companion = new Companion(null);
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000 \n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\026\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\bJ\026\020\t\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\bJ\026\020\n\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\b¨\006\013"}, d2 = {"Lca/albertahealthservices/contacttracing/notifications/NotificationTemplates$Companion;", "", "()V", "getRunningNotification", "Landroid/app/Notification;", "context", "Landroid/content/Context;", "channel", "", "getStartupNotification", "lackingThingsNotification", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class Companion {
    private Companion() {}
    
    public final Notification getRunningNotification(Context param1Context, String param1String) {
      Intrinsics.checkParameterIsNotNull(param1Context, "context");
      Intrinsics.checkParameterIsNotNull(param1String, "channel");
      Intent intent = new Intent(param1Context, MainActivity.class);
      PendingIntent pendingIntent = PendingIntent.getActivity(param1Context, BluetoothMonitoringService.Companion.getPENDING_ACTIVITY(), intent, 0);
      Notification notification = (new NotificationCompat.Builder(param1Context, param1String)).setContentTitle(param1Context.getText(2131820739)).setContentText(param1Context.getText(2131820738)).setOngoing(true).setPriority(-1).setSmallIcon(2131165378).setContentIntent(pendingIntent).setTicker(param1Context.getText(2131820738)).setStyle((NotificationCompat.Style)(new NotificationCompat.BigTextStyle()).bigText(param1Context.getText(2131820738))).setWhen(System.currentTimeMillis()).setSound(null).setVibrate(null).setColor(ContextCompat.getColor(param1Context, 2131034313)).build();
      Intrinsics.checkExpressionValueIsNotNull(notification, "builder.build()");
      return notification;
    }
    
    public final Notification getStartupNotification(Context param1Context, String param1String) {
      Intrinsics.checkParameterIsNotNull(param1Context, "context");
      Intrinsics.checkParameterIsNotNull(param1String, "channel");
      Notification notification = (new NotificationCompat.Builder(param1Context, param1String)).setContentText("Tracer is setting up its antennas").setContentTitle("Setting things up").setOngoing(true).setPriority(-1).setSmallIcon(2131165379).setWhen(System.currentTimeMillis()).setSound(null).setVibrate(null).setColor(ContextCompat.getColor(param1Context, 2131034313)).build();
      Intrinsics.checkExpressionValueIsNotNull(notification, "builder.build()");
      return notification;
    }
    
    public final Notification lackingThingsNotification(Context param1Context, String param1String) {
      Intrinsics.checkParameterIsNotNull(param1Context, "context");
      Intrinsics.checkParameterIsNotNull(param1String, "channel");
      Intent intent = new Intent(param1Context, OnboardingActivity.class);
      intent.putExtra("page", 3);
      PendingIntent pendingIntent = PendingIntent.getActivity(param1Context, BluetoothMonitoringService.Companion.getPENDING_WIZARD_REQ_CODE(), intent, 0);
      Notification notification = (new NotificationCompat.Builder(param1Context, param1String)).setContentTitle(param1Context.getText(2131820737)).setContentText(param1Context.getText(2131820736)).setOngoing(true).setPriority(-1).setSmallIcon(2131165380).setTicker(param1Context.getText(2131820736)).addAction(2131165379, param1Context.getText(2131820735), pendingIntent).setContentIntent(pendingIntent).setWhen(System.currentTimeMillis()).setSound(null).setVibrate(null).setColor(ContextCompat.getColor(param1Context, 2131034313)).build();
      Intrinsics.checkExpressionValueIsNotNull(notification, "builder.build()");
      return notification;
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/notifications/NotificationTemplates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */