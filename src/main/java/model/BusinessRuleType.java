package model;

import java.util.List;

/**
 * Created by Yorick on 23/01/2018.
 */
public class BusinessRuleType {
    private String typeName;
    private Category category;
    private Template template;
    private List<Operator> operators;

    public BusinessRuleType(String typeName, Category category, Template template, List<Operator> operators) {
        this.typeName = typeName;
        this.category = category;
        this.template = template;
        this.operators = operators;
    }

    public String getTypeName() {
        return typeName;
    }

    public Category getCategory() {
        return category;
    }

    public Template getTemplate() {
        return template;
    }

    public List<Operator> getOperators() {
        return operators;
    }
}
