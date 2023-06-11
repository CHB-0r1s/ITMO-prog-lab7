package src.GUI.CommandButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotNullStringsDialogWindow extends JDialog {
    private JTextField textField;
    private JButton okButton;

    public NotNullStringsDialogWindow(Frame parent, String title) {
        super(parent, title, true);

        textField = new JTextField(20);
        okButton = new JButton("OK");
        okButton.setEnabled(false);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField.getText().trim().isEmpty()) {
                    okButton.setEnabled(false);
                } else {
                    okButton.setEnabled(true);
                }
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textField, BorderLayout.NORTH);
        panel.add(okButton, BorderLayout.SOUTH);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public String getInputValue() {
        return textField.getText();
    }
}