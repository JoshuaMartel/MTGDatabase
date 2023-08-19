package org.pheonix.ui;

import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.swing.* ;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

final public class SearchWindow {

    JFrame frame;
    Container pane;
    GroupLayout layout;
    JButton searchButton, cancelButton, editSearchArea;
    JTextArea searchCriteriaArea;
    Map<String, Integer> textAreaLinesMap;
    JScrollPane scrollPane;
    ImmutablePair<JCheckBox, JTextField> [] checkBoxes = new ImmutablePair[] {
            new ImmutablePair<>(new JCheckBox("Name"), new JTextField("")),
            new ImmutablePair<>(new JCheckBox("Mana"), new JTextField("")),
            new ImmutablePair<>(new JCheckBox("Card Type"), new JTextField("")),
            new ImmutablePair<>(new JCheckBox("Card Subtype"), new JTextField("")),
            new ImmutablePair<>(new JCheckBox("Card Set "), new JTextField("")),
            new ImmutablePair<>(new JCheckBox("Power"), new JTextField("")),
            new ImmutablePair<>(new JCheckBox("Toughness"), new JTextField("")),
            new ImmutablePair<>(new JCheckBox("Tags"), new JTextField("")),
            new ImmutablePair<>(new JCheckBox("Colours"), new JTextField("")),
    };

    public SearchWindow() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane = frame.getContentPane();
        layout = new GroupLayout(pane);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        searchCriteriaArea = new JTextArea(15,50);
        searchCriteriaArea.setEditable(false);
        textAreaLinesMap = new HashMap<>();

        scrollPane = new JScrollPane(searchCriteriaArea);

        editSearchArea = new JButton("Edit");
        cancelButton = new JButton("Cancel");
        searchButton = new JButton("Search");

        initialiseActionListeners();

        GroupLayout.ParallelGroup  horizontalParGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup verticalSeqGroup = layout.createSequentialGroup();

        for(ImmutablePair<JCheckBox, JTextField> pair: checkBoxes) {
            JCheckBox checkBox = pair.left;
            JTextField textField = pair.right;
            textField.setEnabled(false);
            textField.setVisible(false);
            textField.setSize(100,30);
            textField.setMaximumSize(new Dimension(100,30));

            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    textField.setEnabled(checkBox.isSelected());
                    textField.setVisible(checkBox.isSelected());
                    SwingUtilities.updateComponentTreeUI(frame); // updates frame
                }
            });

            textField.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String [] text = searchCriteriaArea.getText().split("\\n");

                    if(textAreaLinesMap.containsKey(checkBox.getText())){
                        int lineNumber = textAreaLinesMap.get(checkBox.getText());

                        text[lineNumber] = text[lineNumber]  + "," + textField.getText();

                        String appendedText = "";

                        for(String line : text){
                            appendedText += line + "\n";
                        }
                        searchCriteriaArea.setText(appendedText);
                    }else {
                        textAreaLinesMap.put(checkBox.getText(), text.length-1);
                        searchCriteriaArea.append(checkBox.getText() + ": " + textField.getText() + "\n");
                    }
                    textField.setText("");
                }
            });

            horizontalParGroup.addComponent(checkBox).addComponent(textField);
            verticalSeqGroup.addComponent(checkBox).addComponent(textField);

        }

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(horizontalParGroup)
                .addComponent(searchCriteriaArea));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addGroup(verticalSeqGroup)
                .addComponent(searchCriteriaArea));

        frame.setLayout(layout);
        frame.pack();
        frame.setSize(800,600);
        frame.setVisible(true);
    }

    /**
     *  Used to initialise various action listeners.
     *  Must be called after initialising each class variable found within this method
     *  */
    private void initialiseActionListeners(){
        editSearchArea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
