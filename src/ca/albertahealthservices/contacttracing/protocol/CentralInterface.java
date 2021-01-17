package ca.albertahealthservices.contacttracing.protocol;

import ca.albertahealthservices.contacttracing.streetpass.ConnectionRecord;
import kotlin.Metadata;

@Metadata(bv = {1, 0, 3}, d1 = {"\000(\n\002\030\002\n\002\020\000\n\000\n\002\020\022\n\000\n\002\020\b\n\002\b\004\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\002\bf\030\0002\0020\001J'\020\002\032\0020\0032\006\020\004\032\0020\0052\006\020\006\032\0020\0052\b\020\007\032\004\030\0010\005H&¢\006\002\020\bJ1\020\t\032\004\030\0010\n2\006\020\013\032\0020\0032\006\020\f\032\0020\r2\006\020\006\032\0020\0052\b\020\007\032\004\030\0010\005H&¢\006\002\020\016¨\006\017"}, d2 = {"Lca/albertahealthservices/contacttracing/protocol/CentralInterface;", "", "prepareWriteRequestData", "", "protocolVersion", "", "rssi", "txPower", "(IILjava/lang/Integer;)[B", "processReadRequestDataReceived", "Lca/albertahealthservices/contacttracing/streetpass/ConnectionRecord;", "dataRead", "peripheralAddress", "", "([BLjava/lang/String;ILjava/lang/Integer;)Lca/albertahealthservices/contacttracing/streetpass/ConnectionRecord;", "app_release"}, k = 1, mv = {1, 1, 16})
public interface CentralInterface {
  byte[] prepareWriteRequestData(int paramInt1, int paramInt2, Integer paramInteger);
  
  ConnectionRecord processReadRequestDataReceived(byte[] paramArrayOfbyte, String paramString, int paramInt, Integer paramInteger);
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/protocol/CentralInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */