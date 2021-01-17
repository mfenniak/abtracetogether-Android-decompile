package ca.albertahealthservices.contacttracing.protocol;

import kotlin.Metadata;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\036\n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\b\b\026\030\0002\0020\001B\035\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007¢\006\002\020\bR\021\020\004\032\0020\005¢\006\b\n\000\032\004\b\t\020\nR\021\020\006\032\0020\007¢\006\b\n\000\032\004\b\013\020\fR\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\r\020\016¨\006\017"}, d2 = {"Lca/albertahealthservices/contacttracing/protocol/BlueTraceProtocol;", "", "versionInt", "", "central", "Lca/albertahealthservices/contacttracing/protocol/CentralInterface;", "peripheral", "Lca/albertahealthservices/contacttracing/protocol/PeripheralInterface;", "(ILca/albertahealthservices/contacttracing/protocol/CentralInterface;Lca/albertahealthservices/contacttracing/protocol/PeripheralInterface;)V", "getCentral", "()Lca/albertahealthservices/contacttracing/protocol/CentralInterface;", "getPeripheral", "()Lca/albertahealthservices/contacttracing/protocol/PeripheralInterface;", "getVersionInt", "()I", "app_release"}, k = 1, mv = {1, 1, 16})
public class BlueTraceProtocol {
  private final CentralInterface central;
  
  private final PeripheralInterface peripheral;
  
  private final int versionInt;
  
  public BlueTraceProtocol(int paramInt, CentralInterface paramCentralInterface, PeripheralInterface paramPeripheralInterface) {
    this.versionInt = paramInt;
    this.central = paramCentralInterface;
    this.peripheral = paramPeripheralInterface;
  }
  
  public final CentralInterface getCentral() {
    return this.central;
  }
  
  public final PeripheralInterface getPeripheral() {
    return this.peripheral;
  }
  
  public final int getVersionInt() {
    return this.versionInt;
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/protocol/BlueTraceProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */