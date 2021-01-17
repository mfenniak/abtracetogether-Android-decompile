package ca.albertahealthservices.contacttracing.onboarding;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.widget.AppCompatTextView;
import ca.albertahealthservices.contacttracing.R;
import ca.albertahealthservices.contacttracing.Utils;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

@Metadata(bv = {1, 0, 3}, d1 = {"\000^\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\t\n\002\020\b\n\002\b\002\030\0002\0020\001:\001-B\005¢\006\002\020\002J\b\020\f\032\0020\rH\026J \020\016\032\0020\0172\006\020\020\032\0020\0042\006\020\021\032\0020\0042\006\020\022\032\0020\023H\002J\020\020\024\032\0020\r2\006\020\025\032\0020\tH\026J\020\020\026\032\0020\r2\006\020\027\032\0020\030H\026J\020\020\031\032\0020\r2\006\020\032\032\0020\030H\026J\022\020\033\032\0020\r2\b\020\034\032\004\030\0010\035H\026J&\020\036\032\004\030\0010\0302\006\020\037\032\0020 2\b\020!\032\004\030\0010\"2\b\020\034\032\004\030\0010\035H\026J\b\020#\032\0020\rH\026J\020\020$\032\0020\r2\006\020%\032\0020\004H\026J\020\020&\032\0020\r2\006\020'\032\0020\004H\026J\032\020(\032\0020\r2\006\020\027\032\0020\0302\b\020\034\032\004\030\0010\035H\026J\b\020)\032\0020\rH\002J\020\020*\032\0020\r2\006\020+\032\0020,H\002R\016\020\003\032\0020\004XD¢\006\002\n\000R\016\020\005\032\0020\004X\016¢\006\002\n\000R\020\020\006\032\004\030\0010\007X\016¢\006\002\n\000R\016\020\b\032\0020\tX.¢\006\002\n\000R\020\020\n\032\004\030\0010\004X\016¢\006\002\n\000R\020\020\013\032\004\030\0010\004X\016¢\006\002\n\000¨\006."}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/TOUFragment;", "Lca/albertahealthservices/contacttracing/onboarding/OnboardingFragmentInterface;", "()V", "TAG", "", "helpEmail", "listener", "Lca/albertahealthservices/contacttracing/onboarding/TOUFragment$OnFragmentInteractionListener;", "mainContext", "Landroid/content/Context;", "param1", "param2", "becomesVisible", "", "createSpannableString", "Landroid/text/SpannableString;", "string", "span", "clickableSpan", "Landroid/text/style/ClickableSpan;", "onAttach", "context", "onBackButtonClick", "view", "Landroid/view/View;", "onButtonClick", "buttonView", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDetach", "onError", "error", "onUpdatePhoneNumber", "num", "onViewCreated", "sendEmailIntent", "startWebActivityIntent", "type", "", "OnFragmentInteractionListener", "app_release"}, k = 1, mv = {1, 1, 16})
public final class TOUFragment extends OnboardingFragmentInterface {
  private final String TAG = "TOUFragment";
  
  private HashMap _$_findViewCache;
  
  private String helpEmail = "";
  
  private OnFragmentInteractionListener listener;
  
  private Context mainContext;
  
  private String param1;
  
  private String param2;
  
  private final SpannableString createSpannableString(String paramString1, String paramString2, ClickableSpan paramClickableSpan) {
    CharSequence charSequence = paramString1;
    int i = StringsKt.indexOf$default(charSequence, "%s", 0, false, 6, null);
    SpannableString spannableString = new SpannableString((new Regex("\\%s\\b")).replaceFirst(charSequence, paramString2));
    if (i > 0)
      spannableString.setSpan(paramClickableSpan, i, paramString2.length() + i, 33); 
    return spannableString;
  }
  
  private final void sendEmailIntent() {
    String str = this.helpEmail;
    Intent intent = new Intent("android.intent.action.SENDTO");
    intent.setType("text/plain");
    intent.setData(Uri.parse("mailto:"));
    intent.putExtra("android.intent.extra.EMAIL", new String[] { str });
    Context context = getContext();
    if (context == null)
      Intrinsics.throwNpe(); 
    Intrinsics.checkExpressionValueIsNotNull(context, "context!!");
    if (intent.resolveActivity(context.getPackageManager()) != null)
      startActivity(intent); 
  }
  
  private final void startWebActivityIntent(int paramInt) {
    Context context = this.mainContext;
    if (context == null)
      Intrinsics.throwUninitializedPropertyAccessException("mainContext"); 
    Intent intent = new Intent(context, WebViewActivity.class);
    intent.putExtra("type", paramInt);
    startActivity(intent);
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
    this.mainContext = paramContext;
    if (paramContext instanceof OnFragmentInteractionListener) {
      this.listener = (OnFragmentInteractionListener)paramContext;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramContext);
    stringBuilder.append(" must  implement OnFragmentInteractionListener");
    throw (Throwable)new RuntimeException(stringBuilder.toString());
  }
  
  public void onBackButtonClick(View paramView) {
    Intrinsics.checkParameterIsNotNull(paramView, "view");
  }
  
  public void onButtonClick(View paramView) {
    Intrinsics.checkParameterIsNotNull(paramView, "buttonView");
    CentralLog.Companion.d(this.TAG, "OnButtonClick 4");
    OnboardingActivity onboardingActivity = (OnboardingActivity)getContext();
    if (onboardingActivity != null) {
      onboardingActivity.navigateToNextPage();
    } else {
      Utils.INSTANCE.restartAppWithNoContext(0, "TOUFragment not attached to OnboardingActivity");
    } 
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    paramBundle = getArguments();
    if (paramBundle != null) {
      this.param1 = paramBundle.getString("param1");
      this.param2 = paramBundle.getString("param2");
    } 
    String str = getString(2131820626);
    Intrinsics.checkExpressionValueIsNotNull(str, "getString(R.string.help_desk_email)");
    this.helpEmail = str;
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramLayoutInflater, "inflater");
    return paramLayoutInflater.inflate(2131492923, paramViewGroup, false);
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
    Button button = (Button)_$_findCachedViewById(R.id.privacy_button);
    if (button != null)
      button.setOnClickListener(new TOUFragment$onViewCreated$1()); 
    button = (Button)_$_findCachedViewById(R.id.faq_button);
    if (button != null)
      button.setOnClickListener(new TOUFragment$onViewCreated$2()); 
    disableButton();
    TOUFragment$onViewCreated$privacyClickableSpan$1 tOUFragment$onViewCreated$privacyClickableSpan$1 = new TOUFragment$onViewCreated$privacyClickableSpan$1();
    TOUFragment$onViewCreated$faqClickableSpan$1 tOUFragment$onViewCreated$faqClickableSpan$1 = new TOUFragment$onViewCreated$faqClickableSpan$1();
    AppCompatTextView appCompatTextView3 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc6);
    if (appCompatTextView3 != null) {
      AppCompatTextView appCompatTextView = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc6);
      Intrinsics.checkExpressionValueIsNotNull(appCompatTextView, "privacy_desc6");
      String str2 = appCompatTextView.getText().toString();
      String str1 = getString(2131820727);
      Intrinsics.checkExpressionValueIsNotNull(str1, "getString(R.string.privacy_statement_label)");
      appCompatTextView3.setText((CharSequence)createSpannableString(str2, str1, tOUFragment$onViewCreated$privacyClickableSpan$1));
    } 
    AppCompatTextView appCompatTextView2 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc9);
    if (appCompatTextView2 != null) {
      appCompatTextView3 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc9);
      Intrinsics.checkExpressionValueIsNotNull(appCompatTextView3, "privacy_desc9");
      String str2 = appCompatTextView3.getText().toString();
      String str1 = getString(2131820618);
      Intrinsics.checkExpressionValueIsNotNull(str1, "getString(R.string.faq_statement_label)");
      appCompatTextView2.setText((CharSequence)createSpannableString(str2, str1, tOUFragment$onViewCreated$faqClickableSpan$1));
    } 
    TOUFragment$onViewCreated$emailClickableSpan$1 tOUFragment$onViewCreated$emailClickableSpan$1 = new TOUFragment$onViewCreated$emailClickableSpan$1();
    appCompatTextView3 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc10);
    if (appCompatTextView3 != null) {
      appCompatTextView2 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc10);
      Intrinsics.checkExpressionValueIsNotNull(appCompatTextView2, "privacy_desc10");
      appCompatTextView3.setText((CharSequence)createSpannableString(appCompatTextView2.getText().toString(), this.helpEmail, tOUFragment$onViewCreated$emailClickableSpan$1));
    } 
    appCompatTextView2 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc12);
    if (appCompatTextView2 != null) {
      appCompatTextView3 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc12);
      Intrinsics.checkExpressionValueIsNotNull(appCompatTextView3, "privacy_desc12");
      appCompatTextView2.setText((CharSequence)createSpannableString(appCompatTextView3.getText().toString(), this.helpEmail, tOUFragment$onViewCreated$emailClickableSpan$1));
    } 
    Switch switch_ = (Switch)_$_findCachedViewById(R.id.checkbox_agreement);
    if (switch_ != null)
      switch_.setOnCheckedChangeListener(new TOUFragment$onViewCreated$3()); 
    AppCompatTextView appCompatTextView1 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc6);
    if (appCompatTextView1 != null)
      appCompatTextView1.setMovementMethod(LinkMovementMethod.getInstance()); 
    appCompatTextView1 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc9);
    if (appCompatTextView1 != null)
      appCompatTextView1.setMovementMethod(LinkMovementMethod.getInstance()); 
    appCompatTextView1 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc10);
    if (appCompatTextView1 != null)
      appCompatTextView1.setMovementMethod(LinkMovementMethod.getInstance()); 
    appCompatTextView1 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc12);
    if (appCompatTextView1 != null)
      appCompatTextView1.setMovementMethod(LinkMovementMethod.getInstance()); 
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\026\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\030\002\n\000\bf\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H&¨\006\006"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/TOUFragment$OnFragmentInteractionListener;", "", "onFragmentInteraction", "", "uri", "Landroid/net/Uri;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static interface OnFragmentInteractionListener {
    void onFragmentInteraction(Uri param1Uri);
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class TOUFragment$onViewCreated$1 implements View.OnClickListener {
    public final void onClick(View param1View) {
      CentralLog.Companion.d(TOUFragment.this.TAG, "clicked view privacy");
      TOUFragment.this.startWebActivityIntent(0);
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class TOUFragment$onViewCreated$2 implements View.OnClickListener {
    public final void onClick(View param1View) {
      CentralLog.Companion.d(TOUFragment.this.TAG, "clicked view faq");
      TOUFragment.this.startWebActivityIntent(1);
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\026\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\000\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\0032\006\020\005\032\0020\006H\n¢\006\002\b\007"}, d2 = {"<anonymous>", "", "buttonView", "Landroid/widget/CompoundButton;", "kotlin.jvm.PlatformType", "isChecked", "", "onCheckedChanged"}, k = 3, mv = {1, 1, 16})
  static final class TOUFragment$onViewCreated$3 implements CompoundButton.OnCheckedChangeListener {
    public final void onCheckedChanged(CompoundButton param1CompoundButton, boolean param1Boolean) {
      Switch switch_ = (Switch)TOUFragment.this._$_findCachedViewById(R.id.checkbox_agreement);
      Intrinsics.checkExpressionValueIsNotNull(switch_, "checkbox_agreement");
      if (switch_.isChecked()) {
        TOUFragment.this.enableButton();
      } else {
        TOUFragment.this.disableButton();
      } 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\027\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H\026¨\006\006"}, d2 = {"ca/albertahealthservices/contacttracing/onboarding/TOUFragment$onViewCreated$emailClickableSpan$1", "Landroid/text/style/ClickableSpan;", "onClick", "", "textView", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class TOUFragment$onViewCreated$emailClickableSpan$1 extends ClickableSpan {
    public void onClick(View param1View) {
      Intrinsics.checkParameterIsNotNull(param1View, "textView");
      CentralLog.Companion.d(TOUFragment.this.TAG, "Starting send email intent");
      TOUFragment.this.sendEmailIntent();
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\027\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H\026¨\006\006"}, d2 = {"ca/albertahealthservices/contacttracing/onboarding/TOUFragment$onViewCreated$faqClickableSpan$1", "Landroid/text/style/ClickableSpan;", "onClick", "", "textView", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class TOUFragment$onViewCreated$faqClickableSpan$1 extends ClickableSpan {
    public void onClick(View param1View) {
      Intrinsics.checkParameterIsNotNull(param1View, "textView");
      TOUFragment.this.startWebActivityIntent(1);
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\027\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H\026¨\006\006"}, d2 = {"ca/albertahealthservices/contacttracing/onboarding/TOUFragment$onViewCreated$privacyClickableSpan$1", "Landroid/text/style/ClickableSpan;", "onClick", "", "textView", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class TOUFragment$onViewCreated$privacyClickableSpan$1 extends ClickableSpan {
    public void onClick(View param1View) {
      Intrinsics.checkParameterIsNotNull(param1View, "textView");
      TOUFragment.this.startWebActivityIntent(0);
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/onboarding/TOUFragment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */