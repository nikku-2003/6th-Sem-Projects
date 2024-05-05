package com.project.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ElectricityBillSystem extends JFrame implements ActionListener {
    private JTextField txtUnits, txtName, txtBillAmount;
    private JButton btnCalculate, btnClear;
    private JLabel lblSummary;
    private JComboBox<String> cmbBillingCycle;

    public ElectricityBillSystem() {
        setTitle("Electricity Bill System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        add(panel);

        JLabel lblName = new JLabel("Customer Name:");
        panel.add(lblName);
        txtName = new JTextField();
        panel.add(txtName);

        JLabel lblUnits = new JLabel("Units Consumed:");
        panel.add(lblUnits);
        txtUnits = new JTextField();
        panel.add(txtUnits);

        JLabel lblBillingCycle = new JLabel("Billing Cycle:");
        panel.add(lblBillingCycle);
        cmbBillingCycle = new JComboBox<>(new String[] { "Monthly", "Quarterly", "Annually" });
        panel.add(cmbBillingCycle);

        JLabel lblSummaryTitle = new JLabel("Bill Summary:");
        panel.add(lblSummaryTitle);
        lblSummary = new JLabel();
        panel.add(lblSummary);

        JLabel lblBillAmount = new JLabel("Bill Amount:");
        panel.add(lblBillAmount);
        txtBillAmount = new JTextField();
        txtBillAmount.setEditable(false);
        panel.add(txtBillAmount);

        btnCalculate = new JButton("Calculate Bill");
        btnCalculate.addActionListener(this);
        panel.add(btnCalculate);

        btnClear = new JButton("Clear");
        btnClear.addActionListener(this);
        panel.add(btnClear);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCalculate) {
            showSummary();
            calculateBill();
        } else if (e.getSource() == btnClear) {
            clearFields();
        }
    }

    private void showSummary() {
        String customerName = txtName.getText();
        String unitsText = txtUnits.getText();
        String billingCycle = (String) cmbBillingCycle.getSelectedItem();
        String summaryText = "<html><b>Customer Name:</b> " + customerName + "<br>"
                + "<b>Units Consumed:</b> " + unitsText + "<br>"
                + "<b>Billing Cycle:</b> " + billingCycle + "<br>"
                + "<b>Customer Type:</b> " + getCustomerType() + "<br>"
                + "<b>Tariff Rates:</b> Residential: $1/unit, Commercial: $2/unit (customize as needed)</html>";
        lblSummary.setText(summaryText);
    }

    private String getCustomerType() {
        Object[] options = { "Residential", "Commercial" };
        int choice = JOptionPane.showOptionDialog(this, "Select Customer Type", "Customer Type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            return "Residential";
        } else if (choice == 1) {
            return "Commercial";
        } else {
            return null;
        }
    }

    private void calculateBill() {
        try {
            double units = Double.parseDouble(txtUnits.getText());
            String customerType = getCustomerType();
            double billAmount = 0.0;

            if (customerType.equals("Residential")) {
                billAmount = calculateResidentialBill(units);
            } else if (customerType.equals("Commercial")) {
                billAmount = calculateCommercialBill(units);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid customer type selected.");
                return;
            }

            String billingCycle = (String) cmbBillingCycle.getSelectedItem();
            int billingPeriod = 1;
            if (billingCycle.equals("Quarterly")) {
                billingPeriod = 3;
            } else if (billingCycle.equals("Annually")) {
                billingPeriod = 12;
            }

            billAmount *= billingPeriod;
            txtBillAmount.setText(String.format("%.2f", billAmount));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for units consumed.");
        }
    }

    private double calculateResidentialBill(double units) {
        double billAmount;
        if (units <= 100) {
            billAmount = units * 1.0; // $1 per unit for first 100 units
        } else if (units <= 200) {
            billAmount = 100 + (units - 100) * 2.0; // $2 per unit for next 100 units
        } else {
            billAmount = 300 + (units - 200) * 3.0; // $3 per unit for additional units
        }
        return billAmount;
    }

    private double calculateCommercialBill(double units) {
        double billAmount;
        if (units <= 100) {
            billAmount = units * 2.0; // $2 per unit for first 100 units
        } else if (units <= 200) {
            billAmount = 200 + (units - 100) * 3.5; // $3.5 per unit for next 100 units
        } else {
            billAmount = 600 + (units - 200) * 4.5; // $4.5 per unit for additional units
        }
        return billAmount;
    }

    private void clearFields() {
        txtName.setText("");
        txtUnits.setText("");
        txtBillAmount.setText("");
        lblSummary.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ElectricityBillSystem().setVisible(true);
        });
    }
}