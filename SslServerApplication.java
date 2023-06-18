package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}
//FIXME: Add route to enable check sum return of static data example:  String data = "Hello World Check Sum!";
@RestController 
class ServerController {
	@RequestMapping("/hash")
	public String myHash() {
		String data = "Hello World Check Sum!";
		String checkSum = createChecksum(data);
		String usersName = "Gerardo Gonzalez";
		return "<p>data: "+data+"</p>"+"<p>Student: "+usersName+"</p>"+"<p>Check Sum: "+checkSum+"</p>";
	}
	private String createChecksum(String data) {
		try { 
			MessageDigest dig = MessageDigest.getInstance("SHA-256");
			byte [] hashy = dig.digest(data.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(hashy); 
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2* hash.length);
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}