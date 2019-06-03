package it.prepattag.F1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 
 */
@RestController
public class LoginController {
	
	
	/**
	 * Metodo per l'autenticazione dell'utente
	 * @param user	username utente
	 * @param psw	password utente
	 * @return		Se utente è esistente..
	 */
	@RequestMapping(value = "authenticate", method =RequestMethod.POST)
	public boolean login(@RequestParam("username") String user, @RequestParam("password") String psw) throws NoSuchAlgorithmException{
		boolean ris = true;
		String a = DigestUtils.sha256Hex("123");
		
		
		return psw.equals(a) && user.equals("prova");
	}
}
