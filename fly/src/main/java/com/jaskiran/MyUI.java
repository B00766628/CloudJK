package com.jaskiran;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Slider;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.components.grid.MultiSelectionModel;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        Label logo = new Label("<H1>Feckin Flyers</H1> <p/> <h2>Termonfeckin's <strong>fifth</strong> best airline</h2><br>",ContentMode.HTML); 
        Connection connection = null;
        String connectionString ="jdbc:sqlserver://flying1ser.database.windows.net:1433;"+
        "database=Flying1DB;"+
        "user=kiran@flying1ser;"+
        "password={Test1234};"+
        "encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

                try 
        {
            // Connect with JDBC driver to a database
            connection = DriverManager.getConnection(connectionString);
            // Add a label to the web app with the message and name of the database we connected to 
            layout.addComponent(new Label("Connected to database: " + connection.getCatalog()));




        } 
        catch (Exception e) 
        {

            layout.addComponent(new Label(e.getMessage()));
        }
        List<Fly> mylist = new ArrayList<Fly>();
        try{
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM TimeTable;");
            
          
            
            while(rs.next())
            {   
                // Add a new Customer instantiated with the fields from the record (that we want, we might not want all the fields, note how I skip the id)
                mylist.add(new Fly(rs.getString("Departs"), 
                            rs.getString("departsfrom"), 
                            rs.getString("Arrives"), 
                            rs.getString("arrivesto")));
            }

            }
            catch (Exception e) 
            {
            
                layout.addComponent(new Label(e.getMessage()));
            }
            Grid<Fly> myGrid = new Grid<> ();
            myGrid.setItems(mylist);
            myGrid.addColumn(Fly::getDeparts).setCaption("Departs");
            myGrid.addColumn(Fly::getDepartsfrom).setCaption("From");
            myGrid.addColumn(Fly::getArrives).setCaption("Arrives");
            myGrid.addColumn(Fly::getArrivesto).setCaption("To");
            myGrid.setSelectionMode(SelectionMode.MULTI);
            myGrid.setSizeFull();    

        final HorizontalLayout layout1 = new HorizontalLayout();

        Label msg = new Label("",ContentMode.HTML);

        ComboBox<String> combo1 = new ComboBox<String>("Gender");
                combo1.setItems("female", "male" );

                ComboBox<String> combo2 = new ComboBox<String>("Status");
                combo2.setItems("infant", "child", "adult");        
  


        
        final TextField name = new TextField();
        name.setCaption("Full name of pessanger:");

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            if (name.isEmpty()){
                msg.setValue("<strong>Please enter your name!</strong>" );
                return;
                }
                if (combo2.isEmpty()){
                    msg.setValue("<strong>Please select gender and status</strong>");
                    return;
                }       
    
    
            if (combo1.isEmpty()){
                            msg.setValue("<strong>Please select gender and status</strong> ");
                            return;
            }
            Set<Fly> selectedElements = myGrid.getSelectedItems();
            
            int i =selectedElements.size();

 		if(selectedElements.size()<=0){
                msg.setValue("<strong>Please select at least one flight!</strong>");

                return;

            }
            double  totalCost ; 
            double  cost  = 10.50;
            if (combo2.getValue()=="child"){
                cost = 10.50*.5;
                totalCost =cost*i;
                msg.setValue("<h3>The total cost is <strong>"+"€"+ totalCost+"</strong></h3>");
                return;
            }
            if(combo2.getValue()=="infant"){
                cost = 10.50*.25;
                totalCost =cost*i;
                msg.setValue("<h3>The total cost is <strong>"+"€"+ totalCost+"</strong></h3>");
                return;
            }
               
            if(combo2.getValue()=="adult"&& combo1.getValue()=="female"){
                cost = 5.50;
                totalCost =cost*i;
                msg.setValue("<h3>The total cost is <strong>"+"€"+ totalCost+"</strong></h3>");
                return;
            }
               totalCost = cost*i;        
            msg.setValue("<h3>The total cost is <strong>"+"€"+ totalCost+"</strong></h3>");
                                     
                                           
        });
        layout1.addComponents(name,combo2,combo1);
        layout.addComponents(logo,myGrid,layout1, button, msg, new Label("B00766628"));

        
        
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
