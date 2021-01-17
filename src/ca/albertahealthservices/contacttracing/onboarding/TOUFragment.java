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
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import ca.albertahealthservices.contacttracing.R;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

@Metadata(bv = {1, 0, 3}, d1 = {"\000Z\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\t\030\0002\0020\001:\001+B\005¢\006\002\020\002J\b\020\r\032\0020\016H\026J \020\017\032\0020\0202\006\020\021\032\0020\0042\006\020\022\032\0020\0042\006\020\023\032\0020\024H\002J\020\020\025\032\0020\0162\006\020\026\032\0020\bH\026J\020\020\027\032\0020\0162\006\020\030\032\0020\031H\026J\020\020\032\032\0020\0162\006\020\033\032\0020\031H\026J\022\020\034\032\0020\0162\b\020\035\032\004\030\0010\036H\026J&\020\037\032\004\030\0010\0312\006\020 \032\0020!2\b\020\"\032\004\030\0010#2\b\020\035\032\004\030\0010\036H\026J\b\020$\032\0020\016H\026J\020\020%\032\0020\0162\006\020&\032\0020\004H\026J\020\020'\032\0020\0162\006\020(\032\0020\004H\026J\032\020)\032\0020\0162\006\020\030\032\0020\0312\b\020\035\032\004\030\0010\036H\026J\b\020*\032\0020\016H\002R\016\020\003\032\0020\004XD¢\006\002\n\000R\020\020\005\032\004\030\0010\006X\016¢\006\002\n\000R\016\020\007\032\0020\bX.¢\006\002\n\000R\020\020\t\032\004\030\0010\004X\016¢\006\002\n\000R\020\020\n\032\004\030\0010\004X\016¢\006\002\n\000R\016\020\013\032\0020\fX.¢\006\002\n\000¨\006,"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/TOUFragment;", "Lca/albertahealthservices/contacttracing/onboarding/OnboardingFragmentInterface;", "()V", "TAG", "", "listener", "Lca/albertahealthservices/contacttracing/onboarding/TOUFragment$OnFragmentInteractionListener;", "mainContext", "Landroid/content/Context;", "param1", "param2", "privacyTextView", "Landroid/widget/TextView;", "becomesVisible", "", "createSpannableString", "Landroid/text/SpannableString;", "string", "span", "clickableSpan", "Landroid/text/style/ClickableSpan;", "onAttach", "context", "onBackButtonClick", "view", "Landroid/view/View;", "onButtonClick", "buttonView", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDetach", "onError", "error", "onUpdatePhoneNumber", "num", "onViewCreated", "sendEmailIntent", "OnFragmentInteractionListener", "app_release"}, k = 1, mv = {1, 1, 16})
public final class TOUFragment extends OnboardingFragmentInterface {
  private final String TAG = "TOUFragment";
  
  private HashMap _$_findViewCache;
  
  private OnFragmentInteractionListener listener;
  
  private Context mainContext;
  
  private String param1;
  
  private String param2;
  
  private TextView privacyTextView;
  
  private final SpannableString createSpannableString(String paramString1, String paramString2, ClickableSpan paramClickableSpan) {
    CharSequence charSequence = paramString1;
    int i = StringsKt.indexOf$default(charSequence, "%s", 0, false, 6, null);
    SpannableString spannableString = new SpannableString((new Regex("\\%s\\b")).replaceFirst(charSequence, paramString2));
    spannableString.setSpan(paramClickableSpan, i, paramString2.length() + i, 33);
    return spannableString;
  }
  
  private final void sendEmailIntent() {
    Intent intent = new Intent("android.intent.action.SENDTO");
    intent.setType("text/plain");
    intent.setData(Uri.parse("mailto:"));
    intent.putExtra("android.intent.extra.EMAIL", new String[] { "HiaHelpDesk@gov.ab.ca" });
    Context context = getContext();
    if (context == null)
      Intrinsics.throwNpe(); 
    Intrinsics.checkExpressionValueIsNotNull(context, "context!!");
    if (intent.resolveActivity(context.getPackageManager()) != null)
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
    Context context = getContext();
    if (context != null) {
      ((OnboardingActivity)context).navigateToNextPage();
      return;
    } 
    throw new TypeCastException("null cannot be cast to non-null type ca.albertahealthservices.contacttracing.onboarding.OnboardingActivity");
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
    ((Button)_$_findCachedViewById(R.id.privacy_button)).setOnClickListener(new TOUFragment$onViewCreated$1());
    ((Button)_$_findCachedViewById(R.id.faq_button)).setOnClickListener(new TOUFragment$onViewCreated$2());
    disableButton();
    TOUFragment$onViewCreated$privacyClickableSpan$1 tOUFragment$onViewCreated$privacyClickableSpan$1 = new TOUFragment$onViewCreated$privacyClickableSpan$1();
    AppCompatTextView appCompatTextView3 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc4);
    Intrinsics.checkExpressionValueIsNotNull(appCompatTextView3, "privacy_desc4");
    AppCompatTextView appCompatTextView1 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc4);
    Intrinsics.checkExpressionValueIsNotNull(appCompatTextView1, "privacy_desc4");
    appCompatTextView3.setText((CharSequence)createSpannableString(appCompatTextView1.getText().toString(), "Privacy Statement", tOUFragment$onViewCreated$privacyClickableSpan$1));
    TOUFragment$onViewCreated$emailClickableSpan$1 tOUFragment$onViewCreated$emailClickableSpan$1 = new TOUFragment$onViewCreated$emailClickableSpan$1();
    AppCompatTextView appCompatTextView2 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc7);
    Intrinsics.checkExpressionValueIsNotNull(appCompatTextView2, "privacy_desc7");
    appCompatTextView3 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc7);
    Intrinsics.checkExpressionValueIsNotNull(appCompatTextView3, "privacy_desc7");
    appCompatTextView2.setText((CharSequence)createSpannableString(appCompatTextView3.getText().toString(), "HiaHelpDesk@gov.ab.ca", tOUFragment$onViewCreated$emailClickableSpan$1));
    appCompatTextView2 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc8);
    Intrinsics.checkExpressionValueIsNotNull(appCompatTextView2, "privacy_desc8");
    appCompatTextView3 = (AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc8);
    Intrinsics.checkExpressionValueIsNotNull(appCompatTextView3, "privacy_desc8");
    appCompatTextView2.setText((CharSequence)createSpannableString(appCompatTextView3.getText().toString(), "HiaHelpDesk@gov.ab.ca", tOUFragment$onViewCreated$emailClickableSpan$1));
    ((Switch)_$_findCachedViewById(R.id.checkbox_agreement)).setOnCheckedChangeListener(new TOUFragment$onViewCreated$3());
    ((AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc4)).setMovementMethod(LinkMovementMethod.getInstance());
    ((AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc7)).setMovementMethod(LinkMovementMethod.getInstance());
    ((AppCompatTextView)_$_findCachedViewById(R.id.privacy_desc8)).setMovementMethod(LinkMovementMethod.getInstance());
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\026\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\030\002\n\000\bf\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H&¨\006\006"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/TOUFragment$OnFragmentInteractionListener;", "", "onFragmentInteraction", "", "uri", "Landroid/net/Uri;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static interface OnFragmentInteractionListener {
    void onFragmentInteraction(Uri param1Uri);
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class TOUFragment$onViewCreated$1 implements View.OnClickListener {
    public final void onClick(View param1View) {
      CentralLog.Companion.d(TOUFragment.this.TAG, "clicked view privacy");
      Intent intent = new Intent(TOUFragment.access$getMainContext$p(TOUFragment.this), WebViewActivity.class);
      intent.putExtra("type", 0);
      TOUFragment.this.startActivity(intent);
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class TOUFragment$onViewCreated$2 implements View.OnClickListener {
    public final void onClick(View param1View) {
      CentralLog.Companion.d(TOUFragment.this.TAG, "clicked view faq");
      Intent intent = new Intent(TOUFragment.access$getMainContext$p(TOUFragment.this), WebViewActivity.class);
      intent.putExtra("type", 1);
      TOUFragment.this.startActivity(intent);
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
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\027\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H\026¨\006\006"}, d2 = {"ca/albertahealthservices/contacttracing/onboarding/TOUFragment$onViewCreated$privacyClickableSpan$1", "Landroid/text/style/ClickableSpan;", "onClick", "", "textView", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class TOUFragment$onViewCreated$privacyClickableSpan$1 extends ClickableSpan {
    public void onClick(View param1View) {
      Intrinsics.checkParameterIsNotNull(param1View, "textView");
      Intent intent = new Intent(TOUFragment.access$getMainContext$p(TOUFragment.this), WebViewActivity.class);
      intent.putExtra("type", 0);
      TOUFragment.this.startActivity(intent);
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/onboarding/TOUFragment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */