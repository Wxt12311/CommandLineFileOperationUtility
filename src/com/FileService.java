package com;

import java.io.File;
import java.io.IOException;


public class FileService {
    private File currentDir = new File("."); // 当前目录

    // 显示文件浏览器，选择.cse文件
    public AddressBook openAddressBook() {
        while (true) {
            System.out.println("\n当前文件夹包含：");
            File[] files = currentDir.listFiles();
            if (files == null || files.length == 0) {
                System.out.println("当前文件夹为空");
                return null;
            }

            // 列出所有文件和文件夹
            for (int i = 0; i < files.length; i++) {
                System.out.println(i + ":" + files[i].getName() + "\t\t" + (files[i].isDirectory() ? "Dir" : "File"));
            }

            System.out.println(">>>返回上一层：<；选择文件或文件夹：输入文件目录序号；放弃选择：Q");
            String input = InputUtils.getNonEmptyInput("");

            if (input.equalsIgnoreCase("Q")) {
                return null;
            } else if (input.equals("<")) {
                currentDir = currentDir.getParentFile() != null ? currentDir.getParentFile() : currentDir;
            } else {
                try {
                    int index = Integer.parseInt(input);
                    if (index >= 0 && index < files.length) {
                        File selected = files[index];
                        if (selected.isDirectory()) {
                            currentDir = selected;
                        } else if (selected.getName().endsWith(".cse")) {
                            // 加载通讯录文件
                            return (AddressBook) SerializationUtils.deserialize(selected.getAbsolutePath());
                        } else {
                            System.out.println("请选择后缀为.cse的通讯录文件");
                        }
                    } else {
                        System.out.println("无效的序号");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("请输入有效的数字序号");
                } catch (Exception e) {
                    System.out.println("文件加载失败：" + e.getMessage());
                }
            }
        }
    }

    // 保存通讯录到文件
    public void saveAddressBook(AddressBook addressBook, String filePath) {
        try {
            SerializationUtils.serialize(addressBook, filePath);
            System.out.println("通讯录保存成功！");
        } catch (IOException e) {
            System.out.println("保存失败：" + e.getMessage());
        }
    }
}