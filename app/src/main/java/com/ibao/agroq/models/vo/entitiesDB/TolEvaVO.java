package com.ibao.agroq.models.vo.entitiesDB;

public class TolEvaVO {

    private int id;
    private int idConfEva;
    private int idVariedad ;
    private float tolMin;
    private float tolMax;
/*
    TABLE_TOLEVALUACION+
            "("+
    TABLE_TOLEVALUACION_COL_ID+","+
    TABLE_TOLEVALUACION_COL_IDCONFEVALUACION +","+
    TABLE_TOLEVALUACION_COL_IDVARIEDAD+","+
    TABLE_TOLEVALUACION_COL_MINIMO+","+
    TABLE_TOLEVALUACION_COL_MAXIMO+""+
    */
    public TolEvaVO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdConfEva() {
        return idConfEva;
    }

    public void setIdConfEva(int idConfEva) {
        this.idConfEva = idConfEva;
    }

    public int getIdVariedad() {
        return idVariedad;
    }

    public void setIdVariedad(int idVariedad) {
        this.idVariedad = idVariedad;
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
}
