package ca.albertahealthservices.contacttracing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.logging.WFLog;
import java.util.HashMap;
import kotlin.Metadata;

@Metadata(bv = {1, 0, 3}, d1 = {"\000 \n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\003\030\0002\0020\001B\005¢\006\002\020\002J\022\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH\024J\b\020\t\032\0020\006H\002J\b\020\n\032\0020\006H\002R\016\020\003\032\0020\004XD¢\006\002\n\000¨\006\013"}, d2 = {"Lca/albertahealthservices/contacttracing/RestartActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "TAG", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "restartApp", "restartOnboarding", "app_release"}, k = 1, mv = {1, 1, 16})
public final class RestartActivity extends AppCompatActivity {
  private final String TAG = "RestartActivity";
  
  private HashMap _$_findViewCache;
  
  private final void restartApp() {
    Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
    intent.addFlags(32768);
    intent.addFlags(268435456);
    startActivity(intent);
  }
  
  private final void restartOnboarding() {
    Preference preference = Preference.INSTANCE;
    Context context = (Context)this;
    preference.putIsOnBoarded(context, false);
    int i = Preference.INSTANCE.getUUIDRetryAttempts(context);
    Preference.INSTANCE.putUUIDRetryAttempts(context, i + 1);
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
    if (getIntent().getIntExtra("error_type", 0) == 1)
      restartOnboarding(); 
    String str = getIntent().getStringExtra("error_msg");
    if (str != null) {
      CentralLog.Companion companion = CentralLog.Companion;
      String str1 = this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append(", Restarting application");
      companion.e(str1, stringBuilder.toString());
      WFLog.Companion companion1 = WFLog.Companion;
      stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append(", Restarting application");
      companion1.logError(stringBuilder.toString());
    } 
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
    builder.setMessage(getString(2131820742));
    builder.setCancelable(false);
    builder.setPositiveButton("Ok", new RestartActivity$onCreate$2());
    builder.create().show();
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\026\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\0032\006\020\005\032\0020\006H\n¢\006\002\b\007"}, d2 = {"<anonymous>", "", "dialog", "Landroid/content/DialogInterface;", "kotlin.jvm.PlatformType", "id", "", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class RestartActivity$onCreate$2 implements DialogInterface.OnClickListener {
    public final void onClick(DialogInterface param1DialogInterface, int param1Int) {
      RestartActivity.this.restartApp();
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/RestartActivity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */