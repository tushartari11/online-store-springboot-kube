package com.rekreation.store.order.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class InventoryClientStub {

  public static void stubInventoryCall(String skuCode, int quantity) {
//    System.out.println("Stubbing InventoryClient#isInStock for skuCode: " + skuCode + " and quantity: " + quantity);
      stubFor(get(urlEqualTo("/api/inventory?skuCode=" + skuCode + "&quantity=" + quantity))
        .willReturn(aResponse()
          .withHeader("Content-Type", "application/json")
          .withStatus(200)
          .withBody("true")));
  }

}
