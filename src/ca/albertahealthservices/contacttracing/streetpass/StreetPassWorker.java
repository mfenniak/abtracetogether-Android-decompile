package ca.albertahealthservices.contacttracing.streetpass;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import ca.albertahealthservices.contacttracing.Utils;
import ca.albertahealthservices.contacttracing.idmanager.TempIDManager;
import ca.albertahealthservices.contacttracing.idmanager.TemporaryID;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.protocol.BlueTrace;
import ca.albertahealthservices.contacttracing.protocol.BlueTraceProtocol;
import ca.albertahealthservices.contacttracing.services.BluetoothMonitoringService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.PriorityBlockingQueue;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000v\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020!\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\020\002\n\002\b\003\n\002\030\002\n\002\b\t\030\0002\0020\001:\003123B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\016\020#\032\0020$2\006\020%\032\0020\025J\006\020&\032\0020'J\016\020(\032\0020'2\006\020%\032\0020\025J\020\020)\032\0020$2\006\020*\032\0020+H\002J\020\020,\032\0020$2\b\020-\032\004\030\0010\006J\b\020.\032\0020'H\002J\006\020/\032\0020'J\006\0200\032\0020'R\016\020\005\032\0020\006XD¢\006\002\n\000R\024\020\007\032\b\022\004\022\0020\t0\bX\004¢\006\002\n\000R\016\020\n\032\0020\013X.¢\006\002\n\000R\022\020\f\032\0060\rR\0020\000X\004¢\006\002\n\000R\016\020\016\032\0020\017X\004¢\006\002\n\000R\016\020\020\032\0020\021X\004¢\006\002\n\000R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\022\020\023R\020\020\024\032\004\030\0010\025X\016¢\006\002\n\000R\016\020\026\032\0020\027X\016¢\006\002\n\000R\021\020\030\032\0020\031¢\006\b\n\000\032\004\b\032\020\033R\016\020\034\032\0020\013X.¢\006\002\n\000R\022\020\035\032\0060\036R\0020\000X\004¢\006\002\n\000R\016\020\037\032\0020\021X\004¢\006\002\n\000R\016\020 \032\0020\013X.¢\006\002\n\000R\024\020!\032\b\022\004\022\0020\0250\"X\004¢\006\002\n\000¨\0064"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "TAG", "", "blacklist", "", "Lca/albertahealthservices/contacttracing/streetpass/BlacklistEntry;", "blacklistHandler", "Landroid/os/Handler;", "blacklistReceiver", "Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker$BlacklistReceiver;", "bluetoothManager", "Landroid/bluetooth/BluetoothManager;", "characteristicV2", "Ljava/util/UUID;", "getContext", "()Landroid/content/Context;", "currentWork", "Lca/albertahealthservices/contacttracing/streetpass/Work;", "localBroadcastManager", "Landroidx/localbroadcastmanager/content/LocalBroadcastManager;", "onWorkTimeoutListener", "Lca/albertahealthservices/contacttracing/streetpass/Work$OnWorkTimeoutListener;", "getOnWorkTimeoutListener", "()Lca/albertahealthservices/contacttracing/streetpass/Work$OnWorkTimeoutListener;", "queueHandler", "scannedDeviceReceiver", "Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker$ScannedDeviceReceiver;", "serviceUUID", "timeoutHandler", "workQueue", "Ljava/util/concurrent/PriorityBlockingQueue;", "addWork", "", "work", "doWork", "", "finishWork", "getConnectionStatus", "device", "Landroid/bluetooth/BluetoothDevice;", "isCurrentlyWorkedOn", "address", "prepare", "terminateConnections", "unregisterReceivers", "BlacklistReceiver", "CentralGattCallback", "ScannedDeviceReceiver", "app_release"}, k = 1, mv = {1, 1, 16})
public final class StreetPassWorker {
  private final String TAG;
  
  private final List<BlacklistEntry> blacklist;
  
  private Handler blacklistHandler;
  
  private final BlacklistReceiver blacklistReceiver;
  
  private final BluetoothManager bluetoothManager;
  
  private final UUID characteristicV2;
  
  private final Context context;
  
  private Work currentWork;
  
  private LocalBroadcastManager localBroadcastManager;
  
  private final Work.OnWorkTimeoutListener onWorkTimeoutListener;
  
  private Handler queueHandler;
  
  private final ScannedDeviceReceiver scannedDeviceReceiver;
  
  private final UUID serviceUUID;
  
  private Handler timeoutHandler;
  
  private final PriorityBlockingQueue<Work> workQueue;
  
  public StreetPassWorker(Context paramContext) {
    this.context = paramContext;
    this.workQueue = new PriorityBlockingQueue<>(5, Collections.reverseOrder());
    List<?> list = Collections.synchronizedList(new ArrayList());
    Intrinsics.checkExpressionValueIsNotNull(list, "Collections.synchronizedList(ArrayList())");
    this.blacklist = (List)list;
    this.scannedDeviceReceiver = new ScannedDeviceReceiver();
    this.blacklistReceiver = new BlacklistReceiver();
    UUID uUID = UUID.fromString("70f34cb2-8882-11ea-bc55-0242ac130003");
    Intrinsics.checkExpressionValueIsNotNull(uUID, "UUID.fromString(BuildConfig.BLE_SSID)");
    this.serviceUUID = uUID;
    uUID = UUID.fromString("7bee419e-8882-11ea-bc55-0242ac130003");
    Intrinsics.checkExpressionValueIsNotNull(uUID, "UUID.fromString(BuildConfig.V2_CHARACTERISTIC_ID)");
    this.characteristicV2 = uUID;
    this.TAG = "StreetPassWorker";
    Object object = this.context.getSystemService("bluetooth");
    if (object != null) {
      this.bluetoothManager = (BluetoothManager)object;
      object = LocalBroadcastManager.getInstance(this.context);
      Intrinsics.checkExpressionValueIsNotNull(object, "LocalBroadcastManager.getInstance(context)");
      this.localBroadcastManager = (LocalBroadcastManager)object;
      this.onWorkTimeoutListener = new StreetPassWorker$onWorkTimeoutListener$1();
      prepare();
      return;
    } 
    throw new TypeCastException("null cannot be cast to non-null type android.bluetooth.BluetoothManager");
  }
  
  private final boolean getConnectionStatus(BluetoothDevice paramBluetoothDevice) {
    return this.bluetoothManager.getDevicesMatchingConnectionStates(7, new int[] { 2 }).contains(paramBluetoothDevice);
  }
  
  private final void prepare() {
    IntentFilter intentFilter = new IntentFilter("ca.albertahealthservices.contacttracing.ACTION_DEVICE_SCANNED");
    this.localBroadcastManager.registerReceiver(this.scannedDeviceReceiver, intentFilter);
    intentFilter = new IntentFilter("ca.albertahealthservices.contacttracing.ACTION_DEVICE_PROCESSED");
    this.localBroadcastManager.registerReceiver(this.blacklistReceiver, intentFilter);
    this.timeoutHandler = new Handler();
    this.queueHandler = new Handler();
    this.blacklistHandler = new Handler();
    CentralLog.Companion companion = CentralLog.Companion;
    String str2 = this.TAG;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("Service UUID ");
    stringBuilder1.append(this.serviceUUID);
    companion.d(str2, stringBuilder1.toString());
    companion = CentralLog.Companion;
    String str1 = this.TAG;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("characteristicV2 ");
    stringBuilder2.append(this.characteristicV2);
    companion.d(str1, stringBuilder2.toString());
  }
  
  public final boolean addWork(Work paramWork) {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 'work'
    //   4: invokestatic checkParameterIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_0
    //   8: aload_1
    //   9: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   12: invokevirtual getAddress : ()Ljava/lang/String;
    //   15: invokevirtual isCurrentlyWorkedOn : (Ljava/lang/String;)Z
    //   18: ifeq -> 73
    //   21: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   24: astore_2
    //   25: aload_0
    //   26: getfield TAG : Ljava/lang/String;
    //   29: astore_3
    //   30: new java/lang/StringBuilder
    //   33: dup
    //   34: invokespecial <init> : ()V
    //   37: astore #4
    //   39: aload #4
    //   41: aload_1
    //   42: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   45: invokevirtual getAddress : ()Ljava/lang/String;
    //   48: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   51: pop
    //   52: aload #4
    //   54: ldc_w ' is being worked on, not adding to queue'
    //   57: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   60: pop
    //   61: aload_2
    //   62: aload_3
    //   63: aload #4
    //   65: invokevirtual toString : ()Ljava/lang/String;
    //   68: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   71: iconst_0
    //   72: ireturn
    //   73: getstatic ca/albertahealthservices/contacttracing/services/BluetoothMonitoringService.Companion : Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Companion;
    //   76: invokevirtual getUseBlacklist : ()Z
    //   79: ifeq -> 229
    //   82: aload_0
    //   83: getfield blacklist : Ljava/util/List;
    //   86: checkcast java/lang/Iterable
    //   89: astore #4
    //   91: new java/util/ArrayList
    //   94: dup
    //   95: invokespecial <init> : ()V
    //   98: checkcast java/util/Collection
    //   101: astore_3
    //   102: aload #4
    //   104: invokeinterface iterator : ()Ljava/util/Iterator;
    //   109: astore_2
    //   110: aload_2
    //   111: invokeinterface hasNext : ()Z
    //   116: ifeq -> 160
    //   119: aload_2
    //   120: invokeinterface next : ()Ljava/lang/Object;
    //   125: astore #4
    //   127: aload #4
    //   129: checkcast ca/albertahealthservices/contacttracing/streetpass/BlacklistEntry
    //   132: invokevirtual getUniqueIdentifier : ()Ljava/lang/String;
    //   135: aload_1
    //   136: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   139: invokevirtual getAddress : ()Ljava/lang/String;
    //   142: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   145: ifeq -> 110
    //   148: aload_3
    //   149: aload #4
    //   151: invokeinterface add : (Ljava/lang/Object;)Z
    //   156: pop
    //   157: goto -> 110
    //   160: aload_3
    //   161: checkcast java/util/List
    //   164: checkcast java/util/Collection
    //   167: invokeinterface isEmpty : ()Z
    //   172: iconst_1
    //   173: ixor
    //   174: ifeq -> 229
    //   177: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   180: astore_2
    //   181: aload_0
    //   182: getfield TAG : Ljava/lang/String;
    //   185: astore_3
    //   186: new java/lang/StringBuilder
    //   189: dup
    //   190: invokespecial <init> : ()V
    //   193: astore #4
    //   195: aload #4
    //   197: aload_1
    //   198: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   201: invokevirtual getAddress : ()Ljava/lang/String;
    //   204: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   207: pop
    //   208: aload #4
    //   210: ldc_w ' is in blacklist, not adding to queue'
    //   213: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   216: pop
    //   217: aload_2
    //   218: aload_3
    //   219: aload #4
    //   221: invokevirtual toString : ()Ljava/lang/String;
    //   224: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   227: iconst_0
    //   228: ireturn
    //   229: aload_0
    //   230: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   233: checkcast java/lang/Iterable
    //   236: astore #4
    //   238: new java/util/ArrayList
    //   241: dup
    //   242: invokespecial <init> : ()V
    //   245: checkcast java/util/Collection
    //   248: astore_3
    //   249: aload #4
    //   251: invokeinterface iterator : ()Ljava/util/Iterator;
    //   256: astore_2
    //   257: aload_2
    //   258: invokeinterface hasNext : ()Z
    //   263: ifeq -> 310
    //   266: aload_2
    //   267: invokeinterface next : ()Ljava/lang/Object;
    //   272: astore #4
    //   274: aload #4
    //   276: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
    //   279: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   282: invokevirtual getAddress : ()Ljava/lang/String;
    //   285: aload_1
    //   286: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   289: invokevirtual getAddress : ()Ljava/lang/String;
    //   292: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   295: ifeq -> 257
    //   298: aload_3
    //   299: aload #4
    //   301: invokeinterface add : (Ljava/lang/Object;)Z
    //   306: pop
    //   307: goto -> 257
    //   310: aload_3
    //   311: checkcast java/util/List
    //   314: invokeinterface isEmpty : ()Z
    //   319: ifeq -> 419
    //   322: aload_0
    //   323: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   326: aload_1
    //   327: invokevirtual offer : (Ljava/lang/Object;)Z
    //   330: pop
    //   331: aload_0
    //   332: getfield queueHandler : Landroid/os/Handler;
    //   335: astore_3
    //   336: aload_3
    //   337: ifnonnull -> 346
    //   340: ldc_w 'queueHandler'
    //   343: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
    //   346: aload_3
    //   347: new ca/albertahealthservices/contacttracing/streetpass/StreetPassWorker$addWork$3
    //   350: dup
    //   351: aload_0
    //   352: aload_1
    //   353: invokespecial <init> : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;Lca/albertahealthservices/contacttracing/streetpass/Work;)V
    //   356: checkcast java/lang/Runnable
    //   359: getstatic ca/albertahealthservices/contacttracing/services/BluetoothMonitoringService.Companion : Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Companion;
    //   362: invokevirtual getMaxQueueTime : ()J
    //   365: invokevirtual postDelayed : (Ljava/lang/Runnable;J)Z
    //   368: pop
    //   369: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   372: astore_3
    //   373: aload_0
    //   374: getfield TAG : Ljava/lang/String;
    //   377: astore #4
    //   379: new java/lang/StringBuilder
    //   382: dup
    //   383: invokespecial <init> : ()V
    //   386: astore_2
    //   387: aload_2
    //   388: ldc_w 'Added to work queue: '
    //   391: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   394: pop
    //   395: aload_2
    //   396: aload_1
    //   397: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   400: invokevirtual getAddress : ()Ljava/lang/String;
    //   403: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   406: pop
    //   407: aload_3
    //   408: aload #4
    //   410: aload_2
    //   411: invokevirtual toString : ()Ljava/lang/String;
    //   414: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   417: iconst_1
    //   418: ireturn
    //   419: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   422: astore #4
    //   424: aload_0
    //   425: getfield TAG : Ljava/lang/String;
    //   428: astore_2
    //   429: new java/lang/StringBuilder
    //   432: dup
    //   433: invokespecial <init> : ()V
    //   436: astore_3
    //   437: aload_3
    //   438: aload_1
    //   439: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   442: invokevirtual getAddress : ()Ljava/lang/String;
    //   445: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   448: pop
    //   449: aload_3
    //   450: ldc_w ' is already in work queue'
    //   453: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   456: pop
    //   457: aload #4
    //   459: aload_2
    //   460: aload_3
    //   461: invokevirtual toString : ()Ljava/lang/String;
    //   464: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   467: aload_0
    //   468: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   471: checkcast java/lang/Iterable
    //   474: invokeinterface iterator : ()Ljava/util/Iterator;
    //   479: astore #4
    //   481: aload #4
    //   483: invokeinterface hasNext : ()Z
    //   488: ifeq -> 525
    //   491: aload #4
    //   493: invokeinterface next : ()Ljava/lang/Object;
    //   498: astore_3
    //   499: aload_3
    //   500: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
    //   503: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   506: invokevirtual getAddress : ()Ljava/lang/String;
    //   509: aload_1
    //   510: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   513: invokevirtual getAddress : ()Ljava/lang/String;
    //   516: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   519: ifeq -> 481
    //   522: goto -> 527
    //   525: aconst_null
    //   526: astore_3
    //   527: aload_3
    //   528: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
    //   531: astore_3
    //   532: aload_0
    //   533: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   536: aload_3
    //   537: invokevirtual remove : (Ljava/lang/Object;)Z
    //   540: istore #5
    //   542: aload_0
    //   543: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   546: aload_1
    //   547: invokevirtual offer : (Ljava/lang/Object;)Z
    //   550: istore #6
    //   552: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   555: astore #4
    //   557: aload_0
    //   558: getfield TAG : Ljava/lang/String;
    //   561: astore_3
    //   562: new java/lang/StringBuilder
    //   565: dup
    //   566: invokespecial <init> : ()V
    //   569: astore_1
    //   570: aload_1
    //   571: ldc_w 'Queue entry updated - removed: '
    //   574: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   577: pop
    //   578: aload_1
    //   579: iload #5
    //   581: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   584: pop
    //   585: aload_1
    //   586: ldc_w ', added: '
    //   589: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   592: pop
    //   593: aload_1
    //   594: iload #6
    //   596: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   599: pop
    //   600: aload #4
    //   602: aload_3
    //   603: aload_1
    //   604: invokevirtual toString : ()Ljava/lang/String;
    //   607: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   610: iconst_0
    //   611: ireturn
  }
  
  public final void doWork() {
    // Byte code:
    //   0: aload_0
    //   1: getfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   4: astore_1
    //   5: iconst_0
    //   6: istore_2
    //   7: iconst_0
    //   8: istore_3
    //   9: aconst_null
    //   10: astore #4
    //   12: aload_1
    //   13: ifnull -> 437
    //   16: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   19: astore #5
    //   21: aload_0
    //   22: getfield TAG : Ljava/lang/String;
    //   25: astore #6
    //   27: new java/lang/StringBuilder
    //   30: dup
    //   31: invokespecial <init> : ()V
    //   34: astore #7
    //   36: aload #7
    //   38: ldc_w 'Already trying to connect to: '
    //   41: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: pop
    //   45: aload_0
    //   46: getfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   49: astore_1
    //   50: aload_1
    //   51: ifnull -> 71
    //   54: aload_1
    //   55: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   58: astore_1
    //   59: aload_1
    //   60: ifnull -> 71
    //   63: aload_1
    //   64: invokevirtual getAddress : ()Ljava/lang/String;
    //   67: astore_1
    //   68: goto -> 73
    //   71: aconst_null
    //   72: astore_1
    //   73: aload #7
    //   75: aload_1
    //   76: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: pop
    //   80: aload #5
    //   82: aload #6
    //   84: aload #7
    //   86: invokevirtual toString : ()Ljava/lang/String;
    //   89: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   92: invokestatic currentTimeMillis : ()J
    //   95: lstore #8
    //   97: aload_0
    //   98: getfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   101: astore_1
    //   102: aload_1
    //   103: ifnull -> 115
    //   106: aload_1
    //   107: invokevirtual getTimeout : ()J
    //   110: lstore #10
    //   112: goto -> 118
    //   115: lconst_0
    //   116: lstore #10
    //   118: lload #8
    //   120: lload #10
    //   122: lcmp
    //   123: ifle -> 131
    //   126: iconst_1
    //   127: istore_2
    //   128: goto -> 133
    //   131: iconst_0
    //   132: istore_2
    //   133: aload_0
    //   134: getfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   137: astore_1
    //   138: aload_1
    //   139: ifnull -> 150
    //   142: aload_1
    //   143: invokevirtual getFinished : ()Z
    //   146: iconst_1
    //   147: if_icmpeq -> 154
    //   150: iload_2
    //   151: ifeq -> 436
    //   154: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   157: astore #6
    //   159: aload_0
    //   160: getfield TAG : Ljava/lang/String;
    //   163: astore #5
    //   165: new java/lang/StringBuilder
    //   168: dup
    //   169: invokespecial <init> : ()V
    //   172: astore #7
    //   174: aload #7
    //   176: ldc_w 'Handling erroneous current work for '
    //   179: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   182: pop
    //   183: aload_0
    //   184: getfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   187: astore_1
    //   188: aload_1
    //   189: ifnull -> 209
    //   192: aload_1
    //   193: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   196: astore_1
    //   197: aload_1
    //   198: ifnull -> 209
    //   201: aload_1
    //   202: invokevirtual getAddress : ()Ljava/lang/String;
    //   205: astore_1
    //   206: goto -> 211
    //   209: aconst_null
    //   210: astore_1
    //   211: aload #7
    //   213: aload_1
    //   214: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   217: pop
    //   218: aload #7
    //   220: ldc_w ' : - finished: '
    //   223: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   226: pop
    //   227: aload_0
    //   228: getfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   231: astore_1
    //   232: aload_1
    //   233: ifnull -> 241
    //   236: aload_1
    //   237: invokevirtual getFinished : ()Z
    //   240: istore_3
    //   241: aload #7
    //   243: iload_3
    //   244: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   247: pop
    //   248: aload #7
    //   250: ldc_w ', timedout: '
    //   253: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   256: pop
    //   257: aload #7
    //   259: iload_2
    //   260: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   263: pop
    //   264: aload #6
    //   266: aload #5
    //   268: aload #7
    //   270: invokevirtual toString : ()Ljava/lang/String;
    //   273: invokevirtual w : (Ljava/lang/String;Ljava/lang/String;)V
    //   276: aload_0
    //   277: getfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   280: ifnull -> 432
    //   283: aload_0
    //   284: getfield bluetoothManager : Landroid/bluetooth/BluetoothManager;
    //   287: bipush #7
    //   289: invokevirtual getConnectedDevices : (I)Ljava/util/List;
    //   292: astore #5
    //   294: aload_0
    //   295: getfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   298: astore_1
    //   299: aload_1
    //   300: ifnull -> 311
    //   303: aload_1
    //   304: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   307: astore_1
    //   308: goto -> 313
    //   311: aconst_null
    //   312: astore_1
    //   313: aload #5
    //   315: aload_1
    //   316: invokeinterface contains : (Ljava/lang/Object;)Z
    //   321: ifeq -> 436
    //   324: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   327: astore #7
    //   329: aload_0
    //   330: getfield TAG : Ljava/lang/String;
    //   333: astore #6
    //   335: new java/lang/StringBuilder
    //   338: dup
    //   339: invokespecial <init> : ()V
    //   342: astore #5
    //   344: aload #5
    //   346: ldc_w 'Disconnecting dangling connection to '
    //   349: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   352: pop
    //   353: aload_0
    //   354: getfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   357: astore #12
    //   359: aload #4
    //   361: astore_1
    //   362: aload #12
    //   364: ifnull -> 388
    //   367: aload #12
    //   369: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   372: astore #12
    //   374: aload #4
    //   376: astore_1
    //   377: aload #12
    //   379: ifnull -> 388
    //   382: aload #12
    //   384: invokevirtual getAddress : ()Ljava/lang/String;
    //   387: astore_1
    //   388: aload #5
    //   390: aload_1
    //   391: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   394: pop
    //   395: aload #7
    //   397: aload #6
    //   399: aload #5
    //   401: invokevirtual toString : ()Ljava/lang/String;
    //   404: invokevirtual w : (Ljava/lang/String;Ljava/lang/String;)V
    //   407: aload_0
    //   408: getfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   411: astore_1
    //   412: aload_1
    //   413: ifnull -> 436
    //   416: aload_1
    //   417: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
    //   420: astore_1
    //   421: aload_1
    //   422: ifnull -> 436
    //   425: aload_1
    //   426: invokevirtual disconnect : ()V
    //   429: goto -> 436
    //   432: aload_0
    //   433: invokevirtual doWork : ()V
    //   436: return
    //   437: aload_0
    //   438: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   441: invokevirtual isEmpty : ()Z
    //   444: ifeq -> 461
    //   447: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   450: aload_0
    //   451: getfield TAG : Ljava/lang/String;
    //   454: ldc_w 'Queue empty. Nothing to do.'
    //   457: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   460: return
    //   461: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   464: astore #4
    //   466: aload_0
    //   467: getfield TAG : Ljava/lang/String;
    //   470: astore_1
    //   471: new java/lang/StringBuilder
    //   474: dup
    //   475: invokespecial <init> : ()V
    //   478: astore #5
    //   480: aload #5
    //   482: ldc_w 'Queue size: '
    //   485: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   488: pop
    //   489: aload #5
    //   491: aload_0
    //   492: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   495: invokevirtual size : ()I
    //   498: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   501: pop
    //   502: aload #4
    //   504: aload_1
    //   505: aload #5
    //   507: invokevirtual toString : ()Ljava/lang/String;
    //   510: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   513: aconst_null
    //   514: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
    //   517: astore #4
    //   519: invokestatic currentTimeMillis : ()J
    //   522: lstore #10
    //   524: aload #4
    //   526: astore_1
    //   527: aload_1
    //   528: ifnonnull -> 651
    //   531: aload_0
    //   532: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   535: checkcast java/util/Collection
    //   538: invokeinterface isEmpty : ()Z
    //   543: iconst_1
    //   544: ixor
    //   545: ifeq -> 651
    //   548: aload_0
    //   549: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   552: invokevirtual poll : ()Ljava/lang/Object;
    //   555: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
    //   558: astore #5
    //   560: aload #5
    //   562: astore_1
    //   563: aload #5
    //   565: ifnull -> 527
    //   568: aload #5
    //   570: astore_1
    //   571: lload #10
    //   573: aload #5
    //   575: invokevirtual getTimeStamp : ()J
    //   578: lsub
    //   579: getstatic ca/albertahealthservices/contacttracing/services/BluetoothMonitoringService.Companion : Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Companion;
    //   582: invokevirtual getMaxQueueTime : ()J
    //   585: lcmp
    //   586: ifle -> 527
    //   589: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   592: astore #7
    //   594: aload_0
    //   595: getfield TAG : Ljava/lang/String;
    //   598: astore #6
    //   600: new java/lang/StringBuilder
    //   603: dup
    //   604: invokespecial <init> : ()V
    //   607: astore_1
    //   608: aload_1
    //   609: ldc_w 'Work request for '
    //   612: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   615: pop
    //   616: aload_1
    //   617: aload #5
    //   619: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   622: invokevirtual getAddress : ()Ljava/lang/String;
    //   625: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   628: pop
    //   629: aload_1
    //   630: ldc_w ' too old. Not doing'
    //   633: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   636: pop
    //   637: aload #7
    //   639: aload #6
    //   641: aload_1
    //   642: invokevirtual toString : ()Ljava/lang/String;
    //   645: invokevirtual w : (Ljava/lang/String;Ljava/lang/String;)V
    //   648: goto -> 524
    //   651: aload_1
    //   652: ifnull -> 1470
    //   655: aload_1
    //   656: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   659: astore #5
    //   661: getstatic ca/albertahealthservices/contacttracing/services/BluetoothMonitoringService.Companion : Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Companion;
    //   664: invokevirtual getUseBlacklist : ()Z
    //   667: ifeq -> 833
    //   670: aload_0
    //   671: getfield blacklist : Ljava/util/List;
    //   674: checkcast java/lang/Iterable
    //   677: astore #7
    //   679: new java/util/ArrayList
    //   682: dup
    //   683: invokespecial <init> : ()V
    //   686: checkcast java/util/Collection
    //   689: astore #6
    //   691: aload #7
    //   693: invokeinterface iterator : ()Ljava/util/Iterator;
    //   698: astore #12
    //   700: aload #12
    //   702: invokeinterface hasNext : ()Z
    //   707: ifeq -> 751
    //   710: aload #12
    //   712: invokeinterface next : ()Ljava/lang/Object;
    //   717: astore #7
    //   719: aload #7
    //   721: checkcast ca/albertahealthservices/contacttracing/streetpass/BlacklistEntry
    //   724: invokevirtual getUniqueIdentifier : ()Ljava/lang/String;
    //   727: aload #5
    //   729: invokevirtual getAddress : ()Ljava/lang/String;
    //   732: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   735: ifeq -> 700
    //   738: aload #6
    //   740: aload #7
    //   742: invokeinterface add : (Ljava/lang/Object;)Z
    //   747: pop
    //   748: goto -> 700
    //   751: aload #6
    //   753: checkcast java/util/List
    //   756: checkcast java/util/Collection
    //   759: invokeinterface isEmpty : ()Z
    //   764: iconst_1
    //   765: ixor
    //   766: ifeq -> 833
    //   769: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   772: astore_1
    //   773: aload_0
    //   774: getfield TAG : Ljava/lang/String;
    //   777: astore #6
    //   779: new java/lang/StringBuilder
    //   782: dup
    //   783: invokespecial <init> : ()V
    //   786: astore #4
    //   788: aload #4
    //   790: ldc_w 'Already worked on '
    //   793: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   796: pop
    //   797: aload #4
    //   799: aload #5
    //   801: invokevirtual getAddress : ()Ljava/lang/String;
    //   804: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   807: pop
    //   808: aload #4
    //   810: ldc_w '. Skip.'
    //   813: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   816: pop
    //   817: aload_1
    //   818: aload #6
    //   820: aload #4
    //   822: invokevirtual toString : ()Ljava/lang/String;
    //   825: invokevirtual w : (Ljava/lang/String;Ljava/lang/String;)V
    //   828: aload_0
    //   829: invokevirtual doWork : ()V
    //   832: return
    //   833: aload_0
    //   834: aload #5
    //   836: invokespecial getConnectionStatus : (Landroid/bluetooth/BluetoothDevice;)Z
    //   839: istore_3
    //   840: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   843: astore #6
    //   845: aload_0
    //   846: getfield TAG : Ljava/lang/String;
    //   849: astore #7
    //   851: new java/lang/StringBuilder
    //   854: dup
    //   855: invokespecial <init> : ()V
    //   858: astore #12
    //   860: aload #12
    //   862: ldc_w 'Already connected to '
    //   865: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   868: pop
    //   869: aload #12
    //   871: aload #5
    //   873: invokevirtual getAddress : ()Ljava/lang/String;
    //   876: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   879: pop
    //   880: aload #12
    //   882: ldc_w ' : '
    //   885: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   888: pop
    //   889: aload #12
    //   891: iload_3
    //   892: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   895: pop
    //   896: aload #6
    //   898: aload #7
    //   900: aload #12
    //   902: invokevirtual toString : ()Ljava/lang/String;
    //   905: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   908: iload_3
    //   909: ifeq -> 944
    //   912: aload_1
    //   913: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
    //   916: invokevirtual getSkipped : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
    //   919: iconst_1
    //   920: invokevirtual setStatus : (Z)V
    //   923: aload_1
    //   924: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
    //   927: invokevirtual getSkipped : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
    //   930: invokestatic currentTimeMillis : ()J
    //   933: invokevirtual setTimePerformed : (J)V
    //   936: aload_0
    //   937: aload_1
    //   938: invokevirtual finishWork : (Lca/albertahealthservices/contacttracing/streetpass/Work;)V
    //   941: goto -> 1470
    //   944: new ca/albertahealthservices/contacttracing/streetpass/StreetPassWorker$CentralGattCallback
    //   947: dup
    //   948: aload_0
    //   949: aload_1
    //   950: invokespecial <init> : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;Lca/albertahealthservices/contacttracing/streetpass/Work;)V
    //   953: astore #6
    //   955: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   958: astore #7
    //   960: aload_0
    //   961: getfield TAG : Ljava/lang/String;
    //   964: astore #13
    //   966: new java/lang/StringBuilder
    //   969: dup
    //   970: invokespecial <init> : ()V
    //   973: astore #12
    //   975: aload #12
    //   977: ldc_w 'Starting work - connecting to device: '
    //   980: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   983: pop
    //   984: aload #12
    //   986: aload #5
    //   988: invokevirtual getAddress : ()Ljava/lang/String;
    //   991: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   994: pop
    //   995: aload #12
    //   997: ldc_w ' @ '
    //   1000: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1003: pop
    //   1004: aload #12
    //   1006: aload_1
    //   1007: invokevirtual getConnectable : ()Lca/albertahealthservices/contacttracing/streetpass/ConnectablePeripheral;
    //   1010: invokevirtual getRssi : ()I
    //   1013: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1016: pop
    //   1017: aload #12
    //   1019: bipush #32
    //   1021: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   1024: pop
    //   1025: aload #12
    //   1027: invokestatic currentTimeMillis : ()J
    //   1030: aload_1
    //   1031: invokevirtual getTimeStamp : ()J
    //   1034: lsub
    //   1035: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   1038: pop
    //   1039: aload #12
    //   1041: ldc_w 'ms ago'
    //   1044: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1047: pop
    //   1048: aload #7
    //   1050: aload #13
    //   1052: aload #12
    //   1054: invokevirtual toString : ()Ljava/lang/String;
    //   1057: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   1060: aload_0
    //   1061: aload_1
    //   1062: putfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   1065: aload_1
    //   1066: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
    //   1069: invokevirtual getStarted : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
    //   1072: iconst_1
    //   1073: invokevirtual setStatus : (Z)V
    //   1076: aload_1
    //   1077: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
    //   1080: invokevirtual getStarted : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
    //   1083: invokestatic currentTimeMillis : ()J
    //   1086: invokevirtual setTimePerformed : (J)V
    //   1089: aload_1
    //   1090: aload_0
    //   1091: getfield context : Landroid/content/Context;
    //   1094: aload #6
    //   1096: invokevirtual startWork : (Landroid/content/Context;Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker$CentralGattCallback;)V
    //   1099: aload_1
    //   1100: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
    //   1103: astore #6
    //   1105: aload #6
    //   1107: ifnull -> 1116
    //   1110: aload #6
    //   1112: invokevirtual connect : ()Z
    //   1115: istore_2
    //   1116: iload_2
    //   1117: ifne -> 1210
    //   1120: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1123: astore #7
    //   1125: aload_0
    //   1126: getfield TAG : Ljava/lang/String;
    //   1129: astore #6
    //   1131: new java/lang/StringBuilder
    //   1134: astore #12
    //   1136: aload #12
    //   1138: invokespecial <init> : ()V
    //   1141: aload #12
    //   1143: ldc_w 'Alamak! not connecting to '
    //   1146: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1149: pop
    //   1150: aload #12
    //   1152: aload_1
    //   1153: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   1156: invokevirtual getAddress : ()Ljava/lang/String;
    //   1159: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1162: pop
    //   1163: aload #12
    //   1165: ldc_w '??'
    //   1168: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1171: pop
    //   1172: aload #7
    //   1174: aload #6
    //   1176: aload #12
    //   1178: invokevirtual toString : ()Ljava/lang/String;
    //   1181: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1184: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1187: aload_0
    //   1188: getfield TAG : Ljava/lang/String;
    //   1191: ldc_w 'Moving on to next task'
    //   1194: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1197: aload_0
    //   1198: aconst_null
    //   1199: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
    //   1202: putfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   1205: aload_0
    //   1206: invokevirtual doWork : ()V
    //   1209: return
    //   1210: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1213: astore #12
    //   1215: aload_0
    //   1216: getfield TAG : Ljava/lang/String;
    //   1219: astore #6
    //   1221: new java/lang/StringBuilder
    //   1224: astore #7
    //   1226: aload #7
    //   1228: invokespecial <init> : ()V
    //   1231: aload #7
    //   1233: ldc_w 'Connection to '
    //   1236: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1239: pop
    //   1240: aload #7
    //   1242: aload_1
    //   1243: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   1246: invokevirtual getAddress : ()Ljava/lang/String;
    //   1249: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1252: pop
    //   1253: aload #7
    //   1255: ldc_w ' attempt in progress'
    //   1258: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1261: pop
    //   1262: aload #12
    //   1264: aload #6
    //   1266: aload #7
    //   1268: invokevirtual toString : ()Ljava/lang/String;
    //   1271: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   1274: aload_0
    //   1275: getfield timeoutHandler : Landroid/os/Handler;
    //   1278: astore #6
    //   1280: aload #6
    //   1282: ifnonnull -> 1290
    //   1285: ldc 'timeoutHandler'
    //   1287: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
    //   1290: aload #6
    //   1292: aload_1
    //   1293: invokevirtual getTimeoutRunnable : ()Ljava/lang/Runnable;
    //   1296: getstatic ca/albertahealthservices/contacttracing/services/BluetoothMonitoringService.Companion : Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Companion;
    //   1299: invokevirtual getConnectionTimeout : ()J
    //   1302: invokevirtual postDelayed : (Ljava/lang/Runnable;J)Z
    //   1305: pop
    //   1306: aload_1
    //   1307: invokestatic currentTimeMillis : ()J
    //   1310: getstatic ca/albertahealthservices/contacttracing/services/BluetoothMonitoringService.Companion : Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Companion;
    //   1313: invokevirtual getConnectionTimeout : ()J
    //   1316: ladd
    //   1317: invokevirtual setTimeout : (J)V
    //   1320: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1323: astore #12
    //   1325: aload_0
    //   1326: getfield TAG : Ljava/lang/String;
    //   1329: astore #6
    //   1331: new java/lang/StringBuilder
    //   1334: astore #7
    //   1336: aload #7
    //   1338: invokespecial <init> : ()V
    //   1341: aload #7
    //   1343: ldc_w 'Timeout scheduled for '
    //   1346: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1349: pop
    //   1350: aload #7
    //   1352: aload_1
    //   1353: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   1356: invokevirtual getAddress : ()Ljava/lang/String;
    //   1359: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1362: pop
    //   1363: aload #12
    //   1365: aload #6
    //   1367: aload #7
    //   1369: invokevirtual toString : ()Ljava/lang/String;
    //   1372: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   1375: goto -> 1470
    //   1378: astore #7
    //   1380: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1383: astore #12
    //   1385: aload_0
    //   1386: getfield TAG : Ljava/lang/String;
    //   1389: astore #6
    //   1391: new java/lang/StringBuilder
    //   1394: dup
    //   1395: invokespecial <init> : ()V
    //   1398: astore_1
    //   1399: aload_1
    //   1400: ldc_w 'Unexpected error while attempting to connect to '
    //   1403: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1406: pop
    //   1407: aload_1
    //   1408: aload #5
    //   1410: invokevirtual getAddress : ()Ljava/lang/String;
    //   1413: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1416: pop
    //   1417: aload_1
    //   1418: ldc_w ': '
    //   1421: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1424: pop
    //   1425: aload_1
    //   1426: aload #7
    //   1428: invokevirtual getLocalizedMessage : ()Ljava/lang/String;
    //   1431: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1434: pop
    //   1435: aload #12
    //   1437: aload #6
    //   1439: aload_1
    //   1440: invokevirtual toString : ()Ljava/lang/String;
    //   1443: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1446: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1449: aload_0
    //   1450: getfield TAG : Ljava/lang/String;
    //   1453: ldc_w 'Moving on to next task'
    //   1456: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1459: aload_0
    //   1460: aload #4
    //   1462: putfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   1465: aload_0
    //   1466: invokevirtual doWork : ()V
    //   1469: return
    //   1470: aload_1
    //   1471: ifnonnull -> 1487
    //   1474: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1477: aload_0
    //   1478: getfield TAG : Ljava/lang/String;
    //   1481: ldc_w 'No outstanding work'
    //   1484: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   1487: return
    // Exception table:
    //   from	to	target	type
    //   1065	1105	1378	finally
    //   1110	1116	1378	finally
    //   1120	1209	1378	finally
    //   1210	1280	1378	finally
    //   1285	1290	1378	finally
    //   1290	1375	1378	finally
  }
  
  public final void finishWork(Work paramWork) {
    Intrinsics.checkParameterIsNotNull(paramWork, "work");
    if (paramWork.getFinished()) {
      CentralLog.Companion companion = CentralLog.Companion;
      String str = this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Work on ");
      stringBuilder.append(paramWork.getDevice().getAddress());
      stringBuilder.append(" already finished and closed");
      companion.i(str, stringBuilder.toString());
      return;
    } 
    if (paramWork.isCriticalsCompleted()) {
      Utils utils = Utils.INSTANCE;
      Context context = this.context;
      String str = paramWork.getDevice().getAddress();
      Intrinsics.checkExpressionValueIsNotNull(str, "work.device.address");
      utils.broadcastDeviceProcessed(context, str);
    } 
    CentralLog.Companion companion2 = CentralLog.Companion;
    String str3 = this.TAG;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("Work on ");
    stringBuilder1.append(paramWork.getDevice().getAddress());
    stringBuilder1.append(" stopped in: ");
    stringBuilder1.append(paramWork.getChecklist().getDisconnected().getTimePerformed() - paramWork.getChecklist().getStarted().getTimePerformed());
    companion2.i(str3, stringBuilder1.toString());
    CentralLog.Companion companion3 = CentralLog.Companion;
    String str1 = this.TAG;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("Work on ");
    stringBuilder2.append(paramWork.getDevice().getAddress());
    stringBuilder2.append(" completed?: ");
    stringBuilder2.append(paramWork.isCriticalsCompleted());
    stringBuilder2.append(". Connected in: ");
    stringBuilder2.append(paramWork.getChecklist().getConnected().getTimePerformed() - paramWork.getChecklist().getStarted().getTimePerformed());
    stringBuilder2.append(". connection lasted for: ");
    stringBuilder2.append(paramWork.getChecklist().getDisconnected().getTimePerformed() - paramWork.getChecklist().getConnected().getTimePerformed());
    stringBuilder2.append(". Status: ");
    stringBuilder2.append(paramWork.getChecklist());
    companion3.i(str1, stringBuilder2.toString());
    Handler handler = this.timeoutHandler;
    if (handler == null)
      Intrinsics.throwUninitializedPropertyAccessException("timeoutHandler"); 
    handler.removeCallbacks(paramWork.getTimeoutRunnable());
    CentralLog.Companion companion1 = CentralLog.Companion;
    String str2 = this.TAG;
    stringBuilder2 = new StringBuilder();
    stringBuilder2.append("Timeout removed for ");
    stringBuilder2.append(paramWork.getDevice().getAddress());
    companion1.i(str2, stringBuilder2.toString());
    paramWork.setFinished(true);
    doWork();
  }
  
  public final Context getContext() {
    return this.context;
  }
  
  public final Work.OnWorkTimeoutListener getOnWorkTimeoutListener() {
    return this.onWorkTimeoutListener;
  }
  
  public final boolean isCurrentlyWorkedOn(String paramString) {
    boolean bool;
    Work work = this.currentWork;
    if (work != null) {
      bool = Intrinsics.areEqual(work.getDevice().getAddress(), paramString);
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final void terminateConnections() {
    CentralLog.Companion.d(this.TAG, "Cleaning up worker.");
    Work work = this.currentWork;
    if (work != null) {
      BluetoothGatt bluetoothGatt = work.getGatt();
      if (bluetoothGatt != null)
        bluetoothGatt.disconnect(); 
    } 
    this.currentWork = (Work)null;
    Handler handler = this.timeoutHandler;
    if (handler == null)
      Intrinsics.throwUninitializedPropertyAccessException("timeoutHandler"); 
    handler.removeCallbacksAndMessages(null);
    handler = this.queueHandler;
    if (handler == null)
      Intrinsics.throwUninitializedPropertyAccessException("queueHandler"); 
    handler.removeCallbacksAndMessages(null);
    handler = this.blacklistHandler;
    if (handler == null)
      Intrinsics.throwUninitializedPropertyAccessException("blacklistHandler"); 
    handler.removeCallbacksAndMessages(null);
    this.workQueue.clear();
    this.blacklist.clear();
  }
  
  public final void unregisterReceivers() {
    try {
    
    } finally {
      CentralLog.Companion companion;
      String str;
      StringBuilder stringBuilder;
    } 
    try {
      this.localBroadcastManager.unregisterReceiver(this.scannedDeviceReceiver);
    } finally {
      Exception exception = null;
      CentralLog.Companion companion = CentralLog.Companion;
      String str = this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to close receivers: ");
      stringBuilder.append(exception.getLocalizedMessage());
    } 
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\036\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\b\004\030\0002\0020\001B\005¢\006\002\020\002J\030\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\bH\026¨\006\t"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker$BlacklistReceiver;", "Landroid/content/BroadcastReceiver;", "(Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 1, 16})
  public final class BlacklistReceiver extends BroadcastReceiver {
    public void onReceive(Context param1Context, Intent param1Intent) {
      Intrinsics.checkParameterIsNotNull(param1Context, "context");
      Intrinsics.checkParameterIsNotNull(param1Intent, "intent");
      if (Intrinsics.areEqual("ca.albertahealthservices.contacttracing.ACTION_DEVICE_PROCESSED", param1Intent.getAction())) {
        String str1 = param1Intent.getStringExtra("ca.albertahealthservices.contacttracing.DEVICE_ADDRESS");
        CentralLog.Companion companion = CentralLog.Companion;
        String str2 = StreetPassWorker.this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Adding to blacklist: ");
        stringBuilder.append(str1);
        companion.d(str2, stringBuilder.toString());
        Intrinsics.checkExpressionValueIsNotNull(str1, "deviceAddress");
        BlacklistEntry blacklistEntry = new BlacklistEntry(str1, System.currentTimeMillis());
        StreetPassWorker.this.blacklist.add(blacklistEntry);
        StreetPassWorker.access$getBlacklistHandler$p(StreetPassWorker.this).postDelayed(new StreetPassWorker$BlacklistReceiver$onReceive$1(blacklistEntry), BluetoothMonitoringService.Companion.getBlacklistDuration());
      } 
    }
    
    @Metadata(bv = {1, 0, 3}, d1 = {"\000\b\n\000\n\002\020\002\n\000\020\000\032\0020\001H\n¢\006\002\b\002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 1, 16})
    static final class StreetPassWorker$BlacklistReceiver$onReceive$1 implements Runnable {
      StreetPassWorker$BlacklistReceiver$onReceive$1(BlacklistEntry param2BlacklistEntry) {}
      
      public final void run() {
        CentralLog.Companion companion = CentralLog.Companion;
        String str = StreetPassWorker.this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("blacklist for ");
        stringBuilder.append(this.$entry.getUniqueIdentifier());
        stringBuilder.append(" removed? : ");
        stringBuilder.append(StreetPassWorker.this.blacklist.remove(this.$entry));
        companion.i(str, stringBuilder.toString());
      }
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\b\n\000\n\002\020\002\n\000\020\000\032\0020\001H\n¢\006\002\b\002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 1, 16})
  static final class StreetPassWorker$BlacklistReceiver$onReceive$1 implements Runnable {
    StreetPassWorker$BlacklistReceiver$onReceive$1(BlacklistEntry param1BlacklistEntry) {}
    
    public final void run() {
      CentralLog.Companion companion = CentralLog.Companion;
      String str = StreetPassWorker.this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("blacklist for ");
      stringBuilder.append(this.$entry.getUniqueIdentifier());
      stringBuilder.append(" removed? : ");
      stringBuilder.append(StreetPassWorker.this.blacklist.remove(this.$entry));
      companion.i(str, stringBuilder.toString());
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000.\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\b\n\002\b\007\b\004\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\016\020\007\032\0020\b2\006\020\t\032\0020\nJ \020\013\032\0020\b2\006\020\t\032\0020\n2\006\020\f\032\0020\r2\006\020\016\032\0020\017H\026J \020\020\032\0020\b2\006\020\t\032\0020\n2\006\020\f\032\0020\r2\006\020\016\032\0020\017H\026J\"\020\021\032\0020\b2\b\020\t\032\004\030\0010\n2\006\020\016\032\0020\0172\006\020\022\032\0020\017H\026J\"\020\023\032\0020\b2\b\020\t\032\004\030\0010\n2\006\020\024\032\0020\0172\006\020\016\032\0020\017H\026J\030\020\025\032\0020\b2\006\020\t\032\0020\n2\006\020\016\032\0020\017H\026R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\005\020\006¨\006\026"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker$CentralGattCallback;", "Landroid/bluetooth/BluetoothGattCallback;", "work", "Lca/albertahealthservices/contacttracing/streetpass/Work;", "(Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;Lca/albertahealthservices/contacttracing/streetpass/Work;)V", "getWork", "()Lca/albertahealthservices/contacttracing/streetpass/Work;", "endWorkConnection", "", "gatt", "Landroid/bluetooth/BluetoothGatt;", "onCharacteristicRead", "characteristic", "Landroid/bluetooth/BluetoothGattCharacteristic;", "status", "", "onCharacteristicWrite", "onConnectionStateChange", "newState", "onMtuChanged", "mtu", "onServicesDiscovered", "app_release"}, k = 1, mv = {1, 1, 16})
  public final class CentralGattCallback extends BluetoothGattCallback {
    private final Work work;
    
    public CentralGattCallback(Work param1Work) {
      this.work = param1Work;
    }
    
    public final void endWorkConnection(BluetoothGatt param1BluetoothGatt) {
      Intrinsics.checkParameterIsNotNull(param1BluetoothGatt, "gatt");
      CentralLog.Companion companion = CentralLog.Companion;
      String str = StreetPassWorker.this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Ending connection with: ");
      BluetoothDevice bluetoothDevice = param1BluetoothGatt.getDevice();
      Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice, "gatt.device");
      stringBuilder.append(bluetoothDevice.getAddress());
      companion.i(str, stringBuilder.toString());
      param1BluetoothGatt.disconnect();
    }
    
    public final Work getWork() {
      return this.work;
    }
    
    public void onCharacteristicRead(BluetoothGatt param1BluetoothGatt, BluetoothGattCharacteristic param1BluetoothGattCharacteristic, int param1Int) {
      BluetoothDevice bluetoothDevice;
      TemporaryID temporaryID;
      Intrinsics.checkParameterIsNotNull(param1BluetoothGatt, "gatt");
      Intrinsics.checkParameterIsNotNull(param1BluetoothGattCharacteristic, "characteristic");
      CentralLog.Companion companion = CentralLog.Companion;
      String str = StreetPassWorker.this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Read Status: ");
      stringBuilder.append(param1Int);
      companion.i(str, stringBuilder.toString());
      if (param1Int != 0) {
        CentralLog.Companion companion1 = CentralLog.Companion;
        String str1 = StreetPassWorker.this.TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to read characteristics from ");
        BluetoothDevice bluetoothDevice1 = param1BluetoothGatt.getDevice();
        Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
        stringBuilder.append(bluetoothDevice1.getAddress());
        stringBuilder.append(": ");
        stringBuilder.append(param1Int);
        companion1.w(str1, stringBuilder.toString());
      } else {
        CentralLog.Companion companion2 = CentralLog.Companion;
        String str1 = StreetPassWorker.this.TAG;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Characteristic read from ");
        BluetoothDevice bluetoothDevice1 = param1BluetoothGatt.getDevice();
        Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
        stringBuilder1.append(bluetoothDevice1.getAddress());
        stringBuilder1.append(": ");
        stringBuilder1.append(param1BluetoothGattCharacteristic.getStringValue(0));
        companion2.i(str1, stringBuilder1.toString());
        CentralLog.Companion companion1 = CentralLog.Companion;
        str1 = StreetPassWorker.this.TAG;
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("onCharacteristicRead: ");
        stringBuilder1.append(this.work.getDevice().getAddress());
        stringBuilder1.append(" - [");
        stringBuilder1.append(this.work.getConnectable().getRssi());
        stringBuilder1.append(']');
        companion1.i(str1, stringBuilder1.toString());
        if (BlueTrace.INSTANCE.supportsCharUUID(param1BluetoothGattCharacteristic.getUuid()))
          try {
            BlueTrace blueTrace = BlueTrace.INSTANCE;
            UUID uUID = param1BluetoothGattCharacteristic.getUuid();
            Intrinsics.checkExpressionValueIsNotNull(uUID, "characteristic.uuid");
            BlueTraceProtocol blueTraceProtocol = blueTrace.getImplementation(uUID);
            byte[] arrayOfByte = param1BluetoothGattCharacteristic.getValue();
          } finally {
            companion1 = null;
            companion2 = CentralLog.Companion;
            String str2 = StreetPassWorker.this.TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Failed to process read payload - ");
            stringBuilder2.append(companion1.getMessage());
          }  
        this.work.getChecklist().getReadCharacteristic().setStatus(true);
        this.work.getChecklist().getReadCharacteristic().setTimePerformed(System.currentTimeMillis());
      } 
      if (BlueTrace.INSTANCE.supportsCharUUID(param1BluetoothGattCharacteristic.getUuid())) {
        BlueTrace blueTrace = BlueTrace.INSTANCE;
        UUID uUID = param1BluetoothGattCharacteristic.getUuid();
        Intrinsics.checkExpressionValueIsNotNull(uUID, "characteristic.uuid");
        BlueTraceProtocol blueTraceProtocol = blueTrace.getImplementation(uUID);
        if (TempIDManager.INSTANCE.bmValid(StreetPassWorker.this.getContext())) {
          param1BluetoothGattCharacteristic.setValue(blueTraceProtocol.getCentral().prepareWriteRequestData(blueTraceProtocol.getVersionInt(), this.work.getConnectable().getRssi(), this.work.getConnectable().getTransmissionPower()));
          boolean bool = param1BluetoothGatt.writeCharacteristic(param1BluetoothGattCharacteristic);
          PairingFix.INSTANCE.bypassAuthenticationRetry(param1BluetoothGatt);
          CentralLog.Companion companion1 = CentralLog.Companion;
          String str1 = StreetPassWorker.this.TAG;
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Attempt to write characteristic to our service on ");
          bluetoothDevice = param1BluetoothGatt.getDevice();
          Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice, "gatt.device");
          stringBuilder1.append(bluetoothDevice.getAddress());
          stringBuilder1.append(": ");
          stringBuilder1.append(bool);
          companion1.i(str1, stringBuilder1.toString());
        } else {
          companion = CentralLog.Companion;
          String str1 = StreetPassWorker.this.TAG;
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Expired BM. Skipping attempt to write characteristic to our service on ");
          BluetoothDevice bluetoothDevice1 = bluetoothDevice.getDevice();
          Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
          stringBuilder1.append(bluetoothDevice1.getAddress());
          companion.i(str1, stringBuilder1.toString());
          temporaryID = TempIDManager.INSTANCE.retrieveTemporaryID(StreetPassWorker.this.getContext());
          if (temporaryID != null) {
            CentralLog.Companion.i(StreetPassWorker.this.TAG, "Grab New Temp ID");
            BluetoothMonitoringService.Companion.setBroadcastMessage(temporaryID);
          } else {
            CentralLog.Companion.e(StreetPassWorker.this.TAG, "Failed to grab new Temp ID");
          } 
          endWorkConnection((BluetoothGatt)bluetoothDevice);
        } 
      } else {
        CentralLog.Companion companion1 = CentralLog.Companion;
        str = StreetPassWorker.this.TAG;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Not writing to ");
        BluetoothDevice bluetoothDevice1 = bluetoothDevice.getDevice();
        Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
        stringBuilder1.append(bluetoothDevice1.getAddress());
        stringBuilder1.append(". Characteristic ");
        stringBuilder1.append(temporaryID.getUuid());
        stringBuilder1.append(" is not supported");
        companion1.w(str, stringBuilder1.toString());
        endWorkConnection((BluetoothGatt)bluetoothDevice);
      } 
    }
    
    public void onCharacteristicWrite(BluetoothGatt param1BluetoothGatt, BluetoothGattCharacteristic param1BluetoothGattCharacteristic, int param1Int) {
      Intrinsics.checkParameterIsNotNull(param1BluetoothGatt, "gatt");
      Intrinsics.checkParameterIsNotNull(param1BluetoothGattCharacteristic, "characteristic");
      if (param1Int != 0) {
        CentralLog.Companion companion = CentralLog.Companion;
        String str = StreetPassWorker.this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to write characteristics: ");
        stringBuilder.append(param1Int);
        companion.i(str, stringBuilder.toString());
      } else {
        CentralLog.Companion.i(StreetPassWorker.this.TAG, "Characteristic wrote successfully");
        this.work.getChecklist().getWriteCharacteristic().setStatus(true);
        this.work.getChecklist().getWriteCharacteristic().setTimePerformed(System.currentTimeMillis());
      } 
      endWorkConnection(param1BluetoothGatt);
    }
    
    public void onConnectionStateChange(BluetoothGatt param1BluetoothGatt, int param1Int1, int param1Int2) {
      // Byte code:
      //   0: aload_1
      //   1: ifnull -> 481
      //   4: iload_3
      //   5: ifeq -> 219
      //   8: iload_3
      //   9: iconst_2
      //   10: if_icmpeq -> 104
      //   13: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   16: astore #4
      //   18: aload_0
      //   19: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   22: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   25: astore #5
      //   27: new java/lang/StringBuilder
      //   30: dup
      //   31: invokespecial <init> : ()V
      //   34: astore #6
      //   36: aload #6
      //   38: ldc_w 'Connection status for '
      //   41: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   44: pop
      //   45: aload_1
      //   46: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   49: astore #7
      //   51: aload #7
      //   53: ldc 'gatt.device'
      //   55: invokestatic checkExpressionValueIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
      //   58: aload #6
      //   60: aload #7
      //   62: invokevirtual getAddress : ()Ljava/lang/String;
      //   65: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   68: pop
      //   69: aload #6
      //   71: ldc ': '
      //   73: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   76: pop
      //   77: aload #6
      //   79: iload_3
      //   80: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   83: pop
      //   84: aload #4
      //   86: aload #5
      //   88: aload #6
      //   90: invokevirtual toString : ()Ljava/lang/String;
      //   93: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
      //   96: aload_0
      //   97: aload_1
      //   98: invokevirtual endWorkConnection : (Landroid/bluetooth/BluetoothGatt;)V
      //   101: goto -> 481
      //   104: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   107: astore #5
      //   109: aload_0
      //   110: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   113: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   116: astore #7
      //   118: new java/lang/StringBuilder
      //   121: dup
      //   122: invokespecial <init> : ()V
      //   125: astore #4
      //   127: aload #4
      //   129: ldc_w 'Connected to other GATT server - '
      //   132: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   135: pop
      //   136: aload_1
      //   137: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   140: astore #6
      //   142: aload #6
      //   144: ldc 'gatt.device'
      //   146: invokestatic checkExpressionValueIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
      //   149: aload #4
      //   151: aload #6
      //   153: invokevirtual getAddress : ()Ljava/lang/String;
      //   156: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   159: pop
      //   160: aload #5
      //   162: aload #7
      //   164: aload #4
      //   166: invokevirtual toString : ()Ljava/lang/String;
      //   169: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
      //   172: aload_1
      //   173: iconst_0
      //   174: invokevirtual requestConnectionPriority : (I)Z
      //   177: pop
      //   178: aload_1
      //   179: sipush #512
      //   182: invokevirtual requestMtu : (I)Z
      //   185: pop
      //   186: aload_0
      //   187: getfield work : Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   190: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   193: invokevirtual getConnected : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   196: iconst_1
      //   197: invokevirtual setStatus : (Z)V
      //   200: aload_0
      //   201: getfield work : Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   204: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   207: invokevirtual getConnected : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   210: invokestatic currentTimeMillis : ()J
      //   213: invokevirtual setTimePerformed : (J)V
      //   216: goto -> 481
      //   219: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   222: astore #4
      //   224: aload_0
      //   225: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   228: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   231: astore #7
      //   233: new java/lang/StringBuilder
      //   236: dup
      //   237: invokespecial <init> : ()V
      //   240: astore #5
      //   242: aload #5
      //   244: ldc_w 'Disconnected from other GATT server - '
      //   247: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   250: pop
      //   251: aload_1
      //   252: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   255: astore #6
      //   257: aload #6
      //   259: ldc 'gatt.device'
      //   261: invokestatic checkExpressionValueIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
      //   264: aload #5
      //   266: aload #6
      //   268: invokevirtual getAddress : ()Ljava/lang/String;
      //   271: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   274: pop
      //   275: aload #4
      //   277: aload #7
      //   279: aload #5
      //   281: invokevirtual toString : ()Ljava/lang/String;
      //   284: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
      //   287: aload_0
      //   288: getfield work : Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   291: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   294: invokevirtual getDisconnected : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   297: iconst_1
      //   298: invokevirtual setStatus : (Z)V
      //   301: aload_0
      //   302: getfield work : Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   305: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   308: invokevirtual getDisconnected : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   311: invokestatic currentTimeMillis : ()J
      //   314: invokevirtual setTimePerformed : (J)V
      //   317: aload_0
      //   318: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   321: invokestatic access$getTimeoutHandler$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Landroid/os/Handler;
      //   324: aload_0
      //   325: getfield work : Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   328: invokevirtual getTimeoutRunnable : ()Ljava/lang/Runnable;
      //   331: invokevirtual removeCallbacks : (Ljava/lang/Runnable;)V
      //   334: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   337: astore #5
      //   339: aload_0
      //   340: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   343: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   346: astore #7
      //   348: new java/lang/StringBuilder
      //   351: dup
      //   352: invokespecial <init> : ()V
      //   355: astore #4
      //   357: aload #4
      //   359: ldc_w 'Timeout removed for '
      //   362: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   365: pop
      //   366: aload #4
      //   368: aload_0
      //   369: getfield work : Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   372: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   375: invokevirtual getAddress : ()Ljava/lang/String;
      //   378: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   381: pop
      //   382: aload #5
      //   384: aload #7
      //   386: aload #4
      //   388: invokevirtual toString : ()Ljava/lang/String;
      //   391: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
      //   394: aload_0
      //   395: getfield work : Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   398: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   401: invokevirtual getAddress : ()Ljava/lang/String;
      //   404: astore #4
      //   406: aload_0
      //   407: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   410: invokestatic access$getCurrentWork$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   413: astore #5
      //   415: aload #5
      //   417: ifnull -> 442
      //   420: aload #5
      //   422: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   425: astore #5
      //   427: aload #5
      //   429: ifnull -> 442
      //   432: aload #5
      //   434: invokevirtual getAddress : ()Ljava/lang/String;
      //   437: astore #5
      //   439: goto -> 445
      //   442: aconst_null
      //   443: astore #5
      //   445: aload #4
      //   447: aload #5
      //   449: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
      //   452: ifeq -> 466
      //   455: aload_0
      //   456: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   459: aconst_null
      //   460: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
      //   463: invokestatic access$setCurrentWork$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   466: aload_1
      //   467: invokevirtual close : ()V
      //   470: aload_0
      //   471: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   474: aload_0
      //   475: getfield work : Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   478: invokevirtual finishWork : (Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   481: return
    }
    
    public void onMtuChanged(BluetoothGatt param1BluetoothGatt, int param1Int1, int param1Int2) {
      // Byte code:
      //   0: aload_0
      //   1: getfield work : Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   4: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   7: invokevirtual getMtuChanged : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   10: invokevirtual getStatus : ()Z
      //   13: ifne -> 258
      //   16: aload_0
      //   17: getfield work : Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   20: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   23: invokevirtual getMtuChanged : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   26: astore #4
      //   28: iconst_1
      //   29: istore #5
      //   31: aload #4
      //   33: iconst_1
      //   34: invokevirtual setStatus : (Z)V
      //   37: aload_0
      //   38: getfield work : Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   41: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   44: invokevirtual getMtuChanged : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   47: invokestatic currentTimeMillis : ()J
      //   50: invokevirtual setTimePerformed : (J)V
      //   53: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   56: astore #6
      //   58: aload_0
      //   59: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   62: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   65: astore #7
      //   67: new java/lang/StringBuilder
      //   70: dup
      //   71: invokespecial <init> : ()V
      //   74: astore #8
      //   76: aload_1
      //   77: ifnull -> 101
      //   80: aload_1
      //   81: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   84: astore #4
      //   86: aload #4
      //   88: ifnull -> 101
      //   91: aload #4
      //   93: invokevirtual getAddress : ()Ljava/lang/String;
      //   96: astore #4
      //   98: goto -> 104
      //   101: aconst_null
      //   102: astore #4
      //   104: aload #8
      //   106: aload #4
      //   108: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   111: pop
      //   112: aload #8
      //   114: ldc_w ' MTU is '
      //   117: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   120: pop
      //   121: aload #8
      //   123: iload_2
      //   124: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   127: pop
      //   128: aload #8
      //   130: ldc_w '. Was change successful? : '
      //   133: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   136: pop
      //   137: iload_3
      //   138: ifne -> 144
      //   141: goto -> 147
      //   144: iconst_0
      //   145: istore #5
      //   147: aload #8
      //   149: iload #5
      //   151: invokevirtual append : (Z)Ljava/lang/StringBuilder;
      //   154: pop
      //   155: aload #6
      //   157: aload #7
      //   159: aload #8
      //   161: invokevirtual toString : ()Ljava/lang/String;
      //   164: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
      //   167: aload_1
      //   168: ifnull -> 258
      //   171: aload_1
      //   172: invokevirtual discoverServices : ()Z
      //   175: istore #5
      //   177: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   180: astore #4
      //   182: aload_0
      //   183: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   186: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   189: astore #8
      //   191: new java/lang/StringBuilder
      //   194: dup
      //   195: invokespecial <init> : ()V
      //   198: astore #6
      //   200: aload #6
      //   202: ldc_w 'Attempting to start service discovery on '
      //   205: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   208: pop
      //   209: aload_1
      //   210: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   213: astore_1
      //   214: aload_1
      //   215: ldc 'gatt.device'
      //   217: invokestatic checkExpressionValueIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
      //   220: aload #6
      //   222: aload_1
      //   223: invokevirtual getAddress : ()Ljava/lang/String;
      //   226: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   229: pop
      //   230: aload #6
      //   232: ldc ': '
      //   234: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   237: pop
      //   238: aload #6
      //   240: iload #5
      //   242: invokevirtual append : (Z)Ljava/lang/StringBuilder;
      //   245: pop
      //   246: aload #4
      //   248: aload #8
      //   250: aload #6
      //   252: invokevirtual toString : ()Ljava/lang/String;
      //   255: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
      //   258: return
    }
    
    public void onServicesDiscovered(BluetoothGatt param1BluetoothGatt, int param1Int) {
      Intrinsics.checkParameterIsNotNull(param1BluetoothGatt, "gatt");
      if (param1Int != 0) {
        CentralLog.Companion companion = CentralLog.Companion;
        String str = StreetPassWorker.this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No services discovered on ");
        BluetoothDevice bluetoothDevice = param1BluetoothGatt.getDevice();
        Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice, "gatt.device");
        stringBuilder.append(bluetoothDevice.getAddress());
        companion.w(str, stringBuilder.toString());
        endWorkConnection(param1BluetoothGatt);
      } else {
        CentralLog.Companion companion = CentralLog.Companion;
        String str = StreetPassWorker.this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Discovered ");
        stringBuilder.append(param1BluetoothGatt.getServices().size());
        stringBuilder.append(" services on ");
        BluetoothDevice bluetoothDevice = param1BluetoothGatt.getDevice();
        Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice, "gatt.device");
        stringBuilder.append(bluetoothDevice.getAddress());
        companion.i(str, stringBuilder.toString());
        BluetoothGattService bluetoothGattService = param1BluetoothGatt.getService(StreetPassWorker.this.serviceUUID);
        if (bluetoothGattService != null) {
          BluetoothGattCharacteristic bluetoothGattCharacteristic = bluetoothGattService.getCharacteristic(StreetPassWorker.this.characteristicV2);
          if (bluetoothGattCharacteristic != null) {
            PairingFix.INSTANCE.bypassAuthenticationRetry(param1BluetoothGatt);
            boolean bool = param1BluetoothGatt.readCharacteristic(bluetoothGattCharacteristic);
            companion = CentralLog.Companion;
            String str1 = StreetPassWorker.this.TAG;
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("Attempt to read characteristic of our service on ");
            BluetoothDevice bluetoothDevice1 = param1BluetoothGatt.getDevice();
            Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
            stringBuilder1.append(bluetoothDevice1.getAddress());
            stringBuilder1.append(": ");
            stringBuilder1.append(bool);
            companion.i(str1, stringBuilder1.toString());
          } else {
            companion = CentralLog.Companion;
            String str1 = StreetPassWorker.this.TAG;
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("WTF? ");
            BluetoothDevice bluetoothDevice1 = param1BluetoothGatt.getDevice();
            Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
            stringBuilder1.append(bluetoothDevice1.getAddress());
            stringBuilder1.append(" does not have our characteristic");
            companion.e(str1, stringBuilder1.toString());
            endWorkConnection(param1BluetoothGatt);
          } 
        } 
        if (bluetoothGattService == null) {
          CentralLog.Companion companion1 = CentralLog.Companion;
          str = StreetPassWorker.this.TAG;
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("WTF? ");
          BluetoothDevice bluetoothDevice1 = param1BluetoothGatt.getDevice();
          Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
          stringBuilder1.append(bluetoothDevice1.getAddress());
          stringBuilder1.append(" does not have our service");
          companion1.e(str, stringBuilder1.toString());
          endWorkConnection(param1BluetoothGatt);
        } 
      } 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000$\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\b\004\030\0002\0020\001B\005¢\006\002\020\002J\034\020\005\032\0020\0062\b\020\007\032\004\030\0010\b2\b\020\t\032\004\030\0010\nH\026R\016\020\003\032\0020\004XD¢\006\002\n\000¨\006\013"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker$ScannedDeviceReceiver;", "Landroid/content/BroadcastReceiver;", "(Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)V", "TAG", "", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 1, 16})
  public final class ScannedDeviceReceiver extends BroadcastReceiver {
    private final String TAG = "ScannedDeviceReceiver";
    
    public void onReceive(Context param1Context, Intent param1Intent) {
      if (param1Intent != null && Intrinsics.areEqual("ca.albertahealthservices.contacttracing.ACTION_DEVICE_SCANNED", param1Intent.getAction())) {
        boolean bool2;
        BluetoothDevice bluetoothDevice = (BluetoothDevice)param1Intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        ConnectablePeripheral connectablePeripheral = (ConnectablePeripheral)param1Intent.getParcelableExtra("ca.albertahealthservices.contacttracing.CONNECTION_DATA");
        boolean bool1 = true;
        if (bluetoothDevice != null) {
          bool2 = true;
        } else {
          bool2 = false;
        } 
        if (connectablePeripheral == null)
          bool1 = false; 
        CentralLog.Companion companion = CentralLog.Companion;
        String str = this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Device received: ");
        if (bluetoothDevice != null) {
          String str1 = bluetoothDevice.getAddress();
        } else {
          param1Context = null;
        } 
        stringBuilder.append((String)param1Context);
        stringBuilder.append(". Device present: ");
        stringBuilder.append(bool2);
        stringBuilder.append(", Connectable Present: ");
        stringBuilder.append(bool1);
        companion.i(str, stringBuilder.toString());
        if (bluetoothDevice != null && connectablePeripheral != null) {
          Work work = new Work(bluetoothDevice, connectablePeripheral, StreetPassWorker.this.getOnWorkTimeoutListener());
          if (StreetPassWorker.this.addWork(work))
            StreetPassWorker.this.doWork(); 
        } 
      } 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\b\n\000\n\002\020\002\n\000\020\000\032\0020\001H\n¢\006\002\b\002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 1, 16})
  static final class StreetPassWorker$addWork$3 implements Runnable {
    StreetPassWorker$addWork$3(Work param1Work) {}
    
    public final void run() {
      if (StreetPassWorker.this.workQueue.contains(this.$work)) {
        CentralLog.Companion companion = CentralLog.Companion;
        String str = StreetPassWorker.this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Work for ");
        stringBuilder.append(this.$work.getDevice().getAddress());
        stringBuilder.append(" removed from queue? : ");
        stringBuilder.append(StreetPassWorker.this.workQueue.remove(this.$work));
        companion.i(str, stringBuilder.toString());
      } 
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\027\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H\026¨\006\006"}, d2 = {"ca/albertahealthservices/contacttracing/streetpass/StreetPassWorker$onWorkTimeoutListener$1", "Lca/albertahealthservices/contacttracing/streetpass/Work$OnWorkTimeoutListener;", "onWorkTimeout", "", "work", "Lca/albertahealthservices/contacttracing/streetpass/Work;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class StreetPassWorker$onWorkTimeoutListener$1 implements Work.OnWorkTimeoutListener {
    public void onWorkTimeout(Work param1Work) {
      // Byte code:
      //   0: aload_1
      //   1: ldc 'work'
      //   3: invokestatic checkParameterIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
      //   6: aload_0
      //   7: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   10: aload_1
      //   11: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   14: invokevirtual getAddress : ()Ljava/lang/String;
      //   17: invokevirtual isCurrentlyWorkedOn : (Ljava/lang/String;)Z
      //   20: ifne -> 38
      //   23: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   26: aload_0
      //   27: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   30: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   33: ldc 'Work already removed. Timeout ineffective??.'
      //   35: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
      //   38: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   41: astore_2
      //   42: aload_0
      //   43: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   46: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   49: astore_3
      //   50: new java/lang/StringBuilder
      //   53: dup
      //   54: invokespecial <init> : ()V
      //   57: astore #4
      //   59: aload #4
      //   61: ldc 'Work timed out for '
      //   63: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   66: pop
      //   67: aload #4
      //   69: aload_1
      //   70: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   73: invokevirtual getAddress : ()Ljava/lang/String;
      //   76: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   79: pop
      //   80: aload #4
      //   82: ldc ' @ '
      //   84: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   87: pop
      //   88: aload #4
      //   90: aload_1
      //   91: invokevirtual getConnectable : ()Lca/albertahealthservices/contacttracing/streetpass/ConnectablePeripheral;
      //   94: invokevirtual getRssi : ()I
      //   97: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   100: pop
      //   101: aload #4
      //   103: ldc ' queued for '
      //   105: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   108: pop
      //   109: aload #4
      //   111: aload_1
      //   112: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   115: invokevirtual getStarted : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   118: invokevirtual getTimePerformed : ()J
      //   121: aload_1
      //   122: invokevirtual getTimeStamp : ()J
      //   125: lsub
      //   126: invokevirtual append : (J)Ljava/lang/StringBuilder;
      //   129: pop
      //   130: aload #4
      //   132: ldc 'ms'
      //   134: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   137: pop
      //   138: aload_2
      //   139: aload_3
      //   140: aload #4
      //   142: invokevirtual toString : ()Ljava/lang/String;
      //   145: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   148: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   151: astore_3
      //   152: aload_0
      //   153: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   156: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   159: astore #4
      //   161: new java/lang/StringBuilder
      //   164: dup
      //   165: invokespecial <init> : ()V
      //   168: astore_2
      //   169: aload_2
      //   170: aload_1
      //   171: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   174: invokevirtual getAddress : ()Ljava/lang/String;
      //   177: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   180: pop
      //   181: aload_2
      //   182: ldc ' work status: '
      //   184: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   187: pop
      //   188: aload_2
      //   189: aload_1
      //   190: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   193: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   196: pop
      //   197: aload_2
      //   198: bipush #46
      //   200: invokevirtual append : (C)Ljava/lang/StringBuilder;
      //   203: pop
      //   204: aload_3
      //   205: aload #4
      //   207: aload_2
      //   208: invokevirtual toString : ()Ljava/lang/String;
      //   211: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   214: aload_1
      //   215: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   218: invokevirtual getConnected : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   221: invokevirtual getStatus : ()Z
      //   224: ifne -> 443
      //   227: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   230: astore #4
      //   232: aload_0
      //   233: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   236: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   239: astore_2
      //   240: new java/lang/StringBuilder
      //   243: dup
      //   244: invokespecial <init> : ()V
      //   247: astore_3
      //   248: aload_3
      //   249: ldc 'No connection formed for '
      //   251: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   254: pop
      //   255: aload_3
      //   256: aload_1
      //   257: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   260: invokevirtual getAddress : ()Ljava/lang/String;
      //   263: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   266: pop
      //   267: aload #4
      //   269: aload_2
      //   270: aload_3
      //   271: invokevirtual toString : ()Ljava/lang/String;
      //   274: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   277: aload_1
      //   278: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   281: invokevirtual getAddress : ()Ljava/lang/String;
      //   284: astore_2
      //   285: aload_0
      //   286: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   289: invokestatic access$getCurrentWork$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   292: astore #4
      //   294: aload #4
      //   296: ifnull -> 321
      //   299: aload #4
      //   301: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   304: astore #4
      //   306: aload #4
      //   308: ifnull -> 321
      //   311: aload #4
      //   313: invokevirtual getAddress : ()Ljava/lang/String;
      //   316: astore #4
      //   318: goto -> 324
      //   321: aconst_null
      //   322: astore #4
      //   324: aload_2
      //   325: aload #4
      //   327: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
      //   330: ifeq -> 344
      //   333: aload_0
      //   334: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   337: aconst_null
      //   338: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
      //   341: invokestatic access$setCurrentWork$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   344: aload_1
      //   345: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
      //   348: astore #4
      //   350: aload #4
      //   352: ifnull -> 432
      //   355: aload #4
      //   357: invokevirtual close : ()V
      //   360: goto -> 432
      //   363: astore_2
      //   364: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   367: astore #5
      //   369: aload_0
      //   370: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   373: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   376: astore #4
      //   378: new java/lang/StringBuilder
      //   381: dup
      //   382: invokespecial <init> : ()V
      //   385: astore_3
      //   386: aload_3
      //   387: ldc 'Unexpected error while attempting to close clientIf to '
      //   389: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   392: pop
      //   393: aload_3
      //   394: aload_1
      //   395: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   398: invokevirtual getAddress : ()Ljava/lang/String;
      //   401: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   404: pop
      //   405: aload_3
      //   406: ldc ': '
      //   408: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   411: pop
      //   412: aload_3
      //   413: aload_2
      //   414: invokevirtual getLocalizedMessage : ()Ljava/lang/String;
      //   417: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   420: pop
      //   421: aload #5
      //   423: aload #4
      //   425: aload_3
      //   426: invokevirtual toString : ()Ljava/lang/String;
      //   429: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   432: aload_0
      //   433: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   436: aload_1
      //   437: invokevirtual finishWork : (Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   440: goto -> 871
      //   443: aload_1
      //   444: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   447: invokevirtual getConnected : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   450: invokevirtual getStatus : ()Z
      //   453: ifeq -> 805
      //   456: aload_1
      //   457: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   460: invokevirtual getDisconnected : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   463: invokevirtual getStatus : ()Z
      //   466: ifne -> 805
      //   469: aload_1
      //   470: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   473: invokevirtual getReadCharacteristic : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   476: invokevirtual getStatus : ()Z
      //   479: ifne -> 657
      //   482: aload_1
      //   483: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   486: invokevirtual getWriteCharacteristic : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   489: invokevirtual getStatus : ()Z
      //   492: ifne -> 657
      //   495: aload_1
      //   496: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   499: invokevirtual getSkipped : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   502: invokevirtual getStatus : ()Z
      //   505: ifeq -> 511
      //   508: goto -> 657
      //   511: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   514: astore_3
      //   515: aload_0
      //   516: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   519: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   522: astore #4
      //   524: new java/lang/StringBuilder
      //   527: dup
      //   528: invokespecial <init> : ()V
      //   531: astore_2
      //   532: aload_2
      //   533: ldc 'Connected but did nothing for '
      //   535: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   538: pop
      //   539: aload_2
      //   540: aload_1
      //   541: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   544: invokevirtual getAddress : ()Ljava/lang/String;
      //   547: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   550: pop
      //   551: aload_3
      //   552: aload #4
      //   554: aload_2
      //   555: invokevirtual toString : ()Ljava/lang/String;
      //   558: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   561: aload_1
      //   562: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
      //   565: astore #4
      //   567: aload #4
      //   569: ifnull -> 577
      //   572: aload #4
      //   574: invokevirtual disconnect : ()V
      //   577: aload_1
      //   578: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
      //   581: ifnonnull -> 871
      //   584: aload_0
      //   585: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   588: aconst_null
      //   589: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
      //   592: invokestatic access$setCurrentWork$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   595: aload_0
      //   596: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   599: aload_1
      //   600: invokevirtual finishWork : (Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   603: goto -> 871
      //   606: astore #4
      //   608: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   611: astore_3
      //   612: aload_0
      //   613: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   616: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   619: astore_1
      //   620: new java/lang/StringBuilder
      //   623: dup
      //   624: invokespecial <init> : ()V
      //   627: astore_2
      //   628: aload_2
      //   629: ldc 'Failed to clean up work, bluetooth state likely changed or other device's advertiser stopped: '
      //   631: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   634: pop
      //   635: aload_2
      //   636: aload #4
      //   638: invokevirtual getLocalizedMessage : ()Ljava/lang/String;
      //   641: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   644: pop
      //   645: aload_3
      //   646: aload_1
      //   647: aload_2
      //   648: invokevirtual toString : ()Ljava/lang/String;
      //   651: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   654: goto -> 871
      //   657: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   660: astore_2
      //   661: aload_0
      //   662: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   665: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   668: astore_3
      //   669: new java/lang/StringBuilder
      //   672: dup
      //   673: invokespecial <init> : ()V
      //   676: astore #4
      //   678: aload #4
      //   680: ldc 'Connected but did not disconnect in time for '
      //   682: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   685: pop
      //   686: aload #4
      //   688: aload_1
      //   689: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   692: invokevirtual getAddress : ()Ljava/lang/String;
      //   695: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   698: pop
      //   699: aload_2
      //   700: aload_3
      //   701: aload #4
      //   703: invokevirtual toString : ()Ljava/lang/String;
      //   706: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   709: aload_1
      //   710: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
      //   713: astore #4
      //   715: aload #4
      //   717: ifnull -> 725
      //   720: aload #4
      //   722: invokevirtual disconnect : ()V
      //   725: aload_1
      //   726: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
      //   729: ifnonnull -> 871
      //   732: aload_0
      //   733: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   736: aconst_null
      //   737: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
      //   740: invokestatic access$setCurrentWork$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   743: aload_0
      //   744: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   747: aload_1
      //   748: invokevirtual finishWork : (Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   751: goto -> 871
      //   754: astore_3
      //   755: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   758: astore #4
      //   760: aload_0
      //   761: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   764: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   767: astore_2
      //   768: new java/lang/StringBuilder
      //   771: dup
      //   772: invokespecial <init> : ()V
      //   775: astore_1
      //   776: aload_1
      //   777: ldc 'Failed to clean up work, bluetooth state likely changed or other device's advertiser stopped: '
      //   779: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   782: pop
      //   783: aload_1
      //   784: aload_3
      //   785: invokevirtual getLocalizedMessage : ()Ljava/lang/String;
      //   788: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   791: pop
      //   792: aload #4
      //   794: aload_2
      //   795: aload_1
      //   796: invokevirtual toString : ()Ljava/lang/String;
      //   799: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   802: goto -> 871
      //   805: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   808: astore #4
      //   810: aload_0
      //   811: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   814: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   817: astore_2
      //   818: new java/lang/StringBuilder
      //   821: dup
      //   822: invokespecial <init> : ()V
      //   825: astore_3
      //   826: aload_3
      //   827: ldc 'Disconnected but callback not invoked in time. Waiting.: '
      //   829: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   832: pop
      //   833: aload_3
      //   834: aload_1
      //   835: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   838: invokevirtual getAddress : ()Ljava/lang/String;
      //   841: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   844: pop
      //   845: aload_3
      //   846: ldc ': '
      //   848: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   851: pop
      //   852: aload_3
      //   853: aload_1
      //   854: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   857: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   860: pop
      //   861: aload #4
      //   863: aload_2
      //   864: aload_3
      //   865: invokevirtual toString : ()Ljava/lang/String;
      //   868: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   871: return
      // Exception table:
      //   from	to	target	type
      //   344	350	363	java/lang/Exception
      //   355	360	363	java/lang/Exception
      //   561	567	606	finally
      //   572	577	606	finally
      //   577	603	606	finally
      //   709	715	754	finally
      //   720	725	754	finally
      //   725	751	754	finally
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/StreetPassWorker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */