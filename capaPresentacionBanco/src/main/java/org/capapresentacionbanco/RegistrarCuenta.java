/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package org.capapresentacionbanco;

import BO.CuentaBO;
import Configuracion.DependencyInjector;
import DTO.CuentaDTO;
import Exception.NegocioException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
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
public class RegistrarCuenta extends javax.swing.JFrame {
    
    private JLabel lblTitulo, lblNumero, lblFecha, lblSaldo;
    private JTextField txtNumero, txtFecha, txtSaldo;
    private JButton btnCrear, btnAtras;

    private int idCliente;
    private CuentaBO cuentaBO;

    /**
     * Creates new form RegistrarCuenta
     */
    public RegistrarCuenta() {
        cuentaBO = DependencyInjector.getCuentaBO();
        configurarVentana();
        initComponents2();
    }

    public void cargarParaCliente(int idCliente) {
        this.idCliente = idCliente;
        llenarCamposGenerados();
    }

    private void configurarVentana() {
        setTitle("Registrar Cuenta");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }
    
    private void initComponents2() {
        Font fuenteCampos = new Font("SansSerif", Font.PLAIN, 18);
        Dimension dimensionCampo = new Dimension(300, 35);
        Dimension dimensionBoton = new Dimension(140, 35);

        lblTitulo = new JLabel("Nueva Cuenta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 42));

        lblNumero = new JLabel("Número");
        lblFecha = new JLabel("Fecha Apertura");
        lblSaldo = new JLabel("Saldo");

        JLabel[] etiquetas = {lblNumero, lblFecha, lblSaldo};
        for (JLabel lbl : etiquetas) {
            lbl.setFont(fuenteCampos);
        }

        txtNumero = new JTextField();
        txtFecha = new JTextField();
        txtSaldo = new JTextField();

        txtNumero.setEditable(false);
        txtFecha.setEditable(false);

        JTextField[] campos = {txtNumero, txtFecha, txtSaldo};
        for (JTextField campo : campos) {
            campo.setPreferredSize(dimensionCampo);
            campo.setFont(fuenteCampos);
        }

        btnCrear = new JButton("Crear");
        btnCrear.setPreferredSize(dimensionBoton);
        btnCrear.setFont(fuenteCampos);
        btnCrear.addActionListener(e -> crearCuenta());

        btnAtras = new JButton("Atrás");
        btnAtras.setPreferredSize(dimensionBoton);
        btnAtras.setFont(fuenteCampos);
        btnAtras.addActionListener(e -> volver());

        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelCentral.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        gbc.gridy++;
        panelCentral.add(lblNumero, gbc); gbc.gridx = 1; panelCentral.add(txtNumero, gbc);
        gbc.gridx = 0; gbc.gridy++;
        panelCentral.add(lblFecha, gbc); gbc.gridx = 1; panelCentral.add(txtFecha, gbc);
        gbc.gridx = 0; gbc.gridy++;
        panelCentral.add(lblSaldo, gbc); gbc.gridx = 1; panelCentral.add(txtSaldo, gbc);

        JPanel panelInferior = new JPanel(new BorderLayout());
        JPanel panelIzq = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 20));
        JPanel panelDer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 20));

        panelIzq.add(btnAtras);
        panelDer.add(btnCrear);
        panelInferior.add(panelIzq, BorderLayout.WEST);
        panelInferior.add(panelDer, BorderLayout.EAST);

        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void llenarCamposGenerados() {
        long numCuenta = generarNumCuenta();
        String hoy = LocalDate.now().toString();

        txtNumero.setText(String.valueOf(numCuenta));
        txtFecha.setText(hoy);
    }
    
    private long generarNumCuenta() {
        long base = 1000000000L;
        long random = (long) (Math.random() * 900000000L);
        return base + random;
    }

    private void crearCuenta() {
        try {
            double saldo = Double.parseDouble(txtSaldo.getText().trim());
            CuentaDTO dto = new CuentaDTO();
            dto.setNumCuenta(Long.parseLong(txtNumero.getText().trim()));
            dto.setFechaApertura(LocalDate.parse(txtFecha.getText().trim()));
            dto.setSaldo(saldo);
            dto.setIdCliente(idCliente);

            boolean exito = cuentaBO.registrarCuenta(dto);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Cuenta creada con éxito.");
                volver();
            } else {
                JOptionPane.showMessageDialog(this, "No se ha podido crear la cuenta. Intente de nuevo.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Saldo inválido.");
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void volver() {
        CuentasCliente pantalla = new CuentasCliente();
        pantalla.mostrarCuentasDe(idCliente);
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
