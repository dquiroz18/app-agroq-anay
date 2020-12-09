package com.ibao.agroq.models.vo.entitiesInternal;

public class EvaluacionDetalleVO {

    private int id;
    private int idEvaluacion;
    private int idMuestra;
    private String nameEvaluacion;
    private float cantidad;
    private float porcentaje;
    private int calificacion;
    private boolean matSec;
    private boolean defecto;
    private int idTol;
    private float tolMin;
    private float tolMax;

    public int getIdTol() {
        return idTol;
    }

    public void setIdTol(int idTol) {
        this.idTol = idTol;
    }

    public float getTolMin() {
        return tolMin;
    }

    public void setTolMin(float tolMin) {
        this.tolMin = tolMin;
    }

    public float getTolMax() {
        return tolMax;
    }

    public void setTolMax(float tolMax) {
        this.tolMax = tolMax;
    }

    public boolean isMatSec() {
        return matSec;
    }

    public void setMatSec(boolean matSec) {
        this.matSec = matSec;
    }

    public boolean isDefecto() {
        return defecto;
    }

    public void setDefecto(boolean defecto) {
        this.defecto = defecto;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = getFloatFormateado(porcentaje);

    }

    public float getFloatFormateado(float n){
        return ((int)(n*10))/10.0f;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public EvaluacionDetalleVO() {
    }

    public EvaluacionDetalleVO(int id, int idEvaluacion, int idMuestra, String nameEvaluacion) {
        this.id = id;
        this.idEvaluacion = idEvaluacion;
        this.idMuestra = idMuestra;
        this.nameEvaluacion = nameEvaluacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public int getIdMuestra() {
        return idMuestra;
    }

    public void setIdMuestra(int idMuestra) {
        this.idMuestra = idMuestra;
    }

    public String getNameEvaluacion() {
        return nameEvaluacion;
    }

    public void setNameEvaluacion(String nameEvaluacion) {
        this.nameEvaluacion = nameEvaluacion;
    }
}
