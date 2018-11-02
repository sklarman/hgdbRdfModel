import org.hypergraphdb.HGHandle;
import org.hypergraphdb.HGLink;

public class RdfTripleLink implements HGLink
{
    HGHandle id;
    HGHandle subject;
    HGHandle predicate;
    HGHandle object;


    public RdfTripleLink(HGHandle...handles)
    {
        assert handles.length == 4;
        id = handles[0];
        subject = handles[1];
        predicate = handles[2];
        object = handles[3];

    }

    public int getArity()
    {
        return 4;
    }

    public void notifyTargetHandleUpdate(int i, HGHandle handle)
    {
        switch (i)
        {
            case 0: { id = handle; return; }
            case 1: { subject = handle; return; }
            case 2: { predicate = handle; return; }
            case 3: { object = handle; return; }
            default: throw new IllegalArgumentException("Invalid index " + i);
        }
    }

    public HGHandle getTargetAt(int i)
    {
        switch (i)
        {
            case 0: return id;
            case 1: return subject;
            case 2: return predicate;
            case 3: return object;
            default: throw new IllegalArgumentException("Invalid index " + i);
        }
    }

    public void notifyTargetRemoved(int i)
    {
        switch (i)
        {
            case 0: { id = null; return; }
            case 1: { subject = null; return; }
            case 2: { predicate = null; return; }
            case 3: { object = null; return; }
            default: throw new IllegalArgumentException("Invalid index " + i);
        }
    }
}
