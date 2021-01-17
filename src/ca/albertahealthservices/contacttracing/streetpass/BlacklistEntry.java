package ca.albertahealthservices.contacttracing.streetpass;

import kotlin.Metadata;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\030\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\000\n\002\020\t\n\002\b\006\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006R\021\020\004\032\0020\005¢\006\b\n\000\032\004\b\007\020\bR\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\t\020\n¨\006\013"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/BlacklistEntry;", "", "uniqueIdentifier", "", "timeEntered", "", "(Ljava/lang/String;J)V", "getTimeEntered", "()J", "getUniqueIdentifier", "()Ljava/lang/String;", "app_release"}, k = 1, mv = {1, 1, 16})
public final class BlacklistEntry {
  private final long timeEntered;
  
  private final String uniqueIdentifier;
  
  public BlacklistEntry(String paramString, long paramLong) {
    this.uniqueIdentifier = paramString;
    this.timeEntered = paramLong;
  }
  
  public final long getTimeEntered() {
    return this.timeEntered;
  }
  
  public final String getUniqueIdentifier() {
    return this.uniqueIdentifier;
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/BlacklistEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */