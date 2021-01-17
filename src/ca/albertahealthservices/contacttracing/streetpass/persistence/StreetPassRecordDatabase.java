package ca.albertahealthservices.contacttracing.streetpass.persistence;

import android.content.Context;
import androidx.room.RoomDatabase;
import ca.albertahealthservices.contacttracing.status.persistence.StatusRecordDao;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\032\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b'\030\000 \0072\0020\001:\001\007B\005¢\006\002\020\002J\b\020\003\032\0020\004H&J\b\020\005\032\0020\006H&¨\006\b"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDatabase;", "Landroidx/room/RoomDatabase;", "()V", "recordDao", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDao;", "statusDao", "Lca/albertahealthservices/contacttracing/status/persistence/StatusRecordDao;", "Companion", "app_release"}, k = 1, mv = {1, 1, 16})
public abstract class StreetPassRecordDatabase extends RoomDatabase {
  public static final Companion Companion = new Companion(null);
  
  private static volatile StreetPassRecordDatabase INSTANCE;
  
  public abstract StreetPassRecordDao recordDao();
  
  public abstract StatusRecordDao statusDao();
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\016\020\005\032\0020\0042\006\020\006\032\0020\007R\020\020\003\032\004\030\0010\004X\016¢\006\002\n\000¨\006\b"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDatabase$Companion;", "", "()V", "INSTANCE", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDatabase;", "getDatabase", "context", "Landroid/content/Context;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class Companion {
    private Companion() {}
    
    public final StreetPassRecordDatabase getDatabase(Context param1Context) {
      // Byte code:
      //   0: aload_1
      //   1: ldc 'context'
      //   3: invokestatic checkParameterIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
      //   6: invokestatic access$getINSTANCE$cp : ()Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDatabase;
      //   9: astore_2
      //   10: aload_2
      //   11: ifnull -> 16
      //   14: aload_2
      //   15: areturn
      //   16: aload_0
      //   17: monitorenter
      //   18: aload_1
      //   19: ldc ca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDatabase
      //   21: ldc 'record_database'
      //   23: invokestatic databaseBuilder : (Landroid/content/Context;Ljava/lang/Class;Ljava/lang/String;)Landroidx/room/RoomDatabase$Builder;
      //   26: invokevirtual build : ()Landroidx/room/RoomDatabase;
      //   29: astore_1
      //   30: aload_1
      //   31: ldc 'Room.databaseBuilder(\\n  …                 .build()'
      //   33: invokestatic checkExpressionValueIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
      //   36: aload_1
      //   37: checkcast ca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDatabase
      //   40: astore_1
      //   41: aload_1
      //   42: invokestatic access$setINSTANCE$cp : (Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDatabase;)V
      //   45: aload_0
      //   46: monitorexit
      //   47: aload_1
      //   48: areturn
      //   49: astore_1
      //   50: aload_0
      //   51: monitorexit
      //   52: aload_1
      //   53: athrow
      // Exception table:
      //   from	to	target	type
      //   18	45	49	finally
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDatabase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */