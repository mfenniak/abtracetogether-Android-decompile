package ca.albertahealthservices.contacttracing;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import ca.albertahealthservices.contacttracing.fragment.ForUseByOTCFragment;
import ca.albertahealthservices.contacttracing.fragment.HelpFragment;
import ca.albertahealthservices.contacttracing.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000B\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\005\n\002\020\016\n\002\b\003\n\002\020\002\n\002\b\002\n\002\020\013\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\030\0002\0020\001B\005¢\006\002\020\002J\b\020\r\032\0020\016H\002J\006\020\017\032\0020\016J\024\020\020\032\0020\0212\n\020\022\032\006\022\002\b\0030\023H\002J\022\020\024\032\0020\0162\b\020\025\032\004\030\0010\026H\024J&\020\027\032\0020\0162\006\020\030\032\0020\0042\006\020\031\032\0020\0322\006\020\033\032\0020\n2\006\020\034\032\0020\004R\032\020\003\032\0020\004X\016¢\006\016\n\000\032\004\b\005\020\006\"\004\b\007\020\bR\016\020\t\032\0020\nXD¢\006\002\n\000R\016\020\013\032\0020\004X\016¢\006\002\n\000R\016\020\f\032\0020\004X\016¢\006\002\n\000¨\006\035"}, d2 = {"Lca/albertahealthservices/contacttracing/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "LAYOUT_MAIN_ID", "", "getLAYOUT_MAIN_ID", "()I", "setLAYOUT_MAIN_ID", "(I)V", "TAG", "", "mNavigationLevel", "selected", "getFCMToken", "", "goToHome", "isMyServiceRunning", "", "serviceClass", "Ljava/lang/Class;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "openFragment", "containerViewId", "fragment", "Landroidx/fragment/app/Fragment;", "tag", "title", "app_release"}, k = 1, mv = {1, 1, 16})
public final class MainActivity extends AppCompatActivity {
  private int LAYOUT_MAIN_ID;
  
  private final String TAG = "MainActivity";
  
  private HashMap _$_findViewCache;
  
  private int mNavigationLevel;
  
  private int selected;
  
  private final void getFCMToken() {}
  
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
    getFCMToken();
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
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/MainActivity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */