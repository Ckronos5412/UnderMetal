package com.desarollounder.undermetal;

/**
 * Created by filipp on 9/16/2016.
 */
public class clsLocal {

    private int idLocal;
    private String nombreLocal, imagenLocal, latLocal, lonLocal, dirLocal;

    public clsLocal(int idLocal, String nombreLocal, String imagenLocal, String latLocal, String lonLocal, String dirLocal) {
        this.idLocal = idLocal;
        this.nombreLocal = nombreLocal;
        this.imagenLocal = imagenLocal;
        this.latLocal = latLocal;
        this.lonLocal = lonLocal;
        this.dirLocal = dirLocal;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public String getImagenLocal() {
        return imagenLocal;
    }

    public void setImagenLocal(String imagenLocal) {
        this.imagenLocal = imagenLocal;
    }

    public String getLatLocal() {
        return latLocal;
    }

    public void setLatLocal(String latLocal) {
        this.latLocal = latLocal;
    }

    public String getLonLocal() {
        return lonLocal;
    }

    public void setLonLocal(String lonLocal) {
        this.lonLocal = lonLocal;
    }

    public String getDirLocal() {
        return dirLocal;
    }

    public void setDirLocal(String dirLocal) {
        this.dirLocal = dirLocal;
    }
}
