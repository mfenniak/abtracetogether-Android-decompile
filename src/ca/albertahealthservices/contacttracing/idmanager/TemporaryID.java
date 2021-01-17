package ca.albertahealthservices.contacttracing.idmanager;

import ca.albertahealthservices.contacttracing.logging.CentralLog;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(bv = {1, 0, 3}, d1 = {"\000&\n\002\030\002\n\002\020\000\n\000\n\002\020\t\n\000\n\002\020\016\n\002\b\b\n\002\020\013\n\000\n\002\020\002\n\002\b\002\030\000 \0212\0020\001:\001\021B\035\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\003¢\006\002\020\007J\006\020\r\032\0020\016J\006\020\017\032\0020\020R\021\020\006\032\0020\003¢\006\b\n\000\032\004\b\b\020\tR\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\n\020\tR\021\020\004\032\0020\005¢\006\b\n\000\032\004\b\013\020\f¨\006\022"}, d2 = {"Lca/albertahealthservices/contacttracing/idmanager/TemporaryID;", "", "startTime", "", "tempID", "", "expiryTime", "(JLjava/lang/String;J)V", "getExpiryTime", "()J", "getStartTime", "getTempID", "()Ljava/lang/String;", "isValidForCurrentTime", "", "print", "", "Companion", "app_release"}, k = 1, mv = {1, 1, 16})
public final class TemporaryID {
  public static final Companion Companion = new Companion(null);
  
  private static final String TAG = "TempID";
  
  private final long expiryTime;
  
  private final long startTime;
  
  private final String tempID;
  
  public TemporaryID(long paramLong1, String paramString, long paramLong2) {
    this.startTime = paramLong1;
    this.tempID = paramString;
    this.expiryTime = paramLong2;
  }
  
  public final long getExpiryTime() {
    return this.expiryTime;
  }
  
  public final long getStartTime() {
    return this.startTime;
  }
  
  public final String getTempID() {
    return this.tempID;
  }
  
  public final boolean isValidForCurrentTime() {
    boolean bool;
    long l1 = System.currentTimeMillis();
    long l2 = this.startTime;
    long l3 = 1000L;
    if (l1 > l2 * l3 && l1 < this.expiryTime * l3) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final void print() {
    long l1 = this.startTime;
    long l2 = 1000L;
    long l3 = this.expiryTime;
    CentralLog.Companion companion1 = CentralLog.Companion;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("[TempID] Start time: ");
    stringBuilder2.append(l1 * l2);
    companion1.d("TempID", stringBuilder2.toString());
    CentralLog.Companion companion2 = CentralLog.Companion;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("[TempID] Expiry time: ");
    stringBuilder1.append(l3 * l2);
    companion2.d("TempID", stringBuilder1.toString());
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000¨\006\005"}, d2 = {"Lca/albertahealthservices/contacttracing/idmanager/TemporaryID$Companion;", "", "()V", "TAG", "", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class Companion {
    private Companion() {}
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/idmanager/TemporaryID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */