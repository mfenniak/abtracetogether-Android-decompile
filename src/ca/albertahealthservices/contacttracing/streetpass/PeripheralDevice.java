package ca.albertahealthservices.contacttracing.streetpass;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\0004\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\002\b\t\n\002\020\b\n\000\n\002\020\013\n\000\n\002\020\000\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\b\002\b\b\030\0002\0020\001B\027\022\006\020\002\032\0020\003\022\b\020\004\032\004\030\0010\003¢\006\002\020\005J\t\020\t\032\0020\003HÆ\003J\013\020\n\032\004\030\0010\003HÆ\003J\037\020\013\032\0020\0002\b\b\002\020\002\032\0020\0032\n\b\002\020\004\032\004\030\0010\003HÆ\001J\t\020\f\032\0020\rHÖ\001J\023\020\016\032\0020\0172\b\020\020\032\004\030\0010\021HÖ\003J\t\020\022\032\0020\rHÖ\001J\t\020\023\032\0020\003HÖ\001J\031\020\024\032\0020\0252\006\020\026\032\0020\0272\006\020\030\032\0020\rHÖ\001R\023\020\004\032\004\030\0010\003¢\006\b\n\000\032\004\b\006\020\007R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\b\020\007¨\006\031"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/PeripheralDevice;", "Landroid/os/Parcelable;", "modelP", "", "address", "(Ljava/lang/String;Ljava/lang/String;)V", "getAddress", "()Ljava/lang/String;", "getModelP", "component1", "component2", "copy", "describeContents", "", "equals", "", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "app_release"}, k = 1, mv = {1, 1, 16})
public final class PeripheralDevice implements Parcelable {
  public static final Parcelable.Creator CREATOR = new Creator();
  
  private final String address;
  
  private final String modelP;
  
  public PeripheralDevice(String paramString1, String paramString2) {
    this.modelP = paramString1;
    this.address = paramString2;
  }
  
  public final String component1() {
    return this.modelP;
  }
  
  public final String component2() {
    return this.address;
  }
  
  public final PeripheralDevice copy(String paramString1, String paramString2) {
    Intrinsics.checkParameterIsNotNull(paramString1, "modelP");
    return new PeripheralDevice(paramString1, paramString2);
  }
  
  public int describeContents() {
    return 0;
  }
  
  public boolean equals(Object paramObject) {
    if (this != paramObject) {
      if (paramObject instanceof PeripheralDevice) {
        paramObject = paramObject;
        if (Intrinsics.areEqual(this.modelP, ((PeripheralDevice)paramObject).modelP) && Intrinsics.areEqual(this.address, ((PeripheralDevice)paramObject).address))
          return true; 
      } 
      return false;
    } 
    return true;
  }
  
  public final String getAddress() {
    return this.address;
  }
  
  public final String getModelP() {
    return this.modelP;
  }
  
  public int hashCode() {
    byte b;
    String str = this.modelP;
    int i = 0;
    if (str != null) {
      b = str.hashCode();
    } else {
      b = 0;
    } 
    str = this.address;
    if (str != null)
      i = str.hashCode(); 
    return b * 31 + i;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("PeripheralDevice(modelP=");
    stringBuilder.append(this.modelP);
    stringBuilder.append(", address=");
    stringBuilder.append(this.address);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    Intrinsics.checkParameterIsNotNull(paramParcel, "parcel");
    paramParcel.writeString(this.modelP);
    paramParcel.writeString(this.address);
  }
  
  @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 16})
  public static final class Creator implements Parcelable.Creator {
    public final Object createFromParcel(Parcel param1Parcel) {
      Intrinsics.checkParameterIsNotNull(param1Parcel, "in");
      return new PeripheralDevice(param1Parcel.readString(), param1Parcel.readString());
    }
    
    public final Object[] newArray(int param1Int) {
      return (Object[])new PeripheralDevice[param1Int];
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/PeripheralDevice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */