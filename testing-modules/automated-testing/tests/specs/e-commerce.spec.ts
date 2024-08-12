import { test } from "../../base/page-object-model-fixture";
import { expect } from "@playwright/test";

test("Add product to cart", async ({ page, homePage,baseURL }) => {
  await page.goto(`${baseURL}`);

  await homePage.hoverMyAccountLink();
  await expect(homePage.logoutLink).toBeVisible();

  const searchResultPage = await homePage.searchForProduct("iPhone");
  await searchResultPage.addProductToCart();

  await expect(searchResultPage.successMessage).toContainText(
    "Success: You have added iPhone to your shopping cart!"
  );
  const shoppingCart = await searchResultPage.viewCart();
  await expect(shoppingCart.productName).toContainText("iPhone");
});