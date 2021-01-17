package ca.albertahealthservices.contacttracing.streetpass;

import android.bluetooth.BluetoothGatt;
import java.lang.reflect.Field;
import kotlin.Metadata;

@Metadata(bv = {1, 0, 3}, d1 = {"\0004\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\003\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\016\020\016\032\0020\0172\006\020\020\032\0020\007J\b\020\021\032\0020\tH\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\024\020\005\032\b\022\004\022\0020\0070\006X\016¢\006\002\n\000R\016\020\b\032\0020\tX\016¢\006\002\n\000R\016\020\n\032\0020\tX\016¢\006\002\n\000R\020\020\013\032\004\030\0010\fX\016¢\006\002\n\000R\020\020\r\032\004\030\0010\fX\016¢\006\002\n\000¨\006\022"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/PairingFix;", "", "()V", "TAG", "", "bluetoothGattClass", "Ljava/lang/Class;", "Landroid/bluetooth/BluetoothGatt;", "initComplete", "", "initFailed", "mAuthRetryField", "Ljava/lang/reflect/Field;", "mAuthRetryStateField", "bypassAuthenticationRetry", "", "gatt", "tryInit", "app_release"}, k = 1, mv = {1, 1, 16})
public final class PairingFix {
  public static final PairingFix INSTANCE = new PairingFix();
  
  private static final String TAG = "PairingFix";
  
  private static Class<BluetoothGatt> bluetoothGattClass = BluetoothGatt.class;
  
  private static boolean initComplete;
  
  private static boolean initFailed;
  
  private static Field mAuthRetryField;
  
  private static Field mAuthRetryStateField;
  
  private final boolean tryInit() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: getstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.initFailed : Z
    //   5: ifne -> 354
    //   8: getstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.initComplete : Z
    //   11: ifeq -> 17
    //   14: goto -> 354
    //   17: getstatic android/os/Build$VERSION.SDK_INT : I
    //   20: bipush #28
    //   22: if_icmple -> 66
    //   25: new android/content/pm/ApplicationInfo
    //   28: astore_1
    //   29: aload_1
    //   30: invokespecial <init> : ()V
    //   33: aload_1
    //   34: getfield targetSdkVersion : I
    //   37: bipush #28
    //   39: if_icmple -> 66
    //   42: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   45: ldc 'PairingFix'
    //   47: ldc 'Failed to initialise: mAuthRetryState is in restricted grey-list post API 28'
    //   49: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   52: iconst_1
    //   53: putstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.initFailed : Z
    //   56: iconst_1
    //   57: putstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.initComplete : Z
    //   60: aload_0
    //   61: monitorexit
    //   62: iconst_1
    //   63: iconst_1
    //   64: ixor
    //   65: ireturn
    //   66: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   69: ldc 'PairingFix'
    //   71: ldc 'Initialising StreetPassParingFix fields'
    //   73: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   76: getstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.bluetoothGattClass : Ljava/lang/Class;
    //   79: ldc 'mAuthRetryState'
    //   81: invokevirtual getDeclaredField : (Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   84: putstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.mAuthRetryStateField : Ljava/lang/reflect/Field;
    //   87: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   90: ldc 'PairingFix'
    //   92: ldc 'Found mAuthRetryState'
    //   94: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   97: goto -> 144
    //   100: astore_2
    //   101: goto -> 168
    //   104: astore_3
    //   105: goto -> 209
    //   108: astore_3
    //   109: goto -> 250
    //   112: astore_1
    //   113: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   116: ldc 'PairingFix'
    //   118: ldc 'No mAuthRetryState on this device, trying for mAuthRetry'
    //   120: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   123: getstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.bluetoothGattClass : Ljava/lang/Class;
    //   126: ldc 'mAuthRetry'
    //   128: invokevirtual getDeclaredField : (Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   131: putstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.mAuthRetryField : Ljava/lang/reflect/Field;
    //   134: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   137: ldc 'PairingFix'
    //   139: ldc 'Found mAuthRetry'
    //   141: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   144: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   147: ldc 'PairingFix'
    //   149: ldc 'Initialisation complete'
    //   151: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   154: iconst_1
    //   155: putstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.initComplete : Z
    //   158: iconst_0
    //   159: putstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.initFailed : Z
    //   162: aload_0
    //   163: monitorexit
    //   164: iconst_0
    //   165: iconst_1
    //   166: ixor
    //   167: ireturn
    //   168: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   171: astore_3
    //   172: new java/lang/StringBuilder
    //   175: astore_1
    //   176: aload_1
    //   177: invokespecial <init> : ()V
    //   180: aload_1
    //   181: ldc 'Encountered Exception while initialising: '
    //   183: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   186: pop
    //   187: aload_1
    //   188: aload_2
    //   189: invokevirtual getMessage : ()Ljava/lang/String;
    //   192: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   195: pop
    //   196: aload_3
    //   197: ldc 'PairingFix'
    //   199: aload_1
    //   200: invokevirtual toString : ()Ljava/lang/String;
    //   203: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   206: goto -> 330
    //   209: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   212: astore_1
    //   213: new java/lang/StringBuilder
    //   216: astore_2
    //   217: aload_2
    //   218: invokespecial <init> : ()V
    //   221: aload_2
    //   222: ldc 'Encountered NPE while initialising: '
    //   224: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   227: pop
    //   228: aload_2
    //   229: aload_3
    //   230: invokevirtual getMessage : ()Ljava/lang/String;
    //   233: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   236: pop
    //   237: aload_1
    //   238: ldc 'PairingFix'
    //   240: aload_2
    //   241: invokevirtual toString : ()Ljava/lang/String;
    //   244: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   247: goto -> 330
    //   250: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   253: astore_1
    //   254: new java/lang/StringBuilder
    //   257: astore_2
    //   258: aload_2
    //   259: invokespecial <init> : ()V
    //   262: aload_2
    //   263: ldc 'Encountered sandbox exception while initialising: '
    //   265: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   268: pop
    //   269: aload_2
    //   270: aload_3
    //   271: invokevirtual getMessage : ()Ljava/lang/String;
    //   274: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   277: pop
    //   278: aload_1
    //   279: ldc 'PairingFix'
    //   281: aload_2
    //   282: invokevirtual toString : ()Ljava/lang/String;
    //   285: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   288: goto -> 330
    //   291: astore_1
    //   292: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   295: astore_2
    //   296: new java/lang/StringBuilder
    //   299: astore_3
    //   300: aload_3
    //   301: invokespecial <init> : ()V
    //   304: aload_3
    //   305: ldc 'Unable to find field while initialising: '
    //   307: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   310: pop
    //   311: aload_3
    //   312: aload_1
    //   313: invokevirtual getMessage : ()Ljava/lang/String;
    //   316: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   319: pop
    //   320: aload_2
    //   321: ldc 'PairingFix'
    //   323: aload_3
    //   324: invokevirtual toString : ()Ljava/lang/String;
    //   327: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   330: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   333: ldc 'PairingFix'
    //   335: ldc 'Failed to initialise, bypassAuthenticationRetry will quietly fail'
    //   337: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   340: iconst_1
    //   341: putstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.initComplete : Z
    //   344: iconst_1
    //   345: putstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.initFailed : Z
    //   348: aload_0
    //   349: monitorexit
    //   350: iconst_1
    //   351: iconst_1
    //   352: ixor
    //   353: ireturn
    //   354: getstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.initFailed : Z
    //   357: istore #4
    //   359: aload_0
    //   360: monitorexit
    //   361: iload #4
    //   363: iconst_1
    //   364: ixor
    //   365: ireturn
    //   366: astore_1
    //   367: aload_0
    //   368: monitorexit
    //   369: aload_1
    //   370: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	366	finally
    //   17	60	366	finally
    //   66	76	366	finally
    //   76	97	112	java/lang/NoSuchFieldException
    //   76	97	108	java/lang/SecurityException
    //   76	97	104	java/lang/NullPointerException
    //   76	97	100	java/lang/RuntimeException
    //   76	97	366	finally
    //   113	144	291	java/lang/NoSuchFieldException
    //   113	144	108	java/lang/SecurityException
    //   113	144	104	java/lang/NullPointerException
    //   113	144	100	java/lang/RuntimeException
    //   113	144	366	finally
    //   144	162	291	java/lang/NoSuchFieldException
    //   144	162	108	java/lang/SecurityException
    //   144	162	104	java/lang/NullPointerException
    //   144	162	100	java/lang/RuntimeException
    //   144	162	366	finally
    //   168	206	366	finally
    //   209	247	366	finally
    //   250	288	366	finally
    //   292	330	366	finally
    //   330	348	366	finally
    //   354	359	366	finally
  }
  
  public final void bypassAuthenticationRetry(BluetoothGatt paramBluetoothGatt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ldc 'gatt'
    //   5: invokestatic checkParameterIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
    //   8: aload_0
    //   9: invokespecial tryInit : ()Z
    //   12: istore_2
    //   13: iload_2
    //   14: ifne -> 20
    //   17: aload_0
    //   18: monitorexit
    //   19: return
    //   20: getstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.mAuthRetryStateField : Ljava/lang/reflect/Field;
    //   23: ifnull -> 108
    //   26: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   29: ldc 'PairingFix'
    //   31: ldc 'Attempting to bypass mAuthRetryState bonding conditional'
    //   33: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   36: getstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.mAuthRetryStateField : Ljava/lang/reflect/Field;
    //   39: astore_3
    //   40: aload_3
    //   41: ifnonnull -> 47
    //   44: invokestatic throwNpe : ()V
    //   47: aload_3
    //   48: invokevirtual isAccessible : ()Z
    //   51: istore_2
    //   52: iload_2
    //   53: ifne -> 72
    //   56: getstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.mAuthRetryStateField : Ljava/lang/reflect/Field;
    //   59: astore_3
    //   60: aload_3
    //   61: ifnonnull -> 67
    //   64: invokestatic throwNpe : ()V
    //   67: aload_3
    //   68: iconst_1
    //   69: invokevirtual setAccessible : (Z)V
    //   72: getstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.mAuthRetryStateField : Ljava/lang/reflect/Field;
    //   75: astore_3
    //   76: aload_3
    //   77: ifnonnull -> 83
    //   80: invokestatic throwNpe : ()V
    //   83: aload_3
    //   84: aload_1
    //   85: iconst_2
    //   86: invokevirtual setInt : (Ljava/lang/Object;I)V
    //   89: getstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.mAuthRetryStateField : Ljava/lang/reflect/Field;
    //   92: astore_1
    //   93: aload_1
    //   94: ifnonnull -> 100
    //   97: invokestatic throwNpe : ()V
    //   100: aload_1
    //   101: iload_2
    //   102: invokevirtual setAccessible : (Z)V
    //   105: goto -> 372
    //   108: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   111: ldc 'PairingFix'
    //   113: ldc 'Attempting to bypass mAuthRetry bonding conditional'
    //   115: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   118: getstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.mAuthRetryField : Ljava/lang/reflect/Field;
    //   121: astore_3
    //   122: aload_3
    //   123: ifnonnull -> 129
    //   126: invokestatic throwNpe : ()V
    //   129: aload_3
    //   130: invokevirtual isAccessible : ()Z
    //   133: istore_2
    //   134: iload_2
    //   135: ifne -> 154
    //   138: getstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.mAuthRetryField : Ljava/lang/reflect/Field;
    //   141: astore_3
    //   142: aload_3
    //   143: ifnonnull -> 149
    //   146: invokestatic throwNpe : ()V
    //   149: aload_3
    //   150: iconst_1
    //   151: invokevirtual setAccessible : (Z)V
    //   154: getstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.mAuthRetryField : Ljava/lang/reflect/Field;
    //   157: astore_3
    //   158: aload_3
    //   159: ifnonnull -> 165
    //   162: invokestatic throwNpe : ()V
    //   165: aload_3
    //   166: aload_1
    //   167: iconst_1
    //   168: invokevirtual setBoolean : (Ljava/lang/Object;Z)V
    //   171: getstatic ca/albertahealthservices/contacttracing/streetpass/PairingFix.mAuthRetryField : Ljava/lang/reflect/Field;
    //   174: astore_1
    //   175: aload_1
    //   176: ifnonnull -> 182
    //   179: invokestatic throwNpe : ()V
    //   182: aload_1
    //   183: iload_2
    //   184: invokevirtual setAccessible : (Z)V
    //   187: goto -> 372
    //   190: astore_1
    //   191: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   194: astore_3
    //   195: new java/lang/StringBuilder
    //   198: astore #4
    //   200: aload #4
    //   202: invokespecial <init> : ()V
    //   205: aload #4
    //   207: ldc 'Encountered reflection in bypassAuthenticationRetry: '
    //   209: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   212: pop
    //   213: aload #4
    //   215: aload_1
    //   216: invokevirtual getMessage : ()Ljava/lang/String;
    //   219: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   222: pop
    //   223: aload_3
    //   224: ldc 'PairingFix'
    //   226: aload #4
    //   228: invokevirtual toString : ()Ljava/lang/String;
    //   231: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   234: goto -> 372
    //   237: astore_1
    //   238: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   241: astore_3
    //   242: new java/lang/StringBuilder
    //   245: astore #4
    //   247: aload #4
    //   249: invokespecial <init> : ()V
    //   252: aload #4
    //   254: ldc 'Encountered NPE in bypassAuthenticationRetry: '
    //   256: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   259: pop
    //   260: aload #4
    //   262: aload_1
    //   263: invokevirtual getMessage : ()Ljava/lang/String;
    //   266: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   269: pop
    //   270: aload_3
    //   271: ldc 'PairingFix'
    //   273: aload #4
    //   275: invokevirtual toString : ()Ljava/lang/String;
    //   278: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   281: goto -> 372
    //   284: astore_3
    //   285: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   288: astore #4
    //   290: new java/lang/StringBuilder
    //   293: astore_1
    //   294: aload_1
    //   295: invokespecial <init> : ()V
    //   298: aload_1
    //   299: ldc 'Encountered argument exception in bypassAuthenticationRetry: '
    //   301: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   304: pop
    //   305: aload_1
    //   306: aload_3
    //   307: invokevirtual getMessage : ()Ljava/lang/String;
    //   310: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   313: pop
    //   314: aload #4
    //   316: ldc 'PairingFix'
    //   318: aload_1
    //   319: invokevirtual toString : ()Ljava/lang/String;
    //   322: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   325: goto -> 372
    //   328: astore_3
    //   329: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   332: astore_1
    //   333: new java/lang/StringBuilder
    //   336: astore #4
    //   338: aload #4
    //   340: invokespecial <init> : ()V
    //   343: aload #4
    //   345: ldc 'Encountered sandbox exception in bypassAuthenticationRetry: '
    //   347: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   350: pop
    //   351: aload #4
    //   353: aload_3
    //   354: invokevirtual getMessage : ()Ljava/lang/String;
    //   357: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   360: pop
    //   361: aload_1
    //   362: ldc 'PairingFix'
    //   364: aload #4
    //   366: invokevirtual toString : ()Ljava/lang/String;
    //   369: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   372: aload_0
    //   373: monitorexit
    //   374: return
    //   375: astore_1
    //   376: aload_0
    //   377: monitorexit
    //   378: aload_1
    //   379: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	375	finally
    //   20	40	328	java/lang/SecurityException
    //   20	40	284	java/lang/IllegalArgumentException
    //   20	40	237	java/lang/NullPointerException
    //   20	40	190	java/lang/ExceptionInInitializerError
    //   20	40	375	finally
    //   44	47	328	java/lang/SecurityException
    //   44	47	284	java/lang/IllegalArgumentException
    //   44	47	237	java/lang/NullPointerException
    //   44	47	190	java/lang/ExceptionInInitializerError
    //   44	47	375	finally
    //   47	52	328	java/lang/SecurityException
    //   47	52	284	java/lang/IllegalArgumentException
    //   47	52	237	java/lang/NullPointerException
    //   47	52	190	java/lang/ExceptionInInitializerError
    //   47	52	375	finally
    //   56	60	328	java/lang/SecurityException
    //   56	60	284	java/lang/IllegalArgumentException
    //   56	60	237	java/lang/NullPointerException
    //   56	60	190	java/lang/ExceptionInInitializerError
    //   56	60	375	finally
    //   64	67	328	java/lang/SecurityException
    //   64	67	284	java/lang/IllegalArgumentException
    //   64	67	237	java/lang/NullPointerException
    //   64	67	190	java/lang/ExceptionInInitializerError
    //   64	67	375	finally
    //   67	72	328	java/lang/SecurityException
    //   67	72	284	java/lang/IllegalArgumentException
    //   67	72	237	java/lang/NullPointerException
    //   67	72	190	java/lang/ExceptionInInitializerError
    //   67	72	375	finally
    //   72	76	328	java/lang/SecurityException
    //   72	76	284	java/lang/IllegalArgumentException
    //   72	76	237	java/lang/NullPointerException
    //   72	76	190	java/lang/ExceptionInInitializerError
    //   72	76	375	finally
    //   80	83	328	java/lang/SecurityException
    //   80	83	284	java/lang/IllegalArgumentException
    //   80	83	237	java/lang/NullPointerException
    //   80	83	190	java/lang/ExceptionInInitializerError
    //   80	83	375	finally
    //   83	93	328	java/lang/SecurityException
    //   83	93	284	java/lang/IllegalArgumentException
    //   83	93	237	java/lang/NullPointerException
    //   83	93	190	java/lang/ExceptionInInitializerError
    //   83	93	375	finally
    //   97	100	328	java/lang/SecurityException
    //   97	100	284	java/lang/IllegalArgumentException
    //   97	100	237	java/lang/NullPointerException
    //   97	100	190	java/lang/ExceptionInInitializerError
    //   97	100	375	finally
    //   100	105	328	java/lang/SecurityException
    //   100	105	284	java/lang/IllegalArgumentException
    //   100	105	237	java/lang/NullPointerException
    //   100	105	190	java/lang/ExceptionInInitializerError
    //   100	105	375	finally
    //   108	122	328	java/lang/SecurityException
    //   108	122	284	java/lang/IllegalArgumentException
    //   108	122	237	java/lang/NullPointerException
    //   108	122	190	java/lang/ExceptionInInitializerError
    //   108	122	375	finally
    //   126	129	328	java/lang/SecurityException
    //   126	129	284	java/lang/IllegalArgumentException
    //   126	129	237	java/lang/NullPointerException
    //   126	129	190	java/lang/ExceptionInInitializerError
    //   126	129	375	finally
    //   129	134	328	java/lang/SecurityException
    //   129	134	284	java/lang/IllegalArgumentException
    //   129	134	237	java/lang/NullPointerException
    //   129	134	190	java/lang/ExceptionInInitializerError
    //   129	134	375	finally
    //   138	142	328	java/lang/SecurityException
    //   138	142	284	java/lang/IllegalArgumentException
    //   138	142	237	java/lang/NullPointerException
    //   138	142	190	java/lang/ExceptionInInitializerError
    //   138	142	375	finally
    //   146	149	328	java/lang/SecurityException
    //   146	149	284	java/lang/IllegalArgumentException
    //   146	149	237	java/lang/NullPointerException
    //   146	149	190	java/lang/ExceptionInInitializerError
    //   146	149	375	finally
    //   149	154	328	java/lang/SecurityException
    //   149	154	284	java/lang/IllegalArgumentException
    //   149	154	237	java/lang/NullPointerException
    //   149	154	190	java/lang/ExceptionInInitializerError
    //   149	154	375	finally
    //   154	158	328	java/lang/SecurityException
    //   154	158	284	java/lang/IllegalArgumentException
    //   154	158	237	java/lang/NullPointerException
    //   154	158	190	java/lang/ExceptionInInitializerError
    //   154	158	375	finally
    //   162	165	328	java/lang/SecurityException
    //   162	165	284	java/lang/IllegalArgumentException
    //   162	165	237	java/lang/NullPointerException
    //   162	165	190	java/lang/ExceptionInInitializerError
    //   162	165	375	finally
    //   165	175	328	java/lang/SecurityException
    //   165	175	284	java/lang/IllegalArgumentException
    //   165	175	237	java/lang/NullPointerException
    //   165	175	190	java/lang/ExceptionInInitializerError
    //   165	175	375	finally
    //   179	182	328	java/lang/SecurityException
    //   179	182	284	java/lang/IllegalArgumentException
    //   179	182	237	java/lang/NullPointerException
    //   179	182	190	java/lang/ExceptionInInitializerError
    //   179	182	375	finally
    //   182	187	328	java/lang/SecurityException
    //   182	187	284	java/lang/IllegalArgumentException
    //   182	187	237	java/lang/NullPointerException
    //   182	187	190	java/lang/ExceptionInInitializerError
    //   182	187	375	finally
    //   191	234	375	finally
    //   238	281	375	finally
    //   285	325	375	finally
    //   329	372	375	finally
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/PairingFix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */