package ca.albertahealthservices.contacttracing.onboarding;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ca.albertahealthservices.contacttracing.Utils;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000P\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\b\030\000 \"2\0020\001:\002\"#B\005¢\006\002\020\002J\b\020\t\032\0020\nH\026J\020\020\013\032\0020\n2\006\020\f\032\0020\rH\026J\020\020\016\032\0020\n2\006\020\017\032\0020\020H\026J\020\020\021\032\0020\n2\006\020\017\032\0020\020H\026J\016\020\022\032\0020\n2\006\020\023\032\0020\024J\022\020\025\032\0020\n2\b\020\026\032\004\030\0010\027H\026J&\020\030\032\004\030\0010\0202\006\020\031\032\0020\0322\b\020\033\032\004\030\0010\0342\b\020\026\032\004\030\0010\027H\026J\b\020\035\032\0020\nH\026J\020\020\036\032\0020\n2\006\020\037\032\0020\004H\026J\020\020 \032\0020\n2\006\020!\032\0020\004H\026R\016\020\003\032\0020\004XD¢\006\002\n\000R\020\020\005\032\004\030\0010\006X\016¢\006\002\n\000R\020\020\007\032\004\030\0010\004X\016¢\006\002\n\000R\020\020\b\032\004\030\0010\004X\016¢\006\002\n\000¨\006$"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/SetupFragment;", "Lca/albertahealthservices/contacttracing/onboarding/OnboardingFragmentInterface;", "()V", "TAG", "", "listener", "Lca/albertahealthservices/contacttracing/onboarding/SetupFragment$OnFragmentInteractionListener;", "param1", "param2", "becomesVisible", "", "onAttach", "context", "Landroid/content/Context;", "onBackButtonClick", "view", "Landroid/view/View;", "onButtonClick", "onButtonPressed", "uri", "Landroid/net/Uri;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDetach", "onError", "error", "onUpdatePhoneNumber", "num", "Companion", "OnFragmentInteractionListener", "app_release"}, k = 1, mv = {1, 1, 16})
public final class SetupFragment extends OnboardingFragmentInterface {
  public static final Companion Companion = new Companion(null);
  
  private final String TAG = "SetupFragment";
  
  private HashMap _$_findViewCache;
  
  private OnFragmentInteractionListener listener;
  
  private String param1;
  
  private String param2;
  
  @JvmStatic
  public static final SetupFragment newInstance(String paramString1, String paramString2) {
    return Companion.newInstance(paramString1, paramString2);
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
  
  public void becomesVisible() {}
  
  public void onAttach(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    super.onAttach(paramContext);
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
    OnboardingActivity onboardingActivity = (OnboardingActivity)getContext();
    if (onboardingActivity != null) {
      onboardingActivity.enableBluetooth();
    } else {
      Utils.INSTANCE.restartAppWithNoContext(0, "SetupFragment not attached to OnboardingActivity");
    } 
  }
  
  public final void onButtonPressed(Uri paramUri) {
    Intrinsics.checkParameterIsNotNull(paramUri, "uri");
    OnFragmentInteractionListener onFragmentInteractionListener = this.listener;
    if (onFragmentInteractionListener != null)
      onFragmentInteractionListener.onFragmentInteraction(paramUri); 
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    paramBundle = getArguments();
    if (paramBundle != null) {
      this.param1 = paramBundle.getString("param1");
      this.param2 = paramBundle.getString("param2");
    } 
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramLayoutInflater, "inflater");
    return paramLayoutInflater.inflate(2131492921, paramViewGroup, false);
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
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\030\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\006H\007¨\006\b"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/SetupFragment$Companion;", "", "()V", "newInstance", "Lca/albertahealthservices/contacttracing/onboarding/SetupFragment;", "param1", "", "param2", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class Companion {
    private Companion() {}
    
    @JvmStatic
    public final SetupFragment newInstance(String param1String1, String param1String2) {
      Intrinsics.checkParameterIsNotNull(param1String1, "param1");
      Intrinsics.checkParameterIsNotNull(param1String2, "param2");
      SetupFragment setupFragment = new SetupFragment();
      Bundle bundle = new Bundle();
      bundle.putString("param1", param1String1);
      bundle.putString("param2", param1String2);
      setupFragment.setArguments(bundle);
      return setupFragment;
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\026\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\030\002\n\000\bf\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H&¨\006\006"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/SetupFragment$OnFragmentInteractionListener;", "", "onFragmentInteraction", "", "uri", "Landroid/net/Uri;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static interface OnFragmentInteractionListener {
    void onFragmentInteraction(Uri param1Uri);
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/onboarding/SetupFragment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */