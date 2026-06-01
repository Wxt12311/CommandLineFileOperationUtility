package com;

import java.util.List;


public class MenuController {
    private final FileService fileService = new FileService();
    private final AddressBookService addressBookService = new AddressBookService();
    private AddressBook currentAddressBook;

    // 启动程序
    public void start() {
        while (true) {
            System.out.println("\n>>>请选择您的操作（输入N、O或者Q）：");
            System.out.println("N.新建一个通信录");
            System.out.println("O.打开一个通信录");
            System.out.println("Q.退出");

            String choice = InputUtils.getNonEmptyInput("").toUpperCase();
            switch (choice) {
                case "N": createNewAddressBook(); break;
                case "O": openExistingAddressBook(); break;
                case "Q":
                    System.out.println("程序已退出");
                    InputUtils.close();
                    return;
                default: System.out.println("无效选项，请重新输入");
            }
        }
    }

    // 新建通讯录
    private void createNewAddressBook() {
        String owner = InputUtils.getNonEmptyInput("请输入通信录的用户名：");
        String fileName = "新建通讯录For" + owner + ".cse";
        currentAddressBook = new AddressBook(owner, fileName);
        System.out.println(">>>当前的通信录的用户是 " + owner + "，保存的文件是 " + fileName + "。共有联系人：0 名。");
        enterMainMenu();
    }

    // 打开已有通讯录
    private void openExistingAddressBook() {
        currentAddressBook = fileService.openAddressBook();
        if (currentAddressBook != null) {
            System.out.println(">>>当前的通信录的用户是 " + currentAddressBook.getOwner()
                    + "，保存的文件是 " + currentAddressBook.getFileName()
                    + "。共有联系人：" + currentAddressBook.getContacts().size() + " 名。");
            enterMainMenu();
        }
    }

    // 进入主功能菜单
    private void enterMainMenu() {
        while (true) {
            System.out.println("\n>>>功能菜单：L.显示联系人 F.查找联系人 N.新建联系人 D.删除联系人 E.编辑联系人 S.保存通信录 Q.结束编辑");
            String choice = InputUtils.getNonEmptyInput("").toUpperCase();

            switch (choice) {
                case "L": addressBookService.showAllContacts(currentAddressBook); break;
                case "F": searchContactMenu(); break;
                case "N": addressBookService.addContact(currentAddressBook); break;
                case "D": addressBookService.deleteContact(currentAddressBook); break;
                case "E": addressBookService.editContact(currentAddressBook); break;
                case "S": saveAddressBookMenu(); break;
                case "Q": return;
                default: System.out.println("无效选项，请重新输入");
            }

            // 询问是否继续
            String continueOp = InputUtils.getOptionalInput(">>>需要继续修改当前打开通信录吗？ Yes Or No?\n");
            if (!continueOp.equalsIgnoreCase("Y")) {
                return;
            }
        }
    }

    // 查找联系人菜单
    private void searchContactMenu() {
        System.out.println(">>>请输入您计划 查找 联系人的信息项名称：N.姓名 T.电话 A.地址 U.工作单位 J.工作岗位");
        String field = InputUtils.getNonEmptyInput("");
        String keyword = InputUtils.getNonEmptyInput("请输入查找内容：");

        List<Contact> results = addressBookService.searchContacts(currentAddressBook, field, keyword);
        if (results.isEmpty()) {
            System.out.println("未找到匹配的联系人");
            return;
        }

        System.out.println(">>>共找到 " + results.size() + " 名联系人：");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i+1) + ". 姓名：" + results.get(i).getName());
        }
    }

    // 保存通讯录菜单（支持另存为）
    private void saveAddressBookMenu() {
        String newFileName = InputUtils.getOptionalInput("请输入保存文件名（直接回车使用原文件名）：");
        String filePath = newFileName.isEmpty() ? currentAddressBook.getFileName() : "E:\\javac\\CommandLineFileOperationUtility\\src\\"+newFileName+".cse";
        fileService.saveAddressBook(currentAddressBook, filePath);
        if (!newFileName.isEmpty()) {
            currentAddressBook.setFileName(filePath);
        }
    }
}