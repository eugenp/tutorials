package cn.nonocast.base;

// new everytime request if needed
public abstract class FormBase {
    protected String op = "create";
    protected String error;

    public Boolean op_create() {
        return "create".equals(this.op);
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
