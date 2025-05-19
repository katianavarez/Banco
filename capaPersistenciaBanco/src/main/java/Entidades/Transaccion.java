/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDateTime;

/**
 *
 * @author katia
 */
public class Transaccion {
    private int idTransaccion;
    private double monto;
    private LocalDateTime fechaHora;
    private int idCuentaOrigen;

    public Transaccion() {
    }

    public Transaccion(int idTransaccion, double monto, LocalDateTime fechaHora, int idCuentaOrigen) {
        this.idTransaccion = idTransaccion;
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.idCuentaOrigen = idCuentaOrigen;
    }

    public Transaccion(double monto, LocalDateTime fechaHora, int idCuentaOrigen) {
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.idCuentaOrigen = idCuentaOrigen;
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

    @Override
    public String toString() {
        return "Transaccion{" + "idTransaccion=" + idTransaccion + ", monto=" + monto + ", fechaHora=" + fechaHora + ", idCuentaOrigen=" + idCuentaOrigen + '}';
    }
    
    
}
