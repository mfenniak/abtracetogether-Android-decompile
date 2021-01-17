package ca.albertahealthservices.contacttracing.streetpass.persistence;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SupportSQLiteQuery;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Metadata(bv = {1, 0, 3}, d1 = {"\0002\n\002\030\002\n\002\020\000\n\000\n\002\020 \n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\002\n\002\b\005\n\002\020\t\n\002\b\002\bg\030\0002\0020\001J\016\020\002\032\b\022\004\022\0020\0040\003H'J\020\020\005\032\n\022\006\022\004\030\0010\0040\006H'J\024\020\007\032\016\022\n\022\b\022\004\022\0020\0040\0030\006H'J\026\020\b\032\b\022\004\022\0020\0040\0032\006\020\t\032\0020\nH'J\031\020\013\032\0020\f2\006\020\r\032\0020\004H§@ø\001\000¢\006\002\020\016J\b\020\017\032\0020\fH'J\031\020\020\032\0020\f2\006\020\021\032\0020\022H§@ø\001\000¢\006\002\020\023\002\004\n\002\b\031¨\006\024"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDao;", "", "getCurrentRecords", "", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;", "getMostRecentRecord", "Landroidx/lifecycle/LiveData;", "getRecords", "getRecordsViaQuery", "query", "Landroidx/sqlite/db/SupportSQLiteQuery;", "insert", "", "record", "(Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "nukeDb", "purgeOldRecords", "before", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"}, k = 1, mv = {1, 1, 16})
public interface StreetPassRecordDao {
  List<StreetPassRecord> getCurrentRecords();
  
  LiveData<StreetPassRecord> getMostRecentRecord();
  
  LiveData<List<StreetPassRecord>> getRecords();
  
  List<StreetPassRecord> getRecordsViaQuery(SupportSQLiteQuery paramSupportSQLiteQuery);
  
  Object insert(StreetPassRecord paramStreetPassRecord, Continuation<? super Unit> paramContinuation);
  
  void nukeDb();
  
  Object purgeOldRecords(long paramLong, Continuation<? super Unit> paramContinuation);
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */