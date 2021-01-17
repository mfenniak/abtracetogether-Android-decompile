package ca.albertahealthservices.contacttracing.protocol;

import ca.albertahealthservices.contacttracing.streetpass.ConnectionRecord;
import kotlin.Metadata;

@Metadata(bv = {1, 0, 3}, d1 = {"\000$\n\002\030\002\n\002\020\000\n\000\n\002\020\022\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\000\bf\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H&J\032\020\006\032\004\030\0010\0072\006\020\b\032\0020\0032\006\020\t\032\0020\nH&¨\006\013"}, d2 = {"Lca/albertahealthservices/contacttracing/protocol/PeripheralInterface;", "", "prepareReadRequestData", "", "protocolVersion", "", "processWriteRequestDataReceived", "Lca/albertahealthservices/contacttracing/streetpass/ConnectionRecord;", "dataWritten", "centralAddress", "", "app_release"}, k = 1, mv = {1, 1, 16})
public interface PeripheralInterface {
  byte[] prepareReadRequestData(int paramInt);
  
  ConnectionRecord processWriteRequestDataReceived(byte[] paramArrayOfbyte, String paramString);
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/protocol/PeripheralInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */