

/**
 * Created by gaurav_polekar on 4/26/2016.
 */
public interface NetworkResponseListener {
    void onDataReceived(int requestCode,Object data);
    void onDataFailed(int requestCode,Object error);
}
