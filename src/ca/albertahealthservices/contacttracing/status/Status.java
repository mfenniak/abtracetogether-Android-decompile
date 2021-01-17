package ca.albertahealthservices.contacttracing.status;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\0004\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\002\b\006\n\002\020\b\n\000\n\002\020\013\n\000\n\002\020\000\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\b\002\b\b\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\t\020\007\032\0020\003HÆ\003J\023\020\b\032\0020\0002\b\b\002\020\002\032\0020\003HÆ\001J\t\020\t\032\0020\nHÖ\001J\023\020\013\032\0020\f2\b\020\r\032\004\030\0010\016HÖ\003J\t\020\017\032\0020\nHÖ\001J\t\020\020\032\0020\003HÖ\001J\031\020\021\032\0020\0222\006\020\023\032\0020\0242\006\020\025\032\0020\nHÖ\001R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\005\020\006¨\006\026"}, d2 = {"Lca/albertahealthservices/contacttracing/status/Status;", "Landroid/os/Parcelable;", "msg", "", "(Ljava/lang/String;)V", "getMsg", "()Ljava/lang/String;", "component1", "copy", "describeContents", "", "equals", "", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "app_release"}, k = 1, mv = {1, 1, 16})
public final class Status implements Parcelable {
  public static final Parcelable.Creator CREATOR = new Creator();
  
  private final String msg;
  
  public Status(String paramString) {
    this.msg = paramString;
  }
  
  public final String component1() {
    return this.msg;
  }
  
  public final Status copy(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "msg");
    return new Status(paramString);
  }
  
  public int describeContents() {
    return 0;
  }
  
  public boolean equals(Object paramObject) {
    if (this != paramObject) {
      if (paramObject instanceof Status) {
        paramObject = paramObject;
        if (Intrinsics.areEqual(this.msg, ((Status)paramObject).msg))
          return true; 
      } 
      return false;
    } 
    return true;
  }
  
  public final String getMsg() {
    return this.msg;
  }
  
  public int hashCode() {
    boolean bool;
    String str = this.msg;
    if (str != null) {
      bool = str.hashCode();
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Status(msg=");
    stringBuilder.append(this.msg);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    Intrinsics.checkParameterIsNotNull(paramParcel, "parcel");
    paramParcel.writeString(this.msg);
  }
  
  @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 16})
  public static final class Creator implements Parcelable.Creator {
    public final Object createFromParcel(Parcel param1Parcel) {
      Intrinsics.checkParameterIsNotNull(param1Parcel, "in");
      return new Status(param1Parcel.readString());
    }
    
    public final Object[] newArray(int param1Int) {
      return (Object[])new Status[param1Int];
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/status/Status.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */