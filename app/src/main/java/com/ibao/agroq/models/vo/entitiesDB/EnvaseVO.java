package com.ibao.agroq.models.vo.entitiesDB;

public class EnvaseVO {

    private int id;
    private String name;
    private String pesoNeto;
    private String pesoTara;
    private int idTipoProceso;


    public EnvaseVO() {
    }

    public EnvaseVO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public EnvaseVO(int id, String name, String pesoNeto, String pesoTara, int idTipoProceso) {
        this.id = id;
        this.name = name;
        this.pesoNeto = pesoNeto;
        this.pesoTara = pesoTara;
        this.idTipoProceso = idTipoProceso;
    }

    public String getPesoNeto() {
        return pesoNeto;
    }

    public void setPesoNeto(String pesoNeto) {
        this.pesoNeto = pesoNeto;
    }

    public String getPesoTara() {
        return pesoTara;
    }

    public void setPesoTara(String pesoTara) {
        this.pesoTara = pesoTara;
    }

    public int getIdTipoProceso() {
        return idTipoProceso;
    }

    public void setIdTipoProceso(int idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
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
