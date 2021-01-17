package ca.albertahealthservices.contacttracing.streetpass;

import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import ca.albertahealthservices.contacttracing.bluetooth.gatt.GattServer;
import ca.albertahealthservices.contacttracing.bluetooth.gatt.GattService;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000.\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\005\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\002\n\002\020\002\n\000\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006J\006\020\016\032\0020\017J\032\020\020\032\004\030\0010\0132\006\020\002\032\0020\0032\006\020\004\032\0020\005H\002J\006\020\021\032\0020\022R\016\020\007\032\0020\005XD¢\006\002\n\000R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\b\020\tR\020\020\n\032\004\030\0010\013X\016¢\006\002\n\000R\021\020\004\032\0020\005¢\006\b\n\000\032\004\b\f\020\r¨\006\023"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/StreetPassServer;", "", "context", "Landroid/content/Context;", "serviceUUIDString", "", "(Landroid/content/Context;Ljava/lang/String;)V", "TAG", "getContext", "()Landroid/content/Context;", "gattServer", "Lca/albertahealthservices/contacttracing/bluetooth/gatt/GattServer;", "getServiceUUIDString", "()Ljava/lang/String;", "checkServiceAvailable", "", "setupGattServer", "tearDown", "", "app_release"}, k = 1, mv = {1, 1, 16})
public final class StreetPassServer {
  private final String TAG;
  
  private final Context context;
  
  private GattServer gattServer;
  
  private final String serviceUUIDString;
  
  public StreetPassServer(Context paramContext, String paramString) {
    this.context = paramContext;
    this.serviceUUIDString = paramString;
    this.TAG = "StreetPassServer";
    this.gattServer = setupGattServer(paramContext, paramString);
  }
  
  private final GattServer setupGattServer(Context paramContext, String paramString) {
    GattServer gattServer = new GattServer(paramContext, paramString);
    if (gattServer.startServer()) {
      gattServer.addService(new GattService(paramContext, paramString));
      return gattServer;
    } 
    return null;
  }
  
  public final boolean checkServiceAvailable() {
    GattServer gattServer = this.gattServer;
    if (gattServer != null) {
      BluetoothGattServer bluetoothGattServer = gattServer.getBluetoothGattServer();
      if (bluetoothGattServer != null) {
        List<BluetoothGattService> list = bluetoothGattServer.getServices();
        if (list != null) {
          List<BluetoothGattService> list1 = list;
          list = new ArrayList();
          for (BluetoothGattService bluetoothGattService2 : list1) {
            BluetoothGattService bluetoothGattService1 = bluetoothGattService2;
            Intrinsics.checkExpressionValueIsNotNull(bluetoothGattService1, "it");
            if (bluetoothGattService1.getUuid().toString().equals(this.serviceUUIDString))
              list.add(bluetoothGattService2); 
          } 
          return list.isEmpty() ^ true;
        } 
      } 
    } 
    return false;
  }
  
  public final Context getContext() {
    return this.context;
  }
  
  public final String getServiceUUIDString() {
    return this.serviceUUIDString;
  }
  
  public final void tearDown() {
    GattServer gattServer = this.gattServer;
    if (gattServer != null)
      gattServer.stop(); 
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/StreetPassServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */