package com.ibao.agroq.models.vo.entitiesDB;

public class EvaluacionVO {

    private int id;
    private String name;
    private boolean materiaSeca;
    private boolean defecto;
    private int idCultivo;

    public EvaluacionVO() {
    }

    public EvaluacionVO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isMateriaSeca() {
        return materiaSeca;
    }

    public void setMateriaSeca(boolean materiaSeca) {
        this.materiaSeca = materiaSeca;
    }

    public boolean isDefecto() {
        return defecto;
    }

    public void setDefecto(boolean defecto) {
        this.defecto = defecto;
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

    public void setCultivo(int idCultivo) {
        this.idCultivo = idCultivo;
    }

    public int getCultivo() {
        return idCultivo;
    }
}
