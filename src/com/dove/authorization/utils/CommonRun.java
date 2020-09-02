package com.dove.authorization.utils;

import com.dove.authorization.LicenseGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 *  图形页面
 *
 * @author dove
 * @date 2020.09.02
 */
public class CommonRun {

    static String filePath = null;

    public static void main(String[] args) {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("生成lic");
        // Setting the width and height of frame
        frame.setSize(260, 250);
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

        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(true);

        // 创建一个按钮，点击后获取文本框中的文本
        JButton btn = new JButton("提交");

        JTextArea msgTextArea = new JTextArea(5, 10);
        msgTextArea.setLineWrap(true);
        panel.add(msgTextArea);

        JButton btn2 = new JButton("选择文件夹");

        // 按钮监听事件
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filePath = showFileOpenDialog(frame, msgTextArea);
            }
        });

        // 按钮监听事件
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (filePath == null || filePath.length() == 0) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "请选择文件夹",
                            "错误",
                            JOptionPane.WARNING_MESSAGE
                    );
                } else {
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
                            LicenseGenerator.generator(text, filePath);
                        }
                    } else {
                        int result = 0;
                        if (text.length() > 0) {
                            result = JOptionPane.showConfirmDialog(
                                    frame,
                                    "请确认是否自动？",
                                    "提示",
                                    JOptionPane.YES_NO_CANCEL_OPTION
                            );
                        }

                        if (result == 0) {
                            LicenseGenerator.generator(LicenseGenerator.getSerial(), filePath);
                        }
                    }
                }
            }
        });

        //设置位置
        radioBtn01.setBounds(50, 20, 65, 20);
        radioBtn02.setBounds(150, 20, 65, 20);
        textField.setBounds(70, 80, 165, 25);
        label.setBounds(10, 80, 55, 25);

        msgTextArea.setBounds(10,120,220,45);

        btn2.setBounds(10, 170, 120, 25);
        btn.setBounds(150, 170, 80, 25);

        panel.add(btn);
        panel.add(btn2);
        panel.add(radioBtn01);
        panel.add(radioBtn02);
        panel.add(label);
        panel.add(textField);
        frame.add(panel);
        frame.setVisible(true);
    }

    private static String showFileOpenDialog(Component parent, JTextArea msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(false);

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = fileChooser.getSelectedFile();
            msgTextArea.append(file.getAbsolutePath());
            return file.getAbsolutePath();
        }
        return null;
    }
}
