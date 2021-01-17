package ca.albertahealthservices.contacttracing.onboarding;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ca.albertahealthservices.contacttracing.MainActivity;
import ca.albertahealthservices.contacttracing.Preference;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000B\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\b\030\0002\0020\001:\001\036B\005¢\006\002\020\002J\b\020\t\032\0020\nH\026J\020\020\013\032\0020\n2\006\020\f\032\0020\bH\026J\020\020\r\032\0020\n2\006\020\016\032\0020\017H\026J\020\020\020\032\0020\n2\006\020\016\032\0020\017H\026J&\020\021\032\004\030\0010\0172\006\020\022\032\0020\0232\b\020\024\032\004\030\0010\0252\b\020\026\032\004\030\0010\027H\026J\b\020\030\032\0020\nH\026J\020\020\031\032\0020\n2\006\020\032\032\0020\004H\026J\020\020\033\032\0020\n2\006\020\034\032\0020\004H\026J\032\020\035\032\0020\n2\006\020\016\032\0020\0172\b\020\026\032\004\030\0010\027H\026R\016\020\003\032\0020\004XD¢\006\002\n\000R\020\020\005\032\004\030\0010\006X\016¢\006\002\n\000R\016\020\007\032\0020\bX.¢\006\002\n\000¨\006\037"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/SetupCompleteFragment;", "Lca/albertahealthservices/contacttracing/onboarding/OnboardingFragmentInterface;", "()V", "TAG", "", "listener", "Lca/albertahealthservices/contacttracing/onboarding/SetupCompleteFragment$OnFragmentInteractionListener;", "mainContext", "Landroid/content/Context;", "becomesVisible", "", "onAttach", "context", "onBackButtonClick", "view", "Landroid/view/View;", "onButtonClick", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDetach", "onError", "error", "onUpdatePhoneNumber", "num", "onViewCreated", "OnFragmentInteractionListener", "app_release"}, k = 1, mv = {1, 1, 16})
public final class SetupCompleteFragment extends OnboardingFragmentInterface {
  private final String TAG = "SetupCompleteFragment";
  
  private HashMap _$_findViewCache;
  
  private OnFragmentInteractionListener listener;
  
  private Context mainContext;
  
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
  
  public void becomesVisible() {}
  
  public void onAttach(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    super.onAttach(paramContext);
    this.mainContext = paramContext;
    if (paramContext instanceof OnFragmentInteractionListener) {
      this.listener = (OnFragmentInteractionListener)paramContext;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramContext.toString());
    stringBuilder.append(" must implement OnFragmentInteractionListener");
    throw (Throwable)new RuntimeException(stringBuilder.toString());
  }
  
  public void onBackButtonClick(View paramView) {
    Intrinsics.checkParameterIsNotNull(paramView, "view");
  }
  
  public void onButtonClick(View paramView) {
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    CentralLog.Companion.d(this.TAG, "OnButtonClick 2");
    Preference preference = Preference.INSTANCE;
    Context context2 = paramView.getContext();
    Intrinsics.checkExpressionValueIsNotNull(context2, "view.context");
    preference.putCheckpoint(context2, 0);
    preference = Preference.INSTANCE;
    Context context1 = paramView.getContext();
    Intrinsics.checkExpressionValueIsNotNull(context1, "view.context");
    preference.putIsOnBoarded(context1, true);
    Intent intent = new Intent(getContext(), MainActivity.class);
    intent.setFlags(268468224);
    context1 = getContext();
    if (context1 != null)
      context1.startActivity(intent); 
    OnboardingActivity onboardingActivity = (OnboardingActivity)getContext();
    if (onboardingActivity != null)
      onboardingActivity.finish(); 
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramLayoutInflater, "inflater");
    return paramLayoutInflater.inflate(2131492922, paramViewGroup, false);
  }
  
  public void onDetach() {
    super.onDetach();
    this.listener = (OnFragmentInteractionListener)null;
  }
  
  public void onError(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "error");
  }
  
  public void onUpdatePhoneNumber(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "num");
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    super.onViewCreated(paramView, paramBundle);
    String str = getString(2131820620);
    Intrinsics.checkExpressionValueIsNotNull(str, "getString(R.string.finish_button)");
    setButtonText(str);
    setButtonIcon(2131165355);
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\026\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\030\002\n\000\bf\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H&¨\006\006"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/SetupCompleteFragment$OnFragmentInteractionListener;", "", "onFragmentInteraction", "", "uri", "Landroid/net/Uri;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static interface OnFragmentInteractionListener {
    void onFragmentInteraction(Uri param1Uri);
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/onboarding/SetupCompleteFragment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */