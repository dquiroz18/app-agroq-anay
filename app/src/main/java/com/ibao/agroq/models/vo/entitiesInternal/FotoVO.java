package com.ibao.agroq.models.vo.entitiesInternal;

import android.graphics.Bitmap;

public class FotoVO {
    private int id;
    private String fechaHora;
    private String path;
    private int idCriterioDetalle;
    private Bitmap bitmap;
    private String StringBitmap;

    public FotoVO(int id, String fechaHora, String path) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.path = path;
    }

    public String getStringBitmap() {
        return StringBitmap;
    }

    public void setStringBitmap(String stringBitmap) {
        StringBitmap = stringBitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public FotoVO() {

    }

    public int getIdCriterioDetalle() {
        return idCriterioDetalle;
    }

    public void setIdCriterioDetalle(int idCriterioDetalle) {
        this.idCriterioDetalle = idCriterioDetalle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fecha) {
        this.fechaHora = fecha;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
