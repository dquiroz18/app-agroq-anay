package com.ibao.agroq.models.vo.entitiesInternal;

public class RecepcionVO extends ProcesoVO{

    private int idPlanta;
    private String namePlanta;

    private int idZona;
    private String nameZona;

    private int idEmpresa;
    private String nameEmpresa;

    private int idFundo;
    private String nameFundo;

    private int idCultivo;
    private String nameCultivo;

    private int idEnvase;
    private String nameEnvase;

    private int idVariedad;
    private String nameVariedad;

    private String nOrdenProceso;
    private String unidadesRecepcion;
    private String kilosRecepcion;

    private String nOrdenGuia;

    public RecepcionVO() {

    }

    public String getnOrdenGuia() {
        return nOrdenGuia;
    }

    public void setnOrdenGuia(String nOrdenGuia) {
        this.nOrdenGuia = nOrdenGuia;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public String getNameZona() {
        return nameZona;
    }

    public void setNameZona(String nameZona) {
        this.nameZona = nameZona;
    }

    public int getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(int idCultivo) {
        this.idCultivo = idCultivo;
    }

    public String getNameCultivo() {
        return nameCultivo;
    }

    public void setNameCultivo(String nameCultivo) {
        this.nameCultivo = nameCultivo;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNameEmpresa() {
        return nameEmpresa;
    }

    public void setNameEmpresa(String nameEmpresa) {
        this.nameEmpresa = nameEmpresa;
    }

    public String getNamePlanta() {
        return namePlanta;
    }

    public void setNamePlanta(String namePlanta) {
        this.namePlanta = namePlanta;
    }

    public String getNameFundo() {
        return nameFundo;
    }

    public void setNameFundo(String nameFundo) {
        this.nameFundo = nameFundo;
    }

    public String getNameEnvase() {
        return nameEnvase;
    }

    public void setNameEnvase(String nameEnvase) {
        this.nameEnvase = nameEnvase;
    }

    public String getNameVariedad() {
        return nameVariedad;
    }

    public void setNameVariedad(String nameVariedad) {
        this.nameVariedad = nameVariedad;
    }

    public String getnOrdenProceso() {
        return nOrdenProceso;
    }

    public void setnOrdenProceso(String nOrdenProceso) {
        this.nOrdenProceso = nOrdenProceso;
    }

    public int getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(int idPlanta) {
        this.idPlanta = idPlanta;
    }

    public int getIdFundo() {
        return idFundo;
    }

    public void setIdFundo(int idFundo) {
        this.idFundo = idFundo;
    }

    public int getIdEnvase() {
        return idEnvase;
    }

    public void setIdEnvase(int idEnvase) {
        this.idEnvase = idEnvase;
    }

    public int getIdVariedad() {
        return idVariedad;
    }

    public void setIdVariedad(int idVariedad) {
        this.idVariedad = idVariedad;
    }



    public String getUnidadesRecepcion() {
        return unidadesRecepcion;
    }

    public void setUnidadesRecepcion(String unidadesRecepcion) {
        this.unidadesRecepcion = unidadesRecepcion;
    }

    public String getKilosRecepcion() {
        return kilosRecepcion;
    }

    public void setKilosRecepcion(String kilosRecepcion) {
        this.kilosRecepcion = kilosRecepcion;
    }
}
