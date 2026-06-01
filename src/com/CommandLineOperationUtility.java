package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.TreeMap;

public class CommandLineOperationUtility {
    private File theCurrentPath;

    public CommandLineOperationUtility() {
        this.theCurrentPath = new File(".");
    }

    public CommandLineOperationUtility(String defaultPathName) throws FileNotFoundException {
        theCurrentPath = new File(defaultPathName);
        if (!theCurrentPath.exists()) {
            throw new FileNotFoundException(String.format("指定的文件/目录 %s 不存在!", defaultPathName));
        }
        if (!theCurrentPath.isDirectory()) {
            throw new FileNotFoundException(String.format("指定的名称：%s 不是文件夹，无法查找文件", defaultPathName));
        }
    }

    public void welcome() {
        System.out.println("=== 命令行文件操作工具 ===");
        System.out.println("欢迎使用!");
    }

    public void run() {
        try {
            String selectedFile = GetTheSelectFileName();
            if (selectedFile != null) {
                System.out.println("您选择的文件: " + selectedFile);
            } else {
                System.out.println("未选择文件");
            }
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
    }

    public String GetTheSelectFileName() throws FileNotFoundException {
        while (true) {
            File[] theFiles = theCurrentPath.listFiles();
            if (theFiles == null) {
                throw new FileNotFoundException("无法读取目录: " + theCurrentPath.getAbsolutePath());
            }

            TreeMap<Integer, File> theNumberCodeFiles = new TreeMap<>();
            int codeIndex = 0;

            for (int index = 0; index < theFiles.length; index++) {
                if (theFiles[index].isDirectory() && !theFiles[index].isHidden()) {
                    theNumberCodeFiles.put(codeIndex++, theFiles[index]);
                }
            }
            for (int index = 0; index < theFiles.length; index++) {
                if (theFiles[index].isFile() && !theFiles[index].isHidden()) {
                    theNumberCodeFiles.put(codeIndex++, theFiles[index]);
                }
            }

            System.out.println("\n当前目录: " + theCurrentPath.getAbsolutePath());
            for (int index = 0; index < codeIndex; index++) {
                System.out.printf("%d - %-30s\t%s\n", index,
                        theNumberCodeFiles.get(index).getName(),
                        theNumberCodeFiles.get(index).isDirectory() ? "[目录]" : "[文件]");
            }

            System.out.print("\n返回上一级: < ; 选择文件或文件夹: 输入文件目录序号; 退出: q\n");
            System.out.print("请选择: ");

            String inputString = CommandLineConsoleUtility.getStringFromKeyboard(
                    "返回上一层：< | 退出：q/Q | 选择序号：数字"
            );


            switch (inputString) {
                case "<":
                    File theParent = theCurrentPath.getParentFile();
                    if (theParent != null) {
                        CommandLineOperationUtility theNextPath = new CommandLineOperationUtility(theParent.getAbsolutePath());
                        return theNextPath.GetTheSelectFileName();
                    } else {
                        CommandLineConsoleUtility.printNoticeMessage(String.format("%s 已经为根文件夹，不能再上溯了，请重新选择!", theCurrentPath.getAbsolutePath()));
                    }
                    break;
                case "q":
                case "Q":
                    return null;
                default:
                    try {
                        int index = Integer.parseInt(inputString);
                        if (index >= 0 && index < codeIndex) {
                            File selectedFile = theNumberCodeFiles.get(index);
                            if (selectedFile.isFile()) {
                                return selectedFile.getAbsolutePath();
                            } else {
                                theCurrentPath = selectedFile;
                            }
                        } else {
                            System.out.printf("您所输入的序号不正确，有效范围为[%d..%d]请重新选择!\n", 0, codeIndex - 1);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("输入无效，请输入数字、< 或 q");
                    }
                    break;
            }
        }
    }
}