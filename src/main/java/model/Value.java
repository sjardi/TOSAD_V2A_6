package model;

/**
 * Created by Yorick on 23/01/2018.
 */
public class Value {
    private int position;
    private String value;

    public Value(int position, String value) {
        this.position = position;
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public String getValue() {
        return value;
    }
}
