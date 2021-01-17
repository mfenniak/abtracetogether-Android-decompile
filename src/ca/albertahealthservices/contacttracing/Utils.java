package ca.albertahealthservices.contacttracing;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Parcelable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.scheduler.Scheduler;
import ca.albertahealthservices.contacttracing.services.BluetoothMonitoringService;
import ca.albertahealthservices.contacttracing.status.Status;
import ca.albertahealthservices.contacttracing.streetpass.ConnectablePeripheral;
import ca.albertahealthservices.contacttracing.streetpass.ConnectionRecord;
import com.worklight.wlclient.api.WLClient;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;

@Metadata(bv = {1, 0, 3}, d1 = {"\000r\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\n\n\002\020\t\n\002\b\003\n\002\020\021\n\002\b\005\n\002\030\002\n\002\b\005\n\002\020\b\n\002\b\020\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\026\020\005\032\0020\0062\006\020\007\032\0020\b2\006\020\t\032\0020\nJ\026\020\013\032\0020\0062\006\020\007\032\0020\b2\006\020\f\032\0020\004J\036\020\r\032\0020\0062\006\020\007\032\0020\b2\006\020\t\032\0020\n2\006\020\016\032\0020\017J\026\020\020\032\0020\0062\006\020\007\032\0020\b2\006\020\021\032\0020\022J\026\020\023\032\0020\0062\006\020\007\032\0020\b2\006\020\024\032\0020\025J\026\020\026\032\0020\0062\006\020\007\032\0020\b2\006\020\027\032\0020\004J\030\020\030\032\0020\0312\006\020\032\032\0020\0332\b\020\034\032\004\030\0010\035J\016\020\036\032\0020\0062\006\020\007\032\0020\bJ\016\020\037\032\0020\0062\006\020\007\032\0020\bJ\016\020 \032\0020\0062\006\020\007\032\0020\bJ\016\020!\032\0020\0062\006\020\007\032\0020\bJ\021\020\"\032\0020\031H@ø\001\000¢\006\002\020#J\016\020$\032\0020\0332\006\020%\032\0020\004J\016\020&\032\0020\0042\006\020'\032\0020(J\020\020)\032\004\030\0010\0042\006\020*\032\0020(J\021\020+\032\b\022\004\022\0020\0040,¢\006\002\020-J\006\020.\032\0020\004J\016\020/\032\0020\0042\006\020'\032\0020(J\026\0200\032\0020\0062\006\020\007\032\0020\b2\006\0201\032\00202J\006\0203\032\0020\031J\026\0204\032\0020\0042\006\020\007\032\0020\b2\006\0205\032\0020\004J\"\0206\032\0020\0062\006\020\007\032\0020\b2\006\0207\032\002082\n\b\002\0209\032\004\030\0010\004J\032\020:\032\0020\0062\006\0207\032\002082\n\b\002\0209\032\004\030\0010\004J\026\020;\032\0020\0062\006\020\007\032\0020\b2\006\020<\032\0020(J\026\020=\032\0020\0062\006\020\007\032\0020\b2\006\020>\032\0020(J\026\020?\032\0020\0062\006\020\007\032\0020\b2\006\020@\032\0020(J\026\020A\032\0020\0062\006\020\007\032\0020\b2\006\020@\032\0020(J\026\020B\032\0020\0062\006\020\007\032\0020\b2\006\020C\032\0020(J\026\020D\032\0020\0062\006\020\007\032\0020\b2\006\020@\032\0020(J\030\020E\032\0020\0062\006\020\007\032\0020\b2\b\0201\032\004\030\00102J\016\020F\032\0020\0062\006\020\007\032\0020\bJ\016\020G\032\0020\0062\006\020\007\032\0020\bR\016\020\003\032\0020\004XT¢\006\002\n\000\002\004\n\002\b\031¨\006H"}, d2 = {"Lca/albertahealthservices/contacttracing/Utils;", "", "()V", "TAG", "", "broadcastDeviceDisconnected", "", "context", "Landroid/content/Context;", "device", "Landroid/bluetooth/BluetoothDevice;", "broadcastDeviceProcessed", "deviceAddress", "broadcastDeviceScanned", "connectableBleDevice", "Lca/albertahealthservices/contacttracing/streetpass/ConnectablePeripheral;", "broadcastStatusReceived", "statusRecord", "Lca/albertahealthservices/contacttracing/status/Status;", "broadcastStreetPassReceived", "streetpass", "Lca/albertahealthservices/contacttracing/streetpass/ConnectionRecord;", "buildWrongVersionDialog", "msg", "canHandleIntent", "", "batteryExemptionIntent", "Landroid/content/Intent;", "packageManager", "Landroid/content/pm/PackageManager;", "cancelBMUpdateCheck", "cancelNextAdvertise", "cancelNextHealthCheck", "cancelNextScan", "checkIfAppRegistered", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBatteryOptimizerExemptionIntent", "packageName", "getDate", "milliSeconds", "", "getDateFromUnix", "unix_timestamp", "getRequiredPermissions", "", "()[Ljava/lang/String;", "getServerURL", "getTime", "hideKeyboardFrom", "view", "Landroid/view/View;", "isBluetoothAvailable", "readFromInternalStorage", "fileName", "restartApp", "errorType", "", "errorMsg", "restartAppWithNoContext", "scheduleBMUpdateCheck", "bmCheckInterval", "scheduleNextAdvertise", "timeToNextAdvertise", "scheduleNextHealthCheck", "timeInMillis", "scheduleNextScan", "scheduleRepeatingPurge", "intervalMillis", "scheduleStartMonitoringService", "showKeyboardFrom", "startBluetoothMonitoringService", "stopBluetoothMonitoringService", "app_release"}, k = 1, mv = {1, 1, 16})
public final class Utils {
  public static final Utils INSTANCE = new Utils();
  
  private static final String TAG = "Utils";
  
  public final void broadcastDeviceDisconnected(Context paramContext, BluetoothDevice paramBluetoothDevice) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramBluetoothDevice, "device");
    Intent intent = new Intent("ca.albertahealthservices.contacttracing.ACTION_GATT_DISCONNECTED");
    intent.putExtra("android.bluetooth.device.extra.DEVICE", (Parcelable)paramBluetoothDevice);
    LocalBroadcastManager.getInstance(paramContext).sendBroadcast(intent);
  }
  
  public final void broadcastDeviceProcessed(Context paramContext, String paramString) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramString, "deviceAddress");
    Intent intent = new Intent("ca.albertahealthservices.contacttracing.ACTION_DEVICE_PROCESSED");
    intent.putExtra("ca.albertahealthservices.contacttracing.DEVICE_ADDRESS", paramString);
    LocalBroadcastManager.getInstance(paramContext).sendBroadcast(intent);
  }
  
  public final void broadcastDeviceScanned(Context paramContext, BluetoothDevice paramBluetoothDevice, ConnectablePeripheral paramConnectablePeripheral) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramBluetoothDevice, "device");
    Intrinsics.checkParameterIsNotNull(paramConnectablePeripheral, "connectableBleDevice");
    Intent intent = new Intent("ca.albertahealthservices.contacttracing.ACTION_DEVICE_SCANNED");
    intent.putExtra("android.bluetooth.device.extra.DEVICE", (Parcelable)paramBluetoothDevice);
    intent.putExtra("ca.albertahealthservices.contacttracing.CONNECTION_DATA", (Parcelable)paramConnectablePeripheral);
    LocalBroadcastManager.getInstance(paramContext).sendBroadcast(intent);
  }
  
  public final void broadcastStatusReceived(Context paramContext, Status paramStatus) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramStatus, "statusRecord");
    Intent intent = new Intent("ca.albertahealthservices.contacttracing.ACTION_RECEIVED_STATUS");
    intent.putExtra("ca.albertahealthservices.contacttracing.STATUS", (Parcelable)paramStatus);
    LocalBroadcastManager.getInstance(paramContext).sendBroadcast(intent);
  }
  
  public final void broadcastStreetPassReceived(Context paramContext, ConnectionRecord paramConnectionRecord) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramConnectionRecord, "streetpass");
    Intent intent = new Intent("ca.albertahealthservices.contacttracing.ACTION_RECEIVED_STREETPASS");
    intent.putExtra("ca.albertahealthservices.contacttracing.STREET_PASS", (Parcelable)paramConnectionRecord);
    LocalBroadcastManager.getInstance(paramContext).sendBroadcast(intent);
  }
  
  public final void buildWrongVersionDialog(Context paramContext, String paramString) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramString, "msg");
    AlertDialog.Builder builder = new AlertDialog.Builder(paramContext);
    builder.setMessage(paramString);
    builder.setCancelable(false);
    builder.create().show();
  }
  
  public final boolean canHandleIntent(Intent paramIntent, PackageManager paramPackageManager) {
    Intrinsics.checkParameterIsNotNull(paramIntent, "batteryExemptionIntent");
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (paramPackageManager != null) {
      bool2 = bool1;
      if (paramIntent.resolveActivity(paramPackageManager) != null)
        bool2 = true; 
    } 
    return bool2;
  }
  
  public final void cancelBMUpdateCheck(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intent intent = new Intent(paramContext, BluetoothMonitoringService.class);
    intent.putExtra(BluetoothMonitoringService.Companion.getCOMMAND_KEY(), BluetoothMonitoringService.Command.ACTION_UPDATE_BM.getIndex());
    Scheduler.INSTANCE.cancelServiceIntent(BluetoothMonitoringService.Companion.getPENDING_BM_UPDATE(), paramContext, intent);
  }
  
  public final void cancelNextAdvertise(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intent intent = new Intent(paramContext, BluetoothMonitoringService.class);
    intent.putExtra(BluetoothMonitoringService.Companion.getCOMMAND_KEY(), BluetoothMonitoringService.Command.ACTION_ADVERTISE.getIndex());
    Scheduler.INSTANCE.cancelServiceIntent(BluetoothMonitoringService.Companion.getPENDING_ADVERTISE_REQ_CODE(), paramContext, intent);
  }
  
  public final void cancelNextHealthCheck(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intent intent = new Intent(paramContext, BluetoothMonitoringService.class);
    intent.putExtra(BluetoothMonitoringService.Companion.getCOMMAND_KEY(), BluetoothMonitoringService.Command.ACTION_SELF_CHECK.getIndex());
    Scheduler.INSTANCE.cancelServiceIntent(BluetoothMonitoringService.Companion.getPENDING_HEALTH_CHECK_CODE(), paramContext, intent);
  }
  
  public final void cancelNextScan(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intent intent = new Intent(paramContext, BluetoothMonitoringService.class);
    intent.putExtra(BluetoothMonitoringService.Companion.getCOMMAND_KEY(), BluetoothMonitoringService.Command.ACTION_SCAN.getIndex());
    Scheduler.INSTANCE.cancelServiceIntent(BluetoothMonitoringService.Companion.getPENDING_SCAN_REQ_CODE(), paramContext, intent);
  }
  
  public final Object checkIfAppRegistered(Continuation<? super Boolean> paramContinuation) {
    // Byte code:
    //   0: aload_1
    //   1: instanceof ca/albertahealthservices/contacttracing/Utils$checkIfAppRegistered$1
    //   4: ifeq -> 40
    //   7: aload_1
    //   8: checkcast ca/albertahealthservices/contacttracing/Utils$checkIfAppRegistered$1
    //   11: astore_2
    //   12: aload_2
    //   13: getfield label : I
    //   16: ldc_w -2147483648
    //   19: iand
    //   20: ifeq -> 40
    //   23: aload_2
    //   24: aload_2
    //   25: getfield label : I
    //   28: ldc_w -2147483648
    //   31: iadd
    //   32: putfield label : I
    //   35: aload_2
    //   36: astore_1
    //   37: goto -> 50
    //   40: new ca/albertahealthservices/contacttracing/Utils$checkIfAppRegistered$1
    //   43: dup
    //   44: aload_0
    //   45: aload_1
    //   46: invokespecial <init> : (Lca/albertahealthservices/contacttracing/Utils;Lkotlin/coroutines/Continuation;)V
    //   49: astore_1
    //   50: aload_1
    //   51: getfield result : Ljava/lang/Object;
    //   54: astore_2
    //   55: invokestatic getCOROUTINE_SUSPENDED : ()Ljava/lang/Object;
    //   58: astore_3
    //   59: aload_1
    //   60: getfield label : I
    //   63: istore #4
    //   65: iconst_1
    //   66: istore #5
    //   68: iload #4
    //   70: ifeq -> 113
    //   73: iload #4
    //   75: iconst_1
    //   76: if_icmpne -> 102
    //   79: aload_1
    //   80: getfield Z$0 : Z
    //   83: istore #5
    //   85: aload_1
    //   86: getfield L$0 : Ljava/lang/Object;
    //   89: checkcast ca/albertahealthservices/contacttracing/Utils
    //   92: astore_1
    //   93: aload_2
    //   94: invokestatic throwOnFailure : (Ljava/lang/Object;)V
    //   97: aload_2
    //   98: astore_1
    //   99: goto -> 164
    //   102: new java/lang/IllegalStateException
    //   105: dup
    //   106: ldc_w 'call to 'resume' before 'invoke' with coroutine'
    //   109: invokespecial <init> : (Ljava/lang/String;)V
    //   112: athrow
    //   113: aload_2
    //   114: invokestatic throwOnFailure : (Ljava/lang/Object;)V
    //   117: getstatic ca/albertahealthservices/contacttracing/api/Request.INSTANCE : Lca/albertahealthservices/contacttracing/api/Request;
    //   120: astore_2
    //   121: aload_1
    //   122: aload_0
    //   123: putfield L$0 : Ljava/lang/Object;
    //   126: aload_1
    //   127: iconst_1
    //   128: putfield Z$0 : Z
    //   131: aload_1
    //   132: iconst_1
    //   133: putfield label : I
    //   136: aload_2
    //   137: ldc_w '/adapters/smsOtpService/phone/isRegistered'
    //   140: ldc_w 'GET'
    //   143: iconst_0
    //   144: aconst_null
    //   145: aconst_null
    //   146: aconst_null
    //   147: aload_1
    //   148: bipush #60
    //   150: aconst_null
    //   151: invokestatic runRequest$default : (Lca/albertahealthservices/contacttracing/api/Request;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Lorg/json/JSONObject;Ljava/util/HashMap;Lkotlin/coroutines/Continuation;ILjava/lang/Object;)Ljava/lang/Object;
    //   154: astore_2
    //   155: aload_2
    //   156: astore_1
    //   157: aload_2
    //   158: aload_3
    //   159: if_acmpne -> 164
    //   162: aload_3
    //   163: areturn
    //   164: aload_1
    //   165: checkcast ca/albertahealthservices/contacttracing/api/Response
    //   168: invokevirtual getErrorCode : ()Ljava/lang/String;
    //   171: getstatic ca/albertahealthservices/contacttracing/api/ErrorCode.INSTANCE : Lca/albertahealthservices/contacttracing/api/ErrorCode;
    //   174: invokevirtual getAPPLICATION_DOES_NOT_EXIST : ()Ljava/lang/String;
    //   177: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   180: ifeq -> 186
    //   183: iconst_0
    //   184: istore #5
    //   186: iload #5
    //   188: invokestatic boxBoolean : (Z)Ljava/lang/Boolean;
    //   191: areturn
  }
  
  public final Intent getBatteryOptimizerExemptionIntent(String paramString) {
    Intrinsics.checkParameterIsNotNull(paramString, "packageName");
    Intent intent = new Intent();
    intent.setAction("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("package:");
    stringBuilder.append(paramString);
    intent.setData(Uri.parse(stringBuilder.toString()));
    return intent;
  }
  
  public final String getDate(long paramLong) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
    Calendar calendar = Calendar.getInstance();
    Intrinsics.checkExpressionValueIsNotNull(calendar, "calendar");
    calendar.setTimeInMillis(paramLong);
    String str = simpleDateFormat.format(calendar.getTime());
    Intrinsics.checkExpressionValueIsNotNull(str, "formatter.format(calendar.time)");
    return str;
  }
  
  public final String getDateFromUnix(long paramLong) {
    return (new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.ENGLISH)).format(Long.valueOf(paramLong)).toString();
  }
  
  public final String[] getRequiredPermissions() {
    return new String[] { "android.permission.ACCESS_FINE_LOCATION" };
  }
  
  public final String getServerURL() {
    WLClient wLClient = WLClient.getInstance();
    Intrinsics.checkExpressionValueIsNotNull(wLClient, "WLClient.getInstance()");
    String str = wLClient.getServerUrl().toString();
    Intrinsics.checkExpressionValueIsNotNull(str, "WLClient.getInstance().serverUrl.toString()");
    return str;
  }
  
  public final String getTime(long paramLong) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
    Calendar calendar = Calendar.getInstance();
    Intrinsics.checkExpressionValueIsNotNull(calendar, "calendar");
    calendar.setTimeInMillis(paramLong);
    String str = simpleDateFormat.format(calendar.getTime());
    Intrinsics.checkExpressionValueIsNotNull(str, "formatter.format(calendar.time)");
    return str;
  }
  
  public final void hideKeyboardFrom(Context paramContext, View paramView) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    Object object = paramContext.getSystemService("input_method");
    if (object != null) {
      ((InputMethodManager)object).hideSoftInputFromWindow(paramView.getWindowToken(), 0);
      return;
    } 
    throw new TypeCastException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
  }
  
  public final boolean isBluetoothAvailable() {
    boolean bool;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (bluetoothAdapter != null && bluetoothAdapter.isEnabled() && bluetoothAdapter.getState() == 12) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final String readFromInternalStorage(Context paramContext, String paramString) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramString, "fileName");
    CentralLog.Companion.d("Utils", "Reading from internal storage");
    Ref.ObjectRef objectRef = new Ref.ObjectRef();
    objectRef.element = null;
    StringBuilder stringBuilder = new StringBuilder();
    FileInputStream fileInputStream = paramContext.openFileInput(paramString);
    Intrinsics.checkExpressionValueIsNotNull(fileInputStream, "context.openFileInput(fileName)");
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
    try {
      while (true) {
        Utils$readFromInternalStorage$1 utils$readFromInternalStorage$1 = new Utils$readFromInternalStorage$1();
        return str1;
      } 
    } finally {
      bufferedReader = null;
      CentralLog.Companion companion = CentralLog.Companion;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Failed to readFromInternalStorage: ");
      stringBuilder1.append(bufferedReader.getMessage());
    } 
    String str = stringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(str, "stringBuilder.toString()");
    return str;
  }
  
  public final void restartApp(Context paramContext, int paramInt, String paramString) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intent intent = new Intent(paramContext, RestartActivity.class);
    if (paramString != null)
      intent.putExtra("error_msg", paramString); 
    intent.putExtra("error_type", paramInt);
    intent.addFlags(268435456);
    paramContext.startActivity(intent);
  }
  
  public final void restartAppWithNoContext(int paramInt, String paramString) {
    Intent intent = new Intent(TracerApp.Companion.getAppContext(), RestartActivity.class);
    if (paramString != null)
      intent.putExtra("error_msg", paramString); 
    intent.putExtra("error_type", paramInt);
    intent.addFlags(268435456);
    TracerApp.Companion.getAppContext().startActivity(intent);
  }
  
  public final void scheduleBMUpdateCheck(Context paramContext, long paramLong) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    cancelBMUpdateCheck(paramContext);
    Intent intent = new Intent(paramContext, BluetoothMonitoringService.class);
    intent.putExtra(BluetoothMonitoringService.Companion.getCOMMAND_KEY(), BluetoothMonitoringService.Command.ACTION_UPDATE_BM.getIndex());
    Scheduler.INSTANCE.scheduleServiceIntent(BluetoothMonitoringService.Companion.getPENDING_BM_UPDATE(), paramContext, intent, paramLong);
  }
  
  public final void scheduleNextAdvertise(Context paramContext, long paramLong) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    cancelNextAdvertise(paramContext);
    Intent intent = new Intent(paramContext, BluetoothMonitoringService.class);
    intent.putExtra(BluetoothMonitoringService.Companion.getCOMMAND_KEY(), BluetoothMonitoringService.Command.ACTION_ADVERTISE.getIndex());
    Scheduler.INSTANCE.scheduleServiceIntent(BluetoothMonitoringService.Companion.getPENDING_ADVERTISE_REQ_CODE(), paramContext, intent, paramLong);
  }
  
  public final void scheduleNextHealthCheck(Context paramContext, long paramLong) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    cancelNextHealthCheck(paramContext);
    Intent intent = new Intent(paramContext, BluetoothMonitoringService.class);
    intent.putExtra(BluetoothMonitoringService.Companion.getCOMMAND_KEY(), BluetoothMonitoringService.Command.ACTION_SELF_CHECK.getIndex());
    Scheduler.INSTANCE.scheduleServiceIntent(BluetoothMonitoringService.Companion.getPENDING_HEALTH_CHECK_CODE(), paramContext, intent, paramLong);
  }
  
  public final void scheduleNextScan(Context paramContext, long paramLong) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    cancelNextScan(paramContext);
    Intent intent = new Intent(paramContext, BluetoothMonitoringService.class);
    intent.putExtra(BluetoothMonitoringService.Companion.getCOMMAND_KEY(), BluetoothMonitoringService.Command.ACTION_SCAN.getIndex());
    Scheduler.INSTANCE.scheduleServiceIntent(BluetoothMonitoringService.Companion.getPENDING_SCAN_REQ_CODE(), paramContext, intent, paramLong);
  }
  
  public final void scheduleRepeatingPurge(Context paramContext, long paramLong) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intent intent = new Intent(paramContext, BluetoothMonitoringService.class);
    intent.putExtra(BluetoothMonitoringService.Companion.getCOMMAND_KEY(), BluetoothMonitoringService.Command.ACTION_PURGE.getIndex());
    Scheduler.INSTANCE.scheduleRepeatingServiceIntent(BluetoothMonitoringService.Companion.getPENDING_PURGE_CODE(), paramContext, intent, paramLong);
  }
  
  public final void scheduleStartMonitoringService(Context paramContext, long paramLong) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intent intent = new Intent(paramContext, BluetoothMonitoringService.class);
    intent.putExtra(BluetoothMonitoringService.Companion.getCOMMAND_KEY(), BluetoothMonitoringService.Command.ACTION_START.getIndex());
    Scheduler.INSTANCE.scheduleServiceIntent(BluetoothMonitoringService.Companion.getPENDING_START(), paramContext, intent, paramLong);
  }
  
  public final void showKeyboardFrom(Context paramContext, View paramView) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Object object = paramContext.getSystemService("input_method");
    if (object != null) {
      ((InputMethodManager)object).showSoftInput(paramView, 2);
      return;
    } 
    throw new TypeCastException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
  }
  
  public final void startBluetoothMonitoringService(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intent intent = new Intent(paramContext, BluetoothMonitoringService.class);
    intent.putExtra(BluetoothMonitoringService.Companion.getCOMMAND_KEY(), BluetoothMonitoringService.Command.ACTION_START.getIndex());
    paramContext.startService(intent);
  }
  
  public final void stopBluetoothMonitoringService(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intent intent = new Intent(paramContext, BluetoothMonitoringService.class);
    intent.putExtra(BluetoothMonitoringService.Companion.getCOMMAND_KEY(), BluetoothMonitoringService.Command.ACTION_STOP.getIndex());
    cancelNextScan(paramContext);
    cancelNextHealthCheck(paramContext);
    paramContext.stopService(intent);
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\000\n\000\n\002\030\002\n\002\020\013\020\000\032\004\030\0010\0012\f\020\002\032\b\022\004\022\0020\0040\003H@"}, d2 = {"checkIfAppRegistered", "", "continuation", "Lkotlin/coroutines/Continuation;", ""}, k = 3, mv = {1, 1, 16})
  @DebugMetadata(c = "ca.albertahealthservices.contacttracing.Utils", f = "Utils.kt", i = {0, 0}, l = {348}, m = "checkIfAppRegistered", n = {"this", "isRegistered"}, s = {"L$0", "Z$0"})
  static final class Utils$checkIfAppRegistered$1 extends ContinuationImpl {
    Object L$0;
    
    boolean Z$0;
    
    int label;
    
    Utils$checkIfAppRegistered$1(Continuation param1Continuation) {
      super(param1Continuation);
    }
    
    public final Object invokeSuspend(Object param1Object) {
      this.result = param1Object;
      this.label |= Integer.MIN_VALUE;
      return Utils.this.checkIfAppRegistered((Continuation<? super Boolean>)this);
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\b\n\000\n\002\020\016\n\000\020\000\032\004\030\0010\001H\n¢\006\002\b\002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 1, 16})
  static final class Utils$readFromInternalStorage$1 extends Lambda implements Function0<String> {
    Utils$readFromInternalStorage$1(Ref.ObjectRef param1ObjectRef, BufferedReader param1BufferedReader) {
      super(0);
    }
    
    public final String invoke() {
      this.$text.element = this.$bufferedReader.readLine();
      return (String)this.$text.element;
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */