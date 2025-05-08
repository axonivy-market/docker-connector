package com.axonivy.connector.docker.test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import com.axonivy.ivy.webtest.IvyWebTest;
import com.axonivy.ivy.webtest.engine.EngineUrl;
import com.codeborne.selenide.Selenide;

@IvyWebTest
public class ITDockerConnector {

  @Test
  public void demo() {
    open(EngineUrl.createProcessUrl("docker-connector-demo/18A50F6DFF3B27D1/start.ivp"));

    // Open link to started engine
    $(By.id("form:OpenEngine")).click();


    Selenide.Wait()
      .withTimeout(Duration.ofMinutes(1))
      .pollingEvery(Duration.ofSeconds(5))
      .ignoring(WebDriverException.class)
      .ignoring(AssertionError.class)
      .until(driver -> {
          Selenide.switchTo().window(1);
          Selenide.refresh();
          assertThat(Selenide.title()).contains("Axon Ivy Engine");
          return true;
        });

    Selenide.switchTo().window(0);

    $(By.id("form:proceed")).click();
  }

}