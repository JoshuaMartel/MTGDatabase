package org.pheonix.ui;

import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.swing.* ;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final public class SearchWindow {

    JFrame frame;
    Container pain;
    GroupLayout layout;
    JButton searchButton, cancelButton, editSearchArea;
    JTextArea searchArea;
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
        pain = frame.getContentPane();
        layout = new GroupLayout(pain);

        searchArea = new JTextArea(20,60);
        scrollPane = new JScrollPane(searchArea);

        editSearchArea = new JButton("Edit");
        cancelButton = new JButton("Cancel");
        searchButton = new JButton("Search");

        initialiseActionListeners();

        GroupLayout.ParallelGroup  horizontalParGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup horizontalSeqGroup = layout.createSequentialGroup();

        GroupLayout.SequentialGroup verticalSeqGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup  verticalParGroup = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);

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

                }
            });

            horizontalParGroup.addComponent(checkBox).addComponent(textField);
            //horizontalSeqGroup.addComponent(textField);

            //verticalParGroup.addComponent(textField);
            verticalSeqGroup.addComponent(checkBox).addComponent(textField);

        }



        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(horizontalParGroup)
                .addComponent(searchArea));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addGroup(verticalSeqGroup)
                .addComponent(searchArea));

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

    private void createHorizontalGroup(){

    }
}

/*public class SearchWindow extends Frame implements ActionListener {
    //private JFrame frame;
   *//* private JButton button;
    private JTextField tf;
    private JLabel label;*//*
    private JTextField tf1,tf2,tf3;
    private JButton b1,b2;
    private JButton exit;

    public SearchWindow(){

        tf1 = new JTextField();
        tf1.setBounds(50,50,150,20);
        tf2 = new JTextField();
        tf2.setBounds(50,100,150,20);
        tf3 = new JTextField();
        tf3.setBounds(50,150,150,20);
        tf3.setEditable(false);
        b1 = new JButton("+");
        b1.setBounds(50,200,50,50);
        b2 = new JButton("-");
        b2.setBounds(120,200,50,50);
        b1.addActionListener(this);
        b2.addActionListener(this);

        exit = new JButton("Exit");
        exit.setBounds(50, 300, 50,50);

        add(tf1); add(tf2); add(tf3); add(b1); add(b2);
        setSize(300,300);
        setLayout(null);
        setVisible(true);

        *//*tf = new JTextField();
        tf.setBounds(50,50,150,20);
        label = new JLabel();
        label.setBounds(50,100,250,20);
        button = new JButton("Find IP");
        button.setBounds(50,150,95,30);
        button.addActionListener(this);
        add(button);
        add(tf);
        add(label);
        setSize(400,400);
        setLayout(null);
        setVisible(true);*//*

        *//*frame = new JFrame("Button");
        button = new JButton(new ImageIcon(getClass().getResource("/Badlands.jpg")));

        button.setBounds(100,100,100,40);
        frame.add(button);
        frame.setSize(300,400);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*//*
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s1 = tf1.getText();
        String s2 = tf2.getText();
        int a = Integer.parseInt(s1);
        int b = Integer.parseInt(s2);
        int c = 0;
        if(e.getSource() == b1){
            c = a + b;
        }else if(e.getSource() == b2){
            c = a - b;
        }
        String result = String.valueOf(c);
        tf3.setText(result);

        *//*try {
            String host = tf.getText();
            String ip = java.net.InetAddress.getByName(host).getHostAddress();
            label.setText("IP of " + host + " is: " + ip);
        } catch (Exception ex){
            System.out.println(ex);
        }*//*
    }
}*/
