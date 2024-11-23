package com.example.tp7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nom de la base de données
    private static final String DATABASE_NAME = "MyDataBase";
    private static final int DATABASE_VERSION = 1;

    // Table Teacher
    private static final String TABLE_TEACHER = "Teacher";
    private static final String COLUMN_TEACHER_ID = "id";
    private static final String COLUMN_TEACHER_NAME = "name";
    private static final String COLUMN_TEACHER_EMAIL = "email";

    // Table Cours
    private static final String TABLE_COURSE = "Cours";
    private static final String COLUMN_COURSE_ID = "id";
    private static final String COLUMN_COURSE_NAME = "name";
    private static final String COLUMN_COURSE_HOURS = "hours";
    private static final String COLUMN_COURSE_TEACHER_ID = "teacher_id";  // Clé étrangère vers Teacher

    // Créer la table Teacher
    private static final String CREATE_TABLE_TEACHER = "CREATE TABLE " + TABLE_TEACHER + " ("
            + COLUMN_TEACHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TEACHER_NAME + " TEXT, "
            + COLUMN_TEACHER_EMAIL + " TEXT);";

    // Créer la table Cours
    private static final String CREATE_TABLE_COURSE = "CREATE TABLE " + TABLE_COURSE + " ("
            + COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_COURSE_NAME + " TEXT, "
            + COLUMN_COURSE_HOURS + " INTEGER, "
            + COLUMN_COURSE_TEACHER_ID + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_COURSE_TEACHER_ID + ") REFERENCES " + TABLE_TEACHER + "(" + COLUMN_TEACHER_ID + "));";

    // Constructeur
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Créer les tables
            db.execSQL(CREATE_TABLE_TEACHER);
            db.execSQL(CREATE_TABLE_COURSE);
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Erreur lors de la création des tables", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Supprimer les anciennes tables si elles existent
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHER);
        // Re-créer les tables
        onCreate(db);
    }

    // Ajouter un enseignant
    public long addTeacher(String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEACHER_NAME, name);
        values.put(COLUMN_TEACHER_EMAIL, email);
        long result = db.insert(TABLE_TEACHER, null, values);
        db.close();
        return result;
    }

    // Ajouter un cours
    public long addCourse(String name, int hours, int teacherId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE_NAME, name);
        values.put(COLUMN_COURSE_HOURS, hours);
        values.put(COLUMN_COURSE_TEACHER_ID, teacherId);
        long result = db.insert(TABLE_COURSE, null, values);
        db.close();
        return result;
    }

    // Obtenir tous les enseignants
    public Cursor getAllTeachers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_TEACHER, null, null, null, null, null, null);
    }

    // Obtenir tous les cours
    public Cursor getAllCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_COURSE, null, null, null, null, null, null);
    }

    // Mettre à jour un enseignant
    public int updateTeacher(int id, String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEACHER_NAME, name);
        values.put(COLUMN_TEACHER_EMAIL, email);
        return db.update(TABLE_TEACHER, values, COLUMN_TEACHER_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Mettre à jour un cours
    public int updateCourse(int id, String name, int hours, int teacherId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE_NAME, name);
        values.put(COLUMN_COURSE_HOURS, hours);
        values.put(COLUMN_COURSE_TEACHER_ID, teacherId);
        return db.update(TABLE_COURSE, values, COLUMN_COURSE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Supprimer un enseignant
    public void deleteTeacher(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TEACHER, COLUMN_TEACHER_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Supprimer un cours
    public void deleteCourse(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSE, COLUMN_COURSE_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
