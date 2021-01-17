package ca.albertahealthservices.contacttracing.status.persistence;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\"\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\002\n\002\020\b\n\002\b\b\n\002\020\t\n\002\b\005\b\007\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004R\036\020\005\032\0020\0068\006@\006X\016¢\006\016\n\000\032\004\b\007\020\b\"\004\b\t\020\nR\036\020\002\032\0020\0038\006@\006X\016¢\006\016\n\000\032\004\b\013\020\f\"\004\b\r\020\004R\036\020\016\032\0020\0178\006@\006X\016¢\006\016\n\000\032\004\b\020\020\021\"\004\b\022\020\023¨\006\024"}, d2 = {"Lca/albertahealthservices/contacttracing/status/persistence/StatusRecord;", "", "msg", "", "(Ljava/lang/String;)V", "id", "", "getId", "()I", "setId", "(I)V", "getMsg", "()Ljava/lang/String;", "setMsg", "timestamp", "", "getTimestamp", "()J", "setTimestamp", "(J)V", "app_release"}, k = 1, mv = {1, 1, 16})
public final class StatusRecord {
  private int id;
  
  private String msg;
  
  private long timestamp;
  
  public StatusRecord(String paramString) {
    this.msg = paramString;
    this.timestamp = System.currentTimeMillis();
  }
  
  public final int getId() {
    return this.id;
  }
  
  public final String getMsg() {
    return this.msg;
  }
  
  public final long getTimestamp() {
    return this.timestamp;
  }
  
  public final void setId(int paramInt) {
    this.id = paramInt;
  }
  
  public final void setMsg(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "<set-?>");
    this.msg = paramString;
  }
  
  public final void setTimestamp(long paramLong) {
    this.timestamp = paramLong;
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/status/persistence/StatusRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */