package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddressBookService {
    // 显示所有联系人
    public void showAllContacts(AddressBook addressBook) {
        List<Contact> contacts = addressBook.getContacts();
        if (contacts.isEmpty()) {
            System.out.println("通讯录中暂无联系人");
            return;
        }

        for (int i = 0; i < contacts.size(); i++) {
            Contact c = contacts.get(i);
            System.out.println((i+1) + ". 姓名：" + c.getName());
            System.out.println("   工作单位：" + c.getWorkUnit());
            System.out.println("   工作岗位：" + c.getPosition());
            System.out.println("   出生日期：" + c.getBirthday());

            if (!c.getPhones().isEmpty()) {
                System.out.println("   联系电话：");
                c.getPhones().forEach((k, v) -> System.out.println("      " + k + "：" + v));
            }
            if (!c.getAddresses().isEmpty()) {
                System.out.println("   通信地址：");
                c.getAddresses().forEach((k, v) -> System.out.println("      " + k + "：" + v));
            }
            if (!c.getOnlineContacts().isEmpty()) {
                System.out.println("   在线通信方式：");
                c.getOnlineContacts().forEach((k, v) -> System.out.println("      " + k + "：" + v));
            }
            if (!c.getEmails().isEmpty()) {
                System.out.println("   电子邮箱：");
                c.getEmails().forEach((k, v) -> System.out.println("      " + k + "：" + v));
            }
            System.out.println("   备注：" + c.getRemark() + "\n");
        }
    }

    // 按字段查询联系人
    public List<Contact> searchContacts(AddressBook addressBook, String field, String keyword) {
        return addressBook.getContacts().stream()
                .filter(c -> {
                    switch (field.toUpperCase()) {
                        case "N": return c.getName().contains(keyword);
                        case "T": return c.getPhones().values().stream().anyMatch(v -> v.contains(keyword));
                        case "A": return c.getAddresses().values().stream().anyMatch(v -> v.contains(keyword));
                        case "U": return c.getWorkUnit().contains(keyword);
                        case "J": return c.getPosition().contains(keyword);
                        default: return false;
                    }
                })
                .collect(Collectors.toList());
    }

    // 添加联系人
    public void addContact(AddressBook addressBook) {
        Contact contact = new Contact();
        System.out.println("\n>>>请按照提示逐项输入联系人信息：");

        contact.setName(InputUtils.getNonEmptyInput("姓名："));
        contact.setBirthday(InputUtils.getOptionalInput("出生日期【YYYYMMDD】："));
        contact.setWorkUnit(InputUtils.getOptionalInput("工作单位："));
        contact.setPosition(InputUtils.getOptionalInput("工作岗位："));
        contact.setRemark(InputUtils.getOptionalInput("备注："));

        // 添加多值字段
        addMultiFields(contact);

        addressBook.getContacts().add(contact);
        System.out.println("联系人添加成功！");
    }

    // 添加多值字段（电话/地址/邮箱/在线方式）
    private void addMultiFields(Contact contact) {
        while (true) {
            System.out.println("\n>>>请选择你要输入的联系人数据项：A.联系地址 E.电子邮箱 O.在线联系方式 T.联系电话 Q.结束输入");
            String choice = InputUtils.getNonEmptyInput("").toUpperCase();
            if (choice.equals("Q")) break;

            String type = InputUtils.getNonEmptyInput("名称：");
            String value = InputUtils.getNonEmptyInput("详细地址/号码/账号：");

            switch (choice) {
                case "A": contact.getAddresses().put(type, value); break;
                case "E": contact.getEmails().put(type, value); break;
                case "O": contact.getOnlineContacts().put(type, value); break;
                case "T": contact.getPhones().put(type, value); break;
                default: System.out.println("无效选项");
            }
        }
    }

    // 删除联系人
    public void deleteContact(AddressBook addressBook) {
        System.out.println(">>>请输入您计划 删除 联系人的信息项名称：N.姓名 T.电话 A.地址 U.工作单位 J.工作岗位");
        String field = InputUtils.getNonEmptyInput("");
        String keyword = InputUtils.getNonEmptyInput("请输入您计划 删除 联系人信息项的内容：");

        List<Contact> results = searchContacts(addressBook, field, keyword);
        if (results.isEmpty()) {
            System.out.println("未找到匹配的联系人");
            return;
        }

        System.out.println(">>>共找到 " + results.size() + " 名联系人，具体如下：");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i+1) + ". 姓名：" + results.get(i).getName());
        }

        System.out.println(">>>请选择需要删除的联系人的序号 1~" + results.size() + "，输入 0 放弃删除");
        int index = Integer.parseInt(InputUtils.getNonEmptyInput(""));
        if (index > 0 && index <= results.size()) {
            addressBook.getContacts().remove(results.get(index-1));
            System.out.println("删除成功！");
        } else {
            System.out.println("已放弃删除");
        }
    }

    // 编辑联系人
    public void editContact(AddressBook addressBook) {
        System.out.println(">>>请输入您计划 编辑 联系人的信息项名称：N.姓名 T.电话 A.地址 U.工作单位 J.工作岗位");
        String field = InputUtils.getNonEmptyInput("");
        String keyword = InputUtils.getNonEmptyInput("请输入您计划 编辑 联系人信息项的内容：");

        List<Contact> results = searchContacts(addressBook, field, keyword);
        if (results.isEmpty()) {
            System.out.println("未找到匹配的联系人");
            return;
        }

        System.out.println(">>>共找到 " + results.size() + " 名联系人，具体如下：");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i+1) + ". 姓名：" + results.get(i).getName());
        }

        System.out.println(">>>请选择需要修改的联系人的序号 1~" + results.size() + "，输入 0 放弃编辑");
        int index = Integer.parseInt(InputUtils.getNonEmptyInput(""));
        if (index <= 0 || index > results.size()) {
            System.out.println("已放弃编辑");
            return;
        }

        Contact contact = results.get(index-1);
        System.out.println(">>>请选择你要修改的联系人数据项：A.联系地址 E.电子邮箱 O.在线联系方式 P.工作岗位 T.联系电话 U.工作单位 Q.放弃编辑");
        String choice = InputUtils.getNonEmptyInput("").toUpperCase();
        if (choice.equals("Q")) return;

        // 编辑多值字段
        if (Arrays.asList("A", "E", "O", "T").contains(choice)) {
            editMultiField(contact, choice);
        } else {
            // 编辑单值字段
            switch (choice) {
                case "P": contact.setPosition(InputUtils.getNonEmptyInput("请输入新的工作岗位：")); break;
                case "U": contact.setWorkUnit(InputUtils.getNonEmptyInput("请输入新的工作单位：")); break;
                default: System.out.println("无效选项");
            }
        }
        System.out.println("修改成功！");
    }

    // 编辑多值字段
    private void editMultiField(Contact contact, String fieldType) {
        Map<String, String> fieldMap;
        String fieldName;
        switch (fieldType) {
            case "A": fieldMap = contact.getAddresses(); fieldName = "联系地址"; break;
            case "E": fieldMap = contact.getEmails(); fieldName = "电子邮箱"; break;
            case "O": fieldMap = contact.getOnlineContacts(); fieldName = "在线联系方式"; break;
            case "T": fieldMap = contact.getPhones(); fieldName = "联系电话"; break;
            default: return;
        }

        System.out.println(">>>" + fieldName + "信息如下：");
        List<Map.Entry<String, String>> entries = new ArrayList<>(fieldMap.entrySet());
        for (int i = 0; i < entries.size(); i++) {
            System.out.println((i+1) + "." + entries.get(i).getKey() + ":" + entries.get(i).getValue());
        }

        System.out.println(">>>请选择你的修改方式：A.增加 D.删除 M.修改 Q.放弃修改");
        String op = InputUtils.getNonEmptyInput("").toUpperCase();
        if (op.equals("Q")) return;

        switch (op) {
            case "A":
                String type = InputUtils.getNonEmptyInput("类型：");
                String value = InputUtils.getNonEmptyInput("值：");
                fieldMap.put(type, value);
                break;
            case "D":
                int delIndex = Integer.parseInt(InputUtils.getNonEmptyInput("请输入要删除的序号：")) - 1;
                if (delIndex >= 0 && delIndex < entries.size()) {
                    fieldMap.remove(entries.get(delIndex).getKey());
                }
                break;
            case "M":
                int modIndex = Integer.parseInt(InputUtils.getNonEmptyInput("请输入要修改的序号：")) - 1;
                if (modIndex >= 0 && modIndex < entries.size()) {
                    String newType = InputUtils.getNonEmptyInput("新类型：");
                    String newValue = InputUtils.getNonEmptyInput("新值：");
                    fieldMap.remove(entries.get(modIndex).getKey());
                    fieldMap.put(newType, newValue);
                }
                break;
        }
    }
}