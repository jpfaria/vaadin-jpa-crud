package br.com.yaw.vaadin.ui;

import java.text.NumberFormat;

import br.com.yaw.vaadin.ds.MercadoriaJPAContainer;

import com.vaadin.data.Property;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

/**
 * <code>Table</code> para apresentar os registros da <code>Mercadoria</code>.
 * 
 * @author YaW Tecnologia
 */
public class MercadoriaTable extends Table {

	private static final long serialVersionUID = 7224804420418157408L;

	private NumberFormat fmtCurrency;
	
	public MercadoriaTable(MercadoriaJPAContainer mercadoriaContainer) {
		fmtCurrency = NumberFormat.getCurrencyInstance(UI.getCurrent().getLocale());
		setContainerDataSource(mercadoriaContainer);
		setWidth("650px");
		configColumns();
	}
	
	private void configColumns() {
		setVisibleColumns(new Object[]{"id", "nome", "descricao", "quantidade", "preco"});
		setColumnHeaders(new String[] {"#", "Nome", "Descrição", "Quantidade", "Preço"});
		addGeneratedColumn("preco", new CurrencyColumnGenerator());
	}
	
	/**
	 * Implementa um formatador de moeda durante a renderização da coluna.
	 */
	private class CurrencyColumnGenerator implements Table.ColumnGenerator {
		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {
			Property<?> prop = source.getItem(itemId).getItemProperty(columnId);
		        if (prop.getType().equals(Double.class)) {
		        	Label label = new Label(fmtCurrency.format((Double) prop.getValue()));
		        	label.addStyleName("column-type-value");
		            label.addStyleName("column-" + (String) columnId);
		            return label;
		        }
			return null;
		}
	}
}
