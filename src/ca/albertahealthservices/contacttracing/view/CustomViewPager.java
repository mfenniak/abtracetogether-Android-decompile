package ca.albertahealthservices.contacttracing.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.viewpager.widget.ViewPager;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000.\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\002\030\0002\0020\001B\031\022\b\020\002\032\004\030\0010\003\022\b\020\004\032\004\030\0010\005¢\006\002\020\006J\020\020\007\032\0020\b2\006\020\t\032\0020\nH\026J\020\020\013\032\0020\b2\006\020\t\032\0020\nH\026J\016\020\f\032\0020\r2\006\020\016\032\0020\b¨\006\017"}, d2 = {"Lca/albertahealthservices/contacttracing/view/CustomViewPager;", "Landroidx/viewpager/widget/ViewPager;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "onInterceptTouchEvent", "", "event", "Landroid/view/MotionEvent;", "onTouchEvent", "setPagingEnabled", "", "enabled", "app_release"}, k = 1, mv = {1, 1, 16})
public final class CustomViewPager extends ViewPager {
  private HashMap _$_findViewCache;
  
  public CustomViewPager(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
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
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    boolean bool;
    Intrinsics.checkParameterIsNotNull(paramMotionEvent, "event");
    if (isEnabled()) {
      bool = super.onInterceptTouchEvent(paramMotionEvent);
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    boolean bool;
    Intrinsics.checkParameterIsNotNull(paramMotionEvent, "event");
    if (isEnabled()) {
      bool = super.onTouchEvent(paramMotionEvent);
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final void setPagingEnabled(boolean paramBoolean) {
    setEnabled(paramBoolean);
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/view/CustomViewPager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */