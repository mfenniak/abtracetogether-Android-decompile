package ca.albertahealthservices.contacttracing.idmanager;

import android.content.Context;
import ca.albertahealthservices.contacttracing.Preference;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;

@Metadata(bv = {1, 0, 3}, d1 = {"\000H\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\002\b\005\n\002\030\002\n\002\b\006\n\002\020\002\n\002\b\002\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\016\020\t\032\0020\n2\006\020\013\032\0020\fJ!\020\r\032\b\022\004\022\0020\0170\0162\f\020\020\032\b\022\004\022\0020\0170\021H\002¢\006\002\020\022J\033\020\023\032\b\022\004\022\0020\0170\0212\006\020\024\032\0020\004H\002¢\006\002\020\025J\016\020\026\032\0020\0272\006\020\013\032\0020\fJ\036\020\030\032\0020\0172\006\020\013\032\0020\f2\f\020\031\032\b\022\004\022\0020\0170\016H\002J\016\020\032\032\0020\n2\006\020\013\032\0020\fJ\016\020\033\032\0020\n2\006\020\013\032\0020\fJ\020\020\034\032\004\030\0010\0172\006\020\013\032\0020\fJ\026\020\035\032\0020\0362\006\020\013\032\0020\f2\006\020\037\032\0020\004R\016\020\003\032\0020\004XT¢\006\002\n\000R\022\020\005\032\0020\006X\005¢\006\006\032\004\b\007\020\b¨\006 "}, d2 = {"Lca/albertahealthservices/contacttracing/idmanager/TempIDManager;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "TAG", "", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "bmValid", "", "context", "Landroid/content/Context;", "convertToQueue", "Ljava/util/Queue;", "Lca/albertahealthservices/contacttracing/idmanager/TemporaryID;", "tempIDArray", "", "([Lca/albertahealthservices/contacttracing/idmanager/TemporaryID;)Ljava/util/Queue;", "convertToTemporaryIDs", "tempIDString", "(Ljava/lang/String;)[Lca/albertahealthservices/contacttracing/idmanager/TemporaryID;", "getTemporaryIDs", "Lkotlinx/coroutines/Job;", "getValidOrLastTemporaryID", "tempIDQueue", "needToRollNewTempID", "needToUpdate", "retrieveTemporaryID", "storeTemporaryIDs", "", "packet", "app_release"}, k = 1, mv = {1, 1, 16})
public final class TempIDManager implements CoroutineScope {
  public static final TempIDManager INSTANCE = new TempIDManager();
  
  private static final String TAG = "TempIDManager";
  
  private final Queue<TemporaryID> convertToQueue(TemporaryID[] paramArrayOfTemporaryID) {
    CentralLog.Companion companion2 = CentralLog.Companion;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("[TempID] Before Sort: ");
    byte b = 0;
    stringBuilder2.append(paramArrayOfTemporaryID[0]);
    companion2.d("TempIDManager", stringBuilder2.toString());
    if (paramArrayOfTemporaryID.length > 1)
      ArraysKt.sortWith((Object[])paramArrayOfTemporaryID, new TempIDManager$convertToQueue$$inlined$sortBy$1()); 
    CentralLog.Companion companion3 = CentralLog.Companion;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("[TempID] After Sort: ");
    stringBuilder1.append(paramArrayOfTemporaryID[0]);
    companion3.d("TempIDManager", stringBuilder1.toString());
    LinkedList<TemporaryID> linkedList = new LinkedList();
    int i = paramArrayOfTemporaryID.length;
    while (b < i) {
      linkedList.offer(paramArrayOfTemporaryID[b]);
      b++;
    } 
    CentralLog.Companion companion1 = CentralLog.Companion;
    stringBuilder1 = new StringBuilder();
    stringBuilder1.append("[TempID] Retrieving from Queue: ");
    stringBuilder1.append(linkedList.peek());
    companion1.d("TempIDManager", stringBuilder1.toString());
    return linkedList;
  }
  
  private final TemporaryID[] convertToTemporaryIDs(String paramString) {
    Gson gson = (new GsonBuilder()).create();
    Intrinsics.checkExpressionValueIsNotNull(gson, "GsonBuilder().create()");
    TemporaryID[] arrayOfTemporaryID = (TemporaryID[])gson.fromJson(paramString, TemporaryID[].class);
    CentralLog.Companion companion = CentralLog.Companion;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[TempID] After GSON conversion: ");
    stringBuilder.append(arrayOfTemporaryID[0].getTempID());
    stringBuilder.append(' ');
    stringBuilder.append(arrayOfTemporaryID[0].getStartTime());
    companion.d("TempIDManager", stringBuilder.toString());
    Intrinsics.checkExpressionValueIsNotNull(arrayOfTemporaryID, "tempIDResult");
    return arrayOfTemporaryID;
  }
  
  private final TemporaryID getValidOrLastTemporaryID(Context paramContext, Queue<TemporaryID> paramQueue) {
    CentralLog.Companion.d("TempIDManager", "[TempID] Retrieving Temporary ID");
    long l1 = System.currentTimeMillis();
    byte b;
    for (b = 0; paramQueue.size() > 1; b++) {
      TemporaryID temporaryID1 = paramQueue.peek();
      temporaryID1.print();
      if (temporaryID1.isValidForCurrentTime()) {
        CentralLog.Companion.d("TempIDManager", "[TempID] Breaking out of the loop");
        break;
      } 
      paramQueue.poll();
    } 
    TemporaryID temporaryID = paramQueue.peek();
    long l2 = temporaryID.getStartTime();
    long l3 = 1000L;
    long l4 = temporaryID.getExpiryTime() * l3;
    CentralLog.Companion companion2 = CentralLog.Companion;
    StringBuilder stringBuilder3 = new StringBuilder();
    stringBuilder3.append("[TempID Total number of items in queue: ");
    stringBuilder3.append(paramQueue.size());
    companion2.d("TempIDManager", stringBuilder3.toString());
    companion2 = CentralLog.Companion;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("[TempID Number of items popped from queue: ");
    stringBuilder1.append(b);
    companion2.d("TempIDManager", stringBuilder1.toString());
    companion2 = CentralLog.Companion;
    stringBuilder1 = new StringBuilder();
    stringBuilder1.append("[TempID] Current time: ");
    stringBuilder1.append(l1);
    companion2.d("TempIDManager", stringBuilder1.toString());
    companion2 = CentralLog.Companion;
    stringBuilder1 = new StringBuilder();
    stringBuilder1.append("[TempID] Start time: ");
    stringBuilder1.append(l2 * l3);
    companion2.d("TempIDManager", stringBuilder1.toString());
    CentralLog.Companion companion1 = CentralLog.Companion;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("[TempID] Expiry time: ");
    stringBuilder2.append(l4);
    companion1.d("TempIDManager", stringBuilder2.toString());
    CentralLog.Companion.d("TempIDManager", "[TempID] Updating expiry time");
    Preference.INSTANCE.putExpiryTimeInMillis(paramContext, l4);
    Intrinsics.checkExpressionValueIsNotNull(temporaryID, "foundTempID");
    return temporaryID;
  }
  
  public final boolean bmValid(Context paramContext) {
    boolean bool;
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    long l = Preference.INSTANCE.getExpiryTimeInMillis(paramContext);
    if (System.currentTimeMillis() < l) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public CoroutineContext getCoroutineContext() {
    return this.$$delegate_0.getCoroutineContext();
  }
  
  public final Job getTemporaryIDs(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    return BuildersKt.launch$default(this, null, null, new TempIDManager$getTemporaryIDs$1(paramContext, null), 3, null);
  }
  
  public final boolean needToRollNewTempID(Context paramContext) {
    boolean bool;
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    long l1 = Preference.INSTANCE.getExpiryTimeInMillis(paramContext);
    long l2 = System.currentTimeMillis();
    if (l2 >= l1) {
      bool = true;
    } else {
      bool = false;
    } 
    CentralLog.Companion companion = CentralLog.Companion;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[TempID] Need to get new TempID? ");
    stringBuilder.append(l1);
    stringBuilder.append(" vs ");
    stringBuilder.append(l2);
    stringBuilder.append(": ");
    stringBuilder.append(bool);
    companion.d("TempIDManager", stringBuilder.toString());
    return bool;
  }
  
  public final boolean needToUpdate(Context paramContext) {
    boolean bool;
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    long l1 = Preference.INSTANCE.getNextFetchTimeInMillis(paramContext);
    long l2 = System.currentTimeMillis();
    if (l2 >= l1) {
      bool = true;
    } else {
      bool = false;
    } 
    CentralLog.Companion companion = CentralLog.Companion;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Need to update and fetch TemporaryIDs? ");
    stringBuilder.append(l1);
    stringBuilder.append(" vs ");
    stringBuilder.append(l2);
    stringBuilder.append(": ");
    stringBuilder.append(bool);
    companion.i("TempIDManager", stringBuilder.toString());
    return bool;
  }
  
  public final TemporaryID retrieveTemporaryID(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    File file = new File(paramContext.getFilesDir(), "tempIDs");
    if (file.exists()) {
      String str = FilesKt.readText$default(file, null, 1, null);
      CentralLog.Companion companion = CentralLog.Companion;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("[TempID] fetched broadcastmessage from file:  ");
      stringBuilder.append(str);
      companion.d("TempIDManager", stringBuilder.toString());
      return getValidOrLastTemporaryID(paramContext, convertToQueue(convertToTemporaryIDs(str)));
    } 
    return null;
  }
  
  public final void storeTemporaryIDs(Context paramContext, String paramString) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramString, "packet");
    CentralLog.Companion.d("TempIDManager", "[TempID] Storing temporary IDs into internal storage...");
    FilesKt.writeText$default(new File(paramContext.getFilesDir(), "tempIDs"), paramString, null, 2, null);
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\036\n\000\n\002\020\b\n\002\b\006\n\002\b\006\n\002\b\006\n\002\b\006\n\002\b\006\n\002\b\007\020\000\032\0020\001\"\004\b\000\020\0022\016\020\003\032\n \004*\004\030\001H\002H\0022\016\020\005\032\n \004*\004\030\001H\002H\002H\n¢\006\004\b\006\020\007¨\006\b"}, d2 = {"<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I", "kotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2"}, k = 3, mv = {1, 1, 16})
  public static final class TempIDManager$convertToQueue$$inlined$sortBy$1<T> implements Comparator<T> {
    public final int compare(T param1T1, T param1T2) {
      return ComparisonsKt.compareValues(Long.valueOf(((TemporaryID)param1T1).getStartTime()), Long.valueOf(((TemporaryID)param1T2).getStartTime()));
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\016\n\000\n\002\020\002\n\002\030\002\n\002\b\002\020\000\032\0020\001*\0020\002H@¢\006\004\b\003\020\004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 16})
  @DebugMetadata(c = "ca.albertahealthservices.contacttracing.idmanager.TempIDManager$getTemporaryIDs$1", f = "TempIDManager.kt", i = {0, 0}, l = {129}, m = "invokeSuspend", n = {"$this$launch", "queryParams"}, s = {"L$0", "L$1"})
  static final class TempIDManager$getTemporaryIDs$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    Object L$0;
    
    Object L$1;
    
    int label;
    
    private CoroutineScope p$;
    
    TempIDManager$getTemporaryIDs$1(Context param1Context, Continuation param1Continuation) {
      super(2, param1Continuation);
    }
    
    public final Continuation<Unit> create(Object param1Object, Continuation<?> param1Continuation) {
      Intrinsics.checkParameterIsNotNull(param1Continuation, "completion");
      TempIDManager$getTemporaryIDs$1 tempIDManager$getTemporaryIDs$1 = new TempIDManager$getTemporaryIDs$1(this.$context, param1Continuation);
      tempIDManager$getTemporaryIDs$1.p$ = (CoroutineScope)param1Object;
      return (Continuation<Unit>)tempIDManager$getTemporaryIDs$1;
    }
    
    public final Object invoke(Object param1Object1, Object param1Object2) {
      return ((TempIDManager$getTemporaryIDs$1)create(param1Object1, (Continuation)param1Object2)).invokeSuspend(Unit.INSTANCE);
    }
    
    public final Object invokeSuspend(Object param1Object) {
      // Byte code:
      //   0: invokestatic getCOROUTINE_SUSPENDED : ()Ljava/lang/Object;
      //   3: astore_2
      //   4: aload_0
      //   5: getfield label : I
      //   8: istore_3
      //   9: iload_3
      //   10: ifeq -> 53
      //   13: iload_3
      //   14: iconst_1
      //   15: if_icmpne -> 43
      //   18: aload_0
      //   19: getfield L$1 : Ljava/lang/Object;
      //   22: checkcast java/util/HashMap
      //   25: astore #4
      //   27: aload_0
      //   28: getfield L$0 : Ljava/lang/Object;
      //   31: checkcast kotlinx/coroutines/CoroutineScope
      //   34: astore #4
      //   36: aload_1
      //   37: invokestatic throwOnFailure : (Ljava/lang/Object;)V
      //   40: goto -> 144
      //   43: new java/lang/IllegalStateException
      //   46: dup
      //   47: ldc 'call to 'resume' before 'invoke' with coroutine'
      //   49: invokespecial <init> : (Ljava/lang/String;)V
      //   52: athrow
      //   53: aload_1
      //   54: invokestatic throwOnFailure : (Ljava/lang/Object;)V
      //   57: aload_0
      //   58: getfield p$ : Lkotlinx/coroutines/CoroutineScope;
      //   61: astore #4
      //   63: new java/util/HashMap
      //   66: astore_1
      //   67: aload_1
      //   68: invokespecial <init> : ()V
      //   71: aload_1
      //   72: checkcast java/util/Map
      //   75: ldc 'userId'
      //   77: getstatic ca/albertahealthservices/contacttracing/Preference.INSTANCE : Lca/albertahealthservices/contacttracing/Preference;
      //   80: aload_0
      //   81: getfield $context : Landroid/content/Context;
      //   84: invokevirtual getUUID : (Landroid/content/Context;)Ljava/lang/String;
      //   87: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   92: pop
      //   93: getstatic ca/albertahealthservices/contacttracing/api/Request.INSTANCE : Lca/albertahealthservices/contacttracing/api/Request;
      //   96: astore #5
      //   98: aload_0
      //   99: aload #4
      //   101: putfield L$0 : Ljava/lang/Object;
      //   104: aload_0
      //   105: aload_1
      //   106: putfield L$1 : Ljava/lang/Object;
      //   109: aload_0
      //   110: iconst_1
      //   111: putfield label : I
      //   114: aload #5
      //   116: ldc '/adapters/tempidsAdapter/tempIds'
      //   118: ldc 'GET'
      //   120: iconst_0
      //   121: aconst_null
      //   122: aconst_null
      //   123: aload_1
      //   124: aload_0
      //   125: bipush #28
      //   127: aconst_null
      //   128: invokestatic runRequest$default : (Lca/albertahealthservices/contacttracing/api/Request;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Lorg/json/JSONObject;Ljava/util/HashMap;Lkotlin/coroutines/Continuation;ILjava/lang/Object;)Ljava/lang/Object;
      //   131: astore #4
      //   133: aload #4
      //   135: astore_1
      //   136: aload #4
      //   138: aload_2
      //   139: if_acmpne -> 144
      //   142: aload_2
      //   143: areturn
      //   144: aload_1
      //   145: checkcast ca/albertahealthservices/contacttracing/api/Response
      //   148: astore #4
      //   150: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   153: ldc 'TempIDManager'
      //   155: aload #4
      //   157: invokevirtual toString : ()Ljava/lang/String;
      //   160: invokevirtual d : (Ljava/lang/String;Ljava/lang/String;)V
      //   163: aload #4
      //   165: invokevirtual isSuccess : ()Z
      //   168: ifeq -> 617
      //   171: aload #4
      //   173: invokevirtual getStatus : ()Ljava/lang/Integer;
      //   176: astore_1
      //   177: aload_1
      //   178: ifnull -> 617
      //   181: aload_1
      //   182: invokevirtual intValue : ()I
      //   185: sipush #200
      //   188: if_icmpne -> 617
      //   191: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   194: ldc 'TempIDManager'
      //   196: ldc 'Retrieved Temporary IDs from Server'
      //   198: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
      //   201: aload #4
      //   203: invokevirtual getText : ()Ljava/lang/String;
      //   206: astore #4
      //   208: aconst_null
      //   209: astore_1
      //   210: aload #4
      //   212: ifnull -> 232
      //   215: aload #4
      //   217: ldc '{"pin":'
      //   219: ldc ''
      //   221: iconst_0
      //   222: iconst_4
      //   223: aconst_null
      //   224: invokestatic replace$default : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Ljava/lang/String;
      //   227: astore #4
      //   229: goto -> 235
      //   232: aconst_null
      //   233: astore #4
      //   235: aload #4
      //   237: ifnull -> 282
      //   240: aload #4
      //   242: invokevirtual length : ()I
      //   245: istore_3
      //   246: aload #4
      //   248: ifnull -> 270
      //   251: aload #4
      //   253: iconst_0
      //   254: iload_3
      //   255: iconst_1
      //   256: isub
      //   257: invokevirtual substring : (II)Ljava/lang/String;
      //   260: astore_1
      //   261: aload_1
      //   262: ldc '(this as java.lang.Strin…ing(startIndex, endIndex)'
      //   264: invokestatic checkExpressionValueIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
      //   267: goto -> 282
      //   270: new kotlin/TypeCastException
      //   273: astore_1
      //   274: aload_1
      //   275: ldc 'null cannot be cast to non-null type java.lang.String'
      //   277: invokespecial <init> : (Ljava/lang/String;)V
      //   280: aload_1
      //   281: athrow
      //   282: new com/google/gson/Gson
      //   285: astore #4
      //   287: aload #4
      //   289: invokespecial <init> : ()V
      //   292: new ca/albertahealthservices/contacttracing/idmanager/TempIDManager$getTemporaryIDs$1$result$1
      //   295: astore_2
      //   296: aload_2
      //   297: invokespecial <init> : ()V
      //   300: aload #4
      //   302: aload_1
      //   303: aload_2
      //   304: invokevirtual getType : ()Ljava/lang/reflect/Type;
      //   307: invokevirtual fromJson : (Ljava/lang/String;Ljava/lang/reflect/Type;)Ljava/lang/Object;
      //   310: astore_1
      //   311: aload_1
      //   312: ldc 'Gson().fromJson(\\n       …tType()\\n                )'
      //   314: invokestatic checkExpressionValueIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
      //   317: aload_1
      //   318: checkcast java/util/HashMap
      //   321: astore_2
      //   322: aload_2
      //   323: ldc 'status'
      //   325: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
      //   328: astore #4
      //   330: aload_2
      //   331: ldc 'tempIDs'
      //   333: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
      //   336: astore_1
      //   337: aload_2
      //   338: checkcast java/util/Map
      //   341: ldc 'refreshTime'
      //   343: invokestatic getValue : (Ljava/util/Map;Ljava/lang/Object;)Ljava/lang/Object;
      //   346: astore_2
      //   347: aload_2
      //   348: ifnull -> 604
      //   351: aload_2
      //   352: checkcast java/lang/Double
      //   355: invokevirtual doubleValue : ()D
      //   358: dstore #6
      //   360: aload #4
      //   362: ldc_w 'SUCCESS'
      //   365: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
      //   368: iconst_1
      //   369: ixor
      //   370: ifeq -> 396
      //   373: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   376: ldc 'TempIDManager'
      //   378: ldc_w '[TempID] Error getting Temporary IDs - status not SUCCESS'
      //   381: invokevirtual d : (Ljava/lang/String;Ljava/lang/String;)V
      //   384: getstatic ca/albertahealthservices/contacttracing/logging/WFLog.Companion : Lca/albertahealthservices/contacttracing/logging/WFLog$Companion;
      //   387: ldc_w '[TempID] Error getting Temporary IDs - status not SUCCESS'
      //   390: invokevirtual logError : (Ljava/lang/String;)V
      //   393: goto -> 654
      //   396: aload_1
      //   397: ifnonnull -> 423
      //   400: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   403: ldc 'TempIDManager'
      //   405: ldc_w '[TempID] Error getting Temporary IDs - no temp IDs returned'
      //   408: invokevirtual d : (Ljava/lang/String;Ljava/lang/String;)V
      //   411: getstatic ca/albertahealthservices/contacttracing/logging/WFLog.Companion : Lca/albertahealthservices/contacttracing/logging/WFLog$Companion;
      //   414: ldc_w '[TempID] Error getting Temporary IDs - no temp IDs returned'
      //   417: invokevirtual logError : (Ljava/lang/String;)V
      //   420: goto -> 654
      //   423: dload #6
      //   425: invokestatic boxDouble : (D)Ljava/lang/Double;
      //   428: ifnonnull -> 454
      //   431: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   434: ldc 'TempIDManager'
      //   436: ldc_w '[TempID] Error getting Temporary IDs - no refreshTime returned'
      //   439: invokevirtual d : (Ljava/lang/String;Ljava/lang/String;)V
      //   442: getstatic ca/albertahealthservices/contacttracing/logging/WFLog.Companion : Lca/albertahealthservices/contacttracing/logging/WFLog$Companion;
      //   445: ldc_w '[TempID] Error getting Temporary IDs - no refreshTime returned'
      //   448: invokevirtual logError : (Ljava/lang/String;)V
      //   451: goto -> 654
      //   454: new com/google/gson/GsonBuilder
      //   457: astore #4
      //   459: aload #4
      //   461: invokespecial <init> : ()V
      //   464: aload #4
      //   466: invokevirtual create : ()Lcom/google/gson/Gson;
      //   469: aload_1
      //   470: invokevirtual toJson : (Ljava/lang/Object;)Ljava/lang/String;
      //   473: astore_1
      //   474: aload_1
      //   475: ldc_w 'GsonBuilder().create().toJson(tempIDs)'
      //   478: invokestatic checkExpressionValueIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
      //   481: getstatic kotlin/text/Charsets.UTF_8 : Ljava/nio/charset/Charset;
      //   484: astore #4
      //   486: aload_1
      //   487: ifnull -> 592
      //   490: aload_1
      //   491: aload #4
      //   493: invokevirtual getBytes : (Ljava/nio/charset/Charset;)[B
      //   496: astore #4
      //   498: aload #4
      //   500: ldc_w '(this as java.lang.String).getBytes(charset)'
      //   503: invokestatic checkExpressionValueIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
      //   506: getstatic ca/albertahealthservices/contacttracing/idmanager/TempIDManager.INSTANCE : Lca/albertahealthservices/contacttracing/idmanager/TempIDManager;
      //   509: astore #5
      //   511: aload_0
      //   512: getfield $context : Landroid/content/Context;
      //   515: astore_1
      //   516: getstatic kotlin/text/Charsets.UTF_8 : Ljava/nio/charset/Charset;
      //   519: astore_2
      //   520: new java/lang/String
      //   523: astore #8
      //   525: aload #8
      //   527: aload #4
      //   529: aload_2
      //   530: invokespecial <init> : ([BLjava/nio/charset/Charset;)V
      //   533: aload #5
      //   535: aload_1
      //   536: aload #8
      //   538: invokevirtual storeTemporaryIDs : (Landroid/content/Context;Ljava/lang/String;)V
      //   541: getstatic ca/albertahealthservices/contacttracing/Preference.INSTANCE : Lca/albertahealthservices/contacttracing/Preference;
      //   544: astore_1
      //   545: aload_0
      //   546: getfield $context : Landroid/content/Context;
      //   549: astore #4
      //   551: dload #6
      //   553: d2l
      //   554: lstore #9
      //   556: sipush #1000
      //   559: i2l
      //   560: lstore #11
      //   562: aload_1
      //   563: aload #4
      //   565: lload #9
      //   567: lload #11
      //   569: lmul
      //   570: invokevirtual putNextFetchTimeInMillis : (Landroid/content/Context;J)V
      //   573: getstatic ca/albertahealthservices/contacttracing/Preference.INSTANCE : Lca/albertahealthservices/contacttracing/Preference;
      //   576: aload_0
      //   577: getfield $context : Landroid/content/Context;
      //   580: invokestatic currentTimeMillis : ()J
      //   583: lload #11
      //   585: lmul
      //   586: invokevirtual putLastFetchTimeInMillis : (Landroid/content/Context;J)V
      //   589: goto -> 654
      //   592: new kotlin/TypeCastException
      //   595: astore_1
      //   596: aload_1
      //   597: ldc 'null cannot be cast to non-null type java.lang.String'
      //   599: invokespecial <init> : (Ljava/lang/String;)V
      //   602: aload_1
      //   603: athrow
      //   604: new kotlin/TypeCastException
      //   607: astore_1
      //   608: aload_1
      //   609: ldc_w 'null cannot be cast to non-null type kotlin.Double'
      //   612: invokespecial <init> : (Ljava/lang/String;)V
      //   615: aload_1
      //   616: athrow
      //   617: new java/lang/Exception
      //   620: astore_1
      //   621: aload_1
      //   622: ldc_w 'Failed to get temp ids'
      //   625: invokespecial <init> : (Ljava/lang/String;)V
      //   628: aload_1
      //   629: checkcast java/lang/Throwable
      //   632: athrow
      //   633: astore_1
      //   634: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   637: ldc 'TempIDManager'
      //   639: ldc_w '[TempID] Error getting Temporary IDs'
      //   642: invokevirtual d : (Ljava/lang/String;Ljava/lang/String;)V
      //   645: getstatic ca/albertahealthservices/contacttracing/logging/WFLog.Companion : Lca/albertahealthservices/contacttracing/logging/WFLog$Companion;
      //   648: ldc_w '[TempID] Error getting Temporary IDs'
      //   651: invokevirtual logError : (Ljava/lang/String;)V
      //   654: getstatic kotlin/Unit.INSTANCE : Lkotlin/Unit;
      //   657: areturn
      //   658: astore_1
      //   659: goto -> 634
      // Exception table:
      //   from	to	target	type
      //   36	40	633	java/lang/Exception
      //   63	114	633	java/lang/Exception
      //   114	133	658	java/lang/Exception
      //   144	177	658	java/lang/Exception
      //   181	208	658	java/lang/Exception
      //   215	229	658	java/lang/Exception
      //   240	246	658	java/lang/Exception
      //   251	267	658	java/lang/Exception
      //   270	282	658	java/lang/Exception
      //   282	347	658	java/lang/Exception
      //   351	393	658	java/lang/Exception
      //   400	420	658	java/lang/Exception
      //   423	451	658	java/lang/Exception
      //   454	486	658	java/lang/Exception
      //   490	551	658	java/lang/Exception
      //   562	589	658	java/lang/Exception
      //   592	604	658	java/lang/Exception
      //   604	617	658	java/lang/Exception
      //   617	633	658	java/lang/Exception
    }
    
    @Metadata(bv = {1, 0, 3}, d1 = {"\000\027\n\000\n\002\030\002\n\002\030\002\n\002\020\016\n\002\020\000\n\000*\001\000\b\n\030\0002\024\022\020\022\016\022\004\022\0020\003\022\004\022\0020\0040\0020\001¨\006\005"}, d2 = {"ca/albertahealthservices/contacttracing/idmanager/TempIDManager$getTemporaryIDs$1$result$1", "Lcom/google/gson/reflect/TypeToken;", "Ljava/util/HashMap;", "", "", "app_release"}, k = 1, mv = {1, 1, 16})
    public static final class TempIDManager$getTemporaryIDs$1$result$1 extends TypeToken<HashMap<String, Object>> {}
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\027\n\000\n\002\030\002\n\002\030\002\n\002\020\016\n\002\020\000\n\000*\001\000\b\n\030\0002\024\022\020\022\016\022\004\022\0020\003\022\004\022\0020\0040\0020\001¨\006\005"}, d2 = {"ca/albertahealthservices/contacttracing/idmanager/TempIDManager$getTemporaryIDs$1$result$1", "Lcom/google/gson/reflect/TypeToken;", "Ljava/util/HashMap;", "", "", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class TempIDManager$getTemporaryIDs$1$result$1 extends TypeToken<HashMap<String, Object>> {}
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/idmanager/TempIDManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */