package repository;

import model.Categoria;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.ArrayList;

public class CategoriaRepository {

    private static final String rutaArchivo="data/categorias.xml";

    public CategoriaRepository(){
        File archivo = new File(rutaArchivo);
        if(!archivo.exists()){
            guardarTodo(new ArrayList<>());
        }
    }

    public ArrayList<Categoria> listar(){
        ArrayList<Categoria> lista = new ArrayList<>();
        try{
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
            DocumentBuilder constructor = fabrica.newDocumentBuilder();

            Document documento = constructor.parse(new File(rutaArchivo));
            documento.getDocumentElement().normalize();

            NodeList nodosCategoria = documento.getElementsByTagName("categoria");

            for (int i = 0; i < nodosCategoria.getLength(); i++) {
                Element elementoCategoria = (Element) nodosCategoria.item(i);

                int id = Integer.parseInt(obtenerTexto(elementoCategoria, "id"));
                String descripcion = obtenerTexto(elementoCategoria, "descripcion");

                lista.add(new Categoria(id, descripcion));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return lista;
    }

    private String obtenerTexto(Element elementoPadre, String nombreEtiqueta) {
        NodeList nodos = elementoPadre.getElementsByTagName(nombreEtiqueta);
        return nodos.item(0).getTextContent();
    }

    public Categoria buscarPorId(int id) {
        for (Categoria categoria : listar()) {
            if (categoria.getId() == id) {
                return categoria;
            }
        }
        return null;
    }

    public int obtenerSiguienteId() {
        int maximo = 0;
        for (Categoria categoria : listar()) {
            if (categoria.getId() > maximo) {
                maximo = categoria.getId();
            }
        }
        return maximo + 1;
    }

    public void agregar(Categoria categoria) {
        ArrayList<Categoria> lista = listar();
        lista.add(categoria);
        guardarTodo(lista);
    }

    public void actualizar(Categoria categoriaActualizada) {
        ArrayList<Categoria> lista = listar();
        for (Categoria categoria : lista) {
            if (categoria.getId() == categoriaActualizada.getId()) {
                categoria.setDescripcion(categoriaActualizada.getDescripcion());
            }
        }
        guardarTodo(lista);
    }

    public void eliminar(Categoria categoria) {
        ArrayList<Categoria> lista = listar();
        lista.removeIf(c -> c.getId() == categoria.getId());
        guardarTodo(lista);
    }

    private void guardarTodo(ArrayList<Categoria> lista) {
        try {
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
            DocumentBuilder constructor = fabrica.newDocumentBuilder();
            Document documento = constructor.newDocument();

            Element raiz = documento.createElement("categorias");
            documento.appendChild(raiz);

            for (Categoria categoria : lista) {
                Element elementoCategoria = documento.createElement("categoria");

                Element idElemento = documento.createElement("id");
                idElemento.setTextContent(String.valueOf(categoria.getId()));
                elementoCategoria.appendChild(idElemento);

                Element descripcionElemento = documento.createElement("descripcion");
                descripcionElemento.setTextContent(categoria.getDescripcion());
                elementoCategoria.appendChild(descripcionElemento);

                raiz.appendChild(elementoCategoria);
            }

            File carpetaDatos = new File("data");
            if (!carpetaDatos.exists()) {
                carpetaDatos.mkdirs();
            }

            TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
            Transformer transformador = fabricaTransformador.newTransformer();
            transformador.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource fuente = new DOMSource(documento);
            StreamResult destino = new StreamResult(new File(rutaArchivo));
            transformador.transform(fuente, destino);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
