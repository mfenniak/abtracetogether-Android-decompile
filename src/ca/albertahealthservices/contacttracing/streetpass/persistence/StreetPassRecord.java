package ca.albertahealthservices.contacttracing.streetpass.persistence;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000 \n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\025\n\002\020\t\n\002\b\n\b\007\030\0002\0020\001B?\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\005\022\006\020\007\032\0020\005\022\006\020\b\032\0020\005\022\006\020\t\032\0020\003\022\b\020\n\032\004\030\0010\003¢\006\002\020\013R\036\020\f\032\0020\0038\006@\006X\016¢\006\016\n\000\032\004\b\r\020\016\"\004\b\017\020\020R\026\020\b\032\0020\0058\006X\004¢\006\b\n\000\032\004\b\021\020\022R\026\020\007\032\0020\0058\006X\004¢\006\b\n\000\032\004\b\023\020\022R\036\020\004\032\0020\0058\006@\006X\016¢\006\016\n\000\032\004\b\024\020\022\"\004\b\025\020\026R\036\020\006\032\0020\0058\006@\006X\016¢\006\016\n\000\032\004\b\027\020\022\"\004\b\030\020\026R\026\020\t\032\0020\0038\006X\004¢\006\b\n\000\032\004\b\031\020\016R\036\020\032\032\0020\0338\006@\006X\016¢\006\016\n\000\032\004\b\034\020\035\"\004\b\036\020\037R\032\020\n\032\004\030\0010\0038\006X\004¢\006\n\n\002\020\"\032\004\b \020!R\036\020\002\032\0020\0038\006@\006X\016¢\006\016\n\000\032\004\b#\020\016\"\004\b$\020\020¨\006%"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;", "", "v", "", "msg", "", "org", "modelP", "modelC", "rssi", "txPower", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Integer;)V", "id", "getId", "()I", "setId", "(I)V", "getModelC", "()Ljava/lang/String;", "getModelP", "getMsg", "setMsg", "(Ljava/lang/String;)V", "getOrg", "setOrg", "getRssi", "timestamp", "", "getTimestamp", "()J", "setTimestamp", "(J)V", "getTxPower", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getV", "setV", "app_release"}, k = 1, mv = {1, 1, 16})
public final class StreetPassRecord {
  private int id;
  
  private final String modelC;
  
  private final String modelP;
  
  private String msg;
  
  private String org;
  
  private final int rssi;
  
  private long timestamp;
  
  private final Integer txPower;
  
  private int v;
  
  public StreetPassRecord(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt2, Integer paramInteger) {
    this.v = paramInt1;
    this.msg = paramString1;
    this.org = paramString2;
    this.modelP = paramString3;
    this.modelC = paramString4;
    this.rssi = paramInt2;
    this.txPower = paramInteger;
    this.timestamp = System.currentTimeMillis();
  }
  
  public final int getId() {
    return this.id;
  }
  
  public final String getModelC() {
    return this.modelC;
  }
  
  public final String getModelP() {
    return this.modelP;
  }
  
  public final String getMsg() {
    return this.msg;
  }
  
  public final String getOrg() {
    return this.org;
  }
  
  public final int getRssi() {
    return this.rssi;
  }
  
  public final long getTimestamp() {
    return this.timestamp;
  }
  
  public final Integer getTxPower() {
    return this.txPower;
  }
  
  public final int getV() {
    return this.v;
  }
  
  public final void setId(int paramInt) {
    this.id = paramInt;
  }
  
  public final void setMsg(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "<set-?>");
    this.msg = paramString;
  }
  
  public final void setOrg(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "<set-?>");
    this.org = paramString;
  }
  
  public final void setTimestamp(long paramLong) {
    this.timestamp = paramLong;
  }
  
  public final void setV(int paramInt) {
    this.v = paramInt;
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */