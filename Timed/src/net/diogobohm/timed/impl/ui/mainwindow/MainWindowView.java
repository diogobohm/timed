/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.mainwindow;

import com.jgoodies.binding.adapter.Bindings;
import java.awt.event.ActionListener;
import net.diogobohm.timed.api.ui.mvc.MVCView;
import net.diogobohm.timed.impl.ui.tasklist.TaskListPanel;

/**
 *
 * @author diogo
 */
public class MainWindowView extends javax.swing.JFrame implements MVCView {

    private final MainWindowModel model;
    private final TaskListPanel taskListPanel;
    private final ActionListener newTaskAction;
    private final ActionListener showOverviewAction;

    /**
     * Creates new form MainWindow
     */
    public MainWindowView(MainWindowModel model, TaskListPanel taskListPanel, ActionListener newTaskAction,
            ActionListener showOverviewAction) {
        this.model = model;
        this.taskListPanel = taskListPanel;
        this.newTaskAction = newTaskAction;
        this.showOverviewAction = showOverviewAction;

        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox2 = new javax.swing.JComboBox();
        currentTaskNameLabel = new javax.swing.JLabel();
        tagSetLabel = new javax.swing.JLabel();
        newTaskTagsField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        showOverviewButton = new javax.swing.JButton();
        addEarlierActivityButton = new javax.swing.JButton();
        dayWorkedTimeLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        newActivityComboBox = new javax.swing.JComboBox();
        newProjectComboBox = new javax.swing.JComboBox();
        currentProjectLabel = new javax.swing.JLabel();
        workedTimeValueLabel = new javax.swing.JLabel();

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TimeD");
        setMinimumSize(new java.awt.Dimension(640, 400));
        setPreferredSize(new java.awt.Dimension(640, 400));

        currentTaskNameLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        currentTaskNameLabel.setText("Current Task Name");
        Bindings.bind(currentTaskNameLabel, model.getCurrentActivityHolder().getRenderer());

        tagSetLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        tagSetLabel.setText("Current Task Tags");
        Bindings.bind(tagSetLabel, model.getCurrentTaskTagsHolder().getRenderer());

        newTaskTagsField.setText("NewTaskTags");
        newTaskTagsField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newTaskTagsFieldActionPerformed(evt);
            }
        });
        Bindings.bind(newTaskTagsField, model.getNewTaskTagsHolder());

        jButton1.setText("Start Tracking");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Today");

        showOverviewButton.setText("Show Overview");
        showOverviewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showOverviewButtonActionPerformed(evt);
            }
        });

        addEarlierActivityButton.setText("Add Earlier Activity");

        dayWorkedTimeLabel.setText("Total worked time:");

        jScrollPane1.setViewportView(taskListPanel);

        newActivityComboBox.setEditable(true);
        newActivityComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        newActivityComboBox.setModel(model.getNewActivityComboHolder());

        newProjectComboBox.setEditable(true);
        newProjectComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        newProjectComboBox.setModel(model.getNewProjectComboHolder());

        currentProjectLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        currentProjectLabel.setText("Current Project");
        Bindings.bind(currentProjectLabel, model.getCurrentProjectHolder().getRenderer());

        workedTimeValueLabel.setText("00:00");
        Bindings.bind(workedTimeValueLabel, model.getWorkedTimeHolder());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dayWorkedTimeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(workedTimeValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addEarlierActivityButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showOverviewButton))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(newActivityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newProjectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newTaskTagsField, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(currentProjectLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tagSetLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(currentTaskNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(currentTaskNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tagSetLabel)
                    .addComponent(currentProjectLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(newTaskTagsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(newActivityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(newProjectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addEarlierActivityButton)
                    .addComponent(showOverviewButton)
                    .addComponent(dayWorkedTimeLabel)
                    .addComponent(workedTimeValueLabel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newTaskTagsFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newTaskTagsFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newTaskTagsFieldActionPerformed

    private void showOverviewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showOverviewButtonActionPerformed
        showOverviewAction.actionPerformed(evt);
    }//GEN-LAST:event_showOverviewButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        newTaskAction.actionPerformed(evt);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEarlierActivityButton;
    private javax.swing.JLabel currentProjectLabel;
    private javax.swing.JLabel currentTaskNameLabel;
    private javax.swing.JLabel dayWorkedTimeLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox newActivityComboBox;
    private javax.swing.JComboBox newProjectComboBox;
    private javax.swing.JTextField newTaskTagsField;
    private javax.swing.JButton showOverviewButton;
    private javax.swing.JLabel tagSetLabel;
    private javax.swing.JLabel workedTimeValueLabel;
    // End of variables declaration//GEN-END:variables
}
