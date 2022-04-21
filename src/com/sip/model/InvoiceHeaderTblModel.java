/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sip.model;

import com.sip.view.InvoiceForm;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author BosBos
 */
public class InvoiceHeaderTblModel extends AbstractTableModel{

private ArrayList<invoiceHeader> invoicesArray;
private String[] columns = {"Invoice Number" , "Invoice Date" , "Customer Name" , "Invoice Total"};

    public InvoiceHeaderTblModel(ArrayList<invoiceHeader> invoicesArray) {
        this.invoicesArray = invoicesArray;
    }


    @Override
    public int getRowCount() {
    return invoicesArray.size();
    }

    @Override
    public int getColumnCount() {
    return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    invoiceHeader inv = invoicesArray.get(rowIndex);
    switch (columnIndex) {
        case 0: return inv.getNum();
        case 1: return InvoiceForm.DateFormate.format(inv.getInvDate());
        case 2 : return inv.getCustomer();
        case 3 : return inv.getInvoiceTotal();
    }
    return "";
    }

    @Override
    public String getColumnName(int column) {
    return columns[column];
    }
    
}
