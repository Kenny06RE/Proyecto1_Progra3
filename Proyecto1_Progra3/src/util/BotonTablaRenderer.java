package util;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;

public class BotonTablaRenderer extends JButton implements TableCellRenderer {

    public BotonTablaRenderer(String texto) {
        setText(texto);
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable tabla, Object valor,
                                                   boolean seleccionada, boolean tieneFoco,
                                                   int fila, int columna) {
        return this;
    }
}