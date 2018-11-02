public class Literal extends Resource
{
    private Literal() {

    }

    public Literal(Object value) {

        this.value = value;

        if(value instanceof java.lang.String)
        {
            this.type = Type.STRING;
        }

        if(value instanceof java.lang.Integer)
        {
            this.type = Type.INTEGER;
        }

        if(value instanceof java.lang.Float)
        {
            this.type = Type.FLOAT;
        }

        if(value instanceof java.lang.Boolean)
        {
            this.type = Type.BOOLEAN;
        }


    }

}
