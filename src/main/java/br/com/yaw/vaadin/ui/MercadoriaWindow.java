package br.com.yaw.vaadin.ui;

import org.apache.log4j.Logger;
import org.vaadin.dialogs.ConfirmDialog;

import br.com.yaw.vaadin.ds.MercadoriaJPAContainer;
import br.com.yaw.vaadin.model.Mercadoria;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * Tela de Edição / Cadastro / Delete da <code>Mercadoria</code>.
 * 
 * @author YaW Tecnologia
 */
public class MercadoriaWindow extends Window implements Button.ClickListener {

	private static Logger log = Logger.getLogger(MercadoriaWindow.class);
	
	private static final long serialVersionUID = -2996627404786697495L;

	private FormLayout layout;
	private BeanFieldGroup<Mercadoria> binder;
	private HorizontalLayout buttons;	
	private Button bSalvar;
	private Button bCancelar;
	private Button bExcluir;
	
	private MercadoriaJPAContainer datasource;
	
	public MercadoriaWindow(MercadoriaJPAContainer datasource) {
		this.datasource = datasource;
		init();
		setModal(true);
	}
	
	private void init() {
		layout = new FormLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		
		bSalvar = new Button("Salvar");
		bSalvar.addClickListener(this);
		bSalvar.setClickShortcut(KeyCode.ENTER);
		
		bCancelar = new Button("Cancelar");
		bCancelar.addClickListener(this);
		bCancelar.setClickShortcut(KeyCode.ESCAPE);
		
		bExcluir = new Button("Excluir");
		bExcluir.addClickListener(this);
		bExcluir.setVisible(false);
		
		buttons = new HorizontalLayout();
		buttons.addComponent(bSalvar);
		buttons.addComponent(bCancelar);
		buttons.addComponent(bExcluir);
		
		setContent(layout);
        
        setHeight("370");
        setWidth("400");
	}
	
	/**
	 * Apresenta a <code>Window</code> em forma de edição.
	 * @param id
	 */
	public void edit(Integer id) {
		try {
			setCaption("Editar Mercadoria");
			Mercadoria m = datasource.getItem(id).getEntity();
			bindingFields(m);
			bExcluir.setVisible(true);
			UI.getCurrent().addWindow(this);
		} catch (Exception ex) {
			log.error("Não conseguiu carregar a tela de edição: ", ex);
			Notification.show("Não consegui abrir a mercadoria para edição!\n"+ex.getMessage(), Type.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Apresenta a <code>Window</code> em forma de edição.
	 */
	public void create() {
		setCaption("Nova Mercadoria");
		bindingFields(new Mercadoria());
		UI.getCurrent().addWindow(this);
	}
	
	/**
	 * Cria o formulário com os campos para preenchimento.
	 * @param m
	 */
	private void bindingFields(Mercadoria m) {
		binder = new BeanFieldGroup<Mercadoria>(Mercadoria.class);
		binder.setItemDataSource(m);
		Field<?> field = null;
		field = binder.buildAndBind("Nome", "nome");
		field.setWidth("140");
		layout.addComponent(field);
		
		field = binder.buildAndBind("Descrição", "descricao");
		field.setWidth("200");
		layout.addComponent(field);
		
		field = binder.buildAndBind("Quantidade", "quantidade");
		field.setWidth("80");
		layout.addComponent(field);
		
		field = binder.buildAndBind("Preço", "preco");
		field.setWidth("80");
		
		layout.addComponent(field);
		layout.addComponent(binder.buildAndBind("Id", "id"));
		
		layout.addComponent(buttons);
	}
	
	/**
	 * Implementa tratadores para os eventos dos <code>Button</code> dessa tela.
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton() == bSalvar) {
			try {
				binder.commit();
			} catch (CommitException e) {
				Notification.show("Preencha o formulário corretamente");
				return;
			}
			
			try {
				datasource.addEntity(binder.getItemDataSource().getBean());
				//log.debug("Mercadoria persistida!");
				Notification.show("Mercadoria persistida!", Type.HUMANIZED_MESSAGE);
			} catch(Exception e) {
				log.debug("Não consegui salvar a mercadoria!",e);
				Notification.show("Nao consegui salvar a mercadoria!\n"+e.getMessage(), Type.ERROR_MESSAGE);
				return;
			}
		} else if (event.getButton() == bExcluir) {
			ConfirmDialog.show(this.getUI(), "Confirma a exclusão?",
			        new ConfirmDialog.Listener() {
			            public void onClose(ConfirmDialog dialog) {
			                if (dialog.isConfirmed()) {
			                	try {
			                		datasource.removeItem(binder.getItemDataSource().getBean().getId());
			                		//log.debug("Excluiu a Mercadoria!");
				    			} catch (Exception e) {
				    				log.debug("Não consegui remover a Mercadoria!",e);
				    				Notification.show("Nao consegui remover a Mercadoria!\n"+e.getMessage(), Type.ERROR_MESSAGE);
			                	}
			                	close();
			                }
			            }
			        });
			return;
		} else if (event.getButton() == bCancelar) {
			binder.discard();
		}
		close();
	}
	
}
