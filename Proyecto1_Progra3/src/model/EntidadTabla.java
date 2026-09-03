package model;


//Esto lo deben cumplir todas las entidades que se muestran en una tabla

//El id se pone string ya que los id son alfanumericos y algunos los genera el usuario


public interface EntidadTabla {
    String getId();
    void setId(String id);
    Object[] toFila();
}