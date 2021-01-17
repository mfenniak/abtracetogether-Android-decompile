package ca.albertahealthservices.contacttracing.protocol.v2;

import ca.albertahealthservices.contacttracing.streetpass.CentralDevice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

@Metadata(bv = {1, 0, 3}, d1 = {"\000(\n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\013\n\002\020\022\n\002\b\002\030\000 \0252\0020\001:\001\025B-\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\005\022\006\020\007\032\0020\b\022\006\020\t\032\0020\003¢\006\002\020\nJ\006\020\023\032\0020\024R\021\020\004\032\0020\005¢\006\b\n\000\032\004\b\013\020\fR\021\020\r\032\0020\005¢\006\b\n\000\032\004\b\016\020\fR\021\020\006\032\0020\005¢\006\b\n\000\032\004\b\017\020\fR\021\020\t\032\0020\003¢\006\b\n\000\032\004\b\020\020\021R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\022\020\021¨\006\026"}, d2 = {"Lca/albertahealthservices/contacttracing/protocol/v2/V2WriteRequestPayload;", "", "v", "", "id", "", "o", "central", "Lca/albertahealthservices/contacttracing/streetpass/CentralDevice;", "rs", "(ILjava/lang/String;Ljava/lang/String;Lca/albertahealthservices/contacttracing/streetpass/CentralDevice;I)V", "getId", "()Ljava/lang/String;", "mc", "getMc", "getO", "getRs", "()I", "getV", "getPayload", "", "Companion", "app_release"}, k = 1, mv = {1, 1, 16})
public final class V2WriteRequestPayload {
  public static final Companion Companion = new Companion(null);
  
  private static final Gson gson;
  
  private final String id;
  
  private final String mc;
  
  private final String o;
  
  private final int rs;
  
  private final int v;
  
  static {
    Gson gson = (new GsonBuilder()).disableHtmlEscaping().create();
    Intrinsics.checkExpressionValueIsNotNull(gson, "GsonBuilder()\n          …leHtmlEscaping().create()");
    gson = gson;
  }
  
  public V2WriteRequestPayload(int paramInt1, String paramString1, String paramString2, CentralDevice paramCentralDevice, int paramInt2) {
    this.v = paramInt1;
    this.id = paramString1;
    this.o = paramString2;
    this.rs = paramInt2;
    this.mc = paramCentralDevice.getModelC();
  }
  
  public final String getId() {
    return this.id;
  }
  
  public final String getMc() {
    return this.mc;
  }
  
  public final String getO() {
    return this.o;
  }
  
  public final byte[] getPayload() {
    String str = gson.toJson(this);
    Intrinsics.checkExpressionValueIsNotNull(str, "gson.toJson(this)");
    Charset charset = Charsets.UTF_8;
    if (str != null) {
      byte[] arrayOfByte = str.getBytes(charset);
      Intrinsics.checkExpressionValueIsNotNull(arrayOfByte, "(this as java.lang.String).getBytes(charset)");
      return arrayOfByte;
    } 
    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
  }
  
  public final int getRs() {
    return this.rs;
  }
  
  public final int getV() {
    return this.v;
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000 \n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\022\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\016\020\007\032\0020\b2\006\020\t\032\0020\nR\021\020\003\032\0020\004¢\006\b\n\000\032\004\b\005\020\006¨\006\013"}, d2 = {"Lca/albertahealthservices/contacttracing/protocol/v2/V2WriteRequestPayload$Companion;", "", "()V", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "fromPayload", "Lca/albertahealthservices/contacttracing/protocol/v2/V2WriteRequestPayload;", "dataBytes", "", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class Companion {
    private Companion() {}
    
    public final V2WriteRequestPayload fromPayload(byte[] param1ArrayOfbyte) {
      Intrinsics.checkParameterIsNotNull(param1ArrayOfbyte, "dataBytes");
      String str = new String(param1ArrayOfbyte, Charsets.UTF_8);
      Object object = getGson().fromJson(str, V2WriteRequestPayload.class);
      Intrinsics.checkExpressionValueIsNotNull(object, "gson.fromJson(dataString…questPayload::class.java)");
      return (V2WriteRequestPayload)object;
    }
    
    public final Gson getGson() {
      return V2WriteRequestPayload.gson;
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/protocol/v2/V2WriteRequestPayload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */