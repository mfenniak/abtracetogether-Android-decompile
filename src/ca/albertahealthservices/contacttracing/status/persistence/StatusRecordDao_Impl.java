package ca.albertahealthservices.contacttracing.status.persistence;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

public final class StatusRecordDao_Impl implements StatusRecordDao {
  private final RoomDatabase __db;
  
  private final EntityInsertionAdapter<StatusRecord> __insertionAdapterOfStatusRecord;
  
  private final SharedSQLiteStatement __preparedStmtOfNukeDb;
  
  private final SharedSQLiteStatement __preparedStmtOfPurgeOldRecords;
  
  public StatusRecordDao_Impl(RoomDatabase paramRoomDatabase) {
    this.__db = paramRoomDatabase;
    this.__insertionAdapterOfStatusRecord = new EntityInsertionAdapter<StatusRecord>(paramRoomDatabase) {
        public void bind(SupportSQLiteStatement param1SupportSQLiteStatement, StatusRecord param1StatusRecord) {
          param1SupportSQLiteStatement.bindLong(1, param1StatusRecord.getId());
          param1SupportSQLiteStatement.bindLong(2, param1StatusRecord.getTimestamp());
          if (param1StatusRecord.getMsg() == null) {
            param1SupportSQLiteStatement.bindNull(3);
          } else {
            param1SupportSQLiteStatement.bindString(3, param1StatusRecord.getMsg());
          } 
        }
        
        public String createQuery() {
          return "INSERT OR IGNORE INTO `status_table` (`id`,`timestamp`,`msg`) VALUES (nullif(?, 0),?,?)";
        }
      };
    this.__preparedStmtOfNukeDb = new SharedSQLiteStatement(paramRoomDatabase) {
        public String createQuery() {
          return "DELETE FROM status_table";
        }
      };
    this.__preparedStmtOfPurgeOldRecords = new SharedSQLiteStatement(paramRoomDatabase) {
        public String createQuery() {
          return "DELETE FROM status_table WHERE timestamp < ?";
        }
      };
  }
  
  private StatusRecord __entityCursorConverter_caAlbertahealthservicesContacttracingStatusPersistenceStatusRecord(Cursor paramCursor) {
    String str;
    int i = paramCursor.getColumnIndex("id");
    int j = paramCursor.getColumnIndex("timestamp");
    int k = paramCursor.getColumnIndex("msg");
    if (k == -1) {
      str = null;
    } else {
      str = paramCursor.getString(k);
    } 
    StatusRecord statusRecord = new StatusRecord(str);
    if (i != -1)
      statusRecord.setId(paramCursor.getInt(i)); 
    if (j != -1)
      statusRecord.setTimestamp(paramCursor.getLong(j)); 
    return statusRecord;
  }
  
  public List<StatusRecord> getCurrentRecords() {
    RoomSQLiteQuery roomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT * from status_table ORDER BY timestamp ASC", 0);
    this.__db.assertNotSuspendingTransaction();
    Cursor cursor = DBUtil.query(this.__db, (SupportSQLiteQuery)roomSQLiteQuery, false, null);
    try {
      int i = CursorUtil.getColumnIndexOrThrow(cursor, "id");
      int j = CursorUtil.getColumnIndexOrThrow(cursor, "timestamp");
      int k = CursorUtil.getColumnIndexOrThrow(cursor, "msg");
      ArrayList<StatusRecord> arrayList = new ArrayList();
      this(cursor.getCount());
      while (cursor.moveToNext()) {
        String str = cursor.getString(k);
        StatusRecord statusRecord = new StatusRecord();
        this(str);
        statusRecord.setId(cursor.getInt(i));
        statusRecord.setTimestamp(cursor.getLong(j));
        arrayList.add(statusRecord);
      } 
      return arrayList;
    } finally {
      cursor.close();
      roomSQLiteQuery.release();
    } 
  }
  
  public LiveData<StatusRecord> getMostRecentRecord(String paramString) {
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * from status_table where msg = ? ORDER BY timestamp DESC LIMIT 1", 1);
    if (paramString == null) {
      roomSQLiteQuery.bindNull(1);
    } else {
      roomSQLiteQuery.bindString(1, paramString);
    } 
    InvalidationTracker invalidationTracker = this.__db.getInvalidationTracker();
    Callable<StatusRecord> callable = new Callable<StatusRecord>() {
        public StatusRecord call() throws Exception {
          RoomDatabase roomDatabase = StatusRecordDao_Impl.this.__db;
          RoomSQLiteQuery roomSQLiteQuery = _statement;
          null = null;
          Cursor cursor = DBUtil.query(roomDatabase, (SupportSQLiteQuery)roomSQLiteQuery, false, null);
          try {
            int i = CursorUtil.getColumnIndexOrThrow(cursor, "id");
            int j = CursorUtil.getColumnIndexOrThrow(cursor, "timestamp");
            int k = CursorUtil.getColumnIndexOrThrow(cursor, "msg");
            if (cursor.moveToFirst()) {
              String str = cursor.getString(k);
              null = new StatusRecord();
              this(str);
              null.setId(cursor.getInt(i));
              null.setTimestamp(cursor.getLong(j));
            } 
            return null;
          } finally {
            cursor.close();
          } 
        }
        
        protected void finalize() {
          _statement.release();
        }
      };
    return invalidationTracker.createLiveData(new String[] { "status_table" }, false, callable);
  }
  
  public LiveData<List<StatusRecord>> getRecords() {
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * from status_table ORDER BY timestamp ASC", 0);
    InvalidationTracker invalidationTracker = this.__db.getInvalidationTracker();
    Callable<List<StatusRecord>> callable = new Callable<List<StatusRecord>>() {
        public List<StatusRecord> call() throws Exception {
          Cursor cursor = DBUtil.query(StatusRecordDao_Impl.this.__db, (SupportSQLiteQuery)_statement, false, null);
          try {
            int i = CursorUtil.getColumnIndexOrThrow(cursor, "id");
            int j = CursorUtil.getColumnIndexOrThrow(cursor, "timestamp");
            int k = CursorUtil.getColumnIndexOrThrow(cursor, "msg");
            ArrayList<StatusRecord> arrayList = new ArrayList();
            this(cursor.getCount());
            while (cursor.moveToNext()) {
              String str = cursor.getString(k);
              StatusRecord statusRecord = new StatusRecord();
              this(str);
              statusRecord.setId(cursor.getInt(i));
              statusRecord.setTimestamp(cursor.getLong(j));
              arrayList.add(statusRecord);
            } 
            return arrayList;
          } finally {
            cursor.close();
          } 
        }
        
        protected void finalize() {
          _statement.release();
        }
      };
    return invalidationTracker.createLiveData(new String[] { "status_table" }, false, callable);
  }
  
  public List<StatusRecord> getRecordsViaQuery(SupportSQLiteQuery paramSupportSQLiteQuery) {
    this.__db.assertNotSuspendingTransaction();
    Cursor cursor = DBUtil.query(this.__db, paramSupportSQLiteQuery, false, null);
    try {
      ArrayList<StatusRecord> arrayList = new ArrayList();
      this(cursor.getCount());
      while (cursor.moveToNext())
        arrayList.add(__entityCursorConverter_caAlbertahealthservicesContacttracingStatusPersistenceStatusRecord(cursor)); 
      return arrayList;
    } finally {
      cursor.close();
    } 
  }
  
  public Object insert(final StatusRecord record, Continuation<? super Unit> paramContinuation) {
    return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() {
          public Unit call() throws Exception {
            StatusRecordDao_Impl.this.__db.beginTransaction();
            try {
              StatusRecordDao_Impl.this.__insertionAdapterOfStatusRecord.insert(record);
              StatusRecordDao_Impl.this.__db.setTransactionSuccessful();
              return Unit.INSTANCE;
            } finally {
              StatusRecordDao_Impl.this.__db.endTransaction();
            } 
          }
        },  paramContinuation);
  }
  
  public void nukeDb() {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement supportSQLiteStatement = this.__preparedStmtOfNukeDb.acquire();
    this.__db.beginTransaction();
    try {
      supportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return;
    } finally {
      this.__db.endTransaction();
      this.__preparedStmtOfNukeDb.release(supportSQLiteStatement);
    } 
  }
  
  public Object purgeOldRecords(final long before, Continuation<? super Unit> paramContinuation) {
    return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() {
          public Unit call() throws Exception {
            SupportSQLiteStatement supportSQLiteStatement = StatusRecordDao_Impl.this.__preparedStmtOfPurgeOldRecords.acquire();
            supportSQLiteStatement.bindLong(1, before);
            StatusRecordDao_Impl.this.__db.beginTransaction();
            try {
              supportSQLiteStatement.executeUpdateDelete();
              StatusRecordDao_Impl.this.__db.setTransactionSuccessful();
              return Unit.INSTANCE;
            } finally {
              StatusRecordDao_Impl.this.__db.endTransaction();
              StatusRecordDao_Impl.this.__preparedStmtOfPurgeOldRecords.release(supportSQLiteStatement);
            } 
          }
        }paramContinuation);
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/status/persistence/StatusRecordDao_Impl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */