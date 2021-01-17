package ca.albertahealthservices.contacttracing.streetpass;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000B\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\037\n\002\020\013\n\000\n\002\020\000\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\b\002\b\b\030\0002\0020\001B?\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\005\022\006\020\007\032\0020\b\022\006\020\t\032\0020\n\022\006\020\013\032\0020\003\022\b\020\f\032\004\030\0010\003¢\006\002\020\rJ\t\020\037\032\0020\003HÆ\003J\t\020 \032\0020\005HÆ\003J\t\020!\032\0020\005HÆ\003J\t\020\"\032\0020\bHÆ\003J\t\020#\032\0020\nHÆ\003J\t\020$\032\0020\003HÆ\003J\020\020%\032\004\030\0010\003HÆ\003¢\006\002\020\032JV\020&\032\0020\0002\b\b\002\020\002\032\0020\0032\b\b\002\020\004\032\0020\0052\b\b\002\020\006\032\0020\0052\b\b\002\020\007\032\0020\b2\b\b\002\020\t\032\0020\n2\b\b\002\020\013\032\0020\0032\n\b\002\020\f\032\004\030\0010\003HÆ\001¢\006\002\020'J\t\020(\032\0020\003HÖ\001J\023\020)\032\0020*2\b\020+\032\004\030\0010,HÖ\003J\t\020-\032\0020\003HÖ\001J\b\020.\032\0020\005H\026J\031\020/\032\002002\006\0201\032\002022\006\0203\032\0020\003HÖ\001R\021\020\t\032\0020\n¢\006\b\n\000\032\004\b\016\020\017R\021\020\004\032\0020\005¢\006\b\n\000\032\004\b\020\020\021R\021\020\006\032\0020\005¢\006\b\n\000\032\004\b\022\020\021R\021\020\007\032\0020\b¢\006\b\n\000\032\004\b\023\020\024R\032\020\013\032\0020\003X\016¢\006\016\n\000\032\004\b\025\020\026\"\004\b\027\020\030R\036\020\f\032\004\030\0010\003X\016¢\006\020\n\002\020\035\032\004\b\031\020\032\"\004\b\033\020\034R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\036\020\026¨\0064"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/ConnectionRecord;", "Landroid/os/Parcelable;", "version", "", "msg", "", "org", "peripheral", "Lca/albertahealthservices/contacttracing/streetpass/PeripheralDevice;", "central", "Lca/albertahealthservices/contacttracing/streetpass/CentralDevice;", "rssi", "txPower", "(ILjava/lang/String;Ljava/lang/String;Lca/albertahealthservices/contacttracing/streetpass/PeripheralDevice;Lca/albertahealthservices/contacttracing/streetpass/CentralDevice;ILjava/lang/Integer;)V", "getCentral", "()Lca/albertahealthservices/contacttracing/streetpass/CentralDevice;", "getMsg", "()Ljava/lang/String;", "getOrg", "getPeripheral", "()Lca/albertahealthservices/contacttracing/streetpass/PeripheralDevice;", "getRssi", "()I", "setRssi", "(I)V", "getTxPower", "()Ljava/lang/Integer;", "setTxPower", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getVersion", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "(ILjava/lang/String;Ljava/lang/String;Lca/albertahealthservices/contacttracing/streetpass/PeripheralDevice;Lca/albertahealthservices/contacttracing/streetpass/CentralDevice;ILjava/lang/Integer;)Lca/albertahealthservices/contacttracing/streetpass/ConnectionRecord;", "describeContents", "equals", "", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "app_release"}, k = 1, mv = {1, 1, 16})
public final class ConnectionRecord implements Parcelable {
  public static final Parcelable.Creator CREATOR = new Creator();
  
  private final CentralDevice central;
  
  private final String msg;
  
  private final String org;
  
  private final PeripheralDevice peripheral;
  
  private int rssi;
  
  private Integer txPower;
  
  private final int version;
  
  public ConnectionRecord(int paramInt1, String paramString1, String paramString2, PeripheralDevice paramPeripheralDevice, CentralDevice paramCentralDevice, int paramInt2, Integer paramInteger) {
    this.version = paramInt1;
    this.msg = paramString1;
    this.org = paramString2;
    this.peripheral = paramPeripheralDevice;
    this.central = paramCentralDevice;
    this.rssi = paramInt2;
    this.txPower = paramInteger;
  }
  
  public final int component1() {
    return this.version;
  }
  
  public final String component2() {
    return this.msg;
  }
  
  public final String component3() {
    return this.org;
  }
  
  public final PeripheralDevice component4() {
    return this.peripheral;
  }
  
  public final CentralDevice component5() {
    return this.central;
  }
  
  public final int component6() {
    return this.rssi;
  }
  
  public final Integer component7() {
    return this.txPower;
  }
  
  public final ConnectionRecord copy(int paramInt1, String paramString1, String paramString2, PeripheralDevice paramPeripheralDevice, CentralDevice paramCentralDevice, int paramInt2, Integer paramInteger) {
    Intrinsics.checkParameterIsNotNull(paramString1, "msg");
    Intrinsics.checkParameterIsNotNull(paramString2, "org");
    Intrinsics.checkParameterIsNotNull(paramPeripheralDevice, "peripheral");
    Intrinsics.checkParameterIsNotNull(paramCentralDevice, "central");
    return new ConnectionRecord(paramInt1, paramString1, paramString2, paramPeripheralDevice, paramCentralDevice, paramInt2, paramInteger);
  }
  
  public int describeContents() {
    return 0;
  }
  
  public boolean equals(Object paramObject) {
    if (this != paramObject) {
      if (paramObject instanceof ConnectionRecord) {
        paramObject = paramObject;
        if (this.version == ((ConnectionRecord)paramObject).version && Intrinsics.areEqual(this.msg, ((ConnectionRecord)paramObject).msg) && Intrinsics.areEqual(this.org, ((ConnectionRecord)paramObject).org) && Intrinsics.areEqual(this.peripheral, ((ConnectionRecord)paramObject).peripheral) && Intrinsics.areEqual(this.central, ((ConnectionRecord)paramObject).central) && this.rssi == ((ConnectionRecord)paramObject).rssi && Intrinsics.areEqual(this.txPower, ((ConnectionRecord)paramObject).txPower))
          return true; 
      } 
      return false;
    } 
    return true;
  }
  
  public final CentralDevice getCentral() {
    return this.central;
  }
  
  public final String getMsg() {
    return this.msg;
  }
  
  public final String getOrg() {
    return this.org;
  }
  
  public final PeripheralDevice getPeripheral() {
    return this.peripheral;
  }
  
  public final int getRssi() {
    return this.rssi;
  }
  
  public final Integer getTxPower() {
    return this.txPower;
  }
  
  public final int getVersion() {
    return this.version;
  }
  
  public int hashCode() {
    byte b1;
    byte b2;
    byte b3;
    byte b4;
    int i = this.version;
    String str = this.msg;
    int j = 0;
    if (str != null) {
      b1 = str.hashCode();
    } else {
      b1 = 0;
    } 
    str = this.org;
    if (str != null) {
      b2 = str.hashCode();
    } else {
      b2 = 0;
    } 
    PeripheralDevice peripheralDevice = this.peripheral;
    if (peripheralDevice != null) {
      b3 = peripheralDevice.hashCode();
    } else {
      b3 = 0;
    } 
    CentralDevice centralDevice = this.central;
    if (centralDevice != null) {
      b4 = centralDevice.hashCode();
    } else {
      b4 = 0;
    } 
    int k = this.rssi;
    Integer integer = this.txPower;
    if (integer != null)
      j = integer.hashCode(); 
    return (((((i * 31 + b1) * 31 + b2) * 31 + b3) * 31 + b4) * 31 + k) * 31 + j;
  }
  
  public final void setRssi(int paramInt) {
    this.rssi = paramInt;
  }
  
  public final void setTxPower(Integer paramInteger) {
    this.txPower = paramInteger;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Central ");
    stringBuilder.append(this.central.getModelC());
    stringBuilder.append(" - ");
    stringBuilder.append(this.central.getAddress());
    stringBuilder.append(" ---> Peripheral ");
    stringBuilder.append(this.peripheral.getModelP());
    stringBuilder.append(" - ");
    stringBuilder.append(this.peripheral.getAddress());
    return stringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    Intrinsics.checkParameterIsNotNull(paramParcel, "parcel");
    paramParcel.writeInt(this.version);
    paramParcel.writeString(this.msg);
    paramParcel.writeString(this.org);
    this.peripheral.writeToParcel(paramParcel, 0);
    this.central.writeToParcel(paramParcel, 0);
    paramParcel.writeInt(this.rssi);
    Integer integer = this.txPower;
    if (integer != null) {
      paramParcel.writeInt(1);
      paramParcel.writeInt(integer.intValue());
    } else {
      paramParcel.writeInt(0);
    } 
  }
  
  @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 16})
  public static final class Creator implements Parcelable.Creator {
    public final Object createFromParcel(Parcel param1Parcel) {
      Intrinsics.checkParameterIsNotNull(param1Parcel, "in");
      int i = param1Parcel.readInt();
      String str1 = param1Parcel.readString();
      String str2 = param1Parcel.readString();
      PeripheralDevice peripheralDevice = (PeripheralDevice)PeripheralDevice.CREATOR.createFromParcel(param1Parcel);
      CentralDevice centralDevice = (CentralDevice)CentralDevice.CREATOR.createFromParcel(param1Parcel);
      int j = param1Parcel.readInt();
      if (param1Parcel.readInt() != 0) {
        Integer integer = Integer.valueOf(param1Parcel.readInt());
      } else {
        param1Parcel = null;
      } 
      return new ConnectionRecord(i, str1, str2, peripheralDevice, centralDevice, j, (Integer)param1Parcel);
    }
    
    public final Object[] newArray(int param1Int) {
      return (Object[])new ConnectionRecord[param1Int];
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/ConnectionRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */