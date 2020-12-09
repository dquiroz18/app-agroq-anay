package com.ibao.agroq.utilities;


public class Utilities {

    private static String TAG = Utilities.class.getSimpleName();

    public static final String URL_ROOT="http://35.167.15.182/APIs/AgroQ/AnayPeruvian/Requests/";
    public static final String URL_AUTENTIFICATION=URL_ROOT+"autenticar.php";
    public static final String URL_DOWNLOAD_TABLE_EMPRESA=URL_ROOT+"getEmpresas.php";
    public static final String URL_DOWNLOAD_TABLE_FUNDO=URL_ROOT+"getFundos.php";
    public static final String URL_DOWNLOAD_TABLE_CULTIVO=URL_ROOT+"getCultivos.php";
    public static final String URL_DOWNLOAD_TABLE_VARIEDAD=URL_ROOT+"getVariedades.php";
    public static final String URL_DOWNLOAD_TABLE_CRITERIO=URL_ROOT+"getCriterios.php";
    public static final String URL_DOWNLOAD_TABLE_CONFIGURACIONEVALUACION =URL_ROOT+"getConfiguracionEvaluacion.php";
    public static final String URL_DOWNLOAD_TABLE_ZONA=URL_ROOT+"getZonas.php";
    public static final String URL_DOWNLOAD_TABLE_PLANTA=URL_ROOT+"getPlantas.php";
    public static final String URL_DOWNLOAD_TABLE_TIPOPROCESO=URL_ROOT+"getProcesos.php";
    public static final String URL_DOWNLOAD_TABLE_EVALUACION=URL_ROOT+"getEvaluaciones.php";
    public static final String URL_DOWNLOAD_TABLE_ENVASE=URL_ROOT+"getEnvases.php";
    public static final String URL_DOWNLOAD_TABLE_TOLERANCIAEVALUACION=URL_ROOT+"getToleranciaEvaluacion.php";
    public static final String URL_DOWNLOAD_TABLE_CATEGORIA=URL_ROOT+"getCategorias.php";
    public static final String URL_DOWNLOAD_TABLE_DESTINO=URL_ROOT+"getDestinos.php";
    public static final String URL_DOWNLOAD_TABLE_CALIBRE=URL_ROOT+"getCalibres.php";
    public static final String URL_DOWNLOAD_TABLE_TIPOCALIBRE=URL_ROOT+"getTipoCalibre.php";
    public static final String URL_DOWNLOAD_RECEPCION_OP=URL_ROOT+"getRecepcionOP.php";


    public static final String URL_UPLOAD_MASTER_RECEPCION =URL_ROOT+"insertDataFromMovilFinal.php";
    public static final String URL_UPLOAD_MASTER_PRODUCCION =URL_ROOT+"insertDataFromMovilFinal.php";
    public static final String URL_UPLOAD_MASTER_DESPACHO =URL_ROOT+"insertDataFromMovilFinal.php";
    public static final String URL_UPLOAD_MASTER_DESCARTE =URL_ROOT+"insertDataFromMovilFinal.php";

    public static final String URL_UPLOAD_FOTOS=URL_ROOT+"insertFotos.php";
    public static final String URL_UPLOAD_MAKE_PDF=URL_ROOT+"makepdfs.php";

    public static final String DATABASE_NAME="data";

    public static final String TABLE_ZONA="zona",
            TABLE_ZONA_COL_ID                ="id",
            TABLE_ZONA_TYPECOL_ID            ="INTEGER",
            TABLE_ZONA_COL_NAME              ="name",
            TABLE_ZONA_TYPECOL_NAME          ="varchar(50)";


    public static final String TABLE_EMPRESA="empresa",
            TABLE_EMPRESA_COL_ID                ="id",
            TABLE_EMPRESA_TYPECOL_ID            ="INTEGER",
            TABLE_EMPRESA_COL_NAME              ="name",
            TABLE_EMPRESA_TYPECOL_NAME          ="varchar(50)",
            TABLE_EMPRESA_COL_IDZONA            ="idZona",
            TABLE_EMPRESA_TYPECOL_IDZONA            ="INTEGER";

    public static final String TABLE_FUNDO  ="fundo",
            TABLE_FUNDO_COL_ID                  ="id",
            TABLE_FUNDO_TYPECOL_ID              ="INTEGER",
            TABLE_FUNDO_COL_NAME                ="name",
            TABLE_FUNDO_TYPECOL_NAME            ="varchar(50)",
            TABLE_FUNDO_COL_IDEMPRESA           ="idEmpresa",
            TABLE_FUNDO_TYPECOL_IDEMPRESA       ="INTEGER";

    public static final String TABLE_CULTIVO="cultivo",
            TABLE_CULTIVO_COL_ID                ="id",
            TABLE_CULTIVO_TYPECOL_ID            ="INTEGER",
            TABLE_CULTIVO_COL_NAME              ="name",
            TABLE_CULTIVO_TYPECOL_NAME          ="varchar(50)";

    public static final String TABLE_VARIEDAD="variedad",
            TABLE_VARIEDAD_COL_ID               ="id",
            TABLE_VARIEDAD_TYPECOL_ID           ="INTEGER",
            TABLE_VARIEDAD_COL_NAME             ="name",
            TABLE_VARIEDAD_TYPECOL_NAME         ="VARCHAR(50)",
            TABLE_VARIEDAD_COL_IDCULTIVO        ="idCultivo",
            TABLE_VARIEDAD_TYPECOL_IDCULTIVO    ="INTEGER";

    public static final String TABLE_CAMPO="recepcion",
            TABLE_CAMPO_COL_ID             ="id",
            TABLE_CAMPO_TYPECOL_ID         ="INTEGER",
            TABLE_CAMPO_COL_IDFUNDO        ="idFundo",
            TABLE_CAMPO_TYPECOL_IDFUNDO    ="INTEGER",
            TABLE_CAMPO_COL_IDLOTE       ="idFundo",
            TABLE_CAMPO_TYPECOL_IDLOTE    ="INTEGER",
            TABLE_CAMPO_COL_IDVARIEDAD     ="idVariedad",
            TABLE_CAMPO_TYPECOL_IDVARIEDAD ="INTEGER",
            TABLE_CAMPO_COL_FECHAHORA  ="fechaHoraCampo",
            TABLE_CAMPO_TYPECOL_FECHAHORA="date",
            TABLE_CAMPO_COL_EDITING  ="statusEdit",
            TABLE_CAMPO_TYPECOL_EDITING="BOOLEAN";

    public static final String TABLE_RECEPCION="recepcion",
            TABLE_RECEPCION_COL_ID             ="id",
            TABLE_RECEPCION_TYPECOL_ID         ="INTEGER",
            TABLE_RECEPCION_COL_IDPLANTA       ="idPlanta",
            TABLE_RECEPCION_TYPECOL_IDPLANTA   ="INTEGER",
            TABLE_RECEPCION_COL_IDFUNDO        ="idFundo",
            TABLE_RECEPCION_TYPECOL_IDFUNDO    ="INTEGER",
            TABLE_RECEPCION_COL_IDVARIEDAD     ="idVariedad",
            TABLE_RECEPCION_TYPECOL_IDVARIEDAD ="INTEGER",
            TABLE_RECEPCION_COL_IDENVASE ="idTipoEnvase",
            TABLE_RECEPCION_TYPECOL_IDTIPOENVASE="INTEGER",
            TABLE_RECEPCION_COL_FECHAHORA  ="fechaHoraRecepción",
            TABLE_RECEPCION_TYPECOL_FECHAHORA="date",
            TABLE_RECEPCION_COL_EDITING  ="statusEdit",
            TABLE_RECEPCION_TYPECOL_EDITING="BOOLEAN",
            TABLE_RECEPCION_COL_NORDENPROCESO    ="nOrdenProceso",
            TABLE_RECEPCION_TYPECOL_NORDENPROCESO="varchar(50)",
            TABLE_RECEPCION_COL_NGUIA    ="nGuia",
            TABLE_RECEPCION_TYPECOL_NGUIA="varchar(50)",
            TABLE_RECEPCION_COL_UNIDADES    ="unidadesRecepcion",
            TABLE_RECEPCION_TYPECOL_UNIDADES="DECIMAL(10,3)",
            TABLE_RECEPCION_COL_KILOS    ="kilosRecepcion",
            TABLE_RECEPCION_TYPECOL_KILOS="DECIMAL(10,3)";


    public static final String TABLE_PRODUCCION="produccion",
            TABLE_PRODUCCION_COL_ID             ="id",
            TABLE_PRODUCCION_TYPECOL_ID         ="INTEGER",
            TABLE_PRODUCCION_COL_IDRECEPCION     ="idRecepcion",
            TABLE_PRODUCCION_TYPECOL_IDRECEPCION="INTEGER",
            TABLE_PRODUCCION_COL_IDPLANTA       ="idPlanta",
            TABLE_PRODUCCION_TYPECOL_IDPLANTA   ="INTEGER",
            TABLE_PRODUCCION_COL_IDFUNDO        ="idFundo",
            TABLE_PRODUCCION_TYPECOL_IDFUNDO    ="INTEGER",
            TABLE_PRODUCCION_COL_IDVARIEDAD  ="idVariedad",
            TABLE_PRODUCCION_TYPECOL_IDVARIEDAD="INTEGER",
            TABLE_PRODUCCION_COL_IDENVASE       ="idTipoEnvase",
            TABLE_PRODUCCION_TYPECOL_IDENVASE="INTEGER",
            TABLE_PRODUCCION_COL_IDDESTINO       ="idDestino",
            TABLE_PRODUCCION_TYPECOL_IDDESTINO  ="INTEGER",
            TABLE_PRODUCCION_COL_IDCATEGORIA    ="idCategoria",
            TABLE_PRODUCCION_TYPECOL_IDCATEGORIA="INTEGER",
            TABLE_PRODUCCION_COL_IDCALIBRE      ="idCalibre",
            TABLE_PRODUCCION_TYPECOL_IDCALIBRE  ="INTEGER",
            TABLE_PRODUCCION_COL_FECHAHORA      ="fechaHora",
            TABLE_PRODUCCION_TYPECOL_FECHAHORA  ="date",
            TABLE_PRODUCCION_COL_EDITING        ="statusEdit",
            TABLE_PRODUCCION_TYPECOL_EDITING    ="BOOLEAN",
            TABLE_PRODUCCION_COL_NORDENPROCESO    ="nOrdenProceso",
            TABLE_PRODUCCION_TYPECOL_NORDENPROCESO="varchar(50)",
            TABLE_PRODUCCION_COL_UNIDADES       ="unidades",
            TABLE_PRODUCCION_TYPECOL_UNIDADES   ="DECIMAL(10,3)",
            TABLE_PRODUCCION_COL_KILOS          ="kilos",
            TABLE_PRODUCCION_TYPECOL_KILOS      ="DECIMAL(10,3)";


    public static final String TABLE_DESPACHO="despacho",
            TABLE_DESPACHO_COL_ID             ="id",//0
            TABLE_DESPACHO_TYPECOL_ID         ="INTEGER",
            TABLE_DESPACHO_COL_IDPLANTA       ="idPlanta",//1
            TABLE_DESPACHO_TYPECOL_IDPLANTA   ="INTEGER",
            TABLE_DESPACHO_COL_IDCULTIVO      ="idCultivo",//2
            TABLE_DESPACHO_TYPECOL_IDCULTIVO  ="INTEGER",
            TABLE_DESPACHO_COL_FECHAHORA      ="fechaHora",//3
            TABLE_DESPACHO_TYPECOL_FECHAHORA  ="date",
            TABLE_DESPACHO_COL_EDITING        ="statusEdit",//4
            TABLE_DESPACHO_TYPECOL_EDITING    ="BOOLEAN",

            TABLE_DESPACHO_COL_ORIGEN="origen",//5
            TABLE_DESPACHO_TYPECOL_ORIGEN="varchar(50)",
            TABLE_DESPACHO_COL_GGN="ggn",//6
            TABLE_DESPACHO_TYPECOL_GGN="INTEGER",
            TABLE_DESPACHO_COL_NRESERVA="nReserva",//7
            TABLE_DESPACHO_TYPECOL_NRESERVA="VARCHAR(50)",
            TABLE_DESPACHO_COL_TRANSPORTMODEL="vessel",//8
            TABLE_DESPACHO_TYPECOL_TRANSPORTMODEL="VARCHAR(50)",
            TABLE_DESPACHO_COL_NCONTENEDOR="nContenedor",//9
            TABLE_DESPACHO_TYPECOL_NCONTENEDOR="VARCHAR(50)",

            TABLE_DESPACHO_COL_FECHAINSPECCION="fechaInspecion",//10
            TABLE_DESPACHO_TYPECOL_FECHAINSPECCION="DATE",
            TABLE_DESPACHO_COL_FECHACARGA="fechaCarga",//11
            TABLE_DESPACHO_TYPECOL_FECHACARGA="DATE",
            TABLE_DESPACHO_COL_FECHASALIDA="fechaSalida",//12
            TABLE_DESPACHO_TYPECOL_FECHASALIDA="DATE",
            TABLE_DESPACHO_COL_FECHALLEGADA="fechaLlegada",//13
            TABLE_DESPACHO_TYPECOL_FECHALLEGADA="DATE",


            TABLE_DESPACHO_COL_TEMPERATURA="temperatura",//14
            TABLE_DESPACHO_TYPECOL_TEMPERATURA="DECIMAL(10,3)",
            TABLE_DESPACHO_COL_DIOXIDOCARBONO="dioxidoDeCarbono",//15
            TABLE_DESPACHO_TYPECOL_DIOXIDOCARBONO="DECIMAL(10,3)",
            TABLE_DESPACHO_COL_OXIGENO="oxigeno",//16
            TABLE_DESPACHO_TYPECOL_OXIGENO="DECIMAL(10,3)",
            TABLE_DESPACHO_COL_TECNOLOGIA="tecnologia",//17
            TABLE_DESPACHO_TYPECOL_TECNOLOGIA="VARCHAR(50)",

            TABLE_DESPACHO_COL_TERMOGRAFO1="termografo1",//18
            TABLE_DESPACHO_TYPECOL_TERMOGRAFO1="VARCHAR(50)",
            TABLE_DESPACHO_COL_TERMOGRAFO2="termografo2",//19
            TABLE_DESPACHO_TYPECOL_TERMOGRAFO2="VARCHAR(50)",
            TABLE_DESPACHO_COL_TERMOGRAFO3="termografo3",//20
            TABLE_DESPACHO_TYPECOL_TERMOGRAFO3="VARCHAR(50)",

            TABLE_DESPACHO_COL_NAMECONTROLADOR="nameControlador",//21
            TABLE_DESPACHO_TYPECOL_NAMECONTROLADOR="VARCHAR(50)",
            TABLE_DESPACHO_COL_NPALLETS="nPallets",//22
            TABLE_DESPACHO_TYPECOL_NPALLETS="INTEGER",
            TABLE_DESPACHO_COL_CAJASPALLET="cajasPallet",//23
            TABLE_DESPACHO_TYPECOL_CAJASPALLET="INTEGER",
            TABLE_DESPACHO_COL_PESOCAJA="pesoCaja",//24
            TABLE_DESPACHO_TYPECOL_PESOCAJA="DECIMAL(10,3)",
            TABLE_DESPACHO_COL_RG="rg",//24
            TABLE_DESPACHO_TYPECOL_RG="VARCHAR(50)",
            TABLE_DESPACHO_COL_PL="pl",//24
            TABLE_DESPACHO_TYPECOL_PL="VARCHAR(50)",
            TABLE_DESPACHO_COL_TP="rp",//24
            TABLE_DESPACHO_TYPECOL_TP="VARCHAR(50)",
            TABLE_DESPACHO_COL_PREFRIO="prefrio",//25
            TABLE_DESPACHO_TYPECOL_PREFRIO="BOOLEAN",
            TABLE_DESPACHO_COL_CLIENTE="cliente",//25
            TABLE_DESPACHO_TYPECOL_CLIENTE="VARCHAR(50)"

    ;



    public static final String TABLE_DESCARTE="descarte",
            TABLE_DESCARTE_COL_ID             ="id",
            TABLE_DESCARTE_TYPECOL_ID         ="INTEGER",
            TABLE_DESCARTE_COL_IDRECEPCION     ="idRecepcion",
            TABLE_DESCARTE_TYPECOL_IDRECEPCION="INTEGER",
            TABLE_DESCARTE_COL_IDPLANTA       ="idPlanta",
            TABLE_DESCARTE_TYPECOL_IDPLANTA   ="INTEGER",
            TABLE_DESCARTE_COL_IDFUNDO        ="idFundo",
            TABLE_DESCARTE_TYPECOL_IDFUNDO    ="INTEGER",
            TABLE_DESCARTE_COL_IDVARIEDAD  ="idVariedad",
            TABLE_DESCARTE_TYPECOL_IDVARIEDAD="INTEGER",
            TABLE_DESCARTE_COL_IDENVASE       ="idTipoEnvase",
            TABLE_DESCARTE_TYPECOL_IDENVASE="INTEGER",
            TABLE_DESCARTE_COL_FECHAHORA      ="fechaHora",
            TABLE_DESCARTE_TYPECOL_FECHAHORA  ="date",
            TABLE_DESCARTE_COL_EDITING        ="statusEdit",
            TABLE_DESCARTE_TYPECOL_EDITING    ="BOOLEAN",
            TABLE_DESCARTE_COL_NORDENPROCESO    ="nOrdenProceso",
            TABLE_DESCARTE_TYPECOL_NORDENPROCESO="varchar(50)",
            TABLE_DESCARTE_COL_UNIDADES       ="unidades",
            TABLE_DESCARTE_TYPECOL_UNIDADES   ="DECIMAL(10,3)",
            TABLE_DESCARTE_COL_KILOS          ="kilos",
            TABLE_DESCARTE_TYPECOL_KILOS      ="DECIMAL(10,3)";



    public static final String TABLE_EVALUACION="evaluacion",
            TABLE_EVALUACION_COL_ID                 ="id",
            TABLE_EVALUACION_TYPECOL_ID             ="INTEGER",
            TABLE_EVALUACION_COL_ISMATSEC           ="isMatSec",
            TABLE_EVALUACION_TYPECOL_ISMATSEC       ="BOOLEAN",
            TABLE_EVALUACION_COL_NAME               ="name",
            TABLE_EVALUACION_TYPECOL_NAME           ="varchar(50)",
            TABLE_EVALUACION_COL_CULTIVO            ="idCultivo",
            TABLE_EVALUACION_TYPECOL_CULTIVO        ="INTEGER";

    public static final String TABLE_FOTO="foto",
            TABLE_FOTO_COL_ID = "id",
            TABLE_FOTO_TYPECOL_ID ="INTEGER",
            TABLE_FOTO_COL_PATH = "path",
            TABLE_FOTO_TYPECOL_PATH = "VARCHAR(200)",
            TABLE_FOTO_COL_HORAFECHA ="fechaHora",
            TABLE_FOTO_TYPECOL_HORAFECHA ="DATE",
            TABLE_FOTO_COL_IMAGEN ="imagen",
            TABLE_FOTO_TYPECOL_IMAGEN ="BLOB",
            TABLE_FOTO_COL_IDCRITERIODETALLE="idCriterioDetalle",
            TABLE_FOTO_TYPECOL_IDCRITERIODETALLE="INTEGER";

    public static final String TABLE_CRITERIODETALLE="criterioDetalle",
            TABLE_CRITERIODETALLE_COL_ID = "id",
            TABLE_CRITERIODETALLE_TYPECOL_ID ="INTEGER",
            TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE ="idEvaluaciondetalle",
            TABLE_CRITERIODETALLE_TYPECOL_IDEVALAUCIONDETALLE ="INTEGER",
            TABLE_CRITERIODETALLE_COL_IDCRITERIO="idMuestra",
            TABLE_CRITERIODETALLE_TYPECOL_IDCRITERIO="INTEGER",
            TABLE_CRITERIODETALLE_COL_VALOR="valor",
            TABLE_CRITERIODETALLE_TYPECOL_VALOR="VARCHAR(50)";

    public static final String TABLE_MUESTRA="muestra",
            TABLE_MUESTRA_COL_ID = "id",
            TABLE_MUESTRA_TYPECOL_ID ="INTEGER",
            TABLE_MUESTRA_COL_IDPROCESO = "idProceso",
            TABLE_MUESTRA_TYPECOL_IDPROCESO = "INTEGER",
            TABLE_MUESTRA_COL_FECHAHORA  ="fechaHoraMuestreo",
            TABLE_MUESTRA_TYPECOL_FECHAHORA="DATE",
            TABLE_MUESTRA_COL_IDTIPOPROCESO ="idTipoProceso",
            TABLE_MUESTRA_TYPECOL_IDTIPOPROCESO ="INTEGER",
            TABLE_MUESTRA_COL_CANTIDAD ="cantidad",
            TABLE_MUESTRA_TYPECOL_CANTIDAD ="INTEGER",
            TABLE_MUESTRA_COL_COMENTARIO="comentario",
            TABLE_MUESTRA_TYPECOL_COMENTARIO="varchar(250)";

    public static final String TABLE_CONFEVALUACION  ="configuracionCriterio",
            TABLE_CONFEVALUACION_COL_ID                ="id",
            TABLE_CONFEVALUACION_TYPECOL_ID            ="INTEGER",
            TABLE_CONFEVALUACION_COL_IDEVALUACION      ="idEvaluacion",
            TABLE_CONFEVALUACION_TYPECOL_IDEVALUACION  ="INTEGER",
            TABLE_CONFEVALUACION_COL_IDTIPOPROCESO     ="idTipoProceso",
            TABLE_CONFEVALUACION_TYPECOL_IDTIPOPROCESO ="INTEGER";

    public static final String TABLE_CRITERIO   ="criterio",
            TABLE_CRITERIO_COL_ID               ="id",
            TABLE_CRITERIO_TYPECOL_ID           ="INTEGER",
            TABLE_CRITERIO_COL_CODIGO           ="codigo",
            TABLE_CRITERIO_TYPECOL_CODIGO       ="INTEGER",
            TABLE_CRITERIO_COL_NAME             ="name",
            TABLE_CRITERIO_TYPECOL_NAME         ="VARCHAR(50)",
            TABLE_CRITERIO_COL_TIPODATO         ="tipoDato",
            TABLE_CRITERIO_TYPECOL_TIPODATO     ="VARCHAR(50)",
            TABLE_CRITERIO_COL_ISPHOTO          ="isFotografiable",
            TABLE_CRITERIO_TYPECOL_ISPHOTO      ="BOOLEAN",
            TABLE_CRITERIO_COL_IDEVALUACION     ="idEvaluacion",
            TABLE_CRITERIO_TYPECOL_IDEVALUACION ="INTEGER",
            TABLE_CRITERIO_COL_TOLERANCIAMINIMO ="toleranciaMinimo",
            TABLE_CRITERIO_TYPECOL_TOLERANCIAMINIMO="DECIMAL(10,3)",
            TABLE_CRITERIO_COL_TOLERANCIAMAXIMO ="toleranciaMaximo",
            TABLE_CRITERIO_TYPECOL_TOLERANCIAMAXIMO="DECIMAL(10,3)",
            TABLE_CRITERIO_COL_IDTIPOPROCESO     ="idTipoProceso",
            TABLE_CRITERIO_TYPECOL_IDTIPOPROCESO ="INTEGER"
                    ;

    public static final String TABLE_CALIBRE="calibre",
            TABLE_CALIBRE_COL_ID                ="id",
            TABLE_CALIBRE_TYPECOL_ID            ="INTEGER",
            TABLE_CALIBRE_COL_NAME              ="name",
            TABLE_CALIBRE_TYPECOL_NAME          ="varchar(50)",
            TABLE_CALIBRE_COL_PESOMIN           ="pesoMinimo",
            TABLE_CALIBRE_TYPECOL_PESOMIN       ="varchar(50)",
            TABLE_CALIBRE_COL_PESOMAX           ="pesoMaximo",
            TABLE_CALIBRE_TYPECOL_PESOMAX       ="varchar(50)",
            TABLE_CALIBRE_COL_IDTIPOCALIBRE ="idTipoCalibre",
            TABLE_CALIBRE_TYPECOL_IDTIPOCALIBRE ="INTEGER";

    public static final String TABLE_DESTINO    ="destino",
            TABLE_DESTINO_COL_ID                ="id",
            TABLE_DESTINO_TYPECOL_ID            ="INTEGER",
            TABLE_DESTINO_COL_NAME              ="name",
            TABLE_DESTINO_TYPECOL_NAME          ="varchar(50)";

    public static final String TABLE_CATEGORIA    ="categoria",
            TABLE_CATEGORIA_COL_ID                ="id",
            TABLE_CATEGORIA_TYPECOL_ID            ="INTEGER",
            TABLE_CATEGORIA_COL_NAME              ="name",
            TABLE_CATEGORIA_TYPECOL_NAME          ="varchar(50)";

    public static final String TABLE_PLANTACULTIVO="plantaCultivo",
            TABLE_PLANTACULTIVO_COL_ID            ="id",
            TABLE_PLANTACULTIVO_TYPECOL_ID        ="INTEGER",
            TABLE_PLANTACULTIVO_COL_IDPLANTA      ="idPlanta",
            TABLE_PLANTACULTIVO_TYPECOL_IDPLANTA  ="INTEGER",
            TABLE_PLANTACULTIVO_COL_IDCULTIVO     ="idCultivo",
            TABLE_PLANTACULTIVO_TYPECOL_IDCULTIVO ="INTEGER";

    public static final String TABLE_EVALUACIONDETALLE="evaluacionDetalle",
            TABLE_EVALUACIONDETALLE_COL_ID              ="id",
            TABLE_EVALUACIONDETALLE_TYPECOL_ID          ="INTEGER",
            TABLE_EVALUACIONDETALLE_COL_IDMUESTRA       ="idMuestra",
            TABLE_EVALUACIONDETALLE_TYPECOL_IDMUESTRA   ="INTEGER",
            TABLE_EVALUACIONDETALLE_COL_IDEVALUACION    ="idEvaluacion",
            TABLE_EVALUACIONDETALLE_TYPECOL_IDEVALUACION="INTEGER";


    public static final String TABLE_TIPOPROCESO="tipoProceso",
            TABLE_TIPOPROCESO_COL_ID                    ="id",
            TABLE_TIPOPROCESO_TYPECOL_ID                ="INTEGER",
            TABLE_TIPOPROCESO_COL_NAME                  ="name",
            TABLE_TIPOPROCESO_TYPECOL_NAME               ="varchar(50)";

    public static final String TABLE_TIPOCALIBRE="tipoCalibre",
            TABLE_TIPOCALIBRE_COL_ID            ="id",
            TABLE_TIPOCALIBRE_TYPECOL_ID        ="INTEGER",
            TABLE_TIPOCALIBRE_COL_NAME          ="name",
            TABLE_TIPOCALIBRE_TYPECOL_NAME      ="varchar(50)";

    public static final String TABLE_PLANTA="planta",
            TABLE_PLANTA_COL_ID               ="id",
            TABLE_PLANTA_TYPECOL_ID           ="INTEGER",
            TABLE_PLANTA_COL_NAME             ="name",
            TABLE_PLANTA_TYPECOL_NAME         ="varchar(50)";

    public static final String TABLE_ENVASE="envase",
            TABLE_ENVASE_COL_ID               ="id",
            TABLE_ENVASE_TYPECOL_ID           ="INTEGER",
            TABLE_ENVASE_COL_NAME             ="name",
            TABLE_ENVASE_TYPECOL_NAME         ="varchar(50)",
            TABLE_ENVASE_COL_PESONETO         ="pesoNeto",
            TABLE_ENVASE_TYPECOL_PESONETO     ="VARCHAR(50)",
            TABLE_ENVASE_COL_PESOTARA         ="pesoTara",
            TABLE_ENVASE_TYPECOL_PESOTARA     ="VARCHAR(50)",
            TABLE_ENVASE_COL_IDPROCESO         ="idProceso",
            TABLE_ENVASE_TYPECOL_IDPROCESO     ="INTEGER";

    public static final String TABLE_LOGINDATA="loginData",
            TABLE_LOGINDATA_COL_IDUSER            ="idUser",
            TABLE_LOGINDATA_TYPECOL_IDUSER        ="INTEGER",
            TABLE_LOGINDATA_COL_USER              ="user",
            TABLE_LOGINDATA_TYPECOL_USER          ="varchar(50)",
            TABLE_LOGINDATA_COL_LISTIDTIPOPROCESO ="listIdTipoProceso",
            TABLE_LOGINDATA_TYPECOL_LISTIDTIPOPROCESO="varchar(150)",
            TABLE_LOGINDATA_COL_PASSWORD          ="password",
            TABLE_LOGINDATA_TYPECOL_PASSWORD      ="varchar(50)",
            TABLE_LOGINDATA_COL_NAME              ="name",
            TABLE_LOGINDATA_TYPECOL_NAME          ="varchar(50)",
            TABLE_LOGINDATA_COL_LASTNAME          ="lastname",
            TABLE_LOGINDATA_TYPECOL_LASTNAME      ="varchar(50)",
            TABLE_LOGINDATA_COL_CODIGO            ="codigo",
            TABLE_LOGINDATA_TYPECOL_CODIGO        ="varchar(50)",
            TABLE_LOGINDATA_COL_IDTIPOPROCESO     ="idTipoProceso",
            TABLE_LOGINDATA_TYPECOL_IDTIPOPROCESO ="INTEGER",
            TABLE_LOGINDATA_COL_IDPLANTA          ="idPlanta",
            TABLE_LOGINDATA_TYPECOL_IDPLANTA      ="INTEGER",
            TABLE_LOGINDATA_COL_IDCULTIVO         ="idCultivo",
            TABLE_LOGINDATA_TYPECOL_IDCULTIVO     ="INTEGER";



    public static final String TABLE_TOLEVALUACION ="toleranciaEvaluacion",
            TABLE_TOLEVALUACION_COL_ID              ="id",
            TABLE_TOLEVALUACION_TYPECOL_ID          ="INTEGER",
            TABLE_TOLEVALUACION_COL_IDCULTIVO       ="idCultivo",
            TABLE_TOLEVALUACION_TYPECOL_IDCULTIVO   ="INTEGER",
            TABLE_TOLEVALUACION_COL_IDVARIEDAD ="idVariedad",
            TABLE_TOLEVALUACION_TYPECOL_IDVARIEDAD  ="integer",
            TABLE_TOLEVALUACION_COL_IDCONFEVALUACION ="idConfEvaluacion",
            TABLE_TOLEVALUACION_TYPECOL_IDCONFEVALUACION ="INTEGER",
            TABLE_TOLEVALUACION_COL_MINIMO          ="minimo",
            TABLE_TOLEVALUACION_TYPECOL_MINIMO      ="DECIMAL(10,3)",
            TABLE_TOLEVALUACION_COL_MAXIMO          ="maximo",
            TABLE_TOLEVALUACION_TYPECOL_MAXIMO      ="DECIMAL(10,3)";

    //SCRIPTS SQL CREATE TABLES

    public static final String CREATE_TABLE_TOLEVALUACION =
            " CREATE TABLE IF NOT EXISTS "+ TABLE_TOLEVALUACION +" ("+
                    TABLE_TOLEVALUACION_COL_ID +" "+ TABLE_TOLEVALUACION_TYPECOL_ID +" PRIMARY KEY , "+
                    TABLE_TOLEVALUACION_COL_IDCONFEVALUACION +" "+ TABLE_TOLEVALUACION_TYPECOL_IDCONFEVALUACION +" , "+
                    TABLE_TOLEVALUACION_COL_IDCULTIVO +" "+ TABLE_TOLEVALUACION_TYPECOL_IDCULTIVO +" , "+
                    TABLE_TOLEVALUACION_COL_IDVARIEDAD +" "+ TABLE_TOLEVALUACION_TYPECOL_IDVARIEDAD +", "+
                    TABLE_TOLEVALUACION_COL_MINIMO+" "+TABLE_TOLEVALUACION_TYPECOL_MINIMO+", "+
                    TABLE_TOLEVALUACION_COL_MAXIMO+" "+TABLE_TOLEVALUACION_TYPECOL_MAXIMO+" "+
                    ")";

    public static final String CREATE_TABLE_LOGINDATA =
            " CREATE TABLE IF NOT EXISTS "+TABLE_LOGINDATA+" (" +
                    TABLE_LOGINDATA_COL_IDUSER    +" "+TABLE_LOGINDATA_TYPECOL_IDUSER+" PRIMARY KEY," +
                    TABLE_LOGINDATA_COL_USER      +" "+TABLE_LOGINDATA_TYPECOL_USER+"," +
                    TABLE_LOGINDATA_COL_PASSWORD  +" "+TABLE_LOGINDATA_TYPECOL_PASSWORD+"," +
                    TABLE_LOGINDATA_COL_NAME      +" "+TABLE_LOGINDATA_TYPECOL_NAME+","+
                    TABLE_LOGINDATA_COL_LISTIDTIPOPROCESO      +" "+TABLE_LOGINDATA_TYPECOL_LISTIDTIPOPROCESO+","+
                    TABLE_LOGINDATA_COL_LASTNAME  +" "+TABLE_LOGINDATA_TYPECOL_LASTNAME+", "+
                    TABLE_LOGINDATA_COL_CODIGO    +" "+TABLE_LOGINDATA_TYPECOL_CODIGO+", "+
                    TABLE_LOGINDATA_COL_IDTIPOPROCESO+" "+TABLE_LOGINDATA_TYPECOL_IDTIPOPROCESO+", "+
                    TABLE_LOGINDATA_COL_IDPLANTA    +" "+TABLE_LOGINDATA_TYPECOL_IDPLANTA+", "+
                    TABLE_LOGINDATA_COL_IDCULTIVO    +" "+TABLE_LOGINDATA_TYPECOL_IDCULTIVO+" "+
                    ")";

    public static final String CREATE_TABLE_ENVASE =
            " CREATE TABLE IF NOT EXISTS "+TABLE_ENVASE+" ("+
                    TABLE_ENVASE_COL_ID       +" "+TABLE_ENVASE_TYPECOL_ID+" PRIMARY KEY , "+
                    TABLE_ENVASE_COL_NAME +" "+ TABLE_ENVASE_TYPECOL_NAME +" , "+
                    TABLE_ENVASE_COL_PESONETO+" "+TABLE_ENVASE_TYPECOL_PESONETO+", "+
                    TABLE_ENVASE_COL_PESOTARA+" "+TABLE_ENVASE_TYPECOL_PESOTARA+", "+
                    TABLE_ENVASE_COL_IDPROCESO+" "+TABLE_ENVASE_TYPECOL_IDPROCESO+" "+
                    ")";

    public static final String CREATE_TABLE_PLANTA =
            " CREATE TABLE IF NOT EXISTS "+TABLE_PLANTA+" ("+
                    TABLE_PLANTA_COL_ID   +" "+TABLE_PLANTA_TYPECOL_ID+" PRIMARY KEY , "+
                    TABLE_PLANTA_COL_NAME +" "+ TABLE_PLANTA_TYPECOL_NAME +"  "+
                    ")";



    public static final String CREATE_TABLE_TIPOCALIBRE =
            " CREATE TABLE IF NOT EXISTS "+TABLE_TIPOCALIBRE+" ("+
                    TABLE_TIPOCALIBRE_COL_ID   +" "+TABLE_TIPOCALIBRE_TYPECOL_ID+" PRIMARY KEY , "+
                    TABLE_TIPOCALIBRE_COL_NAME +" "+ TABLE_TIPOCALIBRE_TYPECOL_NAME +"  "+
                    ")";


    public static final String CREATE_TABLE_EVALUACIONDETALLE =
            " CREATE TABLE IF NOT EXISTS "+TABLE_EVALUACIONDETALLE+" ("+
                    TABLE_EVALUACIONDETALLE_COL_ID       +" "+TABLE_EVALUACIONDETALLE_TYPECOL_ID+" PRIMARY KEY , "+
                    TABLE_EVALUACIONDETALLE_COL_IDMUESTRA +" "+ TABLE_EVALUACIONDETALLE_TYPECOL_IDMUESTRA +" , "+
                    TABLE_EVALUACIONDETALLE_COL_IDEVALUACION+" "+TABLE_EVALUACIONDETALLE_TYPECOL_IDEVALUACION+" "+
                    ")";

    public static final String CREATE_TABLE_TIPOPROCESO =
            " CREATE TABLE IF NOT EXISTS "+TABLE_TIPOPROCESO+" ("+
                    TABLE_TIPOPROCESO_COL_ID       +" "+TABLE_TIPOPROCESO_TYPECOL_ID+" PRIMARY KEY , "+
                    TABLE_TIPOPROCESO_COL_NAME +" "+ TABLE_TIPOPROCESO_TYPECOL_NAME +"  "+
                    ")";

    public static final String CREATE_TABLE_PLANTACULTIVO =
            " CREATE TABLE IF NOT EXISTS "+TABLE_PLANTACULTIVO+" ("+
                    TABLE_PLANTACULTIVO_COL_ID       +" "+TABLE_PLANTACULTIVO_TYPECOL_ID+" PRIMARY KEY , "+
                    TABLE_PLANTACULTIVO_COL_IDPLANTA +" "+ TABLE_PLANTACULTIVO_TYPECOL_IDPLANTA +" , "+
                    TABLE_PLANTACULTIVO_COL_IDCULTIVO+" "+TABLE_PLANTACULTIVO_TYPECOL_IDCULTIVO+" "+
                    ")";

    public static final String CREATE_TABLE_CALIBRE =
            " CREATE TABLE IF NOT EXISTS "+TABLE_CALIBRE+" ("+
                    TABLE_CALIBRE_COL_ID       +" "+TABLE_CALIBRE_TYPECOL_ID+" PRIMARY KEY , "+
                    TABLE_CALIBRE_COL_NAME +" "+ TABLE_CALIBRE_TYPECOL_NAME +" , "+
                    TABLE_CALIBRE_COL_PESOMIN+" "+TABLE_CALIBRE_TYPECOL_PESOMIN+", "+
                    TABLE_CALIBRE_COL_PESOMAX+" "+TABLE_CALIBRE_TYPECOL_PESOMAX+", "+
                    TABLE_CALIBRE_COL_IDTIPOCALIBRE +" "+ TABLE_CALIBRE_TYPECOL_IDTIPOCALIBRE +" "+
                    ")";

    public static final String CREATE_TABLE_DESTINO =
            " CREATE TABLE IF NOT EXISTS "+TABLE_DESTINO+" ("+
                    TABLE_DESTINO_COL_ID       +" "+TABLE_DESTINO_TYPECOL_ID+" PRIMARY KEY , "+
                    TABLE_DESTINO_COL_NAME +" "+ TABLE_DESTINO_TYPECOL_NAME +"  "+
                    ")";

    public static final String CREATE_TABLE_CATEGORIA =
            " CREATE TABLE IF NOT EXISTS "+TABLE_CATEGORIA+" ("+
                    TABLE_CATEGORIA_COL_ID       +" "+TABLE_CATEGORIA_TYPECOL_ID+" PRIMARY KEY , "+
                    TABLE_CATEGORIA_COL_NAME +" "+ TABLE_CATEGORIA_TYPECOL_NAME +"  "+
                    ")";

    public static final String CREATE_TABLE_CRITERIO =
            " CREATE TABLE IF NOT EXISTS "+TABLE_CRITERIO+" ("+
                    TABLE_CRITERIO_COL_ID       +" "+TABLE_CRITERIO_TYPECOL_ID+" PRIMARY KEY AUTOINCREMENT, "+
                    TABLE_CRITERIO_COL_CODIGO+" "+TABLE_CRITERIO_TYPECOL_CODIGO+", "+
                    TABLE_CRITERIO_COL_NAME +" "+ TABLE_CRITERIO_TYPECOL_NAME +", "+
                    TABLE_CRITERIO_COL_TIPODATO+" "+TABLE_CRITERIO_TYPECOL_TIPODATO+", "+
                    TABLE_CRITERIO_COL_ISPHOTO+" "+TABLE_CRITERIO_TYPECOL_ISPHOTO+", "+
                    TABLE_CRITERIO_COL_IDEVALUACION+" "+TABLE_CRITERIO_TYPECOL_IDEVALUACION+", "+
                    TABLE_CRITERIO_COL_TOLERANCIAMINIMO+" "+TABLE_CRITERIO_TYPECOL_TOLERANCIAMINIMO+", "+
                    TABLE_CRITERIO_COL_TOLERANCIAMAXIMO +" "+ TABLE_CRITERIO_TYPECOL_TOLERANCIAMAXIMO +", "+
                    TABLE_CRITERIO_COL_IDTIPOPROCESO +" "+ TABLE_CRITERIO_TYPECOL_IDTIPOPROCESO +" "+
                    ")";
    
    public static final String CREATE_TABLE_CONFEVALUACION =
            " CREATE TABLE IF NOT EXISTS "+TABLE_CONFEVALUACION+" ("+
                    TABLE_CONFEVALUACION_COL_ID       +" "+TABLE_CONFEVALUACION_TYPECOL_ID+" PRIMARY KEY , "+
                    TABLE_CONFEVALUACION_COL_IDEVALUACION +" "+ TABLE_CONFEVALUACION_TYPECOL_IDEVALUACION +" NOT NULL, "+
                    TABLE_CONFEVALUACION_COL_IDTIPOPROCESO+" "+TABLE_CONFEVALUACION_TYPECOL_IDTIPOPROCESO+" NOT NULL "+
                    ")";

    public static final String CREATE_TABLE_MUESTRA =
            " CREATE TABLE IF NOT EXISTS "+TABLE_MUESTRA+" ("+
                    TABLE_MUESTRA_COL_ID       +" "+TABLE_MUESTRA_TYPECOL_ID       +" PRIMARY KEY AUTOINCREMENT, "+
                    TABLE_MUESTRA_COL_IDPROCESO +" "+ TABLE_MUESTRA_TYPECOL_IDPROCESO +" NOT NULL, "+
                    TABLE_MUESTRA_COL_IDTIPOPROCESO+" "+TABLE_MUESTRA_TYPECOL_IDTIPOPROCESO+" NOT NULL, "+
                    TABLE_MUESTRA_COL_FECHAHORA+" "+TABLE_MUESTRA_TYPECOL_FECHAHORA+" DEFAULT (datetime('now','localtime')), "+
                    TABLE_MUESTRA_COL_CANTIDAD+" "+TABLE_MUESTRA_TYPECOL_CANTIDAD+", "+
                    TABLE_MUESTRA_COL_COMENTARIO+" "+TABLE_MUESTRA_TYPECOL_COMENTARIO+" "+
                    ")";

    public static final String CREATE_TABLE_CRITERIODETALLE =
            " CREATE TABLE IF NOT EXISTS "+TABLE_CRITERIODETALLE+" ("+
                    TABLE_CRITERIODETALLE_COL_ID       +" "+TABLE_CRITERIODETALLE_TYPECOL_ID       +" PRIMARY KEY AUTOINCREMENT, "+
                    TABLE_CRITERIODETALLE_COL_IDEVALAUCIONDETALLE +" "+ TABLE_CRITERIODETALLE_TYPECOL_IDEVALAUCIONDETALLE +" NOT NULL, "+
                    TABLE_CRITERIODETALLE_COL_IDCRITERIO+" "+TABLE_CRITERIODETALLE_TYPECOL_IDCRITERIO+" NOT NULL, "+
                    TABLE_CRITERIODETALLE_COL_VALOR+" "+TABLE_CRITERIODETALLE_TYPECOL_VALOR+" "+
                    ")";

    public static final String CREATE_TABLE_FOTO =
            " CREATE TABLE IF NOT EXISTS "+TABLE_FOTO+" ("+
                    TABLE_FOTO_COL_ID       +" "+TABLE_FOTO_TYPECOL_ID       +" PRIMARY KEY AUTOINCREMENT, "+
                    TABLE_FOTO_COL_PATH +" "+ TABLE_FOTO_TYPECOL_PATH +", "+
                    TABLE_FOTO_COL_HORAFECHA+" "+TABLE_FOTO_TYPECOL_HORAFECHA+" DEFAULT (datetime('now','localtime')), "+
                    TABLE_FOTO_COL_IMAGEN+" "+TABLE_FOTO_TYPECOL_IMAGEN+", "+
                    TABLE_FOTO_COL_IDCRITERIODETALLE+" "+TABLE_FOTO_TYPECOL_IDCRITERIODETALLE+" NOT NULL "+
            ")";


    public static final String CREATE_TABLE_EVALUACION=
            " CREATE TABLE IF NOT EXISTS "+TABLE_EVALUACION+" ("+
                    TABLE_EVALUACION_COL_ID    +" "+TABLE_EVALUACION_TYPECOL_ID+" PRIMARY KEY , "+
                    TABLE_EVALUACION_COL_NAME  +" "+TABLE_EVALUACION_TYPECOL_NAME+", "+
                    TABLE_EVALUACION_COL_ISMATSEC  +" "+TABLE_EVALUACION_TYPECOL_ISMATSEC+",  "+
                    TABLE_EVALUACION_COL_CULTIVO  +" "+TABLE_EVALUACION_TYPECOL_CULTIVO+"  "+
            ")";


    public static final String CREATE_TABLE_RECEPCION=
            " CREATE TABLE IF NOT EXISTS "+TABLE_RECEPCION+" (" +
                    TABLE_RECEPCION_COL_ID           +" "+TABLE_RECEPCION_TYPECOL_ID+" PRIMARY KEY AUTOINCREMENT, " +
                    TABLE_RECEPCION_COL_IDPLANTA     +" "+TABLE_RECEPCION_TYPECOL_IDPLANTA+", "+
                    TABLE_RECEPCION_COL_IDFUNDO      +" "+TABLE_RECEPCION_TYPECOL_IDFUNDO+", "+
                    TABLE_RECEPCION_COL_IDVARIEDAD   +" "+TABLE_RECEPCION_TYPECOL_IDVARIEDAD+", "+
                    TABLE_RECEPCION_COL_IDENVASE +" "+TABLE_RECEPCION_TYPECOL_IDTIPOENVASE+", "+
                    TABLE_RECEPCION_COL_FECHAHORA    +" "+TABLE_RECEPCION_TYPECOL_FECHAHORA +" DEFAULT (datetime('now','localtime')), "+
                    TABLE_RECEPCION_COL_EDITING      +" "+TABLE_RECEPCION_TYPECOL_EDITING   +", "+
                    TABLE_RECEPCION_COL_NORDENPROCESO+" "+TABLE_RECEPCION_TYPECOL_NORDENPROCESO+", "+
                    TABLE_RECEPCION_COL_NGUIA        +" "+TABLE_RECEPCION_TYPECOL_NGUIA+", "+
                    TABLE_RECEPCION_COL_UNIDADES     +" "+TABLE_RECEPCION_TYPECOL_UNIDADES+", "+
                    TABLE_RECEPCION_COL_KILOS        +" "+TABLE_RECEPCION_TYPECOL_KILOS+
            ")";




    public static final String CREATE_TABLE_PRODUCCION=
            " CREATE TABLE IF NOT EXISTS "+TABLE_PRODUCCION+" (" +
                    TABLE_PRODUCCION_COL_ID           +" "+TABLE_PRODUCCION_TYPECOL_ID+" PRIMARY KEY AUTOINCREMENT, " +
                    TABLE_PRODUCCION_COL_IDRECEPCION           +" "+TABLE_PRODUCCION_TYPECOL_IDRECEPCION+", " +
                    TABLE_PRODUCCION_COL_IDPLANTA     +" "+TABLE_PRODUCCION_TYPECOL_IDPLANTA+", "+
                    TABLE_PRODUCCION_COL_IDFUNDO      +" "+TABLE_PRODUCCION_TYPECOL_IDFUNDO+", "+
                    TABLE_PRODUCCION_COL_IDVARIEDAD   +" "+TABLE_PRODUCCION_TYPECOL_IDVARIEDAD+", "+
                    TABLE_PRODUCCION_COL_IDENVASE     +" "+TABLE_PRODUCCION_TYPECOL_IDENVASE+", "+
                    TABLE_PRODUCCION_COL_IDDESTINO    +" "+TABLE_PRODUCCION_TYPECOL_IDDESTINO+", "+
                    TABLE_PRODUCCION_COL_IDCATEGORIA  +" "+TABLE_PRODUCCION_TYPECOL_IDCATEGORIA+", "+
                    TABLE_PRODUCCION_COL_IDCALIBRE  +" "+TABLE_PRODUCCION_TYPECOL_IDCALIBRE+", "+
                    TABLE_PRODUCCION_COL_FECHAHORA    +" "+TABLE_PRODUCCION_TYPECOL_FECHAHORA +" DEFAULT (datetime('now','localtime')), "+
                    TABLE_PRODUCCION_COL_EDITING      +" "+TABLE_PRODUCCION_TYPECOL_EDITING   +", "+
                    TABLE_PRODUCCION_COL_NORDENPROCESO+" "+TABLE_PRODUCCION_TYPECOL_NORDENPROCESO+", "+
                    TABLE_PRODUCCION_COL_UNIDADES     +" "+TABLE_PRODUCCION_TYPECOL_UNIDADES+", "+
                    TABLE_PRODUCCION_COL_KILOS        +" "+TABLE_PRODUCCION_TYPECOL_KILOS+
                    ")";



    public static final String CREATE_TABLE_DESPACHO=
            " CREATE TABLE IF NOT EXISTS "+TABLE_DESPACHO+" (" +
                    TABLE_DESPACHO_COL_ID           +" "+TABLE_DESPACHO_TYPECOL_ID+" PRIMARY KEY AUTOINCREMENT, " +
                    TABLE_DESPACHO_COL_IDPLANTA     +" "+TABLE_DESPACHO_TYPECOL_IDPLANTA+", "+
                    TABLE_DESPACHO_COL_IDCULTIVO +" "+ TABLE_DESPACHO_TYPECOL_IDCULTIVO +", "+
                    TABLE_DESPACHO_COL_FECHAHORA    +" "+TABLE_DESPACHO_TYPECOL_FECHAHORA +" DEFAULT (datetime('now','localtime')), "+
                    TABLE_DESPACHO_COL_EDITING      +" "+TABLE_DESPACHO_TYPECOL_EDITING   +", "+

                    TABLE_DESPACHO_COL_ORIGEN+" "+TABLE_DESPACHO_TYPECOL_ORIGEN+", "+
                    TABLE_DESPACHO_COL_GGN+" "+TABLE_DESPACHO_TYPECOL_GGN+", "+
                    TABLE_DESPACHO_COL_NRESERVA+" "+TABLE_DESPACHO_TYPECOL_NRESERVA+", "+
                    TABLE_DESPACHO_COL_TRANSPORTMODEL+" "+TABLE_DESPACHO_TYPECOL_TRANSPORTMODEL+", "+
                    TABLE_DESPACHO_COL_NCONTENEDOR+" "+TABLE_DESPACHO_TYPECOL_NCONTENEDOR+", "+

                    //TABLE_DESPACHO_COL_FECHAINSPECCION+" "+TABLE_DESPACHO_TYPECOL_FECHAINSPECCION+" DEFAULT (date('now','localtime')), "
                    TABLE_DESPACHO_COL_FECHAINSPECCION+" "+TABLE_DESPACHO_TYPECOL_FECHAINSPECCION+" DEFAULT (date('now')), "+
                    TABLE_DESPACHO_COL_FECHACARGA+" "+TABLE_DESPACHO_TYPECOL_FECHACARGA+", "+
                    TABLE_DESPACHO_COL_FECHASALIDA+" "+TABLE_DESPACHO_TYPECOL_FECHASALIDA+", "+
                    TABLE_DESPACHO_COL_FECHALLEGADA+" "+TABLE_DESPACHO_TYPECOL_FECHALLEGADA+", "+

                    TABLE_DESPACHO_COL_TEMPERATURA+" "+TABLE_DESPACHO_TYPECOL_TEMPERATURA+", "+
                    TABLE_DESPACHO_COL_DIOXIDOCARBONO+" "+TABLE_DESPACHO_TYPECOL_DIOXIDOCARBONO+", "+
                    TABLE_DESPACHO_COL_OXIGENO+" "+TABLE_DESPACHO_TYPECOL_OXIGENO+", "+
                    TABLE_DESPACHO_COL_TECNOLOGIA+" "+TABLE_DESPACHO_TYPECOL_TECNOLOGIA+", "+

                    TABLE_DESPACHO_COL_TERMOGRAFO1+" "+TABLE_DESPACHO_TYPECOL_TERMOGRAFO1+", "+
                    TABLE_DESPACHO_COL_TERMOGRAFO2+" "+TABLE_DESPACHO_TYPECOL_TERMOGRAFO2+", "+
                    TABLE_DESPACHO_COL_TERMOGRAFO3+" "+TABLE_DESPACHO_TYPECOL_TERMOGRAFO3+", "+

                    TABLE_DESPACHO_COL_NAMECONTROLADOR+" "+TABLE_DESPACHO_TYPECOL_NAMECONTROLADOR+", "+
                    TABLE_DESPACHO_COL_NPALLETS+" "+TABLE_DESPACHO_TYPECOL_NPALLETS+", "+
                    TABLE_DESPACHO_COL_CAJASPALLET+" "+TABLE_DESPACHO_TYPECOL_CAJASPALLET+", "+
                    TABLE_DESPACHO_COL_PESOCAJA+" "+TABLE_DESPACHO_TYPECOL_PESOCAJA+", "+
                    TABLE_DESPACHO_COL_PREFRIO+" "+TABLE_DESPACHO_TYPECOL_PREFRIO+", "+
                    TABLE_DESPACHO_COL_CLIENTE+" "+TABLE_DESPACHO_TYPECOL_CLIENTE+", "+
                    TABLE_DESPACHO_COL_PL+" "+TABLE_DESPACHO_TYPECOL_PL+", "+
                    TABLE_DESPACHO_COL_RG+" "+TABLE_DESPACHO_TYPECOL_RG+", "+
                    TABLE_DESPACHO_COL_TP+" "+TABLE_DESPACHO_TYPECOL_TP+
                    ")";

    public static final String CREATE_TABLE_DESCARTE=
            " CREATE TABLE IF NOT EXISTS "+TABLE_DESCARTE+" (" +
                    TABLE_DESCARTE_COL_ID           +" "+TABLE_DESCARTE_TYPECOL_ID+" PRIMARY KEY AUTOINCREMENT, " +
                    TABLE_DESCARTE_COL_IDRECEPCION  +" "+TABLE_DESCARTE_TYPECOL_IDRECEPCION+", " +
                    TABLE_DESCARTE_COL_IDPLANTA     +" "+TABLE_DESCARTE_TYPECOL_IDPLANTA+", "+
                    TABLE_DESCARTE_COL_IDFUNDO      +" "+TABLE_DESCARTE_TYPECOL_IDFUNDO+", "+
                    TABLE_DESCARTE_COL_IDVARIEDAD   +" "+TABLE_DESCARTE_TYPECOL_IDVARIEDAD+", "+
                    TABLE_DESCARTE_COL_IDENVASE     +" "+TABLE_DESCARTE_TYPECOL_IDENVASE+", "+
                    TABLE_DESCARTE_COL_FECHAHORA    +" "+TABLE_DESCARTE_TYPECOL_FECHAHORA +" DEFAULT (datetime('now','localtime')), "+
                    TABLE_DESCARTE_COL_EDITING      +" "+TABLE_DESCARTE_TYPECOL_EDITING   +", "+
                    TABLE_DESCARTE_COL_NORDENPROCESO+" "+TABLE_DESCARTE_TYPECOL_NORDENPROCESO+", "+
                    TABLE_DESCARTE_COL_UNIDADES     +" "+TABLE_DESCARTE_TYPECOL_UNIDADES+", "+
                    TABLE_DESCARTE_COL_KILOS        +" "+TABLE_DESCARTE_TYPECOL_KILOS+
                    ")";


    public static final String CREATE_TABLE_VARIEDAD =
            " CREATE TABLE IF NOT EXISTS "+TABLE_VARIEDAD+" (" +
                    TABLE_VARIEDAD_COL_ID        +" "+TABLE_VARIEDAD_TYPECOL_ID+" PRIMARY KEY," +
                    TABLE_VARIEDAD_COL_NAME      +" "+TABLE_VARIEDAD_TYPECOL_NAME+","+
                    TABLE_VARIEDAD_COL_IDCULTIVO +" "+TABLE_VARIEDAD_TYPECOL_IDCULTIVO+
            ")";

    public static final String CREATE_TABLE_CULTIVO =
            " CREATE TABLE IF NOT EXISTS "+TABLE_CULTIVO+" (" +
                    TABLE_CULTIVO_COL_ID        +" "+TABLE_CULTIVO_TYPECOL_ID+" PRIMARY KEY," +
                    TABLE_CULTIVO_COL_NAME      +" "+TABLE_CULTIVO_TYPECOL_NAME+
            ")";

    public static final String CREATE_TABLE_FUNDO =
            " CREATE TABLE IF NOT EXISTS "+TABLE_FUNDO+" (" +
                    TABLE_FUNDO_COL_ID        +" "+TABLE_FUNDO_TYPECOL_ID+" PRIMARY KEY," +
                    TABLE_FUNDO_COL_NAME      +" "+TABLE_FUNDO_TYPECOL_NAME+","+
                    TABLE_FUNDO_COL_IDEMPRESA +" "+TABLE_FUNDO_TYPECOL_IDEMPRESA+
            ")";

    public static final String CREATE_TABLE_EMPRESA =
            " CREATE TABLE IF NOT EXISTS "+TABLE_EMPRESA+" (" +
                    TABLE_EMPRESA_COL_ID        +" "+TABLE_EMPRESA_TYPECOL_ID+" PRIMARY KEY," +
                    TABLE_EMPRESA_COL_NAME      +" "+TABLE_EMPRESA_TYPECOL_NAME+", "+
                    TABLE_EMPRESA_COL_IDZONA      +" "+TABLE_EMPRESA_TYPECOL_IDZONA+
                    ")";
    public static final String CREATE_TABLE_ZONA =
            " CREATE TABLE IF NOT EXISTS "+TABLE_ZONA+" (" +
                    TABLE_ZONA_COL_ID        +" "+TABLE_ZONA_TYPECOL_ID+" PRIMARY KEY," +
                    TABLE_ZONA_COL_NAME      +" "+TABLE_ZONA_TYPECOL_NAME+" "+
                    ")";


}
