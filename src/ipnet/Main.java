package ipnet;

import java.util.Scanner;

public class Main {
	static final String ERROR = "���͒l������������܂���";

	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);

		Boolean isCIDR = false;
		String ip2 = "";

		String ipAndMask = sc.nextLine();
		String ipSetting[] = ipAndMask.split("/", 0);

		//�G���[����
		try{
			if(ipSetting.length != 2 || IPNet.isIP(ipSetting[0]) == false){ //IP��Mask�����݂��Ȃ����AIP�\�L�������������H
				throw new IPv4NetException("");
			}else if(IPNet.isSubnetOrCIDR(ipSetting[1]) == IPNet.NETMASK_IP){
				isCIDR = false;
			}else if(IPNet.isSubnetOrCIDR(ipSetting[1]) == IPNet.NETMASK_CIDR){
				isCIDR = true;
			}else{
				throw new IPv4NetException("");
			}

			ip2 = sc.nextLine();
			if(IPNet.isIP(ip2) == false){ //2�ڂ�IP���ُ킩�H
				throw new IPv4NetException("");
			}

		}catch(IPv4NetException e){
			System.out.println(ERROR);
		}finally{
			sc.close();
		}


		//ok
		String netMask = "";
		if(isCIDR){
			netMask = IPNet.CIDRToBin(Integer.valueOf(ipSetting[1]));
		}else{
			netMask = IPNet.decToBin(ipSetting[1]);
		}

		//IP��32bit�o�C�i���`���ɕϊ�
		String net1 = IPNet.decToBin(ipSetting[0]);
		String net2 = IPNet.decToBin(ip2);

		//2��IP�̃l�b�g���[�N�����Z�o
		net1 = IPNet.andSubNetBin(net1, netMask);
		net2 = IPNet.andSubNetBin(net2, netMask);


		System.out.printf("����IP�A�h���X�̃l�b�g���[�N����%s�ŁA����l�b�g���[�N��", IPNet.binToIPNet(net2));
		if(net1.equals(net2)){
			System.out.println("����܂�");
		}else{
			System.out.println("����܂���");
		}
	}
}
