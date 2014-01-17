package com.github.josrutten.wiremock.restresource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/sample/")
public class SampleJerseyRestService {

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Sample getSampleDetail() {
		return new Sample("just some text", "some more text");
	}

}
