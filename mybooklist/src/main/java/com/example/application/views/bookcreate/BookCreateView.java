package com.example.application.views.bookcreate;
// IMPORTAÇÕES ----------------------
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.example.application.data.service.SamplePersonService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Book Create")
@Route(value = "Book-Create", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class BookCreateView extends Composite<VerticalLayout> {
    // CONSTRUCTOR -------------------------
    public BookCreateView() {

        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn5 = new VerticalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        TextField textField = new TextField();
        MultiSelectComboBox multiSelectComboBox = new MultiSelectComboBox();
        DatePicker datePicker = new DatePicker();
        VerticalLayout layoutColumn4 = new VerticalLayout();
        TextField textField2 = new TextField();
        MultiSelectComboBox multiSelectComboBox2 = new MultiSelectComboBox();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        DatePicker datePicker2 = new DatePicker();
        Checkbox checkbox = new Checkbox();
        HorizontalLayout layoutRow4 = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        VerticalLayout layoutColumn6 = new VerticalLayout();

        getContent().setWidthFull();
        getContent().addClassName(Padding.LARGE);
        layoutRow.setWidthFull();
        layoutRow.setFlexGrow(1.0, layoutColumn5);
        layoutColumn5.setWidth(null);
        layoutRow.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth(null);

        h3.setText("Book add");

        layoutRow2.setWidthFull();
        layoutRow2.addClassName(Gap.LARGE);
        layoutRow2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth(null);

        textField.setLabel("Name");
        textField.setWidthFull();

        multiSelectComboBox.setLabel("Genre");
        multiSelectComboBox.setWidthFull();
        setMultiSelectComboBoxSampleDataGenre(multiSelectComboBox);

        datePicker.setLabel("Start date");
        datePicker.setWidthFull();
        
        layoutRow2.setFlexGrow(1.0, layoutColumn4);
        layoutColumn4.setWidth(null);

        textField2.setLabel("Autor");
        textField2.setWidthFull();

        multiSelectComboBox2.setLabel("Theme");
        multiSelectComboBox2.setWidthFull();
        setMultiSelectComboBoxSampleDataTheme(multiSelectComboBox2);

        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidthFull();

        datePicker2.setLabel("Finish date");
        layoutRow3.setFlexGrow(1.0, datePicker2);

        checkbox.setLabel("Favorite");
        layoutRow4.addClassName(Gap.MEDIUM);

        buttonPrimary.setText("Save");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(e -> {
            String name = textField.getValue();
            String autor = textField2.getValue();
            Object genre = multiSelectComboBox.getSelectedItems().stream().map(Object::toString).collect(Collectors.joining(","));
            Object theme = multiSelectComboBox2.getSelectedItems().stream().map(Object::toString).collect(Collectors.joining(","));
            String startDate = datePicker.getValue().toString();
            String finishDate = datePicker2.getValue().toString();
            boolean favorite = checkbox.getValue();

            addBookToDatabase(name, autor, genre, theme, startDate, finishDate, favorite);
        });

        buttonSecondary.setText("Cancel");
        
        layoutRow.setFlexGrow(1.0, layoutColumn6);
        layoutColumn6.setWidth(null);
        getContent().add(layoutRow);
        layoutRow.add(layoutColumn5);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(layoutRow2);
        layoutRow2.add(layoutColumn3);
        layoutColumn3.add(textField);
        layoutColumn3.add(multiSelectComboBox);
        layoutColumn3.add(datePicker);
        layoutRow2.add(layoutColumn4);
        layoutColumn4.add(textField2);
        layoutColumn4.add(multiSelectComboBox2);
        layoutColumn4.add(layoutRow3);
        layoutRow3.add(datePicker2);
        layoutColumn2.add(checkbox);
        layoutColumn2.add(layoutRow4);
        layoutRow4.add(buttonPrimary);
        layoutRow4.add(buttonSecondary);
        layoutRow.add(layoutColumn6);
    }

    record SampleItem(String value, String label, Boolean disabled) {
        public String toString() {
            return label;
        } 
    }

    private void setMultiSelectComboBoxSampleDataGenre(MultiSelectComboBox multiSelectComboBoxGenre) {
        List<SampleItem> sampleItemsGenre = new ArrayList<>();
        sampleItemsGenre.add(new SampleItem("action", "Action", null));
        sampleItemsGenre.add(new SampleItem("adventure", "Adventure", null));
        sampleItemsGenre.add(new SampleItem("comedy", "Comedy", null));
        sampleItemsGenre.add(new SampleItem("drama", "Drama", null));
        sampleItemsGenre.add(new SampleItem("fantasy", "Fantasy", null));
        sampleItemsGenre.add(new SampleItem("mystery", "Mystery", null));
        sampleItemsGenre.add(new SampleItem("Romance", "Romance", null));
        sampleItemsGenre.add(new SampleItem("scifi", "Sci-Fi", null));
        sampleItemsGenre.add(new SampleItem("sports", "Sports", null));
        sampleItemsGenre.add(new SampleItem("suspense", "Suspense", null));
        multiSelectComboBoxGenre.setItems(sampleItemsGenre);
        multiSelectComboBoxGenre.setItemLabelGenerator(item -> ((SampleItem) item).label());
    }

    private void setMultiSelectComboBoxSampleDataTheme(MultiSelectComboBox multiSelectComboBoxTheme) {
        List<SampleItem> sampleItemsTheme = new ArrayList<>();
        sampleItemsTheme.add(new SampleItem("educational", "Educational", null));
        sampleItemsTheme.add(new SampleItem("historical", "Historical", null));
        sampleItemsTheme.add(new SampleItem("martialarts", "Martial Arts", null));
        sampleItemsTheme.add(new SampleItem("military", "Military", null));
        sampleItemsTheme.add(new SampleItem("music", "Music", null));
        sampleItemsTheme.add(new SampleItem("psychological", "Psychological", null));
        sampleItemsTheme.add(new SampleItem("space", "Space", null));
        multiSelectComboBoxTheme.setItems(sampleItemsTheme);
        multiSelectComboBoxTheme.setItemLabelGenerator(item -> ((SampleItem) item).label());
    }

    private void addBookToDatabase(String name, String autor, Object genre, Object theme, String startDate,  String finishDate, boolean favorite) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:mybooklist\\target\\classes\\biblioteca.db");
            try (Statement statement = DriverManager.getConnection("jdbc:sqlite:mybooklist\\target\\classes\\biblioteca.db").createStatement()) {
                //CRIAÇÃO DE TABELA PARA O BANCO DE DADOS
                String createTableSQL = "CREATE TABLE IF NOT EXISTS booksTable ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "bookName TEXT,"
                        + "autorName TEXT,"
                        + "genre TEXT,"
                        + "theme TEXT,"
                        + "startDate TEXT,"
                        + "finishDate TEXT,"
                        + "favorite INTEGER);";
                statement.executeUpdate(createTableSQL);
                System.out.println("Tabela criada com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao manipular o banco de dados!");
                e.printStackTrace();}
            //INSERT DE VALORES PARA CRIAÇÃO DO LIVRO
            String insertQuery = "INSERT INTO booksTable (bookName, autorName, genre, theme, startDate, finishDate, favorite) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, autor);
            preparedStatement.setObject(3, genre);
            preparedStatement.setObject(4, theme);
            preparedStatement.setString(5, startDate);
            preparedStatement.setString(6, finishDate);
            preparedStatement.setBoolean(7, favorite);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao adicionar livro ao banco de dados");
        }     
    }
 
    private void setGridSampleData(Grid grid) {
        grid.setItems(query -> samplePersonService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
    }

    @Autowired()
    private SamplePersonService samplePersonService;
}