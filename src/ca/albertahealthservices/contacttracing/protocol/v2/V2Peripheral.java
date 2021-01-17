package ca.albertahealthservices.contacttracing.protocol.v2;

import ca.albertahealthservices.contacttracing.TracerApp;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.protocol.PeripheralInterface;
import ca.albertahealthservices.contacttracing.streetpass.CentralDevice;
import ca.albertahealthservices.contacttracing.streetpass.ConnectionRecord;
import ca.albertahealthservices.contacttracing.streetpass.PeripheralDevice;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000&\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020\022\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\003\030\0002\0020\001B\005¢\006\002\020\002J\020\020\005\032\0020\0062\006\020\007\032\0020\bH\026J\032\020\t\032\004\030\0010\n2\006\020\013\032\0020\0062\006\020\f\032\0020\004H\026R\016\020\003\032\0020\004XD¢\006\002\n\000¨\006\r"}, d2 = {"Lca/albertahealthservices/contacttracing/protocol/v2/V2Peripheral;", "Lca/albertahealthservices/contacttracing/protocol/PeripheralInterface;", "()V", "TAG", "", "prepareReadRequestData", "", "protocolVersion", "", "processWriteRequestDataReceived", "Lca/albertahealthservices/contacttracing/streetpass/ConnectionRecord;", "dataReceived", "centralAddress", "app_release"}, k = 1, mv = {1, 1, 16})
public final class V2Peripheral implements PeripheralInterface {
  private final String TAG = "V2Peripheral";
  
  public byte[] prepareReadRequestData(int paramInt) {
    return (new V2ReadRequestPayload(paramInt, TracerApp.Companion.thisDeviceMsg(), "CA_AB", TracerApp.Companion.asPeripheralDevice())).getPayload();
  }
  
  public ConnectionRecord processWriteRequestDataReceived(byte[] paramArrayOfbyte, String paramString) {
    Intrinsics.checkParameterIsNotNull(paramArrayOfbyte, "dataReceived");
    Intrinsics.checkParameterIsNotNull(paramString, "centralAddress");
    try {
      CentralDevice centralDevice;
      return new ConnectionRecord(i, str1, str2, peripheralDevice, centralDevice, v2WriteRequestPayload.getRs(), null);
    } finally {
      Exception exception = null;
      CentralLog.Companion companion = CentralLog.Companion;
      paramString = this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Failed to deserialize write payload ");
      stringBuilder.append(exception.getMessage());
      companion.e(paramString, stringBuilder.toString());
    } 
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/protocol/v2/V2Peripheral.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */