package ca.albertahealthservices.contacttracing.protocol.v2;

import ca.albertahealthservices.contacttracing.TracerApp;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.protocol.CentralInterface;
import ca.albertahealthservices.contacttracing.streetpass.ConnectionRecord;
import ca.albertahealthservices.contacttracing.streetpass.PeripheralDevice;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000(\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020\022\n\000\n\002\020\b\n\002\b\004\n\002\030\002\n\002\b\004\030\0002\0020\001B\005¢\006\002\020\002J'\020\005\032\0020\0062\006\020\007\032\0020\b2\006\020\t\032\0020\b2\b\020\n\032\004\030\0010\bH\026¢\006\002\020\013J1\020\f\032\004\030\0010\r2\006\020\016\032\0020\0062\006\020\017\032\0020\0042\006\020\t\032\0020\b2\b\020\n\032\004\030\0010\bH\026¢\006\002\020\020R\016\020\003\032\0020\004XD¢\006\002\n\000¨\006\021"}, d2 = {"Lca/albertahealthservices/contacttracing/protocol/v2/V2Central;", "Lca/albertahealthservices/contacttracing/protocol/CentralInterface;", "()V", "TAG", "", "prepareWriteRequestData", "", "protocolVersion", "", "rssi", "txPower", "(IILjava/lang/Integer;)[B", "processReadRequestDataReceived", "Lca/albertahealthservices/contacttracing/streetpass/ConnectionRecord;", "dataRead", "peripheralAddress", "([BLjava/lang/String;ILjava/lang/Integer;)Lca/albertahealthservices/contacttracing/streetpass/ConnectionRecord;", "app_release"}, k = 1, mv = {1, 1, 16})
public final class V2Central implements CentralInterface {
  private final String TAG = "V2Central";
  
  public byte[] prepareWriteRequestData(int paramInt1, int paramInt2, Integer paramInteger) {
    return (new V2WriteRequestPayload(paramInt1, TracerApp.Companion.thisDeviceMsg(), "CA_AB", TracerApp.Companion.asCentralDevice(), paramInt2)).getPayload();
  }
  
  public ConnectionRecord processReadRequestDataReceived(byte[] paramArrayOfbyte, String paramString, int paramInt, Integer paramInteger) {
    Intrinsics.checkParameterIsNotNull(paramArrayOfbyte, "dataRead");
    Intrinsics.checkParameterIsNotNull(paramString, "peripheralAddress");
    try {
      PeripheralDevice peripheralDevice;
      return new ConnectionRecord(v2ReadRequestPayload.getV(), v2ReadRequestPayload.getId(), v2ReadRequestPayload.getO(), peripheralDevice, TracerApp.Companion.asCentralDevice(), paramInt, paramInteger);
    } finally {
      paramInteger = null;
      CentralLog.Companion companion = CentralLog.Companion;
      String str = this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Failed to deserialize read payload ");
      stringBuilder.append(paramInteger.getMessage());
      companion.e(str, stringBuilder.toString());
    } 
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/protocol/v2/V2Central.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */