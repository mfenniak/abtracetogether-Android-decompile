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
    //   100: astore_1
    //   101: goto -> 168
    //   104: astore_1
    //   105: goto -> 209
    //   108: astore_2
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
    //   175: astore_2
    //   176: aload_2
    //   177: invokespecial <init> : ()V
    //   180: aload_2
    //   181: ldc 'Encountered Exception while initialising: '
    //   183: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   186: pop
    //   187: aload_2
    //   188: aload_1
    //   189: invokevirtual getMessage : ()Ljava/lang/String;
    //   192: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   195: pop
    //   196: aload_3
    //   197: ldc 'PairingFix'
    //   199: aload_2
    //   200: invokevirtual toString : ()Ljava/lang/String;
    //   203: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   206: goto -> 330
    //   209: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   212: astore_2
    //   213: new java/lang/StringBuilder
    //   216: astore_3
    //   217: aload_3
    //   218: invokespecial <init> : ()V
    //   221: aload_3
    //   222: ldc 'Encountered NPE while initialising: '
    //   224: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   227: pop
    //   228: aload_3
    //   229: aload_1
    //   230: invokevirtual getMessage : ()Ljava/lang/String;
    //   233: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   236: pop
    //   237: aload_2
    //   238: ldc 'PairingFix'
    //   240: aload_3
    //   241: invokevirtual toString : ()Ljava/lang/String;
    //   244: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   247: goto -> 330
    //   250: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   253: astore_3
    //   254: new java/lang/StringBuilder
    //   257: astore_1
    //   258: aload_1
    //   259: invokespecial <init> : ()V
    //   262: aload_1
    //   263: ldc 'Encountered sandbox exception while initialising: '
    //   265: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   268: pop
    //   269: aload_1
    //   270: aload_2
    //   271: invokevirtual getMessage : ()Ljava/lang/String;
    //   274: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   277: pop
    //   278: aload_3
    //   279: ldc 'PairingFix'
    //   281: aload_1
    //   282: invokevirtual toString : ()Ljava/lang/String;
    //   285: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   288: goto -> 330
    //   291: astore_3
    //   292: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   295: astore_2
    //   296: new java/lang/StringBuilder
    //   299: astore_1
    //   300: aload_1
    //   301: invokespecial <init> : ()V
    //   304: aload_1
    //   305: ldc 'Unable to find field while initialising: '
    //   307: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   310: pop
    //   311: aload_1
    //   312: aload_3
    //   313: invokevirtual getMessage : ()Ljava/lang/String;
    //   316: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   319: pop
    //   320: aload_2
    //   321: ldc 'PairingFix'
    //   323: aload_1
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
    //   105: goto -> 363
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
    //   187: goto -> 363
    //   190: astore #4
    //   192: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   195: astore_3
    //   196: new java/lang/StringBuilder
    //   199: astore_1
    //   200: aload_1
    //   201: invokespecial <init> : ()V
    //   204: aload_1
    //   205: ldc 'Encountered reflection in bypassAuthenticationRetry: '
    //   207: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   210: pop
    //   211: aload_1
    //   212: aload #4
    //   214: invokevirtual getMessage : ()Ljava/lang/String;
    //   217: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   220: pop
    //   221: aload_3
    //   222: ldc 'PairingFix'
    //   224: aload_1
    //   225: invokevirtual toString : ()Ljava/lang/String;
    //   228: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   231: goto -> 363
    //   234: astore_3
    //   235: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   238: astore #4
    //   240: new java/lang/StringBuilder
    //   243: astore_1
    //   244: aload_1
    //   245: invokespecial <init> : ()V
    //   248: aload_1
    //   249: ldc 'Encountered NPE in bypassAuthenticationRetry: '
    //   251: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   254: pop
    //   255: aload_1
    //   256: aload_3
    //   257: invokevirtual getMessage : ()Ljava/lang/String;
    //   260: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   263: pop
    //   264: aload #4
    //   266: ldc 'PairingFix'
    //   268: aload_1
    //   269: invokevirtual toString : ()Ljava/lang/String;
    //   272: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   275: goto -> 363
    //   278: astore_3
    //   279: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   282: astore #4
    //   284: new java/lang/StringBuilder
    //   287: astore_1
    //   288: aload_1
    //   289: invokespecial <init> : ()V
    //   292: aload_1
    //   293: ldc 'Encountered argument exception in bypassAuthenticationRetry: '
    //   295: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   298: pop
    //   299: aload_1
    //   300: aload_3
    //   301: invokevirtual getMessage : ()Ljava/lang/String;
    //   304: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   307: pop
    //   308: aload #4
    //   310: ldc 'PairingFix'
    //   312: aload_1
    //   313: invokevirtual toString : ()Ljava/lang/String;
    //   316: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   319: goto -> 363
    //   322: astore #4
    //   324: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
    //   327: astore_1
    //   328: new java/lang/StringBuilder
    //   331: astore_3
    //   332: aload_3
    //   333: invokespecial <init> : ()V
    //   336: aload_3
    //   337: ldc 'Encountered sandbox exception in bypassAuthenticationRetry: '
    //   339: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   342: pop
    //   343: aload_3
    //   344: aload #4
    //   346: invokevirtual getMessage : ()Ljava/lang/String;
    //   349: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   352: pop
    //   353: aload_1
    //   354: ldc 'PairingFix'
    //   356: aload_3
    //   357: invokevirtual toString : ()Ljava/lang/String;
    //   360: invokevirtual i : (Ljava/lang/String;Ljava/lang/String;)V
    //   363: aload_0
    //   364: monitorexit
    //   365: return
    //   366: astore_1
    //   367: aload_0
    //   368: monitorexit
    //   369: aload_1
    //   370: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	366	finally
    //   20	40	322	java/lang/SecurityException
    //   20	40	278	java/lang/IllegalArgumentException
    //   20	40	234	java/lang/NullPointerException
    //   20	40	190	java/lang/ExceptionInInitializerError
    //   20	40	366	finally
    //   44	47	322	java/lang/SecurityException
    //   44	47	278	java/lang/IllegalArgumentException
    //   44	47	234	java/lang/NullPointerException
    //   44	47	190	java/lang/ExceptionInInitializerError
    //   44	47	366	finally
    //   47	52	322	java/lang/SecurityException
    //   47	52	278	java/lang/IllegalArgumentException
    //   47	52	234	java/lang/NullPointerException
    //   47	52	190	java/lang/ExceptionInInitializerError
    //   47	52	366	finally
    //   56	60	322	java/lang/SecurityException
    //   56	60	278	java/lang/IllegalArgumentException
    //   56	60	234	java/lang/NullPointerException
    //   56	60	190	java/lang/ExceptionInInitializerError
    //   56	60	366	finally
    //   64	67	322	java/lang/SecurityException
    //   64	67	278	java/lang/IllegalArgumentException
    //   64	67	234	java/lang/NullPointerException
    //   64	67	190	java/lang/ExceptionInInitializerError
    //   64	67	366	finally
    //   67	72	322	java/lang/SecurityException
    //   67	72	278	java/lang/IllegalArgumentException
    //   67	72	234	java/lang/NullPointerException
    //   67	72	190	java/lang/ExceptionInInitializerError
    //   67	72	366	finally
    //   72	76	322	java/lang/SecurityException
    //   72	76	278	java/lang/IllegalArgumentException
    //   72	76	234	java/lang/NullPointerException
    //   72	76	190	java/lang/ExceptionInInitializerError
    //   72	76	366	finally
    //   80	83	322	java/lang/SecurityException
    //   80	83	278	java/lang/IllegalArgumentException
    //   80	83	234	java/lang/NullPointerException
    //   80	83	190	java/lang/ExceptionInInitializerError
    //   80	83	366	finally
    //   83	93	322	java/lang/SecurityException
    //   83	93	278	java/lang/IllegalArgumentException
    //   83	93	234	java/lang/NullPointerException
    //   83	93	190	java/lang/ExceptionInInitializerError
    //   83	93	366	finally
    //   97	100	322	java/lang/SecurityException
    //   97	100	278	java/lang/IllegalArgumentException
    //   97	100	234	java/lang/NullPointerException
    //   97	100	190	java/lang/ExceptionInInitializerError
    //   97	100	366	finally
    //   100	105	322	java/lang/SecurityException
    //   100	105	278	java/lang/IllegalArgumentException
    //   100	105	234	java/lang/NullPointerException
    //   100	105	190	java/lang/ExceptionInInitializerError
    //   100	105	366	finally
    //   108	122	322	java/lang/SecurityException
    //   108	122	278	java/lang/IllegalArgumentException
    //   108	122	234	java/lang/NullPointerException
    //   108	122	190	java/lang/ExceptionInInitializerError
    //   108	122	366	finally
    //   126	129	322	java/lang/SecurityException
    //   126	129	278	java/lang/IllegalArgumentException
    //   126	129	234	java/lang/NullPointerException
    //   126	129	190	java/lang/ExceptionInInitializerError
    //   126	129	366	finally
    //   129	134	322	java/lang/SecurityException
    //   129	134	278	java/lang/IllegalArgumentException
    //   129	134	234	java/lang/NullPointerException
    //   129	134	190	java/lang/ExceptionInInitializerError
    //   129	134	366	finally
    //   138	142	322	java/lang/SecurityException
    //   138	142	278	java/lang/IllegalArgumentException
    //   138	142	234	java/lang/NullPointerException
    //   138	142	190	java/lang/ExceptionInInitializerError
    //   138	142	366	finally
    //   146	149	322	java/lang/SecurityException
    //   146	149	278	java/lang/IllegalArgumentException
    //   146	149	234	java/lang/NullPointerException
    //   146	149	190	java/lang/ExceptionInInitializerError
    //   146	149	366	finally
    //   149	154	322	java/lang/SecurityException
    //   149	154	278	java/lang/IllegalArgumentException
    //   149	154	234	java/lang/NullPointerException
    //   149	154	190	java/lang/ExceptionInInitializerError
    //   149	154	366	finally
    //   154	158	322	java/lang/SecurityException
    //   154	158	278	java/lang/IllegalArgumentException
    //   154	158	234	java/lang/NullPointerException
    //   154	158	190	java/lang/ExceptionInInitializerError
    //   154	158	366	finally
    //   162	165	322	java/lang/SecurityException
    //   162	165	278	java/lang/IllegalArgumentException
    //   162	165	234	java/lang/NullPointerException
    //   162	165	190	java/lang/ExceptionInInitializerError
    //   162	165	366	finally
    //   165	175	322	java/lang/SecurityException
    //   165	175	278	java/lang/IllegalArgumentException
    //   165	175	234	java/lang/NullPointerException
    //   165	175	190	java/lang/ExceptionInInitializerError
    //   165	175	366	finally
    //   179	182	322	java/lang/SecurityException
    //   179	182	278	java/lang/IllegalArgumentException
    //   179	182	234	java/lang/NullPointerException
    //   179	182	190	java/lang/ExceptionInInitializerError
    //   179	182	366	finally
    //   182	187	322	java/lang/SecurityException
    //   182	187	278	java/lang/IllegalArgumentException
    //   182	187	234	java/lang/NullPointerException
    //   182	187	190	java/lang/ExceptionInInitializerError
    //   182	187	366	finally
    //   192	231	366	finally
    //   235	275	366	finally
    //   279	319	366	finally
    //   324	363	366	finally
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/PairingFix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */