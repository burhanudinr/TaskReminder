package com.burhanudin.taskreminder.model;

public class Reminder {
    public static final String TABLE = "reminder";

    public static final String KOLOM_ID = "id";
    public static final String KOLOM_NAMA = "nama";
    public static final String KOLOM_DSC = "dsc";
    public static final String KOLOM_PATH_IMG = "pathimg";
    public static final String KOLOM_TIMESTAMP = "timestamp";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE + "("
                    + KOLOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KOLOM_NAMA + " TEXT,"
                    + KOLOM_DSC + " TEXT,"
                    + KOLOM_PATH_IMG + " TEXT,"
                    + KOLOM_TIMESTAMP + " TEXT"
                    + ")";
    private int id;
    private String nama;
    private String dsc;
    private String pathimg;
    private String timestamp;

    public Reminder() {
    }

    public Reminder(int id, String nama, String dsc, String pathimg, String timestamp) {
        this.id = id;
        this.nama = nama;
        this.dsc = dsc;
        this.pathimg = pathimg;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public String getPathimg() {
        return pathimg;
    }

    public void setPathimg(String pathimg) {
        this.pathimg = pathimg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
