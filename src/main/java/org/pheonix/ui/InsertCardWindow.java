package org.pheonix.ui;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.pheonix.business.QueryHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class InsertCardWindow {
    JFrame frame;
    Container pane;
    GroupLayout layout;
    JComboBox tagsComboBox;
    JButton cancelButton, insertButton;
    QueryHandler queryHandler;
    Vector<String> tags;

    ImmutablePair<JLabel, JTextField> [] insertFields = new ImmutablePair[]{
            new ImmutablePair(new JLabel("Name"), new JTextField("")),
            new ImmutablePair(new JLabel("Mana"), new JTextField("")),
            new ImmutablePair(new JLabel("Card Type"), new JTextField("")),
            new ImmutablePair(new JLabel("Subtypes"), new JTextField("")),
            new ImmutablePair(new JLabel("Simple Abilities"), new JTextField("")),
            new ImmutablePair(new JLabel("Complex Abilities"), new JTextField("")),
            new ImmutablePair(new JLabel("Card Set"), new JTextField("")),
            new ImmutablePair(new JLabel("Power/Toughness"), new JTextField("")),
            new ImmutablePair(new JLabel("Colour Identity"), new JTextField("")),
            new ImmutablePair(new JLabel("Tags"), new JTextField("")),
            new ImmutablePair(new JLabel("Lore Text"), new JTextField("")),
            new ImmutablePair(new JLabel("Image Path"), new JTextField(""))
    };

    public InsertCardWindow(QueryHandler newQueryHandler, String uri) {
        frame = new JFrame();
        pane = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layout = new GroupLayout(pane);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        queryHandler = newQueryHandler;
        tags = queryHandler.getTags();

        tagsComboBox = new JComboBox(tags);

        GroupLayout.ParallelGroup horizontalParGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup verticalSeqGroup = layout.createSequentialGroup();

        for(ImmutablePair<JLabel, JTextField> pair: insertFields) {
            JLabel label = pair.left;
            JTextField textField = pair.right;

            if(label.getText().equalsIgnoreCase("Image Path")){
                // Default image location
                textField.setText(uri.replace("\"",""));
            }

            textField .setSize(100,25);

            textField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });

            horizontalParGroup
                    .addComponent(label)
                    .addComponent(textField);

            verticalSeqGroup
                    .addComponent(label)
                    .addComponent(textField);
        }

        layout.setHorizontalGroup(horizontalParGroup);
        layout.setVerticalGroup(verticalSeqGroup);

        frame.setLayout(layout);
        frame.pack();
        frame.setSize(new Dimension(800, 600));
        frame.setVisible(true);
    }
}
