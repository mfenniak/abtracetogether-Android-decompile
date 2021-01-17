package ca.albertahealthservices.contacttracing.streetpass;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000&\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020\b\n\002\b\021\n\002\020\002\n\000\n\002\030\002\n\002\b\002\b\007\030\0002\0020\001B\037\022\006\020\002\032\0020\003\022\b\020\004\032\004\030\0010\005\022\006\020\006\032\0020\005¢\006\002\020\007J\t\020\025\032\0020\005HÖ\001J\031\020\026\032\0020\0272\006\020\030\032\0020\0312\006\020\032\032\0020\005HÖ\001R\032\020\002\032\0020\003X\016¢\006\016\n\000\032\004\b\b\020\t\"\004\b\n\020\013R\032\020\006\032\0020\005X\016¢\006\016\n\000\032\004\b\f\020\r\"\004\b\016\020\017R\036\020\004\032\004\030\0010\005X\016¢\006\020\n\002\020\024\032\004\b\020\020\021\"\004\b\022\020\023¨\006\033"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/ConnectablePeripheral;", "Landroid/os/Parcelable;", "manuData", "", "transmissionPower", "", "rssi", "(Ljava/lang/String;Ljava/lang/Integer;I)V", "getManuData", "()Ljava/lang/String;", "setManuData", "(Ljava/lang/String;)V", "getRssi", "()I", "setRssi", "(I)V", "getTransmissionPower", "()Ljava/lang/Integer;", "setTransmissionPower", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "describeContents", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "app_release"}, k = 1, mv = {1, 1, 16})
public final class ConnectablePeripheral implements Parcelable {
  public static final Parcelable.Creator CREATOR = new Creator();
  
  private String manuData;
  
  private int rssi;
  
  private Integer transmissionPower;
  
  public ConnectablePeripheral(String paramString, Integer paramInteger, int paramInt) {
    this.manuData = paramString;
    this.transmissionPower = paramInteger;
    this.rssi = paramInt;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public final String getManuData() {
    return this.manuData;
  }
  
  public final int getRssi() {
    return this.rssi;
  }
  
  public final Integer getTransmissionPower() {
    return this.transmissionPower;
  }
  
  public final void setManuData(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "<set-?>");
    this.manuData = paramString;
  }
  
  public final void setRssi(int paramInt) {
    this.rssi = paramInt;
  }
  
  public final void setTransmissionPower(Integer paramInteger) {
    this.transmissionPower = paramInteger;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    Intrinsics.checkParameterIsNotNull(paramParcel, "parcel");
    paramParcel.writeString(this.manuData);
    Integer integer = this.transmissionPower;
    if (integer != null) {
      paramParcel.writeInt(1);
      paramInt = integer.intValue();
    } else {
      paramInt = 0;
    } 
    paramParcel.writeInt(paramInt);
    paramParcel.writeInt(this.rssi);
  }
  
  @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 16})
  public static final class Creator implements Parcelable.Creator {
    public final Object createFromParcel(Parcel param1Parcel) {
      Integer integer;
      Intrinsics.checkParameterIsNotNull(param1Parcel, "in");
      String str = param1Parcel.readString();
      if (param1Parcel.readInt() != 0) {
        integer = Integer.valueOf(param1Parcel.readInt());
      } else {
        integer = null;
      } 
      return new ConnectablePeripheral(str, integer, param1Parcel.readInt());
    }
    
    public final Object[] newArray(int param1Int) {
      return (Object[])new ConnectablePeripheral[param1Int];
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/ConnectablePeripheral.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */