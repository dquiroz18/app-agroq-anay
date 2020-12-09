package com.ibao.agroq.models.vo.entitiesDB;

public class CriterioVO {

    private int id;
    private int codigo;
    private String name;
    private String tipoDato;
    private boolean isFotografiable;
    private float tolMax;
    private float tolMin;
    private int idEvaluacion;
    private int idTipoProceso;

    public CriterioVO() {
    }

    public int getCodigo() {
        return codigo;
    }

    public int getIdTipoProceso() {
        return idTipoProceso;
    }

    public void setIdTipoProceso(int idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public boolean isFotografiable() {
        return isFotografiable;
    }

    public void setFotografiable(boolean fotografiable) {
        isFotografiable = fotografiable;
    }

    public float getTolMax() {
        return tolMax;
    }

    public void setTolMax(float tolMax) {
        this.tolMax = tolMax;
    }

    public float getTolMin() {
        return tolMin;
    }

    public void setTolMin(float tolMin) {
        this.tolMin = tolMin;
    }

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
