package com;


import java.io.*;

public class SerializationUtils {
    // 序列化对象到文件
    public static void serialize(Object obj, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(obj);
        }
    }

    // 从文件反序列化对象
    public static Object deserialize(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readObject();
        }
    }
}