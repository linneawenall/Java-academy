package com.cosylab.jwenall.academy.problem8;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


class HeaderListener extends MouseAdapter {
    protected JTable table;
    protected ParsingSorterMain p;

    public HeaderListener(JTable t) {
      table = t;
    p = new ParsingSorterMain();
    }

    public void mouseClicked(MouseEvent e) {
      TableColumnModel colModel = table.getColumnModel();
      int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
      int modelIndex = colModel.getColumn(columnModelIndex)
          .getModelIndex();
      if (modelIndex < 0)
        return;
      
      
      if (sortCol == modelIndex)
        isSortAsc = !isSortAsc;
      else
        sortCol = modelIndex;

      for (int i = 0; i < columnsCount; i++) { 
        TableColumn column = colModel.getColumn(i);
        column.setHeaderValue(getColumnName(column.getModelIndex()));
      }
      table.getTableHeader().repaint();

      Collections.sort(vector,new MyComparator(isSortAsc));
      table.tableChanged(new TableModelEvent(MyTableModel.this));
      table.repaint();
    }
  }
}
