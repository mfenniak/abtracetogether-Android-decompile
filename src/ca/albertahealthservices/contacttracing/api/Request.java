package ca.albertahealthservices.contacttracing.api;

import ca.albertahealthservices.contacttracing.api.auth.SmsCodeChallengeHandler;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLResourceRequest;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.WLResponseListener;
import com.worklight.wlclient.api.challengehandler.BaseChallengeHandler;
import java.net.URI;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import org.json.JSONObject;

@Metadata(bv = {1, 0, 3}, d1 = {"\000>\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\007\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002Jm\020\017\032\0020\0202\006\020\021\032\0020\0042\006\020\022\032\0020\0042\b\b\002\020\023\032\0020\0242\n\b\002\020\025\032\004\030\0010\0042\n\b\002\020\026\032\004\030\0010\0272(\b\002\020\030\032\"\022\004\022\0020\004\022\004\022\0020\004\030\0010\031j\020\022\004\022\0020\004\022\004\022\0020\004\030\001`\032H@ø\001\000¢\006\002\020\033R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000R\016\020\006\032\0020\004XT¢\006\002\n\000R\016\020\007\032\0020\004XT¢\006\002\n\000R\016\020\b\032\0020\004XT¢\006\002\n\000R\016\020\t\032\0020\004XT¢\006\002\n\000R\016\020\n\032\0020\004XT¢\006\002\n\000R\022\020\013\032\0020\fX\005¢\006\006\032\004\b\r\020\016\002\004\n\002\b\031¨\006\034"}, d2 = {"Lca/albertahealthservices/contacttracing/api/Request;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "DELETE", "", "GET", "HEAD", "OPTIONS", "POST", "PUT", "TRACE", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "runRequest", "Lca/albertahealthservices/contacttracing/api/Response;", "path", "method", "timeout", "", "scope", "data", "Lorg/json/JSONObject;", "queryParams", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Lorg/json/JSONObject;Ljava/util/HashMap;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"}, k = 1, mv = {1, 1, 16})
public final class Request implements CoroutineScope {
  public static final String DELETE = "DELETE";
  
  public static final String GET = "GET";
  
  public static final String HEAD = "HEAD";
  
  public static final Request INSTANCE = new Request();
  
  public static final String OPTIONS = "OPTIONS";
  
  public static final String POST = "POST";
  
  public static final String PUT = "PUT";
  
  public static final String TRACE = "TRACE";
  
  static {
    WLClient.getInstance().registerChallengeHandler((BaseChallengeHandler)new SmsCodeChallengeHandler());
  }
  
  public CoroutineContext getCoroutineContext() {
    return this.$$delegate_0.getCoroutineContext();
  }
  
  public final Object runRequest(String paramString1, String paramString2, int paramInt, String paramString3, JSONObject paramJSONObject, HashMap<String, String> paramHashMap, Continuation<? super Response> paramContinuation) {
    SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(paramContinuation));
    Continuation continuation = (Continuation)safeContinuation;
    WLResourceRequest wLResourceRequest = new WLResourceRequest(new URI(paramString1), paramString2, paramInt, paramString3);
    CentralLog.Companion companion = CentralLog.Companion;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("method=");
    stringBuilder.append(wLResourceRequest.getMethod());
    stringBuilder.append(" - url=");
    stringBuilder.append(wLResourceRequest.getUrl());
    companion.d("Request", stringBuilder.toString());
    if (paramHashMap != null)
      wLResourceRequest.setQueryParameters(paramHashMap); 
    Request$runRequest$2$listener$1 request$runRequest$2$listener$1 = new Request$runRequest$2$listener$1(wLResourceRequest, continuation);
    if (paramJSONObject == null) {
      wLResourceRequest.send(request$runRequest$2$listener$1);
    } else {
      wLResourceRequest.send(paramJSONObject, request$runRequest$2$listener$1);
    } 
    Object object = safeContinuation.getOrThrow();
    if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED())
      DebugProbesKt.probeCoroutineSuspended(paramContinuation); 
    return object;
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\035\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\022\020\002\032\0020\0032\b\020\004\032\004\030\0010\005H\026J\022\020\006\032\0020\0032\b\020\004\032\004\030\0010\007H\026¨\006\b"}, d2 = {"ca/albertahealthservices/contacttracing/api/Request$runRequest$2$listener$1", "Lcom/worklight/wlclient/api/WLResponseListener;", "onFailure", "", "response", "Lcom/worklight/wlclient/api/WLFailResponse;", "onSuccess", "Lcom/worklight/wlclient/api/WLResponse;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class Request$runRequest$2$listener$1 implements WLResponseListener {
    Request$runRequest$2$listener$1(WLResourceRequest param1WLResourceRequest, Continuation param1Continuation) {}
    
    public void onFailure(WLFailResponse param1WLFailResponse) {
      // Byte code:
      //   0: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   3: astore_2
      //   4: new java/lang/StringBuilder
      //   7: dup
      //   8: invokespecial <init> : ()V
      //   11: astore_3
      //   12: aload_3
      //   13: ldc 'Request.onFailure url='
      //   15: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   18: pop
      //   19: aload_3
      //   20: aload_0
      //   21: getfield $request : Lcom/worklight/wlclient/api/WLResourceRequest;
      //   24: invokevirtual getUrl : ()Ljava/net/URL;
      //   27: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   30: pop
      //   31: aload_3
      //   32: ldc ' -  response='
      //   34: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   37: pop
      //   38: aload_3
      //   39: aload_1
      //   40: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   43: pop
      //   44: aload_2
      //   45: ldc 'Request'
      //   47: aload_3
      //   48: invokevirtual toString : ()Ljava/lang/String;
      //   51: invokevirtual d : (Ljava/lang/String;Ljava/lang/String;)V
      //   54: getstatic ca/albertahealthservices/contacttracing/logging/WFLog.Companion : Lca/albertahealthservices/contacttracing/logging/WFLog$Companion;
      //   57: astore_3
      //   58: new java/lang/StringBuilder
      //   61: dup
      //   62: invokespecial <init> : ()V
      //   65: astore_2
      //   66: aload_2
      //   67: ldc 'Request.onFailure request='
      //   69: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   72: pop
      //   73: aload_2
      //   74: aload_0
      //   75: getfield $request : Lcom/worklight/wlclient/api/WLResourceRequest;
      //   78: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   81: pop
      //   82: aload_2
      //   83: ldc ' -  response='
      //   85: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   88: pop
      //   89: aload_2
      //   90: aload_1
      //   91: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   94: pop
      //   95: aload_3
      //   96: aload_2
      //   97: invokevirtual toString : ()Ljava/lang/String;
      //   100: invokevirtual logError : (Ljava/lang/String;)V
      //   103: aload_1
      //   104: ifnull -> 161
      //   107: aload_1
      //   108: invokevirtual getErrorMsg : ()Ljava/lang/String;
      //   111: ifnull -> 161
      //   114: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   117: astore_2
      //   118: new java/lang/StringBuilder
      //   121: dup
      //   122: invokespecial <init> : ()V
      //   125: astore_3
      //   126: aload_3
      //   127: aload_1
      //   128: invokevirtual getErrorStatusCode : ()Ljava/lang/String;
      //   131: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   134: pop
      //   135: aload_3
      //   136: ldc ' - '
      //   138: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   141: pop
      //   142: aload_3
      //   143: aload_1
      //   144: invokevirtual getErrorMsg : ()Ljava/lang/String;
      //   147: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   150: pop
      //   151: aload_2
      //   152: ldc 'Request'
      //   154: aload_3
      //   155: invokevirtual toString : ()Ljava/lang/String;
      //   158: invokevirtual d : (Ljava/lang/String;Ljava/lang/String;)V
      //   161: aload_0
      //   162: getfield $cont : Lkotlin/coroutines/Continuation;
      //   165: astore #4
      //   167: aconst_null
      //   168: astore #5
      //   170: aload_1
      //   171: ifnull -> 185
      //   174: aload_1
      //   175: invokevirtual getStatus : ()I
      //   178: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   181: astore_2
      //   182: goto -> 187
      //   185: aconst_null
      //   186: astore_2
      //   187: aload_1
      //   188: ifnull -> 199
      //   191: aload_1
      //   192: invokevirtual getResponseText : ()Ljava/lang/String;
      //   195: astore_3
      //   196: goto -> 201
      //   199: aconst_null
      //   200: astore_3
      //   201: aload_1
      //   202: ifnull -> 211
      //   205: aload_1
      //   206: invokevirtual getResponseJSON : ()Lorg/json/JSONObject;
      //   209: astore #5
      //   211: getstatic ca/albertahealthservices/contacttracing/api/ErrorCode.INSTANCE : Lca/albertahealthservices/contacttracing/api/ErrorCode;
      //   214: astore #6
      //   216: getstatic ca/albertahealthservices/contacttracing/TracerApp.Companion : Lca/albertahealthservices/contacttracing/TracerApp$Companion;
      //   219: invokevirtual getAppContext : ()Landroid/content/Context;
      //   222: astore #7
      //   224: ldc ''
      //   226: astore #8
      //   228: aload_1
      //   229: ifnull -> 246
      //   232: aload_1
      //   233: invokevirtual getErrorStatusCode : ()Ljava/lang/String;
      //   236: astore #9
      //   238: aload #9
      //   240: ifnull -> 246
      //   243: goto -> 250
      //   246: ldc ''
      //   248: astore #9
      //   250: aload #6
      //   252: aload #7
      //   254: aload #9
      //   256: invokevirtual getStringForErrorCode : (Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
      //   259: astore #7
      //   261: aload #8
      //   263: astore #9
      //   265: aload_1
      //   266: ifnull -> 285
      //   269: aload_1
      //   270: invokevirtual getErrorStatusCode : ()Ljava/lang/String;
      //   273: astore_1
      //   274: aload #8
      //   276: astore #9
      //   278: aload_1
      //   279: ifnull -> 285
      //   282: aload_1
      //   283: astore #9
      //   285: new ca/albertahealthservices/contacttracing/api/Response
      //   288: dup
      //   289: aload_2
      //   290: aload_3
      //   291: aload #5
      //   293: aload #7
      //   295: aload #9
      //   297: invokespecial <init> : (Ljava/lang/Integer;Ljava/lang/String;Lorg/json/JSONObject;Ljava/lang/String;Ljava/lang/String;)V
      //   300: astore_1
      //   301: getstatic kotlin/Result.Companion : Lkotlin/Result$Companion;
      //   304: astore_2
      //   305: aload #4
      //   307: aload_1
      //   308: invokestatic constructor-impl : (Ljava/lang/Object;)Ljava/lang/Object;
      //   311: invokeinterface resumeWith : (Ljava/lang/Object;)V
      //   316: return
    }
    
    public void onSuccess(WLResponse param1WLResponse) {
      Integer integer;
      CentralLog.Companion companion = CentralLog.Companion;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Request.onSuccess url=");
      stringBuilder.append(this.$request.getUrl());
      stringBuilder.append(" - response=");
      JSONObject jSONObject = null;
      if (param1WLResponse != null) {
        integer = (Integer)param1WLResponse.getResponseJSON();
      } else {
        integer = null;
      } 
      stringBuilder.append(integer);
      companion.d("Request", stringBuilder.toString());
      Continuation continuation = this.$cont;
      if (param1WLResponse != null) {
        integer = Integer.valueOf(param1WLResponse.getStatus());
      } else {
        integer = null;
      } 
      if (param1WLResponse != null) {
        String str = param1WLResponse.getResponseText();
      } else {
        stringBuilder = null;
      } 
      if (param1WLResponse != null)
        jSONObject = param1WLResponse.getResponseJSON(); 
      Response response = new Response(integer, (String)stringBuilder, jSONObject, null, null, 24, null);
      Result.Companion companion1 = Result.Companion;
      continuation.resumeWith(Result.constructor-impl(response));
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/api/Request.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */