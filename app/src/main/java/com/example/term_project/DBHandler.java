package com.example.term_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBHandler {    // Singleton Database
    private static DBHandler instance = null;
    public SQLiteDatabase database;

    String databaseName = "db";
    String tableName_Phone = "phone_table";
    String tableName_Schedule = "schedule_table";

    private DBHandler(Context context){
        database = context.openOrCreateDatabase(databaseName, context.MODE_PRIVATE,null) ; // Database 생성
        String sql_phone = "create table if not exists " + tableName_Phone +
                "(_id integer PRIMARY KEY autoincrement, name text, phone_number text, Email text)";
        database.execSQL(sql_phone);        // 연락처 테이블 생성
        String sql_schedule = "create table if not exists " + tableName_Schedule +
                "(_id integer PRIMARY KEY autoincrement, title text, date text, time text, place text, Email text)";    // date?
        database.execSQL(sql_schedule);     // 일정 테이블 생성
    }

    public static DBHandler getInstance(Context context) {
        if(instance == null){
            instance = new DBHandler(context);
        }
        return instance;    // 유일한 DBHandler instance 객체 생성
    }

    public int insertRecordParam(String table_name, String[] col) {

        switch (table_name){
            case "phone_table":     // 연락처 입력
                int phone_count = 1;
                ContentValues recordValues_phone = new ContentValues();

                recordValues_phone.put("name", col[0]);
                recordValues_phone.put("phone_number", col[1]);
                recordValues_phone.put("Email", col[2]);

                /*
                recordValues_phone.put("name", "Rice");
                recordValues_phone.put("phone_number", "010-0000-1111");
                recordValues_phone.put("Email", "AAA111@gmail.com");
                 */
                int rowPosition_phone = (int) database.insert(table_name, null, recordValues_phone);

                return phone_count;

            case "schedule_table":  // 일정 입력
                int schedule_count = 1;
                ContentValues recordValues_schedule = new ContentValues();

                recordValues_schedule.put("title", col[0]);
                recordValues_schedule.put("date", col[1]);
                recordValues_schedule.put("time", col[2]);
                recordValues_schedule.put("place", col[3]);
                recordValues_schedule.put("Email", col[4]);

                /*
                recordValues_schedule.put("title", "Rice");
                recordValues_schedule.put("date", "2022-11-28");
                recordValues_schedule.put("time", "PM 8:00");
                recordValues_schedule.put("place", "장소");
                recordValues_schedule.put("Email", "AAA111@gmail.com");
                */

                int rowPosition_schedule = (int) database.insert(table_name, null, recordValues_schedule);
                /*
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    ContentValues initialValues = new ContentValues();
                    initialValues.put("date_created", dateFormat.format(date));
                    long rowId = mDb.insert(DATABASE_TABLE, null, initialValues);
                */
                return schedule_count;
            default:
                return 0;
        }
    }

    public int updateRecordParam(String table_name, String[] col) {

        switch (table_name){
            case "phone_table":     // 연락처 입력
                int phone_count = 1;
                ContentValues recordValues_phone = new ContentValues();

                recordValues_phone.put("name", col[0]);
                String[] whereArgs_phone = {"Rice"};

                int rowAffected_phone = database.update(table_name,
                        recordValues_phone,
                        "name = ?",     // primary key로 검색
                        whereArgs_phone);

                return rowAffected_phone;

            case "schedule_table":  // 일정 입력
                int schedule_count = 1;
                ContentValues recordValues_schedule = new ContentValues();

                recordValues_schedule.put("title", col[0]);
                String[] whereArgs_schedule = {"Rice"};

                int rowAffected_schedule = database.update(table_name,
                        recordValues_schedule,
                        "name = ?",     // primary key로 검색
                        whereArgs_schedule);

                return rowAffected_schedule;
            default:
                return 0;
        }
    }

    public int deleteRecordParam(String table_name) {

        switch (table_name){
            case "phone_table":     // 연락처 입력
                String[] whereArgs_phone = {"Rice"};

                int rowAffected_phone = database.delete(table_name,
                        "name = ?",
                        whereArgs_phone);

                return rowAffected_phone;

            case "schedule_table":  // 일정 입력
                String[] whereArgs_schedule = {"Rice"};

                int rowAffected_schedule = database.delete(table_name,
                        "name = ?",
                        whereArgs_schedule);

                return rowAffected_schedule;
            default:
                return 0;
        }
    }

    public void selectData(String table_name, String sql){
        // sql = "select name, age, mobile from "+tableName;
        if(database != null){

            switch (table_name){
                case "phone_table":     // 연락처 입력
                    Cursor cursor_phone = database.rawQuery(sql, null);

                    for( int i = 0; i< cursor_phone.getCount(); i++){
                        cursor_phone.moveToNext();//다음 레코드로 넘어간다.
                        String name = cursor_phone.getString(0);
                        String phone_number = cursor_phone.getString(1);
                        String Email = cursor_phone.getString(2);
                    }
                    cursor_phone.close();

                case "schedule_table":  // 일정 입력
                    Cursor cursor_schedule = database.rawQuery(sql, null);

                    for( int i = 0; i< cursor_schedule.getCount(); i++){
                        cursor_schedule.moveToNext();//다음 레코드로 넘어간다.
                        String title = cursor_schedule.getString(0);
                        String date = cursor_schedule.getString(1);
                        String time = cursor_schedule.getString(2);
                        String place = cursor_schedule.getString(3);
                        String Email = cursor_schedule.getString(4);
                    }
                    cursor_schedule.close();

                default:
                    return ;
            }
        }
    }

}
