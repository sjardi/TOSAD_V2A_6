package services;

import model.BusinessRule;
import model.Operator;
import model.Table;
import model.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Yorick on 23/01/2018.
 */
public class TemplateParser {
    public TemplateParser(){

    }

    public String parse(BusinessRule br){
        String beginCode = br.getRuleType().getTemplate().getBeginCode();
        String mainCode = br.getRuleType().getTemplate().getCode();
        String endCode = br.getRuleType().getTemplate().getEndCode();
        String name = br.getRuleType().getTypeName() + "_" + br.getId();
        System.out.print(name);

        // sort values by position
        ArrayList<Table> tables = (ArrayList<Table>) br.getTables();
        if(tables.size() == 0)
            return "";
        ArrayList<Value> values = (ArrayList<Value>) br.getValues();
        Collections.sort(tables, (pos1, pos2) -> pos1.getPosition() - pos2.getPosition());
        Collections.sort(values, (pos1, pos2) -> pos1.getPosition() - pos2.getPosition());


        beginCode = beginCode.replaceFirst("#NAME(.*?)#", name);
        beginCode = beginCode.replaceFirst("#TIMING(.*?)#", br.getRuleType().getTemplate().getTiming());
        beginCode = beginCode.replaceFirst("#EVENTS(.*?)#", br.getRuleType().getTemplate().getTriggerevent());
        String columns = "";
        for(Table t : tables){

            beginCode = beginCode.replaceFirst("#TABLE(.*?)#", t.getName());

            for(String column : t.getColumns()){
                beginCode = beginCode.replaceFirst("#COLUMN(.*?)#", column);
                mainCode = mainCode.replaceFirst("#COLUMN(.*?)#", column);
                columns = columns + column + ", ";
            }
            if(columns.endsWith(", ")){
                columns = columns.substring(0, columns.length()-2);
            }
        }
        beginCode = beginCode.replaceFirst("#COLUMNS(.*?)#", columns);

        // fill the main code's values and the error msg values
        String valuesList = "(";
        for(int i = 0; i < values.size(); i++) {
            mainCode = mainCode.replaceFirst("#VALUE(.*?)#", values.get(i).getValue());
            valuesList += "'" +values.get(i).getValue()+"',";
        }

        // fills the placeholder with a list of values
        valuesList = valuesList.substring(0, valuesList.length() - 1);
        valuesList += ")";
        mainCode = mainCode.replaceFirst("#LISTVALUES(.*?)#", valuesList);

        String operator = br.getRuleType().getOperators().get(0).getOperator();
        mainCode = mainCode.replaceAll("#OPERATOR(.*?)#", operator);


        // Add error message
        for(int i = 0; i < values.size(); i++)
            mainCode = mainCode.replaceFirst("#VALUE(.*?)#", values.get(i).getValue());
        for(Table t : tables){
            for(String column : t.getColumns()){
                mainCode = mainCode.replaceFirst("#COLUMN(.*?)#", column);
            }
        }

        return (beginCode + " " + mainCode + " " + endCode).replaceAll("(\\r|\\n)", " ");
    }
}
