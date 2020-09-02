package com.dove.authorization.utils;

import com.dove.authorization.LicenseGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommonRun {

    public static void main(String[] args) {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("生成lic");
        // Setting the width and height of frame
        frame.setSize(260, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);
        // 创建两个单选按钮
        JRadioButton radioBtn01 = new JRadioButton("自动", false);
        JRadioButton radioBtn02 = new JRadioButton("手动", true);
        // 创建按钮组，把两个单选按钮添加到该组
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(radioBtn01);
        btnGroup.add(radioBtn02);
        JLabel label = new JLabel("机器码:");
        // 文本框
        JTextField textField = new JTextField(8);
        textField.setFont(new Font(null, Font.PLAIN, 20));
        textField.setEnabled(true);
        // 创建一个按钮，点击后获取文本框中的文本
        JButton btn = new JButton("提交");
        // 按钮监听事件
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText().trim();
                if (radioBtn02.isSelected()) {
                    if (text.length() == 0) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "请输入机器码",
                                "错误",
                                JOptionPane.WARNING_MESSAGE
                        );
                    } else {
                        LicenseGenerator.generator(text);
                    }
                } else {
                    int result = 1;
                    if (text.length() > 0) {
                        result = JOptionPane.showConfirmDialog(
                                frame,
                                "请确认是否自动？",
                                "提示",
                                JOptionPane.YES_NO_CANCEL_OPTION
                        );
                    }

                    if (result == 0) {
                        LicenseGenerator.generator(LicenseGenerator.getSerial());
                    }
                }
            }
        });
        //设置位置
        radioBtn01.setBounds(50, 20, 65, 20);
        radioBtn02.setBounds(150, 20, 65, 20);
        textField.setBounds(70, 80, 165, 25);
        label.setBounds(10, 80, 55, 25);
        btn.setBounds(100, 120, 80, 25);

        panel.add(btn);
        panel.add(radioBtn01);
        panel.add(radioBtn02);
        panel.add(label);
        panel.add(textField);
        frame.add(panel);
        frame.setVisible(true);
    }
}
