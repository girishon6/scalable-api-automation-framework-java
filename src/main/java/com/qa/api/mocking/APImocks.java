package com.qa.api.mocking;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

public class APImocks {

	public static void defineGetCallForSingleUser() {
		//http://localhost:8089/api/users
		stubFor(get(urlEqualTo("/api/users"))
					.willReturn(aResponse()
					.withStatus(200)
					.withHeader("Content-Type", "application/json")
					.withBody("{\r\n"
							+ "    \"name\": \"john\"\r\n"
							+ "}"))
				);
	}
	
	public static void defineGetCallForSingleUserUsingJsonFile() {
		//http://localhost:8089/api/users
		stubFor(get(urlEqualTo("/api/users"))
					.willReturn(aResponse()
					.withStatus(200)
					.withHeader("Content-Type", "application/json")
					.withBodyFile("user.json"))
				);
	}
	
	public static void defineGetCallForSingleUserUsingQueryparam() {
		//http://localhost:8089/api/users
		stubFor(get(urlPathEqualTo("/api/users"))
					.withQueryParam("name",equalTo("girish"))
					.withQueryParam("status",equalTo("inactive"))
					.willReturn(aResponse()
					.withStatus(200)
					.withHeader("Content-Type", "application/json")
					.withBody("{\r\n"
							+ "    \"name\": \"girish\",\r\n"
							+ "    \"status\": \"inactive\"\r\n"
							+ "}")));
	}
	
	public static void definePostCallForSingleUser() {
		//http://localhost:8089/api/users
		stubFor(post(urlEqualTo("/api/users"))
						.withHeader("Content-Type",equalTo("application/json"))
						.willReturn(aResponse()
						.withStatus(201)
						.withHeader("Content-Type", "application/json")
						.withBody("{\r\n"
								+ "    \"id\": 8441981,\r\n"
								+ "    \"name\": \"Ted \",\r\n"
								+ "    \"email\": \"Teddd01@Test.com\",\r\n"
								+ "    \"gender\": \"male\",\r\n"
								+ "    \"status\": \"active\"\r\n"
								+ "}"))
						);
						
	}
	
	public static void definePutCallForSingleUser() {
		//http://localhost:8089/api/users
		stubFor(put(urlEqualTo("/api/users"))
						.withHeader("Content-Type", equalTo("application/json"))
						.willReturn(aResponse()
									.withHeader("Content-Type", "application/json")
									.withStatus(200)
									.withResponseBody(null)));
						
	}
}
