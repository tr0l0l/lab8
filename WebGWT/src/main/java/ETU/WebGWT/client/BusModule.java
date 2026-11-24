package ETU.WebGWT.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import ETU.WebGWT.shared.BusReader;

public class BusModule implements EntryPoint {

    private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

    @Override
    public void onModuleLoad() {
        final ListBox readerListBox = new ListBox(false);
        final Button sendButton = new Button("Получить список");
        final Label errorLabel = new Label();

        RootPanel.get("readerListContainer").add(readerListBox);
        RootPanel.get("errorLabelContainer").add(errorLabel);
        RootPanel.get("sendButtonContainer").add(sendButton);
        
        readerListBox.setFocus(true);

        greetingService.getReaderList(new AsyncCallback<List<String>>() {
            public void onFailure(Throwable caught) {
                errorLabel.setText("Ошибка загрузки списка: " + caught.getMessage());
            }
            public void onSuccess(List<String> result) {
                for (String s : result) {
                    readerListBox.addItem(s);
                }
            }
        });

        sendButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                errorLabel.setText("");
                int selectedIndex = readerListBox.getSelectedIndex();
                if (selectedIndex == -1) {
                    errorLabel.setText("Выберите автобус");
                    return;
                }
                String fio = readerListBox.getItemText(selectedIndex);
                
                final DialogBox dialogBox = new DialogBox();
                dialogBox.setText("Данные автобуса: " + fio);
                dialogBox.setAnimationEnabled(true);

                final Button closeButton = new Button("Закрыть");
                final VerticalPanel dialogVPanel = new VerticalPanel();
                
                greetingService.getStudReaderList(fio, new AsyncCallback<List<BusReader>>() {
                    public void onFailure(Throwable caught) {
                        errorLabel.setText("Ошибка: " + caught.getMessage());
                    }
                    public void onSuccess(List<BusReader> result) {
                        CellTable<BusReader> table = new CellTable<BusReader>();
                        
                        TextColumn<BusReader> fioColumn = new TextColumn<BusReader>() {
                            public String getValue(BusReader object) {
                                return object.getFio();
                            }
                        };
                        table.addColumn(fioColumn, "Автобус");
                        
                        TextColumn<BusReader> facultyColumn = new TextColumn<BusReader>() {
                            public String getValue(BusReader object) {
                                return object.getFaculty();
                            }
                        };
                        table.addColumn(facultyColumn, "Водитель");
                        
                        TextColumn<BusReader> enrolledColumn = new TextColumn<BusReader>() {
                            public String getValue(BusReader object) {
                                return object.isEnrolled() ? "Да" : "Нет";
                            }
                        };
                        table.addColumn(enrolledColumn, "Работал");
                        
                        table.setRowCount(result.size(), true);
                        table.setRowData(0, result);
                        
                        dialogVPanel.add(table);
                        dialogVPanel.add(closeButton);
                        dialogBox.setWidget(dialogVPanel);
                        dialogBox.center();
                    }
                });
                
                closeButton.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        dialogBox.hide();
                    }
                });
            }
        });
    }
}