package ca.albertahealthservices.contacttracing.idmanager;

import android.content.Context;
import ca.albertahealthservices.contacttracing.Preference;
import ca.albertahealthservices.contacttracing.Utils;
import ca.albertahealthservices.contacttracing.api.Request;
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
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
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
    CentralLog.Companion companion = CentralLog.Companion;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("[TempID] Before Sort: ");
    byte b = 0;
    stringBuilder2.append(paramArrayOfTemporaryID[0]);
    companion.d("TempIDManager", stringBuilder2.toString());
    if (paramArrayOfTemporaryID.length > 1)
      ArraysKt.sortWith((Object[])paramArrayOfTemporaryID, new TempIDManager$convertToQueue$$inlined$sortBy$1()); 
    companion = CentralLog.Companion;
    stringBuilder2 = new StringBuilder();
    stringBuilder2.append("[TempID] After Sort: ");
    stringBuilder2.append(paramArrayOfTemporaryID[0]);
    companion.d("TempIDManager", stringBuilder2.toString());
    LinkedList<TemporaryID> linkedList = new LinkedList();
    int i = paramArrayOfTemporaryID.length;
    while (b < i) {
      linkedList.offer(paramArrayOfTemporaryID[b]);
      b++;
    } 
    companion = CentralLog.Companion;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("[TempID] Retrieving from Queue: ");
    stringBuilder1.append(linkedList.peek());
    companion.d("TempIDManager", stringBuilder1.toString());
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
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("[TempID Total number of items in queue: ");
    stringBuilder2.append(paramQueue.size());
    companion2.d("TempIDManager", stringBuilder2.toString());
    CentralLog.Companion companion1 = CentralLog.Companion;
    stringBuilder2 = new StringBuilder();
    stringBuilder2.append("[TempID Number of items popped from queue: ");
    stringBuilder2.append(b);
    companion1.d("TempIDManager", stringBuilder2.toString());
    companion1 = CentralLog.Companion;
    stringBuilder2 = new StringBuilder();
    stringBuilder2.append("[TempID] Current time: ");
    stringBuilder2.append(l1);
    companion1.d("TempIDManager", stringBuilder2.toString());
    CentralLog.Companion companion3 = CentralLog.Companion;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("[TempID] Start time: ");
    stringBuilder1.append(l2 * l3);
    companion3.d("TempIDManager", stringBuilder1.toString());
    companion3 = CentralLog.Companion;
    stringBuilder1 = new StringBuilder();
    stringBuilder1.append("[TempID] Expiry time: ");
    stringBuilder1.append(l4);
    companion3.d("TempIDManager", stringBuilder1.toString());
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
  @DebugMetadata(c = "ca.albertahealthservices.contacttracing.idmanager.TempIDManager$getTemporaryIDs$1", f = "TempIDManager.kt", i = {0, 0, 0}, l = {132}, m = "invokeSuspend", n = {"$this$launch", "userId", "queryParams"}, s = {"L$0", "L$1", "L$2"})
  static final class TempIDManager$getTemporaryIDs$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    Object L$0;
    
    Object L$1;
    
    Object L$2;
    
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
      Object object = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      int i = this.label;
      if (i != 0) {
        if (i == 1) {
          HashMap hashMap = (HashMap)this.L$2;
          String str = (String)this.L$1;
          CoroutineScope coroutineScope = (CoroutineScope)this.L$0;
          try {
            ResultKt.throwOnFailure(param1Object);
            param1Object = param1Object;
            CentralLog.Companion.d("TempIDManager", param1Object.toString());
          } catch (Exception exception) {
            CentralLog.Companion.d("TempIDManager", "[TempID] Error getting Temporary IDs");
          } 
        } else {
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } 
      } else {
        ResultKt.throwOnFailure(exception);
        CoroutineScope coroutineScope = this.p$;
        String str = Preference.INSTANCE.getUUID(this.$context);
        if ((Intrinsics.areEqual(str, "") ^ true) != 0) {
          HashMap<Object, Object> hashMap = new HashMap<>();
          this();
          hashMap.put("userId", str);
          Request request = Request.INSTANCE;
          this.L$0 = coroutineScope;
          this.L$1 = str;
          this.L$2 = hashMap;
          this.label = 1;
          Object object2 = Request.runRequest$default(request, "/adapters/tempidsAdapter/tempIds", "GET", 0, null, null, hashMap, (Continuation)this, 28, null);
          object1 = object2;
          if (object2 == object)
            return object; 
        } else {
          CentralLog.Companion.d("TempIDManager", "User is not logged in");
          Utils.INSTANCE.restartApp(this.$context, 1, "[TempID] Error getting Temporary IDs, no userId");
          return Unit.INSTANCE;
        } 
        Object object1 = object1;
        CentralLog.Companion.d("TempIDManager", object1.toString());
      } 
      return Unit.INSTANCE;
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