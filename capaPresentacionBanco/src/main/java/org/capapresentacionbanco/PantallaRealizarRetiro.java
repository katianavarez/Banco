/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package org.capapresentacionbanco;

import BO.RetiroBO;
import Configuracion.DependencyInjector;
import Exception.NegocioException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author katia
 */
public class PantallaRealizarRetiro extends javax.swing.JFrame {
    
    private JLabel lblTitulo, lblFolio, lblContraseña;
    private JTextField txtFolio;
    private JPasswordField txtContraseña;
    private JButton btnRetirar, btnAtras;

    /**
     * Creates new form PantallaRealizarRetiro
     */
    public PantallaRealizarRetiro() {
        configurarVentana();
        initComponents2();
    }
    
    private void configurarVentana() {
        setTitle("Realizar Retiro");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }
    
    private void initComponents2() {
        Font fuente = new Font("SansSerif", Font.PLAIN, 18);
        Dimension dimensionCampo = new Dimension(300, 35);
        Dimension boton = new Dimension(120, 35);

        lblTitulo = new JLabel("Realizar retiro", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 42));

        lblFolio = new JLabel("Folio");
        lblContraseña = new JLabel("Contraseña");

        JLabel[] etiquetas = {lblFolio, lblContraseña};
        for (JLabel lbl : etiquetas) lbl.setFont(fuente);

        txtFolio = new JTextField();
        txtContraseña = new JPasswordField();

        JTextField[] campos = {txtFolio, txtContraseña};
        for (JTextField campo : campos) {
            campo.setPreferredSize(dimensionCampo);
            campo.setFont(fuente);
        }

        btnRetirar = new JButton("Retirar");
        btnAtras = new JButton("Atrás");

        JButton[] botones = {btnRetirar, btnAtras};
        for (JButton b : botones) {
            b.setPreferredSize(boton);
            b.setFont(fuente);
        }

        btnRetirar.addActionListener(e -> realizarRetiro());
        btnAtras.addActionListener(e -> volver());

        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelCentral.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        gbc.gridy++; gbc.gridx = 0;
        panelCentral.add(lblFolio, gbc);
        gbc.gridx = 1;
        panelCentral.add(txtFolio, gbc);

        gbc.gridy++; gbc.gridx = 0;
        panelCentral.add(lblContraseña, gbc);
        gbc.gridx = 1;
        panelCentral.add(txtContraseña, gbc);

        JPanel panelInferior = new JPanel(new BorderLayout());
        JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 20));
        JPanel der = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 20));
        izq.add(btnAtras);
        der.add(btnRetirar);

        panelInferior.add(izq, BorderLayout.WEST);
        panelInferior.add(der, BorderLayout.EAST);

        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void realizarRetiro() {
        try {
            int folio = Integer.parseInt(txtFolio.getText().trim());
            String contraseña = new String(txtContraseña.getPassword()).trim();

            if (contraseña.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La contraseña no puede estar vacía.");
                return;
            }

            RetiroBO retiroBO = DependencyInjector.getRetiroBO();
            boolean exito = retiroBO.cobrarRetiroPorFolioYContraseña(folio, contraseña);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Retiro aceptado.");
                volver();
            } else {
                JOptionPane.showMessageDialog(this, "Retiro rechazado.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Folio inválido.");
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(this, "Error al procesar retiro: " + e.getMessage());
        }
    }
    
    private void volver() {
        PantallaInicio inicio = new PantallaInicio();
        inicio.setVisible(true);
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
