package ca.albertahealthservices.contacttracing.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import ca.albertahealthservices.contacttracing.R;
import ca.albertahealthservices.contacttracing.TracerApp;
import ca.albertahealthservices.contacttracing.Utils;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.status.persistence.StatusRecord;
import ca.albertahealthservices.contacttracing.status.persistence.StatusRecordStorage;
import ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecord;
import ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecordStorage;
import com.google.gson.Gson;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import org.json.JSONObject;

@Metadata(bv = {1, 0, 3}, d1 = {"\000d\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020!\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\020\013\n\002\b\004\030\000 &2\0020\0012\0020\002:\002&'B\005¢\006\002\020\003J\b\020\017\032\0020\020H\002J\021\020\021\032\0020\022H@ø\001\000¢\006\002\020\023J\b\020\024\032\0020\016H\002J\b\020\025\032\0020\026H\002J&\020\027\032\004\030\0010\0302\006\020\031\032\0020\0322\b\020\033\032\004\030\0010\0342\b\020\035\032\004\030\0010\036H\026J\b\020\037\032\0020\020H\026J\032\020 \032\0020\0202\006\020!\032\0020\0302\b\020\035\032\004\030\0010\036H\026J\b\020\"\032\0020\026H\002J\020\020#\032\0020$2\006\020%\032\0020\016H\002R\022\020\004\032\0020\005X\005¢\006\006\032\004\b\006\020\007R\020\020\b\032\004\030\0010\tX\016¢\006\002\n\000R\024\020\n\032\b\022\004\022\0020\f0\013X\016¢\006\002\n\000R\020\020\r\032\004\030\0010\016X\016¢\006\002\n\000\002\004\n\002\b\031¨\006("}, d2 = {"Lca/albertahealthservices/contacttracing/fragment/EnterPinFragment;", "Landroidx/fragment/app/Fragment;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "disposeObj", "Lio/reactivex/disposables/Disposable;", "otpInputs", "", "Landroid/widget/EditText;", "uploadToken", "", "clearInputs", "", "getEncounterJSON", "Lorg/json/JSONObject;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOtp", "getUploadToken", "Lkotlinx/coroutines/Job;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onViewCreated", "view", "uploadData", "validateOtp", "", "otp", "Companion", "OTPTextWatcher", "app_release"}, k = 1, mv = {1, 1, 16})
public final class EnterPinFragment extends Fragment implements CoroutineScope {
  public static final Companion Companion = new Companion(null);
  
  public static final String TAG = "UploadFragment";
  
  public static final String TEMP_UPLOAD_FILE_NAME = "StreetPassRecord.json";
  
  private HashMap _$_findViewCache;
  
  private Disposable disposeObj;
  
  private List<EditText> otpInputs = new ArrayList<>();
  
  private String uploadToken;
  
  private final void clearInputs() {
    EditText editText = (EditText)_$_findCachedViewById(R.id.otp_et6);
    if (editText != null)
      editText.setText(""); 
    editText = (EditText)_$_findCachedViewById(R.id.otp_et5);
    if (editText != null)
      editText.setText(""); 
    editText = (EditText)_$_findCachedViewById(R.id.otp_et4);
    if (editText != null)
      editText.setText(""); 
    editText = (EditText)_$_findCachedViewById(R.id.otp_et3);
    if (editText != null)
      editText.setText(""); 
    editText = (EditText)_$_findCachedViewById(R.id.otp_et2);
    if (editText != null)
      editText.setText(""); 
    editText = (EditText)_$_findCachedViewById(R.id.otp_et1);
    if (editText != null)
      editText.setText(""); 
  }
  
  private final String getOtp() {
    Iterator<EditText> iterator = this.otpInputs.iterator();
    String str;
    for (str = ""; iterator.hasNext(); str = stringBuilder.toString()) {
      EditText editText = iterator.next();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append(editText.getText());
    } 
    return str;
  }
  
  private final Job getUploadToken() {
    return BuildersKt.launch$default(this, null, null, new EnterPinFragment$getUploadToken$1(null), 3, null);
  }
  
  private final Job uploadData() {
    return BuildersKt.launch$default(this, null, null, new EnterPinFragment$uploadData$1(null), 3, null);
  }
  
  private final boolean validateOtp(String paramString) {
    boolean bool;
    if (paramString.length() >= 6) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
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
      view2 = getView();
      if (view2 == null)
        return null; 
      view2 = view2.findViewById(paramInt);
      this._$_findViewCache.put(Integer.valueOf(paramInt), view2);
    } 
    return view2;
  }
  
  public CoroutineContext getCoroutineContext() {
    return this.$$delegate_0.getCoroutineContext();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramLayoutInflater, "inflater");
    return paramLayoutInflater.inflate(2131492924, paramViewGroup, false);
  }
  
  public void onDestroy() {
    CoroutineScopeKt.cancel$default(this, null, 1, null);
    super.onDestroy();
    Disposable disposable = this.disposeObj;
    if (disposable != null)
      disposable.dispose(); 
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    super.onViewCreated(paramView, paramBundle);
    clearInputs();
    LinearLayout linearLayout2 = (LinearLayout)_$_findCachedViewById(R.id.enterPinActionButton);
    if (linearLayout2 != null)
      linearLayout2.setEnabled(false); 
    List<EditText> list5 = this.otpInputs;
    EditText editText3 = (EditText)_$_findCachedViewById(R.id.otp_et1);
    Intrinsics.checkExpressionValueIsNotNull(editText3, "otp_et1");
    list5.add(editText3);
    List<EditText> list2 = this.otpInputs;
    EditText editText6 = (EditText)_$_findCachedViewById(R.id.otp_et2);
    Intrinsics.checkExpressionValueIsNotNull(editText6, "otp_et2");
    list2.add(editText6);
    list2 = this.otpInputs;
    editText6 = (EditText)_$_findCachedViewById(R.id.otp_et3);
    Intrinsics.checkExpressionValueIsNotNull(editText6, "otp_et3");
    list2.add(editText6);
    List<EditText> list4 = this.otpInputs;
    EditText editText2 = (EditText)_$_findCachedViewById(R.id.otp_et4);
    Intrinsics.checkExpressionValueIsNotNull(editText2, "otp_et4");
    list4.add(editText2);
    List<EditText> list1 = this.otpInputs;
    EditText editText5 = (EditText)_$_findCachedViewById(R.id.otp_et5);
    Intrinsics.checkExpressionValueIsNotNull(editText5, "otp_et5");
    list1.add(editText5);
    List<EditText> list3 = this.otpInputs;
    EditText editText1 = (EditText)_$_findCachedViewById(R.id.otp_et6);
    Intrinsics.checkExpressionValueIsNotNull(editText1, "otp_et6");
    list3.add(editText1);
    EditText editText4 = (EditText)_$_findCachedViewById(R.id.otp_et1);
    if (editText4 != null) {
      editText1 = (EditText)_$_findCachedViewById(R.id.otp_et1);
      Intrinsics.checkExpressionValueIsNotNull(editText1, "otp_et1");
      editText4.addTextChangedListener(new OTPTextWatcher(editText1));
    } 
    editText1 = (EditText)_$_findCachedViewById(R.id.otp_et2);
    if (editText1 != null) {
      editText4 = (EditText)_$_findCachedViewById(R.id.otp_et2);
      Intrinsics.checkExpressionValueIsNotNull(editText4, "otp_et2");
      editText1.addTextChangedListener(new OTPTextWatcher(editText4));
    } 
    editText4 = (EditText)_$_findCachedViewById(R.id.otp_et3);
    if (editText4 != null) {
      editText1 = (EditText)_$_findCachedViewById(R.id.otp_et3);
      Intrinsics.checkExpressionValueIsNotNull(editText1, "otp_et3");
      editText4.addTextChangedListener(new OTPTextWatcher(editText1));
    } 
    editText1 = (EditText)_$_findCachedViewById(R.id.otp_et4);
    if (editText1 != null) {
      editText4 = (EditText)_$_findCachedViewById(R.id.otp_et4);
      Intrinsics.checkExpressionValueIsNotNull(editText4, "otp_et4");
      editText1.addTextChangedListener(new OTPTextWatcher(editText4));
    } 
    editText1 = (EditText)_$_findCachedViewById(R.id.otp_et5);
    if (editText1 != null) {
      editText4 = (EditText)_$_findCachedViewById(R.id.otp_et5);
      Intrinsics.checkExpressionValueIsNotNull(editText4, "otp_et5");
      editText1.addTextChangedListener(new OTPTextWatcher(editText4));
    } 
    editText1 = (EditText)_$_findCachedViewById(R.id.otp_et6);
    if (editText1 != null) {
      editText4 = (EditText)_$_findCachedViewById(R.id.otp_et6);
      Intrinsics.checkExpressionValueIsNotNull(editText4, "otp_et6");
      editText1.addTextChangedListener(new OTPTextWatcher(editText4));
    } 
    if (this.uploadToken == null)
      getUploadToken(); 
    LinearLayout linearLayout1 = (LinearLayout)_$_findCachedViewById(R.id.enterPinActionButton);
    if (linearLayout1 != null)
      linearLayout1.setOnClickListener(new EnterPinFragment$onViewCreated$1()); 
    AppCompatImageView appCompatImageView = (AppCompatImageView)_$_findCachedViewById(R.id.enterPinBackButton);
    if (appCompatImageView != null)
      appCompatImageView.setOnClickListener(new EnterPinFragment$onViewCreated$2()); 
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000¨\006\006"}, d2 = {"Lca/albertahealthservices/contacttracing/fragment/EnterPinFragment$Companion;", "", "()V", "TAG", "", "TEMP_UPLOAD_FILE_NAME", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class Companion {
    private Companion() {}
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000.\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\r\n\000\n\002\020\b\n\002\b\004\b\004\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\020\020\007\032\0020\b2\006\020\t\032\0020\nH\026J(\020\013\032\0020\b2\006\020\f\032\0020\r2\006\020\016\032\0020\0172\006\020\020\032\0020\0172\006\020\021\032\0020\017H\026J(\020\022\032\0020\b2\006\020\f\032\0020\r2\006\020\016\032\0020\0172\006\020\020\032\0020\0172\006\020\021\032\0020\017H\026R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\005\020\006¨\006\023"}, d2 = {"Lca/albertahealthservices/contacttracing/fragment/EnterPinFragment$OTPTextWatcher;", "Landroid/text/TextWatcher;", "view", "Landroid/widget/EditText;", "(Lca/albertahealthservices/contacttracing/fragment/EnterPinFragment;Landroid/widget/EditText;)V", "getView", "()Landroid/widget/EditText;", "afterTextChanged", "", "editable", "Landroid/text/Editable;", "beforeTextChanged", "arg0", "", "arg1", "", "arg2", "arg3", "onTextChanged", "app_release"}, k = 1, mv = {1, 1, 16})
  public final class OTPTextWatcher implements TextWatcher {
    private final EditText view;
    
    public OTPTextWatcher(EditText param1EditText) {
      this.view = param1EditText;
    }
    
    public void afterTextChanged(Editable param1Editable) {
      Intrinsics.checkParameterIsNotNull(param1Editable, "editable");
      String str = param1Editable.toString();
      int i = this.view.getId();
      boolean bool1 = false;
      boolean bool2 = false;
      boolean bool3 = false;
      boolean bool4 = false;
      boolean bool5 = true;
      switch (i) {
        default:
          return;
        case 2131296511:
          bool5 = bool4;
          if (str.length() == 0)
            bool5 = true; 
          if (bool5)
            ((EditText)EnterPinFragment.this.otpInputs.get(4)).requestFocus(); 
        case 2131296510:
          if (str.length() == 1) {
            str = EnterPinFragment.this.otpInputs.get(5);
          } else {
            bool5 = bool1;
            if (str.length() == 0)
              bool5 = true; 
            if (bool5) {
              str = EnterPinFragment.this.otpInputs.get(3);
            } else {
            
            } 
          } 
          ((EditText)str).requestFocus();
        case 2131296509:
          if (str.length() == 1) {
            str = EnterPinFragment.this.otpInputs.get(4);
          } else {
            bool5 = bool2;
            if (str.length() == 0)
              bool5 = true; 
            if (bool5) {
              str = EnterPinFragment.this.otpInputs.get(2);
            } else {
            
            } 
          } 
          ((EditText)str).requestFocus();
        case 2131296508:
          if (str.length() == 1) {
            str = EnterPinFragment.this.otpInputs.get(3);
          } else {
            bool5 = bool3;
            if (str.length() == 0)
              bool5 = true; 
            if (bool5) {
              str = EnterPinFragment.this.otpInputs.get(1);
            } else {
            
            } 
          } 
          ((EditText)str).requestFocus();
        case 2131296507:
          if (str.length() == 1) {
            str = EnterPinFragment.this.otpInputs.get(2);
          } else {
            if (str.length() != 0)
              bool5 = false; 
            if (bool5) {
              str = EnterPinFragment.this.otpInputs.get(0);
            } else {
            
            } 
          } 
          ((EditText)str).requestFocus();
        case 2131296506:
          break;
      } 
      if (str.length() == 1)
        ((EditText)EnterPinFragment.this.otpInputs.get(1)).requestFocus(); 
    }
    
    public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
      Intrinsics.checkParameterIsNotNull(param1CharSequence, "arg0");
    }
    
    public final EditText getView() {
      return this.view;
    }
    
    public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
      Intrinsics.checkParameterIsNotNull(param1CharSequence, "arg0");
      EnterPinFragment enterPinFragment = EnterPinFragment.this;
      if (enterPinFragment.validateOtp(enterPinFragment.getOtp())) {
        Utils utils = Utils.INSTANCE;
        Context context = this.view.getContext();
        Intrinsics.checkExpressionValueIsNotNull(context, "view.context");
        utils.hideKeyboardFrom(context, (View)this.view);
        LinearLayout linearLayout = (LinearLayout)EnterPinFragment.this._$_findCachedViewById(R.id.enterPinActionButton);
        if (linearLayout != null)
          linearLayout.setEnabled(true); 
        TextView textView = (TextView)EnterPinFragment.this._$_findCachedViewById(R.id.enterPinButtonText);
        if (textView != null)
          textView.setText(EnterPinFragment.this.getString(2131820777)); 
      } else {
        LinearLayout linearLayout = (LinearLayout)EnterPinFragment.this._$_findCachedViewById(R.id.enterPinActionButton);
        if (linearLayout != null)
          linearLayout.setEnabled(false); 
        TextView textView = (TextView)EnterPinFragment.this._$_findCachedViewById(R.id.enterPinButtonText);
        if (textView != null)
          textView.setText(EnterPinFragment.this.getString(2131820762)); 
      } 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\024\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\b\003\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005¨\006\006"}, d2 = {"<anonymous>", "", "exportedData", "Lca/albertahealthservices/contacttracing/fragment/ExportData;", "kotlin.jvm.PlatformType", "accept", "ca/albertahealthservices/contacttracing/fragment/EnterPinFragment$getEncounterJSON$2$2"}, k = 3, mv = {1, 1, 16})
  static final class EnterPinFragment$getEncounterJSON$$inlined$suspendCoroutine$lambda$1<T> implements Consumer<ExportData> {
    EnterPinFragment$getEncounterJSON$$inlined$suspendCoroutine$lambda$1(EnterPinFragment param1EnterPinFragment) {}
    
    public final void accept(ExportData param1ExportData) {
      CentralLog.Companion companion1 = CentralLog.Companion;
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("records: ");
      stringBuilder2.append(param1ExportData.getRecordList());
      companion1.d("UploadFragment", stringBuilder2.toString());
      CentralLog.Companion companion2 = CentralLog.Companion;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("status: ");
      stringBuilder1.append(param1ExportData.getStatusList());
      companion2.d("UploadFragment", stringBuilder1.toString());
      Gson gson = new Gson();
      List<StreetPassRecord> list = param1ExportData.getRecordList();
      ArrayList<StreetPassRecord> arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
      for (StreetPassRecord streetPassRecord : list) {
        streetPassRecord.setTimestamp(streetPassRecord.getTimestamp() / 1000L);
        arrayList.add(streetPassRecord);
      } 
      arrayList = arrayList;
      list = (List)param1ExportData.getStatusList();
      ArrayList<StatusRecord> arrayList1 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
      for (StatusRecord statusRecord : list) {
        statusRecord.setTimestamp(statusRecord.getTimestamp() / 1000L);
        arrayList1.add(statusRecord);
      } 
      ArrayList<StatusRecord> arrayList2 = arrayList1;
      HashMap<Object, Object> hashMap = new HashMap<>();
      String str = EnterPinFragment.this.uploadToken;
      if (str != null) {
        hashMap.put("token", str);
        hashMap.put("records", arrayList);
        hashMap.put("events", arrayList2);
        String str1 = gson.toJson(hashMap);
        Continuation continuation = this.$cont;
        JSONObject jSONObject = new JSONObject(str1);
        Result.Companion companion = Result.Companion;
        continuation.resumeWith(Result.constructor-impl(jSONObject));
        return;
      } 
      throw new TypeCastException("null cannot be cast to non-null type kotlin.Any");
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\030\n\000\n\002\030\002\n\000\n\002\020 \n\002\030\002\n\000\n\002\030\002\n\000\020\000\032\0020\0012\f\020\002\032\b\022\004\022\0020\0040\0032\f\020\005\032\b\022\004\022\0020\0060\003H\n¢\006\002\b\007"}, d2 = {"<anonymous>", "Lca/albertahealthservices/contacttracing/fragment/ExportData;", "records", "", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;", "status", "Lca/albertahealthservices/contacttracing/status/persistence/StatusRecord;", "apply"}, k = 3, mv = {1, 1, 16})
  static final class EnterPinFragment$getEncounterJSON$2$1<T1, T2, R> implements BiFunction<List<? extends StreetPassRecord>, List<? extends StatusRecord>, ExportData> {
    public static final EnterPinFragment$getEncounterJSON$2$1 INSTANCE = new EnterPinFragment$getEncounterJSON$2$1();
    
    public final ExportData apply(List<StreetPassRecord> param1List, List<StatusRecord> param1List1) {
      Intrinsics.checkParameterIsNotNull(param1List, "records");
      Intrinsics.checkParameterIsNotNull(param1List1, "status");
      return new ExportData(param1List, param1List1);
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\030\n\000\n\002\020\002\n\000\n\002\030\002\n\002\020 \n\002\030\002\n\002\b\002\020\000\032\0020\0012 \020\002\032\034\022\030\022\026\022\004\022\0020\005 \006*\n\022\004\022\0020\005\030\0010\0040\0040\003H\n¢\006\002\b\007"}, d2 = {"<anonymous>", "", "it", "Lio/reactivex/ObservableEmitter;", "", "Lca/albertahealthservices/contacttracing/status/persistence/StatusRecord;", "kotlin.jvm.PlatformType", "subscribe"}, k = 3, mv = {1, 1, 16})
  static final class EnterPinFragment$getEncounterJSON$2$observableStatusRecords$1<T> implements ObservableOnSubscribe<T> {
    public static final EnterPinFragment$getEncounterJSON$2$observableStatusRecords$1 INSTANCE = new EnterPinFragment$getEncounterJSON$2$observableStatusRecords$1();
    
    public final void subscribe(ObservableEmitter<List<StatusRecord>> param1ObservableEmitter) {
      Intrinsics.checkParameterIsNotNull(param1ObservableEmitter, "it");
      param1ObservableEmitter.onNext((new StatusRecordStorage(TracerApp.Companion.getAppContext())).getAllRecords());
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\030\n\000\n\002\020\002\n\000\n\002\030\002\n\002\020 \n\002\030\002\n\002\b\002\020\000\032\0020\0012 \020\002\032\034\022\030\022\026\022\004\022\0020\005 \006*\n\022\004\022\0020\005\030\0010\0040\0040\003H\n¢\006\002\b\007"}, d2 = {"<anonymous>", "", "it", "Lio/reactivex/ObservableEmitter;", "", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;", "kotlin.jvm.PlatformType", "subscribe"}, k = 3, mv = {1, 1, 16})
  static final class EnterPinFragment$getEncounterJSON$2$observableStreetRecords$1<T> implements ObservableOnSubscribe<T> {
    public static final EnterPinFragment$getEncounterJSON$2$observableStreetRecords$1 INSTANCE = new EnterPinFragment$getEncounterJSON$2$observableStreetRecords$1();
    
    public final void subscribe(ObservableEmitter<List<StreetPassRecord>> param1ObservableEmitter) {
      Intrinsics.checkParameterIsNotNull(param1ObservableEmitter, "it");
      param1ObservableEmitter.onNext((new StreetPassRecordStorage(TracerApp.Companion.getAppContext())).getAllRecords());
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\016\n\000\n\002\020\002\n\002\030\002\n\002\b\002\020\000\032\0020\001*\0020\002H@¢\006\004\b\003\020\004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 16})
  @DebugMetadata(c = "ca.albertahealthservices.contacttracing.fragment.EnterPinFragment$getUploadToken$1", f = "EnterPinFragment.kt", i = {0, 0, 0}, l = {125}, m = "invokeSuspend", n = {"$this$launch", "myParentFragment", "queryParams"}, s = {"L$0", "L$1", "L$2"})
  static final class EnterPinFragment$getUploadToken$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    Object L$0;
    
    Object L$1;
    
    Object L$2;
    
    int label;
    
    private CoroutineScope p$;
    
    EnterPinFragment$getUploadToken$1(Continuation param1Continuation) {
      super(2, param1Continuation);
    }
    
    public final Continuation<Unit> create(Object param1Object, Continuation<?> param1Continuation) {
      Intrinsics.checkParameterIsNotNull(param1Continuation, "completion");
      EnterPinFragment$getUploadToken$1 enterPinFragment$getUploadToken$1 = new EnterPinFragment$getUploadToken$1(param1Continuation);
      enterPinFragment$getUploadToken$1.p$ = (CoroutineScope)param1Object;
      return (Continuation<Unit>)enterPinFragment$getUploadToken$1;
    }
    
    public final Object invoke(Object param1Object1, Object param1Object2) {
      return ((EnterPinFragment$getUploadToken$1)create(param1Object1, (Continuation)param1Object2)).invokeSuspend(Unit.INSTANCE);
    }
    
    public final Object invokeSuspend(Object param1Object) {
      // Byte code:
      //   0: invokestatic getCOROUTINE_SUSPENDED : ()Ljava/lang/Object;
      //   3: astore_2
      //   4: aload_0
      //   5: getfield label : I
      //   8: istore_3
      //   9: iload_3
      //   10: ifeq -> 61
      //   13: iload_3
      //   14: iconst_1
      //   15: if_icmpne -> 51
      //   18: aload_0
      //   19: getfield L$2 : Ljava/lang/Object;
      //   22: checkcast java/util/HashMap
      //   25: astore #4
      //   27: aload_0
      //   28: getfield L$1 : Ljava/lang/Object;
      //   31: checkcast ca/albertahealthservices/contacttracing/fragment/UploadPageFragment
      //   34: astore #4
      //   36: aload_0
      //   37: getfield L$0 : Ljava/lang/Object;
      //   40: checkcast kotlinx/coroutines/CoroutineScope
      //   43: astore_2
      //   44: aload_1
      //   45: invokestatic throwOnFailure : (Ljava/lang/Object;)V
      //   48: goto -> 194
      //   51: new java/lang/IllegalStateException
      //   54: dup
      //   55: ldc 'call to 'resume' before 'invoke' with coroutine'
      //   57: invokespecial <init> : (Ljava/lang/String;)V
      //   60: athrow
      //   61: aload_1
      //   62: invokestatic throwOnFailure : (Ljava/lang/Object;)V
      //   65: aload_0
      //   66: getfield p$ : Lkotlinx/coroutines/CoroutineScope;
      //   69: astore_1
      //   70: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   73: ldc 'EnterPinFragment'
      //   75: ldc 'Fetching upload token'
      //   77: invokevirtual d : (Ljava/lang/String;Ljava/lang/String;)V
      //   80: aload_0
      //   81: getfield this$0 : Lca/albertahealthservices/contacttracing/fragment/EnterPinFragment;
      //   84: invokevirtual getParentFragment : ()Landroidx/fragment/app/Fragment;
      //   87: astore #4
      //   89: aload #4
      //   91: ifnull -> 355
      //   94: aload #4
      //   96: checkcast ca/albertahealthservices/contacttracing/fragment/UploadPageFragment
      //   99: astore #4
      //   101: aload #4
      //   103: invokevirtual turnOnLoadingProgress : ()V
      //   106: new java/util/HashMap
      //   109: dup
      //   110: invokespecial <init> : ()V
      //   113: astore #5
      //   115: aload #5
      //   117: checkcast java/util/Map
      //   120: ldc 'userId'
      //   122: getstatic ca/albertahealthservices/contacttracing/Preference.INSTANCE : Lca/albertahealthservices/contacttracing/Preference;
      //   125: aload_0
      //   126: getfield this$0 : Lca/albertahealthservices/contacttracing/fragment/EnterPinFragment;
      //   129: invokevirtual getContext : ()Landroid/content/Context;
      //   132: invokevirtual getUUID : (Landroid/content/Context;)Ljava/lang/String;
      //   135: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   140: pop
      //   141: getstatic ca/albertahealthservices/contacttracing/api/Request.INSTANCE : Lca/albertahealthservices/contacttracing/api/Request;
      //   144: astore #6
      //   146: aload_0
      //   147: aload_1
      //   148: putfield L$0 : Ljava/lang/Object;
      //   151: aload_0
      //   152: aload #4
      //   154: putfield L$1 : Ljava/lang/Object;
      //   157: aload_0
      //   158: aload #5
      //   160: putfield L$2 : Ljava/lang/Object;
      //   163: aload_0
      //   164: iconst_1
      //   165: putfield label : I
      //   168: aload #6
      //   170: ldc '/adapters/getUploadTokenAdapter/getUploadToken'
      //   172: ldc 'GET'
      //   174: iconst_0
      //   175: aconst_null
      //   176: aconst_null
      //   177: aload #5
      //   179: aload_0
      //   180: bipush #28
      //   182: aconst_null
      //   183: invokestatic runRequest$default : (Lca/albertahealthservices/contacttracing/api/Request;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Lorg/json/JSONObject;Ljava/util/HashMap;Lkotlin/coroutines/Continuation;ILjava/lang/Object;)Ljava/lang/Object;
      //   186: astore_1
      //   187: aload_1
      //   188: aload_2
      //   189: if_acmpne -> 194
      //   192: aload_2
      //   193: areturn
      //   194: aload_1
      //   195: checkcast ca/albertahealthservices/contacttracing/api/Response
      //   198: astore_2
      //   199: aload_2
      //   200: invokevirtual getData : ()Lorg/json/JSONObject;
      //   203: astore_1
      //   204: aload_1
      //   205: ifnull -> 218
      //   208: aload_1
      //   209: ldc 'token'
      //   211: invokevirtual get : (Ljava/lang/String;)Ljava/lang/Object;
      //   214: astore_1
      //   215: goto -> 220
      //   218: aconst_null
      //   219: astore_1
      //   220: aload_2
      //   221: invokevirtual isSuccess : ()Z
      //   224: ifeq -> 289
      //   227: aload_2
      //   228: invokevirtual getStatus : ()Ljava/lang/Integer;
      //   231: astore_2
      //   232: aload_2
      //   233: ifnonnull -> 239
      //   236: goto -> 289
      //   239: aload_2
      //   240: invokevirtual intValue : ()I
      //   243: sipush #200
      //   246: if_icmpne -> 289
      //   249: aload_1
      //   250: instanceof org/json/JSONObject
      //   253: ifeq -> 259
      //   256: goto -> 289
      //   259: aload_0
      //   260: getfield this$0 : Lca/albertahealthservices/contacttracing/fragment/EnterPinFragment;
      //   263: astore_2
      //   264: aload_1
      //   265: ifnull -> 279
      //   268: aload_2
      //   269: aload_1
      //   270: checkcast java/lang/String
      //   273: invokestatic access$setUploadToken$p : (Lca/albertahealthservices/contacttracing/fragment/EnterPinFragment;Ljava/lang/String;)V
      //   276: goto -> 346
      //   279: new kotlin/TypeCastException
      //   282: dup
      //   283: ldc 'null cannot be cast to non-null type kotlin.String'
      //   285: invokespecial <init> : (Ljava/lang/String;)V
      //   288: athrow
      //   289: aload_0
      //   290: getfield this$0 : Lca/albertahealthservices/contacttracing/fragment/EnterPinFragment;
      //   293: getstatic ca/albertahealthservices/contacttracing/R$id.enterPinFragmentErrorMessage : I
      //   296: invokevirtual _$_findCachedViewById : (I)Landroid/view/View;
      //   299: checkcast androidx/appcompat/widget/AppCompatTextView
      //   302: astore_1
      //   303: aload_1
      //   304: ifnull -> 323
      //   307: aload_1
      //   308: aload_0
      //   309: getfield this$0 : Lca/albertahealthservices/contacttracing/fragment/EnterPinFragment;
      //   312: ldc 2131820616
      //   314: invokevirtual getString : (I)Ljava/lang/String;
      //   317: checkcast java/lang/CharSequence
      //   320: invokevirtual setText : (Ljava/lang/CharSequence;)V
      //   323: aload_0
      //   324: getfield this$0 : Lca/albertahealthservices/contacttracing/fragment/EnterPinFragment;
      //   327: getstatic ca/albertahealthservices/contacttracing/R$id.enterPinFragmentErrorMessage : I
      //   330: invokevirtual _$_findCachedViewById : (I)Landroid/view/View;
      //   333: checkcast androidx/appcompat/widget/AppCompatTextView
      //   336: astore_1
      //   337: aload_1
      //   338: ifnull -> 346
      //   341: aload_1
      //   342: iconst_0
      //   343: invokevirtual setVisibility : (I)V
      //   346: aload #4
      //   348: invokevirtual turnOffLoadingProgress : ()V
      //   351: getstatic kotlin/Unit.INSTANCE : Lkotlin/Unit;
      //   354: areturn
      //   355: new kotlin/TypeCastException
      //   358: dup
      //   359: ldc 'null cannot be cast to non-null type ca.albertahealthservices.contacttracing.fragment.UploadPageFragment'
      //   361: invokespecial <init> : (Ljava/lang/String;)V
      //   364: athrow
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class EnterPinFragment$onViewCreated$1 implements View.OnClickListener {
    public final void onClick(View param1View) {
      String str = EnterPinFragment.this.uploadToken;
      if (str != null && str.equals(EnterPinFragment.this.getOtp()) == true) {
        AppCompatTextView appCompatTextView = (AppCompatTextView)EnterPinFragment.this._$_findCachedViewById(R.id.enterPinFragmentErrorMessage);
        Intrinsics.checkExpressionValueIsNotNull(appCompatTextView, "enterPinFragmentErrorMessage");
        appCompatTextView.setVisibility(4);
        EnterPinFragment.this.uploadData();
      } else {
        EnterPinFragment.this.clearInputs();
        AppCompatTextView appCompatTextView = (AppCompatTextView)EnterPinFragment.this._$_findCachedViewById(R.id.enterPinFragmentErrorMessage);
        Intrinsics.checkExpressionValueIsNotNull(appCompatTextView, "enterPinFragmentErrorMessage");
        appCompatTextView.setText(EnterPinFragment.this.getString(2131820639));
        appCompatTextView = (AppCompatTextView)EnterPinFragment.this._$_findCachedViewById(R.id.enterPinFragmentErrorMessage);
        Intrinsics.checkExpressionValueIsNotNull(appCompatTextView, "enterPinFragmentErrorMessage");
        appCompatTextView.setVisibility(0);
      } 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class EnterPinFragment$onViewCreated$2 implements View.OnClickListener {
    public final void onClick(View param1View) {
      Fragment fragment = EnterPinFragment.this.getParentFragment();
      if (fragment != null) {
        ((UploadPageFragment)fragment).popStack();
        return;
      } 
      throw new TypeCastException("null cannot be cast to non-null type ca.albertahealthservices.contacttracing.fragment.UploadPageFragment");
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\016\n\000\n\002\020\002\n\002\030\002\n\002\b\002\020\000\032\0020\001*\0020\002H@¢\006\004\b\003\020\004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 16})
  @DebugMetadata(c = "ca.albertahealthservices.contacttracing.fragment.EnterPinFragment$uploadData$1", f = "EnterPinFragment.kt", i = {0, 0, 1, 1, 1, 1}, l = {141, 150}, m = "invokeSuspend", n = {"$this$launch", "myParentFragment", "$this$launch", "myParentFragment", "jsonData", "queryParams"}, s = {"L$0", "L$1", "L$0", "L$1", "L$2", "L$3"})
  static final class EnterPinFragment$uploadData$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    Object L$0;
    
    Object L$1;
    
    Object L$2;
    
    Object L$3;
    
    int label;
    
    private CoroutineScope p$;
    
    EnterPinFragment$uploadData$1(Continuation param1Continuation) {
      super(2, param1Continuation);
    }
    
    public final Continuation<Unit> create(Object param1Object, Continuation<?> param1Continuation) {
      Intrinsics.checkParameterIsNotNull(param1Continuation, "completion");
      EnterPinFragment$uploadData$1 enterPinFragment$uploadData$1 = new EnterPinFragment$uploadData$1(param1Continuation);
      enterPinFragment$uploadData$1.p$ = (CoroutineScope)param1Object;
      return (Continuation<Unit>)enterPinFragment$uploadData$1;
    }
    
    public final Object invoke(Object param1Object1, Object param1Object2) {
      return ((EnterPinFragment$uploadData$1)create(param1Object1, (Continuation)param1Object2)).invokeSuspend(Unit.INSTANCE);
    }
    
    public final Object invokeSuspend(Object param1Object) {
      Object object1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      int i = this.label;
      if (i != 0) {
        if (i != 1) {
          if (i == 2) {
            HashMap hashMap = (HashMap)this.L$3;
            JSONObject jSONObject = (JSONObject)this.L$2;
            UploadPageFragment uploadPageFragment = (UploadPageFragment)this.L$1;
            CoroutineScope coroutineScope = (CoroutineScope)this.L$0;
            ResultKt.throwOnFailure(param1Object);
            object2 = param1Object;
            param1Object = uploadPageFragment;
          } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
          } 
        } else {
          UploadPageFragment uploadPageFragment = (UploadPageFragment)this.L$1;
          object2 = this.L$0;
          ResultKt.throwOnFailure(param1Object);
          Object object = param1Object;
          param1Object = uploadPageFragment;
          object = object;
        } 
      } else {
        ResultKt.throwOnFailure(param1Object);
        object2 = this.p$;
        param1Object = EnterPinFragment.this.getParentFragment();
        if (param1Object != null) {
          param1Object = param1Object;
          param1Object.turnOnLoadingProgress();
          EnterPinFragment enterPinFragment = EnterPinFragment.this;
          this.L$0 = object2;
          this.L$1 = param1Object;
          this.label = 1;
          object = enterPinFragment.getEncounterJSON((Continuation<? super JSONObject>)this);
          if (object == object1)
            return object1; 
        } else {
          throw new TypeCastException("null cannot be cast to non-null type ca.albertahealthservices.contacttracing.fragment.UploadPageFragment");
        } 
        Object object = object;
      } 
      Object object2 = object2;
      param1Object.turnOffLoadingProgress();
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/fragment/EnterPinFragment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */