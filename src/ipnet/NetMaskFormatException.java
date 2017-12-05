package ipnet;

public class NetMaskFormatException extends IPv4NetException{
	public NetMaskFormatException() {
		super("ネットマスクの表記が不正です\n"
				+ "\tIPv4のサブネットマスク表記、もしくはCIDR表記で正常なフォーマットを入力して下さい\n");
	}
}
