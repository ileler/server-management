package root.util;

import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectDB<T> {

    private String name;

    private List<T> data;

    public ObjectDB(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new NullPointerException("name is null");
        }
        this.name = name;
        load();
        if (data == null) {
            data = new ArrayList<>(0);
        }
    }

    public List<T> getData() {
        return data;
    }

    public void addData(T t) {
        addData(t, true);
    }

    private void addData(T t, boolean isSave) {
        data.add(t);
        if (isSave) save();
    }

    public void delData(T t) {
        delData(t, true);
    }

    private void delData(T t, boolean isSave) {
        data.remove(t);
        if (isSave) save();
    }

    private void save() {
        try {
            Collections.reverse(data);
            ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream(this.name));
            objectOutput.writeObject(data);
            objectOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void load() {
        try {
            File file = new File(this.name);
            if (!file.exists()) return;
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            Object object = objectInputStream.readObject();
            if (object != null) {
                data = (List<T>) object;
            }
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
