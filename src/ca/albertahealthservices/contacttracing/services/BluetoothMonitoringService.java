package ca.albertahealthservices.contacttracing.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PowerManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import ca.albertahealthservices.contacttracing.Preference;
import ca.albertahealthservices.contacttracing.Utils;
import ca.albertahealthservices.contacttracing.bluetooth.BLEAdvertiser;
import ca.albertahealthservices.contacttracing.idmanager.TempIDManager;
import ca.albertahealthservices.contacttracing.idmanager.TemporaryID;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.notifications.NotificationTemplates;
import ca.albertahealthservices.contacttracing.permissions.RequestFileWritePermission;
import ca.albertahealthservices.contacttracing.status.Status;
import ca.albertahealthservices.contacttracing.status.persistence.StatusRecord;
import ca.albertahealthservices.contacttracing.status.persistence.StatusRecordStorage;
import ca.albertahealthservices.contacttracing.streetpass.ConnectionRecord;
import ca.albertahealthservices.contacttracing.streetpass.StreetPassScanner;
import ca.albertahealthservices.contacttracing.streetpass.StreetPassServer;
import ca.albertahealthservices.contacttracing.streetpass.StreetPassWorker;
import ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecord;
import ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecordStorage;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import pub.devrel.easypermissions.EasyPermissions;

@Metadata(bv = {1, 0, 3}, d1 = {"\000®\001\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\020\002\n\002\b\b\n\002\020\t\n\002\b\003\n\002\020\013\n\002\b\006\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\t\n\002\030\002\n\002\b\026\n\002\030\002\030\000 `2\0020\0012\0020\002:\006^_`abcB\005¢\006\002\020\003J\b\020*\032\0020+H\002J\b\020,\032\0020+H\002J\b\020-\032\0020+H\002J\b\020.\032\0020+H\002J\b\020/\032\0020+H\002J\b\0200\032\0020+H\002J\b\0201\032\0020+H\002J\006\0202\032\0020+J\026\0203\032\002042\006\0205\032\002042\006\0206\032\00204J\b\0207\032\00208H\002J\b\0209\032\00208H\002J\b\020:\032\00208H\002J\022\020;\032\0020+2\b\b\002\020<\032\00208H\002J\022\020=\032\0020+2\b\b\002\020<\032\00208H\002J\024\020>\032\004\030\0010?2\b\020@\032\004\030\0010AH\026J\b\020B\032\0020+H\026J\b\020C\032\0020+H\026J\"\020D\032\0020E2\b\020@\032\004\030\0010A2\006\020F\032\0020E2\006\020G\032\0020EH\026J\b\020H\032\0020+H\002J\b\020I\032\0020+H\002J\b\020J\032\0020+H\002J\b\020K\032\0020+H\002J\b\020L\032\0020+H\002J\020\020M\032\0020+2\b\020N\032\004\030\0010OJ\b\020P\032\0020+H\002J\b\020Q\032\0020+H\002J\b\020R\032\0020+H\002J\b\020S\032\0020+H\002J\b\020T\032\0020+H\002J\b\020U\032\0020+H\002J\b\020V\032\0020+H\002J\b\020W\032\0020+H\002J\b\020X\032\0020+H\002J\b\020Y\032\0020+H\002J\b\020Z\032\0020+H\002J\b\020[\032\0020+H\002J\006\020\\\032\0020+J\b\020]\032\0020+H\002R\020\020\004\032\004\030\0010\005X\016¢\006\002\n\000R\022\020\006\032\0060\007R\0020\000X\004¢\006\002\n\000R\016\020\b\032\0020\tX.¢\006\002\n\000R\024\020\n\032\0020\0138VX\004¢\006\006\032\004\b\f\020\rR\016\020\016\032\0020\017X\016¢\006\002\n\000R\016\020\020\032\0020\021X.¢\006\002\n\000R\020\020\022\032\004\030\0010\023X\016¢\006\002\n\000R\020\020\024\032\004\030\0010\025X\016¢\006\002\n\000R\016\020\026\032\0020\027X.¢\006\002\n\000R\022\020\030\032\0060\031R\0020\000X\004¢\006\002\n\000R\016\020\032\032\0020\033X.¢\006\002\n\000R\022\020\034\032\0060\035R\0020\000X\004¢\006\002\n\000R\016\020\036\032\0020\037X.¢\006\002\n\000R\020\020 \032\004\030\0010!X\016¢\006\002\n\000R\020\020\"\032\004\030\0010#X\016¢\006\002\n\000R\034\020$\032\004\030\0010%X\016¢\006\016\n\000\032\004\b&\020'\"\004\b(\020)¨\006d²\006\f\020e\032\004\030\0010fX\002"}, d2 = {"Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService;", "Landroid/app/Service;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "advertiser", "Lca/albertahealthservices/contacttracing/bluetooth/BLEAdvertiser;", "bluetoothStatusReceiver", "Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$BluetoothStatusReceiver;", "commandHandler", "Lca/albertahealthservices/contacttracing/services/CommandHandler;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "job", "Lkotlinx/coroutines/Job;", "localBroadcastManager", "Landroidx/localbroadcastmanager/content/LocalBroadcastManager;", "mNotificationManager", "Landroid/app/NotificationManager;", "notificationShown", "Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$NOTIFICATION_STATE;", "serviceUUID", "", "statusReceiver", "Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$StatusReceiver;", "statusRecordStorage", "Lca/albertahealthservices/contacttracing/status/persistence/StatusRecordStorage;", "streetPassReceiver", "Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$StreetPassReceiver;", "streetPassRecordStorage", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordStorage;", "streetPassScanner", "Lca/albertahealthservices/contacttracing/streetpass/StreetPassScanner;", "streetPassServer", "Lca/albertahealthservices/contacttracing/streetpass/StreetPassServer;", "worker", "Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;", "getWorker", "()Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;", "setWorker", "(Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)V", "acquireWritePermission", "", "actionAdvertise", "actionHealthCheck", "actionPurge", "actionScan", "actionStart", "actionStop", "actionUpdateBm", "calcPhaseShift", "", "min", "max", "hasLocationPermissions", "", "hasWritePermissions", "isBluetoothEnabled", "notifyLackingThings", "override", "notifyRunning", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "", "flags", "startId", "performHealthCheck", "performPurge", "performScan", "performUserLoginCheck", "registerReceivers", "runService", "cmd", "Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Command;", "scheduleAdvertisement", "scheduleScan", "setup", "setupAdvertiser", "setupAdvertisingCycles", "setupCycles", "setupNotifications", "setupScanCycles", "setupScanner", "setupService", "startScan", "stopService", "teardown", "unregisterReceivers", "BluetoothStatusReceiver", "Command", "Companion", "NOTIFICATION_STATE", "StatusReceiver", "StreetPassReceiver", "app_release", "bluetoothAdapter", "Landroid/bluetooth/BluetoothAdapter;"}, k = 1, mv = {1, 1, 16})
public final class BluetoothMonitoringService extends Service implements CoroutineScope {
  private static final String CHANNEL_ID = "ACT Updates";
  
  private static final String CHANNEL_SERVICE = "ACT Foreground Service";
  
  private static final String COMMAND_KEY = "ca.albertahealthservices.contacttracing_CMD";
  
  public static final Companion Companion = new Companion(null);
  
  private static final int NOTIFICATION_ID = 771579;
  
  private static final int PENDING_ACTIVITY = 5;
  
  private static final int PENDING_ADVERTISE_REQ_CODE = 8;
  
  private static final int PENDING_BM_UPDATE = 11;
  
  private static final int PENDING_HEALTH_CHECK_CODE = 9;
  
  private static final int PENDING_PURGE_CODE = 12;
  
  private static final int PENDING_SCAN_REQ_CODE = 7;
  
  private static final int PENDING_START = 6;
  
  private static final int PENDING_WIZARD_REQ_CODE = 10;
  
  private static final int PUSH_NOTIFICATION_ID = 771578;
  
  private static final String TAG = "BTMService";
  
  private static final long advertisingDuration = 180000L;
  
  private static final long advertisingGap = 5000L;
  
  private static final long blacklistDuration = 100000L;
  
  private static final long bmCheckInterval = 540000L;
  
  private static TemporaryID broadcastMessage;
  
  private static final long connectionTimeout = 6000L;
  
  private static final long healthCheckInterval = 900000L;
  
  private static final boolean infiniteAdvertising = false;
  
  private static final boolean infiniteScanning = false;
  
  private static final long maxQueueTime = 7000L;
  
  private static final long maxScanInterval = 60000L;
  
  private static final long minScanInterval = 35000L;
  
  private static final long purgeInterval = 86400000L;
  
  private static final long purgeTTL = 1814400000L;
  
  private static final long scanDuration = 10000L;
  
  private static final boolean useBlacklist = true;
  
  private BLEAdvertiser advertiser;
  
  private final BluetoothStatusReceiver bluetoothStatusReceiver = new BluetoothStatusReceiver();
  
  private CommandHandler commandHandler;
  
  private Job job = (Job)JobKt.Job$default(null, 1, null);
  
  private LocalBroadcastManager localBroadcastManager;
  
  private NotificationManager mNotificationManager;
  
  private NOTIFICATION_STATE notificationShown;
  
  private String serviceUUID;
  
  private final StatusReceiver statusReceiver = new StatusReceiver();
  
  private StatusRecordStorage statusRecordStorage;
  
  private final StreetPassReceiver streetPassReceiver = new StreetPassReceiver();
  
  private StreetPassRecordStorage streetPassRecordStorage;
  
  private StreetPassScanner streetPassScanner;
  
  private StreetPassServer streetPassServer;
  
  private StreetPassWorker worker;
  
  static {
    NOTIFICATION_ID = 771579;
    CHANNEL_ID = "ACT Updates";
    CHANNEL_SERVICE = "ACT Foreground Service";
    PUSH_NOTIFICATION_ID = 771578;
    COMMAND_KEY = "ca.albertahealthservices.contacttracing_CMD";
    PENDING_ACTIVITY = 5;
    PENDING_START = 6;
    PENDING_SCAN_REQ_CODE = 7;
    PENDING_ADVERTISE_REQ_CODE = 8;
    PENDING_HEALTH_CHECK_CODE = 9;
    PENDING_WIZARD_REQ_CODE = 10;
    PENDING_BM_UPDATE = 11;
    PENDING_PURGE_CODE = 12;
    scanDuration = 10000L;
    minScanInterval = 35000L;
    maxScanInterval = 60000L;
    advertisingDuration = 180000L;
    advertisingGap = 5000L;
    maxQueueTime = 7000L;
    bmCheckInterval = 540000L;
    healthCheckInterval = 900000L;
    purgeInterval = 86400000L;
    purgeTTL = 1814400000L;
    connectionTimeout = 6000L;
    blacklistDuration = 100000L;
    useBlacklist = true;
  }
  
  private final void acquireWritePermission() {
    Intent intent = new Intent(getApplicationContext(), RequestFileWritePermission.class);
    intent.setFlags(268435456);
    startActivity(intent);
  }
  
  private final void actionAdvertise() {
    setupAdvertiser();
    if (isBluetoothEnabled()) {
      BLEAdvertiser bLEAdvertiser = this.advertiser;
      if (bLEAdvertiser != null)
        bLEAdvertiser.startAdvertising(advertisingDuration); 
    } else {
      CentralLog.Companion.w(TAG, "Unable to start advertising, bluetooth is off");
    } 
  }
  
  private final void actionHealthCheck() {
    performUserLoginCheck();
    performHealthCheck();
    Utils utils = Utils.INSTANCE;
    Context context = getApplicationContext();
    Intrinsics.checkExpressionValueIsNotNull(context, "this.applicationContext");
    utils.scheduleRepeatingPurge(context, purgeInterval);
  }
  
  private final void actionPurge() {
    performPurge();
  }
  
  private final void actionScan() {
    TempIDManager tempIDManager = TempIDManager.INSTANCE;
    Context context = getApplicationContext();
    Intrinsics.checkExpressionValueIsNotNull(context, "this.applicationContext");
    if (tempIDManager.needToUpdate(context) || broadcastMessage == null) {
      CentralLog.Companion.i(TAG, "[TempID] Need to update TemporaryID in actionScan");
      tempIDManager = TempIDManager.INSTANCE;
      context = getApplicationContext();
      Intrinsics.checkExpressionValueIsNotNull(context, "this.applicationContext");
      tempIDManager.getTemporaryIDs(context);
      tempIDManager = TempIDManager.INSTANCE;
      context = getApplicationContext();
      Intrinsics.checkExpressionValueIsNotNull(context, "this.applicationContext");
      TemporaryID temporaryID = tempIDManager.retrieveTemporaryID(context);
      if (temporaryID != null) {
        broadcastMessage = temporaryID;
        performScan();
      } 
    } else {
      CentralLog.Companion.i(TAG, "[TempID] Don't need to update Temp ID in actionScan");
      performScan();
    } 
    performScan();
  }
  
  private final void actionStart() {
    CentralLog.Companion.d(TAG, "Action Start");
    TempIDManager tempIDManager1 = TempIDManager.INSTANCE;
    Context context2 = getApplicationContext();
    Intrinsics.checkExpressionValueIsNotNull(context2, "applicationContext");
    broadcastMessage = tempIDManager1.retrieveTemporaryID(context2);
    setupCycles();
    TempIDManager.INSTANCE.getTemporaryIDs((Context)this);
    CentralLog.Companion.d(TAG, "Get TemporaryIDs completed");
    TempIDManager tempIDManager2 = TempIDManager.INSTANCE;
    Context context1 = getApplicationContext();
    Intrinsics.checkExpressionValueIsNotNull(context1, "this.applicationContext");
    TemporaryID temporaryID = tempIDManager2.retrieveTemporaryID(context1);
    if (temporaryID != null) {
      broadcastMessage = temporaryID;
      setupCycles();
    } 
  }
  
  private final void actionStop() {
    stopForeground(true);
    stopSelf();
    CentralLog.Companion.w(TAG, "Service Stopping");
  }
  
  private final boolean hasLocationPermissions() {
    String[] arrayOfString = Utils.INSTANCE.getRequiredPermissions();
    return EasyPermissions.hasPermissions(getApplicationContext(), Arrays.<String>copyOf(arrayOfString, arrayOfString.length));
  }
  
  private final boolean hasWritePermissions() {
    return EasyPermissions.hasPermissions(getApplicationContext(), Arrays.<String>copyOf(new String[] { "android.permission.WRITE_EXTERNAL_STORAGE" }, 1));
  }
  
  private final boolean isBluetoothEnabled() {
    Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, new BluetoothMonitoringService$isBluetoothEnabled$bluetoothAdapter$2());
    KProperty[] arrayOfKProperty = $$delegatedProperties;
    boolean bool = false;
    KProperty kProperty = arrayOfKProperty[0];
    BluetoothAdapter bluetoothAdapter = (BluetoothAdapter)lazy.getValue();
    if (bluetoothAdapter != null)
      bool = bluetoothAdapter.isEnabled(); 
    return bool;
  }
  
  private final void notifyLackingThings(boolean paramBoolean) {
    if (this.notificationShown != NOTIFICATION_STATE.LACKING_THINGS || paramBoolean) {
      NotificationTemplates.Companion companion = NotificationTemplates.Companion;
      Context context = getApplicationContext();
      Intrinsics.checkExpressionValueIsNotNull(context, "this.applicationContext");
      Notification notification = companion.lackingThingsNotification(context, CHANNEL_ID);
      startForeground(NOTIFICATION_ID, notification);
      this.notificationShown = NOTIFICATION_STATE.LACKING_THINGS;
    } 
  }
  
  private final void notifyRunning(boolean paramBoolean) {
    if (this.notificationShown != NOTIFICATION_STATE.RUNNING || paramBoolean) {
      NotificationTemplates.Companion companion = NotificationTemplates.Companion;
      Context context = getApplicationContext();
      Intrinsics.checkExpressionValueIsNotNull(context, "this.applicationContext");
      Notification notification = companion.getRunningNotification(context, CHANNEL_ID);
      startForeground(NOTIFICATION_ID, notification);
      this.notificationShown = NOTIFICATION_STATE.RUNNING;
    } 
  }
  
  private final void performHealthCheck() {
    CentralLog.Companion.i(TAG, "Performing self diagnosis");
    if (!hasLocationPermissions() || !isBluetoothEnabled()) {
      CentralLog.Companion.i(TAG, "no location permission");
      notifyLackingThings(true);
      return;
    } 
    notifyRunning(true);
    setupService();
    if (!infiniteScanning) {
      CommandHandler commandHandler = this.commandHandler;
      if (commandHandler == null)
        Intrinsics.throwUninitializedPropertyAccessException("commandHandler"); 
      if (!commandHandler.hasScanScheduled()) {
        CentralLog.Companion.w(TAG, "Missing Scan Schedule - rectifying");
        commandHandler = this.commandHandler;
        if (commandHandler == null)
          Intrinsics.throwUninitializedPropertyAccessException("commandHandler"); 
        commandHandler.scheduleNextScan(100L);
      } else {
        CentralLog.Companion.w(TAG, "Scan Schedule present");
      } 
    } else {
      CentralLog.Companion.w(TAG, "Should be operating under infinite scan mode");
    } 
    if (!infiniteAdvertising) {
      CommandHandler commandHandler = this.commandHandler;
      if (commandHandler == null)
        Intrinsics.throwUninitializedPropertyAccessException("commandHandler"); 
      if (!commandHandler.hasAdvertiseScheduled()) {
        CentralLog.Companion.w(TAG, "Missing Advertise Schedule - rectifying");
        commandHandler = this.commandHandler;
        if (commandHandler == null)
          Intrinsics.throwUninitializedPropertyAccessException("commandHandler"); 
        commandHandler.scheduleNextAdvertise(100L);
      } else {
        CentralLog.Companion companion = CentralLog.Companion;
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Advertise Schedule present. Should be advertising?:  ");
        BLEAdvertiser bLEAdvertiser = this.advertiser;
        boolean bool1 = false;
        if (bLEAdvertiser != null) {
          bool2 = bLEAdvertiser.getShouldBeAdvertising();
        } else {
          bool2 = false;
        } 
        stringBuilder.append(bool2);
        stringBuilder.append(". Is Advertising?: ");
        bLEAdvertiser = this.advertiser;
        boolean bool2 = bool1;
        if (bLEAdvertiser != null)
          bool2 = bLEAdvertiser.isAdvertising(); 
        stringBuilder.append(bool2);
        companion.w(str, stringBuilder.toString());
      } 
    } else {
      CentralLog.Companion.w(TAG, "Should be operating under infinite advertise mode");
    } 
  }
  
  private final void performPurge() {
    BuildersKt.launch$default(this, null, null, new BluetoothMonitoringService$performPurge$1(this, null), 3, null);
  }
  
  private final void performScan() {
    setupScanner();
    startScan();
  }
  
  private final void performUserLoginCheck() {
    if (Intrinsics.areEqual(Preference.INSTANCE.getUUID(getApplicationContext()), "")) {
      Preference preference = Preference.INSTANCE;
      Context context = getApplicationContext();
      Intrinsics.checkExpressionValueIsNotNull(context, "applicationContext");
      if (preference.isOnBoarded(context))
        CentralLog.Companion.d(TAG, "User is not login but has completed onboarding"); 
    } 
  }
  
  private final void registerReceivers() {
    IntentFilter intentFilter2 = new IntentFilter("ca.albertahealthservices.contacttracing.ACTION_RECEIVED_STREETPASS");
    LocalBroadcastManager localBroadcastManager2 = this.localBroadcastManager;
    if (localBroadcastManager2 == null)
      Intrinsics.throwUninitializedPropertyAccessException("localBroadcastManager"); 
    localBroadcastManager2.registerReceiver(this.streetPassReceiver, intentFilter2);
    IntentFilter intentFilter3 = new IntentFilter("ca.albertahealthservices.contacttracing.ACTION_RECEIVED_STATUS");
    LocalBroadcastManager localBroadcastManager1 = this.localBroadcastManager;
    if (localBroadcastManager1 == null)
      Intrinsics.throwUninitializedPropertyAccessException("localBroadcastManager"); 
    localBroadcastManager1.registerReceiver(this.statusReceiver, intentFilter3);
    IntentFilter intentFilter1 = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
    registerReceiver(this.bluetoothStatusReceiver, intentFilter1);
    CentralLog.Companion.i(TAG, "Receivers registered");
  }
  
  private final void scheduleAdvertisement() {
    if (!infiniteAdvertising) {
      CommandHandler commandHandler = this.commandHandler;
      if (commandHandler == null)
        Intrinsics.throwUninitializedPropertyAccessException("commandHandler"); 
      commandHandler.scheduleNextAdvertise(advertisingDuration + advertisingGap);
    } 
  }
  
  private final void scheduleScan() {
    if (!infiniteScanning) {
      CommandHandler commandHandler = this.commandHandler;
      if (commandHandler == null)
        Intrinsics.throwUninitializedPropertyAccessException("commandHandler"); 
      commandHandler.scheduleNextScan(scanDuration + calcPhaseShift(minScanInterval, maxScanInterval));
    } 
  }
  
  private final void setup() {
    Object object = getSystemService("power");
    if (object != null) {
      object = object;
      CentralLog.Companion.setPowerManager((PowerManager)object);
      this.commandHandler = new CommandHandler(new WeakReference<>(this));
      CentralLog.Companion.d(TAG, "Creating service - BluetoothMonitoringService");
      this.serviceUUID = "70f34cb2-8882-11ea-bc55-0242ac130003";
      object = getApplicationContext();
      Intrinsics.checkExpressionValueIsNotNull(object, "this.applicationContext");
      this.worker = new StreetPassWorker((Context)object);
      unregisterReceivers();
      registerReceivers();
      object = getApplicationContext();
      Intrinsics.checkExpressionValueIsNotNull(object, "this.applicationContext");
      this.streetPassRecordStorage = new StreetPassRecordStorage((Context)object);
      object = getApplicationContext();
      Intrinsics.checkExpressionValueIsNotNull(object, "this.applicationContext");
      this.statusRecordStorage = new StatusRecordStorage((Context)object);
      setupNotifications();
      object = TempIDManager.INSTANCE;
      Context context = getApplicationContext();
      Intrinsics.checkExpressionValueIsNotNull(context, "this.applicationContext");
      broadcastMessage = object.retrieveTemporaryID(context);
      return;
    } 
    throw new TypeCastException("null cannot be cast to non-null type android.os.PowerManager");
  }
  
  private final void setupAdvertiser() {
    BLEAdvertiser bLEAdvertiser = this.advertiser;
    if (bLEAdvertiser == null) {
      String str = this.serviceUUID;
      if (str == null)
        Intrinsics.throwUninitializedPropertyAccessException("serviceUUID"); 
      bLEAdvertiser = new BLEAdvertiser(str);
    } 
    this.advertiser = bLEAdvertiser;
  }
  
  private final void setupAdvertisingCycles() {
    CommandHandler commandHandler = this.commandHandler;
    if (commandHandler == null)
      Intrinsics.throwUninitializedPropertyAccessException("commandHandler"); 
    commandHandler.scheduleNextAdvertise(0L);
  }
  
  private final void setupCycles() {
    setupScanCycles();
    setupAdvertisingCycles();
  }
  
  private final void setupNotifications() {
    Object object = getSystemService("notification");
    if (object != null) {
      this.mNotificationManager = (NotificationManager)object;
      if (Build.VERSION.SDK_INT >= 26) {
        object = CHANNEL_SERVICE;
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, (CharSequence)object, 2);
        notificationChannel.enableLights(false);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[] { 0L });
        notificationChannel.setSound(null, null);
        notificationChannel.setShowBadge(false);
        object = this.mNotificationManager;
        if (object == null)
          Intrinsics.throwNpe(); 
        object.createNotificationChannel(notificationChannel);
      } 
      return;
    } 
    throw new TypeCastException("null cannot be cast to non-null type android.app.NotificationManager");
  }
  
  private final void setupScanCycles() {
    CommandHandler commandHandler = this.commandHandler;
    if (commandHandler == null)
      Intrinsics.throwUninitializedPropertyAccessException("commandHandler"); 
    commandHandler.scheduleNextScan(0L);
  }
  
  private final void setupScanner() {
    StreetPassScanner streetPassScanner = this.streetPassScanner;
    if (streetPassScanner == null) {
      Context context = (Context)this;
      String str = this.serviceUUID;
      if (str == null)
        Intrinsics.throwUninitializedPropertyAccessException("serviceUUID"); 
      streetPassScanner = new StreetPassScanner(context, str, scanDuration);
    } 
    this.streetPassScanner = streetPassScanner;
  }
  
  private final void setupService() {
    StreetPassServer streetPassServer = this.streetPassServer;
    if (streetPassServer == null) {
      Context context = getApplicationContext();
      Intrinsics.checkExpressionValueIsNotNull(context, "this.applicationContext");
      String str = this.serviceUUID;
      if (str == null)
        Intrinsics.throwUninitializedPropertyAccessException("serviceUUID"); 
      streetPassServer = new StreetPassServer(context, str);
    } 
    this.streetPassServer = streetPassServer;
    setupScanner();
    setupAdvertiser();
  }
  
  private final void startScan() {
    if (isBluetoothEnabled()) {
      StreetPassScanner streetPassScanner = this.streetPassScanner;
      if (streetPassScanner != null)
        if (!streetPassScanner.isScanning()) {
          streetPassScanner.startScan();
        } else {
          CentralLog.Companion.e(TAG, "Already scanning!");
        }  
    } else {
      CentralLog.Companion.w(TAG, "Unable to start scan - bluetooth is off");
    } 
  }
  
  private final void stopService() {
    teardown();
    unregisterReceivers();
    StreetPassWorker streetPassWorker = this.worker;
    if (streetPassWorker != null)
      streetPassWorker.terminateConnections(); 
    streetPassWorker = this.worker;
    if (streetPassWorker != null)
      streetPassWorker.unregisterReceivers(); 
    Job.DefaultImpls.cancel$default(this.job, null, 1, null);
  }
  
  private final void unregisterReceivers() {
    try {
      LocalBroadcastManager localBroadcastManager = this.localBroadcastManager;
      if (localBroadcastManager == null)
        Intrinsics.throwUninitializedPropertyAccessException("localBroadcastManager"); 
    } finally {
      Exception exception = null;
    } 
    try {
      LocalBroadcastManager localBroadcastManager = this.localBroadcastManager;
      if (localBroadcastManager == null)
        Intrinsics.throwUninitializedPropertyAccessException("localBroadcastManager"); 
    } finally {
      Exception exception = null;
    } 
    try {
      unregisterReceiver(this.bluetoothStatusReceiver);
    } finally {
      Exception exception = null;
    } 
  }
  
  public final void actionUpdateBm() {
    TempIDManager tempIDManager = TempIDManager.INSTANCE;
    Context context = getApplicationContext();
    Intrinsics.checkExpressionValueIsNotNull(context, "this.applicationContext");
    if (tempIDManager.needToUpdate(context) || broadcastMessage == null) {
      CentralLog.Companion.i(TAG, "[TempID] Need to update TemporaryID in actionUpdateBM");
      TempIDManager.INSTANCE.getTemporaryIDs((Context)this);
      TempIDManager tempIDManager1 = TempIDManager.INSTANCE;
      Context context1 = getApplicationContext();
      Intrinsics.checkExpressionValueIsNotNull(context1, "this.applicationContext");
      TemporaryID temporaryID = tempIDManager1.retrieveTemporaryID(context1);
      if (temporaryID != null) {
        CentralLog.Companion.i(TAG, "[TempID] Updated Temp ID");
        broadcastMessage = temporaryID;
      } 
      if (temporaryID == null)
        CentralLog.Companion.e(TAG, "[TempID] Failed to fetch new Temp ID"); 
      return;
    } 
    CentralLog.Companion.i(TAG, "[TempID] Don't need to update Temp ID in actionUpdateBM");
  }
  
  public final long calcPhaseShift(long paramLong1, long paramLong2) {
    return (long)(paramLong1 + Math.random() * (paramLong2 - paramLong1));
  }
  
  public CoroutineContext getCoroutineContext() {
    return Dispatchers.getMain().plus((CoroutineContext)this.job);
  }
  
  public final StreetPassWorker getWorker() {
    return this.worker;
  }
  
  public IBinder onBind(Intent paramIntent) {
    return null;
  }
  
  public void onCreate() {
    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance((Context)this);
    Intrinsics.checkExpressionValueIsNotNull(localBroadcastManager, "LocalBroadcastManager.getInstance(this)");
    this.localBroadcastManager = localBroadcastManager;
    setup();
  }
  
  public void onDestroy() {
    super.onDestroy();
    CentralLog.Companion.i(TAG, "BluetoothMonitoringService destroyed - tearing down");
    stopService();
    CentralLog.Companion.i(TAG, "BluetoothMonitoringService destroyed");
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
    String str;
    CentralLog.Companion.i(TAG, "Service onStartCommand");
    if (!hasLocationPermissions() || !isBluetoothEnabled()) {
      CentralLog.Companion companion = CentralLog.Companion;
      str = TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("location permission: ");
      stringBuilder.append(hasLocationPermissions());
      stringBuilder.append(" bluetooth: ");
      stringBuilder.append(isBluetoothEnabled());
      companion.i(str, stringBuilder.toString());
      notifyLackingThings$default(this, false, 1, (Object)null);
      return 1;
    } 
    if (str != null) {
      paramInt1 = str.getIntExtra(COMMAND_KEY, Command.INVALID.getIndex());
      runService(Command.Companion.findByValue(paramInt1));
      return 1;
    } 
    CentralLog.Companion.e(TAG, "WTF? Nothing in intent @ onStartCommand");
    CommandHandler commandHandler = this.commandHandler;
    if (commandHandler == null)
      Intrinsics.throwUninitializedPropertyAccessException("commandHandler"); 
    commandHandler.startBluetoothMonitoringService();
    return 1;
  }
  
  public final void runService(Command paramCommand) {
    StringBuilder stringBuilder1;
    Context context;
    String str3;
    CentralLog.Companion companion1 = CentralLog.Companion;
    String str1 = TAG;
    StringBuilder stringBuilder3 = new StringBuilder();
    stringBuilder3.append("Command is:");
    if (paramCommand != null) {
      str3 = paramCommand.getString();
    } else {
      str3 = null;
    } 
    stringBuilder3.append(str3);
    companion1.i(str1, stringBuilder3.toString());
    if (!hasLocationPermissions() || !isBluetoothEnabled()) {
      CentralLog.Companion companion = CentralLog.Companion;
      String str = TAG;
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("location permission: ");
      stringBuilder1.append(hasLocationPermissions());
      stringBuilder1.append(" bluetooth: ");
      stringBuilder1.append(isBluetoothEnabled());
      companion.i(str, stringBuilder1.toString());
      notifyLackingThings$default(this, false, 1, (Object)null);
      return;
    } 
    notifyRunning$default(this, false, 1, (Object)null);
    if (stringBuilder1 != null) {
      Utils utils1;
      Context context1;
      Utils utils2;
      switch (BluetoothMonitoringService$WhenMappings.$EnumSwitchMapping$0[stringBuilder1.ordinal()]) {
        case 7:
          actionPurge();
          return;
        case 6:
          utils1 = Utils.INSTANCE;
          context1 = getApplicationContext();
          Intrinsics.checkExpressionValueIsNotNull(context1, "this.applicationContext");
          utils1.scheduleNextHealthCheck(context1, healthCheckInterval);
          actionHealthCheck();
          return;
        case 5:
          actionStop();
          return;
        case 4:
          utils1 = Utils.INSTANCE;
          context1 = getApplicationContext();
          Intrinsics.checkExpressionValueIsNotNull(context1, "this.applicationContext");
          utils1.scheduleBMUpdateCheck(context1, bmCheckInterval);
          actionUpdateBm();
          return;
        case 3:
          scheduleAdvertisement();
          actionAdvertise();
          return;
        case 2:
          scheduleScan();
          actionScan();
          return;
        case 1:
          setupService();
          utils2 = Utils.INSTANCE;
          context = getApplicationContext();
          Intrinsics.checkExpressionValueIsNotNull(context, "this.applicationContext");
          utils2.scheduleNextHealthCheck(context, healthCheckInterval);
          utils2 = Utils.INSTANCE;
          context = getApplicationContext();
          Intrinsics.checkExpressionValueIsNotNull(context, "this.applicationContext");
          utils2.scheduleRepeatingPurge(context, purgeInterval);
          utils2 = Utils.INSTANCE;
          context = getApplicationContext();
          Intrinsics.checkExpressionValueIsNotNull(context, "this.applicationContext");
          utils2.scheduleBMUpdateCheck(context, bmCheckInterval);
          actionStart();
          return;
      } 
    } 
    CentralLog.Companion companion2 = CentralLog.Companion;
    String str2 = TAG;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("Invalid / ignored command: ");
    stringBuilder2.append(context);
    stringBuilder2.append(". Nothing to do");
    companion2.i(str2, stringBuilder2.toString());
  }
  
  public final void setWorker(StreetPassWorker paramStreetPassWorker) {
    this.worker = paramStreetPassWorker;
  }
  
  public final void teardown() {
    StreetPassServer streetPassServer = this.streetPassServer;
    if (streetPassServer != null)
      streetPassServer.tearDown(); 
    this.streetPassServer = (StreetPassServer)null;
    StreetPassScanner streetPassScanner = this.streetPassScanner;
    if (streetPassScanner != null)
      streetPassScanner.stopScan(); 
    this.streetPassScanner = (StreetPassScanner)null;
    CommandHandler commandHandler = this.commandHandler;
    if (commandHandler == null)
      Intrinsics.throwUninitializedPropertyAccessException("commandHandler"); 
    commandHandler.removeCallbacksAndMessages(null);
    Utils utils = Utils.INSTANCE;
    Context context = getApplicationContext();
    Intrinsics.checkExpressionValueIsNotNull(context, "this.applicationContext");
    utils.cancelBMUpdateCheck(context);
    utils = Utils.INSTANCE;
    context = getApplicationContext();
    Intrinsics.checkExpressionValueIsNotNull(context, "this.applicationContext");
    utils.cancelNextScan(context);
    utils = Utils.INSTANCE;
    context = getApplicationContext();
    Intrinsics.checkExpressionValueIsNotNull(context, "this.applicationContext");
    utils.cancelNextAdvertise(context);
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\036\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\b\004\030\0002\0020\001B\005¢\006\002\020\002J\034\020\003\032\0020\0042\b\020\005\032\004\030\0010\0062\b\020\007\032\004\030\0010\bH\026¨\006\t"}, d2 = {"Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$BluetoothStatusReceiver;", "Landroid/content/BroadcastReceiver;", "(Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService;)V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 1, 16})
  public final class BluetoothStatusReceiver extends BroadcastReceiver {
    public void onReceive(Context param1Context, Intent param1Intent) {
      if (param1Intent != null && Intrinsics.areEqual(param1Intent.getAction(), "android.bluetooth.adapter.action.STATE_CHANGED")) {
        Utils utils;
        switch (param1Intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1)) {
          default:
            return;
          case 13:
            CentralLog.Companion.d(BluetoothMonitoringService.TAG, "BluetoothAdapter.STATE_TURNING_OFF");
            BluetoothMonitoringService.notifyLackingThings$default(BluetoothMonitoringService.this, false, 1, (Object)null);
            BluetoothMonitoringService.this.teardown();
          case 12:
            CentralLog.Companion.d(BluetoothMonitoringService.TAG, "BluetoothAdapter.STATE_ON");
            utils = Utils.INSTANCE;
            param1Context = BluetoothMonitoringService.this.getApplicationContext();
            Intrinsics.checkExpressionValueIsNotNull(param1Context, "this@BluetoothMonitoringService.applicationContext");
            utils.startBluetoothMonitoringService(param1Context);
          case 11:
            CentralLog.Companion.d(BluetoothMonitoringService.TAG, "BluetoothAdapter.STATE_TURNING_ON");
          case 10:
            break;
        } 
        CentralLog.Companion.d(BluetoothMonitoringService.TAG, "BluetoothAdapter.STATE_OFF");
      } 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\030\n\002\030\002\n\002\020\020\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\017\b\001\030\000 \0232\b\022\004\022\0020\0000\001:\001\023B\027\b\002\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\002\020\006R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\007\020\bR\021\020\004\032\0020\005¢\006\b\n\000\032\004\b\t\020\nj\002\b\013j\002\b\fj\002\b\rj\002\b\016j\002\b\017j\002\b\020j\002\b\021j\002\b\022¨\006\024"}, d2 = {"Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Command;", "", "index", "", "string", "", "(Ljava/lang/String;IILjava/lang/String;)V", "getIndex", "()I", "getString", "()Ljava/lang/String;", "INVALID", "ACTION_START", "ACTION_SCAN", "ACTION_STOP", "ACTION_ADVERTISE", "ACTION_SELF_CHECK", "ACTION_UPDATE_BM", "ACTION_PURGE", "Companion", "app_release"}, k = 1, mv = {1, 1, 16})
  public enum Command {
    ACTION_ADVERTISE, ACTION_PURGE, ACTION_SCAN, ACTION_SELF_CHECK, ACTION_START, ACTION_STOP, ACTION_UPDATE_BM, INVALID;
    
    public static final Companion Companion;
    
    private static final Map<Integer, Command> types;
    
    private final int index;
    
    private final String string;
    
    static {
      byte b = 0;
      Command command1 = new Command("INVALID", 0, -1, "INVALID");
      INVALID = command1;
      Command command2 = new Command("ACTION_START", 1, 0, "START");
      ACTION_START = command2;
      Command command3 = new Command("ACTION_SCAN", 2, 1, "SCAN");
      ACTION_SCAN = command3;
      Command command4 = new Command("ACTION_STOP", 3, 2, "STOP");
      ACTION_STOP = command4;
      Command command5 = new Command("ACTION_ADVERTISE", 4, 3, "ADVERTISE");
      ACTION_ADVERTISE = command5;
      Command command6 = new Command("ACTION_SELF_CHECK", 5, 4, "SELF_CHECK");
      ACTION_SELF_CHECK = command6;
      Command command7 = new Command("ACTION_UPDATE_BM", 6, 5, "UPDATE_BM");
      ACTION_UPDATE_BM = command7;
      Command command8 = new Command("ACTION_PURGE", 7, 6, "PURGE");
      ACTION_PURGE = command8;
      $VALUES = new Command[] { command1, command2, command3, command4, command5, command6, command7, command8 };
      Companion = new Companion(null);
      Command[] arrayOfCommand = values();
      LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>(RangesKt.coerceAtLeast(MapsKt.mapCapacity(arrayOfCommand.length), 16));
      int i = arrayOfCommand.length;
      while (b < i) {
        command5 = arrayOfCommand[b];
        Pair pair = TuplesKt.to(Integer.valueOf(command5.index), command5);
        linkedHashMap.put(pair.getFirst(), pair.getSecond());
        b++;
      } 
      types = (Map)linkedHashMap;
    }
    
    Command(int param1Int1, String param1String1) {
      this.index = param1Int1;
      this.string = param1String1;
    }
    
    public final int getIndex() {
      return this.index;
    }
    
    public final String getString() {
      return this.string;
    }
    
    @Metadata(bv = {1, 0, 3}, d1 = {"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020$\n\002\020\b\n\002\030\002\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\020\020\007\032\004\030\0010\0062\006\020\b\032\0020\005R\032\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0060\004X\004¢\006\002\n\000¨\006\t"}, d2 = {"Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Command$Companion;", "", "()V", "types", "", "", "Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Command;", "findByValue", "value", "app_release"}, k = 1, mv = {1, 1, 16})
    public static final class Companion {
      private Companion() {}
      
      public final BluetoothMonitoringService.Command findByValue(int param2Int) {
        return (BluetoothMonitoringService.Command)BluetoothMonitoringService.Command.types.get(Integer.valueOf(param2Int));
      }
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020$\n\002\020\b\n\002\030\002\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\020\020\007\032\004\030\0010\0062\006\020\b\032\0020\005R\032\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0060\004X\004¢\006\002\n\000¨\006\t"}, d2 = {"Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Command$Companion;", "", "()V", "types", "", "", "Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Command;", "findByValue", "value", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class Companion {
    private Companion() {}
    
    public final BluetoothMonitoringService.Command findByValue(int param1Int) {
      return (BluetoothMonitoringService.Command)BluetoothMonitoringService.Command.types.get(Integer.valueOf(param1Int));
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\0004\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\006\n\002\020\b\n\002\b\025\n\002\020\t\n\002\b\t\n\002\030\002\n\002\b\t\n\002\020\013\n\002\b\023\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XD¢\006\002\n\000R\024\020\005\032\0020\004XD¢\006\b\n\000\032\004\b\006\020\007R\024\020\b\032\0020\004XD¢\006\b\n\000\032\004\b\t\020\007R\016\020\n\032\0020\013XD¢\006\002\n\000R\024\020\f\032\0020\013XD¢\006\b\n\000\032\004\b\r\020\016R\024\020\017\032\0020\013XD¢\006\b\n\000\032\004\b\020\020\016R\024\020\021\032\0020\013XD¢\006\b\n\000\032\004\b\022\020\016R\024\020\023\032\0020\013XD¢\006\b\n\000\032\004\b\024\020\016R\024\020\025\032\0020\013XD¢\006\b\n\000\032\004\b\026\020\016R\024\020\027\032\0020\013XD¢\006\b\n\000\032\004\b\030\020\016R\024\020\031\032\0020\013XD¢\006\b\n\000\032\004\b\032\020\016R\024\020\033\032\0020\013XD¢\006\b\n\000\032\004\b\034\020\016R\024\020\035\032\0020\013XD¢\006\b\n\000\032\004\b\036\020\016R\016\020\037\032\0020\004XD¢\006\002\n\000R\024\020 \032\0020!XD¢\006\b\n\000\032\004\b\"\020#R\024\020$\032\0020!XD¢\006\b\n\000\032\004\b%\020#R\024\020&\032\0020!XD¢\006\b\n\000\032\004\b'\020#R\024\020(\032\0020!XD¢\006\b\n\000\032\004\b)\020#R\034\020*\032\004\030\0010+X\016¢\006\016\n\000\032\004\b,\020-\"\004\b.\020/R\024\0200\032\0020!XD¢\006\b\n\000\032\004\b1\020#R\024\0202\032\0020!XD¢\006\b\n\000\032\004\b3\020#R\024\0204\032\00205XD¢\006\b\n\000\032\004\b6\0207R\024\0208\032\00205XD¢\006\b\n\000\032\004\b9\0207R\024\020:\032\0020!XD¢\006\b\n\000\032\004\b;\020#R\024\020<\032\0020!XD¢\006\b\n\000\032\004\b=\020#R\024\020>\032\0020!XD¢\006\b\n\000\032\004\b?\020#R\024\020@\032\0020!XD¢\006\b\n\000\032\004\bA\020#R\024\020B\032\0020!XD¢\006\b\n\000\032\004\bC\020#R\024\020D\032\0020!XD¢\006\b\n\000\032\004\bE\020#R\024\020F\032\00205XD¢\006\b\n\000\032\004\bG\0207¨\006H"}, d2 = {"Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Companion;", "", "()V", "CHANNEL_ID", "", "CHANNEL_SERVICE", "getCHANNEL_SERVICE", "()Ljava/lang/String;", "COMMAND_KEY", "getCOMMAND_KEY", "NOTIFICATION_ID", "", "PENDING_ACTIVITY", "getPENDING_ACTIVITY", "()I", "PENDING_ADVERTISE_REQ_CODE", "getPENDING_ADVERTISE_REQ_CODE", "PENDING_BM_UPDATE", "getPENDING_BM_UPDATE", "PENDING_HEALTH_CHECK_CODE", "getPENDING_HEALTH_CHECK_CODE", "PENDING_PURGE_CODE", "getPENDING_PURGE_CODE", "PENDING_SCAN_REQ_CODE", "getPENDING_SCAN_REQ_CODE", "PENDING_START", "getPENDING_START", "PENDING_WIZARD_REQ_CODE", "getPENDING_WIZARD_REQ_CODE", "PUSH_NOTIFICATION_ID", "getPUSH_NOTIFICATION_ID", "TAG", "advertisingDuration", "", "getAdvertisingDuration", "()J", "advertisingGap", "getAdvertisingGap", "blacklistDuration", "getBlacklistDuration", "bmCheckInterval", "getBmCheckInterval", "broadcastMessage", "Lca/albertahealthservices/contacttracing/idmanager/TemporaryID;", "getBroadcastMessage", "()Lca/albertahealthservices/contacttracing/idmanager/TemporaryID;", "setBroadcastMessage", "(Lca/albertahealthservices/contacttracing/idmanager/TemporaryID;)V", "connectionTimeout", "getConnectionTimeout", "healthCheckInterval", "getHealthCheckInterval", "infiniteAdvertising", "", "getInfiniteAdvertising", "()Z", "infiniteScanning", "getInfiniteScanning", "maxQueueTime", "getMaxQueueTime", "maxScanInterval", "getMaxScanInterval", "minScanInterval", "getMinScanInterval", "purgeInterval", "getPurgeInterval", "purgeTTL", "getPurgeTTL", "scanDuration", "getScanDuration", "useBlacklist", "getUseBlacklist", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class Companion {
    private Companion() {}
    
    public final long getAdvertisingDuration() {
      return BluetoothMonitoringService.advertisingDuration;
    }
    
    public final long getAdvertisingGap() {
      return BluetoothMonitoringService.advertisingGap;
    }
    
    public final long getBlacklistDuration() {
      return BluetoothMonitoringService.blacklistDuration;
    }
    
    public final long getBmCheckInterval() {
      return BluetoothMonitoringService.bmCheckInterval;
    }
    
    public final TemporaryID getBroadcastMessage() {
      return BluetoothMonitoringService.broadcastMessage;
    }
    
    public final String getCHANNEL_SERVICE() {
      return BluetoothMonitoringService.CHANNEL_SERVICE;
    }
    
    public final String getCOMMAND_KEY() {
      return BluetoothMonitoringService.COMMAND_KEY;
    }
    
    public final long getConnectionTimeout() {
      return BluetoothMonitoringService.connectionTimeout;
    }
    
    public final long getHealthCheckInterval() {
      return BluetoothMonitoringService.healthCheckInterval;
    }
    
    public final boolean getInfiniteAdvertising() {
      return BluetoothMonitoringService.infiniteAdvertising;
    }
    
    public final boolean getInfiniteScanning() {
      return BluetoothMonitoringService.infiniteScanning;
    }
    
    public final long getMaxQueueTime() {
      return BluetoothMonitoringService.maxQueueTime;
    }
    
    public final long getMaxScanInterval() {
      return BluetoothMonitoringService.maxScanInterval;
    }
    
    public final long getMinScanInterval() {
      return BluetoothMonitoringService.minScanInterval;
    }
    
    public final int getPENDING_ACTIVITY() {
      return BluetoothMonitoringService.PENDING_ACTIVITY;
    }
    
    public final int getPENDING_ADVERTISE_REQ_CODE() {
      return BluetoothMonitoringService.PENDING_ADVERTISE_REQ_CODE;
    }
    
    public final int getPENDING_BM_UPDATE() {
      return BluetoothMonitoringService.PENDING_BM_UPDATE;
    }
    
    public final int getPENDING_HEALTH_CHECK_CODE() {
      return BluetoothMonitoringService.PENDING_HEALTH_CHECK_CODE;
    }
    
    public final int getPENDING_PURGE_CODE() {
      return BluetoothMonitoringService.PENDING_PURGE_CODE;
    }
    
    public final int getPENDING_SCAN_REQ_CODE() {
      return BluetoothMonitoringService.PENDING_SCAN_REQ_CODE;
    }
    
    public final int getPENDING_START() {
      return BluetoothMonitoringService.PENDING_START;
    }
    
    public final int getPENDING_WIZARD_REQ_CODE() {
      return BluetoothMonitoringService.PENDING_WIZARD_REQ_CODE;
    }
    
    public final int getPUSH_NOTIFICATION_ID() {
      return BluetoothMonitoringService.PUSH_NOTIFICATION_ID;
    }
    
    public final long getPurgeInterval() {
      return BluetoothMonitoringService.purgeInterval;
    }
    
    public final long getPurgeTTL() {
      return BluetoothMonitoringService.purgeTTL;
    }
    
    public final long getScanDuration() {
      return BluetoothMonitoringService.scanDuration;
    }
    
    public final boolean getUseBlacklist() {
      return BluetoothMonitoringService.useBlacklist;
    }
    
    public final void setBroadcastMessage(TemporaryID param1TemporaryID) {
      BluetoothMonitoringService.broadcastMessage = param1TemporaryID;
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\f\n\002\030\002\n\002\020\020\n\002\b\004\b\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002¢\006\002\020\002j\002\b\003j\002\b\004¨\006\005"}, d2 = {"Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$NOTIFICATION_STATE;", "", "(Ljava/lang/String;I)V", "RUNNING", "LACKING_THINGS", "app_release"}, k = 1, mv = {1, 1, 16})
  public enum NOTIFICATION_STATE {
    LACKING_THINGS, RUNNING;
    
    static {
      NOTIFICATION_STATE nOTIFICATION_STATE1 = new NOTIFICATION_STATE("RUNNING", 0);
      RUNNING = nOTIFICATION_STATE1;
      NOTIFICATION_STATE nOTIFICATION_STATE2 = new NOTIFICATION_STATE("LACKING_THINGS", 1);
      LACKING_THINGS = nOTIFICATION_STATE2;
      $VALUES = new NOTIFICATION_STATE[] { nOTIFICATION_STATE1, nOTIFICATION_STATE2 };
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000$\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\b\004\030\0002\0020\001B\005¢\006\002\020\002J\030\020\005\032\0020\0062\006\020\007\032\0020\b2\006\020\t\032\0020\nH\026R\016\020\003\032\0020\004XD¢\006\002\n\000¨\006\013"}, d2 = {"Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$StatusReceiver;", "Landroid/content/BroadcastReceiver;", "(Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService;)V", "TAG", "", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 1, 16})
  public final class StatusReceiver extends BroadcastReceiver {
    private final String TAG = "StatusReceiver";
    
    public void onReceive(Context param1Context, Intent param1Intent) {
      Intrinsics.checkParameterIsNotNull(param1Context, "context");
      Intrinsics.checkParameterIsNotNull(param1Intent, "intent");
      if (Intrinsics.areEqual("ca.albertahealthservices.contacttracing.ACTION_RECEIVED_STATUS", param1Intent.getAction())) {
        boolean bool;
        Parcelable parcelable = param1Intent.getParcelableExtra("ca.albertahealthservices.contacttracing.STATUS");
        Intrinsics.checkExpressionValueIsNotNull(parcelable, "intent.getParcelableExtra(STATUS)");
        Status status = (Status)parcelable;
        CentralLog.Companion companion = CentralLog.Companion;
        String str = this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Status received: ");
        stringBuilder.append(status.getMsg());
        companion.d(str, stringBuilder.toString());
        if (((CharSequence)status.getMsg()).length() > 0) {
          bool = true;
        } else {
          bool = false;
        } 
        if (bool) {
          StatusRecord statusRecord = new StatusRecord(status.getMsg());
          BuildersKt.launch$default(BluetoothMonitoringService.this, null, null, new BluetoothMonitoringService$StatusReceiver$onReceive$1(statusRecord, null), 3, null);
        } 
      } 
    }
    
    @Metadata(bv = {1, 0, 3}, d1 = {"\000\016\n\000\n\002\020\002\n\002\030\002\n\002\b\002\020\000\032\0020\001*\0020\002H@¢\006\004\b\003\020\004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 16})
    @DebugMetadata(c = "ca.albertahealthservices.contacttracing.services.BluetoothMonitoringService$StatusReceiver$onReceive$1", f = "BluetoothMonitoringService.kt", i = {0}, l = {664}, m = "invokeSuspend", n = {"$this$launch"}, s = {"L$0"})
    static final class BluetoothMonitoringService$StatusReceiver$onReceive$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
      Object L$0;
      
      int label;
      
      private CoroutineScope p$;
      
      BluetoothMonitoringService$StatusReceiver$onReceive$1(StatusRecord param2StatusRecord, Continuation param2Continuation) {
        super(2, param2Continuation);
      }
      
      public final Continuation<Unit> create(Object param2Object, Continuation<?> param2Continuation) {
        Intrinsics.checkParameterIsNotNull(param2Continuation, "completion");
        BluetoothMonitoringService$StatusReceiver$onReceive$1 bluetoothMonitoringService$StatusReceiver$onReceive$1 = new BluetoothMonitoringService$StatusReceiver$onReceive$1(this.$statusRecord, param2Continuation);
        bluetoothMonitoringService$StatusReceiver$onReceive$1.p$ = (CoroutineScope)param2Object;
        return (Continuation<Unit>)bluetoothMonitoringService$StatusReceiver$onReceive$1;
      }
      
      public final Object invoke(Object param2Object1, Object param2Object2) {
        return ((BluetoothMonitoringService$StatusReceiver$onReceive$1)create(param2Object1, (Continuation)param2Object2)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object param2Object) {
        Object object = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i != 0) {
          if (i == 1) {
            object = this.L$0;
            ResultKt.throwOnFailure(param2Object);
          } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
          } 
        } else {
          ResultKt.throwOnFailure(param2Object);
          param2Object = this.p$;
          StatusRecordStorage statusRecordStorage = BluetoothMonitoringService.access$getStatusRecordStorage$p(BluetoothMonitoringService.this);
          StatusRecord statusRecord = this.$statusRecord;
          this.L$0 = param2Object;
          this.label = 1;
          if (statusRecordStorage.saveRecord(statusRecord, (Continuation)this) == object)
            return object; 
        } 
        return Unit.INSTANCE;
      }
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\016\n\000\n\002\020\002\n\002\030\002\n\002\b\002\020\000\032\0020\001*\0020\002H@¢\006\004\b\003\020\004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 16})
  @DebugMetadata(c = "ca.albertahealthservices.contacttracing.services.BluetoothMonitoringService$StatusReceiver$onReceive$1", f = "BluetoothMonitoringService.kt", i = {0}, l = {664}, m = "invokeSuspend", n = {"$this$launch"}, s = {"L$0"})
  static final class BluetoothMonitoringService$StatusReceiver$onReceive$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    Object L$0;
    
    int label;
    
    private CoroutineScope p$;
    
    BluetoothMonitoringService$StatusReceiver$onReceive$1(StatusRecord param1StatusRecord, Continuation param1Continuation) {
      super(2, param1Continuation);
    }
    
    public final Continuation<Unit> create(Object param1Object, Continuation<?> param1Continuation) {
      Intrinsics.checkParameterIsNotNull(param1Continuation, "completion");
      BluetoothMonitoringService$StatusReceiver$onReceive$1 bluetoothMonitoringService$StatusReceiver$onReceive$1 = new BluetoothMonitoringService$StatusReceiver$onReceive$1(this.$statusRecord, param1Continuation);
      bluetoothMonitoringService$StatusReceiver$onReceive$1.p$ = (CoroutineScope)param1Object;
      return (Continuation<Unit>)bluetoothMonitoringService$StatusReceiver$onReceive$1;
    }
    
    public final Object invoke(Object param1Object1, Object param1Object2) {
      return ((BluetoothMonitoringService$StatusReceiver$onReceive$1)create(param1Object1, (Continuation)param1Object2)).invokeSuspend(Unit.INSTANCE);
    }
    
    public final Object invokeSuspend(Object param1Object) {
      Object object = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      int i = this.label;
      if (i != 0) {
        if (i == 1) {
          object = this.L$0;
          ResultKt.throwOnFailure(param1Object);
        } else {
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } 
      } else {
        ResultKt.throwOnFailure(param1Object);
        param1Object = this.p$;
        StatusRecordStorage statusRecordStorage = BluetoothMonitoringService.access$getStatusRecordStorage$p(BluetoothMonitoringService.this);
        StatusRecord statusRecord = this.$statusRecord;
        this.L$0 = param1Object;
        this.label = 1;
        if (statusRecordStorage.saveRecord(statusRecord, (Continuation)this) == object)
          return object; 
      } 
      return Unit.INSTANCE;
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000$\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\b\004\030\0002\0020\001B\005¢\006\002\020\002J\030\020\005\032\0020\0062\006\020\007\032\0020\b2\006\020\t\032\0020\nH\026R\016\020\003\032\0020\004XD¢\006\002\n\000¨\006\013"}, d2 = {"Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$StreetPassReceiver;", "Landroid/content/BroadcastReceiver;", "(Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService;)V", "TAG", "", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 1, 16})
  public final class StreetPassReceiver extends BroadcastReceiver {
    private final String TAG = "StreetPassReceiver";
    
    public void onReceive(Context param1Context, Intent param1Intent) {
      Intrinsics.checkParameterIsNotNull(param1Context, "context");
      Intrinsics.checkParameterIsNotNull(param1Intent, "intent");
      if (Intrinsics.areEqual("ca.albertahealthservices.contacttracing.ACTION_RECEIVED_STREETPASS", param1Intent.getAction())) {
        boolean bool;
        Parcelable parcelable = param1Intent.getParcelableExtra("ca.albertahealthservices.contacttracing.STREET_PASS");
        Intrinsics.checkExpressionValueIsNotNull(parcelable, "intent.getParcelableExtra(STREET_PASS)");
        ConnectionRecord connectionRecord = (ConnectionRecord)parcelable;
        CentralLog.Companion companion = CentralLog.Companion;
        String str = this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("StreetPass received: ");
        stringBuilder.append(connectionRecord);
        companion.d(str, stringBuilder.toString());
        if (((CharSequence)connectionRecord.getMsg()).length() > 0) {
          bool = true;
        } else {
          bool = false;
        } 
        if (bool) {
          StreetPassRecord streetPassRecord = new StreetPassRecord(connectionRecord.getVersion(), connectionRecord.getMsg(), connectionRecord.getOrg(), connectionRecord.getPeripheral().getModelP(), connectionRecord.getCentral().getModelC(), connectionRecord.getRssi(), connectionRecord.getTxPower());
          BuildersKt.launch$default(BluetoothMonitoringService.this, null, null, new BluetoothMonitoringService$StreetPassReceiver$onReceive$1(streetPassRecord, null), 3, null);
        } 
      } 
    }
    
    @Metadata(bv = {1, 0, 3}, d1 = {"\000\016\n\000\n\002\020\002\n\002\030\002\n\002\b\002\020\000\032\0020\001*\0020\002H@¢\006\004\b\003\020\004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 16})
    @DebugMetadata(c = "ca.albertahealthservices.contacttracing.services.BluetoothMonitoringService$StreetPassReceiver$onReceive$1", f = "BluetoothMonitoringService.kt", i = {0}, l = {645}, m = "invokeSuspend", n = {"$this$launch"}, s = {"L$0"})
    static final class BluetoothMonitoringService$StreetPassReceiver$onReceive$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
      Object L$0;
      
      int label;
      
      private CoroutineScope p$;
      
      BluetoothMonitoringService$StreetPassReceiver$onReceive$1(StreetPassRecord param2StreetPassRecord, Continuation param2Continuation) {
        super(2, param2Continuation);
      }
      
      public final Continuation<Unit> create(Object param2Object, Continuation<?> param2Continuation) {
        Intrinsics.checkParameterIsNotNull(param2Continuation, "completion");
        BluetoothMonitoringService$StreetPassReceiver$onReceive$1 bluetoothMonitoringService$StreetPassReceiver$onReceive$1 = new BluetoothMonitoringService$StreetPassReceiver$onReceive$1(this.$record, param2Continuation);
        bluetoothMonitoringService$StreetPassReceiver$onReceive$1.p$ = (CoroutineScope)param2Object;
        return (Continuation<Unit>)bluetoothMonitoringService$StreetPassReceiver$onReceive$1;
      }
      
      public final Object invoke(Object param2Object1, Object param2Object2) {
        return ((BluetoothMonitoringService$StreetPassReceiver$onReceive$1)create(param2Object1, (Continuation)param2Object2)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object param2Object) {
        Object object = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i != 0) {
          if (i == 1) {
            object = this.L$0;
            ResultKt.throwOnFailure(param2Object);
          } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
          } 
        } else {
          ResultKt.throwOnFailure(param2Object);
          param2Object = this.p$;
          CentralLog.Companion companion = CentralLog.Companion;
          String str = BluetoothMonitoringService.StreetPassReceiver.this.TAG;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Coroutine - Saving StreetPassRecord: ");
          stringBuilder.append(Utils.INSTANCE.getDate(this.$record.getTimestamp()));
          companion.d(str, stringBuilder.toString());
          StreetPassRecordStorage streetPassRecordStorage = BluetoothMonitoringService.access$getStreetPassRecordStorage$p(BluetoothMonitoringService.this);
          StreetPassRecord streetPassRecord = this.$record;
          this.L$0 = param2Object;
          this.label = 1;
          if (streetPassRecordStorage.saveRecord(streetPassRecord, (Continuation)this) == object)
            return object; 
        } 
        return Unit.INSTANCE;
      }
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\016\n\000\n\002\020\002\n\002\030\002\n\002\b\002\020\000\032\0020\001*\0020\002H@¢\006\004\b\003\020\004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 16})
  @DebugMetadata(c = "ca.albertahealthservices.contacttracing.services.BluetoothMonitoringService$StreetPassReceiver$onReceive$1", f = "BluetoothMonitoringService.kt", i = {0}, l = {645}, m = "invokeSuspend", n = {"$this$launch"}, s = {"L$0"})
  static final class BluetoothMonitoringService$StreetPassReceiver$onReceive$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    Object L$0;
    
    int label;
    
    private CoroutineScope p$;
    
    BluetoothMonitoringService$StreetPassReceiver$onReceive$1(StreetPassRecord param1StreetPassRecord, Continuation param1Continuation) {
      super(2, param1Continuation);
    }
    
    public final Continuation<Unit> create(Object param1Object, Continuation<?> param1Continuation) {
      Intrinsics.checkParameterIsNotNull(param1Continuation, "completion");
      BluetoothMonitoringService$StreetPassReceiver$onReceive$1 bluetoothMonitoringService$StreetPassReceiver$onReceive$1 = new BluetoothMonitoringService$StreetPassReceiver$onReceive$1(this.$record, param1Continuation);
      bluetoothMonitoringService$StreetPassReceiver$onReceive$1.p$ = (CoroutineScope)param1Object;
      return (Continuation<Unit>)bluetoothMonitoringService$StreetPassReceiver$onReceive$1;
    }
    
    public final Object invoke(Object param1Object1, Object param1Object2) {
      return ((BluetoothMonitoringService$StreetPassReceiver$onReceive$1)create(param1Object1, (Continuation)param1Object2)).invokeSuspend(Unit.INSTANCE);
    }
    
    public final Object invokeSuspend(Object param1Object) {
      Object object = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      int i = this.label;
      if (i != 0) {
        if (i == 1) {
          object = this.L$0;
          ResultKt.throwOnFailure(param1Object);
        } else {
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } 
      } else {
        ResultKt.throwOnFailure(param1Object);
        param1Object = this.p$;
        CentralLog.Companion companion = CentralLog.Companion;
        String str = BluetoothMonitoringService.StreetPassReceiver.this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Coroutine - Saving StreetPassRecord: ");
        stringBuilder.append(Utils.INSTANCE.getDate(this.$record.getTimestamp()));
        companion.d(str, stringBuilder.toString());
        StreetPassRecordStorage streetPassRecordStorage = BluetoothMonitoringService.access$getStreetPassRecordStorage$p(BluetoothMonitoringService.this);
        StreetPassRecord streetPassRecord = this.$record;
        this.L$0 = param1Object;
        this.label = 1;
        if (streetPassRecordStorage.saveRecord(streetPassRecord, (Continuation)this) == object)
          return object; 
      } 
      return Unit.INSTANCE;
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\n\n\000\n\002\030\002\n\002\b\002\020\000\032\n \002*\004\030\0010\0010\001H\n¢\006\002\b\003"}, d2 = {"<anonymous>", "Landroid/bluetooth/BluetoothAdapter;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 1, 16})
  static final class BluetoothMonitoringService$isBluetoothEnabled$bluetoothAdapter$2 extends Lambda implements Function0<BluetoothAdapter> {
    BluetoothMonitoringService$isBluetoothEnabled$bluetoothAdapter$2() {
      super(0);
    }
    
    public final BluetoothAdapter invoke() {
      Object object = BluetoothMonitoringService.this.getSystemService("bluetooth");
      if (object != null)
        return ((BluetoothManager)object).getAdapter(); 
      throw new TypeCastException("null cannot be cast to non-null type android.bluetooth.BluetoothManager");
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\016\n\000\n\002\020\002\n\002\030\002\n\002\b\002\020\000\032\0020\001*\0020\002H@¢\006\004\b\003\020\004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 16})
  @DebugMetadata(c = "ca.albertahealthservices.contacttracing.services.BluetoothMonitoringService$performPurge$1", f = "BluetoothMonitoringService.kt", i = {0, 0, 1, 1}, l = {525, 526}, m = "invokeSuspend", n = {"$this$launch", "before", "$this$launch", "before"}, s = {"L$0", "J$0", "L$0", "J$0"})
  static final class BluetoothMonitoringService$performPurge$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    long J$0;
    
    Object L$0;
    
    int label;
    
    private CoroutineScope p$;
    
    BluetoothMonitoringService$performPurge$1(BluetoothMonitoringService param1BluetoothMonitoringService2, Continuation param1Continuation) {
      super(2, param1Continuation);
    }
    
    public final Continuation<Unit> create(Object param1Object, Continuation<?> param1Continuation) {
      Intrinsics.checkParameterIsNotNull(param1Continuation, "completion");
      BluetoothMonitoringService$performPurge$1 bluetoothMonitoringService$performPurge$1 = new BluetoothMonitoringService$performPurge$1(this.$context, param1Continuation);
      bluetoothMonitoringService$performPurge$1.p$ = (CoroutineScope)param1Object;
      return (Continuation<Unit>)bluetoothMonitoringService$performPurge$1;
    }
    
    public final Object invoke(Object param1Object1, Object param1Object2) {
      return ((BluetoothMonitoringService$performPurge$1)create(param1Object1, (Continuation)param1Object2)).invokeSuspend(Unit.INSTANCE);
    }
    
    public final Object invokeSuspend(Object param1Object) {
      Object object = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      int i = this.label;
      if (i != 0) {
        if (i != 1) {
          if (i == 2) {
            CoroutineScope coroutineScope = (CoroutineScope)this.L$0;
            ResultKt.throwOnFailure(param1Object);
          } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
          } 
        } else {
          long l = this.J$0;
          CoroutineScope coroutineScope = (CoroutineScope)this.L$0;
          ResultKt.throwOnFailure(param1Object);
          param1Object = coroutineScope;
          StatusRecordStorage statusRecordStorage = BluetoothMonitoringService.access$getStatusRecordStorage$p(BluetoothMonitoringService.this);
          this.L$0 = param1Object;
          this.J$0 = l;
          this.label = 2;
        } 
      } else {
        ResultKt.throwOnFailure(param1Object);
        param1Object = this.p$;
        long l = System.currentTimeMillis() - BluetoothMonitoringService.Companion.getPurgeTTL();
        CentralLog.Companion companion = CentralLog.Companion;
        String str = BluetoothMonitoringService.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Coroutine - Purging of data before epoch time ");
        stringBuilder.append(l);
        companion.i(str, stringBuilder.toString());
        StreetPassRecordStorage streetPassRecordStorage = BluetoothMonitoringService.access$getStreetPassRecordStorage$p(BluetoothMonitoringService.this);
        this.L$0 = param1Object;
        this.J$0 = l;
        this.label = 1;
        if (streetPassRecordStorage.purgeOldRecords(l, (Continuation)this) == object)
          return object; 
        StatusRecordStorage statusRecordStorage = BluetoothMonitoringService.access$getStatusRecordStorage$p(BluetoothMonitoringService.this);
        this.L$0 = param1Object;
        this.J$0 = l;
        this.label = 2;
      } 
      Preference.INSTANCE.putLastPurgeTime((Context)this.$context, System.currentTimeMillis());
      return Unit.INSTANCE;
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/services/BluetoothMonitoringService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */