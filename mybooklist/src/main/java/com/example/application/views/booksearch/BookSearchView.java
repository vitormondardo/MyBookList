package com.example.application.views.booksearch;
//IMPORTAÇÕES ----------------------------------
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;
import com.example.application.data.entity.Books;
import com.example.application.data.entity.SamplePerson;
import com.example.application.data.service.SamplePersonService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

@PageTitle("Book Search")
@Route(value = "Book-Search", layout = MainLayout.class)
@Uses(Icon.class)
public class BookSearchView extends Composite<VerticalLayout> {
    // ATRIBUTO ------------------------
    public Integer selectedBookId;

    // CONSTRUCTOR-----------------------------
    public BookSearchView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        MultiSelectComboBox multiSelectComboBox = new MultiSelectComboBox();
        MultiSelectComboBox multiSelectComboBox2 = new MultiSelectComboBox();
        DatePicker datePicker = new DatePicker();
        DatePicker datePicker2 = new DatePicker();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Checkbox checkbox = new Checkbox();
        Button buttonPrimary = new Button();
        Grid minimalistGrid = new Grid(SamplePerson.class);
        getContent().setHeightFull();
        getContent().setWidthFull();
        getContent().addClassName(Gap.XSMALL);
        getContent().addClassName(Padding.XSMALL);
        layoutRow.setWidthFull();
        layoutRow.addClassName(Gap.MEDIUM);
        
        textField.setLabel("Name");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, textField);

        textField2.setLabel("Autor");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, textField2);

        multiSelectComboBox.setLabel("Genre");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, multiSelectComboBox);
        setMultiSelectComboBoxSampleDataGenre(multiSelectComboBox);

        multiSelectComboBox2.setLabel("Theme");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, multiSelectComboBox2);
        setMultiSelectComboBoxSampleDataTheme(multiSelectComboBox2);

        datePicker.setLabel("Started after");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, datePicker);

        datePicker2.setLabel("Finished before");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, datePicker2);

        layoutRow2.setWidthFull();
        layoutRow2.addClassName(Gap.MEDIUM);

        checkbox.setLabel("Favorite");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, checkbox);

        buttonPrimary.setText("Search");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.CENTER, buttonPrimary);
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        minimalistGrid.addThemeVariants(
            GridVariant.LUMO_COMPACT, 
            GridVariant.LUMO_NO_BORDER,
            GridVariant.LUMO_NO_ROW_BORDERS);

        //Adiciona os dados ao grid
        setGridSampleData(minimalistGrid);
        //

        Grid<Books> gridCostumized = new Grid<>(Books.class, false);
        gridCostumized.addColumn(Books::getId).setHeader("ID");
        gridCostumized.addColumn(Books::getBookName).setHeader("Name");
        gridCostumized.addColumn(Books::getAutorName).setHeader("Autor");
        gridCostumized.addColumn(Books::getGenre).setHeader("Genre");
        gridCostumized.addColumn(Books::getTheme).setHeader("Theme");
        gridCostumized.addColumn(Books::getStartDate).setHeader("Start Date");
        gridCostumized.addColumn(Books::getFinishDate).setHeader("Finish Date");
        gridCostumized.addColumn(Books::getFavorite).setHeader("Favorite");

        //adicione uma coluna que seja um botão
gridCostumized.addComponentColumn(item -> {
    Button button = new Button("Edit");
    button.addClickListener(event -> {
        selectedBookId = item.getId(); // Defina o ID do livro selecionado
        UI.getCurrent().navigate("Book-Update/" + selectedBookId); // Redirecione para a página Book-Update com o ID
    });
    return button;
}).setHeader("Edit");

        List<Books> books = getBooksFromDatabase();
        gridCostumized.setItems(books);
  
        getContent().add(layoutRow);
        layoutRow.add(textField);
        layoutRow.add(textField2);
        layoutRow.add(multiSelectComboBox);
        layoutRow.add(multiSelectComboBox2);
        layoutRow.add(datePicker);
        layoutRow.add(datePicker2);
        getContent().add(layoutRow2);
        layoutRow2.add(checkbox);
        layoutRow2.add(buttonPrimary);

        //onde coloca a grid que via ser usada
        getContent().add(gridCostumized);

        //ATUALIZAR GRID
        buttonPrimary.addClickListener(event -> {
        // Outras ações de pesquisa, se houver
        updateGridData(gridCostumized); // Atualize o Grid com os novos dados
        });
        //FIM ATUALIZAR GRID
    }
    
    record SampleItem(String value, String label, Boolean disabled) {
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

// -- Fim para funcionar a Combobox

    private void setGridSampleData(Grid grid) {
        grid.setItems(query -> samplePersonService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
    }

    @Autowired()
    private SamplePersonService samplePersonService;

    private List<Books> getBooksFromDatabase() {

        List<Books> books = new ArrayList<>();
        try {
        // Configurar o DataSource para SQLite
        SQLiteConfig config = new SQLiteConfig();
        //config.setReadOnly(true); // Defina como "true" se for apenas leitura

        SQLiteDataSource dataSource = new SQLiteDataSource(config);
        dataSource.setUrl("jdbc:sqlite:mybooklist\\target\\classes\\biblioteca.db"); // Substitua pelo caminho correto para o arquivo SQLite

        // Estabelecer uma conexão com o banco de dados SQLite
        Connection connection = dataSource.getConnection();

        // Preparar a consulta SQL
        String query = "SELECT * FROM booksTable";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        // Executar a consulta
        ResultSet resultSet = preparedStatement.executeQuery();
            // Processar os resultados
            while (resultSet.next()) {
                Books book = new Books();
                book.setId(resultSet.getInt("Id"));
                book.setBookName(resultSet.getString("bookName"));
                book.setAutorName(resultSet.getString("autorName"));
                book.setGenre(resultSet.getString("genre"));
                book.setTheme(resultSet.getString("theme"));
                book.setStartDate(resultSet.getString("startDate"));
                book.setFinishDate(resultSet.getString("finishDate"));
                book.setFavorite(resultSet.getBoolean("favorite"));
                books.add(book);
            }

            System.out.println("Os livros" + books);
            // Fechar recursos
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    //METODO ATUALIZAR GRID
    private void updateGridData(Grid<Books> grid) {
        List<Books> books = getBooksFromDatabase(); // Obtenha os novos dados do banco de dados
        grid.setItems(books); // Atualize os dados no Grid
    }

}