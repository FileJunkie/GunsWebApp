package gunsWebApp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GunsWebApp implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	//private static final String SERVER_ERROR = "An error occurred while "
		//	+ "attempting to contact the server. Please check your network "
		//	+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private final FlexTable roTable = new FlexTable();
	private final FlexTable editTable = new FlexTable();
	private final Button fillById = new Button("Fill fields by weapon id");
	private final Button sendToServer = new Button("Add/Update gun");
	TextBox txtId = new TextBox();
	TextBox txtName = new TextBox();
	
	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {		
		RootPanel.get("allGuns").add(roTable);
		RootPanel.get("editGuns").add(editTable);
		
		editTable.setWidget(0, 0, txtId);
		editTable.setWidget(0, 1, txtName);
		
		editTable.setWidget(1, 0, fillById);
		editTable.setWidget(1, 1, sendToServer);
		
		roTable.setBorderWidth(1);
		roTable.setCellPadding(5);
		roTable.setCellSpacing(0);
		editTable.setBorderWidth(1);
		editTable.setCellPadding(5);
		editTable.setCellSpacing(0);
		
		greetingService.getGunsAmount(new AsyncCallback<String>(){
			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(String result) {
				getAllGuns(Integer.parseInt(result));
			}
		});
		
		class FillHandler implements ClickHandler{
			@Override
			public void onClick(ClickEvent event) {
				String id = txtId.getText();
				
				greetingService.getGunById(id, new AsyncCallback<String>(){
					@Override
					public void onFailure(Throwable caught) {
						
					}
					@Override
					public void onSuccess(String result) {
						txtName.setText(result);
					}
				});
			}
		}
		
		FillHandler fillHandler = new FillHandler();
		fillById.addClickHandler(fillHandler);
		
		class UpdateHandler implements ClickHandler{
			@Override
			public void onClick(ClickEvent event){
				String id = txtId.getText();
				String name = txtName.getText();
				
				greetingService.addUpdateGun(id, name, new AsyncCallback<String>(){
					@Override
					public void onFailure(Throwable caught) {
						
					}
					@Override
					public void onSuccess(String result) {
						roTable.removeAllRows();
						
						greetingService.getGunsAmount(new AsyncCallback<String>(){
							@Override
							public void onFailure(Throwable caught) {
								
							}

							@Override
							public void onSuccess(String result) {
								getAllGuns(Integer.parseInt(result));
							}
						});
					}
				});				
			}
		}
		
		UpdateHandler updateHandler = new UpdateHandler();
		sendToServer.addClickHandler(updateHandler);
	}
	
	void getAllGuns(int amount){		
		for(int i = 0; i < amount; i++){
			final int row = i;
					
			greetingService.getGun(Integer.toString(i), "id", new AsyncCallback<String>(){
				@Override
				public void onFailure(Throwable caught) {
				
				}

				@Override
				public void onSuccess(String result) {
					roTable.setText(row, 0, result);
				}
			});
			
			greetingService.getGun(Integer.toString(i), "name", new AsyncCallback<String>(){
				@Override
				public void onFailure(Throwable caught) {
				
				}

				@Override
				public void onSuccess(String result) {
					roTable.setText(row, 1, result);
				}
			});
		}				
	}
}
