package ca.albertahealthservices.contacttracing.streetpass.persistence;

import android.content.Context;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;

@Metadata(bv = {1, 0, 3}, d1 = {"\0004\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\020 \n\002\030\002\n\000\n\002\020\002\n\002\b\002\n\002\020\t\n\002\b\005\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\f\020\013\032\b\022\004\022\0020\r0\fJ\006\020\016\032\0020\017J\031\020\020\032\0020\0172\006\020\021\032\0020\022H@ø\001\000¢\006\002\020\023J\031\020\024\032\0020\0172\006\020\025\032\0020\rH@ø\001\000¢\006\002\020\026R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\005\020\006R\021\020\007\032\0020\b¢\006\b\n\000\032\004\b\t\020\n\002\004\n\002\b\031¨\006\027"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordStorage;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getContext", "()Landroid/content/Context;", "recordDao", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDao;", "getRecordDao", "()Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDao;", "getAllRecords", "", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;", "nukeDb", "", "purgeOldRecords", "before", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveRecord", "record", "(Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"}, k = 1, mv = {1, 1, 16})
public final class StreetPassRecordStorage {
  private final Context context;
  
  private final StreetPassRecordDao recordDao;
  
  public StreetPassRecordStorage(Context paramContext) {
    this.context = paramContext;
    this.recordDao = StreetPassRecordDatabase.Companion.getDatabase(this.context).recordDao();
  }
  
  public final List<StreetPassRecord> getAllRecords() {
    return this.recordDao.getCurrentRecords();
  }
  
  public final Context getContext() {
    return this.context;
  }
  
  public final StreetPassRecordDao getRecordDao() {
    return this.recordDao;
  }
  
  public final void nukeDb() {
    this.recordDao.nukeDb();
  }
  
  public final Object purgeOldRecords(long paramLong, Continuation<? super Unit> paramContinuation) {
    Object object = this.recordDao.purgeOldRecords(paramLong, paramContinuation);
    return (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? object : Unit.INSTANCE;
  }
  
  public final Object saveRecord(StreetPassRecord paramStreetPassRecord, Continuation<? super Unit> paramContinuation) {
    Object object = this.recordDao.insert(paramStreetPassRecord, paramContinuation);
    return (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? object : Unit.INSTANCE;
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */