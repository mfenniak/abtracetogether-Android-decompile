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
    //   79: ifeq -> 227
    //   82: aload_0
    //   83: getfield blacklist : Ljava/util/List;
    //   86: checkcast java/lang/Iterable
    //   89: astore_3
    //   90: new java/util/ArrayList
    //   93: dup
    //   94: invokespecial <init> : ()V
    //   97: checkcast java/util/Collection
    //   100: astore #4
    //   102: aload_3
    //   103: invokeinterface iterator : ()Ljava/util/Iterator;
    //   108: astore_2
    //   109: aload_2
    //   110: invokeinterface hasNext : ()Z
    //   115: ifeq -> 157
    //   118: aload_2
    //   119: invokeinterface next : ()Ljava/lang/Object;
    //   124: astore_3
    //   125: aload_3
    //   126: checkcast ca/albertahealthservices/contacttracing/streetpass/BlacklistEntry
    //   129: invokevirtual getUniqueIdentifier : ()Ljava/lang/String;
    //   132: aload_1
    //   133: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   136: invokevirtual getAddress : ()Ljava/lang/String;
    //   139: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   142: ifeq -> 109
    //   145: aload #4
    //   147: aload_3
    //   148: invokeinterface add : (Ljava/lang/Object;)Z
    //   153: pop
    //   154: goto -> 109
    //   157: aload #4
    //   159: checkcast java/util/List
    //   162: checkcast java/util/Collection
    //   165: invokeinterface isEmpty : ()Z
    //   170: iconst_1
    //   171: ixor
    //   172: ifeq -> 227
    //   175: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   178: astore_3
    //   179: aload_0
    //   180: getfield TAG : Ljava/lang/String;
    //   183: astore_2
    //   184: new java/lang/StringBuilder
    //   187: dup
    //   188: invokespecial <init> : ()V
    //   191: astore #4
    //   193: aload #4
    //   195: aload_1
    //   196: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   199: invokevirtual getAddress : ()Ljava/lang/String;
    //   202: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   205: pop
    //   206: aload #4
    //   208: ldc_w ' is in blacklist, not adding to queue'
    //   211: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   214: pop
    //   215: aload_3
    //   216: aload_2
    //   217: aload #4
    //   219: invokevirtual toString : ()Ljava/lang/String;
    //   222: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   225: iconst_0
    //   226: ireturn
    //   227: aload_0
    //   228: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   231: checkcast java/lang/Iterable
    //   234: astore_3
    //   235: new java/util/ArrayList
    //   238: dup
    //   239: invokespecial <init> : ()V
    //   242: checkcast java/util/Collection
    //   245: astore #4
    //   247: aload_3
    //   248: invokeinterface iterator : ()Ljava/util/Iterator;
    //   253: astore_3
    //   254: aload_3
    //   255: invokeinterface hasNext : ()Z
    //   260: ifeq -> 305
    //   263: aload_3
    //   264: invokeinterface next : ()Ljava/lang/Object;
    //   269: astore_2
    //   270: aload_2
    //   271: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
    //   274: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   277: invokevirtual getAddress : ()Ljava/lang/String;
    //   280: aload_1
    //   281: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   284: invokevirtual getAddress : ()Ljava/lang/String;
    //   287: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   290: ifeq -> 254
    //   293: aload #4
    //   295: aload_2
    //   296: invokeinterface add : (Ljava/lang/Object;)Z
    //   301: pop
    //   302: goto -> 254
    //   305: aload #4
    //   307: checkcast java/util/List
    //   310: invokeinterface isEmpty : ()Z
    //   315: ifeq -> 420
    //   318: aload_0
    //   319: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   322: aload_1
    //   323: invokevirtual offer : (Ljava/lang/Object;)Z
    //   326: pop
    //   327: aload_0
    //   328: getfield queueHandler : Landroid/os/Handler;
    //   331: astore #4
    //   333: aload #4
    //   335: ifnonnull -> 344
    //   338: ldc_w 'queueHandler'
    //   341: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
    //   344: aload #4
    //   346: new ca/albertahealthservices/contacttracing/streetpass/StreetPassWorker$addWork$3
    //   349: dup
    //   350: aload_0
    //   351: aload_1
    //   352: invokespecial <init> : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;Lca/albertahealthservices/contacttracing/streetpass/Work;)V
    //   355: checkcast java/lang/Runnable
    //   358: getstatic ca/albertahealthservices/contacttracing/services/BluetoothMonitoringService.Companion : Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Companion;
    //   361: invokevirtual getMaxQueueTime : ()J
    //   364: invokevirtual postDelayed : (Ljava/lang/Runnable;J)Z
    //   367: pop
    //   368: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   371: astore_3
    //   372: aload_0
    //   373: getfield TAG : Ljava/lang/String;
    //   376: astore_2
    //   377: new java/lang/StringBuilder
    //   380: dup
    //   381: invokespecial <init> : ()V
    //   384: astore #4
    //   386: aload #4
    //   388: ldc_w 'Added to work queue: '
    //   391: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   394: pop
    //   395: aload #4
    //   397: aload_1
    //   398: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   401: invokevirtual getAddress : ()Ljava/lang/String;
    //   404: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   407: pop
    //   408: aload_3
    //   409: aload_2
    //   410: aload #4
    //   412: invokevirtual toString : ()Ljava/lang/String;
    //   415: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   418: iconst_1
    //   419: ireturn
    //   420: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   423: astore_3
    //   424: aload_0
    //   425: getfield TAG : Ljava/lang/String;
    //   428: astore #4
    //   430: new java/lang/StringBuilder
    //   433: dup
    //   434: invokespecial <init> : ()V
    //   437: astore_2
    //   438: aload_2
    //   439: aload_1
    //   440: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   443: invokevirtual getAddress : ()Ljava/lang/String;
    //   446: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   449: pop
    //   450: aload_2
    //   451: ldc_w ' is already in work queue'
    //   454: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   457: pop
    //   458: aload_3
    //   459: aload #4
    //   461: aload_2
    //   462: invokevirtual toString : ()Ljava/lang/String;
    //   465: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   468: aload_0
    //   469: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   472: checkcast java/lang/Iterable
    //   475: invokeinterface iterator : ()Ljava/util/Iterator;
    //   480: astore_3
    //   481: aload_3
    //   482: invokeinterface hasNext : ()Z
    //   487: ifeq -> 525
    //   490: aload_3
    //   491: invokeinterface next : ()Ljava/lang/Object;
    //   496: astore #4
    //   498: aload #4
    //   500: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
    //   503: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   506: invokevirtual getAddress : ()Ljava/lang/String;
    //   509: aload_1
    //   510: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   513: invokevirtual getAddress : ()Ljava/lang/String;
    //   516: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   519: ifeq -> 481
    //   522: goto -> 528
    //   525: aconst_null
    //   526: astore #4
    //   528: aload #4
    //   530: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
    //   533: astore #4
    //   535: aload_0
    //   536: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   539: aload #4
    //   541: invokevirtual remove : (Ljava/lang/Object;)Z
    //   544: istore #5
    //   546: aload_0
    //   547: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   550: aload_1
    //   551: invokevirtual offer : (Ljava/lang/Object;)Z
    //   554: istore #6
    //   556: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   559: astore_1
    //   560: aload_0
    //   561: getfield TAG : Ljava/lang/String;
    //   564: astore #4
    //   566: new java/lang/StringBuilder
    //   569: dup
    //   570: invokespecial <init> : ()V
    //   573: astore_3
    //   574: aload_3
    //   575: ldc_w 'Queue entry updated - removed: '
    //   578: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   581: pop
    //   582: aload_3
    //   583: iload #5
    //   585: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   588: pop
    //   589: aload_3
    //   590: ldc_w ', added: '
    //   593: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   596: pop
    //   597: aload_3
    //   598: iload #6
    //   600: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   603: pop
    //   604: aload_1
    //   605: aload #4
    //   607: aload_3
    //   608: invokevirtual toString : ()Ljava/lang/String;
    //   611: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   614: iconst_0
    //   615: ireturn
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
    //   157: astore #5
    //   159: aload_0
    //   160: getfield TAG : Ljava/lang/String;
    //   163: astore #6
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
    //   264: aload #5
    //   266: aload #6
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
    //   292: astore #7
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
    //   313: aload #7
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
    //   464: astore #7
    //   466: aload_0
    //   467: getfield TAG : Ljava/lang/String;
    //   470: astore #4
    //   472: new java/lang/StringBuilder
    //   475: dup
    //   476: invokespecial <init> : ()V
    //   479: astore_1
    //   480: aload_1
    //   481: ldc_w 'Queue size: '
    //   484: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   487: pop
    //   488: aload_1
    //   489: aload_0
    //   490: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   493: invokevirtual size : ()I
    //   496: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   499: pop
    //   500: aload #7
    //   502: aload #4
    //   504: aload_1
    //   505: invokevirtual toString : ()Ljava/lang/String;
    //   508: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   511: aconst_null
    //   512: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
    //   515: astore #4
    //   517: invokestatic currentTimeMillis : ()J
    //   520: lstore #10
    //   522: aload #4
    //   524: astore_1
    //   525: aload_1
    //   526: ifnonnull -> 652
    //   529: aload_0
    //   530: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   533: checkcast java/util/Collection
    //   536: invokeinterface isEmpty : ()Z
    //   541: iconst_1
    //   542: ixor
    //   543: ifeq -> 652
    //   546: aload_0
    //   547: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   550: invokevirtual poll : ()Ljava/lang/Object;
    //   553: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
    //   556: astore #7
    //   558: aload #7
    //   560: astore_1
    //   561: aload #7
    //   563: ifnull -> 525
    //   566: aload #7
    //   568: astore_1
    //   569: lload #10
    //   571: aload #7
    //   573: invokevirtual getTimeStamp : ()J
    //   576: lsub
    //   577: getstatic ca/albertahealthservices/contacttracing/services/BluetoothMonitoringService.Companion : Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Companion;
    //   580: invokevirtual getMaxQueueTime : ()J
    //   583: lcmp
    //   584: ifle -> 525
    //   587: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   590: astore #5
    //   592: aload_0
    //   593: getfield TAG : Ljava/lang/String;
    //   596: astore_1
    //   597: new java/lang/StringBuilder
    //   600: dup
    //   601: invokespecial <init> : ()V
    //   604: astore #6
    //   606: aload #6
    //   608: ldc_w 'Work request for '
    //   611: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   614: pop
    //   615: aload #6
    //   617: aload #7
    //   619: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   622: invokevirtual getAddress : ()Ljava/lang/String;
    //   625: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   628: pop
    //   629: aload #6
    //   631: ldc_w ' too old. Not doing'
    //   634: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   637: pop
    //   638: aload #5
    //   640: aload_1
    //   641: aload #6
    //   643: invokevirtual toString : ()Ljava/lang/String;
    //   646: invokevirtual w : (Ljava/lang/String;Ljava/lang/String;)V
    //   649: goto -> 522
    //   652: aload_1
    //   653: ifnull -> 1475
    //   656: aload_1
    //   657: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   660: astore #7
    //   662: getstatic ca/albertahealthservices/contacttracing/services/BluetoothMonitoringService.Companion : Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Companion;
    //   665: invokevirtual getUseBlacklist : ()Z
    //   668: ifeq -> 834
    //   671: aload_0
    //   672: getfield blacklist : Ljava/util/List;
    //   675: checkcast java/lang/Iterable
    //   678: astore #5
    //   680: new java/util/ArrayList
    //   683: dup
    //   684: invokespecial <init> : ()V
    //   687: checkcast java/util/Collection
    //   690: astore #6
    //   692: aload #5
    //   694: invokeinterface iterator : ()Ljava/util/Iterator;
    //   699: astore #5
    //   701: aload #5
    //   703: invokeinterface hasNext : ()Z
    //   708: ifeq -> 752
    //   711: aload #5
    //   713: invokeinterface next : ()Ljava/lang/Object;
    //   718: astore #12
    //   720: aload #12
    //   722: checkcast ca/albertahealthservices/contacttracing/streetpass/BlacklistEntry
    //   725: invokevirtual getUniqueIdentifier : ()Ljava/lang/String;
    //   728: aload #7
    //   730: invokevirtual getAddress : ()Ljava/lang/String;
    //   733: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   736: ifeq -> 701
    //   739: aload #6
    //   741: aload #12
    //   743: invokeinterface add : (Ljava/lang/Object;)Z
    //   748: pop
    //   749: goto -> 701
    //   752: aload #6
    //   754: checkcast java/util/List
    //   757: checkcast java/util/Collection
    //   760: invokeinterface isEmpty : ()Z
    //   765: iconst_1
    //   766: ixor
    //   767: ifeq -> 834
    //   770: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   773: astore #6
    //   775: aload_0
    //   776: getfield TAG : Ljava/lang/String;
    //   779: astore_1
    //   780: new java/lang/StringBuilder
    //   783: dup
    //   784: invokespecial <init> : ()V
    //   787: astore #4
    //   789: aload #4
    //   791: ldc_w 'Already worked on '
    //   794: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   797: pop
    //   798: aload #4
    //   800: aload #7
    //   802: invokevirtual getAddress : ()Ljava/lang/String;
    //   805: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   808: pop
    //   809: aload #4
    //   811: ldc_w '. Skip.'
    //   814: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   817: pop
    //   818: aload #6
    //   820: aload_1
    //   821: aload #4
    //   823: invokevirtual toString : ()Ljava/lang/String;
    //   826: invokevirtual w : (Ljava/lang/String;Ljava/lang/String;)V
    //   829: aload_0
    //   830: invokevirtual doWork : ()V
    //   833: return
    //   834: aload_0
    //   835: aload #7
    //   837: invokespecial getConnectionStatus : (Landroid/bluetooth/BluetoothDevice;)Z
    //   840: istore_3
    //   841: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   844: astore #12
    //   846: aload_0
    //   847: getfield TAG : Ljava/lang/String;
    //   850: astore #6
    //   852: new java/lang/StringBuilder
    //   855: dup
    //   856: invokespecial <init> : ()V
    //   859: astore #5
    //   861: aload #5
    //   863: ldc_w 'Already connected to '
    //   866: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   869: pop
    //   870: aload #5
    //   872: aload #7
    //   874: invokevirtual getAddress : ()Ljava/lang/String;
    //   877: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   880: pop
    //   881: aload #5
    //   883: ldc_w ' : '
    //   886: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   889: pop
    //   890: aload #5
    //   892: iload_3
    //   893: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   896: pop
    //   897: aload #12
    //   899: aload #6
    //   901: aload #5
    //   903: invokevirtual toString : ()Ljava/lang/String;
    //   906: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   909: iload_3
    //   910: ifeq -> 945
    //   913: aload_1
    //   914: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
    //   917: invokevirtual getSkipped : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
    //   920: iconst_1
    //   921: invokevirtual setStatus : (Z)V
    //   924: aload_1
    //   925: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
    //   928: invokevirtual getSkipped : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
    //   931: invokestatic currentTimeMillis : ()J
    //   934: invokevirtual setTimePerformed : (J)V
    //   937: aload_0
    //   938: aload_1
    //   939: invokevirtual finishWork : (Lca/albertahealthservices/contacttracing/streetpass/Work;)V
    //   942: goto -> 1475
    //   945: new ca/albertahealthservices/contacttracing/streetpass/StreetPassWorker$CentralGattCallback
    //   948: dup
    //   949: aload_0
    //   950: aload_1
    //   951: invokespecial <init> : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;Lca/albertahealthservices/contacttracing/streetpass/Work;)V
    //   954: astore #12
    //   956: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   959: astore #6
    //   961: aload_0
    //   962: getfield TAG : Ljava/lang/String;
    //   965: astore #5
    //   967: new java/lang/StringBuilder
    //   970: dup
    //   971: invokespecial <init> : ()V
    //   974: astore #13
    //   976: aload #13
    //   978: ldc_w 'Starting work - connecting to device: '
    //   981: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   984: pop
    //   985: aload #13
    //   987: aload #7
    //   989: invokevirtual getAddress : ()Ljava/lang/String;
    //   992: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   995: pop
    //   996: aload #13
    //   998: ldc_w ' @ '
    //   1001: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1004: pop
    //   1005: aload #13
    //   1007: aload_1
    //   1008: invokevirtual getConnectable : ()Lca/albertahealthservices/contacttracing/streetpass/ConnectablePeripheral;
    //   1011: invokevirtual getRssi : ()I
    //   1014: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1017: pop
    //   1018: aload #13
    //   1020: bipush #32
    //   1022: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   1025: pop
    //   1026: aload #13
    //   1028: invokestatic currentTimeMillis : ()J
    //   1031: aload_1
    //   1032: invokevirtual getTimeStamp : ()J
    //   1035: lsub
    //   1036: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   1039: pop
    //   1040: aload #13
    //   1042: ldc_w 'ms ago'
    //   1045: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1048: pop
    //   1049: aload #6
    //   1051: aload #5
    //   1053: aload #13
    //   1055: invokevirtual toString : ()Ljava/lang/String;
    //   1058: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   1061: aload_0
    //   1062: aload_1
    //   1063: putfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   1066: aload_1
    //   1067: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
    //   1070: invokevirtual getStarted : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
    //   1073: iconst_1
    //   1074: invokevirtual setStatus : (Z)V
    //   1077: aload_1
    //   1078: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
    //   1081: invokevirtual getStarted : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
    //   1084: invokestatic currentTimeMillis : ()J
    //   1087: invokevirtual setTimePerformed : (J)V
    //   1090: aload_1
    //   1091: aload_0
    //   1092: getfield context : Landroid/content/Context;
    //   1095: aload #12
    //   1097: invokevirtual startWork : (Landroid/content/Context;Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker$CentralGattCallback;)V
    //   1100: aload_1
    //   1101: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
    //   1104: astore #6
    //   1106: aload #6
    //   1108: ifnull -> 1117
    //   1111: aload #6
    //   1113: invokevirtual connect : ()Z
    //   1116: istore_2
    //   1117: iload_2
    //   1118: ifne -> 1211
    //   1121: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1124: astore #5
    //   1126: aload_0
    //   1127: getfield TAG : Ljava/lang/String;
    //   1130: astore #12
    //   1132: new java/lang/StringBuilder
    //   1135: astore #6
    //   1137: aload #6
    //   1139: invokespecial <init> : ()V
    //   1142: aload #6
    //   1144: ldc_w 'Alamak! not connecting to '
    //   1147: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1150: pop
    //   1151: aload #6
    //   1153: aload_1
    //   1154: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   1157: invokevirtual getAddress : ()Ljava/lang/String;
    //   1160: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1163: pop
    //   1164: aload #6
    //   1166: ldc_w '??'
    //   1169: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1172: pop
    //   1173: aload #5
    //   1175: aload #12
    //   1177: aload #6
    //   1179: invokevirtual toString : ()Ljava/lang/String;
    //   1182: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1185: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1188: aload_0
    //   1189: getfield TAG : Ljava/lang/String;
    //   1192: ldc_w 'Moving on to next task'
    //   1195: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1198: aload_0
    //   1199: aconst_null
    //   1200: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
    //   1203: putfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   1206: aload_0
    //   1207: invokevirtual doWork : ()V
    //   1210: return
    //   1211: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1214: astore #12
    //   1216: aload_0
    //   1217: getfield TAG : Ljava/lang/String;
    //   1220: astore #6
    //   1222: new java/lang/StringBuilder
    //   1225: astore #5
    //   1227: aload #5
    //   1229: invokespecial <init> : ()V
    //   1232: aload #5
    //   1234: ldc_w 'Connection to '
    //   1237: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1240: pop
    //   1241: aload #5
    //   1243: aload_1
    //   1244: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   1247: invokevirtual getAddress : ()Ljava/lang/String;
    //   1250: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1253: pop
    //   1254: aload #5
    //   1256: ldc_w ' attempt in progress'
    //   1259: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1262: pop
    //   1263: aload #12
    //   1265: aload #6
    //   1267: aload #5
    //   1269: invokevirtual toString : ()Ljava/lang/String;
    //   1272: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   1275: aload_0
    //   1276: getfield timeoutHandler : Landroid/os/Handler;
    //   1279: astore #6
    //   1281: aload #6
    //   1283: ifnonnull -> 1291
    //   1286: ldc 'timeoutHandler'
    //   1288: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
    //   1291: aload #6
    //   1293: aload_1
    //   1294: invokevirtual getTimeoutRunnable : ()Ljava/lang/Runnable;
    //   1297: getstatic ca/albertahealthservices/contacttracing/services/BluetoothMonitoringService.Companion : Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Companion;
    //   1300: invokevirtual getConnectionTimeout : ()J
    //   1303: invokevirtual postDelayed : (Ljava/lang/Runnable;J)Z
    //   1306: pop
    //   1307: aload_1
    //   1308: invokestatic currentTimeMillis : ()J
    //   1311: getstatic ca/albertahealthservices/contacttracing/services/BluetoothMonitoringService.Companion : Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Companion;
    //   1314: invokevirtual getConnectionTimeout : ()J
    //   1317: ladd
    //   1318: invokevirtual setTimeout : (J)V
    //   1321: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1324: astore #12
    //   1326: aload_0
    //   1327: getfield TAG : Ljava/lang/String;
    //   1330: astore #5
    //   1332: new java/lang/StringBuilder
    //   1335: astore #6
    //   1337: aload #6
    //   1339: invokespecial <init> : ()V
    //   1342: aload #6
    //   1344: ldc_w 'Timeout scheduled for '
    //   1347: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1350: pop
    //   1351: aload #6
    //   1353: aload_1
    //   1354: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   1357: invokevirtual getAddress : ()Ljava/lang/String;
    //   1360: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1363: pop
    //   1364: aload #12
    //   1366: aload #5
    //   1368: aload #6
    //   1370: invokevirtual toString : ()Ljava/lang/String;
    //   1373: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   1376: goto -> 1475
    //   1379: astore_1
    //   1380: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1383: astore #12
    //   1385: aload_0
    //   1386: getfield TAG : Ljava/lang/String;
    //   1389: astore #6
    //   1391: new java/lang/StringBuilder
    //   1394: dup
    //   1395: invokespecial <init> : ()V
    //   1398: astore #5
    //   1400: aload #5
    //   1402: ldc_w 'Unexpected error while attempting to connect to '
    //   1405: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1408: pop
    //   1409: aload #5
    //   1411: aload #7
    //   1413: invokevirtual getAddress : ()Ljava/lang/String;
    //   1416: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1419: pop
    //   1420: aload #5
    //   1422: ldc_w ': '
    //   1425: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1428: pop
    //   1429: aload #5
    //   1431: aload_1
    //   1432: invokevirtual getLocalizedMessage : ()Ljava/lang/String;
    //   1435: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1438: pop
    //   1439: aload #12
    //   1441: aload #6
    //   1443: aload #5
    //   1445: invokevirtual toString : ()Ljava/lang/String;
    //   1448: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1451: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1454: aload_0
    //   1455: getfield TAG : Ljava/lang/String;
    //   1458: ldc_w 'Moving on to next task'
    //   1461: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1464: aload_0
    //   1465: aload #4
    //   1467: putfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   1470: aload_0
    //   1471: invokevirtual doWork : ()V
    //   1474: return
    //   1475: aload_1
    //   1476: ifnonnull -> 1492
    //   1479: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1482: aload_0
    //   1483: getfield TAG : Ljava/lang/String;
    //   1486: ldc_w 'No outstanding work'
    //   1489: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   1492: return
    // Exception table:
    //   from	to	target	type
    //   1066	1106	1379	finally
    //   1111	1117	1379	finally
    //   1121	1210	1379	finally
    //   1211	1281	1379	finally
    //   1286	1291	1379	finally
    //   1291	1376	1379	finally
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
    CentralLog.Companion companion1 = CentralLog.Companion;
    String str2 = this.TAG;
    StringBuilder stringBuilder3 = new StringBuilder();
    stringBuilder3.append("Work on ");
    stringBuilder3.append(paramWork.getDevice().getAddress());
    stringBuilder3.append(" stopped in: ");
    stringBuilder3.append(paramWork.getChecklist().getDisconnected().getTimePerformed() - paramWork.getChecklist().getStarted().getTimePerformed());
    companion1.i(str2, stringBuilder3.toString());
    CentralLog.Companion companion3 = CentralLog.Companion;
    String str1 = this.TAG;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("Work on ");
    stringBuilder1.append(paramWork.getDevice().getAddress());
    stringBuilder1.append(" completed?: ");
    stringBuilder1.append(paramWork.isCriticalsCompleted());
    stringBuilder1.append(". Connected in: ");
    stringBuilder1.append(paramWork.getChecklist().getConnected().getTimePerformed() - paramWork.getChecklist().getStarted().getTimePerformed());
    stringBuilder1.append(". connection lasted for: ");
    stringBuilder1.append(paramWork.getChecklist().getDisconnected().getTimePerformed() - paramWork.getChecklist().getConnected().getTimePerformed());
    stringBuilder1.append(". Status: ");
    stringBuilder1.append(paramWork.getChecklist());
    companion3.i(str1, stringBuilder1.toString());
    Handler handler = this.timeoutHandler;
    if (handler == null)
      Intrinsics.throwUninitializedPropertyAccessException("timeoutHandler"); 
    handler.removeCallbacks(paramWork.getTimeoutRunnable());
    CentralLog.Companion companion2 = CentralLog.Companion;
    str1 = this.TAG;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("Timeout removed for ");
    stringBuilder2.append(paramWork.getDevice().getAddress());
    companion2.i(str1, stringBuilder2.toString());
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
        String str2 = param1Intent.getStringExtra("ca.albertahealthservices.contacttracing.DEVICE_ADDRESS");
        CentralLog.Companion companion = CentralLog.Companion;
        String str1 = StreetPassWorker.this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Adding to blacklist: ");
        stringBuilder.append(str2);
        companion.d(str1, stringBuilder.toString());
        Intrinsics.checkExpressionValueIsNotNull(str2, "deviceAddress");
        BlacklistEntry blacklistEntry = new BlacklistEntry(str2, System.currentTimeMillis());
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
      StringBuilder stringBuilder1;
      Intrinsics.checkParameterIsNotNull(param1BluetoothGatt, "gatt");
      Intrinsics.checkParameterIsNotNull(param1BluetoothGattCharacteristic, "characteristic");
      CentralLog.Companion companion = CentralLog.Companion;
      String str = StreetPassWorker.this.TAG;
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("Read Status: ");
      stringBuilder2.append(param1Int);
      companion.i(str, stringBuilder2.toString());
      if (param1Int != 0) {
        CentralLog.Companion companion1 = CentralLog.Companion;
        String str1 = StreetPassWorker.this.TAG;
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Failed to read characteristics from ");
        BluetoothDevice bluetoothDevice1 = param1BluetoothGatt.getDevice();
        Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
        stringBuilder2.append(bluetoothDevice1.getAddress());
        stringBuilder2.append(": ");
        stringBuilder2.append(param1Int);
        companion1.w(str1, stringBuilder2.toString());
      } else {
        CentralLog.Companion companion2 = CentralLog.Companion;
        String str2 = StreetPassWorker.this.TAG;
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("Characteristic read from ");
        BluetoothDevice bluetoothDevice1 = param1BluetoothGatt.getDevice();
        Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
        stringBuilder3.append(bluetoothDevice1.getAddress());
        stringBuilder3.append(": ");
        stringBuilder3.append(param1BluetoothGattCharacteristic.getStringValue(0));
        companion2.i(str2, stringBuilder3.toString());
        CentralLog.Companion companion1 = CentralLog.Companion;
        String str1 = StreetPassWorker.this.TAG;
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append("onCharacteristicRead: ");
        stringBuilder4.append(this.work.getDevice().getAddress());
        stringBuilder4.append(" - [");
        stringBuilder4.append(this.work.getConnectable().getRssi());
        stringBuilder4.append(']');
        companion1.i(str1, stringBuilder4.toString());
        if (BlueTrace.INSTANCE.supportsCharUUID(param1BluetoothGattCharacteristic.getUuid()))
          try {
            BlueTrace blueTrace = BlueTrace.INSTANCE;
            UUID uUID = param1BluetoothGattCharacteristic.getUuid();
            Intrinsics.checkExpressionValueIsNotNull(uUID, "characteristic.uuid");
            BlueTraceProtocol blueTraceProtocol = blueTrace.getImplementation(uUID);
            byte[] arrayOfByte = param1BluetoothGattCharacteristic.getValue();
          } finally {
            stringBuilder4 = null;
            CentralLog.Companion companion3 = CentralLog.Companion;
            String str3 = StreetPassWorker.this.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to process read payload - ");
            stringBuilder.append(stringBuilder4.getMessage());
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
          CentralLog.Companion companion1 = CentralLog.Companion;
          String str1 = StreetPassWorker.this.TAG;
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Attempt to write characteristic to our service on ");
          bluetoothDevice = param1BluetoothGatt.getDevice();
          Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice, "gatt.device");
          stringBuilder1.append(bluetoothDevice.getAddress());
          stringBuilder1.append(": ");
          stringBuilder1.append(bool);
          companion1.i(str1, stringBuilder1.toString());
        } else {
          CentralLog.Companion companion1 = CentralLog.Companion;
          String str1 = StreetPassWorker.this.TAG;
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Expired BM. Skipping attempt to write characteristic to our service on ");
          BluetoothDevice bluetoothDevice1 = bluetoothDevice.getDevice();
          Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
          stringBuilder1.append(bluetoothDevice1.getAddress());
          companion1.i(str1, stringBuilder1.toString());
          endWorkConnection((BluetoothGatt)bluetoothDevice);
        } 
      } else {
        CentralLog.Companion companion1 = CentralLog.Companion;
        str = StreetPassWorker.this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not writing to ");
        BluetoothDevice bluetoothDevice1 = bluetoothDevice.getDevice();
        Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
        stringBuilder.append(bluetoothDevice1.getAddress());
        stringBuilder.append(". Characteristic ");
        stringBuilder.append(stringBuilder1.getUuid());
        stringBuilder.append(" is not supported");
        companion1.w(str, stringBuilder.toString());
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
      //   107: astore #7
      //   109: aload_0
      //   110: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   113: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   116: astore #5
      //   118: new java/lang/StringBuilder
      //   121: dup
      //   122: invokespecial <init> : ()V
      //   125: astore #6
      //   127: aload #6
      //   129: ldc_w 'Connected to other GATT server - '
      //   132: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   135: pop
      //   136: aload_1
      //   137: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   140: astore #4
      //   142: aload #4
      //   144: ldc 'gatt.device'
      //   146: invokestatic checkExpressionValueIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
      //   149: aload #6
      //   151: aload #4
      //   153: invokevirtual getAddress : ()Ljava/lang/String;
      //   156: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   159: pop
      //   160: aload #7
      //   162: aload #5
      //   164: aload #6
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
      //   231: astore #5
      //   233: new java/lang/StringBuilder
      //   236: dup
      //   237: invokespecial <init> : ()V
      //   240: astore #7
      //   242: aload #7
      //   244: ldc_w 'Disconnected from other GATT server - '
      //   247: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   250: pop
      //   251: aload_1
      //   252: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   255: astore #6
      //   257: aload #6
      //   259: ldc 'gatt.device'
      //   261: invokestatic checkExpressionValueIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
      //   264: aload #7
      //   266: aload #6
      //   268: invokevirtual getAddress : ()Ljava/lang/String;
      //   271: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   274: pop
      //   275: aload #4
      //   277: aload #5
      //   279: aload #7
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
      //   404: astore #7
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
      //   445: aload #7
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
            boolean bool = param1BluetoothGatt.readCharacteristic(bluetoothGattCharacteristic);
            CentralLog.Companion companion1 = CentralLog.Companion;
            String str1 = StreetPassWorker.this.TAG;
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("Attempt to read characteristic of our service on ");
            BluetoothDevice bluetoothDevice1 = param1BluetoothGatt.getDevice();
            Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
            stringBuilder1.append(bluetoothDevice1.getAddress());
            stringBuilder1.append(": ");
            stringBuilder1.append(bool);
            companion1.i(str1, stringBuilder1.toString());
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
          String str1 = StreetPassWorker.this.TAG;
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("WTF? ");
          BluetoothDevice bluetoothDevice1 = param1BluetoothGatt.getDevice();
          Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
          stringBuilder1.append(bluetoothDevice1.getAddress());
          stringBuilder1.append(" does not have our service");
          companion1.e(str1, stringBuilder1.toString());
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
      //   151: astore #4
      //   153: aload_0
      //   154: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   157: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   160: astore_2
      //   161: new java/lang/StringBuilder
      //   164: dup
      //   165: invokespecial <init> : ()V
      //   168: astore_3
      //   169: aload_3
      //   170: aload_1
      //   171: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   174: invokevirtual getAddress : ()Ljava/lang/String;
      //   177: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   180: pop
      //   181: aload_3
      //   182: ldc ' work status: '
      //   184: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   187: pop
      //   188: aload_3
      //   189: aload_1
      //   190: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   193: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   196: pop
      //   197: aload_3
      //   198: bipush #46
      //   200: invokevirtual append : (C)Ljava/lang/StringBuilder;
      //   203: pop
      //   204: aload #4
      //   206: aload_2
      //   207: aload_3
      //   208: invokevirtual toString : ()Ljava/lang/String;
      //   211: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   214: aload_1
      //   215: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   218: invokevirtual getConnected : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   221: invokevirtual getStatus : ()Z
      //   224: ifne -> 437
      //   227: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   230: astore_2
      //   231: aload_0
      //   232: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   235: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   238: astore_3
      //   239: new java/lang/StringBuilder
      //   242: dup
      //   243: invokespecial <init> : ()V
      //   246: astore #4
      //   248: aload #4
      //   250: ldc 'No connection formed for '
      //   252: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   255: pop
      //   256: aload #4
      //   258: aload_1
      //   259: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   262: invokevirtual getAddress : ()Ljava/lang/String;
      //   265: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   268: pop
      //   269: aload_2
      //   270: aload_3
      //   271: aload #4
      //   273: invokevirtual toString : ()Ljava/lang/String;
      //   276: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   279: aload_1
      //   280: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   283: invokevirtual getAddress : ()Ljava/lang/String;
      //   286: astore_2
      //   287: aload_0
      //   288: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   291: invokestatic access$getCurrentWork$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   294: astore_3
      //   295: aload_3
      //   296: ifnull -> 316
      //   299: aload_3
      //   300: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   303: astore_3
      //   304: aload_3
      //   305: ifnull -> 316
      //   308: aload_3
      //   309: invokevirtual getAddress : ()Ljava/lang/String;
      //   312: astore_3
      //   313: goto -> 318
      //   316: aconst_null
      //   317: astore_3
      //   318: aload_2
      //   319: aload_3
      //   320: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
      //   323: ifeq -> 337
      //   326: aload_0
      //   327: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   330: aconst_null
      //   331: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
      //   334: invokestatic access$setCurrentWork$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   337: aload_1
      //   338: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
      //   341: astore_3
      //   342: aload_3
      //   343: ifnull -> 426
      //   346: aload_3
      //   347: invokevirtual close : ()V
      //   350: goto -> 426
      //   353: astore #4
      //   355: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   358: astore_3
      //   359: aload_0
      //   360: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   363: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   366: astore_2
      //   367: new java/lang/StringBuilder
      //   370: dup
      //   371: invokespecial <init> : ()V
      //   374: astore #5
      //   376: aload #5
      //   378: ldc 'Unexpected error while attempting to close clientIf to '
      //   380: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   383: pop
      //   384: aload #5
      //   386: aload_1
      //   387: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   390: invokevirtual getAddress : ()Ljava/lang/String;
      //   393: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   396: pop
      //   397: aload #5
      //   399: ldc ': '
      //   401: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   404: pop
      //   405: aload #5
      //   407: aload #4
      //   409: invokevirtual getLocalizedMessage : ()Ljava/lang/String;
      //   412: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   415: pop
      //   416: aload_3
      //   417: aload_2
      //   418: aload #5
      //   420: invokevirtual toString : ()Ljava/lang/String;
      //   423: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   426: aload_0
      //   427: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   430: aload_1
      //   431: invokevirtual finishWork : (Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   434: goto -> 859
      //   437: aload_1
      //   438: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   441: invokevirtual getConnected : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   444: invokevirtual getStatus : ()Z
      //   447: ifeq -> 793
      //   450: aload_1
      //   451: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   454: invokevirtual getDisconnected : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   457: invokevirtual getStatus : ()Z
      //   460: ifne -> 793
      //   463: aload_1
      //   464: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   467: invokevirtual getReadCharacteristic : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   470: invokevirtual getStatus : ()Z
      //   473: ifne -> 648
      //   476: aload_1
      //   477: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   480: invokevirtual getWriteCharacteristic : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   483: invokevirtual getStatus : ()Z
      //   486: ifne -> 648
      //   489: aload_1
      //   490: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   493: invokevirtual getSkipped : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   496: invokevirtual getStatus : ()Z
      //   499: ifeq -> 505
      //   502: goto -> 648
      //   505: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   508: astore #4
      //   510: aload_0
      //   511: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   514: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   517: astore_2
      //   518: new java/lang/StringBuilder
      //   521: dup
      //   522: invokespecial <init> : ()V
      //   525: astore_3
      //   526: aload_3
      //   527: ldc 'Connected but did nothing for '
      //   529: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   532: pop
      //   533: aload_3
      //   534: aload_1
      //   535: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   538: invokevirtual getAddress : ()Ljava/lang/String;
      //   541: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   544: pop
      //   545: aload #4
      //   547: aload_2
      //   548: aload_3
      //   549: invokevirtual toString : ()Ljava/lang/String;
      //   552: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   555: aload_1
      //   556: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
      //   559: astore_3
      //   560: aload_3
      //   561: ifnull -> 568
      //   564: aload_3
      //   565: invokevirtual disconnect : ()V
      //   568: aload_1
      //   569: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
      //   572: ifnonnull -> 859
      //   575: aload_0
      //   576: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   579: aconst_null
      //   580: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
      //   583: invokestatic access$setCurrentWork$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   586: aload_0
      //   587: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   590: aload_1
      //   591: invokevirtual finishWork : (Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   594: goto -> 859
      //   597: astore #4
      //   599: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   602: astore_1
      //   603: aload_0
      //   604: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   607: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   610: astore_2
      //   611: new java/lang/StringBuilder
      //   614: dup
      //   615: invokespecial <init> : ()V
      //   618: astore_3
      //   619: aload_3
      //   620: ldc 'Failed to clean up work, bluetooth state likely changed or other device's advertiser stopped: '
      //   622: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   625: pop
      //   626: aload_3
      //   627: aload #4
      //   629: invokevirtual getLocalizedMessage : ()Ljava/lang/String;
      //   632: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   635: pop
      //   636: aload_1
      //   637: aload_2
      //   638: aload_3
      //   639: invokevirtual toString : ()Ljava/lang/String;
      //   642: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   645: goto -> 859
      //   648: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   651: astore_2
      //   652: aload_0
      //   653: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   656: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   659: astore_3
      //   660: new java/lang/StringBuilder
      //   663: dup
      //   664: invokespecial <init> : ()V
      //   667: astore #4
      //   669: aload #4
      //   671: ldc 'Connected but did not disconnect in time for '
      //   673: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   676: pop
      //   677: aload #4
      //   679: aload_1
      //   680: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   683: invokevirtual getAddress : ()Ljava/lang/String;
      //   686: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   689: pop
      //   690: aload_2
      //   691: aload_3
      //   692: aload #4
      //   694: invokevirtual toString : ()Ljava/lang/String;
      //   697: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   700: aload_1
      //   701: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
      //   704: astore_3
      //   705: aload_3
      //   706: ifnull -> 713
      //   709: aload_3
      //   710: invokevirtual disconnect : ()V
      //   713: aload_1
      //   714: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
      //   717: ifnonnull -> 859
      //   720: aload_0
      //   721: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   724: aconst_null
      //   725: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
      //   728: invokestatic access$setCurrentWork$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   731: aload_0
      //   732: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   735: aload_1
      //   736: invokevirtual finishWork : (Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   739: goto -> 859
      //   742: astore #4
      //   744: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   747: astore_2
      //   748: aload_0
      //   749: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   752: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   755: astore_3
      //   756: new java/lang/StringBuilder
      //   759: dup
      //   760: invokespecial <init> : ()V
      //   763: astore_1
      //   764: aload_1
      //   765: ldc 'Failed to clean up work, bluetooth state likely changed or other device's advertiser stopped: '
      //   767: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   770: pop
      //   771: aload_1
      //   772: aload #4
      //   774: invokevirtual getLocalizedMessage : ()Ljava/lang/String;
      //   777: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   780: pop
      //   781: aload_2
      //   782: aload_3
      //   783: aload_1
      //   784: invokevirtual toString : ()Ljava/lang/String;
      //   787: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   790: goto -> 859
      //   793: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   796: astore_2
      //   797: aload_0
      //   798: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   801: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   804: astore #4
      //   806: new java/lang/StringBuilder
      //   809: dup
      //   810: invokespecial <init> : ()V
      //   813: astore_3
      //   814: aload_3
      //   815: ldc 'Disconnected but callback not invoked in time. Waiting.: '
      //   817: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   820: pop
      //   821: aload_3
      //   822: aload_1
      //   823: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   826: invokevirtual getAddress : ()Ljava/lang/String;
      //   829: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   832: pop
      //   833: aload_3
      //   834: ldc ': '
      //   836: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   839: pop
      //   840: aload_3
      //   841: aload_1
      //   842: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   845: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   848: pop
      //   849: aload_2
      //   850: aload #4
      //   852: aload_3
      //   853: invokevirtual toString : ()Ljava/lang/String;
      //   856: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   859: return
      // Exception table:
      //   from	to	target	type
      //   337	342	353	java/lang/Exception
      //   346	350	353	java/lang/Exception
      //   555	560	597	finally
      //   564	568	597	finally
      //   568	594	597	finally
      //   700	705	742	finally
      //   709	713	742	finally
      //   713	739	742	finally
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/StreetPassWorker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */