package broadcastListener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import stationData.StationInfo;
import fileputterGUI.Mover;

public class BroadcastListenServer implements Runnable {
	private int port;
	private Handler messageHandler = new Handler();
	private DatagramSocket socket;
	private Runnable backPoster;

	private boolean interrupt = false;

	private StationInfo result;

	public BroadcastListenServer(int port, Runnable backPoster)
			throws SocketException {
		this.backPoster = backPoster;
		this.port = port;
		createSocket(port);
	}

	private void createSocket(int port) throws SocketException {
		socket = new DatagramSocket(port);
		socket.setReuseAddress(true);
	}

	@Override
	public void run() {
		String tmp = null;
		while (!interrupt) {
			byte[] buf = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(packet);
				tmp = new String(buf);
				result = StationInfo.newFromString(tmp);
				result.setStationAddress(packet.getAddress());
				messageHandler.post(backPoster);
			} catch (IOException e) {
				Mover.error("Mover.NET", e.getMessage());
			} catch (Exception e) {
				Mover.error("Mover.NET", e.getMessage());
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		socket.close();
	}

	/**
	 * @return the results
	 */
	public StationInfo getResult() {
		return result;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResult(StationInfo result) {
		this.result = result;
	}

	public void interrupt() {
		interrupt = true;
		socket.close();
	}

	public void resume() throws SocketException {
		interrupt = false;
		createSocket(port);
	}
}