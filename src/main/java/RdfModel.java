import org.hypergraphdb.HGHandle;
import org.hypergraphdb.HGQuery;
import org.hypergraphdb.HyperGraph;
import org.hypergraphdb.query.AtomTypeCondition;
import org.hypergraphdb.query.HGQueryCondition;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hypergraphdb.HGQuery.hg.and;
import static org.hypergraphdb.HGQuery.hg.incidentAt;

public class RdfModel
{
    HyperGraph graph;

    public RdfModel(HyperGraph graph) {
        this.graph = graph;
    }

    public URI uri(String value) {
        return new URI(value);
    }

    public Literal literal(Object value) {
        return new Literal(value);
    }

    public RdfTriple assertTriple(Resource subject, Resource predicate, Resource object) {

        HGHandle s = getUniqueResourceHandle(subject);
        HGHandle p = getUniqueResourceHandle(predicate);
        HGHandle o = getUniqueResourceHandle(object);

        RdfTripleLink tripleLink = HGQuery.hg.getOne(graph, and(incidentAt(s, 1), incidentAt(p, 2), incidentAt(o, 3)));

        Resource idResource;

        if (tripleLink==null) {
            idResource = new URI("_://id/" + UUID.randomUUID());
            HGHandle id = getUniqueResourceHandle(idResource);
            tripleLink = new RdfTripleLink(id, s, p, o);
            graph.add(tripleLink);
        }
        else {
            idResource = graph.get(tripleLink.id);
        }

        RdfTriple triple = new RdfTriple(idResource, subject, predicate, object);

        return triple;
    }

    public void printAllTriples() {
        HGQueryCondition query = new AtomTypeCondition(RdfTripleLink.class);

        List<HGHandle> rs = graph.findAll(query);
        for (HGHandle current : rs) {
            RdfTripleLink res = graph.get(current);
            RdfTriple foundTriple = new RdfTriple(graph, res);
            System.out.println(foundTriple.toString());
        }
    }

    public Set<RdfTriple> getTriples(Resource subject, Resource predicate, Resource object) {
        Set<RdfTriple> triples = new HashSet<RdfTriple>();

        HGHandle subHandle = getUniqueResourceHandle(subject);
        HGHandle predHandle = getUniqueResourceHandle(predicate);
        HGHandle objHandle = getUniqueResourceHandle(object);

        HGHandle defaultHandle = null;
        Integer defaultPosition = 0;

        HGHandle s;
        HGHandle p;
        HGHandle o;
        Integer sPosition;
        Integer pPosition;
        Integer oPosition;

        if (subHandle!=null) {
            defaultHandle = subHandle;
            defaultPosition = 1;
        }

        if (predHandle!=null) {
            defaultHandle = predHandle;
            defaultPosition = 2;
        }

        if (objHandle!=null) {
            defaultHandle = objHandle;
            defaultPosition = 3;
        }

        if (defaultHandle==null) {
            return getAllTriples();
        }

        if (subHandle!=null) {
            s = subHandle;
            sPosition = 1;
        } else {
            s = defaultHandle;
            sPosition = defaultPosition;
        }

        if (predHandle!=null) {
            p = predHandle;
            pPosition = 2;
        } else {
            p = defaultHandle;
            pPosition = defaultPosition;
        }

        if (objHandle!=null) {
            o = objHandle;
            oPosition = 3;
        } else {
            o = defaultHandle;
            oPosition = defaultPosition;
        }

        HGQueryCondition query = and(incidentAt(s, sPosition), incidentAt(p, pPosition), incidentAt(o, oPosition));

        List<HGHandle> rs = graph.findAll(query);
        for (HGHandle current : rs)
        {
            RdfTripleLink triple = graph.get(current);
            RdfTriple foundTriple = new RdfTriple(graph, triple);
            triples.add(foundTriple);
        }

        return triples;
    }

    private Set<RdfTriple> getAllTriples()
    {
        Set<RdfTriple> triples = new HashSet<RdfTriple>();
        HGQueryCondition query = new AtomTypeCondition(RdfTripleLink.class);

        List<HGHandle> rs = graph.findAll(query);
        for (HGHandle current : rs) {
            RdfTripleLink res = graph.get(current);
            triples.add(new RdfTriple(graph, res));
        }

        return triples;
    }

    public Set<RdfTriple> getTriplesWithSubject(String subject) {

        Resource resource = new URI(subject);
        return getTriples(resource, null, null);

    }

    public Set<RdfTriple> getTriplesWithPredicate(String predicate) {

        Resource resource = new URI(predicate);
        return getTriples(null, resource,null);

    }

    public Set<RdfTriple> getTriplesWithURIObject(String object) {

        Resource resource = new URI(object);
        return getTriples(null, null, resource);

    }

    public Set<RdfTriple> getTriplesWithLiteralObject(Object object) {

        Resource resource = new Literal(object);
        return getTriples(null, null, resource);

    }

    public Set<Resource> getInstanesOf(String className) {

        Set<Resource> uris = new HashSet<Resource>();
        Resource resource = new URI(className);
        Set<RdfTriple> triples = getTriples(null, RdfsVocabulary.RDF_TYPE, resource);
        for (RdfTriple triple : triples) {
            uris.add(triple.getSubject());
        }

        return uris;
    }




    private HGHandle getUniqueResourceHandle(Resource resource) {

        if (resource==null) return null;

        Resource res;

        if (resource.type== Resource.Type.URI)
        {
            res = HGQuery.hg.getOne(graph, and(HGQuery.hg.type(URI.class), HGQuery.hg.eq("value", resource.getValue())));
        }

        else {
            res = HGQuery.hg.getOne(graph, and(HGQuery.hg.type(Literal.class), HGQuery.hg.eq("value", resource.getValue())));
        }

        HGHandle handle;

        if (res==null) {
            handle = HGQuery.hg.assertAtom(graph, resource);
        }
        else {
            handle = graph.getHandle(res);
        }
        return handle;

    }
}
