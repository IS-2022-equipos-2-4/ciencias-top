package com.unam.cienciastop.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.unam.cienciastop.entity.Usuario;
import com.unam.cienciastop.service.SvcUsuario;


@Component
public class InfoAdicionalToken implements TokenEnhancer{
	
	@Autowired
	private SvcUsuario usuarioService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Usuario usuario = usuarioService.findByNumInstitucional(authentication.getName());
		Map<String, Object> info = new HashMap<>();
		info.put("id", usuario.getId());
		info.put("numInstitucional", usuario.getNumInstitucional());
		info.put("nombre", usuario.getNombre());
		info.put("email", usuario.getCorreo());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}

}

