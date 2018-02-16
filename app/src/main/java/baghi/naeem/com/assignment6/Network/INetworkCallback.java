package baghi.naeem.com.assignment6.Network;

public interface INetworkCallback<T> {

    public void callback(T result, String errorMessage);
}
