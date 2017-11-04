package com.zwq.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.removeAllMappings;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import com.github.tomakehurst.wiremock.client.WireMock;
import java.io.IOException;
import javax.annotation.Resource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

public class MockServer {

  public static void main(String[] args) throws IOException {
    String url = "192.168.106.130";
    configureFor(url, 8060);
    removeAllMappings();
    mock("/order/1", "01");
  }

  private static void mock(String url, String file) throws IOException {
    ClassPathResource resource = new ClassPathResource("mock/response/" + file + ".json");
    String content = StringUtils
        .join(FileUtils.readLines(resource.getFile(), "utf-8").toArray(), "\n");
    stubFor(
        get(urlPathEqualTo(url))
            .willReturn(aResponse().withBody(content).withStatus(200)));
  }
}
