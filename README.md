# IPNet
IPアドレスやサブネットマスクの表記を入力すると、2つのIPアドレスのネットワーク部を照合して、同じネットワーク内かどうか判定するプログラム  
プロコンハッカソンで作ったものを、練習がてら少し改良したもの  


# pattern 1
## input
192.168.0.1/255.255.255.0
192.168.0.2

## output
このIPアドレスのネットワーク部は192.168.0.0で、同一ネットワークにあります  

---

# pattern 2
## input
192.168.0.1/255.255.255.0
10.0.0.1

## output
このIPアドレスのネットワーク部は10.0.0.0で、同一ネットワークにありません   
