package ca.albertahealthservices.contacttracing.streetpass;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import ca.albertahealthservices.contacttracing.Utils;
import ca.albertahealthservices.contacttracing.bluetooth.BLEScanner;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.services.BluetoothMonitoringService;
import ca.albertahealthservices.contacttracing.status.Status;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty;
import kotlin.text.Charsets;

@Metadata(bv = {1, 0, 3}, d1 = {"\000J\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020\t\n\002\b\n\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\007\n\002\020\b\n\002\b\005\n\002\020\013\n\000\n\002\020\002\n\002\b\003\030\0002\0020\001:\001)B\035\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007¢\006\002\020\bJ\006\020$\032\0020%J\006\020&\032\0020'J\006\020(\032\0020'R\016\020\t\032\0020\005XD¢\006\002\n\000R+\020\002\032\0020\0032\006\020\n\032\0020\0038B@BX\002¢\006\022\n\004\b\017\020\020\032\004\b\013\020\f\"\004\b\r\020\016R\016\020\021\032\0020\022X\016¢\006\002\n\000R\025\020\023\032\0060\024R\0020\000¢\006\b\n\000\032\004\b\025\020\026R\016\020\006\032\0020\007X\004¢\006\002\n\000R+\020\030\032\0020\0272\006\020\n\032\0020\0278B@BX\002¢\006\022\n\004\b\035\020\020\032\004\b\031\020\032\"\004\b\033\020\034R\032\020\036\032\0020\037X\016¢\006\016\n\000\032\004\b \020!\"\004\b\"\020#¨\006*"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/StreetPassScanner;", "", "context", "Landroid/content/Context;", "serviceUUIDString", "", "scanDurationInMillis", "", "(Landroid/content/Context;Ljava/lang/String;J)V", "TAG", "<set-?>", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "context$delegate", "Lkotlin/properties/ReadWriteProperty;", "handler", "Landroid/os/Handler;", "scanCallback", "Lca/albertahealthservices/contacttracing/streetpass/StreetPassScanner$BleScanCallback;", "getScanCallback", "()Lca/albertahealthservices/contacttracing/streetpass/StreetPassScanner$BleScanCallback;", "Lca/albertahealthservices/contacttracing/bluetooth/BLEScanner;", "scanner", "getScanner", "()Lca/albertahealthservices/contacttracing/bluetooth/BLEScanner;", "setScanner", "(Lca/albertahealthservices/contacttracing/bluetooth/BLEScanner;)V", "scanner$delegate", "scannerCount", "", "getScannerCount", "()I", "setScannerCount", "(I)V", "isScanning", "", "startScan", "", "stopScan", "BleScanCallback", "app_release"}, k = 1, mv = {1, 1, 16})
public final class StreetPassScanner {
  private final String TAG;
  
  private final ReadWriteProperty context$delegate;
  
  private Handler handler;
  
  private final BleScanCallback scanCallback;
  
  private final long scanDurationInMillis;
  
  private final ReadWriteProperty scanner$delegate;
  
  private int scannerCount;
  
  public StreetPassScanner(Context paramContext, String paramString, long paramLong) {
    this.scanDurationInMillis = paramLong;
    this.scanner$delegate = Delegates.INSTANCE.notNull();
    this.context$delegate = Delegates.INSTANCE.notNull();
    this.TAG = "StreetPassScanner";
    this.handler = new Handler();
    this.scanCallback = new BleScanCallback();
    setScanner(new BLEScanner(paramContext, paramString, 0L));
    setContext(paramContext);
  }
  
  private final Context getContext() {
    return (Context)this.context$delegate.getValue(this, $$delegatedProperties[1]);
  }
  
  private final BLEScanner getScanner() {
    return (BLEScanner)this.scanner$delegate.getValue(this, $$delegatedProperties[0]);
  }
  
  private final void setContext(Context paramContext) {
    this.context$delegate.setValue(this, $$delegatedProperties[1], paramContext);
  }
  
  private final void setScanner(BLEScanner paramBLEScanner) {
    this.scanner$delegate.setValue(this, $$delegatedProperties[0], paramBLEScanner);
  }
  
  public final BleScanCallback getScanCallback() {
    return this.scanCallback;
  }
  
  public final int getScannerCount() {
    return this.scannerCount;
  }
  
  public final boolean isScanning() {
    boolean bool;
    if (this.scannerCount > 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final void setScannerCount(int paramInt) {
    this.scannerCount = paramInt;
  }
  
  public final void startScan() {
    Status status = new Status("Scanning Started");
    Utils.INSTANCE.broadcastStatusReceived(getContext(), status);
    getScanner().startScan(this.scanCallback);
    this.scannerCount++;
    if (!BluetoothMonitoringService.Companion.getInfiniteScanning())
      this.handler.postDelayed(new StreetPassScanner$startScan$1(), this.scanDurationInMillis); 
    CentralLog.Companion.d(this.TAG, "scanning started");
  }
  
  public final void stopScan() {
    if (this.scannerCount > 0) {
      Status status = new Status("Scanning Stopped");
      Utils.INSTANCE.broadcastStatusReceived(getContext(), status);
      this.scannerCount--;
      getScanner().stopScan();
    } 
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000(\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020\002\n\000\n\002\020\b\n\002\b\003\n\002\030\002\n\002\b\003\b\004\030\0002\0020\001B\005¢\006\002\020\002J\020\020\005\032\0020\0062\006\020\007\032\0020\bH\026J\032\020\t\032\0020\0062\006\020\n\032\0020\b2\b\020\013\032\004\030\0010\fH\026J\022\020\r\032\0020\0062\b\020\016\032\004\030\0010\fH\002R\016\020\003\032\0020\004XD¢\006\002\n\000¨\006\017"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/StreetPassScanner$BleScanCallback;", "Landroid/bluetooth/le/ScanCallback;", "(Lca/albertahealthservices/contacttracing/streetpass/StreetPassScanner;)V", "TAG", "", "onScanFailed", "", "errorCode", "", "onScanResult", "callbackType", "result", "Landroid/bluetooth/le/ScanResult;", "processScanResult", "scanResult", "app_release"}, k = 1, mv = {1, 1, 16})
  public final class BleScanCallback extends ScanCallback {
    private final String TAG = "BleScanCallback";
    
    private final void processScanResult(ScanResult param1ScanResult) {
      byte[] arrayOfByte;
      BluetoothDevice bluetoothDevice;
      int i;
      if (param1ScanResult != null) {
        bluetoothDevice = param1ScanResult.getDevice();
        i = param1ScanResult.getRssi();
        Integer integer1 = (Integer)null;
        Integer integer2 = integer1;
        if (Build.VERSION.SDK_INT >= 26) {
          integer2 = Integer.valueOf(param1ScanResult.getTxPower());
          if (integer2.intValue() == 127)
            integer2 = integer1; 
        } 
        ScanRecord scanRecord = param1ScanResult.getScanRecord();
        if (scanRecord != null) {
          byte[] arrayOfByte1 = scanRecord.getManufacturerSpecificData(1023);
          if (arrayOfByte1 != null) {
            String str3 = new String(arrayOfByte1, Charsets.UTF_8);
            connectablePeripheral = new ConnectablePeripheral(str3, integer2, i);
            CentralLog.Companion companion1 = CentralLog.Companion;
            String str4 = this.TAG;
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("Scanned: ");
            stringBuilder1.append(str3);
            stringBuilder1.append(" - ");
            Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice, "device");
            stringBuilder1.append(bluetoothDevice.getAddress());
            companion1.i(str4, stringBuilder1.toString());
            Utils.INSTANCE.broadcastDeviceScanned(StreetPassScanner.this.getContext(), bluetoothDevice, connectablePeripheral);
            return;
          } 
        } 
        arrayOfByte = "N.A".getBytes(Charsets.UTF_8);
        Intrinsics.checkExpressionValueIsNotNull(arrayOfByte, "(this as java.lang.String).getBytes(charset)");
      } else {
        return;
      } 
      String str1 = new String(arrayOfByte, Charsets.UTF_8);
      ConnectablePeripheral connectablePeripheral = new ConnectablePeripheral(str1, (Integer)connectablePeripheral, i);
      CentralLog.Companion companion = CentralLog.Companion;
      String str2 = this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Scanned: ");
      stringBuilder.append(str1);
      stringBuilder.append(" - ");
      Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice, "device");
      stringBuilder.append(bluetoothDevice.getAddress());
      companion.i(str2, stringBuilder.toString());
      Utils.INSTANCE.broadcastDeviceScanned(StreetPassScanner.this.getContext(), bluetoothDevice, connectablePeripheral);
    }
    
    public void onScanFailed(int param1Int) {
      String str1;
      super.onScanFailed(param1Int);
      if (param1Int != 1) {
        if (param1Int != 2) {
          if (param1Int != 3) {
            if (param1Int != 4) {
              StringBuilder stringBuilder1 = new StringBuilder();
              stringBuilder1.append(param1Int);
              stringBuilder1.append(" - UNDOCUMENTED");
              str1 = stringBuilder1.toString();
            } else {
              StringBuilder stringBuilder1 = new StringBuilder();
              stringBuilder1.append(param1Int);
              stringBuilder1.append(" - SCAN_FAILED_FEATURE_UNSUPPORTED");
              str1 = stringBuilder1.toString();
            } 
          } else {
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append(param1Int);
            stringBuilder1.append(" - SCAN_FAILED_INTERNAL_ERROR");
            str1 = stringBuilder1.toString();
          } 
        } else {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append(param1Int);
          stringBuilder1.append(" - SCAN_FAILED_APPLICATION_REGISTRATION_FAILED");
          str1 = stringBuilder1.toString();
        } 
      } else {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(param1Int);
        stringBuilder1.append(" - SCAN_FAILED_ALREADY_STARTED");
        str1 = stringBuilder1.toString();
      } 
      CentralLog.Companion companion = CentralLog.Companion;
      String str2 = this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("BT Scan failed: ");
      stringBuilder.append(str1);
      companion.e(str2, stringBuilder.toString());
      if (StreetPassScanner.this.getScannerCount() > 0) {
        StreetPassScanner streetPassScanner = StreetPassScanner.this;
        streetPassScanner.setScannerCount(streetPassScanner.getScannerCount() - 1);
      } 
    }
    
    public void onScanResult(int param1Int, ScanResult param1ScanResult) {
      super.onScanResult(param1Int, param1ScanResult);
      processScanResult(param1ScanResult);
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\b\n\000\n\002\020\002\n\000\020\000\032\0020\001H\n¢\006\002\b\002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 1, 16})
  static final class StreetPassScanner$startScan$1 implements Runnable {
    public final void run() {
      StreetPassScanner.this.stopScan();
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/StreetPassScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */