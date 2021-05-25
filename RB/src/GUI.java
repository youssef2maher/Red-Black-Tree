
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;
import javax.swing.border.AbstractBorder;


public class GUI extends JFrame implements ActionListener {

    RB_Tree rbt = new RB_Tree();

    //Panels
    JPanel treePanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    //Buttons
    JButton insertButton = new JButton("Insert");
    JButton deleteButton = new JButton("Delete");
    JButton clearButton = new JButton("Clear");

    //Text Fields
    JTextField insertField = new JTextField();
    JTextField deleteField = new JTextField();


    public GUI() {

    }

    public GUI(RB_Tree rbt) {


        this.rbt = rbt;

        /** Window Settings */

        //To make the frame appears
        this.setVisible(true);

        //To determine a size to the window (width, height)
        this.setSize(1300, 750);

        //To make the window size non-changeable
        this.setResizable(false);

        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

        //To determine a location for the frame opening (width, height)
        this.setLocation(70, 10);

        //To set a Title to the frame
        this.setTitle("Red Black Tree");

        //Frame Layout
        this.setLayout(null);

        //Adding the Panels
        this.add(treePanel);
        this.add(buttonsPanel);

        /** End of Window Settings */







        /** Tree Panel Settings */

        treePanel.setBackground(Color.gray);
        treePanel.setBounds(0, 0, 1300, 630);
        treePanel.setLayout(null);

        /** End of Tree Panel Settings */






        /** Buttons Panel Settings */

        buttonsPanel.setBackground(Color.white);
        buttonsPanel.setBounds(0, 635, 1300, 100);
        buttonsPanel.setLayout(null);


        buttonsPanel.add(insertButton);
        insertButton.setBounds(200, 20, 100, 40);
        insertButton.addActionListener(this);

        buttonsPanel.add(insertField);
        insertField.setBounds(100, 20, 100, 40);

        buttonsPanel.add(deleteButton);
        deleteButton.setBounds(700, 20, 100, 40);
        deleteButton.addActionListener(this);

        buttonsPanel.add(deleteField);
        deleteField.setBounds(600, 20, 100, 40);

        buttonsPanel.add(clearButton);
        clearButton.setBounds(1100, 20, 100, 40);
        clearButton.addActionListener(this);

        /** End of Buttons Panel Settings */


    }




//													0
//											0				0
//										0		0		0		0















    public void buildChildren(RBNode node, int x, int y, int d) {


        if (node != null) {

            if (node.getLeft() != null) {

                RBNode left = node.getLeft();

                String leftValue = Integer.toString(left.getValue());
                JLabel leftNode = new JLabel(leftValue, JLabel.CENTER);

                treePanel.add(leftNode);
                leftNode.setBounds(x-d, y+80, 40, 40);

                leftNode.setFont(new Font("Arial", Font.BOLD, 16));
                leftNode.setOpaque(true);
                leftNode.setForeground(Color.white);

                if (left.getColor()) {
                    leftNode.setBackground(Color.red);
                } else {
                    leftNode.setBackground(Color.black);
                }

                buildChildren(left, x-d, y+80, d/2);
            }

            else {

                JLabel leftNode = new JLabel("N", JLabel.CENTER);
                treePanel.add(leftNode);
                leftNode.setBounds(x-d, y+80, 40, 40);

                leftNode.setFont(new Font("Arial", Font.BOLD, 16));
                leftNode.setOpaque(true);
                leftNode.setForeground(Color.white);
                leftNode.setBackground(Color.black);
            }


            if (node.getRight() != null) {

                RBNode right = node.getRight();

                String rightValue = Integer.toString(right.getValue());
                JLabel rightNode = new JLabel(rightValue, JLabel.CENTER);

                treePanel.add(rightNode);
                rightNode.setBounds(x+d, y+80, 40, 40);

                rightNode.setFont(new Font("Arial", Font.BOLD, 16));
                rightNode.setOpaque(true);
                rightNode.setForeground(Color.white);

                if (right.getColor()) {
                    rightNode.setBackground(Color.red);
                } else {
                    rightNode.setBackground(Color.black);
                }

                buildChildren(right, x+d, y+80, d/2);
            }

            else {

                JLabel rightNode = new JLabel("N", JLabel.CENTER);
                treePanel.add(rightNode);
                rightNode.setBounds(x+d, y+80, 40, 40);

                rightNode.setFont(new Font("Arial", Font.BOLD, 16));
                rightNode.setOpaque(true);
                rightNode.setForeground(Color.white);
                rightNode.setBackground(Color.black);
            }
        }
    }






    public void buildTree() {

        treePanel.removeAll();
        treePanel.setBackground(Color.gray);
        treePanel.setBounds(0, 0, 1300, 630);
        treePanel.setLayout(null);
        this.add(treePanel);


        if(rbt.getroot()==null)
            return;

        String rootValue = Integer.toString(rbt.getroot().getValue());
        JLabel rootNode = new JLabel(rootValue, JLabel.CENTER);


        treePanel.add(rootNode);
        rootNode.setBounds(600, 50, 40, 40);


        rootNode.setFont(new Font("Arial", Font.BOLD, 16));
        rootNode.setOpaque(true);
        rootNode.setForeground(Color.white);


        if (rbt.getroot().getColor()) {
            rootNode.setBackground(Color.red);
        } else {
            rootNode.setBackground(Color.black);
        }

        buildChildren(rbt.getroot(), 600, 80, 300);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        if (e.getSource() == insertButton) {

            String value = insertField.getText();
            int intValue = Integer.parseInt(value);
            rbt.insert(intValue);
            insertField.setText(null);

            buildTree();
        }

        else if (e.getSource() == deleteButton) {

            String value = deleteField.getText();
            int intValue = Integer.parseInt(value);
            rbt.delete(intValue);
            deleteField.setText(null);

            buildTree();
        }

        else if (e.getSource() == clearButton) {

            rbt.clear(rbt.getroot());

            buildTree();
        }

    }





}