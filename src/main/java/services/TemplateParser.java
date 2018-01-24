package services;

import model.BusinessRule;
import model.Table;
import model.Value;

/**
 * Created by Yorick on 23/01/2018.
 */
public class TemplateParser {
    public TemplateParser(){

    }

    public String parse(BusinessRule br){
        String inputCode = br.getRuleType().getTemplate().getCode();
        // replace #TABLE# with actual table names
        for(Table t : br.getTables()){
            inputCode = inputCode.replaceFirst("#TABLE(.*?)#", t.getName());
            for(String column : t.getColumns()){
                inputCode = inputCode.replaceFirst("#COLUMN(.*?)#", column);
            }
        }

        for(Value v : br.getValues()){
            inputCode = inputCode.replaceFirst("#VALUE(.*?)#", v.getValue());
        }
        inputCode = inputCode.replaceAll("(\\r|\\n)", " ");
        System.out.print(inputCode);

        return inputCode;
    }
}
