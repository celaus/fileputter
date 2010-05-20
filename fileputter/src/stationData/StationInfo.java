package stationData;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Random;
import java.util.StringTokenizer;

public class StationInfo implements Serializable {

	private int id;
	private String stationName;
	private int responseId;
	private InetAddress stationAddress;

	/**
	 *
	 */
	private static final long serialVersionUID = -7345931383382764925L;

	public StationInfo() {
		stationName = "testPackage";
		this.responseId = -1;
		this.id = -1;
	}

	public StationInfo(String stationName, int respondsTo) {
		this.stationName = stationName;
		this.responseId = respondsTo;
		this.id = new Random().nextInt(Integer.MAX_VALUE);
	}

	public StationInfo(String stationName, int respondsTo, int id) {
		this.stationName = stationName;
		this.responseId = respondsTo;
		this.id = id;
	}

	@Override
	public String toString() {
		return stationName;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public int getResponseId() {
		return responseId;
	}

	public int getId() {
		return id;
	}

	public static StationInfo newFromString(String tmp) throws Exception {
		StringTokenizer stok = new StringTokenizer(tmp, "=;");
		String tmpStr = null;
		String name = null;
		int responseId = -1;
		int id = -1;
		Token tok = Token.ERROR;
		int nameCnt = 0, respCnt = 0, idCnt = 0;
		if (stok.countTokens() == ((Token.values().length - 1) * 2)) {
			while (stok.hasMoreTokens()) {
				tmpStr = stok.nextToken();

				if (tmpStr.equals("name") && nameCnt == 0)
					tok = Token.NAME;
				else if (tmpStr.equals("responseid") && respCnt == 0)
					tok = Token.RESPONSEID;
				else if (tmpStr.equals("id") && idCnt == 0)
					tok = Token.ID;
				else
					throw new Exception("Invalid Network Message!");

				switch (tok) {
				case NAME:
					name = stok.nextToken();
					nameCnt++;
					break;
				case RESPONSEID:
					responseId = Integer.parseInt(stok.nextToken().trim());
					respCnt++;
					break;
				case ID:
					id = Integer.parseInt(stok.nextToken().trim());
					idCnt++;
					break;
				default:
					throw new Exception("Invalid Network Message!");
				}
			}
			StationInfo s = new StationInfo(name, responseId, id);

			return s;
		} else
			throw new Exception("Invalid Network Message!");
	}

	public String getNetworkString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name=" + stationName + ";");
		sb.append("responseid=" + responseId + ";");
		sb.append("id=" + id);
		return sb.toString();
	}

	public void setStationAddress(InetAddress stationAddress) {
		this.stationAddress = stationAddress;
	}

	public InetAddress getStationAddress() {
		return stationAddress;
	}


	private enum Token {
		NAME, RESPONSEID, ID, ERROR
	}

	public boolean equals(StationInfo s) {
		return this.stationAddress.equals(s);
	}
}