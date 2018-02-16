package baghi.naeem.com.assignment6.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Command {

    private String operand;
    private SQLiteDatabase database;
    private Context context;
    private Object data;

    public Command(String operand, SQLiteDatabase database, Context context, Object data) {
        this.operand = operand;
        this.database = database;
        this.context = context;
        this.data = data;
    }

    public Command(String operand, Context context, Object data) {
        this.operand = operand;
        this.database = database;
        this.context = context;
        this.data = data;
    }

    public Command(Context context, Object data) {
        this.context = context;
        this.data = data;
    }

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
