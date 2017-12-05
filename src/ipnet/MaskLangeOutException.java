package ipnet;

public class MaskLangeOutException extends IPv4NetException{

	public MaskLangeOutException() {
		super("マスクの正常な範囲を超えています\n"
				+ "\tIPv4のサブネットマスク表記、もしくはCIDR表記で正常な範囲の値を入力してください\n");
	}

}
