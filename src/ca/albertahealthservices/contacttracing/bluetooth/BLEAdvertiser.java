package ca.albertahealthservices.contacttracing.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.os.Handler;
import android.os.ParcelUuid;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.services.BluetoothMonitoringService;
import java.nio.charset.Charset;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

@Metadata(bv = {1, 0, 3}, d1 = {"\000b\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\005\n\002\020\013\n\002\b\004\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\005\n\002\020\002\n\000\n\002\020\t\n\002\b\003\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\016\0201\032\002022\006\0203\032\00204J\016\0205\032\002022\006\0203\032\00204J\006\0206\032\00202R\016\020\005\032\0020\003XD¢\006\002\n\000R\020\020\006\032\004\030\0010\007X\016¢\006\002\n\000R\016\020\b\032\0020\tX\016¢\006\002\n\000R\016\020\n\032\0020\013X\016¢\006\002\n\000R\034\020\f\032\004\030\0010\rX\016¢\006\016\n\000\032\004\b\016\020\017\"\004\b\020\020\021R\032\020\022\032\0020\023X\016¢\006\016\n\000\032\004\b\024\020\025\"\004\b\026\020\027R\032\020\030\032\0020\031X\016¢\006\016\n\000\032\004\b\030\020\032\"\004\b\033\020\034R\021\020\035\032\0020\036¢\006\b\n\000\032\004\b\037\020 R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b!\020\"R\031\020#\032\n %*\004\030\0010$0$¢\006\b\n\000\032\004\b&\020'R\032\020(\032\0020\031X\016¢\006\016\n\000\032\004\b)\020\032\"\004\b*\020\034R\032\020+\032\0020,X\016¢\006\016\n\000\032\004\b-\020.\"\004\b/\0200¨\0067"}, d2 = {"Lca/albertahealthservices/contacttracing/bluetooth/BLEAdvertiser;", "", "serviceUUID", "", "(Ljava/lang/String;)V", "TAG", "advertiser", "Landroid/bluetooth/le/BluetoothLeAdvertiser;", "callback", "Landroid/bluetooth/le/AdvertiseCallback;", "charLength", "", "data", "Landroid/bluetooth/le/AdvertiseData;", "getData", "()Landroid/bluetooth/le/AdvertiseData;", "setData", "(Landroid/bluetooth/le/AdvertiseData;)V", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "setHandler", "(Landroid/os/Handler;)V", "isAdvertising", "", "()Z", "setAdvertising", "(Z)V", "pUuid", "Landroid/os/ParcelUuid;", "getPUuid", "()Landroid/os/ParcelUuid;", "getServiceUUID", "()Ljava/lang/String;", "settings", "Landroid/bluetooth/le/AdvertiseSettings;", "kotlin.jvm.PlatformType", "getSettings", "()Landroid/bluetooth/le/AdvertiseSettings;", "shouldBeAdvertising", "getShouldBeAdvertising", "setShouldBeAdvertising", "stopRunnable", "Ljava/lang/Runnable;", "getStopRunnable", "()Ljava/lang/Runnable;", "setStopRunnable", "(Ljava/lang/Runnable;)V", "startAdvertising", "", "timeoutInMillis", "", "startAdvertisingLegacy", "stopAdvertising", "app_release"}, k = 1, mv = {1, 1, 16})
public final class BLEAdvertiser {
  private final String TAG;
  
  private BluetoothLeAdvertiser advertiser;
  
  private AdvertiseCallback callback;
  
  private int charLength;
  
  private AdvertiseData data;
  
  private Handler handler;
  
  private boolean isAdvertising;
  
  private final ParcelUuid pUuid;
  
  private final String serviceUUID;
  
  private final AdvertiseSettings settings;
  
  private boolean shouldBeAdvertising;
  
  private Runnable stopRunnable;
  
  public BLEAdvertiser(String paramString) {
    this.serviceUUID = paramString;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Intrinsics.checkExpressionValueIsNotNull(bluetoothAdapter, "BluetoothAdapter.getDefaultAdapter()");
    this.advertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
    this.TAG = "BLEAdvertiser";
    this.charLength = 3;
    this.callback = new BLEAdvertiser$callback$1();
    this.pUuid = new ParcelUuid(UUID.fromString(this.serviceUUID));
    this.settings = (new AdvertiseSettings.Builder()).setTxPowerLevel(3).setAdvertiseMode(0).setConnectable(true).setTimeout(0).build();
    this.handler = new Handler();
    this.stopRunnable = new BLEAdvertiser$stopRunnable$1();
  }
  
  public final AdvertiseData getData() {
    return this.data;
  }
  
  public final Handler getHandler() {
    return this.handler;
  }
  
  public final ParcelUuid getPUuid() {
    return this.pUuid;
  }
  
  public final String getServiceUUID() {
    return this.serviceUUID;
  }
  
  public final AdvertiseSettings getSettings() {
    return this.settings;
  }
  
  public final boolean getShouldBeAdvertising() {
    return this.shouldBeAdvertising;
  }
  
  public final Runnable getStopRunnable() {
    return this.stopRunnable;
  }
  
  public final boolean isAdvertising() {
    return this.isAdvertising;
  }
  
  public final void setAdvertising(boolean paramBoolean) {
    this.isAdvertising = paramBoolean;
  }
  
  public final void setData(AdvertiseData paramAdvertiseData) {
    this.data = paramAdvertiseData;
  }
  
  public final void setHandler(Handler paramHandler) {
    Intrinsics.checkParameterIsNotNull(paramHandler, "<set-?>");
    this.handler = paramHandler;
  }
  
  public final void setShouldBeAdvertising(boolean paramBoolean) {
    this.shouldBeAdvertising = paramBoolean;
  }
  
  public final void setStopRunnable(Runnable paramRunnable) {
    Intrinsics.checkParameterIsNotNull(paramRunnable, "<set-?>");
    this.stopRunnable = paramRunnable;
  }
  
  public final void startAdvertising(long paramLong) {
    startAdvertisingLegacy(paramLong);
    this.shouldBeAdvertising = true;
  }
  
  public final void startAdvertisingLegacy(long paramLong) {
    String str = UUID.randomUUID().toString();
    Intrinsics.checkExpressionValueIsNotNull(str, "UUID.randomUUID().toString()");
    int i = str.length();
    int j = this.charLength;
    int k = str.length();
    if (str != null) {
      str = str.substring(i - j, k);
      Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      CentralLog.Companion companion = CentralLog.Companion;
      String str1 = this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unique string: ");
      stringBuilder.append(str);
      companion.d(str1, stringBuilder.toString());
      Charset charset = Charsets.UTF_8;
      if (str != null) {
        byte[] arrayOfByte = str.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(arrayOfByte, "(this as java.lang.String).getBytes(charset)");
        this.data = (new AdvertiseData.Builder()).setIncludeDeviceName(false).setIncludeTxPowerLevel(true).addServiceUuid(this.pUuid).addManufacturerData(1023, arrayOfByte).build();
        try {
        
        } finally {
          arrayOfByte = null;
          CentralLog.Companion companion1 = CentralLog.Companion;
          String str2 = this.TAG;
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Failed to start advertising legacy: ");
          stringBuilder1.append(arrayOfByte.getMessage());
        } 
        if (!BluetoothMonitoringService.Companion.getInfiniteAdvertising()) {
          this.handler.removeCallbacksAndMessages(this.stopRunnable);
          this.handler.postDelayed(this.stopRunnable, paramLong);
        } 
        return;
      } 
      throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    } 
    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
  }
  
  public final void stopAdvertising() {
    try {
    
    } finally {
      Exception exception = null;
      CentralLog.Companion companion = CentralLog.Companion;
      String str = this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Failed to stop advertising: ");
      stringBuilder.append(exception.getMessage());
    } 
    this.shouldBeAdvertising = false;
    this.handler.removeCallbacksAndMessages(null);
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\037\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H\026J\020\020\006\032\0020\0032\006\020\007\032\0020\bH\026¨\006\t"}, d2 = {"ca/albertahealthservices/contacttracing/bluetooth/BLEAdvertiser$callback$1", "Landroid/bluetooth/le/AdvertiseCallback;", "onStartFailure", "", "errorCode", "", "onStartSuccess", "settingsInEffect", "Landroid/bluetooth/le/AdvertiseSettings;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class BLEAdvertiser$callback$1 extends AdvertiseCallback {
    public void onStartFailure(int param1Int) {
      String str1;
      super.onStartFailure(param1Int);
      if (param1Int != 1) {
        if (param1Int != 2) {
          if (param1Int != 3) {
            if (param1Int != 4) {
              if (param1Int != 5) {
                str1 = "UNDOCUMENTED";
              } else {
                BLEAdvertiser.this.setAdvertising(false);
                str1 = "ADVERTISE_FAILED_FEATURE_UNSUPPORTED";
              } 
            } else {
              BLEAdvertiser.this.setAdvertising(false);
              str1 = "ADVERTISE_FAILED_INTERNAL_ERROR";
            } 
          } else {
            BLEAdvertiser.this.setAdvertising(true);
            str1 = "ADVERTISE_FAILED_ALREADY_STARTED";
          } 
        } else {
          BLEAdvertiser.this.setAdvertising(false);
          str1 = "ADVERTISE_FAILED_TOO_MANY_ADVERTISERS";
        } 
      } else {
        BLEAdvertiser.this.setAdvertising(false);
        BLEAdvertiser bLEAdvertiser = BLEAdvertiser.this;
        bLEAdvertiser.charLength = bLEAdvertiser.charLength - 1;
        str1 = "ADVERTISE_FAILED_DATA_TOO_LARGE";
      } 
      CentralLog.Companion companion = CentralLog.Companion;
      String str2 = BLEAdvertiser.this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Advertising onStartFailure: ");
      stringBuilder.append(param1Int);
      stringBuilder.append(" - ");
      stringBuilder.append(str1);
      companion.d(str2, stringBuilder.toString());
    }
    
    public void onStartSuccess(AdvertiseSettings param1AdvertiseSettings) {
      Intrinsics.checkParameterIsNotNull(param1AdvertiseSettings, "settingsInEffect");
      super.onStartSuccess(param1AdvertiseSettings);
      CentralLog.Companion.i(BLEAdvertiser.this.TAG, "Advertising onStartSuccess");
      CentralLog.Companion companion = CentralLog.Companion;
      String str2 = BLEAdvertiser.this.TAG;
      String str1 = param1AdvertiseSettings.toString();
      Intrinsics.checkExpressionValueIsNotNull(str1, "settingsInEffect.toString()");
      companion.i(str2, str1);
      BLEAdvertiser.this.setAdvertising(true);
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\b\n\000\n\002\020\002\n\000\020\000\032\0020\001H\n¢\006\002\b\002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 1, 16})
  static final class BLEAdvertiser$stopRunnable$1 implements Runnable {
    public final void run() {
      CentralLog.Companion.i(BLEAdvertiser.this.TAG, "Advertising stopping as scheduled.");
      BLEAdvertiser.this.stopAdvertising();
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/bluetooth/BLEAdvertiser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */