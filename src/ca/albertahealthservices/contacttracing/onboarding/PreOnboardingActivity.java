package ca.albertahealthservices.contacttracing.onboarding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.fragment.app.FragmentActivity;
import ca.albertahealthservices.contacttracing.R;
import java.util.HashMap;
import kotlin.Metadata;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\030\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\030\0002\0020\001B\005¢\006\002\020\002J\022\020\003\032\0020\0042\b\020\005\032\004\030\0010\006H\024¨\006\007"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/PreOnboardingActivity;", "Landroidx/fragment/app/FragmentActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_release"}, k = 1, mv = {1, 1, 16})
public final class PreOnboardingActivity extends FragmentActivity {
  private HashMap _$_findViewCache;
  
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
    setContentView(2131492930);
    ((LinearLayout)_$_findCachedViewById(R.id.btn_onboardingStart)).setOnClickListener(new PreOnboardingActivity$onCreate$1());
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class PreOnboardingActivity$onCreate$1 implements View.OnClickListener {
    public final void onClick(View param1View) {
      Intent intent = new Intent((Context)PreOnboardingActivity.this, HowItWorksActivity.class);
      PreOnboardingActivity.this.startActivity(intent);
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/onboarding/PreOnboardingActivity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */