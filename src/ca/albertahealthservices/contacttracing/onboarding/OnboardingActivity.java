package ca.albertahealthservices.contacttracing.onboarding;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import ca.albertahealthservices.contacttracing.Preference;
import ca.albertahealthservices.contacttracing.R;
import ca.albertahealthservices.contacttracing.Utils;
import ca.albertahealthservices.contacttracing.api.ErrorCode;
import ca.albertahealthservices.contacttracing.api.Request;
import ca.albertahealthservices.contacttracing.idmanager.TempIDManager;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.services.BluetoothMonitoringService;
import ca.albertahealthservices.contacttracing.view.CustomViewPager;
import com.google.android.material.tabs.TabLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import org.json.JSONObject;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\001\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\004\n\002\020\002\n\002\b\n\n\002\020\b\n\002\b\006\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\020\021\n\000\n\002\020\025\n\002\b\004\n\002\030\002\n\002\b\017\030\0002\0020\0012\0020\0022\0020\0032\0020\0042\0020\0052\0020\0062\0020\007:\001QB\005¢\006\002\020\bJ\022\020\036\032\0020\0372\b\020 \032\004\030\0010\nH\002J\006\020!\032\0020\037J\b\020\"\032\0020\037H\002J\006\020#\032\0020\037J\b\020$\032\0020\037H\002J\b\020%\032\0020\037H\002J\b\020&\032\0020\037H\002J\b\020'\032\0020\037H\002J\020\020(\032\0020\0372\006\020)\032\0020*H\002J\006\020+\032\0020\037J\006\020,\032\0020\037J\"\020-\032\0020\0372\006\020.\032\0020*2\006\020/\032\0020*2\b\0200\032\004\030\00101H\024J\b\0202\032\0020\037H\026J\022\0203\032\0020\0372\b\0204\032\004\030\00105H\024J\b\0206\032\0020\037H\024J\020\0207\032\0020\0372\006\0208\032\00209H\026J-\020:\032\0020\0372\006\020.\032\0020*2\016\020;\032\n\022\006\b\001\022\0020\n0<2\006\020=\032\0020>H\026¢\006\002\020?J\b\020@\032\0020\037H\024J\b\020A\032\0020\037H\002J\030\020B\032\0020C2\006\020D\032\0020\n2\b\b\002\020E\032\0020\fJ\b\020F\032\0020\037H\002J\020\020G\032\0020\0372\b\b\002\020H\032\0020\fJ\b\020I\032\0020\037H\007J\020\020J\032\0020\0372\006\020K\032\0020\nH\002J\016\020L\032\0020\0372\006\020M\032\0020\nJ\016\020N\032\0020\0372\006\020K\032\0020\nJ\016\020O\032\0020\0372\006\020P\032\0020\nR\016\020\t\032\0020\nX\016¢\006\002\n\000R\016\020\013\032\0020\fX\016¢\006\002\n\000R\035\020\r\032\004\030\0010\0168BX\002¢\006\f\n\004\b\021\020\022\032\004\b\017\020\020R\022\020\023\032\0020\024X\005¢\006\006\032\004\b\025\020\026R\016\020\027\032\0020\fX\016¢\006\002\n\000R\016\020\030\032\0020\fX\016¢\006\002\n\000R\024\020\031\032\b\030\0010\032R\0020\000X\016¢\006\002\n\000R\016\020\033\032\0020\fX\016¢\006\002\n\000R\030\020\034\032\0020\f*\0020\0168BX\004¢\006\006\032\004\b\034\020\035¨\006R"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/OnboardingActivity;", "Landroidx/fragment/app/FragmentActivity;", "Lkotlinx/coroutines/CoroutineScope;", "Lca/albertahealthservices/contacttracing/onboarding/SetupFragment$OnFragmentInteractionListener;", "Lca/albertahealthservices/contacttracing/onboarding/SetupCompleteFragment$OnFragmentInteractionListener;", "Lca/albertahealthservices/contacttracing/onboarding/RegisterNumberFragment$OnFragmentInteractionListener;", "Lca/albertahealthservices/contacttracing/onboarding/OTPFragment$OnFragmentInteractionListener;", "Lca/albertahealthservices/contacttracing/onboarding/TOUFragment$OnFragmentInteractionListener;", "()V", "TAG", "", "bleSupported", "", "bluetoothAdapter", "Landroid/bluetooth/BluetoothAdapter;", "getBluetoothAdapter", "()Landroid/bluetooth/BluetoothAdapter;", "bluetoothAdapter$delegate", "Lkotlin/Lazy;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "mIsOpenSetting", "mIsResetup", "pagerAdapter", "Lca/albertahealthservices/contacttracing/onboarding/OnboardingActivity$ScreenSlidePagerAdapter;", "resendingCode", "isDisabled", "(Landroid/bluetooth/BluetoothAdapter;)Z", "alertDialog", "", "desc", "cancelChallengeAndGoBack", "checkBLESupport", "enableBluetooth", "enableFragmentbutton", "excludeFromBatteryOptimization", "getTemporaryID", "initBluetooth", "navigateTo", "page", "", "navigateToNextPage", "navigateToPreviousPage", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onFragmentInteraction", "uri", "Landroid/net/Uri;", "onRequestPermissionsResult", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onResume", "registerForSmsCodeChallengeBroadcasts", "requestForOTP", "Lkotlinx/coroutines/Job;", "phoneNumber", "skipRegister", "requestTempIdsIfNeeded", "resendCode", "skipCancelChallenge", "setupPermissionsAndSettings", "updateOTPError", "error", "updatePhoneNumber", "num", "updatePhoneNumberError", "validateOTP", "otp", "ScreenSlidePagerAdapter", "app_release"}, k = 1, mv = {1, 1, 16})
public final class OnboardingActivity extends FragmentActivity implements CoroutineScope, SetupFragment.OnFragmentInteractionListener, SetupCompleteFragment.OnFragmentInteractionListener, RegisterNumberFragment.OnFragmentInteractionListener, OTPFragment.OnFragmentInteractionListener, TOUFragment.OnFragmentInteractionListener {
  private String TAG = "OnboardingActivity";
  
  private HashMap _$_findViewCache;
  
  private boolean bleSupported;
  
  private final Lazy bluetoothAdapter$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, new OnboardingActivity$bluetoothAdapter$2());
  
  private boolean mIsOpenSetting;
  
  private boolean mIsResetup;
  
  private ScreenSlidePagerAdapter pagerAdapter;
  
  private boolean resendingCode;
  
  private final void alertDialog(String paramString) {
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
    builder.setMessage(paramString).setCancelable(false).setPositiveButton(getString(2131820693), OnboardingActivity$alertDialog$1.INSTANCE);
    builder.create().show();
  }
  
  private final void checkBLESupport() {
    CentralLog.Companion.d(this.TAG, "[checkBLESupport] ");
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Intrinsics.checkExpressionValueIsNotNull(bluetoothAdapter, "BluetoothAdapter.getDefaultAdapter()");
    if (!bluetoothAdapter.isMultipleAdvertisementSupported()) {
      this.bleSupported = false;
      Utils.INSTANCE.stopBluetoothMonitoringService((Context)this);
    } else {
      this.bleSupported = true;
    } 
  }
  
  private final void enableFragmentbutton() {
    OnboardingFragmentInterface onboardingFragmentInterface;
    ScreenSlidePagerAdapter screenSlidePagerAdapter = this.pagerAdapter;
    if (screenSlidePagerAdapter != null) {
      CustomViewPager customViewPager = (CustomViewPager)_$_findCachedViewById(R.id.pager);
      Intrinsics.checkExpressionValueIsNotNull(customViewPager, "pager");
      onboardingFragmentInterface = screenSlidePagerAdapter.getItem(customViewPager.getCurrentItem());
    } else {
      onboardingFragmentInterface = null;
    } 
    if (onboardingFragmentInterface != null)
      onboardingFragmentInterface.enableButton(); 
  }
  
  private final void excludeFromBatteryOptimization() {
    CentralLog.Companion.d(this.TAG, "[excludeFromBatteryOptimization] ");
    Object object = getSystemService("power");
    if (object != null) {
      object = object;
      String str = getPackageName();
      if (Build.VERSION.SDK_INT >= 23) {
        Utils utils = Utils.INSTANCE;
        Intrinsics.checkExpressionValueIsNotNull(str, "packageName");
        Intent intent = utils.getBatteryOptimizerExemptionIntent(str);
        if (!object.isIgnoringBatteryOptimizations(str)) {
          CentralLog.Companion.d(this.TAG, "Not on Battery Optimization whitelist");
          if (Utils.INSTANCE.canHandleIntent(intent, getPackageManager())) {
            startActivityForResult(intent, 789);
          } else {
            navigateToNextPage();
          } 
        } else {
          CentralLog.Companion.d(this.TAG, "On Battery Optimization whitelist");
          navigateToNextPage();
        } 
      } 
      return;
    } 
    throw new TypeCastException("null cannot be cast to non-null type android.os.PowerManager");
  }
  
  private final BluetoothAdapter getBluetoothAdapter() {
    Lazy lazy = this.bluetoothAdapter$delegate;
    KProperty kProperty = $$delegatedProperties[0];
    return (BluetoothAdapter)lazy.getValue();
  }
  
  private final void getTemporaryID() {
    TempIDManager.INSTANCE.getTemporaryIDs((Context)this);
    CentralLog.Companion.d(this.TAG, "Retrieved Temporary ID successfully");
  }
  
  private final void initBluetooth() {
    checkBLESupport();
  }
  
  private final boolean isDisabled(BluetoothAdapter paramBluetoothAdapter) {
    return paramBluetoothAdapter.isEnabled() ^ true;
  }
  
  private final void navigateTo(int paramInt) {
    CentralLog.Companion.d(this.TAG, "Navigating to page");
    CustomViewPager customViewPager = (CustomViewPager)_$_findCachedViewById(R.id.pager);
    Intrinsics.checkExpressionValueIsNotNull(customViewPager, "pager");
    customViewPager.setCurrentItem(paramInt);
    ScreenSlidePagerAdapter screenSlidePagerAdapter = this.pagerAdapter;
    if (screenSlidePagerAdapter == null)
      Intrinsics.throwNpe(); 
    screenSlidePagerAdapter.notifyDataSetChanged();
  }
  
  private final void registerForSmsCodeChallengeBroadcasts() {
    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance((Context)this);
    Intrinsics.checkExpressionValueIsNotNull(localBroadcastManager, "LocalBroadcastManager.getInstance(this)");
    localBroadcastManager.registerReceiver(new OnboardingActivity$registerForSmsCodeChallengeBroadcasts$1(), new IntentFilter("ACTION_VERIFY_SMS_CODE_REQUIRED"));
    localBroadcastManager.registerReceiver(new OnboardingActivity$registerForSmsCodeChallengeBroadcasts$2(), new IntentFilter("ACTION_VERIFY_SMS_CODE_SUCCESS"));
    localBroadcastManager.registerReceiver(new OnboardingActivity$registerForSmsCodeChallengeBroadcasts$3(), new IntentFilter("ACTION_VERIFY_SMS_CODE_FAIL"));
    localBroadcastManager.registerReceiver(new OnboardingActivity$registerForSmsCodeChallengeBroadcasts$4(), new IntentFilter("ACTION_CHALLENGE_CANCELLED"));
  }
  
  private final void requestTempIdsIfNeeded() {
    CentralLog.Companion.d(this.TAG, "requestTempIdsIfNeeded");
    if (BluetoothMonitoringService.Companion.getBroadcastMessage() != null) {
      TempIDManager tempIDManager = TempIDManager.INSTANCE;
      Context context = getApplicationContext();
      Intrinsics.checkExpressionValueIsNotNull(context, "applicationContext");
      if (tempIDManager.needToUpdate(context)) {
        getTemporaryID();
        return;
      } 
      return;
    } 
    getTemporaryID();
  }
  
  private final void updateOTPError(String paramString) {
    ScreenSlidePagerAdapter screenSlidePagerAdapter = this.pagerAdapter;
    if (screenSlidePagerAdapter == null)
      Intrinsics.throwNpe(); 
    screenSlidePagerAdapter.getItem(2).onError(paramString);
    alertDialog(paramString);
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
  
  public final void cancelChallengeAndGoBack() {
    Intent intent = new Intent();
    intent.setAction("ACTION_CANCEL_CHALLENGE");
    intent.putExtra("EXTRA_SKIP_CANCELLED_BROADCAST", true);
    LocalBroadcastManager.getInstance((Context)this).sendBroadcast(intent);
    navigateToPreviousPage();
  }
  
  public final void enableBluetooth() {
    CentralLog.Companion.d(this.TAG, "[enableBluetooth]");
    BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();
    if (bluetoothAdapter != null)
      if (isDisabled(bluetoothAdapter)) {
        startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 123);
      } else {
        setupPermissionsAndSettings();
      }  
  }
  
  public CoroutineContext getCoroutineContext() {
    return this.$$delegate_0.getCoroutineContext();
  }
  
  public final void navigateToNextPage() {
    CentralLog.Companion.d(this.TAG, "Navigating to next page");
    CustomViewPager customViewPager1 = (CustomViewPager)_$_findCachedViewById(R.id.pager);
    Intrinsics.checkExpressionValueIsNotNull(customViewPager1, "pager");
    CustomViewPager customViewPager2 = (CustomViewPager)_$_findCachedViewById(R.id.pager);
    Intrinsics.checkExpressionValueIsNotNull(customViewPager2, "pager");
    customViewPager1.setCurrentItem(customViewPager2.getCurrentItem() + 1);
    ScreenSlidePagerAdapter screenSlidePagerAdapter = this.pagerAdapter;
    if (screenSlidePagerAdapter == null)
      Intrinsics.throwNpe(); 
    screenSlidePagerAdapter.notifyDataSetChanged();
  }
  
  public final void navigateToPreviousPage() {
    CentralLog.Companion.d(this.TAG, "Navigating to previous page");
    if (this.mIsResetup) {
      CustomViewPager customViewPager = (CustomViewPager)_$_findCachedViewById(R.id.pager);
      Intrinsics.checkExpressionValueIsNotNull(customViewPager, "pager");
      if (customViewPager.getCurrentItem() >= 4) {
        CustomViewPager customViewPager1 = (CustomViewPager)_$_findCachedViewById(R.id.pager);
        Intrinsics.checkExpressionValueIsNotNull(customViewPager1, "pager");
        customViewPager = (CustomViewPager)_$_findCachedViewById(R.id.pager);
        Intrinsics.checkExpressionValueIsNotNull(customViewPager, "pager");
        customViewPager1.setCurrentItem(customViewPager.getCurrentItem() - 1);
        ScreenSlidePagerAdapter screenSlidePagerAdapter = this.pagerAdapter;
        if (screenSlidePagerAdapter == null)
          Intrinsics.throwNpe(); 
        screenSlidePagerAdapter.notifyDataSetChanged();
      } else {
        finish();
      } 
    } else {
      CustomViewPager customViewPager1 = (CustomViewPager)_$_findCachedViewById(R.id.pager);
      Intrinsics.checkExpressionValueIsNotNull(customViewPager1, "pager");
      CustomViewPager customViewPager2 = (CustomViewPager)_$_findCachedViewById(R.id.pager);
      Intrinsics.checkExpressionValueIsNotNull(customViewPager2, "pager");
      customViewPager1.setCurrentItem(customViewPager2.getCurrentItem() - 1);
      ScreenSlidePagerAdapter screenSlidePagerAdapter = this.pagerAdapter;
      if (screenSlidePagerAdapter == null)
        Intrinsics.throwNpe(); 
      screenSlidePagerAdapter.notifyDataSetChanged();
    } 
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    CentralLog.Companion companion = CentralLog.Companion;
    String str = this.TAG;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("requestCode ");
    stringBuilder.append(paramInt1);
    stringBuilder.append(" resultCode ");
    stringBuilder.append(paramInt2);
    companion.d(str, stringBuilder.toString());
    if (paramInt1 == 123) {
      if (paramInt2 == 0) {
        finish();
        return;
      } 
      setupPermissionsAndSettings();
    } else if (paramInt1 == 789 && paramInt2 != 0) {
      (new Handler()).postDelayed(new OnboardingActivity$onActivityResult$1(), 1000L);
    } 
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onBackPressed() {
    CustomViewPager customViewPager = (CustomViewPager)_$_findCachedViewById(R.id.pager);
    Intrinsics.checkExpressionValueIsNotNull(customViewPager, "pager");
    if (customViewPager.getCurrentItem() > 1) {
      customViewPager = (CustomViewPager)_$_findCachedViewById(R.id.pager);
      Intrinsics.checkExpressionValueIsNotNull(customViewPager, "pager");
      if (customViewPager.getCurrentItem() != 0) {
        customViewPager = (CustomViewPager)_$_findCachedViewById(R.id.pager);
        Intrinsics.checkExpressionValueIsNotNull(customViewPager, "pager");
        if (customViewPager.getCurrentItem() == 2) {
          cancelChallengeAndGoBack();
        } else {
          navigateToPreviousPage();
        } 
        return;
      } 
    } 
    super.onBackPressed();
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131492893);
    FragmentManager fragmentManager = getSupportFragmentManager();
    Intrinsics.checkExpressionValueIsNotNull(fragmentManager, "supportFragmentManager");
    this.pagerAdapter = new ScreenSlidePagerAdapter(fragmentManager);
    CustomViewPager customViewPager2 = (CustomViewPager)_$_findCachedViewById(R.id.pager);
    Intrinsics.checkExpressionValueIsNotNull(customViewPager2, "pager");
    customViewPager2.setAdapter((PagerAdapter)this.pagerAdapter);
    TabLayout tabLayout = (TabLayout)_$_findCachedViewById(R.id.tabDots);
    if (tabLayout != null)
      tabLayout.setupWithViewPager((ViewPager)_$_findCachedViewById(R.id.pager), true); 
    ((CustomViewPager)_$_findCachedViewById(R.id.pager)).addOnPageChangeListener(new OnboardingActivity$onCreate$1());
    ((CustomViewPager)_$_findCachedViewById(R.id.pager)).setPagingEnabled(false);
    CustomViewPager customViewPager1 = (CustomViewPager)_$_findCachedViewById(R.id.pager);
    Intrinsics.checkExpressionValueIsNotNull(customViewPager1, "pager");
    customViewPager1.setOffscreenPageLimit(5);
    Intent intent = getIntent();
    Intrinsics.checkExpressionValueIsNotNull(intent, "intent");
    Bundle bundle = intent.getExtras();
    if (bundle != null) {
      this.mIsResetup = true;
      navigateTo(bundle.getInt("page", 0));
    } else {
      navigateTo(Preference.INSTANCE.getCheckpoint((Context)this));
    } 
    registerForSmsCodeChallengeBroadcasts();
  }
  
  protected void onDestroy() {
    CoroutineScopeKt.cancel$default(this, null, 1, null);
    super.onDestroy();
  }
  
  public void onFragmentInteraction(Uri paramUri) {
    Intrinsics.checkParameterIsNotNull(paramUri, "uri");
    CentralLog.Companion companion = CentralLog.Companion;
    String str = this.TAG;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("########## fragment interaction: ");
    stringBuilder.append(paramUri);
    companion.d(str, stringBuilder.toString());
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    Intrinsics.checkParameterIsNotNull(paramArrayOfString, "permissions");
    Intrinsics.checkParameterIsNotNull(paramArrayOfint, "grantResults");
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
    CentralLog.Companion companion = CentralLog.Companion;
    String str = this.TAG;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[onRequestPermissionsResult] requestCode ");
    stringBuilder.append(paramInt);
    companion.d(str, stringBuilder.toString());
    if (paramInt == 456) {
      int i = paramArrayOfString.length;
      for (paramInt = 0; paramInt < i; paramInt++) {
        String str1 = paramArrayOfString[paramInt];
        if (paramArrayOfint[paramInt] == -1) {
          if (Build.VERSION.SDK_INT >= 23) {
            AlertDialog.Builder builder;
            if (!shouldShowRequestPermissionRationale(str1)) {
              builder = new AlertDialog.Builder((Context)this);
              builder.setMessage(getString(2131820694)).setCancelable(false).setPositiveButton(getString(2131820693), new OnboardingActivity$onRequestPermissionsResult$1()).setNegativeButton(getString(2131820593), OnboardingActivity$onRequestPermissionsResult$2.INSTANCE);
              builder.create().show();
            } else if ("android.permission.WRITE_CONTACTS".equals(builder)) {
              CentralLog.Companion.d(this.TAG, "user did not CHECKED never ask again");
            } else {
              excludeFromBatteryOptimization();
            } 
          } 
        } else if (paramArrayOfint[paramInt] == 0) {
          excludeFromBatteryOptimization();
        } 
      } 
    } 
  }
  
  protected void onResume() {
    super.onResume();
    if (this.mIsOpenSetting)
      (new Handler()).postDelayed(new OnboardingActivity$onResume$1(), 1000L); 
  }
  
  public final Job requestForOTP(String paramString, boolean paramBoolean) {
    Intrinsics.checkParameterIsNotNull(paramString, "phoneNumber");
    return BuildersKt.launch$default(this, null, null, new OnboardingActivity$requestForOTP$1(paramBoolean, paramString, null), 3, null);
  }
  
  public final void resendCode(boolean paramBoolean) {
    this.resendingCode = true;
    if (!paramBoolean) {
      Intent intent = new Intent();
      intent.setAction("ACTION_CANCEL_CHALLENGE");
      LocalBroadcastManager.getInstance((Context)this).sendBroadcast(intent);
    } else {
      requestForOTP("", true);
    } 
  }
  
  @AfterPermissionGranted(456)
  public final void setupPermissionsAndSettings() {
    CentralLog.Companion.d(this.TAG, "[setupPermissionsAndSettings]");
    if (Build.VERSION.SDK_INT >= 23) {
      String[] arrayOfString = Utils.INSTANCE.getRequiredPermissions();
      if (EasyPermissions.hasPermissions((Context)this, Arrays.<String>copyOf(arrayOfString, arrayOfString.length))) {
        initBluetooth();
        excludeFromBatteryOptimization();
      } else {
        EasyPermissions.requestPermissions((Activity)this, getString(2131820710), 456, Arrays.<String>copyOf(arrayOfString, arrayOfString.length));
      } 
    } else {
      initBluetooth();
      navigateToNextPage();
    } 
  }
  
  public final void updatePhoneNumber(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "num");
    ScreenSlidePagerAdapter screenSlidePagerAdapter = this.pagerAdapter;
    if (screenSlidePagerAdapter == null)
      Intrinsics.throwNpe(); 
    screenSlidePagerAdapter.getItem(2).onUpdatePhoneNumber(paramString);
  }
  
  public final void updatePhoneNumberError(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "error");
    ScreenSlidePagerAdapter screenSlidePagerAdapter = this.pagerAdapter;
    if (screenSlidePagerAdapter == null)
      Intrinsics.throwNpe(); 
    screenSlidePagerAdapter.getItem(1).onError(paramString);
  }
  
  public final void validateOTP(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "otp");
    if (TextUtils.isEmpty(paramString) || paramString.length() < 6) {
      paramString = getString(2131820684);
      Intrinsics.checkExpressionValueIsNotNull(paramString, "getString(R.string.must_be_six_digit)");
      updateOTPError(paramString);
      return;
    } 
    Intent intent = new Intent();
    intent.setAction("ACTION_VERIFY_SMS_CODE");
    intent.putExtra("otp", paramString);
    LocalBroadcastManager.getInstance((Context)this).sendBroadcast(intent);
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\"\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020%\n\002\020\b\n\002\030\002\n\002\b\b\b\004\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\020\020\013\032\0020\b2\006\020\f\032\0020\007H\002J\b\020\r\032\0020\007H\026J\020\020\016\032\0020\b2\006\020\017\032\0020\007H\026R\035\020\005\032\016\022\004\022\0020\007\022\004\022\0020\b0\006¢\006\b\n\000\032\004\b\t\020\n¨\006\020"}, d2 = {"Lca/albertahealthservices/contacttracing/onboarding/OnboardingActivity$ScreenSlidePagerAdapter;", "Landroidx/fragment/app/FragmentStatePagerAdapter;", "fm", "Landroidx/fragment/app/FragmentManager;", "(Lca/albertahealthservices/contacttracing/onboarding/OnboardingActivity;Landroidx/fragment/app/FragmentManager;)V", "fragmentMap", "", "", "Lca/albertahealthservices/contacttracing/onboarding/OnboardingFragmentInterface;", "getFragmentMap", "()Ljava/util/Map;", "createFragAtIndex", "index", "getCount", "getItem", "position", "app_release"}, k = 1, mv = {1, 1, 16})
  private final class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private final Map<Integer, OnboardingFragmentInterface> fragmentMap = new HashMap<>();
    
    public ScreenSlidePagerAdapter(FragmentManager param1FragmentManager) {
      super(param1FragmentManager);
    }
    
    private final OnboardingFragmentInterface createFragAtIndex(int param1Int) {
      return (OnboardingFragmentInterface)((param1Int != 0) ? ((param1Int != 1) ? ((param1Int != 2) ? ((param1Int != 3) ? ((param1Int != 4) ? new TOUFragment() : new SetupCompleteFragment()) : new SetupFragment()) : new OTPFragment()) : new RegisterNumberFragment()) : new TOUFragment());
    }
    
    public int getCount() {
      return 5;
    }
    
    public final Map<Integer, OnboardingFragmentInterface> getFragmentMap() {
      return this.fragmentMap;
    }
    
    public OnboardingFragmentInterface getItem(int param1Int) {
      Map<Integer, OnboardingFragmentInterface> map = this.fragmentMap;
      Integer integer = Integer.valueOf(param1Int);
      OnboardingFragmentInterface onboardingFragmentInterface1 = (OnboardingFragmentInterface)map.get(integer);
      OnboardingFragmentInterface onboardingFragmentInterface2 = onboardingFragmentInterface1;
      if (onboardingFragmentInterface1 == null) {
        onboardingFragmentInterface2 = createFragAtIndex(param1Int);
        map.put(integer, onboardingFragmentInterface2);
      } 
      return onboardingFragmentInterface2;
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\026\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\0032\006\020\005\032\0020\006H\n¢\006\002\b\007"}, d2 = {"<anonymous>", "", "dialog", "Landroid/content/DialogInterface;", "kotlin.jvm.PlatformType", "id", "", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class OnboardingActivity$alertDialog$1 implements DialogInterface.OnClickListener {
    public static final OnboardingActivity$alertDialog$1 INSTANCE = new OnboardingActivity$alertDialog$1();
    
    public final void onClick(DialogInterface param1DialogInterface, int param1Int) {
      param1DialogInterface.dismiss();
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\n\n\000\n\002\030\002\n\002\b\002\020\000\032\n \002*\004\030\0010\0010\001H\n¢\006\002\b\003"}, d2 = {"<anonymous>", "Landroid/bluetooth/BluetoothAdapter;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 1, 16})
  static final class OnboardingActivity$bluetoothAdapter$2 extends Lambda implements Function0<BluetoothAdapter> {
    OnboardingActivity$bluetoothAdapter$2() {
      super(0);
    }
    
    public final BluetoothAdapter invoke() {
      Object object = OnboardingActivity.this.getSystemService("bluetooth");
      if (object != null)
        return ((BluetoothManager)object).getAdapter(); 
      throw new TypeCastException("null cannot be cast to non-null type android.bluetooth.BluetoothManager");
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\b\n\000\n\002\020\002\n\000\020\000\032\0020\001H\n¢\006\002\b\002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 1, 16})
  static final class OnboardingActivity$onActivityResult$1 implements Runnable {
    public final void run() {
      OnboardingActivity.this.navigateToNextPage();
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000!\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\020\b\n\002\b\003\n\002\020\007\n\002\b\003*\001\000\b\n\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H\026J \020\006\032\0020\0032\006\020\007\032\0020\0052\006\020\b\032\0020\t2\006\020\n\032\0020\005H\026J\020\020\013\032\0020\0032\006\020\007\032\0020\005H\026¨\006\f"}, d2 = {"ca/albertahealthservices/contacttracing/onboarding/OnboardingActivity$onCreate$1", "Landroidx/viewpager/widget/ViewPager$OnPageChangeListener;", "onPageScrollStateChanged", "", "state", "", "onPageScrolled", "position", "positionOffset", "", "positionOffsetPixels", "onPageSelected", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class OnboardingActivity$onCreate$1 implements ViewPager.OnPageChangeListener {
    public void onPageScrollStateChanged(int param1Int) {
      CentralLog.Companion.d(OnboardingActivity.this.TAG, "OnPageScrollStateChanged");
    }
    
    public void onPageScrolled(int param1Int1, float param1Float, int param1Int2) {
      CentralLog.Companion.d(OnboardingActivity.this.TAG, "OnPageScrolled");
    }
    
    public void onPageSelected(int param1Int) {
      CentralLog.Companion companion = CentralLog.Companion;
      String str = OnboardingActivity.this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("position: ");
      stringBuilder.append(param1Int);
      companion.d(str, stringBuilder.toString());
      OnboardingActivity.ScreenSlidePagerAdapter screenSlidePagerAdapter = OnboardingActivity.this.pagerAdapter;
      if (screenSlidePagerAdapter == null)
        Intrinsics.throwNpe(); 
      screenSlidePagerAdapter.getItem(param1Int).becomesVisible();
      if (param1Int != 0) {
        if (param1Int != 1) {
          if (param1Int != 3) {
            if (param1Int == 4) {
              Preference preference = Preference.INSTANCE;
              Context context = OnboardingActivity.this.getBaseContext();
              Intrinsics.checkExpressionValueIsNotNull(context, "baseContext");
              preference.putCheckpoint(context, param1Int);
            } 
          } else {
            Preference preference = Preference.INSTANCE;
            Context context = OnboardingActivity.this.getBaseContext();
            Intrinsics.checkExpressionValueIsNotNull(context, "baseContext");
            preference.putCheckpoint(context, param1Int);
          } 
        } else {
          Preference preference = Preference.INSTANCE;
          Context context = OnboardingActivity.this.getBaseContext();
          Intrinsics.checkExpressionValueIsNotNull(context, "baseContext");
          preference.putCheckpoint(context, param1Int);
        } 
      } else {
        Preference preference = Preference.INSTANCE;
        Context context = OnboardingActivity.this.getBaseContext();
        Intrinsics.checkExpressionValueIsNotNull(context, "baseContext");
        preference.putCheckpoint(context, param1Int);
      } 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\026\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\0032\006\020\005\032\0020\006H\n¢\006\002\b\007"}, d2 = {"<anonymous>", "", "dialog", "Landroid/content/DialogInterface;", "kotlin.jvm.PlatformType", "id", "", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class OnboardingActivity$onRequestPermissionsResult$1 implements DialogInterface.OnClickListener {
    public final void onClick(DialogInterface param1DialogInterface, int param1Int) {
      CentralLog.Companion.d(OnboardingActivity.this.TAG, "user also CHECKED never ask again");
      OnboardingActivity.this.mIsOpenSetting = true;
      Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
      intent.addFlags(268435456);
      Uri uri = Uri.fromParts("package", OnboardingActivity.this.getPackageName(), null);
      Intrinsics.checkExpressionValueIsNotNull(uri, "Uri.fromParts(\"package\", packageName, null)");
      intent.setData(uri);
      OnboardingActivity.this.startActivity(intent);
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\026\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\0032\006\020\005\032\0020\006H\n¢\006\002\b\007"}, d2 = {"<anonymous>", "", "dialog", "Landroid/content/DialogInterface;", "kotlin.jvm.PlatformType", "id", "", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class OnboardingActivity$onRequestPermissionsResult$2 implements DialogInterface.OnClickListener {
    public static final OnboardingActivity$onRequestPermissionsResult$2 INSTANCE = new OnboardingActivity$onRequestPermissionsResult$2();
    
    public final void onClick(DialogInterface param1DialogInterface, int param1Int) {
      param1DialogInterface.cancel();
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\b\n\000\n\002\020\002\n\000\020\000\032\0020\001H\n¢\006\002\b\002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 1, 16})
  static final class OnboardingActivity$onResume$1 implements Runnable {
    public final void run() {
      OnboardingActivity.this.setupPermissionsAndSettings();
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\035\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\032\020\002\032\0020\0032\b\020\004\032\004\030\0010\0052\006\020\006\032\0020\007H\026¨\006\b"}, d2 = {"ca/albertahealthservices/contacttracing/onboarding/OnboardingActivity$registerForSmsCodeChallengeBroadcasts$1", "Landroid/content/BroadcastReceiver;", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class OnboardingActivity$registerForSmsCodeChallengeBroadcasts$1 extends BroadcastReceiver {
    public void onReceive(Context param1Context, Intent param1Intent) {
      Intrinsics.checkParameterIsNotNull(param1Intent, "intent");
      CustomViewPager customViewPager = (CustomViewPager)OnboardingActivity.this._$_findCachedViewById(R.id.pager);
      Intrinsics.checkExpressionValueIsNotNull(customViewPager, "pager");
      if (customViewPager.getCurrentItem() == 1)
        OnboardingActivity.this.navigateToNextPage(); 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\035\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\032\020\002\032\0020\0032\b\020\004\032\004\030\0010\0052\006\020\006\032\0020\007H\026¨\006\b"}, d2 = {"ca/albertahealthservices/contacttracing/onboarding/OnboardingActivity$registerForSmsCodeChallengeBroadcasts$2", "Landroid/content/BroadcastReceiver;", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class OnboardingActivity$registerForSmsCodeChallengeBroadcasts$2 extends BroadcastReceiver {
    public void onReceive(Context param1Context, Intent param1Intent) {
      Intrinsics.checkParameterIsNotNull(param1Intent, "intent");
      OnboardingActivity.this.navigateToNextPage();
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\035\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\032\020\002\032\0020\0032\b\020\004\032\004\030\0010\0052\006\020\006\032\0020\007H\026¨\006\b"}, d2 = {"ca/albertahealthservices/contacttracing/onboarding/OnboardingActivity$registerForSmsCodeChallengeBroadcasts$3", "Landroid/content/BroadcastReceiver;", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class OnboardingActivity$registerForSmsCodeChallengeBroadcasts$3 extends BroadcastReceiver {
    public void onReceive(Context param1Context, Intent param1Intent) {
      Intrinsics.checkParameterIsNotNull(param1Intent, "intent");
      OnboardingActivity onboardingActivity = OnboardingActivity.this;
      String str = onboardingActivity.getString(2131820784);
      Intrinsics.checkExpressionValueIsNotNull(str, "getString(R.string.verification_failed)");
      onboardingActivity.updateOTPError(str);
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\035\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\034\020\002\032\0020\0032\b\020\004\032\004\030\0010\0052\b\020\006\032\004\030\0010\007H\026¨\006\b"}, d2 = {"ca/albertahealthservices/contacttracing/onboarding/OnboardingActivity$registerForSmsCodeChallengeBroadcasts$4", "Landroid/content/BroadcastReceiver;", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class OnboardingActivity$registerForSmsCodeChallengeBroadcasts$4 extends BroadcastReceiver {
    public void onReceive(Context param1Context, Intent param1Intent) {
      OnboardingActivity.this.requestForOTP("", true);
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\016\n\000\n\002\020\002\n\002\030\002\n\002\b\002\020\000\032\0020\001*\0020\002H@¢\006\004\b\003\020\004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 16})
  @DebugMetadata(c = "ca.albertahealthservices.contacttracing.onboarding.OnboardingActivity$requestForOTP$1", f = "OnboardingActivity.kt", i = {0, 0, 1}, l = {466, 479}, m = "invokeSuspend", n = {"$this$launch", "continueToOtp", "$this$launch"}, s = {"L$0", "I$0", "L$0"})
  static final class OnboardingActivity$requestForOTP$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int I$0;
    
    Object L$0;
    
    int label;
    
    private CoroutineScope p$;
    
    OnboardingActivity$requestForOTP$1(boolean param1Boolean, String param1String, Continuation param1Continuation) {
      super(2, param1Continuation);
    }
    
    public final Continuation<Unit> create(Object param1Object, Continuation<?> param1Continuation) {
      Intrinsics.checkParameterIsNotNull(param1Continuation, "completion");
      OnboardingActivity$requestForOTP$1 onboardingActivity$requestForOTP$1 = new OnboardingActivity$requestForOTP$1(this.$skipRegister, this.$phoneNumber, param1Continuation);
      onboardingActivity$requestForOTP$1.p$ = (CoroutineScope)param1Object;
      return (Continuation<Unit>)onboardingActivity$requestForOTP$1;
    }
    
    public final Object invoke(Object param1Object1, Object param1Object2) {
      return ((OnboardingActivity$requestForOTP$1)create(param1Object1, (Continuation)param1Object2)).invokeSuspend(Unit.INSTANCE);
    }
    
    public final Object invokeSuspend(Object param1Object) {
      Object object = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      int i = this.label;
      boolean bool = false;
      if (i != 0) {
        if (i != 1) {
          if (i == 2) {
            CoroutineScope coroutineScope = (CoroutineScope)this.L$0;
            ResultKt.throwOnFailure(param1Object);
          } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
          } 
        } else {
          i = this.I$0;
          CoroutineScope coroutineScope = (CoroutineScope)this.L$0;
          ResultKt.throwOnFailure(param1Object);
          Object object1 = param1Object;
          param1Object = coroutineScope;
          object1 = object1;
          Integer integer = object1.getStatus();
        } 
      } else {
        ResultKt.throwOnFailure(param1Object);
        param1Object = this.p$;
        OnboardingActivity.this.resendingCode = false;
        if (!this.$skipRegister) {
          Request request = Request.INSTANCE;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("/adapters/smsOtpService/phone/register/");
          stringBuilder.append(this.$phoneNumber);
          String str = stringBuilder.toString();
          this.L$0 = param1Object;
          this.I$0 = 1;
          this.label = 1;
          object1 = Request.runRequest$default(request, str, "POST", 0, null, null, null, (Continuation)this, 60, null);
          if (object1 == object)
            return object; 
          i = 1;
        } else {
          i = 1;
          if (i != 0) {
            Request request = Request.INSTANCE;
            object1 = new JSONObject();
            this.L$0 = param1Object;
            this.label = 2;
            Object object2 = Request.runRequest$default(request, "/adapters/smsOtpService/phone/verifySmsOTP", "POST", 0, null, (JSONObject)object1, null, (Continuation)this, 44, null);
            param1Object = object2;
            if (object2 == object)
              return object; 
          } else {
            return Unit.INSTANCE;
          } 
        } 
        Object object1 = object1;
        Integer integer = object1.getStatus();
      } 
      param1Object = param1Object;
      if (param1Object.isSuccess()) {
        Integer integer = param1Object.getStatus();
        if (integer == null || integer.intValue() != 200 || param1Object.getData() == null) {
          if ((Intrinsics.areEqual(param1Object.getErrorCode(), ErrorCode.INSTANCE.getCHALLENGE_HANDLING_CANCELED()) ^ true) != 0) {
            OnboardingActivity onboardingActivity = OnboardingActivity.this;
            param1Object = onboardingActivity.getString(2131820637);
            Intrinsics.checkExpressionValueIsNotNull(param1Object, "getString(R.string.invalid_otp)");
            onboardingActivity.updateOTPError((String)param1Object);
          } 
          OnboardingActivity.this.enableFragmentbutton();
          return Unit.INSTANCE;
        } 
        try {
          Preference preference = Preference.INSTANCE;
          Context context = OnboardingActivity.this.getApplicationContext();
          Intrinsics.checkExpressionValueIsNotNull(context, "applicationContext");
          param1Object = param1Object.getData().getString("userId");
          Intrinsics.checkExpressionValueIsNotNull(param1Object, "triggerSmsOTPResponse.data.getString(\"userId\")");
          preference.putUUID(context, (String)param1Object);
        } catch (Exception exception) {
          exception.printStackTrace();
        } 
        OnboardingActivity.this.requestTempIdsIfNeeded();
        return Unit.INSTANCE;
      } 
      if ((Intrinsics.areEqual(exception.getErrorCode(), ErrorCode.INSTANCE.getCHALLENGE_HANDLING_CANCELED()) ^ true) != 0) {
        OnboardingActivity onboardingActivity = OnboardingActivity.this;
        String str = onboardingActivity.getString(2131820637);
        Intrinsics.checkExpressionValueIsNotNull(str, "getString(R.string.invalid_otp)");
        onboardingActivity.updateOTPError(str);
      } 
      OnboardingActivity.this.enableFragmentbutton();
      return Unit.INSTANCE;
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/onboarding/OnboardingActivity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */