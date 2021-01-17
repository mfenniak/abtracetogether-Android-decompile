package ca.albertahealthservices.contacttracing.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;
import ca.albertahealthservices.contacttracing.R;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\0002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\002\030\0002\0020\001B\005¢\006\002\020\002J&\020\007\032\004\030\0010\b2\006\020\005\032\0020\0062\b\020\t\032\004\030\0010\n2\b\020\013\032\004\030\0010\fH\026J\032\020\r\032\0020\0162\006\020\017\032\0020\b2\b\020\013\032\004\030\0010\fH\026R\016\020\003\032\0020\004XD¢\006\002\n\000R\016\020\005\032\0020\006X.¢\006\002\n\000¨\006\020"}, d2 = {"Lca/albertahealthservices/contacttracing/fragment/HelpFragment;", "Landroidx/fragment/app/Fragment;", "()V", "TAG", "", "inflater", "Landroid/view/LayoutInflater;", "onCreateView", "Landroid/view/View;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "", "view", "app_release"}, k = 1, mv = {1, 1, 16})
public final class HelpFragment extends Fragment {
  private final String TAG = "HomeFragment";
  
  private HashMap _$_findViewCache;
  
  private LayoutInflater inflater;
  
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
    this.inflater = paramLayoutInflater;
    return paramLayoutInflater.inflate(2131492917, paramViewGroup, false);
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    WebSettings webSettings;
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    super.onViewCreated(paramView, paramBundle);
    WebView webView2 = (WebView)_$_findCachedViewById(R.id.help_webview);
    if (webView2 != null)
      webView2.setWebViewClient(new WebViewClient()); 
    webView2 = (WebView)_$_findCachedViewById(R.id.help_webview);
    paramBundle = null;
    if (webView2 != null) {
      webSettings = webView2.getSettings();
    } else {
      webView2 = null;
    } 
    if (webView2 == null)
      Intrinsics.throwNpe(); 
    webView2.setJavaScriptEnabled(true);
    WebView webView3 = (WebView)_$_findCachedViewById(R.id.help_webview);
    Bundle bundle = paramBundle;
    if (webView3 != null)
      webSettings = webView3.getSettings(); 
    if (webSettings == null)
      Intrinsics.throwNpe(); 
    webSettings.setLoadWithOverviewMode(true);
    WebView webView1 = (WebView)_$_findCachedViewById(R.id.help_webview);
    if (webView1 != null)
      webView1.loadUrl("https://alberta.ca/ABTraceTogetherFAQ"); 
    HelpFragment$onViewCreated$wbc$1 helpFragment$onViewCreated$wbc$1 = new HelpFragment$onViewCreated$wbc$1();
    webView1 = (WebView)_$_findCachedViewById(R.id.help_webview);
    if (webView1 != null)
      webView1.setWebChromeClient(helpFragment$onViewCreated$wbc$1); 
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\027\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H\026¨\006\006"}, d2 = {"ca/albertahealthservices/contacttracing/fragment/HelpFragment$onViewCreated$wbc$1", "Landroid/webkit/WebChromeClient;", "onCloseWindow", "", "w", "Landroid/webkit/WebView;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class HelpFragment$onViewCreated$wbc$1 extends WebChromeClient {
    public void onCloseWindow(WebView param1WebView) {
      Intrinsics.checkParameterIsNotNull(param1WebView, "w");
      CentralLog.Companion.d(HelpFragment.this.TAG, "OnCloseWindow for WebChromeClient");
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/fragment/HelpFragment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */