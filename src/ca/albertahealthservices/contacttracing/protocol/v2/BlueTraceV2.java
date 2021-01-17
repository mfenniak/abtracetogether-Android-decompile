package ca.albertahealthservices.contacttracing.protocol.v2;

import ca.albertahealthservices.contacttracing.protocol.BlueTraceProtocol;
import kotlin.Metadata;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\f\n\002\030\002\n\002\030\002\n\002\b\002\030\0002\0020\001B\005¢\006\002\020\002¨\006\003"}, d2 = {"Lca/albertahealthservices/contacttracing/protocol/v2/BlueTraceV2;", "Lca/albertahealthservices/contacttracing/protocol/BlueTraceProtocol;", "()V", "app_release"}, k = 1, mv = {1, 1, 16})
public final class BlueTraceV2 extends BlueTraceProtocol {
  public BlueTraceV2() {
    super(2, new V2Central(), v2Peripheral);
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/protocol/v2/BlueTraceV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */