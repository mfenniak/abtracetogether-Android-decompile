package ca.albertahealthservices.contacttracing.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import ca.albertahealthservices.contacttracing.MainActivity;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000,\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\030\0002\0020\001B\005¢\006\002\020\002J\006\020\003\032\0020\004J&\020\005\032\004\030\0010\0062\006\020\007\032\0020\b2\b\020\t\032\004\030\0010\n2\b\020\013\032\004\030\0010\fH\026J\032\020\r\032\0020\0042\006\020\016\032\0020\0062\b\020\013\032\004\030\0010\fH\026¨\006\017"}, d2 = {"Lca/albertahealthservices/contacttracing/fragment/ForUseByOTCFragment;", "Landroidx/fragment/app/Fragment;", "()V", "goToUploadFragment", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "view", "app_release"}, k = 1, mv = {1, 1, 16})
public final class ForUseByOTCFragment extends Fragment {
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
  
  public final void goToUploadFragment() {
    FragmentActivity fragmentActivity = getActivity();
    if (fragmentActivity != null) {
      MainActivity mainActivity = (MainActivity)fragmentActivity;
      int i = mainActivity.getLAYOUT_MAIN_ID();
      UploadPageFragment uploadPageFragment = new UploadPageFragment();
      String str = UploadPageFragment.class.getName();
      Intrinsics.checkExpressionValueIsNotNull(str, "UploadPageFragment::class.java.name");
      mainActivity.openFragment(i, uploadPageFragment, str, 0);
      return;
    } 
    throw new TypeCastException("null cannot be cast to non-null type ca.albertahealthservices.contacttracing.MainActivity");
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramLayoutInflater, "inflater");
    return paramLayoutInflater.inflate(2131492916, paramViewGroup, false);
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    super.onViewCreated(paramView, paramBundle);
    FragmentManager fragmentManager = getChildFragmentManager();
    Intrinsics.checkExpressionValueIsNotNull(fragmentManager, "childFragmentManager");
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    Intrinsics.checkExpressionValueIsNotNull(fragmentTransaction, "childFragMan.beginTransaction()");
    fragmentTransaction.add(2131296410, new ForUseFragment());
    fragmentTransaction.addToBackStack("VerifyCaller");
    fragmentTransaction.commit();
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/fragment/ForUseByOTCFragment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */