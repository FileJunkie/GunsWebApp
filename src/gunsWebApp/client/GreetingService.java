package gunsWebApp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String getGunsAmount();
	String getGun(String index, String property);
	String getGunById(String id);
	String addUpdateGun(String id, String name);
}
