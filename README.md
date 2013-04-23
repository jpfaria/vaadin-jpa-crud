vaadin-jpa-crud
===============

Aplicativo web, desenvolvido utilizando o Vaadin e JPA. O Vaadin é um framework para desenvolvimento aplicações ricas para web (RIA - Rich Internet Application). A proposta do Vaadin é criar um modelo de programação robusto e eficaz para os componentes das camadas servidor e cliente, utilizando apenas a linguagem Java. O Vaadin trabalha sobre o GWT (Google Web Toolkit), a tecnologia RIA do Google.
Esse projeto utiliza algumas extensões do Vaadin (add-ons) para integração com outras tecnologias Java, como a JPA (Java Persistence API). A aplicação foi implantada na plataforma de cloud computing da Red Hat, o Openshift. 

A Java Persistence API (JPA) é o mecanismo padrão ORM do Java. O Hibernate é utilizado como o provider JPA desse projeto. Os dados dessa aplicação são armazenados no banco de dados MySQL.

O objetivo dessa aplicação é servir como conteúdo no estudo de desenvolvimento de soluções ricas para web (RIA), utilizando a nuvem.

Você pode acessar a aplicação no Openshift através da url: https://vaadin-yaw.rhcloud.com/

Detalhes da implementação
-------
Tecnologias utilizadas na implementação:
* Vaadin: utilizamos o Vaadin para desenvolvimento da camada visual (front-end) e integração com os serviços de negócio (servidor);
* JPA: mecanismo padrão do Java para resolver o mapeamento objeto relacional (ORM);
* Hibernate: provedor JPA.
* Bean Validation: mecanismo padrão do Java para determinar regras de validação através de anotações;

Pré-requisitos
-------
* JDK - versão 1.6 ou mais recente;
* Eclipse IDE - o projeto possui as configurações do Eclipse;
* Plugin do Vaadin para Eclipse (opcional) - plugin com funcionalidades para desenvolvimento com o Vaadin.
* Maven - para build e dependências.
* Web Servlet Java EE - Apache Tomcat 7 ou mais recente;
* MySQL - versão 5 ou mais recente;

Saiba mais
-------
Visite a página do projeto:
http://www.yaw.com.br/open/projetos/vaadin-jpa-crud/

