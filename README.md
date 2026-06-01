# CommandLineFileOperationUtility

基于 Java 的命令行通讯录管理工具，支持联系人的增删改查及文件序列化存储。

## 功能

- **新建通讯录** — 创建以 `.cse` 格式存储的通讯录文件
- **打开通讯录** — 浏览文件系统，加载已有通讯录
- **联系人管理** — 添加、删除、编辑、查找联系人
- **多值字段** — 支持多个电话、地址、邮箱、在线联系方式
- **数据持久化** — 使用 Java 序列化将通讯录保存到文件

## 项目结构

```
src/com/
├── ProgEntry.java                  # 程序入口
├── MenuController.java             # 菜单控制器
├── AddressBook.java                 # 通讯录实体
├── AddressBookService.java          # 通讯录业务逻辑
├── Contact.java                    # 联系人实体
├── FileService.java                # 文件浏览与加载
├── SerializationUtils.java         # 序列化工具
├── InputUtils.java                 # 控制台输入工具
├── CommandLineOperationUtility.java # 命令行文件浏览器
└── CommandLineConsoleUtility.java   # 控制台辅助工具
```

## 运行环境

- JDK 1.8+

## 运行方式

```bash
# 编译
javac -d out -encoding UTF-8 src/com/*.java

# 运行
java -cp out com.ProgEntry
```

或在 IntelliJ IDEA 中直接运行 `ProgEntry.java`。

## 使用说明

1. 启动后选择 **N** 新建通讯录 或 **O** 打开已有通讯录
2. 进入功能菜单后可执行以下操作：

| 选项 | 功能 |
|------|------|
| L | 显示所有联系人 |
| F | 按姓名/电话/地址/单位/岗位查找 |
| N | 添加新联系人 |
| D | 删除联系人 |
| E | 编辑联系人 |
| S | 保存通讯录 |
| Q | 退出编辑 |

## License

MIT
