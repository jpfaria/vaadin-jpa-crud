package br.com.yaw.vaadin.ui;

import br.com.yaw.vaadin.util.ApplicationProperties;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * <code>Window</code> com os detalhes <i>Sobre</i> a aplicação.
 * 
 * @author YaW Tenologia
 */
public class SobreWindow extends Window {

	private static final long serialVersionUID = -4327984100145879448L;

	private Button bFechar;
	
	public SobreWindow() {
		setCaption("Sobre a aplicação");
		init();
		setModal(true);
	}
	
	private void init() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		
		bFechar = new Button("Fechar");
		bFechar.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
		bFechar.setClickShortcut(KeyCode.ESCAPE);
		
		layout.addComponent(buildLine("Aplicação:", ApplicationProperties.getTitulo()));
		layout.addComponent(buildLine("Versão:", ApplicationProperties.getVersao()));
		layout.addComponent(buildLine("Desenvolvido por:", ApplicationProperties.getDesenvolvidoPor()));
		layout.addComponent(buildLine("Build:", ApplicationProperties.getBuild()));
		layout.addComponent(buildLine("Site:", ApplicationProperties.getSite()));
		
		layout.addComponent(bFechar);
		
		setContent(layout);
        setHeight("300");
        setWidth("430");
	}
	
	public void show() {
		UI.getCurrent().addWindow(this);
	}
	
	private HorizontalLayout buildLine(String left, String right) {
		Label lTitle = new Label(String.format("<b>%s</b>",left));
		lTitle.setContentMode(ContentMode.HTML);
		Label lContent = new Label(right);
		lContent.setWidth("100%");
		
		HorizontalLayout line = new HorizontalLayout();
		line.addComponent(lTitle);
		line.addComponent(lContent);
		line.setWidth("420");
		line.setExpandRatio(lTitle, 1.0f);
		line.setExpandRatio(lContent, 2.0f);
		
		return line;
	}
	
}
