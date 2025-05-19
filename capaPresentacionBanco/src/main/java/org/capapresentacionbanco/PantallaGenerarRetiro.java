/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package org.capapresentacionbanco;

import BO.CuentaBO;
import BO.RetiroBO;
import Configuracion.DependencyInjector;
import DTO.CuentaDTO;
import DTO.RetiroDTO;
import Exception.NegocioException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Random;
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
public class PantallaGenerarRetiro extends javax.swing.JFrame {

    private JLabel lblTitulo, lblMonto, lblFolio, lblContraseña;
    private JTextField txtMonto, txtFolio, txtContraseña;
    private JButton btnAceptar, btnAtras;

    private long cuentaOrigen;
    private CuentaDTO cuentaOrigenDTO;

    
    /**
     * Creates new form PantallaGenerarRetiro
     */
    public PantallaGenerarRetiro() {
        configurarVentana();
        initComponents2();
    }

    public void cargarConCuentaOrigen(int cuentaOrigen) {
        try {
            CuentaBO cuentaBO = DependencyInjector.getCuentaBO();
            cuentaOrigenDTO = cuentaBO.obtenerPorId(cuentaOrigen);
            this.cuentaOrigen = cuentaOrigenDTO.getNumCuenta(); // Si sigues usando el número en algo
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar cuenta origen: " + e.getMessage());
            volver();
        }
    }
    
    private void configurarVentana() {
        setTitle("Generar Retiro");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    private void initComponents2() {
        Font fuenteCampos = new Font("SansSerif", Font.PLAIN, 18);
        Dimension dimensionCampo = new Dimension(300, 35);
        Dimension dimensionBoton = new Dimension(140, 35);

        lblTitulo = new JLabel("Generar retiro sin cuenta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 42));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        lblMonto = new JLabel("Monto");
        lblFolio = new JLabel("Folio");
        lblContraseña = new JLabel("Contraseña");

        JLabel[] etiquetas = {lblMonto, lblFolio, lblContraseña};
        for (JLabel lbl : etiquetas) {
            lbl.setFont(fuenteCampos);
        }

        txtMonto = new JTextField();
        txtFolio = new JTextField();
        txtContraseña = new JTextField();

        JTextField[] campos = {txtMonto, txtFolio, txtContraseña};
        for (JTextField campo : campos) {
            campo.setPreferredSize(dimensionCampo);
            campo.setFont(fuenteCampos);
        }

        txtFolio.setEditable(false);
        txtContraseña.setEditable(false);

        btnAceptar = new JButton("Aceptar");
        btnAtras = new JButton("Atrás");

        JButton[] botones = {btnAceptar, btnAtras};
        for (JButton btn : botones) {
            btn.setPreferredSize(dimensionBoton);
            btn.setFont(fuenteCampos);
        }

        btnAceptar.addActionListener(e -> generarRetiro());
        btnAtras.addActionListener(e -> volver());

        // Panel de campos
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
        panelCentral.add(lblMonto, gbc);
        gbc.gridx = 1;
        panelCentral.add(txtMonto, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelCentral.add(lblFolio, gbc);
        gbc.gridx = 1;
        panelCentral.add(txtFolio, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelCentral.add(lblContraseña, gbc);
        gbc.gridx = 1;
        panelCentral.add(txtContraseña, gbc);

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

    private void generarRetiro() {
        try {
            double monto = Double.parseDouble(txtMonto.getText().trim());

            RetiroBO retiroBO = DependencyInjector.getRetiroBO();
            RetiroDTO dto = retiroBO.registrarRetiro(cuentaOrigenDTO.getIdCuenta(), monto);

            txtFolio.setText(String.valueOf(dto.getFolio()));
            txtContraseña.setText(dto.getContraseña());

            JOptionPane.showMessageDialog(this, "Se ha generado con éxito.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Monto inválido.");
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
