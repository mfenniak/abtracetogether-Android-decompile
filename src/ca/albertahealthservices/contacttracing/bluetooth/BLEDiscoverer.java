package ca.albertahealthservices.contacttracing.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.ParcelUuid;
import android.os.Parcelable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import java.util.UUID;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty;

@Metadata(bv = {1, 0, 3}, d1 = {"\000=\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\003\n\002\030\002\n\002\b\f\n\002\030\002\n\000\n\002\b\002\n\002\030\002\n\002\b\007\n\002\020\002\n\002\b\002*\001\030\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006J\006\020!\032\0020\"J\006\020#\032\0020\"R\016\020\007\032\0020\005XD¢\006\002\n\000R\035\020\b\032\004\030\0010\t8BX\002¢\006\f\n\004\b\f\020\r\032\004\b\n\020\013R+\020\002\032\0020\0032\006\020\016\032\0020\0038B@BX\002¢\006\022\n\004\b\023\020\024\032\004\b\017\020\020\"\004\b\021\020\022R\016\020\025\032\0020\026X\016¢\006\002\n\000R\020\020\027\032\0020\030X\004¢\006\004\n\002\020\031R+\020\033\032\0020\0322\006\020\016\032\0020\0328B@BX\002¢\006\022\n\004\b \020\024\032\004\b\034\020\035\"\004\b\036\020\037¨\006$"}, d2 = {"Lca/albertahealthservices/contacttracing/bluetooth/BLEDiscoverer;", "", "context", "Landroid/content/Context;", "serviceUUIDString", "", "(Landroid/content/Context;Ljava/lang/String;)V", "TAG", "bluetoothAdapter", "Landroid/bluetooth/BluetoothAdapter;", "getBluetoothAdapter", "()Landroid/bluetooth/BluetoothAdapter;", "bluetoothAdapter$delegate", "Lkotlin/Lazy;", "<set-?>", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "context$delegate", "Lkotlin/properties/ReadWriteProperty;", "localBroadcastManager", "Landroidx/localbroadcastmanager/content/LocalBroadcastManager;", "mDiscoveryReceiver", "ca/albertahealthservices/contacttracing/bluetooth/BLEDiscoverer$mDiscoveryReceiver$1", "Lca/albertahealthservices/contacttracing/bluetooth/BLEDiscoverer$mDiscoveryReceiver$1;", "Landroid/os/ParcelUuid;", "serviceUUID", "getServiceUUID", "()Landroid/os/ParcelUuid;", "setServiceUUID", "(Landroid/os/ParcelUuid;)V", "serviceUUID$delegate", "cancelDiscovery", "", "startDiscovery", "app_release"}, k = 1, mv = {1, 1, 16})
public final class BLEDiscoverer {
  private final String TAG = "BLEDiscoverer";
  
  private final Lazy bluetoothAdapter$delegate;
  
  private final ReadWriteProperty context$delegate = Delegates.INSTANCE.notNull();
  
  private LocalBroadcastManager localBroadcastManager;
  
  private final BLEDiscoverer$mDiscoveryReceiver$1 mDiscoveryReceiver;
  
  private final ReadWriteProperty serviceUUID$delegate = Delegates.INSTANCE.notNull();
  
  public BLEDiscoverer(Context paramContext, String paramString) {
    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(paramContext);
    Intrinsics.checkExpressionValueIsNotNull(localBroadcastManager, "LocalBroadcastManager.getInstance(context)");
    this.localBroadcastManager = localBroadcastManager;
    setContext(paramContext);
    setServiceUUID(new ParcelUuid(UUID.fromString(paramString)));
    this.bluetoothAdapter$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, new BLEDiscoverer$bluetoothAdapter$2(paramContext));
    this.mDiscoveryReceiver = new BLEDiscoverer$mDiscoveryReceiver$1();
  }
  
  private final BluetoothAdapter getBluetoothAdapter() {
    Lazy lazy = this.bluetoothAdapter$delegate;
    KProperty kProperty = $$delegatedProperties[2];
    return (BluetoothAdapter)lazy.getValue();
  }
  
  private final Context getContext() {
    return (Context)this.context$delegate.getValue(this, $$delegatedProperties[1]);
  }
  
  private final ParcelUuid getServiceUUID() {
    return (ParcelUuid)this.serviceUUID$delegate.getValue(this, $$delegatedProperties[0]);
  }
  
  private final void setContext(Context paramContext) {
    this.context$delegate.setValue(this, $$delegatedProperties[1], paramContext);
  }
  
  private final void setServiceUUID(ParcelUuid paramParcelUuid) {
    this.serviceUUID$delegate.setValue(this, $$delegatedProperties[0], paramParcelUuid);
  }
  
  public final void cancelDiscovery() {
    BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();
    if (bluetoothAdapter == null);
    bluetoothAdapter.cancelDiscovery();
    try {
      this.localBroadcastManager.unregisterReceiver(this.mDiscoveryReceiver);
    } finally {
      Exception exception = null;
      CentralLog.Companion companion = CentralLog.Companion;
      String str = this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Already unregistered workReceiver? ");
      stringBuilder.append(exception.getMessage());
    } 
  }
  
  public final void startDiscovery() {
    IntentFilter intentFilter = new IntentFilter("android.bluetooth.device.action.FOUND");
    intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
    this.localBroadcastManager.registerReceiver(this.mDiscoveryReceiver, intentFilter);
    BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();
    if (bluetoothAdapter == null)
      Intrinsics.throwNpe(); 
    bluetoothAdapter.startDiscovery();
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\n\n\000\n\002\030\002\n\002\b\002\020\000\032\n \002*\004\030\0010\0010\001H\n¢\006\002\b\003"}, d2 = {"<anonymous>", "Landroid/bluetooth/BluetoothAdapter;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 1, 16})
  static final class BLEDiscoverer$bluetoothAdapter$2 extends Lambda implements Function0<BluetoothAdapter> {
    BLEDiscoverer$bluetoothAdapter$2(Context param1Context) {
      super(0);
    }
    
    public final BluetoothAdapter invoke() {
      Object object = this.$context.getSystemService("bluetooth");
      if (object != null)
        return ((BluetoothManager)object).getAdapter(); 
      throw new TypeCastException("null cannot be cast to non-null type android.bluetooth.BluetoothManager");
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000%\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\030\020\002\032\0020\0032\006\020\004\032\0020\0052\006\020\006\032\0020\007H\026J\022\020\b\032\0020\0032\b\020\t\032\004\030\0010\nH\002¨\006\013"}, d2 = {"ca/albertahealthservices/contacttracing/bluetooth/BLEDiscoverer$mDiscoveryReceiver$1", "Landroid/content/BroadcastReceiver;", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "processScanResult", "scanResult", "Landroid/bluetooth/le/ScanResult;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class BLEDiscoverer$mDiscoveryReceiver$1 extends BroadcastReceiver {
    private final void processScanResult(ScanResult param1ScanResult) {
      if (param1ScanResult != null) {
        param1ScanResult.getDevice();
        param1ScanResult.getRssi();
        Integer integer = (Integer)null;
        if (Build.VERSION.SDK_INT >= 26)
          Integer.valueOf(param1ScanResult.getTxPower()).intValue(); 
      } 
    }
    
    public void onReceive(Context param1Context, Intent param1Intent) {
      Intrinsics.checkParameterIsNotNull(param1Context, "context");
      Intrinsics.checkParameterIsNotNull(param1Intent, "intent");
      String str = param1Intent.getAction();
      if (str != null) {
        BluetoothDevice bluetoothDevice;
        int i = str.hashCode();
        if (i != -1780914469) {
          if (i != 6759640) {
            if (i == 1167529923 && str.equals("android.bluetooth.device.action.FOUND")) {
              Parcelable parcelable = param1Intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
              Intrinsics.checkExpressionValueIsNotNull(parcelable, "intent.getParcelableExtr…toothDevice.EXTRA_DEVICE)");
              bluetoothDevice = (BluetoothDevice)parcelable;
              i = param1Intent.getShortExtra("android.bluetooth.device.extra.RSSI", -32768);
              CentralLog.Companion companion = CentralLog.Companion;
              String str1 = BLEDiscoverer.this.TAG;
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Scanned Device address: ");
              stringBuilder.append(bluetoothDevice.getAddress());
              stringBuilder.append(" @ ");
              stringBuilder.append(i);
              companion.i(str1, stringBuilder.toString());
              if (bluetoothDevice.getUuids() == null) {
                companion = CentralLog.Companion;
                str1 = BLEDiscoverer.this.TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Nope. No uuids cached for address: ");
                stringBuilder.append(bluetoothDevice.getAddress());
                companion.w(str1, stringBuilder.toString());
              } 
            } 
          } else if (bluetoothDevice.equals("android.bluetooth.adapter.action.DISCOVERY_STARTED")) {
            CentralLog.Companion.i(BLEDiscoverer.this.TAG, "Discovery started");
          } 
        } else if (bluetoothDevice.equals("android.bluetooth.adapter.action.DISCOVERY_FINISHED")) {
          CentralLog.Companion.i(BLEDiscoverer.this.TAG, "Discovery ended");
        } 
      } 
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/bluetooth/BLEDiscoverer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */