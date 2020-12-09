package com.ibao.agroq.models.vo.entitiesInternal;

public class MuestraVO {

    private int id;
    private int idProceso;
    private int idTipoProceso;
    private int cantidad;//parsearlo a int float o boolean segun se crea conveniente
    private String fechaHora;
    private String comentario;
    private String nameTipoProceso;
    private int calificacion;

    public MuestraVO(){

    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getNameTipoProceso() {
        return nameTipoProceso;
    }

    public void setNameTipoProceso(String nameTipoProceso) {
        this.nameTipoProceso = nameTipoProceso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(int idProceso) {
        this.idProceso = idProceso;
    }

    public int getIdTipoProceso() {
        return idTipoProceso;
    }

    public void setIdTipoProceso(int idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
