package ipnet;

public class MaskBinFormatException extends IPv4NetException{

	public MaskBinFormatException() {
		super("2進数サブネットマスク表記が不正です\n"
				+ "\t32bitの2進数表記を入力して下さい");
	}

}
