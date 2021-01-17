package ca.albertahealthservices.contacttracing.bluetooth.gatt;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
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

@Metadata(bv = {1, 0, 3}, d1 = {"\0000\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\b\n\002\030\002\n\002\b\002\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006R\016\020\007\032\0020\bX\016¢\006\002\n\000R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\t\020\nR+\020\r\032\0020\f2\006\020\013\032\0020\f8F@FX\002¢\006\022\n\004\b\022\020\023\032\004\b\016\020\017\"\004\b\020\020\021R\026\020\024\032\n \026*\004\030\0010\0250\025X\016¢\006\002\n\000¨\006\027"}, d2 = {"Lca/albertahealthservices/contacttracing/bluetooth/gatt/GattService;", "", "context", "Landroid/content/Context;", "serviceUUIDString", "", "(Landroid/content/Context;Ljava/lang/String;)V", "characteristicV2", "Landroid/bluetooth/BluetoothGattCharacteristic;", "getContext", "()Landroid/content/Context;", "<set-?>", "Landroid/bluetooth/BluetoothGattService;", "gattService", "getGattService", "()Landroid/bluetooth/BluetoothGattService;", "setGattService", "(Landroid/bluetooth/BluetoothGattService;)V", "gattService$delegate", "Lkotlin/properties/ReadWriteProperty;", "serviceUUID", "Ljava/util/UUID;", "kotlin.jvm.PlatformType", "app_release"}, k = 1, mv = {1, 1, 16})
public final class GattService {
  private BluetoothGattCharacteristic characteristicV2;
  
  private final Context context;
  
  private final ReadWriteProperty gattService$delegate;
  
  private UUID serviceUUID;
  
  public GattService(Context paramContext, String paramString) {
    this.context = paramContext;
    this.serviceUUID = UUID.fromString(paramString);
    this.gattService$delegate = Delegates.INSTANCE.notNull();
    setGattService(new BluetoothGattService(this.serviceUUID, 0));
    this.characteristicV2 = new BluetoothGattCharacteristic(UUID.fromString("7bee419e-8882-11ea-bc55-0242ac130003"), 10, 17);
    getGattService().addCharacteristic(this.characteristicV2);
  }
  
  public final Context getContext() {
    return this.context;
  }
  
  public final BluetoothGattService getGattService() {
    return (BluetoothGattService)this.gattService$delegate.getValue(this, $$delegatedProperties[0]);
  }
  
  public final void setGattService(BluetoothGattService paramBluetoothGattService) {
    Intrinsics.checkParameterIsNotNull(paramBluetoothGattService, "<set-?>");
    this.gattService$delegate.setValue(this, $$delegatedProperties[0], paramBluetoothGattService);
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/bluetooth/gatt/GattService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */