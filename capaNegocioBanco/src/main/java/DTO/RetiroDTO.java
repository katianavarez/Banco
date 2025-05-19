/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entidades.EstadoRetiro;
import java.time.LocalDateTime;

/**
 *
 * @author katia
 */
public class RetiroDTO {
    private int idTransaccion;
    private double monto;
    private LocalDateTime fechaHora;
    private int idCuentaOrigen;
    private int folio;
    private String contraseña;
    private EstadoRetiro estado;

    public RetiroDTO() {}

    public RetiroDTO(int idTransaccion, double monto, LocalDateTime fechaHora, int idCuentaOrigen,
            int folio, String contraseña, EstadoRetiro estado) {
        this.idTransaccion = idTransaccion;
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.idCuentaOrigen = idCuentaOrigen;
        this.folio = folio;
        this.contraseña = contraseña;
        this.estado = estado;
    }

    public RetiroDTO(double monto, LocalDateTime fechaHora, int idCuentaOrigen, int folio, String contraseña, EstadoRetiro estado) {
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.idCuentaOrigen = idCuentaOrigen;
        this.folio = folio;
        this.contraseña = contraseña;
        this.estado = estado;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public int getIdCuentaOrigen() {
        return idCuentaOrigen;
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

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setIdCuentaOrigen(int idCuentaOrigen) {
        this.idCuentaOrigen = idCuentaOrigen;
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
        return "RetiroDTO{" + "idTransaccion=" + idTransaccion + ", monto=" + monto + ", fechaHora=" + fechaHora + ", idCuentaOrigen=" + idCuentaOrigen + ", folio=" + folio + ", contrase\u00f1a=" + contraseña + ", estado=" + estado + '}';
    }
}
