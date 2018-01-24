package services;

import model.BusinessRule;

import java.sql.ResultSet;

/**
 * Created by Yorick on 23/01/2018.
 */
public class BusinessRuleService {

    private static BusinessRuleService instance;
    private DAOService daoservice;
    private TemplateParser templateParser;

    public BusinessRuleService() {
        this.daoservice = new DAOService();
        this.templateParser = new TemplateParser();
    }

    public static BusinessRuleService getInstance() {
        if(instance == null){
            instance = new BusinessRuleService();
        }
        return instance;
    }

    public DAOService getDaoservice() {
        return daoservice;
    }

    public String generate(Integer id){
        return "";
    }

    private BusinessRule resultSetToBusinessRule(ResultSet rs){
        return null;
    }
}
