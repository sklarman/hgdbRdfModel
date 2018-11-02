public abstract class Resource
{

    enum Type{
        URI, STRING, INTEGER, FLOAT, BOOLEAN;
    }

    protected Object value = null;
    protected Type type = null;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value)
    {

    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString()
    {
        if (this.getType()== Resource.Type.URI) return "<" + this.getValue() + ">";
        if (this.getType()== Resource.Type.STRING) return "\"" + this.getValue() + "\"";
        if (this.getType()== Resource.Type.INTEGER) return this.getValue().toString();
        if (this.getType()== Resource.Type.FLOAT) return this.getValue().toString();
        if (this.getType()== Resource.Type.BOOLEAN) return this.getValue().toString();

        return null;
    }
}
