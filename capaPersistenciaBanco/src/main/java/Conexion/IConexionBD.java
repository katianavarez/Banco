/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import Excepciones.PersistenciaException;
import java.sql.Connection;

/**
 *
 * @author katia
 */
public interface IConexionBD {
    public Connection crearConexion() throws PersistenciaException;
}
