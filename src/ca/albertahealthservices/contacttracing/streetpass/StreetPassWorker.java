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
    String str = this.TAG;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Service UUID ");
    stringBuilder.append(this.serviceUUID);
    companion.d(str, stringBuilder.toString());
    companion = CentralLog.Companion;
    str = this.TAG;
    stringBuilder = new StringBuilder();
    stringBuilder.append("characteristicV2 ");
    stringBuilder.append(this.characteristicV2);
    companion.d(str, stringBuilder.toString());
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
    //   178: astore_2
    //   179: aload_0
    //   180: getfield TAG : Ljava/lang/String;
    //   183: astore_3
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
    //   215: aload_2
    //   216: aload_3
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
    //   253: astore_2
    //   254: aload_2
    //   255: invokeinterface hasNext : ()Z
    //   260: ifeq -> 305
    //   263: aload_2
    //   264: invokeinterface next : ()Ljava/lang/Object;
    //   269: astore_3
    //   270: aload_3
    //   271: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
    //   274: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   277: invokevirtual getAddress : ()Ljava/lang/String;
    //   280: aload_1
    //   281: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   284: invokevirtual getAddress : ()Ljava/lang/String;
    //   287: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   290: ifeq -> 254
    //   293: aload #4
    //   295: aload_3
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
    //   371: astore_2
    //   372: aload_0
    //   373: getfield TAG : Ljava/lang/String;
    //   376: astore_3
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
    //   408: aload_2
    //   409: aload_3
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
    //   559: astore #4
    //   561: aload_0
    //   562: getfield TAG : Ljava/lang/String;
    //   565: astore_1
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
    //   604: aload #4
    //   606: aload_1
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
    //   163: astore #7
    //   165: new java/lang/StringBuilder
    //   168: dup
    //   169: invokespecial <init> : ()V
    //   172: astore #6
    //   174: aload #6
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
    //   211: aload #6
    //   213: aload_1
    //   214: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   217: pop
    //   218: aload #6
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
    //   241: aload #6
    //   243: iload_3
    //   244: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   247: pop
    //   248: aload #6
    //   250: ldc_w ', timedout: '
    //   253: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   256: pop
    //   257: aload #6
    //   259: iload_2
    //   260: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   263: pop
    //   264: aload #5
    //   266: aload #7
    //   268: aload #6
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
    //   333: astore #5
    //   335: new java/lang/StringBuilder
    //   338: dup
    //   339: invokespecial <init> : ()V
    //   342: astore #6
    //   344: aload #6
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
    //   388: aload #6
    //   390: aload_1
    //   391: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   394: pop
    //   395: aload #7
    //   397: aload #5
    //   399: aload #6
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
    //   464: astore #5
    //   466: aload_0
    //   467: getfield TAG : Ljava/lang/String;
    //   470: astore_1
    //   471: new java/lang/StringBuilder
    //   474: dup
    //   475: invokespecial <init> : ()V
    //   478: astore #4
    //   480: aload #4
    //   482: ldc_w 'Queue size: '
    //   485: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   488: pop
    //   489: aload #4
    //   491: aload_0
    //   492: getfield workQueue : Ljava/util/concurrent/PriorityBlockingQueue;
    //   495: invokevirtual size : ()I
    //   498: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   501: pop
    //   502: aload #5
    //   504: aload_1
    //   505: aload #4
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
    //   652: ifnull -> 1471
    //   655: aload_1
    //   656: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   659: astore #5
    //   661: getstatic ca/albertahealthservices/contacttracing/services/BluetoothMonitoringService.Companion : Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Companion;
    //   664: invokevirtual getUseBlacklist : ()Z
    //   667: ifeq -> 830
    //   670: aload_0
    //   671: getfield blacklist : Ljava/util/List;
    //   674: checkcast java/lang/Iterable
    //   677: astore #6
    //   679: new java/util/ArrayList
    //   682: dup
    //   683: invokespecial <init> : ()V
    //   686: checkcast java/util/Collection
    //   689: astore #7
    //   691: aload #6
    //   693: invokeinterface iterator : ()Ljava/util/Iterator;
    //   698: astore #12
    //   700: aload #12
    //   702: invokeinterface hasNext : ()Z
    //   707: ifeq -> 751
    //   710: aload #12
    //   712: invokeinterface next : ()Ljava/lang/Object;
    //   717: astore #6
    //   719: aload #6
    //   721: checkcast ca/albertahealthservices/contacttracing/streetpass/BlacklistEntry
    //   724: invokevirtual getUniqueIdentifier : ()Ljava/lang/String;
    //   727: aload #5
    //   729: invokevirtual getAddress : ()Ljava/lang/String;
    //   732: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   735: ifeq -> 700
    //   738: aload #7
    //   740: aload #6
    //   742: invokeinterface add : (Ljava/lang/Object;)Z
    //   747: pop
    //   748: goto -> 700
    //   751: aload #7
    //   753: checkcast java/util/List
    //   756: checkcast java/util/Collection
    //   759: invokeinterface isEmpty : ()Z
    //   764: iconst_1
    //   765: ixor
    //   766: ifeq -> 830
    //   769: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   772: astore #7
    //   774: aload_0
    //   775: getfield TAG : Ljava/lang/String;
    //   778: astore #4
    //   780: new java/lang/StringBuilder
    //   783: dup
    //   784: invokespecial <init> : ()V
    //   787: astore_1
    //   788: aload_1
    //   789: ldc_w 'Already worked on '
    //   792: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   795: pop
    //   796: aload_1
    //   797: aload #5
    //   799: invokevirtual getAddress : ()Ljava/lang/String;
    //   802: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   805: pop
    //   806: aload_1
    //   807: ldc_w '. Skip.'
    //   810: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   813: pop
    //   814: aload #7
    //   816: aload #4
    //   818: aload_1
    //   819: invokevirtual toString : ()Ljava/lang/String;
    //   822: invokevirtual w : (Ljava/lang/String;Ljava/lang/String;)V
    //   825: aload_0
    //   826: invokevirtual doWork : ()V
    //   829: return
    //   830: aload_0
    //   831: aload #5
    //   833: invokespecial getConnectionStatus : (Landroid/bluetooth/BluetoothDevice;)Z
    //   836: istore_3
    //   837: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   840: astore #7
    //   842: aload_0
    //   843: getfield TAG : Ljava/lang/String;
    //   846: astore #6
    //   848: new java/lang/StringBuilder
    //   851: dup
    //   852: invokespecial <init> : ()V
    //   855: astore #12
    //   857: aload #12
    //   859: ldc_w 'Already connected to '
    //   862: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   865: pop
    //   866: aload #12
    //   868: aload #5
    //   870: invokevirtual getAddress : ()Ljava/lang/String;
    //   873: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   876: pop
    //   877: aload #12
    //   879: ldc_w ' : '
    //   882: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   885: pop
    //   886: aload #12
    //   888: iload_3
    //   889: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   892: pop
    //   893: aload #7
    //   895: aload #6
    //   897: aload #12
    //   899: invokevirtual toString : ()Ljava/lang/String;
    //   902: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   905: iload_3
    //   906: ifeq -> 941
    //   909: aload_1
    //   910: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
    //   913: invokevirtual getSkipped : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
    //   916: iconst_1
    //   917: invokevirtual setStatus : (Z)V
    //   920: aload_1
    //   921: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
    //   924: invokevirtual getSkipped : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
    //   927: invokestatic currentTimeMillis : ()J
    //   930: invokevirtual setTimePerformed : (J)V
    //   933: aload_0
    //   934: aload_1
    //   935: invokevirtual finishWork : (Lca/albertahealthservices/contacttracing/streetpass/Work;)V
    //   938: goto -> 1471
    //   941: new ca/albertahealthservices/contacttracing/streetpass/StreetPassWorker$CentralGattCallback
    //   944: dup
    //   945: aload_0
    //   946: aload_1
    //   947: invokespecial <init> : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;Lca/albertahealthservices/contacttracing/streetpass/Work;)V
    //   950: astore #7
    //   952: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   955: astore #6
    //   957: aload_0
    //   958: getfield TAG : Ljava/lang/String;
    //   961: astore #12
    //   963: new java/lang/StringBuilder
    //   966: dup
    //   967: invokespecial <init> : ()V
    //   970: astore #13
    //   972: aload #13
    //   974: ldc_w 'Starting work - connecting to device: '
    //   977: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   980: pop
    //   981: aload #13
    //   983: aload #5
    //   985: invokevirtual getAddress : ()Ljava/lang/String;
    //   988: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   991: pop
    //   992: aload #13
    //   994: ldc_w ' @ '
    //   997: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1000: pop
    //   1001: aload #13
    //   1003: aload_1
    //   1004: invokevirtual getConnectable : ()Lca/albertahealthservices/contacttracing/streetpass/ConnectablePeripheral;
    //   1007: invokevirtual getRssi : ()I
    //   1010: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1013: pop
    //   1014: aload #13
    //   1016: bipush #32
    //   1018: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   1021: pop
    //   1022: aload #13
    //   1024: invokestatic currentTimeMillis : ()J
    //   1027: aload_1
    //   1028: invokevirtual getTimeStamp : ()J
    //   1031: lsub
    //   1032: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   1035: pop
    //   1036: aload #13
    //   1038: ldc_w 'ms ago'
    //   1041: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1044: pop
    //   1045: aload #6
    //   1047: aload #12
    //   1049: aload #13
    //   1051: invokevirtual toString : ()Ljava/lang/String;
    //   1054: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   1057: aload_0
    //   1058: aload_1
    //   1059: putfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   1062: aload_1
    //   1063: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
    //   1066: invokevirtual getStarted : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
    //   1069: iconst_1
    //   1070: invokevirtual setStatus : (Z)V
    //   1073: aload_1
    //   1074: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
    //   1077: invokevirtual getStarted : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
    //   1080: invokestatic currentTimeMillis : ()J
    //   1083: invokevirtual setTimePerformed : (J)V
    //   1086: aload_1
    //   1087: aload_0
    //   1088: getfield context : Landroid/content/Context;
    //   1091: aload #7
    //   1093: invokevirtual startWork : (Landroid/content/Context;Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker$CentralGattCallback;)V
    //   1096: aload_1
    //   1097: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
    //   1100: astore #7
    //   1102: aload #7
    //   1104: ifnull -> 1113
    //   1107: aload #7
    //   1109: invokevirtual connect : ()Z
    //   1112: istore_2
    //   1113: iload_2
    //   1114: ifne -> 1207
    //   1117: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1120: astore #6
    //   1122: aload_0
    //   1123: getfield TAG : Ljava/lang/String;
    //   1126: astore #12
    //   1128: new java/lang/StringBuilder
    //   1131: astore #7
    //   1133: aload #7
    //   1135: invokespecial <init> : ()V
    //   1138: aload #7
    //   1140: ldc_w 'Alamak! not connecting to '
    //   1143: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1146: pop
    //   1147: aload #7
    //   1149: aload_1
    //   1150: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   1153: invokevirtual getAddress : ()Ljava/lang/String;
    //   1156: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1159: pop
    //   1160: aload #7
    //   1162: ldc_w '??'
    //   1165: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1168: pop
    //   1169: aload #6
    //   1171: aload #12
    //   1173: aload #7
    //   1175: invokevirtual toString : ()Ljava/lang/String;
    //   1178: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1181: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1184: aload_0
    //   1185: getfield TAG : Ljava/lang/String;
    //   1188: ldc_w 'Moving on to next task'
    //   1191: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1194: aload_0
    //   1195: aconst_null
    //   1196: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
    //   1199: putfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   1202: aload_0
    //   1203: invokevirtual doWork : ()V
    //   1206: return
    //   1207: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1210: astore #12
    //   1212: aload_0
    //   1213: getfield TAG : Ljava/lang/String;
    //   1216: astore #6
    //   1218: new java/lang/StringBuilder
    //   1221: astore #7
    //   1223: aload #7
    //   1225: invokespecial <init> : ()V
    //   1228: aload #7
    //   1230: ldc_w 'Connection to '
    //   1233: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1236: pop
    //   1237: aload #7
    //   1239: aload_1
    //   1240: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   1243: invokevirtual getAddress : ()Ljava/lang/String;
    //   1246: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1249: pop
    //   1250: aload #7
    //   1252: ldc_w ' attempt in progress'
    //   1255: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1258: pop
    //   1259: aload #12
    //   1261: aload #6
    //   1263: aload #7
    //   1265: invokevirtual toString : ()Ljava/lang/String;
    //   1268: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   1271: aload_0
    //   1272: getfield timeoutHandler : Landroid/os/Handler;
    //   1275: astore #7
    //   1277: aload #7
    //   1279: ifnonnull -> 1287
    //   1282: ldc 'timeoutHandler'
    //   1284: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
    //   1287: aload #7
    //   1289: aload_1
    //   1290: invokevirtual getTimeoutRunnable : ()Ljava/lang/Runnable;
    //   1293: getstatic ca/albertahealthservices/contacttracing/services/BluetoothMonitoringService.Companion : Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Companion;
    //   1296: invokevirtual getConnectionTimeout : ()J
    //   1299: invokevirtual postDelayed : (Ljava/lang/Runnable;J)Z
    //   1302: pop
    //   1303: aload_1
    //   1304: invokestatic currentTimeMillis : ()J
    //   1307: getstatic ca/albertahealthservices/contacttracing/services/BluetoothMonitoringService.Companion : Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Companion;
    //   1310: invokevirtual getConnectionTimeout : ()J
    //   1313: ladd
    //   1314: invokevirtual setTimeout : (J)V
    //   1317: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1320: astore #7
    //   1322: aload_0
    //   1323: getfield TAG : Ljava/lang/String;
    //   1326: astore #12
    //   1328: new java/lang/StringBuilder
    //   1331: astore #6
    //   1333: aload #6
    //   1335: invokespecial <init> : ()V
    //   1338: aload #6
    //   1340: ldc_w 'Timeout scheduled for '
    //   1343: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1346: pop
    //   1347: aload #6
    //   1349: aload_1
    //   1350: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
    //   1353: invokevirtual getAddress : ()Ljava/lang/String;
    //   1356: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1359: pop
    //   1360: aload #7
    //   1362: aload #12
    //   1364: aload #6
    //   1366: invokevirtual toString : ()Ljava/lang/String;
    //   1369: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   1372: goto -> 1471
    //   1375: astore_1
    //   1376: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1379: astore #6
    //   1381: aload_0
    //   1382: getfield TAG : Ljava/lang/String;
    //   1385: astore #12
    //   1387: new java/lang/StringBuilder
    //   1390: dup
    //   1391: invokespecial <init> : ()V
    //   1394: astore #7
    //   1396: aload #7
    //   1398: ldc_w 'Unexpected error while attempting to connect to '
    //   1401: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1404: pop
    //   1405: aload #7
    //   1407: aload #5
    //   1409: invokevirtual getAddress : ()Ljava/lang/String;
    //   1412: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1415: pop
    //   1416: aload #7
    //   1418: ldc_w ': '
    //   1421: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1424: pop
    //   1425: aload #7
    //   1427: aload_1
    //   1428: invokevirtual getLocalizedMessage : ()Ljava/lang/String;
    //   1431: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1434: pop
    //   1435: aload #6
    //   1437: aload #12
    //   1439: aload #7
    //   1441: invokevirtual toString : ()Ljava/lang/String;
    //   1444: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1447: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1450: aload_0
    //   1451: getfield TAG : Ljava/lang/String;
    //   1454: ldc_w 'Moving on to next task'
    //   1457: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1460: aload_0
    //   1461: aload #4
    //   1463: putfield currentWork : Lca/albertahealthservices/contacttracing/streetpass/Work;
    //   1466: aload_0
    //   1467: invokevirtual doWork : ()V
    //   1470: return
    //   1471: aload_1
    //   1472: ifnonnull -> 1488
    //   1475: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   1478: aload_0
    //   1479: getfield TAG : Ljava/lang/String;
    //   1482: ldc_w 'No outstanding work'
    //   1485: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   1488: return
    // Exception table:
    //   from	to	target	type
    //   1062	1102	1375	finally
    //   1107	1113	1375	finally
    //   1117	1206	1375	finally
    //   1207	1277	1375	finally
    //   1282	1287	1375	finally
    //   1287	1372	1375	finally
  }
  
  public final void finishWork(Work paramWork) {
    Intrinsics.checkParameterIsNotNull(paramWork, "work");
    if (paramWork.getFinished()) {
      CentralLog.Companion companion1 = CentralLog.Companion;
      String str = this.TAG;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Work on ");
      stringBuilder1.append(paramWork.getDevice().getAddress());
      stringBuilder1.append(" already finished and closed");
      companion1.i(str, stringBuilder1.toString());
      return;
    } 
    if (paramWork.isCriticalsCompleted()) {
      Utils utils = Utils.INSTANCE;
      Context context = this.context;
      String str = paramWork.getDevice().getAddress();
      Intrinsics.checkExpressionValueIsNotNull(str, "work.device.address");
      utils.broadcastDeviceProcessed(context, str);
    } 
    CentralLog.Companion companion = CentralLog.Companion;
    String str2 = this.TAG;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Work on ");
    stringBuilder.append(paramWork.getDevice().getAddress());
    stringBuilder.append(" stopped in: ");
    stringBuilder.append(paramWork.getChecklist().getDisconnected().getTimePerformed() - paramWork.getChecklist().getStarted().getTimePerformed());
    companion.i(str2, stringBuilder.toString());
    companion = CentralLog.Companion;
    str2 = this.TAG;
    stringBuilder = new StringBuilder();
    stringBuilder.append("Work on ");
    stringBuilder.append(paramWork.getDevice().getAddress());
    stringBuilder.append(" completed?: ");
    stringBuilder.append(paramWork.isCriticalsCompleted());
    stringBuilder.append(". Connected in: ");
    stringBuilder.append(paramWork.getChecklist().getConnected().getTimePerformed() - paramWork.getChecklist().getStarted().getTimePerformed());
    stringBuilder.append(". connection lasted for: ");
    stringBuilder.append(paramWork.getChecklist().getDisconnected().getTimePerformed() - paramWork.getChecklist().getConnected().getTimePerformed());
    stringBuilder.append(". Status: ");
    stringBuilder.append(paramWork.getChecklist());
    companion.i(str2, stringBuilder.toString());
    Handler handler = this.timeoutHandler;
    if (handler == null)
      Intrinsics.throwUninitializedPropertyAccessException("timeoutHandler"); 
    handler.removeCallbacks(paramWork.getTimeoutRunnable());
    companion = CentralLog.Companion;
    String str1 = this.TAG;
    stringBuilder = new StringBuilder();
    stringBuilder.append("Timeout removed for ");
    stringBuilder.append(paramWork.getDevice().getAddress());
    companion.i(str1, stringBuilder.toString());
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
      String str1;
      Intrinsics.checkParameterIsNotNull(param1BluetoothGatt, "gatt");
      Intrinsics.checkParameterIsNotNull(param1BluetoothGattCharacteristic, "characteristic");
      CentralLog.Companion companion = CentralLog.Companion;
      String str2 = StreetPassWorker.this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Read Status: ");
      stringBuilder.append(param1Int);
      companion.i(str2, stringBuilder.toString());
      if (param1Int != 0) {
        CentralLog.Companion companion1 = CentralLog.Companion;
        String str = StreetPassWorker.this.TAG;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Failed to read characteristics from ");
        BluetoothDevice bluetoothDevice1 = param1BluetoothGatt.getDevice();
        Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
        stringBuilder1.append(bluetoothDevice1.getAddress());
        stringBuilder1.append(": ");
        stringBuilder1.append(param1Int);
        companion1.w(str, stringBuilder1.toString());
      } else {
        CentralLog.Companion companion2 = CentralLog.Companion;
        str2 = StreetPassWorker.this.TAG;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Characteristic read from ");
        BluetoothDevice bluetoothDevice1 = param1BluetoothGatt.getDevice();
        Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
        stringBuilder1.append(bluetoothDevice1.getAddress());
        stringBuilder1.append(": ");
        stringBuilder1.append(param1BluetoothGattCharacteristic.getStringValue(0));
        companion2.i(str2, stringBuilder1.toString());
        CentralLog.Companion companion1 = CentralLog.Companion;
        String str = StreetPassWorker.this.TAG;
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("onCharacteristicRead: ");
        stringBuilder1.append(this.work.getDevice().getAddress());
        stringBuilder1.append(" - [");
        stringBuilder1.append(this.work.getConnectable().getRssi());
        stringBuilder1.append(']');
        companion1.i(str, stringBuilder1.toString());
        if (BlueTrace.INSTANCE.supportsCharUUID(param1BluetoothGattCharacteristic.getUuid()))
          try {
            BlueTrace blueTrace = BlueTrace.INSTANCE;
            UUID uUID = param1BluetoothGattCharacteristic.getUuid();
            Intrinsics.checkExpressionValueIsNotNull(uUID, "characteristic.uuid");
            BlueTraceProtocol blueTraceProtocol = blueTrace.getImplementation(uUID);
            byte[] arrayOfByte = param1BluetoothGattCharacteristic.getValue();
          } finally {
            companion1 = null;
            CentralLog.Companion companion3 = CentralLog.Companion;
            String str3 = StreetPassWorker.this.TAG;
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
          String str = StreetPassWorker.this.TAG;
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Attempt to write characteristic to our service on ");
          bluetoothDevice = param1BluetoothGatt.getDevice();
          Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice, "gatt.device");
          stringBuilder1.append(bluetoothDevice.getAddress());
          stringBuilder1.append(": ");
          stringBuilder1.append(bool);
          companion1.i(str, stringBuilder1.toString());
        } else {
          CentralLog.Companion companion1 = CentralLog.Companion;
          str1 = StreetPassWorker.this.TAG;
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Expired BM. Skipping attempt to write characteristic to our service on ");
          BluetoothDevice bluetoothDevice1 = bluetoothDevice.getDevice();
          Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
          stringBuilder1.append(bluetoothDevice1.getAddress());
          companion1.i(str1, stringBuilder1.toString());
          endWorkConnection((BluetoothGatt)bluetoothDevice);
        } 
      } else {
        CentralLog.Companion companion1 = CentralLog.Companion;
        String str = StreetPassWorker.this.TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Not writing to ");
        BluetoothDevice bluetoothDevice1 = bluetoothDevice.getDevice();
        Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
        stringBuilder.append(bluetoothDevice1.getAddress());
        stringBuilder.append(". Characteristic ");
        stringBuilder.append(str1.getUuid());
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
      //   116: astore #6
      //   118: new java/lang/StringBuilder
      //   121: dup
      //   122: invokespecial <init> : ()V
      //   125: astore #5
      //   127: aload #5
      //   129: ldc_w 'Connected to other GATT server - '
      //   132: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   135: pop
      //   136: aload_1
      //   137: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   140: astore #4
      //   142: aload #4
      //   144: ldc 'gatt.device'
      //   146: invokestatic checkExpressionValueIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
      //   149: aload #5
      //   151: aload #4
      //   153: invokevirtual getAddress : ()Ljava/lang/String;
      //   156: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   159: pop
      //   160: aload #7
      //   162: aload #6
      //   164: aload #5
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
      //   222: astore #5
      //   224: aload_0
      //   225: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   228: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   231: astore #6
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
      //   255: astore #4
      //   257: aload #4
      //   259: ldc 'gatt.device'
      //   261: invokestatic checkExpressionValueIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
      //   264: aload #7
      //   266: aload #4
      //   268: invokevirtual getAddress : ()Ljava/lang/String;
      //   271: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   274: pop
      //   275: aload #5
      //   277: aload #6
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
      //   337: astore #7
      //   339: aload_0
      //   340: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   343: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   346: astore #5
      //   348: new java/lang/StringBuilder
      //   351: dup
      //   352: invokespecial <init> : ()V
      //   355: astore #6
      //   357: aload #6
      //   359: ldc_w 'Timeout removed for '
      //   362: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   365: pop
      //   366: aload #6
      //   368: aload_0
      //   369: getfield work : Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   372: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   375: invokevirtual getAddress : ()Ljava/lang/String;
      //   378: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   381: pop
      //   382: aload #7
      //   384: aload #5
      //   386: aload #6
      //   388: invokevirtual toString : ()Ljava/lang/String;
      //   391: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
      //   394: aload_0
      //   395: getfield work : Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   398: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   401: invokevirtual getAddress : ()Ljava/lang/String;
      //   404: astore #6
      //   406: aload_0
      //   407: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   410: invokestatic access$getCurrentWork$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   413: astore #7
      //   415: aload #7
      //   417: ifnull -> 442
      //   420: aload #7
      //   422: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   425: astore #7
      //   427: aload #7
      //   429: ifnull -> 442
      //   432: aload #7
      //   434: invokevirtual getAddress : ()Ljava/lang/String;
      //   437: astore #7
      //   439: goto -> 445
      //   442: aconst_null
      //   443: astore #7
      //   445: aload #6
      //   447: aload #7
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
      //   180: astore #8
      //   182: aload_0
      //   183: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   186: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   189: astore #4
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
      //   246: aload #8
      //   248: aload #4
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
            CentralLog.Companion companion1 = CentralLog.Companion;
            String str1 = StreetPassWorker.this.TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("WTF? ");
            BluetoothDevice bluetoothDevice1 = param1BluetoothGatt.getDevice();
            Intrinsics.checkExpressionValueIsNotNull(bluetoothDevice1, "gatt.device");
            stringBuilder.append(bluetoothDevice1.getAddress());
            stringBuilder.append(" does not have our characteristic");
            companion1.e(str1, stringBuilder.toString());
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
      //   151: astore_2
      //   152: aload_0
      //   153: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   156: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   159: astore_3
      //   160: new java/lang/StringBuilder
      //   163: dup
      //   164: invokespecial <init> : ()V
      //   167: astore #4
      //   169: aload #4
      //   171: aload_1
      //   172: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   175: invokevirtual getAddress : ()Ljava/lang/String;
      //   178: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   181: pop
      //   182: aload #4
      //   184: ldc ' work status: '
      //   186: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   189: pop
      //   190: aload #4
      //   192: aload_1
      //   193: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   196: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   199: pop
      //   200: aload #4
      //   202: bipush #46
      //   204: invokevirtual append : (C)Ljava/lang/StringBuilder;
      //   207: pop
      //   208: aload_2
      //   209: aload_3
      //   210: aload #4
      //   212: invokevirtual toString : ()Ljava/lang/String;
      //   215: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   218: aload_1
      //   219: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   222: invokevirtual getConnected : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   225: invokevirtual getStatus : ()Z
      //   228: ifne -> 441
      //   231: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   234: astore #4
      //   236: aload_0
      //   237: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   240: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   243: astore_2
      //   244: new java/lang/StringBuilder
      //   247: dup
      //   248: invokespecial <init> : ()V
      //   251: astore_3
      //   252: aload_3
      //   253: ldc 'No connection formed for '
      //   255: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   258: pop
      //   259: aload_3
      //   260: aload_1
      //   261: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   264: invokevirtual getAddress : ()Ljava/lang/String;
      //   267: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   270: pop
      //   271: aload #4
      //   273: aload_2
      //   274: aload_3
      //   275: invokevirtual toString : ()Ljava/lang/String;
      //   278: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   281: aload_1
      //   282: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   285: invokevirtual getAddress : ()Ljava/lang/String;
      //   288: astore #4
      //   290: aload_0
      //   291: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   294: invokestatic access$getCurrentWork$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Lca/albertahealthservices/contacttracing/streetpass/Work;
      //   297: astore_3
      //   298: aload_3
      //   299: ifnull -> 319
      //   302: aload_3
      //   303: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   306: astore_3
      //   307: aload_3
      //   308: ifnull -> 319
      //   311: aload_3
      //   312: invokevirtual getAddress : ()Ljava/lang/String;
      //   315: astore_3
      //   316: goto -> 321
      //   319: aconst_null
      //   320: astore_3
      //   321: aload #4
      //   323: aload_3
      //   324: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
      //   327: ifeq -> 341
      //   330: aload_0
      //   331: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   334: aconst_null
      //   335: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
      //   338: invokestatic access$setCurrentWork$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   341: aload_1
      //   342: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
      //   345: astore_3
      //   346: aload_3
      //   347: ifnull -> 430
      //   350: aload_3
      //   351: invokevirtual close : ()V
      //   354: goto -> 430
      //   357: astore_2
      //   358: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   361: astore_3
      //   362: aload_0
      //   363: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   366: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   369: astore #5
      //   371: new java/lang/StringBuilder
      //   374: dup
      //   375: invokespecial <init> : ()V
      //   378: astore #4
      //   380: aload #4
      //   382: ldc 'Unexpected error while attempting to close clientIf to '
      //   384: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   387: pop
      //   388: aload #4
      //   390: aload_1
      //   391: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   394: invokevirtual getAddress : ()Ljava/lang/String;
      //   397: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   400: pop
      //   401: aload #4
      //   403: ldc ': '
      //   405: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   408: pop
      //   409: aload #4
      //   411: aload_2
      //   412: invokevirtual getLocalizedMessage : ()Ljava/lang/String;
      //   415: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   418: pop
      //   419: aload_3
      //   420: aload #5
      //   422: aload #4
      //   424: invokevirtual toString : ()Ljava/lang/String;
      //   427: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   430: aload_0
      //   431: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   434: aload_1
      //   435: invokevirtual finishWork : (Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   438: goto -> 867
      //   441: aload_1
      //   442: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   445: invokevirtual getConnected : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   448: invokevirtual getStatus : ()Z
      //   451: ifeq -> 801
      //   454: aload_1
      //   455: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   458: invokevirtual getDisconnected : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   461: invokevirtual getStatus : ()Z
      //   464: ifne -> 801
      //   467: aload_1
      //   468: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   471: invokevirtual getReadCharacteristic : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   474: invokevirtual getStatus : ()Z
      //   477: ifne -> 654
      //   480: aload_1
      //   481: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   484: invokevirtual getWriteCharacteristic : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   487: invokevirtual getStatus : ()Z
      //   490: ifne -> 654
      //   493: aload_1
      //   494: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   497: invokevirtual getSkipped : ()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;
      //   500: invokevirtual getStatus : ()Z
      //   503: ifeq -> 509
      //   506: goto -> 654
      //   509: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   512: astore_2
      //   513: aload_0
      //   514: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   517: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   520: astore #4
      //   522: new java/lang/StringBuilder
      //   525: dup
      //   526: invokespecial <init> : ()V
      //   529: astore_3
      //   530: aload_3
      //   531: ldc 'Connected but did nothing for '
      //   533: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   536: pop
      //   537: aload_3
      //   538: aload_1
      //   539: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   542: invokevirtual getAddress : ()Ljava/lang/String;
      //   545: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   548: pop
      //   549: aload_2
      //   550: aload #4
      //   552: aload_3
      //   553: invokevirtual toString : ()Ljava/lang/String;
      //   556: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   559: aload_1
      //   560: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
      //   563: astore_3
      //   564: aload_3
      //   565: ifnull -> 572
      //   568: aload_3
      //   569: invokevirtual disconnect : ()V
      //   572: aload_1
      //   573: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
      //   576: ifnonnull -> 867
      //   579: aload_0
      //   580: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   583: aconst_null
      //   584: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
      //   587: invokestatic access$setCurrentWork$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   590: aload_0
      //   591: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   594: aload_1
      //   595: invokevirtual finishWork : (Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   598: goto -> 867
      //   601: astore_1
      //   602: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   605: astore_3
      //   606: aload_0
      //   607: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   610: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   613: astore_2
      //   614: new java/lang/StringBuilder
      //   617: dup
      //   618: invokespecial <init> : ()V
      //   621: astore #4
      //   623: aload #4
      //   625: ldc 'Failed to clean up work, bluetooth state likely changed or other device's advertiser stopped: '
      //   627: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   630: pop
      //   631: aload #4
      //   633: aload_1
      //   634: invokevirtual getLocalizedMessage : ()Ljava/lang/String;
      //   637: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   640: pop
      //   641: aload_3
      //   642: aload_2
      //   643: aload #4
      //   645: invokevirtual toString : ()Ljava/lang/String;
      //   648: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   651: goto -> 867
      //   654: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   657: astore_2
      //   658: aload_0
      //   659: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   662: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   665: astore_3
      //   666: new java/lang/StringBuilder
      //   669: dup
      //   670: invokespecial <init> : ()V
      //   673: astore #4
      //   675: aload #4
      //   677: ldc 'Connected but did not disconnect in time for '
      //   679: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   682: pop
      //   683: aload #4
      //   685: aload_1
      //   686: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   689: invokevirtual getAddress : ()Ljava/lang/String;
      //   692: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   695: pop
      //   696: aload_2
      //   697: aload_3
      //   698: aload #4
      //   700: invokevirtual toString : ()Ljava/lang/String;
      //   703: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   706: aload_1
      //   707: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
      //   710: astore_3
      //   711: aload_3
      //   712: ifnull -> 719
      //   715: aload_3
      //   716: invokevirtual disconnect : ()V
      //   719: aload_1
      //   720: invokevirtual getGatt : ()Landroid/bluetooth/BluetoothGatt;
      //   723: ifnonnull -> 867
      //   726: aload_0
      //   727: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   730: aconst_null
      //   731: checkcast ca/albertahealthservices/contacttracing/streetpass/Work
      //   734: invokestatic access$setCurrentWork$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   737: aload_0
      //   738: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   741: aload_1
      //   742: invokevirtual finishWork : (Lca/albertahealthservices/contacttracing/streetpass/Work;)V
      //   745: goto -> 867
      //   748: astore_1
      //   749: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   752: astore_3
      //   753: aload_0
      //   754: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   757: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   760: astore_2
      //   761: new java/lang/StringBuilder
      //   764: dup
      //   765: invokespecial <init> : ()V
      //   768: astore #4
      //   770: aload #4
      //   772: ldc 'Failed to clean up work, bluetooth state likely changed or other device's advertiser stopped: '
      //   774: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   777: pop
      //   778: aload #4
      //   780: aload_1
      //   781: invokevirtual getLocalizedMessage : ()Ljava/lang/String;
      //   784: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   787: pop
      //   788: aload_3
      //   789: aload_2
      //   790: aload #4
      //   792: invokevirtual toString : ()Ljava/lang/String;
      //   795: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   798: goto -> 867
      //   801: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   804: astore_2
      //   805: aload_0
      //   806: getfield this$0 : Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;
      //   809: invokestatic access$getTAG$p : (Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;)Ljava/lang/String;
      //   812: astore #4
      //   814: new java/lang/StringBuilder
      //   817: dup
      //   818: invokespecial <init> : ()V
      //   821: astore_3
      //   822: aload_3
      //   823: ldc 'Disconnected but callback not invoked in time. Waiting.: '
      //   825: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   828: pop
      //   829: aload_3
      //   830: aload_1
      //   831: invokevirtual getDevice : ()Landroid/bluetooth/BluetoothDevice;
      //   834: invokevirtual getAddress : ()Ljava/lang/String;
      //   837: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   840: pop
      //   841: aload_3
      //   842: ldc ': '
      //   844: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   847: pop
      //   848: aload_3
      //   849: aload_1
      //   850: invokevirtual getChecklist : ()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;
      //   853: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   856: pop
      //   857: aload_2
      //   858: aload #4
      //   860: aload_3
      //   861: invokevirtual toString : ()Ljava/lang/String;
      //   864: invokevirtual e : (Ljava/lang/String;Ljava/lang/String;)V
      //   867: return
      // Exception table:
      //   from	to	target	type
      //   341	346	357	java/lang/Exception
      //   350	354	357	java/lang/Exception
      //   559	564	601	finally
      //   568	572	601	finally
      //   572	598	601	finally
      //   706	711	748	finally
      //   715	719	748	finally
      //   719	745	748	finally
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/StreetPassWorker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */