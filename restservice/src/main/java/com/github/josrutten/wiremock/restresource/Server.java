package com.github.josrutten.wiremock.restresource;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class Server {

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost/").port(8888).build();
	}

	public static final URI BASE_URI = getBaseURI();

	@SuppressWarnings("unchecked")
	protected static HttpServer startServer() throws IOException {
		System.out.println("Starting server...");
		ResourceConfig rc = new PackagesResourceConfig("com.github.josrutten.wiremock.restresource");
		rc.getContainerResponseFilters().add(new ContainerResponseFilter() {
			@Override
			public ContainerResponse filter(final ContainerRequest request, final ContainerResponse response) {
				response.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
				if ("OPTIONS".equals(request.getMethod())) {
					response.getHttpHeaders().add("Access-Control-Allow-Headers", "Content-Type");
				}
				return response;
			}
		});
		rc.getContainerRequestFilters().add(new ContainerRequestFilter() {
            
            @Override
            public ContainerRequest filter(ContainerRequest request) {
                System.out.println("Host header:" + request.getHeaderValue("Host"));
                return request;
            }
        });
		return GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
	}

	public static void main(String[] args) throws IOException {
		HttpServer httpServer = startServer();
		System.out.println(String.format("Jersey app started with WADL available at "
								+ "%sapplication.wadl\nTry out %ssample\nHit enter to stop it...",
								BASE_URI, BASE_URI));
		System.in.read();
		httpServer.stop();
	}
}