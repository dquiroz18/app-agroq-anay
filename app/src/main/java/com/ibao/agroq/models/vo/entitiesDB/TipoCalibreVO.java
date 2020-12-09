package com.ibao.agroq.models.vo.entitiesDB;

public class TipoCalibreVO {
    private int id;
    private String name;

    public TipoCalibreVO(){
    }

    public TipoCalibreVO(int id, String name) {
        this.id = id;
        this.name = name;
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
