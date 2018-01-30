package model;

/**
 * Created by Yorick on 23/01/2018.
 */
public class Template {
    private String code;
    private String beginCode;
    private String endCode;
    private String timing;
    private String triggerevent;

    public Template(String code, String begincode, String endcode, String timing, String triggerevent) {
        this.code = code;
        this.beginCode = begincode;
        this.endCode = endcode;
        this.timing = timing;
        this.triggerevent = triggerevent;
    }

    public String getTiming() {
        return timing;
    }

    public String getTriggerevent() {
        return triggerevent;
    }

    public String getCode() {
        return code;
    }

    public String getBeginCode(){
        return beginCode;
    }

    public String getEndCode(){
        return endCode;
    }
}
