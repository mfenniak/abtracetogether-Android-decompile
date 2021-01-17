package ca.albertahealthservices.contacttracing.streetpass.persistence;

import androidx.lifecycle.LiveData;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;

@Metadata(bv = {1, 0, 3}, d1 = {"\000*\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020 \n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\003\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\031\020\013\032\0020\f2\006\020\r\032\0020\bH@ø\001\000¢\006\002\020\016R\035\020\005\032\016\022\n\022\b\022\004\022\0020\b0\0070\006¢\006\b\n\000\032\004\b\t\020\nR\016\020\002\032\0020\003X\004¢\006\002\n\000\002\004\n\002\b\031¨\006\017"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordRepository;", "", "recordDao", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDao;", "(Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDao;)V", "allRecords", "Landroidx/lifecycle/LiveData;", "", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;", "getAllRecords", "()Landroidx/lifecycle/LiveData;", "insert", "", "word", "(Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"}, k = 1, mv = {1, 1, 16})
public final class StreetPassRecordRepository {
  private final LiveData<List<StreetPassRecord>> allRecords;
  
  private final StreetPassRecordDao recordDao;
  
  public StreetPassRecordRepository(StreetPassRecordDao paramStreetPassRecordDao) {
    this.recordDao = paramStreetPassRecordDao;
    this.allRecords = paramStreetPassRecordDao.getRecords();
  }
  
  public final LiveData<List<StreetPassRecord>> getAllRecords() {
    return this.allRecords;
  }
  
  public final Object insert(StreetPassRecord paramStreetPassRecord, Continuation<? super Unit> paramContinuation) {
    Object object = this.recordDao.insert(paramStreetPassRecord, paramContinuation);
    return (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? object : Unit.INSTANCE;
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */