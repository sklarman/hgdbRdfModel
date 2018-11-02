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
        Resource ageResource = new URI("http://age");
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

//        _://id/79b4b8d2-6f3b-475a-8395-f8a291b4cea9 = <http://szymon> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://Person>
//        _://id/74b4b5fe-b8c4-42ab-a827-c14232189ff0 = <http://szymon> <http://age> 37
//        _://id/539bbc3a-2b7f-407a-9b37-1e72a77c4814 = <http://szymon> <http://owns> <http://dog>
//        _://id/16fad93f-4811-441a-8546-6bd1f7f162ea = <http://szymon> <http://name> "szymon"
//        _://id/55ad6c56-31c5-433f-8905-ccf6cf1f9bcd = <http://szymon> <http://owns> <http://cat>

        
        System.out.println();
        Set<RdfTriple> set2 = model.getTriplesWithPredicate("http://owns");
        printTriples(set2);

//        _://id/6162462a-75b1-43c8-a6c0-714ea5816456 = <http://mary> <http://owns> <http://cat>
//        _://id/539bbc3a-2b7f-407a-9b37-1e72a77c4814 = <http://szymon> <http://owns> <http://dog>
//        _://id/55ad6c56-31c5-433f-8905-ccf6cf1f9bcd = <http://szymon> <http://owns> <http://cat>


        System.out.println();
        Set<RdfTriple> set3 = model.getTriplesWithURIObject("http://cat");
        printTriples(set3);

//        _://id/6162462a-75b1-43c8-a6c0-714ea5816456 = <http://mary> <http://owns> <http://cat>
//        _://id/55ad6c56-31c5-433f-8905-ccf6cf1f9bcd = <http://szymon> <http://owns> <http://cat>


        System.out.println();
        Set<RdfTriple> set4 = model.getTriplesWithLiteralObject(37);
        printTriples(set4);

//        _://id/74b4b5fe-b8c4-42ab-a827-c14232189ff0 = <http://szymon> <http://age> 37


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

