package app.mmt.test.project;

import java.util.ArrayList;

public class MyData {
    private String title;
    private String Desc;

    public MyData(String title, String desc) {
        this.title = String.valueOf(title);
        this.Desc = String.valueOf(desc);
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return Desc;
    }
}
