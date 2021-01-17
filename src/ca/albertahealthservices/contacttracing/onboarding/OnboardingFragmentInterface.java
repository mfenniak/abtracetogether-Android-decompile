package ca.albertahealthservices.contacttracing.onboarding;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import ca.albertahealthservices.contacttracing.R;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\0004\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\007\n\002\020\016\n\002\b\005\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\004\b&\030\0002\0020\001B\005¢\006\002\020\002J\b\020\006\032\0020\007H&J\006\020\b\032\0020\007J\006\020\t\032\0020\007J\020\020\n\032\0020\0072\006\020\013\032\0020\004H&J\020\020\f\032\0020\0072\006\020\013\032\0020\004H&J\020\020\r\032\0020\0072\006\020\016\032\0020\017H&J\020\020\020\032\0020\0072\006\020\021\032\0020\017H&J\032\020\022\032\0020\0072\006\020\023\032\0020\0042\b\020\024\032\004\030\0010\025H\026J\016\020\026\032\0020\0072\006\020\027\032\0020\030J\016\020\031\032\0020\0072\006\020\032\032\0020\017J\b\020\033\032\0020\007H\002R\020\020\003\032\004\030\0010\004X\016¢\006\002\n\000R\020\020\005\032\004\030\0010\004X\016¢\006\002\n\000¨\006\034"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/OnboardingFragmentInterface;", "Landroidx/fragment/app/Fragment;", "()V", "actionButton", "Landroid/view/View;", "backButton", "becomesVisible", "", "disableButton", "enableButton", "onBackButtonClick", "buttonView", "onButtonClick", "onError", "error", "", "onUpdatePhoneNumber", "num", "onViewCreated", "view", "savedInstanceState", "Landroid/os/Bundle;", "setButtonIcon", "id", "", "setButtonText", "string", "setupButton", "app_release"}, k = 1, mv = {1, 1, 16})
public abstract class OnboardingFragmentInterface extends Fragment {
  private HashMap _$_findViewCache;
  
  private View actionButton;
  
  private View backButton;
  
  private final void setupButton() {
    LinearLayout linearLayout = (LinearLayout)_$_findCachedViewById(R.id.onboardingButton);
    if (linearLayout != null) {
      this.actionButton = (View)linearLayout;
      linearLayout.setOnClickListener(new OnboardingFragmentInterface$setupButton$$inlined$let$lambda$1());
    } 
    AppCompatImageView appCompatImageView = (AppCompatImageView)_$_findCachedViewById(R.id.onboardingBackButton);
    if (appCompatImageView != null) {
      this.backButton = (View)appCompatImageView;
      appCompatImageView.setOnClickListener(new OnboardingFragmentInterface$setupButton$$inlined$let$lambda$2());
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
      view2 = getView();
      if (view2 == null)
        return null; 
      view2 = view2.findViewById(paramInt);
      this._$_findViewCache.put(Integer.valueOf(paramInt), view2);
    } 
    return view2;
  }
  
  public abstract void becomesVisible();
  
  public final void disableButton() {
    View view = this.actionButton;
    if (view != null)
      view.setEnabled(false); 
  }
  
  public final void enableButton() {
    View view = this.actionButton;
    if (view != null)
      view.setEnabled(true); 
  }
  
  public abstract void onBackButtonClick(View paramView);
  
  public abstract void onButtonClick(View paramView);
  
  public abstract void onError(String paramString);
  
  public abstract void onUpdatePhoneNumber(String paramString);
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    super.onViewCreated(paramView, paramBundle);
    setupButton();
  }
  
  public final void setButtonIcon(int paramInt) {
    ImageView imageView = (ImageView)_$_findCachedViewById(R.id.onboardingButtonIcon);
    if (imageView != null)
      imageView.setImageResource(paramInt); 
  }
  
  public final void setButtonText(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "string");
    TextView textView = (TextView)_$_findCachedViewById(R.id.onboardingButtonText);
    if (textView != null)
      textView.setText(paramString); 
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\024\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\b\003\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005¨\006\006"}, d2 = {"<anonymous>", "", "buttonView", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick", "ca/albertahealthservices/contacttracing/onboarding/OnboardingFragmentInterface$setupButton$1$1"}, k = 3, mv = {1, 1, 16})
  static final class OnboardingFragmentInterface$setupButton$$inlined$let$lambda$1 implements View.OnClickListener {
    public final void onClick(View param1View) {
      OnboardingFragmentInterface onboardingFragmentInterface = OnboardingFragmentInterface.this;
      Intrinsics.checkExpressionValueIsNotNull(param1View, "buttonView");
      onboardingFragmentInterface.onButtonClick(param1View);
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\024\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\b\003\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005¨\006\006"}, d2 = {"<anonymous>", "", "buttonView", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick", "ca/albertahealthservices/contacttracing/onboarding/OnboardingFragmentInterface$setupButton$2$1"}, k = 3, mv = {1, 1, 16})
  static final class OnboardingFragmentInterface$setupButton$$inlined$let$lambda$2 implements View.OnClickListener {
    public final void onClick(View param1View) {
      OnboardingFragmentInterface onboardingFragmentInterface = OnboardingFragmentInterface.this;
      Intrinsics.checkExpressionValueIsNotNull(param1View, "buttonView");
      onboardingFragmentInterface.onBackButtonClick(param1View);
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/onboarding/OnboardingFragmentInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */