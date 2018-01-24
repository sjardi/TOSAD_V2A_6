package model;

import java.util.List;

/**
 * Created by Yorick on 23/01/2018.
 */
public class Table {
    private String name;
    private List<String> columns;
    private Integer position;

    public Table(String name, List<String> columns, Integer position) {
        this.name = name;
        this.columns = columns;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public List<String> getColumns() {
        return columns;
    }

    public Integer getPosition() {
        return position;
    }
}
