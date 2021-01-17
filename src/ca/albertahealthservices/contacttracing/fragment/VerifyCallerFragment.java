package ca.albertahealthservices.contacttracing.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import ca.albertahealthservices.contacttracing.Preference;
import ca.albertahealthservices.contacttracing.R;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000,\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\002\030\0002\0020\001B\005¢\006\002\020\002J&\020\003\032\004\030\0010\0042\006\020\005\032\0020\0062\b\020\007\032\004\030\0010\b2\b\020\t\032\004\030\0010\nH\026J\032\020\013\032\0020\f2\006\020\r\032\0020\0042\b\020\t\032\004\030\0010\nH\026¨\006\016"}, d2 = {"Lca/albertahealthservices/contacttracing/fragment/VerifyCallerFragment;", "Landroidx/fragment/app/Fragment;", "()V", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "", "view", "app_release"}, k = 1, mv = {1, 1, 16})
public final class VerifyCallerFragment extends Fragment {
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
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramLayoutInflater, "inflater");
    return paramLayoutInflater.inflate(2131492928, paramViewGroup, false);
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    super.onViewCreated(paramView, paramBundle);
    AppCompatTextView appCompatTextView = (AppCompatTextView)_$_findCachedViewById(R.id.verifyCallerFragmentVerificationCode);
    Intrinsics.checkExpressionValueIsNotNull(appCompatTextView, "verifyCallerFragmentVerificationCode");
    appCompatTextView.setText(Preference.INSTANCE.getUUID(paramView.getContext()));
    ((LinearLayout)_$_findCachedViewById(R.id.verifyCallerFragmentActionButton)).setOnClickListener(new VerifyCallerFragment$onViewCreated$1());
    ((AppCompatImageView)_$_findCachedViewById(R.id.verifyCallerBackButton)).setOnClickListener(new VerifyCallerFragment$onViewCreated$2());
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class VerifyCallerFragment$onViewCreated$1 implements View.OnClickListener {
    public final void onClick(View param1View) {
      Fragment fragment = VerifyCallerFragment.this.getParentFragment();
      if (fragment != null) {
        ((UploadPageFragment)fragment).navigateToUploadPin();
        return;
      } 
      throw new TypeCastException("null cannot be cast to non-null type ca.albertahealthservices.contacttracing.fragment.UploadPageFragment");
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class VerifyCallerFragment$onViewCreated$2 implements View.OnClickListener {
    public final void onClick(View param1View) {
      Fragment fragment = VerifyCallerFragment.this.getParentFragment();
      if (fragment != null) {
        ((UploadPageFragment)fragment).navigateToOTCFragment();
        return;
      } 
      throw new TypeCastException("null cannot be cast to non-null type ca.albertahealthservices.contacttracing.fragment.UploadPageFragment");
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/fragment/VerifyCallerFragment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */