package ca.albertahealthservices.contacttracing.status.persistence;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SupportSQLiteQuery;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Metadata(bv = {1, 0, 3}, d1 = {"\0008\n\002\030\002\n\002\020\000\n\000\n\002\020 \n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\003\n\002\030\002\n\000\n\002\020\002\n\002\b\005\n\002\020\t\n\002\b\002\bg\030\0002\0020\001J\016\020\002\032\b\022\004\022\0020\0040\003H'J\030\020\005\032\n\022\006\022\004\030\0010\0040\0062\006\020\007\032\0020\bH'J\024\020\t\032\016\022\n\022\b\022\004\022\0020\0040\0030\006H'J\026\020\n\032\b\022\004\022\0020\0040\0032\006\020\013\032\0020\fH'J\031\020\r\032\0020\0162\006\020\017\032\0020\004H§@ø\001\000¢\006\002\020\020J\b\020\021\032\0020\016H'J\031\020\022\032\0020\0162\006\020\023\032\0020\024H§@ø\001\000¢\006\002\020\025\002\004\n\002\b\031¨\006\026"}, d2 = {"Lca/albertahealthservices/contacttracing/status/persistence/StatusRecordDao;", "", "getCurrentRecords", "", "Lca/albertahealthservices/contacttracing/status/persistence/StatusRecord;", "getMostRecentRecord", "Landroidx/lifecycle/LiveData;", "msg", "", "getRecords", "getRecordsViaQuery", "query", "Landroidx/sqlite/db/SupportSQLiteQuery;", "insert", "", "record", "(Lca/albertahealthservices/contacttracing/status/persistence/StatusRecord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "nukeDb", "purgeOldRecords", "before", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"}, k = 1, mv = {1, 1, 16})
public interface StatusRecordDao {
  List<StatusRecord> getCurrentRecords();
  
  LiveData<StatusRecord> getMostRecentRecord(String paramString);
  
  LiveData<List<StatusRecord>> getRecords();
  
  List<StatusRecord> getRecordsViaQuery(SupportSQLiteQuery paramSupportSQLiteQuery);
  
  Object insert(StatusRecord paramStatusRecord, Continuation<? super Unit> paramContinuation);
  
  void nukeDb();
  
  Object purgeOldRecords(long paramLong, Continuation<? super Unit> paramContinuation);
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/status/persistence/StatusRecordDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */