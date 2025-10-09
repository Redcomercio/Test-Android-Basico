package com.orsanredcomercio.testandroidredcomercio.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "qr_scans")
public class QrScan {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String content; // Contenido del QR
    public Long timestamp; // Fecha y hora de la captura

    public QrScan(String content) {
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public String getFormattedTime() {
        if (timestamp == 0) return "Sin hora";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
}
