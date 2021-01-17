package ca.albertahealthservices.contacttracing.protocol.v2;

import ca.albertahealthservices.contacttracing.streetpass.PeripheralDevice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

@Metadata(bv = {1, 0, 3}, d1 = {"\000(\n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\t\n\002\020\022\n\002\b\002\030\000 \0232\0020\001:\001\023B%\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\005\022\006\020\007\032\0020\b¢\006\002\020\tJ\006\020\021\032\0020\022R\021\020\004\032\0020\005¢\006\b\n\000\032\004\b\n\020\013R\021\020\f\032\0020\005¢\006\b\n\000\032\004\b\r\020\013R\021\020\006\032\0020\005¢\006\b\n\000\032\004\b\016\020\013R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\017\020\020¨\006\024"}, d2 = {"Lca/albertahealthservices/contacttracing/protocol/v2/V2ReadRequestPayload;", "", "v", "", "id", "", "o", "peripheral", "Lca/albertahealthservices/contacttracing/streetpass/PeripheralDevice;", "(ILjava/lang/String;Ljava/lang/String;Lca/albertahealthservices/contacttracing/streetpass/PeripheralDevice;)V", "getId", "()Ljava/lang/String;", "mp", "getMp", "getO", "getV", "()I", "getPayload", "", "Companion", "app_release"}, k = 1, mv = {1, 1, 16})
public final class V2ReadRequestPayload {
  public static final Companion Companion = new Companion(null);
  
  private static final Gson gson;
  
  private final String id;
  
  private final String mp;
  
  private final String o;
  
  private final int v;
  
  static {
    Gson gson = (new GsonBuilder()).disableHtmlEscaping().create();
    Intrinsics.checkExpressionValueIsNotNull(gson, "GsonBuilder()\n          …leHtmlEscaping().create()");
    gson = gson;
  }
  
  public V2ReadRequestPayload(int paramInt, String paramString1, String paramString2, PeripheralDevice paramPeripheralDevice) {
    this.v = paramInt;
    this.id = paramString1;
    this.o = paramString2;
    this.mp = paramPeripheralDevice.getModelP();
  }
  
  public final String getId() {
    return this.id;
  }
  
  public final String getMp() {
    return this.mp;
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
  
  public final int getV() {
    return this.v;
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000 \n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\022\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\016\020\007\032\0020\b2\006\020\t\032\0020\nR\021\020\003\032\0020\004¢\006\b\n\000\032\004\b\005\020\006¨\006\013"}, d2 = {"Lca/albertahealthservices/contacttracing/protocol/v2/V2ReadRequestPayload$Companion;", "", "()V", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "fromPayload", "Lca/albertahealthservices/contacttracing/protocol/v2/V2ReadRequestPayload;", "dataBytes", "", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class Companion {
    private Companion() {}
    
    public final V2ReadRequestPayload fromPayload(byte[] param1ArrayOfbyte) {
      Intrinsics.checkParameterIsNotNull(param1ArrayOfbyte, "dataBytes");
      String str = new String(param1ArrayOfbyte, Charsets.UTF_8);
      Object object = getGson().fromJson(str, V2ReadRequestPayload.class);
      Intrinsics.checkExpressionValueIsNotNull(object, "gson.fromJson(dataString…questPayload::class.java)");
      return (V2ReadRequestPayload)object;
    }
    
    public final Gson getGson() {
      return V2ReadRequestPayload.gson;
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/protocol/v2/V2ReadRequestPayload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */