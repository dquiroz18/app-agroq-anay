package com.ibao.agroq.models.vo.entitiesInternal;



public class CriterioDetalleVO {
    private int id;
    private int codigo;
    private String valor;
    private int idEvaluacionDetalle;
    private int idCriterio;
    private String nameCriterio;
    private String tipoDatoCriterio;
    private boolean isFotografiableCriterio;
    private float tolMaxCriterio;
    private float tolMinCriterio;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCalificacion() {
        return Calificacion;
    }

    public void setCalificacion(int calificacion) {
        Calificacion = calificacion;
    }

    private int Calificacion;//1 - bueno , 2 regular , 3 malo

    private int nFotos;
    private float porcentaje;

    public int getnFotos() {
        return nFotos;
    }

    public void setnFotos(int nFotos) {
        this.nFotos = nFotos;
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


    public CriterioDetalleVO() {


    }


    public String getNameCriterio() {
        return nameCriterio;
    }

    public void setNameCriterio(String nameCriterio) {
        this.nameCriterio = nameCriterio;
    }

    public String getTipoDatoCriterio() {
        return tipoDatoCriterio;
    }

    public void setTipoDatoCriterio(String tipoDatoCriterio) {
        this.tipoDatoCriterio = tipoDatoCriterio;
    }

    public boolean isFotografiableCriterio() {
        return isFotografiableCriterio;
    }

    public void setFotografiableCriterio(boolean fotografiableCriterio) {
        isFotografiableCriterio = fotografiableCriterio;
    }

    public float getTolMaxCriterio() {
        return tolMaxCriterio;
    }

    public void setTolMaxCriterio(float tolMaxCriterio) {
        this.tolMaxCriterio = tolMaxCriterio;
    }

    public float getTolMinCriterio() {
        return tolMinCriterio;
    }

    public void setTolMinCriterio(float tolMinCriterio) {
        this.tolMinCriterio = tolMinCriterio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getIdEvaluacionDetalle() {
        return idEvaluacionDetalle;
    }

    public void setIdEvaluacionDetalle(int idEvaluacionDetalle) {
        this.idEvaluacionDetalle = idEvaluacionDetalle;
    }

    public int getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(int idCriterio) {
        this.idCriterio = idCriterio;
    }
}
