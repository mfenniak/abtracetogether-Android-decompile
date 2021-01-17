package ca.albertahealthservices.contacttracing.onboarding;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import ca.albertahealthservices.contacttracing.Preference;
import ca.albertahealthservices.contacttracing.R;
import ca.albertahealthservices.contacttracing.TracerApp;
import ca.albertahealthservices.contacttracing.Utils;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

@Metadata(bv = {1, 0, 3}, d1 = {"\000P\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\020\002\n\002\b\006\n\002\030\002\n\002\b\006\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\t\030\0002\0020\001:\001)B\005¢\006\002\020\002J\020\020\016\032\0020\0172\006\020\020\032\0020\004H\002J\b\020\021\032\0020\017H\026J\b\020\022\032\0020\017H\002J\020\020\023\032\0020\0042\006\020\020\032\0020\004H\002J\020\020\024\032\0020\0172\006\020\025\032\0020\026H\026J\020\020\027\032\0020\0172\006\020\030\032\0020\013H\026J\020\020\031\032\0020\0172\006\020\032\032\0020\013H\026J$\020\033\032\0020\0132\006\020\034\032\0020\0352\b\020\036\032\004\030\0010\0372\b\020 \032\004\030\0010!H\026J\b\020\"\032\0020\017H\026J\020\020#\032\0020\0172\006\020$\032\0020\004H\026J\020\020%\032\0020\0172\006\020\020\032\0020\004H\026J\032\020&\032\0020\0172\006\020\030\032\0020\0132\b\020 \032\004\030\0010!H\026J\b\020'\032\0020\017H\002J\020\020(\032\0020\0062\006\020\020\032\0020\004H\002R\016\020\003\032\0020\004XD¢\006\002\n\000R\016\020\005\032\0020\006X\016¢\006\002\n\000R\016\020\007\032\0020\006X\016¢\006\002\n\000R\020\020\b\032\004\030\0010\tX\016¢\006\002\n\000R\020\020\n\032\004\030\0010\013X\016¢\006\002\n\000R\016\020\f\032\0020\rX\016¢\006\002\n\000¨\006*"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/RegisterNumberFragment;", "Lca/albertahealthservices/contacttracing/onboarding/OnboardingFragmentInterface;", "()V", "TAG", "", "backspaceFlag", "", "editFlag", "listener", "Lca/albertahealthservices/contacttracing/onboarding/RegisterNumberFragment$OnFragmentInteractionListener;", "mView", "Landroid/view/View;", "selectionPointer", "", "applyMask", "", "num", "becomesVisible", "disableButtonAndRequestOTP", "getUnmaskedNumber", "onAttach", "context", "Landroid/content/Context;", "onBackButtonClick", "view", "onButtonClick", "buttonView", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDetach", "onError", "error", "onUpdatePhoneNumber", "onViewCreated", "requestOTP", "validateNumber", "OnFragmentInteractionListener", "app_release"}, k = 1, mv = {1, 1, 16})
public final class RegisterNumberFragment extends OnboardingFragmentInterface {
  private final String TAG = "RegisterNumberFragment";
  
  private HashMap _$_findViewCache;
  
  private boolean backspaceFlag;
  
  private boolean editFlag;
  
  private OnFragmentInteractionListener listener;
  
  private View mView;
  
  private int selectionPointer;
  
  private final void applyMask(String paramString) {
    paramString = getUnmaskedNumber(paramString);
    int i = paramString.length();
    if (!this.editFlag) {
      EditText editText;
      if (i >= 6 && !this.backspaceFlag) {
        this.editFlag = true;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        if (paramString != null) {
          String str = paramString.substring(0, 3);
          Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
          stringBuilder.append(str);
          stringBuilder.append(") ");
          if (paramString != null) {
            str = paramString.substring(3, 6);
            Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            stringBuilder.append(str);
            stringBuilder.append("-");
            if (paramString != null) {
              paramString = paramString.substring(6);
              Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.String).substring(startIndex)");
              stringBuilder.append(paramString);
              String str1 = stringBuilder.toString();
              editText = (EditText)_$_findCachedViewById(R.id.phone_number);
              if (editText != null)
                editText.setText(str1); 
              EditText editText1 = (EditText)_$_findCachedViewById(R.id.phone_number);
              if (editText1 != null) {
                editText = (EditText)_$_findCachedViewById(R.id.phone_number);
                Intrinsics.checkExpressionValueIsNotNull(editText, "phone_number");
                editText1.setSelection(editText.getText().length() - this.selectionPointer);
              } 
            } else {
              throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } 
          } else {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
          } 
        } else {
          throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } 
      } else if (i >= 3 && !this.backspaceFlag) {
        this.editFlag = true;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        if (editText != null) {
          String str = editText.substring(0, 3);
          Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
          stringBuilder.append(str);
          stringBuilder.append(") ");
          if (editText != null) {
            String str1 = editText.substring(3);
            Intrinsics.checkExpressionValueIsNotNull(str1, "(this as java.lang.String).substring(startIndex)");
            stringBuilder.append(str1);
            String str2 = stringBuilder.toString();
            EditText editText1 = (EditText)_$_findCachedViewById(R.id.phone_number);
            if (editText1 != null)
              editText1.setText(str2); 
            EditText editText2 = (EditText)_$_findCachedViewById(R.id.phone_number);
            if (editText2 != null) {
              editText1 = (EditText)_$_findCachedViewById(R.id.phone_number);
              Intrinsics.checkExpressionValueIsNotNull(editText1, "phone_number");
              editText2.setSelection(editText1.getText().length() - this.selectionPointer);
            } 
          } else {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
          } 
        } else {
          throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } 
      } 
    } else {
      this.editFlag = false;
    } 
  }
  
  private final void disableButtonAndRequestOTP() {
    disableButton();
    requestOTP();
  }
  
  private final String getUnmaskedNumber(String paramString) {
    paramString = paramString;
    return (new Regex("[^\\d]")).replace(paramString, "");
  }
  
  private final void requestOTP() {
    if (this.mView != null) {
      AppCompatTextView appCompatTextView = (AppCompatTextView)_$_findCachedViewById(R.id.phone_number_error);
      if (appCompatTextView != null)
        appCompatTextView.setVisibility(4); 
      EditText editText = (EditText)_$_findCachedViewById(R.id.phone_number);
      if (editText != null) {
        Editable editable = editText.getText();
      } else {
        editText = null;
      } 
      String str1 = getUnmaskedNumber(String.valueOf(editText));
      CentralLog.Companion companion = CentralLog.Companion;
      String str2 = this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("The value retrieved: ");
      stringBuilder.append(str1);
      companion.d(str2, stringBuilder.toString());
      OnboardingActivity onboardingActivity = (OnboardingActivity)getContext();
      Preference.INSTANCE.putPhoneNumber(TracerApp.Companion.getAppContext(), str1);
      if (onboardingActivity != null) {
        onboardingActivity.updatePhoneNumber(str1);
        if (OnboardingActivity.requestForOTP$default(onboardingActivity, str1, false, 2, null) != null)
          return; 
      } 
      Utils.INSTANCE.restartAppWithNoContext(0, "RegisterNumberFragment not attached to OnboardingActivity");
      Unit unit = Unit.INSTANCE;
    } 
  }
  
  private final boolean validateNumber(String paramString) {
    boolean bool;
    if (getUnmaskedNumber(paramString).length() == 10) {
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
    RegisterNumberFragment registerNumberFragment = this;
    EditText editText = (EditText)_$_findCachedViewById(R.id.phone_number);
    if (editText != null) {
      Editable editable = editText.getText();
    } else {
      editText = null;
    } 
    if (validateNumber(String.valueOf(editText))) {
      registerNumberFragment.enableButton();
    } else {
      registerNumberFragment.disableButton();
    } 
  }
  
  public void onAttach(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    super.onAttach(paramContext);
    if (paramContext instanceof OnFragmentInteractionListener) {
      this.listener = (OnFragmentInteractionListener)paramContext;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramContext);
    stringBuilder.append(" must implement OnFragmentInteractionListener");
    throw (Throwable)new RuntimeException(stringBuilder.toString());
  }
  
  public void onBackButtonClick(View paramView) {
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    OnboardingActivity onboardingActivity = (OnboardingActivity)getContext();
    if (onboardingActivity != null) {
      onboardingActivity.onBackPressed();
    } else {
      Utils.INSTANCE.restartAppWithNoContext(0, "RegisterNumberFragment not attached to OnboardingActivity");
    } 
  }
  
  public void onButtonClick(View paramView) {
    Intrinsics.checkParameterIsNotNull(paramView, "buttonView");
    CentralLog.Companion.d(this.TAG, "OnButtonClick");
    disableButtonAndRequestOTP();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramLayoutInflater, "inflater");
    super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    CentralLog.Companion.i(this.TAG, "Making view");
    View view = paramLayoutInflater.inflate(2131492920, paramViewGroup, false);
    Intrinsics.checkExpressionValueIsNotNull(view, "inflater.inflate(R.layou…number, container, false)");
    return view;
  }
  
  public void onDetach() {
    super.onDetach();
    this.listener = (OnFragmentInteractionListener)null;
    this.mView = (View)null;
    CentralLog.Companion.i(this.TAG, "Detached??");
  }
  
  public void onError(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "error");
    if ((AppCompatTextView)_$_findCachedViewById(R.id.phone_number_error) != null) {
      AppCompatTextView appCompatTextView = (AppCompatTextView)_$_findCachedViewById(R.id.phone_number_error);
      Intrinsics.checkExpressionValueIsNotNull(appCompatTextView, "phone_number_error");
      appCompatTextView.setVisibility(0);
      appCompatTextView = (AppCompatTextView)_$_findCachedViewById(R.id.phone_number_error);
      Intrinsics.checkExpressionValueIsNotNull(appCompatTextView, "phone_number_error");
      appCompatTextView.setText(paramString);
    } 
    CentralLog.Companion companion = CentralLog.Companion;
    String str = this.TAG;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("error: ");
    stringBuilder.append(paramString.toString());
    companion.e(str, stringBuilder.toString());
  }
  
  public void onUpdatePhoneNumber(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "num");
    CentralLog.Companion companion = CentralLog.Companion;
    String str = this.TAG;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("onUpdatePhoneNumber ");
    stringBuilder.append(paramString);
    companion.d(str, stringBuilder.toString());
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    String str;
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    super.onViewCreated(paramView, paramBundle);
    CentralLog.Companion.i(this.TAG, "View created");
    this.mView = paramView;
    EditText editText = (EditText)_$_findCachedViewById(R.id.phone_number);
    if (editText != null)
      editText.addTextChangedListener((TextWatcher)new RegisterNumberFragment$onViewCreated$1()); 
    editText = (EditText)_$_findCachedViewById(R.id.phone_number);
    if (editText != null)
      editText.setOnEditorActionListener(new RegisterNumberFragment$onViewCreated$2(paramView)); 
    if (StringsKt.contains$default(Utils.INSTANCE.getServerURL(), "stg", false, 2, null)) {
      str = ".S";
    } else {
      str = "";
    } 
    AppCompatTextView appCompatTextView = (AppCompatTextView)_$_findCachedViewById(R.id.tv_app_version);
    if (appCompatTextView != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(getString(2131820582));
      stringBuilder.append("1.4.0");
      stringBuilder.append(str);
      appCompatTextView.setText(stringBuilder.toString());
    } 
    disableButton();
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\026\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\030\002\n\000\bf\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H&¨\006\006"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/RegisterNumberFragment$OnFragmentInteractionListener;", "", "onFragmentInteraction", "", "uri", "Landroid/net/Uri;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static interface OnFragmentInteractionListener {
    void onFragmentInteraction(Uri param1Uri);
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000%\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\r\n\000\n\002\020\b\n\002\b\005*\001\000\b\n\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H\026J(\020\006\032\0020\0032\006\020\004\032\0020\0072\006\020\b\032\0020\t2\006\020\n\032\0020\t2\006\020\013\032\0020\tH\026J(\020\f\032\0020\0032\006\020\004\032\0020\0072\006\020\b\032\0020\t2\006\020\r\032\0020\t2\006\020\n\032\0020\tH\026¨\006\016"}, d2 = {"ca/albertahealthservices/contacttracing/onboarding/RegisterNumberFragment$onViewCreated$1", "Landroid/telephony/PhoneNumberFormattingTextWatcher;", "afterTextChanged", "", "s", "Landroid/text/Editable;", "beforeTextChanged", "", "start", "", "count", "after", "onTextChanged", "before", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class RegisterNumberFragment$onViewCreated$1 extends PhoneNumberFormattingTextWatcher {
    public void afterTextChanged(Editable param1Editable) {
      Intrinsics.checkParameterIsNotNull(param1Editable, "s");
      RegisterNumberFragment.this.applyMask(param1Editable.toString());
    }
    
    public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
      boolean bool;
      Intrinsics.checkParameterIsNotNull(param1CharSequence, "s");
      RegisterNumberFragment registerNumberFragment2 = RegisterNumberFragment.this;
      param1Int1 = param1CharSequence.length();
      EditText editText = (EditText)RegisterNumberFragment.this._$_findCachedViewById(R.id.phone_number);
      Intrinsics.checkExpressionValueIsNotNull(editText, "phone_number");
      registerNumberFragment2.selectionPointer = param1Int1 - editText.getSelectionStart();
      RegisterNumberFragment registerNumberFragment1 = RegisterNumberFragment.this;
      if (param1Int2 > param1Int3) {
        bool = true;
      } else {
        bool = false;
      } 
      registerNumberFragment1.backspaceFlag = bool;
    }
    
    public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
      Intrinsics.checkParameterIsNotNull(param1CharSequence, "s");
      AppCompatTextView appCompatTextView = (AppCompatTextView)RegisterNumberFragment.this._$_findCachedViewById(R.id.phone_number_error);
      Intrinsics.checkExpressionValueIsNotNull(appCompatTextView, "phone_number_error");
      appCompatTextView.setVisibility(8);
      if (RegisterNumberFragment.this.validateNumber(param1CharSequence.toString())) {
        RegisterNumberFragment.this.enableButton();
      } else {
        RegisterNumberFragment.this.disableButton();
      } 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\034\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\000\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\0032\006\020\005\032\0020\0062\016\020\007\032\n \004*\004\030\0010\b0\bH\n¢\006\002\b\t"}, d2 = {"<anonymous>", "", "<anonymous parameter 0>", "Landroid/widget/TextView;", "kotlin.jvm.PlatformType", "actionId", "", "<anonymous parameter 2>", "Landroid/view/KeyEvent;", "onEditorAction"}, k = 3, mv = {1, 1, 16})
  static final class RegisterNumberFragment$onViewCreated$2 implements TextView.OnEditorActionListener {
    RegisterNumberFragment$onViewCreated$2(View param1View) {}
    
    public final boolean onEditorAction(TextView param1TextView, int param1Int, KeyEvent param1KeyEvent) {
      boolean bool;
      if (param1Int == 2) {
        Utils utils = Utils.INSTANCE;
        Context context = this.$view.getContext();
        Intrinsics.checkExpressionValueIsNotNull(context, "view.context");
        utils.hideKeyboardFrom(context, this.$view);
        RegisterNumberFragment.this.disableButtonAndRequestOTP();
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/onboarding/RegisterNumberFragment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */