import { Page, Locator } from "@playwright/test";
import { ShoppingCart } from "./shopping-cart-page";

export class SearchResultPage {
  readonly pageHeader: Locator;
  readonly selectProduct: Locator;
  readonly notificationPopup: Locator;
  readonly addToCartBtn: Locator;
  readonly successMessage: Locator;
  readonly viewCardBtn: Locator;
  readonly page: Page;

  constructor(page: Page) {
    this.page = page;
    this.pageHeader = page.getByText("Search - iPhone");
    this.selectProduct = page.locator("div.product-layout:nth-child(1)");
    this.notificationPopup = page.locator("#notification-box-top");
    this.addToCartBtn = page.locator("button.btn-cart");
    this.successMessage = this.notificationPopup.locator("p");
    this.viewCardBtn = this.notificationPopup.locator("a.btn-primary");
  }

  async addProductToCart():Promise<void> {
    await this.selectProduct.hover();
    await this.addToCartBtn.first().click();
  }

  async viewCart():Promise<ShoppingCart> {
    await this.viewCardBtn.click();
    return new ShoppingCart(this.page);
  }
}