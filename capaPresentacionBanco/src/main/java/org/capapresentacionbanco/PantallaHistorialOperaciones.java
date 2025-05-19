/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package org.capapresentacionbanco;

import BO.CuentaBO;
import BO.RetiroBO;
import BO.TransferenciaBO;
import Configuracion.DependencyInjector;
import DTO.CuentaDTO;
import DTO.RetiroDTO;
import DTO.TransferenciaDTO;
import Exception.NegocioException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author katia
 */
public class PantallaHistorialOperaciones extends javax.swing.JFrame {
    
    private JComboBox<String> comboTipoOperacion;
    private DatePicker dateInicio, dateFin;
    private JTable tabla;
    private JButton btnAtras;
    private CuentaDTO cuentaOrigenDTO;

    
    /**
     * Creates new form PantallaHistorialOperaciones
     */
    public PantallaHistorialOperaciones() {
        configurarVentana();
        initComponents2();
    }
    
    public void cargarConCuenta(int idCuenta) {
        try {
            CuentaBO cuentaBO = DependencyInjector.getCuentaBO();
            cuentaOrigenDTO = cuentaBO.obtenerPorId(idCuenta);
            comboTipoOperacion.setSelectedItem("Todas");
            dateInicio.clear();
            dateFin.clear();
            cargarTabla(); 
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener cuenta: " + e.getMessage());
            volver();
        }
    }
    
    private void configurarVentana() {
        setTitle("Historial de operaciones");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }
    
    private void initComponents2() {
        Font fuente = new Font("SansSerif", Font.PLAIN, 18);
        Dimension campo = new Dimension(200, 35);

        JLabel lblTitulo = new JLabel("Historial de operaciones", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 42));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel filtros = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblTipo = new JLabel("Tipo de operación");
        comboTipoOperacion = new JComboBox<>(new String[]{"Todas", "Transferencia", "Retiro"});
        comboTipoOperacion.setPreferredSize(campo);
        comboTipoOperacion.setFont(fuente);

        DatePickerSettings settingsInicio = new DatePickerSettings();
        settingsInicio.setFormatForDatesCommonEra("yyyy-MM-dd");
        dateInicio = new DatePicker(settingsInicio);
        
        DatePickerSettings settingsFin = new DatePickerSettings();
        settingsFin.setFormatForDatesCommonEra("yyyy-MM-dd");
        dateFin = new DatePicker(settingsFin);
        
        dateInicio.setPreferredSize(campo);
        dateFin.setPreferredSize(campo);
        dateInicio.setFont(fuente);
        dateFin.setFont(fuente);

        JLabel lblInicio = new JLabel("Fecha inicio");
        JLabel lblFin = new JLabel("Fecha fin");
        lblTipo.setFont(fuente);
        lblInicio.setFont(fuente);
        lblFin.setFont(fuente);

        gbc.gridx = 0; gbc.gridy = 0; filtros.add(lblTipo, gbc);
        gbc.gridx = 1; filtros.add(comboTipoOperacion, gbc);
        gbc.gridx = 0; gbc.gridy = 1; filtros.add(lblInicio, gbc);
        gbc.gridx = 1; filtros.add(dateInicio, gbc);
        gbc.gridx = 0; gbc.gridy = 2; filtros.add(lblFin, gbc);
        gbc.gridx = 1; filtros.add(dateFin, gbc);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setPreferredSize(new Dimension(100, 35));
        btnFiltrar.setFont(fuente);
        btnFiltrar.addActionListener(e -> cargarTabla());
        gbc.gridx = 2; gbc.gridy = 1; filtros.add(btnFiltrar, gbc);

        // Tabla
        tabla = new JTable();
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(600, 300));
        
        // Panel central que contiene filtros y tabla
        JPanel panelCentro = new JPanel(new BorderLayout());

        // Filtros arriba
        panelCentro.add(filtros, BorderLayout.NORTH);
        panelCentro.add(scroll, BorderLayout.CENTER);

        //panelCentro.add(scroll, BorderLayout.CENTER);
        
        // Agregar el panel central completo al frame
        add(panelCentro, BorderLayout.CENTER);

        // Botón atrás
        btnAtras = new JButton("Atrás");
        btnAtras.setPreferredSize(new Dimension(120, 35));
        btnAtras.setFont(fuente);
        btnAtras.addActionListener(e -> volver());

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 20));
        panelInferior.add(btnAtras);
        add(panelInferior, BorderLayout.SOUTH);
        
        //cargarTabla();
    }
    
    private void cargarTabla() {
        String tipo = (String) comboTipoOperacion.getSelectedItem();
        LocalDate inicio = dateInicio.getDate();
        LocalDate fin = dateFin.getDate();

        try {
            DefaultTableModel model = new DefaultTableModel(
                new String[]{"FechaHora", "Destino/Folio", "Monto", "Tipo"}, 0
            );
            if ("Transferencia".equals(tipo) || "Todas".equals(tipo)) {
                TransferenciaBO transferenciaBO = DependencyInjector.getTransferenciaBO();
                List<TransferenciaDTO> transferencias;
                if (inicio != null && fin != null) {
                    transferencias = transferenciaBO.obtenerTransferenciasFiltradas(
                        cuentaOrigenDTO.getIdCuenta(), true, false,
                        LocalDateTime.of(inicio, LocalTime.MIN),
                        LocalDateTime.of(fin, LocalTime.MAX)
                    );
                } else {
                    transferencias = transferenciaBO.obtenerPorCuenta(cuentaOrigenDTO.getIdCuenta());
                }
                
//                DefaultTableModel model = new DefaultTableModel(
//                        new String[]{"FechaHora", "Cuenta destino", "Monto", "Tipo"}, 0
//                );
                for (TransferenciaDTO t : transferencias) {
                    model.addRow(new Object[]{
                            t.getFechaHora().toString(),
                            t.getIdCuentaDestino(),
                            t.getMonto(),
                            "Transferencia"
                    });
                }

            } if ("Retiro".equals(tipo) || "Todas".equals(tipo)) {
                RetiroBO retiroBO = DependencyInjector.getRetiroBO();
                List<RetiroDTO> retiros;

                if (inicio != null && fin != null) {
                    retiros = retiroBO.obtenerRetirosFiltradosPorCuenta(
                        cuentaOrigenDTO.getIdCuenta(),
                        LocalDateTime.of(inicio, LocalTime.MIN),
                        LocalDateTime.of(fin, LocalTime.MAX)
                    );
                } else {
                    retiros = retiroBO.obtenerRetirosCobradosPorCuenta(cuentaOrigenDTO.getIdCuenta());
                }

//                DefaultTableModel model = new DefaultTableModel(
//                        new String[]{"FechaHora", "Folio", "Monto", "Tipo"}, 0
//                );
                for (RetiroDTO r : retiros) {
                    model.addRow(new Object[]{
                            r.getFechaHora().toString(),
                            r.getFolio(),
                            r.getMonto(),
                            "Retiro"
                    });
                }
            }
            tabla.setModel(model);
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
