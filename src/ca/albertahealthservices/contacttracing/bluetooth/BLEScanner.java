package ca.albertahealthservices.contacttracing.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.ParcelUuid;
import ca.albertahealthservices.contacttracing.Utils;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import java.util.ArrayList;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty;

@Metadata(bv = {1, 0, 3}, d1 = {"\0004\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020\t\n\002\b\017\n\002\030\002\n\000\n\002\030\002\n\002\b\007\n\002\020\002\n\002\b\003\030\0002\0020\001B\035\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007¢\006\002\020\bJ\006\020 \032\0020!J\016\020\"\032\0020!2\006\020\026\032\0020\027J\006\020#\032\0020!R\016\020\t\032\0020\005XD¢\006\002\n\000R+\020\002\032\0020\0032\006\020\n\032\0020\0038B@BX\002¢\006\022\n\004\b\017\020\020\032\004\b\013\020\f\"\004\b\r\020\016R+\020\006\032\0020\0072\006\020\n\032\0020\0078B@BX\002¢\006\022\n\004\b\025\020\020\032\004\b\021\020\022\"\004\b\023\020\024R\020\020\026\032\004\030\0010\027X\016¢\006\002\n\000R\020\020\030\032\004\030\0010\031X\016¢\006\002\n\000R+\020\032\032\0020\0052\006\020\n\032\0020\0058B@BX\002¢\006\022\n\004\b\037\020\020\032\004\b\033\020\034\"\004\b\035\020\036¨\006$"}, d2 = {"Lca/albertahealthservices/contacttracing/bluetooth/BLEScanner;", "", "context", "Landroid/content/Context;", "uuid", "", "reportDelay", "", "(Landroid/content/Context;Ljava/lang/String;J)V", "TAG", "<set-?>", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "context$delegate", "Lkotlin/properties/ReadWriteProperty;", "getReportDelay", "()J", "setReportDelay", "(J)V", "reportDelay$delegate", "scanCallback", "Landroid/bluetooth/le/ScanCallback;", "scanner", "Landroid/bluetooth/le/BluetoothLeScanner;", "serviceUUID", "getServiceUUID", "()Ljava/lang/String;", "setServiceUUID", "(Ljava/lang/String;)V", "serviceUUID$delegate", "flush", "", "startScan", "stopScan", "app_release"}, k = 1, mv = {1, 1, 16})
public final class BLEScanner {
  private final String TAG;
  
  private final ReadWriteProperty context$delegate = Delegates.INSTANCE.notNull();
  
  private final ReadWriteProperty reportDelay$delegate = Delegates.INSTANCE.notNull();
  
  private ScanCallback scanCallback;
  
  private BluetoothLeScanner scanner;
  
  private final ReadWriteProperty serviceUUID$delegate = Delegates.INSTANCE.notNull();
  
  public BLEScanner(Context paramContext, String paramString, long paramLong) {
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Intrinsics.checkExpressionValueIsNotNull(bluetoothAdapter, "BluetoothAdapter.getDefaultAdapter()");
    this.scanner = bluetoothAdapter.getBluetoothLeScanner();
    this.TAG = "BLEScanner";
    setServiceUUID(paramString);
    setContext(paramContext);
    setReportDelay(paramLong);
  }
  
  private final Context getContext() {
    return (Context)this.context$delegate.getValue(this, $$delegatedProperties[1]);
  }
  
  private final long getReportDelay() {
    return ((Number)this.reportDelay$delegate.getValue(this, $$delegatedProperties[2])).longValue();
  }
  
  private final String getServiceUUID() {
    return (String)this.serviceUUID$delegate.getValue(this, $$delegatedProperties[0]);
  }
  
  private final void setContext(Context paramContext) {
    this.context$delegate.setValue(this, $$delegatedProperties[1], paramContext);
  }
  
  private final void setReportDelay(long paramLong) {
    this.reportDelay$delegate.setValue(this, $$delegatedProperties[2], Long.valueOf(paramLong));
  }
  
  private final void setServiceUUID(String paramString) {
    this.serviceUUID$delegate.setValue(this, $$delegatedProperties[0], paramString);
  }
  
  public final void flush() {
    ScanCallback scanCallback = this.scanCallback;
    if (scanCallback != null) {
      BluetoothLeScanner bluetoothLeScanner = this.scanner;
      if (bluetoothLeScanner != null)
        bluetoothLeScanner.flushPendingScanResults(scanCallback); 
    } 
  }
  
  public final void startScan(ScanCallback paramScanCallback) {
    Intrinsics.checkParameterIsNotNull(paramScanCallback, "scanCallback");
    ScanFilter scanFilter = (new ScanFilter.Builder()).setServiceUuid(new ParcelUuid(UUID.fromString(getServiceUUID()))).build();
    ArrayList<ScanFilter> arrayList = new ArrayList();
    arrayList.add(scanFilter);
    ScanSettings scanSettings = (new ScanSettings.Builder()).setReportDelay(getReportDelay()).setScanMode(0).build();
    this.scanCallback = paramScanCallback;
    BluetoothLeScanner bluetoothLeScanner = this.scanner;
    if (bluetoothLeScanner == null) {
      BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
      Intrinsics.checkExpressionValueIsNotNull(bluetoothAdapter, "BluetoothAdapter.getDefaultAdapter()");
      bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
    } 
    this.scanner = bluetoothLeScanner;
    if (bluetoothLeScanner != null)
      bluetoothLeScanner.startScan(arrayList, scanSettings, paramScanCallback); 
  }
  
  public final void stopScan() {
    try {
      if (this.scanCallback != null && Utils.INSTANCE.isBluetoothAvailable()) {
        BluetoothLeScanner bluetoothLeScanner = this.scanner;
        if (bluetoothLeScanner != null)
          bluetoothLeScanner.stopScan(this.scanCallback); 
        CentralLog.Companion.d(this.TAG, "scanning stopped");
      } 
    } finally {
      Exception exception = null;
      CentralLog.Companion companion = CentralLog.Companion;
      String str = this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("unable to stop scanning - callback null or bluetooth off? : ");
      stringBuilder.append(exception.getLocalizedMessage());
    } 
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/bluetooth/BLEScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */