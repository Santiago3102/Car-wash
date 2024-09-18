/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package lava_autoss;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class lava_autoss extends JFrame {

    private DefaultTableModel inf;
    private JTable tabla;
    private JTextField Pedirdueño, Pedirtelef, Pedirplaca;
    private JComboBox<String> Vehiculo, Lavado;
    private JLabel TotalConsumo;
    private int totalAgua = 0;
    private int totalJabon = 0;

    private final int[][] consumoBase = { {1, 2}, {3, 5}, {5, 8} };

    public lava_autoss() {
        setTitle("Información de Servicios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
       
        inf = new DefaultTableModel(new Object[][]{}, new String[]{
                "Servicio", "Dueño", "Teléfono", "Tipo de vehículo", "Placa", "Tipo de lavado", "Agua (L)", "Jabón (ml)"
        });
        
       
        tabla = new JTable(inf);
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        Pedirdueño = new JTextField(10);
        Pedirtelef = new JTextField(10);
        Pedirplaca = new JTextField(10);
        
        Vehiculo = new JComboBox<>(new String[]{"Tipo de vehículo", "Moto", "Carro"});
        Lavado = new JComboBox<>(new String[]{"Tipo de lavado", "Sencillo", "Profesional", "Polichado"});
        
        JButton Añadir = new JButton("Añadir");
        JButton Borrar = new JButton("Borrar");
        TotalConsumo = new JLabel("Consumo total: Agua = 0 L, Jabón = 0 ml");
        
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Agregar padding al panel
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Dueño:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(Pedirdueño, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Teléfono:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(Pedirtelef, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Placa:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(Pedirplaca, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(Vehiculo, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(Lavado, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(Añadir, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(Borrar, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(TotalConsumo, gbc);
        
       
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

       
        Añadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                añadirServicio();
            }
        });

        
        Borrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                borrarServicio();
            }
        });
    }

    private int[] calcularConsumo(String tipoLavado, String tipoVehiculo) {
        int[] consumo = {0, 0}; // [agua, jabón]

        switch (tipoLavado) {
            case "Sencillo":
                consumo = consumoBase[0];
                break;
            case "Profesional":
                consumo = consumoBase[1];
                break;
            case "Polichado":
                consumo = consumoBase[2];
                break;
        }

        if (tipoVehiculo.equals("Carro")) {
            consumo[0] *= 3; 
            consumo[1] *= 2; 
        }

        return consumo;
    }

    private void añadirServicio() {
        String dueño = Pedirdueño.getText();
        String telefono = Pedirtelef.getText();
        String placa = Pedirplaca.getText();
        String tipoVehiculo = (String) Vehiculo.getSelectedItem();
        String tipoLavado = (String) Lavado.getSelectedItem();

        if (dueño.isEmpty() || telefono.isEmpty() || placa.isEmpty() || 
            tipoVehiculo.equals("Tipo de vehículo") || tipoLavado.equals("Tipo de lavado")) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos y seleccione un vehículo y tipo de lavado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int[] consumo = calcularConsumo(tipoLavado, tipoVehiculo);
        totalAgua += consumo[0];
        totalJabon += consumo[1];

        inf.addRow(new Object[]{inf.getRowCount() + 1, dueño, telefono, tipoVehiculo, placa, tipoLavado, consumo[0], consumo[1]});
        TotalConsumo.setText("Consumo total: Agua = " + totalAgua + " L, Jabón = " + totalJabon + " ml");

        Pedirdueño.setText("");
        Pedirtelef.setText("");
        Pedirplaca.setText("");
        Vehiculo.setSelectedIndex(0);
        Lavado.setSelectedIndex(0);
    }

    private void borrarServicio() {
        int selectedRow = tabla.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila para borrar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int agua = (int) inf.getValueAt(selectedRow, 6);
        int jabon = (int) inf.getValueAt(selectedRow, 7);
        totalAgua -= agua;
        totalJabon -= jabon;

        inf.removeRow(selectedRow);
        TotalConsumo.setText("Consumo total: Agua = " + totalAgua + " L, Jabón = " + totalJabon + " ml");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new lava_autoss().setVisible(true);
        });
    }
}
