package ipnet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPNet {
	static final int NETMASK_ERROR = -1;
	static final int NETMASK_IP = 1;
	static final int NETMASK_CIDR = 2;

	//�T�u�l�b�g�}�X�N�\�L���T�C�_�[�\�L�����肷�郁�\�b�h
	public static int isSubnetOrCIDR(String mask) throws IPv4NetException{
		if(isIP(mask))
			return NETMASK_IP;
		else if(isCIDR(mask))
			return NETMASK_CIDR;

		throw new NetMaskFormatException();
	}

	//IP�\�L�����肷�郁�\�b�h
	public static Boolean isIP(String strIP) throws IPv4NetException{
		Pattern pattern = Pattern.compile("^([0-9]+)\\.([0-9]+)\\.([0-9]+)\\.([0-9]+)$"); //IP�A�h���X
		Matcher matcher = pattern.matcher(strIP);

		if(matcher.find() == false)
			return false; //IP�\�L�ł͂Ȃ�

		String ip[] = strIP.split("\\.", 0);
		if(ip.length != 4)
			throw new MaskLangeOutException(); //32bit / 8bit ��4�����\�L�łȂ����߈ُ�l
		else if(ip[0] == "0")
			throw new MaskLangeOutException();

		for(String num:ip){ //IP�͈̔͂����킩����
			int n = Integer.valueOf(num);
			if(n < 0 || n > 255)
				throw new MaskLangeOutException();
		}

		return true;
	}

	//CIDR�\�L�����肷�郁�\�b�h
	public static Boolean isCIDR(String CIDR) throws IPv4NetException{
		Pattern pattern = Pattern.compile("^[0-9]+$"); //IP�A�h���X
		Matcher matcher = pattern.matcher(CIDR);

		if(matcher.matches() == false) //CIDR�\�L�ł͂Ȃ�
			return false;

		int num = Integer.valueOf(CIDR);
		if(num < 1 || num > 32) //���l�͈̔͂����킩
			throw new MaskLangeOutException();

		return true;
	}

	//IPv4���󂯎����2�i���\�L�̕�����ɕϊ�����
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

	//CIDR�`���̃}�X�N��2�i���\�L�̃l�b�g�}�X�N������ɕϊ�����
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

	//32bit2�i���\�L��IP��10�i��IP�A�h���X�\�L�ɕϊ����ĕԂ�
	public static String binToIPNet(String netMask) throws IPv4NetException{
		Pattern pattern = Pattern.compile("[0-1]{8}"); //32bit�l�b�g�}�X�N
		Matcher matcher = pattern.matcher(netMask);

		String ip = "";
		for(int i = 0; matcher.find(); i++){
			ip += String.valueOf(Integer.parseInt(matcher.group(), 2));
			if(i < 3)
				ip += ".";
		}
		return ip;
	}

	//2�i���\�L�̃T�u�l�b�g�}�X�N��and���Z�������ʂ�Ԃ�
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
