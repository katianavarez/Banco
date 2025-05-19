/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package org.capapresentacionbanco;

import BO.CuentaBO;
import BO.TransferenciaBO;
import Configuracion.DependencyInjector;
import DAO.CuentaDAO;
import DTO.CuentaDTO;
import DTO.TransferenciaDTO;
import Exception.NegocioException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author katia
 */
public class PantallaTransferencia extends javax.swing.JFrame {
    
    private JLabel lblTitulo, lblCuentaDestino, lblMonto;
    private JTextField txtCuentaDestino, txtMonto;
    private JButton btnAceptar, btnAtras;
    
    private int idCuentaOrigen;
    private CuentaDTO cuentaOrigenDTO;

    /**
     * Creates new form PantallaTransferencia
     */
    public PantallaTransferencia() {
        configurarVentana();
        initComponents2();
    }
    
    public void cargarConCuentaOrigen(int idCuenta) {
        this.idCuentaOrigen = idCuenta;
        try{
            CuentaBO cuentaBO = DependencyInjector.getCuentaBO();
            this.cuentaOrigenDTO = cuentaBO.obtenerPorId(idCuenta);
        } catch (NegocioException e){
            JOptionPane.showMessageDialog(this, "Error al cargar cuenta origen: " + e.getMessage());
            volver();
        }
    }
    
    private void configurarVentana() {
        setTitle("Transferencia");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    private void initComponents2() {
        Font fuenteCampos = new Font("SansSerif", Font.PLAIN, 18);
        Dimension dimensionCampo = new Dimension(300, 35);
        Dimension dimensionBoton = new Dimension(140, 35);

        lblTitulo = new JLabel("Transferencia", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 48));

        lblCuentaDestino = new JLabel("Cuenta Destino");
        lblMonto = new JLabel("Monto");

        JLabel[] etiquetas = {lblCuentaDestino, lblMonto};
        for (JLabel lbl : etiquetas) {
            lbl.setFont(fuenteCampos);
        }

        txtCuentaDestino = new JTextField();
        txtMonto = new JTextField();
        JTextField[] campos = {txtCuentaDestino, txtMonto};
        for (JTextField campo : campos) {
            campo.setPreferredSize(dimensionCampo);
            campo.setFont(fuenteCampos);
        }

        btnAceptar = new JButton("Aceptar");
        btnAtras = new JButton("Atrás");

        JButton[] botones = {btnAceptar, btnAtras};
        for (JButton btn : botones) {
            btn.setPreferredSize(dimensionBoton);
            btn.setFont(fuenteCampos);
        }

        btnAceptar.addActionListener(e -> realizarTransferencia());
        btnAtras.addActionListener(e -> volver());

        // Layout
        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelCentral.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        gbc.gridy++;
        panelCentral.add(lblCuentaDestino, gbc);
        gbc.gridx = 1;
        panelCentral.add(txtCuentaDestino, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelCentral.add(lblMonto, gbc);
        gbc.gridx = 1;
        panelCentral.add(txtMonto, gbc);

        // Panel inferior
        JPanel panelInferior = new JPanel(new BorderLayout());
        JPanel panelIzq = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 20));
        JPanel panelDer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 20));

        panelIzq.add(btnAtras);
        panelDer.add(btnAceptar);

        panelInferior.add(panelIzq, BorderLayout.WEST);
        panelInferior.add(panelDer, BorderLayout.EAST);

        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void realizarTransferencia() {
        try {
            long numeroCuentaDestino = Long.parseLong(txtCuentaDestino.getText().trim());
            double monto = Double.parseDouble(txtMonto.getText().trim());

            CuentaBO cuentaBO = DependencyInjector.getCuentaBO();
            CuentaDTO cuentaDestinoDTO = cuentaBO.obtenerPorNumeroCuenta(numeroCuentaDestino);

            if (cuentaDestinoDTO == null) {
                JOptionPane.showMessageDialog(this, "La cuenta destino no existe.");
                return;
            }
            
            int idCuentaDestino = cuentaDestinoDTO.getIdCuenta();
            
            TransferenciaBO transferenciaBO = DependencyInjector.getTransferenciaBO();
            boolean exito = transferenciaBO.realizarTransferencia(
                idCuentaOrigen,
                idCuentaDestino,
                monto
            );

            if (exito) {
                JOptionPane.showMessageDialog(this, "Transferencia realizada con éxito.");
                volver();
            } else {
                JOptionPane.showMessageDialog(this, "No se ha podido realizar la transferencia.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Datos inválidos. Verifica cuenta y monto.");
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void volver() {
        CuentasCliente pantalla = new CuentasCliente();
        pantalla.mostrarCuentasDe(cuentaOrigenDTO.getIdCliente());
        pantalla.setVisible(true);
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
