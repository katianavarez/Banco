/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configuracion;

import BO.ClienteBO;
import BO.CuentaBO;
import BO.RetiroBO;
import BO.TransaccionBO;
import BO.TransferenciaBO;
import Conexion.ConexionBD;
import DAO.ClienteDAO;
import DAO.CuentaDAO;
import DAO.IClienteDAO;
import DAO.ICuentaDAO;
import DAO.IRetiroDAO;
import DAO.ITransaccionDAO;
import DAO.ITransferenciaDAO;
import DAO.RetiroDAO;
import DAO.TransaccionDAO;
import DAO.TransferenciaDAO;

/**
 *
 * @author katia
 */
public class DependencyInjector {
    private static final ConexionBD conexion = new ConexionBD();
    
    public static ClienteBO getClienteBO() {
        IClienteDAO clienteDAO = new ClienteDAO(conexion);
        return new ClienteBO(clienteDAO);
    }
    
    public static CuentaBO getCuentaBO() {
        ICuentaDAO cuentaDAO = new CuentaDAO(conexion);
        return new CuentaBO(cuentaDAO);
    }

    public static RetiroBO getRetiroBO() {
        IRetiroDAO retiroDAO = new RetiroDAO(conexion);
        ICuentaDAO cuentaDAO = new CuentaDAO(conexion);
        return new RetiroBO(retiroDAO, cuentaDAO);
    }

    public static TransferenciaBO getTransferenciaBO() {
        ITransferenciaDAO transferenciaDAO = new TransferenciaDAO(conexion);
        ICuentaDAO cuentaDAO = new CuentaDAO(conexion);
        return new TransferenciaBO(transferenciaDAO, cuentaDAO);
    }

    public static TransaccionBO getTransaccionBO() {
        ITransaccionDAO transaccionDAO = new TransaccionDAO(conexion);
        return new TransaccionBO(transaccionDAO);
    }
}
