import org.hypergraphdb.HyperGraph;

import java.util.Set;

public class Main {

    public static void main(String[] args) {

        HyperGraph graph = new HyperGraph("db/hgdb");

        RdfModel model = new RdfModel(graph);

        Resource szymonResource = new URI("http://szymon");
        Resource maryResource = new URI("http://mary");
        Resource ownsResource = new URI("http://owns");
        Resource catResource = new URI("http://cat");
        Resource dogResource = new URI("http://dog");
        Resource nameResource = new URI("http://name");
        Resource personResource = new URI("http://Person");
        Resource name = new Literal("szymon");
        Resource ageResource = new Literal("http://age");
        Resource age = new Literal(37);

        model.assertTriple(szymonResource, ownsResource, catResource);
        model.assertTriple(szymonResource, ownsResource, dogResource);
        model.assertTriple(maryResource, ownsResource, catResource);
        model.assertTriple(szymonResource, nameResource, name);
        model.assertTriple(szymonResource, ageResource, age);
        model.assertTriple(szymonResource, RdfsVocabulary.RDF_TYPE, personResource);
        model.assertTriple(maryResource, RdfsVocabulary.RDF_TYPE, personResource);

        Set<RdfTriple> set1 = model.getTriplesWithSubject("http://szymon");
        printTriples(set1);

//        uniqueIdPlaceholder = <http://szymon> <http://name> "szymon"
//        uniqueIdPlaceholder = <http://szymon> <http://owns> <http://cat>
//        uniqueIdPlaceholder = <http://szymon> <http://owns> <http://dog>
//        uniqueIdPlaceholder = <http://szymon> <http://age> 37
//        uniqueIdPlaceholder = <http://szymon> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://Person>


        System.out.println();
        Set<RdfTriple> set2 = model.getTriplesWithPredicate("http://owns");
        printTriples(set2);

//        uniqueIdPlaceholder = <http://szymon> <http://owns> <http://dog>
//        uniqueIdPlaceholder = <http://mary> <http://owns> <http://cat>
//        uniqueIdPlaceholder = <http://szymon> <http://owns> <http://cat>


        System.out.println();
        Set<RdfTriple> set3 = model.getTriplesWithURIObject("http://cat");
        printTriples(set3);

//        uniqueIdPlaceholder = <http://szymon> <http://owns> <http://cat>
//        uniqueIdPlaceholder = <http://mary> <http://owns> <http://cat>


        System.out.println();
        Set<RdfTriple> set4 = model.getTriplesWithLiteralObject(37);
        printTriples(set4);

//        uniqueIdPlaceholder = <http://szymon> <http://age> 37

        System.out.println();
        Set<Resource> set5 = model.getInstanesOf("http://Person");
        printResources(set5);

//        <http://mary>
//        <http://szymon>

        graph.close();

    }

    private static void printResources(Set<Resource> resourceSet)
    {
        for (Resource res : resourceSet) {
            System.out.println(res.toString());
        }
    }

    private static void printTriples(Set<RdfTriple> tripleSet)
    {
        for (RdfTriple triple : tripleSet) {
            System.out.println(triple.toString());
        }
    }

}

