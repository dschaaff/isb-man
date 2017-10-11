package net.javalib.isb.man.pojo.metrics;

import java.util.ArrayList;
import java.util.Collection;

public class DataSourceMetric {
    private String dataSource;
    private String table;
    private String operation;
    private Timer metric;
    private Collection<StatementMetric> statements = new ArrayList<>();

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Timer getMetric() {
        return metric;
    }

    public void setMetric(Timer metric) {
        this.metric = metric;
    }

    public Collection<StatementMetric> getStatements() {
        return statements;
    }

    public void setStatements(Collection<StatementMetric> statements) {
        this.statements = statements;
    }

    public static class StatementMetric {
        private String sql;
        private Timer metric;

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public Timer getMetric() {
            return metric;
        }

        public void setMetric(Timer metric) {
            this.metric = metric;
        }

    }

}
