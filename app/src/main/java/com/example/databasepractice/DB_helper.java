package com.example.databasepractice;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB_helper {

    //변수 선언
    private static final String DATABASE_NAME = "PersonInfo.db";    //생성될 데베파일 이름
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context context;

    //데이터베이스헬퍼 정의 (SQLiteOpenHelper 를 상속 받음)
    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //테이블을 만들어라
            db.execSQL(DB_table.CreateDB._CREATE0);
        }

        //만약에 기존 테이블에서 컬럼이 추가 or 삭제 된다면 버전을 올려서 관리한다.
        //참고할만한 블로그 : https://blog.hansoolabs.com/603
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_table.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    //생성자
    public DB_helper(Context context) {
        this.context = context;
    }

    //데베를 사용하기 위한 준비
    //이 메소드를 사용하는 곳으로 예외 책임을 전가(예외 던지기 - throws)
    public DB_helper open() throws SQLException {
        mDBHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create() {
        mDBHelper.onCreate(mDB);
    }

    public void cloase() {
        mDB.close();
    }

    //데이터 삽입
    public long insertColum(String name, Integer age) {
        ContentValues values = new ContentValues();
        values.put(DB_table.CreateDB.NAME, name);
        values.put(DB_table.CreateDB.AGE, age);
        return mDB.insert(DB_table.CreateDB._TABLENAME0, null, values);
    }

    //데이터 갱신
    public boolean updateColumn(String name, Integer age) {
        ContentValues values = new ContentValues();
        values.put(DB_table.CreateDB.NAME, name);
        values.put(DB_table.CreateDB.AGE, age);
        return mDB.update(DB_table.CreateDB._TABLENAME0,  values, null, null) > 0;
    }

    //데이터 전체 삭제
    public void deleteAllColumns() {
        mDB.delete(DB_table.CreateDB._TABLENAME0, null, null);
    }

    //데이터 삭제
    public boolean deleteColumn(String name){
        return mDB.delete(DB_table.CreateDB._TABLENAME0, "name="+"'"+name+"'", null)>0;
    }

    //데이터 선택
    public Cursor selectColumns(){
        return mDB.query(DB_table.CreateDB._TABLENAME0,  null, null, null, null, null, null);
    }

    //데이터 이름으로 찾기
    public Cursor selectNameColumns(String name){
        Cursor c = mDB.rawQuery("SELECT * FROM usertable WHERE name = '" + name + "'", null);
        return c;
    }
}
