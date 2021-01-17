package ca.albertahealthservices.contacttracing;

import android.app.Application;
import android.content.Context;
import ca.albertahealthservices.contacttracing.idmanager.TempIDManager;
import ca.albertahealthservices.contacttracing.idmanager.TemporaryID;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.services.BluetoothMonitoringService;
import ca.albertahealthservices.contacttracing.streetpass.CentralDevice;
import ca.albertahealthservices.contacttracing.streetpass.PeripheralDevice;
import com.worklight.common.Logger;
import com.worklight.common.WLAnalytics;
import com.worklight.wlclient.api.WLClient;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\024\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\002\030\000 \0052\0020\001:\001\005B\005¢\006\002\020\002J\b\020\003\032\0020\004H\026¨\006\006"}, d2 = {"Lca/albertahealthservices/contacttracing/TracerApp;", "Landroid/app/Application;", "()V", "onCreate", "", "Companion", "app_release"}, k = 1, mv = {1, 1, 16})
public final class TracerApp extends Application {
  public static Context AppContext;
  
  public static final Companion Companion = new Companion(null);
  
  public static final String ORG = "CA_AB";
  
  private static final String TAG = "TracerApp";
  
  public void onCreate() {
    super.onCreate();
    Context context = getApplicationContext();
    Intrinsics.checkExpressionValueIsNotNull(context, "applicationContext");
    AppContext = context;
    WLClient.createInstance((Context)this);
    WLClient.getInstance().pinTrustedCertificatePublicKey("customCertificate.cer");
    WLAnalytics.init(this);
    WLAnalytics.addDeviceEventListener(WLAnalytics.DeviceEvent.LIFECYCLE);
    Logger.setCapture(true);
    Logger.setLevel(Logger.LEVEL.ERROR);
    WLAnalytics.send();
    Logger.send();
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000*\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\016\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\006\020\f\032\0020\rJ\006\020\016\032\0020\017J\006\020\020\032\0020\nR\032\020\003\032\0020\004X.¢\006\016\n\000\032\004\b\005\020\006\"\004\b\007\020\bR\016\020\t\032\0020\nXT¢\006\002\n\000R\016\020\013\032\0020\nXD¢\006\002\n\000¨\006\021"}, d2 = {"Lca/albertahealthservices/contacttracing/TracerApp$Companion;", "", "()V", "AppContext", "Landroid/content/Context;", "getAppContext", "()Landroid/content/Context;", "setAppContext", "(Landroid/content/Context;)V", "ORG", "", "TAG", "asCentralDevice", "Lca/albertahealthservices/contacttracing/streetpass/CentralDevice;", "asPeripheralDevice", "Lca/albertahealthservices/contacttracing/streetpass/PeripheralDevice;", "thisDeviceMsg", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class Companion {
    private Companion() {}
    
    public final CentralDevice asCentralDevice() {
      return new CentralDevice("Android", "SELF");
    }
    
    public final PeripheralDevice asPeripheralDevice() {
      return new PeripheralDevice("Android", "SELF");
    }
    
    public final Context getAppContext() {
      Context context = TracerApp.AppContext;
      if (context == null)
        Intrinsics.throwUninitializedPropertyAccessException("AppContext"); 
      return context;
    }
    
    public final void setAppContext(Context param1Context) {
      Intrinsics.checkParameterIsNotNull(param1Context, "<set-?>");
      TracerApp.AppContext = param1Context;
    }
    
    public final String thisDeviceMsg() {
      TemporaryID temporaryID1 = BluetoothMonitoringService.Companion.getBroadcastMessage();
      if (temporaryID1 != null) {
        CentralLog.Companion companion = CentralLog.Companion;
        String str = TracerApp.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Retrieved BM for storage: ");
        stringBuilder.append(temporaryID1);
        companion.i(str, stringBuilder.toString());
        if (!temporaryID1.isValidForCurrentTime()) {
          TemporaryID temporaryID = TempIDManager.INSTANCE.retrieveTemporaryID(TracerApp.Companion.getAppContext());
          if (temporaryID != null) {
            CentralLog.Companion.i(TracerApp.TAG, "Grab New Temp ID");
            BluetoothMonitoringService.Companion.setBroadcastMessage(temporaryID);
          } 
          if (temporaryID == null)
            CentralLog.Companion.e(TracerApp.TAG, "Failed to grab new Temp ID"); 
        } 
      } 
      TemporaryID temporaryID2 = BluetoothMonitoringService.Companion.getBroadcastMessage();
      if (temporaryID2 != null) {
        String str = temporaryID2.getTempID();
        if (str != null)
          return str; 
      } 
      return "Missing TempID";
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/TracerApp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */