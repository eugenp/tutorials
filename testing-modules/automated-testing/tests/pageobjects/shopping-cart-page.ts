import { Page, Locator } from "@playwright/test";

export class ShoppingCart {
  readonly tableLocator: Locator;
  readonly productName: Locator;
  readonly productQty: Locator;

  constructor(page: Page) {
    this.tableLocator = page.locator(".table-responsive table.table-bordered");
    this.productName = this.tableLocator.locator("td").nth(1);
    this.productQty = this.tableLocator.locator("td").nth(3);
  }

  async getProductQty() {
    await this.productQty.getAttribute("defaultValue");
  }
}