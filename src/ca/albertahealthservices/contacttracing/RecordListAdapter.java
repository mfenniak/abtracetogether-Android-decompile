package ca.albertahealthservices.contacttracing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecord;
import ca.albertahealthservices.contacttracing.streetpass.view.StreetPassRecordViewModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000J\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020 \n\002\030\002\n\000\n\002\030\002\n\002\b\007\n\002\020\b\n\000\n\002\020\002\n\002\b\004\n\002\030\002\n\002\b\n\030\0002\f\022\b\022\0060\002R\0020\0000\001:\002%&B\017\b\000\022\006\020\003\032\0020\004¢\006\002\020\005J\030\020\017\032\b\022\004\022\0020\f0\0132\b\020\020\032\004\030\0010\fH\002J&\020\021\032\b\022\004\022\0020\f0\0132\b\020\022\032\004\030\0010\f2\f\020\023\032\b\022\004\022\0020\0160\013H\002J&\020\024\032\b\022\004\022\0020\f0\0132\b\020\022\032\004\030\0010\f2\f\020\023\032\b\022\004\022\0020\0160\013H\002J\b\020\025\032\0020\026H\026J\034\020\027\032\0020\0302\n\020\031\032\0060\002R\0020\0002\006\020\032\032\0020\026H\026J\034\020\033\032\0060\002R\0020\0002\006\020\034\032\0020\0352\006\020\036\032\0020\026H\026J\034\020\037\032\b\022\004\022\0020\f0\0132\f\020\023\032\b\022\004\022\0020\0160\013H\002J\034\020 \032\b\022\004\022\0020\f0\0132\f\020\023\032\b\022\004\022\0020\0160\013H\002J\016\020!\032\0020\0302\006\020\b\032\0020\tJ\032\020!\032\0020\0302\006\020\b\032\0020\t2\b\020\022\032\004\030\0010\fH\002J\026\020\"\032\0020\0302\f\020\n\032\b\022\004\022\0020\f0\013H\002J\033\020#\032\0020\0302\f\020\n\032\b\022\004\022\0020\0160\013H\000¢\006\002\b$R\016\020\006\032\0020\007X\004¢\006\002\n\000R\016\020\b\032\0020\tX\016¢\006\002\n\000R\024\020\n\032\b\022\004\022\0020\f0\013X\016¢\006\002\n\000R\024\020\r\032\b\022\004\022\0020\0160\013X\016¢\006\002\n\000¨\006'"}, d2 = {"Lca/albertahealthservices/contacttracing/RecordListAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lca/albertahealthservices/contacttracing/RecordListAdapter$RecordViewHolder;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "inflater", "Landroid/view/LayoutInflater;", "mode", "Lca/albertahealthservices/contacttracing/RecordListAdapter$MODE;", "records", "", "Lca/albertahealthservices/contacttracing/streetpass/view/StreetPassRecordViewModel;", "sourceData", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;", "filter", "sample", "filterByModelC", "model", "words", "filterByModelP", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "prepareCollapsedData", "prepareViewData", "setMode", "setRecords", "setSourceData", "setSourceData$app_release", "MODE", "RecordViewHolder", "app_release"}, k = 1, mv = {1, 1, 16})
public final class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.RecordViewHolder> {
  private final LayoutInflater inflater;
  
  private MODE mode;
  
  private List<StreetPassRecordViewModel> records;
  
  private List<StreetPassRecord> sourceData;
  
  public RecordListAdapter(Context paramContext) {
    LayoutInflater layoutInflater = LayoutInflater.from(paramContext);
    Intrinsics.checkExpressionValueIsNotNull(layoutInflater, "LayoutInflater.from(context)");
    this.inflater = layoutInflater;
    this.records = CollectionsKt.emptyList();
    this.sourceData = CollectionsKt.emptyList();
    this.mode = MODE.ALL;
  }
  
  private final List<StreetPassRecordViewModel> filter(StreetPassRecordViewModel paramStreetPassRecordViewModel) {
    List<StreetPassRecordViewModel> list;
    MODE mODE = this.mode;
    int i = RecordListAdapter$WhenMappings.$EnumSwitchMapping$0[mODE.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i == 4) {
            list = filterByModelC(paramStreetPassRecordViewModel, this.sourceData);
          } else {
            throw new NoWhenBranchMatchedException();
          } 
        } else {
          list = filterByModelP((StreetPassRecordViewModel)list, this.sourceData);
        } 
      } else {
        list = prepareViewData(this.sourceData);
      } 
    } else {
      list = prepareCollapsedData(this.sourceData);
    } 
    return list;
  }
  
  private final List<StreetPassRecordViewModel> filterByModelC(StreetPassRecordViewModel paramStreetPassRecordViewModel, List<StreetPassRecord> paramList) {
    if (paramStreetPassRecordViewModel != null) {
      list = paramList;
      paramList = new ArrayList<>();
      for (List<StreetPassRecord> list : list) {
        if (Intrinsics.areEqual(((StreetPassRecord)list).getModelC(), paramStreetPassRecordViewModel.getModelC()))
          paramList.add(list); 
      } 
      return prepareViewData(paramList);
    } 
    return prepareViewData(paramList);
  }
  
  private final List<StreetPassRecordViewModel> filterByModelP(StreetPassRecordViewModel paramStreetPassRecordViewModel, List<StreetPassRecord> paramList) {
    if (paramStreetPassRecordViewModel != null) {
      List<StreetPassRecord> list = paramList;
      paramList = new ArrayList<>();
      for (StreetPassRecord streetPassRecord : list) {
        if (Intrinsics.areEqual(((StreetPassRecord)streetPassRecord).getModelP(), paramStreetPassRecordViewModel.getModelP()))
          paramList.add(streetPassRecord); 
      } 
      return prepareViewData(paramList);
    } 
    return prepareViewData(paramList);
  }
  
  private final List<StreetPassRecordViewModel> prepareCollapsedData(List<StreetPassRecord> paramList) {
    list = paramList;
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
    for (StreetPassRecord streetPassRecord : list) {
      String str = ((StreetPassRecord)streetPassRecord).getModelC();
      Object object2 = linkedHashMap.get(str);
      Object object1 = object2;
      if (object2 == null) {
        object1 = new ArrayList();
        linkedHashMap.put(str, object1);
      } 
      ((List<StreetPassRecord>)object1).add(streetPassRecord);
    } 
    HashSet<String> hashSet = new HashSet();
    ArrayList<List<StreetPassRecord>> arrayList2 = new ArrayList();
    for (List<StreetPassRecord> list : list) {
      if (hashSet.add(((StreetPassRecord)list).getModelC()))
        arrayList2.add(list); 
    } 
    ArrayList<List<StreetPassRecord>> arrayList1 = arrayList2;
    ArrayList<StreetPassRecordViewModel> arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList1, 10));
    for (StreetPassRecord streetPassRecord : arrayList1) {
      StreetPassRecordViewModel streetPassRecordViewModel;
      List<StreetPassRecord> list1 = (List)linkedHashMap.get(streetPassRecord.getModelC());
      list = null;
      arrayList2 = null;
      if (list1 != null) {
        Integer integer = Integer.valueOf(list1.size());
      } else {
        list1 = null;
      } 
      if (list1 != null) {
        StreetPassRecord streetPassRecord1;
        int i = ((Number)list1).intValue();
        List list2 = (List)linkedHashMap.get(streetPassRecord.getModelC());
        list1 = list;
        if (list2 != null) {
          Iterator<List<StreetPassRecord>> iterator = list2.iterator();
          if (!iterator.hasNext()) {
            ArrayList<List<StreetPassRecord>> arrayList3 = arrayList2;
          } else {
            list1 = iterator.next();
            if (iterator.hasNext()) {
              long l = ((StreetPassRecord)list1).getTimestamp();
              List<StreetPassRecord> list3 = list1;
              do {
                list = iterator.next();
                long l1 = ((StreetPassRecord)list).getTimestamp();
                list1 = list3;
                long l2 = l;
                if (l < l1) {
                  list1 = list;
                  l2 = l1;
                } 
                list3 = list1;
                l = l2;
              } while (iterator.hasNext());
            } 
          } 
          streetPassRecord1 = (StreetPassRecord)list1;
        } 
        if (streetPassRecord1 != null) {
          streetPassRecordViewModel = new StreetPassRecordViewModel(streetPassRecord1, i);
        } else {
          streetPassRecordViewModel = new StreetPassRecordViewModel(streetPassRecord, i);
        } 
      } else {
        streetPassRecordViewModel = new StreetPassRecordViewModel(streetPassRecord);
      } 
      arrayList.add(streetPassRecordViewModel);
    } 
    return arrayList;
  }
  
  private final List<StreetPassRecordViewModel> prepareViewData(List<StreetPassRecord> paramList) {
    List list = CollectionsKt.reversed(paramList);
    paramList = new ArrayList<>(CollectionsKt.collectionSizeOrDefault(list, 10));
    Iterator<StreetPassRecord> iterator = list.iterator();
    while (iterator.hasNext())
      paramList.add(new StreetPassRecordViewModel(iterator.next())); 
    return (List)paramList;
  }
  
  private final void setMode(MODE paramMODE, StreetPassRecordViewModel paramStreetPassRecordViewModel) {
    this.mode = paramMODE;
    setRecords(filter(paramStreetPassRecordViewModel));
  }
  
  private final void setRecords(List<StreetPassRecordViewModel> paramList) {
    this.records = paramList;
    notifyDataSetChanged();
  }
  
  public int getItemCount() {
    return this.records.size();
  }
  
  public void onBindViewHolder(RecordViewHolder paramRecordViewHolder, int paramInt) {
    Intrinsics.checkParameterIsNotNull(paramRecordViewHolder, "holder");
    StreetPassRecordViewModel streetPassRecordViewModel = this.records.get(paramInt);
    paramRecordViewHolder.getMsgView().setText(streetPassRecordViewModel.getMsg());
    paramRecordViewHolder.getModelCView().setText(streetPassRecordViewModel.getModelC());
    paramRecordViewHolder.getModelPView().setText(streetPassRecordViewModel.getModelP());
    TextView textView2 = paramRecordViewHolder.getFindsView();
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("Detections: ");
    stringBuilder2.append(streetPassRecordViewModel.getNumber());
    textView2.setText(stringBuilder2.toString());
    String str = Utils.INSTANCE.getDate(streetPassRecordViewModel.getTimeStamp());
    paramRecordViewHolder.getTimestampView().setText(str);
    TextView textView1 = paramRecordViewHolder.getVersion();
    stringBuilder2 = new StringBuilder();
    stringBuilder2.append("v: ");
    stringBuilder2.append(streetPassRecordViewModel.getVersion());
    textView1.setText(stringBuilder2.toString());
    textView1 = paramRecordViewHolder.getOrg();
    stringBuilder2 = new StringBuilder();
    stringBuilder2.append("ORG: ");
    stringBuilder2.append(streetPassRecordViewModel.getOrg());
    textView1.setText(stringBuilder2.toString());
    paramRecordViewHolder.getFilterModelP().setTag(streetPassRecordViewModel);
    paramRecordViewHolder.getFilterModelC().setTag(streetPassRecordViewModel);
    textView1 = paramRecordViewHolder.getSignalStrengthView();
    stringBuilder2 = new StringBuilder();
    stringBuilder2.append("Signal Strength: ");
    stringBuilder2.append(streetPassRecordViewModel.getRssi());
    textView1.setText(stringBuilder2.toString());
    TextView textView3 = paramRecordViewHolder.getTxpowerView();
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("Tx Power: ");
    stringBuilder1.append(streetPassRecordViewModel.getTransmissionPower());
    textView3.setText(stringBuilder1.toString());
    paramRecordViewHolder.getFilterModelP().setOnClickListener(new RecordListAdapter$onBindViewHolder$1());
    paramRecordViewHolder.getFilterModelC().setOnClickListener(new RecordListAdapter$onBindViewHolder$2());
  }
  
  public RecordViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
    Intrinsics.checkParameterIsNotNull(paramViewGroup, "parent");
    View view = this.inflater.inflate(2131492974, paramViewGroup, false);
    Intrinsics.checkExpressionValueIsNotNull(view, "itemView");
    return new RecordViewHolder(view);
  }
  
  public final void setMode(MODE paramMODE) {
    Intrinsics.checkParameterIsNotNull(paramMODE, "mode");
    setMode(paramMODE, null);
  }
  
  public final void setSourceData$app_release(List<StreetPassRecord> paramList) {
    Intrinsics.checkParameterIsNotNull(paramList, "records");
    this.sourceData = paramList;
    setMode(this.mode);
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\f\n\002\030\002\n\002\020\020\n\002\b\006\b\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002¢\006\002\020\002j\002\b\003j\002\b\004j\002\b\005j\002\b\006¨\006\007"}, d2 = {"Lca/albertahealthservices/contacttracing/RecordListAdapter$MODE;", "", "(Ljava/lang/String;I)V", "ALL", "COLLAPSE", "MODEL_P", "MODEL_C", "app_release"}, k = 1, mv = {1, 1, 16})
  public enum MODE {
    ALL, COLLAPSE, MODEL_C, MODEL_P;
    
    static {
      MODE mODE1 = new MODE("ALL", 0);
      ALL = mODE1;
      MODE mODE2 = new MODE("COLLAPSE", 1);
      COLLAPSE = mODE2;
      MODE mODE3 = new MODE("MODEL_P", 2);
      MODEL_P = mODE3;
      MODE mODE4 = new MODE("MODEL_C", 3);
      MODEL_C = mODE4;
      $VALUES = new MODE[] { mODE1, mODE2, mODE3, mODE4 };
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\032\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\023\b\004\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004R\021\020\005\032\0020\003¢\006\b\n\000\032\004\b\006\020\007R\021\020\b\032\0020\003¢\006\b\n\000\032\004\b\t\020\007R\021\020\n\032\0020\013¢\006\b\n\000\032\004\b\f\020\rR\021\020\016\032\0020\013¢\006\b\n\000\032\004\b\017\020\rR\021\020\020\032\0020\013¢\006\b\n\000\032\004\b\021\020\rR\021\020\022\032\0020\013¢\006\b\n\000\032\004\b\023\020\rR\021\020\024\032\0020\013¢\006\b\n\000\032\004\b\025\020\rR\021\020\026\032\0020\013¢\006\b\n\000\032\004\b\027\020\rR\021\020\030\032\0020\013¢\006\b\n\000\032\004\b\031\020\rR\021\020\032\032\0020\013¢\006\b\n\000\032\004\b\033\020\rR\021\020\034\032\0020\013¢\006\b\n\000\032\004\b\035\020\r¨\006\036"}, d2 = {"Lca/albertahealthservices/contacttracing/RecordListAdapter$RecordViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lca/albertahealthservices/contacttracing/RecordListAdapter;Landroid/view/View;)V", "filterModelC", "getFilterModelC", "()Landroid/view/View;", "filterModelP", "getFilterModelP", "findsView", "Landroid/widget/TextView;", "getFindsView", "()Landroid/widget/TextView;", "modelCView", "getModelCView", "modelPView", "getModelPView", "msgView", "getMsgView", "org", "getOrg", "signalStrengthView", "getSignalStrengthView", "timestampView", "getTimestampView", "txpowerView", "getTxpowerView", "version", "getVersion", "app_release"}, k = 1, mv = {1, 1, 16})
  public final class RecordViewHolder extends RecyclerView.ViewHolder {
    private final View filterModelC;
    
    private final View filterModelP;
    
    private final TextView findsView;
    
    private final TextView modelCView;
    
    private final TextView modelPView;
    
    private final TextView msgView;
    
    private final TextView org;
    
    private final TextView signalStrengthView;
    
    private final TextView timestampView;
    
    private final TextView txpowerView;
    
    private final TextView version;
    
    public RecordViewHolder(View param1View) {
      super(param1View);
      AppCompatTextView appCompatTextView2 = (AppCompatTextView)param1View.findViewById(R.id.modelc);
      Intrinsics.checkExpressionValueIsNotNull(appCompatTextView2, "itemView.modelc");
      this.modelCView = (TextView)appCompatTextView2;
      appCompatTextView2 = (AppCompatTextView)param1View.findViewById(R.id.modelp);
      Intrinsics.checkExpressionValueIsNotNull(appCompatTextView2, "itemView.modelp");
      this.modelPView = (TextView)appCompatTextView2;
      appCompatTextView2 = (AppCompatTextView)param1View.findViewById(R.id.timestamp);
      Intrinsics.checkExpressionValueIsNotNull(appCompatTextView2, "itemView.timestamp");
      this.timestampView = (TextView)appCompatTextView2;
      appCompatTextView2 = (AppCompatTextView)param1View.findViewById(R.id.finds);
      Intrinsics.checkExpressionValueIsNotNull(appCompatTextView2, "itemView.finds");
      this.findsView = (TextView)appCompatTextView2;
      appCompatTextView2 = (AppCompatTextView)param1View.findViewById(R.id.txpower);
      Intrinsics.checkExpressionValueIsNotNull(appCompatTextView2, "itemView.txpower");
      this.txpowerView = (TextView)appCompatTextView2;
      appCompatTextView2 = (AppCompatTextView)param1View.findViewById(R.id.signal_strength);
      Intrinsics.checkExpressionValueIsNotNull(appCompatTextView2, "itemView.signal_strength");
      this.signalStrengthView = (TextView)appCompatTextView2;
      Button button = (Button)param1View.findViewById(R.id.filter_by_modelp);
      Intrinsics.checkExpressionValueIsNotNull(button, "itemView.filter_by_modelp");
      this.filterModelP = (View)button;
      button = (Button)param1View.findViewById(R.id.filter_by_modelc);
      Intrinsics.checkExpressionValueIsNotNull(button, "itemView.filter_by_modelc");
      this.filterModelC = (View)button;
      AppCompatTextView appCompatTextView1 = (AppCompatTextView)param1View.findViewById(R.id.msg);
      Intrinsics.checkExpressionValueIsNotNull(appCompatTextView1, "itemView.msg");
      this.msgView = (TextView)appCompatTextView1;
      appCompatTextView1 = (AppCompatTextView)param1View.findViewById(R.id.version);
      Intrinsics.checkExpressionValueIsNotNull(appCompatTextView1, "itemView.version");
      this.version = (TextView)appCompatTextView1;
      appCompatTextView1 = (AppCompatTextView)param1View.findViewById(R.id.org);
      Intrinsics.checkExpressionValueIsNotNull(appCompatTextView1, "itemView.org");
      this.org = (TextView)appCompatTextView1;
    }
    
    public final View getFilterModelC() {
      return this.filterModelC;
    }
    
    public final View getFilterModelP() {
      return this.filterModelP;
    }
    
    public final TextView getFindsView() {
      return this.findsView;
    }
    
    public final TextView getModelCView() {
      return this.modelCView;
    }
    
    public final TextView getModelPView() {
      return this.modelPView;
    }
    
    public final TextView getMsgView() {
      return this.msgView;
    }
    
    public final TextView getOrg() {
      return this.org;
    }
    
    public final TextView getSignalStrengthView() {
      return this.signalStrengthView;
    }
    
    public final TextView getTimestampView() {
      return this.timestampView;
    }
    
    public final TextView getTxpowerView() {
      return this.txpowerView;
    }
    
    public final TextView getVersion() {
      return this.version;
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class RecordListAdapter$onBindViewHolder$1 implements View.OnClickListener {
    public final void onClick(View param1View) {
      Intrinsics.checkExpressionValueIsNotNull(param1View, "it");
      Object object = param1View.getTag();
      if (object != null) {
        object = object;
        RecordListAdapter.this.setMode(RecordListAdapter.MODE.MODEL_P, (StreetPassRecordViewModel)object);
        return;
      } 
      throw new TypeCastException("null cannot be cast to non-null type ca.albertahealthservices.contacttracing.streetpass.view.StreetPassRecordViewModel");
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class RecordListAdapter$onBindViewHolder$2 implements View.OnClickListener {
    public final void onClick(View param1View) {
      Intrinsics.checkExpressionValueIsNotNull(param1View, "it");
      Object object = param1View.getTag();
      if (object != null) {
        object = object;
        RecordListAdapter.this.setMode(RecordListAdapter.MODE.MODEL_C, (StreetPassRecordViewModel)object);
        return;
      } 
      throw new TypeCastException("null cannot be cast to non-null type ca.albertahealthservices.contacttracing.streetpass.view.StreetPassRecordViewModel");
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/RecordListAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */