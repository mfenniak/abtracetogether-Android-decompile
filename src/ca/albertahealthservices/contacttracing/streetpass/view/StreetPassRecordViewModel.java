package ca.albertahealthservices.contacttracing.streetpass.view;

import ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecord;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(bv = {1, 0, 3}, d1 = {"\000*\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020\016\n\002\b\r\n\002\020\t\n\002\b\t\030\0002\0020\001B\017\b\026\022\006\020\002\032\0020\003¢\006\002\020\004B\027\022\006\020\002\032\0020\003\022\b\b\002\020\005\032\0020\006¢\006\002\020\007R\021\020\b\032\0020\t¢\006\b\n\000\032\004\b\n\020\013R\021\020\f\032\0020\t¢\006\b\n\000\032\004\b\r\020\013R\021\020\016\032\0020\t¢\006\b\n\000\032\004\b\017\020\013R\021\020\005\032\0020\006¢\006\b\n\000\032\004\b\020\020\021R\021\020\022\032\0020\t¢\006\b\n\000\032\004\b\023\020\013R\021\020\024\032\0020\006¢\006\b\n\000\032\004\b\025\020\021R\021\020\026\032\0020\027¢\006\b\n\000\032\004\b\030\020\031R\025\020\032\032\004\030\0010\006¢\006\n\n\002\020\035\032\004\b\033\020\034R\021\020\036\032\0020\006¢\006\b\n\000\032\004\b\037\020\021¨\006 "}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/view/StreetPassRecordViewModel;", "", "record", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;", "(Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;)V", "number", "", "(Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;I)V", "modelC", "", "getModelC", "()Ljava/lang/String;", "modelP", "getModelP", "msg", "getMsg", "getNumber", "()I", "org", "getOrg", "rssi", "getRssi", "timeStamp", "", "getTimeStamp", "()J", "transmissionPower", "getTransmissionPower", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "version", "getVersion", "app_release"}, k = 1, mv = {1, 1, 16})
public final class StreetPassRecordViewModel {
  private final String modelC;
  
  private final String modelP;
  
  private final String msg;
  
  private final int number;
  
  private final String org;
  
  private final int rssi;
  
  private final long timeStamp;
  
  private final Integer transmissionPower;
  
  private final int version;
  
  public StreetPassRecordViewModel(StreetPassRecord paramStreetPassRecord) {
    this(paramStreetPassRecord, 1);
  }
  
  public StreetPassRecordViewModel(StreetPassRecord paramStreetPassRecord, int paramInt) {
    this.number = paramInt;
    this.version = paramStreetPassRecord.getV();
    this.modelC = paramStreetPassRecord.getModelC();
    this.modelP = paramStreetPassRecord.getModelP();
    this.msg = paramStreetPassRecord.getMsg();
    this.timeStamp = paramStreetPassRecord.getTimestamp();
    this.rssi = paramStreetPassRecord.getRssi();
    this.transmissionPower = paramStreetPassRecord.getTxPower();
    this.org = paramStreetPassRecord.getOrg();
  }
  
  public final String getModelC() {
    return this.modelC;
  }
  
  public final String getModelP() {
    return this.modelP;
  }
  
  public final String getMsg() {
    return this.msg;
  }
  
  public final int getNumber() {
    return this.number;
  }
  
  public final String getOrg() {
    return this.org;
  }
  
  public final int getRssi() {
    return this.rssi;
  }
  
  public final long getTimeStamp() {
    return this.timeStamp;
  }
  
  public final Integer getTransmissionPower() {
    return this.transmissionPower;
  }
  
  public final int getVersion() {
    return this.version;
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/view/StreetPassRecordViewModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */