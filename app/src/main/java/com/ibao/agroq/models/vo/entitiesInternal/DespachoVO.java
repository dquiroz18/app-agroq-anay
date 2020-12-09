package com.ibao.agroq.models.vo.entitiesInternal;

public class DespachoVO extends ProcesoVO{


    private String origen;
    private int GGN;
    private String nReserva;
    private String TrasportModel;
    private String nContenedor;

    private String fechaInspeccion;
    private String fechaCarga;
    private String fechaSalida;
    private String fechaLlegada;

    private float temperatura;
    private float dioxidoCarbono;
    private float oxigeno;
    private String tecnologia;

    private String termo1;
    private String termo2;
    private String termo3;

    private String nameControlador;
    private int nPallets;
    private int nCajasPallet;
    private float pesoCaja;
    private boolean preFrio;

    private int idPlanta;
    private String namePlanta;


    private int idCultivo;
    private String nameCultivo;

    private String cliente;

    public String getRG() {
        return rg;
    }

    public void setRG(String rg) {
        this.rg = rg;
    }

    public String getPL() {
        return pl;
    }

    public void setPL(String pl) {
        this.pl = pl;
    }

    public String getTP() {
        return tp;
    }

    public void setTP(String tp) {
        this.tp = tp;
    }

    private String rg;
    private String pl;
    private String tp;



    public DespachoVO() {
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public int getGGN() {
        return GGN;
    }

    public void setGGN(int GGN) {
        this.GGN = GGN;
    }

    public String getnReserva() {
        return nReserva;
    }

    public void setnReserva(String nReserva) {
        this.nReserva = nReserva;
    }

    public String getTrasportModel() {
        return TrasportModel;
    }

    public void setTrasportModel(String trasportModel) {
        TrasportModel = trasportModel;
    }

    public String getnContenedor() {
        return nContenedor;
    }

    public void setnContenedor(String nContenedor) {
        this.nContenedor = nContenedor;
    }

    public String getFechaInspeccion() {
        return fechaInspeccion;
    }

    public void setFechaInspeccion(String fechaInspeccion) {
        this.fechaInspeccion = fechaInspeccion;
    }

    public String getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(String fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(String fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public float getDioxidoCarbono() {
        return dioxidoCarbono;
    }

    public void setDioxidoCarbono(float dioxidoCarbono) {
        this.dioxidoCarbono = dioxidoCarbono;
    }

    public float getOxigeno() {
        return oxigeno;
    }

    public void setOxigeno(float oxigeno) {
        this.oxigeno = oxigeno;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getTermo1() {
        return termo1;
    }

    public void setTermo1(String termo1) {
        this.termo1 = termo1;
    }

    public String getTermo2() {
        return termo2;
    }

    public void setTermo2(String termo2) {
        this.termo2 = termo2;
    }

    public String getTermo3() {
        return termo3;
    }

    public void setTermo3(String termo3) {
        this.termo3 = termo3;
    }

    public String getNameControlador() {
        return nameControlador;
    }

    public void setNameControlador(String nameControlador) {
        this.nameControlador = nameControlador;
    }

    public int getnPallets() {
        return nPallets;
    }

    public void setnPallets(int nPallets) {
        this.nPallets = nPallets;
    }

    public int getnCajasPallet() {
        return nCajasPallet;
    }

    public void setnCajasPallet(int nCajasPallet) {
        this.nCajasPallet = nCajasPallet;
    }

    public float getPesoCaja() {
        return pesoCaja;
    }

    public void setPesoCaja(float pesoCaja) {
        this.pesoCaja = pesoCaja;
    }

    public boolean isPreFrio() {
        return preFrio;
    }

    public void setPreFrio(boolean preFrio) {
        this.preFrio = preFrio;
    }


    public int getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(int idPlanta) {
        this.idPlanta = idPlanta;
    }

    public String getNamePlanta() {
        return namePlanta;
    }

    public void setNamePlanta(String namePlanta) {
        this.namePlanta = namePlanta;
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

}
