package services;

import model.BusinessRule;
import model.Table;

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
            inputCode.replaceFirst("#([^#]*);", t.getName());
        }




        return "";
    }
}
