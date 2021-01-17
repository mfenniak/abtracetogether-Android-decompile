package ca.albertahealthservices.contacttracing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import ca.albertahealthservices.contacttracing.onboarding.PreOnboardingActivity;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\0000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\t\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\005\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\003\030\0002\0020\001B\005¢\006\002\020\002J\b\020\r\032\0020\016H\002J\022\020\017\032\0020\0162\b\020\020\032\004\030\0010\021H\024J\b\020\022\032\0020\016H\024J\b\020\023\032\0020\016H\024R\016\020\003\032\0020\004XD¢\006\002\n\000R\016\020\005\032\0020\006X.¢\006\002\n\000R\032\020\007\032\0020\bX\016¢\006\016\n\000\032\004\b\t\020\n\"\004\b\013\020\f¨\006\024"}, d2 = {"Lca/albertahealthservices/contacttracing/SplashActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "SPLASH_TIME", "", "mHandler", "Landroid/os/Handler;", "needToUpdateApp", "", "getNeedToUpdateApp", "()Z", "setNeedToUpdateApp", "(Z)V", "goToNextScreen", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onPause", "onResume", "app_release"}, k = 1, mv = {1, 1, 16})
public final class SplashActivity extends AppCompatActivity {
  private final long SPLASH_TIME = 2000L;
  
  private HashMap _$_findViewCache;
  
  private Handler mHandler;
  
  private boolean needToUpdateApp;
  
  private final void goToNextScreen() {
    Preference preference = Preference.INSTANCE;
    Context context = (Context)this;
    if (!preference.isOnBoarded(context)) {
      startActivity(new Intent(context, PreOnboardingActivity.class));
    } else {
      startActivity(new Intent(context, MainActivity.class));
    } 
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
  
  public final boolean getNeedToUpdateApp() {
    return this.needToUpdateApp;
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131492895);
    this.mHandler = new Handler();
    Intent intent = getIntent();
    Intrinsics.checkExpressionValueIsNotNull(intent, "intent");
    Bundle bundle = intent.getExtras();
    if (bundle != null) {
      String str = bundle.getString("event", null);
      if (str != null && str.equals("update")) {
        this.needToUpdateApp = true;
        setIntent(new Intent("android.intent.action.VIEW"));
        Intent intent1 = getIntent();
        Intrinsics.checkExpressionValueIsNotNull(intent1, "intent");
        intent1.setData(Uri.parse("http://play.google.com/store/apps/details?id=ca.albertahealthservices.contacttracing"));
        startActivity(getIntent());
        finish();
      } 
    } 
  }
  
  protected void onPause() {
    super.onPause();
    Handler handler = this.mHandler;
    if (handler == null)
      Intrinsics.throwUninitializedPropertyAccessException("mHandler"); 
    handler.removeCallbacksAndMessages(null);
  }
  
  protected void onResume() {
    super.onResume();
    if (!this.needToUpdateApp) {
      Handler handler = this.mHandler;
      if (handler == null)
        Intrinsics.throwUninitializedPropertyAccessException("mHandler"); 
      handler.postDelayed(new SplashActivity$onResume$1(), this.SPLASH_TIME);
    } 
  }
  
  public final void setNeedToUpdateApp(boolean paramBoolean) {
    this.needToUpdateApp = paramBoolean;
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\b\n\000\n\002\020\002\n\000\020\000\032\0020\001H\n¢\006\002\b\002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 1, 16})
  static final class SplashActivity$onResume$1 implements Runnable {
    public final void run() {
      SplashActivity.this.goToNextScreen();
      SplashActivity.this.finish();
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/SplashActivity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */