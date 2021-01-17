package ca.albertahealthservices.contacttracing.bluetooth.gatt;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import ca.albertahealthservices.contacttracing.Utils;
import ca.albertahealthservices.contacttracing.idmanager.TempIDManager;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.protocol.BlueTrace;
import ca.albertahealthservices.contacttracing.protocol.BlueTraceProtocol;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty;
import kotlin.text.Charsets;

@Metadata(bv = {1, 0, 3}, d1 = {"\000K\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\003\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\n\n\002\b\002\n\002\030\002\n\002\b\007\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\002*\001\032\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006J\016\020#\032\0020$2\006\020%\032\0020&J\006\020'\032\0020(J\006\020)\032\0020$R\016\020\007\032\0020\005XD¢\006\002\n\000R\034\020\b\032\004\030\0010\tX\016¢\006\016\n\000\032\004\b\n\020\013\"\004\b\f\020\rR+\020\020\032\0020\0172\006\020\016\032\0020\0178B@BX\002¢\006\022\n\004\b\025\020\026\032\004\b\021\020\022\"\004\b\023\020\024R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\027\020\030R\020\020\031\032\0020\032X\004¢\006\004\n\002\020\033R+\020\035\032\0020\0342\006\020\016\032\0020\0348B@BX\002¢\006\022\n\004\b\"\020\026\032\004\b\036\020\037\"\004\b \020!¨\006*"}, d2 = {"Lca/albertahealthservices/contacttracing/bluetooth/gatt/GattServer;", "", "context", "Landroid/content/Context;", "serviceUUIDString", "", "(Landroid/content/Context;Ljava/lang/String;)V", "TAG", "bluetoothGattServer", "Landroid/bluetooth/BluetoothGattServer;", "getBluetoothGattServer", "()Landroid/bluetooth/BluetoothGattServer;", "setBluetoothGattServer", "(Landroid/bluetooth/BluetoothGattServer;)V", "<set-?>", "Landroid/bluetooth/BluetoothManager;", "bluetoothManager", "getBluetoothManager", "()Landroid/bluetooth/BluetoothManager;", "setBluetoothManager", "(Landroid/bluetooth/BluetoothManager;)V", "bluetoothManager$delegate", "Lkotlin/properties/ReadWriteProperty;", "getContext", "()Landroid/content/Context;", "gattServerCallback", "ca/albertahealthservices/contacttracing/bluetooth/gatt/GattServer$gattServerCallback$1", "Lca/albertahealthservices/contacttracing/bluetooth/gatt/GattServer$gattServerCallback$1;", "Ljava/util/UUID;", "serviceUUID", "getServiceUUID", "()Ljava/util/UUID;", "setServiceUUID", "(Ljava/util/UUID;)V", "serviceUUID$delegate", "addService", "", "service", "Lca/albertahealthservices/contacttracing/bluetooth/gatt/GattService;", "startServer", "", "stop", "app_release"}, k = 1, mv = {1, 1, 16})
public final class GattServer {
  private final String TAG;
  
  private BluetoothGattServer bluetoothGattServer;
  
  private final ReadWriteProperty bluetoothManager$delegate;
  
  private final Context context;
  
  private final GattServer$gattServerCallback$1 gattServerCallback;
  
  private final ReadWriteProperty serviceUUID$delegate;
  
  public GattServer(Context paramContext, String paramString) {
    this.context = paramContext;
    this.TAG = "GattServer";
    this.bluetoothManager$delegate = Delegates.INSTANCE.notNull();
    this.serviceUUID$delegate = Delegates.INSTANCE.notNull();
    Object object = this.context.getSystemService("bluetooth");
    if (object != null) {
      setBluetoothManager((BluetoothManager)object);
      object = UUID.fromString(paramString);
      Intrinsics.checkExpressionValueIsNotNull(object, "UUID.fromString(serviceUUIDString)");
      setServiceUUID((UUID)object);
      this.gattServerCallback = new GattServer$gattServerCallback$1();
      return;
    } 
    throw new TypeCastException("null cannot be cast to non-null type android.bluetooth.BluetoothManager");
  }
  
  private final BluetoothManager getBluetoothManager() {
    return (BluetoothManager)this.bluetoothManager$delegate.getValue(this, $$delegatedProperties[0]);
  }
  
  private final UUID getServiceUUID() {
    return (UUID)this.serviceUUID$delegate.getValue(this, $$delegatedProperties[1]);
  }
  
  private final void setBluetoothManager(BluetoothManager paramBluetoothManager) {
    this.bluetoothManager$delegate.setValue(this, $$delegatedProperties[0], paramBluetoothManager);
  }
  
  private final void setServiceUUID(UUID paramUUID) {
    this.serviceUUID$delegate.setValue(this, $$delegatedProperties[1], paramUUID);
  }
  
  public final void addService(GattService paramGattService) {
    Intrinsics.checkParameterIsNotNull(paramGattService, "service");
    BluetoothGattServer bluetoothGattServer = this.bluetoothGattServer;
    if (bluetoothGattServer != null)
      bluetoothGattServer.addService(paramGattService.getGattService()); 
  }
  
  public final BluetoothGattServer getBluetoothGattServer() {
    return this.bluetoothGattServer;
  }
  
  public final Context getContext() {
    return this.context;
  }
  
  public final void setBluetoothGattServer(BluetoothGattServer paramBluetoothGattServer) {
    this.bluetoothGattServer = paramBluetoothGattServer;
  }
  
  public final boolean startServer() {
    BluetoothGattServer bluetoothGattServer = getBluetoothManager().openGattServer(this.context, this.gattServerCallback);
    this.bluetoothGattServer = bluetoothGattServer;
    if (bluetoothGattServer != null) {
      bluetoothGattServer.clearServices();
      return true;
    } 
    return false;
  }
  
  public final void stop() {
    try {
    
    } finally {
      Exception exception = null;
      CentralLog.Companion companion = CentralLog.Companion;
      String str = this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("GATT server can't be closed elegantly ");
      stringBuilder.append(exception.getLocalizedMessage());
    } 
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000G\n\000\n\002\030\002\n\000\n\002\020%\n\002\020\016\n\002\030\002\n\002\b\003\n\002\020\022\n\002\b\004\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\t*\001\000\b\n\030\0002\0020\001J,\020\r\032\0020\0162\b\020\017\032\004\030\0010\0202\006\020\021\032\0020\0222\006\020\023\032\0020\0222\b\020\024\032\004\030\0010\025H\026JD\020\026\032\0020\0162\b\020\017\032\004\030\0010\0202\006\020\021\032\0020\0222\006\020\024\032\0020\0252\006\020\027\032\0020\0302\006\020\031\032\0020\0302\006\020\023\032\0020\0222\b\020\032\032\004\030\0010\tH\026J\"\020\033\032\0020\0162\b\020\017\032\004\030\0010\0202\006\020\034\032\0020\0222\006\020\035\032\0020\022H\026J \020\036\032\0020\0162\006\020\017\032\0020\0202\006\020\021\032\0020\0222\006\020\037\032\0020\030H\026J\016\020 \032\0020\0162\006\020\017\032\0020\020R\035\020\002\032\016\022\004\022\0020\004\022\004\022\0020\0050\003¢\006\b\n\000\032\004\b\006\020\007R\035\020\b\032\016\022\004\022\0020\004\022\004\022\0020\t0\003¢\006\b\n\000\032\004\b\n\020\007R\035\020\013\032\016\022\004\022\0020\004\022\004\022\0020\t0\003¢\006\b\n\000\032\004\b\f\020\007¨\006!"}, d2 = {"ca/albertahealthservices/contacttracing/bluetooth/gatt/GattServer$gattServerCallback$1", "Landroid/bluetooth/BluetoothGattServerCallback;", "deviceCharacteristicMap", "", "", "Ljava/util/UUID;", "getDeviceCharacteristicMap", "()Ljava/util/Map;", "readPayloadMap", "", "getReadPayloadMap", "writeDataPayload", "getWriteDataPayload", "onCharacteristicReadRequest", "", "device", "Landroid/bluetooth/BluetoothDevice;", "requestId", "", "offset", "characteristic", "Landroid/bluetooth/BluetoothGattCharacteristic;", "onCharacteristicWriteRequest", "preparedWrite", "", "responseNeeded", "value", "onConnectionStateChange", "status", "newState", "onExecuteWrite", "execute", "saveDataReceived", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class GattServer$gattServerCallback$1 extends BluetoothGattServerCallback {
    private final Map<String, UUID> deviceCharacteristicMap = new HashMap<>();
    
    private final Map<String, byte[]> readPayloadMap = (Map)new HashMap<>();
    
    private final Map<String, byte[]> writeDataPayload = (Map)new HashMap<>();
    
    public final Map<String, UUID> getDeviceCharacteristicMap() {
      return this.deviceCharacteristicMap;
    }
    
    public final Map<String, byte[]> getReadPayloadMap() {
      return this.readPayloadMap;
    }
    
    public final Map<String, byte[]> getWriteDataPayload() {
      return this.writeDataPayload;
    }
    
    public void onCharacteristicReadRequest(BluetoothDevice param1BluetoothDevice, int param1Int1, int param1Int2, BluetoothGattCharacteristic param1BluetoothGattCharacteristic) {
      if (param1BluetoothDevice == null)
        CentralLog.Companion.w(GattServer.this.TAG, "No device"); 
      if (param1BluetoothDevice != null) {
        CentralLog.Companion companion = CentralLog.Companion;
        String str = GattServer.this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onCharacteristicReadRequest from ");
        stringBuilder.append(param1BluetoothDevice.getAddress());
        companion.i(str, stringBuilder.toString());
        BlueTrace blueTrace = BlueTrace.INSTANCE;
        if (param1BluetoothGattCharacteristic != null) {
          UUID uUID = param1BluetoothGattCharacteristic.getUuid();
        } else {
          stringBuilder = null;
        } 
        if (blueTrace.supportsCharUUID((UUID)stringBuilder)) {
          if (param1BluetoothGattCharacteristic != null) {
            UUID uUID = param1BluetoothGattCharacteristic.getUuid();
            if (uUID != null) {
              BlueTraceProtocol blueTraceProtocol = BlueTrace.INSTANCE.getImplementation(uUID);
              if (TempIDManager.INSTANCE.bmValid(GattServer.this.getContext())) {
                Map<String, byte[]> map = this.readPayloadMap;
                String str1 = param1BluetoothDevice.getAddress();
                Intrinsics.checkExpressionValueIsNotNull(str1, "device.address");
                stringBuilder = (StringBuilder)map.get(str1);
                StringBuilder stringBuilder1 = stringBuilder;
                if (stringBuilder == null) {
                  arrayOfByte = blueTraceProtocol.getPeripheral().prepareReadRequestData(blueTraceProtocol.getVersionInt());
                  map.put(str1, arrayOfByte);
                } 
                byte[] arrayOfByte = arrayOfByte;
                arrayOfByte = ArraysKt.copyOfRange(arrayOfByte, param1Int2, arrayOfByte.length);
                CentralLog.Companion companion1 = CentralLog.Companion;
                str1 = GattServer.this.TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("onCharacteristicReadRequest from ");
                stringBuilder.append(param1BluetoothDevice.getAddress());
                stringBuilder.append(" - ");
                stringBuilder.append(param1Int1);
                stringBuilder.append("- ");
                stringBuilder.append(param1Int2);
                stringBuilder.append(" - ");
                stringBuilder.append(new String(arrayOfByte, Charsets.UTF_8));
                companion1.i(str1, stringBuilder.toString());
                BluetoothGattServer bluetoothGattServer = GattServer.this.getBluetoothGattServer();
                if (bluetoothGattServer != null)
                  bluetoothGattServer.sendResponse(param1BluetoothDevice, param1Int1, 0, 0, arrayOfByte); 
              } else {
                CentralLog.Companion companion1 = CentralLog.Companion;
                String str1 = GattServer.this.TAG;
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append("onCharacteristicReadRequest from ");
                stringBuilder1.append(param1BluetoothDevice.getAddress());
                stringBuilder1.append(" - ");
                stringBuilder1.append(param1Int1);
                stringBuilder1.append("- ");
                stringBuilder1.append(param1Int2);
                stringBuilder1.append(" - BM Expired");
                companion1.i(str1, stringBuilder1.toString());
                BluetoothGattServer bluetoothGattServer = GattServer.this.getBluetoothGattServer();
                if (bluetoothGattServer != null)
                  bluetoothGattServer.sendResponse(param1BluetoothDevice, param1Int1, 257, 0, new byte[0]); 
              } 
            } 
          } 
        } else {
          CentralLog.Companion companion1 = CentralLog.Companion;
          String str1 = GattServer.this.TAG;
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("unsupported characteristic UUID from ");
          stringBuilder1.append(param1BluetoothDevice.getAddress());
          companion1.i(str1, stringBuilder1.toString());
          BluetoothGattServer bluetoothGattServer = GattServer.this.getBluetoothGattServer();
          if (bluetoothGattServer != null)
            bluetoothGattServer.sendResponse(param1BluetoothDevice, param1Int1, 257, 0, null); 
        } 
      } 
    }
    
    public void onCharacteristicWriteRequest(BluetoothDevice param1BluetoothDevice, int param1Int1, BluetoothGattCharacteristic param1BluetoothGattCharacteristic, boolean param1Boolean1, boolean param1Boolean2, int param1Int2, byte[] param1ArrayOfbyte) {
      Intrinsics.checkParameterIsNotNull(param1BluetoothGattCharacteristic, "characteristic");
      if (param1BluetoothDevice == null)
        CentralLog.Companion.e(GattServer.this.TAG, "Write stopped - no device"); 
      if (param1BluetoothDevice != null) {
        CentralLog.Companion companion = CentralLog.Companion;
        String str = GattServer.this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onCharacteristicWriteRequest - ");
        stringBuilder.append(param1BluetoothDevice.getAddress());
        stringBuilder.append(" - preparedWrite: ");
        stringBuilder.append(param1Boolean1);
        companion.i(str, stringBuilder.toString());
        companion = CentralLog.Companion;
        str = GattServer.this.TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("onCharacteristicWriteRequest from ");
        stringBuilder.append(param1BluetoothDevice.getAddress());
        stringBuilder.append(" - ");
        stringBuilder.append(param1Int1);
        stringBuilder.append(" - ");
        stringBuilder.append(param1Int2);
        companion.i(str, stringBuilder.toString());
        if (BlueTrace.INSTANCE.supportsCharUUID(param1BluetoothGattCharacteristic.getUuid())) {
          String str1;
          Map<String, UUID> map = this.deviceCharacteristicMap;
          String str2 = param1BluetoothDevice.getAddress();
          Intrinsics.checkExpressionValueIsNotNull(str2, "device.address");
          UUID uUID = param1BluetoothGattCharacteristic.getUuid();
          Intrinsics.checkExpressionValueIsNotNull(uUID, "characteristic.uuid");
          map.put(str2, uUID);
          if (param1ArrayOfbyte != null) {
            str1 = new String(param1ArrayOfbyte, Charsets.UTF_8);
          } else {
            str1 = "";
          } 
          CentralLog.Companion companion1 = CentralLog.Companion;
          String str3 = GattServer.this.TAG;
          stringBuilder = new StringBuilder();
          stringBuilder.append("onCharacteristicWriteRequest from ");
          stringBuilder.append(param1BluetoothDevice.getAddress());
          stringBuilder.append(" - ");
          stringBuilder.append(str1);
          companion1.i(str3, stringBuilder.toString());
          if (param1ArrayOfbyte != null) {
            byte[] arrayOfByte2 = this.writeDataPayload.get(param1BluetoothDevice.getAddress());
            byte[] arrayOfByte1 = arrayOfByte2;
            if (arrayOfByte2 == null)
              arrayOfByte1 = new byte[0]; 
            arrayOfByte1 = ArraysKt.plus(arrayOfByte1, param1ArrayOfbyte);
            Map<String, byte[]> map1 = this.writeDataPayload;
            String str4 = param1BluetoothDevice.getAddress();
            Intrinsics.checkExpressionValueIsNotNull(str4, "device.address");
            map1.put(str4, arrayOfByte1);
            CentralLog.Companion companion2 = CentralLog.Companion;
            String str5 = GattServer.this.TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Accumulated characteristic: ");
            stringBuilder.append(new String(arrayOfByte1, Charsets.UTF_8));
            companion2.i(str5, stringBuilder.toString());
            if (param1Boolean1 && param1Boolean2) {
              CentralLog.Companion companion3 = CentralLog.Companion;
              String str6 = GattServer.this.TAG;
              stringBuilder = new StringBuilder();
              stringBuilder.append("Sending response offset: ");
              stringBuilder.append(arrayOfByte1.length);
              companion3.i(str6, stringBuilder.toString());
              BluetoothGattServer bluetoothGattServer = GattServer.this.getBluetoothGattServer();
              if (bluetoothGattServer != null)
                bluetoothGattServer.sendResponse(param1BluetoothDevice, param1Int1, 0, arrayOfByte1.length, param1ArrayOfbyte); 
            } 
            if (!param1Boolean1) {
              CentralLog.Companion companion3 = CentralLog.Companion;
              str5 = GattServer.this.TAG;
              StringBuilder stringBuilder1 = new StringBuilder();
              stringBuilder1.append("onCharacteristicWriteRequest - ");
              stringBuilder1.append(param1BluetoothDevice.getAddress());
              stringBuilder1.append(" - preparedWrite: ");
              stringBuilder1.append(param1Boolean1);
              companion3.i(str5, stringBuilder1.toString());
              saveDataReceived(param1BluetoothDevice);
              if (param1Boolean2) {
                BluetoothGattServer bluetoothGattServer = GattServer.this.getBluetoothGattServer();
                if (bluetoothGattServer != null)
                  bluetoothGattServer.sendResponse(param1BluetoothDevice, param1Int1, 0, 0, null); 
              } 
            } 
          } 
        } else {
          CentralLog.Companion companion1 = CentralLog.Companion;
          String str1 = GattServer.this.TAG;
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("unsupported characteristic UUID from ");
          stringBuilder1.append(param1BluetoothDevice.getAddress());
          companion1.i(str1, stringBuilder1.toString());
          if (param1Boolean2) {
            BluetoothGattServer bluetoothGattServer = GattServer.this.getBluetoothGattServer();
            if (bluetoothGattServer != null)
              bluetoothGattServer.sendResponse(param1BluetoothDevice, param1Int1, 257, 0, null); 
          } 
        } 
      } 
    }
    
    public void onConnectionStateChange(BluetoothDevice param1BluetoothDevice, int param1Int1, int param1Int2) {
      CentralLog.Companion companion;
      String str1 = null;
      StringBuilder stringBuilder = null;
      String str2 = null;
      if (param1Int2 != 0) {
        if (param1Int2 != 2) {
          CentralLog.Companion companion1 = CentralLog.Companion;
          str1 = GattServer.this.TAG;
          stringBuilder = new StringBuilder();
          stringBuilder.append("Connection status: ");
          stringBuilder.append(param1Int2);
          stringBuilder.append(" - ");
          if (param1BluetoothDevice != null)
            str2 = param1BluetoothDevice.getAddress(); 
          stringBuilder.append(str2);
          companion1.i(str1, stringBuilder.toString());
        } else {
          companion = CentralLog.Companion;
          String str = GattServer.this.TAG;
          StringBuilder stringBuilder1 = new StringBuilder();
          str2 = str1;
          if (param1BluetoothDevice != null)
            str2 = param1BluetoothDevice.getAddress(); 
          stringBuilder1.append(str2);
          stringBuilder1.append(" Connected to local GATT server");
          companion.i(str, stringBuilder1.toString());
        } 
      } else {
        String str3;
        CentralLog.Companion companion1 = CentralLog.Companion;
        String str4 = GattServer.this.TAG;
        StringBuilder stringBuilder1 = new StringBuilder();
        CentralLog.Companion companion2 = companion;
        if (param1BluetoothDevice != null)
          str3 = param1BluetoothDevice.getAddress(); 
        stringBuilder1.append(str3);
        stringBuilder1.append(" Disconnected from local GATT server.");
        companion1.i(str4, stringBuilder1.toString());
      } 
    }
    
    public void onExecuteWrite(BluetoothDevice param1BluetoothDevice, int param1Int, boolean param1Boolean) {
      Intrinsics.checkParameterIsNotNull(param1BluetoothDevice, "device");
      super.onExecuteWrite(param1BluetoothDevice, param1Int, param1Boolean);
      byte[] arrayOfByte = this.writeDataPayload.get(param1BluetoothDevice.getAddress());
      if (arrayOfByte != null) {
        CentralLog.Companion companion = CentralLog.Companion;
        String str = GattServer.this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onExecuteWrite - ");
        stringBuilder.append(param1Int);
        stringBuilder.append("- ");
        stringBuilder.append(param1BluetoothDevice.getAddress());
        stringBuilder.append(" - ");
        stringBuilder.append(new String(arrayOfByte, Charsets.UTF_8));
        companion.i(str, stringBuilder.toString());
        saveDataReceived(param1BluetoothDevice);
        BluetoothGattServer bluetoothGattServer = GattServer.this.getBluetoothGattServer();
        if (bluetoothGattServer != null)
          bluetoothGattServer.sendResponse(param1BluetoothDevice, param1Int, 0, 0, null); 
      } else {
        BluetoothGattServer bluetoothGattServer = GattServer.this.getBluetoothGattServer();
        if (bluetoothGattServer != null)
          bluetoothGattServer.sendResponse(param1BluetoothDevice, param1Int, 257, 0, null); 
      } 
    }
    
    public final void saveDataReceived(BluetoothDevice param1BluetoothDevice) {
      Intrinsics.checkParameterIsNotNull(param1BluetoothDevice, "device");
      byte[] arrayOfByte = this.writeDataPayload.get(param1BluetoothDevice.getAddress());
      UUID uUID = this.deviceCharacteristicMap.get(param1BluetoothDevice.getAddress());
      if (uUID != null && arrayOfByte != null) {
        try {
        
        } finally {
          arrayOfByte = null;
          CentralLog.Companion companion = CentralLog.Companion;
          String str1 = GattServer.this.TAG;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Failed to process write payload - ");
          stringBuilder.append(arrayOfByte.getMessage());
        } 
        Utils utils = Utils.INSTANCE;
        Context context = GattServer.this.getContext();
        String str = param1BluetoothDevice.getAddress();
        Intrinsics.checkExpressionValueIsNotNull(str, "device.address");
        utils.broadcastDeviceProcessed(context, str);
        this.writeDataPayload.remove(param1BluetoothDevice.getAddress());
        this.readPayloadMap.remove(param1BluetoothDevice.getAddress());
        UUID uUID1 = this.deviceCharacteristicMap.remove(param1BluetoothDevice.getAddress());
      } 
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/bluetooth/gatt/GattServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */