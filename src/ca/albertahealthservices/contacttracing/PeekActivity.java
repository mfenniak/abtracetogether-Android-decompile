package ca.albertahealthservices.contacttracing;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecord;
import ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecordStorage;
import ca.albertahealthservices.contacttracing.streetpass.view.RecordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000(\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\003\n\002\030\002\n\002\b\003\030\0002\0020\001B\005¢\006\002\020\002J\b\020\007\032\0020\bH\002J\b\020\t\032\0020\004H\002J\022\020\n\032\0020\b2\b\020\013\032\004\030\0010\fH\024J\b\020\r\032\0020\bH\002J\b\020\016\032\0020\bH\002R\016\020\003\032\0020\004X\016¢\006\002\n\000R\016\020\005\032\0020\006X.¢\006\002\n\000¨\006\017"}, d2 = {"Lca/albertahealthservices/contacttracing/PeekActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "timePeriod", "", "viewModel", "Lca/albertahealthservices/contacttracing/streetpass/view/RecordViewModel;", "newPeek", "", "nextTimePeriod", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "startService", "stopService", "app_release"}, k = 1, mv = {1, 1, 16})
public final class PeekActivity extends AppCompatActivity {
  private HashMap _$_findViewCache;
  
  private int timePeriod;
  
  private RecordViewModel viewModel;
  
  private final void newPeek() {
    setContentView(2131492899);
    Context context = (Context)this;
    RecordListAdapter recordListAdapter = new RecordListAdapter(context);
    RecyclerView recyclerView2 = (RecyclerView)_$_findCachedViewById(R.id.recyclerview);
    Intrinsics.checkExpressionValueIsNotNull(recyclerView2, "recyclerview");
    recyclerView2.setAdapter(recordListAdapter);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
    RecyclerView recyclerView1 = (RecyclerView)_$_findCachedViewById(R.id.recyclerview);
    Intrinsics.checkExpressionValueIsNotNull(recyclerView1, "recyclerview");
    recyclerView1.setLayoutManager((RecyclerView.LayoutManager)linearLayoutManager);
    recyclerView1 = (RecyclerView)_$_findCachedViewById(R.id.recyclerview);
    Intrinsics.checkExpressionValueIsNotNull(recyclerView1, "recyclerview");
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView1.getContext(), linearLayoutManager.getOrientation());
    ((RecyclerView)_$_findCachedViewById(R.id.recyclerview)).addItemDecoration((RecyclerView.ItemDecoration)dividerItemDecoration);
    ViewModel viewModel = (new ViewModelProvider((ViewModelStoreOwner)this)).get(RecordViewModel.class);
    Intrinsics.checkExpressionValueIsNotNull(viewModel, "ViewModelProvider(this).…ordViewModel::class.java)");
    RecordViewModel recordViewModel = (RecordViewModel)viewModel;
    this.viewModel = recordViewModel;
    if (recordViewModel == null)
      Intrinsics.throwUninitializedPropertyAccessException("viewModel"); 
    recordViewModel.getAllRecords().observe((LifecycleOwner)this, new PeekActivity$newPeek$1(recordListAdapter));
    FloatingActionButton floatingActionButton3 = (FloatingActionButton)_$_findCachedViewById(R.id.expand);
    if (floatingActionButton3 != null)
      floatingActionButton3.setOnClickListener(new PeekActivity$newPeek$2(recordListAdapter)); 
    floatingActionButton3 = (FloatingActionButton)_$_findCachedViewById(R.id.collapse);
    if (floatingActionButton3 != null)
      floatingActionButton3.setOnClickListener(new PeekActivity$newPeek$3(recordListAdapter)); 
    FloatingActionButton floatingActionButton2 = (FloatingActionButton)_$_findCachedViewById(R.id.start);
    if (floatingActionButton2 != null)
      floatingActionButton2.setOnClickListener(new PeekActivity$newPeek$4()); 
    floatingActionButton2 = (FloatingActionButton)_$_findCachedViewById(R.id.stop);
    if (floatingActionButton2 != null)
      floatingActionButton2.setOnClickListener(new PeekActivity$newPeek$5()); 
    floatingActionButton2 = (FloatingActionButton)_$_findCachedViewById(R.id.delete);
    if (floatingActionButton2 != null)
      floatingActionButton2.setOnClickListener(new PeekActivity$newPeek$6()); 
    floatingActionButton2 = (FloatingActionButton)_$_findCachedViewById(R.id.plot);
    if (floatingActionButton2 != null)
      floatingActionButton2.setOnClickListener(new PeekActivity$newPeek$7()); 
    String str = Preference.INSTANCE.getUUID(getApplicationContext());
    TextView textView = (TextView)_$_findCachedViewById(R.id.info);
    if (textView != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("UID: ");
      int i = str.length();
      if (str != null) {
        str = str.substring(i - 4);
        Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.String).substring(startIndex)");
        stringBuilder.append(str);
        stringBuilder.append("   SSID: ");
        str = "70f34cb2-8882-11ea-bc55-0242ac130003".substring(32);
        Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.String).substring(startIndex)");
        stringBuilder.append(str);
        textView.setText(stringBuilder.toString());
      } else {
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } 
    } 
    FloatingActionButton floatingActionButton1 = (FloatingActionButton)_$_findCachedViewById(R.id.start);
    if (floatingActionButton1 != null)
      floatingActionButton1.setVisibility(8); 
    floatingActionButton1 = (FloatingActionButton)_$_findCachedViewById(R.id.stop);
    if (floatingActionButton1 != null)
      floatingActionButton1.setVisibility(8); 
    floatingActionButton1 = (FloatingActionButton)_$_findCachedViewById(R.id.delete);
    if (floatingActionButton1 != null)
      floatingActionButton1.setVisibility(8); 
  }
  
  private final int nextTimePeriod() {
    int i = this.timePeriod;
    byte b = 12;
    if (i != 1) {
      if (i != 3) {
        if (i != 6)
          if (i != 12) {
            b = 1;
          } else {
            b = 24;
          }  
      } else {
        b = 6;
      } 
    } else {
      b = 3;
    } 
    this.timePeriod = b;
    return b;
  }
  
  private final void startService() {
    Utils.INSTANCE.startBluetoothMonitoringService((Context)this);
  }
  
  private final void stopService() {
    Utils.INSTANCE.stopBluetoothMonitoringService((Context)this);
  }
  
  public void _$_clearFindViewByIdCache() {
    HashMap hashMap = this._$_findViewCache;
    if (hashMap != null)
      hashMap.clear(); 
  }
  
  public View _$_findCachedViewById(int paramInt) {
    if (this._$_findViewCache == null)
      this._$_findViewCache = new HashMap<>(); 
    View view1 = (View)this._$_findViewCache.get(Integer.valueOf(paramInt));
    View view2 = view1;
    if (view1 == null) {
      view2 = findViewById(paramInt);
      this._$_findViewCache.put(Integer.valueOf(paramInt), view2);
    } 
    return view2;
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    newPeek();
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\024\n\000\n\002\020\002\n\000\n\002\020 \n\002\030\002\n\002\b\002\020\000\032\0020\0012\032\020\002\032\026\022\004\022\0020\004 \005*\n\022\004\022\0020\004\030\0010\0030\003H\n¢\006\002\b\006"}, d2 = {"<anonymous>", "", "records", "", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;", "kotlin.jvm.PlatformType", "onChanged"}, k = 3, mv = {1, 1, 16})
  static final class PeekActivity$newPeek$1<T> implements Observer<List<? extends StreetPassRecord>> {
    PeekActivity$newPeek$1(RecordListAdapter param1RecordListAdapter) {}
    
    public final void onChanged(List<StreetPassRecord> param1List) {
      RecordListAdapter recordListAdapter = this.$adapter;
      Intrinsics.checkExpressionValueIsNotNull(param1List, "records");
      recordListAdapter.setSourceData$app_release(param1List);
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class PeekActivity$newPeek$2 implements View.OnClickListener {
    PeekActivity$newPeek$2(RecordListAdapter param1RecordListAdapter) {}
    
    public final void onClick(View param1View) {
      if ((List)PeekActivity.access$getViewModel$p(PeekActivity.this).getAllRecords().getValue() != null)
        this.$adapter.setMode(RecordListAdapter.MODE.ALL); 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class PeekActivity$newPeek$3 implements View.OnClickListener {
    PeekActivity$newPeek$3(RecordListAdapter param1RecordListAdapter) {}
    
    public final void onClick(View param1View) {
      if ((List)PeekActivity.access$getViewModel$p(PeekActivity.this).getAllRecords().getValue() != null)
        this.$adapter.setMode(RecordListAdapter.MODE.COLLAPSE); 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class PeekActivity$newPeek$4 implements View.OnClickListener {
    public final void onClick(View param1View) {
      PeekActivity.this.startService();
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class PeekActivity$newPeek$5 implements View.OnClickListener {
    public final void onClick(View param1View) {
      PeekActivity.this.stopService();
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "view", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class PeekActivity$newPeek$6 implements View.OnClickListener {
    public final void onClick(View param1View) {
      Intrinsics.checkExpressionValueIsNotNull(param1View, "view");
      param1View.setEnabled(false);
      AlertDialog.Builder builder = new AlertDialog.Builder((Context)PeekActivity.this);
      builder.setTitle(2131820706).setCancelable(false).setMessage(2131820607).setPositiveButton(2131820606, new DialogInterface.OnClickListener(param1View) {
            public final void onClick(DialogInterface param1DialogInterface, int param1Int) {
              Observable.create(new ObservableOnSubscribe<T>() {
                    public final void subscribe(ObservableEmitter<Boolean> param2ObservableEmitter) {
                      Intrinsics.checkParameterIsNotNull(param2ObservableEmitter, "it");
                      (new StreetPassRecordStorage((Context)PeekActivity.this)).nukeDb();
                      param2ObservableEmitter.onNext(Boolean.valueOf(true));
                    }
                  }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<Boolean>(param1DialogInterface) {
                    public final void accept(Boolean param2Boolean) {
                      Context context = (Context)PeekActivity.this;
                      StringBuilder stringBuilder = new StringBuilder();
                      stringBuilder.append("Database nuked: ");
                      stringBuilder.append(param2Boolean);
                      Toast.makeText(context, stringBuilder.toString(), 0).show();
                      View view = PeekActivity$newPeek$6.null.this.$view;
                      Intrinsics.checkExpressionValueIsNotNull(view, "view");
                      view.setEnabled(true);
                      this.$dialog.cancel();
                    }
                  });
            }
          }).setNegativeButton(2131820690, new DialogInterface.OnClickListener(param1View) {
            public final void onClick(DialogInterface param1DialogInterface, int param1Int) {
              View view = this.$view;
              Intrinsics.checkExpressionValueIsNotNull(view, "view");
              view.setEnabled(true);
              param1DialogInterface.cancel();
            }
          });
      AlertDialog alertDialog = builder.create();
      Intrinsics.checkExpressionValueIsNotNull(alertDialog, "builder.create()");
      alertDialog.show();
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\026\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\0032\006\020\005\032\0020\006H\n¢\006\002\b\007"}, d2 = {"<anonymous>", "", "dialog", "Landroid/content/DialogInterface;", "kotlin.jvm.PlatformType", "which", "", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class null implements DialogInterface.OnClickListener {
    null(View param1View) {}
    
    public final void onClick(DialogInterface param1DialogInterface, int param1Int) {
      Observable.create(new ObservableOnSubscribe<T>() {
            public final void subscribe(ObservableEmitter<Boolean> param2ObservableEmitter) {
              Intrinsics.checkParameterIsNotNull(param2ObservableEmitter, "it");
              (new StreetPassRecordStorage((Context)PeekActivity.this)).nukeDb();
              param2ObservableEmitter.onNext(Boolean.valueOf(true));
            }
          }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<Boolean>(param1DialogInterface) {
            public final void accept(Boolean param2Boolean) {
              Context context = (Context)PeekActivity.this;
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Database nuked: ");
              stringBuilder.append(param2Boolean);
              Toast.makeText(context, stringBuilder.toString(), 0).show();
              View view = PeekActivity$newPeek$6.null.this.$view;
              Intrinsics.checkExpressionValueIsNotNull(view, "view");
              view.setEnabled(true);
              this.$dialog.cancel();
            }
          });
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\024\n\000\n\002\020\002\n\000\n\002\030\002\n\002\020\013\n\002\b\002\020\000\032\0020\0012\024\020\002\032\020\022\f\022\n \005*\004\030\0010\0040\0040\003H\n¢\006\002\b\006"}, d2 = {"<anonymous>", "", "it", "Lio/reactivex/ObservableEmitter;", "", "kotlin.jvm.PlatformType", "subscribe"}, k = 3, mv = {1, 1, 16})
  static final class null<T> implements ObservableOnSubscribe<T> {
    public final void subscribe(ObservableEmitter<Boolean> param1ObservableEmitter) {
      Intrinsics.checkParameterIsNotNull(param1ObservableEmitter, "it");
      (new StreetPassRecordStorage((Context)PeekActivity.this)).nukeDb();
      param1ObservableEmitter.onNext(Boolean.valueOf(true));
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\020\013\n\002\b\003\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\004\b\005\020\006"}, d2 = {"<anonymous>", "", "result", "", "kotlin.jvm.PlatformType", "accept", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 1, 16})
  static final class null<T> implements Consumer<Boolean> {
    public final void accept(Boolean param1Boolean) {
      Context context = (Context)PeekActivity.this;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Database nuked: ");
      stringBuilder.append(param1Boolean);
      Toast.makeText(context, stringBuilder.toString(), 0).show();
      View view = PeekActivity$newPeek$6.null.this.$view;
      Intrinsics.checkExpressionValueIsNotNull(view, "view");
      view.setEnabled(true);
      this.$dialog.cancel();
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\026\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\0032\006\020\005\032\0020\006H\n¢\006\002\b\007"}, d2 = {"<anonymous>", "", "dialog", "Landroid/content/DialogInterface;", "kotlin.jvm.PlatformType", "which", "", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class null implements DialogInterface.OnClickListener {
    null(View param1View) {}
    
    public final void onClick(DialogInterface param1DialogInterface, int param1Int) {
      View view = this.$view;
      Intrinsics.checkExpressionValueIsNotNull(view, "view");
      view.setEnabled(true);
      param1DialogInterface.cancel();
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "view", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class PeekActivity$newPeek$7 implements View.OnClickListener {
    public final void onClick(View param1View) {
      Intent intent = new Intent((Context)PeekActivity.this, PlotActivity.class);
      intent.putExtra("time_period", PeekActivity.this.nextTimePeriod());
      PeekActivity.this.startActivity(intent);
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/PeekActivity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */