package model;


public class Recurso implements EntidadTabla {

    public static final String TITULO = "Gestion de Recursos";
    public static final String NOMBRE_SINGULAR = "recurso";
    public static final String[] COLUMNAS = {"ID / Activo", "Categoria", "Descripcion"};

    private String id;
    private String categoriaId;
    private String descripcion;

    public Recurso() { }

    public Recurso(String id, String categoriaId, String descripcion) {
        this.id = id;
        this.categoriaId = categoriaId;
        this.descripcion = descripcion;
    }

    @Override public String getId() { return id; }
    @Override public void setId(String id) { this.id = id; }

    public String getCategoriaId() { return categoriaId; }
    public void setCategoriaId(String categoriaId) { this.categoriaId = categoriaId; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public Object[] toFila() {
        return new Object[]{id, categoriaId, descripcion};
    }
}