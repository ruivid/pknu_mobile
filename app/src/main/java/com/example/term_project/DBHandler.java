package com.example.term_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBHandler {    // Singleton Database
    private static DBHandler instance = null;
    public SQLiteDatabase database;

    String databaseName = "db";
    String tableName_Phone = "phone_table";
    String tableName_Schedule = "schedule_table";

    private DBHandler(Context context){
        database = context.openOrCreateDatabase(databaseName, context.MODE_PRIVATE,null) ; // Database 생성
        String sql_phone = "create table if not exists " + tableName_Phone +
                "(_id integer PRIMARY KEY autoincrement, name text, phone_number text, email text)";
        database.execSQL(sql_phone);        // 연락처 테이블 생성
        String sql_schedule = "create table if not exists " + tableName_Schedule +
                "(_id integer PRIMARY KEY autoincrement, title text, date text, time text, place text, email text)";    // date?
        database.execSQL(sql_schedule);     // 일정 테이블 생성
    }

    /**
     * 싱글 톤 DB 생성
     * @param context 어플 컨텍스트
     * @return DB 인스턴스
     */
    public static DBHandler getInstance(Context context) {
        if(instance == null){
            instance = new DBHandler(context.getApplicationContext());
        }
        return instance;    // 유일한 DBHandler instance 객체 생성
    }

    /**
     * 테이블에 insert
     * @param table_name 테이블 이름
     * @param col 데이터
     * @return 새롭게 생성된 행 숫자 반환. 실패 시 -1.
     */
    public int insertRecordParam(String table_name, String[] col) {

        switch (table_name){
            case "phone_table":     // 연락처 입력
                ContentValues recordValues_phone = new ContentValues();

                recordValues_phone.put("name", col[0]);
                recordValues_phone.put("phone_number", col[1]);
                recordValues_phone.put("email", col[2]);

                return (int) database.insert(table_name, null, recordValues_phone);

            case "schedule_table":  // 일정 입력
                ContentValues recordValues_schedule = new ContentValues();

                recordValues_schedule.put("title", col[0]);
                recordValues_schedule.put("date", col[1]);
                recordValues_schedule.put("time", col[2]);
                recordValues_schedule.put("place", col[3]);
                recordValues_schedule.put("email", col[4]);

                return (int) database.insert(table_name, null, recordValues_schedule);
            default:
                return -1;
        }
    }

    /**
     * 테이블에 update
     * @param table_name 테이블 이름
     * @param col 데이터
     * @param id 행
     * @return 업데이트된 행 수.
     */
    public int updateRecordParam(String table_name, String[] col, String[] id) {

        switch (table_name){
            case "phone_table":     // 연락처 입력
                int phone_count = 1;
                ContentValues recordValues_phone = new ContentValues();

                recordValues_phone.put("name", col[0]);

                return database.update(table_name,
                        recordValues_phone,
                        "name = ?",     // primary key로 검색
                        id);

            case "schedule_table":  // 일정 입력
                int schedule_count = 1;
                ContentValues recordValues_schedule = new ContentValues();

                recordValues_schedule.put("title", col[0]);

                return database.update(table_name,
                        recordValues_schedule,
                        "name = ?",     // primary key로 검색
                        id);
            default:
                return 0;
        }
    }

    public int deleteRecordParam(String table_name, String[] id) {

        switch (table_name){
            case "phone_table":     // 연락처 입력

                return database.delete(table_name,
                        "_id = ?",
                        id);

            case "schedule_table":  // 일정 입력
                String[] whereArgs_schedule = {"Rice"};

                return database.delete(table_name,
                        "_id = ?",
                        whereArgs_schedule);
            default:
                return 0;
        }
    }

    public List<String[]> selectData(String table_name, String sql) {
        // sql = "select name, age, mobile from "+tableName;
        List<String[]> items = new ArrayList<>();
        if(database != null){

            switch (table_name){
                case "phone_table":     // 연락처 입력
                    Cursor cursor_phone = database.rawQuery(sql, null);

                    while(cursor_phone.moveToNext()) {
                        String name = cursor_phone.getString(1);
                        String phone_number = cursor_phone.getString(2);
                        String email = cursor_phone.getString(3);
                        items.add(new String[]{name, phone_number, email});
                    }
                    cursor_phone.close();
                    return items;
                case "schedule_table":  // 일정 입력
                    Cursor cursor_schedule = database.rawQuery(sql, null);

                    while(cursor_schedule.moveToNext()){
                        String title = cursor_schedule.getString(1);
                        String date = cursor_schedule.getString(2);
                        String time = cursor_schedule.getString(3);
                        String place = cursor_schedule.getString(4);
                        String email = cursor_schedule.getString(5);
                        items.add(new String[]{title, date, time, place, email});
                    }
                    cursor_schedule.close();
                    return items;
                default:
            }
        }
        return items;
    }
}
