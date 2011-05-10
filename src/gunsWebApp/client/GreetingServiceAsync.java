package gunsWebApp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void getGunsAmount(AsyncCallback<String> callback);
	void getGun(String index, String property, AsyncCallback<String> callback);
	void getGunById(String id, AsyncCallback<String> callback);
	void addUpdateGun(String id, String name, AsyncCallback<String> callback);
}
