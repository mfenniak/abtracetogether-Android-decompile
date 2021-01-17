package ca.albertahealthservices.contacttracing.streetpass.view;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecord;
import ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecordDatabase;
import ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecordRepository;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000(\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020 \n\002\030\002\n\002\b\005\n\002\030\002\n\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004R&\020\005\032\016\022\n\022\b\022\004\022\0020\b0\0070\006X\016¢\006\016\n\000\032\004\b\t\020\n\"\004\b\013\020\fR\016\020\r\032\0020\016X\016¢\006\002\n\000¨\006\017"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/view/RecordViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "app", "Landroid/app/Application;", "(Landroid/app/Application;)V", "allRecords", "Landroidx/lifecycle/LiveData;", "", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;", "getAllRecords", "()Landroidx/lifecycle/LiveData;", "setAllRecords", "(Landroidx/lifecycle/LiveData;)V", "repo", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordRepository;", "app_release"}, k = 1, mv = {1, 1, 16})
public final class RecordViewModel extends AndroidViewModel {
  private LiveData<List<StreetPassRecord>> allRecords;
  
  private StreetPassRecordRepository repo;
  
  public RecordViewModel(Application paramApplication) {
    super(paramApplication);
    StreetPassRecordRepository streetPassRecordRepository = new StreetPassRecordRepository(StreetPassRecordDatabase.Companion.getDatabase((Context)paramApplication).recordDao());
    this.repo = streetPassRecordRepository;
    this.allRecords = streetPassRecordRepository.getAllRecords();
  }
  
  public final LiveData<List<StreetPassRecord>> getAllRecords() {
    return this.allRecords;
  }
  
  public final void setAllRecords(LiveData<List<StreetPassRecord>> paramLiveData) {
    Intrinsics.checkParameterIsNotNull(paramLiveData, "<set-?>");
    this.allRecords = paramLiveData;
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/view/RecordViewModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */