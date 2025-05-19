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
public class Transferencia extends Transaccion{
    private int idCuentaDestino;

    public Transferencia() {
    }

    public Transferencia(int idCuentaDestino) {
        this.idCuentaDestino = idCuentaDestino;
    }

    public Transferencia(int idCuentaDestino, int idTransaccion, double monto, LocalDateTime fechaHora, int idCuentaOrigen) {
        super(idTransaccion, monto, fechaHora, idCuentaOrigen);
        this.idCuentaDestino = idCuentaDestino;
    }

    public Transferencia(int idCuentaDestino, double monto, LocalDateTime fechaHora, int idCuentaOrigen) {
        super(monto, fechaHora, idCuentaOrigen);
        this.idCuentaDestino = idCuentaDestino;
    }

    public int getIdCuentaDestino() {
        return idCuentaDestino;
    }

    public void setIdCuentaDestino(int idCuentaDestino) {
        this.idCuentaDestino = idCuentaDestino;
    }

//    @Override
//    public String toString() {
//        return "Transferencia{" + "idCuentaDestino=" + idCuentaDestino + '}';
//    }
//    
    @Override
    public String toString() {
        return "Transferencia{" +
               "idTransaccion=" + getIdTransaccion() +
               ", monto=" + getMonto() +
               ", fechaHora=" + getFechaHora() +
               ", idCuentaOrigen=" + getIdCuentaOrigen() +
               ", idCuentaDestino=" + idCuentaDestino +
               '}';
    }
}
