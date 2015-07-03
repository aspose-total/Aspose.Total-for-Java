package com.aspose.maven;

import com.aspose.maven.utils.AsposeConstants;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.NbBundle;

/**
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 */
public class AsposeMavenPanel extends JPanel {

    private AsposeWizardPanelComponent panel;

    public AsposeMavenPanel(AsposeWizardPanelComponent panel) {
        initComponents();
        this.panel = panel;
    }

    @Override
    public String getName() {
        return "Aspose Maven Dependencies";
    }

    private void initComponents() {

        jPanel1 = new JPanel();
        jLabel5 = new JLabel();
        jPanel2 = new JPanel();
        jPanel6 = new JPanel();
        jCheckBoxSelectAll = new JCheckBox();
        jCheckBoxAsposeCells = new JCheckBox();
        jCheckBoxAsposeWords = new JCheckBox();
        jCheckBoxAsposePdf = new JCheckBox();
        jCheckBoxAsposeSlides = new JCheckBox();
        jCheckBoxAsposeBarCode = new JCheckBox();
        jCheckBoxAsposeTasks = new JCheckBox();
        jCheckBoxAsposeEmail = new JCheckBox();
        jCheckBoxAsposeOCR = new JCheckBox();
        jCheckBoxAsposeImaging = new JCheckBox();
        jCheckBoxAsposeDiagram = new JCheckBox();

        jPanel4 = new JPanel();
        jLabelCommonUses = new JLabel();
        jLabelMessage = new JLabel();

        //======== this ========
        //======== jPanel1 ========
        {
            jPanel1.setBackground(Color.white);
            jPanel1.setBorder(new EtchedBorder());
            jPanel1.setForeground(Color.white);

            //---- jLabel5 ----
            jLabel5.setText(NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jLabel5.text"));
            org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jLabel5.text")); // NOI18N

            jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel5.setIcon(icon); // NOI18N
            jLabel5.setDoubleBuffered(true);
            jLabel5.setOpaque(true);
            jLabel5.addComponentListener(new java.awt.event.ComponentAdapter() {
                public void componentResized(java.awt.event.ComponentEvent evt) {
                    jLabel1ComponentResized(evt);
                }
            });
            GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup()
                    .addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 390, Short.MAX_VALUE)
            );
            jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(0, 0, Short.MAX_VALUE))
            );
        }

        //======== jPanel2 ========
        {

            GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                    jPanel2Layout.createParallelGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
            );
            jPanel2Layout.setVerticalGroup(
                    jPanel2Layout.createParallelGroup()
                    .addGap(0, 285, Short.MAX_VALUE)
            );
        }

        //======== jPanel6 ========
        {
            jPanel6.setBorder(new TitledBorder(NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jPanel6.border.title")));
            org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxSelectAll, org.openide.util.NbBundle.getMessage(AsposeMavenBasicWizardPanel.class, "AsposeMavenPanel.jCheckBoxSelectAll.text")); // NOI18N

            jCheckBoxSelectAll.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jCheckBoxSelectAllActionPerformed(e);
                }
            });

            //---- jCheckBoxAsposeCells ----
            org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxAsposeCells, org.openide.util.NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jCheckBoxAsposeCells.text")); // NOI18N

            jCheckBoxAsposeCells.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jCheckBoxAsposeCellsActionPerformed(e);
                }
            });

            //---- jCheckBoxAsposeWords ----
            org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxAsposeWords, org.openide.util.NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jCheckBoxAsposeWords.text")); // NOI18N

            jCheckBoxAsposeWords.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jCheckBoxAsposeWordsActionPerformed(e);
                }
            });

            //---- jCheckBoxAsposePdf ----
            org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxAsposePdf, org.openide.util.NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jCheckBoxAsposePdf.text")); // NOI18N

            jCheckBoxAsposePdf.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jCheckBoxAsposePdfActionPerformed(e);
                }
            });

            //---- jCheckBoxAsposeSlides ----
            org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxAsposeSlides, org.openide.util.NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jCheckBoxAsposeSlides.text")); // NOI18N

            jCheckBoxAsposeSlides.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jCheckBoxAsposeSlidesActionPerformed(e);
                }
            });

            //---- jCheckBoxAsposeTasks ----
            org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxAsposeBarCode, org.openide.util.NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jCheckBoxAsposeBarCode.text")); // NOI18N

            jCheckBoxAsposeTasks.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jCheckBoxAsposeTasksActionPerformed(e);
                }
            });

            //---- jCheckBoxAsposeBarCode ----
            org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxAsposeEmail, org.openide.util.NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jCheckBoxAsposeEmail.text")); // NOI18N

            jCheckBoxAsposeBarCode.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jCheckBoxAsposeBarCodeActionPerformed(e);
                }
            });

            //---- jCheckBoxAsposeEmail ----
            org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxAsposeOCR, org.openide.util.NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jCheckBoxAsposeOCR.text")); // NOI18N

            jCheckBoxAsposeEmail.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jCheckBoxAsposeEmailActionPerformed(e);
                }
            });

            //---- jCheckBoxAsposeOCR ----
            org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxAsposeImaging, org.openide.util.NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jCheckBoxAsposeImaging.text")); // NOI18N

            jCheckBoxAsposeOCR.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jCheckBoxAsposeOCRActionPerformed(e);
                }
            });

            //---- jCheckBoxAsposeImaging ----
            org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxAsposeTasks, org.openide.util.NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jCheckBoxAsposeTasks.text")); // NOI18N

            jCheckBoxAsposeImaging.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jCheckBoxAsposeImagingActionPerformed(e);
                }
            });

            //---- jCheckBoxAsposeDiagram ----
            org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxAsposeDiagram, org.openide.util.NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jCheckBoxAsposeDiagram.text")); // NOI18N

            jCheckBoxAsposeDiagram.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jCheckBoxAsposeDiagramActionPerformed(e);
                }
            });

            GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
            jPanel6.setLayout(jPanel6Layout);
            jPanel6Layout.setHorizontalGroup(
                    jPanel6Layout.createParallelGroup()
                    .addGroup(jPanel6Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel6Layout.createParallelGroup()
                                    .addComponent(jCheckBoxSelectAll)
                                    .addComponent(jCheckBoxAsposeCells)
                                    .addComponent(jCheckBoxAsposeWords)
                                    .addComponent(jCheckBoxAsposePdf)
                                    .addComponent(jCheckBoxAsposeSlides)
                                    .addComponent(jCheckBoxAsposeBarCode)
                                    .addComponent(jCheckBoxAsposeTasks)
                                    .addComponent(jCheckBoxAsposeEmail)
                                    .addComponent(jCheckBoxAsposeOCR)
                                    .addComponent(jCheckBoxAsposeImaging)
                                    .addComponent(jCheckBoxAsposeDiagram))
                            .addContainerGap(8, Short.MAX_VALUE))
            );
            jPanel6Layout.setVerticalGroup(
                    jPanel6Layout.createParallelGroup()
                    .addGroup(jPanel6Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jCheckBoxSelectAll)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jCheckBoxAsposeCells)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jCheckBoxAsposeWords)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jCheckBoxAsposePdf)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jCheckBoxAsposeSlides)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jCheckBoxAsposeBarCode)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jCheckBoxAsposeTasks)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jCheckBoxAsposeEmail)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jCheckBoxAsposeOCR)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jCheckBoxAsposeImaging)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jCheckBoxAsposeDiagram)
                            .addContainerGap(12, Short.MAX_VALUE))
            );
        }

        //======== jPanel4 ========
        {
            jPanel4.setBorder(new TitledBorder(NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jPanel4.border.title")));
            jPanel4.setPreferredSize(new java.awt.Dimension(333, 285));

            //---- jLabelCommonUses ----
            org.openide.awt.Mnemonics.setLocalizedText(jLabelCommonUses, NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jLabelCommonUses.text")); // NOI18N
            jLabelCommonUses.setToolTipText(NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jLabelCommonUses.toolTipText")); // NOI18N

            GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
            jPanel4.setLayout(jPanel4Layout);
            jPanel4Layout.setHorizontalGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabelCommonUses, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 1, Short.MAX_VALUE))
            );
            jPanel4Layout.setVerticalGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabelCommonUses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
        }

        //---- jLabelMessage ----
        org.openide.awt.Mnemonics.setLocalizedText(jLabelMessage, org.openide.util.NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jLabelMessage.text")); // NOI18N
        jLabelMessage.setToolTipText(NbBundle.getMessage(AsposeMavenPanel.class, "AsposeMavenPanel.jLabelMessage.toolTipText"));

        GroupLayout layout = new GroupLayout(this);

        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabelMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 364, Short.MAX_VALUE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jPanel2, 0, 0, 0))
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20))
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup()
                                                .addComponent(jPanel6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                       .addComponent(jLabelMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE)
                                        )))
        );

    }

    private void jCheckBoxSelectAllActionPerformed(ActionEvent e) {
        setSelectionOfAPIs(jCheckBoxSelectAll.isSelected());
        validateDialog();
    }

    private void jCheckBoxAsposeCellsActionPerformed(ActionEvent e) {
        validateDialog();
    }

    private void jCheckBoxAsposeWordsActionPerformed(ActionEvent e) {
        validateDialog();
    }

    private void jCheckBoxAsposePdfActionPerformed(ActionEvent e) {
        validateDialog();
    }

    private void jCheckBoxAsposeSlidesActionPerformed(ActionEvent e) {
        validateDialog();
    }

    private void jCheckBoxAsposeBarCodeActionPerformed(ActionEvent e) {
        validateDialog();
    }

    private void jCheckBoxAsposeTasksActionPerformed(ActionEvent e) {
        validateDialog();
    }

    private void jCheckBoxAsposeEmailActionPerformed(ActionEvent e) {
        validateDialog();
    }

    private void jCheckBoxAsposeOCRActionPerformed(ActionEvent e) {
        validateDialog();
    }

    private void jCheckBoxAsposeImagingActionPerformed(ActionEvent e) {
        validateDialog();
    }

    private void jCheckBoxAsposeDiagramActionPerformed(ActionEvent e) {
        validateDialog();
    }

    private JPanel jPanel1;
    private JLabel jLabel5;
    private JPanel jPanel2;
    private JPanel jPanel6;
    private JCheckBox jCheckBoxSelectAll;
    private JCheckBox jCheckBoxAsposeCells;
    private JCheckBox jCheckBoxAsposeWords;
    private JCheckBox jCheckBoxAsposePdf;
    private JCheckBox jCheckBoxAsposeSlides;
    private JCheckBox jCheckBoxAsposeBarCode;
    private JCheckBox jCheckBoxAsposeTasks;
    private JCheckBox jCheckBoxAsposeEmail;
    private JCheckBox jCheckBoxAsposeOCR;
    private JCheckBox jCheckBoxAsposeImaging;
    private JCheckBox jCheckBoxAsposeDiagram;
    private JPanel jPanel4;
    private JLabel jLabelCommonUses;
    private JLabel jLabelMessage;
    private final ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/resources/long_banner.png"));

    @Override
    public void addNotify() {
        super.addNotify();
    }

    boolean valid(WizardDescriptor wizardDescriptor) {

        if (isAPISelected()) {
            return true;
        }
        return false;
    }

    void validate(WizardDescriptor d) throws WizardValidationException {
        // nothing to validate
    }

    private boolean validateDialog() {
        panel.fireChangeEvent();
        if (!isAPISelected()) {
            diplayMessage(AsposeConstants.IS_API_SELECTED, true);
            return false;
        }
        clearMessage();
        return true;

    }

    public boolean isAPISelected() {
        if (jCheckBoxAsposeCells.isSelected()) {
            return true;
        }
        if (jCheckBoxAsposeWords.isSelected()) {
            return true;
        }
        if (jCheckBoxAsposePdf.isSelected()) {
            return true;
        }
        if (jCheckBoxAsposeSlides.isSelected()) {
            return true;
        }
        if (jCheckBoxAsposeBarCode.isSelected()) {
            return true;
        }
        if (jCheckBoxAsposeTasks.isSelected()) {
            return true;
        }
        if (jCheckBoxAsposeEmail.isSelected()) {
            return true;
        }
        if (jCheckBoxAsposeOCR.isSelected()) {
            return true;
        }
        if (jCheckBoxAsposeImaging.isSelected()) {
            return true;
        }
        if (jCheckBoxAsposeDiagram.isSelected()) {
            return true;
        }

        return false;
    }

    public void diplayMessage(String message, boolean error) {
        if (error) {
            jLabelMessage.setForeground(Color.RED);

        } else {
            jLabelMessage.setForeground(Color.GREEN);

        }
        jLabelMessage.setText(message);
        jLabelMessage.update(jLabelMessage.getGraphics());
    }

    public void clearMessage() {
        jLabelMessage.setText("");
    }

    private void jLabel1ComponentResized(java.awt.event.ComponentEvent evt) {
        int labelwidth = jLabel5.getWidth();
        int labelheight = jLabel5.getHeight();
        Image img = icon.getImage();
        jLabel5.setIcon(new ImageIcon(img.getScaledInstance(labelwidth, labelheight, Image.SCALE_FAST)));
    }

    //=========================================================================
    private void setSelectionOfAPIs(boolean value) {
        jCheckBoxAsposeBarCode.setSelected(value);
        jCheckBoxAsposeTasks.setSelected(value);
        jCheckBoxAsposeCells.setSelected(value);
        jCheckBoxAsposeEmail.setSelected(value);
        jCheckBoxAsposeImaging.setSelected(value);
        jCheckBoxAsposeDiagram.setSelected(value);
        jCheckBoxAsposeOCR.setSelected(value);
        jCheckBoxAsposePdf.setSelected(value);
        jCheckBoxAsposeSlides.setSelected(value);
        jCheckBoxAsposeWords.setSelected(value);

    }

    public JCheckBox getjCheckBoxAsposeCells() {
        return jCheckBoxAsposeCells;
    }

    public JCheckBox getjCheckBoxAsposeWords() {
        return jCheckBoxAsposeWords;
    }

    public JCheckBox getjCheckBoxAsposePdf() {
        return jCheckBoxAsposePdf;
    }

    public JCheckBox getjCheckBoxAsposeSlides() {
        return jCheckBoxAsposeSlides;
    }

    public JCheckBox getjCheckBoxAsposeBarCode() {
        return jCheckBoxAsposeBarCode;
    }

    public JCheckBox getjCheckBoxAsposeTasks() {
        return jCheckBoxAsposeTasks;
    }

    public JCheckBox getjCheckBoxAsposeEmail() {
        return jCheckBoxAsposeEmail;
    }

    public JCheckBox getjCheckBoxAsposeOCR() {
        return jCheckBoxAsposeOCR;
    }

    public JCheckBox getjCheckBoxAsposeImaging() {
        return jCheckBoxAsposeImaging;
    }

    public JCheckBox getjCheckBoxAsposeDiagram() {
        return jCheckBoxAsposeDiagram;
    }

}
