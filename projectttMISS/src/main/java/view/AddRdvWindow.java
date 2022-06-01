/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import controller.RdvJpaController;
import controller.PatientsJpaController;
import controller.PersonnesJpaController;
import controller.DocteurJpaController;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import model.Patients;
import model.Docteur;
import model.Personnes;
import model.Rdv;

/**
 *
 * @author pc
 */
public class AddRdvWindow extends javax.swing.JFrame {

    private final EntityManagerFactory emfac = Persistence.createEntityManagerFactory("bdprojecct_PU");
    private final RdvJpaController rdvCtrl = new RdvJpaController(emfac);
    private final PatientsJpaController patientsCtrl = new PatientsJpaController(emfac);
    private final DocteurJpaController docteurCtrl = new DocteurJpaController(emfac);
    private final PersonnesJpaController personnesCtrl = new PersonnesJpaController(emfac);
    /*dd-M-yyyy hh:mm:ss*/
    private final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    
    private static final Logger LOGGER = LogManager.getLogger(AddRdvWindow.class.getName());
    
    Rdv rdv = null;
    
    /**
     * Creates new form AddRdvWindow
     */
    public AddRdvWindow() {
        initComponents();
    }
    
    public void setRdv(Rdv rdv){
        this.rdv = rdv;
        
        addPatientsPanel.setPatient(rdv.getPatients());
        addDocteurPanel.setDocteur(rdv.getDocteur());
        presenceComboBox.setSelectedItem(rdv.getPresence());
        pecComboBox.setSelectedItem(rdv.getPec());
        daterdvTextField.setText(fmt.format(rdv.getDaterdv()));
        heurerdvTextField.setText(fmt.format(rdv.getHeurerdv()));
    }
    
    public Rdv getRdv(){
        updateRdv();
                
        return rdv;
    }
    private void updateRdv (){
        if (rdv == null){
            rdv = new model.Rdv(); 
        }
        
        try {
            rdv.setDaterdv(fmt.parse(daterdvTextField.getText()));
            rdv.setHeurerdv(fmt1.parse(heurerdvTextField.getText())); 
        } catch (ParseException ex) {
            LOGGER.error("Error setting person date of birth", ex);
        }
        
        rdv.setPresence((String) presenceComboBox.getSelectedItem());
        rdv.setPec((String) pecComboBox.getSelectedItem());
        
        rdv.setPatients(addPatientsPanel.getPatients());
        rdv.setDocteur(addDocteurPanel.getDocteur());
        /*rdv.setRenewable(((String)renewableComboBox.getSelectedItem()).getBytes());*/
        /*patients.setPseudo(pseudoTextField.getText());*/
        
        
        
            
    }  
    
    private void SaveActionPerformed(java.awt.event.ActionEvent evt) { 
        updateRdv(); 
          
        // Create docteur if necessary
        if( rdv.getDocteur() == null ){
            rdvCtrl.create(rdv);
            LOGGER.debug("Created new docteur (id = %d)".formatted(rdv.getDocteur()));
        }
        // Create patient if necessary
        if( rdv.getPatients() == null ){
            rdvCtrl.create(rdv);
            LOGGER.debug("Created new docteur (id = %d)".formatted(rdv.getPatients()));
        }
        
        // Create prescription if necessary:
        if( rdv.getIdrdv() == null ){
           rdvCtrl.create(rdv);
           LOGGER.debug("Created new rdv (id = %d)".formatted(rdv.getIdrdv()));
        }
        
        
        try{ 
            patientsCtrl.edit(rdv.getPatients());
            docteurCtrl.edit(rdv.getDocteur());
            rdvCtrl.edit(rdv);
            LOGGER.debug("Edited rdv (id = %d)".formatted(rdv.getIdrdv()));
        } catch (NonexistentEntityException ex) {
            LOGGER.error("Couldn't edit docteur", ex);
        } catch (Exception ex) {
            LOGGER.error("Couldn't edit docteur", ex);
        }
        
      rdv.setIdrdv(rdvCtrl.getRdvCount());
    }

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        daterdvTextField = new javax.swing.JTextField();
        heurerdvTextField = new javax.swing.JTextField();
        presenceComboBox = new javax.swing.JComboBox<>();
        pecComboBox = new javax.swing.JComboBox<>();
        addPatientsPanel = new view.AddPatientsPanel();
        addDocteurPanel = new view.AddDocteurPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Add Rdv");

        jLabel4.setText("DateRdv:");

        jLabel5.setText("HeureRdv:");

        jLabel6.setText("Presence:");

        jLabel7.setText("pec:");

        saveButton.setText("save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("cancel");

        daterdvTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                daterdvTextFieldActionPerformed(evt);
            }
        });

        heurerdvTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heurerdvTextFieldActionPerformed(evt);
            }
        });

        presenceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NO", "YES", "ABSENT" }));

        pecComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NO", "YES" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addComponent(pecComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addComponent(presenceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63)
                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(118, 118, 118)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(heurerdvTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(daterdvTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(209, 209, 209)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(addPatientsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(addDocteurPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addPatientsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(addDocteurPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(heurerdvTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(daterdvTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pecComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(presenceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void daterdvTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_daterdvTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_daterdvTextFieldActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveButtonActionPerformed

    private void heurerdvTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_heurerdvTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_heurerdvTextFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.AddDocteurPanel addDocteurPanel;
    private view.AddPatientsPanel addPatientsPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField daterdvTextField;
    private javax.swing.JTextField heurerdvTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JComboBox<String> pecComboBox;
    private javax.swing.JComboBox<String> presenceComboBox;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
