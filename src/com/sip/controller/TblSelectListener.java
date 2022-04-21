/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sip.controller;

import com.sip.model.InvoiceLineTblModel;
import com.sip.model.invoiceHeader;
import com.sip.model.invoiceLine;
import com.sip.view.InvoiceForm;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author BosBos
 */
public class TblSelectListener implements ListSelectionListener{
    private InvoiceForm frame;

    public TblSelectListener(InvoiceForm frame) {
        this.frame = frame;
    }
    
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedInvIndex = frame.getInvoice_header_Table().getSelectedRow();
        System.out.println(" invoice selected " + selectedInvIndex);
        if (selectedInvIndex != -1){
        invoiceHeader selectedInv = frame.getInvoicesArray().get(selectedInvIndex);
        ArrayList<invoiceLine> lines = selectedInv.getLines();
        InvoiceLineTblModel lineTblModel = new InvoiceLineTblModel(lines);
        frame.setLinesArray(lines);
        frame.getInvoice_line_table().setModel(lineTblModel);
        frame.getCustomerName().setText(selectedInv.getCustomer());
        frame.getInvoiceNo().setText("" + selectedInv.getNum());
        frame.getInvoiceTotal().setText("" + selectedInv.getInvoiceTotal());
        frame.getInvoiceDate().setText(InvoiceForm.DateFormate.format(selectedInv.getInvDate()));
    }
    }
    
}
