package ca.albertahealthservices.contacttracing.onboarding;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import ca.albertahealthservices.contacttracing.R;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\036\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020\002\n\000\n\002\030\002\n\000\030\0002\0020\001B\005¢\006\002\020\002J\022\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH\024R\016\020\003\032\0020\004XD¢\006\002\n\000¨\006\t"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/WebViewActivity;", "Landroidx/fragment/app/FragmentActivity;", "()V", "TAG", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_release"}, k = 1, mv = {1, 1, 16})
public final class WebViewActivity extends FragmentActivity {
  private final String TAG = "WebViewActivity";
  
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
    String str;
    WebSettings webSettings;
    super.onCreate(paramBundle);
    setContentView(2131492992);
    WebView webView1 = (WebView)_$_findCachedViewById(R.id.webview);
    if (webView1 != null)
      webView1.setWebViewClient(new WebViewClient()); 
    if (getIntent().getIntExtra("type", 0) == 1) {
      AppCompatTextView appCompatTextView = (AppCompatTextView)_$_findCachedViewById(R.id.tv_title);
      if (appCompatTextView != null)
        appCompatTextView.setText(getString(2131820619)); 
      str = "https://alberta.ca/ABTraceTogetherFAQ";
    } else {
      AppCompatTextView appCompatTextView = (AppCompatTextView)_$_findCachedViewById(R.id.tv_title);
      if (appCompatTextView != null)
        appCompatTextView.setText(getString(2131820725)); 
      str = "https://alberta.ca/ABTraceTogetherPrivacy";
    } 
    WebView webView3 = (WebView)_$_findCachedViewById(R.id.webview);
    WebView webView4 = null;
    if (webView3 != null) {
      webSettings = webView3.getSettings();
    } else {
      webView3 = null;
    } 
    if (webView3 == null)
      Intrinsics.throwNpe(); 
    webView3.setJavaScriptEnabled(true);
    WebView webView5 = (WebView)_$_findCachedViewById(R.id.webview);
    webView3 = webView4;
    if (webView5 != null)
      webSettings = webView5.getSettings(); 
    if (webSettings == null)
      Intrinsics.throwNpe(); 
    webSettings.setLoadWithOverviewMode(true);
    WebView webView2 = (WebView)_$_findCachedViewById(R.id.webview);
    if (webView2 != null)
      webView2.loadUrl(str); 
    WebViewActivity$onCreate$wbc$1 webViewActivity$onCreate$wbc$1 = new WebViewActivity$onCreate$wbc$1();
    webView2 = (WebView)_$_findCachedViewById(R.id.webview);
    if (webView2 != null)
      webView2.setWebChromeClient(webViewActivity$onCreate$wbc$1); 
    AppCompatImageView appCompatImageView = (AppCompatImageView)_$_findCachedViewById(R.id.webviewBackButton);
    if (appCompatImageView != null)
      appCompatImageView.setOnClickListener(new WebViewActivity$onCreate$$inlined$let$lambda$1()); 
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\024\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\b\003\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005¨\006\006"}, d2 = {"<anonymous>", "", "buttonView", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick", "ca/albertahealthservices/contacttracing/onboarding/WebViewActivity$onCreate$1$1"}, k = 3, mv = {1, 1, 16})
  static final class WebViewActivity$onCreate$$inlined$let$lambda$1 implements View.OnClickListener {
    public final void onClick(View param1View) {
      WebViewActivity.this.finish();
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\027\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H\026¨\006\006"}, d2 = {"ca/albertahealthservices/contacttracing/onboarding/WebViewActivity$onCreate$wbc$1", "Landroid/webkit/WebChromeClient;", "onCloseWindow", "", "w", "Landroid/webkit/WebView;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class WebViewActivity$onCreate$wbc$1 extends WebChromeClient {
    public void onCloseWindow(WebView param1WebView) {
      Intrinsics.checkParameterIsNotNull(param1WebView, "w");
      CentralLog.Companion.d(WebViewActivity.this.TAG, "OnCloseWindow for WebChromeClient");
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/onboarding/WebViewActivity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */