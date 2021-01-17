package ca.albertahealthservices.contacttracing.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import ca.albertahealthservices.contacttracing.MainActivity;
import ca.albertahealthservices.contacttracing.R;
import ca.albertahealthservices.contacttracing.Utils;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000.\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\006\030\0002\0020\001B\005¢\006\002\020\002J\006\020\003\032\0020\004J\006\020\005\032\0020\004J\006\020\006\032\0020\004J\006\020\007\032\0020\004J&\020\b\032\004\030\0010\t2\006\020\n\032\0020\0132\b\020\f\032\004\030\0010\r2\b\020\016\032\004\030\0010\017H\026J\032\020\020\032\0020\0042\006\020\021\032\0020\t2\b\020\016\032\004\030\0010\017H\026J\006\020\022\032\0020\004J\006\020\023\032\0020\004J\006\020\024\032\0020\004¨\006\025"}, d2 = {"Lca/albertahealthservices/contacttracing/fragment/UploadPageFragment;", "Landroidx/fragment/app/Fragment;", "()V", "goBackToHome", "", "navigateToOTCFragment", "navigateToUploadComplete", "navigateToUploadPin", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "view", "popStack", "turnOffLoadingProgress", "turnOnLoadingProgress", "app_release"}, k = 1, mv = {1, 1, 16})
public final class UploadPageFragment extends Fragment {
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
      view2 = getView();
      if (view2 == null)
        return null; 
      view2 = view2.findViewById(paramInt);
      this._$_findViewCache.put(Integer.valueOf(paramInt), view2);
    } 
    return view2;
  }
  
  public final void goBackToHome() {
    MainActivity mainActivity = (MainActivity)getActivity();
    if (mainActivity != null) {
      mainActivity.goToHome();
    } else {
      Utils.INSTANCE.restartAppWithNoContext(0, "UploadPageFragment not attached to MainActivity");
    } 
  }
  
  public final void navigateToOTCFragment() {
    MainActivity mainActivity = (MainActivity)getActivity();
    if (mainActivity != null) {
      int i = mainActivity.getLAYOUT_MAIN_ID();
      ForUseByOTCFragment forUseByOTCFragment = new ForUseByOTCFragment();
      String str = ForUseByOTCFragment.class.getName();
      Intrinsics.checkExpressionValueIsNotNull(str, "ForUseByOTCFragment::class.java.name");
      mainActivity.openFragment(i, forUseByOTCFragment, str, 0);
    } else {
      Utils.INSTANCE.restartAppWithNoContext(0, "UploadPageFragment not attached to MainActivity");
    } 
  }
  
  public final void navigateToUploadComplete() {
    FragmentManager fragmentManager = getChildFragmentManager();
    Intrinsics.checkExpressionValueIsNotNull(fragmentManager, "childFragmentManager");
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    Intrinsics.checkExpressionValueIsNotNull(fragmentTransaction, "childFragMan.beginTransaction()");
    fragmentTransaction.add(2131296410, new UploadCompleteFragment());
    fragmentTransaction.addToBackStack("UploadComplete");
    fragmentTransaction.commit();
  }
  
  public final void navigateToUploadPin() {
    FragmentManager fragmentManager = getChildFragmentManager();
    Intrinsics.checkExpressionValueIsNotNull(fragmentManager, "childFragmentManager");
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    Intrinsics.checkExpressionValueIsNotNull(fragmentTransaction, "childFragMan.beginTransaction()");
    fragmentTransaction.add(2131296410, new EnterPinFragment());
    fragmentTransaction.addToBackStack("C");
    fragmentTransaction.commit();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramLayoutInflater, "inflater");
    return paramLayoutInflater.inflate(2131492926, paramViewGroup, false);
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    super.onViewCreated(paramView, paramBundle);
    FragmentManager fragmentManager = getChildFragmentManager();
    Intrinsics.checkExpressionValueIsNotNull(fragmentManager, "childFragmentManager");
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    Intrinsics.checkExpressionValueIsNotNull(fragmentTransaction, "childFragMan.beginTransaction()");
    fragmentTransaction.add(2131296410, new VerifyCallerFragment());
    fragmentTransaction.addToBackStack("VerifyCaller");
    fragmentTransaction.commit();
  }
  
  public final void popStack() {
    FragmentManager fragmentManager = getChildFragmentManager();
    Intrinsics.checkExpressionValueIsNotNull(fragmentManager, "childFragmentManager");
    fragmentManager.popBackStack();
  }
  
  public final void turnOffLoadingProgress() {
    FrameLayout frameLayout = (FrameLayout)_$_findCachedViewById(R.id.uploadPageFragmentLoadingProgressBarFrame);
    Intrinsics.checkExpressionValueIsNotNull(frameLayout, "uploadPageFragmentLoadingProgressBarFrame");
    frameLayout.setVisibility(4);
  }
  
  public final void turnOnLoadingProgress() {
    FrameLayout frameLayout = (FrameLayout)_$_findCachedViewById(R.id.uploadPageFragmentLoadingProgressBarFrame);
    Intrinsics.checkExpressionValueIsNotNull(frameLayout, "uploadPageFragmentLoadingProgressBarFrame");
    frameLayout.setVisibility(0);
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/fragment/UploadPageFragment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */