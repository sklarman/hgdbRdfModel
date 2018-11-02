import org.hypergraphdb.HyperGraph;

public class RdfTriple
{
    private Resource subject;
    private Resource predicate;
    private Resource object;
    private Resource id;

    public Resource getSubject()
    {
        return subject;
    }

    public Resource getPredicate()
    {
        return predicate;
    }

    public Resource getObject()
    {
        return object;
    }

    public Resource getId()
    {
        return id;
    }

    public RdfTriple(Resource id, Resource subject, Resource predicate, Resource object)
    {
        this.id = id;
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;

    }

    public RdfTriple(HyperGraph graph, RdfTripleLink rdfTripleLink)
    {
        this.id = graph.get(rdfTripleLink.id);
        this.subject = graph.get(rdfTripleLink.subject);
        this.predicate = graph.get(rdfTripleLink.predicate);
        this.object = graph.get(rdfTripleLink.object);

    }

    @Override
    public String toString()
    {
        return id.getValue() + " = " + subject.toString() + " " + predicate.toString() + " " + object.toString();
    }
}
