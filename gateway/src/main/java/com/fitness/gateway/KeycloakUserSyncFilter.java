package com.fitness.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.fitness.gateway.user.RegisterRequest;
import com.fitness.gateway.user.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class KeycloakUserSyncFilter implements WebFilter{

	@Autowired
	private UserService userService;
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange , WebFilterChain chain){
		String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
		String token = exchange.getRequest().getHeaders().getFirst("Authorization");
		RegisterRequest registerRequest = getUserDetails(token);
		
 		if(userId == null) {
			userId = registerRequest.getKeycloakId();
		}
		
		if(userId != null && token != null) {
			String finaluserId = userId;
			return userService.validateUser(userId)
					.flatMap(exist -> {
						if(!exist) {
							//register user
							
							if(registerRequest != null) {
								return userService.registerUser(registerRequest)
										.then(Mono.empty());
							}else {
								return Mono.empty();
							}
						}
						else {
							log.info("User already exists skipping sync");
							return Mono.empty();
						}
					})
					.then(Mono.defer(() -> {
						
						ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
				                .header("X-User-ID", finaluserId)
				                .build();

				        // 2. Mutate the exchange to include the new request
				        ServerWebExchange mutatedExchange = exchange.mutate()
				                .request(mutatedRequest)
				                .build();

				        log.info("Forwarding request to downstream with X-User-ID: {}", finaluserId);
				        
				        // 3. IMPORTANT: Pass the MUTATED exchange to the next filter
				        return chain.filter(mutatedExchange);
					}));
		}
		return chain.filter(exchange); 

		
	}

	private RegisterRequest getUserDetails(String token) {
		try {
			
			String tokenWithoutBearer = token.replace("Bearer ", "");
			SignedJWT signedJWT = SignedJWT.parse(tokenWithoutBearer);
			JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
			
			RegisterRequest registerRequest = new RegisterRequest();
			
			registerRequest.setEmail(claims.getStringClaim("email"));
			registerRequest.setFirstName(claims.getStringClaim("given_name"));
			registerRequest.setKeycloakId(claims.getStringClaim("sub"));
			log.info("Token is {}" ,claims.getStringClaim("sub"));
			registerRequest.setPassword("dummy@1212");
			registerRequest.setLastName(claims.getStringClaim("family_name"));
			return registerRequest;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		//return null;
	}
}




/*
 		if(userId == null) {
			userId = registerRequest.getKeycloackId();
		}
		
		if(userId != null && token != null) {
			String finaluserId = userId;
			return userService.validateUser(userId)
					.flatMap(exist -> {
						if(!exist) {
							//register user
							
							if(registerRequest != null) {
								return userService.registerUser(registerRequest)
										.then(Mono.empty());
							}else {
								return Mono.empty();
							}
						}
						else {
							log.info("User already exists skipping sync");
							return Mono.empty();
						}
					})
					.then(Mono.defer(() -> {
						
						ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
								.header("X-User-ID", finaluserId)
								.build();
						return chain.filter(exchange.mutate().request(mutatedRequest).build());
					}));
		}
		return chain.filter(exchange); 
 */


/*
 	    if (userId == null && registerRequest != null) {
	        userId = registerRequest.getKeycloakId();
	    }

	    if (userId == null || token == null) {
	        return chain.filter(exchange);
	    }

	    String finalUserId = userId;

	    return userService.validateUser(finalUserId)
	        .flatMap(exists -> {
	            if (!exists && registerRequest != null) {
	                return userService.registerUser(registerRequest);
	            }
	            return Mono.empty();
	        })
	        .then(Mono.defer(() -> {
	            HttpHeaders newHeaders = new HttpHeaders();
	            newHeaders.putAll(exchange.getRequest().getHeaders());
	            newHeaders.set("X-User-ID", finalUserId);

	            ServerHttpRequest mutatedRequest =
	                new DefaultServerHttpRequestBuilder(exchange.getRequest())
	                    .headers(httpHeaders -> {
	                        httpHeaders.clear();
	                        httpHeaders.putAll(newHeaders);
	                    })
	                    .build();

	            return chain.filter(
	                exchange.mutate().request(mutatedRequest).build()
	            );
	        }));
  
 */
