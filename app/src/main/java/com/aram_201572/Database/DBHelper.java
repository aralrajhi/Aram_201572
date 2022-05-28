package com.aram_201572.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aram_201572.Models.Student;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "studentDB";
	public static final int DATABASE_VERSION = 1;

	public static final String TABLE_STUDENT= "student";

	public static final String STUDENT_ROW_ID = "_row_id";
	public static final String STUDENT_ID = "student_id";
	public static final String STUDENT_NAME = "student_name";
	public static final String STUDENT_FATHER_NAME = "father_name";
	public static final String STUDENT_GENDER = "gender";
	public static final String STUDENT_SURNAME = "surname";
	public static final String STUDENT_NATIONAL_ID = "national_id";
	public static final String STUDENT_DATE_OF_BIRTH = "dob";

	private String[] allColumns = {STUDENT_ROW_ID, STUDENT_ID,STUDENT_NATIONAL_ID, STUDENT_NAME, STUDENT_SURNAME, STUDENT_FATHER_NAME,STUDENT_DATE_OF_BIRTH,STUDENT_GENDER};

	private static final String DATABASE_CREATE = "create table "
			+ TABLE_STUDENT + "( " + STUDENT_ROW_ID
			+ " integer primary key autoincrement, "
			+ STUDENT_ID +" text not null,"
			+ STUDENT_NATIONAL_ID +" text not null,"
			+ STUDENT_NAME +" text not null,"
			+ STUDENT_SURNAME+" text not null,"
			+ STUDENT_FATHER_NAME +" text not null,"
			+ STUDENT_DATE_OF_BIRTH +" text not null,"
			+ STUDENT_GENDER +" text not null" +
			");";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
		onCreate(db);
	}


	public List<Student> getAllStudents()
	{
		List<Student> list = new ArrayList<Student>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_STUDENT,allColumns, null, null, null, null, null);

		if (cursor==null || cursor.getCount()==0)
		{
			cursor.close();
			db.close();
			return list;
		}

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {

			long row_id = cursor.getLong(0);
			String id = cursor.getString(1);
			String natId = cursor.getString(2);
			String name = cursor.getString(3);
			String surname = cursor.getString(4);
			String fname = cursor.getString(5);
			String dob = cursor.getString(6);
			String gender = cursor.getString(7);

			Student student = new Student(id,name,surname,fname,natId,dob,gender);
			list.add(student);
			cursor.moveToNext();
		}

		db.close();
		cursor.close();
		return list;
	}

	public Student getStudentById(String id)
	{
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_STUDENT,allColumns, STUDENT_ID + "=?",  new String[] { String.valueOf(id) }, null, null, null);
		Student student = null;
		if (cursor==null || cursor.getCount()==0)
		{
			cursor.close();
			db.close();
			return null;
		}

		cursor.moveToFirst();
		long row_id = cursor.getLong(0);
		String natId = cursor.getString(2);
		String name = cursor.getString(3);
		String surname = cursor.getString(4);
		String fname = cursor.getString(5);
		String dob = cursor.getString(6);
		String gender = cursor.getString(7);

		student = new Student(id,name,surname,fname,natId,dob,gender);


		db.close();
		cursor.close();
		return student;
	}

	public long insertStudent(Student student) {
		ContentValues values = new ContentValues();
		values.put(STUDENT_ID,student.getId());
		values.put(STUDENT_FATHER_NAME,student.getFatherName());
		values.put(STUDENT_GENDER,student.getGender());
		values.put(STUDENT_NAME,student.getName());
		values.put(STUDENT_DATE_OF_BIRTH,student.getDob());
		values.put(STUDENT_SURNAME,student.getSurname());
		values.put(STUDENT_NATIONAL_ID,student.getNationalId());
		SQLiteDatabase db = getWritableDatabase();
		long dataId = db.insert(TABLE_STUDENT, null,values);
		db.close();
		return dataId;
	}

	public long updateStudent(Student student) {
		ContentValues values = new ContentValues();
		values.put(STUDENT_ID,student.getId());
		values.put(STUDENT_FATHER_NAME,student.getFatherName());
		values.put(STUDENT_GENDER,student.getGender());
		values.put(STUDENT_NAME,student.getName());
		values.put(STUDENT_DATE_OF_BIRTH,student.getDob());
		values.put(STUDENT_SURNAME,student.getSurname());
		values.put(STUDENT_NATIONAL_ID,student.getNationalId());
		SQLiteDatabase db = getWritableDatabase();
		long dataId = db.update(TABLE_STUDENT,values ,STUDENT_ID + " = ?",new String[] { String.valueOf(student.getId()) });
		db.close();
		return dataId;
	}


	public void deleteStudent(String id) {
		try{
			SQLiteDatabase db = getWritableDatabase();
			String deleteQuery = "DELETE FROM "+TABLE_STUDENT+" WHERE "+STUDENT_ID+"= "+id+"";
			db.execSQL(deleteQuery);
			db.close();
		}
		catch (Exception e){
			e.getMessage();
		}
	}
}
