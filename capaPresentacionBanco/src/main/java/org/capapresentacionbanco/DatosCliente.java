/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package org.capapresentacionbanco;

import BO.ClienteBO;
import Configuracion.DependencyInjector;
import DTO.ClienteDTO;
import Exception.NegocioException;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
public class DatosCliente extends javax.swing.JFrame {
    private JLabel lblTitulo, lblNombre, lblApellidoPaterno, lblApellidoMaterno,
                   lblDomicilio, lblFechaNacimiento, lblEdad, lblMisCuentas;
    private JTextField txtNombre, txtApellidoPaterno, txtApellidoMaterno,
                       txtDomicilio, txtEdad;
    private JButton btnCuentas, btnActualizar, btnAtras;
    private DatePicker datePickerFechaNac;
    
    private ClienteBO clienteBO;
    private ClienteDTO clienteDTO;

    /**
     * Creates new form DatosCliente
     */
    public DatosCliente() {
        clienteBO = DependencyInjector.getClienteBO();
        configurarVentana();
        initComponents2();
    }
    
    public void mostrarConCliente(int idCliente) {
        try {
            clienteDTO = clienteBO.obtenerPorId(idCliente);
            llenarCamposConDatos();
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener datos del cliente: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void configurarVentana() {
        setTitle("Datos Cliente");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }
    
    private void llenarCamposConDatos() {
        txtNombre.setText(clienteDTO.getNombre());
        txtApellidoPaterno.setText(clienteDTO.getApellidoPaterno());
        txtApellidoMaterno.setText(clienteDTO.getApellidoMaterno());
        txtDomicilio.setText(clienteDTO.getDomicilio());

        LocalDate fechaNac = clienteDTO.getFechaNacimiento();
        datePickerFechaNac.setDate(clienteDTO.getFechaNacimiento());

        int edad = Period.between(fechaNac, LocalDate.now()).getYears();
        txtEdad.setText(String.valueOf(edad));
    }
    
    private void initComponents2() {
        Font fuenteCampos = new Font("SansSerif", Font.PLAIN, 18);
        Dimension dimensionCampo = new Dimension(300, 35);
        Dimension dimensionBoton = new Dimension(140, 35);
        
        lblTitulo = new JLabel("Datos Cliente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 48));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        lblNombre = new JLabel("Nombre");
        lblApellidoPaterno = new JLabel("Apellido Paterno");
        lblApellidoMaterno = new JLabel("Apellido Materno");
        lblDomicilio = new JLabel("Domicilio");
        lblFechaNacimiento = new JLabel("Fecha nacimiento");
        lblEdad = new JLabel("Edad");
        lblMisCuentas = new JLabel("Mis cuentas");

        JLabel[] etiquetas = {
            lblNombre, lblApellidoPaterno, lblApellidoMaterno,
            lblDomicilio, lblFechaNacimiento, lblEdad, lblMisCuentas
        };
        
        for (JLabel lbl : etiquetas) {
            lbl.setFont(fuenteCampos);
        }
        
        txtNombre = new JTextField();
        txtApellidoPaterno = new JTextField();
        txtApellidoMaterno = new JTextField();
        txtDomicilio = new JTextField(20);
        txtEdad = new JTextField();
        txtEdad.setEditable(false);

        JTextField[] campos = {
            txtNombre, txtApellidoPaterno, txtApellidoMaterno,
            txtDomicilio, txtEdad
        };

        for (JTextField campo : campos) {
            campo.setPreferredSize(dimensionCampo);
            campo.setFont(fuenteCampos);
        }
        
        DatePickerSettings settings = new DatePickerSettings();
        settings.setFormatForDatesCommonEra("dd/MM/yyyy");
        settings.setAllowEmptyDates(false);
        settings.setAllowKeyboardEditing(false);
        
        datePickerFechaNac = new DatePicker(settings);
        datePickerFechaNac.setPreferredSize(new Dimension(300, 35));
        datePickerFechaNac.getComponentDateTextField().setFont(new Font("SansSerif", Font.PLAIN, 18));
        
        btnCuentas = new JButton("Cuentas");
        btnActualizar = new JButton("Actualizar");
        btnAtras = new JButton("Atrás");

        JButton[] botones = {btnCuentas, btnActualizar, btnAtras};
        for (JButton btn : botones) {
            btn.setPreferredSize(dimensionBoton);
            btn.setFont(fuenteCampos);
        }
        
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnCuentas.addActionListener(e -> abrirPantallaCuentas());
        btnAtras.addActionListener(e -> volver());

        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelCentral.add(lblTitulo, gbc);
        gbc.gridwidth = 1;
        
        String[] etiquetasOrden = {
            "Nombre", "Apellido Paterno", "Apellido Materno",
            "Domicilio", "Fecha nacimiento", "Edad", "Mis cuentas"
        };
        
        JLabel[] lbls = {
            lblNombre, lblApellidoPaterno, lblApellidoMaterno,
            lblDomicilio, lblFechaNacimiento, lblEdad, lblMisCuentas
        };

        Component[] comps = {
            txtNombre, txtApellidoPaterno, txtApellidoMaterno,
            txtDomicilio, datePickerFechaNac, txtEdad, btnCuentas
        };
        
        for (int i = 0; i < lbls.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            panelCentral.add(lbls[i], gbc);

            gbc.gridx = 1;
            panelCentral.add(comps[i], gbc);
        }

        JPanel panelInferior = new JPanel(new BorderLayout());

        // Panel izquierdo con "Atrás"
        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 20));
        panelIzquierdo.add(btnAtras);

        // Panel derecho con "Actualizar"
        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 20));
        panelDerecho.add(btnActualizar);

        panelInferior.add(panelIzquierdo, BorderLayout.WEST);
        panelInferior.add(panelDerecho, BorderLayout.EAST);

        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void actualizarCliente() {
        try {
            clienteDTO.setNombre(txtNombre.getText().trim());
            clienteDTO.setApellidoPaterno(txtApellidoPaterno.getText().trim());
            clienteDTO.setApellidoMaterno(txtApellidoMaterno.getText().trim());
            clienteDTO.setDomicilio(txtDomicilio.getText().trim());

            LocalDate fecha = datePickerFechaNac.getDate();
            clienteDTO.setFechaNacimiento(fecha);

            boolean exito = clienteBO.actualizarCliente(clienteDTO);
            if (exito) {
                int edad = Period.between(fecha, LocalDate.now()).getYears();
                txtEdad.setText(String.valueOf(edad));
                JOptionPane.showMessageDialog(this, "Datos actualizados correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el cliente.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirPantallaCuentas() {
        CuentasCliente pantalla = new CuentasCliente();
        pantalla.mostrarCuentasDe(clienteDTO.getIdCliente());
        pantalla.setVisible(true);
        this.dispose();
    }

    private void volver() {
        new PantallaInicio().setVisible(true);
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
