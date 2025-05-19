/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDate;

/**
 *
 * @author katia
 */
public class CuentaDTO {
    private int idCuenta;
    private long numCuenta;
    private LocalDate fechaApertura;
    private double saldo;
    private int idCliente;
    private String estado;

    public CuentaDTO() {}

    public CuentaDTO(int idCuenta, long numCuenta, LocalDate fechaApertura, double saldo, int idCliente, String estado) {
        this.idCuenta = idCuenta;
        this.numCuenta = numCuenta;
        this.fechaApertura = fechaApertura;
        this.saldo = saldo;
        this.idCliente = idCliente;
        this.estado = estado;
    }

    public CuentaDTO(long numCuenta, LocalDate fechaApertura, double saldo, int idCliente, String estado) {
        this.numCuenta = numCuenta;
        this.fechaApertura = fechaApertura;
        this.saldo = saldo;
        this.idCliente = idCliente;
        this.estado = estado;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public long getNumCuenta() {
        return numCuenta;
    }

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public void setNumCuenta(long numCuenta) {
        this.numCuenta = numCuenta;
    }

    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "CuentaDTO{" + "idCuenta=" + idCuenta + ", numCuenta=" + numCuenta + ", fechaApertura=" + fechaApertura + ", saldo=" + saldo + ", idCliente=" + idCliente + ", estado=" + estado + '}';
    }
}
