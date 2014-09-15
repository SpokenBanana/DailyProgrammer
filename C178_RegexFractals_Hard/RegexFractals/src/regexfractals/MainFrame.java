/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package regexfractals;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 *
 * @author David
 */
public class MainFrame extends JFrame implements ActionListener{
    JTextField pattern;
    JButton done;
    RegexFractals rf;
    JCheckBox colored;
    JComboBox<String> regexes;
    
    public static void main(String[] args) {
        new MainFrame();
    }
    public MainFrame() {
        rf = new RegexFractals(new Dimension(256,256));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(rf, BorderLayout.CENTER);
        pattern = new JTextField(".*(?:13|31)(.*)",15);
        regexes = new JComboBox<String>();
        regexes.addItem(".*1.*");
        regexes.addItem(".*12(.*)");
        regexes.addItem(".*(?:12|21|34|43)(.*)");
        regexes.addItem(".*?[24][13]*[24]*[13](.*)");
        regexes.addItem("(?:..)*?4(.*)|.*2(?:..)*");
        regexes.addItem(".*(?:13|31)(.*)");
        
        regexes.addActionListener(this);
        
        done = new JButton("Enter");
        done.addActionListener(this);
        pattern.addActionListener(this);
        colored = new JCheckBox();
        colored.setSelected(true);
        colored.addActionListener(this);
        JPanel tools = new JPanel();
        
        tools.add(new JLabel("Enter new pattern"));
        tools.add(pattern);
        tools.add(regexes);
        tools.add(done);
        tools.add(new JLabel("Color"));
        tools.add(colored);
        
        
        add(tools, BorderLayout.NORTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pattern || e.getSource() == done) {
            rf.changePattern(pattern.getText(), colored.isSelected());
        }
        if (e.getSource() == regexes) {
            pattern.setText((String)regexes.getSelectedItem());
            rf.changePattern(pattern.getText(), colored.isSelected());
        }
    }
}
