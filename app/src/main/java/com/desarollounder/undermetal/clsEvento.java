package com.desarollounder.undermetal;

/**
 * Created by filipp on 9/16/2016.
 */
public class clsEvento {

    private int id;
    private String descripcionEvento, imagenEvento, fechaEvento;



    public clsEvento(int id, String descripcionEvento, String fechaEvento, String imagenEvento) {
        this.id = id;
        this.descripcionEvento = descripcionEvento;
        this.imagenEvento = imagenEvento;
        this.fechaEvento = fechaEvento;

   }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public String getImagenEvento() {
        return imagenEvento;
    }

    public void setImagenEvento(String imagenEvento) {
        this.imagenEvento = imagenEvento;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }
}
