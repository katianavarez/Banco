/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author katia
 */
public class Retiro extends Transaccion{
    private int folio;
    private String contraseña;
    private EstadoRetiro estado;

    public Retiro() {
    }

    public Retiro(int folio, String contraseña, EstadoRetiro estado, int idTransaccion, double monto, LocalDateTime fechaHora, int idCuentaOrigen) {
        super(idTransaccion, monto, fechaHora, idCuentaOrigen);
        this.folio = folio;
        this.contraseña = contraseña;
        this.estado = estado;
    }

    public Retiro(int folio, String contraseña, EstadoRetiro estado, double monto, LocalDateTime fechaHora, int idCuentaOrigen) {
        super(monto, fechaHora, idCuentaOrigen);
        this.folio = folio;
        this.contraseña = contraseña;
        this.estado = estado;
    }

    public int getFolio() {
        return folio;
    }

    public String getContraseña() {
        return contraseña;
    }

    public EstadoRetiro getEstado() {
        return estado;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setEstado(EstadoRetiro estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Retiro{" +
               "idTransaccion=" + getIdTransaccion() +
               ", monto=" + getMonto() +
               ", fechaHora=" + getFechaHora() +
               ", idCuentaOrigen=" + getIdCuentaOrigen() +
               ", folio=" + folio +
               ", contraseña='" + contraseña + '\'' +
               ", estado=" + estado +
               '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.folio;
        hash = 97 * hash + Objects.hashCode(this.contraseña);
        hash = 97 * hash + Objects.hashCode(this.estado);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Retiro other = (Retiro) obj;
        if (this.folio != other.folio) {
            return false;
        }
        if (!Objects.equals(this.contraseña, other.contraseña)) {
            return false;
        }
        return this.estado == other.estado;
    }
}
