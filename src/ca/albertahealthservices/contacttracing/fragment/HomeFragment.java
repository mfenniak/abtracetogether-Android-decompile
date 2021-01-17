package ca.albertahealthservices.contacttracing.fragment;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import ca.albertahealthservices.contacttracing.MainActivity;
import ca.albertahealthservices.contacttracing.Preference;
import ca.albertahealthservices.contacttracing.R;
import ca.albertahealthservices.contacttracing.TracerApp;
import ca.albertahealthservices.contacttracing.Utils;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.onboarding.OnboardingActivity;
import ca.albertahealthservices.contacttracing.status.persistence.StatusRecord;
import ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecordDatabase;
import com.airbnb.lottie.LottieAnimationView;
import java.util.Arrays;
import java.util.HashMap;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

@Metadata(bv = {1, 0, 3}, d1 = {"\000v\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\006\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\003\n\002\020\002\n\002\b\007\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\021\n\000\n\002\020\025\n\002\b\n\030\0002\0020\001B\005¢\006\002\020\002J\022\020\031\032\0020\0322\b\020\033\032\004\030\0010\004H\002J\b\020\034\032\0020\026H\002J\b\020\035\032\0020\032H\002J\b\020\036\032\0020\032H\002J\b\020\037\032\0020\026H\002J\022\020 \032\0020\0322\b\020!\032\004\030\0010\"H\026J\"\020#\032\0020\0322\006\020$\032\0020\0062\006\020%\032\0020\0062\b\020&\032\004\030\0010'H\026J&\020(\032\004\030\0010)2\006\020*\032\0020+2\b\020,\032\004\030\0010-2\b\020!\032\004\030\0010\"H\026J\b\020.\032\0020\032H\026J\b\020/\032\0020\032H\026J-\0200\032\0020\0322\006\020$\032\0020\0062\016\0201\032\n\022\006\b\001\022\0020\004022\006\0203\032\00204H\026¢\006\002\0205J\b\0206\032\0020\032H\026J\032\0207\032\0020\0322\006\0208\032\0020)2\b\020!\032\004\030\0010\"H\026J\b\0209\032\0020\032H\007J\b\020:\032\0020\032H\002J\b\020;\032\0020\032H\002J\006\020<\032\0020\032J\b\020=\032\0020\032H\002R\016\020\003\032\0020\004XD¢\006\002\n\000R\016\020\005\032\0020\006X\016¢\006\002\n\000R\035\020\007\032\004\030\0010\b8BX\002¢\006\f\n\004\b\013\020\f\032\004\b\t\020\nR\016\020\r\032\0020\006X\016¢\006\002\n\000R\026\020\016\032\n\022\006\022\004\030\0010\0200\017X.¢\006\002\n\000R\016\020\021\032\0020\022X\016¢\006\002\n\000R\016\020\023\032\0020\024X\004¢\006\002\n\000R\016\020\025\032\0020\026X\016¢\006\002\n\000R\030\020\027\032\0020\026*\0020\b8BX\004¢\006\006\032\004\b\027\020\030¨\006>"}, d2 = {"Lca/albertahealthservices/contacttracing/fragment/HomeFragment;", "Landroidx/fragment/app/Fragment;", "()V", "TAG", "", "animationWindow", "", "bluetoothAdapter", "Landroid/bluetooth/BluetoothAdapter;", "getBluetoothAdapter", "()Landroid/bluetooth/BluetoothAdapter;", "bluetoothAdapter$delegate", "Lkotlin/Lazy;", "counter", "lastKnownScanningStarted", "Landroidx/lifecycle/LiveData;", "Lca/albertahealthservices/contacttracing/status/persistence/StatusRecord;", "listener", "Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;", "mBroadcastListener", "Landroid/content/BroadcastReceiver;", "mIsBroadcastListenerRegistered", "", "isDisabled", "(Landroid/bluetooth/BluetoothAdapter;)Z", "alertDialog", "", "desc", "canRequestBatteryOptimizerExemption", "clearAndHideAnnouncement", "enableBluetooth", "isShowRestartSetup", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroyView", "onPause", "onRequestPermissionsResult", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onResume", "onViewCreated", "view", "setupPermissionsAndSettings", "shareThisApp", "showNonEmptyAnnouncement", "showSetup", "updatedPremLabels", "app_release"}, k = 1, mv = {1, 1, 16})
public final class HomeFragment extends Fragment {
  private final String TAG = "HomeFragment";
  
  private HashMap _$_findViewCache;
  
  private int animationWindow = 10000;
  
  private final Lazy bluetoothAdapter$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, new HomeFragment$bluetoothAdapter$2());
  
  private int counter;
  
  private LiveData<StatusRecord> lastKnownScanningStarted;
  
  private SharedPreferences.OnSharedPreferenceChangeListener listener = new HomeFragment$listener$1();
  
  private final BroadcastReceiver mBroadcastListener = new HomeFragment$mBroadcastListener$1();
  
  private boolean mIsBroadcastListenerRegistered;
  
  private final void alertDialog(String paramString) {
    FragmentActivity fragmentActivity = getActivity();
    if (fragmentActivity != null) {
      AlertDialog.Builder builder = new AlertDialog.Builder((Context)fragmentActivity);
      builder.setMessage(paramString).setCancelable(false).setPositiveButton(getString(2131820688), HomeFragment$alertDialog$1.INSTANCE);
      builder.create().show();
      return;
    } 
    throw new TypeCastException("null cannot be cast to non-null type ca.albertahealthservices.contacttracing.MainActivity");
  }
  
  private final boolean canRequestBatteryOptimizerExemption() {
    if (Build.VERSION.SDK_INT >= 23) {
      Utils utils1 = Utils.INSTANCE;
      Utils utils2 = Utils.INSTANCE;
      String str = TracerApp.Companion.getAppContext().getPackageName();
      Intrinsics.checkExpressionValueIsNotNull(str, "TracerApp.AppContext.packageName");
      if (utils1.canHandleIntent(utils2.getBatteryOptimizerExemptionIntent(str), TracerApp.Companion.getAppContext().getPackageManager()))
        return true; 
    } 
    return false;
  }
  
  private final void clearAndHideAnnouncement() {
    ConstraintLayout constraintLayout = (ConstraintLayout)_$_findCachedViewById(R.id.view_announcement);
    Intrinsics.checkExpressionValueIsNotNull(constraintLayout, "view_announcement");
    ((View)constraintLayout).setVisibility(8);
    Preference preference = Preference.INSTANCE;
    FragmentActivity fragmentActivity = getActivity();
    if (fragmentActivity == null)
      Intrinsics.throwNpe(); 
    Intrinsics.checkExpressionValueIsNotNull(fragmentActivity, "activity!!");
    Context context = fragmentActivity.getApplicationContext();
    Intrinsics.checkExpressionValueIsNotNull(context, "activity!!.applicationContext");
    preference.putAnnouncement(context, "");
  }
  
  private final void enableBluetooth() {
    BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();
    if (bluetoothAdapter != null && isDisabled(bluetoothAdapter))
      startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 123); 
  }
  
  private final BluetoothAdapter getBluetoothAdapter() {
    Lazy lazy = this.bluetoothAdapter$delegate;
    KProperty kProperty = $$delegatedProperties[0];
    return (BluetoothAdapter)lazy.getValue();
  }
  
  private final boolean isDisabled(BluetoothAdapter paramBluetoothAdapter) {
    return paramBluetoothAdapter.isEnabled() ^ true;
  }
  
  private final boolean isShowRestartSetup() {
    if (canRequestBatteryOptimizerExemption()) {
      ImageView imageView = (ImageView)_$_findCachedViewById(R.id.iv_bluetooth);
      Intrinsics.checkExpressionValueIsNotNull(imageView, "iv_bluetooth");
      if (imageView.isSelected()) {
        imageView = (ImageView)_$_findCachedViewById(R.id.iv_location);
        Intrinsics.checkExpressionValueIsNotNull(imageView, "iv_location");
        if (imageView.isSelected())
          return false; 
      } 
    } else {
      ImageView imageView = (ImageView)_$_findCachedViewById(R.id.iv_bluetooth);
      Intrinsics.checkExpressionValueIsNotNull(imageView, "iv_bluetooth");
      if (imageView.isSelected()) {
        imageView = (ImageView)_$_findCachedViewById(R.id.iv_location);
        Intrinsics.checkExpressionValueIsNotNull(imageView, "iv_location");
        if (imageView.isSelected())
          return false; 
      } 
    } 
    return true;
  }
  
  private final void shareThisApp() {
    Intent intent = new Intent("android.intent.action.SEND");
    intent.setType("text/plain");
    intent.putExtra("android.intent.extra.SUBJECT", getString(2131820577));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getString(2131820744));
    stringBuilder.append("https://www.alberta.ca/ab-trace-together.aspx");
    intent.putExtra("android.intent.extra.TEXT", stringBuilder.toString());
    startActivity(Intent.createChooser(intent, "Choose one"));
  }
  
  private final void showNonEmptyAnnouncement() {
    boolean bool;
    Preference preference = Preference.INSTANCE;
    FragmentActivity fragmentActivity = getActivity();
    if (fragmentActivity == null)
      Intrinsics.throwNpe(); 
    Intrinsics.checkExpressionValueIsNotNull(fragmentActivity, "activity!!");
    Context context = fragmentActivity.getApplicationContext();
    Intrinsics.checkExpressionValueIsNotNull(context, "activity!!.applicationContext");
    String str1 = preference.getAnnouncement(context);
    if (str1.length() == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool)
      return; 
    CentralLog.Companion companion = CentralLog.Companion;
    String str2 = this.TAG;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("FCM Announcement Changed to ");
    stringBuilder.append(str1);
    stringBuilder.append('!');
    companion.d(str2, stringBuilder.toString());
    AppCompatTextView appCompatTextView2 = (AppCompatTextView)_$_findCachedViewById(R.id.tv_announcement);
    Intrinsics.checkExpressionValueIsNotNull(appCompatTextView2, "tv_announcement");
    appCompatTextView2.setText((CharSequence)HtmlCompat.fromHtml(str1, 63));
    AppCompatTextView appCompatTextView1 = (AppCompatTextView)_$_findCachedViewById(R.id.tv_announcement);
    Intrinsics.checkExpressionValueIsNotNull(appCompatTextView1, "tv_announcement");
    appCompatTextView1.setMovementMethod((MovementMethod)new HomeFragment$showNonEmptyAnnouncement$1());
    ConstraintLayout constraintLayout = (ConstraintLayout)_$_findCachedViewById(R.id.view_announcement);
    Intrinsics.checkExpressionValueIsNotNull(constraintLayout, "view_announcement");
    ((View)constraintLayout).setVisibility(0);
  }
  
  private final void updatedPremLabels() {
    String str3;
    String str2;
    String str1;
    ImageView imageView3 = (ImageView)_$_findCachedViewById(R.id.iv_location);
    Intrinsics.checkExpressionValueIsNotNull(imageView3, "iv_location");
    boolean bool = imageView3.isSelected();
    String str4 = "Yes";
    if (bool) {
      str3 = "Yes";
    } else {
      str3 = "No";
    } 
    AppCompatTextView appCompatTextView2 = (AppCompatTextView)_$_findCachedViewById(R.id.tv_location);
    if (appCompatTextView2 != null)
      appCompatTextView2.setText((CharSequence)HtmlCompat.fromHtml(getString(2131820640, new Object[] { str3 }), 0)); 
    ImageView imageView2 = (ImageView)_$_findCachedViewById(R.id.iv_bluetooth);
    Intrinsics.checkExpressionValueIsNotNull(imageView2, "iv_bluetooth");
    if (imageView2.isSelected()) {
      str2 = "Yes";
    } else {
      str2 = "No";
    } 
    appCompatTextView2 = (AppCompatTextView)_$_findCachedViewById(R.id.tv_bluetooth);
    if (appCompatTextView2 != null)
      appCompatTextView2.setText((CharSequence)HtmlCompat.fromHtml(getString(2131820589, new Object[] { str2 }), 0)); 
    ImageView imageView1 = (ImageView)_$_findCachedViewById(R.id.iv_push);
    Intrinsics.checkExpressionValueIsNotNull(imageView1, "iv_push");
    if (imageView1.isSelected()) {
      str1 = str4;
    } else {
      str1 = "No";
    } 
    AppCompatTextView appCompatTextView1 = (AppCompatTextView)_$_findCachedViewById(R.id.tv_push);
    if (appCompatTextView1 != null)
      appCompatTextView1.setText((CharSequence)HtmlCompat.fromHtml(getString(2131820719, new Object[] { str1 }), 0)); 
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
  
  public void onActivityCreated(Bundle paramBundle) {
    super.onActivityCreated(paramBundle);
    ((CardView)_$_findCachedViewById(R.id.share_card_view)).setOnClickListener(new HomeFragment$onActivityCreated$1());
    ((LottieAnimationView)_$_findCachedViewById(R.id.animation_view)).setOnClickListener(new HomeFragment$onActivityCreated$2());
    ((Button)_$_findCachedViewById(R.id.btn_restart_app_setup)).setOnClickListener(new HomeFragment$onActivityCreated$3());
    ((ImageButton)_$_findCachedViewById(R.id.btn_announcement_close)).setOnClickListener(new HomeFragment$onActivityCreated$4());
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    if (paramInt1 == 123) {
      boolean bool;
      ImageView imageView = (ImageView)_$_findCachedViewById(R.id.iv_bluetooth);
      Intrinsics.checkExpressionValueIsNotNull(imageView, "iv_bluetooth");
      if (paramInt2 == -1) {
        bool = true;
      } else {
        bool = false;
      } 
      imageView.setSelected(bool);
    } 
    showSetup();
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramLayoutInflater, "inflater");
    View view = paramLayoutInflater.inflate(2131492918, paramViewGroup, false);
    Preference preference = Preference.INSTANCE;
    FragmentActivity fragmentActivity = getActivity();
    if (fragmentActivity == null)
      Intrinsics.throwNpe(); 
    Intrinsics.checkExpressionValueIsNotNull(fragmentActivity, "activity!!");
    Context context = fragmentActivity.getApplicationContext();
    Intrinsics.checkExpressionValueIsNotNull(context, "activity!!.applicationContext");
    preference.registerListener(context, this.listener);
    return view;
  }
  
  public void onDestroyView() {
    super.onDestroyView();
    Preference preference = Preference.INSTANCE;
    FragmentActivity fragmentActivity = getActivity();
    if (fragmentActivity == null)
      Intrinsics.throwNpe(); 
    Intrinsics.checkExpressionValueIsNotNull(fragmentActivity, "activity!!");
    Context context = fragmentActivity.getApplicationContext();
    Intrinsics.checkExpressionValueIsNotNull(context, "activity!!.applicationContext");
    preference.unregisterListener(context, this.listener);
    LiveData<StatusRecord> liveData = this.lastKnownScanningStarted;
    if (liveData == null)
      Intrinsics.throwUninitializedPropertyAccessException("lastKnownScanningStarted"); 
    liveData.removeObservers(getViewLifecycleOwner());
    _$_clearFindViewByIdCache();
  }
  
  public void onPause() {
    super.onPause();
    if (this.mIsBroadcastListenerRegistered) {
      FragmentActivity fragmentActivity = getActivity();
      if (fragmentActivity == null)
        Intrinsics.throwNpe(); 
      fragmentActivity.unregisterReceiver(this.mBroadcastListener);
      this.mIsBroadcastListenerRegistered = false;
    } 
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    Intrinsics.checkParameterIsNotNull(paramArrayOfString, "permissions");
    Intrinsics.checkParameterIsNotNull(paramArrayOfint, "grantResults");
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
    CentralLog.Companion companion = CentralLog.Companion;
    String str = this.TAG;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[onRequestPermissionsResult]requestCode ");
    stringBuilder.append(paramInt);
    companion.d(str, stringBuilder.toString());
    if (paramInt == 456) {
      ImageView imageView = (ImageView)_$_findCachedViewById(R.id.iv_location);
      Intrinsics.checkExpressionValueIsNotNull(imageView, "iv_location");
      if (paramArrayOfString.length == 0) {
        paramInt = 1;
      } else {
        paramInt = 0;
      } 
      imageView.setSelected(paramInt ^ 0x1);
    } 
    showSetup();
  }
  
  public void onResume() {
    super.onResume();
    if (!this.mIsBroadcastListenerRegistered) {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
      FragmentActivity fragmentActivity = getActivity();
      if (fragmentActivity == null)
        Intrinsics.throwNpe(); 
      fragmentActivity.registerReceiver(this.mBroadcastListener, intentFilter);
      this.mIsBroadcastListenerRegistered = true;
    } 
    if (getView() != null) {
      String[] arrayOfString = Utils.INSTANCE.getRequiredPermissions();
      ImageView imageView = (ImageView)_$_findCachedViewById(R.id.iv_location);
      Intrinsics.checkExpressionValueIsNotNull(imageView, "iv_location");
      FragmentActivity fragmentActivity = getActivity();
      if (fragmentActivity != null) {
        imageView.setSelected(EasyPermissions.hasPermissions((Context)fragmentActivity, Arrays.<String>copyOf(arrayOfString, arrayOfString.length)));
        imageView = (ImageView)_$_findCachedViewById(R.id.iv_push);
        Intrinsics.checkExpressionValueIsNotNull(imageView, "iv_push");
        fragmentActivity = getActivity();
        if (fragmentActivity != null) {
          imageView.setSelected(NotificationManagerCompat.from((Context)fragmentActivity).areNotificationsEnabled());
          BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();
          if (bluetoothAdapter != null) {
            ImageView imageView1 = (ImageView)_$_findCachedViewById(R.id.iv_bluetooth);
            Intrinsics.checkExpressionValueIsNotNull(imageView1, "iv_bluetooth");
            imageView1.setSelected(isDisabled(bluetoothAdapter) ^ true);
          } 
          fragmentActivity = getActivity();
          if (fragmentActivity != null) {
            Object object = ((MainActivity)fragmentActivity).getSystemService("power");
            if (object != null) {
              object = object;
              FragmentActivity fragmentActivity1 = getActivity();
              if (fragmentActivity1 != null) {
                String str = ((MainActivity)fragmentActivity1).getPackageName();
                if (Build.VERSION.SDK_INT >= 23)
                  if (!object.isIgnoringBatteryOptimizations(str)) {
                    CentralLog.Companion.d(this.TAG, "Not on Battery Optimization whitelist");
                  } else {
                    CentralLog.Companion.d(this.TAG, "On Battery Optimization whitelist");
                  }  
                showSetup();
              } else {
                throw new TypeCastException("null cannot be cast to non-null type ca.albertahealthservices.contacttracing.MainActivity");
              } 
            } else {
              throw new TypeCastException("null cannot be cast to non-null type android.os.PowerManager");
            } 
          } else {
            throw new TypeCastException("null cannot be cast to non-null type ca.albertahealthservices.contacttracing.MainActivity");
          } 
        } else {
          throw new TypeCastException("null cannot be cast to non-null type ca.albertahealthservices.contacttracing.MainActivity");
        } 
      } else {
        throw new TypeCastException("null cannot be cast to non-null type ca.albertahealthservices.contacttracing.MainActivity");
      } 
    } 
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    super.onViewCreated(paramView, paramBundle);
    StreetPassRecordDatabase.Companion companion = StreetPassRecordDatabase.Companion;
    Context context1 = paramView.getContext();
    Intrinsics.checkExpressionValueIsNotNull(context1, "view.context");
    LiveData<StatusRecord> liveData = companion.getDatabase(context1).statusDao().getMostRecentRecord("Scanning Started");
    this.lastKnownScanningStarted = liveData;
    if (liveData == null)
      Intrinsics.throwUninitializedPropertyAccessException("lastKnownScanningStarted"); 
    liveData.observe(getViewLifecycleOwner(), new HomeFragment$onViewCreated$1());
    ((ImageView)_$_findCachedViewById(R.id.prem_info_button)).setOnClickListener(new HomeFragment$onViewCreated$2());
    showSetup();
    Preference preference = Preference.INSTANCE;
    FragmentActivity fragmentActivity = getActivity();
    if (fragmentActivity == null)
      Intrinsics.throwNpe(); 
    Intrinsics.checkExpressionValueIsNotNull(fragmentActivity, "activity!!");
    Context context2 = fragmentActivity.getApplicationContext();
    Intrinsics.checkExpressionValueIsNotNull(context2, "activity!!.applicationContext");
    preference.registerListener(context2, this.listener);
    showNonEmptyAnnouncement();
  }
  
  @AfterPermissionGranted(456)
  public final void setupPermissionsAndSettings() {
    if (Build.VERSION.SDK_INT >= 23) {
      String[] arrayOfString = Utils.INSTANCE.getRequiredPermissions();
      FragmentActivity fragmentActivity = getActivity();
      if (fragmentActivity != null) {
        if (!EasyPermissions.hasPermissions((Context)fragmentActivity, Arrays.<String>copyOf(arrayOfString, arrayOfString.length)))
          EasyPermissions.requestPermissions(this, getString(2131820705), 456, Arrays.<String>copyOf(arrayOfString, arrayOfString.length)); 
      } else {
        throw new TypeCastException("null cannot be cast to non-null type ca.albertahealthservices.contacttracing.MainActivity");
      } 
    } 
  }
  
  public final void showSetup() {
    updatedPremLabels();
    LinearLayout linearLayout = (LinearLayout)_$_findCachedViewById(R.id.view_complete);
    Intrinsics.checkExpressionValueIsNotNull(linearLayout, "view_complete");
    ((View)linearLayout).setVisibility(0);
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\026\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\0032\006\020\005\032\0020\006H\n¢\006\002\b\007"}, d2 = {"<anonymous>", "", "dialog", "Landroid/content/DialogInterface;", "kotlin.jvm.PlatformType", "id", "", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class HomeFragment$alertDialog$1 implements DialogInterface.OnClickListener {
    public static final HomeFragment$alertDialog$1 INSTANCE = new HomeFragment$alertDialog$1();
    
    public final void onClick(DialogInterface param1DialogInterface, int param1Int) {
      param1DialogInterface.dismiss();
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\n\n\000\n\002\030\002\n\002\b\002\020\000\032\n \002*\004\030\0010\0010\001H\n¢\006\002\b\003"}, d2 = {"<anonymous>", "Landroid/bluetooth/BluetoothAdapter;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 1, 16})
  static final class HomeFragment$bluetoothAdapter$2 extends Lambda implements Function0<BluetoothAdapter> {
    HomeFragment$bluetoothAdapter$2() {
      super(0);
    }
    
    public final BluetoothAdapter invoke() {
      FragmentActivity fragmentActivity = HomeFragment.this.getActivity();
      if (fragmentActivity != null) {
        Object object = ((MainActivity)fragmentActivity).getSystemService("bluetooth");
        if (object != null)
          return ((BluetoothManager)object).getAdapter(); 
        throw new TypeCastException("null cannot be cast to non-null type android.bluetooth.BluetoothManager");
      } 
      throw new TypeCastException("null cannot be cast to non-null type ca.albertahealthservices.contacttracing.MainActivity");
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\026\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\000\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\0032\016\020\005\032\n \004*\004\030\0010\0060\006H\n¢\006\002\b\007"}, d2 = {"<anonymous>", "", "<anonymous parameter 0>", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "key", "", "onSharedPreferenceChanged"}, k = 3, mv = {1, 1, 16})
  static final class HomeFragment$listener$1 implements SharedPreferences.OnSharedPreferenceChangeListener {
    public final void onSharedPreferenceChanged(SharedPreferences param1SharedPreferences, String param1String) {
      if (param1String != null && param1String.hashCode() == -1820904121 && param1String.equals("ANNOUNCEMENT"))
        HomeFragment.this.showNonEmptyAnnouncement(); 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\035\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\030\020\002\032\0020\0032\006\020\004\032\0020\0052\006\020\006\032\0020\007H\026¨\006\b"}, d2 = {"ca/albertahealthservices/contacttracing/fragment/HomeFragment$mBroadcastListener$1", "Landroid/content/BroadcastReceiver;", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class HomeFragment$mBroadcastListener$1 extends BroadcastReceiver {
    public void onReceive(Context param1Context, Intent param1Intent) {
      Intrinsics.checkParameterIsNotNull(param1Context, "context");
      Intrinsics.checkParameterIsNotNull(param1Intent, "intent");
      CentralLog.Companion.d(HomeFragment.this.TAG, String.valueOf(param1Intent.getAction()));
      if (Intrinsics.areEqual(param1Intent.getAction(), "android.bluetooth.adapter.action.STATE_CHANGED")) {
        int i = param1Intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
        if (i == 10) {
          ImageView imageView = (ImageView)HomeFragment.this._$_findCachedViewById(R.id.iv_bluetooth);
          Intrinsics.checkExpressionValueIsNotNull(imageView, "iv_bluetooth");
          imageView.setSelected(false);
        } else if (i == 13) {
          ImageView imageView = (ImageView)HomeFragment.this._$_findCachedViewById(R.id.iv_bluetooth);
          Intrinsics.checkExpressionValueIsNotNull(imageView, "iv_bluetooth");
          imageView.setSelected(false);
        } else if (i == 12) {
          ImageView imageView = (ImageView)HomeFragment.this._$_findCachedViewById(R.id.iv_bluetooth);
          Intrinsics.checkExpressionValueIsNotNull(imageView, "iv_bluetooth");
          imageView.setSelected(true);
        } 
        HomeFragment.this.showSetup();
      } 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class HomeFragment$onActivityCreated$1 implements View.OnClickListener {
    public final void onClick(View param1View) {
      HomeFragment.this.shareThisApp();
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class HomeFragment$onActivityCreated$2 implements View.OnClickListener {
    public final void onClick(View param1View) {}
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class HomeFragment$onActivityCreated$3 implements View.OnClickListener {
    public final void onClick(View param1View) {
      Intent intent = new Intent(HomeFragment.this.getContext(), OnboardingActivity.class);
      intent.putExtra("page", 3);
      Context context = HomeFragment.this.getContext();
      if (context != null)
        context.startActivity(intent); 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class HomeFragment$onActivityCreated$4 implements View.OnClickListener {
    public final void onClick(View param1View) {
      HomeFragment.this.clearAndHideAnnouncement();
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\016\n\000\n\002\020\002\n\000\n\002\030\002\n\000\020\000\032\0020\0012\b\020\002\032\004\030\0010\003H\n¢\006\002\b\004"}, d2 = {"<anonymous>", "", "record", "Lca/albertahealthservices/contacttracing/status/persistence/StatusRecord;", "onChanged"}, k = 3, mv = {1, 1, 16})
  static final class HomeFragment$onViewCreated$1<T> implements Observer<StatusRecord> {
    public final void onChanged(StatusRecord param1StatusRecord) {
      if (param1StatusRecord != null) {
        AppCompatTextView appCompatTextView = (AppCompatTextView)HomeFragment.this._$_findCachedViewById(R.id.tv_last_update);
        Intrinsics.checkExpressionValueIsNotNull(appCompatTextView, "tv_last_update");
        appCompatTextView.setVisibility(0);
        appCompatTextView = (AppCompatTextView)HomeFragment.this._$_findCachedViewById(R.id.tv_last_update);
        Intrinsics.checkExpressionValueIsNotNull(appCompatTextView, "tv_last_update");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Last updated: ");
        stringBuilder.append(Utils.INSTANCE.getTime(param1StatusRecord.getTimestamp()));
        appCompatTextView.setText(stringBuilder.toString());
        long l = System.currentTimeMillis();
        CentralLog.Companion.d(HomeFragment.this.TAG, String.valueOf(param1StatusRecord.getTimestamp()));
        CentralLog.Companion.d(HomeFragment.this.TAG, String.valueOf(l));
        if (l - HomeFragment.this.animationWindow >= param1StatusRecord.getTimestamp() && param1StatusRecord.getTimestamp() <= l + HomeFragment.this.animationWindow) {
          CentralLog.Companion.d(HomeFragment.this.TAG, "Start animation");
          ((LottieAnimationView)HomeFragment.this._$_findCachedViewById(R.id.animation_view)).playAnimation();
        } 
      } 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
  static final class HomeFragment$onViewCreated$2 implements View.OnClickListener {
    public final void onClick(View param1View) {
      HomeFragment homeFragment = HomeFragment.this;
      homeFragment.alertDialog(homeFragment.getString(2131820580));
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000#\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J&\020\002\032\0020\0032\b\020\004\032\004\030\0010\0052\b\020\006\032\004\030\0010\0072\b\020\b\032\004\030\0010\tH\026¨\006\n"}, d2 = {"ca/albertahealthservices/contacttracing/fragment/HomeFragment$showNonEmptyAnnouncement$1", "Landroid/text/method/LinkMovementMethod;", "onTouchEvent", "", "widget", "Landroid/widget/TextView;", "buffer", "Landroid/text/Spannable;", "event", "Landroid/view/MotionEvent;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class HomeFragment$showNonEmptyAnnouncement$1 extends LinkMovementMethod {
    public boolean onTouchEvent(TextView param1TextView, Spannable param1Spannable, MotionEvent param1MotionEvent) {
      if (param1MotionEvent != null && param1MotionEvent.getAction() == 1 && param1TextView != null && param1Spannable != null) {
        float f1 = param1MotionEvent.getX();
        float f2 = param1TextView.getTotalPaddingLeft();
        float f3 = param1TextView.getScrollX();
        float f4 = param1MotionEvent.getY();
        float f5 = param1TextView.getTotalPaddingTop();
        float f6 = param1TextView.getScrollY();
        Layout layout = param1TextView.getLayout();
        int i = layout.getOffsetForHorizontal(layout.getLineForVertical((int)(f4 - f5 + f6)), f1 - f2 + f3);
        Object[] arrayOfObject = param1Spannable.getSpans(i, i, URLSpan.class);
        Intrinsics.checkExpressionValueIsNotNull(arrayOfObject, "buffer.getSpans(off, off, URLSpan::class.java)");
        if (((URLSpan[])arrayOfObject).length == 0) {
          i = 1;
        } else {
          i = 0;
        } 
        if ((i ^ 0x1) != 0)
          HomeFragment.this.clearAndHideAnnouncement(); 
      } 
      return super.onTouchEvent(param1TextView, param1Spannable, param1MotionEvent);
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/fragment/HomeFragment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */