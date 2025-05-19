/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDate;

/**
 *
 * @author katia
 */
public class Cuenta {
    private int idCuenta;
    private long numCuenta;
    private LocalDate fechaApertura;
    private double saldo;
    private int idCliente;
    private EstadoCuenta estado;

    public Cuenta() {
    }

    public Cuenta(int idCuenta, long numCuenta, LocalDate fechaApertura, double saldo, int idCliente, EstadoCuenta estado) {
        this.idCuenta = idCuenta;
        this.numCuenta = numCuenta;
        this.fechaApertura = fechaApertura;
        this.saldo = saldo;
        this.idCliente = idCliente;
        this.estado = estado;
    }

    public Cuenta(long numCuenta, LocalDate fechaApertura, double saldo, int idCliente, EstadoCuenta estado) {
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

    public EstadoCuenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuenta estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Cuenta{" + "idCuenta=" + idCuenta + ", numCuenta=" + numCuenta + ", fechaApertura=" + fechaApertura + ", saldo=" + saldo + ", idCliente=" + idCliente + ", estado=" + estado + '}';
    }
}
