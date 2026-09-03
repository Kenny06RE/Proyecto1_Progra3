package util;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import java.awt.Component;

public class BotonTablaEditor extends AbstractCellEditor implements TableCellEditor {

    public interface AccionFila {
        void ejecutar(int filaModelo);
    }

    private final JButton boton;
    private int filaActual;

    public BotonTablaEditor(String texto, AccionFila accion) {
        boton = new JButton(texto);
        boton.addActionListener(evento -> {
            fireEditingStopped();
            accion.ejecutar(filaActual);
        });
    }

    public Component getTableCellEditorComponent(JTable tabla, Object valor,
                                                 boolean seleccionada, int fila, int columna) {
        this.filaActual = fila;
        return boton;
    }

    public Object getCellEditorValue() {
        return "";
    }
}