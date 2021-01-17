package ca.albertahealthservices.contacttracing.protocol;

import ca.albertahealthservices.contacttracing.protocol.v2.BlueTraceV2;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000*\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020$\n\002\030\002\n\002\020\b\n\002\b\003\n\002\030\002\n\002\b\005\n\002\020\013\n\000\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\016\020\f\032\0020\n2\006\020\r\032\0020\005J\016\020\f\032\0020\n2\006\020\016\032\0020\006J\020\020\017\032\0020\0202\b\020\r\032\004\030\0010\005R\035\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0060\004¢\006\b\n\000\032\004\b\007\020\bR\035\020\t\032\016\022\004\022\0020\006\022\004\022\0020\n0\004¢\006\b\n\000\032\004\b\013\020\b¨\006\021"}, d2 = {"Lca/albertahealthservices/contacttracing/protocol/BlueTrace;", "", "()V", "characteristicToProtocolVersionMap", "", "Ljava/util/UUID;", "", "getCharacteristicToProtocolVersionMap", "()Ljava/util/Map;", "implementations", "Lca/albertahealthservices/contacttracing/protocol/BlueTraceProtocol;", "getImplementations", "getImplementation", "charUUID", "protocolVersion", "supportsCharUUID", "", "app_release"}, k = 1, mv = {1, 1, 16})
public final class BlueTrace {
  public static final BlueTrace INSTANCE = new BlueTrace();
  
  private static final Map<UUID, Integer> characteristicToProtocolVersionMap;
  
  private static final Map<Integer, BlueTraceProtocol> implementations;
  
  static {
    UUID uUID = UUID.fromString("7bee419e-8882-11ea-bc55-0242ac130003");
    Integer integer = Integer.valueOf(2);
    characteristicToProtocolVersionMap = MapsKt.mapOf(TuplesKt.to(uUID, integer));
    implementations = MapsKt.mapOf(TuplesKt.to(integer, new BlueTraceV2()));
  }
  
  public final Map<UUID, Integer> getCharacteristicToProtocolVersionMap() {
    return characteristicToProtocolVersionMap;
  }
  
  public final BlueTraceProtocol getImplementation(int paramInt) {
    BlueTraceProtocol blueTraceProtocol = implementations.get(Integer.valueOf(paramInt));
    if (blueTraceProtocol == null)
      blueTraceProtocol = (BlueTraceProtocol)new BlueTraceV2(); 
    return blueTraceProtocol;
  }
  
  public final BlueTraceProtocol getImplementation(UUID paramUUID) {
    boolean bool;
    Intrinsics.checkParameterIsNotNull(paramUUID, "charUUID");
    Integer integer = characteristicToProtocolVersionMap.get(paramUUID);
    if (integer != null) {
      bool = integer.intValue();
    } else {
      bool = true;
    } 
    return getImplementation(bool);
  }
  
  public final Map<Integer, BlueTraceProtocol> getImplementations() {
    return implementations;
  }
  
  public final boolean supportsCharUUID(UUID paramUUID) {
    boolean bool1 = false;
    if (paramUUID == null)
      return false; 
    Integer integer = characteristicToProtocolVersionMap.get(paramUUID);
    boolean bool2 = bool1;
    if (integer != null) {
      int i = integer.intValue();
      bool2 = bool1;
      if (implementations.get(Integer.valueOf(i)) != null)
        bool2 = true; 
    } 
    return bool2;
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/protocol/BlueTrace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */