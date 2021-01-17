package ca.albertahealthservices.contacttracing.onboarding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import ca.albertahealthservices.contacttracing.Preference;
import ca.albertahealthservices.contacttracing.R;
import ca.albertahealthservices.contacttracing.Utils;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;

@Metadata(bv = {1, 0, 3}, d1 = {"\0002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\030\0002\0020\0012\0020\002B\005¢\006\002\020\003J\022\020\n\032\0020\0132\b\020\f\032\004\030\0010\rH\024J\b\020\016\032\0020\013H\024J\b\020\017\032\0020\020H\002R\016\020\004\032\0020\005X\016¢\006\002\n\000R\022\020\006\032\0020\007X\005¢\006\006\032\004\b\b\020\t¨\006\021"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/PreOnboardingActivity;", "Landroidx/fragment/app/FragmentActivity;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "TAG", "", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "showAppRegistrationStatus", "Lkotlinx/coroutines/Job;", "app_release"}, k = 1, mv = {1, 1, 16})
public final class PreOnboardingActivity extends FragmentActivity implements CoroutineScope {
  private String TAG = "PreOnboardingActivity";
  
  private HashMap _$_findViewCache;
  
  private final Job showAppRegistrationStatus() {
    return BuildersKt.launch$default(this, null, null, new PreOnboardingActivity$showAppRegistrationStatus$1(null), 3, null);
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
  
  public CoroutineContext getCoroutineContext() {
    return this.$$delegate_0.getCoroutineContext();
  }
  
  protected void onCreate(Bundle paramBundle) {
    String str;
    super.onCreate(paramBundle);
    setContentView(2131492930);
    if (StringsKt.contains$default(Utils.INSTANCE.getServerURL(), "stg", false, 2, null)) {
      str = ".S";
    } else {
      str = "";
    } 
    AppCompatTextView appCompatTextView = (AppCompatTextView)_$_findCachedViewById(R.id.tv_app_version);
    if (appCompatTextView != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(getString(2131820582));
      stringBuilder.append("1.5.0");
      stringBuilder.append(str);
      appCompatTextView.setText(stringBuilder.toString());
    } 
    LinearLayout linearLayout = (LinearLayout)_$_findCachedViewById(R.id.btn_onboardingStart);
    if (linearLayout != null)
      linearLayout.setOnClickListener(new PreOnboardingActivity$onCreate$1()); 
    Preference preference = Preference.INSTANCE;
    Context context = getApplicationContext();
    Intrinsics.checkExpressionValueIsNotNull(context, "applicationContext");
    if (preference.getUUIDRetryAttempts(context) > 2) {
      LinearLayout linearLayout1 = (LinearLayout)_$_findCachedViewById(R.id.btn_onboardingStart);
      if (linearLayout1 != null)
        linearLayout1.setEnabled(false); 
      AppCompatTextView appCompatTextView1 = (AppCompatTextView)_$_findCachedViewById(R.id.tv_desc);
      if (appCompatTextView1 != null)
        appCompatTextView1.setText(getString(2131820773)); 
      appCompatTextView1 = (AppCompatTextView)_$_findCachedViewById(R.id.tv_desc);
      if (appCompatTextView1 != null)
        appCompatTextView1.setTextColor(ContextCompat.getColor((Context)this, 2131034204)); 
    } 
  }
  
  protected void onResume() {
    super.onResume();
    showAppRegistrationStatus();
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class PreOnboardingActivity$onCreate$1 implements View.OnClickListener {
    public final void onClick(View param1View) {
      Intent intent = new Intent((Context)PreOnboardingActivity.this, HowItWorksActivity.class);
      PreOnboardingActivity.this.startActivity(intent);
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\016\n\000\n\002\020\002\n\002\030\002\n\002\b\002\020\000\032\0020\001*\0020\002H@¢\006\004\b\003\020\004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 16})
  @DebugMetadata(c = "ca.albertahealthservices.contacttracing.onboarding.PreOnboardingActivity$showAppRegistrationStatus$1", f = "PreOnboardingActivity.kt", i = {0}, l = {44}, m = "invokeSuspend", n = {"$this$launch"}, s = {"L$0"})
  static final class PreOnboardingActivity$showAppRegistrationStatus$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    Object L$0;
    
    int label;
    
    private CoroutineScope p$;
    
    PreOnboardingActivity$showAppRegistrationStatus$1(Continuation param1Continuation) {
      super(2, param1Continuation);
    }
    
    public final Continuation<Unit> create(Object param1Object, Continuation<?> param1Continuation) {
      Intrinsics.checkParameterIsNotNull(param1Continuation, "completion");
      PreOnboardingActivity$showAppRegistrationStatus$1 preOnboardingActivity$showAppRegistrationStatus$1 = new PreOnboardingActivity$showAppRegistrationStatus$1(param1Continuation);
      preOnboardingActivity$showAppRegistrationStatus$1.p$ = (CoroutineScope)param1Object;
      return (Continuation<Unit>)preOnboardingActivity$showAppRegistrationStatus$1;
    }
    
    public final Object invoke(Object param1Object1, Object param1Object2) {
      return ((PreOnboardingActivity$showAppRegistrationStatus$1)create(param1Object1, (Continuation)param1Object2)).invokeSuspend(Unit.INSTANCE);
    }
    
    public final Object invokeSuspend(Object param1Object) {
      Object object = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      int i = this.label;
      if (i != 0) {
        if (i == 1) {
          CoroutineScope coroutineScope = (CoroutineScope)this.L$0;
          ResultKt.throwOnFailure(param1Object);
        } else {
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } 
      } else {
        ResultKt.throwOnFailure(param1Object);
        CoroutineScope coroutineScope = this.p$;
        param1Object = Utils.INSTANCE;
        this.L$0 = coroutineScope;
        this.label = 1;
        Object object1 = param1Object.checkIfAppRegistered((Continuation)this);
        param1Object = object1;
        if (object1 == object)
          return object; 
      } 
      if (!((Boolean)param1Object).booleanValue()) {
        CentralLog.Companion.i(PreOnboardingActivity.this.TAG, "App version not supported");
        param1Object = PreOnboardingActivity.this._$_findCachedViewById(R.id.transparent_layer);
        if (param1Object != null)
          param1Object.setVisibility(0); 
        param1Object = Utils.INSTANCE;
        object = PreOnboardingActivity.this;
        Context context = (Context)object;
        object = object.getString(2131820789);
        Intrinsics.checkExpressionValueIsNotNull(object, "getString(R.string.wrong_version_msg)");
        param1Object.buildWrongVersionDialog(context, (String)object);
      } 
      return Unit.INSTANCE;
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/onboarding/PreOnboardingActivity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */