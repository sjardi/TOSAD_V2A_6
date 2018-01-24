package model;

import persistence.TargetDAO;

import java.util.List;

/**
 * Created by Yorick on 23/01/2018.
 */
public class BusinessRule {
    private Integer id;
    private String name;
    private String timing;
    private BusinessRuleType ruleType;
    private List<Value> values;
    private List<Table> tables;
    private boolean executed;
    private TargetDAO targetdb;

    public BusinessRule(Integer id, String name, String timing, BusinessRuleType ruleType, List<Value> values, List<Table> tables, boolean executed, TargetDAO targetdb) {
        this.id = id;
        this.name = name;
        this.timing = timing;
        this.ruleType = ruleType;
        this.values = values;
        this.tables = tables;
        this.executed = executed;
        this.targetdb = targetdb;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTiming() {
        return timing;
    }

    public BusinessRuleType getRuleType() {
        return ruleType;
    }

    public List<Value> getValues() {
        return values;
    }

    public List<Table> getTables() {
        return tables;
    }

    public boolean isExecuted() {
        return executed;
    }

    public TargetDAO getTargetdb() {
        return targetdb;
    }
}
