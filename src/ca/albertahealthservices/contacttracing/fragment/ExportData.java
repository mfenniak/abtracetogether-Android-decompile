package ca.albertahealthservices.contacttracing.fragment;

import ca.albertahealthservices.contacttracing.status.persistence.StatusRecord;
import ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecord;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\0000\n\002\030\002\n\002\020\000\n\000\n\002\020 \n\002\030\002\n\000\n\002\030\002\n\002\b\b\n\002\020\013\n\002\b\002\n\002\020\b\n\000\n\002\020\016\n\000\b\b\030\0002\0020\001B!\022\f\020\002\032\b\022\004\022\0020\0040\003\022\f\020\005\032\b\022\004\022\0020\0060\003¢\006\002\020\007J\017\020\013\032\b\022\004\022\0020\0040\003HÆ\003J\017\020\f\032\b\022\004\022\0020\0060\003HÆ\003J)\020\r\032\0020\0002\016\b\002\020\002\032\b\022\004\022\0020\0040\0032\016\b\002\020\005\032\b\022\004\022\0020\0060\003HÆ\001J\023\020\016\032\0020\0172\b\020\020\032\004\030\0010\001HÖ\003J\t\020\021\032\0020\022HÖ\001J\t\020\023\032\0020\024HÖ\001R\027\020\002\032\b\022\004\022\0020\0040\003¢\006\b\n\000\032\004\b\b\020\tR\027\020\005\032\b\022\004\022\0020\0060\003¢\006\b\n\000\032\004\b\n\020\t¨\006\025"}, d2 = {"Lca/albertahealthservices/contacttracing/fragment/ExportData;", "", "recordList", "", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;", "statusList", "Lca/albertahealthservices/contacttracing/status/persistence/StatusRecord;", "(Ljava/util/List;Ljava/util/List;)V", "getRecordList", "()Ljava/util/List;", "getStatusList", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_release"}, k = 1, mv = {1, 1, 16})
public final class ExportData {
  private final List<StreetPassRecord> recordList;
  
  private final List<StatusRecord> statusList;
  
  public ExportData(List<StreetPassRecord> paramList, List<StatusRecord> paramList1) {
    this.recordList = paramList;
    this.statusList = paramList1;
  }
  
  public final List<StreetPassRecord> component1() {
    return this.recordList;
  }
  
  public final List<StatusRecord> component2() {
    return this.statusList;
  }
  
  public final ExportData copy(List<StreetPassRecord> paramList, List<StatusRecord> paramList1) {
    Intrinsics.checkParameterIsNotNull(paramList, "recordList");
    Intrinsics.checkParameterIsNotNull(paramList1, "statusList");
    return new ExportData(paramList, paramList1);
  }
  
  public boolean equals(Object paramObject) {
    if (this != paramObject) {
      if (paramObject instanceof ExportData) {
        paramObject = paramObject;
        if (Intrinsics.areEqual(this.recordList, ((ExportData)paramObject).recordList) && Intrinsics.areEqual(this.statusList, ((ExportData)paramObject).statusList))
          return true; 
      } 
      return false;
    } 
    return true;
  }
  
  public final List<StreetPassRecord> getRecordList() {
    return this.recordList;
  }
  
  public final List<StatusRecord> getStatusList() {
    return this.statusList;
  }
  
  public int hashCode() {
    byte b;
    List<StreetPassRecord> list1 = this.recordList;
    int i = 0;
    if (list1 != null) {
      b = list1.hashCode();
    } else {
      b = 0;
    } 
    List<StatusRecord> list = this.statusList;
    if (list != null)
      i = list.hashCode(); 
    return b * 31 + i;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("ExportData(recordList=");
    stringBuilder.append(this.recordList);
    stringBuilder.append(", statusList=");
    stringBuilder.append(this.statusList);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/fragment/ExportData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */