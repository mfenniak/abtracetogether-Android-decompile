package ca.albertahealthservices.contacttracing.logging;

import android.os.Environment;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000N\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\020\t\n\002\b\002\n\002\020\030\n\002\b\003\n\002\020\002\n\000\n\002\020\021\n\002\b\t\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\b\020\023\032\0020\024H\002J\020\020\025\032\0020\n2\006\020\026\032\0020\004H\002J\037\020\027\032\0020\0302\022\020\031\032\n\022\006\b\001\022\0020\0040\032\"\0020\004¢\006\002\020\033J\037\020\034\032\0020\0302\022\020\031\032\n\022\006\b\001\022\0020\0040\032\"\0020\004¢\006\002\020\033J\b\020\035\032\0020\nH\002J\037\020\036\032\0020\0302\022\020\031\032\n\022\006\b\001\022\0020\0040\032\"\0020\004¢\006\002\020\033J%\020\037\032\0020\0302\006\020 \032\0020\0042\016\020\031\032\n\022\006\b\001\022\0020\0040\032H\002¢\006\002\020!J\037\020\"\032\0020\0302\022\020\031\032\n\022\006\b\001\022\0020\0040\032\"\0020\004¢\006\002\020\033R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000R\016\020\006\032\0020\007X\016¢\006\002\n\000R\016\020\b\032\0020\004X\016¢\006\002\n\000R\016\020\t\032\0020\nX.¢\006\002\n\000R\016\020\013\032\0020\fX\004¢\006\002\n\000R\024\020\r\032\0020\0168BX\004¢\006\006\032\004\b\r\020\017R\016\020\020\032\0020\021X\016¢\006\002\n\000R\016\020\022\032\0020\fX\004¢\006\002\n\000¨\006#"}, d2 = {"Lca/albertahealthservices/contacttracing/logging/SDLog;", "", "()V", "APP_NAME", "", "FOLDER", "buffer", "Ljava/lang/StringBuffer;", "cachedDateStamp", "cachedFileWriter", "Ljava/io/BufferedWriter;", "dateFormat", "Ljava/text/SimpleDateFormat;", "isWritable", "", "()Z", "lastWrite", "", "timestampFormat", "checkSDState", "", "createFileWriter", "dateStamp", "d", "", "message", "", "([Ljava/lang/String;)V", "e", "getFileWriter", "i", "log", "label", "(Ljava/lang/String;[Ljava/lang/String;)V", "w", "app_release"}, k = 1, mv = {1, 1, 16})
public final class SDLog {
  private static final String APP_NAME = "ContactTracing";
  
  private static final String FOLDER = "SDLogging";
  
  public static final SDLog INSTANCE = new SDLog();
  
  private static StringBuffer buffer;
  
  private static String cachedDateStamp;
  
  private static BufferedWriter cachedFileWriter;
  
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
  
  private static long lastWrite;
  
  private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
  
  static {
    buffer = new StringBuffer();
    cachedDateStamp = "";
  }
  
  private final boolean[] checkSDState() {
    // Byte code:
    //   0: invokestatic getExternalStorageState : ()Ljava/lang/String;
    //   3: astore_1
    //   4: aload_1
    //   5: ifnonnull -> 11
    //   8: goto -> 62
    //   11: aload_1
    //   12: invokevirtual hashCode : ()I
    //   15: istore_2
    //   16: iload_2
    //   17: ldc 1242932856
    //   19: if_icmpeq -> 48
    //   22: iload_2
    //   23: ldc 1299749220
    //   25: if_icmpeq -> 31
    //   28: goto -> 62
    //   31: aload_1
    //   32: ldc 'mounted_ro'
    //   34: invokevirtual equals : (Ljava/lang/Object;)Z
    //   37: ifeq -> 62
    //   40: iconst_1
    //   41: istore_3
    //   42: iconst_0
    //   43: istore #4
    //   45: goto -> 67
    //   48: aload_1
    //   49: ldc 'mounted'
    //   51: invokevirtual equals : (Ljava/lang/Object;)Z
    //   54: ifeq -> 62
    //   57: iconst_1
    //   58: istore_3
    //   59: goto -> 64
    //   62: iconst_0
    //   63: istore_3
    //   64: iload_3
    //   65: istore #4
    //   67: iconst_2
    //   68: newarray boolean
    //   70: dup
    //   71: iconst_0
    //   72: iload_3
    //   73: bastore
    //   74: dup
    //   75: iconst_1
    //   76: iload #4
    //   78: bastore
    //   79: areturn
  }
  
  private final BufferedWriter createFileWriter(String paramString) {
    BufferedWriter bufferedWriter;
    StringBuilder stringBuilder1 = new StringBuilder();
    File file2 = Environment.getExternalStorageDirectory();
    Intrinsics.checkExpressionValueIsNotNull(file2, "Environment.getExternalStorageDirectory()");
    stringBuilder1.append(file2.getAbsolutePath());
    stringBuilder1.append("/");
    stringBuilder1.append("SDLogging");
    File file1 = new File(stringBuilder1.toString());
    file1.mkdirs();
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("ContactTracing_");
    stringBuilder2.append(paramString);
    stringBuilder2.append(".txt");
    FileWriter fileWriter = new FileWriter(new File(file1, stringBuilder2.toString()), true);
    if (fileWriter instanceof BufferedWriter) {
      bufferedWriter = (BufferedWriter)fileWriter;
    } else {
      bufferedWriter = new BufferedWriter(bufferedWriter, 8192);
    } 
    return bufferedWriter;
  }
  
  private final BufferedWriter getFileWriter() {
    BufferedWriter bufferedWriter1;
    BufferedWriter bufferedWriter2;
    String str = dateFormat.format(new Date());
    if (Intrinsics.areEqual(str, cachedDateStamp)) {
      bufferedWriter1 = cachedFileWriter;
      bufferedWriter2 = bufferedWriter1;
      if (bufferedWriter1 == null) {
        Intrinsics.throwUninitializedPropertyAccessException("cachedFileWriter");
        bufferedWriter2 = bufferedWriter1;
      } 
    } else {
      bufferedWriter2 = cachedFileWriter;
      if (bufferedWriter2 != null) {
        if (bufferedWriter2 == null)
          Intrinsics.throwUninitializedPropertyAccessException("cachedFileWriter"); 
        bufferedWriter2.flush();
        bufferedWriter2 = cachedFileWriter;
        if (bufferedWriter2 == null)
          Intrinsics.throwUninitializedPropertyAccessException("cachedFileWriter"); 
        bufferedWriter2.close();
      } 
      Intrinsics.checkExpressionValueIsNotNull(bufferedWriter1, "dateStamp");
      bufferedWriter2 = createFileWriter((String)bufferedWriter1);
      cachedFileWriter = bufferedWriter2;
      cachedDateStamp = (String)bufferedWriter1;
      if (bufferedWriter2 == null)
        Intrinsics.throwUninitializedPropertyAccessException("cachedFileWriter"); 
    } 
    return bufferedWriter2;
  }
  
  private final boolean isWritable() {
    boolean[] arrayOfBoolean = checkSDState();
    boolean bool = arrayOfBoolean[0];
    return arrayOfBoolean[1] & bool;
  }
  
  private final void log(String paramString, String[] paramArrayOfString) {
    if (!isWritable())
      return; 
    if (paramArrayOfString == null)
      return; 
    String str1 = timestampFormat.format(new Date());
    String str2 = ArraysKt.joinToString$default((Object[])paramArrayOfString, " ", null, null, 0, null, null, 62, null);
    StringBuffer stringBuffer = buffer;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str1);
    stringBuilder.append(' ');
    stringBuilder.append(paramString);
    stringBuilder.append(' ');
    stringBuilder.append(str2);
    stringBuilder.append('\n');
    stringBuffer.append(stringBuilder.toString());
    try {
      BufferedWriter bufferedWriter = getFileWriter();
      bufferedWriter.write(buffer.toString());
      stringBuffer = new StringBuffer();
      this();
      buffer = stringBuffer;
      if (System.currentTimeMillis() - lastWrite > 10000L) {
        bufferedWriter.flush();
        lastWrite = System.currentTimeMillis();
      } 
    } catch (IOException iOException) {
      StringBuffer stringBuffer1 = buffer;
      stringBuilder = new StringBuilder();
      stringBuilder.append(str1);
      stringBuilder.append(" ERROR SDLog ??? IOException while writing to SDLog: ");
      stringBuilder.append(iOException.getMessage());
      stringBuilder.append('\n');
      stringBuffer1.append(stringBuilder.toString());
    } 
  }
  
  public final void d(String... paramVarArgs) {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "message");
    log("DEBUG", paramVarArgs);
  }
  
  public final void e(String... paramVarArgs) {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "message");
    log("ERROR", paramVarArgs);
  }
  
  public final void i(String... paramVarArgs) {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "message");
    log("INFO", paramVarArgs);
  }
  
  public final void w(String... paramVarArgs) {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "message");
    log("WARN", paramVarArgs);
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/logging/SDLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */