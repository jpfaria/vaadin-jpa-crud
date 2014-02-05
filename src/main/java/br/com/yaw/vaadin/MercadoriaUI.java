package br.com.yaw.vaadin;

import java.util.Locale;

import org.apache.log4j.Logger;

import br.com.yaw.vaadin.ds.MercadoriaJPAContainer;
import br.com.yaw.vaadin.ui.MercadoriaTable;
import br.com.yaw.vaadin.ui.MercadoriaWindow;
import br.com.yaw.vaadin.ui.SobreWindow;

import com.vaadin.annotations.Theme;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;

/**
 * Classe principal da aplicação, o ponto de entrada.
 * 
 * <p>Com essa classe o Vaadin cria os componentes para apresentar na página principal (Browser).</p>
 * 
 * <p>O método <code>init</code> é executado quando uma nova sessão do navegador é aberta.</p>
 * 
 * @see <code>web.xml</code>
 */
@Theme("chameleon")
@SuppressWarnings("serial")
public class MercadoriaUI extends UI {
	
	private Logger log = Logger.getLogger(MercadoriaUI.class);
	
	private Table table;
	
    @Override
    protected void init(VaadinRequest request) {
    	setLocale(new Locale("pt", "BR"));
    	final MercadoriaJPAContainer datasource = new MercadoriaJPAContainer();
    	table = new MercadoriaTable(datasource);
		table.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				MercadoriaWindow window = new MercadoriaWindow(datasource);
				window.edit(Integer.valueOf(event.getItemId().toString()));
			}
		});
		
    	VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		setContent(content);
		
		VerticalLayout vertical = putAllOnVertical(buildTop(), table, 
				buildBarButtons(datasource), buildBottom());
		content.addComponent(vertical);
		content.setComponentAlignment(vertical, Alignment.MIDDLE_CENTER);
		
		addErrorHandle(content);
		
		//log.debug("Pagina principal inicializada!");
    }
    
    private VerticalLayout putAllOnVertical(Component... components) {
    	VerticalLayout vertical = new VerticalLayout();
    	for (Component c: components) {
    		if (c != null) {
    			vertical.addComponent(c);
        		vertical.setComponentAlignment(c, Alignment.MIDDLE_CENTER);
    		}
    	}
    	return vertical;
    }
    
    private HorizontalLayout buildTop() {
    	Label lProjeto = new Label("Projeto exemplo: Vaadin e JPA no Openshift");
		lProjeto.addStyleName(Runo.LABEL_H2);
		//String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		Image logo = new Image(null, new ExternalResource("http://s3-sa-east-1.amazonaws.com/yaw-site-sp/static/img/logo_yaw.png"));
		
		HorizontalLayout layoutTop = new HorizontalLayout();
		layoutTop.setHeight("100");
		layoutTop.setWidth("650");
		layoutTop.addComponent(logo);
		layoutTop.addComponent(lProjeto);
		layoutTop.setExpandRatio(lProjeto, 1.0f);
		
		layoutTop.setComponentAlignment(lProjeto, Alignment.MIDDLE_CENTER);
		
		return layoutTop;
    }
    
    private HorizontalLayout buildBarButtons(final MercadoriaJPAContainer datasource) {
    	Button bIncluir = new Button("Incluir");
		bIncluir.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				MercadoriaWindow window = new MercadoriaWindow(datasource);
				window.create();
			}
		});
		
		Button bAtualizar = new Button("Atualizar");
		bAtualizar.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				datasource.refresh();
			}
		});
		
		Button bSobre = new Button("Sobre");
		bSobre.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				new SobreWindow().show();
			}
		});
		bSobre.setClickShortcut(KeyCode.F1);
		
		Button[] buttons = {bIncluir, bAtualizar, bSobre};
		HorizontalLayout barButton = new HorizontalLayout();
		barButton.setHeight("50");
		
		for (Button b: buttons) {
			b.setStyleName(Runo.BUTTON_BIG);
			barButton.addComponent(b);
			barButton.setComponentAlignment(b, Alignment.MIDDLE_CENTER);
		}
		
		return barButton;
    }
    
    private HorizontalLayout buildBottom() {
    	String content = "<a href='http://www.yaw.com.br' " +
    			"title='YaW Tecnologia' target='open'>YaW Tecnologia</a> | " +
    			"<small><strong>Projeto livre</strong>, demonstração das tecnologias " +
    			"<em>Vaadin </em> e <em>JPA</em> na plataforma de " +
    			"<strong>cloud computing</strong> da Red Hat, o <em>OpenShift</em>. " +
    			"(<a href='http://www.yaw.com.br/open/projetos/jsf2-gae/' title='saiba mais' target='open'>saiba mais</a>) </small>";
    	Label text = new Label(content);
    	text.setContentMode(ContentMode.HTML);
    	
    	HorizontalLayout layoutbottom = new HorizontalLayout();
    	layoutbottom.setHeight("100");
    	layoutbottom.setWidth("650");
    	layoutbottom.addComponent(text);
    	
    	return layoutbottom;
    }
    
    /**
     * Adiciona tratador geral para exceção não capturadas.
     * @param content
     */
    private void addErrorHandle(final VerticalLayout content) {
    	UI.getCurrent().setErrorHandler(new DefaultErrorHandler() {
    		@Override
    		public void error(com.vaadin.server.ErrorEvent event) {
    			String cause = "Erro durante execução:\n";
    	        for (Throwable t = event.getThrowable(); t != null;
    	             t = t.getCause())
    	            if (t.getCause() == null)
    	                cause += t.getClass().getName() + "\n";
    	        
    	        log.error("Exceção capturada",event.getThrowable());
    	        Notification.show(cause, Type.ERROR_MESSAGE);
    	        
    	        doDefault(event);
    		}
    	});
    }
    
}
