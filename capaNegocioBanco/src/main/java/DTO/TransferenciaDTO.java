/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDateTime;

/**
 *
 * @author katia
 */
public class TransferenciaDTO {
    private int idTransaccion;
    private double monto;
    private LocalDateTime fechaHora;
    private int idCuentaOrigen;
    private int idCuentaDestino;

    public TransferenciaDTO() {}

    public TransferenciaDTO(int idTransaccion, double monto, LocalDateTime fechaHora, int idCuentaOrigen, int idCuentaDestino) {
        this.idTransaccion = idTransaccion;
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.idCuentaOrigen = idCuentaOrigen;
        this.idCuentaDestino = idCuentaDestino;
    }

    public TransferenciaDTO(double monto, LocalDateTime fechaHora, int idCuentaOrigen, int idCuentaDestino) {
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.idCuentaOrigen = idCuentaOrigen;
        this.idCuentaDestino = idCuentaDestino;
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

    public int getIdCuentaDestino() {
        return idCuentaDestino;
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

    public void setIdCuentaDestino(int idCuentaDestino) {
        this.idCuentaDestino = idCuentaDestino;
    }

    @Override
    public String toString() {
        return "TransferenciaDTO{" + "idTransaccion=" + idTransaccion + ", monto=" + monto + ", fechaHora=" + fechaHora + ", idCuentaOrigen=" + idCuentaOrigen + ", idCuentaDestino=" + idCuentaDestino + '}';
    }
}
