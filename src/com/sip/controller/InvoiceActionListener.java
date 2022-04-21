/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sip.controller;

import com.sip.view.HeaderDialog;
import com.sip.model.InvoiceHeaderTblModel;
import com.sip.model.InvoiceLineTblModel;
import com.sip.model.invoiceHeader;
import com.sip.model.invoiceLine;
import com.sip.view.InvoiceForm;
import com.sip.view.LineDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author BosBos
 */
public class InvoiceActionListener implements ActionListener {

private InvoiceForm frame;
private HeaderDialog headerDialog;
private LineDialog lineDialog;
public InvoiceActionListener (InvoiceForm frame){
    this.frame = frame;
}

    public InvoiceActionListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()){
            
              case "Save Files":
                 saveFiles();
            break;
            
            
            case "Load Files":
                loadFiles();
            break;
            
             case "New Line":
                 createNewLine();
            break;
            
              case "Delete Line":
                 deleteLine();
            break;
            
          
             case "New Invoice":
                 createNewInvoice();
            break;
            
             case "Delete Invoice":
                 deleteInvoice();
            break;
            
             case "createInvoiceOK":
                 OkNewInvoiceDialog();
                 break;
                 
             case "createInvoiceCancel":
                 CancelNewInvoiceDialog();
                 break;
                 
             case "createLineOK" :
                 createLineDialogOk();
                 break;
                 
             case "createLineCancel" :
                 createLineDialogCancel();
                 break;
                 
            
        
        }
    }

    private void loadFiles() {
        JFileChooser fileChosser= new JFileChooser();
        try{
    int result = fileChosser.showOpenDialog(frame);
    if(result == JFileChooser.APPROVE_OPTION){
       File headerFile= fileChosser.getSelectedFile();
       Path headerPath = Paths.get(headerFile.getAbsolutePath());
       List<String> headerLines = Files.readAllLines(headerPath);
       ArrayList<invoiceHeader> invoiceHeaders = new ArrayList<>();
       for (String headerLine : headerLines) {
       String[] arr = headerLine.split(",");
       String str1 = arr[0];
       String str2 = arr[1];
       String str3 = arr[2];
       int code = Integer.parseInt(str1);
       Date invoiceDate = InvoiceForm.DateFormate.parse(str2);
       invoiceHeader header = new invoiceHeader(code,str3,invoiceDate);
       invoiceHeaders.add(header);
        }
       frame.setInvoicesArray(invoiceHeaders);
       
        result = fileChosser.showOpenDialog(frame);
        if(result == JFileChooser.APPROVE_OPTION){
        File lineFile= fileChosser.getSelectedFile();
         Path linePath = Paths.get(lineFile.getAbsolutePath());
         List<String> lineLines = Files.readAllLines(linePath);
          for (String lineLine : lineLines) {
            String[] arr = lineLine.split(",");
            String str1 = arr[0];
            String str2 = arr[1];
            String str3 = arr[2];
            String str4 = arr[3];
            int invCode = Integer.parseInt(str1);
            double price = Double.parseDouble(str3);
            int count = Integer.parseInt(str4);
            invoiceHeader inv = frame.getInvObject(invCode);
            invoiceLine line = new invoiceLine(str2, price, count, inv);
            inv.getLines().add(line);
            
          }
          System.out.println("files read");
        System.out.println("************************ \n Reading Headers \n " + invoiceHeaders);
        System.out.println("************************ \n Reading Lines \n " + lineLines);
    }    
        InvoiceHeaderTblModel headerTblModel = new InvoiceHeaderTblModel(invoiceHeaders);
        frame.setHeaderTblModel(headerTblModel);
        frame.getInvoice_header_Table().setModel(headerTblModel);
        
    }
    }catch (IOException ex) {
        JOptionPane.showMessageDialog(frame, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
              JOptionPane.showMessageDialog(frame, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

            }
    }

    private void saveFiles() {
       ArrayList<invoiceHeader> invoicesArray = frame.getInvoicesArray();
       JFileChooser fchoos = new JFileChooser();
        try {
            int result = fchoos.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION && invoicesArray !=null) {
              File headFile = fchoos.getSelectedFile();
              FileWriter headerfw = new FileWriter(headFile);
              String headers = "";
              String lines = "";
              for (invoiceHeader invc :invoicesArray) {
                headers += invc.toString();
                headers += "\n";
                for (invoiceLine ln : invc.getLines()) {
                     lines += ln.toString();
                     lines += "\n";
                }
            }
            
            headers = headers.substring(0, headers.length()-1);
            lines = lines.substring(0, lines.length()-1);
            result = fchoos.showSaveDialog(frame);
            File lineFile = fchoos.getSelectedFile();
            FileWriter linefw = new FileWriter(lineFile);
            headerfw.write(headers);
            linefw.write(lines);
            headerfw.close();
            linefw.close();
            }
        } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            
        }
    }
        
    private void createNewInvoice() {
        headerDialog = new HeaderDialog(frame);
        headerDialog.setVisible(true);
    }
    
    private void deleteLine() {  
        int selectedlineIndex = frame.getInvoice_line_table().getSelectedRow();
        int selectedHeaderIndex = frame.getInvoice_header_Table().getSelectedRow();
        if ( selectedlineIndex != -1 ) {
        frame.getLinesArray().remove(selectedlineIndex);
        InvoiceLineTblModel lineTblModel = (InvoiceLineTblModel) frame.getInvoice_line_table().getModel();
        frame.getHeaderTblModel().fireTableDataChanged();
        frame.getInvoice_header_Table().setRowSelectionInterval(selectedHeaderIndex, selectedHeaderIndex);

        lineTblModel.fireTableDataChanged();
       
       
        }
        
    }

    private void deleteInvoice() {
        int selectedInvoiceIndex = frame.getInvoice_header_Table().getSelectedRow();
        if ( selectedInvoiceIndex != -1 ) {
            
        frame.getInvoicesArray().remove(selectedInvoiceIndex);
        frame.getHeaderTblModel().fireTableDataChanged();   
        frame.getInvoice_line_table().setModel(new InvoiceLineTblModel(null));
        frame.setLinesArray(null);
        frame.getCustomerName().setText("");
        frame.getInvoiceNo().setText("");
        frame.getInvoiceTotal().setText("");
        frame.getInvoiceDate().setText("");
            
        }
    
    }

    private void createNewLine() {
        lineDialog = new LineDialog(frame);
        lineDialog.setVisible(true);
    }


    private void OkNewInvoiceDialog() {
        headerDialog.setVisible(false);
        
        String customerName = headerDialog.getCustNameField().getText();
        String str = headerDialog.getInvDateField().getText();
        Date de = new Date();
        try{
            de = InvoiceForm.DateFormate.parse(str);
        } catch (ParseException ex){
            JOptionPane.showMessageDialog(frame , "can't pasre date , resetting to today." , "Invalid date format" , JOptionPane.ERROR_MESSAGE);
        }
        int invNu = 0;
        for(invoiceHeader inv : frame.getInvoicesArray()){
            if (inv.getNum() > invNu) invNu = inv.getNum();
        }
        invNu++;
        invoiceHeader newInv = new invoiceHeader(invNu , customerName , de );
        frame.getInvoicesArray().add(newInv);
        frame.getHeaderTblModel().fireTableDataChanged();
        headerDialog.dispose();
        headerDialog = null;
    }

    private void CancelNewInvoiceDialog() {
        headerDialog.setVisible(false);
        headerDialog.dispose();
        headerDialog = null;
    }

    private void createLineDialogOk() {
        lineDialog.setVisible(false);
        
        String name = lineDialog.getItemNameField().getText();
        String count = lineDialog.getItemCountField().getText();
        String price = lineDialog.getItemPriceField().getText();
        int thecount = 1;
        double theprice = 1;
        
        try { 
            thecount = Integer.parseInt(count);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Can't convert number" , "Invalid Number format" , JOptionPane.ERROR_MESSAGE );
        }
        
         try { 
        theprice = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Can't convert price" , "Invalid Number format" , JOptionPane.ERROR_MESSAGE );
        }
         
        int selectedInvoiceHdr = frame.getInvoice_header_Table().getSelectedRow();
           if (selectedInvoiceHdr != -1) {
               invoiceHeader invHeader = frame.getInvoicesArray().get(selectedInvoiceHdr);
               invoiceLine line = new invoiceLine(name, theprice, thecount, invHeader);
               frame.getLinesArray().add(line);
               InvoiceLineTblModel lineTblModel = (InvoiceLineTblModel) frame.getInvoice_line_table().getModel();
               lineTblModel.fireTableDataChanged();
               frame.getHeaderTblModel().fireTableDataChanged();        
           }
        frame.getInvoice_header_Table().setRowSelectionInterval(selectedInvoiceHdr, selectedInvoiceHdr);  
        lineDialog.dispose();
        lineDialog = null;
    }

    private void createLineDialogCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

    
    
}
