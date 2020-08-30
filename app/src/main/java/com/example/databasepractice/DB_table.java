package com.example.databasepractice;

import android.provider.BaseColumns;

public class DB_table {
    //데이터베이스 테이블 구성
    public static final class CreateDB implements BaseColumns {
        public static final String _TABLENAME0 = "usertable";     //테이블 이름
        public static final String NAME = "name";
        public static final String AGE = "age";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement, "
                +NAME+" text not null , "
                +AGE+"  not null ); ";
    }
}
