package ca.albertahealthservices.contacttracing;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import ca.albertahealthservices.contacttracing.fragment.ForUseByOTCFragment;
import ca.albertahealthservices.contacttracing.fragment.HelpFragment;
import ca.albertahealthservices.contacttracing.fragment.HomeFragment;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.onboarding.PreOnboardingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;

@Metadata(bv = {1, 0, 3}, d1 = {"\000P\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\005\n\002\020\016\n\000\n\002\030\002\n\002\b\005\n\002\020\002\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\030\002\n\000\030\0002\0020\0012\0020\002B\005¢\006\002\020\003J\006\020\022\032\0020\023J\024\020\024\032\0020\0252\n\020\026\032\006\022\002\b\0030\027H\002J\022\020\030\032\0020\0232\b\020\031\032\004\030\0010\032H\024J\b\020\033\032\0020\023H\024J&\020\034\032\0020\0232\006\020\035\032\0020\0052\006\020\036\032\0020\0372\006\020 \032\0020\0132\006\020!\032\0020\005J\b\020\"\032\0020#H\002R\032\020\004\032\0020\005X\016¢\006\016\n\000\032\004\b\006\020\007\"\004\b\b\020\tR\016\020\n\032\0020\013XD¢\006\002\n\000R\022\020\f\032\0020\rX\005¢\006\006\032\004\b\016\020\017R\016\020\020\032\0020\005X\016¢\006\002\n\000R\016\020\021\032\0020\005X\016¢\006\002\n\000¨\006$"}, d2 = {"Lca/albertahealthservices/contacttracing/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "LAYOUT_MAIN_ID", "", "getLAYOUT_MAIN_ID", "()I", "setLAYOUT_MAIN_ID", "(I)V", "TAG", "", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "mNavigationLevel", "selected", "goToHome", "", "isMyServiceRunning", "", "serviceClass", "Ljava/lang/Class;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "openFragment", "containerViewId", "fragment", "Landroidx/fragment/app/Fragment;", "tag", "title", "showAppRegistrationStatus", "Lkotlinx/coroutines/Job;", "app_release"}, k = 1, mv = {1, 1, 16})
public final class MainActivity extends AppCompatActivity implements CoroutineScope {
  private int LAYOUT_MAIN_ID;
  
  private final String TAG = "MainActivity";
  
  private HashMap _$_findViewCache;
  
  private int mNavigationLevel;
  
  private int selected;
  
  private final boolean isMyServiceRunning(Class<?> paramClass) {
    Object object = getSystemService("activity");
    if (object != null) {
      for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager)object).getRunningServices(2147483647)) {
        object = paramClass.getName();
        ComponentName componentName = runningServiceInfo.service;
        Intrinsics.checkExpressionValueIsNotNull(componentName, "service.service");
        if (Intrinsics.areEqual(object, componentName.getClassName()))
          return true; 
      } 
      return false;
    } 
    throw new TypeCastException("null cannot be cast to non-null type android.app.ActivityManager");
  }
  
  private final Job showAppRegistrationStatus() {
    return BuildersKt.launch$default(this, null, null, new MainActivity$showAppRegistrationStatus$1(null), 3, null);
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
  
  public final int getLAYOUT_MAIN_ID() {
    return this.LAYOUT_MAIN_ID;
  }
  
  public final void goToHome() {
    BottomNavigationView bottomNavigationView = (BottomNavigationView)_$_findCachedViewById(R.id.nav_view);
    Intrinsics.checkExpressionValueIsNotNull(bottomNavigationView, "nav_view");
    bottomNavigationView.setSelectedItemId(2131296487);
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131492892);
    Utils.INSTANCE.startBluetoothMonitoringService((Context)this);
    this.LAYOUT_MAIN_ID = 2131296359;
    MainActivity$onCreate$mOnNavigationItemSelectedListener$1 mainActivity$onCreate$mOnNavigationItemSelectedListener$1 = new MainActivity$onCreate$mOnNavigationItemSelectedListener$1();
    ((BottomNavigationView)_$_findCachedViewById(R.id.nav_view)).setOnNavigationItemSelectedListener(mainActivity$onCreate$mOnNavigationItemSelectedListener$1);
    goToHome();
  }
  
  protected void onResume() {
    super.onResume();
    showAppRegistrationStatus();
  }
  
  public final void openFragment(int paramInt1, Fragment paramFragment, String paramString, int paramInt2) {
    Intrinsics.checkParameterIsNotNull(paramFragment, "fragment");
    Intrinsics.checkParameterIsNotNull(paramString, "tag");
    try {
      getSupportFragmentManager().popBackStackImmediate(this.LAYOUT_MAIN_ID, 1);
      this.mNavigationLevel = 0;
      FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
      Intrinsics.checkExpressionValueIsNotNull(fragmentTransaction, "supportFragmentManager.beginTransaction()");
      fragmentTransaction.replace(paramInt1, paramFragment, paramString);
      fragmentTransaction.commit();
    } finally {
      paramFragment = null;
    } 
  }
  
  public final void setLAYOUT_MAIN_ID(int paramInt) {
    this.LAYOUT_MAIN_ID = paramInt;
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\016\n\000\n\002\020\013\n\000\n\002\030\002\n\000\020\000\032\0020\0012\006\020\002\032\0020\003H\n¢\006\002\b\004"}, d2 = {"<anonymous>", "", "item", "Landroid/view/MenuItem;", "onNavigationItemSelected"}, k = 3, mv = {1, 1, 16})
  static final class MainActivity$onCreate$mOnNavigationItemSelectedListener$1 implements BottomNavigationView.OnNavigationItemSelectedListener {
    public final boolean onNavigationItemSelected(MenuItem param1MenuItem) {
      Intrinsics.checkParameterIsNotNull(param1MenuItem, "item");
      switch (param1MenuItem.getItemId()) {
        default:
          return false;
        case 2131296488:
          if (MainActivity.this.selected != 2131296488) {
            MainActivity mainActivity = MainActivity.this;
            int i = mainActivity.getLAYOUT_MAIN_ID();
            Fragment fragment = (Fragment)new ForUseByOTCFragment();
            String str = ForUseByOTCFragment.class.getName();
            Intrinsics.checkExpressionValueIsNotNull(str, "ForUseByOTCFragment::class.java.name");
            mainActivity.openFragment(i, fragment, str, 0);
          } 
          MainActivity.this.selected = 2131296488;
          return true;
        case 2131296487:
          if (MainActivity.this.selected != 2131296487) {
            MainActivity mainActivity = MainActivity.this;
            int i = mainActivity.getLAYOUT_MAIN_ID();
            Fragment fragment = (Fragment)new HomeFragment();
            String str = HomeFragment.class.getName();
            Intrinsics.checkExpressionValueIsNotNull(str, "HomeFragment::class.java.name");
            mainActivity.openFragment(i, fragment, str, 0);
          } 
          MainActivity.this.selected = 2131296487;
          return true;
        case 2131296486:
          break;
      } 
      if (MainActivity.this.selected != 2131296486) {
        MainActivity mainActivity = MainActivity.this;
        int i = mainActivity.getLAYOUT_MAIN_ID();
        Fragment fragment = (Fragment)new HelpFragment();
        String str = HelpFragment.class.getName();
        Intrinsics.checkExpressionValueIsNotNull(str, "HelpFragment::class.java.name");
        mainActivity.openFragment(i, fragment, str, 0);
      } 
      MainActivity.this.selected = 2131296486;
      return true;
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\016\n\000\n\002\020\002\n\002\030\002\n\002\b\002\020\000\032\0020\001*\0020\002H@¢\006\004\b\003\020\004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 16})
  @DebugMetadata(c = "ca.albertahealthservices.contacttracing.MainActivity$showAppRegistrationStatus$1", f = "MainActivity.kt", i = {0}, l = {92}, m = "invokeSuspend", n = {"$this$launch"}, s = {"L$0"})
  static final class MainActivity$showAppRegistrationStatus$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    Object L$0;
    
    int label;
    
    private CoroutineScope p$;
    
    MainActivity$showAppRegistrationStatus$1(Continuation param1Continuation) {
      super(2, param1Continuation);
    }
    
    public final Continuation<Unit> create(Object param1Object, Continuation<?> param1Continuation) {
      Intrinsics.checkParameterIsNotNull(param1Continuation, "completion");
      MainActivity$showAppRegistrationStatus$1 mainActivity$showAppRegistrationStatus$1 = new MainActivity$showAppRegistrationStatus$1(param1Continuation);
      mainActivity$showAppRegistrationStatus$1.p$ = (CoroutineScope)param1Object;
      return (Continuation<Unit>)mainActivity$showAppRegistrationStatus$1;
    }
    
    public final Object invoke(Object param1Object1, Object param1Object2) {
      return ((MainActivity$showAppRegistrationStatus$1)create(param1Object1, (Continuation)param1Object2)).invokeSuspend(Unit.INSTANCE);
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
        param1Object = this.p$;
        Utils utils = Utils.INSTANCE;
        this.L$0 = param1Object;
        this.label = 1;
        Object object1 = utils.checkIfAppRegistered((Continuation<? super Boolean>)this);
        param1Object = object1;
        if (object1 == object)
          return object; 
      } 
      if (!((Boolean)param1Object).booleanValue()) {
        CentralLog.Companion.i(MainActivity.this.TAG, "App version not supported, stopping BluetoothMonitoringService");
        Utils.INSTANCE.stopBluetoothMonitoringService((Context)MainActivity.this);
        MainActivity.this.startActivity(new Intent((Context)MainActivity.this, PreOnboardingActivity.class));
        MainActivity.this.finish();
      } 
      return Unit.INSTANCE;
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/MainActivity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */