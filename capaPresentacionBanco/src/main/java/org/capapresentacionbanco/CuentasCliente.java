/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package org.capapresentacionbanco;

import BO.CuentaBO;
import Configuracion.DependencyInjector;
import DTO.CuentaDTO;
import Exception.NegocioException;
import javax.swing.*;
import java.awt.*;
import java.util.List;
/**
 *
 * @author katia
 */
public class CuentasCliente extends javax.swing.JFrame {
    private JLabel lblTitulo;
    private JPanel panelTabla;
    private JButton btnRegistrarCuenta, btnAtras;

    private int idCliente;
    private CuentaBO cuentaBO;

    /**
     * Creates new form CuentasCliente
     */
    public CuentasCliente() {
        cuentaBO = DependencyInjector.getCuentaBO();
        configurarVentana();
        initComponents2();
    }
    
    public void mostrarCuentasDe(int idCliente) {
        this.idCliente = idCliente;
        try {
            List<CuentaDTO> cuentas = cuentaBO.obtenerPorCliente(idCliente);
            if (cuentas.isEmpty()){
                JOptionPane.showMessageDialog(this, "No tiene cuentas activas.");
            }
            llenarTablaConCuentas(cuentas);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener cuentas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void configurarVentana() {
        setTitle("Cuentas");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void initComponents2() {
        Font fuente = new Font("SansSerif", Font.PLAIN, 18);
        Dimension dimensionBoton = new Dimension(120, 35);

        lblTitulo = new JLabel("Cuentas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 48));
        add(lblTitulo, BorderLayout.NORTH);

        panelTabla = new JPanel(new GridBagLayout());
        JScrollPane scroll = new JScrollPane(panelTabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scroll, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout());

        btnAtras = new JButton("Atrás");
        btnAtras.setPreferredSize(dimensionBoton);
        btnAtras.setFont(fuente);
        btnAtras.addActionListener(e -> {
            DatosCliente pantalla = new DatosCliente();
            pantalla.mostrarConCliente(idCliente);
            pantalla.setVisible(true);
            this.dispose();
        });

        btnRegistrarCuenta = new JButton("Registrar Cuenta");
        btnRegistrarCuenta.setPreferredSize(dimensionBoton);
        btnRegistrarCuenta.setFont(fuente);
        btnRegistrarCuenta.addActionListener(e -> {
            RegistrarCuenta pantalla = new RegistrarCuenta();
            pantalla.cargarParaCliente(idCliente);
            pantalla.setVisible(true);
            this.dispose();
        });

        JPanel panelIzq = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 20));
        panelIzq.add(btnAtras);

        JPanel panelDer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 20));
        panelDer.add(btnRegistrarCuenta);

        panelInferior.add(panelIzq, BorderLayout.WEST);
        panelInferior.add(panelDer, BorderLayout.EAST);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void llenarTablaConCuentas(List<CuentaDTO> cuentas) {
        panelTabla.removeAll();

        Font fuente = new Font("SansSerif", Font.PLAIN, 16);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] headers = {"Número", "Fecha apertura", "Saldo",
                "Realizar transferencia", "Generar retiro sin cuenta",
                "Historial de operaciones", "Cancelar cuenta"};

        // Encabezados
        for (int i = 0; i < headers.length; i++) {
            gbc.gridx = i;
            gbc.gridy = 0;
            JLabel header = new JLabel(headers[i], SwingConstants.CENTER);
            header.setFont(fuente.deriveFont(Font.BOLD));
            panelTabla.add(header, gbc);
        }

        int fila = 1;
        for (CuentaDTO cuenta : cuentas) {
            gbc.gridy = fila;
            gbc.gridx = 0;
            panelTabla.add(new JLabel(String.valueOf(cuenta.getNumCuenta())), gbc);

            gbc.gridx = 1;
            panelTabla.add(new JLabel(cuenta.getFechaApertura().toString()), gbc);

            gbc.gridx = 2;
            panelTabla.add(new JLabel(String.format("$%.2f", cuenta.getSaldo())), gbc);

            JButton btnTransferir = crearBoton("Transferencia");
            btnTransferir.addActionListener(e -> {
                PantallaTransferencia ventana = new PantallaTransferencia();
                ventana.cargarConCuentaOrigen(cuenta.getIdCuenta());
                ventana.setVisible(true);
                this.dispose();
            });
            gbc.gridx = 3;
            panelTabla.add(btnTransferir, gbc);

            JButton btnRetiro = crearBoton("Generar");
            btnRetiro.addActionListener(e -> {
                PantallaGenerarRetiro ventana = new PantallaGenerarRetiro();
                ventana.cargarConCuentaOrigen(cuenta.getIdCuenta());
                ventana.setVisible(true);
                this.dispose();
            });
            
            gbc.gridx = 4;
            panelTabla.add(btnRetiro, gbc);

            JButton btnHistorial = crearBoton("Historial");
            btnHistorial.addActionListener(e -> {
                PantallaHistorialOperaciones ventana = new PantallaHistorialOperaciones();
                ventana.cargarConCuenta(cuenta.getIdCuenta());
                ventana.setVisible(true);
                this.dispose();
            });
            gbc.gridx = 5;
            panelTabla.add(btnHistorial, gbc);

            JButton btnCancelar = crearBoton("Cancelar");
            btnCancelar.addActionListener(e -> cancelarCuenta(cuenta));
            gbc.gridx = 6;
            panelTabla.add(btnCancelar, gbc);

            fila++;
        }

        panelTabla.revalidate();
        panelTabla.repaint();
    }
    
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(130, 35));
        btn.setFont(new Font("SansSerif", Font.PLAIN, 16));
        return btn;
    }

    private void cancelarCuenta(CuentaDTO cuenta) {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Deseas cancelar esta cuenta?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean exito = cuentaBO.cancelarCuenta(cuenta.getIdCuenta());
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Cuenta cancelada.");
                    mostrarCuentasDe(idCliente);
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo cancelar.");
                }
            } catch (NegocioException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
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
