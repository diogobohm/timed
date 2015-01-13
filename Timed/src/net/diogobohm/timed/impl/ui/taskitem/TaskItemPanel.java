/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.taskitem;

import com.jgoodies.binding.adapter.Bindings;
import net.diogobohm.timed.api.ui.mvc.MVCView;
import net.diogobohm.timed.api.ui.mvc.model.formatter.TaskTimeFormatter;

/**
 *
 * @author diogo
 */
public class TaskItemPanel extends javax.swing.JPanel implements MVCView {

    private static final TaskTimeFormatter TIME_FORMATTER = new TaskTimeFormatter();

    private final TaskItemModel model;

    /**
     * Creates new form TaskItemPanel
     */
    public TaskItemPanel(TaskItemModel model) {
        this.model = model;

        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startTimeLabel = new javax.swing.JLabel();
        timeSplitLabel = new javax.swing.JLabel();
        stopTimeLabel = new javax.swing.JLabel();
        taskNameLabel = new javax.swing.JLabel();
        tagListLabel = new javax.swing.JLabel();
        editTaskButton = new javax.swing.JButton();

        setOpaque(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        startTimeLabel.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        startTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startTimeLabel.setText("StartTime");
        Bindings.bind(startTimeLabel, model.getStartDateHolder().getRenderer());

        timeSplitLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeSplitLabel.setText(":");

        stopTimeLabel.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        stopTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stopTimeLabel.setText("StopTime");
        stopTimeLabel.setPreferredSize(new java.awt.Dimension(35, 16));
        Bindings.bind(stopTimeLabel, model.getStopDateHolder().getRenderer());

        taskNameLabel.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        taskNameLabel.setText("TaskName");
        Bindings.bind(taskNameLabel, model.getTaskLabelHolder().getRenderer());

        tagListLabel.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        tagListLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        tagListLabel.setText("#Tag1, #Tag2");
        Bindings.bind(tagListLabel, model.getTagSetHolder().getRenderer());

        editTaskButton.setText("Edit");
        editTaskButton.setOpaque(false);
        editTaskButton.addActionListener(model.getEditTaskAction());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(startTimeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeSplitLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stopTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(taskNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(tagListLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editTaskButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startTimeLabel)
                    .addComponent(timeSplitLabel)
                    .addComponent(stopTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(taskNameLabel)
                    .addComponent(tagListLabel)
                    .addComponent(editTaskButton))
                .addGap(9, 9, 9))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        editTaskButton.doClick();
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton editTaskButton;
    private javax.swing.JLabel startTimeLabel;
    private javax.swing.JLabel stopTimeLabel;
    private javax.swing.JLabel tagListLabel;
    private javax.swing.JLabel taskNameLabel;
    private javax.swing.JLabel timeSplitLabel;
    // End of variables declaration//GEN-END:variables
}
