package model;

public class Categoria implements EntidadTabla {

    public static final String TITULO = "Gestion de Categorias de Recursos";
    public static final String NOMBRE_SINGULAR = "categoria";
    public static final String[] COLUMNAS = {"ID", "Descripcion"};
    public static final String PREFIJO_ID = "CAT-";

    private String id;
    private String descripcion;

    public Categoria() { }

    public Categoria(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    @Override public String getId() { return id; }
    @Override public void setId(String id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public Object[] toFila() {
        return new Object[]{id, descripcion};
    }

    @Override
    public String toString() {
        // Util para mostrar la categoria en un JComboBox/JList
        return (id != null ? id + " - " : "") + descripcion;
    }
}