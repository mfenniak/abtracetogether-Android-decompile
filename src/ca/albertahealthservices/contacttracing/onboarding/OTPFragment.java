package ca.albertahealthservices.contacttracing.onboarding;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import ca.albertahealthservices.contacttracing.Preference;
import ca.albertahealthservices.contacttracing.R;
import ca.albertahealthservices.contacttracing.Utils;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000l\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\t\n\000\n\002\020\016\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\020!\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\002\n\002\b\006\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\020\030\0002\0020\001:\00289B\005¢\006\002\020\002J\b\020\026\032\0020\027H\026J\020\020\030\032\0020\0062\006\020\031\032\0020\006H\002J\b\020\032\032\0020\027H\002J\b\020\033\032\0020\006H\002J\020\020\034\032\0020\0272\006\020\035\032\0020\036H\026J\020\020\037\032\0020\0272\006\020 \032\0020!H\026J\020\020\"\032\0020\0272\006\020 \032\0020!H\026J\022\020#\032\0020\0272\b\020$\032\004\030\0010%H\026J&\020&\032\004\030\0010!2\006\020'\032\0020(2\b\020)\032\004\030\0010*2\b\020$\032\004\030\0010%H\026J\b\020+\032\0020\027H\026J\b\020,\032\0020\027H\026J\020\020-\032\0020\0272\006\020.\032\0020\006H\026J\020\020/\032\0020\0272\006\0200\032\0020\006H\026J\032\0201\032\0020\0272\006\020 \032\0020!2\b\020$\032\004\030\0010%H\026J\b\0202\032\0020\027H\002J\b\0203\032\0020\027H\002J\020\0204\032\0020\0272\006\0205\032\0020\025H\026J\b\0206\032\0020\027H\002J\020\0207\032\0020\0252\006\0200\032\0020\006H\002R\016\020\003\032\0020\004XD¢\006\002\n\000R\016\020\005\032\0020\006XD¢\006\002\n\000R\016\020\007\032\0020\bX\016¢\006\002\n\000R\016\020\t\032\0020\bX\016¢\006\002\n\000R\020\020\n\032\004\030\0010\013X\016¢\006\002\n\000R\024\020\f\032\b\022\004\022\0020\0160\rX\016¢\006\002\n\000R\020\020\017\032\004\030\0010\006X\016¢\006\002\n\000R\020\020\020\032\004\030\0010\006X\016¢\006\002\n\000R\016\020\021\032\0020\006X.¢\006\002\n\000R\020\020\022\032\004\030\0010\023X\016¢\006\002\n\000R\016\020\024\032\0020\025X\016¢\006\002\n\000¨\006:"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/OTPFragment;", "Lca/albertahealthservices/contacttracing/onboarding/OnboardingFragmentInterface;", "()V", "COUNTDOWN_DURATION", "", "TAG", "", "colorError", "", "colorText", "listener", "Lca/albertahealthservices/contacttracing/onboarding/OTPFragment$OnFragmentInteractionListener;", "otpInputs", "", "Landroid/widget/EditText;", "param1", "param2", "phoneNumber", "stopWatch", "Landroid/os/CountDownTimer;", "timerHasFinished", "", "becomesVisible", "", "buildPhoneString", "phone", "clearInputs", "getOtp", "onAttach", "context", "Landroid/content/Context;", "onBackButtonClick", "view", "Landroid/view/View;", "onButtonClick", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroy", "onDetach", "onError", "error", "onUpdatePhoneNumber", "num", "onViewCreated", "resendCodeAndStartTimer", "resetTimer", "setUserVisibleHint", "isVisibleToUser", "startTimer", "validateNumber", "OTPTextWatcher", "OnFragmentInteractionListener", "app_release"}, k = 1, mv = {1, 1, 16})
public final class OTPFragment extends OnboardingFragmentInterface {
  private final long COUNTDOWN_DURATION = 180L;
  
  private final String TAG = "OTPFragment";
  
  private HashMap _$_findViewCache;
  
  private int colorError;
  
  private int colorText;
  
  private OnFragmentInteractionListener listener;
  
  private List<EditText> otpInputs = new ArrayList<>();
  
  private String param1;
  
  private String param2;
  
  private String phoneNumber;
  
  private CountDownTimer stopWatch;
  
  private boolean timerHasFinished;
  
  private final String buildPhoneString(String paramString) {
    if (paramString.length() < 6)
      return paramString; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("+1 ");
    if (paramString != null) {
      String str = paramString.substring(0, 3);
      Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      stringBuilder.append(str);
      stringBuilder.append("-");
      if (paramString != null) {
        str = paramString.substring(3, 6);
        Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        stringBuilder.append(str);
        stringBuilder.append("-");
        if (paramString != null) {
          paramString = paramString.substring(6);
          Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.String).substring(startIndex)");
          stringBuilder.append(paramString);
          return stringBuilder.toString();
        } 
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } 
      throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    } 
    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
  }
  
  private final void clearInputs() {
    EditText editText = (EditText)_$_findCachedViewById(R.id.otp_et6);
    if (editText != null)
      editText.setText(""); 
    editText = (EditText)_$_findCachedViewById(R.id.otp_et5);
    if (editText != null)
      editText.setText(""); 
    editText = (EditText)_$_findCachedViewById(R.id.otp_et4);
    if (editText != null)
      editText.setText(""); 
    editText = (EditText)_$_findCachedViewById(R.id.otp_et3);
    if (editText != null)
      editText.setText(""); 
    editText = (EditText)_$_findCachedViewById(R.id.otp_et2);
    if (editText != null)
      editText.setText(""); 
    editText = (EditText)_$_findCachedViewById(R.id.otp_et1);
    if (editText != null)
      editText.setText(""); 
  }
  
  private final String getOtp() {
    Iterator<EditText> iterator = this.otpInputs.iterator();
    String str;
    for (str = ""; iterator.hasNext(); str = stringBuilder.toString()) {
      EditText editText = iterator.next();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append(editText.getText());
    } 
    return str;
  }
  
  private final void resendCodeAndStartTimer() {
    OnboardingActivity onboardingActivity = (OnboardingActivity)getActivity();
    clearInputs();
    if (onboardingActivity != null) {
      OnboardingActivity.resendCode$default(onboardingActivity, false, 1, null);
    } else {
      Utils.INSTANCE.restartAppWithNoContext(0, "OTPFragment not attached to OnboardingActivity");
    } 
    resetTimer();
    startTimer();
  }
  
  private final void resetTimer() {
    CountDownTimer countDownTimer = this.stopWatch;
    if (countDownTimer != null)
      countDownTimer.cancel(); 
  }
  
  private final void startTimer() {
    this.timerHasFinished = false;
    long l = this.COUNTDOWN_DURATION;
    OTPFragment$startTimer$1 oTPFragment$startTimer$1 = new OTPFragment$startTimer$1(1000L * l, 1000L);
    this.stopWatch = oTPFragment$startTimer$1;
    if (oTPFragment$startTimer$1 != null)
      oTPFragment$startTimer$1.start(); 
    AppCompatTextView appCompatTextView = (AppCompatTextView)_$_findCachedViewById(R.id.timer);
    if (appCompatTextView != null)
      appCompatTextView.setTextColor(this.colorText); 
  }
  
  private final boolean validateNumber(String paramString) {
    boolean bool;
    if (paramString.length() >= 6) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
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
  
  public void becomesVisible() {
    CentralLog.Companion.d(this.TAG, "becomes visible");
    clearInputs();
  }
  
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
    OnboardingActivity onboardingActivity = (OnboardingActivity)getContext();
    if (onboardingActivity != null) {
      onboardingActivity.onBackPressed();
    } else {
      Utils.INSTANCE.restartAppWithNoContext(0, "OTPFragment not attached to OnboardingActivity");
    } 
  }
  
  public void onButtonClick(View paramView) {
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    CentralLog.Companion.d(this.TAG, "OnButtonClick 3B");
    String str = getOtp();
    OnboardingActivity onboardingActivity = (OnboardingActivity)getContext();
    if (this.timerHasFinished) {
      resendCodeAndStartTimer();
    } else if (onboardingActivity != null) {
      onboardingActivity.validateOTP(str);
    } else {
      Utils.INSTANCE.restartAppWithNoContext(0, "OTPFragment not attached to OnboardingActivity");
    } 
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
    return paramLayoutInflater.inflate(2131492919, paramViewGroup, false);
  }
  
  public void onDestroy() {
    super.onDestroy();
    CountDownTimer countDownTimer = this.stopWatch;
    if (countDownTimer != null)
      countDownTimer.cancel(); 
  }
  
  public void onDetach() {
    super.onDetach();
    this.listener = (OnFragmentInteractionListener)null;
  }
  
  public void onError(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "error");
    OnboardingActivity onboardingActivity = (OnboardingActivity)getContext();
    if (onboardingActivity != null) {
      onboardingActivity.onBackPressed();
    } else {
      Utils.INSTANCE.restartAppWithNoContext(0, "OTPFragment not attached to OnboardingActivity");
    } 
  }
  
  public void onUpdatePhoneNumber(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "num");
    CentralLog.Companion companion = CentralLog.Companion;
    String str = this.TAG;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("onUpdatePhoneNumber ");
    stringBuilder.append(paramString);
    companion.d(str, stringBuilder.toString());
    AppCompatTextView appCompatTextView = (AppCompatTextView)_$_findCachedViewById(R.id.sent_to);
    if (appCompatTextView != null)
      appCompatTextView.setText((CharSequence)HtmlCompat.fromHtml(getString(2131820698, new Object[] { String.valueOf(buildPhoneString(paramString)) }), 0)); 
    this.phoneNumber = paramString;
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    super.onViewCreated(paramView, paramBundle);
    AppCompatTextView appCompatTextView2 = (AppCompatTextView)_$_findCachedViewById(R.id.sent_to);
    if (appCompatTextView2 != null) {
      Preference preference = Preference.INSTANCE;
      Context context1 = getContext();
      if (context1 == null)
        Intrinsics.throwNpe(); 
      Intrinsics.checkExpressionValueIsNotNull(context1, "context!!");
      appCompatTextView2.setText((CharSequence)HtmlCompat.fromHtml(getString(2131820698, new Object[] { String.valueOf(buildPhoneString(preference.getPhoneNumber(context1))) }), 0));
    } 
    Context context = getContext();
    if (context == null)
      Intrinsics.throwNpe(); 
    this.colorError = ContextCompat.getColor(context, 2131034204);
    context = getContext();
    if (context == null)
      Intrinsics.throwNpe(); 
    this.colorText = ContextCompat.getColor(context, 2131034218);
    List<EditText> list4 = this.otpInputs;
    EditText editText4 = (EditText)_$_findCachedViewById(R.id.otp_et1);
    Intrinsics.checkExpressionValueIsNotNull(editText4, "otp_et1");
    list4.add(editText4);
    list4 = this.otpInputs;
    editText4 = (EditText)_$_findCachedViewById(R.id.otp_et2);
    Intrinsics.checkExpressionValueIsNotNull(editText4, "otp_et2");
    list4.add(editText4);
    List<EditText> list2 = this.otpInputs;
    EditText editText6 = (EditText)_$_findCachedViewById(R.id.otp_et3);
    Intrinsics.checkExpressionValueIsNotNull(editText6, "otp_et3");
    list2.add(editText6);
    list2 = this.otpInputs;
    editText6 = (EditText)_$_findCachedViewById(R.id.otp_et4);
    Intrinsics.checkExpressionValueIsNotNull(editText6, "otp_et4");
    list2.add(editText6);
    List<EditText> list3 = this.otpInputs;
    EditText editText3 = (EditText)_$_findCachedViewById(R.id.otp_et5);
    Intrinsics.checkExpressionValueIsNotNull(editText3, "otp_et5");
    list3.add(editText3);
    List<EditText> list1 = this.otpInputs;
    EditText editText5 = (EditText)_$_findCachedViewById(R.id.otp_et6);
    Intrinsics.checkExpressionValueIsNotNull(editText5, "otp_et6");
    list1.add(editText5);
    EditText editText2 = (EditText)_$_findCachedViewById(R.id.otp_et1);
    if (editText2 != null) {
      editText5 = (EditText)_$_findCachedViewById(R.id.otp_et1);
      Intrinsics.checkExpressionValueIsNotNull(editText5, "otp_et1");
      editText2.addTextChangedListener(new OTPTextWatcher((View)editText5, this.otpInputs));
    } 
    editText5 = (EditText)_$_findCachedViewById(R.id.otp_et2);
    if (editText5 != null) {
      editText2 = (EditText)_$_findCachedViewById(R.id.otp_et2);
      Intrinsics.checkExpressionValueIsNotNull(editText2, "otp_et2");
      editText5.addTextChangedListener(new OTPTextWatcher((View)editText2, this.otpInputs));
    } 
    editText2 = (EditText)_$_findCachedViewById(R.id.otp_et3);
    if (editText2 != null) {
      editText5 = (EditText)_$_findCachedViewById(R.id.otp_et3);
      Intrinsics.checkExpressionValueIsNotNull(editText5, "otp_et3");
      editText2.addTextChangedListener(new OTPTextWatcher((View)editText5, this.otpInputs));
    } 
    editText5 = (EditText)_$_findCachedViewById(R.id.otp_et4);
    if (editText5 != null) {
      editText2 = (EditText)_$_findCachedViewById(R.id.otp_et4);
      Intrinsics.checkExpressionValueIsNotNull(editText2, "otp_et4");
      editText5.addTextChangedListener(new OTPTextWatcher((View)editText2, this.otpInputs));
    } 
    editText5 = (EditText)_$_findCachedViewById(R.id.otp_et5);
    if (editText5 != null) {
      editText2 = (EditText)_$_findCachedViewById(R.id.otp_et5);
      Intrinsics.checkExpressionValueIsNotNull(editText2, "otp_et5");
      editText5.addTextChangedListener(new OTPTextWatcher((View)editText2, this.otpInputs));
    } 
    editText2 = (EditText)_$_findCachedViewById(R.id.otp_et6);
    if (editText2 != null) {
      editText5 = (EditText)_$_findCachedViewById(R.id.otp_et6);
      Intrinsics.checkExpressionValueIsNotNull(editText5, "otp_et6");
      editText2.addTextChangedListener(new OTPTextWatcher((View)editText5, this.otpInputs));
    } 
    AppCompatTextView appCompatTextView1 = (AppCompatTextView)_$_findCachedViewById(R.id.resendCode);
    if (appCompatTextView1 != null)
      appCompatTextView1.setOnClickListener(new OTPFragment$onViewCreated$1()); 
    EditText editText1 = (EditText)_$_findCachedViewById(R.id.otp_et6);
    if (editText1 != null)
      editText1.setOnEditorActionListener(new OTPFragment$onViewCreated$2(paramView)); 
  }
  
  public void setUserVisibleHint(boolean paramBoolean) {
    super.setUserVisibleHint(paramBoolean);
    if (paramBoolean) {
      startTimer();
    } else {
      resetTimer();
    } 
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\0008\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020!\n\002\030\002\n\002\b\005\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\r\n\000\n\002\020\b\n\002\b\004\b\004\030\0002\0020\001B\033\022\006\020\002\032\0020\003\022\f\020\004\032\b\022\004\022\0020\0060\005¢\006\002\020\007J\020\020\013\032\0020\f2\006\020\r\032\0020\016H\026J(\020\017\032\0020\f2\006\020\020\032\0020\0212\006\020\022\032\0020\0232\006\020\024\032\0020\0232\006\020\025\032\0020\023H\026J(\020\026\032\0020\f2\006\020\020\032\0020\0212\006\020\022\032\0020\0232\006\020\024\032\0020\0232\006\020\025\032\0020\023H\026R\024\020\b\032\b\022\004\022\0020\0060\005X\016¢\006\002\n\000R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\t\020\n¨\006\027"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/OTPFragment$OTPTextWatcher;", "Landroid/text/TextWatcher;", "view", "Landroid/view/View;", "otpInputs", "", "Landroid/widget/EditText;", "(Lca/albertahealthservices/contacttracing/onboarding/OTPFragment;Landroid/view/View;Ljava/util/List;)V", "inputs", "getView", "()Landroid/view/View;", "afterTextChanged", "", "editable", "Landroid/text/Editable;", "beforeTextChanged", "arg0", "", "arg1", "", "arg2", "arg3", "onTextChanged", "app_release"}, k = 1, mv = {1, 1, 16})
  public final class OTPTextWatcher implements TextWatcher {
    private List<EditText> inputs;
    
    private final View view;
    
    public OTPTextWatcher(View param1View, List<EditText> param1List) {
      this.view = param1View;
      this.inputs = param1List;
    }
    
    public void afterTextChanged(Editable param1Editable) {
      Intrinsics.checkParameterIsNotNull(param1Editable, "editable");
      String str = param1Editable.toString();
      int i = this.view.getId();
      boolean bool1 = false;
      boolean bool2 = false;
      boolean bool3 = false;
      boolean bool4 = false;
      boolean bool5 = true;
      switch (i) {
        default:
          return;
        case 2131296511:
          bool5 = bool4;
          if (str.length() == 0)
            bool5 = true; 
          if (bool5)
            ((EditText)this.inputs.get(4)).requestFocus(); 
        case 2131296510:
          if (str.length() == 1) {
            str = (String)this.inputs.get(5);
          } else {
            bool5 = bool1;
            if (str.length() == 0)
              bool5 = true; 
            if (bool5) {
              str = (String)this.inputs.get(3);
            } else {
            
            } 
          } 
          ((EditText)str).requestFocus();
        case 2131296509:
          if (str.length() == 1) {
            str = (String)this.inputs.get(4);
          } else {
            bool5 = bool2;
            if (str.length() == 0)
              bool5 = true; 
            if (bool5) {
              str = (String)this.inputs.get(2);
            } else {
            
            } 
          } 
          ((EditText)str).requestFocus();
        case 2131296508:
          if (str.length() == 1) {
            str = (String)this.inputs.get(3);
          } else {
            bool5 = bool3;
            if (str.length() == 0)
              bool5 = true; 
            if (bool5) {
              str = (String)this.inputs.get(1);
            } else {
            
            } 
          } 
          ((EditText)str).requestFocus();
        case 2131296507:
          if (str.length() == 1) {
            str = (String)this.inputs.get(2);
          } else {
            if (str.length() != 0)
              bool5 = false; 
            if (bool5) {
              str = (String)this.inputs.get(0);
            } else {
            
            } 
          } 
          ((EditText)str).requestFocus();
        case 2131296506:
          break;
      } 
      if (str.length() == 1)
        ((EditText)this.inputs.get(1)).requestFocus(); 
    }
    
    public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
      Intrinsics.checkParameterIsNotNull(param1CharSequence, "arg0");
    }
    
    public final View getView() {
      return this.view;
    }
    
    public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
      Intrinsics.checkParameterIsNotNull(param1CharSequence, "arg0");
      OTPFragment oTPFragment = OTPFragment.this;
      if (oTPFragment.validateNumber(oTPFragment.getOtp())) {
        Utils utils = Utils.INSTANCE;
        Context context = this.view.getContext();
        Intrinsics.checkExpressionValueIsNotNull(context, "view.context");
        utils.hideKeyboardFrom(context, this.view);
        OTPFragment.this.enableButton();
        OTPFragment oTPFragment1 = OTPFragment.this;
        String str = oTPFragment1.getString(2131820762);
        Intrinsics.checkExpressionValueIsNotNull(str, "getString(R.string.submit_button)");
        oTPFragment1.setButtonText(str);
      } else {
        OTPFragment.this.disableButton();
        OTPFragment oTPFragment1 = OTPFragment.this;
        String str = oTPFragment1.getString(2131820687);
        Intrinsics.checkExpressionValueIsNotNull(str, "getString(R.string.next_button)");
        oTPFragment1.setButtonText(str);
      } 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\026\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\030\002\n\000\bf\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H&¨\006\006"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/OTPFragment$OnFragmentInteractionListener;", "", "onFragmentInteraction", "", "uri", "Landroid/net/Uri;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static interface OnFragmentInteractionListener {
    void onFragmentInteraction(Uri param1Uri);
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class OTPFragment$onViewCreated$1 implements View.OnClickListener {
    public final void onClick(View param1View) {
      CentralLog.Companion.d(OTPFragment.this.TAG, "resend pressed");
      OTPFragment.this.resendCodeAndStartTimer();
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\034\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\000\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\0032\006\020\005\032\0020\0062\016\020\007\032\n \004*\004\030\0010\b0\bH\n¢\006\002\b\t"}, d2 = {"<anonymous>", "", "<anonymous parameter 0>", "Landroid/widget/TextView;", "kotlin.jvm.PlatformType", "actionId", "", "<anonymous parameter 2>", "Landroid/view/KeyEvent;", "onEditorAction"}, k = 3, mv = {1, 1, 16})
  static final class OTPFragment$onViewCreated$2 implements TextView.OnEditorActionListener {
    OTPFragment$onViewCreated$2(View param1View) {}
    
    public final boolean onEditorAction(TextView param1TextView, int param1Int, KeyEvent param1KeyEvent) {
      if (param1Int == 2) {
        Utils utils = Utils.INSTANCE;
        Context context = this.$view.getContext();
        Intrinsics.checkExpressionValueIsNotNull(context, "view.context");
        utils.hideKeyboardFrom(context, this.$view);
        String str = OTPFragment.this.getOtp();
        OnboardingActivity onboardingActivity = (OnboardingActivity)OTPFragment.this.getContext();
        if (onboardingActivity != null) {
          onboardingActivity.validateOTP(str);
        } else {
          Utils.INSTANCE.restartAppWithNoContext(0, "OTPFragment not attached to OnboardingActivity");
        } 
      } 
      return false;
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\031\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\002\n\002\020\t\n\000*\001\000\b\n\030\0002\0020\001J\b\020\002\032\0020\003H\026J\020\020\004\032\0020\0032\006\020\005\032\0020\006H\026¨\006\007"}, d2 = {"ca/albertahealthservices/contacttracing/onboarding/OTPFragment$startTimer$1", "Landroid/os/CountDownTimer;", "onFinish", "", "onTick", "millisUntilFinished", "", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class OTPFragment$startTimer$1 extends CountDownTimer {
    OTPFragment$startTimer$1(long param1Long1, long param1Long2) {
      super(param1Long1, param1Long2);
    }
    
    public void onFinish() {
      AppCompatTextView appCompatTextView = (AppCompatTextView)OTPFragment.this._$_findCachedViewById(R.id.timer);
      if (appCompatTextView != null)
        appCompatTextView.setText(OTPFragment.this.getString(2131820697)); 
      appCompatTextView = (AppCompatTextView)OTPFragment.this._$_findCachedViewById(R.id.timer);
      if (appCompatTextView != null)
        appCompatTextView.setTextColor(OTPFragment.this.colorError); 
      OTPFragment oTPFragment = OTPFragment.this;
      String str = oTPFragment.getString(2131820738);
      Intrinsics.checkExpressionValueIsNotNull(str, "getString(R.string.resend_button)");
      oTPFragment.setButtonText(str);
      OTPFragment.this.enableButton();
      OTPFragment.this.timerHasFinished = true;
    }
    
    public void onTick(long param1Long) {
      String str;
      double d = param1Long;
      int i = (int)Math.floor(1.0D * d / '');
      int j = (int)Math.floor(d / 1000.0D % 60);
      if (j < 10) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('0');
        stringBuilder.append(j);
        str = stringBuilder.toString();
      } else {
        str = String.valueOf(j);
      } 
      AppCompatTextView appCompatTextView = (AppCompatTextView)OTPFragment.this._$_findCachedViewById(R.id.timer);
      if (appCompatTextView != null) {
        OTPFragment oTPFragment = OTPFragment.this;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>");
        stringBuilder.append(i);
        stringBuilder.append(':');
        stringBuilder.append(str);
        stringBuilder.append("</b>");
        appCompatTextView.setText((CharSequence)HtmlCompat.fromHtml(oTPFragment.getString(2131820696, new Object[] { stringBuilder.toString() }), 0));
      } 
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/onboarding/OTPFragment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */