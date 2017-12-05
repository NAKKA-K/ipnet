package ipnet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPNet {
	static final int NETMASK_ERROR = -1;
	static final int NETMASK_IP = 1;
	static final int NETMASK_CIDR = 2;

	//サブネットマスク表記かサイダー表記か判定するメソッド
	public static int isSubnetOrCIDR(String mask) throws IPv4NetException{
		if(isIP(mask))
			return NETMASK_IP;
		else if(isCIDR(mask))
			return NETMASK_CIDR;

		throw new NetMaskFormatException();
	}

	//IP表記か判定するメソッド
	public static Boolean isIP(String strIP) throws IPv4NetException{
		Pattern pattern = Pattern.compile("^([0-9]+)\\.([0-9]+)\\.([0-9]+)\\.([0-9]+)$"); //IPアドレス
		Matcher matcher = pattern.matcher(strIP);

		if(matcher.find() == false)
			return false; //IP表記ではない

		String ip[] = strIP.split("\\.", 0);
		if(ip.length != 4)
			throw new MaskLangeOutException(); //32bit / 8bit の4分割表記でないため異常値
		else if(ip[0] == "0")
			throw new MaskLangeOutException();

		for(String num:ip){ //IPの範囲が正常か判定
			int n = Integer.valueOf(num);
			if(n < 0 || n > 255)
				throw new MaskLangeOutException();
		}

		return true;
	}

	//CIDR表記か判定するメソッド
	public static Boolean isCIDR(String CIDR) throws IPv4NetException{
		Pattern pattern = Pattern.compile("^[0-9]+$"); //IPアドレス
		Matcher matcher = pattern.matcher(CIDR);

		if(matcher.matches() == false) //CIDR表記ではない
			return false;

		int num = Integer.valueOf(CIDR);
		if(num < 1 || num > 32) //数値の範囲が正常か
			throw new MaskLangeOutException();

		return true;
	}

	//IPv4を受け取って2進数表記の文字列に変換する
	public static String decToBin(String ip)throws IPv4NetException{
		String sec[] = ip.split("\\.", 0);
		String binIP = "";

		for(int i = 0; i < 4; i++){
			binIP += toBinaryStringIP(Integer.valueOf(sec[i]));
		}
		return binIP;
	}

	public static String toBinaryStringIP(int num){
		String bin = Integer.toBinaryString(num);
		for(int i = bin.length(); i < 8; i++){
			bin = "0" + bin;
		}
		return bin;
	}

	//CIDR形式のマスクを2進数表記のネットマスク文字列に変換する
	public static String CIDRToBin(int CIDR) throws IPv4NetException{
		String binIP = "";
		for(int i = 0; i < 32; i++){
			if(i < CIDR)
				binIP += "1";
			else
				binIP += "0";
		}
		return binIP;
	}

	//32bit2進数表記のIPを10進数IPアドレス表記に変換して返す
	public static String binToIPNet(String netMask) throws IPv4NetException{
		Pattern pattern = Pattern.compile("[0-1]{8}"); //32bitネットマスク
		Matcher matcher = pattern.matcher(netMask);

		String ip = "";
		for(int i = 0; matcher.find(); i++){
			ip += String.valueOf(Integer.parseInt(matcher.group(), 2));
			if(i < 3)
				ip += ".";
		}
		return ip;
	}

	//2進数表記のサブネットマスクをand演算した結果を返す
	public static String andSubNetBin(String netMask1, String netMask2) throws MaskBinFormatException{
		String netMask = "";
		for(int i = 0; i < 32; i++){
			if(netMask1.charAt(i) == '1' && netMask2.charAt(i) == '1'){
				netMask += '1';
			}else{
				netMask += '0';
			}
		}
		return netMask;
	}

}
